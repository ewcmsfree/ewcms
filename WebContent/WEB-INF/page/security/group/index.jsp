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
        $(function(){
            var groupIndex = new GroupIndex({
                queryUrl:'<s:url action="query"/>',
                detailUrl:'<s:url action="detail"/>',
                editUrl:'<s:url action="input"/>'
                });
            
           groupIndex.init({
               datagridId:'#tt',
               toolbarAddId:'#toolbar-add',
               toolbarUpdateId:'#toolbar-update',
               toolbarQueryId:'#toolbar-query',
               queryFormId:'#queryform'
           });
        });
        
        function openDetailWindow(name){
            var url = '<s:url action="detail"/>?name='+name;
            $('#editifr-auth-user-id').attr('src',url);
            openWindow('#edit-auth-user-window',{title:'修改 - 权限/用户',width:600,height:450})
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
    <div id="edit-auth-user-window" class="easyui-window" closed="true" icon="icon-winedit" title="用户组" style="display:none;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false">
               <iframe id="editifr-auth-user-id" width="98%" frameborder="0" height="350"/></iframe>
            </div>
            <div region="south" border="false" style="padding-right:20px;text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">取消</a>
            </div>
        </div>
    </div>  
</body>
</html>