<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>数据源设置</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'></link>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>          
		<script type="text/javascript">
			$(function(){
				$('#systemtab').tabs({
					onSelect:function(title){
						var jdbcurl="<s:url namespace='/ds/jdbc' action='index'/>";
						var jndiurl="<s:url namespace='/ds/jndi' action='index'/>";
						var beanurl="<s:url namespace='/ds/bean' action='index'/>";
						var customurl="<s:url namespace='/ds/custom' action='index'/>";
						if(title=="JDBC数据源"){
							$("#editjdbcifr").attr('src',jdbcurl);
						}
						if(title=="JNDI数据源"){
							$("#editjndiifr").attr('src',jndiurl);
						}
						if(title=="BEAN数据源"){
							$("#editbeanifr").attr('src',beanurl);
						}
						if(title=="CUSTOM数据源"){
							$("#editcustomifr").attr('src',customurl);
						}	
					}
				});
			});	
		</script>					
	</head>
	<body>
		<div class="easyui-tabs" id="systemtab" border="false" fit="true">
			<div title="JDBC数据源">
				<iframe id="editjdbcifr"  name="editjdbcifr" class="editifr" scrolling="no"></iframe>
			</div>			
			<div title="JNDI数据源" >
				<iframe id="editjndiifr"  name="editjndiifr" class="editifr" scrolling="no"></iframe>	
			</div>
			<div title="BEAN数据源" >
				<iframe id="editbeanifr"  name="editbeanifr" class="editifr" scrolling="no"></iframe>	
			</div>					
			<div title="CUSTOM数据源" >
			    <iframe id="editcustomifr"  name="editcustomifr" class="editifr" scrolling="no"></iframe> 
			</div>
		</div>	
	</body>
</html>