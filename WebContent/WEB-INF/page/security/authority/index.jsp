<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>权限</title>
    <script type='text/javascript' src='<s:url value="/source/js/jquery.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/ext/datagrid-detailview.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/icon.css"/>'>
    <script type="text/javascript">
        $(function(){
            $('#tt').datagrid({
                fitColumns:true,
                nowrap: false,
                singleSelect:true,
                rownumbers:true,
                pagination:true,
                url:'<s:url action="query"/>',
                frozenColumns:[[
                    {field:'name',title:'名称',width:200}
                ]],
                columns:[[
                    {field:'remark',title:'描述',width:500}
                ]]
            });
        });
    </script>
</head>
 <body>
     <table id="tt"></table>
</body>
</html>