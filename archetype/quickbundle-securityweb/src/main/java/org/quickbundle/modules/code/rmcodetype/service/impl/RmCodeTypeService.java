//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodetype/service/impl/RmCodeTypeService.java
//代码生成时,系统时间: 2010-04-08 21:15:45.734
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodetype.service.impl --> RmCodeTypeService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:45.734 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodetype.service.impl;

import java.util.List;

import org.quickbundle.base.service.RmService;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.modules.code.rmcodedata.service.IRmCodeDataService;
import org.quickbundle.modules.code.rmcodetype.dao.IRmCodeTypeDao;
import org.quickbundle.modules.code.rmcodetype.service.IRmCodeTypeService;
import org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants;
import org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo;
import org.quickbundle.tools.helper.RmStringHelper;

import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;
import org.quickbundle.orgauth.util.RmOrgAuthSqlHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmCodeTypeService extends RmService implements IRmCodeTypeService, IRmCodeTypeConstants {
    
    /**
     * dao 表示: 数据访问层的实例
     */
    private IRmCodeTypeDao dao = null;

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmCodeTypeDao getDao() {
        return dao;
    }

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmCodeTypeDao dao) {
        this.dao = dao;
    }

    private IRmCodeDataService codeDataService = null;
    public IRmCodeDataService getCodeDataService() {
		return codeDataService;
	}
	public void setCodeDataService(IRmCodeDataService codeDataService) {
		this.codeDataService = codeDataService;
	}

	/**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public String insert(RmCodeTypeVo vo) {
        String id = getDao().insert(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条记录,id=" + String.valueOf(id));
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmCodeTypeVo[] vos) {
        String[] aId = new String[vos.length];
        for (int i = 0; i < vos.length; i++) {
            aId[i] = insert(vos[i]);
        }
        return aId;
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id) {
    	RmProjectHelper.getCommonServiceInstance().doUpdate("delete from RM_CODE_DATA where CODE_TYPE_ID='" + id + "'");
		int sum = getDao().delete(id);
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + String.valueOf(id));
		return sum;
    }

    /**
     * 删除多条记录
     * 
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(String ids[]) {
    	RmProjectHelper.getCommonServiceInstance().doUpdate("delete from RM_CODE_DATA where CODE_TYPE_ID in(" + RmStringHelper.parseToSQLStringApos(ids) + ")");
		int sum = getDao().delete(ids);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
		return sum;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmCodeTypeVo find(String id) {
		RmCodeTypeVo vo = getDao().find(id);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmCodeTypeVo vo) {
		int sum = getDao().update(vo);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" + String.valueOf(vo.getId()));
		return sum;
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
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr) {
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
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
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
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
        List<RmCodeTypeVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return lResult;
    }

    /**
     * 通用的方法，执行更新，返回更新的记录条数
     *
     * @param strsql 要执行的sql语句
     * @return 更新记录条数
     */
    public int doUpdate(String strsql) {
        int sum = getDao().doUpdate(strsql);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "执行一条update语句" + strsql + "，影响行数count=" + sum);
        return sum;
    }

    /**
     * 集成了数据权限后，查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param party_ids party_id的集合(包含了用户、角色等)
     * @param authorize 权限类别对象
     * @return
     */
	public int getRecordCount(String queryCondition, RmAuthorizeVo authorize, String[] party_ids) {
		String sql = RmOrgAuthSqlHelper.getSqlJoinAuthorize(authorize, party_ids, "count(*)", IRmCodeTypeConstants.TABLE_NAME, queryCondition);
		return RmProjectHelper.getCommonServiceInstance().doQueryForInt(sql);
	}

    /**
     * 集成了数据权限后，通过查询条件获得所有的VO对象列表，带翻页，带排序字符
     * 
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param party_ids party_id的集合(包含了用户、角色等)
     * @param authorize 权限类别对象
     * @return
     */
	public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, RmAuthorizeVo authorize, String[] party_ids) {
		if(queryCondition == null) {
			queryCondition = "";
		}
		if(orderStr != null) {
			queryCondition += ORDER_BY_SYMBOL + orderStr;
		}
		String sqlSelect = IRmCodeTypeConstants.AFTER_SELECT_SHORT;
		String sql = RmOrgAuthSqlHelper.getSqlJoinAuthorize(authorize, party_ids, sqlSelect, IRmCodeTypeConstants.TABLE_NAME, queryCondition);
		return getDao().queryByCondition(sql, startIndex, size);
	}

}
