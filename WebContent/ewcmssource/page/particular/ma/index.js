var MaIndex = function(urls){
	this._urls = urls;
}

MaIndex.prototype.init = function(options){
	var urls = this._urls;
	var datagridId = options.datagridId;
	
    ewcmsBOBJ = new EwcmsBase();
    ewcmsBOBJ.setQueryURL(urls.queryUrl);
    
	ewcmsBOBJ.addToolItem('发布','icon-publish', null, 'pubToolbar');
	ewcmsBOBJ.addToolItem('取消发布','icon-publish', null, 'unPubToolbar');
    
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    ewcmsOOBJ.setInputURL(urls.inputUrl);
    ewcmsOOBJ.setDeleteURL(urls.deleteUrl);
        
	ewcmsBOBJ.setWinWidth(1050);
	ewcmsBOBJ.setWinHeight(600);
	
	ewcmsBOBJ.openDataGrid(datagridId ,{
        columns:[[
                {field:'id',title:'编号',hidden:true},
				{field:'employeBasic_cardCode',title:'证件号码',width:150,sortable:true,
                	formatter : function(val, rec){
                		return (rec.employeBasic == null) ? "" : rec.employeBasic.cardCode;
                	}	
				},
				{field:'employeBasic_name',title:'姓名',width:150,
					formatter : function(val, rec){
						return (rec.employeBasic == null) ? "" : rec.employeBasic.name;
					}	
				},
                {field:'organ_name',title:'发布部门',width:200,
					formatter : function(val, rec){
						return (rec.organ == null) ? "" : rec.organ.name;
					}	
                },
                {field:'published',title:'发布日期',width:145},
                {field:'denseDescription',title:'所属密级', width:100},
                {field:'release',title:'发布',width:40,
                	formatter : function(val, rec){
                		return val ? '&nbsp;是' : '&nbsp;否';
                	}
                }
          ]]
	});
	
    $('#pubToolbar').bind('click', function(){
    	var rows = $(datagridId).datagrid('getSelections');
        if(rows.length == 0){
         	$.messager.alert('提示','请选择发布记录','info');
            return;
        }
        var parameter = 'selections=' + rows[0].id;
    	for ( var i = 1; i < rows.length; i++) {
    		parameter = parameter + '&selections=' + rows[i].id;
    	}
    	$.post(urls.pubUrl, parameter, function(data){
    		if (data == 'false'){
    			$.messager.alert('提示','发布失败','info');
    			return;
    		} else if (data == 'accessdenied') {
				$.messager.alert('提示', '没有发布权限', 'info');
				return;
    		}else if (data == 'true'){
				$.messager.alert('提示', '发布成功', 'info');
	    		$(datagridId).datagrid('clearSelections');
	    		$(datagridId).datagrid('reload');
    		}
    	});
    });
    
    $('#unPubToolbar').bind('click', function(){
    	var rows = $(datagridId).datagrid('getSelections');
        if(rows.length == 0){
         	$.messager.alert('提示','请选择取消发布记录','info');
            return;
        }
        var parameter = 'selections=' + rows[0].id;
    	for ( var i = 1; i < rows.length ; i++) {
    		parameter = parameter + '&selections=' + rows[i].id;
    	}
    	$.post(urls.unPubUrl, parameter, function(data){
    		if (data == 'false'){
    			$.messager.alert('提示','取消发布失败','info');
    			return;
    		} else if (data == 'accessdenied') {
				$.messager.alert('提示', '没有取消发布权限', 'info');
				return;
    		}else if (data == 'true'){
				$.messager.alert('提示', '取消发布成功', 'info');
	    		$(datagridId).datagrid('clearSelections');
	    		$(datagridId).datagrid('reload');
    		}
    	});
    });
}