<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>匹配块</title>	
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
				ewcmsBOBJ.setQueryURL('<s:url namespace="/crawler/match" action="query"/>?gatherId=' + $('#gatherId').val());
				
				$('#tt').treegrid({
					pagination:true,
					animate:true,
					collapsible:true,
					nowrap:true,
					rownumbers:true,
					url : ewcmsBOBJ.getQueryURL(),
					idField:'id',
					treeField:'regex',
					columns:[[
						{field:'id', title:'编号', width:60},
						{field:'regex', title:'表达式', width:600}
					]],
					toolbar:[
						{id:'btnAdd',text:'新增',iconCls:'icon-add',handler:addCallBack},'-',
						{id:'btnUpd',text:'修改',iconCls:'icon-edit',handler:updCallBack},'-',
						{id:'btnRemove',text:'删除',iconCls:'icon-remove', handler:delCallBack},'-',
						{id:'btnUp',text:'上移',iconCls:'icon-up',handler:upOperate},'-',
						{id:'btnDown',text:'下移',iconCls:'icon-down',handler:downOperate},'-',
						{id:'btnSearch',text:'查询',iconCls:'icon-search', handler:queryCallBack},'-',
						{id:'btnBack',text:'缺省查询',iconCls:'icon-back', handler:defQueryCallBack},'-',
					]
				});
				
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/crawler/match" action="input"/>?gatherId=' + $('#gatherId').val());
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/crawler/match" action="delete"/>?gatherId=' + $('#gatherId').val());
			});
			function upOperate(){}
			function downOperate(){}
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
                                <td class="tdtitle">表达式：</td>
                                <td class="tdinput">
                                    <input type="text" id="regex" name="regex" class="inputtext"/>
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