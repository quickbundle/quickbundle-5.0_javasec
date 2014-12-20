//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmparty/service/IRmPartyService.java
//代码生成时,系统时间: 2010-11-28 17:40:30
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmparty.service --> IRmPartyService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:30 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmparty.service;

import java.util.List;

import org.quickbundle.orgauth.rmparty.dao.IRmPartyDao;
import org.quickbundle.orgauth.rmparty.vo.RmPartyVo;
import org.quickbundle.orgauth.rmpartyrelation.vo.RmPartyRelationVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public interface IRmPartyService {

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmPartyDao getDao();

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmPartyDao dao);

    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的id
     */
    public String insert(RmPartyVo vo);
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmPartyVo[] vos);

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id);

    /**
     * 删除多条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(String id[]);

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public RmPartyVo find(String id);

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmPartyVo vo);

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(RmPartyVo[] vos);
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(RmPartyVo[] vos);

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getRecordCount(String queryCondition);
    
    /**
     * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<RmPartyVo> queryByCondition(String queryCondition, String orderStr);
    
    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return 查询到的VO列表
     */
    public List<RmPartyVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size);

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
    public List<RmPartyVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn);

    /**
     * 插入中间表RM_AUTHORIZE_RESOURCE_RECORD数据
     * 
     * @param party_id
     * @param authorize_resource_ids
     * @return 插入的authorize_resource_id列表
     */
    public String[] insertRm_authorize_resource_record(String bs_keyword, String party_id, String[] old_resource_ids);
    
    /**
     * 功能: 删除中间表RM_AUTHORIZE_RESOURCE_RECORD数据
     * 
     * @param party_id
     * @param authorize_resource_ids
     * @return 删除的记录数
     */
    public int deleteRm_authorize_resource_record(String party_id, String[] authorize_resource_ids);
	

    /**
     * 保存团体和团体关系
     * @param vo 团体关系ID
     * @param parentPartyIds
     * @param isInherit 
     * @return
     */
    public String insertParty(RmPartyRelationVo vo,String[] parentPartyIds,String [] parentPartyNames ,String is_inherit);
    
    /**
     * 更新团体关系
     * @param id
     * @param pratyViewId
     * @param name
     * @param partyTypeId
     * @param parentPartyIds
     * @param isInherit
     * @return
     */
    public String updateParty (String id , String pratyViewId,String oldName,String name,String partyTypeId,String[] parentPartyIds ,String[] parentPartyNames,String[] oldParentPartyIds,String isInherit);
    /**
     * 删除团体和团体关系记录(单条删除)
     * @param id
     * @param childPartyId
     * @param childPartyCode
     * @param parentPartyCode
     * @param view_id
     * @param delesql 扩展删除语句
     * @return
     */
    public String deleteParty(String childPartyId,String childPartyCode,String parentPartyCode,String view_id,String delesql);
    /**
     * 只更新团体和团体关系名称
     * @param id 团体ID
     * @param name 名称
     * @param view_id 视图ID，如视图等于null，就更新所有视图下的团体关系
     */
    public int[] updatePartyName(String id , String name , String view_id);
    
    /**
     * 删除团体和所有视图下的团体关系
     * @param ids
     * @return
     */
    public int deleteParty(String[] ids);
}