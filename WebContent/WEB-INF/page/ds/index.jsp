<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>数据源设置</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'></link>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>          
	</head>
	<body>
		<div class="easyui-tabs" id="dstab" border="false" fit="true">
			<div title="JDBC数据源">
				<iframe id="editinfoifr"  name="editjdbcifr" class="editifr" src="<s:url namespace='/ds/jdbc' action='index'/>" onload="iframeFitHeight(this);"></iframe>
			</div>			
			<div title="JNDI数据源" >
				<iframe id="editjdbcfr"  name="editjndiifr" class="editifr" src="<s:url namespace='/ds/jndi' action='index'/>" onload="iframeFitHeight(this);"></iframe>	
			</div>
			<div title="BEAN数据源" >
				<iframe id="editsrcifr"  name="editbeanifr" class="editifr" src="<s:url namespace='/ds/bean' action='index'/>" onload="iframeFitHeight(this);"></iframe>	
			</div>					
			<div title="CUSTOM数据源" >
			    <iframe id="editauthifr"  name="editcustomifr" class="editifr" src="<s:url namespace='/ds/custom' action='index'/>" onload="iframeFitHeight(this);"></iframe> 
			</div>
		</div>	
	</body>
</html>