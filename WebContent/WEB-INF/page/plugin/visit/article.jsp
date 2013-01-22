<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>文章点击排行</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
		<script type="text/javascript">
			var tableUrl = '<s:url namespace="/plugin/visit" action="articleTable"/>'
			var channelId = 0;
			$(function() {
				$('#tt').datagrid({
					singleSelect : true,
					pagination : false,
					nowrap : true,
					striped : true,
					fitColumns : true,
					url : tableUrl,
				    columns:[[  
				            {field:'channelName',title:'栏目名称',width:150}, 
				            {field:'title',title:'标题',width:300,
				            	formatter : function(val, rec){
				            		return '<a href="' + rec.url + '" style="text-decoration: none" target="_blank">' + val + '</a>';
				            	}	
				            },
				            {field:'owner',title:'创建者',width:100},  
				            {field:'pageView',title:'点击量',width:100},
				            {field:'stickTime',title:'页均停留时间',width:100}
				    ]]  
				});
				$('#cc_channel').combotree({  
				    url:'<s:url namespace="/site/channel" action="tree"/>',
				    onClick : function(node){
				    	var rootnode = $('#cc_channel').combotree('tree').tree('getRoot');
				    	if (node.id == rootnode.id){
				    		$('#cc_channel').combotree('setValue', '');
				    		channelId = 0;
				    	}else{
				    		channelId = node.id;
				    	}
				    }
				});
			});
			function refresh(){
				var param = "";
				if (channelId != 0){
					param = '?channelId=' + channelId;
				}
				$('#tt').datagrid({
					url: tableUrl + param
				});
			}
		</script>
	</head>
	<body class="easyui-layout">
		 <div region="north" style="height:40px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：文章点击排行&nbsp;&nbsp;&nbsp;&nbsp;<select id="cc_channel" style="width:200px;"></select>&nbsp;<a class="easyui-linkbutton" href="javascript:void(0)" onclick="refresh();return false;">查看</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<table id="tt" fit="true"></table>
		</div>
	</body>
</html>