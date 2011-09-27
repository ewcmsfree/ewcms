<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>资源管理</title>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    </head>
    
    <body>
        <div class="easyui-tabs"  id="systemtab" border="false" fit="true">
            <div title="图片"  style="padding: 5px;">
                <iframe src="<s:url action="manage"/>?type=image" class="editifr" scrolling="no"></iframe>
            </div>
            <div title="FLASH" style="padding:5px;">
                <iframe src="<s:url action="manage"/>?type=flash" class="editifr" scrolling="no"></iframe>
            </div>
            <div title="视频" style="padding: 5px;">
                <iframe src="<s:url action="manage"/>?type=video" class="editifr" scrolling="no"></iframe>
            </div>
            <div title="附件" style="padding: 5px;">
                <iframe src="<s:url action="manage"/>?type=annex" class="editifr" scrolling="no"></iframe>
            </div>
        </div>
    </body>
</html>