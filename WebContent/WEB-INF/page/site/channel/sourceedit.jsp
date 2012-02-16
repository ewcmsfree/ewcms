<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>资源信息编辑</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
	    	$(function() {
		        <s:include value="../../alertMessage.jsp"/>
	    	});
        </script>			
	</head>
	<body>
		<s:form action="save" namespace="/site/template/source" method="post" enctype="multipart/form-data">
			<table class="formtable" align="center">
				<tr>
					<td >资源路径：</td>
					<td >
						<s:textfield name="sourceVo.path" readonly="true" cssClass="inputdisabled" size="60"/>
					</td>
				</tr>				
				<tr>
					<td>资源文件：</td>
					<td class="formFieldError" width="80%">
						<s:file name="sourceFile" cssClass="inputtext" size="50"/>
						<s:fielderror><s:param value="%{'sourceFile'}" /></s:fielderror>
					</td>
				</tr>									
				<tr>
					<td>说明：</td>
					<td class="formFieldError">
						<s:textfield name="sourceVo.describe" cssClass="inputtext"/>
						<s:fielderror ><s:param value="%{'sourceVo.descripe'}" /></s:fielderror>
					</td>				
				</tr>
			</table>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>
            <s:hidden name="sourceVo.channelId"/>
            <s:hidden name="sourceVo.id"/>
		</s:form>
	</body>
</html>