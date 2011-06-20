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
 
/**
  * iframe自适应大小调节
  */  
function iframeFitHeight(oIframe){
     newHeight = oIframe.contentWindow.document.body.scrollHeight;
     newWidth = oIframe.contentWindow.document.body.scrollWidth;
     if(newHeight < 80)newHeight = 80;
     if(newWidth < 150)newWidth = 150;
     oIframe.width = newWidth;
     oIframe.height = newHeight;
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
function EwcmsBase(options){
    var toolMap = new HashMap();//工具拦HashMap对象
    var frozenMap = new HashMap();//表格冻结字段HashMap对象
    var queryURL,winWidth,winHeight,dgWidth,dgHeight;
    if(typeof(options) != 'undefined'){
    	queryURL = options.queryURL ? options.queryURL : "";
    	winWidth = options.winWidth ? options.winWidth : 500;
    	winHeight = options.winHeight ? options.winHeight : 300;
    	dgWidth = options.dgWidth ? options.dgWidth : 500;
    	dgHeight = options.dgHeight ? options.dgHeight : 300;
    }
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
		   title: (options.title ? options.title : ''),
		   width: (options.width ? options.width : winWidth),
		   height: (options.height ? options.height : winHeight),
		   left:(options.left ? options.left : ($(window).width() - options.width)/2),
		   top:(options.top ? options.top : ($(window).height() - options.height)/2),
		   modal: (options.modal ? options.modal : true),
		   maximizable:(options.maximizable ? options.maximizable : false),
		   minimizable:(options.minimizable ? options.minimizable : false)
		});
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
		    idField:(options.idField ? options.idField : ''),
		    frozenColumns:(options.frozenColumns ? options.frozenColumns : [[frozenMap.values().join(",")]]),
		    columns:baseOptions.columns,
		    toolbar:(options.toolbar ? options.toolbar : [toolMap.values().join(",'-',")]),
		    onLoadSuccess:function(data){
		    	if(data.errors)$.messager.alert('提示',data.errors.join('\n'));
		    }
		});
	}
	
	this.addToolItem = function(text,icon,handler){
		toolMap.put(text, "{text:'"+text+"',iconCls:'"+icon+",handler:"+handler+"}");
	}
	
	this.delToolItem = function(text){
		toolMap.remove(text);
	}
	
	this.addFrozenItem = function(field,title,width){
		frozenMap.put(text, "{field:'"+field+"',title:'"+title+",width:"+width+"}");
	}
	
	this.initToolBar = function(){
		//初试化默认工具栏
	    addToolItem('新增','icon-add','addOperateBack');	
	    addToolItem('修改','icon-edit','updOperateBack');
	    addToolItem('删除','icon-remove','delOperateBack');
	    addToolItem('查询','icon-search','queryOperateBack');
	    addToolItem('缺省查询','icon-back','initOperateQueryBack');
	    //初试化 冻结字段
	    frozenMap.put("ck", "{field:'ck',checkbox:true,width:50}");		
	}
}

/**
 * ewcms操作对象
 */
function EwcmsOperate(ewcmsBaseObj,options){
	var queryURL,inputURL,deleteURL,datagridID,windowID;
	var ebOBJ = ewcmsBaseObj;
    if(typeof(ewcmsBaseObj) != 'undefined'){
    	queryURL = ewcmsBaseObj.getQueryURL() ? ewcmsBaseObj.getQueryURL() : "";
    }
    
    if(typeof(options) != 'undefined'){
    	queryURL = options.queryURL ? options.queryURL : "";
    	winWidth = options.winWidth ? options.winWidth : 500;
    	winHeight = options.winHeight ? options.winHeight : 300;
    	dgWidth = options.dgWidth ? options.dgWidth : 500;
    	dgHeight = options.dgHeight ? options.dgHeight : 300;
    }    
    
	this.setQueryURL = function(url){
		queryURL = url;
	}
	
	this.setInputURL = function(url){
		inputURL = url;
	}
	
	this.setDeleteURL = function(url){
		deleteURL = url;
	}
	
	this.setWindowID = function(winID){
		windowID = winID;
	}
	
	this.setDatagridID = function(dgID){
		datagridID = dgID;
	}

	this.setEbOBJ = function(ewcmsBaseObj){
		ebOBJ = ewcmsBaseObj;
		queryURL = ewcmsBaseObj.getQueryURL() ? ewcmsBaseObj.getQueryURL() : queryURL;
	}
	
	/*根据查询条件查询记录*/ 
	this.querySearch = function(formID){
		if(typeof(formID) == 'undefined')formID = '#queryform';
	    var value = $(formID).serialize();
	    value = "parameters['" + value;
	    value = value.replace(/\=/g,"']=");
	    value = value.replace(/\&/g,"&parameters['");
	    
	    var url = queryURL;
	    var index = url.indexOf("?");
	    if (index == -1){
	        url = url + '?' + value;
	    }else{
	        url = url + '&' + value;
	    }
	    $(datagridID).datagrid({
	        pageNumber:1,
	        url:url
	    });
	    $(windowID).window('close');
	}
	
	/*刷新修改后的记录*/ 
	this.queryReload = function(hide){
	    $(datagridID).datagrid('reload');
	    if(hide){
	        $(windowID).window('close');
	        $(datagridID).datagrid('clearSelections');
	    }
	}
	            
	/*查询指定的id号记录*/ 
	this.queryNews = function(ids){
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
	    $(datagridID).datagrid({
	        'url':url
	    });
	}
				
	/*表单提交操作*/    
	this.saveOperator = function(iframeID){
	    if(typeof(iframeID) == 'undefined')iframeID = 'editifr';
	    window.frames[iframeID].document.forms[0].submit();
	}
	            
	/*添加操作*/ 
	this.addOperateBack = function(iframeID){
		if(typeof(iframeID) == 'undefined')iframeID = '#editifr';
	    $(iframeid).attr('src',inputURL);
	    $(windowID).window('open');
	}
		    	
	/*修改操作*/ 
	this.updOperateBack = function(iframeID){
		if(typeof(iframeID) == 'undefined')iframeID = '#editifr';
	    var rows = $(tableid).datagrid('getSelections');
	    if(rows.length == 0){
	        $.messager.alert('提示','请选择修改记录','info');
	        return;
	    }
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
	    if(options.callBackId){
	        callBackId = options.callBackId;
	    }
	    for(var i=0;i<rows.length;++i){
	        url += 'selections=' + callBackId(rows[i]) +'&';
	    }
	    $(iframeID).attr('src',url);
	    $(windowID).window('open');
	}
		    	
	/*删除操作*/ 
	this.delOperateBack = function(){        	  	
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
	            $.post(url,ids,function(data){          	
	            	$.messager.alert('成功','删除成功','info');
	            	$(datagridID).datagrid('clearSelections');
	                $(datagridID).datagrid('reload');              	
	            });
	        }
	    });
	}
		    	
	/*查询操作*/ 
	this.queryOperateBack = function(){
	    $(windowID).window('open');
	}
		    	
	/*初始查询操作*/ 
	this.initOperateQueryBack = function(){
	    $(datagridID).datagrid({
	        pageNumber:1,
	        url:queryURL
	    });    	
	}
	
	//根据参数查询URL对应值
	this.getQueryStringRegExp = function(name){
	    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
	    if (reg.test(queryURL)) return unescape(RegExp.$2.replace(/\+/g, " "));
	    return "";
	}	
}