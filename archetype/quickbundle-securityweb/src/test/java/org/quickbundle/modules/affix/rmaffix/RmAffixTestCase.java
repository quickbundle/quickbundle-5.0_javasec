//代码生成时,文件路径: E:/platform/myProject/navinfo/code/nifl/src/main/java/org/quickbundle/modules/affix/rmaffix/util/testcase/RmAffixTestCase.java
//代码生成时,系统时间: 2010-07-26 01:03:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> nifl
 * 
 * 文件名称: org.quickbundle.modules.affix.rmaffix.util.testcase --> RmAffixTestCase.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-07-26 01:03:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.affix.rmaffix;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.modules.affix.rmaffix.service.IRmAffixService;
import org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants;
import org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.mail.IRmMailService;
import org.quickbundle.project.test.RmWebTestCase;
import org.quickbundle.tools.helper.RmVoHelper;
import org.springframework.jdbc.core.RowMapper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAffixTestCase extends RmWebTestCase implements IRmAffixConstants {
    
    /**
     * 得到BS对象
     * 
     * @return BS对象
     */
    private IRmAffixService getService() {
        return (IRmAffixService) RmBeanFactory.getBean(SERVICE_KEY); //得到Service对象,受事务控制
    }

    /**
     * 插入单条记录
     */
    public void insert() {
        RmAffixVo vo = new RmAffixVo();
        RmVoHelper.objectToString(getService().insert(vo));
    }

    /**
     * 删除单条记录，删除多条记录
     */
    public void delete() {
        RmVoHelper.objectToString(getService().delete("1000100000000000001"));
        RmVoHelper.objectToString(getService().delete(new String[] { "1000100000000000001" }));
    }

    /**
     * 根据Id进行查询
     */
    public void find() {
        RmVoHelper.objectToString(getService().find("1000100000000000001"));
    }

    /**
     * 更新单条记录
     */
    public void update() {
        RmAffixVo vo = new RmAffixVo();
        RmVoHelper.objectToString(getService().update(vo));
    }

    /**
     * 查询总记录数
     */
    public void getRecordCount() {
        RmVoHelper.objectToString(getService().getRecordCount(" 1=1 "));
    }

    /**
     * 通过查询条件获得所有的VO对象列表
     */
    public void queryByCondition() {
        RmVoHelper.objectToString(getService().queryByCondition(" 1=1 ", null));
        RmVoHelper.objectToString(getService().queryByCondition(" 1=1 ", DEFAULT_ORDER_BY_CODE, 1, 10 ));
    }
    
    
    public static void main2(String[] args) {
	    RmWebTestCase.init();
	    final IRmAffixService service = ((IRmAffixService)RmBeanFactory.getBean(IRmAffixConstants.SERVICE_KEY));
	    final String[] ids= (String[])RmProjectHelper.getCommonServiceInstance().query("select id from rm_affix", new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}
		}).toArray(new String[0]);
	    final int c = 10000;

	    new Thread() {
	    	public void run() {
	    		long startTime = System.currentTimeMillis();
	    		System.out.println(startTime);
	    		for (int i = 0; i < c; i++) {
	    	    	int count = getRandomInt(ids.length);
	    	    	List<String> lId = new ArrayList<String>();
	    	    	for (int j = 0; j < count; j++) {
	    	    		lId.add(ids[getRandomInt(ids.length)]);
					}
	    	    	service.readonly_inserthello((String[])lId.toArray(new String[0]));		    			
	    		}

	    	    System.out.println("*******inserthello_readonly 耗时:" + (System.currentTimeMillis() - startTime));
	    	};
	    }.start();
	    
	    new Thread() {
	    	public void run() {
	    	    long startTime = System.currentTimeMillis();
	    	    System.out.println(startTime);
	    	    for (int i = 0; i < c; i++) {
	    	    	int count = getRandomInt(ids.length);
	    	    	List<String> lId = new ArrayList<String>();
	    	    	for (int j = 0; j < count; j++) {
	    	    		lId.add(ids[getRandomInt(ids.length)]);
					}
	    	    	service.inserthello((String[])lId.toArray(new String[0]));			
	    		}
	    	    System.out.println("*******inserthello 耗时:" + (System.currentTimeMillis() - startTime));
	    	};
	    }.start();
	}
    
    public static void main(String[] args) {
		RmWebTestCase.init();
		IRmMailService ms = (IRmMailService)RmBeanFactory.getBean("IRmMailService");
		ms.send("hi@helloworld.com", "hello", System.currentTimeMillis() + " world", null, null);
	}
    
    
    /**
     * 功能: 得到0-maxValue之间的随机整数
     *
     * @param maxValue
     * @return
     */
    private static int getRandomInt(int maxValue) {
        Random random = new Random();
        int a = random.nextInt(maxValue);
        if(a == 0) {
        	a ++;
        }
        return a;
    }
}