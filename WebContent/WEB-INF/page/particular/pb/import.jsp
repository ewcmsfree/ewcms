<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>数据导入</title>
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function(){
				parent.$("#tt").datagrid('reload');				
			});
		</script>
	</head>
	<body >
		<s:form action="import" namespace="/particular/pb" method="post" enctype="multipart/form-data" style="padding: 5px;">
			<table class="formtable" align="center">
				<tr>
					<td>XML或ZIP文件：</td>
					<td width="80%">
						<s:file name="xmlFile" cssClass="inputtext" size="50"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">可以使用XML文件导入或者是包含有XML文件的ZIP文件进行导入</td>
				</tr>
				<tr>
					<td colspan="2" style="padding:0;">
						<div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
						   <a class="easyui-linkbutton" icon="icon-save" href="javascript:document.forms[0].submit();">保存</a>
						   <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:document.forms[0].reset();">重置</a>
						</div>								
					</td>
				</tr>
			</table>
			<s:hidden id="channelId" name="channelId"></s:hidden>
		</s:form>
	</body>
</html>