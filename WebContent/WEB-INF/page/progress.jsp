<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/cupertino/easyui.css"/>' rel="stylesheet" title="cupertino"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'/>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
     <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.pubsub.js"/>'></script>  
     <script type="text/javascript" src='<s:url value="/ewcmssource/page/progress.js"/>'></script>  
       
    <script type="text/javascript">
    var _progress = new progress('pubsub/progress/<s:property value="currentSite.id"/>');
    $(function(){
        var username = '<s:property value="userDetails.username"/>';
        var adminRole = false;
        <sec:authorize ifAnyGranted="ROLE_ADMIN">
        adminRole = true; 
        </sec:authorize>
        _progress.init({
            username:username,
            adminRole:adminRole
        });
    });
    
    var loadData = function(data){
        _progress.loadData(data);
    }
    </script>
</head>
<body>
    <table id="progress"></table>
    <!-- <iframe src="/pubsub/progress/<s:property value="currentSite.id"/>" style="width: 0;height: 0;overflow: hidden;" frameborder="0"></iframe> -->
</body>
</html>