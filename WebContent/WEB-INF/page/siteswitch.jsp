<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>站点切换</title>
		<s:include value="taglibs.jsp"/>
        <script type="text/javascript" src='<s:url value="/ewcmssource/page/siteswitch.js"/>'></script>
		<script type="text/javascript">
    		var _siteSwitch = new SiteSwitch({
    		    queryUrl:'<s:url action="siteQuery"/>',
    		    switchUrl:'<s:url action="index"/>'
    		});
    		
    		$(function(){
    		    _siteSwitch.init();
    		});
    		
    		function pageSubmit(){
    		    _siteSwitch.switchSite();
    		}
		</script>
	</head>
	<body>
        <table id="tt"></table>
        <s:hidden name="siteId"/>
	</body>
</html>