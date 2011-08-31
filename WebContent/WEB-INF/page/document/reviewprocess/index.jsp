<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>审批流程</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
		$(function(){
			//基本变量初始
			setGlobaVariable({
				inputURL:'<s:url namespace="/document/reviewprocess" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>',
				queryURL:'<s:url namespace="/document/reviewprocess" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>',
				deleteURL:'<s:url namespace="/document/reviewprocess" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>',
				defaultWidth:300
			});
			//数据表格定义 						
			openDataGrid({
				columns:[[
							{field:'id',title:'编号',width:50,sortable:true},
		                 	{field:'name',title:'名称',width:200},
		                 	{field:'userName',title:'用户',width:100},
		                 	{field:'userGroup',title:'用户组',width:100}
		                 ]],
				toolbar:[
							{id:'btnAdd',text:'新增',iconCls:'icon-add',handler:addOperateBack},'-',
							{id:'btnUpd',text:'修改',iconCls:'icon-edit',handler:updOperateBack},'-',
							{id:'btnUp',text:'上移',iconCls:'icon-up',handler:upOperate},'-',
							{id:'btnDown',text:'下移',iconCls:'icon-down',handler:downOperate},'-',
							{id:'btnRemove',text:'删除',iconCls:'icon-remove', handler:delOperateBack},'-',
							{id:'btnQuery',text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
							{id:'btnBack',text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
						]
			});
		});
		function upOperate(){
			var rows = $("#tt").datagrid("getSelections");
			if (rows.length == 0){
				$.messager.alert("提示","请选择移动记录","info");
				return;
			}
			if (rows.length > 1){
				$.messager.alert("提示","只能选择一个记录进行移动","info");
				return;
			}
			$.post('<s:url namespace="/document/reviewprocess" action="up"/>', {'channelId':$("#channelId").val(),'selections':rows[0].id}, function(data){
				if (data == "false"){
					$.messager.alert("提示","上移失败","info");
					return;
				}
				$("#tt").datagrid('reload');
			});
            return false;           
		}
		function downOperate(){
			var rows = $("#tt").datagrid("getSelections");
			if (rows.length == 0){
				$.messager.alert("提示","请选择移动记录","info");
				return;
			}
			if (rows.length > 1){
				$.messager.alert("提示","只能选择一个记录进行移动","info");
				return;
			}
			$.post('<s:url namespace="/document/reviewprocess" action="down"/>', {'channelId':$("#channelId").val(),'selections':rows[0].id}, function(data){
				if (data == "false"){
					$.messager.alert("提示","下移失败","info");
					return;
				}
				$("#tt").datagrid('reload');
			});
            return false;           
		}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;频道流程" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#edit-window').window('close');">取消</a>
                </div>
            </div>
        </div>	
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">编号：</td>
                                <td class="tdinput">
                                    <input type="text" id="id" name="id" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden id="channelId" name="channelId"/>      	
	</body>
</html>