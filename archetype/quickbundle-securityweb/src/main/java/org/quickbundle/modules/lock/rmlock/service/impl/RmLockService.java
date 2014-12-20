//代码生成时,文件路径: D:/rc/svn/fm/code/cu-tm/src/main/java/modules/lock/rmlock/service/impl/RmLockService.java
//代码生成时,系统时间: 2010-11-30 22:19:58
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.lock.rmlock.service.impl --> RmLockService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-30 22:19:58 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.lock.rmlock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.quickbundle.base.service.RmService;
import org.quickbundle.modules.lock.rmlock.dao.IRmLockDao;
import org.quickbundle.modules.lock.rmlock.service.IRmLockService;
import org.quickbundle.modules.lock.rmlock.util.IRmLockConstants;
import org.quickbundle.modules.lock.rmlock.vo.RmLockVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.springframework.dao.DataIntegrityViolationException;
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

public class RmLockService extends RmService implements IRmLockService, IRmLockConstants {

	/**
	 * dao 表示: 数据访问层的实例
	 */
	private IRmLockDao dao = null;

	/**
	 * 设置数据访问接口
	 * 
	 * @return
	 */
	public IRmLockDao getDao() {
		return dao;
	}

	/**
	 * 获取数据访问接口
	 * 
	 * @param dao
	 */
	public void setDao(IRmLockDao dao) {
		this.dao = dao;
	}

	/**
	 * 插入单条记录
	 * 
	 * @param vo
	 *            用于添加的VO对象
	 * @return 若添加成功，返回新生成的Oid
	 */
	public String insert(RmLockVo vo) {
		String id = getDao().insert(vo);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条记录,id=" +
		// String.valueOf(id));
		RmSqlCountCache.clearCount(TABLE_NAME); // 清除count记录数缓存
		return id;
	}

	/**
	 * 插入多条记录
	 * 
	 * @param vos
	 *            用于添加的VO对象数组
	 * @return 返回新生成的id数组
	 */
	public String[] insert(RmLockVo[] vos) {
		String[] aId = getDao().insert(vos);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了" + vos.length +
		// "条记录,id=" + RmStringHelper.ArrayToString(aId, ","));
		RmSqlCountCache.clearCount(TABLE_NAME); // 清除count记录数缓存
		return aId;
	}

	/**
	 * 删除单条记录
	 * 
	 * @param id
	 *            用于删除的记录的id
	 * @return 成功删除的记录数
	 */
	public int delete(String id) {
		int sum = getDao().delete(id);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" +
		// String.valueOf(id));
		RmSqlCountCache.clearCount(TABLE_NAME); // 清除count记录数缓存
		return sum;
	}

	/**
	 * 删除多条记录
	 * 
	 * @param ids
	 *            用于删除的记录的ids
	 * @return 成功删除的记录数
	 */
	public int delete(String ids[]) {
		int sum = getDao().delete(ids);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" +
		// RmStringHelper.ArrayToString(ids, ","));
		RmSqlCountCache.clearCount(TABLE_NAME); // 清除count记录数缓存
		return sum;
	}

	/**
	 * 根据Id进行查询
	 * 
	 * @param id
	 *            用于查找的id
	 * @return 查询到的VO对象
	 */
	public RmLockVo find(String id) {
		RmLockVo vo = getDao().find(id);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
	}

	/**
	 * 更新单条记录
	 * 
	 * @param vo
	 *            用于更新的VO对象
	 * @return 成功更新的记录数
	 */
	public int update(RmLockVo vo) {
		int sum = getDao().update(vo);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" +
		// String.valueOf(vo.getId()));
		RmSqlCountCache.clearCount(TABLE_NAME); // 清除count记录数缓存
		return sum;
	}

	/**
	 * 批量更新修改多条记录
	 * 
	 * @param vos
	 *            更新的VO对象数组
	 * @return 成功更新的记录最终数量
	 */
	public int update(RmLockVo[] vos) {
		int[] sum = getDao().update(vos);
		int finalSum = 0;
		for (int i = 0; i < sum.length; i++) {
			finalSum += sum[i];
		}
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "批量更新了" + finalSum + "条记录);
		RmSqlCountCache.clearCount(TABLE_NAME); // 清除count记录数缓存
		return finalSum;
	}

	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos
	 *            更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}
	 */
	public int[] insertUpdateBatch(RmLockVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmLockVo> lInsert = new ArrayList<RmLockVo>();
		List<RmLockVo> lUpdate = new ArrayList<RmLockVo>();
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getId() != null && vos[i].getId().trim().length() > 0) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if (lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmLockVo[0])).length;
		}
		if (lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmLockVo[0]));
		}
		return sum_insert_update;
	}

	/**
	 * 查询总记录数，带查询条件
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return 总记录数
	 */
	public int getRecordCount(String queryCondition) {
		int sum = getDao().getRecordCount(queryCondition);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "查询到了总记录数,count=" + sum +
		// ", queryCondition=" + queryCondition);
		return sum;
	}

	/**
	 * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
	 * 
	 * @param queryCondition
	 *            查询条件, queryCondition等于null或""时查询全部
	 * @param orderStr
	 *            排序字段
	 * @return 查询到的VO列表
	 */
	public List<RmLockVo> queryByCondition(String queryCondition, String orderStr) {
		return queryByCondition(queryCondition, orderStr, -1, -1);
	}

	/**
	 * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，只查询必要的字段
	 * 
	 * @param queryCondition
	 *            查询条件, queryCondition等于null或""时查询全部
	 * @param orderStr
	 *            排序字符
	 * @param startIndex
	 *            开始位置(第一条是1，第二条是2...)
	 * @param size
	 *            查询多少条记录(size小于等于0时,忽略翻页查询全部)
	 * @return 查询到的VO列表
	 */
	public List<RmLockVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size) {
		return queryByCondition(queryCondition, orderStr, startIndex, size, false);
	}

	/**
	 * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，根据selectAllClumn判断是否查询所有字段
	 * 
	 * @param queryCondition
	 *            查询条件, queryCondition等于null或""时查询全部
	 * @param orderStr
	 *            排序字符
	 * @param startIndex
	 *            开始位置(第一条是1，第二条是2...)
	 * @param size
	 *            查询多少条记录(size小于等于0时,忽略翻页查询全部)
	 * @param selectAllClumn
	 *            是否查询所有列，即 SELECT * FROM ...(适用于导出)
	 * @return 查询到的VO列表
	 */
	public List<RmLockVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
		List<RmLockVo> lResult = getDao().queryByCondition(queryCondition, orderStr, startIndex, size, selectAllClumn);
		// RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" +
		// lResult.size() + ", startIndex=" + startIndex + ", size=" + size +
		// ", queryCondition=" + queryCondition + ", orderStr=" + orderStr +
		// ", selectAllClumn=" + selectAllClumn);
		return lResult;
	}

	/**
	 * 删除业务锁
	 * 
	 * @param user_id
	 * @param value
	 * @param bs_keyword
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int deleteLock(String user_id, String value, String bs_keyword) {
		return RmProjectHelper.getCommonServiceInstance().doUpdate("delete from RM_LOCK where USER_ID=? and BS_KEYWORD=? and LOCK_CONTENT=?",
				new String[] { user_id, bs_keyword, value });
	}

	/**
	 * 执行获得业务锁
	 * 
	 * @param vo
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean executeLock(RmLockVo vo) {
		try {
			insert(vo);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().indexOf("IDXU_LOCK_BK_CONTENT") != -1) {
				if (vo.getUser_id() != null && vo.getUser_id().length() > 0) {
					// 指定的user_id可以重复获得自己曾经拥有的业务锁
					int sum = RmProjectHelper.getCommonServiceInstance().queryForObject(
							"select count(ID) from RM_LOCK where USER_ID=? and BS_KEYWORD=? and LOCK_CONTENT=?",
							new String[] { vo.getUser_id(), vo.getBs_keyword(), vo.getLock_content() }, Integer.class);
					if (sum > 0) {
						return true;
					}
				}
				return false;
			} else {
				throw e;
			}
		}
		return true;
	}
}