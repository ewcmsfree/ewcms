<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>专栏信息设置</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript"> 
	    	$(function() {
		        <s:include value="../../alertMessage.jsp"/>
	    	});
		</script>
	</head>
	<body>
		<s:form action="saveInfo" namespace="/site/channel"  method="post" enctype="multipart/form-data">
			<table  class="formtable" align="center">
				<tr>
					<td>专栏(<s:property value="channelVo.id"/>)：</td>
					<td  width="80%" class="formFieldError">
						 <s:checkbox id="publicenable" name="channelVo.publicenable"/><label for="publicenable">是否发布</label>
					</td>
				</tr>
				<s:if test="channelVo.parent == null && !hasFieldErrors()">
					<tr>
						<td>站点名称：</td>
						<td  width="80%">
							<s:property value="channelVo.site.siteName"/> &nbsp;<a href="<s:property value="channelVo.site.siteURL" />" target="_blank">预览</a>
						</td>
					</tr>
					<tr>
						<td >站点目录名：</td>
						<td >
							<s:property value="channelVo.site.siteRoot"/>
							<input type="hidden" name="channelVo.dir" value='<s:property value="channelVo.site.siteRoot"/>'>
						</td>				
					</tr>	
					<tr>
						<td >站点URL：</td>
						<td >
							<s:property value="channelVo.site.siteURL"/>
						</td>				
					</tr>														
				</s:if>	
				<s:else>
					<tr>
						<td>专栏类型：</td>
						<td>
							<s:select list="@com.ewcms.core.site.model.Channel$Type@values()" listValue="description" name="channelVo.type" id="channelVo_type"></s:select>
						</td>
					</tr>													
					<tr>
						<td>专栏访问相对地址：</td>
						<td  width="80%" class="formFieldError">
							<s:property value="channelVo.absUrl"/>
						</td>
					</tr>																
					<tr>
						<td>专栏名称：</td>
						<td  width="80%">
							<s:textfield name="channelVo.name" size="30" readonly="true" cssClass="inputdisabled"/>
						</td>
					</tr>
					<tr>
						<td >专栏目录：</td>
						<td class="formFieldError">
							<s:textfield name="channelVo.dir" cssClass="inputtext" size="20"/>
							<s:fielderror ><s:param value="%{'channelVo.dir'}" /></s:fielderror>
						</td>				
					</tr>	
					<tr>
						<td >专栏URL：</td>
						<td class="formFieldError">
							<s:textfield name="channelVo.url" cssClass="inputtext" size="30"/>
							<s:fielderror ><s:param value="%{'channelVo.url'}" /></s:fielderror>
						</td>				
					</tr>		
					<tr>
						<td  >列表页最大文档数：</td>
						<td  class="formFieldError">
							<s:textfield name="channelVo.listSize" cssClass="inputtext" size="10"/>
							<s:fielderror ><s:param value="%{'channelVo.listSize'}" /></s:fielderror>
						</td>					
					</tr>
					<tr>
						<td  >最大显示文档数：</td>
						<td  class="formFieldError">
							<s:textfield name="channelVo.maxSize" cssClass="inputtext" size="10"/>
							<s:fielderror ><s:param value="%{'channelVo.maxSize'}" /></s:fielderror>
						</td>					
					</tr>
					<tr>
						<td>专栏介绍：</td>
						<td>
							<s:textarea name="channelVo.describe" style="width:300px;height:45px" cssClass="inputtext"></s:textarea>			
						</td>				
					</tr>																																					
				</s:else>	
					<tr>
						<td>引导图：</td>
						<td>
							<s:file name="iconFile" cssClass="inputtext" size="50"/>				
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
			<s:hidden name="channelVo.id"/>					
		</s:form>
	</body>
</html>