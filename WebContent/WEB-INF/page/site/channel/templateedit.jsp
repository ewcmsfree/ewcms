<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板信息编辑</title>
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
	    	$(function() {
		        <s:include value="../../alertMessage.jsp"/>
	    	});
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
					<td>
						<s:file name="templateFile" cssClass="inputtext" size="50"/>
					</td>
				</tr>
				<tr>
					<td>模板类型：</td>
					<td class="formFieldError">
						<s:select list="@com.ewcms.core.site.model.Template$TemplateType@values()" listValue="description" name="templateVo.type" id="templateVo.type" headerKey="" headerValue="------请选择------"></s:select>
						<s:fielderror ><s:param value="%{'templateVo.type'}" /></s:fielderror>&nbsp;&nbsp;<label style="color:red;">*</label>
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
						<s:fielderror><s:param value="%{'templateVo.describe'}" /></s:fielderror>&nbsp;&nbsp;<label style="color:red;">*</label>
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