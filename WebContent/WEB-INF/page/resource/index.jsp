<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>资源管理</title>
        <script type='text/javascript' src='<s:url value="/ewcmssource/js/jquery.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/page/resource/index.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'>
        <script type="text/javascript">
            var _m = new index('<s:url action="manage"/>');
            $(function(){
                _m.init();
            });
        </script>
    </head>
    <body>
        <div class="easyui-tabs"  id="systemtab" border="false" fit="true">
            <div title="图片"  style="padding: 5px;">
                <iframe id="imageifr" src="<s:url action="manage"/>?type=image" class="editifr" scrolling="no"></iframe>
            </div>
            <div title="FLASH" style="padding:5px;">
                <iframe id="flashifr" src="" class="editifr" scrolling="no"></iframe>
            </div>
            <div title="视频和音频" style="padding: 5px;">
                <iframe id="videoifr" src="" class="editifr" scrolling="no"></iframe>
            </div>
            <div title="附件" style="padding: 5px;">
                <iframe id="annexifr" src="" class="editifr" scrolling="no"></iframe>
            </div>
        </div>
    </body>
</html>