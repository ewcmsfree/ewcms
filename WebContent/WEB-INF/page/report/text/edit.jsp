<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>文字报表信息</title>
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
		<s:form action="save" namespace="/report/text" enctype="multipart/form-data" method="post">
			<table class="formtable" >
				<tr>
					<td>报表名：</td>
					<td class="formFieldError">
						<s:textfield name="textReportVo.name" cssClass="inputtext" maxlength="20" />
						<s:fielderror><s:param value="%{'textReportVoVo.name'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>报表文件：</td>
					<td class="formFieldError">
						<s:file	name="textFile" accept="jrxml" theme="simple" onchange="javascript:if(this.value.toLowerCase().lastIndexOf('jrxml')==-1){alert('请选择jrxml文件！');this.value='';}"/>
						<s:fielderror><s:param value="%{'textFile'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>数据源类型：</td>
					<td class="formFieldError">
						<s:select list="baseDSList" name="textReportVo.baseDS.id" listKey="id" listValue="name" headerKey="0" headerValue="默认数据源" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>是否隐藏：</td>
					<td>
						<s:checkbox	name="textReportVo.hidden" />
					</td>
				</tr>
                <tr>
					<td>备注：</td>
					<td>
						<s:textarea name="textReportVo.remarks" cols="60"/>
					</td>
				</tr>			
			</table>
			<s:hidden name="textReportVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>