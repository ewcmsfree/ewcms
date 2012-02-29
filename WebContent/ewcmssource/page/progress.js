/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

var progress = function(url){
    this._url = url;
}

progress.prototype.init = function(opts){
    $('#progress').treegrid({
        width:505,
        height:205,
        rownumbers: true,
        animate:true,
        collapsible:true,
        fitColumns:true,
        idField:'id',
        treeField:'name',
        remoteSort:false,
        columns:[[
            {title:'任务',field:'name',width:180},
            {field:'progress',title:'进度',width:230,rowspan:2,
                formatter:function(value){
                    var content = '<div style="width:100%;background:#fff;border:1px solid #ccc">' ;
                    if (value){
                        content =content +'<div style="width:' + value + '%;background:red">' + value + '%' + '</div>'; 
                    }else{
                        content = content + '等待';                        
                    } 
                    content = content +  '</div>';
                    return content;
                }
            },
            {field:'username',title:'停止',width:60,rowspan:2,
                formatter:function(value,row){
                    if (opts.adminRole  || value == opts.username){
                        return "<a href='#' onclick='removeTask(\""+ row.taskId +"\")'><img src='../../ewcmssource/image/scheduling/pause.png' width='13px' height='13px' title='停用操作'/></a>";
                    }else {
                        return "";
                    }
                }
            } ]]
        });
        var _progress = this;
        $(window).unload(function() {
            pubsub.onUnload();
        }).load(function(){
            pubsub.initialize(_progress._url);
        });
}

progress.prototype.loadData=function(data){
    $('#progress').treegrid('loadData',data);    
}