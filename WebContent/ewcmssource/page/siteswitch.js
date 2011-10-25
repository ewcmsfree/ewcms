var SiteSwitch = function(urls){
    this._urls = urls;
}

SiteSwitch.prototype.init = function(){
    var urls = this._urls;
    
    $("#tt").datagrid({
       height:180,
       fitColumns:true,
       nowrap: false,
       singleSelect:true,
       rownumbers:true,
       pagination:true,
       idField:'id',
       pageSize:5,
       pageList:[5],
       url: urls.queryUrl,
       loadMsg:'',
       columns:[[
           {field:'ck',checkbox:true},
           {field:'siteName',title:'站点名称',width:300}
       ]],
       onLoadSuccess:function(data){
           var siteId = $('input[name=siteId]').val();
           $(this).datagrid('selectRecord',siteId);
       }
   });
   $('#tt').datagrid("getPager").pagination({
       displayMsg:'共有 {total} 站点',
       showPageList:false
   });    
}

SiteSwitch.prototype.switchSite=function(){
    var site = $('#tt').datagrid("getSelected");
    if(!site){
        $.messager.alert('提示','请选择切换的站点','info');
    }
    top.location = this._urls.switchUrl+'?siteId='+site.id;
}