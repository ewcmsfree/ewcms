<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>订阅内容</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
	    	$(function() {
	    		<s:include value="../../alertMessage.jsp"/>
	    	});
        </script>		
	</head>
	<body>
		<s:form id="notesForm" action="save" namespace="/message/content">
			<table class="formtable">
				<tr>
					<td width="60">标题：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="msgContentVo.title" size="50" maxlength="200"/>
						<s:fielderror ><s:param value="%{'msgContentVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
`						<s:textarea id="detail" name="msgContentVo.detail" cols="50"></s:textarea>
						<s:fielderror><s:param value="%{'msgContentVo.detail'}"/></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
			</table>
			<s:hidden id="msgContentId" name="msgContentVo.id"/>
			<s:hidden id="msgSendId" name="msgSendId"/>
		</s:form>
	</body>
</html>