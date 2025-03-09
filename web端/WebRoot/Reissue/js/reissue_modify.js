$(function () {
	$.ajax({
		url : "Reissue/" + $("#reissue_reissueId_edit").val() + "/update",
		type : "get",
		data : {
			//reissueId : $("#reissue_reissueId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (reissue, response, status) {
			$.messager.progress("close");
			if (reissue) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#reissueModifyButton").click(function(){ 
		if ($("#reissueEditForm").form("validate")) {
			$("#reissueEditForm").form({
			    url:"Reissue/" +  $("#reissue_reissueId_edit").val() + "/update",
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
			$("#reissueEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
