/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var GroupIndex = function(urls){
    this._urls = urls;
}

GroupIndex.prototype.init = function(opts){
    
    var urls = this._urls;
    
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    ewcmsOOBJ.setDatagridID(opts.datagridId);
    
    $(opts.datagridId).datagrid({
        fit:true,
        fitColumns:true,
        nowrap: false,
        singleSelect:true,
        rownumbers:true,
        pagination:true,
        idField:'name',
        pageSize:20,
        url: urls.queryUrl,
        view: detailview,
        loadMsg:'',
        columns:[[
            {field:'ck',checkbox:true},
            {field:'name',title:'名称',width:300},
            {field:"remark",title:'描述',width:400}
        ]],
        detailFormatter:function(index,row){
            return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
        },
        onExpandRow: function(index,row){
            $('#ddv-'+index).panel({
                fit:true,
                border:false,
                cache:false,
                content: '<iframe src=' + urls.detailUrl + '?name=' +row.name + ' width=98% frameborder=0 height=350/>',
                onLoad:function(){
                    $('#tt').datagrid('fixDetailRowHeight',index);
                }
            });
            $('#tt').datagrid('fixDetailRowHeight',index);
        }
    });
    
    $(opts.toolbarAddId).bind('click',function(){
        $('#editifr-id').attr('src',urls.editUrl);
        openWindow('#edit-window',{title:'增加 - 用户组',width:680,height:480})
    });
    
    $(opts.toolbarUpdateId).bind('click',function(){
        var rows = $(opts.datagridId).datagrid('getSelections');
        if(rows.length == 0){
            $.messager.alert('提示','请选择修改的用户组','info');
            return;
        }
        var url = urls.editUrl + "?eventOP=update&name=" + rows[0].name; 
        $('#editifr-id').attr('src',url);
        openWindow('#edit-window',{title:'修改 - 用户组',width:680,height:480})
    });
    
    $(opts.toolbarRemoveId).bind('click',function(){
        var rows = $(opts.datagridId).datagrid('getSelections');
        if(rows.length == 0){
            $.messager.alert('提示','请选择删除的用户组','info');
            return;
        }
        $.messager.confirm('提示', '确定删除所选用户组?', function(r){
            if (r){
                $.post(urls.deleteUrl,{"name":rows[0].name},function(data){
                   if(data.success){
                       $('#tt').datagrid('reload');
                       $('#tt').datagrid('unselectAll');
                   } else{
                       $.messager.alert('错误','删除用户组失败','error');
                   }
                });
            }
        },'info');
    });
    
    $(opts.toolbarQueryId).bind('click',function(){
        querySearch(opts.queryFormId);
    });
}

GroupIndex.prototype.closeEditWindow = function(){
    $('#edit-window').window('close');
}