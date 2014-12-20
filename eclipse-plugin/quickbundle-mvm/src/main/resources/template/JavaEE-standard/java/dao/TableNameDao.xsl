<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
    <xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
<xsl:param name="targetFullPath"></xsl:param>
	<!--处理table-->
    <xsl:template match="/meta/tables/table[position()=1 or @tableName=/meta/relations/mainTable[@tableName=/meta/tables/table[1]/@tableName]/refTable[count(middleTable)=0]/@tableName]">
        <xsl:result-document href="{$targetFullPath}/{str:getTableFormatNameUpperFirst(/meta, @tableName)}Dao.java">
        <xsl:variable name="thisTableName" select="@tableName"/>
        <xsl:variable name="thisTablePk" select="str:getTablePk(/meta, @tableName)"/>
		<xsl:value-of select="str:getJavaFileComment($authorName)"/>
package <xsl:value-of select="$javaPackageTableDir"/>.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.quickbundle.base.beans.factory.RmIdFactory;
import org.quickbundle.config.RmConfig;
import org.quickbundle.third.mybatis.ParaMap;
import org.quickbundle.third.mybatis.RmSqlSessionDaoSupport;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.springframework.stereotype.Repository;
import <xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>;
import <xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo;

<xsl:value-of select="str:getClassComment($authorName)"/>

@Repository
public class <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Dao extends RmSqlSessionDaoSupport implements <xsl:value-of select="$ITableNameConstants"/> {

    /**
     * 插入单条记录，用id作主键
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的id
     */
    public <xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/> insert(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo vo) {
        if(vo.get<xsl:value-of select="str:upperFirst(str:getTablePkFormatLower(/meta, @tableName))"/>() == null<xsl:if test="str:getPkColumnClass(/meta, $tableName, @tablePk)='String'"> || vo.get<xsl:value-of select="str:upperFirst(str:getTablePkFormatLower(/meta, @tableName))"/>().length() == 0</xsl:if>) {
            vo.set<xsl:value-of select="str:upperFirst(str:getTablePkFormatLower(/meta, @tableName))"/>(RmIdFactory.requestId<xsl:if test="not(str:getPkColumnClass(/meta, $tableName, @tablePk)='String')"><xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/></xsl:if>(TABLE_NAME<xsl:if test="/meta/relations/mainTable and not(/meta/relations/mainTable[@tableName=$thisTableName])">_<xsl:value-of select="@tableName"/></xsl:if>)); //获得id
        }
        getSqlSession().insert(namespace("insert"), vo);
        return vo.get<xsl:value-of select="str:upperFirst(str:getTablePkFormatLower(/meta, @tableName))"/>();
    }

    /**
     * 批更新插入多条记录，用id作主键
     * 
     * @param vos 添加的VO对象数组
     * @return 若添加成功，返回新生成的id数组
     */
    public <xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/>[] insert(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[] vos) {
        <xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/>[] ids =RmIdFactory.requestId<xsl:if test="not(str:getPkColumnClass(/meta, $tableName, @tablePk)='String')"><xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/></xsl:if>(TABLE_NAME<xsl:if test="/meta/relations/mainTable and not(/meta/relations/mainTable[@tableName=$thisTableName])">_<xsl:value-of select="@tableName"/></xsl:if>, vos.length); //批量获得id
        for(int i=0; i<xsl:value-of select="$charLt"/>vos.length; i++) {
            vos[i].set<xsl:value-of select="str:upperFirst(str:getTablePkFormatLower(/meta, @tableName))"/>(ids[i]);
        }
        SqlSession session = getSqlSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH);
        for(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo vo : vos) {
            session.insert(namespace("insert"), vo);
        }
        session.flushStatements();
        return ids;
    }
    
    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(<xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/> id) {
        return getSqlSession().delete(namespace("delete"), id);
    }

    /**
     * 删除多条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(<xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/> ids[]) {
        if(ids == null || ids.length == 0) {
            return 0;
        }
        int result = 0;
        List<xsl:value-of select="$charLt"/><xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/>[]> lIds = RmSqlHelper.splitPagingArray(ids, RmConfig.getSingleton().getMaxSqlInCount());
        for(<xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/>[] thisIds : lIds) {
            result += getSqlSession().delete(namespace("deleteMulti"), thisIds);
        }
        return result;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo vo) {
        return getSqlSession().update(namespace("update"), vo);
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 添加的VO对象数组
     * @return 成功更新的记录数组
     */
    public int[] update(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[] vos) {
        int[] result = new int[vos.length];
        SqlSession session = getSqlSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH);
        int index = 0;
        for(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo vo : vos) {
            result[index++] = session.update(namespace("update"), vo);
        }
        session.flushStatements();
        return result;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo get(<xsl:value-of select="str:getPkColumnClass(/meta, $tableName, @tablePk)"/> id) {
        return getSqlSession().selectOne(namespace("get"), id);
    }
    
    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getCount(String queryCondition) {
        return (Integer)getSqlSession().selectOne(namespace("getCount"), queryCondition);
    }
    
    /**
     * 功能: 通过组合后的查询条件获得所有的VO对象列表，带翻页，带排序字符
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param selectAllClumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
     * @return 查询到的VO列表
     */
    public List<xsl:value-of select="$charLt"/><xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo> list(String queryCondition, String orderStr, int startIndex, int size, boolean allClumn) {
        return getSqlSession().selectList(namespace(allClumn ? "listAllColumn" : "list"), new ParaMap(queryCondition, orderStr), new RowBounds(startIndex-1, size));
    }
    
    /**
     * 功能: 传入查询参数Map，获得所有的VO对象列表，带翻页，带排序字符
     * 
     * @param searchPara 搜索参数的Map
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return
     */
    public List<xsl:value-of select="$charLt"/><xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo> search(Map<xsl:value-of select="$charLt"/>String, Object> searchPara, String orderStr, int startIndex, int size) {
        searchPara.put("orderStr", orderStr);
        escapeSqlValue(searchPara, new String[]{<xsl:for-each select="column[not(@columnName=$thisTablePk) and @isBuild='true' and (@dataType='java.lang.String' and @maxLength&gt;3)]">"<xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/>"<xsl:if test="not(position()=last())">, </xsl:if>
      </xsl:for-each>});
        return getSqlSession().selectList(namespace("search"), searchPara, new RowBounds(startIndex-1, size));
    }
}
    </xsl:result-document>
    </xsl:template>
</xsl:stylesheet>