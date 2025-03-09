$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('product_productDesc_edit');
	var product_productDesc_edit = UE.getEditor('product_productDesc_edit'); //电动车描述编辑器
	product_productDesc_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
  function ajaxModifyQuery() {	
	$.ajax({
		url : "Product/" + $("#product_productNo_edit").val() + "/update",
		type : "get",
		data : {
			//productNo : $("#product_productNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (product, response, status) {
			$.messager.progress("close");
			if (product) { 
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
				product_productDesc_edit.setContent(product.productDesc);
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

  }

	$("#productModifyButton").click(function(){ 
		if ($("#productEditForm").form("validate")) {
			$("#productEditForm").form({
			    url:"Product/" +  $("#product_productNo_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#productEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
