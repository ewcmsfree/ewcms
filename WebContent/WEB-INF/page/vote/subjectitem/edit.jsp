<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>调查投票</title>
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
		<s:form action="save" namespace="/vote/subjectitem">
			<table class="formtable" >
				<tr>
					<td width="17%" height="21px;">选项名称：</td>
					<td width="83%" height="21px" class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="subjectItemVo.title"/>
						<s:fielderror ><s:param value="%{'subjectItemVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>选项方式：</td>
					<td>
						<s:radio list="@com.ewcms.content.vote.model.SubjectItemStatus@values()" listValue="description" name="subjectItemVo.subjectItemStatus" id="subjectItemVo_subjectItemStatus"></s:radio>
					</td>
				</tr>
				<tr>
					<td>票数：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="subjectItemVo.voteNumber" maxlength="10"/>
						<s:fielderror ><s:param value="%{'subjectItemVo.voteNumber'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="subjectItemVo.id"/>
			<s:hidden name="subjectItemVo.sort"/>
			<s:hidden name="subjectId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>