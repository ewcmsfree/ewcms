<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>资源信息</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>	
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>			
		<script type="text/javascript"> 
			function tipMessage(){
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
							$.messager.alert('提示','<s:property escape="false"/>');
			        </s:iterator>  
		     	</s:if>  
			}
		</script> 				
	</head>
	<body onload="tipMessage();">
					<s:form action="saveInfo" namespace="/site/template/source">	
						<table class="formtable" align="center">
								<tr>
									<td >资源路径：</td>
									<td >
										<s:textfield name="sourceVo.path" readonly="true" cssClass="inputdisabled" size="60"/>
									</td>
								</tr>						
								<tr>
									<td >资源名称：</td>
									<td >
										<s:textfield name="sourceVo.name" readonly="true" cssClass="inputdisabled" size="60"/>
									</td>
								</tr>
								<tr>
									<td>资源说明：</td>
									<td>
										<s:textfield name="sourceVo.describe" cssClass="inputtext" size="60"/>
									</td>
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
						<s:hidden name="sourceVo.id"/>	
					</s:form>
	</body>
</html>