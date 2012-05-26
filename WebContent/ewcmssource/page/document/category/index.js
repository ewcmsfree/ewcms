var CategoryIndex = function(urls){
	this._urls = urls;
}

CategoryIndex.prototype.init = function(options){
	var urls = this._urls;
	var datagridId = options.datagridId;
	
    ewcmsBOBJ = new EwcmsBase();
    ewcmsBOBJ.setQueryURL(urls.queryUrl);
    
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    ewcmsOOBJ.setInputURL(urls.inputUrl);
    ewcmsOOBJ.setDeleteURL(urls.deleteUrl);
    
    ewcmsBOBJ.openDataGrid(datagridId,{
        columns:[[
				{field:'id',title:'序号',width:50,sortable:true},
                {field:'categoryName',title:'名称',width:200}
          ]]
	});
}