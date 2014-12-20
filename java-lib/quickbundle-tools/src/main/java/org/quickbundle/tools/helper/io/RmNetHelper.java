/*
 * 系统名称:基于冉闵开发工具 --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.helper --> RmNetHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2006-12-6 15:48:52 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.tools.helper.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmNetHelper {
    /**
     * 功能: 下载urlPath到file中
     * 
     * @param urlPath
     * @param file
     * @return
     * @throws MalformedURLException
     */
    public static long downloadFileFromUrl(String urlPath, File file) {
        long size = 0;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
            BufferedInputStream bufferedinputstream = new BufferedInputStream(httpurlconnection.getInputStream());
            BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file));
            int i;
            while ((i = bufferedinputstream.read()) != -1) {
                bufferedoutputstream.write(i);
            }
            bufferedinputstream.close();
            bufferedoutputstream.close();
            httpurlconnection.disconnect();
            size = file.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static void main(String[] args) {
        //        System.out
        //                .println(downloadFileFromUrl(
        //                        "http://10.48.57.43:9082/eip/jsp/frame/official/flow/downloadPhFile.jsp?downloadSaveName=2006/2006-12/11653753231419909356.txt&downloadRealName=taa",
        //                        new File("e:\\download\\testabc.txt")));
        System.out
        .println(downloadFileFromUrl(
                "http://down.sandai.net:8080/Thunder5.5.2.252.exe",
                new File("e:\\download\\Thunder5.5.2.252.exe")));
        
    }
}