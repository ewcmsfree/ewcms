<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>用户填写问卷调查结果</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
	</head>
	<body>
		<table class="formtable" width="100%">
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
		<s:hidden id="personId" name="personId"/>
		<s:hidden id="questionnaireId" name="questionnaireId"/>
	</body>
</html>