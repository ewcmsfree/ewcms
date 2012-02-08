<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
    <head>
        <title>资源管理实现</title>
        <script type='text/javascript' src='<s:url value="/ewcmssource/js/jquery.min.js" />'></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/page/resource/recycle.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
        <link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"/>
       
        <ewcms:datepickerhead></ewcms:datepickerhead>
        
        <script type="text/javascript">
            var _r = new recycle('<s:property value="context"/>');
        
            $(function(){
                _r.init({
                    query:'<s:url action="query" namespace="/resource"/>',
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