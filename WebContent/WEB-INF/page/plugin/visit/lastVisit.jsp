<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>访问记录</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/visit/dateutil.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
		<script type="text/javascript">
			$(function() {
				$('#startDate').val(dateTimeToString(new Date(new Date() - 30*24*60*60*1000)));
				$('#endDate').val(dateTimeToString(new Date()));
				$('#tt').datagrid({
					singleSelect : true,
					pagination : false,
					nowrap : true,
					striped : true,
					rownumbers : true,
					url : '<s:url namespace="/plugin/visit" action="lastVisitTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&rows=' + $('#rows').val(),
				    columns:[[  
				            {field:'ip',title:'IP地址',width:100},
				            {field:'country',title:'地域',width:100},
				            {field:'url',title:'访问页面',width:300,
				            	formatter : function(val, rec){
				            		if (val == null) return ''; 
				            		return '<a href="' + val + '" target="_blank">' + val + '</a>';

				            	}	
				            },  
				            {field:'visitTime',title:'访问时间',width:145,
				            	formatter : function(val, rec){
				            		return rec.visitDate + ' ' + rec.visitTime;
				            	}
				            },
				            {field:'referer',title:'来源URL',width:300,
				            	formatter : function(val, rec){
				            		if (val == null) return ''; 
				            		return '<a href="' + val + '" target="_blank">' + val + '</a>';
				            	}	
				            },
				            {field:'browser',title:'浏览器',width:100},
				            {field:'os',title:'操作系统',width:100},
				            {field:'screen',title:'屏幕大小',width:100},
				            {field:'language',title:'语言',width:150},
				            {field:'flashVersion',title:'Flash版本',width:100}
				    ]]  
				});
			});
			function refresh(){
				$('#tt').datagrid({
					url : '<s:url namespace="/plugin/visit" action="lastVisitTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&rows=' + $('#rows').val()
				});
			}
			function view(){
				refresh();
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height:40px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：访问记录&nbsp;&nbsp;&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> 显示行数 <s:textfield name="rows" id="rows"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="view();return false;">查看</a>	<a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">刷新</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<table id="tt" fit="true"></table>
		</div>
	</body>
</html>