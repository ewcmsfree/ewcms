<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>报表存储</title>	
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
		<script type="text/javascript">
		$(function(){
			ewcmsBOBJ = new EwcmsBase();
			ewcmsBOBJ.setQueryURL('<s:url namespace="/report/repository" action="query"/>');
			
			ewcmsBOBJ.delToolItem('新增');
			ewcmsBOBJ.delToolItem('修改');

			ewcmsBOBJ.openDataGrid('#tt',{
                columns:[[
						{field:'id',title:'编号',width:50,sortable:true},
		                {field:'name',title:'名称',width:100},
		                {field:'type',title:'类型',width:100},
		                {field:'updateDate',title:'更新时间',width:125},
		                {field:'description',title:'说明',width:300}
                  ]]
			});

			ewcmsOOBJ = new EwcmsOperate();
			ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			//ewcmsOOBJ.setInputURL('<s:url namespace="/report/repository" action="input"/>');
			ewcmsOOBJ.setDeleteURL('<s:url namespace="/report/repository" action="delete"/>');
		});
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;作业设置" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
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
	</body>
</html>