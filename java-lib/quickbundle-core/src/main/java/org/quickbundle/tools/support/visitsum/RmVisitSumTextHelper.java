/*
 * 系统名称: 
 * 
 * 文件名称: org.quickbundle.tools.helper --> RmVisitSumTextHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-4-17 22:06:24 创建1.0.0版 (Administrator)
 * 
 */
package org.quickbundle.tools.support.visitsum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.quickbundle.tools.support.path.RmPathHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmVisitSumTextHelper {

    /**
     * 构造函数:
     * 
     */
    public RmVisitSumTextHelper() {
    }

    /**
     * visitSum 表示: 访问量
     */
    private static long visitSum = 0;

    private static int step = 0;
    
    private static int maxSessionSum = 0;
    
    private static Set<String> sSessionId = null;
    
    private static List<String> lSessionId = null;
    
    private static String visitSumPropertyPath = RmPathHelper.getWebInfDir() + File.separator + "config" + File.separator + "rm" + File.separator + "visitSum.properties";

    /**
     * isInit 表示: 标识是否被初始化
     */
    private static boolean isInit = false;

    /**
     * 功能: 初始化访问量
     * 
     * @param request
     */
    private static synchronized void initData() {
        try {
            Properties properties = new Properties();
            // 文件位置:当前package
            properties.load(new FileInputStream(visitSumPropertyPath));
            visitSum = Long.parseLong(properties.getProperty("visitSum"));
            step = Integer.parseInt(properties.getProperty("step"));
            maxSessionSum = Integer.parseInt(properties.getProperty("maxSessionSum"));
            sSessionId = new HashSet<String>();
            lSessionId = new ArrayList<String>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 保存访问量
     * 
     * @param request
     */
    private static synchronized void saveData() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(visitSumPropertyPath));
            properties.setProperty("visitSum", String.valueOf(visitSum));
            properties.setProperty("step", String.valueOf(step));
            OutputStream os = new FileOutputStream(visitSumPropertyPath);
            properties.store(os, new Timestamp(System.currentTimeMillis()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 计数器加1
     * 
     * @param request
     */
    public static long increaseVisitSum() {
        if (!isInit) {
            initData();
            isInit = true;
        }
        visitSum++;
        if (step > 0 && visitSum % step == 0) {
            try {
                saveData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return visitSum;
    }
    
    /**
     * 功能: 对Session判断
     *
     * @param request
     */
    public static long increaseVisitSum(HttpServletRequest request) {
        try {
            if (!isInit) {
                initData();
                isInit = true;
            }
            if(!sSessionId.contains(request.getSession(true).getId())) {
                if(sSessionId.size() >= maxSessionSum) {
                    sSessionId.remove(lSessionId.get(0));
                    lSessionId.remove(0);
                }
                sSessionId.add(request.getSession(true).getId());
                lSessionId.add(request.getSession(true).getId());
                visitSum++;
                if (step > 0 && visitSum % step == 0) {
                    try {
                        saveData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }

        return visitSum;
    }

    public static void main(String[] args) {
        System.out.println(increaseVisitSum());
        for (int i = 0; i < 50; i++) {
            increaseVisitSum();
            
        }
    }

}
