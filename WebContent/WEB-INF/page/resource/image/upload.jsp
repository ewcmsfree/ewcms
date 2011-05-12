<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>图片上传</title>
        <script type='text/javascript' src='<s:url value="/source/js/jquery-1.4.2.min.js" />'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/jquery.uploadify.v2.1.0.js"/>'></script>
        <script type='text/javascript' src='<s:url value="/source/uploadify/swfobject.js"/>'></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/uploadify/uploadify.css"/>"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value='/source/css/ewcms.css'/>" />
        <script type="text/javascript">
            var isUpload = false;
            var contextPath = '<s:property  value="context"/>';
            $(document).ready(function() {
                $('#tt').datagrid({
                    title:'图片',
                    iconCls:'icon-image',
                    width:560,
                    height:230,
                    nowrap: false,
                    striped: true,
                    singleSelect:true,
                    columns:[[
                            {title:'图片',field:'releasePath',width:40,align:'center',
                                formatter:function(value,rowData){
                                    return '<img src="' + contextPath + value + '" style="width:32px;height:24px;margin: 0;padding: 0;" />';
                                }
                            },
                            {title:'名称',field:'name',width:120},
                            {field:'title',title:'标题',width:160,formatter:function(value,rowData){
                                    return "<input type='text' name='infos("+ rowData.id +").title' value='"+value+"' style='width:150px;'/>";
                                }
                            },
                            {field:'description',title:'描述',width:180,
                                formatter:function(value,rowData){
                                    return "<input type='text' name='infos("+rowData.id+").description' value='"+value+"' style='width:170px;'/>";
                                }
                            }
                        ]]
                });
                       
                $("#upload").uploadify({
                    'uploader': '<s:url value="/source/uploadify/medium/uploadify.swf"/>',
                    'script': '<s:url action="receive"/>;jsessionid=<%=session.getId()%>',
                    'cancelImg': '<s:url value="/source/uploadify/image/cancel.png"/>',
                    'queueID': 'fileQueue',
                    'fileDataName': 'myUpload',
                    'buttonImg': '<s:url value="/source/uploadify/image/browsfiles.png"/>',
                    'auto': true,
                    'multi':  <s:property value="multi"/>,
                    'fileDesc': 'jpg/gif/jpeg/png/bmp',
                    'fileExt' : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
                    onComplete: function (event, queueID, fileObj, response, data) {
                        var row = (new Function( "return " + response ))();
                        var multi = <s:property value="multi"/>;
                        if(multi){
                            $('#tt').datagrid("appendRow",row).datagrid('acceptChanges');
                        }else{
                            $('#tt').datagrid("deleteRow",0);
                            $('#tt').datagrid("appendRow",row).datagrid('acceptChanges');
                        }
                    },
                    onAllComplete:function(event,data){
                        setUploadComplete();
                    },
                    onSelectOnce:function(event,data){
                        setUpload();
                    },
                    onCancel:function(event,queueID,fileObj,data){
                        if(data.fileCount == 0){
                            setUploadComplete();
                        }
                    }
                });
                function setUpload(){
                    display("#fileQueue");
                    unDisplay('#fileInfosDiv');
                    isUpload = true;
                }
                function setUploadComplete(){
                    unDisplay("#fileQueue");
                    display('#fileInfosDiv');
                    isUpload = true;
                }
                function display(value){
                    $(value).css('display','');
                }
                function unDisplay(value){
                    $(value).css('display','none');
                }
            });

            function insert(callback){
                var params = $('#infosForm').serialize();
                $.post('<s:url action="updInfo" namespace="/resource"/>',params,function(data){
                    if(callback){
                        callback(data);
                    }
                },"json");
            }
        </script>
    </head>
    <body class="easyui-layout">
        <div region="north" border="false" style="text-align:left;height:40px;line-height:40px;padding:5px 0px 5px 0px;">
            <div style="padding-left:20px;"><input type="file" name="upload" id="upload"/></div>
        </div>
        <div region="center" border="false" style="background:#fff;border:0px;">
            <div id="fileQueue" style="display:none;margin-left: 5px;"></div>
            <div id="fileInfosDiv" style="margin-left: 5px;">
                <s:form  id="infosForm">
                    <table id="tt" align="center"></table>
                </s:form>
            </div>
        </div>
    </body>
</html>
