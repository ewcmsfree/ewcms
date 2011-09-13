<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>资源管理</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    </head>
    
    <body>
        <div class="easyui-tabs"  id="systemtab" border="false" fit="true">
            <div title="图片管理"  style="padding: 5px;">
                <iframe src="<s:url action="manage"/>?type=image" id="imagefoifr"  name="imagefoifr" class="editifr" scrolling="no"></iframe>
            </div>
            <div title="附件管理" >
                <iframe src="<s:url action="manage"/>?type=annex" id="annexfoifr"  name="annexfoifr" class="editifr" scrolling="no"></iframe>
            </div>
        </div>
    </body>
</html>