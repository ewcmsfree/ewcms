/*
 * Article Edit JavaScript Library v1.0.0
 * 
 * Licensed under the GPL terms To use it on other terms please contact us
 * 
 * author wu_zhijun
 */
var categoryURL, insertURL, voteURL, treeURL, saveURL;
var pages = 1; // 页数
var currentPage = 1;// 当前选中的页
var noImage = "../../ewcmssource/image/article/nopicture.jpg";
var userName;

$(function() {
	ewcmsBOBJ = new EwcmsBase();
	
	//设置自动保存的时长
	setInterval("auto_save()",600000);
	changeType();
	//初始化页数显示
	for ( var i = 1; i < pages; i++) {
		$("#pageList").append(getLi_Html(i+1));
	}
	
	var st = new ArticleToolbar("articleTitle","tdTitle","FontColor,Bold,Italic,UnderLine,FontFamily,FontSize");
	st.show();
	
	//标题
	if ($("#articleTitleStyle").attr("value") != ""){
		var styleValue = $("#articleTitleStyle").attr("value");
		var articleTitleStyle = $("#articleTitle").attr("style") + ";" + styleValue;
		$("#articleTitle").attr("style",articleTitleStyle);
		$("#articleTitleStyle").attr("style",styleValue);
		ArticleToolbarStyle("articleTitle",styleValue);
	}
	
	st = new ArticleToolbar("articleShortTitle","tdShortTitle","FontColor,Bold,Italic,UnderLine,FontFamily,FontSize");
	st.show();
	//短标题
	if ($("#articleShortTitleStyle").attr("value") != ""){
		var styleValue = $("#articleShortTitleStyle").attr("value");
		var articleShortTitleStyle = $("#artilceShortTitle").attr("style") + ";" + styleValue;
		$("#articleShortTitle").attr("style", articleShortTitleStyle);
		$("#articleShortTitleStyle").attr("style",styleValue);
		ArticleToolbarStyle("articleShortTitle",styleValue);
	}

	st = new ArticleToolbar("articleSubTitle","tdSubTitle","FontColor,Bold,Italic,UnderLine,FontFamily,FontSize");
	st.show();
	//副标题
	if ($("#articleSubTitleStyle").attr("value") != ""){
		var styleValue = $("#articleSubTitleStyle").attr("value");
		var articleSubTitleStyle =$("#articleSubTitle").attr("style") + ";" + styleValue;
		$("#articleSubTitle").attr("style", articleSubTitleStyle);
		$("#articleSubTitleStyle").attr("style",styleValue);
		ArticleToolbarStyle("articleSubTitle",styleValue);
	}
	
	//短标题不为空，设置短标题选项选中并显示短标题内容
	if ($.trim($("#articleShortTitle").val()) != ""){
		$("#ShowShortTitle").attr("checked", true);
		var styleValue = $("#articleShortTitleStyle").attr("value");
		var articleTitleStyle = $("#articleShortTitle").attr("style") + ";" + styleValue;
		$("#articleShortTitle").attr("style",articleTitleStyle);
		$("#trShortTitle").show();
	}
	//副标题不为空，设置副标题选项选中并显示副标题内容
	if ($.trim($("#articleSubTitle").val()) != ""){
		$("#ShowSubTitle").attr("checked", true);
		var styleValue = $("#articleSubTitleStyle").attr("value");
		var articleTitleStyle = $("#articleSubTitle").attr("style") + ";" + styleValue;
		$("#articleSubTitle").attr("style",articleTitleStyle);
		$("#trSubTitle").show();
	}
	//设置短标题选项点击事件
	$("#ShowShortTitle").click(function() {
		if ($("#ShowShortTitle").attr("checked") == 'checked') {
			$("#trShortTitle").show();
		} else {
			$("#trShortTitle").hide();
		}
		window_resize();
	});
	//设置副标题选项点击事件
	$("#ShowSubTitle").click(function() {
		if ($("#ShowSubTitle").attr("checked") == 'checked') {
			$("#trSubTitle").show();
		} else {
			$("#trSubTitle").hide();
		}
		window_resize();
	});
	
	//读取cookies操作
	for (var i=1;i<=5;i++){
		var ewcms_cookies = $.cookie("ewcms_" + i + "_" + userName);
		if (ewcms_cookies != null){
			$('#ewcms_' + i).attr('checked',true);
			$('#trShowHide_' + i).show();
		}else{
			$('#ewcms_' + i).attr('checked',false);
			$('#trShowHide_' + i).hide();
		}
	}
	document.title = '文档编辑：' + $('#articleTitle').val();
	
	$('#cc_categories').combobox({
		url: categoryURL,
		valueField:'id',
        textField:'text',
		editable:false,
		multiple:true,
		cascadeCheck:false,
		panelWidth:200
	});
    
	$('#cc_channel').combotree({  
	    url:treeURL,
	    value:$('#channelId').val(), // the initialized value
	    onLoadSuccess:function(){
	    	//$('#cc_channel').combotree('setValue', {id:$('#channelId').val(),text:'图片报道'});
	    },
	    onClick : function(node){
	    	var rootnode = $('#cc_channel').combotree('tree').tree('getRoot');
	    	if (node.id == rootnode.id){
	    		var channelId = $('#channelId').val();
	    		$('#cc_channel').combotree('setValue', channelId);
	    		$.messager.alert('提示', '不能选择根频道', 'info');
				return;
	    	}
	    	$('#channelId').attr('value', node.id);
	    }
	});
	$('#cc_channel').combotree('setValue', $('#channelId').val());
	
	
	ewcmsCookiesInit(userName);
	initChannel();
	window_resize();
});
//绑定窗体改变大小事件
$(window).bind("resize", function () {
	window_resize();
});
//页面自适应窗体大小
function window_resize(){
	var height = $(window).height() - $("#buttonBarTable").height() - $("#inputBarTable").height() - $("#pageBarDiv").height() - 10;
	var width = $(window).width() - 30*2;
	$("div #_DivContainer").css("height",height + "px");
	try{
		if (tinyMCE.getInstanceById('_Content_' + currentPage) != null){
			tinyMCE.getInstanceById('_Content_' + currentPage).theme.resizeTo(width,(height - 138));
		}else{
			$("#_Content_" + currentPage).css("width", (width + 2) + "px");
			$("#_Content_" + currentPage).css("height", (height - 48) + "px");
		}
	}catch(errRes){
	}
}
//新增一页
function addPage() {
	pages = pages + 1;
	$("#pageList").append(getLi_Html(pages));
	var tr_Content_Temple = getTrContent_Html(pages).replace(/\&quot;/g, "\"").replace(/\&lt;/g, "<").replace(/\&gt;/g, ">").replace(/\&nbsp;/g, " ").replace(/\&amp;/g, "&");
	$("#tableContent").append(tr_Content_Temple);
	try{
		tinyMCE.execCommand('mceAddControl', false, '_Content_' + pages);
	}catch(err_add){
	}
	setActivePage(pages);
}
//删除一页(从后向前)，当只剩下最后一页时不再删除
function delPage() {
	if (pages == 1) {return;}
	
	try{
		if (tinyMCE.getInstanceById('_Content_' + pages) != null){
			tinyMCE.execCommand('mceRemoveControl', false, '_Content_' + pages);
		}
	}catch(err_del){
		//alert(err_del);
	}
	
	$("#trContent_" + pages).remove();
	$("#p" + pages).remove();
	
	pages = pages - 1;
	if (currentPage > pages) {
		currentPage = pages;
	}
	setActivePage(currentPage);

}
//页面编辑HTML代码，并写入前台显示页面
function getTrContent_Html(page) {
	var tr_content_html = "<tr id='trContent_" + page + "' >" 
			+ "	 <td>"
			+ "		<textarea id='_Content_" + page + "' class='mceEditor'></textarea>"
			+ "     <input type='hidden' id='textAreaContent_" + page + "' name='textAreaContent'/>"
			+ "   </td>"
			+ "</tr>";
	return tr_content_html;
}
//页码HTML代码，并写入前台显示页面
function getLi_Html(page){
	var li_html = "<li onclick=\"changePage('p" + page + "')\" " 
				+ "onmouseover=\"onOverPage('p" + page + "')\" " 
				+ "onmouseout=\"onOutPage('p" + page + "')\" " 
				+ "id=\"p" + page + "\" "
				+ "name=\"tabs\"><b>页 " + page + "</b>" 
				+ "</li>"
	return li_html;
}
//设置选中页码与页面
function setActivePage(page) {
	var currentTab = $("#p" + page);
	if (currentPage == page && currentTab.attr("class") == "current") {
		return;
	}
	for ( var i = 0; i < pages; i++) {
		var tab = $("#p" + (i + 1));
		var content = $("#trContent_" + (i + 1));
		if (tab.attr("class") == "current") {
			tab.attr("class", "");
			content.hide();
			break;
		}
	}

	currentTab.attr("class", "current");
	$("#trContent_" + page).show();
	$("#_Content_" + page).focus();
	
	currentPage = page;
	
	window_resize();
}
//改变页码与页面
function changePage(id){
	var pageNum = id.substr(1);
	pageNum = parseInt(pageNum);
	setActivePage(pageNum);
}
//鼠标进入页码选项显示效果
function onOverPage(id){
	var li_object = $("#" + id);
	if (li_object.attr("class")==""){
		li_object.attr("class","pagetabOver");
	}
}
//鼠标离开页码选项显示效果
function onOutPage(id){
	var li_object = $("#" + id);
	if (li_object.attr("class")=="pagetabOver"){
		li_object.attr("class","");
	}
}
//选择内容编辑页面的历史记录
function selectHistoryOperator(url){
	var operator_type = editifr_pop.selectOperator();
	var maxPage = operator_type[0];
	var version = operator_type[1];
	var parameter = {};
	parameter["historyId"] = operator_type[2];
	
	var details;
	$.post(url, parameter ,function(data) {
		details = data;
	});
		
	$.messager.confirm("提示","确定要把第 【" + version + "】号 版本替换当前的内容吗?",function(r){
		if (r){
			currentPage = 1;
			if (maxPage!=pages){
				if(maxPage>pages){
					for(var i=pages+1;i<=maxPage;i++){
						addPage();
					}
				}else{
					for (var i=pages;i>maxPage;i--){
						delPage();
					}
				}
			}
			for (var i=1;i<=pages;i++){
				if (tinyMCE.getInstanceById('_Content_' + i) != null){
					tinyMCE.get('_Content_' + i).setContent(details[i-1]);
				}else{
					$("#_Content_" + i).val(details[i-1]);
				}
			}
			setActivePage(1);
		}
	});
	$("#pop-window").window("close");
}
//新建文章
function createArticle(url){
	if ($("#articleId").val()==""){
		$.messager.confirm("提示","文章尚未保存，确认新建文章?",function(r){
			if (r){
				window.location = url;
			}
		});
	}else{
		window.location = url;
	}
}
//提取关键字和摘要
function getKeywordOrSummary(type,url){
	if ($('#articleVo_type').val() == "TITLE"){
		$.messager.alert("提示","标题新闻不用提取【关键字】或【摘要】","info");
		return;
	}
	var title = $("#articleTitle").val();
	if (title == ""){ 
		$.messager.alert("提示","提取【关键字】或【摘要】时，标题不能为空","info");
		$("#title").focus()
		return;
	}
	
	var contents = "";
	for (var i = 1;i <= pages;i++){
		var contentDetail = tinyMCE.get('_Content_' + i).getContent();
		contents += contentDetail;
    }
	if (contents=="") {
		$.messager.alert("提示","提取【关键字】或【摘要】时，内容不能为空","info");
		$("#_Content_" + currentPage).focus();
		return;
	}
	
    var parameter = {};
    parameter["title"] = title;
    parameter["content"] = contents;

	if (type=="keyWord"){
		$.post(url, parameter ,function(data) {
			if (data != ""){
				$.messager.alert("提示","提取【关键字】成功","info");
				$("#keyword").attr("value",data);
			}else{
				$.messager.alert("提示","提取【关键字】失败,可自行添加","info");
			}
		});
	}else{
		$.post(url, parameter ,function(data) {
			if (data != ""){
				$.messager.alert("提示","提取【摘要】成功","info");
				$("#summary").attr("value",data);
			}else{
				$.messager.alert("提示","提取【摘要】失败,可自行添加","info");
			}
		});
	}
}
//保存文章(手动)
function saveArticle(){
	if ($.trim($("#articleTitle").val())==""){
		$.messager.alert("提示","文章标题不能为空","info");
		return;
	}
	var articleType = $('#articleVo_type').val();
	if ( articleType == 'GENERAL'){
		for (var i = 1;i <= pages;i++){
			var contentDetail = tinyMCE.get('_Content_' + i).getContent();
			//contentDetail = contentDetail.replace(/<br \/>/g,"");
			contentDetail = contentDetail.replace(/<p>\&nbsp;<\/p>/g,"");
			if ($.trim(contentDetail) == ""){
				setActivePage(i);
				$.messager.alert("提示","文章内容不能为空!","info");
				return;
			}
			$("#textAreaContent_" + i).attr("value", contentDetail);
		}
	}else if (articleType == 'TITLE'){
		var url = $('#url').val();
		if ($.trim(url) == ''){
			$.messager.alert('提示','链接地址不能为空!','info')
			return;
		}
	}
	
	loadingEnable();
	if ($("#ShowShortTitle").attr("checked") == false || $.trim($("#articleShortTitle").val()) == ""){
		$("#articleShortTitle").attr("value","");
		$("#articleShortTitleStyle").attr("value","");
	}
	if ($("#ShowSubTitle").attr("checked") == false || $.trim($("#articleSubTitle").val()) == "") {
		$("#articleSubTitle").attr("value","");
		$("#articleSubTitleStyle").attr("value","");
	}
	var params=$('#articleSave').serialize();
	$.post(saveURL ,params ,function(data){
		if (data == "false"){
			$.messager.alert("提示","文章保存失败","info");
		}else if (data == "system-false"){
			$.messager.alert("提示","系统错误","info");
		}else if (data == "accessdenied"){
			$.messager.alert("提示","您没有保存文章的权限","info");
		}else if (data != ""){
			$("#state").attr("value", data.state);
			$('#articleMainId').attr('value',data.articleMainId);
			$('#articleId').attr('value',data.articleId);
			$("#keyword").attr("value", data.keyword);
			$("#summary").attr("value", data.summary);
			$("#saveTime_general").html("<font color='#FF0000'>" + data.modified + "</font>");
			$("#saveTime_title").html("<font color='#FF0000'>" + data.modified + "</font>");
			initChannel();
			window.opener.window.articleReload();
			$.messager.alert('提示','文章保存成功','info');
		}
	});
	loadingDisable();
}
//提交审核文章
function submitReview(url, channelId, articleMainId){
	$.post(url, {'channelId':channelId,'selections':articleMainId} ,function(data) {
        if (data != 'true'){
        	if (data == 'system-false'){
	        	$.messager.alert('提示','文章提交审核失败','info');
        	}else if ( data == 'accessdenied'){
        		$.messager.alert('提示','您没有提交审核文章的权限','info');
        	}else if (data == 'notinstate'){
	        	$.messager.alert('提示','文章只有在初稿或重新编辑状态下才能提交审核','info');
        	}
    		return;
        }else{
			window.opener.window.articleReload();
			window.close();
	        return;
        }
	});
}

//清除引用图片
function clearImage(){
	$("#referenceImage").attr("src",noImage);
	$("#article_image").attr("value","");
}
//关闭文档编辑器
function closeArticle(){
	$.messager.confirm("提示","确定要关闭文档编辑器吗?",function(r){
		if (r){
			window.opener.window.articleReload();
			window.close();
		}
	});
}
//显示内容编辑的历史内容页面
function selectHistory(url){
	if ($('#articleVo_type').val() == "TITLE"){
		$.messager.alert("提示","标题新闻没有历史记录","info");
		return;
	}
	var articleId = $("#articleId").val();
	if (articleId == ""){
		$.messager.alert("提示","新增记录没有历史记录","info");
		return;
	}
	$("#selectHistory_span").attr("style","");
	$("#save_span").attr("style","display:none");
	
	$("#editifr_pop").attr("src",url);
	ewcmsBOBJ.openWindow("#pop-window",{width:800,height:600,title:"历史内容选择"});
}
//选择相关联文章页面
function selectRelation(url){
	if ($('#articleVo_type').val() == "TITLE"){
		$.messager.alert("提示","标题新闻没有相关文章","info");
		return;
	}
	var articleId = $("#articleId").val();
	if (articleId == ""){
		$.messager.alert("提示","在新增状态下不能查看相关文章！","info");
		return;
	}
	$("#selectHistory_span").attr("style","display:none");
	$("#save_span").attr("style","");
	$("#editifr_pop").attr("src",url);
	ewcmsBOBJ.openWindow("#pop-window",{width:800,height:600,title:"相关文章选择"});
}
//展开与收缩
function showHide(){
    var showHideLabel_value = $('#showHideLabel').text();
    if ($.trim(showHideLabel_value) == '展开'){
        $('#trShowHide_1').show();
        $('#trShowHide_2').show();
        $('#trShowHide_3').show();
        $('#trShowHide_4').show();
        $('#trShowHide_5').show();
        //$('#imgShowHide').attr('src', '../../ewcmssource/image/article/hide.gif');
    	$('#showHideLabel').text('收缩');
    }else{
    	for (var i=1;i<=5;i++){
    		var ewcms_cookies = $.cookie('ewcms_' + i + '_' + userName);
    		if (ewcms_cookies != null){
    			$('#ewcms_' + i).attr('checked',true);
    			$('#trShowHide_' + i).show();
    		}else{
    			$('#ewcms_' + i).attr('checked',false);
    			$('#trShowHide_' + i).hide();
    		}
    	}
        //$('#imgShowHide').attr('src', '../../ewcmssource/image/article/show.gif');
    	$('#showHideLabel').text('展开');
    }
    window_resize();
}
//根据文章类型显示不同的页面
function changeType(){
    var articleType = $('#articleVo_type').val();
    if (articleType == "TITLE"){
        $('#table_content').hide();
        $('#pageBarTable_general').hide();
        $('#pageBarTable_title').show();
        $('#tr_url').show();
        $('#inside').attr('disabled', true);
        $('#inside').attr('checked', false);
    }else if (articleType == "GENERAL"){
        $('#table_content').show();
        $('#pageBarTable_general').show();
        $('#pageBarTable_title').hide();
        $('#tr_url').hide();
        $('#inside').removeAttr("disabled");
		$("#ShowShortTitle").removeAttr("disabled");
		$("#ShowSubTitle").removeAttr("disabled");
    }
    window_resize();
}
//打开附件页面
function openAnnexWindow(){
    var url = insertURL + '?type=annex';
    ewcmsBOBJ.openWindow("#insert-window",{width:600,height:500,title:"附件选择",url:url});
}
//打开Flash页面
function openFlashWindow(){
    var url = insertURL + '?type=flash';
    ewcmsBOBJ.openWindow("#insert-window",{width:600,height:500,title:"Flash选择",url:url});
}
//打开视频页面
function openVideoWindow(){
    var url = insertURL + '?type=video';
    ewcmsBOBJ.openWindow("#insert-window",{width:600,height:500,title:"视频选择",url:url});
}
//选择引用图片页面
function openRefenceImageWindow(){
	$('#refence_img').attr('value','true');
	openImageWindow(false);
}
//打开图片页面
function openImageWindow(multi){
    var url = insertURL + '?type=image&multi=' + multi;
    ewcmsBOBJ.openWindow("#insert-window",{width:600,height:500,title:"图片选择",url:url});
}
//插入选择的文章到当前内容编辑页面
function insertFileToTinyMCEOperator(){
	uploadifr_insert.insert(function(data,success){
		if (success){
			$.each(data, function(index,value){
				if ($('#refence_img').val() == 'true'){
    				$("#referenceImage").attr("src", value.uri);
    				$("#article_image").attr("value", value.uri);
					$('#refence_img').attr('value', 'false');
				}else{
					var html_obj = "";
					var type = value.type;
					if (type=="ANNEX"){
						html_obj = "<a href='" + value.uri + "'>" + value.description + "</a>";
					}else if (type=="IMAGE"){
						html_obj = "<p style='text-align: center;'><img border='0' src='" + value.uri + "'/></p><p style='text-align: center;'>" + value.description + "</p>";
					}else if (type=="FLASH"){
						html_obj='<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" data="/flvplayer.swf" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="480" height="360">' +
					    '<param name="movie" value="flvplayer.swf" />' +
					    '<param name="quality" value="high" />' +
					    '<param name="allowFullScreen" value="true" />' +
					    '<param name="FlashVars" value="vcastr_file=' + value.uri + '&LogoText=www.dean.gov.cn&BufferTime=3" />' +
					    '<embed src="flvplayer.swf" allowfullscreen="true" flashvars="vcastr_file=' + value.uri + '&LogoText=www.dean.gov.cn" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="500" height="400"></embed>' +
					    '</object>';
					}else if (type=="VIDEO"){
						var video_uri = value.uri;
						var extension = video_uri.replace(/^.*(\.[^\.\?]*)\??.*$/,'$1');
						if (extension==".rm" || extension==".rmvb"){
							html_obj = writeRealMedia({src:video_uri,width:320,height:240,console:"Clip1",controls:"IMAGEWINDOW,ControlPanel,StatusBar",_ExtentX:11298,_ExtentY:7938,AUTOSTART:0});
						}else if(extension==".avi" || extension==".wmv"){
							html_obj = writeWindowsMedia({src:video_uri,width:320,height:240,data:video_uri,autostart:true,controller:true});
						}else if (extension==".flv"||extension==".swf"){
							html_obj = writeFlash({src:video_uri,width:480,height:360,movie: "flvplayer.swf",quality:"high",allowFullScreen:true,flashvars:"vcastr_file=" + video_uri + "&LogoText=www.dean.gov.cn&BufferTime=3"});
						}else if (extension==".wave"){
							html_obj = writeShockWave({src:video_uri,width:320,height:240});
						}else{
							html_obj = writeQuickTime({src:video_uri,width:320,height:240});
						}
					}
					if (tinyMCE.getInstanceById('_Content_' + pages) != null){
						tinyMCE.execInstanceCommand('_Content_' + pages, 'mceInsertContent', false, html_obj);
					}
				}
		   });
		   $("#insert-window").window("close");
		}else{
			$.messager.alert('错误', '插入失败', 'error');
		}
    });
}
//打开问卷调查页面
function openVoteWidnow(){
	$('#editifr_vote').attr('src',voteURL);
	ewcmsBOBJ.openWindow("#vote-window",{width:600,height:500,title:"问卷调查选择"});
}
//插入问卷调查到内容编辑页面
function insertVote(){
	var rows = editifr_vote.getVoteRows();
	var urlAndContextName = editifr_vote.$('#urlAndContextName').val();
	var host = "http://" + window.location.host;
	for (var i=0;i<rows.length;i++){
		var html_obj= "<iframe src='" + host + urlAndContextName + "/view.vote?id=" + rows[i].id + "' frameborder='0' scrolling='no' style='padding:1px;' width='100%' height='100%' onload='this.height=500'></iframe>";
		if (tinyMCE.getInstanceById('_Content_' + currentPage) != null){
			tinyMCE.execInstanceCommand('_Content_' + currentPage,'mceInsertContent',false,html_obj);
		}
	}
	$("#vote-window").window("close");
}
//文章自动保存
function auto_save() {
	if ($.trim($("#articleTitle").val())==""){
		return;
	}
	if ($('#articleVo_type').val() == "GENERAL"){
		var autoSave = false;
		for (var i = 1;i <= pages;i++){
			var editor_id = "_Content_" + i;
			var content = tinyMCE.get(editor_id).getContent();
			var notDirty = tinyMCE.get(editor_id);
			content = content.replace(/<br \/>/g,"");
			content = content.replace(/<p>\&nbsp;<\/p>/g,"");
			$("#textAreaContent_" + i).attr("value", content);
			if ($.trim(content) == ""){
				continue;
			}
			if(tinyMCE.getInstanceById(editor_id).isDirty()) {
				autoSave = true;
				notDirty.isNotDirty = true;
			}
		}
		if ($("#ShowShortTitle").attr("checked") == false || $.trim($("#articleShortTitle").val()) == ""){
			$("#articleShortTitle").attr("value","");
			$("#articleShortTitleStyle").attr("value","");
		}
		if ($("#ShowSubTitle").attr("checked") == false || $.trim($("#articleSubTitle").val()) == "") {
			$("#articleSubTitle").attr("value","");
			$("#articleSubTitleStyle").attr("value","");
		}
		if (autoSave){
			var params=$('#articleSave').serialize();
			$.post(saveURL ,params ,function(data){
				if (data != "false" && data != "system-false" && data != "accessdenied" && data != ""){
					$("#state").attr("value", data.state);
					$('#articleMainId').attr('value',data.articleMainId);
					$('#articleId').attr('value',data.articleId);
					$("#keyword").attr("value", data.keyword);
					$("#summary").attr("value", data.summary);
					$("#saveTime_general").html("<font color='#0000FF'>" + data.modified + "</font>");
					$("#saveTime_title").html("<font color='#0000FF'>" + data.modified + "</font>");
					initChannel();
					window.opener.window.articleReload();
				}
			});
		}
	}
}
//打开Cookies页面设置
function ewcmsCookies(){
	ewcmsBOBJ.openWindow("#ewcms-cookies",{width:300,height:215,title:"设置常用项"});
}
//Cookies设置
function ewcmsCookiesSet(obj,trId){
	var id = obj.id;
	if ($('#' + id).attr('checked') == 'checked'){
		$.cookie(id + '_' + userName,'true',{expires:14});
//		if (id == 'ewcms_toolbar'){
//			$("div[id='DivToolbar']").each(function(){
//				$(this).show();
//			});
//			$('#ewcms_toolbar').attr('checked', true);
//		}else{
			$('#' + trId).show();
//		}
	}else{
		$.cookie(id + '_' + userName, null);
//		if (id == 'ewcms_toolbar'){
//			$("div[id='DivToolbar']").each(function(){
//				$(this).hide();
//			});
//			$('#ewcms_toolbar').attr('checked', false);
//		}else{
			$('#' + trId).hide();
//		}
	}
	
	window_resize();
}
//Cookies的初始化
function ewcmsCookiesInit(){
//	var ewcms_toolbar_cookies = $.cookie("ewcms_toolbar_" + userName);
//	if (ewcms_toolbar_cookies != null){
//		$('#ewcms_toolbar').attr('checked', true);
//		$("div[id='DivToolbar']").each(function(){
//			$(this).show();
//		});
//	}else{
//		$('#ewcms_toolbar').attr('checked', false);
//		$("div[id='DivToolbar']").each(function(){
//			$(this).hide();
//		});
//	}
	for (var i=1;i<=5;i++){
		var ewcms_cookies = $.cookie("ewcms_" + i + "_" + userName);
		if (ewcms_cookies != null){
			$('#ewcms_' + i).attr('checked',true);
			$('#trShowHide_' + i).show();
		}else{
			$('#ewcms_' + i).attr('checked',false);
			$('#trShowHide_' + i).hide();
		}
	}
}
function loadingEnable(){
   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
   $("<div class=\"datagrid-mask-msg\"></div>").html("<font size='9'>正在处理，请稍候。。。</font>").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
}
function loadingDisable(){
   $('.datagrid-mask-msg').remove();
   $('.datagrid-mask').remove();
}

function writeFlash(p) {
	return writeEmbed(
		'D27CDB6E-AE6D-11cf-96B8-444553540000',
		'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0',
		'application/x-shockwave-flash',
		p
	);
}

function writeShockWave(p) {
	return writeEmbed(
	'166B1BCA-3F9C-11CF-8075-444553540000',
	'http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0',
	'application/x-director',
		p
	);
}

function writeQuickTime(p) {
	return writeEmbed(
		'02BF25D5-8C17-4B23-BC80-D3488ABDDC6B',
		'http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0',
		'video/quicktime',
		p
	);
}

function writeRealMedia(p) {
	return writeEmbed(
		'CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA',
		'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0',
		'audio/x-pn-realaudio-plugin',
		p
	);
}

function writeWindowsMedia(p) {
	p.url = p.src;
	return writeEmbed(
		'6BF52A52-394A-11D3-B153-00C04F79FAA6',
		'http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701',
		'application/x-mplayer2',
		p
	);
}

function writeEmbed(cls, cb, mt, p) {
	var h = '', n;

	h += '<object classid="clsid:' + cls + '" codebase="' + cb + '"';
	h += typeof(p.id) != "undefined" ? 'id="' + p.id + '"' : '';
	h += typeof(p.name) != "undefined" ? 'name="' + p.name + '"' : '';
	h += typeof(p.width) != "undefined" ? 'width="' + p.width + '"' : '';
	h += typeof(p.height) != "undefined" ? 'height="' + p.height + '"' : '';
	h += typeof(p.align) != "undefined" ? 'align="' + p.align + '"' : '';
	h += typeof(p.data) != "undefined" ? 'date="' + p.date + '"' : '';
	h += '>';

	for (n in p)
		h += '<param name="' + n + '" value="' + p[n] + '">';

	h += '<embed type="' + mt + '"';

	for (n in p)
		h += n + '="' + p[n] + '" ';

	h += '></embed></object>';

	return h;
}

function initChannel(){
	if ($('#articleMainId').val() != ''){
		$('#cc_channel').combotree('disable');
	}else{
		$('#cc_channel').combotree('enable');
	}
}

//样式初始化
function ArticleToolbarStyle(styleID, styleValue){
	var styleArr = styleValue.split(";");
	for (var i = 0 ; i < styleArr.length ; i++){
		var styleKey = styleArr[i].split(":")[0];
		var styleValue = styleArr[i].split(":")[1];
		if ($.trim(styleKey) == "font-weight"){//粗体
			if ($.trim(styleValue) == "bold"){
				$("#" + styleID + "_Bold").attr("value","bold");
				$("#" + styleID + "_Bold_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
				$("#" + styleID + "_Bold_Div").get()[0].isClicked = true;
			}else{
				$("#" + styleID + "_Bold").attr("value","normal");
				$("#" + styleID + "_Bold_Div").attr("style","border:none;background:none");
				$("#" + styleID + "_Bold_Div").get()[0].isClicked = false;
			}
		}else if ($.trim(styleKey) == "font-style"){//斜体
			if ($.trim(styleValue) == "italic"){
				$("#" + styleID + "_Italic").attr("value","italic");
				$("#" + styleID + "_Italic_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
				$("#" + styleID + "_Italic_Div").get()[0].isClicked = true;
			}else{
				$("#" + styleID + "_Italic").attr("value","normal");
				$("#" + styleID + "_Italic_Div").attr("style","border:none;background:none");
				$("#" + styleID + "_Italic_Div").get()[0].isClicked = false;
			}
		}else if ($.trim(styleKey) == "text-decoration"){//下划线
			if ($.trim(styleValue) == "underline"){
				$("#" + styleID + "_UnderLine").attr("value","underline");
				$("#" + styleID + "_UnderLine_Div").attr("style","border:#316ac5 1px solid;background:#c1d2ee");
				$("#" + styleID + "_UnderLine_Div").get()[0].isClicked = true;
			}else{
				$("#" + styleID + "_UnderLine").attr("value","none");
				$("#" + styleID + "_UnderLine_Div").attr("style","border:none;background:none");
				$("#" + styleID + "_UnderLine_Div").get()[0].isClicked = false;
			}
		}else if ($.trim(styleKey) == "font-family"){//字体
			$("#" + styleID + "_FontFamily").attr("value",$.trim(styleValue));
			$("#" + styleID + "_FontFamily_Select").val($.trim(styleValue));
		}else if ($.trim(styleKey) == "font-size"){//字号
			$("#" + styleID + "_FontSize").attr("value",$.trim(styleValue));
			$("#" + styleID + "_FontSize_Select").val($.trim(styleValue));
		}
	}
}