<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reissue.css" /> 

<div id="reissue_manage"></div>
<div id="reissue_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="reissue_manage_tool.edit();">处理</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="reissue_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="reissue_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="reissue_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="reissue_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="reissueQueryForm" method="post">
			车牌号：<input class="textbox" type="text" id="proObj_productNo_query" name="proObj.productNo" style="width: auto"/>
			补办用户：<input class="textbox" type="text" id="userObj_user_name_query" name="userObj.user_name" style="width: auto"/>
			补办时间：<input type="text" id="reissueTime" name="reissueTime" class="easyui-datebox" editable="false" style="width:100px">
			处理状态：<input type="text" class="textbox" id="state" name="state" style="width:110px" />
			rfid：<input type="text" class="textbox" id="rfid" name="rfid" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="reissue_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="reissueEditDiv">
	<form id="reissueEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">补办id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_reissueId_edit" name="reissue.reissueId" style="width:200px" />
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
				<select id="reissue_state_edit" name="reissue.state">
					<option value="待审核">待审核</option>
					<option value="审核通过">审核通过</option>
					<option value="审核拒绝">审核拒绝</option>
				</select>
			</span>

		</div>
		<div>
			<span class="label">rfid:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="reissue_rfid_edit" name="reissue.rfid" style="width:200px" />
				<input type=button onclick="readNewRfid();" value="刷新卡" />
			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Reissue/js/reissue_manage.js"></script> 
