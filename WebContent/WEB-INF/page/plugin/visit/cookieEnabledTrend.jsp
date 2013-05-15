<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>时间趋势</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/visit/dateutil.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
		<script type="text/javascript">
			var startDate = parent.$('#startDate').val();
			var endDate = parent.$('#endDate').val();
			$(function() {
				$('#startDate').val(startDate);
				$('#endDate').val(endDate);
			});
			function showChart(){
				var parameter = {};
				parameter['startDate'] = startDate;
				parameter['endDate'] = endDate;
				parameter['enabled'] = $('#enabled').val();
				parameter['labelCount'] = 8;
				$.post('<s:url namespace="/plugin/visit" action="cookieEnabledTrendReport"/>', parameter, function(result) {
			  		var myChart = new FusionCharts('<s:url value="/ewcmssource/fcf/swf/MSLine.swf"/>?ChartNoDataText=无数据显示', 'myChartId', '640', '250','0','0');
		      		myChart.setDataXML(result);      
		      		myChart.render("divChart");
		   		});
			}
			function view(){
				startDate = $('#startDate').val();
				endDate = $('#endDate').val();
				showChart();
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		 <s:hidden id="enabled" name="enabled"/>
		 <div region="north" style="height:310px" border="false">
			<table width="100%" border="0" cellspacing="2" cellpadding="0"style="border-collapse: separate; border-spacing: 2px;">
				<tr>
					<td>
						从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="view();return false;">查看</a>
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
	</body>
</html>