package org.quickbundle.modules.message.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quickbundle.modules.message.IRmMessageConstants;
import org.quickbundle.modules.message.dao.RmMessageDao;
import org.quickbundle.modules.message.dao.RmMessageReceiverDao;
import org.quickbundle.modules.message.vo.RmMessageReceiverVo;
import org.quickbundle.modules.message.vo.RmMessageVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//默认将类中的所有public函数纳入事务管理
@Transactional(readOnly = true)
public class RmMessageService implements IRmMessageConstants {

	@Autowired
	private RmMessageDao rmMessageDao;
	
	@Autowired
	private RmMessageReceiverDao rmMessageReceiverDao;
	
    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public Long insert(RmMessageVo vo) {
        Long id = rmMessageDao.insert(vo);
        if(vo.getBody() != null) {
        	for(RmMessageReceiverVo bodyVo: vo.getBody()) {
        		bodyVo.setMessage_id(vo.getId());
        	}
        	rmMessageReceiverDao.insert(vo.getBody().toArray(new RmMessageReceiverVo[0]));
        }
        RmProjectHelper.log(LOG_TYPE_NAME, "插入了1条记录,id={},子记录{}条", id, vo.getBody() == null ? 0 : vo.getBody().size());
		return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public Long[] insert(RmMessageVo[] vos) {
        Long[] ids = rmMessageDao.insert(vos);
        List<RmMessageReceiverVo> bodyVoToInsert = new ArrayList<RmMessageReceiverVo>();
        for(RmMessageVo vo : vos) {
        	if(vo.getBody() != null) {
        		for(RmMessageReceiverVo bodyVo: vo.getBody()) {
        			bodyVo.setMessage_id(vo.getId());
        			bodyVoToInsert.add(bodyVo);
        		}
        	}
        }
        rmMessageReceiverDao.insert(bodyVoToInsert.toArray(new RmMessageReceiverVo[0]));
        RmProjectHelper.log(LOG_TYPE_NAME, "插入了{}条记录,id={},子记录共{}条", vos.length, Arrays.toString(ids), bodyVoToInsert.size());
        return ids;
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(Long id) {
    	List<Long> bodyIdToDelete = new ArrayList<Long>();
    	RmMessageVo vo = get(id);
    	if(vo.getBody() != null) {
    		for(RmMessageReceiverVo bodyVo : vo.getBody()) {
    			bodyIdToDelete.add(bodyVo.getId());
    		}
    	}
		rmMessageReceiverDao.delete(bodyIdToDelete.toArray(new Long[0]));
		int sum = rmMessageDao.delete(id);
		RmProjectHelper.log(LOG_TYPE_NAME, "删除了{}条记录,id={},子记录{}条", sum, id, bodyIdToDelete.size());
		return sum;
    }

    /**
     * 删除多条记录
     * 
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(Long ids[]) {
    	List<Long> bodyIdToDelete = new ArrayList<Long>();
    	for(Long id : ids) {
    		RmMessageVo vo = get(id);
    		if(vo.getBody() != null) {
    			for(RmMessageReceiverVo bodyVo : vo.getBody()) {
    				bodyIdToDelete.add(bodyVo.getId());
    			}
    		}
    	}
		rmMessageReceiverDao.delete(bodyIdToDelete.toArray(new Long[0]));
		int sum = rmMessageDao.delete(ids);
        RmProjectHelper.log(LOG_TYPE_NAME, "删除了{}条记录,ids={},子记录共{}条", sum, Arrays.toString(ids), bodyIdToDelete.size());
		return sum;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int update(RmMessageVo vo) {
    	if(vo.getBody() != null) {
    		List[] result = RmVoHelper.mergeVos(vo, TABLE_PK, rmMessageReceiverDao.list("message_id=" + vo.getId(), null, 1, Integer.MAX_VALUE, true), vo.getBody(), TABLE_PK_RM_MESSAGE_RECEIVER, "message_id");
    		rmMessageReceiverDao.insert((RmMessageReceiverVo[])result[0].toArray(new RmMessageReceiverVo[0]));
    		rmMessageReceiverDao.delete((Long[])result[1].toArray(new Long[0]));
    		rmMessageReceiverDao.update((RmMessageReceiverVo[])result[2].toArray(new RmMessageReceiverVo[0]));
    	}
		int sum = rmMessageDao.update(vo);
        RmProjectHelper.log(LOG_TYPE_NAME, "更新了{}条记录,id={}", sum, vo.getId());
		return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int update(RmMessageVo[] vos) {
    	List<RmMessageReceiverVo> toInsert = new ArrayList<RmMessageReceiverVo>();
    	List<Long> toDelete = new ArrayList<Long>();
    	List<RmMessageReceiverVo> toUpdate = new ArrayList<RmMessageReceiverVo>();
    	for(RmMessageVo vo : vos) {
    		if(vo.getBody() != null) {
        		List[] result = RmVoHelper.mergeVos(vo, TABLE_PK, rmMessageReceiverDao.list("message_id=" + vo.getId(), null, 1, Integer.MAX_VALUE, true), vo.getBody(), TABLE_PK_RM_MESSAGE_RECEIVER, "message_id");
        		toInsert.addAll(result[0]);
        		toDelete.addAll(result[1]);
        		toUpdate.addAll(result[2]);
    		}
    	}
		rmMessageReceiverDao.insert(toInsert.toArray(new RmMessageReceiverVo[0]));
		rmMessageReceiverDao.delete(toDelete.toArray(new Long[0]));
		rmMessageReceiverDao.update(toUpdate.toArray(new RmMessageReceiverVo[0]));
		int[] sum = rmMessageDao.update(vos);
		int finalSum = 0;
		for (int i = 0; i < sum.length; i++) {
			finalSum += sum[i];
		}
		RmProjectHelper.log(LOG_TYPE_NAME, "批量更新了{}条记录", finalSum);
		return finalSum;
	}

	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(RmMessageVo[] vos) {
		int[] sum_insert_update = new int[2];
		List<RmMessageVo> lInsert = new ArrayList<RmMessageVo>();
		List<RmMessageVo> lUpdate = new ArrayList<RmMessageVo>();
		for (int i = 0; i < vos.length; i++) {
			if(vos[i].getId() != null) {
				lUpdate.add(vos[i]);
			} else {
				lInsert.add(vos[i]);
			}
		}
		if(lInsert.size() > 0) {
			sum_insert_update[0] = insert(lInsert.toArray(new RmMessageVo[0])).length;
		}
		if(lUpdate.size() > 0) {
			sum_insert_update[1] = update(lUpdate.toArray(new RmMessageVo[0]));
		}
		return sum_insert_update;
	}

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmMessageVo get(Long id) {
		RmMessageVo vo = rmMessageDao.get(id);
		List<RmMessageReceiverVo> body = rmMessageReceiverDao.list("message_id=" + id, null, 1, Integer.MAX_VALUE, true);
		vo.setBody(body);
		return vo;
    }
    
    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getCount(String queryCondition) {
		int sum = rmMessageDao.getCount(queryCondition);
		return sum;
    }

    /**
     * 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<RmMessageVo> list(String queryCondition, String orderStr) {
        return list(queryCondition, orderStr, 1, Integer.MAX_VALUE);
    }

    /**
     * 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录
     * @return 查询到的VO列表
     */
    public List<RmMessageVo> list(String queryCondition, String orderStr, int startIndex, int size) {
        return list(queryCondition, orderStr, startIndex, size, false);
    }
    
    /**
     * 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，根据selectAllClumn判断是否查询所有字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录
     * @param allColumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
     * @return 查询到的VO列表
     */
    public List<RmMessageVo> list(String queryCondition, String orderStr, int startIndex, int size, boolean allColumn) {
        List<RmMessageVo> lResult = rmMessageDao.list(queryCondition, orderStr, startIndex, size, allColumn);
        if(allColumn) {
        	for(RmMessageVo vo : lResult) {
        		List<RmMessageReceiverVo> body = rmMessageReceiverDao.list("message_id=" + String.valueOf(vo.getId()), null, 1, Integer.MAX_VALUE, true);
        		vo.setBody(body);
        	}
        }
        return lResult;
    }
    
    /**
     * 按条件搜索，走MyBatis的XML文件的search
     * 
     * @param searchPara 搜索参数的Map
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录
     * @return 查询到的VO列表
     */
    public List<RmMessageVo> search(Map<String, Object> searchPara, String orderStr, int startIndex, int size) {
    	List<RmMessageVo> lResult = rmMessageDao.search(searchPara, orderStr, startIndex, size);
    	return lResult;
    }
    
    /**
     * 插入中间表RM_M_MESSAGE_USER数据
     * 
     * @param message_id
     * @param user_ids
     * @return 插入的user_id列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Long[] insertRm_m_message_user(Long message_id, Long[] user_ids) {
    	if(user_ids.length == 0 || (user_ids.length == 1 && String.valueOf(user_ids[0]).length() == 0)) {
    		return new Long[0];
    	}
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	List<String> lExistId = cs.query("select * from RM_M_MESSAGE_USER where MESSAGE_ID=" + message_id + " and USER_ID in(" + RmStringHelper.parseToString(user_ids) + ")", new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("USER_ID");
			}
		});
    	Set<Long> sUser_id = new HashSet<Long>();
    	for(Long user_id : user_ids) {
    		if(!lExistId.contains(user_id)) {
    			sUser_id.add(user_id);
    		}
    	}
    	if(sUser_id.size() > 0) {
    		Long[][] aaValue = new Long[sUser_id.size()][2];
        	int index = 0;
        	for (Long user_id : sUser_id) {
        		aaValue[index][0] = message_id;
    			aaValue[index][1] = user_id;
    			index ++;
    		}
        	cs.doUpdateBatch("insert into RM_M_MESSAGE_USER (MESSAGE_ID, USER_ID) values(?, ?)", aaValue);
    	}
        return sUser_id.toArray(new Long[0]);
    }
    
    /**
     * 删除中间表RM_M_MESSAGE_USER数据
     * 
     * @param message_id
     * @param user_ids
     * @return 删除的记录数
     */
    public int deleteRm_m_message_user(Long message_id, Long[] user_ids) {
    	IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
    	return cs.doUpdate("delete from RM_M_MESSAGE_USER where MESSAGE_ID=" + message_id + " and USER_ID in(" + RmStringHelper.parseToString(user_ids) + ")");
    }
}
