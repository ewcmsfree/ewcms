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
	ewcmsBOBJ.addToolItem('导入XML', 'icon-redo', importOperate, 'importToolbar');
	ewcmsBOBJ.addToolItem('生成XML', 'icon-undo', generatorOperate, 'generatorToolbar');
	ewcmsBOBJ.addToolItem('发布','icon-publish', pubOperate, 'pubToolbar');
	ewcmsBOBJ.addToolItem('取消发布','icon-publish', unPubOperate, 'unPubToolbar');
	
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(queryUrl);
    ewcmsOOBJ.setInputURL(inputUrl);
    ewcmsOOBJ.setDeleteURL(deleteUrl);

    ewcmsBOBJ.openDataGrid(datagridId,{
        columns:[[
                {field:'id',title:'编号',hidden:true},
                {field:'release',title:'发布',width:40,
                	formatter : function(val, rec){
                		return val ? '&nbsp;是' : '&nbsp;否';
                	}
                },
                {field:'organ_name',title:'发布部门',width:200,
					formatter : function(val, rec){
						return (rec.organ == null)? "" : rec.organ.name;
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
                		return (rec.zoningCode == null) ? "" : rec.zoningCode.name;
                	}
                },
                {field:'organizationCode',title:'组织机构代码',width:80},
                {field:'industryName',title:'行业名称',width:80,
                	formatter : function(val, rec){
               			return (rec.industryCode == null) ? "" : rec.industryCode.name;
                	}	
                },
                {field:'category', title:'项目类别', width: 120},
                {field:'approvalRecordName',title:'审批备案机关名称',width:120,
                	formatter : function(val, rec){
               			return (rec.approvalRecord == null) ? "" : rec.approvalRecord.name;
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
                {field:'published',title:'发布日期',width:145},
          ]]
	});
});

function addOperate(){
	openWindow1({title:'新增项目基本信息', url:inputUrl, width:900, height:633});
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
    ewcmsBOBJ.openWindow1({title:'修改项目基本信息', url:url, width:900, height:633})
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

function importOperate(){
	ewcmsBOBJ.openWindow('#import-window',{title:'导入XML',url:importUrl, iframeID:'#importifr',width:560,height:130});
}

function generatorOperate(){
	var rows = $(datagridId).datagrid('getSelections');
    if(rows.length == 0){
     	$.messager.alert('提示','请选择导出记录','info');
        return;
    }
    var parameter = '?selections=' + rows[0].id;
	for ( var i = 1; i < rows.length; i++) {
		parameter = parameter + '&selections=' + rows[i].id;
	}
	window.location = generatorUrl + parameter;
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