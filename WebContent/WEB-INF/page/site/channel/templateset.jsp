<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板属性设置</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
	</head>
	<body>
		<s:form action="setProperty" namespace="/site/template" method="post">
			<table class="formtable" align="center">
				<tr>
					<td>模板类型：</td>
					<td>
						<s:select list="templateTypeList" name="templateVo.type" listKey="name()" listValue="description" />
					</td>
				</tr>
				<tr>
					<td>路径生成规则：</td>
					<td >
						<s:textfield name="templateVo.uriPattern" cssClass="inputtext"/>
					</td>				
				</tr>
			</table>
            <s:hidden name="templateVo.id"/>
		</s:form>
	</body>
</html>