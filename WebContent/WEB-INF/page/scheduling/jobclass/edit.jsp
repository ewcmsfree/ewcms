<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>调度器任务执行类</title>
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
		<s:form action="save" namespace="/scheduling/jobclass">
			<table class="formtable" >
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
						<s:textfield name="alqcJobClassVo.className" cssClass="inputtext" size="70" maxlength="127" />
						<s:fielderror><s:param value="%{'alqcJobClassVo.className'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>类实体：</td>
					<td class="formFieldError">
						<s:textfield name="alqcJobClassVo.classEntity" cssClass="inputtext" size="70" maxlength="127" />
						<s:fielderror><s:param value="%{'alqcJobClassVo.classEntity'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>
						<s:textarea name="alqcJobClassVo.description" cssStyle="height:80px;width:100%;"/>
					</td>
				</tr>
			</table>
			<s:hidden name="alqcJobClassVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>