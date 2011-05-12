<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>附件管理</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
        <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <script type="text/javascript">
            $(function(){
                //基本变量初始
                setGlobaVariable({
                    inputURL:'NONE',
                    queryURL:'<s:url action="query"/>',
                    deleteURL:'<s:url action="delete" namespace="/resource"/>',
                    editwidth:450,
                    editheight:300
                });
                //数据表格定义
                openDataGrid({
                    toolbar:[
                        {text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
                        {text:'删除',iconCls:'icon-remove',handler:delOperateBack},'-',
                        {text:'重新发布',iconCls:'icon-release', handler:function(){
                                var rows = $('#tt').datagrid('getSelections');
                                if(rows.length == 0){
                                    $.messager.alert('提示','请选择重新发布的附件','info');
                                    return ;
                                }
                                var ids = '';
                                $.each(rows,function(key,value){
                                    if(value){
                                        ids = ids + 'selections=' +value.id +'&';
                                    }
                                });
                                $.post('<s:url action="release" namespace="/resource"/>',ids,function(data){
                                    $('#tt').datagrid('clearSelections');
                                    $.messager.alert('提示','重新发布附件成功');
                                });
                            }}
                    ],
                    columns:[[
                            {field:'id',title:'编号',width:80,sortable:true,align:'center'},
                            {field:'name',title:'文件名称',width:120,align:'center'},
                            {field:'title',title:'标题',width:120,align:'center'},
                            {filed:'size',title:'大小',width:80,align:'left'},
                            {field:'description',title:'描述',width:160,align:'center'},
                            {field:'uploadTime',title:'上传时间',width:80,align:'center'}
                        ]]
                });
            });

            function getPostSelects (){
                var ids = '';
                $.each(selects,function(key,value){
                    if(value){
                        ids = ids + 'selects=' +value +'&';
                    }
                });
                return ids;
            }
        </script>
    </head>
    <body>
        <table id="tt" fit="true"></table>
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                    <form id="queryform">
                        <table class="formtable">
                            <tr>
                                <td class="tdtitle">文件名：</td>
                                <td class="tdinput">
                                    <input type="text" id="name_id" name="name" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">标题：</td>
                                <td class="tdinput">
                                    <input type="text" id="title_id" name="title" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">描述：</td>
                                <td class="tdinput">
                                    <input type="text" id="description_id" name="description" class="inputtext"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                </div>
            </div>
        </div>
    </body>
</html>