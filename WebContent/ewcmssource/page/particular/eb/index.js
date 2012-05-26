var EbIndex = function(urls){
	this._urls = urls;
}

EbIndex.prototype.init = function(options){
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
          ]]
	});
}