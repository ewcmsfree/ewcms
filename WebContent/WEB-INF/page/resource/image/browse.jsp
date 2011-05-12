<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>图片浏览</title>
        <script type='text/javascript' src='<s:url value="/source/js/jquery-1.4.2.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value='/source/css/ewcms.css'/>" />
        <script type="text/javascript">
            var contextPath = '<s:property  value="context"/>';
            var selectedImages = {};
            $(document).ready(function() {
                $('#pp').pagination({
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
                $.get('<s:url action="query"/>?col=5',params,function(data){
                    $('#pp').pagination({'page':page,'total':data.total});
                    $('#tt tr').remove();
                    $.each(data.rows,function(index,value){
                        $('#tt').append(rowHtml(value));
                    });
                    $('#pp').pagination('loaded');
                },'json');
            }

            function rowHtml(values){
                var multi = <s:property value="multi"/>;
                var html = '<tr>';
                $.each(values,function(index,value){
                    html = html + '<td align="left">';
                    if(value.releasePath){
                        html = html + '<ul class="imgArea"><li><img src="' + contextPath + value.releasePathZip + '" title="'+ value.name +'"/></li>';
                        if(multi){
                            html = html + '<li class="imgTitle"><input type="checkbox" onclick="selectImage(this,'+value.id+',\''+value.releasePath+'\',\''+value.title+'\',\''+value.description+'\',true);" ';
                        }else{
                            html = html + '<li class="imgTitle"><input type="radio" name="selectimage" onclick="selectImage(this,'+value.id+',\''+value.releasePath+'\',\''+value.title+'\',\''+value.description+'\',false);" ';
                        }
                        if(selectedImages[value.id]){
                            html = html + ' checked/>';
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
                var params = $('#queryForm').serialize();
                loadImage(page,rows,params);
            }

            function selectImage(obj,id,url,title,description,multi){
                if(multi){
                    if(obj.checked){
                        selectedImages[id] = {releasePath:url,title:title,description:description};
                    }else{
                        selectedImages[id] = null;
                    }
                }else{
                    selectedImages = {};
                    selectedImages[id] = {releasePath:url,title:title,description:description};
                }
            }

            function insert(callback){
                $.each(selectedImages,function(key,value){
                    if(value){
                        callback([value]);
                    }
                });
            }
        </script>
        <style type="text/css">
            form.paramsClass{margin: 0px;padding:0 2px;}
            form.paramsClass table{margin-left: 10px;margin-bottom: 8px;}
            .imgArea {width:90px;margin:3px;border:solid 1px #ebebeb;background:#f7f7f7;padding:5px;list-style: none;}
            .imgArea img{text-align:center;height:60px;width:80px;display:block;border:0px;margin-left: 5px;}
            .imgTitle {text-align: left;padding-top:5px;}
            .imgTitle input {vertical-align:middle;margin:-4px 1px 0 3px;}
        </style>
    </head>
    <body class="easyui-layout">
        <div region="north" border="false" style="text-align:left;height:80px;padding:5px 0px 5px 0px;overflow: hidden;">
            <form id="queryForm" class="paramsClass">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td width="130px">文件名：<input type="text" id="name_id" name="name" size="6"/></td>
                        <td width="130px">标题：<input type="text" id="title_id" name="title" size="6"/></td>
                        <td width="200px">描述：<input type="text" id="description_id" name="description" size="12"/></td>
                        <td><a class="easyui-linkbutton" icon="icon-search" href="javascript:void(0)" onclick="query();">查询</a></td>
                    </tr>
                </table>
                <div id="pp" style="background:#efefef;border:1px solid #ccc;"></div>
                <input type="hidden" name="page" id="page_id"/>
                <input type="hidden" name="rows" id="rows_id"/>
            </form>
        </div>
        <div region="center" border="false" style="background:#fff;border:1px #ccc solid; height:300px;">
            <div align="center" style="width:100%;">
                <table id="tt" align="left"></table>
            </div>
        </div>
    </body>
</html>
