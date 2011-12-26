var queryURL,inputURL,deleteURL,delGatherDataURL,matchIndexURL,filterIndexURL,domainIndexURL,crawlRunURL,schedulingURL,helpURL;

$(function() {
	ewcmsBOBJ = new EwcmsBase();
	ewcmsBOBJ.setQueryURL(queryURL);

	ewcmsBOBJ.addToolItem('地址区配', 'icon-levels', domainOperate, 'btnDomain');
	ewcmsBOBJ.addToolItem('内容匹配', 'icon-match', matchOperate, 'btnMatch');
	ewcmsBOBJ.addToolItem('内容过滤', 'icon-filter', filterOperate, 'btnFilter');
	ewcmsBOBJ.addToolItem('立刻执行', 'icon-run', runCrawlOperate, 'btnCrawlRun');
	ewcmsBOBJ.addToolItem('定时设置', 'icon-scheduler-set', timeCrawlOperate, 'btnCrawlTime');
	ewcmsBOBJ.addToolItem('清空采集数据','icon-delgatherdata', delGatherDataOperate, 'btnDelGatherData')
	//ewcmsBOBJ.addToolItem('帮助', 'icon-help', helpOperate,'btnHelp');
	
	ewcmsBOBJ.setWinWidth(900);
	ewcmsBOBJ.setWinHeight(620);

	ewcmsBOBJ.openDataGrid('#tt', {
		singleSelect : true,
		columns : [[ 
		    {field : 'id',title : '编号',width : 50,	sortable : true}, 
		    {field : 'name',title : '名称',width : 100}, 
		    {field : 'description',title : '描述',width : 300},
		    {field : 'status',title : '状态',width : 40,
		    	formatter : function(val, rec) {
		    		if (val) return '启用';
		    		return '停用';
		    	}
		    }, 
		    {field : 'maxPage',title : '最大采集数',width : 100}, 
		    {field : 'depth',title : '采集深度',	width : 80}, 
		    {field : 'threadCount',title : '线程数',	width : 70}, 
		    {field : 'timeOutWait',title : '超时等待时间',width : 90},
		    {field : 'titleExternal',title : '外部标题',width : 70,
		    	formatter : function(val, rec){
		    		if (val) return '是';
		    		return '否';
		    	}
		    }
		 ]]
	});

	ewcmsOOBJ = new EwcmsOperate();
	ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
	ewcmsOOBJ.setInputURL(inputURL);
	ewcmsOOBJ.setDeleteURL(deleteURL);
})
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
function domainOperate() {
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一条记录', 'info');
		return;
	}
	var url = domainIndexURL + '?gatherId=' + rows[0].id;
	$('#editifr_block').attr('src', url);
	ewcmsBOBJ.openWindow('#block-window', {
		width : 900,
		height : 500,
		title : 'URL层级'
	});
}
function runCrawlOperate(){
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一条记录', 'info');
		return;
	}
	if (!rows[0].status){
		$.messager.alert('提示', '选择的记录为停用状态，不能手动执行', 'info');
		return;
	}
	var url = crawlRunURL + '?selections=' + rows[0].id;
	$.ajax({
        type:'post',
        async:false,
        datatype:'json',
        cache:false,
        url:url,
        data: '',
        success:function(message, textStatus){
        	loadingDisable();
        	if (message=='true'){
        		$.messager.alert('提示', '手动采集成功', 'info');
        	}else{
        		$.messager.alert('错误', message, 'error');
        	}
        },
        beforeSend:function(XMLHttpRequest){
        	loadingEnable();
        },
        complete:function(XMLHttpRequest, textStatus){
        	loadingDisable();
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}
function timeCrawlOperate(){
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一条记录', 'info');
		return;
	}
	if (!rows[0].status){
		$.messager.alert('提示', '选择的记录为停用状态，不能定时设置', 'info');
		return;
	}
	var url = schedulingURL + '?gatherId=' + rows[0].id;
	$('#editifr_scheduling').attr('src', url);
	ewcmsBOBJ.openWindow('#scheduling-window', {
		width : 900,
		height : 500,
		title : '定时器设置'
	});
}
function loadingEnable(){
   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
   $("<div class=\"datagrid-mask-msg\"></div>").html("<font size='9'>正在处理，请稍候。。。</font>").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
function loadingDisable(){
   $('.datagrid-mask-msg').remove();
   $('.datagrid-mask').remove();
}
function saveScheduling(){
	window.frames['editifr_scheduling'].document.forms[0].submit();
}
function helpOperate(){
	window.open(helpURL,'popup','width=900,height=500,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,scrollbars=yes,status=no,left=' + (window.screen.width - 900)/ 2 + ',top=' + (window.screen.height - 500) / 2);
}
function delGatherDataOperate(){
	var rows = $('#tt').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', '请选择记录', 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', '只能选择一条记录', 'info');
		return;
	}
	var url = delGatherDataURL + '?selections=' + rows[0].id;
	$.ajax({
        type:'post',
        async:false,
        datatype:'json',
        cache:false,
        url:url,
        data: '',
        success:function(message, textStatus){
        	loadingDisable();
        	if (message=='true'){
        		$.messager.alert('提示', '清除采集数据成功', 'info');
        	}else{
        		$.messager.alert('错误', message, 'error');
        	}
        },
        beforeSend:function(XMLHttpRequest){
        	loadingEnable();
        },
        complete:function(XMLHttpRequest, textStatus){
        	loadingDisable();
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
        }
    });
}