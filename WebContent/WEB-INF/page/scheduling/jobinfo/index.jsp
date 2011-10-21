<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>作业设置</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<script type="text/javascript">
		$(function(){
			ewcmsBOBJ = new EwcmsBase();
			ewcmsBOBJ.setQueryURL('<s:url namespace="/scheduling/jobinfo" action="query"/>');

			ewcmsBOBJ.setWinWidth(1040);
			ewcmsBOBJ.setWinHeight(470);
			
			ewcmsBOBJ.openDataGrid('#tt',{
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
                  ]]
			});

			ewcmsOOBJ = new EwcmsOperate();
			ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			ewcmsOOBJ.setInputURL('<s:url namespace="/scheduling/jobinfo" action="input"/>');
			ewcmsOOBJ.setDeleteURL('<s:url namespace="/scheduling/jobinfo" action="delete"/>');
		});
		</script>		
	</head>
	<body class="easyui-layout">
		<jsp:include page="../../loading.jsp" flush="true"/>
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