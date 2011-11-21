<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
	<head>
		<title>数据源设置</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>          
	</head>
	<body>
		<div class="easyui-tabs" id="dstab" border="false" fit="true">
			<div title="JDBC数据源">
				<iframe id="editinfoifr"  name="editjdbcifr" class="editifr" scrolling="no" src="<s:url namespace='/ds/jdbc' action='index'/>"></iframe>
			</div>			
			<div title="JNDI数据源" >
				<iframe  id="editjdbcfr"  name="editjndiifr" class="editifr" scrolling="no" src="<s:url namespace='/ds/jndi' action='index'/>"></iframe>	
			</div>
			<div title="BEAN数据源" >
				<iframe  id="editsrcifr"  name="editbeanifr" class="editifr" scrolling="no" src="<s:url namespace='/ds/bean' action='index'/>"></iframe>	
			</div>					
			<div title="CUSTOM数据源" >
			    <iframe id="editauthifr"  name="editcustomifr" class="editifr" scrolling="no" src="<s:url namespace='/ds/custom' action='index'/>"></iframe> 
			</div>
		</div>	
	</body>
</html>