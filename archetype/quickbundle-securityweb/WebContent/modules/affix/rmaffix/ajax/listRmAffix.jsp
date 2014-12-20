<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.config.RmConfig"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=IRmAffixConstants.TABLE_NAME_CHINESE%></title>
<jsp:include page="/jsp/include/rmGlobalAjax.jsp" />
<script type="text/javascript">
Ext.onReady(function(){
	//枚举下拉参照，用于表格编辑
	var rm_mime_type = new fm.ComboBox({
        id: 'rm_mime_type',
        typeAhead: true,
        triggerAction: 'all',
        transform: 'rmData_mime_type',
        lazyRender: true,
        valueField: 'mime_type',
        value: '',
        emptyText: '',
        listClass: 'x-combo-list-small'
    });

	//枚举下拉参照，用于查询
	var rmQuery_mime_type = new fm.ComboBox({
        id: 'rmQuery_mime_type',
        typeAhead: true,
        triggerAction: 'all',
        transform: 'mime_type',
        lazyRender: true,
        listClass: 'x-combo-list-small',
        width:270
    });

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
	                columnWidth:.33,
	                layout: 'form',
	                items: [{xtype: 'textfield', fieldLabel: '业务关键字', name: 'bs_keyword', anchor:'95%'}]
	            },
	            {
	                columnWidth:.33,
	                layout: 'form',
	                items: [{xtype:'textfield', fieldLabel: '主记录ID', name: 'record_id', anchor:'95%'}]
	            }, 
	            {
	            	columnWidth:.33,
	            	layout: 'form',
	                items: [{
	                    xtype: 'compositefield',
	                    fieldLabel: '顺序数',
	                    items: [
	                        {xtype: 'textfield', name: 'order_number_from'},
	                        {xtype: 'displayfield', value: '到'},
	                        {xtype: 'textfield',    name: 'order_number_to'}
	                    ]
	                }]
	            }
            ]
        },
        {
            layout:'column',
            items:[
	            {
	                columnWidth:.33,
	                layout: 'form',
	                items: [{xtype:'textfield', fieldLabel: '标题', name: 'title', anchor:'95%'}]
	            },
	            {
	                columnWidth:.33,
	                layout: 'form',
	                items: [{xtype:'textfield', fieldLabel: '原文件名', name: 'old_name', anchor:'95%'}]
	            },
				{
	                columnWidth:.33,
	                layout: 'form',
	                items: [{xtype:'textfield', fieldLabel: '真实存储路径', name: 'save_name', anchor:'95%'}]
	            }
            ]
        },
        {
            layout:'column',
            items:[
	            {
	            	columnWidth:.33,
	            	layout: 'form',
	                items: [{
	                    xtype: 'compositefield',
	                    fieldLabel: '真实文件大小',
	                    items: [
	                        {xtype: 'textfield', name: 'save_size_from'},
	                        {xtype: 'displayfield', value: '到'},
	                        {xtype: 'textfield',    name: 'save_size_to'}
	                    ]
	                }]
	            },
	            {
	            	columnWidth:.33,
	            	layout: 'form',
					width:300,
	                items: [{
	                    xtype: 'compositefield',
	                    name: 'mime_type',
	                    fieldLabel: '内容类型',
	                    items: [rmQuery_mime_type]
	                }]
	            },
	            {
	            	columnWidth:.33,
	            	layout: 'form',
	                items: [{
	                    xtype: 'compositefield',
	                    fieldLabel: '修改日期',
	                    items: [
	                        {xtype: 'datefield', format:'Y-m-d', width: 100, name: 'modify_date_from'},
	                        {xtype: 'displayfield', value: '到'},
	                        {xtype: 'datefield', format:'Y-m-d', width: 100, name: 'modify_date_to'}
	                    ]
	                }]
	            }
            ]
        },
        {
            layout:'column',
            items:[{
            	columnWidth:.33,
            	layout: 'form',
                items: [
                	{xtype: 'referencefield', fieldLabel: '用户ID', anchor:'95%', name: 'user_id', urlPath: '<%=request.getContextPath()%>/RmUserAction.do?cmd=queryReference'}
                ]
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
            '<div style="padding:5px 0px 5px 45px">{title}</div>'
        )
    });

    //带锁定功能的列定义
    var columnModel = new Ext.ux.grid.LockingColumnModel({
        defaults: {
            sortable: true           
        },
        columns: [
            selectionModel,
            expander,
            {
                header: '<%=IRmAffixConstants.TABLE_COLUMN_CHINESE.get("id")%>',
                dataIndex: 'id',
                width: 0 //主键默认隐藏
            },
            {
                header: '业务关键字',
                dataIndex: 'bs_keyword',
                editor: new fm.TextField({ allowBlank: false })
            },
            {
                header: '主记录ID',
                dataIndex: 'record_id',
                width: 130,
                editor: new fm.TextField({ allowBlank: false })
            },
            {
                header: '顺序数',
                dataIndex: 'order_number',
                width: 50,
                editor: new fm.NumberField({ allowBlank: false, maxValue: 100000 })
            },
            {
                header: '标题',
                dataIndex: 'title',
                width: 300,
                editor: new fm.TextField({ allowBlank: false })
            },
            {
                header: '原文件名',
                dataIndex: 'old_name',
                editor: new fm.TextField({ allowBlank: false })
            },
            {
                header: '真实存储路径',
                dataIndex: 'save_name',
                editor: new fm.ReferenceField({ allowBlank: false, urlPath: '<%=request.getContextPath()%>/RmUserAction.do?cmd=queryReference' })
            },
            {
                header: '真实文件大小',
                dataIndex: 'save_size',
                editor: new fm.NumberField({ allowBlank: true })
            },
            {
                header: '内容类型',
                dataIndex: 'mime_type',
                width: 200,
                editor: rm_mime_type,
                renderer: Ext.util.Format.comboRenderer(rm_mime_type)
            },
            {
                header: '修改日期',
                dataIndex: 'modify_date',
                width: 130,
                renderer: formatDate,
                editor: new fm.DateField({ format: 'Y-m-d' })
            }
        ]
    });

	//主表格对应的数据
	var store = new Ext.data.JsonStore({
	    autoDestroy: true,
	    proxy: new Ext.data.HttpProxy({
	        url: '<%=request.getContextPath()%>/RmAffixAjaxAction.do?cmd=simpleQuery'
	    }),
        remoteSort: true,
	    root: '<%=IGlobalConstants.REQUEST_BEANS%>',
	    idProperty: 'id',
        totalProperty: 'totalCount',
	    fields: [
	    	{name: 'id'},
	    	{name: 'bs_keyword'},
	    	{name: 'record_id'},
	    	{name: 'order_number', type: 'float'},
	    	{name: 'title'},
	    	{name: 'old_name'},
	    	{name: 'save_name'},
	    	{name: 'save_size', type: 'float'},
	    	{name: 'mime_type'},
	    	{name: 'modify_date', type:'date', dateFormat:'Y-m-d H:i:s'}
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
        title: '<%=IRmAffixConstants.TABLE_NAME_CHINESE%>列表',
        store: store,
        colModel: columnModel,
        sm: selectionModel,
        height: 500,
        clicksToEdit: 1, //单击进入编辑状态
        plugins: expander,
        bbar: pagingToolbar,
        renderTo: 'grid',
        view: new Ext.ux.grid.LockingGridView(),
        tbar: [{
            id: 'tbar_save',
            text: '保存',
            icon: '<%=request.getContextPath()%>/images/icon/save.gif',
            //disabled: true,
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
       					params["bs_keyword" + sk + i] = rec.get('bs_keyword');
       					params["record_id" + sk + i] = rec.get('record_id');
       					params["order_number" + sk + i] = rec.get('order_number');
       					params["title" + sk + i] = rec.get('title');
       					params["old_name" + sk + i] = rec.get('old_name');
       					params["save_name" + sk + i] = rec.get('save_name');
       					params["save_size" + sk + i] = rec.get('save_size');
       					params["mime_type" + sk + i] = rec.get('mime_type');
   					}
<%}%>
           			Ext.Ajax.request({
	           			url: '<%=request.getContextPath()%>/RmAffixAjaxAction.do?cmd=insertUpdateBatch', 
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
	           			failure: function(response){
		           			Ext.Msg.alert("警告", "数据更新失败，请稍后再试！");
	           			}
           			}); 
         		} else {
         			Ext.Msg.alert("警告", "没有需要保存的数据！");
         		}
          	}
       	},
       	{
       		id: 'tbar_cancel',
            text: '取消',
            icon: '<%=request.getContextPath()%>/images/icon/cancel.gif',
            handler : function(){
       			store.rejectChanges();
       			Ext.example.msg('消息', "取消了当前表格的修改");
          	}
       	},
       	{
       		id: 'tbar_addRow',
            text: '增行',
            icon: '<%=request.getContextPath()%>/images/icon/add.gif',
            handler : function(){
                var RmAffix = grid.getStore().recordType;
                var p = new RmAffix({  });
                grid.stopEditing();
                store.insert(store.getCount(), p);
                grid.startEditing(store.getCount()-1, 2);
          	}
       	},
       	{
       		id: 'tbar_remove',
            text: '删除',
            icon: '<%=request.getContextPath()%>/images/icon/delete.gif',
            handler : function(){
        		var selModel = grid.getSelectionModel();
				if (selModel.hasSelection()) {
					Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
						if (button == "yes"){
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
				           			url: '<%=request.getContextPath()%>/RmAffixAjaxAction.do?cmd=deleteMulti', 
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
       		id: 'tbar_query',
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
       		id: 'tbar_refresh',
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
	<!-- 绑定主表格 -->
    <div id="grid"></div>
    <!-- 绑定查询条件输入表单 -->
    <div id="formQuery" style="display:none"></div>
    <%
    	out.print(RmJspHelper.getSelectField("rmData_mime_type", -1, org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE), "", "style='display: none;'", false));
    	out.print(RmJspHelper.getSelectField("mime_type", -1, org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE), "", "style='display: none;'", true));
    %>
</body>
</html> 