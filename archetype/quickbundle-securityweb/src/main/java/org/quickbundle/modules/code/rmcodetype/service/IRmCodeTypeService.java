//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodetype/service/IRmCodeTypeService.java
//代码生成时,系统时间: 2010-04-08 21:15:45.781
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodetype.service --> IRmCodeTypeService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:45.781 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodetype.service;

import java.util.List;

import org.quickbundle.modules.code.rmcodetype.dao.IRmCodeTypeDao;
import org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo;

import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public interface IRmCodeTypeService {

    /**
     * 设置数据访问接口
     * 
     * @return
     */
    public IRmCodeTypeDao getDao();

    /**
     * 获取数据访问接口
     * 
     * @param dao
     */
    public void setDao(IRmCodeTypeDao dao);

    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的id
     */
    public String insert(RmCodeTypeVo vo);
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(RmCodeTypeVo[] vos);

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
    public RmCodeTypeVo find(String id);

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(RmCodeTypeVo vo);

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
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
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr);
    
    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return 查询到的VO列表
     */
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size);
    
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
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn);
    
    /**
     * 通用的方法，执行更新，返回更新的记录条数
     *
     * @param strsql 要执行的sql语句
     * @return 更新记录条数
     */
    public int doUpdate(String strsql);
    
    /**
     * 集成了数据权限后，查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param authorize 权限类别对象
     * @param party_ids party_id的集合(包含了用户、角色等)
     * @return
     */
    public int getRecordCount(String queryCondition, RmAuthorizeVo authorize, String[] party_ids);
    
    /**
     * 集成了数据权限后，通过查询条件获得所有的VO对象列表，带翻页，带排序字符
     * 
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param authorize 权限类别对象
     * @param party_ids party_id的集合(包含了用户、角色等)
     * @return
     */
    public List<RmCodeTypeVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, RmAuthorizeVo authorize, String[] party_ids);
}