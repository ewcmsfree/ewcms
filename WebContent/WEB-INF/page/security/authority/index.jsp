<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>权限</title>
	<s:include value="../../taglibs.jsp"/>
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
     <div region="center" border="false" style="padding: 2px;">
         <table id="tt" fit="true"></table>
     </div>
</body>
</html>