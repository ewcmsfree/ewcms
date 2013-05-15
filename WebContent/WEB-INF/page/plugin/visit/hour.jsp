<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>时段分布</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/visit/dateutil.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
		<script type="text/javascript">
			var startDate = dateTimeToString(new Date(new Date() - 30*24*60*60*1000));
			var endDate = dateTimeToString(new Date());
			$(function() {
				$('#startDate').val(startDate);
				$('#endDate').val(endDate);
				$('#tt').datagrid({
					singleSelect : true,
					pagination : false,
					nowrap : true,
					striped : true,
					url : '<s:url namespace="/plugin/visit" action="hourTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val(),
				    columns:[[  
				            {field:'name',title:'时段',width:120}, 
				            {field:'pvRate',title:'PV比例',width:100},
				            {field:'pv',title:'PV数量',width:100},  
				            {field:'uv',title:'UV数量',width:100},
				            {field:'ip',title:'IP数量',width:100},
				            {field:'rv',title:'回头客人数',width:100}
				    ]]  
				});
			});
			function showChart(){
				var parameter = {};
				parameter['startDate'] = startDate;
				parameter['endDate'] = endDate;
				parameter['labelCount'] = 24;
				$.post('<s:url namespace="/plugin/visit" action="hourReport"/>', parameter, function(result) {
			  		var myChart = new FusionCharts('<s:url value="/ewcmssource/fcf/swf/MSLine.swf"/>?ChartNoDataText=无数据显示', 'myChartId', '640', '250','0','0');
		      		myChart.setDataXML(result);      
		      		myChart.render("divChart");
		   		});
			}
			function refresh(){
				startDate = $('#startDate').val();
				endDate = $('#endDate').val();
				showChart();
				$('#tt').datagrid({
					url:'<s:url namespace="/plugin/visit" action="hourTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val()
				})
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		 <div region="north" style="height:310px" border="false">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：时段分布&nbsp;&nbsp;&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
					</td>
				</tr>
				<tr valign="top">
					<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable">
						<tr>
							<td style="padding:0px;">
								<div style="height: 100%;margin:0px;">
									<div id="divChart" style="width:640px;height:250px;background-color:white"></div>
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
		<div region="center">
			<table id="tt" fit="true" border="false"></table>
		</div>
	</body>
</html>