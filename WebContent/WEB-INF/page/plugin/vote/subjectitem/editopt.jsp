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
		<s:form action="saveopt" namespace="/vote/subjectitem">
			<table class="formtable" >
				<tr>
					<td width="17%" height="21px">选项方式：</td>
					<td width="83%" height="21px">
						<s:radio list="#{'SINGLETEXT':'单行文本'}" name="subjectItemVo.status"></s:radio>
						<s:radio list="#{'MULTITEXT':'多行文本'}" name="subjectItemVo.status"></s:radio>
					</td>
				</tr>
				<tr>
					<td >票数：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="subjectItemVo.voteNumber" maxlength="10"/>
						<s:fielderror ><s:param value="%{'subjectItemVo.voteNumber'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="subjectItemVo.id"/>
			<s:hidden name="subjectItemVo.sort"/>
			<s:hidden name="subjectId"/>
		</s:form>
	</body>
</html>