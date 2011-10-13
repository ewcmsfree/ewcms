<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>文章分类属性</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
        <script type="text/javascript">
    	$(function() {
    		$('#userInfo').combobox({
    			url: '<s:url namespace="/document/reviewprocess" action="userInfo"><s:param name="processId" value="reviewProcessVo.id"></s:param></s:url>',
    			valueField:'id',
    	        textField:'text',
    			editable:false,
    			multiple:true,
    			cascadeCheck:false,
    			panelWidth:200,
    			panelHeight:100
    		});
    		$('#groupInfo').combobox({
    			url: '<s:url namespace="/document/reviewprocess" action="groupInfo"><s:param name="processId" value="reviewProcessVo.id"></s:param></s:url>',
    			valueField:'id',
    	        textField:'text',
    			editable:false,
    			multiple:true,
    			cascadeCheck:false,
    			panelWidth:200,
    			panelHeight:100
    		});
    	})
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
		<s:form action="save" namespace="/document/reviewprocess">
			<table class="formtable" >
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="reviewProcessVo.name"/>
						<s:fielderror ><s:param value="%{'reviewProcessVo.name'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>用户名：</td>
					<td>
						<input id="userInfo" name="reviewUserNames"></input>
					</td>
				</tr>
				<tr>
					<td>用户组：</td>
					<td>
						<input id="groupInfo" name="reviewGroupNames"></input>
					</td>
				</tr>
			</table>
			<s:hidden id="processId" name="reviewProcessVo.id"/>
			<s:hidden name="reviewProcessVo.channelId"/>
			<s:hidden name="channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>