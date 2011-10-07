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
				ewcmsBOBJ.setQueryURL('<s:url namespace="/crawler" action="query"/>');
				
				ewcmsBOBJ.addToolItem('URL层级','icon-urllevel',urlLevelOperate,'btnUrlLevel');
				ewcmsBOBJ.addToolItem('匹配','icon-match',matchOperate,'btnMatch');
				ewcmsBOBJ.addToolItem('过滤','icon-filter',filterOperate,'btnFilter');
				
				ewcmsBOBJ.setWinWidth(800);
				ewcmsBOBJ.setWinHeight(500);
				
				ewcmsBOBJ.openDataGrid('#tt',{
					singleSelect : true,
	                columns:[[
							{field:'id',title:'编号',width:50,sortable:true},
			                {field:'name',title:'名称',width:100},
			                {field:'description',title:'描述',width:300},
			                {field:'status',title:'状态',width:40,
			                	formatter : function(val, rec) {
			                		if (val){
			                			return '启用';
			                		}else{
			                			return '停用';
			                		}
			                	}
			                },
			                {field:'maxContent',title:'最大采集数',width:100},
			                {field:'depth',title:'采集深度',width:80},
			                {field:'threadCount',title:'线程数',width:70},
			                {field:'thimeOutWait',title:'超时等待时间',width:90},
			                {field:'errorCount',title:'错误时重试次数',width:110}
	                  ]]
				});
	
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/crawler" action="input"/>');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/crawler" action="delete"/>');
			});
			function matchOperate(){
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择记录', 'info');
					return;
				}
				if (rows.length > 1) {
					$.messager.alert('提示', '只能选择一条记录', 'info');
					return;
				}
				var url =  '<s:url namespace="/crawler/match" action="index"/>?gatherId=' + rows[0].id;
				$('#editifr_block').attr('src',url);
				ewcmsBOBJ.openWindow('#block-window',{width:900,height:500,title:'匹配块'});
			}
			function filterOperate(){
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择记录', 'info');
					return;
				}
				if (rows.length > 1) {
					$.messager.alert('提示', '只能选择一条记录', 'info');
					return;
				}
				var url =  '<s:url namespace="/crawler/filter" action="index"/>?gatherId=' + rows[0].id;
				$('#editifr_block').attr('src',url);
				ewcmsBOBJ.openWindow('#block-window',{width:900,height:500,title:'过滤块'});
			}
			function urlLevelOperate(){
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择记录', 'info');
					return;
				}
				if (rows.length > 1) {
					$.messager.alert('提示', '只能选择一条记录', 'info');
					return;
				}
				var url =  '<s:url namespace="/crawler/url" action="index"/>?gatherId=' + rows[0].id;
				$('#editifr_block').attr('src',url);
				ewcmsBOBJ.openWindow('#block-window',{width:900,height:500,title:'URL层级'});
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
		<div id="block-window" class="easyui-window" closed="true" title="&nbsp;区块" style="display:none;">
	      <div class="easyui-layout" fit="true">
	        <div region="center" border="false">
	          <iframe id="editifr_block"  name="editifr_block" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
	        </div>
	        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
	          <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0);" onclick="javascript:$('#editifr_block').attr('src','');$('#block-window').window('close');">关闭</a>
	        </div>
	      </div>
	    </div>
	</body>
</html>