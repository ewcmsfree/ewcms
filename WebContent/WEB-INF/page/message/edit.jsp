<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>发送消息</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
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
				        <s:select list="@com.ewcms.plugin.message.model.MsgSend$Type@values()" listValue="description" name="msgSendVo.type" id="type" onchange="selType(this);"></s:select>  
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