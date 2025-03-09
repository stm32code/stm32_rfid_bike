<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.card.po.Reissue" %>
<%@ page import="com.card.po.Product" %>
<%@ page import="com.card.po.UserInfo" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的proObj信息
    List<Product> productList = (List<Product>)request.getAttribute("productList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Reissue reissue = (Reissue)request.getAttribute("reissue");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改补办申请信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">补办申请信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="reissueEditForm" id="reissueEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="reissue_reissueId_edit" class="col-md-3 text-right">补办id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="reissue_reissueId_edit" name="reissue.reissueId" class="form-control" placeholder="请输入补办id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="reissue_proObj_productNo_edit" class="col-md-3 text-right">车牌号:</label>
		  	 <div class="col-md-9">
			    <select id="reissue_proObj_productNo_edit" name="reissue.proObj.productNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="reissue_userObj_user_name_edit" class="col-md-3 text-right">补办用户:</label>
		  	 <div class="col-md-9">
			    <select id="reissue_userObj_user_name_edit" name="reissue.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="reissue_reissueTime_edit" class="col-md-3 text-right">补办时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date reissue_reissueTime_edit col-md-12" data-link-field="reissue_reissueTime_edit">
                    <input class="form-control" id="reissue_reissueTime_edit" name="reissue.reissueTime" size="16" type="text" value="" placeholder="请选择补办时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="reissue_ememo_edit" class="col-md-3 text-right">补办原因:</label>
		  	 <div class="col-md-9">
			    <textarea id="reissue_ememo_edit" name="reissue.ememo" rows="8" class="form-control" placeholder="请输入补办原因"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="reissue_state_edit" class="col-md-3 text-right">处理状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="reissue_state_edit" name="reissue.state" class="form-control" placeholder="请输入处理状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="reissue_rfid_edit" class="col-md-3 text-right">rfid:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="reissue_rfid_edit" name="reissue.rfid" class="form-control" placeholder="请输入rfid">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxReissueModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#reissueEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改补办申请界面并初始化数据*/
function reissueEdit(reissueId) {
	$.ajax({
		url :  basePath + "Reissue/" + reissueId + "/update",
		type : "get",
		dataType: "json",
		success : function (reissue, response, status) {
			if (reissue) {
				$("#reissue_reissueId_edit").val(reissue.reissueId);
				$.ajax({
					url: basePath + "Product/listAll",
					type: "get",
					success: function(products,response,status) { 
						$("#reissue_proObj_productNo_edit").empty();
						var html="";
		        		$(products).each(function(i,product){
		        			html += "<option value='" + product.productNo + "'>" + product.productNo + "</option>";
		        		});
		        		$("#reissue_proObj_productNo_edit").html(html);
		        		$("#reissue_proObj_productNo_edit").val(reissue.proObjPri);
					}
				});
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#reissue_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#reissue_userObj_user_name_edit").html(html);
		        		$("#reissue_userObj_user_name_edit").val(reissue.userObjPri);
					}
				});
				$("#reissue_reissueTime_edit").val(reissue.reissueTime);
				$("#reissue_ememo_edit").val(reissue.ememo);
				$("#reissue_state_edit").val(reissue.state);
				$("#reissue_rfid_edit").val(reissue.rfid);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交补办申请信息表单给服务器端修改*/
function ajaxReissueModify() {
	$.ajax({
		url :  basePath + "Reissue/" + $("#reissue_reissueId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#reissueEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#reissueQueryForm").submit();
            }else{
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
    /*补办时间组件*/
    $('.reissue_reissueTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    reissueEdit("<%=request.getParameter("reissueId")%>");
 })
 </script> 
</body>
</html>

