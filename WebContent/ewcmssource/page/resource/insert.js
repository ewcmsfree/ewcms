/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var ResourceInsert = function(context,urls){
    this._context = context;
    this._urls = urls;
}

ResourceInsert.prototype.init=function(opts){
    var context = this._context;
    var urls = this._urls;
    
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.query);
    ewcmsOOBJ.setDatagridID("#tt");
    
    $('#tt').datagrid({
        idField:"id",
        url:urls.query,
        width:560,
        height:330,
        pagination:true,
        nowrap: false,
        pageSize:5,
        pageList:[5],
        singleSelect:!opts.multi,
        columns:[[
           {field:'ck',checkbox:true},
           {field:'thumbUri',title:'引导图',width:60,align:'center',formatter:function(val,row){
               if(val){
                   return '<img src="' + context + val  + '" style="width:40px;height:30px;margin:0;padding: 0;"/>';
               }else{
                   return '<div style="height:30px;">&nbsp;</div>';
               }
           }},
           {field:'name',title:'资源名称',width:200,sortable:true},
           {field:'description',title:'描述',width:250}
        ]],
        onBeforeLoad:function(param){
             param['type'] = opts.type;
             param['multi'] = opts.multi;
        }
    });
    
    $("#toolbar-query").bind('click',function(){
        querySearch("#queryform");
    });
}

ResourceInsert.prototype.insert=function(callback,message){
    var title =$(".easyui-tabs").tabs("getSelected").panel('options').title;
    if(title == "上传"){
        resourceifr.insert(callback,message);
    }else{
        var rows = $("#tt").datagrid('getSelections');
        if(rows.length == 0){
            $.messager.alert('提示','请选择插入的资源','info');
            return;
        }
        callback(rows,true);
    }
}