<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>站点发布设置</title>	
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
	    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>    
		<script type="text/javascript"> 
			function tipMessage(){
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
							$.messager.alert('提示','<s:property escape="false"/>');
			        </s:iterator>  
		     	</s:if> 
		     	outputTypeChange('<s:property value="siteVo.siteServer.outputType"/>'); 
			}

			function outputTypeChange(valuestr){
				$('#serverInfo1').hide();
				$('#serverInfo2').hide();
				$('#serverInfo3').hide();
				$('#serverInfo4').hide();
				$('#serverInfo5').hide();
				if(valuestr=="LOCAL"){
					$('#serverInfo5').show();
					return;
				}
				if(valuestr=="SFTP" || valuestr=="FTP" || valuestr=="FTPS"){
					$('#serverInfo1').show();
					$('#serverInfo2').show();
					$('#serverInfo3').show();
					$('#serverInfo4').show();
					$('#serverInfo5').show();
					return;
				}				
			}

			function testServer(){
				var formValue = $('#serverForm').serialize();
	            $.post('<s:url action="testServer"/>',formValue,function(data){
	    	    	$.messager.alert('提示',data);
	    	    });
			}
		</script>
	</head>
	<body onload="tipMessage();">
		<s:form action="saveServer" namespace="/site/organ" id="serverForm">				
			<table class="formtable" align="center">
				<tr>
					<td>站点名称：</td>
					<td width="80%" class="formFieldError">
						<s:textfield name="siteVo.siteName" readonly="true" cssClass="inputdisabled" size="40"/>
						<ul class="errorMessage"><li><span style="color:gray;">(内部编号：<s:property value="siteVo.id"/>)</span></li></ul>
					</td>
				</tr>				
				<tr>
					<td>发布类型：</td>
					<td class="formFieldError">
						<s:select list="outputTypeList" name="siteVo.siteServer.outputType" cssClass="inputtext"  listKey="name()" listValue="description"  onchange="outputTypeChange(this.value);" headerKey="" headerValue="------请选择------"/>
					</td>				
				</tr>
				<tr id="serverInfo1" style="display:none;">
					<td >服务器IP：</td>
					<td class="formFieldError">
						<s:textfield name="siteVo.siteServer.hostName" cssClass="inputtext" size="40"/>
						<ul class="errorMessage"><li><span style="color:gray;">例如：http://www.bbb.cn</span></li></ul>
						<s:fielderror><s:param value="%{'siteVo.siteURL'}" /></s:fielderror>
					</td>
				</tr>
				<tr id="serverInfo2" style="display:none;">
					<td>端口：</td>
					<td class="formFieldError">
						<s:textfield name="siteVo.siteServer.port" cssClass="inputtext"/>
					</td>				
				</tr>	
				<tr id="serverInfo3" style="display:none;">
					<td>用户名：</td>
					<td class="formFieldError">
						<s:textfield name="siteVo.siteServer.userName" cssClass="inputtext" size="40"/>
					</td>
				</tr>
				<tr id="serverInfo4" style="display:none;">
					<td >密码：</td>
					<td class="formFieldError">
						<s:password name="siteVo.siteServer.password" cssClass="inputtext" size="40"></s:password>
					</td>				
				</tr>
				<tr id="serverInfo5" style="display:none;">
					<td>发布路径：</td>
					<td class="formFieldError">
						<s:textfield name="siteVo.siteServer.path" cssClass="inputtext"  size="40"/>
						<ul class="errorMessage"><li><span style="color:gray;">比如：e:/resource</span></li></ul>
						<s:fielderror ><s:param value="%{'siteVo.resourceDir'}" /></s:fielderror>
					</td>				
				</tr>																												
			</table>	
			<s:hidden name="siteVo.siteServer.id"/>
			<s:hidden name="siteVo.id"/>
		</s:form>							
	</body>
</html>