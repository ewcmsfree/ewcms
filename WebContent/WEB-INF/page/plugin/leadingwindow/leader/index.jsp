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
		<script>
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:'<s:url namespace="/plugin/leadingwindow/leader" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>',
					queryURL:'<s:url namespace="/plugin/leadingwindow/leader" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>',
					deleteURL:'<s:url namespace="/plugin/leadingwindow/leader" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>',
					editwidth:1031,
					editheight:550,
					top:9,
					left:116
				});
				//数据表格定义 						
				openDataGrid({
					singleSelect:true,
					columns:[[
								{field:'id',title:'序号',width:40},
					            {field:'name',title:'姓名',width:80},
					            {field:'duties',title:'职务描述',width:120},
					            {field:'email',title:'E-Mail',width:120},
					            {field:'contact',title:'联系电话',width:120},
					            {field:'mobile',title:'手机',width:120},
					            {field:'resume',title:'简历',width:400},
					            {field:'chargeWork',title:'分管工作',width:400},
					            {field:'officeAddress',title:'办公地址',width:120}
							]],
				   	toolbar:[
								{text:'新增',iconCls:'icon-add',handler:addOperateBack},'-',
								{text:'修改',iconCls:'icon-edit',handler:updOperateBack},'-',
								{text:'删除',iconCls:'icon-remove', handler:delOperateBack},'-',
								{text:'上移',iconCls:'icon-up',handler:upOperate},'-',
								{text:'下移',iconCls:'icon-down',handler:downOperate},'-',
								{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack},'-','-',
								{text:'频道管理',iconCls:'icon-add',handler:leaderChannelOperate}
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
				$.post('<s:url namespace="/plugin/leadingwindow/leader" action="upLeader"><s:param name="channelId" value="channelId"></s:param></s:url>', {'leaderId':rows[0].id}, function(data){
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
				$.post('<s:url namespace="/plugin/leadingwindow/leader" action="downLeader"><s:param name="channelId" value="channelId"></s:param></s:url>', {'leaderId':rows[0].id}, function(data){
					if (data == "false"){
						$.messager.alert("提示","下移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
				});
			}
			function leaderChannelOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择记录","info");
					return;
				}
				if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录","info");
					return;
				}
				var url = '<s:url namespace="/plugin/leadingwindow/leaderchannel" action="index"/>' + '?leaderId=' + rows[0].id + '&channelId=' + $('#channelId').val();
				$("#editifr_leaderchannel").attr('src',url);
				openWindow("#leaderchannel-window",{width:700,height:400,title: '<font color="red">' + rows[0].name + '</font>领导频道'}); 
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
        <div id="leaderchannel-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_leaderchannel"  name="editifr_leaderchannel" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
            </div>
        </div>
         <s:hidden id="channelId" name="channelId"/>
	</body>
</html>