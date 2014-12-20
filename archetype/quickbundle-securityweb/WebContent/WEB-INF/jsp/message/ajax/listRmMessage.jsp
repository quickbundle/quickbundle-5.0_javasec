<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.config.RmConfig"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@ page import="org.quickbundle.modules.message.IRmMessageConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=IRmMessageConstants.TABLE_NAME_DISPLAY%></title>
<jsp:include page="/jsp/include/rmGlobalAjax.jsp" />
<script type="text/javascript">
Ext.onReady(function(){

    //查询条件输入表单
    var formQuery = new Ext.form.FormPanel({
        renderTo: 'formQuery',
        labelAlign: 'top',
        frame:true,
        bodyStyle: 'padding: 1px',
        items: [
		{
			layout:'column',
			items:[
				{
					columnWidth: .33,
					layout: 'form',
					items: [{xtype: 'textfield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%>', name: 'biz_keyword', anchor:'95%'}]
				},
				{
					columnWidth: .33,
					layout: 'form',
					items: [{xtype: 'textfield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>', name: 'sender_id', anchor:'95%'}]
				},
				{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'referencefield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%>', anchor:'95%', name: 'parent_message_id', 
						urlPath: '<%=request.getContextPath()%>/api/message/reference?referenceInputType=radio'
					}]
				}
            ]
        },
		{
			layout:'column',
			items:[
				{
					columnWidth: .33,
					layout: 'form',
					items: [{xtype: 'textfield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%>', name: 'owner_org_id', anchor:'95%'}]
				},
				{
					columnWidth: .33,
					layout: 'form',
					items: [{xtype: 'textfield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>', name: 'template_id', anchor:'95%'}]
				},
				{
					columnWidth: .33,
					layout: 'form',
					items: [{xtype: 'textfield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%>', name: 'is_affix', anchor:'95%'}]
				}
            ]
        },
		{
			layout:'column',
			items:[
				{
					columnWidth: .33,
					layout: 'form',
					items: [{xtype: 'textfield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%>', name: 'record_id', anchor:'95%'}]
				},
				{
					columnWidth: .33,
					layout: 'form',
					items: [{xtype: 'textfield', fieldLabel: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%>', name: 'message_xml_context', anchor:'95%'}]
				}
            ]
        }
		
        ],

        buttons: [{
            text: '查询',
            handler: function() {
                if (formQuery.form.isValid()) {
                    store.load();
                    windowQuery.hide();
                }
            }
        },{
            text: '清空',
            handler: function() {
        		formQuery.form.reset();
            }
        },{
            text: '关闭',
            handler: function() {
        		windowQuery.hide();
            }
        }]
    });

    //查询条件对话框
    var windowQuery = new Ext.Window({
        title: '查询条件输入',
        collapsible: true,
        maximizable: true,
        width: 900,
        height: 500,
        layout: 'fit',
        plain: true,
        bodyStyle: 'padding:5px;',
        buttonAlign: 'center',
        items: formQuery
    });

    //每一行的checkbox框
	var selectionModel = new Ext.grid.CheckboxSelectionModel({});

	//每一行的展开+号
    var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<div style="padding:5px 0px 5px 45px">{id}</div>'
        )
    });

    //带锁定功能的列定义
    var columnModel = new Ext.ux.grid.LockingColumnModel({
        defaults: {
            sortable: true           
        },
        columns: [
            selectionModel,
            //expander, //可点击展开的+号
            {
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("id")%>',
                dataIndex: 'id',
                width: 0 //主键默认隐藏
            },
		
		
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%>',
                dataIndex: 'biz_keyword',
                editor: new fm.TextField({ allowBlank: false })
			},
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>',
                dataIndex: 'sender_id',
                editor: new fm.TextField({ allowBlank: false })
			},
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%>',
                dataIndex: 'parent_message_id',
				editor: new fm.ReferenceField({ urlPath: '<%=request.getContextPath()%>/api/message/reference?referenceInputType=radio' })
			},
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%>',
                dataIndex: 'owner_org_id',
                editor: new fm.TextField({  })
			},
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>',
                dataIndex: 'template_id',
                editor: new fm.TextField({  })
			},
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%>',
                dataIndex: 'is_affix',
                editor: new fm.TextField({  })
			},
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%>',
                dataIndex: 'record_id',
                editor: new fm.TextField({  })
			},
			{
                header: '<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%>',
                dataIndex: 'message_xml_context',
                editor: new fm.TextField({  })
			}
		
        ]
    });

	//主表格对应的数据
	var store = new Ext.data.JsonStore({
	    autoDestroy: true,
	    
	    proxy: new Ext.data.HttpProxy({
	        url: '<%=request.getContextPath()%>/api/message',
	    	method: 'GET'
	    }),
        remoteSort: true,
	    root: '<%=IGlobalConstants.REQUEST_BEANS%>',
	    idProperty: 'id',
        totalProperty: 'totalCount',
	    fields: [
			{name: 'id'},
			{name: 'biz_keyword'},
			{name: 'sender_id'},
			{name: 'parent_message_id'},
			{name: 'owner_org_id'},
			{name: 'template_id'},
			{name: 'is_affix'},
			{name: 'record_id'},
			{name: 'message_xml_context'},
			{name: 'usable_status'},
			{name: 'modify_date', type:'date', dateFormat:'Y-m-d H:i:s'},
			{name: 'modify_ip'},
			{name: 'modify_user_id'}
		
		]
	});

	//状态栏提示
	var statusBar = new Ext.menu.TextItem({
		text: ''
	});

	//分页工具栏
	var pagingToolbar = new Ext.PagingToolbar({
        pageSize: <%=RmConfig.getSingleton().getDefaultPageSize()%>,
        store: store,
        displayInfo: true,
        displayMsg: '第{0}－{1}条 / 共{2}条',
        emptyMsg: "没有相关记录！",
        items:[
            '-',
            {
	            pressed: true,
	            enableToggle:true,
	            text: '预览',
	            cls: 'x-btn-text-icon details',
	            toggleHandler: function(btn, pressed){
	                var view = grid.getView();
	                view.showPreview = pressed;
	                view.refresh();
	            }
        	},
        	'&nbsp;&nbsp;',
			statusBar
		]
    });

	//主表格定义
    var grid = new Ext.grid.EditorGridPanel({
        title: '<%=IRmMessageConstants.TABLE_NAME_DISPLAY%>列表',
        store: store,
        colModel: columnModel,
        sm: selectionModel,
        height: 500,
        clicksToEdit: 1, //单击进入编辑状态
        //plugins: expander, //可点击展开的+号
        bbar: pagingToolbar,
        renderTo: 'grid',
        view: new Ext.ux.grid.LockingGridView(),
        tbar: [{
            text: '保存',
            icon: '<%=request.getContextPath()%>/images/icon/save.gif',
			handler : function(){
    			var records = store.getModifiedRecords();
          		if(records != null && records.length > 0){
          			var params = {};
<%if(RmConfig.getSingleton().isSubmitJson()) {%>
					var json = [];
					Ext.each(records, function(item) {
						json.push(item.data);
					});
       				params["<%=IGlobalConstants.RM_AJAX_JSON%>"] = Ext.util.JSON.encode(json);
<%} else {%> 
       				params["<%=IGlobalConstants.RM_AJAX_RECORD_SIZE%>"] = records.length;
       				var sk = "<%=IGlobalConstants.RM_AJAX_SPLIT_KEY%>";
   					for(var i=0; i<records.length; i++) {
       					var rec = records[i];
						params["id" + sk + i] = rec.get('id');
						params["biz_keyword" + sk + i] = rec.get('biz_keyword');
						params["sender_id" + sk + i] = rec.get('sender_id');
						params["parent_message_id" + sk + i] = rec.get('parent_message_id');
						params["owner_org_id" + sk + i] = rec.get('owner_org_id');
						params["template_id" + sk + i] = rec.get('template_id');
						params["is_affix" + sk + i] = rec.get('is_affix');
						params["record_id" + sk + i] = rec.get('record_id');
						params["message_xml_context" + sk + i] = rec.get('message_xml_context');
   					}
<%}%>
           			Ext.Ajax.request({
	           			url: '<%=request.getContextPath()%>/api/message/batch',
	           			method: 'POST',
	           			params: params,
	           			success: function(response) {
			           		var obj = Ext.decode(response.responseText);
			           		if(obj.error != null) {
			           			Ext.Msg.alert("业务异常", obj.error);
			           		} else if(obj.<%=IGlobalConstants.EXECUTE_ROW_COUNT%> != null){
				           		var int_insert_update = obj.<%=IGlobalConstants.EXECUTE_ROW_COUNT%>;
								Ext.Msg.alert("信息", "数据保存成功，新增" + int_insert_update[0] + "条，修改" + (int_insert_update[1] < 0 ? (records.length-int_insert_update[0]) : int_insert_update[1]) + "条", function() {
									store.commitChanges();
									store.reload();
								});
			           		}
	           			},
	           			failure: function(response) {
		           			Ext.Msg.alert("警告", "数据更新失败，请稍后再试！");
	           			}
           			}); 
         		} else {
         			Ext.Msg.alert("警告", "没有需要保存的数据！");
         		}
          	}
       	},
       	{
            text: '取消',
            icon: '<%=request.getContextPath()%>/images/icon/cancel.gif',
            handler : function(){
       			store.rejectChanges();
       			Ext.example.msg('消息', "取消了当前表格的修改");
          	}
       	},
       	{
            text: '增行',
            icon: '<%=request.getContextPath()%>/images/icon/add.gif',
            handler : function(){
                var RmAffix = grid.getStore().recordType;
                var p = new RmAffix({
				<%
                	int index = 0;
                	for(Object key : request.getParameterMap().keySet()) {
                		if(index > 0) {
                			out.print(",\n");
                		}
                		out.print(key + ": '" + RmStringHelper.replaceStringToScript(request.getParameter(key.toString())) + "'");
                		index ++;
                	}
                %>
                });
                grid.stopEditing();
                store.insert(store.getCount(), p);
                grid.startEditing(store.getCount()-1, 2);
          	}
       	},
       	{
            text: '删除',
            icon: '<%=request.getContextPath()%>/images/icon/delete.gif',
            handler : function(){
        		var selModel = grid.getSelectionModel();
				if (selModel.hasSelection()) {
					Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
						if (button == "yes") {
							var ids = "";
							var selections = selModel.getSelections();
							Ext.each(selections, function(item) {
								if(item.get('id') != null) {
									ids += item.get('id') + ",";
								}
							});
							ids = ids.replace(/,$/g, "");
							if(ids != '') {
			           			Ext.Ajax.request({
				           			url: '<%=request.getContextPath()%>/api/message/delete', 
				           			method: 'POST',
				           			params: {ids: ids},
				           			success: function(response) {
										Ext.Msg.alert("信息", "数据删除成功", function() {
											store.remove(selections);
										});
				           			},
				           			failure: function(response) {
					           			Ext.Msg.alert("警告", "数据删除失败，请稍后再试！");
				           			}
			           			}); 
							} else {
								store.remove(selections);
							}
						}
					});
				} else {
					Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
				}
          	}
       	},
       	{
            text: '查询',
            icon: '<%=request.getContextPath()%>/images/icon/search.gif',
            handler : function(){
            	if(windowQuery.hidden) {
            	    windowQuery.show();
            	} else {
        			windowQuery.hide();
            	}
          	}
       	},
       	{
       		xtype:'splitbutton',
            text: '刷新',
            icon: '<%=request.getContextPath()%>/images/icon/refresh.gif',
            handler : function(){
       			store.reload();
          	},
          	menu: [{
				text: '首页', 
				icon: '<%=request.getContextPath()%>/images/icon/s.gif',
				handler: function() {
					pagingToolbar.moveFirst();
				}
			},
			{
				text: '上一页', 
				icon: '<%=request.getContextPath()%>/images/icon/s.gif',
				handler: function() {
					pagingToolbar.movePrevious();
				}
			},
			{
				text: '下一页', 
				icon: '<%=request.getContextPath()%>/images/icon/s.gif',
				handler: function() {
					pagingToolbar.moveNext();
				}
			},
			{
				text: '末页', 
				icon: '<%=request.getContextPath()%>/images/icon/s.gif',
				handler: function() {
					pagingToolbar.moveLast();
				}
			}]
       	}]
    });

	//在读取json数据前预置parameter参数和formQuery字段
    store.on("beforeload", function(thiz, options) {
    	statusBar.update("");
        Ext.iterate(formQuery.form.getValues(), function(key, value) {
        	thiz.baseParams[key] = value;
        }, this);
        <%
        	for(Object key : request.getParameterMap().keySet()) {
        		out.println("thiz.baseParams['" + key + "'] = '" + RmStringHelper.replaceStringToScript(request.getParameter(key.toString())) + "';");
        	}
        %>
    });

	//载入json数据后更新状态栏
    store.on("load", function(thiz, options) {
        statusBar.update("数据载入完毕！");
		window.setTimeout(function() { statusBar.update(''); }, 2000);
    });

	//手动载入数据
    store.load();
});
</script>
</head>
<body>
	<%-- 绑定主表格 --%>
    <div id="grid"></div>
    <%-- 绑定查询条件输入表单 --%>
    <div id="formQuery" style="display:none"></div>
    <%

    %>
</body>
</html> 