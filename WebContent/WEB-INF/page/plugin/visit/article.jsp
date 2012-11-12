<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>区域分布</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/visit/dateutil.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/fcf/js/FusionCharts.js"/>'></script>
		<script type="text/javascript">
			$(function() {
				$('#tt').datagrid({
					singleSelect : true,
					pagination : false,
					nowrap : true,
					striped : true,
					url : '<s:url namespace="/plugin/visit" action="articleTable"/>?rows=' + $('#rows').val(),
				    columns:[[  
				            {field:'channelName',title:'栏目名称',width:150}, 
				            {field:'title',title:'标题',width:300,
				            	formatter : function(val, rec){
				            		return '<a href="' + rec.url + '">' + val + "</a>";
				            	}	
				            },
				            {field:'owner',title:'创建者',width:100},  
				            {field:'pageView',title:'点击量',width:100},
				            {field:'stickTime',title:'页均停留时间',width:100}
				    ]]  
				});
			});
			function view(){
				$('#tt').datagrid({
					url:'<s:url namespace="/plugin/visit" action="articleTable"/>?rows=' + $('#rows').val()
				})
			}
		</script>
	</head>
	<body class="easyui-layout">
		 <div region="north" style="height:40px">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"style="border-collapse: separate; border-spacing: 6px;">
				<tr>
					<td>
						当前报表：文章点击排行&nbsp;&nbsp;&nbsp;&nbsp;显示行数 <s:textfield name="rows" id="rows"/> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="view();return false;">查看</a> <a class="easyui-linkbutton" href="javascript:void(0)" onclick="view();return false;">刷新</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="center">
			<table id="tt" fit="true"></table>
		</div>
	</body>
</html>