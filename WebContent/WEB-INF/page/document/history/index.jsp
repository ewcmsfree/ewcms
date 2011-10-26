<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>历史内容</title>
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
				ewcmsBOBJ.setQueryURL('<s:url namespace="/document/history" action="query"><s:param name="articleId" value="articleId"></s:param></s:url>');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');

				ewcmsBOBJ.openDataGrid('#tt',{
					singleSelect:true,
	                columns:[[
					        {field:'historyId',title:'编号',width:60,hidden:true},
					        {field:'version',title:'版本号',width:100,sortable:true},
			                {field:'maxPage',title:'页数',width:200},
			                {field:'historyTime',title:'时间',width:200}
	                  ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			});
			function selectOperator(){
				var rows = $('#tt').datagrid('getSelections');
		        if(rows.length == 0){
		        	$.messager.alert('提示','请选择记录','info');
		            return;
		        }
		        if(rows.length > 1){
					$.messager.alert('提示','只能选择一条记录','info');
					return;
			    }
			    var operator_type=[];
			    operator_type.push(rows[0].maxPage);
			    operator_type.push(rows[0].version);
			    //operator_type.push(getQueryStringRegExp("articleId"));
			    operator_type.push(rows[0].historyId);
			    return operator_type;
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
	</body>
</html>