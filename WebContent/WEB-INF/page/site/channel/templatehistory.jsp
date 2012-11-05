<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>历史内容</title>
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/site/template/history" action="query"><s:param name="templateId" value="templateId"></s:param></s:url>');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.addToolItem('还原','icon-resume', restoreOperate);

				ewcmsBOBJ.openDataGrid('#tt',{
					singleSelect : true,
					pagination : false,
	                columns:[[
					        {field:'historyId',title:'编号',width:60,hidden:true},
					        {field:'version',title:'版本号',width:100,sortable:true},
			                {field:'historyTime',title:'时间',width:200}
	                  ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			});
			function restoreOperate(){
				var rows = $("#tt").datagrid("getSelections");
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择还原记录','info');
	                return;
	            }	
	            $.messager.confirm("提示","确定要把第 【" + rows[0].version + "】号 版本替换当前的模板吗?",function(r){
	        		if (r){
	        			$.post("<s:url namespace='/site/template/history' action='restore'/>", {historyId : rows[0].historyId, templateId : $('#templateId').val()}, function(data) {
	    					if (data == 'true'){
	    						$.messager.alert('提示', '还原模板成功', 'info');
	    					}else{
	    						$.messager.alert('提示', '还原模板失败', 'info');
	    					}
	    				});
	        		}
	        	});
			}
			function queryDateSearch(){
				url = globaoptions.queryURL;
				url = url + '&startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val();
			    $('#tt').datagrid({
			        pageNumber:1,
			        url:url
			    });
			    $('#query-window').window('close');			
			}
		</script>
		<ewcms:datepickerhead/>	
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding: 2px;" border="false">
			<table id="tt" fit="true"></table>
		</div>
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">开始时间：</td>
                                <td class="tdinput">
                                	<ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">结束时间：</td>
                                <td class="tdinput">
                                	<ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="queryDateSearch();">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden name="templateId" id="templateId"/>
	</body>
</html>