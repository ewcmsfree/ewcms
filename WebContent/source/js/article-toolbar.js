var ARTICLETOOLBAR_ALL_TYPE = "FontFamily,FontSize,FontColor,Bold,Italic,UnderLine".split(",");
//var ARTICLETOOLBAR_ALL_ATTR = "fontFamily,fontSize,color,fontWeight,fontStyle,textDecoration".split(",");
var ARTICLETOOLBAR_ALL_CSSATTR = "font-family,font-size,color,font-weight,font-style,text-decoration".split(",");

var ArticleToolbar = function(id, appendEle, option){
	this.ID = id;
	this.AppendEle = $("#" + appendEle);
	if (!option){
		this.Option = ARTICLETOOLBAR_ALL_TYPE;
	}else{
		this.Option = option.split(",");
	}
	
}

ArticleToolbar.prototype.show = function(){
	this.Html = [];
	this.Html.push("<div id='DivToolbar' style='display:inline-block;display:-moz-inline-box; -moz-box-align:stretch;*zoom: 1;*display: inline;vertical-align:middle; overflow:hidden;'>");
	this.Html.push("<table id='" + this.ID + "_Table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;'><tr>");
	for(var i = 0; i < this.Option.length; i++){
		this["add" + this.Option[i]]();
	}
	this.Html.push("</tr></table></div>");
	this.AppendEle.append(this.Html.join(''));
	this.ID.Instance = this;
}

ArticleToolbar.prototype.addFontFamily = function(){
	this.Html.push("<td><input type='hidden' id='" + this.ID + "_FontFamily' name='" + this.ID + "_FontFamily'/><div title='字体' style='width:100%;' id='" + this.ID + "_FontFamily_Div' onchange=\"ArticleToolbar.onChange('" + this.ID + "_FontFamily')\">");
	var fonts = "宋体;仿宋_GB2312;新宋体;隶书;楷体_GB2312;华文中宋;幼圆;黑体;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana";
	var arr = [];
	this.Html.push("<select id='" + this.ID + "_FontFamily_Select'>");
	var fontsSplit = fonts.split(";");
	for (var i = 0 ; i < fontsSplit.length; i++){
		arr.push("<option value='" + fontsSplit[i] + "'>" + fontsSplit[i] + "</option>");
	}
	this.Html.push(arr.join('\n')+"</select></div></td>");
}

ArticleToolbar.prototype.addFontSize = function(){
	this.Html.push("<td><input type='hidden' id='" + this.ID + "_FontSize' name='" + this.ID + "_FontSize'/><div title='字号' style='width:100%;' id='" + this.ID + "_FontSize_Div' onchange=\"ArticleToolbar.onChange('" + this.ID + "_FontSize')\">");
	var fonts = "9,10,12,14,16,18,24,36";
	var arr = [];
	this.Html.push("<select id='" + this.ID + "_FontSize_Select'>");
	var fontsSplit = fonts.split(",");
	for (var i = 0; i < fontsSplit.length; i++){
		arr.push("<option value='" + fontsSplit[i] + "px' " + (fontsSplit[i]=="12"?"selected='true'":"") + ">" + fontsSplit[i] + "</option>");
	}
	this.Html.push(arr.join('\n')+"</select></div></td>");
}

ArticleToolbar.prototype.addFontColor = function(){
	this.Html.push("<td><input type='hidden' id='" + this.ID + "_FontColor' name='" + this.ID + "_FontColor'/><div title='字体颜色' id='" + this.ID + "_FontColor_Div'");
	this.Html.push(" onclick='ArticleToolbar.onClick(this);ArticleToolbar.showColorSelector(this)' onmouseover='ArticleToolbar.onMouseOver(this)' onmouseout='ArticleToolbar.onMouseOut(this)'>");
	this.Html.push("<table cellspacing='0' cellpadding='0'><tbody><tr>");
	this.Html.push("<td style='height:17px;'><img src='../../source/image/article/spacer.gif' style='background-position: 0px -704px; background-image: url(../../source/image/article/fck_strip.gif);overflow: hidden;width: 14px;height: 14px;margin: 0px;'></td>");
	this.Html.push("<td style='height:17px;'><img height='3' width='5' src='../../source/image/article/toolbar.buttonarrow.gif'></td>");
	this.Html.push("</tr></tbody></table>");
	this.Html.push("</div></td>");
}

ArticleToolbar.prototype.addBold = function(){
	this.Html.push("<td><input type='hidden' id='" + this.ID + "_Bold' name='" + this.ID + "_Bold'/><div id='" + this.ID + "_Bold_Div' title='加粗'");
	this.Html.push(" onclick=\"ArticleToolbar.onClick(this);ArticleToolbar.setBold('" + this.ID + "')\" onmouseover='ArticleToolbar.onMouseOver(this)' onmouseout='ArticleToolbar.onMouseOut(this)'>");
	this.Html.push("<img src='../../source/image/article/spacer.gif' style='background-position: 0px -304px; background-image: url(../../source/image/article/fck_strip.gif);overflow: hidden;width: 14px;height: 14px;margin: 1px;'>");
	this.Html.push("</div></td>");
}

ArticleToolbar.prototype.addItalic = function(){
	this.Html.push("<td><input type='hidden' id='" + this.ID + "_Italic' name='" + this.ID + "_Italic'/><div id='" + this.ID + "_Italic_Div' title='斜体'");
	this.Html.push(" onclick=\"ArticleToolbar.onClick(this);ArticleToolbar.setItalic('" + this.ID + "')\" onmouseover='ArticleToolbar.onMouseOver(this)' onmouseout='ArticleToolbar.onMouseOut(this)'>");
	this.Html.push("<img src='../../source/image/article/spacer.gif' style='background-position: 0px -320px; background-image: url(../../source/image/article/fck_strip.gif);overflow: hidden;width: 14px;height: 14px;margin: 1px;'>");
	this.Html.push("</div></td>");
}

ArticleToolbar.prototype.addUnderLine = function(){
	this.Html.push("<td><input type='hidden' id='" + this.ID + "_UnderLine' name='" + this.ID + "_UnderLine'/><div id='" + this.ID + "_UnderLine_Div' title='加下划线'");
	this.Html.push(" onclick=\"ArticleToolbar.onClick(this);ArticleToolbar.setUnderLine('" + this.ID + "')\" onmouseover='ArticleToolbar.onMouseOver(this)' onmouseout='ArticleToolbar.onMouseOut(this)'>");
	this.Html.push("<img src='../../source/image/article/spacer.gif' style='background-position: 0px -336px; background-image: url(../../source/image/article/fck_strip.gif);overflow: hidden;width: 14px;height: 14px;margin: 1px;'>");
	this.Html.push("</div></td>");
}

ArticleToolbar.onClick = function(div){
	if(div.isClicked){
		div.style.border = "none";
		div.style.background = "none";
		div.isClicked = false;
	}else{
		div.style.border = "#316ac5 1px solid";
		div.style.backgroundColor = "#c1d2ee";
		div.isClicked = true;
	}
}

ArticleToolbar.onMouseOver = function(div){
	div.style.border = "#316ac5 1px solid";
	div.style.backgroundColor = "#dff1ff";
}

ArticleToolbar.onMouseOut = function(div){
	if(div.isClicked){
		div.style.border = "#316ac5 1px solid";
		div.style.backgroundColor = "#c1d2ee";
	}else{
		div.style.border = "none";
		div.style.background =  "";
	}
}

ArticleToolbar.onChange = function(id){
	var type = id.substring(id.indexOf("_") + 1);
	var targetID = id.substring(0,id.indexOf("_"));
	var checkText = $("#" + id + "_Select").find("option:selected").val();
	if (type == "FontFamily"){
		$("#" + targetID).css({"font-family" : checkText});
		$("#" + targetID + "Style").css({"font-family" : checkText});
	}else if (type == "FontSize"){
		$("#" + targetID).css({"font-size" : checkText});
		$("#" + targetID + "Style").css({"font-size" : checkText});
	}
	$("#" + targetID + "Style").attr("value",$("#" + targetID + "Style").attr("style"));
}

ArticleToolbar.showColorSelector = function(div){
	if(!div.isClicked){
		return;
	}
	var id = div.id;
	id = id.substring(0,id.lastIndexOf('_'));
	var cs = new ColorSelector(id,div);
	cs.AfterSelect = function(targetID,color){
		$("#" + targetID).attr("value",color);
		$("#" + targetID.substring(0,targetID.indexOf('_'))).css({"color":color});
		$("#" + targetID.substring(0,targetID.indexOf('_')) + "Style").css({"color":color});
		ArticleToolbar.onChange(targetID);
	}
	cs.show();
}

ArticleToolbar.setBold = function(id){
	var bold = $("#" + id + "_Bold").attr("value");
	if (bold == "bold"){
		bold = "normal";
	}else{
		bold = "bold";
	}
	$("#" + id + "_Bold").attr("value", bold);
	$("#" + id).css({"font-weight" : bold});
	$("#" + id + "Style").css({"font-weight" : bold});
	ArticleToolbar.onChange(id + "_Bold");
}

ArticleToolbar.setItalic = function(id){
	var italic = $("#" + id + "_Italic").attr("value");
	if (italic == "italic"){
		italic = "normal";
	}else{
		italic = "italic";
	}
	$("#" + id + "_Italic").attr("value", italic);
	$("#" + id).css({"font-style" : italic});
	$("#" + id + "Style").css({"font-style" : italic});
	ArticleToolbar.onChange(id + "_Italic");
}

ArticleToolbar.setUnderLine = function(id){
	var underLine = $("#" + id + "_UnderLine").attr("value");
	if (underLine == "underline"){
		underLine = "none";
	}else{
		underLine = "underline";
	}
	$("#" + id + "_UnderLine").attr("value", underLine);
	$("#" + id).css({"text-decoration" : underLine});
	$("#" + id + "Style").css({"text-decoration" : underLine});
	ArticleToolbar.onChange(id + "_UnderLine");
}

function ColorSelector(id, div){
	this.ID = id;
	this.Div = div;
}

ColorSelector.prototype.show = function(){
	var pw = window;
	var win;
	if (!$("#_ColorSelector").get()[0]){
		var div = pw.document.createElement("DIV");
		div.id = "_ColorSelector";
		div.style.cssText = "background-color:#fff;left:200;top:200;position:absolute;z-index:999;overflow: auto;white-space: nowrap;cursor: default;border: 1px solid #8f8f73;";
		div.innerHTML = "<iframe id='_ColorSelector_Frame' frameborder=0 scrolling=no width=152 height=114></iframe>";
		pw.document.body.appendChild(div);		
		win = $("#_ColorSelector_Frame").get()[0].contentWindow;
		var doc = win.document;
		doc.open();
		var html = [];
		html.push("<style>");
		html.push("body{margin: 0px;padding-left: 2px;padding-right: 2px;padding-top: 2px;}");
		html.push("td{font-size:12px;}");
		html.push(".ColorBoxBorder{border: #808080 1px solid;position: static;}");
		html.push(".ColorBox{font-size: 1px;width: 10px;position: static;height: 10px;}");
		html.push(".ColorDeselected, .ColorSelected{cursor: default;}");
		html.push(".ColorDeselected{border: #ffffff 1px solid;padding: 2px;float: left;}");
		html.push(".ColorSelected{border: #330066 1px solid;padding: 2px;float: left; background-color: #c4cdd6;}");
		html.push("</style>");
		html.push("<body><table id='_TableColors' class='ForceBaseFont' style='tableLayout:fixed;' cellpadding='0' cellspacing='0' border='0' width='150'>");
		html.push('<tr><td colspan=8>');
		html.push("<div class='ColorDeselected' onclick=\"TopWindow.ColorSelector.getInstance('"+this.ID+"').setColor('');\" onmouseover=\"this.className='ColorSelected'\" style='width:95%' onmouseout=\"this.className='ColorDeselected'\">");
		html.push('<table cellspacing="0" cellpadding="0" width="100%" border="0"><tr>');
		html.push('<td><div class="ColorBox" style="background-color: #000000"></div></div></td>');
		html.push('<td nowrap width="100%" align="center">自动</td>');
		html.push('</tr></table>');
		html.push("</div></td></tr>");		

		var FontColors = '000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF' ;
		var aColors = FontColors.toString().split(',');
		var iCounter = 0 ;
		while(iCounter < aColors.length){
			html.push("<tr>");
			for(var i=0;i<8;i++,iCounter++){
				if(iCounter<aColors.length){
					var colorParts = aColors[iCounter].split('/');
					var colorValue = '#' + colorParts[0];
					var colorName = colorParts[1] || colorValue;
				}
				html.push("<td><div class='ColorDeselected' onclick=\"TopWindow.ColorSelector.getInstance('"+this.ID+"').setColor('"+colorValue+"');\" onmouseover=\"this.className='ColorSelected'\" onmouseout=\"this.className='ColorDeselected'\">");
				html.push('<div class="ColorBoxBorder"><div class="ColorBox" style="background-color: ' + colorValue + '"></div></div>');
				html.push("</div></td>");
			}
			html.push("</tr>");
		}
		html.push("</table></table></body>");
		doc.write(html.join(''));
		doc.close();	
	}else{
		win = $("#_ColorSelector_Frame").get()[0].contentWindow;
	}
	win.TopWindow = window;
	win.Instace = this;
	var csDiv = document.getElementById('_ColorSelector');;
	csDiv.style.top = ($("#DivToolbar").offset().top + $("#DivToolbar").height()) + "px";;
	csDiv.style.left = $("#DivToolbar").offset().left + "px";;
	csDiv.style.display = "";
	pw.SourceWindow = window;
}

ColorSelector.prototype.setColor = function(color){
	if(this.AfterSelect){
		this.AfterSelect(this.ID,color);
	}
	ArticleToolbar.onClick(this.Div);
	ColorSelector.close();
}

ColorSelector.getInstance = function(id){
	var win = $("#_ColorSelector_Frame").get()[0].contentWindow;
	return win.Instace;
}

ColorSelector.close = function(){
	if ($("#_ColorSelector")&&$("#_ColorSelector").is(':visible')){
		$("#_ColorSelector").hide();
		window.SourceWindow = null;
	}
}

$("*").mousedown(function(){
	if ($("#_ColorSelector")&&$("#_ColorSelector").is(':visible')){
		$("div[id$=_FontColor_Div]").attr("style","border:none;background:none");
		$("div[id$=_FontColor_Div]").get()[0].isClicked = false;
		$("#_ColorSelector").hide();
		window.SourceWindow = null;
	}
});