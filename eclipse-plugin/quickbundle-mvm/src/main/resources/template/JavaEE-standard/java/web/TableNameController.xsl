<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" xmlns:fn="http://www.w3.org/2005/04/xpath-functions" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<!--处理table-->
	<xsl:template match="table[1]">
		<xsl:value-of select="str:getJavaFileComment($authorName)"/>
package <xsl:value-of select="$javaPackageTableDir"/>.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quickbundle.base.web.page.RmPageVo;
import <xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>;
import <xsl:value-of select="$javaPackageTableDir"/>.service.<xsl:value-of select="$tableFormatNameUpperFirst"/>Service;
import <xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>;
<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
import <xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo;
</xsl:for-each>
import org.quickbundle.third.excel.StatisticExport;
import org.quickbundle.tools.helper.RmJspHelper;<xsl:if test="count(/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0])>0">
import org.quickbundle.tools.helper.RmPopulateHelper;</xsl:if>
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * list                  /<xsl:value-of select="@tableDirName"/>
 * insert page      GET  /<xsl:value-of select="@tableDirName"/>/insert
 * insert action    POST /<xsl:value-of select="@tableDirName"/>/insert
 * update page      GET  /<xsl:value-of select="@tableDirName"/>/update/{id}
 * update action    POST /<xsl:value-of select="@tableDirName"/>/update
 * delete action    POST /<xsl:value-of select="@tableDirName"/>/delete
 * detail                /<xsl:value-of select="@tableDirName"/>/detail/{id}
<xsl:if test="contains(@customBundleCode, 'reference')">
 * reference             /<xsl:value-of select="@tableDirName"/>/reference</xsl:if>
<xsl:if test="contains(@customBundleCode, 'statistic')">
 * statistic             /<xsl:value-of select="@tableDirName"/>/statistic
 * statistic table       /<xsl:value-of select="@tableDirName"/>/statistic/table
 *   export              /<xsl:value-of select="@tableDirName"/>/statistic/table/export
 * statistic chart       /<xsl:value-of select="@tableDirName"/>/statistic/chart
 * statistic flash       /<xsl:value-of select="@tableDirName"/>/statistic/flash
 *   data                /<xsl:value-of select="@tableDirName"/>/statistic/flash/data</xsl:if>
<xsl:if test="contains(@customBundleCode, 'importExport')">
 * import page      GET  /<xsl:value-of select="@tableDirName"/>/import
 * import action    POST /<xsl:value-of select="@tableDirName"/>/import
 * export custom    GET  /<xsl:value-of select="@tableDirName"/>/export
 * export action    POST /<xsl:value-of select="@tableDirName"/>/export</xsl:if>
<xsl:if test="contains(@customBundleCode, 'ajax')">
 * ajax                  /<xsl:value-of select="@tableDirName"/>/ajax</xsl:if>
 */

<xsl:value-of select="str:getClassComment($authorName)"/>

@Controller
@RequestMapping(value = "/<xsl:value-of select="@tableDirName"/>")
public class <xsl:value-of select="$tableFormatNameUpperFirst"/>Controller implements <xsl:value-of select="$ITableNameConstants"/> {

    @Autowired
    private <xsl:value-of select="$tableFormatNameUpperFirst"/>Service <xsl:value-of select="$tableFormatNameLowerFirst"/>Service;
    
    /**
     * 简单查询，分页显示，支持表单回写
     */
    @RequestMapping(value = "")
    public String list(Model model, HttpServletRequest request) {
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.getCount(queryCondition));
        String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> beans = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.list(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
        RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
        model.addAttribute(REQUEST_QUERY_CONDITION, queryCondition);
        model.addAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        model.addAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
        return "<xsl:value-of select="$jspSourceTableDir"/>/list<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
    
    /**
     * 跳转到新增页
     */
    @RequestMapping(value = "insert", method = RequestMethod.GET)
    public String insertForm(Model model) {
        model.addAttribute("bean", new <xsl:value-of select="$TableNameVo"/>());
        model.addAttribute("action", "insert");
        return "<xsl:value-of select="$jspSourceTableDir"/>/insert<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
    
    /**
     * 从页面表单获取信息注入vo，并插入单条记录
     */
	@RequestMapping(value = "insert", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<xsl:value-of select="$charLt"/>?> insert(HttpServletRequest request, @Valid <xsl:value-of select="$TableNameVo"/> vo, Errors errors) {
        RmVoHelper.markCreateStamp(request,vo);  //打创建时间,IP戳<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
        vo.setBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>(RmPopulateHelper.populateVos(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo.class, request, TABLE_PK_<xsl:value-of select="@tableName"/>, TABLE_NAME_<xsl:value-of select="@tableName"/> + RM_NAMESPACE_SPLIT_KEY));
        RmVoHelper.markCreateStamp(request, vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>());
</xsl:for-each><xsl:text>
        </xsl:text><xsl:value-of select="$tableFormatNameLowerFirst"/>Service.insert(vo);  //插入单条记录
        Map<xsl:value-of select="$charLt"/>String, String> result = new HashMap<xsl:value-of select="$charLt"/>String, String>();
        result.put("message", "新增成功: " + vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>());
		return new ResponseEntity<xsl:value-of select="$charLt"/>Map<xsl:value-of select="$charLt"/>String, String>>(result, HttpStatus.CREATED);
	}
    
    /**
     * 从页面的表单获取单条记录id，查出这条记录的值，并跳转到修改页面
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") String id, Model model) {
        <xsl:value-of select="$TableNameVo"/> bean = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.get(new <xsl:value-of select="$tablePkClass"/>(id));
        model.addAttribute(REQUEST_BEAN, bean);  //把vo放入request
        model.addAttribute("action", "update");
        return "<xsl:value-of select="$jspSourceTableDir"/>/insert<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
    
    /**
     * 从页面表单获取信息注入vo，并修改单条记录
     */
	@RequestMapping(value = "update", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<xsl:value-of select="$charLt"/>?> update(HttpServletRequest request, @Valid <xsl:value-of select="$TableNameVo"/> vo, Errors errors) {
		RmVoHelper.markModifyStamp(request,vo);  //打修改时间,IP戳<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]">
        vo.setBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>(RmPopulateHelper.populateVos(<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo.class, request, TABLE_PK_<xsl:value-of select="@tableName"/>, TABLE_NAME_<xsl:value-of select="@tableName"/> + RM_NAMESPACE_SPLIT_KEY));
        RmVoHelper.markModifyStamp(request, vo.getBody<xsl:if test="position()>1">
					<xsl:value-of select="position()"/>
				</xsl:if>());
</xsl:for-each><xsl:text>
        </xsl:text><xsl:value-of select="$tableFormatNameLowerFirst"/>Service.update(vo);  //更新单条记录
        Map<xsl:value-of select="$charLt"/>String, String> result = new HashMap<xsl:value-of select="$charLt"/>String, String>();
        result.put("message", "修改成功: " + vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>());
		return new ResponseEntity<xsl:value-of select="$charLt"/>Map<xsl:value-of select="$charLt"/>String, String>>(result, HttpStatus.CREATED);
	}
    
    /**
     * 从页面的表单获取单条记录id并删除，如request.id为空则删除多条记录（request.ids）
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int deleteCount = 0;  //定义成功删除的记录数
        String id = request.getParameter(REQUEST_ID);
        if(id != null <xsl:value-of select="$charAmp"/><xsl:value-of select="$charAmp"/> id.length() > 0) {
            deleteCount = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.delete(new <xsl:value-of select="$tablePkClass"/>(id));
        } else {
            <xsl:value-of select="$tablePkClass"/>[] ids = RmJspHelper.get<xsl:if test="not($tablePkClass='String')"><xsl:value-of select="$tablePkClass"/></xsl:if>ArrayFromRequest(request, REQUEST_IDS); //从request获取多条记录id
            if (ids != null <xsl:value-of select="$charAmp"/><xsl:value-of select="$charAmp"/> ids.length != 0) {
                deleteCount += <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.delete(ids);  //删除多条记录
            }
        }
        redirectAttributes.addFlashAttribute("message", "删除成功: " + deleteCount);
        return "redirect:/<xsl:value-of select="@tableDirName"/>";
    }

    /**
     * 从页面的表单获取单条记录id，并察看这条记录的详细信息
     */
    @RequestMapping(value = "detail/{id}")
    public String detail(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        <xsl:value-of select="$TableNameVo"/> bean = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.get(new <xsl:value-of select="$tablePkClass"/>(id));
        model.addAttribute(REQUEST_BEAN, bean);  //把vo放入request
        if(RM_YES.equals(request.getParameter(REQUEST_IS_READ_ONLY))) {
            model.addAttribute(REQUEST_IS_READ_ONLY, request.getParameter(REQUEST_IS_READ_ONLY));
        }
        return "<xsl:value-of select="$jspSourceTableDir"/>/detail<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
<xsl:if test="contains(@customBundleCode, 'reference')">
    /**
     * 参照信息查询，带简单查询，分页显示，支持表单回写
     */
    @RequestMapping(value = "reference")
    public String reference(Model model, HttpServletRequest request) {
        list(model, request);
        model.addAttribute(REQUEST_REFERENCE_INPUT_TYPE, request.getParameter(REQUEST_REFERENCE_INPUT_TYPE));  //传送输入方式,checkbox或radio
        return "<xsl:value-of select="$jspSourceTableDir"/>/util/reference<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
</xsl:if>
<xsl:if test="contains(@customBundleCode, 'statistic')">
    /**
     * 表格式统计
     */
    @RequestMapping(value = "statistic/table")
    public String statisticTable(Model model, HttpServletRequest request) {
        String rowKeyField = "<xsl:value-of select="$statisticColumnFormatLower"/>";  //定义行统计关键字
        String columnKeyField = "<xsl:value-of select="$keyColumnFormatLower"/>";  //定义列统计关键字
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> beans = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.list(queryCondition, null);  //查询出全部结果
        StatisticExport sh = new StatisticExport(beans, rowKeyField, columnKeyField, "父消息ID\\主键");
        model.addAttribute(REQUEST_STATISTIC_HANDLER, sh);  //把结果集放入request
        model.addAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
        return "<xsl:value-of select="$jspSourceTableDir"/>/util/statistic<xsl:value-of select="$tableFormatNameUpperFirst"/>_table";
    }
    
    /**
     * 表格式统计
     */
    @RequestMapping(value = "statistic/table/export")
    public String statisticTableExport(Model model, HttpServletRequest request) {
        statisticTable(model, request);
        return "support/downloadStatisticExcel";
    }
    
    /**
     * 图表式统计
     */
    @RequestMapping(value = "statistic/chart")
    public String statisticChart(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/util/statistic<xsl:value-of select="$tableFormatNameUpperFirst"/>_chart";
    }
    
    /**
     * Flash式统计
     */
    @RequestMapping(value = "statistic/flash")
    public String statisticFlash(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/util/statistic<xsl:value-of select="$tableFormatNameUpperFirst"/>_flash";
    }
    
    /**
     * Flash式统计
     */
    @RequestMapping(value = "statistic/flash/data")
    public String statisticFlashData(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/util/statistic<xsl:value-of select="$tableFormatNameUpperFirst"/>_flashData";
    }
</xsl:if>
<xsl:if test="contains(@customBundleCode, 'importExport')">
    /**
     * 跳转到导入页
     */
    @RequestMapping(value = "import", method = RequestMethod.GET)
    public String importDataForm(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/import<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
    
    /**
     * 执行导入
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importData(Model model) {
        model.addAttribute("isSubmit", "1");
        return "<xsl:value-of select="$jspSourceTableDir"/>/import<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
    
    /**
     * 定制导出
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public String exportCustomForm(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/export<xsl:value-of select="$tableFormatNameUpperFirst"/>_custom";
    }
    
    /**
     * 执行导出
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public String exportData(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/export<xsl:value-of select="$tableFormatNameUpperFirst"/>_excel";
    }
</xsl:if>
<xsl:if test="contains(@customBundleCode, 'ajax')">
    /**
     * 跳转到Ajax页
     */
    @RequestMapping(value = "ajax")
    public String ajax(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/ajax/list<xsl:value-of select="$tableFormatNameUpperFirst"/>";
    }
</xsl:if>
    /**
     * 从request中获得查询条件
     * @param request
     * @return 拼装好的SQL条件字符串
     */
    public static String getQueryCondition(HttpServletRequest request) {
        String queryCondition = null;
        if(request.getAttribute(REQUEST_QUERY_CONDITION) != null) {  //如果request.getAttribute中有，就不取request.getParameter
            queryCondition = request.getAttribute(REQUEST_QUERY_CONDITION).toString();
        } else {
            List<xsl:value-of select="$charLt"/>String> lQuery = new ArrayList<xsl:value-of select="$charLt"/>String>();
            <xsl:apply-templates mode="buildTableColumn_actionQueryCondition"/>
            queryCondition = RmSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
        }
        return queryCondition;
    }

<xsl:for-each select="/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)>0]/middleTable[1]">
<xsl:variable name="refTableNameVar" select="../@tableName"/>
    /**
     * 跳转到中间表<xsl:value-of select="@tableName"/>页
     */
    @RequestMapping(value = "<xsl:value-of select="lower-case(@tableName)"/>")
    public String <xsl:value-of select="lower-case(@tableName)"/>(Model model) {
        return "<xsl:value-of select="$jspSourceTableDir"/>/middle/list<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>";
    }
    
    /**
     * 插入中间表<xsl:value-of select="@tableName"/>数据
     */
    @RequestMapping(value = "insert<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>", method = RequestMethod.POST)
    public String insert<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>(HttpServletRequest request, @Valid <xsl:value-of select="$TableNameVo"/> vo, Errors errors, RedirectAttributes redirectAttributes) {
        String <xsl:value-of select="lower-case(@mainColumn)"/> = request.getParameter("<xsl:value-of select="lower-case(@mainColumn)"/>");
        String[] <xsl:value-of select="lower-case(@refColumn)"/>s = request.getParameter("<xsl:value-of select="lower-case(@refColumn)"/>s").split(",");
        int count = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.insert<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>(<xsl:value-of select="lower-case(@mainColumn)"/>, <xsl:value-of select="lower-case(@refColumn)"/>s).length;
        redirectAttributes.addFlashAttribute("message", "插入了" + count + "条记录!");
        redirectAttributes.addAttribute("<xsl:value-of select="lower-case(@mainColumn)"/>", <xsl:value-of select="lower-case(@mainColumn)"/>);
        return "redirect:/<xsl:value-of select="$tableDirName"/>/<xsl:value-of select="lower-case(@tableName)"/>";
    }
    
    /**
     * 删除中间表<xsl:value-of select="@tableName"/>数据
     */
    @RequestMapping(value = "delete<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>", method = RequestMethod.POST)
    public String delete<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>(HttpServletRequest request, @Valid <xsl:value-of select="$TableNameVo"/> vo, RedirectAttributes redirectAttributes) {
        String <xsl:value-of select="lower-case(@mainColumn)"/> = request.getParameter("<xsl:value-of select="lower-case(@mainColumn)"/>");
        String[] <xsl:value-of select="lower-case(@refColumn)"/>s = request.getParameter("<xsl:value-of select="lower-case(@refColumn)"/>s").split(",");
        int count = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.delete<xsl:value-of select="str:upperFirst(lower-case(@tableName))"/>(<xsl:value-of select="lower-case(@mainColumn)"/>, <xsl:value-of select="lower-case(@refColumn)"/>s);
        redirectAttributes.addFlashAttribute("message", "删除了" + count + "条记录!");
        redirectAttributes.addAttribute("<xsl:value-of select="lower-case(@mainColumn)"/>", <xsl:value-of select="lower-case(@mainColumn)"/>);
        return "redirect:/<xsl:value-of select="$tableDirName"/>/<xsl:value-of select="lower-case(@tableName)"/>";
    }
</xsl:for-each>
}
</xsl:template>

	<!--处理后台查询的build查询条件-->
	<xsl:template match="column" mode="buildTableColumn_actionQueryCondition">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true'">
			<xsl:choose>
				<!--处理 参照 && 数字 -->
				<xsl:when test="@humanDisplayType='rm.listReference' and (@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer')">
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>"), RmSqlHelper.TYPE_CUSTOM, "=", ""));
				</xsl:when>
				<!--处理 状态位 || (参照&&字符)，小于3个字符-->
				<xsl:when test="(@dataType='java.lang.String' and @maxLength&lt;=3) or (@humanDisplayType='rm.listReference' and @dataType='java.lang.String')">
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
				</xsl:when>
				<!--处理text或textarea，大于3个字符-->
				<xsl:when test="@dataType='java.lang.String' and @maxLength&gt;3">
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>"), RmSqlHelper.TYPE_CHAR_LIKE));
				</xsl:when>
				<!--处理时间参照-->
				<xsl:when test="@dataType='java.sql.Timestamp' or @dataType='java.sql.Date'">
        	lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>_from"), RmSqlHelper.TYPE_DATE_GREATER_EQUAL));
        	lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>_to"), RmSqlHelper.TYPE_DATE_LESS_EQUAL));	
				</xsl:when>
				<!--处理数字-->
				<xsl:when test="@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer'">
        	lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>_from"), RmSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
        	lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>_to"), RmSqlHelper.TYPE_NUMBER_LESS_EQUAL));
				</xsl:when>
				<xsl:otherwise>
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".<xsl:value-of select="$columnNameFormatLower"/>", request.getParameter("<xsl:value-of select="$columnNameFormatLower"/>"), RmSqlHelper.TYPE_CHAR_LIKE));
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
