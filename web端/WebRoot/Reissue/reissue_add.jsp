<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reissue.css" />
<div id="reissueAddDiv">
	<form id="reissueAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">车牌号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_proObj_productNo" name="reissue.proObj.productNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">补办用户:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_userObj_user_name" name="reissue.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">补办时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_reissueTime" name="reissue.reissueTime" />

			</span>

		</div>
		<div>
			<span class="label">补办原因:</span>
			<span class="inputControl">
				<textarea id="reissue_ememo" name="reissue.ememo" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div>
			<span class="label">处理状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_state" name="reissue.state" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">rfid:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_rfid" name="reissue.rfid" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="reissueAddButton" class="easyui-linkbutton">添加</a>
			<a id="reissueClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Reissue/js/reissue_add.js"></script> 
