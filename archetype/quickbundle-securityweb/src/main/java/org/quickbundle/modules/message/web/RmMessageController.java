package org.quickbundle.modules.message.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.modules.message.IRmMessageConstants;
import org.quickbundle.modules.message.service.RmMessageService;
import org.quickbundle.modules.message.vo.RmMessageReceiverVo;
import org.quickbundle.modules.message.vo.RmMessageVo;
import org.quickbundle.third.excel.StatisticExport;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmStringHelper;
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
 * bootstrap main			/message => formRmMessage.jsp
 * bootstrap 				/message/add|modify|remove|get
 * 
 * 
 * list					 /message/list
 * insert page		GET	 /message/insert
 * insert action	POST /message/insert
 * update page		GET  /message/update/{id}
 * update action	POST /message/update
 * delete action	POST /message/delete
 * detail				 /message/detail/{id}
 * reference			 /message/reference
 * statistic			 /message/statistic
 * statistic table		 /message/statistic/table
 *   export				 /message/statistic/table/export
 * statistic chart		 /message/statistic/chart
 * statistic flash		 /message/statistic/flash
 *   data				 /message/statistic/flash/data
 * import page		GET	 /message/import
 * import action	POST /message/import
 * export custom	GET	 /message/export
 * export action	POST /message/export
 * ajax					 /message/ajax
 * 
 * @author 白小勇
 */
@Controller
@RequestMapping(value = "/message")
public class RmMessageController implements IRmMessageConstants {

	@Autowired
	private RmMessageService rmMessageService;
	
	/**
	 * 简单查询，分页显示，支持表单回写
	 */
	@RequestMapping(value = "")
	public String list(Model model, HttpServletRequest request) {
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, rmMessageService.getCount(queryCondition));
        String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        List<RmMessageVo> beans = rmMessageService.list(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
        RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
        model.addAttribute(REQUEST_QUERY_CONDITION, queryCondition);
        model.addAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        model.addAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
		return "message/formRmMessage";
	}
	
	/**
	 * 跳转到新增页
	 */
	@RequestMapping(value = "insert", method = RequestMethod.GET)
	public String insertForm(Model model) {
		model.addAttribute("bean", new RmMessageVo());
		model.addAttribute("action", "insert");
		return "message/insertRmMessage";
	}
    
	@RequestMapping(value = "modify/{id}")
    public String updateForm1(@PathVariable("id") String id, Model model) {
    	RmMessageVo bean = rmMessageService.get(new Long(id));
        model.addAttribute(REQUEST_BEAN, bean);  //把vo放入request
        model.addAttribute("action", "update");
        return "message/modifyRmMessage";
    }
	
	/**
	 * 从页面表单获取信息注入vo，并插入单条记录
	 */
	/*@RequestMapping(value = "insert", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> insert(HttpServletRequest request, @Valid RmMessageVo vo, Errors errors) {
        RmVoHelper.markCreateStamp(request,vo);  //打创建时间,IP戳
        vo.setBody(RmPopulateHelper.populateVos(RmMessageReceiverVo.class, request, TABLE_PK_RM_MESSAGE_RECEIVER, TABLE_NAME_RM_MESSAGE_RECEIVER + RM_NAMESPACE_SPLIT_KEY));
        RmVoHelper.markCreateStamp(request, vo.getBody());
        rmMessageService.insert(vo);  //插入单条记录
        Map<String, String> result = new HashMap<String, String>();
        result.put("message", "新增成功: " + vo.getId());
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.CREATED);
	}*/
	
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(HttpServletRequest request, @Valid RmMessageVo vo, Errors errors,RedirectAttributes redirectAttributes) {
        RmVoHelper.markCreateStamp(request,vo);  //打创建时间,IP戳
        rmMessageService.insert(vo);  //插入单条记录
		return "redirect:/message";
	}
	
	/**
	 * 从页面的表单获取单条记录id，查出这条记录的值，并跳转到修改页面
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		RmMessageVo bean = rmMessageService.get(new Long(id));
        model.addAttribute(REQUEST_BEAN, bean);  //把vo放入request
        model.addAttribute("action", "update");
		return "message/insertRmMessage";
	}
	
	/**
	 * 从页面表单获取信息注入vo，并修改单条记录
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> update(HttpServletRequest request, @Valid RmMessageVo vo, Errors errors) {
		RmVoHelper.markModifyStamp(request,vo);  //打修改时间,IP戳
		vo.setBody(RmPopulateHelper.populateVos(RmMessageReceiverVo.class, request, TABLE_PK_RM_MESSAGE_RECEIVER, TABLE_NAME_RM_MESSAGE_RECEIVER + RM_NAMESPACE_SPLIT_KEY));
		RmVoHelper.markModifyStamp(request, vo.getBody());
        rmMessageService.update(vo);  //更新单条记录
        Map<String, String> result = new HashMap<String, String>();
        result.put("message", "修改成功: " + vo.getId());
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.CREATED);
	}
	
	/**
	 * 从页面的表单获取单条记录id并删除，如request.id为空则删除多条记录（request.ids）
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int deleteCount = 0;  //定义成功删除的记录数
		String id = request.getParameter(REQUEST_ID);
		if(id != null && id.length() > 0) {
			deleteCount = rmMessageService.delete(new Long(id));
		} else {
			Long[] ids = RmJspHelper.getLongArrayFromRequest(request, REQUEST_IDS); //从request获取多条记录id
			if (ids != null && ids.length != 0) {
				deleteCount += rmMessageService.delete(ids);  //删除多条记录
			}
		}
		redirectAttributes.addFlashAttribute("message", "删除成功: " + deleteCount);
        return "redirect:/message";
	}

	/**
	 * 从页面的表单获取单条记录id，并察看这条记录的详细信息
	 */
	@RequestMapping(value = "detail/{id}")
	public String detail(@PathVariable("id") String id, Model model, HttpServletRequest request) {
		RmMessageVo bean = rmMessageService.get(new Long(id));
        model.addAttribute(REQUEST_BEAN, bean);  //把vo放入request
        if(RM_YES.equals(request.getParameter(REQUEST_IS_READ_ONLY))) {
        	model.addAttribute(REQUEST_IS_READ_ONLY, request.getParameter(REQUEST_IS_READ_ONLY));
        }
		return "message/detailRmMessage";
	}
	
	/**
	 * 参照信息查询，带简单查询，分页显示，支持表单回写
	 */
	@RequestMapping(value = "reference")
	public String reference(Model model, HttpServletRequest request) {
		list(model, request);
		model.addAttribute(REQUEST_REFERENCE_INPUT_TYPE, request.getParameter(REQUEST_REFERENCE_INPUT_TYPE));  //传送输入方式,checkbox或radio
		return "message/util/referenceRmMessage";
	}

	/**
	 * 表格式统计
	 */
	@RequestMapping(value = "statistic/table")
	public String statisticTable(Model model, HttpServletRequest request) {
        String rowKeyField = "parent_message_id";  //定义行统计关键字
        String columnKeyField = "id";  //定义列统计关键字
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        List<RmMessageVo> beans = rmMessageService.list(queryCondition, null);  //查询出全部结果
        StatisticExport sh = new StatisticExport(beans, rowKeyField, columnKeyField, "父消息ID\\主键");
        model.addAttribute(REQUEST_STATISTIC_HANDLER, sh);  //把结果集放入request
        model.addAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
		return "message/util/statisticRmMessage_table";
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
		return "message/util/statisticRmMessage_chart";
	}
	/**
	 * Flash式统计
	 */
	@RequestMapping(value = "statistic/flash")
	public String statisticFlash(Model model) {
		return "message/util/statisticRmMessage_flash";
	}
	/**
	 * Flash式统计
	 */
	@RequestMapping(value = "statistic/flash/data")
	public String statisticFlashData(Model model) {
		return "message/util/statisticRmMessage_flashData";
	}
	
	/**
	 * 跳转到导入页
	 */
	@RequestMapping(value = "import", method = RequestMethod.GET)
	public String importDataForm(Model model) {
		return "message/importRmMessage";
	}
	/**
	 * 执行导入
	 */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importData(Model model) {
		model.addAttribute("isSubmit", "1");
		return "message/importRmMessage";
	}
	/**
	 * 定制导出
	 */
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportCustomForm(Model model) {
		return "message/exportRmMessage_custom";
	}
	/**
	 * 执行导出
	 */
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportData(Model model) {
		return "message/exportRmMessage_excel";
	}
	/**
	 * 跳转到Ajax页
	 */
	@RequestMapping(value = "ajax")
	public String ajax(Model model) {
		return "message/ajax/listRmMessage";
	}

	
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
			List<String> lQuery = new ArrayList<String>();
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".biz_keyword", request.getParameter("biz_keyword"), RmSqlHelper.TYPE_CHAR_LIKE));
				
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".sender_id", request.getParameter("sender_id"), RmSqlHelper.TYPE_CHAR_LIKE));
				
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".parent_message_id", request.getParameter("parent_message_id"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
				
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".owner_org_id", request.getParameter("owner_org_id"), RmSqlHelper.TYPE_CHAR_LIKE));
				
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".template_id", request.getParameter("template_id"), RmSqlHelper.TYPE_CHAR_LIKE));
				
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".is_affix", request.getParameter("is_affix"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
				
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".record_id", request.getParameter("record_id"), RmSqlHelper.TYPE_CHAR_LIKE));
				
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".message_xml_context", request.getParameter("message_xml_context"), RmSqlHelper.TYPE_CHAR_LIKE));
				
			queryCondition = RmSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
        }
        return queryCondition;
    }

	/**
	 * 跳转到中间表RM_M_MESSAGE_USER页
	 */
	@RequestMapping(value = "rm_m_message_user")
	public String rm_m_message_user(Model model) {
		return "message/middle/listRm_m_message_user";
	}
	
    /**
     * 插入中间表RM_M_MESSAGE_USER数据
     */
	@RequestMapping(value = "insertRm_m_message_user", method = RequestMethod.POST)
    public String insertRm_m_message_user(HttpServletRequest request, @Valid RmMessageVo vo, RedirectAttributes redirectAttributes) {
    	Long message_id = new Long(request.getParameter("message_id"));
    	Long[] user_ids = RmStringHelper.parseToLongArray(request.getParameter("user_ids"));
    	int count = rmMessageService.insertRm_m_message_user(message_id, user_ids).length;
    	redirectAttributes.addFlashAttribute("message", "插入了" + count + "条记录!");
    	redirectAttributes.addAttribute("message_id", message_id);
    	return "redirect:/message/rm_m_message_user";
    }
    
    /**
     * 删除中间表RM_M_MESSAGE_USER数据
     */
	@RequestMapping(value = "deleteRm_m_message_user", method = RequestMethod.POST)
    public String deleteRm_m_message_user(HttpServletRequest request, @Valid RmMessageVo vo, RedirectAttributes redirectAttributes) {
    	Long message_id = new Long(request.getParameter("message_id"));
    	Long[] user_ids = RmStringHelper.parseToLongArray(request.getParameter("user_ids"));
    	int count = rmMessageService.deleteRm_m_message_user(message_id, user_ids);
    	redirectAttributes.addFlashAttribute("message", "删除了" + count + "条记录!");
    	redirectAttributes.addAttribute("message_id", message_id);
    	return "redirect:/message/rm_m_message_user";
    }

    /**
     * 从页面表单获取信息注入vo，并修改单条记录
     */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String modify(HttpServletRequest request, @Valid RmMessageVo vo, Errors errors,RedirectAttributes redirectAttributes) {
		update(request, vo, errors);
		return "redirect:/message";
	}

}
