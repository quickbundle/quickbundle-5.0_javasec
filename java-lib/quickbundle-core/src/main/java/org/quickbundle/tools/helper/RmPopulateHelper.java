package org.quickbundle.tools.helper;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.quickbundle.ICoreConstants;
import org.quickbundle.base.vo.RmValueObject;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.itf.IPopulateParser;
import org.quickbundle.project.serializer.RmObjectMapper;
import org.quickbundle.project.serializer.RmPopulateParser;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.util.RmSequenceMap;
import org.slf4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotWritablePropertyException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class RmPopulateHelper<E> {
	static Logger log = RmLogHelper.getLogger(RmPopulateHelper.class);
	static IPopulateParser parser = new RmPopulateParser();
    /**
     * 功能: 
     *
     * @param destinationObj 目标对象(RmValueObject, RmCommonVo, Map)
     * @param sourceObj 来源对象(ServletRequest, ResultSet, RmValueObject, RmCommonVo, Map)
     * @return
     */
    public static int populate(Object destinationObj, Object sourceObj) {
        return populate(destinationObj, sourceObj, null, null, null, null);
    }

    /**
     * 功能: 
     *
     * @param destinationObj 目标对象(ServletRequest, ResultSet, RmValueObject)
     * @param sourceObj 来源对象
     * @param fieldMap field名对应，比如要实现Object.setName(ResultSet.getString("myname"))，需提前fieldMap.put("name", "myname")
     * @param ignoreField 对destinationObj中忽略的field名
     * @param iterateObj 自定义
     * @param getValue
     * @return
     */
    public static int populate(Object destinationObj, Object sourceObj, Map fieldMap, String[] ignoreField, IRmIterateObj iterateObj, IRmGetValue getValue) {
    	if(destinationObj == null || sourceObj == null) {
    		return 0;
    	}
    	boolean destination2source = true;
    	if(destinationObj instanceof ResultSet || destinationObj instanceof ServletRequest) {
    		throw new RuntimeException("destinationObj是不支持的注值类型:" + destinationObj.getClass());
    	}
    	
    	if(destinationObj instanceof Map) {//如果目标是Map类，从右到左
    		destination2source = false;
    	} else if(sourceObj instanceof ResultSet) { //如果来源是ResultSet，从右到左
    		destination2source = false;
    	}
    	return populate(destinationObj, sourceObj, fieldMap, ignoreField, iterateObj, getValue, destination2source);
    }
    
    /**
     * 功能: 
     *
     * @param destinationObj 目标对象(ServletRequest, ResultSet, RmValueObject)
     * @param sourceObj 来源对象
     * @param fieldMap field名对应，比如要实现Object.setName(ResultSet.getString("myname"))，需提前fieldMap.put("name", "myname")
     * @param ignoreField 对destinationObj中忽略的field名
     * @param iterateObj 自定义
     * @param getValue
     * @param destination2source true是从destinationObj取出可能的set方法，再从sourceObj调用get方法
     * @return
     */
    @SuppressWarnings("unchecked")
    public static int populate(Object destinationObj, Object sourceObj, Map fieldMap, String[] ignoreField, IRmIterateObj iterateObj, IRmGetValue getValue, final boolean destination2source) {
        int sum = 0;
        if(getValue != null) {
            //采用自定义的取值
        } else if (sourceObj instanceof Map) {
            getValue = new IRmGetValue() {
                public Object getValue(Object sourceObj, String key) {
                    return ((Map)sourceObj).get(key);
                }
                public boolean containKey(Object sourceObj, String key) {
                    return ((Map)sourceObj).containsKey(key);
                }
				public Map getKeyValueMap(Object sourceObj) {
					return (Map)sourceObj;
				}
            };
        }  else if (sourceObj instanceof ServletRequest) {
            getValue = new IRmGetValue() {
                public Object getValue(Object sourceObj, String key) {
                	ServletRequest request = ((ServletRequest)sourceObj);
                	if(request.getParameterValues(key) == null || request.getParameterValues(key).length == 0) {
                		return null;
                	}
                	if(request.getParameterValues(key).length == 1) {
                		return request.getParameter(key);
                	} else {
                		return RmStringHelper.parseToSQLString(request.getParameterValues(key));
                	}
                }
                public boolean containKey(Object sourceObj, String key) {
                    return ((ServletRequest)sourceObj).getParameterMap().containsKey(key);
                }
				public Map getKeyValueMap(Object sourceObj) {
					Map<String, String[]> mRequest = ((ServletRequest)sourceObj).getParameterMap();
					Map<String, Object> mReturn = new HashMap<String, Object>();
					for(String key: mRequest.keySet()) {
						mReturn.put(key, RmStringHelper.parseToString(mRequest.get(key)));
					}
					return mReturn;
				}
            };
        } else if (sourceObj instanceof ResultSet) {
            getValue = new IRmGetValue() {
                public Object getValue(Object sourceObj, String key) {
                    ResultSet rs = (ResultSet)sourceObj;
                    Object obj = getResultValue(rs, key);
                    return obj;
                }
                public boolean containKey(Object sourceObj, String key) {
                	ResultSet rs = (ResultSet)sourceObj;
                	try {
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	for (int i=1; i<=rsmd.getColumnCount(); i++) {
							if(rsmd.getColumnLabel(i).equalsIgnoreCase(key)) {
								return true;
							}
						}
					} catch (SQLException e) {
						throw new RuntimeException("ResultSet containKey('" + key + "') error", e);
					}

				   return false;
               }
				public Map getKeyValueMap(Object sourceObj) {
					Map<String, Object> m = new RmSequenceMap();
					ResultSet rs = (ResultSet)sourceObj;
                	try {
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	for (int i=1; i<=rsmd.getColumnCount(); i++) {
                    		String columnLabel = rsmd.getColumnLabel(i);
                    		//如果出现小写字母后面紧跟大写字母，则判定为驼峰式规范，保持大小写和sql一致
                    		if(!Pattern.compile("(?<=[a-z])[A-Z]").matcher(columnLabel).find()) {
                    			columnLabel = columnLabel.toLowerCase();
                    		}
                    		m.put(columnLabel, getResultValue(rs, columnLabel));
						}
					} catch (SQLException e) {
						throw new RuntimeException("ResultSet getKeyValueMap() error", e);
					}
					return m;
				}
            };
        } else if (sourceObj instanceof RmValueObject) {
            getValue = new IRmGetValue() {
                public Object getValue(Object sourceObj, String key) {
                    return RmVoHelper.getVoFieldValue(sourceObj, key);
                }
                public boolean containKey(Object sourceObj, String key) {
                    return RmVoHelper.voContainMethod(sourceObj, "get" + RmStringHelper.toFirstUpperCase(key));
                }
				public Map getKeyValueMap(Object sourceObj) {
					//TODO 实现VO-->Map的转化
					return null;
				}
                
            };
        }
        
        //注值
        if(iterateObj == null) {
        	if(destinationObj instanceof Map) {
            	iterateObj = new IRmIterateObj() {
            	    public int iterateObj(Object destinationObj, Object sourceObj, Map fieldMap, String[] ignoreField, IRmGetValue getValue) {
            	    	int sum = 0;
            	    	Map mDestination = (Map)destinationObj;
            	    	if(destination2source) { //从RmCommonVo或Map取出所有get方法的值
            	    		for(Object key : mDestination.keySet()) {
            	    			String keyStr = key != null ? key.toString() : null;
            	    			mDestination.put(key, getValue.getValue(sourceObj, keyStr));
            	    		}
            	    	} else { //一次性从sourceObj取出所有值，赋值给RmCommonVo或Map
            	    		Map mSource = getValue.getKeyValueMap(sourceObj);
            	    		for(Object key : mSource.keySet()) {
            	    			mDestination.put(key, mSource.get(key));
            	    		}
            	    	}
            	        return sum;
            	    }
            	};
            } else if(destinationObj instanceof RmValueObject || destinationObj instanceof Serializable) {
            	iterateObj = new IRmIterateObj() {
            	    public int iterateObj(Object destinationObj, Object sourceObj, Map fieldMap, String[] ignoreField, IRmGetValue getValue) {
            	    	Object vo = destinationObj;
            	    	int sum = 0;
            	        BeanWrapper bw = new BeanWrapperImpl(vo);
            	        PropertyDescriptor pd[] = bw.getPropertyDescriptors();
            	        Map mSourceAll = null;
               	        if(!destination2source) { //一次性从sourceObj取出所有值，赋值给RmCommonVo或Map
               	        	mSourceAll = getValue.getKeyValueMap(sourceObj);
            	        }
            	        for (int i = 0; i < pd.length; i++) {
            	        	try {
            	        		String currentKey = pd[i].getName();
            	        		if (!"class".equals(currentKey) && !RmStringHelper.arrayContainString(ignoreField, currentKey)) {
            	        			if(fieldMap != null) {
            	        				//如果fieldMap只有{"", "$0"},是ajax注值模式
            	        				if(fieldMap.size() == 1 && fieldMap.get("") != null) {
            	        					currentKey = currentKey + fieldMap.get("");
            	        				} else if(fieldMap.get(currentKey) != null) {
            	        					currentKey = fieldMap.get(currentKey).toString();
            	        				}
            	        			}
            	        			Object value = null;
            	        			if(destination2source) {
            	        				value = getValue.getValue(sourceObj, currentKey);
            	        			} else  {
            	        				if(mSourceAll != null) {
            	        					value = mSourceAll.get(currentKey);
            	        				} else {
            	        					log.warn("从source到destination注值模式下，sourceObj.getKeyValueMap()是null");
            	        				}
            	        			}
            	        			if(value == null) {
            	        				continue;
            	        			}
            	        			Class propertyType = pd[i].getPropertyType();
            	        			Object valueFinal = parser.parse(propertyType, value);
            	        			if(valueFinal == null) {
            	        				continue;
            	        			}
            	        			bw.setPropertyValue(pd[i].getName(), valueFinal);                            
            	        			sum ++;
            	        		}
            	        	} catch (NotWritablePropertyException e) {
            	        		continue;
            	        	} catch (RuntimeException e) {
            	        		if(e.getCause() instanceof NoSuchMethodException) {
            	        			continue;
            	        		} else {
            	        			log.warn(e.toString());
            	        			throw e;
            	        		}
            	        	}
            	        }
            	        return sum;
            	    }
            	};
            } else {
            	throw new RuntimeException("destinationObj是不支持的类型:" + destinationObj.getClass().getName());
            }
        }
        sum = iterateObj.iterateObj(destinationObj, sourceObj, fieldMap, ignoreField, getValue);
        return sum;
    }
    
    /**
     * 得到Result值
     * 
     * @param resultObj
     * @return
     */
	public static Object getResultValue(ResultSet rs, String columnName) {
		try {
			Object resultObj = rs.getObject(columnName);
			if(resultObj instanceof Date) {
				if(ICoreConstants.DatabaseProductType.ORACLE.getDatabaseProductName().equals(RmBaseConfig.getSingleton().getDatabaseProductName())) {
					resultObj = rs.getTimestamp(columnName);
				}
			} else if (resultObj instanceof Clob) {
				StringBuilder sb = new StringBuilder();
				Reader reader = ((Clob) resultObj).getCharacterStream();
				BufferedReader br = new BufferedReader(reader);
				String tempStr = null;
				while ((tempStr = br.readLine()) != null) {
					sb.append(tempStr + "\n");
				}
				br.close();
				reader.close();
				return sb.toString();
			} else if (resultObj instanceof Blob) { //Blob输出到byte[]返回
				InputStream is = ((Blob) resultObj).getBinaryStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = bis.read(b)) != -1) {
					baos.write(b, 0, len);
				}
				bis.close();
				is.close();
				return baos.toByteArray();
			}
			return resultObj;
		} catch (SQLException e) {
			if(e.toString().indexOf("列名无效") > -1 || e.toString().indexOf("Invalid column name") > -1) {
				return null;
			} else {
				//throw new RmRuntimeException("获取" + columnName + "出错", e);
				return null;
			}
		} catch (IOException e) {
			throw new RuntimeException("ResultSet获取" + columnName + "出错", e);
		}
	}
    
    public interface IRmIterateObj {
        public int iterateObj(Object destinationObj, Object sourceObj, Map fieldMap, String[] ignoreField, IRmGetValue getValue);
    }
    
    public interface IRmGetValue {
        public Object getValue(Object sourceObj, String key);
        public boolean containKey(Object sourceObj, String key);
        public Map getKeyValueMap(Object sourceObj);
    }
     
    /**
     * ajax注值模式,各vo之间用name$1, name$2区分
     * 
     * @param <T> vo泛型
     * @param classVo vo的class对象
     * @param request 存放form提交数据的request
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @SuppressWarnings("unchecked")
	public static<T> List<T> populateAjax(Class<T> classVo, HttpServletRequest request) {
    	List<T> lvo = new ArrayList<T>();
    	if(RmBaseConfig.getSingleton().isSubmitJson()) {
    		String jsonStr = request.getParameter(ICoreConstants.RM_AJAX_JSON);
    		List vos;
			try {
				vos = RmObjectMapper.getInstance().readValue(jsonStr, List.class);
			} catch (JsonParseException e) {
				throw new RuntimeException(e);
			} catch (JsonMappingException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			for (Object vo : vos) {
				lvo.add(RmObjectMapper.getInstance().convertValue(vo, classVo));
			}
    	} else {
        	int rm_record_length = Integer.parseInt(request.getParameter(ICoreConstants.RM_AJAX_RECORD_SIZE));
        	for(int i=0; i<rm_record_length; i++) {
        	   	try {
    				T vo = classVo.newInstance();
    				Map fieldMap = new HashMap();
    				fieldMap.put("", ICoreConstants.RM_AJAX_SPLIT_KEY + i);
    				populate(vo, request, fieldMap, null, null, null);
    				lvo.add(vo);
    			} catch (Exception e) {
    				log.warn("populateAjax " + classVo.getName() + " " + e.toString());
    			}
        	}
    	}

    	return lvo;
    }
    
	/**
	 * 针对多个vo，按出现的顺序循环注值
	 * 
	 * @param <T> vo泛型
	 * @param classVo vo的class对象
	 * @param request 存放form提交数据的request
	 * @param keyName 某个不为null(可以是""空字符串)的关键字段,此字段的数组length决定注值vo的length
	 * @return
	 */
	public static<T> List<T> populateVos(Class<T> classVo, HttpServletRequest request, String keyName) {
    	return populateVos(classVo, request, keyName, "");
    }
    
	/**
	 * 针对多个vo，按出现的顺序循环注值
	 * 
	 * @param <T> vo泛型
	 * @param classVo vo的class对象
	 * @param request 存放form提交数据的request
	 * @param keyName 某个不为null(可以是""空字符串)的关键字段,此字段的数组length决定注值vo的length
	 * @param namespacePrefix 命名空间前缀，从request.getParameter取值时会加上namespacePrefix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> List<T> populateVos(Class<T> classVo, HttpServletRequest request, String keyName, String namespacePrefix) {
		if(namespacePrefix == null) {
			namespacePrefix = "";
		}
		keyName = namespacePrefix + keyName;
    	List<T> lvo = new ArrayList<T>();
    	if(request.getParameterValues(keyName) == null) {
    		return lvo;
    	}
    	int rm_record_length = request.getParameterValues(keyName).length;
    	Set<String> sKey = request.getParameterMap().keySet();
    	Map<String, String>[] mps = new HashMap[rm_record_length];
    	for(int i=0; i<mps.length; i++) {
    		mps[i] = new HashMap<String, String>();
    	}
    	for(int i=0; i<rm_record_length; i++) {
    		for(String key : sKey) {
    			String[] values = request.getParameterValues(key);
    			if(!key.startsWith(namespacePrefix)) {
    				continue;
    			}
    			String shortKey = key.substring(namespacePrefix.length());
    			if(values != null && values.length > i) {
    				mps[i].put(shortKey, values[i]);
    			}
    		}
    	}
    	for(int i=0; i<rm_record_length; i++) {
    	   	try {
				T vo = classVo.newInstance();
				populate(vo, mps[i], null, null, null, null);
				lvo.add(vo);
			} catch (Exception e) {
				log.warn("populateVos " + classVo.getName() + " " + e.toString());
			}
    	}
    	return lvo;
    }
    
}