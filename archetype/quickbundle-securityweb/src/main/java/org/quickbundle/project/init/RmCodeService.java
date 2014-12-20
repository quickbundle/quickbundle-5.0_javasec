package org.quickbundle.project.init;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.base.exception.RmRuntimeException;
import org.quickbundle.config.RmConfig;
import org.quickbundle.itf.code.IRmCodeService;
import org.quickbundle.modules.code.rmcodedata.service.IRmCodeDataService;
import org.quickbundle.modules.code.rmcodedata.util.IRmCodeDataConstants;
import org.quickbundle.modules.code.rmcodedata.vo.RmCodeDataVo;
import org.quickbundle.modules.code.rmcodetype.service.IRmCodeTypeService;
import org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants;
import org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.tools.support.path.RmPathHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RmCodeService implements IRmCodeService {
	
	/**
	 * 初始化数据库的内置表
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public void executeInitTable() {
		File sqlFolder = new File(RmPathHelper.getWebInfDir().toString() + File.separator + "config" + File.separator + "sql" + File.separator + RmConfig.getSingleton().getDatabaseProductName().toLowerCase());
		File[] aSqlFile = sqlFolder.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
		        return Pattern.compile("^.+\\.sql$", Pattern.CASE_INSENSITIVE).matcher(name).find();
			}
		});
		for (int i = 0; i < aSqlFile.length; i++) {
			String[] aSql = RmSqlHelper.loadSql(aSqlFile[i].toString());
			IRmCommonService commonService = RmProjectHelper.getCommonServiceInstance();
			for (int j = 0; j < aSql.length; j++) {
				try {
					commonService.doUpdate(aSql[j]);
				} catch (Exception e) {
					RmLogHelper.getLogger(this.getClass()).error(e.toString());
					//e.printStackTrace();
				}
			}
		}
	}
	
    /**
     * 功能：从initCodeTypeData.xml中初始化编码数据
     * @throws DocumentException 
     * @throws MalformedURLException 
     */
    public void executeInitCodeTypeDataByXml() {
        IRmCodeTypeService codeTypeService = (IRmCodeTypeService) RmBeanFactory.getBean(IRmCodeTypeConstants.SERVICE_KEY);
        IRmCodeDataService codeDataService = (IRmCodeDataService) RmBeanFactory.getBean(IRmCodeDataConstants.SERVICE_KEY);
        
        Set sCodeType = new HashSet();
        { //首先初始化RmDictionaryType
            List lCodeType = codeTypeService.queryByCondition(null, null);
            for (Iterator itLCodeType = lCodeType.iterator(); itLCodeType.hasNext();) {
                RmCodeTypeVo codeTypeVo = (RmCodeTypeVo) itLCodeType.next();
                sCodeType.add(codeTypeVo.getType_keyword());
            }
        }
        Document doc = null;
		try {
			doc = RmXmlHelper.parse(RmPathHelper.getWebInfDir() + "/config/rm/initCodeTypeData.xml");
		} catch (MalformedURLException e1) {
			throw new RmRuntimeException("initCodeTypeData.xml配置错误", e1);
		} catch (DocumentException e1) {
			throw new RmRuntimeException("initCodeTypeData.xml配置错误", e1);
		}
		List<RmCodeTypeVo> lCodeTypeVo = new ArrayList<RmCodeTypeVo>();
		List<RmCodeDataVo> lCodeDataVo = new ArrayList<RmCodeDataVo>();
        for (Iterator itCode = doc.selectNodes("/codes/code").iterator(); itCode.hasNext();) {
            Element eleCode = (Element) itCode.next();
            String code_type = eleCode.valueOf("@code_type");
            String code_type_name = eleCode.valueOf("@code_type_name");
            if(code_type_name == null || code_type_name.trim().length() == 0) {
            	code_type_name = code_type;
            }
            String code_data = eleCode.getText();
            //只初始化系统中没有的Code
            if(!sCodeType.contains(code_type)) {
                //初始化编码类型
                RmCodeTypeVo codeTypeVo = new RmCodeTypeVo();
                codeTypeVo.setName(code_type_name);
                codeTypeVo.setType_keyword(code_type);
                RmVoHelper.markCreateStamp(null, codeTypeVo);
                String code_type_id = null;
                sCodeType.add(code_type);
                try {
                	//code_type_id = codeTypeService.insert(codeTypeVo);
                	code_type_id = RmIdFactory.requestId(IRmCodeTypeConstants.TABLE_NAME);
                	codeTypeVo.setId(code_type_id);
				} catch (DataIntegrityViolationException e) {
					throw new RmRuntimeException("初始化" + code_type + "出错", e);
				}
				//放入List
            	lCodeTypeVo.add(codeTypeVo);
                
                //初始化编码数据
                String[] aCode_data = code_data.split(",");
                int topKeyInt = new BigInteger("10").pow(String.valueOf(aCode_data.length).length()).intValue();
                for (int i = 0; i < aCode_data.length; i++) {
                    String tempData = aCode_data[i].trim();
                    if(tempData.indexOf("=") > 0 && tempData.indexOf("=") != tempData.length()-1) {
                        String tempKey = tempData.substring(0, tempData.indexOf("="));
                        String tempValue = tempData.substring(tempData.indexOf("=") + 1, tempData.length());
                        String total_code = code_type + "-";
                        if("order".equals(eleCode.valueOf("@order_key"))) {
                        	total_code = total_code + (topKeyInt + i + 1); 
                        } else {
                        	total_code = total_code + tempKey;
                        }
                        String regex = "^(.*?)(\\d+)\\.\\.(\\d+)(.*?)$";
                        if(tempKey.matches(regex)&& tempValue.matches(regex)) {
                        	List<String> lKey = parseCodeWithDot(tempKey);
                        	List<String> lValue = parseCodeWithDot(tempValue);
                        	int maxKeyLength = 0;
                        	if(lKey.size() > 0) {
                        		maxKeyLength = Math.max(lKey.get(0).length(), lKey.get(lKey.size()-1).length());
                        	}
                        	int index = 0;
                        	for(String key : lKey) {
                        		RmCodeDataVo codeDataVo = new RmCodeDataVo(); 
                        		codeDataVo.setCode_type_id(code_type_id);
                        		codeDataVo.setData_key(key);
                        		codeDataVo.setData_value(lValue.get(index));
                        		codeDataVo.setEnable_status(IGlobalConstants.RM_YES);
                        		String keyOrder = key;
                        		if(maxKeyLength > 0) {
                        			for(int j=0; j<maxKeyLength-key.length(); j++) {
                        				keyOrder = "0" + keyOrder;
                        			}
                        		}
                        		total_code = total_code + "-" + keyOrder;
                        		codeDataVo.setTotal_code(total_code);
                        		RmVoHelper.markCreateStamp(null, codeDataVo);
                            	lCodeDataVo.add(codeDataVo);
                            	index ++;
                        	}
                        } else {
                            RmCodeDataVo codeDataVo = new RmCodeDataVo();
                            codeDataVo.setCode_type_id(code_type_id);
                            codeDataVo.setData_key(tempKey);
                            codeDataVo.setData_value(tempValue);
                            codeDataVo.setEnable_status(IGlobalConstants.RM_YES);
                            codeDataVo.setTotal_code(total_code);
                            RmVoHelper.markCreateStamp(null, codeDataVo);
                            lCodeDataVo.add(codeDataVo);
                        }
                    }
                }
            }
        }
        if(lCodeTypeVo.size() > 0) {
        	codeTypeService.insert(lCodeTypeVo.toArray(new RmCodeTypeVo[0]));
        }
        if(lCodeDataVo.size() > 0) {
        	codeDataService.insert(lCodeDataVo.toArray(new RmCodeDataVo[0]));
        }
    }
    
    private List<String> parseCodeWithDot(String str) {
    	String regex = "^(.*?)(\\d+)\\.\\.(\\d+)(.*?)$";
    	List<String> lValue = new ArrayList<String>();
        Pattern p = Pattern.compile(regex);
        Matcher matchKey = p.matcher(str);
        if(matchKey.find()) {
        	MatchResult mr = matchKey.toMatchResult();
            String preStr = mr.group(1);
            String affStr = mr.group(4);
            int from = Integer.parseInt(mr.group(2));
            int to = Integer.parseInt(mr.group(3));
            int circleCount = 0;
            int index = from;
            do {
            	lValue.add(preStr + index + affStr);
            	if(from < to) {
            		index ++;
            	} else if(from > to) {
            		index --;
            	}
            	circleCount ++;
            	if(circleCount == RmConfig.getSingleton().getMaxCircleCount()) {
            		lValue.add(preStr + to + affStr);
            		break;
            	}
            } while(from < to ? index <= to : index >= to);
        }
    	return lValue;
    }
}
