<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户组</title>
	<s:include value="../../taglibs.jsp"/>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/ext/datagrid-detailview.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/page/security/group/index.js"/>'></script>
    
     <script type="text/javascript">
         var _groupIndex = new GroupIndex({
             queryUrl:'<s:url action="query"/>',
             detailUrl:'<s:url action="detail"/>',
             editUrl:'<s:url action="input"/>',
             deleteUrl:'<s:url action="delete"/>'
             });
        $(function(){
           _groupIndex.init({
               datagridId:'#tt',
               toolbarAddId:'#toolbar-add',
               toolbarUpdateId:'#toolbar-update',
               toolbarRemoveId:'#toolbar-remove',
               toolbarQueryId:'#toolbar-query',
               queryFormId:'#queryform'
           });
        });
        
        function closeEditWindow(){
            _groupIndex.closeEditWindow();
        }
    </script>
</head>
<body class="easyui-layout">
    <div region="center" border="false" style="padding: 5px;">
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
     </div>
</body>
</html>