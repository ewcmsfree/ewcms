<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>备忘录</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
        <script type="text/javascript">
			function tipMessage(){
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
						$.messager.alert('提示','<s:property escape="false"/>','info');
			        </s:iterator>  
		     	</s:if>  
			}
            <s:property value="javaScript"/>
        </script>		
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/document/notes">
			<table class="formtable" >
				<tr>
					<td>标题：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="memorandaVo.title"/>
						<s:fielderror ><s:param value="%{'memorandaVo.title'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
						<s:textarea id="content" name="memorandaVo.content" cols="50"></s:textarea>
					</td>
				</tr>
			</table>
			<s:hidden id="memorandaId" name="memorandaVo.id"/>
			<s:hidden id="memorandaVo.noteTime" name="memorandaVo.noteTime"/>
			<s:hidden id="memorandaVo.userName" name="memorandaVo.userName"/>
			<s:hidden id="year" name="year"/>
			<s:hidden id="month" name="month"/>
			<s:hidden id="day" name="day"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>