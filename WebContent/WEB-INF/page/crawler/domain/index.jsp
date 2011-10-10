<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>网络采集器</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/crawler/domain" action="query"/>?gatherId=' + $('#gatherId').val());
				
				ewcmsBOBJ.setWinWidth(650);
				ewcmsBOBJ.setWinHeight(150);
				
				ewcmsBOBJ.openDataGrid('#tt',{
					singleSelect : true,
	                columns:[[
							{field:'id',title:'编号',width:50,sortable:true},
			                {field:'url',title:'URL',width:600}
	                  ]]
				});
	
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/crawler/domain" action="input"/>?gatherId=' + $('#gatherId').val());
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/crawler/domain" action="delete"/>?gatherId=' + $('#gatherId').val());
			});
			function upOperate(){
			    var rows = $('#tt').treegrid('getSelections');
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
					$("#tt").treegrid('reload');
				})
			}
			function downOperate(){
			    var rows = $('#tt').treegrid('getSelections');
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
					$("#tt").treegrid('reload');
				});
			}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;网络采集器属性" style="display:none;">
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