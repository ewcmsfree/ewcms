<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:include page="../loading.jsp" flush="true"/>
<html>
	<head>
		<title>公告/订阅</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/easyui/ext/datagrid-detailview.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<script>
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/message/more" action="query"><s:param name="type" value="type"></s:param></s:url>');
				
				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.delToolItem('查询');
				ewcmsBOBJ.delToolItem('缺省查询');
				
				ewcmsBOBJ.addToolItem('查询', 'icon-search', queryCallBack, 'btnSearch');
				ewcmsBOBJ.addToolItem('缺省查询', 'icon-back', initOperateQuery, 'btnBack');
				
				ewcmsBOBJ.openDataGrid('#tt',{
					singleSelect : true,
					nowrap: false,
	                columns:[[
							 {field:'id',title:'编号',width:60},
							 {field:'userName',title:'用户',hidden:true},
							 {field:'title',title:'标题',width:800},
			                 {field:'sendTime',title:'发送时间',width:125}
							 
	                  ]]
				});
	
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				
				$("#tt").datagrid({
					view : detailview,
					detailFormatter : function(rowIndex, rowData) {
						return detailGridData(rowData);
					},
					onExpandRow: function(index,row){  
						$('#tt').datagrid('fixDetailRowHeight',index);  
					}
				});
			});
			
			//内容数据
			function detailGridData(rowData){
				var htmls = [];
				if (rowData.msgContents.length == 0) {
					htmls.push('<div style="padding:5px 0">没有内容记录!</div>');
				} else {
					htmls.push('<div style="padding:5px 0;"><div class="datagrid-header" style="height:22px;">');
					htmls.push('<div style="float:left;display: block;">');
					htmls.push('<table cellspacing="0" cellpadding="0" border="0" style="height: 23px;">');
					htmls.push('<tr style="height: 21px">');
					htmls.push('<td><div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 20px; text-align: center;"><span></span><span class="datagrid-sort-icon">&nbsp;</span></div></td>');
					htmls.push('<td><div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 1000px; text-align: left;"><span>内容</span><span class="datagrid-sort-icon">&nbsp;</span></div></td>');
					if (rowData.type == 'SUBSCRIPTION'){
						htmls.push('<td><div class="datagrid-cell" style="width: 24px; text-align: center;"><span></span><span class="datagrid-sort-icon"><a href="javascript:void(0);" onclick="subscribe(' + rowData.id + ');return false;" onfocus="this.blur();">订阅</a></span></div></td>');
					}
					htmls.push('</tr>');
					htmls.push('</table>');
					htmls.push('</div>');
					htmls.push('</div>');
					htmls.push('<div class="datagrid-body">');
					for ( var i = 0; i < rowData.msgContents.length; i++) {
						htmls.push('<table cellspacing="0" cellpadding="0" border="0"><tr style="height: 21px">'
										+ '<td>'
										+ '<div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 20px; text-align: center;">'
										+ (rowData.msgContents.length - i)
										+ '</td>'
										+ '</div>'
										+ '<td>'
										+ '<div style="margin: 0;overflow: hidden; padding: 3px 4px; word-wrap: normal; width: 1000px; text-align: left;">'
										+ rowData.msgContents[i].detail
										+ '</div>'
										+ '</td>');
						if (rowData.type == 'SUBSCRIPTION'){
							htmls.push('<td><div class="datagrid-cell" style="width: 24px; text-align: center;"><span>'
										+ '</span></div></td>');
						}
						htmls.push('</tr></table>');
					}
					htmls.push('</div></div>');
				}
				return htmls.join("");
			}
			function initOperateQuery(){
				defQueryCallBack();
			}
			function subscribe(id){
				var url = '<s:url namespace="/message/detail" action="subscribe"/>';
				$.post(url, {'id':id}, function(data) {
					if (data == 'own'){
						$.messager.alert('提示','您不能订阅自已发布的信息！','info');
						return;
					}
					if (data == 'exist'){
						$.messager.alert('提示','您已订阅了此信息，不需要再订阅！','info');
						return;
					}
					if (data == 'false'){
						$.messager.alert('提示','订阅信息失败！','info');
						return;
					}
					if (data == 'true'){
						$.messager.alert('提示','订阅成功！','info');
						return;
					}
				});
			}		
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;消息" style="display:none;">
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
                                <td class="tdtitle">名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="title" name="title" class="inputtext"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');initSubMenu();">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden id="type" name="type"/>
	</body>
</html>