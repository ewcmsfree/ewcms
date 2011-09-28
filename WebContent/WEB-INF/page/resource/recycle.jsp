<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
    <head>
        <title>资源管理实现</title>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/page/resource/recycle.js"/>' charset="utf-8"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
       
        <ewcms:datepickerhead></ewcms:datepickerhead>
        
        <script type="text/javascript">
            var _r = new recycle('<s:property value="context"/>');
        
            $(function(){
                _r.init({
                    query:'<s:url action="query"/>',
                    remove:'<s:url action="delete"/>',
                    clear:'<s:url action="clear"/>',
                    revert:'<s:url action="revert"/>'
                });
            });
        </script>
    </head>
    <body>
        <table id="tt" toolbar="#tb"></table>
        <div id="tb" style="padding:5px;height:auto;display:none;">
                <div style="margin-bottom:5px">
                   <a href="#" id="toolbar-revert" class="easyui-linkbutton" iconCls="icon-resume" plain="true">还原</a>
                   <a href="#" id="toolbar-remove" class="easyui-linkbutton" iconCls="icon-remove" plain="true">永久删除</a>
                   <a href="#" id="toolbar-clear" class="easyui-linkbutton" iconCls="icon-clear" plain="true">清空</a>
                </div>
                <div style="padding-left:5px;">
                   <form id="queryform" style="padding: 0;margin: 0;">
                    文件名: <input type="text" name="name" style="width:80px"/>&nbsp;
                    描述: <input type="text" name="description" style="width:120px"/>&nbsp;
                    上传日期 从: <ewcms:datepicker name="fromDate" option="inputsimple" format="yyyy-MM-dd"/> 
                    到: <ewcms:datepicker name="toDate" option="inputsimple" format="yyyy-MM-dd"/>
                    <input type="hidden" name="state" value="delete"/>
                    <a href="#" id="toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                   </form>
               </div>
        </div>
        <div id="mm" class="easyui-menu" style="width:180px;display:none;">  
            <div id="menu-item-title"><b>操作</b></div>
            <div class="menu-sep"></div>   
            <div iconCls="icon-resume">还原</div>  
            <div iconCls="icon-remove">永久删除</div>  
            <div class="menu-sep"></div>   
            <div iconCls="icon-download">下载</div>   
        </div>  
    </body>
</html>