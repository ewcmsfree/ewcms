<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>测试文件上传</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>">	
        <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#systemtab').tabs({
                    onSelect:function(title){
                        var multi = $('#multi_id').val();
                        var type = $('#type_id').val();
                        var uploadUrl = '';
                        var browerUrl = '';

                        if(type == 'image'){
                            uploadUrl = '<s:url action="upload" namespace="/resource/image"/>?multi='+multi;
                            browerUrl = '<s:url action="browse" namespace="/resource/image"/>?multi='+multi;
                        }else if(type == 'annex'){
                            uploadUrl = '<s:url action="upload" namespace="/resource/annex"/>?multi='+multi;
                            browerUrl = '<s:url action="browse" namespace="/resource/annex"/>?multi='+multi;
                        }

                        if(title == '本地图片'){
                            $("#uploadifr_id").attr('src',uploadUrl);
                        }else{
                            $("#queryifr_id").attr('src',browerUrl);
                        }
                    }
                });
            });

            function openImageWindow(multi){
                $('#multi_id').val(multi);
                $('#type_id').val('image');
                if(multi){
                    $('#uploadifr_id').attr('src','<s:url action="upload" namespace="/resource/image"/>?multi=true');
                }else{
                    $('#uploadifr_id').attr('src','<s:url action="upload" namespace="/resource/image"/>?multi=false');
                }
                openWindow("#edit-window-insert",{width:600,height:500});
            }

            function openAnnexWindow(multi){
                $('#multi_id').val(multi);
                $('#type_id').val('annex');
                if(multi){
                    $('#uploadifr_id').attr('src','<s:url action="upload" namespace="/resource/annex"/>?multi=true');
                }else{
                    $('#uploadifr_id').attr('src','<s:url action="upload" namespace="/resource/annex"/>?multi=false');
                }
                openWindow("#edit-window-insert",{width:600,height:500});

            }

            function tabInsertImage(){
                var tab = $('#systemtab').tabs('getSelected');
                var title = tab.panel('options').title;
                if(title == '本地图片'){
                    uploadifr.insert(function(data){
                        $.each(data,function(index,value){
                            alert(value.title);
                        });
                    });
                }else{
                    queryifr.insert(function(data){
                        $.each(data,function(index,value){
                            alert(value.title);
                        });
                    });
                }
            }

            function openWindow(selected,options){
                $(selected).removeAttr("style");
                $(selected).window({
                    width: (options.width ? options.width : 600),
                    height: (options.height ? options.height : 300),
                    left:(options.left ? options.left :150 ),
                    top:(options.top ? options.top :50),
                    modal: (options.modal ? options.modal : true),
                    maximizable:(options.maximizable ? options.maximizable : false),
                    minimizable:(options.minimizable ? options.minimizable : false)
                });
                $(selected).window('open');
            }
        </script>
    </head>
    <body>
        <a  href="javascript:void(0)" onclick="openImageWindow(true)">图片插入</a>
        <a  href="javascript:void(0)" onclick="openImageWindow(false)">图片插入单个图片</a>
        <a  href="javascript:void(0)" onclick="openAnnexWindow(true)">上传附件</a>
        <a href ="<s:url action="manage" namespace="/resource/image"/>" target="_blank">图片管理</a>
        <a href ="<s:url action="manage" namespace="/resource/annex"/>" target="_blank">附件管理</a>
        <a href ="<s:url action="manage"/>" target="_blank">资源管理</a>
        <div id="edit-window-insert" class="easyui-window" closed="true" icon="icon-save" title="插入图片" style="display:none;">
            <input type="hidden" id="multi_id" value= true />
            <input type="hidden" id="type_id" value="image"/>
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding:10px 5px 10px 0;background:#fff;border:1px solid #ccc;overflow: hidden;">
                    <div class="easyui-tabs"  id="systemtab" border="false" fit="true"  plain="true">
                        <div title="本地图片"  style="padding: 5px;" cache="true">
                            <iframe src="" id="uploadifr_id"  name="uploadifr" class="editifr" scrolling="no"></iframe>
                        </div>
                        <div title="服务器图片" cache="true">
                            <iframe src="" id="queryifr_id"  name="queryifr" class="editifr" scrolling="no"></iframe>
                        </div>
                    </div>
                </div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;padding:3px 6px;">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="tabInsertImage()">插入</a>
                </div>
            </div>
        </div>
    </body>
</html>