/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.tree --> RmXmlHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-11-19 19:16:49 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.tools.support.tree;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DocumentSource;
import org.quickbundle.config.RmBaseConfig;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class DeepTreeXmlHandler {

    /**
     * document 表示: xml的母体
     */
    private Document document;
    
    /**
     * 功能: 获得xml的String表示
     *
     * @return
     */
    public String getStringFromDocument() {
        selfCheckHasChild4HardTree();
        ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
        BufferedOutputStream outer = new BufferedOutputStream(bytesStream);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, RmBaseConfig.getSingleton().getDefaultEncode());
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.transform(new DocumentSource(this.document), new StreamResult(outer));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String returnValue = "";
		try {
			returnValue = bytesStream.toString(RmBaseConfig.getSingleton().getDefaultEncode());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        return returnValue;
    }

    /**
     * 构造函数: 初始化document,并加入根元素"Trees"
     *  
     */
    public DeepTreeXmlHandler() {
        document = DocumentHelper.createDocument();
        document.addElement("Trees");
    }

    /**
     * 功能: 加DeepTreeVo节点到根元素
     *
     * @param dtv 要加入的树节点
     * @return
     */
    public Element addTreeNode(DeepTreeVo dtv) {
        Element root = document.getRootElement();
        Element treeNodeVo = root.addElement("TreeNode");
        for(Iterator itMapAttribute = dtv.getAttributeMapIterator(); itMapAttribute.hasNext(); ) {
            String tempKey = itMapAttribute.next().toString();
            String tempValue = dtv.getAttribute(tempKey);
            treeNodeVo.addAttribute(tempKey, tempValue);
        }
        return treeNodeVo;
    }
    
    /**
     * 功能: 加DeepTreeVo节点到id为id值的元素,如果不存在返回空
     *
     * @param id 父节点id
     * @param dtv 要加入的树节点
     * @return 成功则返回这个树节点的Element，失败则返回null
     */
    public Element addTreeNode(String parentId, DeepTreeVo dtv) {
        if(parentId == null) {
            return null;
        } else {
            Element thisParentTreeNode = (Element) document.selectSingleNode("//TreeNode[@id='" + parentId + "']");
            if(thisParentTreeNode == null) {
                return null;
            }
            Element thisTreeNode = thisParentTreeNode.addElement("TreeNode");
            for(Iterator itMapAttribute = dtv.getAttributeMapIterator(); itMapAttribute.hasNext(); ) {
                String tempKey = itMapAttribute.next().toString();
                String tempValue = dtv.getAttribute(tempKey);
                thisTreeNode.addAttribute(tempKey, tempValue);
            }
            return thisTreeNode;
        }
    }
    
    /**
     * 功能: 加DeepTreeVo节点到id为id值的元素,如果parentId为null或parentId为""则加到id为defaultRootId的元素
     *
     * @param id 父节点id
     * @param dtv 要加入的树节点
     * @param defaultRootId
     * @return 成功则返回这个树节点的Element，失败则返回null
     */
    public Element addTreeNode(String parentId, DeepTreeVo dtv, String defaultRootId) {
        Element rtElement = null;
        if(parentId != null && parentId.length() > 0) {
            rtElement = addTreeNode(parentId, dtv);
        } 
        if(rtElement == null) {
            rtElement = addTreeNode(defaultRootId, dtv);
        }
        return rtElement;
    }
    
    /**
     * 功能: 自我修复不良数据，把没有子节点，并且xmlSource等于tempTreeNode""，并且hasChild等于"1"的节点的hasChild="0"
     *
     * @return 返回修复的节点数
     */
    public int selfCheckHasChild4HardTree() {
        List lTreeNode = this.document.selectNodes("//TreeNode[not(./TreeNode) and @xmlSource='' and @hasChild='1']");
        for(Iterator itLTreeNode = lTreeNode.iterator(); itLTreeNode.hasNext(); ) {
            Node tempTreeNode = (Node) itLTreeNode.next();
            Node hasChild = tempTreeNode.selectSingleNode("@hasChild");
            hasChild.setText("0");
        }
        return lTreeNode.size();
    }

    public static void main(String[] args) {
        DeepTreeXmlHandler dt = new DeepTreeXmlHandler();
        dt.addTreeNode(new DeepTreeVo("1", "销售部", "1", "xmlData.xml"));
        dt.addTreeNode(new DeepTreeVo("2", "开发中心", "1", "xmlData.xml"));
        System.out.println(dt.getStringFromDocument());
    }
}