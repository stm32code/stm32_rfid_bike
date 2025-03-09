<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css" /> 

<div id="product_manage"></div>
<div id="product_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="product_manage_tool.edit();">处理</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="product_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="product_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="product_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="product_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="productQueryForm" method="post">
			车牌号：<input type="text" class="textbox" id="productNo" name="productNo" style="width:110px" />
			电动车类别：<input class="textbox" type="text" id="productClassObj_classId_query" name="productClassObj.classId" style="width: auto"/>
			电动车名称：<input type="text" class="textbox" id="productName" name="productName" style="width:110px" />
			发布时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			所属用户：<input class="textbox" type="text" id="userObj_user_name_query" name="userObj.user_name" style="width: auto"/>
			电动车状态：<input type="text" class="textbox" id="proState" name="proState" style="width:110px" />
			电动车RFID：<input type="text" class="textbox" id="rfid" name="rfid" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="product_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="productEditDiv">
	<form id="productEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">车牌号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productNo_edit" name="product.productNo" style="width:200px" />
			</span>
		</div>
		
		<div>
			<span class="label">电动车RFID:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_rfid_edit" name="product.rfid" style="width:200px" />
				<input type=button onclick="readRfid();" value="&nbsp;读卡&nbsp;" /> 
			</span>

		</div>
		
		
		<div>
			<span class="label">电动车状态:</span>
			<span class="inputControl">
				<select id="product_proState_edit" name="product.proState">
					<option value="待审核">待审核</option>
					<option value="正常">正常</option>
					<option value="已挂失">已挂失</option>
					<option value="已注销">已注销</option>
				</select>
				 
			</span>

		</div>
		
		
		
		<div>
			<span class="label">电动车类别:</span>
			<span class="inputControl">
				<input class="textbox"  id="product_productClassObj_classId_edit" name="product.productClassObj.classId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">电动车名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productName_edit" name="product.productName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">电动车主图:</span>
			<span class="inputControl">
				<img id="product_mainPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="product_mainPhoto" name="product.mainPhoto"/>
				<input id="mainPhotoFile" name="mainPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">电动车价格:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_price_edit" name="product.price" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">电动车描述:</span>
			<span class="inputControl">
				<script name="product.productDesc" id="product_productDesc_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_addTime_edit" name="product.addTime" />

			</span>

		</div>
		<div>
			<span class="label">所属用户:</span>
			<span class="inputControl">
				<input class="textbox"  id="product_userObj_user_name_edit" name="product.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		
		
	</form>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var product_productDesc_editor = UE.getEditor('product_productDesc_edit'); //电动车描述编辑器
</script>
<script type="text/javascript" src="Product/js/product_manage.js"></script> 
