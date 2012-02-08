<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>权限</title>
    <script type='text/javascript' src='<s:url value="/ewcmssource/js/jquery.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/cupertino/easyui.css"/>' rel="stylesheet" title="cupertino"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'/>
    <script type="text/javascript">
        $(function(){
            $('#tt').datagrid({
                fit:true,
                fitColumns:true,
                nowrap: false,
                singleSelect:true,
                rownumbers:true,
                pagination:true,
                loadMsg:'',
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
     <div region="center" border="false" style="padding: 5px;">
         <table id="tt"></table>
     </div>
</body>
</html>