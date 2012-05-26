var MbIndex = function(urls){
	this._urls = urls;
}

MbIndex.prototype.init = function(options){
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
				{field:'name',title:'姓名',width:150,sortable:true},
                {field:'sexDescription',title:'性别',width:60},
                {field:'publishingSector_name',title:'发布部门',width:200,
					formatter : function(val, rec){
						return rec.publishingSector.name;
					}	
                },
                {field:'published',title:'发布时间',width:145},
                {field:'cardTypeDescription',title:'证件类型',width:200},
                {field:'cardCode',title:'证件号码',width:150},
                {field:'denseDescription',title:'所属密级',width:100}
          ]]
	});
}