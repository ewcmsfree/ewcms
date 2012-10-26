<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>发送消息</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
        	$(function() {
        		<s:include value="../../alertMessage.jsp"/>
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
            function selType(obj){
            	if (obj.value != 'GENERAL'){
            		$('#trUserName').hide();
            	}else{
            		$('#trUserName').show();
            	}
            }
        </script>		
	</head>
	<body>
		<s:form id="messageForm" action="save" namespace="/message/send">
			<table class="formtable">
				<tr>
					<td width="60">标题：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="msgSendVo.title" size="50" maxlength="200"/>
						<s:fielderror ><s:param value="%{'msgSendVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr class="formFieldError">
					<td>内容：</td>
					<td>
						<s:textarea id="content" name="content" cols="50"></s:textarea>
						<s:fielderror><s:param value="%{'content'}"/></s:fielderror>&nbsp;&nbsp;<label style="color:red;">*</label>
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
			<s:hidden name="msgSendVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>