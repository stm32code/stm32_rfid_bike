$(function () {
	$("#reissue_proObj_productNo").combobox({
	    url:'Product/listAll',
	    valueField: "productNo",
	    textField: "productNo",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#reissue_proObj_productNo").combobox("getData"); 
            if (data.length > 0) {
                $("#reissue_proObj_productNo").combobox("select", data[0].productNo);
            }
        }
	});
	$("#reissue_userObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#reissue_userObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#reissue_userObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#reissue_reissueTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#reissue_state").validatebox({
		required : true, 
		missingMessage : '请输入处理状态',
	});

	$("#reissue_rfid").validatebox({
		required : true, 
		missingMessage : '请输入rfid',
	});

	//单击添加按钮
	$("#reissueAddButton").click(function () {
		//验证表单 
		if(!$("#reissueAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#reissueAddForm").form({
			    url:"Reissue/add",
			    onSubmit: function(){
					if($("#reissueAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#reissueAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#reissueAddForm").submit();
		}
	});

	//单击清空按钮
	$("#reissueClearButton").click(function () { 
		$("#reissueAddForm").form("clear"); 
	});
});
