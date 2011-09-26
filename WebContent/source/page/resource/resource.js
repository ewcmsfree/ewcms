/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

UploadUtils = {
    getInputElementId:function(row){
        return "input_discription_"+row.id;
    } ,
    getInputElementName:function(row){
        return "descriptions["+row.id+"]";
    },
    getImageElementId:function(row){
        return "img_" + row.id;
    },
    getImageFileElementId:function(row){
        return "image_upload_" + row.id;
    },
    getImageQueueElementId:function(row){
        return "image_queue_" + row.id;
    },
    initThumbUpload:function(context,opts,row){
        var utils = this;
        $("#"+this.getImageFileElementId(row)).uploadify({
            'uploader': opts.uploader,
            'expressInstall':opts.expressInstall,
            'cancelImg': opts.cancelImg,
            'script': opts.thumbScript,
            'queueID': this.getImageQueueElementId(row),
            'fileDataName': 'myUpload',
            'scriptData': {'id':row.id},
            'auto': true,
            'multi':  false,
            'fileDesc': 'jpg/gif/jpeg/png/bmp',
            'fileExt' : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
            onComplete: function (event, queueID, fileObj, response, data) {
                var res = (new Function( "return " + response ))();
                if(res.success){
                    var uri = context + res.value.thumbUri ;
                    $("#"+utils.getImageElementId(row)).attr("src",uri);    
                }else{
                    $.messager.alert('提示',fileObj['name']+'上传错误');
                }
            }
        });
    }
}

var Upload = function(context,saveaction,opts){
    this._context = context;
    this._opts = opts;
    this._saveaction = saveaction;
};

Upload.prototype.init=function(){
    var utils = UploadUtils;
    var context = this._context;
    var opts = this._opts;
    
    $('#tt').datagrid({
        nowrap: false,
        fit : true,
        columns:[[
                  {field:'ck',checkbox:true,width:20},
                  {field:'thumbUri',title:'引导图',width:40,align:'center',
                      formatter:function(value,row){
                          if(value){
                              value = context + value;
                          }else{
                              value = "" ;
                          }
                          return '<img id="'+utils.getImageElementId(row)+'" src="' + value + '" style="width:40px;height:30px;margin:0;padding: 0;"/>';
                      }
                  }, {field:'description',title:'描述',width:180,
                      formatter:function(value,row){
                          return "<input id='"+utils.getInputElementId(row)+"' type='text' name='"+ utils.getInputElementName(row) +"' value='"+value+"' style='width:170px;'/>";
                      }
                  }, {field:'uploadImage',title:'引导图上传',width:300,align:"center",
                      formatter:function(value,row){
                          return '<input type="file" id="'+utils.getImageFileElementId(row)+'"/><div id="'+utils.getImageQueueElementId(row)+'"></div>' ;
                      }
                  }
                  ]],
                  onSelect:function(rowIndex, rowData){
                      $("#"+utils.getInputElementId(rowData)).attr("name",utils.getInputElementName(rowData));
                  },
                  onUnselect:function(rowIndex, rowData){
                      $("#"+utils.getInputElementId(rowData)).attr("name","");
                  }
    });
    
    $("#upload").uploadify({
        'uploader': opts.uploader,
        'expressInstall':opts.expressInstall,
        'cancelImg': opts.cancelImg,
        'script': opts.script,
        'queueID': 'upload_queue',
        'fileDataName': 'myUpload',
        'scriptData': {'id':opts.resourceId,'type':opts.type},
        //'buttonImg': opts.buttonImg,
        'percentage':'speed',
        'auto': true,
        'multi':  opts.multi,
        'fileDesc': opts.fileDesc,
        'fileExt' : opts.fileExt,
        onComplete: function (event, queueID, fileObj, response, data) {
            var res = (new Function( "return " + response ))();
            if(res.success){
                $('#tt').datagrid("appendRow",res.value);
                utils.initThumbUpload(context, opts, res.value);    
            }else{
                $.messager.alert('提示',fileObj['name']+'上传错误');
            }
        },onAllComplete:function(event,data){
            $("#upload_queue").css('display','none');
            $("#resource_infos").css('display','');
            $('#tt').datagrid("selectAll");
            $.each($('td'), function(index, td){
                $(td).bind('click',function(e){
                    return false;
                });
            })
        },onSelectOnce:function(event,data){
            $("#upload_queue").css('display','');
            $("#resource_infos").css('display','none');
            var rows = $('#tt').datagrid("getRows").length;
            for(var i = rows -1 ; i >= 0 ; i--){
                $('#tt').datagrid("deleteRow",i);
            }
        }
    });
};

Upload.prototype.insert=function(callback,message){
    var params = $('#resource_infos').serialize();
    $.post(this._saveaction,params,function(data){
        if(callback){
            callback(data.value,data.success);
        }else{
            if(!data.success){
                $.messager.alert('提示','插入图片错误');
            }
        }
     },"json");
};