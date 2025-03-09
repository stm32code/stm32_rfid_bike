<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reissue.css" />
<div id="reissue_editDiv">
	<form id="reissueEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">补办id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_reissueId_edit" name="reissue.reissueId" value="<%=request.getParameter("reissueId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">车牌号:</span>
			<span class="inputControl">
				<input class="textbox"  id="reissue_proObj_productNo_edit" name="reissue.proObj.productNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">补办用户:</span>
			<span class="inputControl">
				<input class="textbox"  id="reissue_userObj_user_name_edit" name="reissue.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">补办时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_reissueTime_edit" name="reissue.reissueTime" />

			</span>

		</div>
		<div>
			<span class="label">补办原因:</span>
			<span class="inputControl">
				<textarea id="reissue_ememo_edit" name="reissue.ememo" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">处理状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_state_edit" name="reissue.state" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">rfid:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_rfid_edit" name="reissue.rfid" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="reissueModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Reissue/js/reissue_modify.js"></script> 
