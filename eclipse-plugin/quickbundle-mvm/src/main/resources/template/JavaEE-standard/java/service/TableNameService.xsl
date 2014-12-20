<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<!--处理table-->
	<xsl:template match="table[1]">
		<xsl:value-of select="str:getJavaFileComment($authorName)"/>
package <xsl:value-of select="$javaPackageTableDir"/>.service;
<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)>0])>0">
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;</xsl:if>
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import <xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>;
import <xsl:value-of select="$javaPackageTableDir"/>.dao.<xsl:value-of select="$tableFormatNameUpperFirst"/>Dao;
import <xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>;
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
import <xsl:value-of select="$javaPackageTableDir"/>.dao.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Dao;
import <xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo;
</xsl:for-each>
import org.quickbundle.project.RmProjectHelper;<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)>0])>0">
import org.quickbundle.project.common.service.IRmCommonService;
import org.quickbundle.tools.helper.RmStringHelper;</xsl:if>
<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable)>0">
import org.quickbundle.tools.helper.RmVoHelper;</xsl:if>
import org.springframework.beans.factory.annotation.Autowired;<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)>0])>0">
import org.springframework.jdbc.core.RowMapper;</xsl:if>
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<xsl:value-of select="str:getClassComment($authorName)"/>

@Service
//默认将类中的所有public函数纳入事务管理
@Transactional(readOnly = true)
public class <xsl:value-of select="$tableFormatNameUpperFirst"/>Service implements <xsl:value-of select="$ITableNameConstants"/> {

    @Autowired
    private <xsl:value-of select="$tableFormatNameUpperFirst"/>Dao <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao;
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
    @Autowired
    private <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Dao <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao;
</xsl:for-each>
    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public <xsl:value-of select="$tablePkClass"/> insert(<xsl:value-of select="$TableNameVo"/> vo) {
        <xsl:value-of select="$tablePkClass"/> id = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.insert(vo);<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
        if(vo.getBody<xsl:if test="position()>1">
				<xsl:value-of select="position()"/>
			</xsl:if>() != null) {
            for(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo bodyVo: vo.getBody<xsl:if test="position()>1">
				<xsl:value-of select="position()"/>
			</xsl:if>()) {
                bodyVo.set<xsl:value-of select="str:upperFirst(str:getRefColumnFormatLower(/meta, @tableName))"/> (vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>());
            }
            <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.insert(vo.getBody<xsl:if test="position()>1">
                <xsl:value-of select="position()"/>
            </xsl:if>().toArray(new <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[0]));
        }</xsl:for-each>
        RmProjectHelper.log(LOG_TYPE_NAME, "插入了1条记录,id={}<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">,子记录{}条</xsl:if>", id<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">, vo.getBody() == null ? 0 : vo.getBody().size()</xsl:if>);
        return id;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public <xsl:value-of select="$tablePkClass"/>[] insert(<xsl:value-of select="$TableNameVo"/>[] vos) {
        <xsl:value-of select="$tablePkClass"/>[] ids = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.insert(vos);<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
			<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
        List<xsl:value-of select="$charLt"/>
				<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo> bodyVoToInsert<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if> = new ArrayList<xsl:value-of select="$charLt"/>
				<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo>();</xsl:for-each>
        for(<xsl:value-of select="$TableNameVo"/> vo : vos) {<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
            if(vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>() != null) {
                for(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo bodyVo: vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>()) {
                    bodyVo.set<xsl:value-of select="str:upperFirst(str:getRefColumnFormatLower(/meta, @tableName))"/> (vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>());
                    bodyVoToInsert<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.add(bodyVo);
                }
            }</xsl:for-each>
        }
        <xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
				<xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.insert(bodyVoToInsert<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.toArray(new <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[0]));</xsl:for-each>
		</xsl:if>
        RmProjectHelper.log(LOG_TYPE_NAME, "插入了{}条记录,id={}<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">,子记录共{}条</xsl:if>", vos.length, Arrays.toString(ids)<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">, <xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">bodyVoToInsert<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.size()<xsl:if test="not(position()=last())"> + </xsl:if>
			</xsl:for-each>
		</xsl:if>);
        return ids;
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public int delete(<xsl:value-of select="$tablePkClass"/> id) {<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
        <xsl:value-of select="$TableNameVo"/> vo = get(id);</xsl:if>
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
<xsl:variable name="tableNameVar" select="@tableName"/>
        List<xsl:value-of select="$charLt"/><xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>> bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if> = new ArrayList<xsl:value-of select="$charLt"/><xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>>();
        if(vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>() != null) {
            for(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo bodyVo : vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>()) {
                bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.add(bodyVo.get<xsl:value-of select="str:upperFirst(str:getTablePkFormatLower(/meta, @tableName))"/>());
            }
        }
        if(bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.size() > 0) {
            <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.delete(bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.toArray(new <xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>[0]));
        }
</xsl:for-each>
        int sum = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.delete(id);
        RmProjectHelper.log(LOG_TYPE_NAME, "删除了{}条记录,id={}<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">,子记录共{}条</xsl:if>", sum, id<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">, <xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.size()<xsl:if test="not(position()=last())"> + </xsl:if>
			</xsl:for-each>
		</xsl:if>);
        return sum;
    }

    /**
     * 删除多条记录
     * 
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(<xsl:value-of select="$tablePkClass"/> ids[]) {
<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
<xsl:variable name="tableNameVar" select="@tableName"/>
        List<xsl:value-of select="$charLt"/><xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>> bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if> = new ArrayList<xsl:value-of select="$charLt"/><xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>>();
</xsl:for-each>
        for(<xsl:value-of select="$tablePkClass"/> id : ids) {
            <xsl:value-of select="$TableNameVo"/> vo = get(id);
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
            if(vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>() != null) {
                for(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo bodyVo : vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>()) {
                    bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.add(bodyVo.get<xsl:value-of select="str:upperFirst(str:getTablePkFormatLower(/meta, @tableName))"/>());
                }
            }
</xsl:for-each>
        }
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
<xsl:variable name="tableNameVar" select="@tableName"/>
        if(bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.size() > 0) {
            <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.delete(bodyIdToDelete.toArray(new <xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>[0]));
        }
</xsl:for-each>
</xsl:if>
        int sum = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.delete(ids);
        RmProjectHelper.log(LOG_TYPE_NAME, "删除了{}条记录,ids={}<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">,子记录共{}条</xsl:if>", sum, Arrays.toString(ids)<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">, <xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">bodyIdToDelete<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>.size()<xsl:if test="not(position()=last())"> + </xsl:if>
			</xsl:for-each>
		</xsl:if>);
        return sum;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
    @SuppressWarnings({ "rawtypes", "unchecked" })</xsl:if>
    public int update(<xsl:value-of select="$TableNameVo"/> vo) {
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
<xsl:variable name="tableNameVar" select="@tableName"/>
        if(vo.getBody<xsl:if test="position()>1">
                <xsl:value-of select="position()"/>
            </xsl:if>() != null) {
            List[] result = RmVoHelper.mergeVos(vo, TABLE_PK, <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.list("<xsl:value-of select="@refColumn"/>=<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'">'</xsl:if>" + vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>()<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'"> + "'"</xsl:if>, null, 1, Integer.MAX_VALUE, true), vo.getBody<xsl:if test="position()>1">
                <xsl:value-of select="position()"/>
            </xsl:if>(), TABLE_PK_<xsl:value-of select="@tableName"/>, "<xsl:value-of select="str:getRefColumnFormatLower(/meta, @tableName)"/>");
            <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.insert((<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[])result[0].toArray(new <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[0]));
            <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.delete((<xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>[])result[1].toArray(new <xsl:value-of select="str:getPkColumnClass(/meta, $tableName, /meta/tables/table[@tableName=$tableNameVar]/@tablePk)"/>[0]));
            <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.update((<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[])result[2].toArray(new <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[0]));
        }
</xsl:for-each>
        int sum = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.update(vo);
        RmProjectHelper.log(LOG_TYPE_NAME, "更新了{}条记录,id={}", sum, vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>());
        return sum;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
    @SuppressWarnings({ "rawtypes", "unchecked" })</xsl:if>
    public int update(<xsl:value-of select="$TableNameVo"/>[] vos) {
        List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo> toInsert = new ArrayList<xsl:value-of select="$charLt"/>
		<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo>();
        List<xsl:value-of select="$charLt"/><xsl:value-of select="$tablePkClass"/>> toDelete = new ArrayList<xsl:value-of select="$charLt"/><xsl:value-of select="$tablePkClass"/>>();
        List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo> toUpdate = new ArrayList<xsl:value-of select="$charLt"/>
		<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo>();<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
        for(<xsl:value-of select="$TableNameVo"/> vo : vos) {
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
            if(vo.getBody<xsl:if test="position()>1">
                <xsl:value-of select="position()"/>
            </xsl:if>() != null) {
                List[] result = RmVoHelper.mergeVos(vo, TABLE_PK, <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.list("<xsl:value-of select="@refColumn"/>=<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'">'</xsl:if>" + vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>()<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'"> + "'"</xsl:if>, null, 1, Integer.MAX_VALUE, true), vo.getBody<xsl:if test="position()>1">
                <xsl:value-of select="position()"/>
            </xsl:if>(), TABLE_PK_<xsl:value-of select="@tableName"/>, "<xsl:value-of select="str:getRefColumnFormatLower(/meta, @tableName)"/>");
                toInsert.addAll(result[0]);
                toDelete.addAll(result[1]);
                toUpdate.addAll(result[2]);
            }
</xsl:for-each>
        }</xsl:if>
        <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.insert(toInsert.toArray(new <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[0]));
        <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.delete(toDelete.toArray(new <xsl:value-of select="$tablePkClass"/>[0]));
        <xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.update(toUpdate.toArray(new <xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo[0]));
        int[] sum = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.update(vos);
        int finalSum = 0;
        for (int i = 0; i <xsl:value-of select="$charLt"/> sum.length; i++) {
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
    public int[] insertUpdateBatch(<xsl:value-of select="$TableNameVo"/>[] vos) {
        int[] sum_insert_update = new int[2];
        List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>> lInsert = new ArrayList<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>>();
        List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>> lUpdate = new ArrayList<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>>();
        for (int i = 0; i <xsl:value-of select="$charLt"/> vos.length; i++) {
            if(vos[i].get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>() != null) {
                lUpdate.add(vos[i]);
            } else {
                lInsert.add(vos[i]);
            }
        }
        if(lInsert.size() > 0) {
            sum_insert_update[0] = insert(lInsert.toArray(new <xsl:value-of select="$TableNameVo"/>[0])).length;
        }
        if(lUpdate.size() > 0) {
            sum_insert_update[1] = update(lUpdate.toArray(new <xsl:value-of select="$TableNameVo"/>[0]));
        }
        return sum_insert_update;
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public <xsl:value-of select="$TableNameVo"/> get(<xsl:value-of select="$tablePkClass"/> id) {
        <xsl:value-of select="$TableNameVo"/> vo = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.get(id);<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
        vo.setBody<xsl:if test="position()>1">
				<xsl:value-of select="position()"/>
			</xsl:if>(<xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.list("<xsl:value-of select="@refColumn"/>=<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'">'</xsl:if>" + id<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'"> + "'"</xsl:if>, null, 1, Integer.MAX_VALUE, true));
</xsl:for-each>
        return vo;
    }
    
    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getCount(String queryCondition) {
        int sum = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.getCount(queryCondition);
        return sum;
    }

    /**
     * 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>> list(String queryCondition, String orderStr) {
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
    public List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>> list(String queryCondition, String orderStr, int startIndex, int size) {
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
    public List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> list(String queryCondition, String orderStr, int startIndex, int size, boolean allColumn) {
        List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>> lResult = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.list(queryCondition, orderStr, startIndex, size, allColumn);<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
        if(allColumn) {
            for(<xsl:value-of select="$TableNameVo"/> vo : lResult) {
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
        vo.setBody<xsl:if test="position()>1">
				<xsl:value-of select="position()"/>
			</xsl:if>(<xsl:value-of select="str:getTableFormatNameLowerFirst(/meta, @tableName)"/>Dao.list("<xsl:value-of select="lower-case(@refColumn)"/>=<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'">'</xsl:if>" + vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>()<xsl:if test="str:getPkColumnClass(/meta, @tableName, @refColumn)='String'"> + "'"</xsl:if>, null, 1, Integer.MAX_VALUE, true));
</xsl:for-each>
            }
        }</xsl:if>
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
    public List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>> search(Map<xsl:value-of select="$charLt"/>String, Object> searchPara, String orderStr, int startIndex, int size) {
        List<xsl:value-of select="$charLt"/>
		<xsl:value-of select="$TableNameVo"/>> lResult = <xsl:value-of select="$tableFormatNameLowerFirst"/>Dao.search(searchPara, orderStr, startIndex, size);
        return lResult;
    }

<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)>0]/middleTable[1]">
    /**
     * 插入中间表<xsl:value-of select="@tableName"/>数据
     * 
     * @param <xsl:value-of select="lower-case(@mainColumn)"/>
     * @param <xsl:value-of select="lower-case(@refColumn)"/>s
     * @return 插入的<xsl:value-of select="lower-case(@refColumn)"/>列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String[] insert<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>(String <xsl:value-of select="lower-case(@mainColumn)"/>, String[] <xsl:value-of select="lower-case(@refColumn)"/>s) {
        if(<xsl:value-of select="lower-case(@refColumn)"/>s.length == 0 || (<xsl:value-of select="lower-case(@refColumn)"/>s.length == 1 <xsl:value-of select="$charAmp"/>
		<xsl:value-of select="$charAmp"/><xsl:value-of select="lower-case(@refColumn)"/>s[0].trim().length() == 0)) {
            return new String[0];
        }
        IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
        List<xsl:value-of select="$charLt"/>String> lExistId = cs.query("select * from <xsl:value-of select="@tableName"/> where <xsl:value-of select="@mainColumn"/>=<xsl:if test="str:getPkColumnClass(/meta, $tableName, $tablePk)='String'">'</xsl:if>" + <xsl:value-of select="lower-case(@mainColumn)"/> + "<xsl:if test="str:getPkColumnClass(/meta, $tableName, $tablePk)='String'">'</xsl:if> and <xsl:value-of select="@refColumn"/> in(" + RmStringHelper.parseToString<xsl:if test="str:getPkColumnClass(/meta, ../@tableName, ../@refColumn)='String'">Apos</xsl:if>(<xsl:value-of select="lower-case(@refColumn)"/>s) + ")", new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("<xsl:value-of select="@refColumn"/>");
            }
        });
        Set<xsl:value-of select="$charLt"/>String> s<xsl:value-of select="str:upperFirst(lower-case(@refColumn))"/> = new HashSet<xsl:value-of select="$charLt"/>String>();
        for(String <xsl:value-of select="lower-case(@refColumn)"/> : <xsl:value-of select="lower-case(@refColumn)"/>s) {
            if(!lExistId.contains(<xsl:value-of select="lower-case(@refColumn)"/>)) {
                s<xsl:value-of select="str:upperFirst(lower-case(@refColumn))"/>.add(<xsl:value-of select="lower-case(@refColumn)"/>);
            }
        }
        if(s<xsl:value-of select="str:upperFirst(lower-case(@refColumn))"/>.size() > 0) {
            String[][] aaValue = new String[s<xsl:value-of select="str:upperFirst(lower-case(@refColumn))"/>.size()][2];
            int index = 0;
            for (String <xsl:value-of select="lower-case(@refColumn)"/> : s<xsl:value-of select="str:upperFirst(lower-case(@refColumn))"/>) {
                aaValue[index][0] = <xsl:value-of select="lower-case(@mainColumn)"/>;
                aaValue[index][1] = <xsl:value-of select="lower-case(@refColumn)"/>;
                index ++;
            }
            cs.doUpdateBatch("insert into <xsl:value-of select="@tableName"/> (<xsl:value-of select="@mainColumn"/>, <xsl:value-of select="@refColumn"/>) VALUES(?, ?)", aaValue);
        }
        return s<xsl:value-of select="str:upperFirst(lower-case(@refColumn))"/>.toArray(new String[0]);
    }
    
    /**
     * 删除中间表<xsl:value-of select="@tableName"/>数据
     * 
     * @param <xsl:value-of select="lower-case(@mainColumn)"/>
     * @param <xsl:value-of select="lower-case(@refColumn)"/>s
     * @return 删除的记录数
     */
    public int delete<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>(String <xsl:value-of select="lower-case(@mainColumn)"/>, String[] <xsl:value-of select="lower-case(@refColumn)"/>s) {
        IRmCommonService cs = RmProjectHelper.getCommonServiceInstance();
        return cs.doUpdate("delete from <xsl:value-of select="@tableName"/> where <xsl:value-of select="@mainColumn"/>=<xsl:if test="str:getPkColumnClass(/meta, $tableName, $tablePk)='String'">'</xsl:if>" + <xsl:value-of select="lower-case(@mainColumn)"/> + "<xsl:if test="str:getPkColumnClass(/meta, $tableName, $tablePk)='String'">'</xsl:if> and <xsl:value-of select="@refColumn"/> in(" + RmStringHelper.parseToString<xsl:if test="str:getPkColumnClass(/meta, ../@tableName, ../@refColumn)='String'">Apos</xsl:if>(<xsl:value-of select="lower-case(@refColumn)"/>s) + ")");
    }
</xsl:for-each>
}
</xsl:template>
</xsl:stylesheet>
