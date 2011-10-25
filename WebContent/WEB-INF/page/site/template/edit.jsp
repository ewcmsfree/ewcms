<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板编辑</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>          
		<script type="text/javascript">
			function alertInfo(mesg){
				$.messager.alert("",mesg);
			}		
		</script>				
	</head>
	<body>
		<s:if test="templateVo==null">
			模板管理可以对一个网站模板进行统一管理,可以按照文件目录对模板管理
			<br>
			双击模板文件或左边弹出菜单编辑可以对模板信息和内容进行编辑										
		</s:if>
		<s:else>	
			<div class="easyui-tabs"  id="systemtab" border="false" fit="true">
				<div title="基本信息" style="padding: 5px;">
					<iframe  id="editinfoifr"  name="editinfoifr" src='<s:url action="editInfo"/>?templateVo.id=<s:property value="templateVo.id"/>' class="editifr" scrolling="no"></iframe>				
				</div>
				<div title="内容编辑" style="padding: 5px;">
					<iframe  id="editcontentifr"  name="editcontentifr" src='<s:url action="editContent"/>?templateVo.id=<s:property value="templateVo.id"/>' class="editifr" scrolling="no"></iframe>						
				</div>
			</div>
		</s:else>
	</body>
</html>