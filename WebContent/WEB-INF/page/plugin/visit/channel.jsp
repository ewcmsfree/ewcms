<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>栏目点击排行</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/visit/dateutil.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
		<script type="text/javascript">
			var startDate = dateTimeToString(new Date(new Date() - 30*24*60*60*1000));
			var endDate = dateTimeToString(new Date());
			var tableUrl = '<s:url namespace="/plugin/visit" action="channelTable"/>?startDate=' + startDate + '&endDate=' + endDate;
			var channelId = "0";
			var channelIds = [];
			$(function() {
				channelIds.push(0);
				$('#explain').append('<span id="position_0"><a href="javascript:void(0);" style="text-decoration: none" onclick="positionSel(\'0\')">一级</a></span>');
				$('#startDate').val(startDate);
				$('#endDate').val(endDate);
				$('#tt').datagrid({
					singleSelect : true,
					pagination : false,
					nowrap : true,
					striped : true,
					url : tableUrl,
				    columns:[[ 
				             {field:'channelId',title:'栏目编号',hidden:true},
				             {field:'channelName',title:'栏目名称',width:200,
				            	formatter : function(val, rec){
				            		if (val == null) return '';
				            		if (rec.isChannelChildren){
				            			return '<a href="javascript:void(0);" style="text-decoration: none" onclick="channelChildren(\'' + rec.channelId + '\',\'' + rec.channelName + '\')">' + val + '</a>';
				            		}else{
				            			return val;
				            		}
				            	}
				            },
				            {field:'levelPv',title:'本级PV量',width:100},
				            {field:'levelSt',title:'本级页均停留时间',width:110},
				            {field:'trend',title:'时间趋势',width:70,
				            	formatter : function(val, rec){	
				            		return '<a href="javascript:void(0)" style="text-decoration: none" onclick="openTrend(\'' + rec.channelName + '\',\'' + rec.channelId + '\')">时间趋势</a>';
				            	}
				            },
				            {field:'pageView',title:'子栏目PV量',width:100},
				            {field:'stickTime',title:'子栏页均停留时间',width:110}
				    ]]  
				});
			});
			function showChart(){
				var parameter = {};
				parameter['startDate'] = startDate;
				parameter['endDate'] = endDate;
				$.post('<s:url namespace="/plugin/visit" action="channelReport"/>', parameter, function(result) {
			  		var myChart = new FusionCharts('<s:url value="/ewcmssource/fcf/swf/Pie3D.swf"/>?ChartNoDataText=无数据显示', 'myChartId', '640', '250','0','0');
		      		myChart.setDataXML(result);      
		      		myChart.render("divChart");
		   		});
			}
			function refresh(){
				startDate = $('#startDate').val();
				endDate = $('#endDate').val();
				showChart();
				$('#tt').datagrid({
					url:'<s:url namespace="/plugin/visit" action="channelTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val()
				})
			}
			function openTrend(name, value){
				ewcmsBOBJ = new EwcmsBase();
				var url = '<s:url namespace="/plugin/visit" action="channelTrend"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&channelId=' + value + '&channelName=' + name;
				ewcmsBOBJ.openWindow("#pop-window",{url:url,width:660,height:330,title: name + " 时间趋势"});
			}
			function channelChildren(id, name){
				channelId = id;
				channelIds.push(id);
				$('#explain').append('<span id="position_' +  id + '"> >> <a href="javascript:void(0);" style="text-decoration: none" onclick="positionSel(\'' + id + '\',\'' + name + '\')">' + name + '</a></span>');
				startDate = $('#startDate').val();
				endDate = $('#endDate').val();
				var parameter = {};
				parameter['startDate'] = startDate;
				parameter['endDate'] = endDate;
				parameter['channelParentId'] = id;
				$.post('<s:url namespace="/plugin/visit" action="channelReport"/>', parameter, function(result) {
			  		var myChart = new FusionCharts('<s:url value="/ewcmssource/fcf/swf/Pie3D.swf"/>?ChartNoDataText=无数据显示', 'myChartId', '640', '250','0','0');
		      		myChart.setDataXML(result);      
		      		myChart.render("divChart");
		   		});
				$('#tt').datagrid({
					url:'<s:url namespace="/plugin/visit" action="channelTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + "&channelParentId=" + id
				})
			}
			function positionSel(index, name){
				if (channelId == index) return false;
				if (index == "0"){
					window.location.reload();
				}else{
					var position = $.inArray(index, channelIds);
					if ((position > -1) && (position != channelIds.length -1)){
						for (var i = position; i < channelIds.length; i++){
							$('#position_' + channelIds[i]).remove();
						}
						channelIds.splice(position + 1);
					}
					channelChildren(index, name);
				}
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		 <div region="north" style="height:330px" border="false">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：<span id="explain"></span> 下子栏目点击排行
					</td>
				</tr>
				<tr>
					<td>
						从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
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
		<div id="pop-window" class="easyui-window" title="弹出窗口" icon="icon-visit-analysis" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
            </div>
        </div>
	</body>
</html>