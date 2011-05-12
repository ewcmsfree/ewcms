<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>事项基本信息</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:'<s:url action="input"><s:param name="channelId" value="channelId"></s:param></s:url>',
					queryURL:'<s:url action="query"><s:param name="channelId" value="channelId"></s:param></s:url>',
					deleteURL:'<s:url action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>',
					editwidth:1031,
					editheight:580,
					top:6,
					left:160
				});
				//数据表格定义 						
				openDataGrid({
					columns:[[
								{field:'id',title:'序号',width:40,sortable:true},
					            {field:'name',title:'事项名称',width:200},
					            {field:'acceptedWay',title:'受理方式',width:80},
					            {field:'handleSite',title:'办理地点',width:300},
					            {field:'handleBasis',title:'办理依据',width:300},
					            {field:'handleWay',title:'审批、服务数量及方式',width:130},
					            {field:'acceptedCondition',title:'受理条件',width:200},
					            {field:'petitionMaterial',title:'申请材料',width:200},
					            {field:'handleCourse',title:'办理程序',width:200},
					            {field:'timeLimit',title:'法定时限',width:200},
					            {field:'deadline',title:'承诺期限',width:200},
					            {field:'fees',title:'收费标准',width:200},
					            {field:'feesBasis',title:'收费依据',width:200},
					            {field:'consultingTel',title:'咨询电话',width:200},
					            {field:'contactName',title:'联系人',width:200},
					            {field:'department',title:'所在部门',width:200},
					            {field:'contactTel',title:'联系电话',width:200},
					            {field:'email',title:'E-Mail',width:200}
							]],
				   	toolbar:[
								{text:'新增',iconCls:'icon-add',handler:addOperateBack},'-',
								{text:'修改',iconCls:'icon-edit',handler:updOperateBack},'-',
								{text:'删除',iconCls:'icon-remove', handler:delOperateBack},'-',
								{text:'上移',iconCls:'icon-up',handler:upOperate},'-',
								{text:'下移',iconCls:'icon-down',handler:downOperate},'-',
								{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
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
				$.post('<s:url namespace="/plugin/onlineoffice/matter" action="upMatter"><s:param name="channelId" value="channelId"></s:param></s:url>', {'matterId':rows[0].id}, function(data){
					if (data == 'false'){
						$.messager.alert("提示","上移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
				});
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
				$.post('<s:url namespace="/plugin/onlineoffice/matter" action="downMatter"><s:param name="channelId" value="channelId"></s:param></s:url>', {'matterId':rows[0].id}, function(data){
					if (data == "false"){
						$.messager.alert("提示","下移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
				});
			}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
			<table id="tt" fit="true"></table>
		</div>
		<div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;事项信息" style="display:none;">
			<div class="easyui-layout" fit="true">
		    	<div region="center" border="false">
		        	<iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" width="100%" height="100%" scrolling="auto"></iframe>
		    	</div>
				<div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
		    		<a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
		  		</div>
			</div>
		</div>	
		<div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
			<div class="easyui-layout" fit="true">
		    	<div region="center" border="false">
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
			                    	<input type="text" id="title" name="name" class="inputtext"/>
			                    </td>
			           		</tr>
			            </table>
			    	</form>
		        </div>
		  		<div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
		        	<a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
		    	</div>
			</div>
		</div>				
	</body>
</html>