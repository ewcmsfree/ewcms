<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>调查投票</title>
		<s:include value="../../../taglibs.jsp"/>
        <script type="text/javascript">
	    	$(function() {
		        <s:include value="../../../alertMessage.jsp"/>
	    	});
        </script>
	</head>
	<body>
		<s:form action="save" namespace="/vote/subject">
			<table class="formtable" >
				<tr>
					<td width="17%" height="21px">主题名称：</td>
					<td width="83%" height="21px" class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="subjectVo.title"/>
						<s:fielderror ><s:param value="%{'subjectVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>查看方式：</td>
					<td>
						<s:radio list="@com.ewcms.plugin.vote.model.Subject$Status@values()" listValue="description" name="subjectVo.status" id="subjectVo_status"></s:radio>
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