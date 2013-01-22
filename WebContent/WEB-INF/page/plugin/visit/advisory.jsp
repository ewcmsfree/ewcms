<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>网上咨询统计</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function() {
				$('#tt').treegrid({
					rownumbers: true,  
	                collapsible: true,
	                striped : true,
					url : '<s:url namespace="/plugin/visit" action="advisoryTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val(),
					idField:'id',
					treeField: 'text',
				    columns:[[
						{field:'id',title:'组织编号',rowspan:2,width:60},
						{field:'text',title:'组织名称',rowspan:2,width:200},
			            {field:'tgCount',title:'已通过',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.tgCount;
			            	}	
			            },  
			            {field:'wtgCount',title:'未通过',width:60,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.wtgCount;
			            	}	
			            },
			            {field:'sumCount',title:'总数',rowspan:2,width:80,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.tgCount + rec.data.wtgCount;
			            	}	
			            }
				    ]]
				});
			});
			function refresh(){
				$('#tt').treegrid({
					url : '<s:url namespace="/plugin/visit" action="advisoryTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val()
				});
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height:40px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：网上咨询统计&nbsp;&nbsp;&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<table id="tt" fit="true"></table>
		</div>
	</body>
</html>