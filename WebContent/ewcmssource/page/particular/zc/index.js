var ZcIndex = function(urls){
	this._urls = urls;
}

ZcIndex.prototype.init = function(options){
	var urls = this._urls;
	var datagridId = options.datagridId;
	
    ewcmsBOBJ = new EwcmsBase();
    ewcmsBOBJ.setQueryURL(urls.queryUrl);
    
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    ewcmsOOBJ.setInputURL(urls.inputUrl);
    ewcmsOOBJ.setDeleteURL(urls.deleteUrl);
    
    ewcmsBOBJ.openDataGrid(datagridId ,{
        columns:[[
                {field:'id',title:'编号',hidden:true},
				{field:'code',title:'行政区划编码',width:90,sortable:true},
                {field:'name',title:'行政区划名称',width:1000}
          ]]
	});
}