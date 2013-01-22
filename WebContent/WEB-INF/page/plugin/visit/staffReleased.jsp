<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>人员发布统计</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function() {
				$('#tt').datagrid({
					singleSelect : true,
					pagination : false,
					nowrap : true,
					striped : true,
					rownumbers : true,
					url : '<s:url namespace="/plugin/visit" action="staffReleasedTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val(),
				    columns:[[  
				            {field:'organName',title:'所属机构',width:200},
				            {field:'userName',title:'用户',width:150,
				            	formatter : function(val, rec){
				            		return rec.userName + "(" + rec.name + ")";
				            	}	
				            },
				            {field:'draftCount',title:'初稿数',width:150},  
				            {field:'reeditCount',title:'重新编辑数',width:150},
				            {field:'reviewCount',title:'审核数',width:150},
				            {field:'releaseCount',title:'已发布数',width:150},
				            {field:'sumCount',title:'总数',width:150,
				            	formatter : function(val, rec){
				            		return rec.draftCount + rec.reeditCount + rec.reviewCount + rec.releaseCount;
				            	}	
				            }
				    ]]  
				});
				$('#cc_channel').combotree({  
				    url:'<s:url namespace="/site/channel" action="tree"/>',
				    required:false,
				    onClick : function(node){
				    	var rootnode = $('#cc_channel').combotree('tree').tree('getRoot');
				    	if (node.id == rootnode.id){
				    		$('#cc_channel').combotree('setValue', '');
							return;
				    	}
				    }
				});
			});
			function refresh(){
				$('#tt').datagrid({
					url : '<s:url namespace="/plugin/visit" action="staffReleasedTable"/>?startDate=' + $('#startDate').val() + '&endDate=' + $('#endDate').val() + '&channelId=' + $('#cc_channel').combotree('getValue')
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
						当前报表：人员发布统计&nbsp;&nbsp;&nbsp;&nbsp;栏目名称：<select id="cc_channel" style="width:200px;" required="true"></select>&nbsp;&nbsp;从 <ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd"/> 至 <ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<table id="tt" fit="true"></table>
		</div>
	</body>
</html>