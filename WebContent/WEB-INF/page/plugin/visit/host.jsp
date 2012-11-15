<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>被访问主机分析</title>	
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
					rownumbers : true,
					url : '<s:url namespace="/plugin/visit" action="hostTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val(),
				    columns:[[  
				            {field:'name',title:'名称',width:200},
				            {field:'sumPv',title:'次数',width:100},
				            {field:'pvRate',title:'比例',width:100},
				            {field:'trend',title:'时间趋势',width:70,
				            	formatter : function(val, rec){	
				            		return '<a href="javascript:void(0)" onclick="openTrend(\'' + rec.name + '\')" style="text-decoration: none">时间趋势</a>';
				            	}
				            }
				    ]]  
				});
			});
			function refresh(){
				startDate = $('#startDate').val();
				endDate = $('#endDate').val();
				showChart();
				$('#tt').datagrid({
					url : '<s:url namespace="/plugin/visit" action="hostTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val()
				});
			}
			function openTrend(value){
				ewcmsBOBJ = new EwcmsBase();
				var url = '<s:url namespace="/plugin/visit" action="hostTrend"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&host=' + value;
				ewcmsBOBJ.openWindow("#pop-window",{url:url,width:708,height:330,title:"时间趋势"});
			}
			function showChart(){
				var parameter = {};
				parameter['startDate'] = startDate;
				parameter['endDate'] = endDate;
				parameter['rows'] = $('#rows').val();
				$.post('<s:url namespace="/plugin/visit" action="hostReport"/>', parameter, function(result) {
			  		var myChart = new FusionCharts('<s:url value="/ewcmssource/fcf/swf/Pie3D.swf"/>?ChartNoDataText=无数据显示', 'myChartId', '680', '250','0','0');
		      		myChart.setDataXML(result);      
		      		myChart.render("divChart");
		   		});
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height:310px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：被访问主机分析&nbsp;&nbsp;&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
					</td>
				</tr>
				<tr valign="top">
					<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable">
						<tr>
							<td style="padding:0px;">
								<div style="height: 100%;margin:0px;">
									<div id="divChart" style="width:680px;height:250px;background-color:white"></div>
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
			<table id="tt" fit="true"></table>
		</div>
		<div id="pop-window" class="easyui-window" title="弹出窗口" icon="icon-visit-analysis" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
            </div>
        </div>
	</body>
</html>