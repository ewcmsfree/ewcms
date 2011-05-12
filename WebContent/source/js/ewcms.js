var ewcms = {
    openWindow:function(selected,options){
        if($(selected).length == 0){
            alert(selected + " window has no");
        }
        //移除开始隐藏属性
        $(selected).removeAttr("style");
        $(selected).window({
            title: (options.title ? options.title : ' '),
            width: (options.width ? options.width : 500),
            height: (options.height ? options.height : 300),
            left:(options.left ? options.left :150 ),
            top:(options.top ? options.top :50),
            modal: (options.modal ? options.modal : true),
            maximizable:(options.maximizable ? options.maximizable : false),
            minimizable:(options.minimizable ? options.minimizable : false)
        });
        $(selected).window('open');
    },
    closeWindow:function(selected){
        if($(selected).length == 0){
            alert(selected + " window has no");
        }
        $(selected).window('close');
    }      
}

/*新开窗口*/ 
function openWindow(selected,options){
     ewcms.openWindow(selected, options)
}

/*关闭窗口*/ 
function closeWindow(selected){
    ewcms.closeWindow(selected);
}
            
/*数据显示列表制定*/ 
function openDataGrid(baseOptions){ 
    if(typeof(baseOptions) == 'undefined' || !baseOptions.columns)
    {
        $.messager.alert('提示','请定义表字段');
        return;
    }
	    	
    //定义默认工具栏
    var baseToolbarArr = [
    {
        text:'新增',
        iconCls:'icon-add',
        handler:addOperateBack
    },'-',

    {
        text:'修改',
        iconCls:'icon-edit',
        handler:updOperateBack
    },'-',

    {
        text:'删除',
        iconCls:'icon-remove',
        handler:delOperateBack
    },'-',

    {
        text:'查询',
        iconCls:'icon-search',
        handler:queryOperateBack
    },'-',

    {
        text:'缺省查询',
        iconCls:'icon-back',
        handler:initOperateQueryBack
    }
    ];
			
    //定义默认不滑动的字段
    var baseFrozencolumnsArr = [[{
        field:'ck',
        checkbox:true,
        width:50
    }]];
      
    $((baseOptions.tableid ? baseOptions.tableid : globaoptions.tableid)).datagrid({
        title:(baseOptions.title ? baseOptions.title : ''),
        iconCls:(baseOptions.iconCls ? baseOptions.iconCls : ''),
        nowrap: (baseOptions.nowrap ? baseOptions.nowrap : false),
        width:(baseOptions.width ? baseOptions.width : 600),
        height:(baseOptions.height ? baseOptions.height : 350),
        nowrap:(baseOptions.nowrap ? baseOptions.nowrap : true),
        pagination:(baseOptions.pagination ? baseOptions.pagination : true),
        rownumbers:(baseOptions.rownumbers ? baseOptions.rownumbers : true),
        singleSelect:(baseOptions.singleSelect ? baseOptions.singleSelect : false),
        striped: (baseOptions.striped ? baseOptions.striped : true),
        url:(baseOptions.url ? baseOptions.url : globaoptions.queryURL),
        idField:(baseOptions.idField ? baseOptions.idField : globaoptions.idfield),
        frozenColumns:(baseOptions.frozenColumns ? baseOptions.frozenColumns : baseFrozencolumnsArr),
        columns:baseOptions.columns,
        toolbar:(baseOptions.toolbar ? baseOptions.toolbar : baseToolbarArr),
        onLoadSuccess:function(data){
            if(data.errors)$.messager.alert('提示',data.errors.join('\n'));
        }
    });
}
                        
/*根据查询条件查询记录*/ 
function querySearch(options){
    if(typeof(options) == 'undefined')options = {};
    var tableid = (options.tableid ? options.tableid : globaoptions.tableid);
    var windowid = (options.windowid ? options.windowid : globaoptions.querywindowid);
    var url = (options.url ? options.url : globaoptions.queryURL);
    var formid = (options.formid ? options.formid : globaoptions.queryformid);
            	
    var value = $(formid).serialize();
    value = "parameters['" + value;
    value = value.replace(/\=/g,"']=");
    value = value.replace(/\&/g,"&parameters['");

    var index = url.indexOf("?");
    if (index == -1){
        url = url + '?' + value;
    }else{
        url = url + '&' + value;
    }
    $(tableid).datagrid({
        pageNumber:1,
        url:url
    });

    $(windowid).window('close');
}

/*刷新修改后的记录*/ 
function queryReload(hide,options){
    if(typeof(options) == 'undefined')options = {};
    var tableid = (options.tableid ? options.tableid : globaoptions.tableid);
    var windowid = (options.windowid ? options.windowid : globaoptions.editwindowid);
            	         
    $(tableid).datagrid('reload');
    if(hide){
        $(windowid).window('close');
        $(tableid).datagrid('clearSelections');
    }
}
            
/*查询指定的id号记录*/ 
function queryNews(ids,options){
    if(typeof(options) == 'undefined')options = {};
    var tableid = (options.tableid ? options.tableid : globaoptions.tableid);
    var url = (options.url ? options.url : globaoptions.queryURL);
    if (typeof(url) == 'undefined') return;
            	
    var index = url.indexOf("?");
    if (index == -1){
        url = url + '?';
    }else{
        url = url + "&";
    }
                
    for (var i =0 ; i < ids.length ; ++i){
        url += 'selections=' + ids[i] + '&';
    }
    $(tableid).datagrid({
        'url':url
    });
}
          	            
/* iframe自适应大小调节*/    
function iframeFitHeight(oIframe)
{
    newHeight = oIframe.contentWindow.document.body.scrollHeight;
    newWidth = oIframe.contentWindow.document.body.scrollWidth;
    if(newHeight < 80)newHeight = 80;
    if(newWidth < 150)newWidth = 150;
    oIframe.width = newWidth;
    oIframe.height = newHeight;
}  
			
/*表单提交操作*/    
function saveOperator(iframeid){
    if(typeof(iframeid) == 'undefined')iframeid = 'editifr';
    window.frames[iframeid].document.forms[0].submit();
}  
            
	    	
/*添加操作*/ 
function addOperateBack(options){
    if(typeof(options) == 'undefined')options = {};
    var iframeid = (options.iframeid ? options.iframeid : globaoptions.iframeid);
    var windowid = (options.windowid ? options.windowid : globaoptions.editwindowid);
    var url = (options.url ? options.url : globaoptions.inputURL);
    var width = (options.width ? options.width : globaoptions.editwidth);
    var height = (options.height ? options.height : globaoptions.editheight);
    var title = (options.title ? options.title : globaoptions.title);
    var top = (options.top ? options.top : globaoptions.top);
    var left = (options.left ? options.left : globaoptions.left);
            	
    $(iframeid).attr('src',url);
    openWindow(windowid,{
        width:width,
        height:height,
        top:top,
        left:left,
        title:'增加 - ' + title
        });
}
	    	
/*修改操作*/ 
function updOperateBack(options){
    if(typeof(options) == 'undefined')options = {};
    var tableid = (options.tableid ? options.tableid : globaoptions.tableid);
    var iframeid = (options.iframeid ? options.iframeid : globaoptions.iframeid);
    var windowid = (options.windowid ? options.windowid : globaoptions.editwindowid);
    var url = (options.url ? options.url : globaoptions.inputURL);
    var width = (options.width ? options.width : globaoptions.editwidth);
    var height = (options.height ? options.height : globaoptions.editheight);
    var title = (options.title ? options.title : globaoptions.title);
    var top = (options.top ? options.top : globaoptions.top);
    var left = (options.left ? options.left : globaoptions.left);
            	            		    		
    var rows = $(tableid).datagrid('getSelections');
    if(rows.length == 0){
        $.messager.alert('提示','请选择修改记录','info');
        return;
    }
                
    var index = url.indexOf("?");
    if (index == -1){
        url = url + '?';
    }else{
        url = url + "&";
    }

    callBackId=function(row){
        return row.id;
    }
    if(options.callBackId){
        callBackId = options.callBackId;
    }
    for(var i=0;i<rows.length;++i){
        url += 'selections=' + callBackId(rows[i]) +'&';
    }
    $(iframeid).attr('src',url);
    openWindow(windowid,{
        width:width,
        height:height,
        top:top,
        left:left,
        title:'修改 - ' + title
        });
}
	    	
/*删除操作*/ 
function delOperateBack(options){
    if(typeof(options) == 'undefined')options = {};
    var tableid = (options.tableid ? options.tableid : globaoptions.tableid);
    var url = (options.url ? options.url : globaoptions.deleteURL);
            	  	
    var rows = $(tableid).datagrid('getSelections');
    if(rows.length == 0){
        $.messager.alert('提示','请选择删除记录','info');
        return ;
    }
    var ids = '';
    for(var i=0;i<rows.length;++i){
        ids =ids + 'selections=' + rows[i].id +'&';
    }
    $.messager.confirm("提示","确定要删除所选记录吗?",function(r){
        if (r){
            $.post(url,ids,function(data){          	
            	$.messager.alert('成功','删除成功','info');
            	$(tableid).datagrid('clearSelections');
                $(tableid).datagrid('reload');              	
            });
        }
    });
}
	    	
/*查询操作*/ 
function queryOperateBack(options){
    if(typeof(options) == 'undefined')options = {};
    var windowid = (options.windowid ? options.windowid : globaoptions.querywindowid);
    var width = (options.width ? options.width : globaoptions.querywidth);
    var height = (options.height ? options.height : globaoptions.queryheight);
    openWindow(windowid,{
        width:width,
        height:height,
        title:'查询'
    });
}
	    	
/*初始查询操作*/ 
function initOperateQueryBack(options){
    if(typeof(options) == 'undefined')options = {};
    var tableid = (options.tableid ? options.tableid : globaoptions.tableid);
    var url = (options.url ? options.url : globaoptions.queryURL);
    $(tableid).datagrid({
        pageNumber:1,
        url:url
    });

//$(tableid).datagrid('options').pageNumber = 1;
//$(tableid).datagrid('reload',{});
//$(tableid).datagrid('options').pageNumber = 1;
//$(tableid).datagrid('reload');	    	
} 
			
/*全局变量处理*/ 
var globaoptions = {}; //全局变量对象
			 	
function setGlobaVariable(options){
    //表单编辑地址必须指定
    if(typeof(options) == 'undefined' || !options.inputURL)
    {
        $.messager.alert('提示','请定义全局变量inputURL');
        return;
    }
		    	
    //数据查询地址必须指定
    if(!options.queryURL)
    {
        $.messager.alert('提示','请定义全局变量queryURL');
        return;
    }
		    	
    //数据删除地址必须指定
    if(!options.deleteURL)
    {
        $.messager.alert('提示','请定义全局变量deleteURL');
        return;
    }
		    	
    options.querywidth = (options.querywidth ? options.querywidth : 300);//查询窗口默认宽度
    options.queryheight = (options.queryheight ? options.queryheight : 200);//查询窗口默认高度
    options.editwidth = (options.editwidth ? options.editwidth : 350);//编辑窗口默认宽度
    options.editheight = (options.editheight ? options.editheight : 200);//编辑窗口默认高度
		    	
    options.tableid = (options.tableid ? options.tableid : '#tt');//数据显示表格的默认id
    options.querywindowid = (options.querywindowid ? options.querywindowid : '#query-window');//查询窗口的默认id
    options.editwindowid = (options.editwindowid ? options.editwindowid : '#edit-window');//编辑窗口的默认id
    options.iframeid = (options.iframeid ? options.iframeid : '#editifr');//编辑窗口的iframe默认id
    options.idfield = (options.idfield ? options.idfield : 'id');//数据显示表格的默认idfield字段
    options.queryformid = (options.queryformid ? options.queryformid : '#queryform');//查询表单的默认id
    options.title = (options.title ? options.title : '信息管理');
    globaoptions = options;
}      
	    	
//根据参数查询URL对应值
function getQueryStringRegExp(name)	{
    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
    if (reg.test(globaoptions.queryURL)) return unescape(RegExp.$2.replace(/\+/g, " "));
    return "";
//if (reg.test(options.url ? options.url : globaoptions.queryURL)) return unescape(RegExp.$2.replace(/\+/g, " ")); return "";
}