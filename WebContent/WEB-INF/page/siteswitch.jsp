<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
	<head>
		<title>站点切换</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript">
	        $(function(){
	            $('#cc').combobox({
	                url:'<s:url action="siteload"/>',
	                valueField:'id',
	                textField:'siteName'
	            });
	        });
	        function pageSubmit(){
	            $('form').submit();
	         }	        
		</script>
	</head>
	<body>
		<s:form  action="index" method="post" target="_top">
			<table width="100%" >
				<tr height="35">
			        <td align="center">
			        	<font color="gray" style="font-size:12px;"><select id="cc" class="easyui-combobox"  style="width:180px;"  name="siteId"><option>--请选择站点--</option></select></font>
			        </td>                    
				</tr>
			</table>
		</s:form>
	</body>
</html>