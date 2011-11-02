/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var UserDetail = function(urls){
    
    this._urls = urls;
}

UserDetail.prototype._constructParameter = function(opts){
    opts.property = opts.property || 'name'; 
    var rows = $(opts.tableId).datagrid('getSelections');
    var parameters = '';
    $.each(rows,function(index,value){
        if(opts.group){
            if( value.group == opts.group){
                parameters = parameters + opts.parameterName +'='  + value[opts.property] +'&';
            }
        }else{
            parameters = parameters + opts.parameterName +'='  + value[opts.property] +'&';
        }
    });
   return parameters;
}

UserDetail.prototype.init = function(opts){
 
    var urls = this._urls;
    var constructParameter = this._constructParameter;
    
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    
    $('#tt').propertygrid({
        width:700,
        url:urls.queryUrl,
        showGroup:true,
        scrollbarSize:0,
        singleSelect:false,
        columns:[[
            {field:'ck',checkbox:true},
            {field:'name',title:'名称',width:150},
            {field:"value",title:'描述',width:260}
        ]],
        onBeforeLoad:function(param){
             param['username'] = opts.username;    
        },
        toolbar:[{
            id:'btnadd',
            text:'添加',
            iconCls:'icon-add',
            handler:function(){
                openWindow('#edit-window',{title:'增加 - 权限/用户组',width:550,height:320})
          }
        },'-',{
            id:'btnremove',
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
                if($('#tt').datagrid('getSelections').length == 0){
                    $.messager.alert('提示','请选择删除的记录','info');
                    return;
                }
                var parameter ='username=' + opts.username + '&' + 
                        constructParameter({tableId:'#tt',parameterName:'authNames',group:'权限'}) +
                        constructParameter({tableId:'#tt',parameterName:'groupNames',group:'用户组'});
                $.messager.confirm('提示', '确定删除所选记录?', function(r){
                    if (r){
                        $.post(urls.removeUrl,parameter,function(data){
                            if(data.success){
                                $("#tt").datagrid('reload');
                                $('#tt').datagrid('unselectAll');
                            }else{
                                $.messager.alert('错误',data.message);
                            }
                      });
                    }
                },'info');
            }
        }]
    });
    
    if(opts.showTitle){
        $('#tt').propertygrid({
            title:"权限/用户组",
            iconCls:"icon-winedit"
        });
    }
    
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
    
    $("#group-tt").datagrid({
        width:500,
        idField:'name',
        pageSize:5,
        pagination:true,
        nowrap: false,
        rownumbers:true,
        pageList:[5],
        url:urls.groupQueryUrl,
        frozenColumns:[[
             {field:'ck',checkbox:true},
             {field:"name",title:'用户组名称',width:200}
        ]],
        columns:[[
             {field:"remark",title:'描述',width:230}
        ]]
    });
    
    $('#edit-window').dialog({
        buttons:[{
            text:'确定',
            iconCls:'icon-ok',
            handler:function(){
                var parameter ='username=' + opts.username + '&' + 
                        constructParameter({tableId:'#auth-tt',parameterName:'authNames'}) +
                        constructParameter({tableId:'#group-tt',parameterName:'groupNames'});
                $.post(urls.addUrl,parameter,function(data){
                    if(data.success){
                        $("#tt").datagrid('reload');
                    }else{
                        $.messager.alert('错误',data.message);
                    }
                });
                $('#auth-tt').datagrid('unselectAll');
                $('#group-tt').datagrid('unselectAll');
                $('#edit-window').dialog('close');
            }
         }] 
     });
    
    $('#auth-toolbar-query').bind('click',function(){
        ewcmsOOBJ.setQueryURL(urls.authQueryUrl);
        ewcmsOOBJ.setDatagridID('#auth-tt');
        querySearch('#auth-queryform');
    });
    
    $('#group-toolbar-query').bind('click',function(){
        ewcmsOOBJ.setQueryURL(urls.groupQueryUrl);
        ewcmsOOBJ.setDatagridID('#group-tt');
        querySearch('#group-queryform');
    });
}