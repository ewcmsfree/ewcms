<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>内容</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
	</head>
	<body>
		<table class="formtable" width="100%">
		  <tr>
		    <td width="25%">标题：</td>
		    <td width="75%"><s:label name="title"/></td>
		  </tr>
		  <tr>
		    <td width="25%">内容：</td>
		    <td width="75%">
		    <s:if test="results!=null">
		    <table class="formatable" width="100%">
			<s:iterator value="results" status="rowstatus">
			  <tr>
			    <s:if test="#rowstatus.odd == false">
			      <td width="100%" style="background: #EEEEFF"><s:property/></td>
			    </s:if>
			    <s:else>
			      <td width="100%"><s:property/></td>
			    </s:else>
			  </tr>
			</s:iterator>
		    </table>
		    </s:if>
		    <s:else>
		    	<s:label name="detail"/>
		    </s:else>
		    </td>
		  </tr>
		</table>
		<s:hidden id="msgType" name="msgType"/>
		<s:hidden id="id" name="id"/>
	</body>
</html>