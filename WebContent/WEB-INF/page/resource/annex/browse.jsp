<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>附件浏览</title>
        <script type='text/javascript' src='<s:url value="/source/js/jquery-1.4.2.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value='/source/css/ewcms.css'/>" />
        <script type="text/javascript">
            var multi = <s:property value="multi"/>;
            $(document).ready(function() {
                $('#tt').datagrid({
                    title:'附件',
                    iconCls:'icon-attach',
                    width:560,
                    height:280,
                    nowrap: false,
                    striped: true,
                    singleSelect:!multi,
                    pagination:true,
                    rownumbers:true,
                    url:'<s:url action="query"/>',
                    frozenColumns:[[
                            {field:'ck',checkbox:true}
                        ]],
                    columns:[[
                            {field:'name',title:'名称',width:130},
                            {field:'title',title:'标题',width:150},
                            {field:'description',title:'描述',width:170}
                        ]]
                });
            });
 
            function insert(callback){
                var rows = $('#tt').datagrid('getSelections');
                $.each(rows,function(key,value){
                    if(value){
                        callback([value]);
                    }
                });
            }
        </script>
        <style type="text/css">
            form.paramsClass{margin: 0px;padding:0 2px;}
            form.paramsClass table{margin-left: 10px;margin-bottom: 8px;}
        </style>
    </head>
    <body class="easyui-layout">
        <div region="north" border="false" style="text-align:left;height:40px;padding:5px 0px 5px 0px;overflow: hidden;">
            <form id="queryForm" class="paramsClass">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td width="130px">文件名：<input type="text" id="name_id" name="name" size="6"/></td>
                        <td width="130px">标题：<input type="text" id="title_id" name="title" size="6"/></td>
                        <td width="200px">描述：<input type="text" id="description_id" name="description" size="12"/></td>
                        <td><a class="easyui-linkbutton" icon="icon-search" href="javascript:void(0)" onclick="query();">查询</a></td>
                    </tr>
                </table>
            </form>
        </div>
        <div region="center" border="false" style="background:#fff;padding: 10px;">
            <table id="tt" align="center"></table>
        </div>
    </body>
</html>
