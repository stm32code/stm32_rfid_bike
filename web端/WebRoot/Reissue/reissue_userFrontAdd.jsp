<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.card.po.Product" %>
<%@ page import="com.card.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>补办申请添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>Reissue/frontlist">补办申请列表</a></li>
			    	<li role="presentation" class="active"><a href="#reissueAdd" aria-controls="reissueAdd" role="tab" data-toggle="tab">添加补办申请</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="reissueList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="reissueAdd"> 
				      	<form class="form-horizontal" name="reissueAddForm" id="reissueAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="reissue_proObj_productNo" class="col-md-2 text-right">车牌号:</label>
						  	 <div class="col-md-8">
							    <select id="reissue_proObj_productNo" name="reissue.proObj.productNo" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group" style="display:none;">
						  	 <label for="reissue_userObj_user_name" class="col-md-2 text-right">补办用户:</label>
						  	 <div class="col-md-8">
							    <select id="reissue_userObj_user_name" name="reissue.userObj.user_name" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="reissue_reissueTimeDiv" class="col-md-2 text-right">补办时间:</label>
						  	 <div class="col-md-8">
				                <div id="reissue_reissueTimeDiv" class="input-group date reissue_reissueTime col-md-12" data-link-field="reissue_reissueTime">
				                    <input class="form-control" id="reissue_reissueTime" name="reissue.reissueTime" size="16" type="text" value="" placeholder="请选择补办时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="reissue_ememo" class="col-md-2 text-right">补办原因:</label>
						  	 <div class="col-md-8">
							    <textarea id="reissue_ememo" name="reissue.ememo" rows="8" class="form-control" placeholder="请输入补办原因"></textarea>
							 </div>
						  </div>
						  <div class="form-group" style="display:none;">
						  	 <label for="reissue_state" class="col-md-2 text-right">处理状态:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="reissue_state" name="reissue.state" value="待处理" class="form-control" placeholder="请输入处理状态">
							 </div>
						  </div>
						  <div class="form-group" style="display:none;">
						  	 <label for="reissue_rfid" class="col-md-2 text-right">rfid:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="reissue_rfid" name="reissue.rfid" class="form-control" placeholder="请输入rfid">
							 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxReissueAdd();" class="btn btn-primary bottom5 top5">申请补办</span>
				          </div>
						</form> 
				        <style>#reissueAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
		</div>
	</div> 
</div>

<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加补办申请信息
	function ajaxReissueAdd() { 
		//提交之前先验证表单
		$("#reissueAddForm").data('bootstrapValidator').validate();
		if(!$("#reissueAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Reissue/userAdd",
			dataType : "json" , 
			data: new FormData($("#reissueAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#reissueAddForm").find("input").val("");
					$("#reissueAddForm").find("textarea").val("");
				} else {
					alert(obj.message);
				}
			},
			processData: false, 
			contentType: false, 
		});
	} 
$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse > a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();
	//验证补办申请添加表单字段
	$('#reissueAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"reissue.reissueTime": {
				validators: {
					notEmpty: {
						message: "补办时间不能为空",
					}
				}
			},
			"reissue.state": {
				validators: {
					notEmpty: {
						message: "处理状态不能为空",
					}
				}
			},
			 
		}
	}); 
	//初始化车牌号下拉框值 
	$.ajax({
		url: basePath + "Product/listAll",
		type: "get",
		success: function(products,response,status) { 
			$("#reissue_proObj_productNo").empty();
			var html="";
    		$(products).each(function(i,product){
    			html += "<option value='" + product.productNo + "'>" + product.productNo + "</option>";
    		});
    		$("#reissue_proObj_productNo").html(html);
    		$("#reissue_proObj_productNo").val("<%=request.getParameter("productNo") %>");
    	}
	});
	//初始化补办用户下拉框值 
	$.ajax({
		url: basePath + "UserInfo/listAll",
		type: "get",
		success: function(userInfos,response,status) { 
			$("#reissue_userObj_user_name").empty();
			var html="";
    		$(userInfos).each(function(i,userInfo){
    			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
    		});
    		$("#reissue_userObj_user_name").html(html);
    	}
	});
	//补办时间组件
	$('#reissue_reissueTimeDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#reissueAddForm').data('bootstrapValidator').updateStatus('reissue.reissueTime', 'NOT_VALIDATED',null).validateField('reissue.reissueTime');
	});
})
</script>
</body>
</html>
