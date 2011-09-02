<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>审批流程</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<script>
		$(function(){
			ewcmsBOBJ = new EwcmsBase();
			ewcmsBOBJ.setQueryURL('<s:url namespace="/document/reviewprocess" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>');

			ewcmsBOBJ.delToolItem('新增');
			ewcmsBOBJ.delToolItem('修改');
			ewcmsBOBJ.delToolItem('删除');
			ewcmsBOBJ.delToolItem('查询');
			ewcmsBOBJ.delToolItem('缺省查询');
			
			ewcmsBOBJ.addToolItem('新增','icon-add', addCallBack);
			ewcmsBOBJ.addToolItem('修改','icon-edit',updCallBack);
			ewcmsBOBJ.addToolItem('上移','icon-up',upOperate);
			ewcmsBOBJ.addToolItem('下移','icon-down',downOperate);
			ewcmsBOBJ.addToolItem('删除','icon-remove',delCallBack);
			ewcmsBOBJ.addToolItem('查询','icon-search',queryCallBack);
			ewcmsBOBJ.addToolItem('缺省查询','icon-back',defQueryCallBack);
			
			ewcmsBOBJ.openDataGrid('#tt',{
				singleSelect:true,
                columns:[[
						{field:'id',title:'编号',width:50,sortable:true},
		                 {field:'name',title:'名称',width:200},
		                 {field:'userName',title:'用户',width:100},
		                 {field:'userGroup',title:'用户组',width:100}
                  ]]
			});

			ewcmsOOBJ = new EwcmsOperate();
			ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			ewcmsOOBJ.setInputURL('<s:url namespace="/document/reviewprocess" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>');
			ewcmsOOBJ.setDeleteURL('<s:url namespace="/document/reviewprocess" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>');
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