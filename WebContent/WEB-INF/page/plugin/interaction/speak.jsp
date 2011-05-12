<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>留言审核</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <script>
            $(function(){
                //基本变量初始
                setGlobaVariable({
                    inputURL:'none',
                    queryURL:'<s:url action="querySpeak"/>',
                    deleteURL:'none',
                    editwidth:400,
                    editheight:150
                });
                //数据表格定义
                openDataGrid({
                    columns:[[
                            {field:'id',title:'序号',width:40},
                            {field:'username',title:'用户名',width:100},
                            {field:'name',title:'昵名',width:100},
                            {field:'checked',title:'审核',width:100,
                                formatter:function(val,rec){
                                    if(val){
                                        return '通过';
                                    }
                                }
                            },
                            {field:'content',title:'内容',width:400,
                                formatter:function(val,rec){
                                    return "<textarea style='border-style:none;width:400;height:100;' readonly>"+val+"</textarea>"
                                }
                            },
                            {field:'date',title:'日期',width:100}
                        ]],
                    toolbar:[
                        {text:'审核',iconCls:'icon-ok', handler:checked},
                        {text:'取消审核',iconCls:'icon-remove', handler:unChecked},'-',
                        {text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
                        {text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
                    ]
                });
            });
            function checked(){
                checkedOpt(false,'审批');
            }

            function unChecked(){
                checkedOpt(true,'取消审批');
            }

            function checkedOpt(esc,msg){
                var url = '<s:url action="speakChecked"/>';
                var rows = $("#tt").datagrid('getSelections');
                if(rows.length == 0){
                    $.messager.alert('提示','请选择'+msg+'记录','info');
                    return ;
                }
                var ids = '';
                for(var i=0;i<rows.length;++i){
                    ids =ids + 'selections=' + rows[i].id +'&';
                }              
                if(esc){
                   ids =ids +'esc=true';
                }else{
                    ids=ids +'esc=false';
                }
                $.messager.confirm("提示",'确定要'+msg+'所选记录吗?',function(r){
                    if (r){
                        $.post(url,ids,function(data){
                            $.messager.alert('成功',msg+'成功','info');
                            $("#tt").datagrid('clearSelections');
                            $("#tt").datagrid('reload');
                        });
                    }
                });
            }

            function queryInteractionSearch(options){
                if(typeof(options) == 'undefined')options = {};
                var tableid = (options.tableid ? options.tableid : globaoptions.tableid);
                var windowid = (options.windowid ? options.windowid : globaoptions.querywindowid);
                var url = (options.url ? options.url : globaoptions.queryURL);
                var formid = (options.formid ? options.formid : globaoptions.queryformid);

                var value = $(formid).serialize();
                var index = url.indexOf("?");
                if (index == -1){
                    url = url + '?' + value;
                }else{
                    url = url + '&' + value;
                }
                $(tableid).datagrid({
                    pageNumber:1,
                    url:url
                });
                $(windowid).window('close');
            }


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
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator();$('#edit-window').window('close');queryInteractionSearch('');return false;">保存</a>
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
                                <td class="tdtitle">类容：</td>
                                <td class="tdinput">
                                    <input type="text" id="content_id" name="content" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">审核：</td>
                                <td class="tdinput">
                                    <input type="radio" id="checked_id" name="checked" value="0"/>所有
                                    <input type="radio" id="checked_id" name="checked" value="1"/>通过&nbsp;&nbsp;
                                    <input type="radio" id="checked_id" name="checked" value="2"/>未通过&nbsp;&nbsp;
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="queryInteractionSearch('');">查询</a>
                </div>
            </div>
        </div>
    </body>
</html>