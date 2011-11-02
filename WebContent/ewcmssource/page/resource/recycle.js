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
        revert :function(datagridId,url){
            var o = this;
            var selects = this._construtSelects(datagridId);
            $.post(url,selects,function(data){
                if(data.success){
                    o.reload(datagridId);
                }else{
                    $.messager.alert('错误','还原资源错误');
                }
            });
        },
        remove :function(datagridId,url){
            if($(datagridId).datagrid('getSelections').length == 0){
                $.messager.alert('提示','请选择永久删除的资源','info');
                return;
            }
            var o = this;
            var selects = this._construtSelects(datagridId);
            $.messager.confirm('提示', '确定永久删除所选资源?', function(r){
                if (r){
                    $.post(url,selects,function(data){
                        if(data.success){
                            o.reload(datagridId);
                            $(datagridId).datagrid('unselectAll');
                        }else{
                            $.messager.alert('错误','永久删除资源失败');
                        }
                    });
                }
            },'info');
        },
        clear : function (datagridId,url){
            var o = this;
            $.messager.confirm('提示', '确定清空所有删除资源?', function(r){
                if (r){
                    $.post(url,function(data){
                        if(data.success){
                            o.reload(datagridId);
                        }else{
                            $.messager.alert('错误','清空所有删除资源失败');
                        }
                    });
                }
            },'info');
       }
}

var recycle = function(context,opts){
    this._context = context;

    opts = opts || {};
    
    this._opts = {};
    this._opts.datagridId = opts.datagridId || "#tt";
    this._opts.menuId = opts.menuId || "#mm";
    this._opts.menuItemTitleId =  opts.menuItemTitleId || "#menu-item-title";
    this._opts.toolbarRevertId = opts.toolbarRevertId || "#toolbar-revert";
    this._opts.toolbarRemoveId = opts.toolbarRemoveId || "#toolbar-remove";
    this._opts.toolbarClearId = opts.toolbarClearId || "#toolbar-clear";
    this._opts.toolbarQueryId = opts.toolbarQueryId || "#toolbar-query";
    this._opts.queryFormId = opts.queryFormId || "#queryform";
};
 
recycle.prototype.init = function(urls){
    var context = this._context;
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
                   return '<img src="' + context + val +'" style="height:32px;"/>';    
               }else{
                   return '<div style="height:32px;">&nbsp;</div>';
               }
           }},
           {field:'id',title:'编号',width:120,sortable:true,hidden:true},
           {field:'type',title:'资源类型',width:120,sortable:true,formatter:function(val,row){
               if(val == 'IMAGE'){
                   return '图片';
               }else if(val == 'FLASH'){
                   return 'flash';
               }else if(val == 'VIDEO'){
                   return '视频';
               }else{
                   return '附件';
               }
           }},
           {field:'name',title:'资源名称',width:320,sortable:true},
           {field:'description',title:'描述',width:320},
           {field:'createTime',title:'创建时间',width:200,align:'center'},
           {field:'updateTime',title:'删除时间',width:200,align:'center'}
        ]],
        onBeforeLoad:function(param){
             param['removeEvent'] = true;    
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
            if(item.iconCls == 'icon-resume'){
                operators.revert(opts.datagridId,urls.revert);
            }
            if(item.iconCls == 'icon-remove'){
                operators.remove(opts.datagridId,urls.remove);
            }
        }
    });
    
    $(opts.toolbarRevertId).bind('click',function(){
         operators.revert(opts.datagridId,urls.revert);
    });
    
    $(opts.toolbarRemoveId).bind('click',function(){
        operators.remove(opts.datagridId,urls.remove);
    });
    
    $(opts.toolbarClearId).bind('click',function(){
        operators.clear(opts.datagridId,urls.clear);
    });
    
    $(opts.toolbarQueryId).bind('click',function(){
        querySearch(opts.queryFormId);
    });
};