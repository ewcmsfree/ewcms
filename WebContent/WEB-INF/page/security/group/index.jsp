<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户组</title>
    <script type='text/javascript' src='<s:url value="/source/js/jquery-1.4.2.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
     <script type="text/javascript">
        $(function(){
            $("#auth_all_tt").datagrid({
                title:'',
                fitColumns:true,
                nowrap: false,
                rownumbers:true,
                idField:'name',
                pagination:true,
                url:'<s:url action="query" namespace="/security/authority"/>',
                frozenColumns:[[
                     {field:'ck',checkbox:true},
                     {field:"name",title:'权限名称',width:100}
                ]],
                columns:[[
                     {field:"remark",title:'描述',width:300}
                ]]
            });
            $("#user_all_tt").datagrid({
                title:'',
                fitColumns:true,
                nowrap: false,
                rownumbers:true,
                idField:'username',
                pagination:true,
                url:'<s:url action="query" namespace="/security/user"/>',
                frozenColumns:[[
                     {field:'ck',checkbox:true},
                     {field:"username",title:'用户名称',width:100}
                ]],
                columns:[[
                     {field:"userInfo",title:'姓名',width:300,formatter:function(value,row){
                         if(value){
                             return value.name;     
                         }else{
                             return '';
                         }
                     }}
                ]]
            });
            $('#auth_dd').dialog({
                buttons:[{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        addDetails('<s:url action="addAuthorities"/>','#auth_all_tt','#auth_dd',function(data){
                            return data.name;
                        });
                    }
                 }],
                 onOpen:function(){
                     $(this).window('resize');
                     $("#auth_all_tt").datagrid("resize");
                 }
             });
            $('#user_dd').dialog({
                buttons:[{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        addDetails('<s:url action="addUsers"/>','#user_all_tt','#user_dd',function(data){
                            return data.username;
                        });
                    }
                 }],
                 onOpen:function(){
                     $(this).window('resize');
                     $("#user_all_tt").datagrid("resize");
                 }
             });
            $('#tt').treegrid({
                title:'用户组列表',
                nowrap: false,
                rownumbers: true,
                singleSelect:true,
                idField:'id',
                treeField:'name',
                animate:true,
                collapsible:true,
                url:'<s:url action="queryTreeGrid"/>',
                frozenColumns:[[
                    {field:'name',title:'名称',width:200}
                ]],
                columns:[[
                    {field:"remark",title:'描述',width:500}
                ]],toolbar:[{
                    id:'btnadd',
                    text:'添加',
                    iconCls:'icon-add',
                    handler:function(){
                        var url = '<s:url action = "input"/>';
                        $('#editifr').attr('src',url);
                        openWindow('#edit-window',{title:'增加 - 用户组',width:800,height:500})
                  }
                },{
                    id:'btnupdate',
                    text:'修改',
                    iconCls:'icon-edit',
                    disabled:true,
                    handler:function(){
                        var name = $('#tt').treegrid('getSelected').name;
                        var url = '<s:url action = "input"/>?eventOP=update&name='+name;
                        $('#editifr').attr('src',url);
                        openWindow('#edit-window',{title:'修改 - 用户组',width:800,height:500})
                    }
                 },'-',{
                    id:'btndelete',
                    text:'删除',
                    iconCls:'icon-remove',
                    disabled:true,
                    handler:function(){
                        $.messager.confirm('提示',"确定要删除所选记录吗?",function(r){
                            if(r){
                                var row = $('#tt').treegrid('getSelected');
                                if(row.level == 'group'){
                                    deleteGroup();
                                }
                                if(row.level == 'userdetail'){
                                    var url = '<s:url action="deleteUser"/>';
                                    deleteDetail(url);
                                }
                                if(row.level == 'authdetail'){
                                    var url = '<s:url action="deleteAuth"/>';
                                    deleteDetail(url);
                                }
                            }
                        })
                    }
                  },'-',{
                    id:'btnaddauth',
                    text:'添加权限',
                    disabled:true,
                    handler:function(){
                        $('#auth_dd').dialog('open');
                    }
                  },{
                    id:'btnadduser',
                    text:'添加用户',
                    disabled:true,
                    handler:function(){
                        $('#user_dd').dialog('open');
                    }
                 }],
                onBeforeLoad:function(row,param){
                    if (row){
                        if(row.level=='users'){
                            var name = $(this).treegrid('getParent',row.id).name;
                            var url = '<s:url action="queryUsers"/>?name='+name;
                            $(this).treegrid('options').url = url;
                        }
                        if(row.level=='authorities'){
                            var name = $(this).treegrid('getParent',row.id).name;
                            var url = '<s:url action="queryAuthorities"/>?name='+name;
                            $(this).treegrid('options').url = url;
                        }
                    } else {
                        $(this).treegrid('options').url = '<s:url action="queryTreeGrid"/>';
                    }
                },
                onLoadSuccess:function(){
                    disableButtons();
                },
                onClickRow:function(row){
                    disableButtons();
                    if(row){
                       if(row.level == 'group'){
                           $('#btnupdate').linkbutton('enable');
                           $('#btndelete').linkbutton('enable');
                       }
                       if(row.level == 'users'){
                           $('#btnadduser').linkbutton('enable');
                       }
                       if(row.level == 'authorities'){
                           $('#btnaddauth').linkbutton('enable');
                       }
                       if(row.level == 'userdetail' || row.level == 'authdetail'){
                           $('#btndelete').linkbutton('enable');
                       }
                    }
                    $('#tt').treegrid('toggle',row.id);
                }
            });
            $('#edit-window').window({
                onClose:function(){
                    $('#editifr').attr('src',"about:blank");
                    $('#tt').treegrid('reload');
                }
            });
        });
        
        function deleteGroup(){
            var name = $('#tt').treegrid('getSelected').name;
            var url = '<s:url action = "delete"/>?name='+name;
            $.getJSON(url,function(data){
                if(data.success){
                    $('#tt').treegrid('reload');
                    $('#btndelete').linkbutton('disable');
                }else{
                    $.messager.alter("错误",data.message);$.messager.alter("错误",data.message);
                }
            });
        }
        
        function deleteDetail(url){
            var detail = $('#tt').treegrid('getSelected');
            var group = $('#tt').treegrid('getParent',$('#tt').treegrid('getParent',detail.id).id);
            $.getJSON(url, {name:group.name,detailName:detail.name},function(data){
                if(data.success){
                    $('#tt').treegrid('remove',detail.id);
                    $('#btndelete').linkbutton('disable');
                }else{
                    $.messager.alter("错误",data.message);
                }
            });
        }
        
        function addDetails(url,table,dlg,detailCall){
            var selects = $(table).datagrid("getSelections");
            if(selects.length == 0){
                return;
            }
            var detail = $('#tt').treegrid('getSelected');
            var group = $('#tt').treegrid('getParent',detail.id);
            var url = url +'?name='+group.name;
            for(var i = 0 ; i < selects.length ; i++){
                url = url + "&detailNames="+detailCall(selects[i]);
            }
            $.getJSON(url,function(data){
                if(data.success){
                    $('#tt').treegrid('append', {
                        parent: detail.id,
                        data: data.nodes
                    });
                    $(table).datagrid('unselectAll');
                    $(dlg).dialog('close');
                }else{
                    $.messager.alter("错误",data.message);
                }
            });
        }
        
        function disableButtons(){
            $('#btnupdate').linkbutton('disable');
            $('#btndelete').linkbutton('disable');
            $('#btnadduser').linkbutton('disable');
            $('#btnaddauth').linkbutton('disable');
        }
        
        function closeWindow(){
            $('#edit-window').window('close');
        }
        
        function editSubmit(iframeid){
            if(typeof(iframeid) == 'undefined')iframeid = 'editifr';
            window.frames[iframeid].pageSubmit();
        }  
    </script>
</head>
<body class="easyui-layout">
    <div region="center" border="false" style="background:#fff;">
        <table id="tt" align="center" fit="true"></table>
    </div>
    <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="用户组" style="display:none;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false">
               <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
            </div>
            <div region="south" border="false" style="padding-right:20px;text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="editSubmit()">保存</a>
                <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">取消</a>
            </div>
        </div>
    </div>  
    <div id="user_dd" title="用户列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:300px;">
        <table id="user_all_tt" style="width:575px;height:220px;"></table>
    </div>
    <div id="auth_dd" title="权限列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:300px;">
        <table id="auth_all_tt" style="width:575px;height:220px;"></table>
    </div>
</body>
</html>