<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>频道权限</title>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
    <script type="text/javascript">
        var permissions = [
            {id:1,name:'读文章'},
            {id:2,name:'写文章'},
            {id:4,name:'发布专栏'},
            {id:8,name:'创建专栏'},
            {id:16,name:'修改专栏'},
            {id:32,name:'删除专栏'},
            {id:64,name:'所有权限'}
        ];
        
        $(function(){
            var lastIndex;
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
            $("#group_all_tt").datagrid({
                title:'',
                fitColumns:true,
                nowrap: false,
                rownumbers:true,
                idField:'name',
                pagination:true,
                url:'<s:url action="query" namespace="/security/group"/>',
                frozenColumns:[[
                     {field:'ck',checkbox:true},
                     {field:"name",title:'用户组名称',width:100}
                ]],
                columns:[[
                     {field:"remark",title:'说明',width:300}
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
                        addSelectsToTreeGrid('#auth_all_tt',1,'#auth_dd');
                    }
                 }],
                 onOpen:function(){
                     $(this).window('resize');
                     $("#auth_all_tt").datagrid("resize");
                 }
             });
            $('#group_dd').dialog({
                buttons:[{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        addSelectsToTreeGrid('#group_all_tt',2,'#group_dd');
                    }
                 }],
                 onOpen:function(){
                     $(this).window('resize');
                     $("#group_all_tt").datagrid("resize");
                 }
             });
            $('#user_dd').dialog({
                buttons:[{
                    text:'确定',
                    iconCls:'icon-ok',
                    handler:function(){
                        addSelectsToTreeGrid('#user_all_tt',3,'#user_dd',function(data){
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
                treeField:'grant',
                animate:true,
                collapsible:true,
                frozenColumns:[[
                    {field:'grant',title:'授权',width:300}
                ]],
                columns:[[
                    {field:"permission",title:'权限',width:200,
                        editor:{
                            type:'combobox',
                            options:{
                                valueField:'id',
                                textField:'name',
                                required:true,
                                editable:false,
                                data:permissions
                             }
                        },
                        formatter:function(value){
                            for(var i=0; i<permissions.length; i++){
                                if (permissions[i].id == value) {
                                    return permissions[i].name;
                                }
                            }
                            return value;
                        }
                    }
                ]],
                toolbar:[{
                    id:'btnaddauth',
                    text:'添加授权',
                    iconCls:'icon-add',
                    handler:function(){
                        $('#auth_dd').dialog('open');
                  }
                },{
                    id:'btnaddgroup',
                    text:'添加用户组',
                    iconCls:'icon-add',
                    handler:function(){
                        $('#group_dd').dialog('open');
                    }
                },{
                    id:'btnadduser',
                    text:'添加用户',
                    iconCls:'icon-add',
                    handler:function(){
                        $('#user_dd').dialog('open');
                    }
                },'-',{
                    id:'btndelete',
                    text:'删除',
                    iconCls:'icon-remove',
                    handler:function(){
                        var row = $('#tt').treegrid('getSelected');
                        if(row.id < 1000){
                            return ;
                        }
                        $.messager.confirm('提示',"确定要删除所选记录吗?",function(r){
                            if(r){
                                $('#tt').treegrid('remove',row.id);
                            }
                        }); 
                    }
                },'-',{
                    id:'btndsave',
                    text:'保存',
                    iconCls:'icon-save',
                    handler:function(){
                        var insertToForm = function(parent){
                            var rows = $("#tt").treegrid('getChildren',parent);
                            for(var i = 0 ; i < rows.length ; i++){
                                $('#tt').treegrid('endEdit',rows[i].id);
                                if(!rows[i].permission){
                                    $.messager.alert('提示',rows[i].grant + '权限没有设置');
                                    $("#tt").treegrid('select',rows[i].id);
                                    return false;
                                }
                                $('<input>').attr({type: "hidden",name: "sidPermissions['"+rows[i].grant+"']",value: rows[i].permission}).appendTo('form');
                            }
                            return true;
                        }
                        if(insertToForm(1) && insertToForm(2) &&  insertToForm(3)){
                            alert("dsfsdfsdfsdfsdf");
                            $('form').submit();
                        }
                    }
                }],
                onClickRow:function(row){
                    $('#tt').treegrid('endEdit',lastIndex);
                    if(row.id >= 1000){
                        $('#tt').treegrid('beginEdit',row.id);
                        lastIndex = row.id;
                    }
                }
            });
            initTreegridData();
        });
        
        function initTreegridData(){
            $('#tt').treegrid('append', {
                parent: null,
                data: [
                    {id:1,grant:"授权"},
                    {id:2,grant:"用户组"},
                    {id:3,grant:"用户"}
                ]
            });
            <s:iterator value="sidPermissions.keySet()" id="sid">
            var row = {grant:'<s:property value="#sid"/>',
                      permission:<s:property value="sidPermissions.get(#sid)"/>};
            insertRow(row);
            </s:iterator>
        }
        
        function addSelectsToTreeGrid(source,parent,dlg,nameCallback){
            var selects = $(source).datagrid('getSelections');
            var existRows = $("#tt").treegrid('getChildren',parent);
            var newIds = [];
            if(!nameCallback){
                nameCallback = function(data){
                    return data.name;
                }
            }
            for(var i = 0 ; i < selects.length; i++){
                var  notExist = true;
                for(var j = 0 ; j < existRows.length ; j++){
                    if(nameCallback(selects[i]) == existRows[j].grant){
                        notExist = false;
                        break;
                    }
                }
                if(notExist){
                    var id = insertRow({'grant':nameCallback(selects[i])});
                    $('#tt').treegrid('beginEdit',id);
                }
            }
            $(source).datagrid('unselectAll');
            $(dlg).dialog('close');
        }
        
        var idIndex = 1000;
        function insertRow(row){
            idIndex++;
            row.id = idIndex;
            if(isRole(row.grant)){
                $('#tt').treegrid('append', {
                    parent:1,
                    data: [row]
                });
            }else if(isGroup(row.grant)){
                $('#tt').treegrid('append', {
                    parent:2,
                    data: [row]
                });
            }else{
                $('#tt').treegrid('append', {
                    parent:3,
                    data: [row]
                });
            }    
            return idIndex;
        }
        
        function isRole(val){
            return /^ROLE_.*$/.test(val);
        }
        
        function isGroup(val){
            return /^GROUP_.*$/.test(val);
        }
    </script>
</head>
<body class="easyui-layout">
    <div region="center" style="padding:2px;" border="false">
        <table id="tt" fit="true"></table>      
    </div>
    <div region="south" style="padding:3px 10px;" border="false">
        <s:form action="channelAclUpdate">
            <div><s:checkbox name="inherit" cssStyle="vertical-align:middle;"/><span style="font-size:110%;color:#ff3300;">继承权限</span></div>
            <s:hidden name="id"/>
        </s:form>
    </div>
   
    <div id="auth_dd" title="权限列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:300px;">
        <table id="auth_all_tt" style="width:575px;height:220px;"></table>
    </div>
    <div id="group_dd" title="用户组列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:380px;">
       <table id="group_all_tt" style="width:565px;height:300px;"></table>
    </div>
    <div id="user_dd" title="用户列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:300px;">
        <table id="user_all_tt" style="width:575px;height:220px;"></table>
    </div>
</body>
</html>