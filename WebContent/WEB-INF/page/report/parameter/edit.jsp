<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>参数设置</title>
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
		<s:form action="save" namespace="/report/parameter">
			<table class="formtable" >
				<tr>
					<td>参数编号：</td>
					<td>
						<s:textfield name="parameterVo.id" cssClass="inputtext" readonly="true" />
					</td>
					<td>参数名：</td>
					<td>
						<s:textfield name="parameterVo.enName" cssClass="inputtext" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>中文名：</td>
					<td>
						<s:textfield name="parameterVo.cnName" cssClass="inputtext" />
					</td>
					<td>默认值：</td>
					<td>
						<s:textfield name="parameterVo.defaultValue" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>数据输入方式：</td>
					<td>
						<s:select list="@com.ewcms.function.report.model.ParametersType@values()" listValue="description" name="parameterVo.type" id="parametersType"></s:select>
					</td>
					<td>辅助数据设置：</td>
					<td>
						<s:textfield name="parameterVo.value" cssClass="inputtext"/>
					</td>
				</tr>
			</table>
			<s:hidden name="reportId"/>
			<s:hidden name="reportType"/>
			<s:hidden name="parameterVo.className"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>