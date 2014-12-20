package org.quickbundle.mda;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.value.Whitespace;

import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class RmTransform {
	/**
	 * 功能: 从转化中获取String
	 * 
	 * @param document
	 * @return
	 */
	public static String getStringFromTransform(String xsltPath, String myTableXml) {
		return getStringFromTransform(xsltPath, myTableXml, null);
	}
	/**
	 * 功能: 从转化中获取String
	 * 
	 * @param document
	 * @return
	 */
	public static String getStringFromTransform(String xsltPath, String myTableXml, Map<String, Object> mAttribute) {
		String result = "";
		xsltPath = RmXmlHelper.formatToUrl(xsltPath);
		myTableXml = RmXmlHelper.formatToUrl(myTableXml);
		ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
		BufferedOutputStream outer = new BufferedOutputStream(bytesStream);
		Transformer transformer = null;
		try {
			Configuration config = new Configuration();
			config.setStripsAllWhiteSpace(true);
			config.setStripsWhiteSpace(Whitespace.ALL);
			TransformerFactory factory = new net.sf.saxon.TransformerFactoryImpl(config);
			StreamSource ss = new StreamSource(xsltPath);
			Templates pss = factory.newTemplates(ss);
			transformer = pss.newTransformer();
			if(mAttribute != null) {
				for(String key : mAttribute.keySet()) {
					transformer.setParameter(key, mAttribute.get(key));
				}
			}
			transformer.transform(new StreamSource(myTableXml), new StreamResult(outer));
			result = bytesStream.toString(RmBaseConfig.getSingleton().getDefaultEncode());
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e); 
		} finally {
			try {
				if (outer != null) {
					outer.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
}
