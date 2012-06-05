var EbIndex = function(urls){
	this._urls = urls;
}

EbIndex.prototype.init = function(options){
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
                {field:'release',title:'发布',width:40,
                	formatter : function(val, rec){
                		return val ? '&nbsp;是' : '&nbsp;否';
                	}
                },
				{field:'yyzzzch',title:'营业执照注册号',width:150,sortable:true},
                {field:'name',title:'企业名称',width:200},
                {field:'published',title:'发布时间',width:85},
                {field:'yyzzdjjg',title:'营业执照登记机关',width:200},
                {field:'frdb',title:'法人代表',width:300},
                {field:'clrq',title:'成立日期',width:200},
                {field:'jyfw',title:'经营范围',width:60},
                {field:'zzjgdjjg',title:'组织机构登记机关',width:100},
                {field:'zzjgdm',title:'组织机构代码',width:200},
                {field:'qyrx',title:'企业类型',width:80,},
                {field:'zzzb',title:'注册资本',width:80},
                {field:'sjzzzb',title:'实缴注册资本',width:80},
                {field:'jyqx',title:'经营期限',width:120},
                {field:'zs',title:'住所',width:120},
                {field:'denseDescription',title:'所属密级',width:100}
                {field:'organ_name',title:'发布部门',width:200,
					formatter : function(val, rec){
						return (rec.organ == null) ? "" : rec.organ.name;
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
	    		$(datagridId).datagrid('clearSelections');
	    		$(datagridId).datagrid('reload');
    		}
    	});
    });
}