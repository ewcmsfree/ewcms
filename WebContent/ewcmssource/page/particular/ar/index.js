var ArIndex = function(urls){
	this._urls = urls;
}

ArIndex.prototype.init = function(options){
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
                {field:'id',title:'编号',hidden:true},
				{field:'code',title:'组织机构代码',width:90,sortable:true},
                {field:'name',title:'机关单位名称',width:1000}
          ]]
	});
}