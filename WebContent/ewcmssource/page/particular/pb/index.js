var PbIndex = function(urls){
	this._urls = urls;
}

PbIndex.prototype.init = function(options){
	var urls = this._urls;
	var datagridId = options.datagridId;
	
    ewcmsBOBJ = new EwcmsBase();
    ewcmsBOBJ.setQueryURL(urls.queryUrl);
    
    ewcmsBOBJ.setWinWidth(1050);
	ewcmsBOBJ.setWinHeight(600);
	
	ewcmsBOBJ.addToolItem('导入XML', 'icon-redo', null, 'importToolbar');
	ewcmsBOBJ.addToolItem('生成XML', 'icon-undo', null, 'generatorToolbar');
	ewcmsBOBJ.addToolItem('发布','icon-publish', null, 'pubToolbar');
	ewcmsBOBJ.addToolItem('取消发布','icon-publish', null, 'unPubToolbar');
	
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    ewcmsOOBJ.setInputURL(urls.inputUrl);
    ewcmsOOBJ.setDeleteURL(urls.deleteUrl);

    ewcmsBOBJ.openDataGrid(datagridId,{
        columns:[[
                {field:'id',title:'编号',hidden:true},
                {field:'release',title:'发布',width:40,
                	formatter : function(val, rec){
                		return val ? '&nbsp;是' : '&nbsp;否';
                	}
                },
				{field:'code',title:'项目编号',width:150,sortable:true},
                {field:'name',title:'项目名称',width:200},
                {field:'buildTime',title:'建设时间',width:85},
                {field:'investmentScale',title:'投资规模',width:200},
                {field:'overview',title:'项目概况',width:300},
                {field:'buildUnit',title:'建设单位',width:200},
                {field:'unitId',title:'项目编号',width:60},
                {field:'unitPhone',title:'单位联系电话',width:100},
                {field:'unitAddress',title:'单位地址',width:200},
                {field:'zoningName',title:'行政区划名称',width:80,
                	formatter : function(val, rec) {
                		if (rec.zoningCode != null){
                			return rec.zoningCode.name;
                		}
                	}
                },
                {field:'organizationCode',title:'组织机构代码',width:80},
                {field:'industryName',title:'行业名称',width:80,
                	formatter : function(val, rec){
                		if (rec.industryCode != null){
                			return rec.industryCode.name;
                		}
                	}	
                },
                {field:'category', title:'项目类别', width: 120},
                {field:'approvalRecordName',title:'审批备案机关名称',width:120,
                	formatter : function(val, rec){
                		if (rec.approvalRecord != null){
                			return rec.approvalRecord.name;
                		}
                	}	
                },
                {field:'contact',title:'联系人',width:120},
                {field:'phone',title:'联系人电话',width:100},
                {field:'email',title:'联系人电子邮箱',width:120},
                {field:'address',title:'项目地址',width:200},
                {field:'natureDescription',title:'建设性质',width:100},
                {field:'shapeDescription',title:'形式',width:100},
                {field:'documentId',title:'文号',width:100},
                {field:'participation',title:'参建单位',width:200},
                {field:'publishingSectorName',title:'发布部门名称',width:200,
                	formatter : function(val, rec){
                		if (rec.publishingSector != null){
                			return rec.publishingSector.name;
                		}
                	}	
                }
          ]]
	});
    
    $('#importToolbar').bind('click',function(){
		ewcmsBOBJ.openWindow('#import-window',{title:'导入XML',url:urls.importUrl, iframeID:'#importifr',width:560,height:130});
    });
    
    $('#generatorToolbar').bind('click',function(){
    	var rows = $(datagridId).datagrid('getSelections');
        if(rows.length == 0){
         	$.messager.alert('提示','请选择导出记录','info');
            return;
        }
        var parameter = '?selections=' + rows[0].id;
    	for ( var i = 1; i < rows.length; i++) {
    		parameter = parameter + '&selections=' + rows[i].id;
    	}
    	window.location = urls.generatorUrl + parameter;
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
				return;
    		}
    		$(datagridId).datagrid('reload');
    	});
    });
    
    $('#unPubToolbar').bind('click', function(){
    	var rows = $(datagridId).datagrid('getSelections');
        if(rows.length == 0){
         	$.messager.alert('提示','请选择取消发布记录','info');
            return;
        }
        var parameter = 'selections=' + rows[0].id;
    	for ( var i = 1; i < rows.length; i++) {
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
				return;
    		}
    		$(datagridId).datagrid('reload');
    	});
    });
}
