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
var _3=$(_2).children("div.tabs-header");
var _4=0;
$("ul.tabs li",_3).each(function(){
_4+=$(this).outerWidth(true);
});
var _5=_3.children("div.tabs-wrap").width();
var _6=parseInt(_3.find("ul.tabs").css("padding-left"));
return _4-_5+_6;
};
function _7(_8){
var _9=$.data(_8,"tabs").options;
var _a=$(_8).children("div.tabs-header");
var _b=_a.children("div.tabs-tool");
var _c=_a.children("div.tabs-scroller-left");
var _d=_a.children("div.tabs-scroller-right");
var _e=_a.children("div.tabs-wrap");
var _f=($.boxModel==true?(_a.outerHeight()-(_b.outerHeight()-_b.height())):_a.outerHeight());
if(_9.plain){
_f-=2;
}
_b.height(_f);
var _10=0;
$("ul.tabs li",_a).each(function(){
_10+=$(this).outerWidth(true);
});
var _11=_a.width()-_b.outerWidth();
if(_10>_11){
_c.show();
_d.show();
_b.css("right",_d.outerWidth());
_e.css({marginLeft:_c.outerWidth(),marginRight:_d.outerWidth()+_b.outerWidth(),left:0,width:_11-_c.outerWidth()-_d.outerWidth()});
}else{
_c.hide();
_d.hide();
_b.css("right",0);
_e.css({marginLeft:0,marginRight:_b.outerWidth(),left:0,width:_11});
_e.scrollLeft(0);
}
};
function _12(_13){
var _14=$.data(_13,"tabs").options;
var _15=$(_13).children("div.tabs-header");
if(_14.tools){
if(typeof _14.tools=="string"){
$(_14.tools).addClass("tabs-tool").appendTo(_15);
$(_14.tools).show();
}else{
_15.children("div.tabs-tool").remove();
var _16=$("<div class=\"tabs-tool\"></div>").appendTo(_15);
for(var i=0;i<_14.tools.length;i++){
var _17=$("<a href=\"javascript:void(0);\"></a>").appendTo(_16);
_17[0].onclick=eval(_14.tools[i].handler||function(){
});
_17.linkbutton($.extend({},_14.tools[i],{plain:true}));
}
}
}else{
_15.children("div.tabs-tool").remove();
}
};
function _18(_19){
var _1a=$.data(_19,"tabs").options;
var cc=$(_19);
if(_1a.fit==true){
var p=cc.parent();
_1a.width=p.width();
_1a.height=p.height();
}
cc.width(_1a.width).height(_1a.height);
var _1b=$(_19).children("div.tabs-header");
if($.boxModel==true){
_1b.width(_1a.width-(_1b.outerWidth()-_1b.width()));
}else{
_1b.width(_1a.width);
}
_7(_19);
var _1c=$(_19).children("div.tabs-panels");
var _1d=_1a.height;
if(!isNaN(_1d)){
if($.boxModel==true){
var _1e=_1c.outerHeight()-_1c.height();
_1c.css("height",(_1d-_1b.outerHeight()-_1e)||"auto");
}else{
_1c.css("height",_1d-_1b.outerHeight());
}
}else{
_1c.height("auto");
}
var _1f=_1a.width;
if(!isNaN(_1f)){
if($.boxModel==true){
_1c.width(_1f-(_1c.outerWidth()-_1c.width()));
}else{
_1c.width(_1f);
}
}else{
_1c.width("auto");
}
};
function _20(_21){
var _22=$.data(_21,"tabs").options;
var tab=_23(_21);
if(tab){
var _24=$(_21).children("div.tabs-panels");
var _25=_22.width=="auto"?"auto":_24.width();
var _26=_22.height=="auto"?"auto":_24.height();
tab.panel("resize",{width:_25,height:_26});
}
};
function _27(_28){
var cc=$(_28);
cc.addClass("tabs-container");
cc.wrapInner("<div class=\"tabs-panels\"/>");
$("<div class=\"tabs-header\">"+"<div class=\"tabs-scroller-left\"></div>"+"<div class=\"tabs-scroller-right\"></div>"+"<div class=\"tabs-wrap\">"+"<ul class=\"tabs\"></ul>"+"</div>"+"</div>").prependTo(_28);
var _29=[];
var tp=cc.children("div.tabs-panels");
tp.children("div[selected]").attr("toselect","true");
tp.children("div").each(function(){
var pp=$(this);
_29.push(pp);
_37(_28,pp);
});
cc.children("div.tabs-header").find(".tabs-scroller-left, .tabs-scroller-right").hover(function(){
$(this).addClass("tabs-scroller-over");
},function(){
$(this).removeClass("tabs-scroller-over");
});
cc.bind("_resize",function(e,_2a){
var _2b=$.data(_28,"tabs").options;
if(_2b.fit==true||_2a){
_18(_28);
_20(_28);
}
return false;
});
return _29;
};
function _2c(_2d){
var _2e=$.data(_2d,"tabs").options;
var _2f=$(_2d).children("div.tabs-header");
var _30=$(_2d).children("div.tabs-panels");
if(_2e.plain==true){
_2f.addClass("tabs-header-plain");
}else{
_2f.removeClass("tabs-header-plain");
}
if(_2e.border==true){
_2f.removeClass("tabs-header-noborder");
_30.removeClass("tabs-panels-noborder");
}else{
_2f.addClass("tabs-header-noborder");
_30.addClass("tabs-panels-noborder");
}
$(".tabs-scroller-left",_2f).unbind(".tabs").bind("click.tabs",function(){
var _31=$(".tabs-wrap",_2f);
var pos=_31.scrollLeft()-_2e.scrollIncrement;
_31.animate({scrollLeft:pos},_2e.scrollDuration);
});
$(".tabs-scroller-right",_2f).unbind(".tabs").bind("click.tabs",function(){
var _32=$(".tabs-wrap",_2f);
var pos=Math.min(_32.scrollLeft()+_2e.scrollIncrement,_1(_2d));
_32.animate({scrollLeft:pos},_2e.scrollDuration);
});
var _33=$.data(_2d,"tabs").tabs;
for(var i=0,len=_33.length;i<len;i++){
var _34=_33[i];
var tab=_34.panel("options").tab;
tab.unbind(".tabs").bind("click.tabs",{p:_34},function(e){
_46(_2d,_36(_2d,e.data.p));
}).bind("contextmenu.tabs",{p:_34},function(e){
_2e.onContextMenu.call(_2d,e,e.data.p.panel("options").title);
});
tab.find("a.tabs-close").unbind(".tabs").bind("click.tabs",{p:_34},function(e){
_35(_2d,_36(_2d,e.data.p));
return false;
});
}
};
function _37(_38,pp,_39){
_39=_39||{};
pp.panel($.extend({},_39,{border:false,noheader:true,closed:true,doSize:false,iconCls:(_39.icon?_39.icon:undefined),onLoad:function(){
if(_39.onLoad){
_39.onLoad.call(this,arguments);
}
$.data(_38,"tabs").options.onLoad.call(_38,pp);
}}));
var _3a=pp.panel("options");
var _3b=$(_38).children("div.tabs-header");
var _3c=$("ul.tabs",_3b);
var tab=$("<li></li>").appendTo(_3c);
var _3d=$("<a href=\"javascript:void(0)\" class=\"tabs-inner\"></a>").appendTo(tab);
var _3e=$("<span class=\"tabs-title\"></span>").html(_3a.title).appendTo(_3d);
var _3f=$("<span class=\"tabs-icon\"></span>").appendTo(_3d);
if(_3a.closable){
_3e.addClass("tabs-closable");
$("<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>").appendTo(tab);
}
if(_3a.iconCls){
_3e.addClass("tabs-with-icon");
_3f.addClass(_3a.iconCls);
}
if(_3a.tools){
var _40=$("<span class=\"tabs-p-tool\"></span>").insertAfter(_3d);
if(typeof _3a.tools=="string"){
$(_3a.tools).children().appendTo(_40);
}else{
for(var i=0;i<_3a.tools.length;i++){
var t=$("<a href=\"javascript:void(0)\"></a>").appendTo(_40);
t.addClass(_3a.tools[i].iconCls);
if(_3a.tools[i].handler){
t.bind("click",eval(_3a.tools[i].handler));
}
}
}
var pr=_40.children().length*12;
if(_3a.closable){
pr+=8;
}else{
pr-=3;
_40.css("right","5px");
}
_3e.css("padding-right",pr+"px");
}
_3a.tab=tab;
};
function _41(_42,_43){
var _44=$.data(_42,"tabs").options;
var _45=$.data(_42,"tabs").tabs;
var pp=$("<div></div>").appendTo($(_42).children("div.tabs-panels"));
_45.push(pp);
_37(_42,pp,_43);
_44.onAdd.call(_42,_43.title);
_7(_42);
_2c(_42);
_46(_42,_45.length-1);
};
function _47(_48,_49){
var _4a=$.data(_48,"tabs").selectHis;
var pp=_49.tab;
var _4b=pp.panel("options").title;
pp.panel($.extend({},_49.options,{iconCls:(_49.options.icon?_49.options.icon:undefined)}));
var _4c=pp.panel("options");
var tab=_4c.tab;
tab.find("span.tabs-icon").attr("class","tabs-icon");
tab.find("a.tabs-close").remove();
tab.find("span.tabs-title").html(_4c.title);
if(_4c.closable){
tab.find("span.tabs-title").addClass("tabs-closable");
$("<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>").appendTo(tab);
}else{
tab.find("span.tabs-title").removeClass("tabs-closable");
}
if(_4c.iconCls){
tab.find("span.tabs-title").addClass("tabs-with-icon");
tab.find("span.tabs-icon").addClass(_4c.iconCls);
}else{
tab.find("span.tabs-title").removeClass("tabs-with-icon");
}
if(_4b!=_4c.title){
for(var i=0;i<_4a.length;i++){
if(_4a[i]==_4b){
_4a[i]=_4c.title;
}
}
}
_2c(_48);
$.data(_48,"tabs").options.onUpdate.call(_48,_4c.title);
};
function _35(_4d,_4e){
var _4f=$.data(_4d,"tabs").options;
var _50=$.data(_4d,"tabs").tabs;
var _51=$.data(_4d,"tabs").selectHis;
if(!_52(_4d,_4e)){
return;
}
var tab=_53(_4d,_4e);
var _54=tab.panel("options").title;
if(_4f.onBeforeClose.call(_4d,_54)==false){
return;
}
var tab=_53(_4d,_4e,true);
tab.panel("options").tab.remove();
tab.panel("destroy");
_4f.onClose.call(_4d,_54);
_7(_4d);
for(var i=0;i<_51.length;i++){
if(_51[i]==_54){
_51.splice(i,1);
i--;
}
}
var _55=_51.pop();
if(_55){
_46(_4d,_55);
}else{
if(_50.length){
_46(_4d,0);
}
}
};
function _53(_56,_57,_58){
var _59=$.data(_56,"tabs").tabs;
if(typeof _57=="number"){
if(_57<0||_57>=_59.length){
return null;
}else{
var tab=_59[_57];
if(_58){
_59.splice(_57,1);
}
return tab;
}
}
for(var i=0;i<_59.length;i++){
var tab=_59[i];
if(tab.panel("options").title==_57){
if(_58){
_59.splice(i,1);
}
return tab;
}
}
return null;
};
function _36(_5a,tab){
var _5b=$.data(_5a,"tabs").tabs;
for(var i=0;i<_5b.length;i++){
if(_5b[i][0]==$(tab)[0]){
return i;
}
}
return -1;
};
function _23(_5c){
var _5d=$.data(_5c,"tabs").tabs;
for(var i=0;i<_5d.length;i++){
var tab=_5d[i];
if(tab.panel("options").closed==false){
return tab;
}
}
return null;
};
function _5e(_5f){
var _60=$.data(_5f,"tabs").tabs;
for(var i=0;i<_60.length;i++){
if(_60[i].attr("toselect")=="true"){
_46(_5f,i);
return;
}
}
if(_60.length){
_46(_5f,0);
}
};
function _46(_61,_62){
var _63=$.data(_61,"tabs").options;
var _64=$.data(_61,"tabs").tabs;
var _65=$.data(_61,"tabs").selectHis;
if(_64.length==0){
return;
}
var _66=_53(_61,_62);
if(!_66){
return;
}
var _67=_23(_61);
if(_67){
_67.panel("close");
_67.panel("options").tab.removeClass("tabs-selected");
}
_66.panel("open");
var _68=_66.panel("options").title;
_65.push(_68);
var tab=_66.panel("options").tab;
tab.addClass("tabs-selected");
var _69=$(_61).find(">div.tabs-header div.tabs-wrap");
var _6a=tab.position().left+_69.scrollLeft();
var _6b=_6a-_69.scrollLeft();
var _6c=_6b+tab.outerWidth();
if(_6b<0||_6c>_69.innerWidth()){
var pos=Math.min(_6a-(_69.width()-tab.width())/2,_1(_61));
_69.animate({scrollLeft:pos},_63.scrollDuration);
}else{
var pos=Math.min(_69.scrollLeft(),_1(_61));
_69.animate({scrollLeft:pos},_63.scrollDuration);
}
_20(_61);
_63.onSelect.call(_61,_68);
};
function _52(_6d,_6e){
return _53(_6d,_6e)!=null;
};
$.fn.tabs=function(_6f,_70){
if(typeof _6f=="string"){
return $.fn.tabs.methods[_6f](this,_70);
}
_6f=_6f||{};
return this.each(function(){
var _71=$.data(this,"tabs");
var _72;
if(_71){
_72=$.extend(_71.options,_6f);
_71.options=_72;
}else{
$.data(this,"tabs",{options:$.extend({},$.fn.tabs.defaults,$.fn.tabs.parseOptions(this),_6f),tabs:_27(this),selectHis:[]});
}
_12(this);
_2c(this);
_18(this);
_5e(this);
});
};
$.fn.tabs.methods={options:function(jq){
return $.data(jq[0],"tabs").options;
},tabs:function(jq){
return $.data(jq[0],"tabs").tabs;
},resize:function(jq){
return jq.each(function(){
_18(this);
_20(this);
});
},add:function(jq,_73){
return jq.each(function(){
_41(this,_73);
});
},close:function(jq,_74){
return jq.each(function(){
_35(this,_74);
});
},getTab:function(jq,_75){
return _53(jq[0],_75);
},getTabIndex:function(jq,tab){
return _36(jq[0],tab);
},getSelected:function(jq){
return _23(jq[0]);
},select:function(jq,_76){
return jq.each(function(){
_46(this,_76);
});
},exists:function(jq,_77){
return _52(jq[0],_77);
},update:function(jq,_78){
return jq.each(function(){
_47(this,_78);
});
}};
$.fn.tabs.parseOptions=function(_79){
var t=$(_79);
return {width:(parseInt(_79.style.width)||undefined),height:(parseInt(_79.style.height)||undefined),fit:(t.attr("fit")?t.attr("fit")=="true":undefined),border:(t.attr("border")?t.attr("border")=="true":undefined),plain:(t.attr("plain")?t.attr("plain")=="true":undefined),tools:t.attr("tools")};
};
$.fn.tabs.defaults={width:"auto",height:"auto",plain:false,fit:false,border:true,tools:null,scrollIncrement:100,scrollDuration:400,onLoad:function(_7a){
},onSelect:function(_7b){
},onBeforeClose:function(_7c){
},onClose:function(_7d){
},onAdd:function(_7e){
},onUpdate:function(_7f){
},onContextMenu:function(e,_80){
}};
})(jQuery);

