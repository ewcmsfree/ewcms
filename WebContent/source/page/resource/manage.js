/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

operators = {
        _construtSelects : function(datagridId){
            var rows = $(datagridId).datagrid('getSelections');
            var selects = '';
            $.each(rows,function(index,value){
                selects = selects + 'selections=' +value.id +'&';
            });
            return selects;
        },
        reload : function(datagridId){
            $(datagridId).datagrid('unselectAll');
            $(datagridId).datagrid('reload');
        },
        publish :function(datagridId,url){
            var selects = this._construtSelects(datagridId);
            $.post(url,selects,function(data){
                if(data.success){
                    this.reload(datagridId);
                    //$.messager.alert('提示','发布资源成功');
                }else{
                    $.messager.alert('错误',data.message);
                }
            });
        },
        remove :function(datagridId,url){
            var selects = this._construtSelects(datagridId);
            $.post(url,selects,function(data){
                if(data.success){
                    datagrid.reload(datagridId);
                    //$.messager.alert('提示','资源删除成功');
                }else{
                    $.messager.alert('错误','删除资源失败');
                }
            });
        },
        save : function (ifr,windowId,datagridId){
            var o = this;
            ifr.insert(function(success,data){
                if(success){
                    o.reload(datagridId);
                    $(windowId).window('close');
                }else{
                    $.messager.alert('错误','资源上传失败');
                }
           });
       }
}

var manage = function(context,type,opts){
    this._context = context;
    this._type = type;

    opts = opts || {};
    
    this._opts = {};
    this._opts.datagridId = opts.datagridId || "#tt";
    this._opts.menuId = opts.menuId || "#mm";
    this._opts.resourceUploadWindowId = opts.resourceUploadWindowId || "#resource-upload-window";
    this._opts.resourceUpdateWindowId = opts.resourceUpdateWindowId || "#resource-update-window";
    this._opts.thumbUpdateWidnowId = opts.thumbUpdateWindowId || "#thumb-update-window";
    

};
 
manage.prototype.init = function(urls){
    var context = this._context;
    var type = this._type;
    var opts = this._opts;
    
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.query);
    ewcmsOOBJ.setDatagridID(datagridId);
    
    $(opts.datagridId).datagrid({
        idField:"id",
        url:urls.query,
        fit:true,
        fitColumns:true,
        pagination:true,
        columns:[[
           {field:'ck',checkbox:true},
           {field:'thumbUri',title:'图',width:100,align:'center',formatter:function(val,row){
               return '<a href="' + context + row.uri + '" target="_blank">' +
                      '<img src="' + context + val +'" style="height:64px;"/></a>'; 
           }},
           {field:'id',title:'编号',width:120,sortable:true,hidden:true},
           {field:'name',title:'资源名称',width:120,sortable:true},
           {field:'description',title:'描述',width:120,},
           {field:'updateTime',title:'时间',width:100,align:'center'}
        ]],
        onBeforeLoad:function(param){
             param['type'] = type;    
        },
        onLoadSuccess:function(data){
            $.each($('td'), function(index, td){
                $(td).bind('click',function(e){
                    return false;
                });
            });
        },
        onRowContextMenu:function(e, rowIndex, rowData){
            e.preventDefault();
            $(opts.menuId).menu('show', {
                left:e.pageX,
                top:e.pageY
            });
            var menuItem = $(opts.menuId).menu('getItem',"#row_menu_title");
            var title = rowData.name;
            if(title.length > 15){
                title = title.substring(0,12) + "...";
            }
            menuItem.text = "<b>"+title+"</b>";
            menuItem.disabled=true;
            $(opts.menuId).menu('setText',menuItem);
            $(opts.menuId).menu('disableItem',menuItem);
            $(opts.menuId).datagrid("unselectAll");
            $(opts.menuId).datagrid("selectRow",rowIndex);
        }
    });
    
    $(opts.menuId).menu({
        onClick:function(item){
            var row = $(opts.datagridId).datagrid("getSelected");
            if(item.iconCls == 'icon-download'){
                alert(context + row.uri);
            }
            if(item.iconCls == 'icon-save'){
                openWindow(opts.resourceUpdateWindowId,
                        {width:600,height:400,title:"更新资源",url : urls.resourc});
            }
            if(item.iconCls == 'icon-image-upload'){
                openWindow(opts.thumbUpdateWindowId,
                        {width:450,height:200,title:"更新缩略图",url: urls.thumb + "?id=" + row.id});
            }
            if(item.iconCls == 'icon-publish'){
                operators.publish(opts.datagridId,urls.publish);
            }
            if(item.iconCls == 'icon-remove'){
                operators.remove(opts.datagridId,urls.remove);
            }
        }
    });
    
    $(opts.resourceUpdateWindowId).window({
        onClose:function(){
            updateifr.insert(function(success,data){
                operators.reload(opts.datagridId);
            });
        }
    });
};