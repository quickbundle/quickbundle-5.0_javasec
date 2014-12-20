/*
 * 创建日期 2006-2-15
 */
package org.quickbundle.tools.support.picture.jpegtool;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * 本类实现一个对 JPG/JPEG 图像文件进行缩影处理的方法
 * 
 * 即给定一个 JPG 文件，可以生成一个该 JPG 文件的缩影图像文件 (JPG 格式 )
 * 
 * 提供三种生成缩影图像的方法：
 * 
 * 1 、设置缩影文件的宽度，根据设置的宽度和源图像文件的大小来确定新缩影文件的长度来生成缩影图像
 * 
 * 2 、设置缩影文件的长度，根据设置的长度和源图像文件的大小来确定新缩影文件的宽度来生成缩影图像
 * 
 * 3 、设置缩影文件相对于源图像文件的比例大小，根据源图像文件的大小及设置的比例来确定新缩影文件的大小来生成缩影图像
 * 
 * 新生成的缩影图像可以比原图像大，这时即是放大源图像。
 * 
 * @author abnerchai contact:josserchai@yahoo.com
 * 
 * @version 1.0
 * 
 * @exception JpegToolException
 *  
 */

public class JpegTool {

    // 对象是否己经初始化

    private boolean isInitFlag = false;

    // 定义源图片所在的带路径目录的文件名

//    private String pic_big_pathfilename = null;

    // 生成小图片的带存放路径目录的文件名

//    private String pic_small_pathfilename = null;

    // 定义生成小图片的宽度和高度，给其一个就可以了

    private int smallpicwidth = 0;

    private int smallpicheight = 0;

    // 定义小图片的相比原图片的比例

    private double picscale = 0;

    /**
     * 
     * 构造函数
     * 
     * @param 没有参数
     *  
     */

    public JpegTool() {

        this.isInitFlag = false;

    }

    /**
     * 
     * 私有函数，重置所有的参数
     * 
     * @param 没有参数
     * 
     * @return 没有返回参数
     *  
     */

    private void resetJpegToolParams() {

        this.picscale = 0;

        this.smallpicwidth = 0;

        this.smallpicheight = 0;

        this.isInitFlag = false;

    }

    /**
     * 
     * @param scale 设置缩影图像相对于源图像的大小比例如 0.5
     * 
     * @throws JpegToolException
     *  
     */

    public void SetScale(double scale) throws JpegToolException {

        if (scale <= 0) {

            throw new JpegToolException(" 缩放比例不能为 0 和负数！ ");

        }

        this.resetJpegToolParams();

        this.picscale = scale;

        this.isInitFlag = true;

    }

    /**
     * 
     * @param smallpicwidth 设置缩影图像的宽度
     * 
     * @throws JpegToolException
     *  
     */

    public void SetSmallWidth(int smallpicwidth) throws JpegToolException {

        if (smallpicwidth <= 0) {

            throw new JpegToolException(" 缩影图片的宽度不能为 0 和负数！ ");

        }

        this.resetJpegToolParams();

        this.smallpicwidth = smallpicwidth;

        this.isInitFlag = true;

    }

    /**
     * 
     * @param smallpicheight 设置缩影图像的高度
     * 
     * @throws JpegToolException
     *  
     */

    public void SetSmallHeight(int smallpicheight) throws JpegToolException {

        if (smallpicheight <= 0) {

            throw new JpegToolException(" 缩影图片的高度不能为 0 和负数！ ");

        }

        this.resetJpegToolParams();

        this.smallpicheight = smallpicheight;

        this.isInitFlag = true;

    }

    /**
     * 
     * 生成源图像的缩影图像
     * 
     * @param pic_big_pathfilename 源图像文件名，包含路径（如 windows 下 C:\\pic.jpg ； Linux 下
     *            /home/abner/pic/pic.jpg ）
     * 
     * @param pic_small_pathfilename 生成的缩影图像文件名，包含路径（如 windows 下
     *            C:\\pic_small.jpg ； Linux 下 /home/abner/pic/pic_small.jpg ）
     * 
     * @throws JpegToolException
     *  
     */

    public void doFinal(String pic_big_pathfilename, String pic_small_pathfilename) throws JpegToolException {

        if (!this.isInitFlag) {

            throw new JpegToolException(" 对象参数没有初始化！ ");

        }

        if (pic_big_pathfilename == null || pic_small_pathfilename == null) {

            throw new JpegToolException(" 包含文件名的路径为空！ ");

        }

        if ((!pic_big_pathfilename.toLowerCase().endsWith("jpg")) && (!pic_big_pathfilename.toLowerCase().endsWith("jpeg"))) {

            throw new JpegToolException(" 只能处理 JPG/JPEG 文件！ ");

        }

        if ((!pic_small_pathfilename.toLowerCase().endsWith("jpg")) && (!pic_small_pathfilename.toLowerCase().endsWith("jpeg"))) {

            throw new JpegToolException(" 只能处理 JPG/JPEG 文件！ ");

        }

        int smallw = 0;

        int smallh = 0;

        // 新建源图片和生成的小图片的文件对象

        File fi = new File(pic_big_pathfilename);

        File fo = new File(pic_small_pathfilename);

        // 生成图像变换对象

        AffineTransform transform = new AffineTransform();

        // 通过缓冲读入源图片文件

        BufferedImage bsrc = null;

        try {

            bsrc = ImageIO.read(fi);

        } catch (IOException ex) {

            throw new JpegToolException(" 读取源图像文件出错！ ");

        }

        int srcw = bsrc.getWidth();// 原图像的长度

        int srch = bsrc.getHeight();// 原图像的宽度

//        double scale = (double) srcw / srch;// 图像的长宽比例

        if (this.smallpicwidth != 0) {// 根据设定的宽度求出长度

            smallw = this.smallpicwidth;// 新生成的缩略图像的长度

            smallh = (smallw * srch) / srcw;// 新生成的缩略图像的宽度

        } else if (this.smallpicheight != 0) {// 根据设定的长度求出宽度

            smallh = this.smallpicheight;// 新生成的缩略图像的长度

            smallw = (smallh * srcw) / srch;// 新生成的缩略图像的宽度

        } else if (this.picscale != 0) {// 根据设置的缩小比例设置图像的长和宽

            smallw = (int) ((float) srcw * this.picscale);

            smallh = (int) ((float) srch * this.picscale);

        } else {

            throw new JpegToolException(" 对象参数初始化不正确！ ");

        }

        //log(" 源文件大小： " + srcw + "X" + srch + ", 新文件大小： " + smallw + "X" + smallh);


        double sx = (double) smallw / srcw;// 小 / 大图像的宽度比例

        double sy = (double) smallh / srch;// 小 / 大图像的高度比例

        transform.setToScale(sx, sy);// 设置图像转换的比例

        // 生成图像转换操作对象

        AffineTransformOp ato = new AffineTransformOp(transform, null);

        // 生成缩小图像的缓冲对象

        BufferedImage bsmall = new BufferedImage(smallw, smallh, BufferedImage.TYPE_3BYTE_BGR);

        // 生成小图像

        ato.filter(bsrc, bsmall);

        // 输出小图像

        try {

            ImageIO.write(bsmall, "jpeg", fo);

        } catch (IOException ex1) {

            throw new JpegToolException(" 写入缩略图像文件出错！ ");

        }
    }
    
    public static void log(String str) {
        System.out.println(str);
    }
    
    public static int[] getSize(String imagePathName) {
        File fi = new File(imagePathName);

        // 生成图像变换对象

//        AffineTransform transform = new AffineTransform();

        // 通过缓冲读入源图片文件

        BufferedImage bsrc = null;

        try {

            bsrc = ImageIO.read(fi);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int srcw = bsrc.getWidth();// 原图像的长度

        int srch = bsrc.getHeight();// 原图像的宽度
        return new int[]{srcw, srch};
    }

    public static void main(String[] args) {
        try {

            JpegTool jpegTool = new JpegTool();

            jpegTool.SetSmallWidth(100);

            jpegTool.doFinal("C:\\download\\china.jpg", "C:\\download\\china2.jpg");

        } catch (Exception e) {

            e.printStackTrace();
        
        }
    }
    

}

