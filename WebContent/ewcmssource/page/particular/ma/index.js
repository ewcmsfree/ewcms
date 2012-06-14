$(function(){
    ewcmsBOBJ = new EwcmsBase();
    ewcmsBOBJ.setQueryURL(queryUrl);
    
	ewcmsBOBJ.delToolItem('新增');
	ewcmsBOBJ.delToolItem('修改');
	ewcmsBOBJ.delToolItem('删除');
	ewcmsBOBJ.delToolItem('查询');
	ewcmsBOBJ.delToolItem('缺省查询');
    
	ewcmsBOBJ.addToolItem('新增','icon-add', addOperate, 'addToolbar');
	ewcmsBOBJ.addToolItem('修改','icon-edit', editOperate, 'editToolbar');
	ewcmsBOBJ.addToolItem('删除', 'icon-remove', removeOperate, 'removeToolbar');
	ewcmsBOBJ.addToolItem('查询', 'icon-search', queryOperate,'queryToolbar');
	ewcmsBOBJ.addToolItem('缺省查询','icon-back', defQueryOperate, 'defQueryToolbar');
	ewcmsBOBJ.addToolItem('发布','icon-publish', pubOperate, 'pubToolbar');
	ewcmsBOBJ.addToolItem('取消发布','icon-publish', unPubOperate, 'unPubToolbar');
    
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(queryUrl);
    ewcmsOOBJ.setInputURL(inputUrl);
    ewcmsOOBJ.setDeleteURL(deleteUrl);
        
	ewcmsBOBJ.openDataGrid(datagridId ,{
        columns:[[
                {field:'id',title:'编号',hidden:true},
                {field:'release',title:'发布',width:40,
                	formatter : function(val, rec){
                		return val ? '&nbsp;是' : '&nbsp;否';
                	}
                },
                {field:'organ_name',title:'发布部门',width:200,
					formatter : function(val, rec){
						return (rec.organ == null) ? "" : rec.organ.name;
					}	
                },
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
                {field:'published',title:'发布日期',width:145},
                {field:'denseDescription',title:'所属密级', width:100}
          ]]
	});
});
	
function addOperate(){
	openWindow1({title:'新增从业人员文章信息', url:inputUrl, width:930, height:633});
}
function editOperate(){
	var rows = $(datagridId).datagrid('getSelections');
    if(rows.length == 0){
        $.messager.alert('提示','请选择修改记录','info');
        return;
    }
    var url = inputUrl;            
    var index = url.indexOf("?");
    if (index == -1){
        url = url + '?';
    }else{
        url = url + "&";
    }
    callBackId = function(row){
        return row.id;
    }
    if(typeof(options) == 'undefined')options = {};	    
    if(options.callBackId){
        callBackId = options.callBackId;
    }
    for(var i=0;i<rows.length;++i){
        url += 'selections=' + callBackId(rows[i]) +'&';
    }
    ewcmsBOBJ.openWindow1({title:'修改从业人员文章信息', url:url, width:930, height:633})
}

function removeOperate(){
	ewcmsOOBJ.delOperateBack();
}

function queryOperate(){
	ewcmsBOBJ.openWindow(queryWinID);
}

function defQueryOperate(){
	ewcmsOOBJ.initOperateQueryBack();
}
function pubOperate(){
	var rows = $(datagridId).datagrid('getSelections');
    if(rows.length == 0){
     	$.messager.alert('提示','请选择发布记录','info');
        return;
    }
    var parameter = 'selections=' + rows[0].id;
	for ( var i = 1; i < rows.length; i++) {
		parameter = parameter + '&selections=' + rows[i].id;
	}
	$.post(pubUrl, parameter, function(data){
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
}
 
function unPubOperate(){
	var rows = $(datagridId).datagrid('getSelections');
    if(rows.length == 0){
     	$.messager.alert('提示','请选择取消发布记录','info');
        return;
    }
    var parameter = 'selections=' + rows[0].id;
	for ( var i = 1; i < rows.length; i++) {
		parameter = parameter + '&selections=' + rows[i].id;
	}
	$.post(unPubUrl, parameter, function(data){
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
}