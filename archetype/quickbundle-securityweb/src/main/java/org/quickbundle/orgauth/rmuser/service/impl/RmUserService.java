//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmuser/service/impl/RmUserService.java
//代码生成时,系统时间: 2010-11-27 22:08:37
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmuser.service.impl --> RmUserService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:37 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmuser.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.orgauth.rmuser.dao.IRmUserDao;
import org.quickbundle.orgauth.rmuser.service.IRmUserService;
import org.quickbundle.orgauth.rmuser.util.IRmUserConstants;
import org.quickbundle.orgauth.rmuser.vo.RmUserVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.mail.IRmMailService;
import org.quickbundle.project.secure.RmCryptoHelper;
import org.quickbundle.tools.support.mail.SendPassword;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmUserService extends RmService implements IRmUserService, IRmUserConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmUserDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmUserDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmUserDao dao) {
        this.dao = dao;
    }


    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmUserVo vo) {
		//String passwordEncrypt = RmProjectHelper.encryptPassword(vo.getPassword(), vo.getLogin_id());
		//vo.setPassword(passwordEncrypt);
        String id = getDao().insert(vo);
        RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入1条用户记录,id=" + String.valueOf(id)+",login_id="+vo.getLogin_id()+",用户名="+vo.getName());
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmUserVo[] vos) {
        String[] aId = getDao().insert(vos);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了" + vos.length + "条记录,id=" + RmStringHelper.ArrayToString(aId, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
        return aId;
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id) {
		int sum = getDao().delete(id);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + String.valueOf(id));
		RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 删除多条记录
     * 
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(String ids[]) {
		int sum = getDao().delete(ids);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmUserVo find(String id) {
		RmUserVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmUserVo vo) {
    	if(vo.getPassword() == null || vo.getPassword().length() == 0) {
    		RmUserVo oldVo = find(vo.getId());
    		vo.setPassword(oldVo.getPassword());
    	} else {
    		//String passwordEncrypt = RmProjectHelper.encryptPassword(vo.getPassword(), vo.getLogin_id());
    		//vo.setPassword(passwordEncrypt);
    	}
		int sum = getDao().update(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" + String.valueOf(vo.getId()));
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(RmUserVo[] vos) {
		int[] sum = getDao().update(vos);
		int finalSum = 0;
		for (int i = 0; i < sum.length; i++) {
			finalSum += sum[i];
		}
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "批量更新了" + finalSum + "条记录);
        RmSqlCountCache.clearCount(TABLE_NAME);  //清除count记录数缓存
		return finalSum;
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(RmUserVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmUserVo> lInsert = new ArrayList<RmUserVo>();
		List<RmUserVo> lUpdate = new ArrayList<RmUserVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmUserVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmUserVo[0]));
		}
		return sum_insert_update;
	}

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getRecordCount(String queryCondition) {
		int sum = getDao().getRecordCount(queryCondition);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "查询到了总记录数,count=" + sum + ", queryCondition=" + queryCondition);
		return sum;
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<RmUserVo> queryByCondition(String queryCondition, String orderStr) {
        return queryByCondition(queryCondition, orderStr, -1, -1);
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return 查询到的VO列表
     */
    public List<RmUserVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
        return queryByCondition(queryCondition, orderStr, startIndex, size, false);
    }
    
    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，根据selectAllClumn判断是否查询所有字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param selectAllClumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
     * @return 查询到的VO列表
     */
    public List<RmUserVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmUserVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }
    
    
    
    /**
     * 密码校验
     * @param s
     * @return
     */
    public String validatePassword(String password){
    	if(password == null || password.length()==0) {
    		return "密码不能为空";
    	}
    	if(password.length() < 6) {
    		return "密码长度不够";
    	}
    	if(!password.matches(".*[a-zA-Z0-9].*[a-zA-Z0-9].*")) {
    		return "密码应该包含两个以上字母或数字";
    	}
    	if(password.matches("^(!|\\?).*")) {
    		return "密码不能以!或?开头";
    	}
    	return null;
    }
    
    /**
     * 密码重置
     * @param vo
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void executeInitBeforeSendMail(RmUserVo vo) {
    	if(vo.getCustom5() != null && vo.getCustom5().indexOf("sent_mail")>-1) {
    		return;
    	}
    	if(vo.getEmail() == null || "".equals(vo.getEmail())){
    		return;
    	}
    	String password = SendPassword.getRandomPassword(6);
    	IRmMailService ms = (IRmMailService)RmBeanFactory.getBean("IRmMailService");
    	//加密
    	String md5Password = RmCryptoHelper.digestPassword(password, vo.getId());
    	vo.setPassword(md5Password);
    	vo.setCustom5("sent_mail");
    	update(vo);
		//System.out.println("email="+RmStringHelper.prt(vo.getEmail()));
		String sendEmail = vo.getEmail();
		String ts = new Timestamp(System.currentTimeMillis()).toString();
		String seanData = ts.substring(0,4)+"年"+ts.substring(5,7)+"月"+ts.substring(8,10)+"日";
		String content = "";
		//ms.send(sendEmail, "系统用户登录名和和初始密码说明", null ,content, null);
    }
    
	public boolean executeChangePassword(String id, String password) {
		RmUserVo vo = find(id);
		String passwordEncrypt = RmCryptoHelper.digestPassword(password, vo.getLogin_id());
		RmProjectHelper.getCommonServiceInstance().doUpdate("UPDATE RM_USER SET PASSWORD=? WHERE ID=?", new String[]{passwordEncrypt, id});
		RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "修改了密码,id=" + id + ",用户名=" + vo.getLogin_id());
		return true;
	}
    
	/**
	 * 充值安全密码
	 * 
	 * @param id
	 * @return
	 */
	public int updatePassword(String id) {
		RmUserVo vo = find(id);
		RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "生成安全密码,id=" + String.valueOf(id) + ",用户名="+vo.getLogin_id()+",姓名="+vo.getName() + ",email=" + vo.getEmail());
		String newPassword = getRandomPassword(DEFAULT_PASSWORD_LENGTH);
		executeChangePassword(vo.getId(), newPassword);
		if(vo.getEmail() != null && vo.getEmail().trim().length() > 0){
			StringBuilder body = new StringBuilder();
			body.append(vo.getName());
			body.append("：");
			body.append("<br/><br/>\n\nCRM系统升级了帐号信息。您的用户名是");
			body.append(vo.getLogin_id());
			body.append("，");
			body.append("密码是");
			body.append(newPassword);
			body.append("，请保管好您的帐号信息！");

			body.append("<br/>\n这是程序自动发送的邮件，请不要回复此邮箱！");
//			IRmMailService ms = (IRmMailService)RmBeanFactory.getBean("IRmMailService");
//			ms.send(vo.getEmail(), "CRM系统帐号信息升级通知", null, body.toString(), null);
		}
		return 1;
	}
	
	private final static int DEFAULT_PASSWORD_LENGTH = 6;
	private final static String passwordSet = "abcdefghijklmnopqrstuvwxyz0123456789";
	/**
	 * 得到指定位数的随机密码，小写字母+数字
	 * 
	 * @param length
	 * @return
	 */
	 String getRandomPassword(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(passwordSet.charAt((int) (Math.random() * passwordSet.length())));
		}
		return sb.toString();
	}
}