<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>权限</title>
    <script type='text/javascript' src='<s:url value="/source/js/jquery-1.4.2.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <script type="text/javascript">
        $(function(){
            $('#tt').datagrid({
                title:'权限列表',
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
 <body class="easyui-layout">
     <div region="center" border="false" style="background:#fff;">
         <table id="tt" align="center" fit="true"></table>
     </div>
</body>
</html>