<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户组</title>
    <script type='text/javascript' src='<s:url value="/source/js/jquery.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/ext/datagrid-detailview.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/page/security/group/index.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
    
     <script type="text/javascript">
         var _groupIndex = new GroupIndex({
             queryUrl:'<s:url action="query"/>',
             detailUrl:'<s:url action="detail"/>',
             editUrl:'<s:url action="input"/>'
             });
        $(function(){
           _groupIndex.init({
               datagridId:'#tt',
               toolbarAddId:'#toolbar-add',
               toolbarUpdateId:'#toolbar-update',
               toolbarQueryId:'#toolbar-query',
               queryFormId:'#queryform'
           });
        });
        
        function closeEditWindow(){
            _groupIndex.closeEditWindow();
        }
    </script>
</head>
<body>
     <table id="tt" toolbar="#tb" style="display:none;"></table>
     <div id="tb" style="padding:5px;height:auto;display:none;">
         <div style="margin-bottom:5px">
             <a href="#" id="toolbar-add" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
              <a href="#" id="toolbar-update" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
             <a href="#" id="toolbar-remove" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
         </div>
         <div style="padding-left:5px;">
             <form id="queryform" style="padding: 0;margin: 0;">
                 名称: <input type="text" name="name" style="width:150px"/>&nbsp;
                 描述: <input type="text" name="remark" style="width:200px"/>&nbsp;
                 <a href="#" id="toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
             </form>
         </div>
     </div>
     <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="用户组" style="display:none;">
         <iframe id="editifr-id"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
    </div> 
</body>
</html>