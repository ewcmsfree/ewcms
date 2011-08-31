<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>作业设置</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
		$(function(){
			//基本变量初始
			setGlobaVariable({
				inputURL:'<s:url namespace="/scheduling/jobinfo" action="input"/>',
				queryURL:'<s:url namespace="/scheduling/jobinfo" action="query"/>',
				deleteURL:'<s:url namespace="/scheduling/jobinfo" action="delete"/>',
				editwidth:1040,
				editheight:470
			});
			//数据表格定义 						
			openDataGrid({
				columns:[[
							{field:'id',title:'编号',width:50,sortable:true},
		                 	{field:'label',title:'名称',width:100},
		                 	{field:'version',title:'版本',width:40},
		                 	{field:'jobClassName',title:'作业名称',width:200,
			                 	formatter:function(val,rec){
			                 		return rec.jobClass.className;
		                 		}
		                 	},
		                 	{field:'state',title:'状态',width:80},
		                 	{field:'startTime',title:'开始时间',width:125},
		                 	{field:'previousFireTime',title:'上次执行时间',width:125},
		                 	{field:'nextFireTime',title:'下次执行时间',width:125},
		                 	{field:'endTime',title:'结束时间',width:125},
		                 	{field:'operation',title:'操作',width:50,align:'center',
		                 		formatter:function(val,rec){
		                 			var button_html = "";
		                 			if (rec.state=='正常'){
			                 			button_html = "<a href='<s:url namespace='/scheduling/jobinfo' action='pause'/>?jobId=" + rec.id + "'><img src='../../source/image/scheduling/pause.png' width='13px' height='13px' title='暂停操作'/></a>";
		                 			}else if (rec.state=='暂停'){
			                 			button_html = "<a href='<s:url namespace='/scheduling/jobinfo' action='resumed'/>?jobId=" + rec.id + "'><img src='../../source/image/scheduling/resumed.png' width='13px' height='13px' title='恢复操作'/></a>";
		                 			}
		                 			return button_html;
		                 		}
		                 	}
		                 ]],
				toolbar:[
							{text:'新增',iconCls:'icon-add',handler:addOperateBack},'-',
							{text:'修改',iconCls:'icon-edit',handler:updOperateBack},'-',
							{text:'删除',iconCls:'icon-remove', handler:delOperateBack},'-',
							{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
							{text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack},'-'
						]
			});
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
                                    <input type="text" id="label" name="label" class="inputtext"/>
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