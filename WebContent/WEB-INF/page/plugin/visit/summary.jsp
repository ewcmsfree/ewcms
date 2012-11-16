<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>综合报告</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/visit/dateutil.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
		<script type="text/javascript">
			var tableUrl = '<s:url namespace="/plugin/visit" action="summaryTable"/>';
			$(function() {
				$('#tt').datagrid({
					singleSelect : true,
					pagination : false,
					nowrap : true,
					striped : true,
					url : tableUrl,
				    columns:[[  
				            {field:'name',title:'名称',width:130},  
				            {field:'pv',title:'PV数量',width:100},  
				            {field:'uv',title:'UV数量',width:100},
				            {field:'ip',title:'IP数量',width:100},
				            {field:'rvRate',title:'回头率',width:100},
				            {field:'avgTime',title:'平均访问时长',width:100}
				    ]]  
				});
			});
			function showChart(){
				var startDate = dateTimeToString(new Date(new Date() - 30*24*60*60*1000));
				var endDate = dateTimeToString(new Date());
				var parameter = {};
				parameter['startDate'] = startDate;
				parameter['endDate'] = endDate;
				parameter['labelCount'] = 8;
				$.post('<s:url namespace="/plugin/visit" action="summaryReport"/>', parameter, function(result) {
			  		var myChart = new FusionCharts('<s:url value="/ewcmssource/fcf/swf/MSLine.swf"/>?ChartNoDataText=无数据显示', 'myChartId', '640', '250','0','0');
		      		myChart.setDataXML(result);      
		      		myChart.render("divChart");
		   		});
			}
			function refresh(){
				showChart();
				$('#tt').datagrid({
					url : tableUrl
				});
			}
		</script>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height:310px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：综合报告 <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">刷新</a>
					</td>
				</tr>
				<tr valign="top">
					<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable">
						<tr>
							<td style="padding:0px;">
								<div style="height: 100%;margin:0px;">
									<div id="divChart" style="width:640px;height:250px;background-color:white;"></div>
									<script type="text/javascript">
										showChart();
									</script>
								</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</div>
		<div region="center" title='<strong>从 <font color="red"><s:property value='firstAddDate'/></font> 开始统计，总计 <font color="red"><s:property value="visitDay"/></font> 天 </strong>'>
			<table id="tt" fit="true"></table>
		</div>
	</body>
</html>