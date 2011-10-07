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
		<s:form action="save" namespace="/crawler">
			<table class="formtable" >
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="gatherVo.name"/><font color="red">*</font>
						<s:fielderror ><s:param value="%{'gatherVo.name'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>
						<s:textarea id="description" name="gatherVo.description" cols="50"></s:textarea>
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<s:radio id="status" name="gatherVo.status" list='#{true:"&nbsp;启用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}' cssStyle="vertical-align: middle;" value="true"></s:radio>
						<s:radio id="status" name="gatherVo.status" list='#{false:"&nbsp;停止&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"}' cssStyle="vertical-align: middle;"></s:radio> 
					</td>
				</tr>
				<tr>
					<td  class="formFieldError">最大采集数：</td>
					<td>
						<s:textfield id="maxContent" name="gatherVo.maxContent"/><font color="red">*</font>(-1表示不限制)
					</td>
				</tr>
				<tr>
					<td>采集深度：</td>
					<td>
						<s:textfield id="depth" name="gatherVo.depth"/><font color="red">*</font>(-1表示不限制)
					</td>
				</tr>
				<tr>
					<td>采集线程数：</td>
					<td>
						<s:textfield id="threadCount" name="gatherVo.threadCount"/><font color="red">*</font>(1-10)
					</td>
				</tr>
				<tr>
					<td>超时等待时间：</td>
					<td>
						<s:textfield id="timeOutWait" name="gatherVo.timeOutWait"/><font color="red">*</font>秒(1-600)
					</td>
				</tr>
				<tr>
					<td>错误时重试次数：</td>
					<td>
						<s:textfield id="errorCount" name="gatherVo.errorCount"/><font color="red">*</font>(1-10)
					</td>
				</tr>
				<tr>
					<td>发布日期格式：</td>
					<td>
						<s:textfield id="dateFormat" name="gatherVo.dateFormat"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>采集选项：</td>
					<td>
						<s:checkboxlist list="@com.ewcms.crawler.model.CaptureOptions@values()" listValue="description" name="gatherVo.option" id="option" cssStyle="vertical-align: middle;"></s:checkboxlist>
					</td>
				</tr>
				<tr>
					<td>采集到此频道：</td>
					<td>
					</td>
				</tr>
			</table>
			<s:hidden name="gatherVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>