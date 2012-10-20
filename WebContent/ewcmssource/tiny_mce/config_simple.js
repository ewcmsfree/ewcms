tinyMCE.init({
	mode : "textareas",
	theme : "advanced",
	language : 'zh-cn',
	skin : "o2k7",
	convert_urls : false,//不作任何url转换
	indentation : '2em',//首行缩进两个中文字符的空白
	plugins : "pagebreak,style,layer,table,advhr,advimage,advlink,emotions,iespell,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
    theme_advanced_buttons1 : "bold,italic,underline,separator,strikethrough,justifyleft,justifycenter,justifyright,justifyfull,bullist,numlist,undo,redo,link,unlink",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	theme_advanced_buttons4 : "",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_statusbar_location : "bottom",
	content_css : tinyMCE.baseURI.getURI() + "/css/content.css"
});
