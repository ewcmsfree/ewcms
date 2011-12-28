var queryURL,inputURL,deleteURL,upURL,downURL;

$(function(){
	ewcmsBOBJ = new EwcmsBase();
	ewcmsBOBJ.setQueryURL(queryURL);
	
	$('#tt').treegrid({
		pagination:true,
		animate:true,
		collapsible:true,
		nowrap:true,
		rownumbers:true,
		url : queryURL,
		idField:'id',
		treeField:'regex',
		columns:[[
		    {field:'ck',checkbox:true,width:50},
		    {field:'regex', title:'表达式', width:600},
			{field:'id', title:'编号', width:60,hidden:true}
		]],
		toolbar:[
			{id:'btnAdd',text:'新增',iconCls:'icon-add',handler:addOperate},'-',
			{id:'btnUpd',text:'修改',iconCls:'icon-edit',handler:updCallBack},'-',
			{id:'btnRemove',text:'删除',iconCls:'icon-remove', handler:delOperate},'-',
			{id:'btnUp',text:'上移',iconCls:'icon-up',handler:upOperate},'-',
			{id:'btnDown',text:'下移',iconCls:'icon-down',handler:downOperate},'-',
			{id:'btnSearch',text:'查询',iconCls:'icon-search', handler:queryOperate},'-',
			{id:'btnBack',text:'缺省查询',iconCls:'icon-back', handler:initQueryOperate},'-',
		]
	});
	
	ewcmsOOBJ = new EwcmsOperate();
	ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
	
	ewcmsOOBJ.setInputURL(inputURL);
	ewcmsOOBJ.setDeleteURL(deleteURL);
});
function addOperate(){
	 var rows = $('#tt').treegrid('getSelections');
	 var title = '新增';
	 var url = inputURL;
	 if(rows.length != 0){
		url += '&parentId=' + rows[0].id;
		title += '(父节点表达式：' + rows[0].regex + ')';
	 }
	 ewcmsBOBJ.openWindow('#edit-window',{width:650,height:150,title:title,url:url});
}
function saveBlockOperate(){
	window.frames['editifr'].document.forms[0].submit();
	$('#tt').treegrid('reload');
}
function queryOperate(){
    var value = $('#queryform').serialize();
    value = "parameters['" + value;
    value = value.replace(/\=/g,"']=");
    value = value.replace(/\&/g,"&parameters['");
    var url = queryURL;
    var index = url.indexOf("?");
    if (index == -1){
        url = url + '?' + value;
    }else{
        url = url + '&' + value;
    }
    $('#tt').treegrid('reload');
    $('#query-window').window('close');
}
function initQueryOperate(){
	$('#tt').treegrid('reload');
}
function delOperate(){
    var rows = $('#tt').treegrid('getSelections');
    if(rows.length == 0){
        $.messager.alert('提示','请选择删除记录','info');
        return ;
    }
    var ids = '';
    for(var i=0;i<rows.length;++i){
        ids =ids + 'selections=' + rows[i].id +'&';
    }
    $.messager.confirm("提示","确定要删除所选记录吗?",function(r){
        if (r){
            $.post(deleteURL,ids,function(data){          	
            	$.messager.alert('成功','删除成功','info');
            	$('#tt').treegrid('clearSelections');
                $('#tt').treegrid('reload');              	
            });
        }
    });
}
function upOperate(){
    var rows = $('#tt').treegrid('getSelections');
    if(rows.length == 0){
        $.messager.alert('提示','请选择上移的记录','info');
        return ;
    }
    if (rows.length > 1){
		$.messager.alert("提示","只能选择一个记录进行上移","info");
		return;
	}
	$.post(upURL,{'selections':rows[0].id},function(data) {
		if (data == "false"){
			$.messager.alert("提示","上移失败","info");
			return;
		}
		$("#tt").treegrid('reload');
	})
}
function downOperate(){
    var rows = $('#tt').treegrid('getSelections');
    if(rows.length == 0){
        $.messager.alert('提示','请选择下移的记录','info');
        return ;
    }
    if (rows.length > 1){
		$.messager.alert("提示","只能选择一个记录进行下移","info");
		return;
	}
	$.post(downURL,{'selections':rows[0].id},function(data) {
		if (data == "false"){
			$.messager.alert("提示","下移失败","info");
			return;
		}
		$("#tt").treegrid('reload');
	});
}
