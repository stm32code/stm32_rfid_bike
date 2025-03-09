<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.card.po.Reissue" %>
<%@ page import="com.card.po.Product" %>
<%@ page import="com.card.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Reissue> reissueList = (List<Reissue>)request.getAttribute("reissueList");
    //获取所有的proObj信息
    List<Product> productList = (List<Product>)request.getAttribute("productList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Product proObj = (Product)request.getAttribute("proObj");
    UserInfo userObj = (UserInfo)request.getAttribute("userObj");
    String reissueTime = (String)request.getAttribute("reissueTime"); //补办时间查询关键字
    String state = (String)request.getAttribute("state"); //处理状态查询关键字
    String rfid = (String)request.getAttribute("rfid"); //rfid查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>补办申请查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#reissueListPanel" aria-controls="reissueListPanel" role="tab" data-toggle="tab">补办申请列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Reissue/reissue_frontAdd.jsp" style="display:none;">添加补办申请</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="reissueListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>补办id</td><td>车牌号</td><td><td>补办时间</td><td>补办原因</td><td>处理状态</td><td>rfid</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<reissueList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Reissue reissue = reissueList.get(i); //获取到补办申请对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=reissue.getReissueId() %></td>
 											<td><%=reissue.getProObj().getProductNo() %></td>
 											<td><%=reissue.getReissueTime() %></td>
 											<td><%=reissue.getEmemo() %></td>
 											<td><%=reissue.getState() %></td>
 											<td><%=reissue.getRfid() %></td>
 											<td>
 												<a href="<%=basePath  %>Reissue/<%=reissue.getReissueId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="reissueEdit('<%=reissue.getReissueId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="reissueDelete('<%=reissue.getReissueId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>补办申请查询</h1>
		</div>
		<form name="reissueQueryForm" id="reissueQueryForm" action="<%=basePath %>Reissue/userFrontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="proObj_productNo">车牌号：</label>
                <select id="proObj_productNo" name="proObj.productNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Product productTemp:productList) {
	 					String selected = "";
 					if(proObj!=null && proObj.getProductNo()!=null && proObj.getProductNo().equals(productTemp.getProductNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=productTemp.getProductNo() %>" <%=selected %>><%=productTemp.getProductNo() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group" style="display:none;">
            	<label for="userObj_user_name">补办用户：</label>
                <select id="userObj_user_name" name="userObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(userObj!=null && userObj.getUser_name()!=null && userObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="reissueTime">补办时间:</label>
				<input type="text" id="reissueTime" name="reissueTime" class="form-control"  placeholder="请选择补办时间" value="<%=reissueTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="state">处理状态:</label>
				<input type="text" id="state" name="state" value="<%=state %>" class="form-control" placeholder="请输入处理状态">
			</div>






			<div class="form-group">
				<label for="rfid">rfid:</label>
				<input type="text" id="rfid" name="rfid" value="<%=rfid %>" class="form-control" placeholder="请输入rfid">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="reissueEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;补办申请信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#reissueEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxReissueModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.reissueQueryForm.currentPage.value = currentPage;
    document.reissueQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.reissueQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.reissueQueryForm.currentPage.value = pageValue;
    documentreissueQueryForm.submit();
}

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
				$('#reissueEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除补办申请信息*/
function reissueDelete(reissueId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Reissue/deletes",
			data : {
				reissueIds : reissueId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#reissueQueryForm").submit();
					//location.href= basePath + "Reissue/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>

