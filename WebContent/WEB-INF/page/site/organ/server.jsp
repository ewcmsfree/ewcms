<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>站点发布设置</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript"> 
	    	$(function() {
		        <s:include value="../../alertMessage.jsp"/>
		        var value = $("#outputType").val();
		        outputTypeChange(value);
	    	});
	    	
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
	<body>
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
						<s:select id="outputType" name="siteVo.siteServer.outputType" cssClass="inputtext" list="@com.ewcms.core.site.model.SiteServer$OutputType@values()" listValue="description" onchange="outputTypeChange(this.value);" headerKey="" headerValue="------请选择------"></s:select>
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