<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>专栏信息设置</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>	
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>	
		<script type="text/javascript" src='<s:url value="/source/js/tplselect.js"/>'></script>			
		<script type="text/javascript"> 
			var tpltreeURL = '<s:url value="/site/template/treeChannel.do"/>?templateVo.channelId=<s:property value="channelVo.id"/>';
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
		<s:form action="saveInfo" namespace="/site/channel"  method="post" enctype="multipart/form-data">
			<table  class="formtable" align="center">
				<tr>
					<td>专栏(<s:property value="channelVo.id"/>)：</td>
					<td  width="80%" class="formFieldError">
						 <s:checkbox name="channelVo.publicenable"></s:checkbox>是否发布
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
					<tr>
						<td >首页模板：</td>
						<td >
							<s:textfield name="channelVo.homeTPL.path" size="60" readonly="true" cssClass="inputdisabled"/>
							<input type="button" name="Submit" value="选择.." class="inputbutton" onClick='browseTPL("channelVo.homeTPL.path","channelVo.homeTPL.id");'>
							<s:hidden name="channelVo.homeTPL.id"/>									
						</td>					
					</tr>													
				</s:if>	
				<s:else>
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
						<td >首页模板：</td>
						<td >
							<s:textfield name="channelVo.homeTPL.path" size="60" readonly="true" cssClass="inputdisabled"/>
							<input type="button" name="Submit" value="选择.." class="inputbutton" onClick='browseTPL("channelVo.homeTPL.path","channelVo.homeTPL.id");'>
							<s:hidden name="channelVo.homeTPL.id"/>									
						</td>					
					</tr>
					<tr>
						<td  >列表页模板：</td>
						<td >
							<s:textfield name="channelVo.listTPL.path" size="60" readonly="true" cssClass="inputdisabled"/>
							<input type="button" name="Submit" value="选择.." class="inputbutton" onClick='browseTPL("channelVo.listTPL.path","channelVo.listTPL.id");'>
							<s:hidden name="channelVo.listTPL.id"/>									
						</td>
					</tr>
					<tr>
						<td  >详细页模板：</td>
						<td >
							<s:textfield name="channelVo.detailTPL.path" size="60" readonly="true" cssClass="inputdisabled"/>
							<input type="button" name="Submit" value="选择.." class="inputbutton" onClick='browseTPL("channelVo.detailTPL.path","channelVo.detailTPL.id");'>
							<s:hidden name="channelVo.detailTPL.id"/>									
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
				
        <div id="template-window" class="easyui-window" closed="true"   style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul  id="tt2"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:selectTPL();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-remove" href="javascript:void(0)"  onclick="javascript:setvalueTPL('','');">清空</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:closeTPL();">取消</a>
                </div>
            </div>
        </div>
        
	</body>
</html>