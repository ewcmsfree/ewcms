var EaIndex = function(urls){
	this._urls = urls;
}

EaIndex.prototype.init = function(options){
	var urls = this._urls;
	var datagridId = options.datagridId;
	
    ewcmsBOBJ = new EwcmsBase();
    ewcmsBOBJ.setQueryURL(urls.queryUrl);
    
    ewcmsOOBJ = new EwcmsOperate();
    ewcmsOOBJ.setQueryURL(urls.queryUrl);
    ewcmsOOBJ.setInputURL(urls.inputUrl);
    ewcmsOOBJ.setDeleteURL(urls.deleteUrl);
    
	ewcmsBOBJ.setWinWidth(1050);
	ewcmsBOBJ.setWinHeight(600);
	
	ewcmsBOBJ.openDataGrid(datagridId ,{
        columns:[[
                {field:'id',title:'编号',hidden:true},
				{field:'enterpriseBasic_yyzzzch',title:'营业执照注册号',width:150,sortable:true,
                	formatter : function(val, rec){
                		return rec.enterpriseBasic.yyzzzch;
                	}	
				},
				{field:'enterpriseBasic_name',title:'企业名称',width:200,
					formatter : function(val, rec){
						return rec.enterpriseBasic.name;
					}	
				},
                {field:'publishingSector_name',title:'发布部门',width:200,
					formatter : function(val, rec){
						return rec.publishingSector.name;
					}	
                },
                {field:'published',title:'发布日期',width:145},
                {field:'denseDescription',title:'所属密级', width:100}
          ]]
	});
}