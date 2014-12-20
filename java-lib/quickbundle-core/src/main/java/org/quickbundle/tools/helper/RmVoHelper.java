package org.quickbundle.tools.helper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.quickbundle.ICoreConstants;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.itf.ITransctVoField;
import org.quickbundle.project.serializer.RmBeanSerializerFactory;
import org.quickbundle.project.serializer.RmObjectMapper;
import org.quickbundle.tools.context.RmBeanHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.util.RmSequenceMap;
import org.quickbundle.util.RmSequenceSet;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotReadablePropertyException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * 作用是实现自动处理VO操作中的一些事情
 */
public final class RmVoHelper implements ICoreConstants {
    private RmVoHelper() {
    }

    /**
     * 借助BeanWrapper循环Vo
     * 
     * @param obj 输入一个VO
     * @return 被替换的值个数
     */
    public static int accessVo(Object obj, ITransctVoField transctVoField) {
        int returnCount = 0;
        try {
            BeanWrapper bw = new BeanWrapperImpl(obj);
            PropertyDescriptor pd[] = bw.getPropertyDescriptors();
            for (int i = 0; i < pd.length; i++) {
                try {
                    returnCount += transctVoField.transctVo(bw, pd[i]);
                } catch (ClassCastException e) {
                    //e.printStackTrace();
                    continue;
                } catch (NotReadablePropertyException e) {
                    //e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
        	RmLogHelper.getLogger(RmVoHelper.class).error("accessVo " + (obj != null ? obj.getClass() : "") + " " + e.toString());
        }
        return returnCount;
    }

    /**
     * 把VO中值为null的数据一律替换成""
     * 
     * @param obj 输入一个VO
     * @return 被替换的值个数
     */
    static int null2Nothing(Object obj) {
        return accessVo(obj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
                    if (bw.getPropertyValue(pd.getName()) == null) {
                        if(bw.getPropertyType(pd.getName()).getName().equals("java.sql.Timestamp")) {
                            bw.setPropertyValue(pd.getName(), null); 
                        } else {
                            bw.setPropertyValue(pd.getName(), ""); 
                        }
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        });
    }
    
    /**
     * 把VO中的关键字值一律替换成ASCII码表示，同时把null换为""
     * 
     * @param obj 输入一个VO
     * @return 操作次数
     */
    public static int replaceToHtml(Object obj) {
        return replaceToHtml(obj, null);
    }

    /**
     * 把VO中的关键字值一律替换成ASCII码表示，同时把null换为""
     * 
     * @param obj 输入一个VO
     * @param ignoreName 
     * @return 操作次数
     */
    public static int replaceToHtml(Object obj, final String[] ignoreName) {
        return accessVo(obj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
					if (ignoreName != null && ignoreName.length > 0 && RmStringHelper.arrayContainString(ignoreName, pd.getName())) {
                        return 0;
                    }
                    String tempValue = (String) bw.getPropertyValue(pd.getName());
                    if (tempValue == null && "java.lang.String".equals(pd.getPropertyType().getName())) {
                        bw.setPropertyValue(pd.getName(), "");
                        return 1;
                    } else if("java.lang.String".equals(pd.getPropertyType().getName())){
                        bw.setPropertyValue(pd.getName(), RmStringHelper.replaceStringToHtml(tempValue));
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        });

    }

    /**
     * 把VO中的关键字值按指定规则替换成ASCII码表示，同时把null换为""
     * 
     * @param obj 输入一个VO
     * @return 操作次数
     */
    public static int replaceToScript(Object obj) {
        return accessVo(obj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
                    String tempValue = (String) bw.getPropertyValue(pd.getName());
                    if (tempValue == null && "java.lang.String".equals(pd.getPropertyType().getName())) {
                        bw.setPropertyValue(pd.getName(), "");
                        return 1;
                    } else if("java.lang.String".equals(pd.getPropertyType().getName())) {
                        bw.setPropertyValue(pd.getName(), RmStringHelper.replaceStringToScript(tempValue));
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        });
    }

    /**
     * 从vo中获取表单值
     * 
     * @param request
     * @return
     */
    public static Map getMapFromVo(Object obj) {
        Map<String, Object> rtMap = new TreeMap<String, Object>();
        if(obj == null) {
            return rtMap;
        }
        BeanWrapper bw = new BeanWrapperImpl(obj);
        PropertyDescriptor pd[] = bw.getPropertyDescriptors();

        for (int i = 0; i < pd.length; i++) {
            try {
                if (!pd[i].getName().equals("class")) {
                    String tempKey = pd[i].getName();
                    rtMap.put(tempKey, bw.getPropertyValue(pd[i].getName()));
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
                continue;
            } catch (NotReadablePropertyException e) {
                e.printStackTrace();
                continue;
            }
        }
        return rtMap;
    }
    
    /**
     * 从vo列表中获得表单值列表
     * @param lvo
     * @return
     */
    public static List<Map> getMapsFromVos(List lvo) {
    	if(lvo == null) {
    		return null;
    	}
    	List<Map> result = new ArrayList<Map>(lvo.size());
    	for(Object obj : lvo) {
    		result.add(getMapFromVo(obj));
    	}
    	return result;
    }
    
    /**
     * 功能: 从thisObj获得Map
     *
     * @param thisObj 可能是List, Set 等Collection，或者Object[]
     * @param keyValue 至少是长度为1的数组
     * @return 一个Map,key值是keyValue[0],value是多种形式
     */
    public static Map getMapFromContainer(Object thisObj, String[] keyValue) {
        Map<String, Object> rtMap = new RmSequenceMap<String, Object>();
        if(keyValue == null || keyValue.length == 0) {
            return rtMap;
        }
	    if(thisObj instanceof Collection) {
	        for(Iterator itThisObj = ((Collection)thisObj).iterator(); itThisObj.hasNext(); ) {
	            Object singleObj = itThisObj.next();
	            transctVo4GetMapFromContrainer(singleObj, rtMap, keyValue);

	        }
	    } else if(thisObj instanceof Object[]) {
	        for(int i=0; i<((Object[])thisObj).length; i++) {
	            Object singleObj = ((Object[])thisObj)[i];
	            transctVo4GetMapFromContrainer(singleObj, rtMap, keyValue);
	        }
	    } else {
	        throw new RmRuntimeException("getMapFromContainer 不支持的类型:" + (thisObj != null ? thisObj.getClass() : ""));
	    }
	    return rtMap;
    }
    
    private static void transctVo4GetMapFromContrainer(Object singleObj, Map<String, Object> rtMap, String[] keyValue) {
        String tempKey = String.valueOf(getVoFieldValue(singleObj, keyValue[0]));
        Object tempValue = null;
        if(keyValue.length == 1) {
            tempValue = tempKey;
        } else if(keyValue.length == 2) {
            tempValue = String.valueOf(getVoFieldValue(singleObj, keyValue[1]));
        } else {
            Map mvo = new HashMap();
            for(int i=1; i<keyValue.length; i++) {
            	mvo.put(keyValue[i], String.valueOf(getVoFieldValue(singleObj, keyValue[i])));
            }
            tempValue = mvo;
        }
        rtMap.put(tempKey, tempValue);
    }
    
    /**
     * 功能: 从Map的value中取出Set
     *
     * @param mValue
     * @return
     */
    public static Set<Object> getSetFromMapValue(Map mValue) {
        Set<Object> s = new RmSequenceSet<Object>();
        for(Iterator itMValue = mValue.keySet().iterator(); itMValue.hasNext(); ) {
            s.add(mValue.get(itMValue.next()));
        }
        return s;
    }
    
    /**
     * 从request中获取表单值
     * 
     * @param request
     * @return
     */
    public static Map<String, Object> getMapFromRequest(HttpServletRequest request) {
        return getMapFromRequest(request, null);
    }

    /**
     * 从request中获取表单值
     * 
     * @param request
     * @param aNeedName 关注的key值 
     * @return
     */
    public static Map<String, Object> getMapFromRequest(HttpServletRequest request, String[] aNeedName) {
        Map<String, Object> rtMap = new TreeMap<String, Object>();
        Iterator itParms = request.getParameterMap().keySet().iterator();
        while (itParms.hasNext()) {
            String tempKey = (String) itParms.next();
			if (aNeedName != null && !RmStringHelper.arrayContainString(aNeedName, tempKey)) {
                continue;
            }
            String[] tempArray = request.getParameterValues(tempKey);
            if (tempArray == null || tempArray.length == 0) {
                continue;
            }
            if (tempArray.length == 1) {
                rtMap.put(tempKey, request.getParameter(tempKey));
            } else {  //杜绝了相同value的提交值多次被回写
                Set<String> sUniqueValue = new HashSet<String>();
                for(int i=0; i<tempArray.length; i++) {
                    sUniqueValue.add(tempArray[i]);
                }
                rtMap.put(tempKey, sUniqueValue.toArray(new String[0]));
            }
        }
        return rtMap;
    }
    
    /**
     * 回写表单
     *
     * @param mRequest
     * @return
     */
    public static String writeBackMapToForm(Map<String, Object> mRequest) {
        return writeBackMapToForm(mRequest, new String[]{});
    }
    
    /**
     * 回写表单
     *
     * @param mRequest
     * @param ignoreName 定义哪些key值的input不回写
     * @return
     */
    public static String writeBackMapToForm(Map<String, Object> mRequest, String[] ignoreName) {
    	return writeBackMapToForm(mRequest, ignoreName, true);
    }

    /**
     * 回写表单
     *
     * @param mRequest
     * @param ignoreName 定义哪些key值的input不回写
     * @param fix_name 是否自动补齐带_name的参照值
     * @return
     */
    public static String writeBackMapToForm(Map<String, Object> mRequest, String[] ignoreName, boolean fix_name) {
    	String jsFunctionName = "writeBackMapToForm";
        StringBuffer rtValue = new StringBuffer();
        rtValue.append("  var mForm = new Object();\n");
        rtValue.append("  var indexArray = new Array();\n");
        rtValue.append("  function ");
        rtValue.append(jsFunctionName);
        rtValue.append("() {\n");
        for(String tempKey : mRequest.keySet()) {
            Object tempValue = mRequest.get(tempKey);
            if (tempKey.toUpperCase().startsWith("RM_")) {
                continue;                
            }
			if (ignoreName != null && ignoreName.length > 0 && RmStringHelper.arrayContainString(ignoreName, tempKey)) {
                continue;                
            }
			String jsValue = RmStringHelper.parseToJsValue(tempValue);
			if(jsValue == null) {
				continue;
			}
            rtValue.append("    indexArray[indexArray.length] = \"" + tempKey + "\";\n");
            rtValue.append("    mForm[\"" + tempKey + "\"] = ");
            rtValue.append(jsValue);
            rtValue.append(";\n");
        }
        rtValue.append("    for(var i=0; i<indexArray.length; i++) {\n");
        rtValue.append("	  writeBackValue(indexArray[i]);\n");
        rtValue.append("    }\n");
        rtValue.append("  }\n");
        rtValue.append(jsFunctionName + "();\n");
        if(fix_name) {
        	rtValue.append("try{writeBackMapToFormFix_name();}catch(e){}\n");
        }
        return rtValue.toString();
    }
    
    /**
     * 回写List对象到行编辑模式的表格
     * @param namespace
     * @param lvo
     * @return
     */
    public static String writeBackListToRowTable(String namespace, List lvo) {
    	return writeBackListToRowTable(namespace, lvo, null);
    }
    
    /**
     * 回写List对象到行编辑模式的表格
     * @param namespace 
     * @param lvo
     * @param ignoreName
     * @return
     */
    public static String writeBackListToRowTable(String namespace, List lvo, String[] ignoreName) {
    	return writeBackListToRowTable(namespace, lvo, null, null);
    }
    
    /**
     * 回写List对象到行编辑模式的表格
     * @param namespace
     * @param lvo
     * @param ignoreName
     * @param om
     * @return
     * @throws JsonProcessingException
     */
    public static String writeBackListToRowTable(String namespace, List lvo, final String[] ignoreName, ObjectMapper objectMapper) {
    	if(lvo == null || lvo.size() == 0) {
    		return "";
    	}
    	StringBuilder result = new StringBuilder();
    	result.append("jQuery(function(){\n");
    	result.append("writeBackListToRowTable(");
    	result.append("'");
    	result.append(namespace);
    	result.append("'");
    	result.append(", ");
    	try {
        	if(objectMapper == null) {
        		objectMapper = RmObjectMapper.getInstance();
        	}
        	
    		// 排除
        	if(ignoreName != null) {
        		SimpleFilterProvider fileter = new SimpleFilterProvider();
        		fileter.addFilter("executeFilter", SimpleBeanPropertyFilter.serializeAllExcept(ignoreName));
        		objectMapper.setFilters(fileter);
        	}

    		// 设置日期格式化
    		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            SegmentedStringWriter sw = new SegmentedStringWriter(objectMapper.getFactory()._getBufferRecycler());
    		JsonGenerator generator = objectMapper.getFactory().createGenerator(sw);
    		RmBeanSerializerFactory rmBeanFactory = RmBeanSerializerFactory.instance;
    		if(ignoreName != null) {
    			rmBeanFactory.setFilterId("executeFilter");
    		}
    		objectMapper.setSerializerFactory(rmBeanFactory);

    		objectMapper.writeValue(generator, lvo);

			result.append( sw.getAndClear());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	result.append(");");
    	result.append("\n});");
    	return result.toString();
    }
    
    /**
     * 对Object列表打修改的时间和IP戳
     * 
     * @param request 来自页面的请求
     * @param collection VO列表
     */
    public static int markCreateStamp(final HttpServletRequest request, Collection collection) {
    	int result = 0;
    	for(Object obj : collection) {
    		result += markCreateStamp(request, obj);
    	}
    	return result;
    }
    
    static void setDateField(BeanWrapper bw, PropertyDescriptor pd) {
    	String className = bw.getPropertyType(pd.getName()).getName();
        if(Timestamp.class.getName().equals(className)) {
            bw.setPropertyValue(pd.getName(), RmDateHelper.getSysTimestamp()); 
        } else if(java.sql.Date.class.getName().equals(className)) {
        	bw.setPropertyValue(pd.getName(), new java.sql.Date(System.currentTimeMillis())); 
        } else if(java.util.Date.class.getName().equals(className)) {
        	bw.setPropertyValue(pd.getName(), new java.util.Date(System.currentTimeMillis())); 
        } else {
            bw.setPropertyValue(pd.getName(), RmDateHelper.getSysDateTimeMillis()); 
        }
    }
    
    /**
     * 对Object打创建的时间和IP戳
     * 
     * @param request 来自页面的请求
     * @param myVo 输入一个VO
     */
    public static int markCreateStamp(final HttpServletRequest request, Object thisObj) {
        return accessVo(thisObj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
					if (RmStringHelper.arrayContainString(DESC_CREATE_DATE, pd.getName())) {
						setDateField(bw, pd);
                        return 1;
                    } else if (RmStringHelper.arrayContainString(DESC_CREATE_IP, pd.getName()) && request != null) {
                        String create_ip = getIp(request);
                        bw.setPropertyValue(pd.getName(), create_ip);
                        return 1;
                    } else if (pd.getName().equals(DESC_USABLE_STATUS)) {  //加上了打逻辑删除标记启用的戳，数据设为可用
                        bw.setPropertyValue(pd.getName(), RM_YES);
                        return 1;
                    } else if (RmStringHelper.arrayContainString(DESC_CREATE_USER_ID, pd.getName()) && request != null) {
                        String create_user_id = null;
                        try {
                            create_user_id = getRmUserId(request);
                        } catch (Exception e) {
                        	RmLogHelper.getLogger(RmVoHelper.class).warn("getRmUserId(request): " + e.getMessage());
                        }
                        bw.setPropertyValue(pd.getName(), create_user_id);
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        });
    }
    
    /**
     * 对Object打逻辑删除标记启用的戳，数据设为可用
     * 
     * @param request 来自页面的请求
     * @param myVo 输入一个VO
     */
    public static int markLogicDeleteEnableStamp(final HttpServletRequest request, Object thisObj) {
        return accessVo(thisObj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
                    if (pd.getName().equals(DESC_USABLE_STATUS)) {
                        bw.setPropertyValue(pd.getName(), RM_YES); 
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        });
    }
    
    /**
     * 对Object打逻辑删除标记启用的戳，数据设为不可用
     * 
     * @param request 来自页面的请求
     * @param myVo 输入一个VO
     */
    public static int markLogicDeleteDisableStamp(final HttpServletRequest request, Object thisObj) {
        return accessVo(thisObj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
                    if (pd.getName().equals(DESC_USABLE_STATUS)) {
                        bw.setPropertyValue(pd.getName(), RM_YES); 
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        });
    }
    
    /**
     * 对Object列表打修改的时间和IP戳
     * 
     * @param request 来自页面的请求
     * @param collection VO列表
     */
    public static int markModifyStamp(final HttpServletRequest request, Collection collection) {
    	int result = 0;
    	for(Object obj : collection) {
    		result += markModifyStamp(request, obj);
    	}
    	return result;
    }
    
    /**
     * 对Object打修改的时间和IP戳
     * 
     * @param request 来自页面的请求
     * @param myVo 输入一个VO
     */
    public static int markModifyStamp(final HttpServletRequest request, Object thisObj) {
        return accessVo(thisObj, new ITransctVoField() {
            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
                if (!pd.getName().equals("class")) {
					if (RmStringHelper.arrayContainString(DESC_MODIFY_DATE, pd.getName())) {
                        setDateField(bw, pd);
                        return 1;
                    } else if (pd.getName().equals(DESC_USABLE_STATUS)) {  //数据还活着，加上了打逻辑删除标记启用的戳，数据设为可用
                        bw.setPropertyValue(pd.getName(), RM_YES);
                        return 1;
                    } else if (pd.getName().equals(DESC_MODIFY_IP) && request != null) {
                        String modify_ip = getIp(request);
                        bw.setPropertyValue(pd.getName(), modify_ip);
                        return 1;
                    } else if (pd.getName().equals(DESC_MODIFY_USER_ID) && request != null) {
                        String create_user_id = null;
                        try {
                            create_user_id = getRmUserId(request);
                        } catch (Exception e) {
                        	RmLogHelper.getLogger(RmVoHelper.class).warn("getRmUserId(request): " + e.getMessage());
                        }
                        bw.setPropertyValue(pd.getName(), create_user_id);
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        });
    }
    
    static String getIp(HttpServletRequest request) {
    	Class clz = null;
    	Object ip = null;
    	try {
    		clz = Class.forName("org.quickbundle.project.RmProjectHelper");
    		ip = clz.getMethod("getIp", ServletRequest.class).invoke(clz, new Object[]{request});
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
    	return ip != null ? ip.toString() : null;
    }
    
    static String getRmUserId(HttpServletRequest request) {
    	Class clz = null;
    	Object ip = null;
    	try {
    		clz = Class.forName("org.quickbundle.project.RmProjectHelper");
    		ip = clz.getMethod("getRmUserId", ServletRequest.class).invoke(clz, new Object[]{request});
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
    	return ip != null ? ip.toString() : null;
    }

    /**
     * 功能: 把Set中的null和""去掉
     * 
     * @param iterator
     * @return
     */
    public static int removeNullFromSet(Set set) {
        int count = 0;
        if (set == null)
            return -1;
        try {
            if (set.contains("")) {
                set.remove("");
                count++;
            }
            if (set.contains(null)) {
                set.remove(null);
                count++;
            }
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
        return count;
    }
	
	/**
	 * 功能: 把vo中的值求出来
	 *
	 * @param vo
	 * @return
	 */
	public static String voToString(Object vo) {
		if (vo == null) {
			return "";
		}
		final StringBuffer sb = new StringBuffer();
		final Map<String, String> mFinalValue = new HashMap<String, String>();
		mFinalValue.put("tempIndex", "0");
		//sb.append(vo.getClass().getName() + ":" );
		accessVo(vo, new ITransctVoField() {
			public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
				if (pd.getName().equals("class")) {
					return 0;
				}
				int index = Integer.parseInt(mFinalValue.get("tempIndex"));
				mFinalValue.put("tempIndex", String.valueOf(++index));
				sb.append(pd.getName() + "=" + bw.getPropertyValue(pd.getName()) + "\n");
				return 1;
			}
		});
		return sb.toString();
	}

	
	/**
	 * 功能: 判断2个vo的值是否相等
	 *
	 * @param vo1
	 * @param vo2
	 * @return
	 */
	public static boolean voEquals(Object vo1, final Object vo2) {
	    if(vo1 == vo2) {
	        return true;
	    }
	    final Map<String, Object> mFinalValue = new HashMap<String, Object>();
	    mFinalValue.put("tempIndex", "0");
	    mFinalValue.put("tempEquals", "1");
	    if(vo1 != null && vo2 != null) {
	        if(!(vo2.getClass().equals(vo1.getClass()))) {
	            return false;
	        }
	        accessVo(vo1, new ITransctVoField() {
	            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
	                String currentKey = pd.getName();
	                if (!currentKey.equals("class")) {
	                    boolean bEquals = (String.valueOf(mFinalValue.get("tempEquals"))).equals("1") ? true : false ;
                        if(bEquals) {  //只有true才进行比较
                            BeanWrapper bw2 = new BeanWrapperImpl(vo2);
                            if(bw.getPropertyValue(currentKey) == null) {
                                if(bw2.getPropertyValue(currentKey) != null) {
                                    bEquals = false;
                                }
                            } else {
                                if(!bw.getPropertyValue(currentKey).equals(bw2.getPropertyValue(currentKey))) {
                                    bEquals = false; 
                                }
                            }
    	                    int index = Integer.parseInt(String.valueOf(mFinalValue.get("tempIndex")));
    	                    mFinalValue.put("tempIndex", String.valueOf(++ index) );
                            mFinalValue.put("tempEquals", bEquals ? "1" : "0" );
                        }
	                    return 1;
	                } else {
	                    return 0;
	                }
	            }
	        });
	    } else if(vo1 == null && vo2 == null) {
	        return true;
	    }
	    boolean bEquals = (String.valueOf(mFinalValue.get("tempEquals"))).equals("1") ? true : false ;
	    return bEquals;
	}
	
	/**
	 * 功能: 克隆自身
	 *
	 * @param vo1
	 * @return
	 */
	public static Object voClone(Object vo1) {
	    Object vo2 = null;
        try {
    	    vo2 = vo1.getClass().newInstance();
            final BeanWrapper bw2 = new BeanWrapperImpl(vo2);
        
	        accessVo(vo1, new ITransctVoField() {
	            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
	                String currentKey = pd.getName();
	                if (!currentKey.equals("class")) {
	                    bw2.setPropertyValue(currentKey, bw.getPropertyValue(currentKey));
	                    return 1;
	                } else {
	                    return 0;
	                }
	            }
	        });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo2;
	}
	
	public static int voHashCode(Object vo) {
	    final Object[] hashCode = new Object[]{0 + ""};
        try {
	        accessVo(vo, new ITransctVoField() {
	            public int transctVo(BeanWrapper bw, PropertyDescriptor pd) {
	                String currentKey = pd.getName();
	                if (!currentKey.equals("class")) {
	                    Object fieldValue = bw.getPropertyValue(currentKey);
	                    if(fieldValue != null) {
	                        int tempHashCode = Integer.parseInt(hashCode[0].toString());
	                        tempHashCode += 29 * tempHashCode + fieldValue.hashCode();
	                        hashCode[0] = tempHashCode + "";
	                    }
	                    return 1;
	                } else {
	                    return 0;
	                }
	            }
	        });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(hashCode[0].toString());
	}
	
	/**
	 * 功能: 把object中的值求出来
	 *
	 * @param vo
	 * @return
	 */
	public static String objectToString(Object thisObj) {
	    StringBuilder returnStr = new StringBuilder();
	    if(thisObj == null) {
	        return String.valueOf(thisObj);
	    }
	    returnStr.append(thisObj.getClass().getName());
	    returnStr.append(": ");
	    if(thisObj instanceof List) {
	    	returnStr.append("\nsize()=");
	    	returnStr.append(((List)thisObj).size());
	        int index = 0;
	        for(Iterator itThisObj = ((List)thisObj).iterator(); itThisObj.hasNext(); ) {
	            Object singleObj = itThisObj.next();
	            returnStr.append("\n");
	            returnStr.append(++index);
	            returnStr.append(": ");
	            returnStr.append(singleObj);
	        }
	    } else if(thisObj instanceof Object[]) {
	    	returnStr.append("\nsize()=");
	    	returnStr.append(((Object[])thisObj).length);
	        for(int i=0; i<((Object[])thisObj).length; i++) {
	            Object singleObj = ((Object[])thisObj)[i];
	            returnStr.append("\n");
	            returnStr.append(i);
	            returnStr.append(": ");
	            returnStr.append(singleObj);
	        }
	    } else {
	    	returnStr.append(thisObj);
	    }
	    return returnStr.toString();
	}
	
	/**
	 * 功能: 把object中的值求出来
	 *
	 * @param vo
	 * @return
	 */
	public static String objectToString(int sum) {
	    return "result is : " + sum;
	}
	
	/**
	 * 功能: 把object中的值求出来
	 *
	 * @param vo
	 * @return
	 */
	public static String objectToString(long sum) {
	    return "result is : " + sum;
	}
	
	/**
	 * 功能: 得到vo中的某个field的值
	 *
	 * @param vo
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getVoFieldValue(Object vo, String field) {
	    Object rtObj = null;
	    Class fromcls = vo.getClass();
	    String getMethodName = "get" + RmStringHelper.toFirstUpperCase(field);
		try {
			Method getMethod = fromcls.getDeclaredMethod(getMethodName,new Class[]{});
	        if(getMethod != null) {
	            rtObj = getMethod.invoke(vo ,new Object[] {});
	        }
		} catch (NoSuchMethodException e) {
			throw new RmRuntimeException("", e);
		} catch (IllegalAccessException e) {
			throw new RmRuntimeException("", e);
		} catch (InvocationTargetException e) {
			throw new RmRuntimeException("", e);
		}
        return rtObj;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean voContainMethod(Object vo, String method) {
        boolean isContain = false;
        Class fromcls = vo.getClass();
        try {
            Method getMethod = fromcls.getDeclaredMethod(method,new Class[]{});
            if(getMethod != null) {
                isContain = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isContain;
	}
	
	/**
	 * 功能: 设置vo中的某个field的值
	 *
	 * @param vo
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean setVoFieldValue(Object vo, String field, Object value) {
		if(value == null) {
			setVoFieldValue(vo, field, value, String.class);
		}
		return setVoFieldValue(vo, field, value, value.getClass());
	}
	
	/**
	 * 功能: 设置vo中的某个field的值
	 *
	 * @param vo
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean setVoFieldValue(Object vo, String field, Object value, Class valueClazz) {
	    boolean result = false;
	    Class fromcls = vo.getClass();
	    String setMethodName = "set" + RmStringHelper.toFirstUpperCase(field);
	    try {
	        Method setMethod = null;
        	setMethod = fromcls.getDeclaredMethod(setMethodName,new Class[]{valueClazz});
	        setMethod.invoke(vo ,new Object[] {value});
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
	    result = true;
        return result;
	}
	
	/**
	 * 功能: 看数组有几个null
	 *
	 * @param aObj
	 * @return
	 */
	public static int arrayHasNull(Object[] aObj) {
	   if(aObj == null) {
	       return -1;
	   } else {
	       int sum = 0;
	       for(int i=0; i<aObj.length; i++) {
	           if(aObj[i] == null) {
	               sum ++;
	           }
	       }
	       return sum;
	   }
	}
	
	/**
	 * 功能: 返回数组第一个为空的index
	 *
	 * @param aObj
	 * @return
	 */
	public static int firstArrayNull(Object[] aObj) {
		   if(aObj == null) {
		       return -1;
		   } else {
		       for(int i=0; i<aObj.length; i++) {
		           if(aObj[i] == null) {
		               return i;
		           }
		       }
		       return -1;
		   }
		}
	
    public static boolean isAllValid(Set s, String[] array) {
        boolean valid = true;
        for(int i=0; i<array.length; i++) {
            if(!s.contains(array[i])) {
                valid = false;
            }
        }
        return valid;
    }
    
    public static boolean tableHaveColumnData(String tableName, String columnName, String value) {
        boolean valid = false;
        try {
            int count = RmBeanHelper.getCommonServiceInstance().doQueryForInt("select count(*) count from " + tableName + " where " + columnName + "='" + value + "'");
            if(count > 0) {
                valid = true;
            }
        } catch(Exception e) {
            System.out.println("tableHaveColumnData:" + e.getMessage());
        }

        return valid;
    }
    
    
	/**
	 * 比较老数据集与新数据集，得出insert/delete/update的最优序列
	 * @param headVo 父(表头)Vo
	 * @param headPkColumn 父(表头)Vo的PK列
	 * @param oldVos 子(表体)Vo的老数据集
	 * @param newVos 子(表体)Vo的新数据集
	 * @param bodyPkColumn 子(表体)Vo的PK列
	 * @param bodyFkColumn 子(表体)Vo的FK列，和父(表头)Vo的PK列关联
	 * @return 将被新增、将被删除、将被更新的List数组，结构是 List[]{toInsert<放bodyVo对象>, toDelete<放bodyVo的PK>, toUpdate<放bodyVo对象>}
	 */
	public static<H,B> List<?>[] mergeVos(H headVo, String headPkColumn, List<B> oldVos, List<B> newVos, String bodyPkColumn, String bodyFkColumn) {
    	List<B> toInsert = new ArrayList<B>();
    	List<Object> toDelete = new ArrayList<Object>();
    	List<B> toUpdate = new ArrayList<B>();
		List<?>[] result = new List[]{toInsert, toDelete, toUpdate};
    	Map<Object, B> oldVoMap = new HashMap<Object, B>();
    	for(B oldVo : oldVos) {
    		oldVoMap.put(getVoFieldValue(oldVo, bodyPkColumn), oldVo);
    	}
    	for(B newVo : newVos) {
    		Object newVoPk = getVoFieldValue(newVo, bodyPkColumn);
    		if(newVoPk != null && oldVoMap.containsKey(newVoPk)){
    			toUpdate.add(newVo);
    			oldVoMap.remove(newVoPk);
    		} else {
    			Object headVoPk = getVoFieldValue(headVo, headPkColumn);
    			setVoFieldValue(newVo, bodyFkColumn, headVoPk);
    			toInsert.add(newVo);
    		}
    	}
    	for(Map.Entry<Object, B> en : oldVoMap.entrySet()) {
    		toDelete.add(en.getKey());
    	}
    	return result;
    }
}