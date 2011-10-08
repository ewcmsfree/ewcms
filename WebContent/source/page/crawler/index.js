var queryURL,inputURL,deleteURL,matchIndexURL,filterIndexURL,urlLevelIndexURL;

$(function() {
	ewcmsBOBJ = new EwcmsBase();
	ewcmsBOBJ.setQueryURL(queryURL);

	ewcmsBOBJ.addToolItem('URL层级', 'icon-urllevel', urlLevelOperate,'btnUrlLevel');
	ewcmsBOBJ.addToolItem('匹配', 'icon-match', matchOperate, 'btnMatch');
	ewcmsBOBJ.addToolItem('过滤', 'icon-filter', filterOperate, 'btnFilter');
	ewcmsBOBJ.addToolItem('立刻执行', 'icon-crawl-run', runCrawlOperate, 'btnCrawlRun');
	ewcmsBOBJ.addToolItem('定时执行', 'icon-crawl-time', timeCrawlOperate, 'btnCrawlTime');
	
	ewcmsBOBJ.setWinWidth(800);
	ewcmsBOBJ.setWinHeight(500);

	ewcmsBOBJ.openDataGrid('#tt', {
		singleSelect : true,
		columns : [[ 
		    {field : 'id',title : '编号',width : 50,	sortable : true}, 
		    {field : 'name',title : '名称',width : 100}, 
		    {field : 'description',title : '描述',width : 300},
		    {field : 'status',title : '状态',width : 40,
		    	formatter : function(val, rec) {
		    		if (val) return '启用';
		    		else return '停用';
		    	}
		    }, 
		    {field : 'maxContent',title : '最大采集数',width : 100}, 
		    {field : 'depth',title : '采集深度',	width : 80}, 
		    {field : 'threadCount',title : '线程数',	width : 70}, 
		    {field : 'timeOutWait',title : '超时等待时间',width : 90}, 
		    {field : 'errorCount',title : '错误时重试次数',width : 110} 
		 ]]
	});

	ewcmsOOBJ = new EwcmsOperate();
	ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
	ewcmsOOBJ.setInputURL(inputURL);
	ewcmsOOBJ.setDeleteURL(deleteURL);
	
	initSubMenu();
})
function initSubMenu() {
	$('#btnCrawl .l-btn-left').attr('class', 'easyui-linkbutton').menubutton({menu : '#btnCrawlSub'});
}
function matchOperate() {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一条记录', 'info');
		return;
	}
	var url = matchIndexURL + '?gatherId=' + rows[0].id;
	$('#editifr_block').attr('src', url);
	ewcmsBOBJ.openWindow('#block-window', {
		width : 900,
		height : 500,
		title : '匹配块(<font color="red">采集器名称：' + rows[0].name + '</font>)'
	});
}
function filterOperate() {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一条记录', 'info');
		return;
	}
	var url = filterIndexURL + '?gatherId=' + rows[0].id;
	$('#editifr_block').attr('src', url);
	ewcmsBOBJ.openWindow('#block-window', {
		width : 900,
		height : 500,
		title : '过滤块(<font color="red">采集器名称：' + rows[0].name + '</font>)'
	});
}
function urlLevelOperate() {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一条记录', 'info');
		return;
	}
	var url = urlLevelIndexURL + '?gatherId=' + rows[0].id;
	$('#editifr_block').attr('src', url);
	ewcmsBOBJ.openWindow('#block-window', {
		width : 900,
		height : 500,
		title : 'URL层级'
	});
}
function runCrawlOperate(){}
function timeCrawlOperate(){}
