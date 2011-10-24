<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>发送消息</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
        <script type="text/javascript">
        	$(function() {
        		$('#userInfo').combobox({
        			url: '<s:url namespace="/message/send" action="userInfo"/>',
        			valueField:'id',
        	        textField:'text',
        			editable:false,
        			multiple:true,
        			cascadeCheck:false,
        			panelWidth:200,
        			panelHeight:60
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
            function selType(obj){
            	if (obj.value != 'GENERAL'){
            		$('#trUserName').hide();
            	}else{
            		$('#trUserName').show();
            	}
            }
        </script>		
	</head>
	<body onload="tipMessage();">
		<s:form id="notesForm" action="save" namespace="/message/send">
			<table class="formtable">
				<tr>
					<td width="60">标题：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="msgSendVo.title" size="50" maxlength="200"/>
						<s:fielderror ><s:param value="%{'msgSendVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
						<s:textarea id="content" name="content" cols="50"></s:textarea>
					</td>
				</tr>
				<tr>
				    <td>类型：</td>
				    <td>
				        <s:select list="@com.ewcms.content.message.model.MsgType@values()" listValue="description" name="msgSendVo.type" id="type" onchange="selType(this);"></s:select>  
				    </td>
				</tr>
				<tr id="trUserName">
				    <td>用户名：</td>
				    <td>
				        <input id="userInfo" name="receiveUserNames"></input>
				    </td>
				</tr>
			</table>
			<s:hidden id="msgSendId" name="msgSendVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>