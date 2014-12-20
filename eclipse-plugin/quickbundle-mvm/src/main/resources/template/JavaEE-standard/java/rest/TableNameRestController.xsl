<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<!--处理table-->
	<xsl:template match="table[1]">
		<xsl:value-of select="str:getJavaFileComment($authorName)"/>
package <xsl:value-of select="$javaPackageTableDir"/>.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.quickbundle.base.web.page.RmPageVo;
import <xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>;
import <xsl:value-of select="$javaPackageTableDir"/>.service.<xsl:value-of select="$tableFormatNameUpperFirst"/>Service;
import <xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>;
import <xsl:value-of select="$javaPackageTableDir"/>.web.<xsl:value-of select="$tableFormatNameUpperFirst"/>Controller;
import org.quickbundle.third.spring.http.RmResponseEntityFactory;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmVoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * list                 GET     /api/<xsl:value-of select="@tableDirName"/>
 * get                  GET     /api/<xsl:value-of select="@tableDirName"/>/{id}
 * insert action        POST    /api/<xsl:value-of select="@tableDirName"/>
 * update action        PUT     /api/<xsl:value-of select="@tableDirName"/>/{id}
 * delete action        DELETE  /api/<xsl:value-of select="@tableDirName"/>/{id}
 * delete multi action  POST    /api/<xsl:value-of select="@tableDirName"/>/delete
 * batch insert update  POST    /api/<xsl:value-of select="@tableDirName"/>/batch
 */

<xsl:value-of select="str:getClassComment($authorName)"/>

@Controller
@RequestMapping(value = "/api/<xsl:value-of select="@tableDirName"/>")
public class <xsl:value-of select="$tableFormatNameUpperFirst"/>RestController implements <xsl:value-of select="$ITableNameConstants"/> {

    @Autowired
    private <xsl:value-of select="$tableFormatNameUpperFirst"/>Service <xsl:value-of select="$tableFormatNameLowerFirst"/>Service;

    @Autowired
    private Validator validator;

    /**
     * 获取多条记录
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<xsl:value-of select="$charLt"/>String, Object> list(HttpServletRequest request) {
        String queryCondition = <xsl:value-of select="$tableFormatNameUpperFirst"/>Controller.getQueryCondition(request);  //从request中获得查询条件
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.getCount(queryCondition));
        String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> beans = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.list(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
        Map<xsl:value-of select="$charLt"/>String, Object> result = new HashMap<xsl:value-of select="$charLt"/>String, Object>();
        result.put(RM_JSON_TOTAL_COUNT, pageVo.getRecordCount());
        result.put(REQUEST_BEANS, beans);
        return result;
    }
    
    /**
     * 获得单条记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<xsl:value-of select="$charLt"/>?> get(@PathVariable("id") <xsl:value-of select="$tablePkClass"/> id) {
        <xsl:value-of select="$TableNameVo"/> task = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.get(id);
        if (task == null) {
            return new ResponseEntity<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>>(task, HttpStatus.OK);
    }
    
    /**
     * 插入单条记录，使用服务端管理的ID号
     * @param vo
     * @param uriBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<xsl:value-of select="$charLt"/>?> insert(@RequestBody <xsl:value-of select="$TableNameVo"/> vo, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<xsl:value-of select="$charLt"/>ConstraintViolation<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>>> failures = validator.validate(vo);
        if (!failures.isEmpty()) {
            return RmResponseEntityFactory.build(failures, HttpStatus.BAD_REQUEST);
        }
        <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.insert(vo);
        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        <xsl:value-of select="$tablePkClass"/> id = vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>();
        URI uri = uriBuilder.path("/api/<xsl:value-of select="@tableDirName"/>/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<xsl:value-of select="$charLt"/>HttpHeaders>(headers, HttpStatus.CREATED);
    }

    /**
     * 修改单条记录
     * @param vo
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<xsl:value-of select="$charLt"/>?> update(@RequestBody <xsl:value-of select="$TableNameVo"/> vo) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<xsl:value-of select="$charLt"/>ConstraintViolation<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>>> failures = validator.validate(vo);
        if (!failures.isEmpty()) {
            return RmResponseEntityFactory.build(failures, HttpStatus.BAD_REQUEST);
        }
        //保存
        <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.update(vo);
        //按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
        return new ResponseEntity<xsl:value-of select="$charLt"/>String>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * 删除单条记录
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") <xsl:value-of select="$tablePkClass"/> id) {
        <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.delete(id);
    }
    
    /**
     * 删除单条记录
     * @param id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public int delete(HttpServletRequest request) {
        <xsl:value-of select="$tablePkClass"/>[] ids = RmJspHelper.get<xsl:if test="not($tablePkClass='String')"><xsl:value-of select="$tablePkClass"/></xsl:if>ArrayFromRequest(request, REQUEST_IDS); //从request获取多条记录id
        if (ids != null <xsl:value-of select="$charAmp"/><xsl:value-of select="$charAmp"/> ids.length != 0) {
            return <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.delete(ids); //删除多条记录
        }
        return 0;
    }
    
    /**
     * 批量保存，没有主键的insert，有主键的update
     * @param request
     * @return
     */
    @RequestMapping(value = "batch", method = RequestMethod.POST)
    public ResponseEntity<xsl:value-of select="$charLt"/>Map<xsl:value-of select="$charLt"/>String, int[]>> batchInsertUpdate(HttpServletRequest request) {
        List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> lvo = RmPopulateHelper.populateAjax(<xsl:value-of select="$TableNameVo"/>.class, request);
        for(<xsl:value-of select="$TableNameVo"/> vo : lvo) {
            if(vo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>() != null) {
                RmVoHelper.markModifyStamp(request, vo);
            } else {
                RmVoHelper.markCreateStamp(request, vo);
            }
        }
        int[] sum_insert_update = <xsl:value-of select="$tableFormatNameLowerFirst"/>Service.insertUpdateBatch(lvo.toArray(new <xsl:value-of select="$TableNameVo"/>[0]));
        Map<xsl:value-of select="$charLt"/>String, int[]> result = new HashMap<xsl:value-of select="$charLt"/>String, int[]>();
        result.put(EXECUTE_ROW_COUNT, sum_insert_update);
        return new ResponseEntity<xsl:value-of select="$charLt"/>Map<xsl:value-of select="$charLt"/>String, int[]>>(result, HttpStatus.OK);
    }
}
	</xsl:template>
</xsl:stylesheet>