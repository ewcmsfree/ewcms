<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板信息编辑</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript">
            <s:property value="javaScript"/>
        </script>			
	</head>
	<body>
		<s:form action="save" namespace="/site/template" method="post" enctype="multipart/form-data">
			<table class="formtable" align="center">
				<tr>
					<td>模板路径：</td>
					<td>
						<s:textfield name="templateVo.path"  readonly="true" cssClass="inputdisabled" size="50"/>
					</td>
				</tr>			
				<tr>
					<td>模板文件：</td>
					<td class="formFieldError" width="80%">
						<s:file name="templateFile" cssClass="inputtext" size="50"/>
						<s:fielderror><s:param value="%{'templateFile'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>模板类型：</td>
					<td>
						<s:select list="templateTypeList" name="templateVo.type" listKey="name()" listValue="description" headerKey="" headerValue="------请选择------"/>
					</td>
				</tr>
				<tr>
					<td>URI规则：</td>
					<td >
						<s:textfield name="templateVo.uriPattern" cssClass="inputtext"/>
					</td>				
				</tr>				
				<tr>
					<td>说明：</td>
					<td class="formFieldError">
						<s:textfield name="templateVo.describe" cssClass="inputtext"/>
						<s:fielderror ><s:param value="%{'templateVo.descripe'}" /></s:fielderror>
					</td>				
				</tr>
			</table>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>
            <s:hidden name="templateVo.channelId"/>
            <s:hidden name="templateVo.id"/>
		</s:form>
	</body>
</html>