package org.quickbundle.modules.message.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.modules.message.IRmMessageConstants;
import org.quickbundle.modules.message.service.RmMessageService;
import org.quickbundle.modules.message.vo.RmMessageVo;
import org.quickbundle.modules.message.web.RmMessageController;
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
 * list					GET		/api/rmmessage
 * get					GET		/api/rmmessage/{id}
 * insert action		POST	/api/rmmessage
 * update action		PUT		/api/rmmessage/{id}
 * delete action		DELETE	/api/rmmessage/{id}
 * delete multi action	POST	/api/rmmessage/delete
 * batch insert update	POST	/api/rmmessage/batch
 * 
 * @author 白小勇
 */
@Controller
@RequestMapping(value = "/api/message")
public class RmMessageRestController implements IRmMessageConstants {

	@Autowired
	private RmMessageService rmMessageService;

	@Autowired
	private Validator validator;

	/**
	 * 获取多条记录
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
        String queryCondition = RmMessageController.getQueryCondition(request);  //从request中获得查询条件
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, rmMessageService.getCount(queryCondition));
        String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        List<RmMessageVo> beans = rmMessageService.list(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
        Map<String, Object> result = new HashMap<String, Object>();
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
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		RmMessageVo task = rmMessageService.get(id);
		if (task == null) {
			return new ResponseEntity<RmMessageVo>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RmMessageVo>(task, HttpStatus.OK);
	}
	
	/**
	 * 插入单条记录，使用服务端管理的ID号
	 * @param vo
	 * @param uriBuilder
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> insert(@RequestBody RmMessageVo vo, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RmMessageVo>> failures = validator.validate(vo);
		if (!failures.isEmpty()) {
			return RmResponseEntityFactory.build(failures, HttpStatus.BAD_REQUEST);
		}
		rmMessageService.insert(vo);
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		Long id = vo.getId();
		URI uri = uriBuilder.path("/api/message/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity<HttpHeaders>(headers, HttpStatus.CREATED);
	}

	/**
	 * 修改单条记录
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody RmMessageVo vo) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<RmMessageVo>> failures = validator.validate(vo);
		if (!failures.isEmpty()) {
			return RmResponseEntityFactory.build(failures, HttpStatus.BAD_REQUEST);
		}
		//保存
		rmMessageService.update(vo);
		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		rmMessageService.delete(id);
	}
	
	/**
	 * 删除单条记录
	 * @param id
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(HttpServletRequest request) {
		Long[] ids = RmJspHelper.getLongArrayFromRequest(request, REQUEST_IDS); //从request获取多条记录id
		if (ids != null && ids.length != 0) {
			return rmMessageService.delete(ids); //删除多条记录
		}
		return 0;
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "batch", method = RequestMethod.POST)
	public ResponseEntity<Map<String, int[]>> batchInsertUpdate(HttpServletRequest request) {
    	List<RmMessageVo> lvo = RmPopulateHelper.populateAjax(RmMessageVo.class, request);
    	for(RmMessageVo vo : lvo) {
    		if(vo.getId() != null) {
    			RmVoHelper.markModifyStamp(request, vo);
    		} else {
    			RmVoHelper.markCreateStamp(request, vo);
    		}
    	}
    	int[] sum_insert_update = rmMessageService.insertUpdateBatch(lvo.toArray(new RmMessageVo[0]));
    	Map<String, int[]> result = new HashMap<String, int[]>();
    	result.put(EXECUTE_ROW_COUNT, sum_insert_update);
    	return new ResponseEntity<Map<String, int[]>>(result, HttpStatus.OK);
	}
}