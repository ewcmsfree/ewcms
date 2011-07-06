<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
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
        <ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/vote/questionnaire">
			<table class="formtable" >
				<tr>
					<td>主题：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="questionnaireVo.title" size="50"/>
						<s:fielderror ><s:param value="%{'questionnaireVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>查看方式：</td>
					<td>
						<s:radio list="@com.ewcms.content.vote.model.QuestionnaireStatus@values()" listValue="description" name="questionnaireVo.questionnaireStatus" id="questionnaireVo_questionnaireStatus"></s:radio>
					</td>
				</tr>
				<tr>
					<td>验证码：</td>
					<td>
						<s:checkbox id="verifiCode" name="questionnaireVo.verifiCode" cssStyle="vertical-align: middle;"/><label for="verifiCode">验证码</label>&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<ewcms:datepicker id="startTime" name="startTime" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<ewcms:datepicker id="endTime" name="endTime" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td>人数：</td>
					<td>
						<s:textfield id="number" cssClass="inputtext" name="questionnaireVo.number"/>
						<s:fielderror ><s:param value="%{'questionnaireVo.number'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>结束投票：</td>
					<td>
						<s:checkbox id="voteFlag" name="questionnaireVo.voteFlag" cssStyle="vertical-align: middle;"/><label for="verifiCode">结束投票</label>&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			<s:hidden name="questionnaireVo.id"/>
			<s:hidden name="questionnaireVo.sort"/>
			<s:hidden name="channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>