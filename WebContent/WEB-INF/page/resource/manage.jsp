<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
    <head>
        <title>资源管理管理</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
        <ewcms:datepickerhead></ewcms:datepickerhead>
        <script type="text/javascript">
        
            ewcmsBOBJ = new EwcmsBase();
            ewcmsOOBJ = new EwcmsOperate();
            ewcmsOOBJ.setQueryURL('<s:url action="query"/>');
            ewcmsOOBJ.setDatagridID("#tt");
            
            $(function(){
                $('#tt').datagrid({
                    idField:"id",
                    url:"<s:url action="query"/>",
                    fit:true,
                    fitColumns:true,
                    pagination:true,
                    columns:[[
                       {field:'ck',checkbox:true},
                       {field:'thumbUri',title:'图',width:100,align:'center',formatter:function(val,row){
                           var context = '<s:property value="context"/>';
                       return '<a href="' + context + row.uri + '" target="_blank">' +
                              '<img src="' + context + val +'" style="height:64px;"/></a>'; 
                       }},
                       {field:'id',title:'编号',width:120,sortable:true,hidden:true},
                       {field:'name',title:'资源名称',width:120,sortable:true},
                       {field:'description',title:'描述',width:120,},
                       {field:'updateTime',title:'时间',width:100,align:'center'}
                    ]],
                    onBeforeLoad:function(param){
                         param['type'] = "<s:property value="type"/>";    
                    },
                    onLoadSuccess:function(data){
                        $('a.easyui-linkbutton[id^=upload_]').linkbutton({plain:false});
                        $.each($('td'), function(index, td){
                            $(td).bind('click',function(e){
                                return false;
                            });
                        });
                    },
                    onRowContextMenu:function(e, rowIndex, rowData){
                        e.preventDefault();
                        $('#mm').menu('show', {
                            left:e.pageX,
                            top:e.pageY
                        });
                        var menuItem = $("#mm").menu('getItem',"#row_menu_title");
                        var title = rowData.name;
                        if(title.length > 15){
                            title = title.substring(0,12) + "...";
                        }
                        menuItem.text = "<b>"+title+"</b>";
                        menuItem.disabled=true;
                        $('#mm').menu('setText',menuItem);
                        $('#mm').menu('disableItem',menuItem);
                        $('#tt').datagrid("unselectAll");
                        $('#tt').datagrid("selectRow",rowIndex);
                    }
                });
                
                $('#mm').menu({
                    onClick:function(item){
                        var row = $('#tt').datagrid("getSelected");
                        if(item.iconCls == 'icon-download'){
                            var context = '<s:property value="context"/>';
                            alert(context + row.uri);
                        }
                        if(item.iconCls == 'icon-save'){
                            openUpdate(row.id);
                        }
                        if(item.iconCls == 'icon-image-upload'){
                            openThumb(row.id);
                        }
                        if(item.iconCls == 'icon-publish'){
                            publish();
                        }
                        if(item.iconCls == 'icon-remove'){
                            del();
                        }
                    }
                });
                
                $('#window-update').window({
                    onClose:function(){
                        updateifr.insert();
                    }
                });
            });
            
            function openUpload(){
                openWindow("#window-upload",{width:600,height:400,title:"上传资源",
                    url:"<s:url action="resource"/>?type=<s:property value="type"/>"});
            }
            
            function openUpdate(id){
                openWindow("#window-update",{width:600,height:400,title:"更新资源",
                    url:"<s:url action="resource"/>?type=<s:property value="type"/>&multi=false&id="+id});
            }
            
            function openThumb(id){
                openWindow("#window-thumb",{width:450,height:200,title:"更新缩略图",
                    url:"<s:url action="thumb"/>?id="+id});
            }
            
            function publish(){
                var rows = $('#tt').datagrid('getSelections');
                var ids = '';
                $.each(rows,function(index,value){
                    ids = ids + 'selections=' +value.id +'&';
                });
                $.post('<s:url action="publish"/>',ids,function(data){
                    if(data.success){
                        $('#tt').datagrid('unselectAll');
                        $('#tt').datagrid('reload');
                        $.messager.alert('提示','发布资源成功');
                    }else{
                        $.messager.alert('提示',data.message);
                    }
                });
            }
            
            function del(){
                var rows = $('#tt').datagrid('getSelections');
                var ids = '';
                $.each(rows,function(index,value){
                    ids = ids + 'selections=' +value.id +'&';
                });
                $.post('<s:url action="delete"/>',ids,function(data){
                    if(data.success){
                        $('#tt').datagrid('unselectAll');
                        $('#tt').datagrid('reload');
                        $.messager.alert('提示','资源删除成功');
                    }else{
                        $.messager.alert('提示','资源删除失败');
                    }
                });
            }
            
            function save(){
                uploadifr.insert(function(success,data){
                    if(success){
                        $.messager.alert('提示','资源上传成功');
                        $('#window-upload').window('close');
                    }else{
                        $.messager.alert('错误','资源上传失败');
                    }
                });
            }
           
        </script>
    </head>
    <body>
        <table id="tt" toolbar="#tb"></table>
        <div id="tb" style="padding:5px;height:auto;display:none;">
                <div style="margin-bottom:5px">
                   <a href="#" class="easyui-linkbutton" iconCls="icon-upload" plain="true" onclick='javascript:openUpload();'>上传</a>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-publish" plain="true">发布</a>
                   <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
                </div>
                <div style="padding-left:5px;">
                   <form id="queryform" style="padding: 0;margin: 0;">
                    文件名: <input type="text" name="name" style="width:80px"/>&nbsp;
                    描述: <input type="text" name="description" style="width:120px"/>&nbsp;
                    上传日期 从: <ewcms:datepicker name="fromDate" option="inputsimple" format="yyyy-MM-dd"/> 到: <ewcms:datepicker name="toDate" option="inputsimple" format="yyyy-MM-dd"/>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="querySearch('#queryform');">查询</a>
                   </form>
               </div>
        </div>
        <div id="mm" class="easyui-menu" style="width:180px;display:none;">  
            <div id="row_menu_title"><b>操作</b></div>
            <div class="menu-sep"></div>   
            <div iconCls="icon-save">资源更新</div>  
            <div iconCls="icon-image-upload">缩略图更新</div>  
            <div class="menu-sep"></div>
            <div iconCls="icon-publish">发布</div>
            <div iconCls="icon-remove">删除</div>  
            <div class="menu-sep"></div>   
            <div iconCls="icon-download">下载</div>   
        </div>  
        <div id="resource-upload-window" class="easyui-window" closed="true" icon='icon-upload'>
          <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                   <iframe src="" id="uploadifr_id"  name="uploadifr" class="editifr" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:save();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#window-upload').window('close');return false;">取消</a>
                </div>
            </div>
       </div>
       <div id="resource-update-window" class="easyui-window" closed="true" icon='icon-save'>
          <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                   <iframe src="" id="updateifr_id"  name="updateifr" class="editifr" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#window-update').window('close');return false;">关闭</a>
                </div>
            </div>
       </div>
       <div id="thumb-update-window" class="easyui-window" closed="true" icon='icon-save'>
          <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                   <iframe src="" id="thumbifr_id"  name="thumbifr" class="editifr" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#window-thumb').window('close');return false;">关闭</a>
                </div>
            </div>
       </div>
    </body>
</html>