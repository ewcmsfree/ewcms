<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>人大委员管理</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <script type="text/javascript">
            $(function(){
                //基本变量初始
                setGlobaVariable({
                    tableid:'#tt',
                    inputURL:'<s:url action="input"/>',
                    queryURL:'<s:url action="nccpcQuery"/>',
                    deleteURL:'none',
                    idfield:'username',
                    title:'人大委员',
                    editwidth:500,
                    editheight:350
                });
                //数据表格定义
                openDataGrid({
                    columns:[[
                            {field:'username',title:'用户名',width:100},
                            {field:'name',title:'真实名称',width:100},
                            {field:'enabled',title:'是否有效',width:60,
                                formatter:function(val,rec){
                                    if (val){
                                        return '是';
                                    }else{
                                        return '否';
                                    }
                                }
                            }
                        ]],
                    toolbar:[
                        {text:'新增',iconCls:'icon-add',handler:function(){
                            addOperateBack({url:'<s:url action="input"/>?eventOP=add&cppcc=false&nccpc=true'});
                        }},'-',
                        {text:'修改',iconCls:'icon-edit',handler:function(){
                            updOperateBack({
                                url:'<s:url action="input"/>?eventOP=update',
                                callBackId:function(row){
                                    return row.username;
                                }
                            });
                        }},'-',
                        {text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
                        {text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
                    ]
                });
            });
        </script>
    </head>
    <body class="easyui-layout">
        <div region="center" style="padding:2px;" border="false">
            <table id="tt" fit="true"></table>
        </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                    <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator();">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#edit-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                    <form id="queryform">
                        <table class="formtable">
                            <tr>
                                <td class="tdtitle">用户名：</td>
                                <td class="tdinput">
                                    <input type="text" id="username_id" name="username" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">真实名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name_id" name="name" class="inputtext"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch();">查询</a>
                </div>
            </div>
        </div>
    </body>
</html>