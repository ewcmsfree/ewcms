<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>报表分类设置</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
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
		<s:form action="save" namespace="/report/category">
			<table class="formtable" >
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
						<s:textfield name="categoryReportVo.name" cssClass="inputtext" maxlength="10" />
						<s:fielderror><s:param value="%{'categoryReportVo.name'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3">
						<s:textarea name="categoryReportVo.remarks" cols="60"/>
					</td>
				</tr>
			</table>
			<s:hidden name="categoryReportVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>