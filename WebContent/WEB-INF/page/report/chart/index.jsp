<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>图型报表</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src="<s:url value='/ewcmssource/easyui/ext/datagrid-detailview.js'/>"></script>
		<script type="text/javascript">
			var parameterURL = "<s:url namespace='/report/parameter' action='index'/>";
			var schedulingURL = "<s:url namespace='/scheduling/jobreport' action='index'/>";
			var helpURL = "<s:url namespace='/report/chart' action='help'/>";
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/report/chart" action="query"/>');
	
				ewcmsBOBJ.setWinWidth(700);
				ewcmsBOBJ.setWinHeight(500);
				
				ewcmsBOBJ.addToolItem('预览', 'icon-article-preview', previewOperate, 'btnPreview')
				ewcmsBOBJ.addToolItem('定时设置', 'icon-scheduler-set', timeChartOperate, 'btnChartTime');
				
				ewcmsBOBJ.openDataGrid('#tt',{
	                columns:[[
							{field:'id',title:'编号',width:50,sortable:true},
			                {field:'name',title:'名称',width:100},
			                {field:'createDate',title:'创建时间',width:145},
			                {field:'updateDate',title:'更新时间',width:145},
			                {field:'typeDescription',title:'图型类型',width:200},
			                {field:'dsName',title:'数据源名称',width:200,
			                	formatter:function(val,rec){
			                		if (rec.baseDS == null){
			                			return '默认数据源';
			                		}else{
			                			return rec.baseDS.name;
			                		}
			                	}
			                },
			                {field:'remarks',title:'说明',width:600}
	                  ]]
				});
				
				$("#tt").datagrid({
		            view : detailview,
		      		detailFormatter : function(rowIndex, rowData) {
		      			return '<div id="ddv-' + rowIndex + '"></div>';
		      		},
		            onExpandRow: function(rowIndex, rowData){
		      			var content = '<iframe src="' + parameterURL + '?reportId=' + rowData.id + '&reportType=chart" frameborder="0" width="100%" height="300px" scrolling="auto"></iframe>';
		      			
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
				ewcmsOOBJ.setInputURL('<s:url namespace="/report/chart" action="input"/>');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/report/chart" action="delete"/>');
			});
			function previewOperate(){
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择预览记录', 'info');
					return;
				}
				if (rows.length > 1) {
					$.messager.alert('提示', '只能选择一个预览', 'info');
					return;
				}
				var url = '<s:url namespace="/report/show" action="paraset"/>?reportType=chart&reportId='+ rows[0].id;
				$('#parameterifr').attr('src',url);
				ewcmsBOBJ.openWindow("#parameter-window",{width:400,height:213,title:"参数选择"});
			}
			function timeChartOperate(){
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择记录', 'info');
					return;
				}
				if (rows.length > 1) {
					$.messager.alert('提示', '只能选择一条记录', 'info');
					return;
				}
				var url = schedulingURL + '?reportId=' + rows[0].id + '&reportType=chart';
				$('#editifr_scheduling').attr('src', url);
				ewcmsBOBJ.openWindow('#scheduling-window', {
					width : 900,
					height : 500,
					title : '定时器设置'
				});
			}
			function saveScheduling(){
				window.frames['editifr_scheduling'].document.forms[0].submit();
			}
			function helpOperate(){
				window.open(helpURL,'popup','width=900,height=500,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,scrollbars=yes,status=no,left=' + (window.screen.width - 900)/ 2 + ',top=' + (window.screen.height - 500) / 2);
			}
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
		<div id="scheduling-window" class="easyui-window" closed="true" title="&nbsp;定时器设置" style="display:none;">
	      <div class="easyui-layout" fit="true">
	        <div region="center" border="false">
	          <iframe id="editifr_scheduling"  name="editifr_scheduling" class="editifr" frameborder="0" width="100%" height="100%" style="overflow-x:hidden;overflow-y:scroll""></iframe>
	        </div>
	        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
	          <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0);" onclick="javascript:saveScheduling();">保存</a>
	          <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0);" onclick="javascript:$('#editifr_scheduling').attr('src','');$('#scheduling-window').window('close');">关闭</a>
	        </div>
	      </div>
	    </div>
        <div id="parameter-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;参数选择" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="parameterifr"  name="parameterifr" class="editifr" frameborder="0" style="overflow-x:hidden;overflow-y:scroll"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="window.frames['parameterifr'].document.forms[0].submit();">生成</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#parameter-window').window('close');">关闭</a>
                </div>
            </div>
        </div>	
	</body>
</html>