/*
 * 系统名称:quickbundle.org --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.tools.support.reptile --> RmReptileHandler.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2008-6-22 上午08:27:59 创建1.0.0版 (qb)
 * 
 */
package org.quickbundle.tools.support.robot;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import org.quickbundle.tools.support.transpage2htm.HttpFileSuckerException;

public class RmRobotHandler {

    public static Set sLink = null;

    public static String get(String urlStr, String encode) {
        URL url = null;
        Object obj = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            httpurlconnection.setInstanceFollowRedirects(false);
            httpurlconnection.connect();
            
            BufferedInputStream bufferedinputstream = new BufferedInputStream(httpurlconnection.getInputStream());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(baos);
            int i;
            while ((i = bufferedinputstream.read()) != -1)
                bufferedoutputstream.write(i);
            bufferedinputstream.close();
            bufferedoutputstream.close();
            httpurlconnection.disconnect();
            obj = baos.toString(encode);
        } catch (IOException _ex) {
            try {
                HttpURLConnection httpurlconnection1 = (HttpURLConnection) url.openConnection();
                httpurlconnection1.setRequestMethod("HEAD");
                httpurlconnection1.connect();
                HttpFileSuckerException httpfilesuckerexception = new HttpFileSuckerException(httpurlconnection1.getResponseMessage());
                httpurlconnection1.disconnect();
                throw httpfilesuckerexception;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if(obj == null) {
            return null;
        } else {
            return obj.toString();            
        }
    }


}
