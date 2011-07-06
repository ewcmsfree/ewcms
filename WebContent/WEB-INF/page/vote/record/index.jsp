<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>调查投票</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
	</head>
	<body>
		<table class="formtable" >
			<s:iterator value="results" status="rowstatus">
			  <tr>
			    <s:if test="#rowstatus.odd == false">
			      <td style="background: #EEEEFF"><s:property/></td>
			    </s:if>
			    <s:else>
			      <td><s:property/></td>
			    </s:else>
			  </tr>
			</s:iterator>
		</table>
		<s:hidden id="personId" name="personId"/>
		<s:hidden id="questionnaireId" name="questionnaireId"/>
	</body>
</html>