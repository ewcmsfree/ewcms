<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
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
       
    <script type="text/javascript">
    $(function(){
        $('#progress').treegrid({
            title:'progress',
            iconCls:'icon-ok',
            width:500,
            height:130,
            rownumbers: true,
            animate:true,
            collapsible:true,
            fitColumns:true,
            idField:'id',
            treeField:'name',
            remoteSort:false,
            columns:[[
                {title:'任务',field:'name',width:180},
                {field:'progress',title:'进度',width:120,rowspan:2,
                    formatter:function(value){
                        if (value){
                            var s = '<div style="width:100%;background:#fff;border:1px solid #ccc">' +
                                    '<div style="width:' + value + '%;background:red">' + value + '%' + '</div>'
                                    '</div>';
                            return s;
                        } else {
                            return '';
                        }
                    }
                }
            ]]
        });
        
        $(window).unload(function() {
            pubsub.onUnload();
        }).load(function(){
            var url = 'pubsub/progress/<s:property value="currentSite.id"/>';
            pubsub.initialize(url);
        });
    });
    
    var progress=function(data){
        $('#progress').treegrid('loadData',data);    
    }
    </script>
</head>
<body>
    <table id="progress"></table>
    <!-- <iframe src="/pubsub/progress/<s:property value="currentSite.id"/>" style="width: 0;height: 0;overflow: hidden;" frameborder="0"></iframe> -->
</body>
</html>