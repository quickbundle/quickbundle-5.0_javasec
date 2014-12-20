/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.au.tools.test.upload.fileupload --> RmUploadHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-3-10 19:45:56 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.third.fileupload;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.quickbundle.base.web.servlet.RmHolderServlet;
import org.quickbundle.config.RmConfig;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.quickbundle.tools.support.picture.jpegtool.JpegTool;
import org.quickbundle.tools.support.picture.jpegtool.JpegToolException;


/**
 * 功能、用途、现存BUG:
 * 
 * @author  
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmUploadHelper implements IGlobalConstants {
    
    public final static String DEFAULT_JPG_LOGO = "quickbundle.org";
    
    public final static String DEFAULT_UPLOAD_DIR = "upload/default";
    
    public final static String DEFAULT_UPLOAD_DIALOG_DIR = "upload/dialog";

    public final static String SYSTEM_FILE_SEPARATOR = File.separator;

    public static long SYSTEM_CURRENT_TIME_MILLIS = System.currentTimeMillis();
    
    public static String[] DEFAULT_INPUT_ACCESSORY_NAME = new String[]{"upload_saveName", "upload_oldName", "upload_isImage", "upload_abbreviatoryName", "upload_saveNameWidthHeight", "upload_abbreviatoryNameWidthHeight", "upload_remark"};
    
    public static String[] ALLOWABLE_UPLOAD_FILE_TYPE = new String[]{"*"};

    private static double DEFAULT_WH_SCALE = 0.75;

    private static int DEFAULT_MAX_WIDTH = 400;

    private static int DEFAULT_MAX_HEIGHT = (int) (DEFAULT_MAX_WIDTH * DEFAULT_WH_SCALE);

    private static int DEFAULT_sizeThreshold = 100 * 1024;

    private static File DEFAULT_repository = new File(System.getProperty("java.io.tmpdir"));

    public final static int DEFAULT_sizeMax = 200 * 1024 * 1024;

    private static IAccessoryProcess DEFAULT_accessoryProcess = new IAccessoryProcess() {

        public Object[] processFile(UploadConfiguration conf, String clientName, String saveFullName) throws Exception {
            Object[] rtObject = new Object[6]; //0,是否图片; 1,缩略图保存路径; 2,原图宽; 3,原图高; 4,缩略图宽; 5,缩略图高 
            // TODO
            String realName = getFileNameFromPath(clientName);

            { //处理图片
                File imageFile = new File(saveFullName);
                if(imageFile.length() >1.5 * 1024 * 1024) {
                    throw new Exception("图片大小是" + ((double)imageFile.length()/1024/1024) + "M, 大于允许的1.5M，无法制作缩略图");
                }
                if (formatImageType.contains(getExtendName(realName).toLowerCase())) {
                    String image2Name = getFormatSaveName(clientName);
                    String image2FullName = conf.getUploadDir() + SYSTEM_FILE_SEPARATOR + image2Name;
                    JpegTool jpegTool = new JpegTool();
                    File fi = new File(saveFullName);
                    BufferedImage bsrc = ImageIO.read(fi);
                    float whScale = bsrc.getWidth() / bsrc.getHeight();
                    rtObject[2] = new Integer(bsrc.getWidth());
                    rtObject[3] = new Integer(bsrc.getHeight());
//                    int tempWidth = ((Integer)rtObject[2]).intValue();
//                    int tempHeight = ((Integer)rtObject[3]).intValue();
                    if (whScale >= conf.getImageWidthHeightScale() && bsrc.getWidth() > conf.getImageMaxWidth()) {
                        jpegTool.SetSmallWidth(conf.getImageMaxWidth());
//                        rtObject[4] = new Integer(conf.getImageMaxWidth());
//                        rtObject[5] = new Integer((int)(conf.getImageMaxWidth() / (tempWidth / tempHeight) ));
                    } else if(whScale < conf.getImageWidthHeightScale() && bsrc.getHeight() > conf.getImageMaxHeight() ) {
                        jpegTool.SetSmallHeight(conf.getImageMaxHeight());
//                        rtObject[4] = new Integer((int)(conf.getImageMaxHeight() * (tempWidth / tempHeight) ));
//                        rtObject[5] = new Integer((int)(conf.getImageMaxHeight()));
                    } else {
                        jpegTool.SetSmallHeight(bsrc.getHeight());
//                        rtObject[4] = new Integer((int)(bsrc.getWidth()));
//                        rtObject[5] = new Integer((int)(bsrc.getHeight()));
                    }
                    jpegTool.doFinal(saveFullName, image2FullName);
                    try {
                        insertTextIntoImage(saveFullName, DEFAULT_JPG_LOGO);
                    } catch (Exception e) {
                    	RmLogHelper.getLogger(RmUploadHelper.class).error(saveFullName + "不能加水印:" + e.toString());
                    }
                    
                    int size2[] = JpegTool.getSize(image2FullName);
                    rtObject[4] = new Integer(size2[0]);
                    rtObject[5] = new Integer(size2[1]);
                    rtObject[0] = "2";
                    rtObject[1] = image2Name;
                } else {
                    rtObject[0] = "1";
                }
            }
            return rtObject;
        }

    };
    
    private static Set<String> allImageType = new HashSet<String>(){
    	{
            this.add("jpg");
            this.add("jpeg");
            this.add("jpe");
            this.add("jfif");
            this.add("gif");
            this.add("bmp");
            this.add("dib");
            this.add("tif");
            this.add("tiff");
            this.add("png");	
    	}
    };

    private static Set<String> formatImageType = new HashSet<String>(){
    	{
            this.add("jpg");
            this.add("jpeg");
    	}
    };

    /**
     * 功能: 获得配置实例
     * 
     * @param uploadDir
     * @return
     */
    public static UploadConfiguration getConfigurationInstance(File uploadDir) {
        RmUploadHelper uht = new RmUploadHelper();
        UploadConfiguration conf = uht.new UploadConfiguration();
        conf.setImageWidthHeightScale(DEFAULT_WH_SCALE);
        conf.setImageMaxWidth(DEFAULT_MAX_WIDTH);
        conf.setImageMaxHeight(DEFAULT_MAX_HEIGHT);
        conf.setSizeThreshold(DEFAULT_sizeThreshold);
        conf.setRepository(DEFAULT_repository);
        conf.setSizeMax(DEFAULT_sizeMax);
        conf.setUploadDir(uploadDir);
        conf.setAccessoryProcess(DEFAULT_accessoryProcess);
        return conf;
    }

    /**
     * 功能: 得到绝对唯一的数字
     * 
     * @return
     */
    public static synchronized long getUniqueNumber() {
        return SYSTEM_CURRENT_TIME_MILLIS++;
    }
    
    /**
     * 功能: 得到0-maxValue之间的随机整数
     *
     * @param maxValue
     * @return
     */
    public static int getRandomInt(int maxValue) {
        Random random = new Random();
        return random.nextInt(maxValue);
    }
    
    public static String getUniqueString() {
        return String.valueOf(getUniqueNumber()) + String.valueOf(9000000 + getRandomInt(999999)); 
    }

    /**
     * 功能: 从上传的request中得到普通字符值，一定要注意只能调用一次
     * @param request
     * @param fieldName
     * @return
     */
    public static String getValueFromUploadForm(HttpServletRequest request, String fieldName) {
        String returnValue = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(factory);
            List lItems = sfu.parseRequest(request);
            java.util.Iterator iter = lItems.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (fieldName.equals(item.getFieldName())) {
                    returnValue = item.getString();
                    break;
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    
    /**
     * 功能: 获得str中最后出现keyword的坐标
     *
     * @param str
     * @param keyword
     * @return
     */
    public static int getLastPosition(String str, String[] keyword) {
        int position = -1;
        if(str == null || "".equals(str)) {
            position = -2;
        } else {
            for(int i=0; i<keyword.length; i++) {
                if(position < str.lastIndexOf(keyword[i])) {
                    position = str.lastIndexOf(keyword[i]);
                }
            } 
        }

        return position;
    }
    

    /**
     * 功能: 从全路径获得文件名
     *
     * @param fullPath
     * @return
     */
    public static String getFileNameFromPath(String fullPath) {
    	if(fullPath != null && fullPath.endsWith(".jsp")) {
    		fullPath = fullPath + "_";
    	}
        int position = getLastPosition(fullPath, new String[]{"/","\\"});
        if(position >= 0) {
            return fullPath.substring(position + 1);
        } else {
            return fullPath;
        }
    }

    /**
     * 功能: 得到文件扩展名
     * 
     * @param realName
     * @return
     */
    public static String getExtendName(String realName) {
        String rtValue = "";
        int position = realName.lastIndexOf(".");
        if (position >= 0) {
            rtValue = realName.substring(position + 1);
        }
        return rtValue;
    }

    /**
     * 功能: 得到2006/2006-03/1142006923316.jpg
     * 
     * @param fileName
     * @return
     */
    public static String getFormatSaveName(String fileName) {
        String saveName = "";
        String realName = getFileNameFromPath(fileName);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String year = ts.toString().substring(0, 4);
        String yearMonth = ts.toString().substring(0, 7);
        saveName = year + SYSTEM_FILE_SEPARATOR + yearMonth + SYSTEM_FILE_SEPARATOR + getUniqueString() + "." + getExtendName(realName);
        return saveName;
    }

    /**
     * 功能: 得到默认的上传路径
     * 
     * @param request
     * @return
     */
    public static File getDefaultUploadDir(HttpServletRequest request) {
        return new File(request.getSession().getServletContext().getRealPath(DEFAULT_UPLOAD_DIR));
    }

    /**
     * 功能: 上传request中的所有文件，返回真实路径
     *
     * @param request
     * @return Object[] Object[0]是1个Map,用户存储其它input值，肯定有这个对象
     * 					Object[1]以后的对象返回String[], 0是保存路径，1是原文件名，2表示是否图片（1-普通图片,2-能做缩略图的Jpg），3是缩略图路径(可能无)，4是原始图宽X高(可能无)，5是缩略图宽X高(可能无)
     * @throws Exception
     */
    public static Object[] uploadRequestFiles(HttpServletRequest request) throws Exception {
        return uploadRequestFiles(request, RmUploadHelper.getConfigurationInstance(getDefaultUploadDir(request)));
    }

    /**
     * 功能: 上传request中的所有文件，返回真实路径
     *
     * @param request
     * @param conf
     * @return Object[] Object[0]是1个Map,用户存储其它input值，肯定有这个对象
     * 					Object[1]以后的对象返回String[], 0是保存路径，1是原文件名，2表示是否图片（1-普通图片,2-能做缩略图的Jpg），3是缩略图路径(可能无)，4是原始图宽X高(可能无)，5是缩略图宽X高(可能无)
     * @throws Exception
     */
    public static Object[] uploadRequestFiles(HttpServletRequest request, UploadConfiguration conf) throws Exception {
        List<Object> returnInfo = new ArrayList<Object>();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置最多只允许在内存中存储的数据,单位:字节
        factory.setSizeThreshold(conf.getSizeThreshold());
        //设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
        factory.setRepository(conf.getRepository());
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //设置允许用户上传文件大小,单位:字节
        sfu.setSizeMax(conf.getSizeMax());
        Map<String, Object> mOtherValue = new TreeMap<String, Object>();
        //加入其它值的表示
        returnInfo.add(mOtherValue);
        List lItems = null;
        //开始读取上传信息
        try {
            lItems = sfu.parseRequest(request);
        } catch (Exception e) {
        	if(!(e instanceof org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException)) {
        		throw e;
        	} else {
        	    return returnInfo.toArray();
        	}
        }

        //依次处理每个上传的文件
        for (Iterator iter = lItems.iterator(); iter.hasNext();) {
            String isImage = "0";
            FileItem item = (FileItem) iter.next();
            //忽略其他不是文件域的所有表单信息
            if (!item.isFormField()) {
                String name = item.getName();
                long size = item.getSize();
                if ((name == null || "".equals(name.trim())) && size == 0)
                    continue;
                String realName =getFileNameFromPath(name);
                //为了支持多值提交,把","替换为"，"
                realName = RmStringHelper.replaceAll(realName, ",", "，");
                String saveName = null;
                if(conf.getSpecifyFileName() == null || conf.getSpecifyFileName().length() == 0) {
                    saveName = getFormatSaveName(name);
                } else {
                    saveName = conf.getSpecifyFileName();
                }
                
                String saveFullName = conf.getUploadDir() + SYSTEM_FILE_SEPARATOR + saveName;
                RmLogHelper.getLogger(RmUploadHelper.class).debug("new File(saveFullName).getParentFile().exists()=" + new File(saveFullName).getParentFile().exists());
                if (!new File(saveFullName).getParentFile().exists()) {
                    new File(saveFullName).getParentFile().mkdirs();
                }
                RmLogHelper.getLogger(RmUploadHelper.class).debug("upload " + name + " to " + saveFullName + ", size=" + size / 1024 + "k");
                item.write(new File(saveFullName));
//                String abbreviatoryImage = null;
//                String saveNameWidthHeight = "";
//                String abbreviatoryImageWidthHeight = "";
                try { //处理图片
                    if(allImageType.contains(getExtendName(realName).toLowerCase())) {  //判断是否合法图片
                        isImage = "1";
                        /*  注释掉本段为了不做缩略图，提高性能 
                        Object[] result = conf.accessoryProcess.processFile(conf, name, saveFullName);
                        isImage = String.valueOf(result[0]);
                        if("2".equals(isImage)) {
                            abbreviatoryImage = (result[1]==null)?null:String.valueOf(result[1]);
                            saveNameWidthHeight = result[2] + "X" + result[3];
                            abbreviatoryImageWidthHeight = result[4] + "X" + result[5];
                        }
                        */
                    } else {
                        //非图片暂时不作处理
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] fileInfo = null;
                //注释掉本段为了不做缩略图，提高性能 
//                if (abbreviatoryImage == null) {
                    fileInfo = new String[] {formatFileToUrl(saveName), realName, isImage };
//                } else {
//                    fileInfo = new String[] {formatFileToUrl(saveName), realName, isImage, formatFileToUrl(abbreviatoryImage), saveNameWidthHeight, abbreviatoryImageWidthHeight };
//                }
                returnInfo.add(fileInfo);
            } else {
                mOtherValue.put(item.getFieldName(), RmStringHelper.encode2Encode(item.getString(), "iso8859-1", "GBK"));
            }
        }
        return returnInfo.toArray();
    }
    
    /**
     * 功能: 把\\upload\2006\2006-03\test.pic转化为/upload/2006/2006-03/test.pic
     *
     * @param filePath
     * @return
     */
    public static String formatFileToUrl(String filePath) {
        return RmStringHelper.replaceAll(filePath, "\\", "/");
    }
    
    public static void download(HttpServletRequest request, HttpServletResponse response, String saveName, String fileName) throws UnsupportedEncodingException {
        download(request, response, saveName, fileName, DEFAULT_UPLOAD_DIR);
    }

    /**
     * 功能: 以指定的名称下载附件
     * 
     * @param response
     * @param fileName
     * @param downloadedFile
     * @throws UnsupportedEncodingException
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String saveName, String fileName, String uploadDir) throws UnsupportedEncodingException {
        response.setContentType("application/x-msdownload");
        
        String fileName2 = URLEncoder.encode(fileName, RmConfig.getSingleton().getDefaultEncode());
        /*
         * see http://support.microsoft.com/default.aspx?kbid=816868
         */
        if (fileName.length() > 150) {
            String guessCharset = "gb2312";  ///*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/
            fileName = new String(fileName.getBytes(guessCharset), "ISO8859-1"); 
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2);

        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(request.getSession().getServletContext().getRealPath(uploadDir) + SYSTEM_FILE_SEPARATOR + saveName));

            byte[] bytes = new byte[2048];
            int s = 0;

            while ((s = bis.read(bytes)) != -1) {
                out.write(bytes, 0, s);
            }
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {

                }
                out = null;
            }

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e1) {

                }
                bis = null;
            }
        }
    }

    /**
     * 功能: 往图片插入文字
     * 
     * @throws IOException
     * @throws FileNotFoundException
     * 
     *  
     */
    public static void insertTextIntoImage(String imagePath, String text) throws FileNotFoundException, IOException {
        try {
            FileInputStream input = null;
            FileOutputStream output = null;
            input = new FileInputStream(imagePath);
            output = new FileOutputStream(imagePath + ".backup" + System.currentTimeMillis());
            byte[] b = new byte[1024 * 10];
            int len;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        float alpha=0.2f;
        File _file = new File(imagePath);
        Image src = ImageIO.read(_file);
        int wideth = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, wideth, height, null);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT | Font.BOLD, 70));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g.rotate(-Math.atan((double)image.getHeight()/image.getWidth()), 0, 0);
        int tempX = image.getHeight() < image.getWidth() ? 0 : -(int)(2 * (image.getHeight() - image.getWidth()) * Math.acos(Math.toRadians(45)));
        int tempY = (int)(image.getWidth() * image.getHeight() / Math.sqrt(image.getWidth() * image.getWidth() + image.getHeight() * image.getHeight()));
        g.drawString(text, tempX, tempY);
//        g.drawString(text, tempX, tempY - 100);
//        g.drawString(text, tempX, tempY + 100);
        g.dispose();
        FileOutputStream out = new FileOutputStream(imagePath);
        ImageIO.write(image, "jpg", out);
        out.close();
    }
    
    /**
     * @param path
     * @return
     */
    public static int markAllJpg(String path) {
        int count = 0;
        path = RmXmlHelper.formatToFile(path);
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            File a = new File(path);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + file[i]);
                } else {
                    temp = new File(path + File.separator + file[i]);
                }

                if (temp.isFile() && temp.exists() && temp.toString().toLowerCase().endsWith(".jpg")) {
                    insertTextIntoImage(temp.toString(), DEFAULT_JPG_LOGO);
                    count ++;
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    count += markAllJpg(path + File.separator + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return count;
    }

    public class UploadConfiguration {

        private UploadConfiguration() {
            //不实现
        }

        private double imageWidthHeightScale = 0.75;

        private int imageMaxWidth = 400;

        private int imageMaxHeight = 300;

        private File uploadDir;

        private int sizeThreshold;

        private File repository;

        private int sizeMax;
        
        private String specifyFileName;

        private IAccessoryProcess accessoryProcess;

        /**
         * @return 返回 imageMaxHeight。缩略图的最大高度
         */
        public int getImageMaxHeight() {
            return imageMaxHeight;
        }

        /**
         * @param imageMaxHeight
         *            要设置的 imageMaxHeight。缩略图的最大高度
         */
        public void setImageMaxHeight(int defaultMaxHeight) {
            this.imageMaxHeight = defaultMaxHeight;
            this.imageWidthHeightScale = this.imageMaxWidth / this.imageMaxHeight;
        }

        /**
         * @return 返回 imageMaxWidth。缩略图的最大宽度
         */
        public int getImageMaxWidth() {
            return imageMaxWidth;
        }

        /**
         * @param imageMaxWidth
         *            要设置的 imageMaxWidth。缩略图的最大宽度
         */
        public void setImageMaxWidth(int defaultMaxWidth) {
            this.imageMaxWidth = defaultMaxWidth;
            this.imageWidthHeightScale = this.imageMaxWidth / this.imageMaxHeight;
        }

        /**
         * @return 返回 imageWidthHeightScale。缩略图的宽与高之比
         */
        public double getImageWidthHeightScale() {
            return imageWidthHeightScale;
        }

        /**
         * @param imageWidthHeightScale
         *            要设置的 imageWidthHeightScale。缩略图的宽与高之比
         */
        public void setImageWidthHeightScale(double defaultWhScale) {
            this.imageWidthHeightScale = defaultWhScale;
            this.imageMaxHeight = (int) (this.imageMaxWidth / this.imageWidthHeightScale);
        }

        /**
         * @return 返回 uploadDir。上传文件的根路径
         */
        public File getUploadDir() {
            return uploadDir;
        }

        /**
         * @param uploadDir
         *            要设置的 uploadDir。上传文件的根路径
         */
        public void setUploadDir(File uploadDir) {
            this.uploadDir = uploadDir;
        }

        /**
         * @return 返回 repository。临时目录的路径
         */
        public File getRepository() {
            return repository;
        }

        /**
         * @param repository
         *            要设置的 repository。临时目录的路径
         */
        public void setRepository(File repository) {
            this.repository = repository;
        }

        /**
         * @return 返回 sizeMax。上传的所有文件的最大值
         */
        public int getSizeMax() {
            return sizeMax;
        }

        /**
         * @param sizeMax
         *            要设置的 sizeMax。上传的所有文件的最大值
         */
        public void setSizeMax(int sizeMax) {
            this.sizeMax = sizeMax;
        }

        /**
         * @return 返回 sizeThreshold。用于缓存上传的内存空间大小
         */
        public int getSizeThreshold() {
            return sizeThreshold;
        }

        /**
         * @param sizeThreshold
         *            要设置的 sizeThreshold。用于缓存上传的内存空间大小
         */
        public void setSizeThreshold(int sizeThreshold) {
            this.sizeThreshold = sizeThreshold;
        }

        /**
         * @return 返回 accessoryProcess。
         */
        public IAccessoryProcess getAccessoryProcess() {
            return accessoryProcess;
        }

        /**
         * @param accessoryProcess
         *            要设置的 accessoryProcess。
         */
        public void setAccessoryProcess(IAccessoryProcess accessoryProcess) {
            this.accessoryProcess = accessoryProcess;
        }
        

        /**
         * @return 返回 specifyFileName。强制指定文件名，如果设置此项，则上传的文件都是这个名字
         */
        public String getSpecifyFileName() {
            return specifyFileName;
        }
        /**
         * @param specifyFileName 要设置的 specifyFileName。强制指定文件名，如果设置此项，则上传的文件都是这个名字
         */
        public UploadConfiguration setSpecifyFileName(String specifyFileName) {
            this.specifyFileName = specifyFileName;
            return this;
        }
    }
    
    
    public static boolean deleteAffix_default(String save_name) {
    	return deleteAffix(save_name, DEFAULT_UPLOAD_DIR);
    }
    
    public static boolean deleteAffix_default(String[] aSave_name) {
    	boolean isSuccess = true;
    	for (int i = 0; i < aSave_name.length; i++) {
    		isSuccess = isSuccess && deleteAffix_default(aSave_name[i]);
		}
    	return isSuccess;
    }
    
    public static boolean deleteAffix(String save_name, String dir) {
    	File f = new File(RmHolderServlet.getDefaultServletContext().getRealPath("/" + dir + "/" + save_name));
    	System.out.println("%%%%%%%% delete " + f);
    	return f.delete();
    }

//    /**
//     * 功能: 
//     *
//     * @param request
//     * @param stampType 戳类型，可能是INSERT, UPDATE
//     * @return
//     */
//    public static RmAccessoryVo[] populateUploadInfo(HttpServletRequest request, String stampType) {
//        return populateUploadInfo(request, stampType, DEFAULT_INPUT_ACCESSORY_NAME);
//    }
//
//    /**
//     * 功能: 从request中获得上传信息
//     *
//     * @param request
//     * @param stampType 戳类型，可能是INSERT, UPDATE
//     * @param uploadFormName 必须是length为7的字符串数组
//     * @return
//     */
//    public static RmAccessoryVo[] populateUploadInfo(HttpServletRequest request, String stampType, String[] uploadFormName) {
//        RmAccessoryVo[] aAccessory = null;
//        String[] upload_saveName = RmJspHelper.getArrayWithNullFromRequest(request, uploadFormName[0]); 
//        String[] upload_oldName = RmJspHelper.getArrayWithNullFromRequest(request, uploadFormName[1]); 
//        String[] upload_isImage = RmJspHelper.getArrayWithNullFromRequest(request, uploadFormName[2]); 
//        String[] upload_abbreviatoryName = RmJspHelper.getArrayWithNullFromRequest(request, uploadFormName[3]);
//        String[] upload_saveNameWidthHeight = RmJspHelper.getArrayWithNullFromRequest(request, uploadFormName[4]); 
//        String[] upload_abbreviatoryNameWidthHeight = RmJspHelper.getArrayWithNullFromRequest(request, uploadFormName[5]); 
//        String[] upload_remark = RmJspHelper.getArrayWithNullFromRequest(request, uploadFormName[6]); 
//        aAccessory = new RmAccessoryVo[upload_saveName.length];
//        for(int i=0; i<upload_saveName.length; i++) {
//            aAccessory[i] = new RmAccessoryVo();
//            
//            //处理保存名
//            aAccessory[i].setSave_name(upload_saveName[i]);
//            File fileSave = new File(RmJspHelper.getSession(request).getServletContext().getRealPath(DEFAULT_UPLOAD_DIALOG_DIR) + SYSTEM_FILE_SEPARATOR + aAccessory[i].getSave_name());
//            if(fileSave.exists() && fileSave.isFile()) {
//                aAccessory[i].setSave_size(String.valueOf(fileSave.length()));
//            }
//            
//            //处理原始名
//            if(upload_oldName.length > i) {
//                aAccessory[i].setOld_name(upload_oldName[i]);
//            }
//
//            //处理类型
//            if(upload_isImage.length > i) {
//                aAccessory[i].setType(upload_isImage[i]); 
//            }
//            
//            //处理缩略图路径
//            if(upload_abbreviatoryName.length > i) {
//                aAccessory[i].setAbbreviatory_name(upload_abbreviatoryName[i]);
//                File fileAccessory = new File(RmJspHelper.getSession(request).getServletContext().getRealPath(DEFAULT_UPLOAD_DIALOG_DIR) + SYSTEM_FILE_SEPARATOR + aAccessory[i].getAbbreviatory_name());
//                if(fileAccessory.exists() && fileAccessory.isFile()) {
//                    aAccessory[i].setAbbreviatory_size(String.valueOf(fileAccessory.length()));
//                }
//            }
//            
//            //处理保存图片宽高
//            if(upload_saveNameWidthHeight.length > i) {
//                aAccessory[i].setSave_width_height(upload_saveNameWidthHeight[i]);
//            }
//            
//            //处理缩略图宽高
//            if(upload_abbreviatoryNameWidthHeight.length > i) {
//                aAccessory[i].setAbbr_width_height(upload_abbreviatoryNameWidthHeight[i]);
//            }
//
//            //处理上传备注
//            if(upload_remark.length > i) {
//                aAccessory[i].setRemark(upload_remark[i]);
//            }
//            if(stampType == null || stampType.length() == 0) {
//                
//            } else if("INSERT".equals(stampType.toUpperCase())) {
//                RmVoHelper.markCreateStamp(request, aAccessory[i]);
//            } else if("UPDATE".equals(stampType.toUpperCase())) {
//                RmVoHelper.markModifyStamp(request, aAccessory[i]);
//            }
//            aAccessory[i].setOrder_code(String.valueOf(i));
//        }
//        return aAccessory;
//    }
//    
//    public static Map revertUploadInfo(RmAccessoryVo[] accessoryVo) {
//        return revertUploadInfo(accessoryVo, DEFAULT_INPUT_ACCESSORY_NAME);
//    }
//    
//    /**
//     * 功能: 把附件信息还原为回写信息
//     *
//     * @param accessoryVo
//     * @param uploadFormName
//     * @return
//     */
//    public static Map revertUploadInfo(RmAccessoryVo[] accessoryVo, String[] uploadFormName) {
//        Map mUploadInfo = new RmSequenceMap();
//        String[] uploadInfo = new String[uploadFormName.length];
//        for(int i=0; i<uploadInfo.length; i++) {
//            uploadInfo[i] = "";
//        }
//        for(int i=0; i<accessoryVo.length; i++) {
//            uploadInfo[0] += RmStringHelper.prt(accessoryVo[i].getSave_name());  //去掉了encodeUrl
//            uploadInfo[1] += RmStringHelper.prt(accessoryVo[i].getOld_name());
//            uploadInfo[2] += RmStringHelper.prt(accessoryVo[i].getType());
//            uploadInfo[3] += RmStringHelper.prt(accessoryVo[i].getAbbreviatory_name());
//            uploadInfo[4] += RmStringHelper.prt(accessoryVo[i].getSave_width_height());
//            uploadInfo[5] += RmStringHelper.prt(accessoryVo[i].getAbbr_width_height());
//            uploadInfo[6] += RmStringHelper.prt(accessoryVo[i].getRemark());
//            if(i<accessoryVo.length-1) {
//                for(int j=0; j<uploadInfo.length; j++) {
//                    uploadInfo[j] += ",";
//                }
//            }
//        }
//        for(int i=0; i<uploadInfo.length; i++) {
//            mUploadInfo.put(uploadFormName[i], uploadInfo[i]);
//        }
//        return mUploadInfo;
//    }
    
    /**
     * 得到mime type
     * 
     * @param f
     * @return
     */
    public static String getMimeTypeFromFile(File f) {
    	return new MimetypesFileTypeMap().getContentType(f);
    }
    
    public interface IAccessoryProcess {
        public Object[] processFile(UploadConfiguration conf, String clientName, String saveFullName) throws JpegToolException, IOException, Exception;
    }
}