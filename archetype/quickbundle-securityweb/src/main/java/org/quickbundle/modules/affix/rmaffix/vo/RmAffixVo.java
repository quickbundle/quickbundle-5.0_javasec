//代码生成时,文件路径: E:/platform/myProject/navinfo/code/nifl/src/main/java/org/quickbundle/modules/affix/rmaffix/vo/RmAffixVo.java
//代码生成时,系统时间: 2010-07-26 01:03:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> nifl
 * 
 * 文件名称: org.quickbundle.modules.affix.rmaffix.vo --> RmAffixVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-07-26 01:03:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.affix.rmaffix.vo;


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAffixVo extends RmValueObject{
	
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库中的注释: 
     */
    private String id;
	/**
     * bs_keyword 表示: 业务关键字
	 * 数据库中的注释: 
     */
    private String bs_keyword;
	/**
     * record_id 表示: 主记录ID
	 * 数据库中的注释: 
     */
    private String record_id;
	/**
     * order_number 表示: 顺序数
	 * 数据库中的注释: 
     */
    private BigDecimal order_number;
	/**
     * title 表示: 标题
	 * 数据库中的注释: 
     */
    private String title;
	/**
     * old_name 表示: 原文件名
	 * 数据库中的注释: 
     */
    private String old_name;
	/**
     * save_name 表示: 真实储存路径
	 * 数据库中的注释: 
     */
    private String save_name;
	/**
     * save_size 表示: 真实文件大小
	 * 数据库中的注释: 
     */
    private BigDecimal save_size;
	/**
     * mime_type 表示: 内容类型
	 * 数据库中的注释: $RM_MINE_TYPE=内容类型{ application/vnd.adobe.xdp+xml=Adobe Acrobat XML Data Package, application/vnd.adobe.air-application-installer-package+zip=Adobe AIR, application/framemaker=Adobe FrameMaker, application/pagemaker=Adobe PageMaker, application/pdf=Adobe PDF Document, application/photoshop=Adobe Photoshop, audio/x-aiff=AIFF Audio, application/acp=Alfresco Content Package, image/x-portable-anymap=Anymap Image, image/x-dwg=AutoCAD Drawing, image/x-dwt=AutoCAD Template, audio/basic=Basic Audio, image/bmp=Bitmap Image, image/cgm=CGM Image, message/rfc822=EMail, application/eps=EPS Type PostScript, video/x-flv=Flash Video, image/gif=GIF Image, image/x-portable-graymap=Greymap Image, application/x-gzip=GZIP, application/x-gtar=GZIP Tarball, text/html=HTML, application/vnd.oasis.opendocument.text-web=HTML Document Template, text/calendar=iCalendar File, image/ief=IEF Image, application/java=Java Class, application/x-javascript=Java Script, image/jp2=JPEG 2000 Image, image/jpeg=JPEG Image, application/json=JSON, application/x-latex=Latex, application/x-troff-man=Man Page, text/mediawiki=MediaWiki Markup, application/vnd.excel=Microsoft Excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet=Microsoft Excel 2007, application/vnd.powerpoint=Microsoft PowerPoint, application/vnd.openxmlformats-officedocument.presentationml.presentation=Microsoft PowerPoint 2007, application/vnd.ms-project=Microsoft Project, application/visio=Microsoft Visio, application/msword=Microsoft Word, application/vnd.openxmlformats-officedocument.wordprocessingml.document=Microsoft Word 2007, audio/x-mpeg=MPEG Audio, video/mpeg=MPEG Video, video/mpeg2=MPEG2 Video, video/mp4=MPEG4 Video, video/x-ms-wma=MS Streaming Audio, video/x-ms-asf=MS Streaming Video, video/x-ms-wmv=MS Streaming Video, video/x-msvideo=MS Video, application/octet-stream=Octet Stream, application/vnd.oasis.opendocument.chart=OpenDocument Chart, application/vnd.oasis.opendocument.database=OpenDocument Database, application/vnd.oasis.opendocument.graphics=OpenDocument Drawing, application/vnd.oasis.opendocument.graphics-template=OpenDocument Drawing Template, application/vnd.oasis.opendocument.formula=OpenDocument Formula, application/vnd.oasis.opendocument.image=OpenDocument Image, application/vnd.oasis.opendocument.text-master=OpenDocument Master Document, application/vnd.oasis.opendocument.presentation=OpenDocument Presentation, application/vnd.oasis.opendocument.presentation-template=OpenDocument Presentation Template, application/vnd.oasis.opendocument.spreadsheet=OpenDocument Spreadsheet, application/vnd.oasis.opendocument.spreadsheet-template=OpenDocument Spreadsheet Template, application/vnd.oasis.opendocument.text=OpenDocument Text (OpenOffice 2.0), application/vnd.oasis.opendocument.text-template=OpenDocument Text Template, application/vnd.sun.xml.calc=OpenOffice 1.0/StarOffice6.0 Calc 6.0, application/vnd.sun.xml.draw=OpenOffice 1.0/StarOffice6.0 Draw 6.0, application/vnd.sun.xml.impress=OpenOffice 1.0/StarOffice6.0 Impress 6.0, application/vnd.sun.xml.writer=OpenOffice 1.0/StarOffice6.0 Writer 6.0, application/vnd.ms-outlook=Outlook MSG, image/x-portable-pixmap=Pixmap Image, text/plain=Plain Text, image/png=PNG Image, image/x-portable-bitmap=Portable Bitmap, application/postscript=PostScript, application/remote-printing=Printer Text File, video/quicktime=Quicktime Video, video/x-rad-screenplay=RAD Screen Display, image/x-cmu-raster=Raster Image, image/x-rgb=RGB Image, text/richtext=Rich Text, application/rtf=Rich Text Format, image/svg=Scalable Vector Graphics Image, video/x-sgi-movie=SGI Video, text/sgml=SGML, application/sgml=SGML, application/x-sh=Shell Script, application/x-shockwave-flash=Shockwave Flash, application/vnd.stardivision.chart=StaChart 5.x, application/vnd.stardivision.calc=StarCalc 5.x, application/vnd.stardivision.draw=StarDraw 5.x, application/vnd.stardivision.impress=StarImpress 5.x, application/vnd.stardivision.impress-packed=StarImpress Packed 5.x, application/vnd.stardivision.math=StarMath 5.x, application/vnd.stardivision.writer=StarWriter 5.x, application/vnd.stardivision.writer-global=StarWriter 5.x global, text/css=Style Sheet, text/tab-separated-values=Tab Separated Values, application/x-tar=Tarball, application/x-tex=Tex, application/x-texinfo=Tex Info, image/tiff=TIFF Image, x-world/x-vrml=VRML, audio/x-wav=WAV Audio, application/wordperfect=WordPerfect, image/x-xbitmap=XBitmap Image, application/xhtml+xml=XHTML, text/xml=XML, image/x-xpixmap=XPixmap Image, image/x-xwindowdump=XWindow Dump, application/x-compress=Z Compress, application/zip=ZIP }
     */
    private String mime_type;
	/**
     * encoding 表示: 编码
	 * 数据库中的注释: 
     */
    private String encoding;
	/**
     * description 表示: 描述
	 * 数据库中的注释: 
     */
    private String description;
	/**
     * author 表示: 作者
	 * 数据库中的注释: 
     */
    private String author;
	/**
     * usable_status 表示: 数据可用状态
	 * 数据库中的注释: 
     */
    private String usable_status;
	/**
     * modify_date 表示: 修改日期
	 * 数据库中的注释: 
     */
    private Timestamp modify_date;
	/**
     * modify_ip 表示: 修改IP
	 * 数据库中的注释: 
     */
    private String modify_ip;
	/**
     * modify_user_id 表示: 修改用户ID
	 * 数据库中的注释: 
     */
    private String modify_user_id;        
    //结束rm_code_type的属性
        
        
    //开始rm_code_type的setter和getter方法
    
    /**
     * 获得主键
     * 
     * @return 主键
     */
	public String getId(){
		return id;
	}
	
    /**
     * 设置主键
     * 
     * @param id 主键
     */
	public void setId(String id){
		this.id = id;
	}
	
    /**
     * 获得业务关键字
     * 
     * @return 业务关键字
     */
	public String getBs_keyword(){
		return bs_keyword;
	}
	
    /**
     * 设置业务关键字
     * 
     * @param bs_keyword 业务关键字
     */
	public void setBs_keyword(String bs_keyword){
		this.bs_keyword = bs_keyword;
	}
	
    /**
     * 获得主记录ID
     * 
     * @return 主记录ID
     */
	public String getRecord_id(){
		return record_id;
	}
	
    /**
     * 设置主记录ID
     * 
     * @param record_id 主记录ID
     */
	public void setRecord_id(String record_id){
		this.record_id = record_id;
	}
	
    /**
     * 获得顺序数
     * 
     * @return 顺序数
     */
	public BigDecimal getOrder_number(){
		return order_number;
	}
	
    /**
     * 设置顺序数
     * 
     * @param order_number 顺序数
     */
	public void setOrder_number(BigDecimal order_number){
		this.order_number = order_number;
	}
	
    /**
     * 获得标题
     * 
     * @return 标题
     */
	public String getTitle(){
		return title;
	}
	
    /**
     * 设置标题
     * 
     * @param title 标题
     */
	public void setTitle(String title){
		this.title = title;
	}
	
    /**
     * 获得原文件名
     * 
     * @return 原文件名
     */
	public String getOld_name(){
		return old_name;
	}
	
    /**
     * 设置原文件名
     * 
     * @param old_name 原文件名
     */
	public void setOld_name(String old_name){
		this.old_name = old_name;
	}
	
    /**
     * 获得真实储存路径
     * 
     * @return 真实储存路径
     */
	public String getSave_name(){
		return save_name;
	}
	
    /**
     * 设置真实储存路径
     * 
     * @param save_name 真实储存路径
     */
	public void setSave_name(String save_name){
		this.save_name = save_name;
	}
	
    /**
     * 获得真实文件大小
     * 
     * @return 真实文件大小
     */
	public BigDecimal getSave_size(){
		return save_size;
	}
	
    /**
     * 设置真实文件大小
     * 
     * @param save_size 真实文件大小
     */
	public void setSave_size(BigDecimal save_size){
		this.save_size = save_size;
	}
	
    /**
     * 获得内容类型
     * 数据库中的注释: $RM_MINE_TYPE=内容类型{ application/vnd.adobe.xdp+xml=Adobe Acrobat XML Data Package, application/vnd.adobe.air-application-installer-package+zip=Adobe AIR, application/framemaker=Adobe FrameMaker, application/pagemaker=Adobe PageMaker, application/pdf=Adobe PDF Document, application/photoshop=Adobe Photoshop, audio/x-aiff=AIFF Audio, application/acp=Alfresco Content Package, image/x-portable-anymap=Anymap Image, image/x-dwg=AutoCAD Drawing, image/x-dwt=AutoCAD Template, audio/basic=Basic Audio, image/bmp=Bitmap Image, image/cgm=CGM Image, message/rfc822=EMail, application/eps=EPS Type PostScript, video/x-flv=Flash Video, image/gif=GIF Image, image/x-portable-graymap=Greymap Image, application/x-gzip=GZIP, application/x-gtar=GZIP Tarball, text/html=HTML, application/vnd.oasis.opendocument.text-web=HTML Document Template, text/calendar=iCalendar File, image/ief=IEF Image, application/java=Java Class, application/x-javascript=Java Script, image/jp2=JPEG 2000 Image, image/jpeg=JPEG Image, application/json=JSON, application/x-latex=Latex, application/x-troff-man=Man Page, text/mediawiki=MediaWiki Markup, application/vnd.excel=Microsoft Excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet=Microsoft Excel 2007, application/vnd.powerpoint=Microsoft PowerPoint, application/vnd.openxmlformats-officedocument.presentationml.presentation=Microsoft PowerPoint 2007, application/vnd.ms-project=Microsoft Project, application/visio=Microsoft Visio, application/msword=Microsoft Word, application/vnd.openxmlformats-officedocument.wordprocessingml.document=Microsoft Word 2007, audio/x-mpeg=MPEG Audio, video/mpeg=MPEG Video, video/mpeg2=MPEG2 Video, video/mp4=MPEG4 Video, video/x-ms-wma=MS Streaming Audio, video/x-ms-asf=MS Streaming Video, video/x-ms-wmv=MS Streaming Video, video/x-msvideo=MS Video, application/octet-stream=Octet Stream, application/vnd.oasis.opendocument.chart=OpenDocument Chart, application/vnd.oasis.opendocument.database=OpenDocument Database, application/vnd.oasis.opendocument.graphics=OpenDocument Drawing, application/vnd.oasis.opendocument.graphics-template=OpenDocument Drawing Template, application/vnd.oasis.opendocument.formula=OpenDocument Formula, application/vnd.oasis.opendocument.image=OpenDocument Image, application/vnd.oasis.opendocument.text-master=OpenDocument Master Document, application/vnd.oasis.opendocument.presentation=OpenDocument Presentation, application/vnd.oasis.opendocument.presentation-template=OpenDocument Presentation Template, application/vnd.oasis.opendocument.spreadsheet=OpenDocument Spreadsheet, application/vnd.oasis.opendocument.spreadsheet-template=OpenDocument Spreadsheet Template, application/vnd.oasis.opendocument.text=OpenDocument Text (OpenOffice 2.0), application/vnd.oasis.opendocument.text-template=OpenDocument Text Template, application/vnd.sun.xml.calc=OpenOffice 1.0/StarOffice6.0 Calc 6.0, application/vnd.sun.xml.draw=OpenOffice 1.0/StarOffice6.0 Draw 6.0, application/vnd.sun.xml.impress=OpenOffice 1.0/StarOffice6.0 Impress 6.0, application/vnd.sun.xml.writer=OpenOffice 1.0/StarOffice6.0 Writer 6.0, application/vnd.ms-outlook=Outlook MSG, image/x-portable-pixmap=Pixmap Image, text/plain=Plain Text, image/png=PNG Image, image/x-portable-bitmap=Portable Bitmap, application/postscript=PostScript, application/remote-printing=Printer Text File, video/quicktime=Quicktime Video, video/x-rad-screenplay=RAD Screen Display, image/x-cmu-raster=Raster Image, image/x-rgb=RGB Image, text/richtext=Rich Text, application/rtf=Rich Text Format, image/svg=Scalable Vector Graphics Image, video/x-sgi-movie=SGI Video, text/sgml=SGML, application/sgml=SGML, application/x-sh=Shell Script, application/x-shockwave-flash=Shockwave Flash, application/vnd.stardivision.chart=StaChart 5.x, application/vnd.stardivision.calc=StarCalc 5.x, application/vnd.stardivision.draw=StarDraw 5.x, application/vnd.stardivision.impress=StarImpress 5.x, application/vnd.stardivision.impress-packed=StarImpress Packed 5.x, application/vnd.stardivision.math=StarMath 5.x, application/vnd.stardivision.writer=StarWriter 5.x, application/vnd.stardivision.writer-global=StarWriter 5.x global, text/css=Style Sheet, text/tab-separated-values=Tab Separated Values, application/x-tar=Tarball, application/x-tex=Tex, application/x-texinfo=Tex Info, image/tiff=TIFF Image, x-world/x-vrml=VRML, audio/x-wav=WAV Audio, application/wordperfect=WordPerfect, image/x-xbitmap=XBitmap Image, application/xhtml+xml=XHTML, text/xml=XML, image/x-xpixmap=XPixmap Image, image/x-xwindowdump=XWindow Dump, application/x-compress=Z Compress, application/zip=ZIP }
     * @return 内容类型
     */
	public String getMime_type(){
		return mime_type;
	}
	
    /**
     * 设置内容类型
     * 数据库中的注释: $RM_MINE_TYPE=内容类型{ application/vnd.adobe.xdp+xml=Adobe Acrobat XML Data Package, application/vnd.adobe.air-application-installer-package+zip=Adobe AIR, application/framemaker=Adobe FrameMaker, application/pagemaker=Adobe PageMaker, application/pdf=Adobe PDF Document, application/photoshop=Adobe Photoshop, audio/x-aiff=AIFF Audio, application/acp=Alfresco Content Package, image/x-portable-anymap=Anymap Image, image/x-dwg=AutoCAD Drawing, image/x-dwt=AutoCAD Template, audio/basic=Basic Audio, image/bmp=Bitmap Image, image/cgm=CGM Image, message/rfc822=EMail, application/eps=EPS Type PostScript, video/x-flv=Flash Video, image/gif=GIF Image, image/x-portable-graymap=Greymap Image, application/x-gzip=GZIP, application/x-gtar=GZIP Tarball, text/html=HTML, application/vnd.oasis.opendocument.text-web=HTML Document Template, text/calendar=iCalendar File, image/ief=IEF Image, application/java=Java Class, application/x-javascript=Java Script, image/jp2=JPEG 2000 Image, image/jpeg=JPEG Image, application/json=JSON, application/x-latex=Latex, application/x-troff-man=Man Page, text/mediawiki=MediaWiki Markup, application/vnd.excel=Microsoft Excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet=Microsoft Excel 2007, application/vnd.powerpoint=Microsoft PowerPoint, application/vnd.openxmlformats-officedocument.presentationml.presentation=Microsoft PowerPoint 2007, application/vnd.ms-project=Microsoft Project, application/visio=Microsoft Visio, application/msword=Microsoft Word, application/vnd.openxmlformats-officedocument.wordprocessingml.document=Microsoft Word 2007, audio/x-mpeg=MPEG Audio, video/mpeg=MPEG Video, video/mpeg2=MPEG2 Video, video/mp4=MPEG4 Video, video/x-ms-wma=MS Streaming Audio, video/x-ms-asf=MS Streaming Video, video/x-ms-wmv=MS Streaming Video, video/x-msvideo=MS Video, application/octet-stream=Octet Stream, application/vnd.oasis.opendocument.chart=OpenDocument Chart, application/vnd.oasis.opendocument.database=OpenDocument Database, application/vnd.oasis.opendocument.graphics=OpenDocument Drawing, application/vnd.oasis.opendocument.graphics-template=OpenDocument Drawing Template, application/vnd.oasis.opendocument.formula=OpenDocument Formula, application/vnd.oasis.opendocument.image=OpenDocument Image, application/vnd.oasis.opendocument.text-master=OpenDocument Master Document, application/vnd.oasis.opendocument.presentation=OpenDocument Presentation, application/vnd.oasis.opendocument.presentation-template=OpenDocument Presentation Template, application/vnd.oasis.opendocument.spreadsheet=OpenDocument Spreadsheet, application/vnd.oasis.opendocument.spreadsheet-template=OpenDocument Spreadsheet Template, application/vnd.oasis.opendocument.text=OpenDocument Text (OpenOffice 2.0), application/vnd.oasis.opendocument.text-template=OpenDocument Text Template, application/vnd.sun.xml.calc=OpenOffice 1.0/StarOffice6.0 Calc 6.0, application/vnd.sun.xml.draw=OpenOffice 1.0/StarOffice6.0 Draw 6.0, application/vnd.sun.xml.impress=OpenOffice 1.0/StarOffice6.0 Impress 6.0, application/vnd.sun.xml.writer=OpenOffice 1.0/StarOffice6.0 Writer 6.0, application/vnd.ms-outlook=Outlook MSG, image/x-portable-pixmap=Pixmap Image, text/plain=Plain Text, image/png=PNG Image, image/x-portable-bitmap=Portable Bitmap, application/postscript=PostScript, application/remote-printing=Printer Text File, video/quicktime=Quicktime Video, video/x-rad-screenplay=RAD Screen Display, image/x-cmu-raster=Raster Image, image/x-rgb=RGB Image, text/richtext=Rich Text, application/rtf=Rich Text Format, image/svg=Scalable Vector Graphics Image, video/x-sgi-movie=SGI Video, text/sgml=SGML, application/sgml=SGML, application/x-sh=Shell Script, application/x-shockwave-flash=Shockwave Flash, application/vnd.stardivision.chart=StaChart 5.x, application/vnd.stardivision.calc=StarCalc 5.x, application/vnd.stardivision.draw=StarDraw 5.x, application/vnd.stardivision.impress=StarImpress 5.x, application/vnd.stardivision.impress-packed=StarImpress Packed 5.x, application/vnd.stardivision.math=StarMath 5.x, application/vnd.stardivision.writer=StarWriter 5.x, application/vnd.stardivision.writer-global=StarWriter 5.x global, text/css=Style Sheet, text/tab-separated-values=Tab Separated Values, application/x-tar=Tarball, application/x-tex=Tex, application/x-texinfo=Tex Info, image/tiff=TIFF Image, x-world/x-vrml=VRML, audio/x-wav=WAV Audio, application/wordperfect=WordPerfect, image/x-xbitmap=XBitmap Image, application/xhtml+xml=XHTML, text/xml=XML, image/x-xpixmap=XPixmap Image, image/x-xwindowdump=XWindow Dump, application/x-compress=Z Compress, application/zip=ZIP }
     * @param mime_type 内容类型
     */
	public void setMime_type(String mime_type){
		this.mime_type = mime_type;
	}
	
    /**
     * 获得编码
     * 
     * @return 编码
     */
	public String getEncoding(){
		return encoding;
	}
	
    /**
     * 设置编码
     * 
     * @param encoding 编码
     */
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}
	
    /**
     * 获得描述
     * 
     * @return 描述
     */
	public String getDescription(){
		return description;
	}
	
    /**
     * 设置描述
     * 
     * @param description 描述
     */
	public void setDescription(String description){
		this.description = description;
	}
	
    /**
     * 获得作者
     * 
     * @return 作者
     */
	public String getAuthor(){
		return author;
	}
	
    /**
     * 设置作者
     * 
     * @param author 作者
     */
	public void setAuthor(String author){
		this.author = author;
	}
	
    /**
     * 获得数据可用状态
     * 
     * @return 数据可用状态
     */
	public String getUsable_status(){
		return usable_status;
	}
	
    /**
     * 设置数据可用状态
     * 
     * @param usable_status 数据可用状态
     */
	public void setUsable_status(String usable_status){
		this.usable_status = usable_status;
	}
	
    /**
     * 获得修改日期
     * 
     * @return 修改日期
     */
	public Timestamp getModify_date(){
		return modify_date;
	}
	
    /**
     * 设置修改日期
     * 
     * @param modify_date 修改日期
     */
	public void setModify_date(Timestamp modify_date){
		this.modify_date = modify_date;
	}
	
    /**
     * 获得修改IP
     * 
     * @return 修改IP
     */
	public String getModify_ip(){
		return modify_ip;
	}
	
    /**
     * 设置修改IP
     * 
     * @param modify_ip 修改IP
     */
	public void setModify_ip(String modify_ip){
		this.modify_ip = modify_ip;
	}
	
    /**
     * 获得修改用户ID
     * 
     * @return 修改用户ID
     */
	public String getModify_user_id(){
		return modify_user_id;
	}
	
    /**
     * 设置修改用户ID
     * 
     * @param modify_user_id 修改用户ID
     */
	public void setModify_user_id(String modify_user_id){
		this.modify_user_id = modify_user_id;
	}
	
    //结束rm_code_type的setter和getter方法
    
}