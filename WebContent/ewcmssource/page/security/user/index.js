/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var UserIndex = function(urls){
    this._urls = urls;
}

UserIndex.prototype.init = function(opts){
    
    var urls = this._urls;
    
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    ewcmsOOBJ.setDatagridID(opts.datagridId);
    
    $(opts.datagridId).datagrid({
        fit:true,
        nowrap: false,
        singleSelect:true,
        rownumbers:true,
        pagination:true,
        idField:'username',
        pageSize:20,
        url: urls.queryUrl,
        view: detailview,
        loadMsg:'',
        columns:[[
            {field:'ck',checkbox:true},
            {field:'username',title:'用户名称',width:120,sortable:true},
            {field:'userInfo.name',title:'姓名',width:120,formatter:function(val,row){
                return row.userInfo.name;
              }},
            {field:'accountStart',title:'授权开始时间',width:130},
            {field:'accountEnd',title:'授权结束时间',width:130},
            {field:'userInfo.mphone',title:'手机',width:150,formatter:function(val,row){
                return row.userInfo.mphone;
              }},
            {field:'userInfo.phone',title:'电话',width:150,formatter:function(val,row){
                return row.userInfo.phone;
              }},
            {field:'userInfo.email',title:'邮件',width:200,formatter:function(val,row){
                return row.userInfo.email;
              }},
            {field:'createTime',title:'创建时间',width:130,sortable:true},
            {field:'enabled',title:'启用/停用',width:100,align:'center',formatter:function(val,row){
                if (val){
                    return "已启用&nbsp;&nbsp;<a href='#' onclick='inactive(\""+ row.username +"\")'><img src='../../source/image/scheduling/pause.png' width='13px' height='13px' title='停用操作'/></a>";
                }else {
                    return "已停用&nbsp;&nbsp;<a href='#' onclick='active(\""+ row.username +"\")'><img src='../../source/image/scheduling/resumed.png' width='13px' height='13px' title='启用操作'/></a>";
                }
              }}
        ]],
        detailFormatter:function(index,row){
            return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
        },
        onExpandRow: function(index,row){
            $('#ddv-'+index).panel({
                fit:true,
                border:false,
                cache:false,
                content: '<iframe src=' + urls.detailUrl + '?username=' +row.username + ' width=98% frameborder=0 height=350/>',
                onLoad:function(){
                    $('#tt').datagrid('fixDetailRowHeight',index);
                }
            });
            $('#tt').datagrid('fixDetailRowHeight',index);
        }
    });
    
    $(opts.toolbarAddId).bind('click',function(){
        $('#editifr-id').attr('src',urls.editUrl);
        openWindow('#edit-window',{title:'增加 - 用户',width:680,height:480})
    });
    
    $(opts.toolbarUpdateId).bind('click',function(){
        var rows = $(opts.datagridId).datagrid('getSelections');
        if(rows.length == 0){
            $.messager.alert('提示','请选择修改的用户','info');
            return;
        }
        var url = urls.editUrl + "?eventOP=update&username=" + rows[0].username; 
        $('#editifr-id').attr('src',url);
        openWindow('#edit-window',{title:'修改 - 用户',width:680,height:480})
    });
    
    $(opts.toolbarRemoveId).bind('click',function(){
        var rows = $(opts.datagridId).datagrid('getSelections');
        if(rows.length == 0){
            $.messager.alert('提示','请选择删除的用户','info');
            return;
        }
        $.messager.confirm('提示', '确定删除所选用户?', function(r){
            if (r){
                $.post(urls.deleteUrl,{"name":rows[0].name},function(data){
                   if(data.success){
                       $('#tt').datagrid('reload')
                       $('#tt').datagrid('unselectAll');
                   } else{
                       $.messager.alert('错误','删除用户失败','error');
                   }
                });
            }
        },'info');
    });
    
    $(opts.toolbarInitPasswordId).bind('click',function(){
        var rows = $(opts.datagridId).datagrid('getSelections');
        if(rows.length == 0){
            $.messager.alert('提示','请选择修改密码的用户','info');
            return;
        }
        var url = urls.initpasswordUrl + "?username=" + rows[0].username;
        $('#editifr-id').attr('src', url);
        openWindow('#edit-window',{title:'修改 - 用户密码',width:480,height:280})
    });
    
    $(opts.toolbarQueryId).bind('click',function(){
        querySearch(opts.queryFormId);
    });
}

UserIndex.prototype.closeEditWindow = function(){
    $('#edit-window').window('close');
}

UserIndex.prototype.active=function(username){
    var urls = this._urls;
    $.post(urls.activeUrl,{"username":username},function(data){
        if(data.success){
            $('#tt').datagrid('reload')
        } else{
            $.messager.alert('错误','启用用户失败','error');
        }
     });
}

UserIndex.prototype.inactive=function(username){
    var urls = this._urls;
    $.post(urls.inactiveUrl,{"username":username},function(data){
        if(data.success){
            $('#tt').datagrid('reload')
        } else{
            $.messager.alert('错误','启用用户失败','error');
        }
     });
}