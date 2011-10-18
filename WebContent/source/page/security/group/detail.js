/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var GroupDetail = function(urls){
    
    this._urls = urls;
}

GroupDetail.prototype._constructParameter = function(opts){
    opts.property = opts.property || 'name'; 
    var rows = $(opts.tableId).datagrid('getSelections');
    var parameters = '';
    $.each(rows,function(index,value){
        if(opts.group && value.group == opts.group){
            parameters = parameters + opts.parameterName +'='  + value[opts.property] +'&';
        }else{
            parameters = parameters + opts.parameterName +'='  + value[opts.property] +'&';
        }
    });
   return parameters;
}

GroupDetail.prototype.init = function(opts){
 
    var urls = this._urls;
    var constructParameter = this._constructParameter;
    
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    
    $('#tt').propertygrid({
        title:"权限/用户",
        iconCls:"icon-edit",
        width:500,
        height:'auto',
        url:urls.queryUrl,
        showGroup:true,
        scrollbarSize:0,
        singleSelect:false,
        columns:[[
            {field:'ck',checkbox:true},
            {field:'name',title:'名称',width:200},
            {field:"value",title:'描述',width:300}
        ]],
        onBeforeLoad:function(param){
             param['name'] = opts.groupName;    
        },
        toolbar:[{
            id:'btnadd',
            text:'添加',
            iconCls:'icon-add',
            handler:function(){
                openWindow('#edit-window',{title:'增加 - 权限/用户',width:550,height:320})
          }
        },'-',{
            id:'btnremove',
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
                var parameter ='name=' + opts.groupName + '&' + 
                        constructParameter({tableId:'#tt',parameterName:'authNames',group:'权限'}) +
                        constructParameter({tableId:'#tt',parameterName:'usernames',group:'用户'});
                $.post(urls.removeUrl,parameter,function(data){
                    if(data.success){
                        $("#tt").datagrid('reload');
                    }else{
                        $.messager.alert('错误',data.message);
                    }
              });
            }
        }]
    });
    
    $('#auth-tt').datagrid({
        width:500,
        pageSize:5,
        nowrap: false,
        rownumbers:true,
        idField:'name',
        pagination:true,
        pageList:[5],
        url: urls.authQueryUrl,
        frozenColumns:[[
             {field:'ck',checkbox:true},
             {field:"name",title:'权限名称',width:200}
        ]],
        columns:[[
             {field:"remark",title:'描述',width:230}
        ]]
    });
    
    $("#user-tt").datagrid({
        width:500,
        idField:'username',
        pageSize:5,
        pagination:true,
        nowrap: false,
        rownumbers:true,
        pageList:[5],
        url:urls.userQueryUrl,
        frozenColumns:[[
             {field:'ck',checkbox:true},
             {field:"username",title:'用户名称',width:200}
        ]],
        columns:[[
             {field:"userInfo",title:'姓名',width:230,formatter:function(value,row){
                 return value.name;     
             }}
        ]]
    });
    
    $('#edit-window').dialog({
        buttons:[{
            text:'确定',
            iconCls:'icon-ok',
            handler:function(){
                var parameter ='name=' + opts.groupName + '&' + 
                        constructParameter({tableId:'#auth-tt',parameterName:'authNames'}) +
                        constructParameter({tableId:'#user-tt',parameterName:'usernames',property:'username'});
                $.post(urls.addUrl,parameter,function(data){
                    if(data.success){
                        $("#tt").datagrid('reload');
                    }else{
                        $.messager.alert('错误',data.message);
                    }
                });
                $('#auth-tt').datagrid('unselectAll');
                $('#user-tt').datagrid('unselectAll');
                $('#edit-window').dialog('close');
            }
         }] 
     });
    
    $('#auth-toolbar-query').bind('click',function(){
        ewcmsOOBJ.setQueryURL(urls.authQueryUrl);
        ewcmsOOBJ.setDatagridID('#auth-tt');
        querySearch('#auth-queryform');
    });
    
    $('#user-toolbar-query').bind('click',function(){
        ewcmsOOBJ.setQueryURL(urls.userQueryUrl);
        ewcmsOOBJ.setDatagridID('#user-tt');
        querySearch('#user-queryform');
    });
}