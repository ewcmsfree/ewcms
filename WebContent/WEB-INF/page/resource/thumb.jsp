<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>更新缩略图</title>
        <script type='text/javascript' src='<s:url value="/source/js/jquery.min.js" />'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/jquery.uploadify.min.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/swfobject.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/page/resource/thumb.js"/>' charset="utf-8"></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/uploadify/uploadify.css"/>"/>
        <script type="text/javascript">
            var _t = new thumb('<s:property  value="id"/>',
                {uploader:'<s:url value="/source/uploadify/medium/uploadify.allglyphs.swf"/>',
                expressInstall:'<s:url value="/source/uploadify/medium/expressInstall.swf"/>',
                cancelImg: '<s:url value="/source/uploadify/image/cancel.png"/>',
                script: '<s:url action="thumbReceive"/>;jsessionid=<%=session.getId()%>'});
            
            $(function() {
                _t.init();
            });
        </script>
    </head>
    <body>
        <div id="upload_queue" style="margin:20px 5px;height: 50px;"></div>
        <div align="right" style="text-align:right;height:38px;line-height:38px;padding:3px 6px;">
            <input type="file" name="upload" id="upload"/>
        </div>
    </body>
</html>
