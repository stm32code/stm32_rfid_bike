<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.card.po.Product" %>
<%@ page import="com.card.po.ProductClass" %>
<%@ page import="com.card.po.UserInfo" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的productClassObj信息
    List<ProductClass> productClassList = (List<ProductClass>)request.getAttribute("productClassList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Product product = (Product)request.getAttribute("product");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改电动车信息</TITLE>
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
  		<li class="active">电动车信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="productEditForm" id="productEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="product_productNo_edit" class="col-md-3 text-right">车牌号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="product_productNo_edit" name="product.productNo" class="form-control" placeholder="请输入车牌号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="product_productClassObj_classId_edit" class="col-md-3 text-right">电动车类别:</label>
		  	 <div class="col-md-9">
			    <select id="product_productClassObj_classId_edit" name="product.productClassObj.classId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_productName_edit" class="col-md-3 text-right">电动车名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="product_productName_edit" name="product.productName" class="form-control" placeholder="请输入电动车名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_mainPhoto_edit" class="col-md-3 text-right">电动车主图:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="product_mainPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="product_mainPhoto" name="product.mainPhoto"/>
			    <input id="mainPhotoFile" name="mainPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_price_edit" class="col-md-3 text-right">电动车价格:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="product_price_edit" name="product.price" class="form-control" placeholder="请输入电动车价格">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_productDesc_edit" class="col-md-3 text-right">电动车描述:</label>
		  	 <div class="col-md-9">
			    <script name="product.productDesc" id="product_productDesc_edit" type="text/plain"   style="width:100%;height:500px;"></script>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date product_addTime_edit col-md-12" data-link-field="product_addTime_edit">
                    <input class="form-control" id="product_addTime_edit" name="product.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_userObj_user_name_edit" class="col-md-3 text-right">所属用户:</label>
		  	 <div class="col-md-9">
			    <select id="product_userObj_user_name_edit" name="product.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_proState_edit" class="col-md-3 text-right">电动车状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="product_proState_edit" name="product.proState" class="form-control" placeholder="请输入电动车状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="product_rfid_edit" class="col-md-3 text-right">电动车RFID:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="product_rfid_edit" name="product.rfid" class="form-control" placeholder="请输入电动车RFID">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxProductModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#productEditForm .form-group {margin-bottom:5px;}  </style>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
var product_productDesc_editor = UE.getEditor('product_productDesc_edit'); //电动车描述编辑框
var basePath = "<%=basePath%>";
/*弹出修改电动车界面并初始化数据*/
function productEdit(productNo) {
  product_productDesc_editor.addListener("ready", function () {
    // editor准备好之后才可以使用 
    ajaxModifyQuery(productNo);
  });
}
 function ajaxModifyQuery(productNo) {
	$.ajax({
		url :  basePath + "Product/" + productNo + "/update",
		type : "get",
		dataType: "json",
		success : function (product, response, status) {
			if (product) {
				$("#product_productNo_edit").val(product.productNo);
				$.ajax({
					url: basePath + "ProductClass/listAll",
					type: "get",
					success: function(productClasss,response,status) { 
						$("#product_productClassObj_classId_edit").empty();
						var html="";
		        		$(productClasss).each(function(i,productClass){
		        			html += "<option value='" + productClass.classId + "'>" + productClass.className + "</option>";
		        		});
		        		$("#product_productClassObj_classId_edit").html(html);
		        		$("#product_productClassObj_classId_edit").val(product.productClassObjPri);
					}
				});
				$("#product_productName_edit").val(product.productName);
				$("#product_mainPhoto").val(product.mainPhoto);
				$("#product_mainPhotoImg").attr("src", basePath +　product.mainPhoto);
				$("#product_price_edit").val(product.price);
				product_productDesc_editor.setContent(product.productDesc, false);
				$("#product_addTime_edit").val(product.addTime);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#product_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#product_userObj_user_name_edit").html(html);
		        		$("#product_userObj_user_name_edit").val(product.userObjPri);
					}
				});
				$("#product_proState_edit").val(product.proState);
				$("#product_rfid_edit").val(product.rfid);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交电动车信息表单给服务器端修改*/
function ajaxProductModify() {
	$.ajax({
		url :  basePath + "Product/" + $("#product_productNo_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#productEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#productQueryForm").submit();
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
    /*发布时间组件*/
    $('.product_addTime_edit').datetimepicker({
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
    productEdit("<%=request.getParameter("productNo")%>");
 })
 </script> 
</body>
</html>

