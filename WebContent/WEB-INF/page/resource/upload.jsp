<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>资源上传</title>
        <script type='text/javascript' src='<s:url value="/source/js/jquery.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/jquery.uploadify.min.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/swfobject.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/uploadify/uploadify.css"/>"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value='/source/css/ewcms.css'/>" />
        <script type="text/javascript">
        
            var contextPath = '<s:property  value="context"/>';
            
            function display(value){
                $(value).css('display','');
            }
            
            function displayNone(value){
                $(value).css('display','none');
            }
            
            function getInputElementId(row){
                return "input_discription_"+row.id;
            }
            
            function getInputElementName(row){
                return "descriptions["+row.id+"]";
            }
            
            function getImageElementId(row){
                return "img_" + row.id;
            }
            
            function getImageQueueElementId(row){
                return "image_queue_" + row.id;
            }
            
            function getImageUploadElementId(row){
                return "image_upload_" + row.id + "Uploader";
            }
            
            function getImageFileElementId(row){
                return "image_upload_" + row.id;
            }
            
            function initImageUpload(row){
                $("#"+getImageFileElementId(row)).uploadify({
                    'uploader': '<s:url value="/source/uploadify/medium/uploadify.allglyphs.swf"/>',
                    'expressInstall':'<s:url value="/source/uploadify/medium/expressInstall.swf"/>',
                    'script': '<s:url action="thumbReceive"/>;jsessionid=<%=session.getId()%>',
                    'cancelImg': '<s:url value="/source/uploadify/image/cancel.png"/>',
                    'queueID': getImageQueueElementId(row),
                    'fileDataName': 'myUpload',
                    'scriptData': {'id':row.id},
                    'auto': true,
                    'multi':  false,
                    //'buttonText': '\u6570\u636e\u683c\u5f0f\u4e0d\u6b63\u786e',
                    'fileDesc': 'jpg/gif/jpeg/png/bmp',
                    'fileExt' : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
                    onComplete: function (event, queueID, fileObj, response, data) {
                        var imageRow = (new Function( "return " + response ))();
                        var uri = contextPath + imageRow.thumbUri;
                        $("#"+getImageElementId(row)).attr("src",uri);
                    }
                });
            }
            
            $(function() {
                $('#tt').datagrid({
                    nowrap: false,
                    fit : true,
                    columns:[[
                            {field:'ck',checkbox:true,width:20},
                            {field:'thumbUri',title:'图片',width:40,align:'center',
                                formatter:function(value,row){
                                    if(value){
                                        value = contextPath + value;
                                    }else{
                                        value = "" ;
                                    }
                                    return '<img id="'+getImageElementId(row)+'" src="' + value + '" style="width:40px;height:30px;margin:0;padding: 0;"/>';
                                }
                            }, {field:'description',title:'描述',width:180,
                                formatter:function(value,row){
                                    return "<input id='"+getInputElementId(row)+"' type='text' name='"+ getInputElementName(row) +"' value='"+value+"' style='width:170px;'/>";
                                }
                            }, {field:'uploadImage',title:'引导图上传',width:300,align:"center",
                                formatter:function(value,row){
                                    return '<input type="file" id="'+getImageFileElementId(row)+'"/>'+
                                           '<div id="'+getImageQueueElementId(row)+'"></div>' ;
                                }
                            }
                        ]],
                        onSelect:function(rowIndex, rowData){
                            $("#"+getInputElementId(rowData)).attr("name",getInputElementName(rowData));
                        },
                        onUnselect:function(rowIndex, rowData){
                            $("#"+getInputElementId(rowData)).attr("name","");
                        }
                  });
                
                $("#upload").uploadify({
                    'uploader': '<s:url value="/source/uploadify/medium/uploadify.allglyphs.swf"/>',
                    'expressInstall':'<s:url value="/source/uploadify/medium/expressInstall.swf"/>',
                    'script': '<s:url action="receive"/>;jsessionid=<%=session.getId()%>',
                    'cancelImg': '<s:url value="/source/uploadify/image/cancel.png"/>',
                    'queueID': 'upload_queue',
                    'fileDataName': 'myUpload',
                    'scriptData': {'type':'<s:property value="type"/>'},
                    //'buttonImg': '<s:url value="/source/uploadify/image/browsfiles.png"/>',
                    'percentage':'speed',
                    'auto': true,
                    'multi':  <s:property value="multi"/>,
                    'fileDesc': '<s:property value="fileDesc"/>',
                    'fileExt' : '<s:property value="fileExt"/>',
                    onComplete: function (event, queueID, fileObj, response, data) {
                        var row = (new Function( "return " + response ))();
                        $('#tt').datagrid("appendRow",row).datagrid('acceptChanges');
                        initImageUpload(row);
                    },onAllComplete:function(event,data){
                        displayNone("#upload_queue");
                        display('#resource_infos');
                        $('#tt').datagrid("resize");
                        $('#tt').datagrid("selectAll");
                        $.each($('td'), function(index, td){
                            $(td).bind('click',function(e){
                                return false;
                            });
                        })
                    },onSelectOnce:function(event,data){
                        display("#upload_queue");
                        displayNone('#resource_infos');
                        var rows = $('#tt').datagrid("getRows").length;
                        for(var i = rows -1 ; i >= 0 ; i--){
                            $('#tt').datagrid("deleteRow",i);
                        }
                    }
                });
            });

            function insert(callback){
                var params = $('#resource_infos').serialize();
                $.post('<s:url action="save"/>',params,
                        function(data){
                            if(callback){
                               callback(data);
                            }
                        },"json");
            }
        </script>
    </head>
    <body class="easyui-layout">
         <div region="center" border="false">
            <div id="upload_queue" style="margin-left: 5px;display:none;"></div>
            <s:form  id="resource_infos" style="width:565px;height:275px; margin:5px;">
               <table id="tt" align="center"></table>
            </s:form>
        </div>
        <div region="south" border=false style="text-align:right;height:38px;line-height:38px;padding:3px 6px;">
            <input type="file" name="upload" id="upload"/>
        </div>
    </body>
</html>
