<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>组织机构发布统计</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function() {
				$('#tt').treegrid({
					rownumbers: true,  
	                collapsible: true,
	                striped : true,
					url : '<s:url namespace="/plugin/visit" action="organReleasedTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val(),
					idField:'id',
					treeField: 'text',
				    columns:[[
				        {field:'id',title:'频道编号',width:60},
			            {field:'text',title:'频道名称',width:200},
			            {field:'draftCount',title:'初稿数',width:150,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.draftCount;
			            	}	
			            },  
			            {field:'reeditCount',title:'重新编辑数',width:150,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.reeditCount;
			            	}	
			            },
			            {field:'reviewCount',title:'审核数',width:150,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.reviewCount;
			            	}	
			            },
			            {field:'releaseCount',title:'已发布数',width:150,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.releaseCount;
			            	}	
			            },
			            {field:'sumCount',title:'总数',width:150,
			            	formatter : function(val, rec){
			            		if (rec.data != null)
			            		return rec.data.draftCount + rec.data.reeditCount + rec.data.reviewCount + rec.data.releaseCount;
			            	}	
			            }
				        
				    ]]  
				});
			});
			function refresh(){
				$('#tt').treegrid({
					url : '<s:url namespace="/plugin/visit" action="organReleasedTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val()
				});
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		<div region="north" style="height:40px" border="false">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：栏目发布统计&nbsp;&nbsp;&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<table id="tt" fit="true" border="false"></table>
		</div>
	</body>
</html>