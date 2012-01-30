/**
 * jQuery EasyUI 1.2.5
 * 
 * Licensed under the GPL terms
 * To use it on other terms please contact us
 *
 * Copyright(c) 2009-2011 stworthy [ stworthy@gmail.com ] 
 * 
 */
(function($){
function _1(_2){
_2.each(function(){
$(this).remove();
if($.browser.msie){
this.outerHTML="";
}
});
};
function _3(_4,_5){
var _6=$.data(_4,"panel").options;
var _7=$.data(_4,"panel").panel;
var _8=_7.children("div.panel-header");
var _9=_7.children("div.panel-body");
if(_5){
if(_5.width){
_6.width=_5.width;
}
if(_5.height){
_6.height=_5.height;
}
if(_5.left!=null){
_6.left=_5.left;
}
if(_5.top!=null){
_6.top=_5.top;
}
}
if(_6.fit==true){
var p=_7.parent();
_6.width=p.width();
_6.height=p.height();
}
_7.css({left:_6.left,top:_6.top});
if(!isNaN(_6.width)){
if($.boxModel==true){
_7.width(_6.width-(_7.outerWidth()-_7.width()));
}else{
_7.width(_6.width);
}
}else{
_7.width("auto");
}
if($.boxModel==true){
_8.width(_7.width()-(_8.outerWidth()-_8.width()));
_9.width(_7.width()-(_9.outerWidth()-_9.width()));
}else{
_8.width(_7.width());
_9.width(_7.width());
}
if(!isNaN(_6.height)){
if($.boxModel==true){
_7.height(_6.height-(_7.outerHeight()-_7.height()));
_9.height(_7.height()-_8.outerHeight()-(_9.outerHeight()-_9.height()));
}else{
_7.height(_6.height);
_9.height(_7.height()-_8.outerHeight());
}
}else{
_9.height("auto");
}
_7.css("height","");
_6.onResize.apply(_4,[_6.width,_6.height]);
_7.find(">div.panel-body>div").triggerHandler("_resize");
};
function _a(_b,_c){
var _d=$.data(_b,"panel").options;
var _e=$.data(_b,"panel").panel;
if(_c){
if(_c.left!=null){
_d.left=_c.left;
}
if(_c.top!=null){
_d.top=_c.top;
}
}
_e.css({left:_d.left,top:_d.top});
_d.onMove.apply(_b,[_d.left,_d.top]);
};
function _f(_10){
var _11=$(_10).addClass("panel-body").wrap("<div class=\"panel\"></div>").parent();
_11.bind("_resize",function(){
var _12=$.data(_10,"panel").options;
if(_12.fit==true){
_3(_10);
}
return false;
});
return _11;
};
function _13(_14){
var _15=$.data(_14,"panel").options;
var _16=$.data(_14,"panel").panel;
if(_15.tools&&typeof _15.tools=="string"){
_16.find(">div.panel-header>div.panel-tool .panel-tool-a").appendTo(_15.tools);
}
_1(_16.children("div.panel-header"));
if(_15.title&&!_15.noheader){
var _17=$("<div class=\"panel-header\"><div class=\"panel-title\">"+_15.title+"</div></div>").prependTo(_16);
if(_15.iconCls){
_17.find(".panel-title").addClass("panel-with-icon");
$("<div class=\"panel-icon\"></div>").addClass(_15.iconCls).appendTo(_17);
}
var _18=$("<div class=\"panel-tool\"></div>").appendTo(_17);
if(_15.tools){
if(typeof _15.tools=="string"){
$(_15.tools).children().each(function(){
$(this).addClass($(this).attr("iconCls")).addClass("panel-tool-a").appendTo(_18);
});
}else{
for(var i=0;i<_15.tools.length;i++){
var t=$("<a href=\"javascript:void(0)\"></a>").addClass(_15.tools[i].iconCls).appendTo(_18);
if(_15.tools[i].handler){
t.bind("click",eval(_15.tools[i].handler));
}
}
}
}
if(_15.collapsible){
$("<a class=\"panel-tool-collapse\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
if(_15.collapsed==true){
_37(_14,true);
}else{
_27(_14,true);
}
return false;
});
}
if(_15.minimizable){
$("<a class=\"panel-tool-min\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
_42(_14);
return false;
});
}
if(_15.maximizable){
$("<a class=\"panel-tool-max\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
if(_15.maximized==true){
_46(_14);
}else{
_26(_14);
}
return false;
});
}
if(_15.closable){
$("<a class=\"panel-tool-close\" href=\"javascript:void(0)\"></a>").appendTo(_18).bind("click",function(){
_19(_14);
return false;
});
}
_16.children("div.panel-body").removeClass("panel-body-noheader");
}else{
_16.children("div.panel-body").addClass("panel-body-noheader");
}
};
function _1a(_1b){
var _1c=$.data(_1b,"panel");
if(_1c.options.href&&(!_1c.isLoaded||!_1c.options.cache)){
_1c.isLoaded=false;
var _1d=_1c.panel.find(">div.panel-body");
if(_1c.options.loadingMessage){
_1d.html($("<div class=\"panel-loading\"></div>").html(_1c.options.loadingMessage));
}
$.ajax({url:_1c.options.href,cache:false,success:function(_1e){
_1d.html(_1c.options.extractor.call(_1b,_1e));
if($.parser){
$.parser.parse(_1d);
}
_1c.options.onLoad.apply(_1b,arguments);
_1c.isLoaded=true;
}});
}
};
function _1f(_20){
$(_20).find("div.panel:visible,div.accordion:visible,div.tabs-container:visible,div.layout:visible").each(function(){
$(this).triggerHandler("_resize",[true]);
});
};
function _21(_22,_23){
var _24=$.data(_22,"panel").options;
var _25=$.data(_22,"panel").panel;
if(_23!=true){
if(_24.onBeforeOpen.call(_22)==false){
return;
}
}
_25.show();
_24.closed=false;
_24.minimized=false;
_24.onOpen.call(_22);
if(_24.maximized==true){
_24.maximized=false;
_26(_22);
}
if(_24.collapsed==true){
_24.collapsed=false;
_27(_22);
}
if(!_24.collapsed){
_1a(_22);
_1f(_22);
}
};
function _19(_28,_29){
var _2a=$.data(_28,"panel").options;
var _2b=$.data(_28,"panel").panel;
if(_29!=true){
if(_2a.onBeforeClose.call(_28)==false){
return;
}
}
_2b.hide();
_2a.closed=true;
_2a.onClose.call(_28);
};
function _2c(_2d,_2e){
var _2f=$.data(_2d,"panel").options;
var _30=$.data(_2d,"panel").panel;
if(_2e!=true){
if(_2f.onBeforeDestroy.call(_2d)==false){
return;
}
}
_1(_30);
_2f.onDestroy.call(_2d);
};
function _27(_31,_32){
var _33=$.data(_31,"panel").options;
var _34=$.data(_31,"panel").panel;
var _35=_34.children("div.panel-body");
var _36=_34.children("div.panel-header").find("a.panel-tool-collapse");
if(_33.collapsed==true){
return;
}
_35.stop(true,true);
if(_33.onBeforeCollapse.call(_31)==false){
return;
}
_36.addClass("panel-tool-expand");
if(_32==true){
_35.slideUp("normal",function(){
_33.collapsed=true;
_33.onCollapse.call(_31);
});
}else{
_35.hide();
_33.collapsed=true;
_33.onCollapse.call(_31);
}
};
function _37(_38,_39){
var _3a=$.data(_38,"panel").options;
var _3b=$.data(_38,"panel").panel;
var _3c=_3b.children("div.panel-body");
var _3d=_3b.children("div.panel-header").find("a.panel-tool-collapse");
if(_3a.collapsed==false){
return;
}
_3c.stop(true,true);
if(_3a.onBeforeExpand.call(_38)==false){
return;
}
_3d.removeClass("panel-tool-expand");
if(_39==true){
_3c.slideDown("normal",function(){
_3a.collapsed=false;
_3a.onExpand.call(_38);
_1a(_38);
_1f(_38);
});
}else{
_3c.show();
_3a.collapsed=false;
_3a.onExpand.call(_38);
_1a(_38);
_1f(_38);
}
};
function _26(_3e){
var _3f=$.data(_3e,"panel").options;
var _40=$.data(_3e,"panel").panel;
var _41=_40.children("div.panel-header").find("a.panel-tool-max");
if(_3f.maximized==true){
return;
}
_41.addClass("panel-tool-restore");
if(!$.data(_3e,"panel").original){
$.data(_3e,"panel").original={width:_3f.width,height:_3f.height,left:_3f.left,top:_3f.top,fit:_3f.fit};
}
_3f.left=0;
_3f.top=0;
_3f.fit=true;
_3(_3e);
_3f.minimized=false;
_3f.maximized=true;
_3f.onMaximize.call(_3e);
};
function _42(_43){
var _44=$.data(_43,"panel").options;
var _45=$.data(_43,"panel").panel;
_45.hide();
_44.minimized=true;
_44.maximized=false;
_44.onMinimize.call(_43);
};
function _46(_47){
var _48=$.data(_47,"panel").options;
var _49=$.data(_47,"panel").panel;
var _4a=_49.children("div.panel-header").find("a.panel-tool-max");
if(_48.maximized==false){
return;
}
_49.show();
_4a.removeClass("panel-tool-restore");
var _4b=$.data(_47,"panel").original;
_48.width=_4b.width;
_48.height=_4b.height;
_48.left=_4b.left;
_48.top=_4b.top;
_48.fit=_4b.fit;
_3(_47);
_48.minimized=false;
_48.maximized=false;
$.data(_47,"panel").original=null;
_48.onRestore.call(_47);
};
function _4c(_4d){
var _4e=$.data(_4d,"panel").options;
var _4f=$.data(_4d,"panel").panel;
if(_4e.border==true){
_4f.children("div.panel-header").removeClass("panel-header-noborder");
_4f.children("div.panel-body").removeClass("panel-body-noborder");
}else{
_4f.children("div.panel-header").addClass("panel-header-noborder");
_4f.children("div.panel-body").addClass("panel-body-noborder");
}
_4f.css(_4e.style);
_4f.addClass(_4e.cls);
_4f.children("div.panel-header").addClass(_4e.headerCls);
_4f.children("div.panel-body").addClass(_4e.bodyCls);
};
function _50(_51,_52){
$.data(_51,"panel").options.title=_52;
$(_51).panel("header").find("div.panel-title").html(_52);
};
var TO=false;
var _53=true;
$(window).unbind(".panel").bind("resize.panel",function(){
if(!_53){
return;
}
if(TO!==false){
clearTimeout(TO);
}
TO=setTimeout(function(){
_53=false;
var _54=$("body.layout");
if(_54.length){
_54.layout("resize");
}else{
$("body").children("div.panel,div.accordion,div.tabs-container,div.layout").triggerHandler("_resize");
}
_53=true;
TO=false;
},200);
});
$.fn.panel=function(_55,_56){
if(typeof _55=="string"){
return $.fn.panel.methods[_55](this,_56);
}
_55=_55||{};
return this.each(function(){
var _57=$.data(this,"panel");
var _58;
if(_57){
_58=$.extend(_57.options,_55);
}else{
_58=$.extend({},$.fn.panel.defaults,$.fn.panel.parseOptions(this),_55);
$(this).attr("title","");
_57=$.data(this,"panel",{options:_58,panel:_f(this),isLoaded:false});
}
if(_58.content){
$(this).html(_58.content);
if($.parser){
$.parser.parse(this);
}
}
_13(this);
_4c(this);
if(_58.doSize==true){
_57.panel.css("display","block");
_3(this);
}
if(_58.closed==true||_58.minimized==true){
_57.panel.hide();
}else{
_21(this);
}
});
};
$.fn.panel.methods={options:function(jq){
return $.data(jq[0],"panel").options;
},panel:function(jq){
return $.data(jq[0],"panel").panel;
},header:function(jq){
return $.data(jq[0],"panel").panel.find(">div.panel-header");
},body:function(jq){
return $.data(jq[0],"panel").panel.find(">div.panel-body");
},setTitle:function(jq,_59){
return jq.each(function(){
_50(this,_59);
});
},open:function(jq,_5a){
return jq.each(function(){
_21(this,_5a);
});
},close:function(jq,_5b){
return jq.each(function(){
_19(this,_5b);
});
},destroy:function(jq,_5c){
return jq.each(function(){
_2c(this,_5c);
});
},refresh:function(jq,_5d){
return jq.each(function(){
$.data(this,"panel").isLoaded=false;
if(_5d){
$.data(this,"panel").options.href=_5d;
}
_1a(this);
});
},resize:function(jq,_5e){
return jq.each(function(){
_3(this,_5e);
});
},move:function(jq,_5f){
return jq.each(function(){
_a(this,_5f);
});
},maximize:function(jq){
return jq.each(function(){
_26(this);
});
},minimize:function(jq){
return jq.each(function(){
_42(this);
});
},restore:function(jq){
return jq.each(function(){
_46(this);
});
},collapse:function(jq,_60){
return jq.each(function(){
_27(this,_60);
});
},expand:function(jq,_61){
return jq.each(function(){
_37(this,_61);
});
}};
$.fn.panel.parseOptions=function(_62){
var t=$(_62);
return {width:(parseInt(_62.style.width)||undefined),height:(parseInt(_62.style.height)||undefined),left:(parseInt(_62.style.left)||undefined),top:(parseInt(_62.style.top)||undefined),title:(t.attr("title")||undefined),iconCls:(t.attr("iconCls")||t.attr("icon")),cls:t.attr("cls"),headerCls:t.attr("headerCls"),bodyCls:t.attr("bodyCls"),tools:t.attr("tools"),href:t.attr("href"),loadingMessage:(t.attr("loadingMessage")!=undefined?t.attr("loadingMessage"):undefined),cache:(t.attr("cache")?t.attr("cache")=="true":undefined),fit:(t.attr("fit")?t.attr("fit")=="true":undefined),border:(t.attr("border")?t.attr("border")=="true":undefined),noheader:(t.attr("noheader")?t.attr("noheader")=="true":undefined),collapsible:(t.attr("collapsible")?t.attr("collapsible")=="true":undefined),minimizable:(t.attr("minimizable")?t.attr("minimizable")=="true":undefined),maximizable:(t.attr("maximizable")?t.attr("maximizable")=="true":undefined),closable:(t.attr("closable")?t.attr("closable")=="true":undefined),collapsed:(t.attr("collapsed")?t.attr("collapsed")=="true":undefined),minimized:(t.attr("minimized")?t.attr("minimized")=="true":undefined),maximized:(t.attr("maximized")?t.attr("maximized")=="true":undefined),closed:(t.attr("closed")?t.attr("closed")=="true":undefined)};
};
$.fn.panel.defaults={title:null,iconCls:null,width:"auto",height:"auto",left:null,top:null,cls:null,headerCls:null,bodyCls:null,style:{},href:null,cache:true,fit:false,border:true,doSize:true,noheader:false,content:null,collapsible:false,minimizable:false,maximizable:false,closable:false,collapsed:false,minimized:false,maximized:false,closed:false,tools:null,href:null,loadingMessage:"Loading...",extractor:function(_63){
var _64=/<body[^>]*>((.|[\n\r])*)<\/body>/im;
var _65=_64.exec(_63);
if(_65){
return _65[1];
}else{
return _63;
}
},onLoad:function(){
},onBeforeOpen:function(){
},onOpen:function(){
},onBeforeClose:function(){
},onClose:function(){
},onBeforeDestroy:function(){
},onDestroy:function(){
},onResize:function(_66,_67){
},onMove:function(_68,top){
},onMaximize:function(){
},onRestore:function(){
},onMinimize:function(){
},onBeforeCollapse:function(){
},onBeforeExpand:function(){
},onCollapse:function(){
},onExpand:function(){
}};
})(jQuery);

