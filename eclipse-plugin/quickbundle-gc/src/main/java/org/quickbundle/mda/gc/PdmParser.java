package org.quickbundle.mda.gc;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class PdmParser {
	
	private Document pdm = null;
	
    private Map mPdmColumn = null;
	 
	public PdmParser(String pdmPath) throws MalformedURLException, DocumentException {
		pdm = RmXmlHelper.parse(RmXmlHelper.formatToUrl(pdmPath));
		mPdmColumn = getCodeNameByPdm();
	}
	
    /**
     * 功能: Map中存放String[], a[0]=表的Name，a[1]=表的Comment，a[2]=表的多个BusinessRule
     *
     * @param pdmPath
     * @return
     */
    private Map getCodeNameByPdm() {
        Map mColumn = null;
        try {
            Map mBusinessRule = new HashMap();
            for (Iterator iter = pdm.selectNodes("//c:BusinessRules/o:BusinessRule").iterator(); iter.hasNext();) {
                Element eleBusinessRule = (Element) iter.next();
                mBusinessRule.put(eleBusinessRule.valueOf("@Id"), eleBusinessRule.valueOf("a:Code"));
            }
            
            mColumn = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
            for (Iterator iter = pdm.selectNodes("//o:Table[a:Code]").iterator(); iter.hasNext();) {
                Element ele = (Element) iter.next();
                String tableName = ele.valueOf("a:Code");
                mColumn.put(tableName, getPdmInfo(ele, mBusinessRule));
                
                for (Iterator iterator = ele.selectNodes(".//o:Column").iterator(); iterator.hasNext();) {
                    Element eleColumn = (Element) iterator.next();
                    //System.out.println(tableName + ":" + eleColumn.valueOf("a:Code") + ":" + eleColumn.valueOf("a:Name"));
                    mColumn.put(tableName + ":" + eleColumn.valueOf("a:Code"), getPdmInfo(eleColumn, mBusinessRule));
                }
            }
        } catch (Exception e) {
            QbXmlGenerateCodePlugin.log(e.getMessage());
        }
        return mColumn;
    }
    
    private String[] getPdmInfo(Element ele, Map mBusinessRule) {
        String columnBusinessRule = "";
        for (Iterator itColumnAttackRule = ele.selectNodes("c:AttachedRules/o:BusinessRule").iterator(); itColumnAttackRule.hasNext();) {
            Element eleBusinessRule = (Element)itColumnAttackRule.next();
            if(columnBusinessRule.length() > 0) {
                columnBusinessRule += ",";
            }
            columnBusinessRule += mBusinessRule.get(eleBusinessRule.valueOf("@Ref")).toString();
        }
        String[] aTableInfo = new String[]{ele.valueOf("a:Name"), ele.valueOf("a:Comment"), columnBusinessRule};
        return aTableInfo;
    }
    
	public Document getPdm() {
		return pdm;
	}

	public Map getMPdmColumn() {
		return mPdmColumn;
	}
	
}
