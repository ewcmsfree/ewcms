<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>调查投票</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
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
		<s:form action="save" namespace="/vote/subject">
			<table class="formtable" >
				<tr>
					<td width="15%" height="21px">主题名称：</td>
					<td width="85%" height="21px" class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="subjectVo.title" size="50"/>
						<s:fielderror ><s:param value="%{'subjectVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>查看方式：</td>
					<td>
						<s:radio list="@com.ewcms.content.vote.model.SubjectStatus@values()" listValue="description" name="subjectVo.subjectStatus" id="subjectVo_subjectStatus"></s:radio>
					</td>
				</tr>
			</table>
			<s:hidden name="subjectVo.id"/>
			<s:hidden name="subjectVo.sort"/>
			<s:hidden name="questionnaireId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>