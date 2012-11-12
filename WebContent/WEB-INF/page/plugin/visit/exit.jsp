<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>出口分析</title>	
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
					url : '<s:url namespace="/plugin/visit" action="exitTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&rows=' + $('#rows').val(),
				    columns:[[  
				            {field:'url',title:'出口URL',width:500,
				            	formatter : function(val, rec){
				            		if (val == null) return ''; 
				            		return '<a href="' + val + '" target="_blank">' + val + '</a>';
				            	}
				            },
				            {field:'count',title:'出口次数',width:100},
				            {field:'rate',title:'比例',width:100},
				            {field:'trend',title:'时间趋势',width:70,
				            	formatter : function(val, rec){	
				            		return '<a href="javascript:void(0)" onclick="openTrend(\'' + rec.url + '\')">时间趋势</a>';
				            	}
				            }
				    ]]  
				});
			});
			function refresh(){
				$('#tt').datagrid({
					url : '<s:url namespace="/plugin/visit" action="exitTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&rows=' + $('#rows').val()
				});
			}
			function view(){
				refresh();
			}
			function openTrend(value){
				ewcmsBOBJ = new EwcmsBase();
				var url = '<s:url namespace="/plugin/visit" action="exitTrend"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&url=' + value;
				ewcmsBOBJ.openWindow("#pop-window",{url:url,width:708,height:330,title:"时间趋势"});
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height:40px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：出口分析&nbsp;&nbsp;&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> 显示行数 <s:textfield name="rows" id="rows"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="view();return false;">查看</a>	<a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">刷新</a>
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