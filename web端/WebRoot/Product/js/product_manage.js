var product_manage_tool = null; 


$(function () { 
	initProductManageTool(); //建立Product管理对象
	product_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	
	$("#product_manage").datagrid({
		url : 'Product/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "productNo",
		sortOrder : "desc",
		toolbar : "#product_manage_tool",
		columns : [[
			{
				field : "productNo",
				title : "车牌号",
				width : 140,
			},
			{
				field : "productClassObj",
				title : "电动车类别",
				width : 140,
			},
			{
				field : "productName",
				title : "电动车名称",
				width : 140,
			},
			{
				field : "mainPhoto",
				title : "电动车主图",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "price",
				title : "电动车价格",
				width : 70,
			},
			{
				field : "addTime",
				title : "发布时间",
				width : 140,
			},
			{
				field : "userObj",
				title : "所属用户",
				width : 140,
			},
			{
				field : "proState",
				title : "电动车状态",
				width : 140,
			},
			{
				field : "rfid",
				title : "电动车RFID",
				width : 140,
			},
		]],
	});

	$("#productEditDiv").dialog({
		title : "修改管理",
		top: "10px",
		width : 1000,
		height : 600,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#productEditForm").form("validate")) {
					//验证表单 
					if(!$("#productEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#productEditForm").form({
						    url:"Product/" + $("#product_productNo_edit").val() + "/update",
						    onSubmit: function(){
								if($("#productEditForm").form("validate"))  {
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
			                        $("#productEditDiv").dialog("close");
			                        product_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#productEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#productEditDiv").dialog("close");
				$("#productEditForm").form("reset"); 
			},
		}],
	});
});

function initProductManageTool() {
	product_manage_tool = {
		init: function() {
			$.ajax({
				url : "ProductClass/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#productClassObj_classId_query").combobox({ 
					    valueField:"classId",
					    textField:"className",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{classId:0,className:"不限制"});
					$("#productClassObj_classId_query").combobox("loadData",data); 
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
			$("#product_manage").datagrid("reload");
		},
		redo : function () {
			$("#product_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#product_manage").datagrid("options").queryParams;
			queryParams["productNo"] = $("#productNo").val();
			queryParams["productClassObj.classId"] = $("#productClassObj_classId_query").combobox("getValue");
			queryParams["productName"] = $("#productName").val();
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["proState"] = $("#proState").val();
			queryParams["rfid"] = $("#rfid").val();
			$("#product_manage").datagrid("options").queryParams=queryParams; 
			$("#product_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#productQueryForm").form({
			    url:"Product/OutToExcel",
			});
			//提交表单
			$("#productQueryForm").submit();
		},
		remove : function () {
			var rows = $("#product_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var productNos = [];
						for (var i = 0; i < rows.length; i ++) {
							productNos.push(rows[i].productNo);
						}
						$.ajax({
							type : "POST",
							url : "Product/deletes",
							data : {
								productNos : productNos.join(","),
							},
							beforeSend : function () {
								$("#product_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#product_manage").datagrid("loaded");
									$("#product_manage").datagrid("load");
									$("#product_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#product_manage").datagrid("loaded");
									$("#product_manage").datagrid("load");
									$("#product_manage").datagrid("unselectAll");
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
			var rows = $("#product_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Product/" + rows[0].productNo +  "/update",
					type : "get",
					data : {
						//productNo : rows[0].productNo,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (product, response, status) {
						$.messager.progress("close");
						if (product) { 
							$("#productEditDiv").dialog("open");
							$("#product_productNo_edit").val(product.productNo);
							$("#product_productNo_edit").validatebox({
								required : true,
								missingMessage : "请输入车牌号",
								editable: false
							});
							$("#product_productClassObj_classId_edit").combobox({
								url:"ProductClass/listAll",
							    valueField:"classId",
							    textField:"className",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#product_productClassObj_classId_edit").combobox("select", product.productClassObjPri);
									//var data = $("#product_productClassObj_classId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#product_productClassObj_classId_edit").combobox("select", data[0].classId);
						            //}
								}
							});
							$("#product_productName_edit").val(product.productName);
							$("#product_productName_edit").validatebox({
								required : true,
								missingMessage : "请输入电动车名称",
							});
							$("#product_mainPhoto").val(product.mainPhoto);
							$("#product_mainPhotoImg").attr("src", product.mainPhoto);
							$("#product_price_edit").val(product.price);
							$("#product_price_edit").validatebox({
								required : true,
								validType : "number",
								missingMessage : "请输入电动车价格",
								invalidMessage : "电动车价格输入不对",
							});
							product_productDesc_editor.setContent(product.productDesc, false);
							$("#product_addTime_edit").datetimebox({
								value: product.addTime,
							    required: true,
							    showSeconds: true,
							});
							$("#product_userObj_user_name_edit").combobox({
								url:"UserInfo/listAll",
							    valueField:"user_name",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#product_userObj_user_name_edit").combobox("select", product.userObjPri);
									//var data = $("#product_userObj_user_name_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#product_userObj_user_name_edit").combobox("select", data[0].user_name);
						            //}
								}
							});
							$("#product_proState_edit").val(product.proState);
							$("#product_proState_edit").validatebox({
								required : true,
								missingMessage : "请输入电动车状态",
							});
							$("#product_rfid_edit").val(product.rfid);
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



function readRfid() {
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
				$("#product_rfid_edit").val(result.msg);
				
			} else {
				$.messager.alert("错误！", result.msg, "warning");
			}
		}
	});				
} 


