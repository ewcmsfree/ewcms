/**    
  *作者 ：Fantasy  
  *版本 ：V1.1     
  */     
function HashMap(){
     /* Map 大小 */     
     var size = 0;      
     /* 对象 */     
     var entry = new Object();                 
     /* 存 */     
     this.put = function (key , value)      
     {      
         if(!this.containsKey(key))      
         {      
             size ++ ;      
         }      
         entry[key] = value;      
     }      
           
     /* 取 */     
     this.get = function (key)      
     {      
         return this.containsKey(key) ? entry[key] : null;      
     }      
           
     /* 删除 */     
     this.remove = function ( key )      
     {      
         if( this.containsKey(key) && ( delete entry[key] ) )      
         {      
             size --;      
         }      
     }      
           
     /* 是否包含 Key */     
     this.containsKey = function ( key )      
     {      
         return (key in entry);      
     }      
           
     /* 是否包含 Value */     
     this.containsValue = function ( value )      
     {      
         for(var prop in entry)      
         {      
             if(entry[prop] == value)      
             {      
                 return true;      
             }      
         }      
         return false;      
     }      
           
     /* 所有 Value */     
     this.values = function ()      
     {      
         var values = new Array();      
         for(var prop in entry)      
         {      
             values.push(entry[prop]);      
         }      
         return values;      
     }
           
     /* 所有 Key */     
     this.keys = function ()      
     {      
         var keys = new Array();      
         for(var prop in entry)      
         {      
             keys.push(prop);      
         }      
         return keys;      
     }      
           
     /* Map Size */     
     this.size = function ()      
     {      
         return size;      
     }      
           
     /* 清空 */     
     this.clear = function ()      
     {      
         size = 0;      
         entry = new Object();      
     }      
 } 

function isExistID(elementID){
	if($(elementID).length == 0){
		$.messager.alert('提示',elementID + " 元素不存在");
		return false;		    	
	}
	return true;
}

/**
  * ewcms基础对象
  */
function EwcmsBase(){
    var toolMap = new HashMap();//工具拦HashMap对象
    var frozenMap = new HashMap();//表格冻结字段HashMap对象
    var queryURL,winWidth,winHeight,dgWidth,dgHeight;
    winWidth = 500;
    winHeight = 300;
    dgWidth = 500;
    dgHeight = 300;
	
    frozenMap.put("ck", "{field:'ck',checkbox:true,width:50}");
    toolMap.put("新增", "{text:'新增',iconCls:'icon-add',handler:'addCallBack'}");
    toolMap.put("修改", "{text:'修改',iconCls:'icon-edit',handler:'updCallBack'}");
    toolMap.put("删除", "{text:'删除',iconCls:'icon-remove',handler:'delCallBack'}");
    toolMap.put("查询", "{text:'查询',iconCls:'icon-search',handler:'queryCallBack'}");   
    toolMap.put("缺省查询", "{text:'缺省查询',iconCls:'icon-back',handler:'defQueryCallBack'}");
	this.setWinWidth = function(width){
		winWidth=width;
	}
	
	this.setWinHeight = function(height){
		winHeight=height;
	}    
    
	this.setDgWidth = function(width){
		dgWidth=width;
	}
	
	this.setDgHeight = function(height){
		dgHeight=height;
	}	
	
	this.setQueryURL = function(url){
		queryURL=url;
	}
	
	this.getQueryURL = function(){
		return queryURL;
	}
	
	/*ewcms打开窗口*/
	this.openWindow = function(windowID,options){
		if(!isExistID(windowID))return;
		//移除开始隐藏属性
		$(windowID).removeAttr("style");
		if(typeof(options) == 'undefined')options = {};
		$(windowID).window({
		   title: (options.title ? options.title : '窗口'),
		   width: (options.width ? options.width : winWidth),
		   height: (options.height ? options.height : winHeight),
		   left:(options.left ? options.left : ($(window).width() - (options.width ? options.width : winWidth))/2),
		   top:(options.top ? options.top : ($(window).height() - (options.height ? options.height : winHeight))/2),
		   modal: (options.modal ? options.modal : true),
		   maximizable:(options.maximizable ? options.maximizable : false),
		   minimizable:(options.minimizable ? options.minimizable : false)
		});
		if(options.iframeID){
			$(options.iframeID).attr('src',options.url);
		}
		$(windowID).window('open');
	}

	/*ewcms关闭窗口*/
	this.closeWindow = function(windowID){
		if(!isExistID(windowID))return;
		$(windowID).window('close');
	}
	
	/*ewcms数据显示表格定义*/
	this.openDataGrid = function(datagridID,options){
		if(typeof(options) == 'undefined' || !options.columns){
		    $.messager.alert('提示','请定义数据表格的columns属性');
		    return;
		}
		
		if(typeof(queryURL) == 'undefined' || queryURL==""){
		    $.messager.alert('提示','数据查询地址未指定');
		    return;
		}
		
		var frozenArr = eval("[["+frozenMap.values().join(',')+"]]");
		var toolArr = eval("["+toolMap.values().join(",'-',")+"]");
		$(datagridID).datagrid({
			title:(options.title ? options.title : ''),
		    iconCls:(options.iconCls ? options.iconCls : ''),
		    nowrap: (options.nowrap ? options.nowrap : false),
		    width:(options.width ? options.width : dgWidth),
		    height:(options.height ? options.height : dgHeight),
		    nowrap:(options.nowrap ? options.nowrap : true),
		    pagination:(options.pagination ? options.pagination : true),
		    rownumbers:(options.rownumbers ? options.rownumbers : true),
		    singleSelect:(options.singleSelect ? options.singleSelect : false),
		    striped: (options.striped ? options.striped : true),
		    url:(options.url ? options.url : queryURL),
		    idField:(options.idField ? options.idField : 'id'),
		    frozenColumns: frozenArr,
		    columns:options.columns,
		    toolbar:toolArr,
		    onLoadSuccess:function(data){
		    	if(data.errors)$.messager.alert('提示',data.errors.join('\n'));
		    }
		});
	}
	
	this.addToolItem = function(text,icon,handler,id){
		if(typeof(id) == 'undefined'){
			toolMap.put(text, "{text:'"+text+"',iconCls:'"+icon+"',handler:"+handler+"}");
		}else{
			toolMap.put(text, "{id:'"+id + "',text:'"+text+"',iconCls:'"+icon+"',handler:"+handler+"}");
		}
	}
	
	this.delToolItem = function(text){
		toolMap.remove(text);
	}
	
	this.addFrozenItem = function(field,title,width){
		frozenMap.put(text, "{field:'"+field+"',title:'"+title+"',width:"+width+"}");
	}
}

/**
  * ewcms操作对象
  */
function EwcmsOperate(){
	var queryURL,inputURL,deleteURL,datagridID,editWinID,queryWinID;  
    datagridID = '#tt';
    editWinID = '#edit-window';
    queryWinID = '#query-window';
    
	this.setQueryURL = function(url){
		queryURL = url;
	}
	
	this.setInputURL = function(url){
		inputURL = url;
	}
	
	this.setDeleteURL = function(url){
		deleteURL = url;
	}
	
	this.setEditWinID = function(winID){
		editWinID = winID;
	}
	this.setQueryWinID= function(winID){
		queryWinID = winID;
	}
	
	this.setDatagridID = function(dgID){
		datagridID = dgID;
	}
	
	function isExistVAR(varName,describe){
		if(typeof(varName) == 'undefined' || varName==""){
		    $.messager.alert('提示',describe);
		    return false;
		}
		return true;
	}	
	
	/*根据查询条件查询记录*/ 
	this.querySearch = function(formID){
		if(typeof(formID) == 'undefined' || formID == '')formID = '#queryform';
	    var value = $(formID).serialize();
	    value = "parameters['" + value;
	    value = value.replace(/\=/g,"']=");
	    value = value.replace(/\&/g,"&parameters['");
	    if(!isExistVAR('queryURL','查询操作地址未指定'))return;
	    var url = queryURL;
	    var index = url.indexOf("?");
	    if (index == -1){
	        url = url + '?' + value;
	    }else{
	        url = url + '&' + value;
	    }
	    if(!isExistID(datagridID))return;
	    $(datagridID).datagrid({
	        pageNumber:1,
	        url:url
	    });
	    //if(!isExistID(queryWinID))return;
	    $(queryWinID).window('close');
	}
	
	/*刷新修改后的记录*/ 
	this.queryReload = function(hide){
	    $(datagridID).datagrid('reload');
	    if(hide){
	        $(editWinID).window('close');
	        $(datagridID).datagrid('clearSelections');
	    }
	}
	            
	/*查询指定的id号记录*/ 
	this.queryNews = function(ids){
		if(!isExistVAR('queryURL','查询操作地址未指定'))return;
		var url = queryURL;          	
	    var index = url.indexOf("?");
	    if (index == -1){
	        url = url + '?';
	    }else{
	        url = url + "&";
	    }
	                
	    for (var i =0 ; i < ids.length ; ++i){
	        url += 'selections=' + ids[i] + '&';
	    }
	    if(!isExistID(datagridID))return;
	    $(datagridID).datagrid({
	        'url':url
	    });
	}
				
	/*表单提交操作*/    
	this.saveOperator = function(iframeID){
	    if(typeof(iframeID) == 'undefined'|| iframeID == '')iframeID = 'editifr';
	    window.frames[iframeID].document.forms[0].submit();
	}
	            
	/*添加操作*/ 
	this.addOperateBack = function(iframeID){
		if(typeof(iframeID) == 'undefined'|| iframeID == '')iframeID = '#editifr';
		if(!isExistVAR('inputURL','编辑操作页面地址未指定'))return;
		if(!isExistID(iframeID))return;
	    if(!isExistID(editWinID))return;
	    openWindow(editWinID,{url:inputURL,iframeID:iframeID});
	}
		    	
	/*修改操作*/ 
	this.updOperateBack = function(iframeID,options){
		if(typeof(iframeID) == 'undefined'|| iframeID == '')iframeID = '#editifr';
		if(!isExistID(datagridID))return;
		if(!isExistID(iframeID))return;
		if(!isExistID(editWinID))return;		
	    var rows = $(datagridID).datagrid('getSelections');
	    if(rows.length == 0){
	        $.messager.alert('提示','请选择修改记录','info');
	        return;
	    }
	    if(!isExistVAR('inputURL','编辑操作页面地址未指定'))return;
	    var url = inputURL;            
	    var index = url.indexOf("?");
	    if (index == -1){
	        url = url + '?';
	    }else{
	        url = url + "&";
	    }

	    callBackId=function(row){
	        return row.id;
	    }
	    if(typeof(options) == 'undefined')options = {};	    
	    if(options.callBackId){
	        callBackId = options.callBackId;
	    }
	    for(var i=0;i<rows.length;++i){
	        url += 'selections=' + callBackId(rows[i]) +'&';
	    }
	    openWindow(editWinID,{url:url,iframeID:iframeID});
	}
		    	
	/*删除操作*/ 
	this.delOperateBack = function(){
		if(!isExistID(datagridID))return;
		if(!isExistVAR('deleteURL','删除操作页面地址未指定'))return;
	    var rows = $(datagridID).datagrid('getSelections');
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
	            $.post(deleteURL,ids,function(data){          	
	            	$.messager.alert('成功','删除成功','info');
	            	$(datagridID).datagrid('clearSelections');
	                $(datagridID).datagrid('reload');              	
	            });
	        }
	    });
	}
		    	
	/*查询操作*/ 
	this.queryOperateBack = function(){
		if(!isExistID(queryWinID))return;
	    openWindow(queryWinID);
	}
		    	
	/*初始查询操作*/ 
	this.initOperateQueryBack = function(){
		if(!isExistID(datagridID))return;
		if(!isExistVAR('queryURL','查询操作地址未指定'))return;
	    $(datagridID).datagrid({
	        pageNumber:1,
	        url:queryURL
	    });    	
	}
	
	//根据参数查询URL对应值
	this.getQueryStringRegExp = function(name){
		if(!isExistVAR('queryURL','查询操作地址未指定'))return;
	    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
	    if (reg.test(queryURL)) return unescape(RegExp.$2.replace(/\+/g, " "));
	    return "";
	}	
}