var LogsIndex = function(urls){
	this._urls = urls;
}

LogsIndex.prototype.init = function(options){
	var urls = this._urls;
	var datagridId = options.datagridId;
	
    ewcmsBOBJ = new EwcmsBase();
    
	ewcmsBOBJ.delToolItem('新增');
	ewcmsBOBJ.delToolItem('修改');
	ewcmsBOBJ.delToolItem('删除');
	
    ewcmsBOBJ.setQueryURL(urls.queryUrl);
    
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    
    ewcmsBOBJ.openDataGrid(datagridId,{
        columns:[[
				{field:'id',title:'编号',hidden:true},
                {field:'userName',title:'用户名',width:100},
                {field:'lastUsed',title:'登录时间',width:200},
                {field:'ipAddress',title:'IP地址',width:200}
          ]]
	});
}