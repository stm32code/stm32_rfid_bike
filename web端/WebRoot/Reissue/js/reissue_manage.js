var reissue_manage_tool = null; 
$(function () { 
	initReissueManageTool(); //建立Reissue管理对象
	reissue_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#reissue_manage").datagrid({
		url : 'Reissue/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "reissueId",
		sortOrder : "desc",
		toolbar : "#reissue_manage_tool",
		columns : [[
			{
				field : "reissueId",
				title : "补办id",
				width : 70,
			},
			{
				field : "proObj",
				title : "车牌号",
				width : 140,
			},
			{
				field : "userObj",
				title : "补办用户",
				width : 140,
			},
			{
				field : "reissueTime",
				title : "补办时间",
				width : 140,
			},
			{
				field : "ememo",
				title : "补办原因",
				width : 140,
			},
			{
				field : "state",
				title : "处理状态",
				width : 140,
			},
			{
				field : "rfid",
				title : "rfid",
				width : 140,
			},
		]],
	});

	$("#reissueEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#reissueEditForm").form("validate")) {
					//验证表单 
					if(!$("#reissueEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#reissueEditForm").form({
						    url:"Reissue/" + $("#reissue_reissueId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#reissueEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#reissueEditDiv").dialog("close");
			                        reissue_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#reissueEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#reissueEditDiv").dialog("close");
				$("#reissueEditForm").form("reset"); 
			},
		}],
	});
});

function initReissueManageTool() {
	reissue_manage_tool = {
		init: function() {
			$.ajax({
				url : "Product/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#proObj_productNo_query").combobox({ 
					    valueField:"productNo",
					    textField:"productNo",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{productNo:"",productNo:"不限制"});
					$("#proObj_productNo_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#userObj_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",name:"不限制"});
					$("#userObj_user_name_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#reissue_manage").datagrid("reload");
		},
		redo : function () {
			$("#reissue_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#reissue_manage").datagrid("options").queryParams;
			queryParams["proObj.productNo"] = $("#proObj_productNo_query").combobox("getValue");
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["reissueTime"] = $("#reissueTime").datebox("getValue"); 
			queryParams["state"] = $("#state").val();
			queryParams["rfid"] = $("#rfid").val();
			$("#reissue_manage").datagrid("options").queryParams=queryParams; 
			$("#reissue_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#reissueQueryForm").form({
			    url:"Reissue/OutToExcel",
			});
			//提交表单
			$("#reissueQueryForm").submit();
		},
		remove : function () {
			var rows = $("#reissue_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var reissueIds = [];
						for (var i = 0; i < rows.length; i ++) {
							reissueIds.push(rows[i].reissueId);
						}
						$.ajax({
							type : "POST",
							url : "Reissue/deletes",
							data : {
								reissueIds : reissueIds.join(","),
							},
							beforeSend : function () {
								$("#reissue_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#reissue_manage").datagrid("loaded");
									$("#reissue_manage").datagrid("load");
									$("#reissue_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#reissue_manage").datagrid("loaded");
									$("#reissue_manage").datagrid("load");
									$("#reissue_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#reissue_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Reissue/" + rows[0].reissueId +  "/update",
					type : "get",
					data : {
						//reissueId : rows[0].reissueId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (reissue, response, status) {
						$.messager.progress("close");
						if (reissue) { 
							$("#reissueEditDiv").dialog("open");
							$("#reissue_reissueId_edit").val(reissue.reissueId);
							$("#reissue_reissueId_edit").validatebox({
								required : true,
								missingMessage : "请输入补办id",
								editable: false
							});
							$("#reissue_proObj_productNo_edit").combobox({
								url:"Product/listAll",
							    valueField:"productNo",
							    textField:"productNo",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#reissue_proObj_productNo_edit").combobox("select", reissue.proObjPri);
									//var data = $("#reissue_proObj_productNo_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#reissue_proObj_productNo_edit").combobox("select", data[0].productNo);
						            //}
								}
							});
							$("#reissue_userObj_user_name_edit").combobox({
								url:"UserInfo/listAll",
							    valueField:"user_name",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#reissue_userObj_user_name_edit").combobox("select", reissue.userObjPri);
									//var data = $("#reissue_userObj_user_name_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#reissue_userObj_user_name_edit").combobox("select", data[0].user_name);
						            //}
								}
							});
							$("#reissue_reissueTime_edit").datetimebox({
								value: reissue.reissueTime,
							    required: true,
							    showSeconds: true,
							});
							$("#reissue_ememo_edit").val(reissue.ememo);
							$("#reissue_state_edit").val(reissue.state);
							$("#reissue_state_edit").validatebox({
								required : true,
								missingMessage : "请输入处理状态",
							});
							$("#reissue_rfid_edit").val(reissue.rfid);
							$("#reissue_rfid_edit").validatebox({
								required : true,
								missingMessage : "请输入rfid",
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}


function readNewRfid() {
	$.ajax({
		url : "user/getRfid",
		type : "get",
		data : {
			//productNo : rows[0].productNo,
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (result, response, status) {
			$.messager.progress("close");
			if (result.success) { 
				$.messager.alert("成功","刷卡成功！");
				$("#reissue_rfid_edit").val(result.msg);
				
			} else {
				$.messager.alert("错误！", result.msg, "warning");
			}
		}
	});				
} 

