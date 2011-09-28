<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>上传资源</title>
        <script type='text/javascript' src='<s:url value="/source/js/jquery.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/jquery.uploadify.min.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/swfobject.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/page/resource/resource.js"/>' charset="utf-8"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/uploadify/uploadify.css"/>"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value='/source/css/ewcms.css'/>" />
        
        <script type="text/javascript">
            var _u = new Upload('<s:property  value="context"/>','<s:url action="save"/>',
                {uploader:'<s:url value="/source/uploadify/medium/uploadify.allglyphs.swf"/>',
                expressInstall:'<s:url value="/source/uploadify/medium/expressInstall.swf"/>',
                cancelImg: '<s:url value="/source/uploadify/image/cancel.png"/>',
                script: '<s:url action="receive"/>;jsessionid=<%=session.getId()%>',
                thumbScript: '<s:url action="thumbReceive"/>;jsessionid=<%=session.getId()%>',
                type: '<s:property value="type"/>',
                multi:  <s:property value="multi"/>,
                fileDesc: '<s:property value="fileDesc"/>',
                fileExt: '<s:property value="fileExt"/>',
                resourceId:<s:property  value="id"/>});
            
            $(function() {
                _u.init();
            });

            function insert(callback){
                _u.insert(callback);
            }
            
        </script>
    </head>
    <body class="easyui-layout">
         <div region="center" border="false">
            <div id="upload_queue" style="margin-left:5px;display:none;"></div>
            <s:form  id="resource_infos" style="width:565px;height:275px; margin:5px;">
               <table id="tt" align="center"></table>
            </s:form>
        </div>
        <div region="south" border=false style="text-align:right;height:38px;line-height:38px;padding:3px 6px;">
            <input type="file" name="upload" id="upload"/>
        </div>
    </body>
</html>
