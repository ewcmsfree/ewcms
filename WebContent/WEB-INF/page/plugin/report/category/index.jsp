<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>图型报表</title>	
		<s:include value="../../../taglibs.jsp"/>
		<script type="text/javascript" src="<s:url value='/ewcmssource/easyui/ext/datagrid-detailview.js'/>"></script>
		<script type="text/javascript">
			var parameterURL = "<s:url namespace='/report/category/detail' action='index'/>";
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/report/category" action="query"/>');
	
				ewcmsBOBJ.setWinWidth(700);
				ewcmsBOBJ.setWinHeight(200);
				
				ewcmsBOBJ.openDataGrid('#tt',{
	                columns:[[
							{field:'id',title:'编号',width:50,sortable:true},
			                {field:'name',title:'名称',width:300},
			                {field:'remarks',title:'说明',width:800}
	                  ]]
				});
				
				$("#tt").datagrid({
		            view : detailview,
		      		detailFormatter : function(rowIndex, rowData) {
		      			return '<div id="ddv-' + rowIndex + '"></div>';
		      		},
		            onExpandRow: function(rowIndex, rowData){
		      			var content = '<iframe src="' + parameterURL + '?categoryId=' + rowData.id + '" frameborder="0" width="100%" height="300px" scrolling="auto"></iframe>';
		      			
		      			$('#ddv-' + rowIndex).panel({
		      				border : false,
		      				cache : false,
		      				content : content,
		      				onLoad : function(){
		      					$('#tt').datagrid('fixDetailRowHeight',rowIndex);
		      				}
		      			});
		      			$('#tt').datagrid('fixDetailRowHeight',rowIndex);
		      		}
				});
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/report/category" action="input"/>');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/report/category" action="delete"/>');
			});
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;分类设置" style="display:none;">
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
                                <td class="tdtitle">报表名：</td>
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