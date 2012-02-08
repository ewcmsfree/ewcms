<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>网络采集器</title>	
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/crawler/domain" action="query"><s:param name="gatherId" value="%{gatherId}"/></s:url>');
				
				ewcmsBOBJ.setWinWidth(550);
				ewcmsBOBJ.setWinHeight(250);
				
				ewcmsBOBJ.addToolItem('上移','icon-up',upOperate);
				ewcmsBOBJ.addToolItem('下移','icon-down',downOperate);

				ewcmsBOBJ.openDataGrid('#tt',{
					singleSelect : true,
	                columns:[[
							{field:'id',title:'编号',width:50,sortable:true},
			                {field:'url',title:'URL',width:600}
	                  ]]
				});
	
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/crawler/domain" action="input"><s:param name="gatherId" value="%{gatherId}"/></s:url>');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/crawler/domain" action="delete"><s:param name="gatherId" value="%{gatherId}"/></s:url>');
			});
			function upOperate(){
			    var rows = $('#tt').datagrid('getSelections');
			    if(rows.length == 0){
			        $.messager.alert('提示','请选择上移的记录','info');
			        return ;
			    }
			    if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录进行上移","info");
					return;
				}
			    var upURL = '<s:url namespace="/crawler/domain" action="up"/>';
				$.post(upURL,{'gatherId':$('#gatherId').val(),'selections':rows[0].id},function(data) {
					if (data == "false"){
						$.messager.alert("提示","上移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
				})
			}
			function downOperate(){
			    var rows = $('#tt').datagrid('getSelections');
			    if(rows.length == 0){
			        $.messager.alert('提示','请选择下移的记录','info');
			        return ;
			    }
			    if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录进行下移","info");
					return;
				}
			    var downURL = '<s:url namespace="/crawler/domain" action="down"/>';
				$.post(downURL,{'gatherId':$('#gatherId').val(),'selections':rows[0].id},function(data) {
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
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;地址区配属性" style="display:none;">
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
                                <td class="tdtitle">URL：</td>
                                <td class="tdinput">
                                    <input type="text" id="url" name="url" class="inputtext"/>
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
        <s:hidden id="gatherId" name="gatherId"/>
	</body>
</html>