<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>领导基本信息</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script><!--
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:'<s:url namespace="/plugin/leadingwindow/leaderchannel" action="input?leaderId=' + $('#leaderId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					queryURL:'<s:url namespace="/plugin/leadingwindow/leaderchannel" action="query?leaderId=' + $('#leaderId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					deleteURL:'<s:url namespace="/plugin/leadingwindow/leaderchannel" action="delete?leaderId=' + $('#leaderId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					editwidth:400,
					editheight:150
				});
				//数据表格定义 						
				openDataGrid({
					columns:[[
					          {field:'id',title:'序号',width:40,sortable:true},
					          {field:'name',title:'频道',width:200}
							]],
				   	toolbar:[
								{text:'新增',iconCls:'icon-add',handler:addOperateBack},'-',
								{text:'修改',iconCls:'icon-edit',handler:updOperateBack},'-',
								{text:'删除',iconCls:'icon-remove', handler:delOperate},'-',
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
				$.post('<s:url namespace="/plugin/leadingwindow/leaderchannel" action="up"/>', {"channelId":$("#channelId").val(),"leaderChannelId":rows[0].id,"leaderId":$("#leaderId").val()}, function(data){
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
				$.post('<s:url namespace="/plugin/leadingwindow/leaderchannel" action="down"/>', {"channelId":$("#channelId").val(),"leaderChannelId":rows[0].id,"leaderId":$("#leaderId").val()}, function(data){
					if (data == 'false'){
						$.messager.alert("提示","下移失败","info");
						return;
					}	
					$("#tt").datagrid('reload');
				});
			}

			function delOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择删除记录","info");
					return;
				}
				if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录进行删除","info");
					return;
				}
				$.post('<s:url namespace="/plugin/leadingwindow/leaderchannel" action="deleteLC"><s:param name="channelId" value="channelId"></s:param></s:url>', {"leaderId":$("#leaderId").val(),"leaderChannelId":rows[0].id}, function(data){
					if (data == 'false'){
						$.messager.alert("提示","删除失败，必须先把频道的文章删除","info");
						return;
					}
					if (data == 'system-false'){
						$.messager.alert("提示","系统错误","info");
						return;
					}
					$("#tt").datagrid('clearSelections');
					$("#tt").datagrid('reload');
				});
			}
			
			function leaderChannelReload(){
				var url='<s:url namespace="/plugin/leadingwindow/leaderchannel" action="query"/>';
				url = url + '?leaderId=' + $('#leaderId').val() + '&channelId=' + $('#channelId').val();
				$("#tt").datagrid({
	            	pageNumber:1,
	                url:url
	            });
			}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
			<table id="tt" fit="true"></table>
	 	</div>
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
 					<iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator();leaderChannelReload();return false;">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#edit-window').window('close');">取消</a>
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
			                 	   <input type="text" id="name" name="name" class="inputtext"/>
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
		<s:hidden id="leaderId" name="leaderId"/>
		<s:hidden id="channelId" name="channelId"/>				
	</body>
</html>