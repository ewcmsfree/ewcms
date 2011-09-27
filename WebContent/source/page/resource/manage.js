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
            $(datagridId).datagrid('reload');
        },
        publish :function(datagridId,url){
            var o = this;
            var selects = this._construtSelects(datagridId);
            $.post(url,selects,function(data){
                if(data.success){
                    o.reload(datagridId);
                    //$.messager.alert('提示','发布资源成功');
                }else{
                    $.messager.alert('错误',data.message);
                }
            });
        },
        remove :function(datagridId,url){
            var o = this;
            var selects = this._construtSelects(datagridId);
            $.post(url,selects,function(data){
                if(data.success){
                    o.reload(datagridId);
                    //$.messager.alert('提示','资源删除成功');
                }else{
                    $.messager.alert('错误','删除资源失败');
                }
            });
        },
        save : function (ifr,windowId,datagridId){
            var o = this;
            window.frames[ifr].insert(function(success,data){
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
    this._opts.menuItemTitleId =  opts.menuItemTitleId || "#menu-item-title";
    this._opts.resourceUploadWindowId = opts.resourceUploadWindowId || "#resource-upload-window";
    this._opts.resourceUpdateWindowId = opts.resourceUpdateWindowId || "#resource-update-window";
    this._opts.thumbUpdateWindowId = opts.thumbUpdateWindowId || "#thumb-update-window";
    this._opts.toolbarUploadId = opts.toolbarUploadId || "#toolbar-upload";
    this._opts.toolbarPublishId = opts.toolbarPublishId || "#toolbar-publish";
    this._opts.toolbarRemoveId = opts.toolbarRemoveId || "#toolbar-remove";
    this._opts.toolbarQueryId = opts.toolbarQueryId || "#toolbar-query";
    this._opts.queryFormId = opts.queryFormId || "#queryform";
    this._opts.buttonSaveId = opts.buttonSaveId || "#button-save";
    this._opts.iframeUploadName = opts.iframeUploadName || "uploadifr";
    this._opts.iframeUpdateName = opts.iframeUpdateName || "updateifr";
};
 
manage.prototype.init = function(urls){
    var context = this._context;
    var type = this._type;
    var opts = this._opts;
    
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.query);
    ewcmsOOBJ.setDatagridID(opts.datagridId);
    
    $(opts.datagridId).datagrid({
        idField:"id",
        url:urls.query,
        fit:true,
        pagination:true,
        columns:[[
           {field:'ck',checkbox:true},
           {field:'thumbUri',title:'引导图',width:180,align:'center',formatter:function(val,row){
               if(val){
                   return '<img src="' + context + val +'" style="height:64px;"/>';    
               }else{
                   return '<div style="height:64px;">&nbsp;</div>';
               }
           }},
           {field:'id',title:'编号',width:120,sortable:true,hidden:true},
           {field:'name',title:'资源名称',width:320,sortable:true},
           {field:'description',title:'描述',width:320,},
           {field:'createTime',title:'创建时间',width:200,align:'center'},
           {field:'publishTime',title:'发布时间',width:200,align:'center',formatter:function(val,row){
               if(val){
                   return "<font style='font-weight: bold;color:red;'>" + val + "</font>";   
               }
           }}
        ]],
        onBeforeLoad:function(param){
             param['type'] = type;    
        },
        onRowContextMenu:function(e, rowIndex, rowData){
            e.preventDefault();
            $(opts.menuId).menu('show', {
                left:e.pageX,
                top:e.pageY
            });
            var menuItem = $(opts.menuId).menu('getItem',opts.menuItemTitleId);
            var title = rowData.name;
            if(title.length > 15){
                title = title.substring(0,12) + "...";
            }
            menuItem.text = "<b>"+title+"</b>";
            menuItem.disabled=true;
            $(opts.menuId).menu('setText',menuItem);
            $(opts.menuId).menu('disableItem',menuItem);
            $(opts.datagridId).datagrid("unselectAll");
            $(opts.datagridId).datagrid("selectRow",rowIndex);
        }
    });
    
    $(opts.menuId).menu({
        onClick:function(item){
            var row = $(opts.datagridId).datagrid("getSelected");
            if(item.iconCls == 'icon-download'){
                window.open(context + row.uri);
            }
            if(item.iconCls == 'icon-save'){
                openWindow(opts.resourceUpdateWindowId,
                        {width:600,height:400,title:"更新资源",url : urls.resource + "?type="+type+"&multi=false&id="+row.id });
            }
            if(item.iconCls == 'icon-image-upload'){
                openWindow(opts.thumbUpdateWindowId,
                        {width:450,height:200,title:"更新引导图",url: urls.thumb + "?id=" + row.id});
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
                if(success){
                    operators.reload(opts.datagridId);    
                }else{
                    $.messager.alert('错误','更新资源描述错误');
                }
            });
        }
    });
    
    $(opts.thumbUpdateWindowId).window({
        onClose:function(){
            operators.reload(opts.datagridId);
        }
    });
    
    $(opts.toolbarUploadId).bind('click',function(){
        openWindow(opts.resourceUploadWindowId,{width:600,height:400,title:"上传资源",url:urls.resource+'?type='+type});
    });
    
    $(opts.toolbarPublishId).bind('click',function(){
        operators.publish(opts.datagridId,urls.publish);
    });
    
    $(opts.toolbarRemoveId).bind('click',function(){
        operators.remove(opts.datagridId,urls.remove);
    });
    
    $(opts.toolbarQueryId).bind('click',function(){
        querySearch(opts.queryFormId);
    });
    
    $(opts.buttonSaveId).bind('click',function(){
        operators.save(opts.iframeUploadName, opts.resourceUploadWindowId, opts.datagridId);
    });
};