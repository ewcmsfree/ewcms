<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>图片管理</title>
        <script type='text/javascript' src='<s:url value="/source/js/jquery-1.4.2.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value='/source/css/ewcms.css'/>" />
        <script type="text/javascript">
            var contextPath = '<s:property value="context"/>';
            var selects = new Array();
            $(document).ready(function() {
                $('#pp').pagination({
                    buttons:[{
                            iconCls:'icon-search',
                            text:'查询',
                            handler:function(){
                                queryOperateBack({windowid:'#query-window',width:300,height:200});
                            }
                        },'-',{
                            iconCls:'icon-remove',
                            text:'删除',
                            handler:function(){
                                var ids = getPostSelects();
                                if(ids == ''){
                                    $.messager.alert('提示','请选择删除的图片');
                                }
                                $.messager.confirm('提示', '确认要删除选择的图片', function(r){
                                    if(r){
                                        $.post('<s:url action="delete" namespace="/resource"/>',ids,function(data){
                                            refresh();
                                            selects = new Array();
                                            $.messager.alert('提示','删除图片成功');
                                        });
                                    }
                                });
                            }
                        },'-',{
                            iconCls:'icon-release',
                            text:'重新发布',
                            handler:function(){
                                var ids = getPostSelects();
                                if(ids == ''){
                                    $.messager.alert('提示','请选择重新发布的图片');
                                }
                                $.post('<s:url action="release" namespace="/resource"/>',ids,function(data){
                                    refresh();
                                    selects = new Array();
                                    $.messager.alert('提示','重新发布图片成功');
                                });
                            }
                        }],
                    pageList : [24,48,80],
                    onSelectPage:function(pageNumber, pageSize){
                        loadImage(pageNumber,pageSize);
                    },
                    onRefresh:function(pageNumber,pageSize){
                        loadImage(pageNumber,pageSize);
                    },
                    onChangePageSize:function(pageNumber,pageSize){
                        loadImage(pageNumber,pageSize);
                    }
                });
                refresh();
            });

            function getPostSelects (){
                var ids = '';
                $.each(selects,function(key,value){
                    if(value){
                        ids = ids + 'selections=' +value +'&';
                    }
                });
                return ids;
            }

            function refresh(){
                var page = $('#pp').pagination('options').pageNumber;
                var rows = $('#pp').pagination('options').pageSize;
                loadImage(page,rows);
            }

            function loadImage(page,rows,params){
                $('#pp').pagination('loading');
                if(!params){
                    params = {page:page,rows:rows};
                }
                $.get('<s:url action="query"/>?col=8',params,function(data){
                    $('#pp').pagination({'page':page,'total':data.total});
                    $('#tt tr').remove();
                    $.each(data.rows,function(index,value){
                        $('#tt').append(rowHtml(value));
                    });
                    $('#pp').pagination('loaded');
                },'json');
            }

            function rowHtml(values){
                var html = '<tr>';
                $.each(values,function(index,value){
                    html = html + '<td align="center">';
                    if(value.releasePath){
                        html = html + '<ul class="imgArea"><li>';
                        html = html + '<img src="' + contextPath + value.releasePathZip + '" title="文件名：'+ value.name +'\n标题：' +value.title+'\n描述：' +value.description+'"/></li>';
                        html = html + '<li class="imgTitle"><input type="checkbox" onclick="selected(this,'+value.id+');"';
                        if(selects[value.id]){
                            html = html + 'checked/>';
                        }else{
                            html = html + '/>';
                        }
                        html = html + limitTitle(value.title) + '</li>';
                        html = html + '</ul>';
                    }else{
                        html = html + '';
                    }
                    html = html + '</td>';
                });
                html = html + '</tr>';
                return html;
            }

            function limitTitle(title){
                var maxLen = 5;
                if(title){
                    if(title.length > maxLen){
                        return title.substring(0,maxLen) + "...";
                    }else{
                        return title;
                    }
                }else{
                    return '';
                }
            }

            function query(){
                var page = 1;
                var rows = $('#pp').pagination('options').pageSize;
                $('#page_id').val(page);
                $('#rows_id').val(rows);
                var params = $('#queryform').serialize();
                loadImage(page,rows,params);
                $('#query-window').window('close');
            }

            function selected(obj,id){
                if(obj.checked){
                    selects.push(id);
                }else{
                    selects = $.grep(selects,function(value){
                        return value != id;
                    });
                }
            }
        </script>
        <style type="text/css">
            .imgArea {width:90px;margin:3px;border:solid 1px #ebebeb;background:#f7f7f7;padding:5px;list-style: none;}
            .imgArea img{text-align:center;height:60px;width:80px;display:block;border:0px;margin-left: 5px;}
            .imgTitle {text-align: left;padding-top:5px;}
            .imgTitle input {vertical-align:middle;margin:-4px 1px 0 3px;}
        </style>
    </head>
    <body class="easyui-layout">
        <div region="north" border="false" style="text-align:left;height:50px;padding:5px 0px 5px 0px;overflow: hidden;">
            <div id="pp" style="background:#efefef;border:1px solid #ccc;"></div>
        </div>
        <div region="center" border="false" style="background:#fff;border:1px #ccc solid; height:300px;">
            <div align="center" style="width:100%;">
                <table id="tt" align="left"></table>
            </div>
        </div>
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title=""  style="display:none;">
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
                            <input type="hidden" id="page_id" name="page"/>
                            <input type="hidden" id="rows_id" name="rows"/>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="query();">查询</a>
                </div>
            </div>
        </div>
    </body>
</html>
