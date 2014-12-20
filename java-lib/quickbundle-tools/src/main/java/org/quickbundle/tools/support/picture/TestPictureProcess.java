/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.picture --> TestPictureProcess.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-2-15 15:44:18 创建1.0.0版 (user)
 * 
 */
package org.quickbundle.tools.support.picture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;


/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class TestPictureProcess {
    /**
     * 功能: 往图片插入文字
     * 
     *  
     */
    public static void insertTextIntoImage() {
        try {
            //读取模板图片内容
            BufferedImage image = ImageIO.read(new FileInputStream("c:\\download\\china.jpg"));
            Graphics2D g = image.createGraphics();//得到图形上下文
            g.setColor(Color.BLACK); //设置画笔颜色
            //设置字体
            g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 15));//写入签名
            g.drawString("很好吃诶，要不要也来一口？", 43, image.getHeight() - 10);
            g.dispose();
            FileOutputStream out = new FileOutputStream("c:\\download\\chinaText.jpg");
            ImageIO.write(image, "jpg", out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能: 往图片插入文字和图片（比如企业logo）
     * 
     *  
     */
    public static void insertTextIntoImage2() {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream("c:\\download\\china.jpg"));
            //读取图标
            BufferedImage image_biao = ImageIO.read(new FileInputStream("c:\\download\\logo.jpg"));
            Graphics2D g = image.createGraphics();
            g.setColor(Color.YELLOW);
            g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 25));
            g.drawString("很好吃诶，要不要也来一口？", 43, image.getHeight() - 80);
            //写入图标
            g.drawImage(image_biao, 20, image.getHeight() - 80, image_biao.getWidth(null), image_biao.getHeight(null), null);
            g.dispose();
            FileOutputStream out = new FileOutputStream("c:\\download\\chinaText2.jpg");
            ImageIO.write(image, "jpg", out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * 功能: 往图片插入文字和图片（比如企业logo）
     * 
     *  
     */
    public static void insertTextIntoImage2(String file1) {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(file1));
            //读取图标
            BufferedImage image_biao = ImageIO.read(new FileInputStream("c:\\download\\logo.jpg"));
            Graphics2D g = image.createGraphics();
            g.setColor(Color.YELLOW);
            g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 25));
            g.drawString("刘丽丽版权所有", 43, image.getHeight() - 80);
            //写入图标
            g.drawImage(image_biao, 20, image.getHeight() - 80, image_biao.getWidth(null), image_biao.getHeight(null), null);
            g.dispose();
            FileOutputStream out = new FileOutputStream(new File(file1).getParentFile().toString() + "\\2-" + new File(file1).getName());
            ImageIO.write(image, "jpg", out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 功能: 把多个jpg组合成gif
     * 
     *  
     */
    public static void createGif() {
        try {
            //指定Frame的文件
            String imgFileName[] = new String[] { "c:\\download\\1.jpg", "c:\\download\\2.jpg", "c:\\download\\3.jpg", "c:\\download\\4.jpg" };
            String outputFileName = "c:\\download\\test250.gif";
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.start(outputFileName);//开始处理
            e.setDelay(100); //设置延迟时间
            for (int i = 0; i < imgFileName.length; i++) {
                e.addFrame(ImageIO.read(new FileInputStream(imgFileName[i])));//加入Frame
            }
            e.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 把一个gif分拆为多个jpg
     * 
     *  
     */
    public static void decomposeGif() {
        try {
            String inputFileName = "c:\\download\\test.gif";
            GifDecoder decoder = new GifDecoder();
            decoder.read(inputFileName);
            int n = decoder.getFrameCount();//得到frame的个数
            for (int i = 0; i < n; i++) {
                BufferedImage frame = decoder.getFrame(i); //得到frame
//                int delay = decoder.getDelay(i);//得到延迟时间
                //生成JPG文件
                String outFilePath = "c:\\download\\test_" + i + ".jpg";
                FileOutputStream out = new FileOutputStream(outFilePath);
                ImageIO.write(frame, "jpeg", out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 把一个psd分拆为多个jpg
     * 
     *  
     */
    public static void decomposePsd() {
        try {
            PSDReader r = new PSDReader();
            r.read("c:\\download\\testPsd.psd");
            int n = r.getFrameCount();
            for (int i = 0; i < n; i++) {
                BufferedImage image = r.getLayer(i);
//                Point offset = r.getLayerOffset(i);
                //生成JPG文件
                String outFilePath = "c:\\download\\testPsd_" + i + ".jpg";
                FileOutputStream out = new FileOutputStream(outFilePath);
                ImageIO.write(image, "jpg", out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        insertTextIntoImage2("c:\\download\\航母.jpg");
    }
}