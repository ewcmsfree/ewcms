<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板资源编辑</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>	
				<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript"> 
			function alertInfo(mesg){
				$.messager.alert("",mesg);
			}
		</script> 					
	</head>
	<body>
		<s:if test="sourceVo==null">
			模板资源管理可以对一个网站模板资源进行统一管理,可以按照文件目录对模板资源管理
			<br>
			双击模板资源文件或左边弹出菜单编辑可以对模板资源内容进行编辑。								
		</s:if>
		<s:else>	
			<div class="easyui-tabs"  id="systemtab" border="false" fit="true">
				<div title="基本信息" style="padding: 5px;">
					<iframe  id="editinfoifr"  name="editinfoifr" src='<s:url action="editInfo"/>?sourceVo.id=<s:property value="sourceVo.id"/>' class="editifr" scrolling="no"></iframe>				
				</div>
				<div title="内容编辑" style="padding: 5px;">
					<iframe  id="editcontentifr"  name="editcontentifr" src='<s:url action="editContent"/>?sourceVo.id=<s:property value="sourceVo.id"/>' class="editifr" scrolling="no"></iframe>										
				</div>								
			</div>			
		</s:else>
	</body>
</html>