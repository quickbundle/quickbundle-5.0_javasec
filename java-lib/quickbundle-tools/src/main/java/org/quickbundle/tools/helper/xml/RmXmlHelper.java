package org.quickbundle.tools.helper.xml;
/*
 * 系统名称:Quickbundle.org --> ranminXmlGenerateCode
 * 
 * 文件名称: ranminXmlGenerateCode.test --> TestScroll.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2005-12-4 2:46:28 创建1.0.0版 (baixiaoyong)
 *  
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.io.RmFileHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmXmlHelper {

    /**
     * 功能: 从Document对象中获取String
     * 
     * @param document
     * @return
     */
    public static String getStringFromDocument(Document document) {
        String returnStr = "";
        ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
        BufferedOutputStream outer = new BufferedOutputStream(bytesStream);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tFactory.newTransformer();
            transformer.setOutputProperty("encoding", RmBaseConfig.getSingleton().getDefaultEncode());
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DocumentSource(document), new StreamResult(outer));
            returnStr = bytesStream.toString(RmBaseConfig.getSingleton().getDefaultEncode());
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return returnStr;
    }

    /**
     * 转化xml字符串为Document对象
     * 
     * @param xmlStr
     * @return
     */
    public static Document getDocumentFromString(String xmlStr) {
        try {
			return new SAXReader().read(new StringReader(xmlStr));
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
     * 获得一个Rss2.0格式的Document对象 --rss
     * @param channelTitle
     * @param channelLink
     * @param channelDescription
     * @return
     */
    public static Document getRss20Document(String channelTitle, String channelLink, String channelDescription) {
    	Document doc = DocumentHelper.createDocument();
    	Element rootEle = doc.addElement("rss").addAttribute("version", "2.0");
    	Element channelEle = rootEle.addElement("channel");
    	channelEle.addElement("title").setText(channelTitle);
    	channelEle.addElement("link").setText(channelLink);
    	channelEle.addElement("description").setText(channelDescription);
    	return doc;
    }
    
    /**
     * 获得一个Rss2.0格式的Document对象--item
     * @param itemTitle
     * @param itemLink
     * @param itemDescription
     * @param pubDate
     * @return
     */
    public static Document getRss20Item(String itemTitle, String itemLink, String itemDescription, String pubDate) {
    	Document doc = DocumentHelper.createDocument();
    	Element itemEle = doc.addElement("item");
    	itemEle.addElement("title").setText(itemTitle);
    	itemEle.addElement("link").setText(itemLink);
    	itemEle.addElement("description").setText(itemDescription);
    	itemEle.addElement("pubDate").setText(pubDate);
    	return doc;
    }

    /**
     * 功能: 从xmlPath的资源转化成Document对象
     * 
     * @param ruleXml
     * @return
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public static Document parse(String xmlPath) throws MalformedURLException, DocumentException {
        if (xmlPath == null || xmlPath.length() == 0) {
            throw new NullPointerException("xml路径是空!");
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(new URL(formatToUrl(xmlPath)));
        return document;
    }
    
    /**
     * 功能: 从xmlPath的资源转化成Document对象，带命名空间
     * @param ruleXml
     * @param mNamespaceURIs
     * @return
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public static Document parse(String ruleXml, Map mNamespaceURIs) throws MalformedURLException, DocumentException {
        if (ruleXml == null || ruleXml.length() == 0) {
            throw new NullPointerException("xml路径是空!");
        }
        SAXReader reader = new SAXReader();
        reader.getDocumentFactory().setXPathNamespaceURIs(mNamespaceURIs);
        Document document = null;
        if (ruleXml.startsWith("file:")) {
            document = reader.read(new URL(ruleXml));
        } else {
            document = reader.read(new File(ruleXml));
        }

        return document;
    }

    /**
     * 功能: 把xml保存到指定的路径文件名
     * 
     * @param document
     * @param targetFile
     * @throws IOException
     */
    public static boolean saveXmlToPath(Document document, String targetFile) {
        try {
            targetFile = formatToFile(targetFile);
            RmFileHelper.initParentDir(targetFile);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(RmBaseConfig.getSingleton().getDefaultEncode());
            XMLWriter writer = new XMLWriter(new FileOutputStream(targetFile), format);
            writer.write(document);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 功能: 将路径格式化为url --> file:///c:/rmdemo.log
     * 
     * @param filePath
     * @return
     */
    public static String formatToUrl(String filePath) {
    	return RmFileHelper.formatToUrl(filePath);
    }
    
    /**
     * 功能: 将路径格式化为url --> c:/rmdemo.log
     * 
     * @param filePath
     * @return
     */
    public static String formatToUrlNoPrefix(String filePath) {
    	return RmFileHelper.formatToUrlNoPrefix(filePath);
    }
    
    /**
     * 功能: 将路径格式化为File形式 --> c:\rmdemo.log
     * 
     * @param filePath
     * @param osSeparatorStr 指定当前操作系统分隔符
     * @return
     */
    public static String formatToFile(String filePath, String osSeparatorStr) {
    	return RmFileHelper.formatToFile(filePath, osSeparatorStr);
    }
    
    /**
     * 功能: 将路径格式化为File形式 --> c:\rmdemo.log
     *
     * @param filePString
     * @return
     */
    public static String formatToFile(String filePString) {
        return formatToFile(filePString, File.separator);
    }
	
	/**
	 * 复制from下的所有节点(包括Attribute, Element, Text)到to
	 * 
	 * @param from
	 * @param to
	 */
	public static void deepCopyElement(Element from, Element to) {
		if(from == null || to == null) {
			return;
		}
		List<Node> lNode = from.selectNodes("@*|node()");
		for(Node node : lNode) {
			if(node instanceof Attribute) {
				Attribute attr = (Attribute)node;
				to.addAttribute(attr.getName(), attr.getText());
			} else if(node instanceof Element) {
				Element ele = (Element)node;
				to.add(ele.createCopy());
			} else if(node instanceof Text) {
				to.setText(node.getText());
			}
		}
	}
	
	/**
	 * 先清理to的所有node()和Attribute，在复制from下的所有节点(包括Attribute, Element, Text)到to
	 * 
	 * @param from
	 * @param to
	 */
	public static void deepCopyElementWithClear(Element from, Element to) {
		if(from == null || to == null) {
			return;
		}
		List<Node> lNode = to.selectNodes("@*|node()");
		for(Node node : lNode) {
			to.remove(node);
		}
		deepCopyElement(from, to);
	} 
}