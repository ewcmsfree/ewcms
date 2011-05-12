<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>机构设置</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
	</head>
	<body>
		<s:if test="organVo==null">
			机构管理可对机构进行管理，可以创建机构、修改机构、删除机构、管理机构有关信息、管理机构站点等。
			<br>
			鼠标双击左边机构，可对机构属性信息编辑。
		</s:if>
		<s:else>
				<div class="easyui-tabs"  id="systemtab" border="false" fit="true">
					<div title="机构站点" style="padding: 5px;">
						<iframe src="<s:url action="editSite"/>?organVo.id=<s:property value="organVo.id"/>" id="editsiteifr"  name="editsiteifr" class="editifr" scrolling="no"></iframe>
					</div>					
					<div title="机构信息" style="padding: 5px;">
						<iframe src="<s:url action="editInfo"/>?organVo.id=<s:property value="organVo.id"/>" id="editinfoifr"  name="editinfoifr" class="editifr" scrolling="no"></iframe>
					</div>
					<div title="机构介绍" style="padding: 5px;">
						<iframe src="<s:url action="editIntroduce"/>?organVo.id=<s:property value="organVo.id"/>" id="editconfigifr"  name="editconfigifr" class="editifr" scrolling="no"></iframe>
					</div>	
					
				</div>						
		</s:else>			
	</body>
</html>