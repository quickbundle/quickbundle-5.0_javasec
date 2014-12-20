package org.quickbundle.tools.support.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.support.path.RmPathHelper;

public class RmFont{
	public enum FontName {
		defaultFont("微软雅黑", "msyh.ttf"),
		simhei("黑体", "simhei.ttf");
    	private String cnName;
    	private String fontFile;
    	FontName(String cnName_, String fontFile_) {
    		cnName = cnName_;
    		fontFile = fontFile_;
    	}
		public String getCnName() {
			return cnName;
		}
	}
	
	private static String fontpath = RmPathHelper.getWebInfDir() + "/resource/font/";
	public static Font getFontByFile(String name, int style, int size) {
		Font result = null;
		FileInputStream fi = null;
		try {
			File file = new File(fontpath + FontName.valueOf(name).fontFile);
			fi = new FileInputStream(file);
			BufferedInputStream fb = new BufferedInputStream(fi);
			result = Font.createFont(Font.TRUETYPE_FONT, fb);
			result = result.deriveFont(style, size);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (FontFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (fi != null) {
					fi.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 获得默认的字体名称
	 * @return 
	 */
	public static String getDefaultFontName() {
		List<String> lFont = new ArrayList<String>();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames = ge.getAvailableFontFamilyNames();
		for (String fontName : fontNames) {
			lFont.add(fontName);
		}
		if(RmBaseConfig.getSingleton().getDefaultFont() != null && RmBaseConfig.getSingleton().getDefaultFont().trim().length() > 0) {
			String[] aDefaultFont = RmBaseConfig.getSingleton().getDefaultFont().trim().split(",");
			for(String defaultFont : aDefaultFont) {
				if(lFont.contains(defaultFont)) {
					return defaultFont;
				}
			}
		}
		if(lFont.size() > 0) {
			return lFont.get(lFont.size()-1);
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getFontByFile(FontName.simhei.name(), Font.PLAIN, 20));
	}
}
