/**
 * jQuery EasyUI 1.2.4
 * 
 * Licensed under the GPL terms
 * To use it on other terms please contact us
 *
 * Copyright(c) 2009-2011 stworthy [ stworthy@gmail.com ] 
 * 
 */
(function($){
function _1(_2){
var _3=$(">div.tabs-header",_2);
var _4=0;
$("ul.tabs li",_3).each(function(){
_4+=$(this).outerWidth(true);
});
var _5=$("div.tabs-wrap",_3).width();
var _6=parseInt($("ul.tabs",_3).css("padding-left"));
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
var _16=_15.children("div.tabs-tool");
_16.remove();
if(_14.tools){
_16=$("<div class=\"tabs-tool\"></div>").appendTo(_15);
for(var i=0;i<_14.tools.length;i++){
var _17=$("<a href=\"javascript:void(0);\"></a>").appendTo(_16);
_17[0].onclick=eval(_14.tools[i].handler||function(){
});
_17.linkbutton($.extend({},_14.tools[i],{plain:true}));
}
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
var _1b=$(">div.tabs-header",_19);
if($.boxModel==true){
_1b.width(_1a.width-(_1b.outerWidth()-_1b.width()));
}else{
_1b.width(_1a.width);
}
_7(_19);
var _1c=$(">div.tabs-panels",_19);
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
var _24=$(_21).find(">div.tabs-panels");
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
tp.children("div[selected]").attr("closed","false");
tp.children("div").not("div[selected]").attr("closed","true");
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
var _2f=$(">div.tabs-header",_2d);
var _30=$(">div.tabs-panels",_2d);
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
var _35=_34.panel("options").title;
tab.unbind(".tabs").bind("click.tabs",{title:_35},function(e){
_45(_2d,e.data.title);
}).bind("contextmenu.tabs",{title:_35},function(e){
_2e.onContextMenu.call(_2d,e,e.data.title);
});
tab.find("a.tabs-close").unbind(".tabs").bind("click.tabs",{title:_35},function(e){
_36(_2d,e.data.title);
return false;
});
}
};
function _37(_38,pp,_39){
_39=_39||{};
pp.panel($.extend({},_39,{border:false,noheader:true,doSize:false,iconCls:(_39.icon?_39.icon:undefined),onLoad:function(){
$.data(_38,"tabs").options.onLoad.call(_38,pp);
}}));
var _3a=pp.panel("options");
var _3b=$(">div.tabs-header",_38);
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
_3a.tab=tab;
};
function _40(_41,_42){
var _43=$.data(_41,"tabs").options;
var _44=$.data(_41,"tabs").tabs;
var pp=$("<div></div>").appendTo($(">div.tabs-panels",_41));
_44.push(pp);
_37(_41,pp,_42);
_43.onAdd.call(_41,_42.title);
_7(_41);
_2c(_41);
_45(_41,_42.title);
};
function _46(_47,_48){
var _49=$.data(_47,"tabs").selectHis;
var pp=_48.tab;
var _4a=pp.panel("options").title;
pp.panel($.extend({},_48.options,{iconCls:(_48.options.icon?_48.options.icon:undefined)}));
var _4b=pp.panel("options");
var tab=_4b.tab;
tab.find("span.tabs-icon").attr("class","tabs-icon");
tab.find("a.tabs-close").remove();
tab.find("span.tabs-title").html(_4b.title);
if(_4b.closable){
tab.find("span.tabs-title").addClass("tabs-closable");
$("<a href=\"javascript:void(0)\" class=\"tabs-close\"></a>").appendTo(tab);
}else{
tab.find("span.tabs-title").removeClass("tabs-closable");
}
if(_4b.iconCls){
tab.find("span.tabs-title").addClass("tabs-with-icon");
tab.find("span.tabs-icon").addClass(_4b.iconCls);
}else{
tab.find("span.tabs-title").removeClass("tabs-with-icon");
}
if(_4a!=_4b.title){
for(var i=0;i<_49.length;i++){
if(_49[i]==_4a){
_49[i]=_4b.title;
}
}
}
_2c(_47);
$.data(_47,"tabs").options.onUpdate.call(_47,_4b.title);
};
function _36(_4c,_4d){
var _4e=$.data(_4c,"tabs").options;
var _4f=$.data(_4c,"tabs").tabs;
var _50=$.data(_4c,"tabs").selectHis;
if(!_51(_4c,_4d)){
return;
}
if(_4e.onBeforeClose.call(_4c,_4d)==false){
return;
}
var tab=_52(_4c,_4d,true);
tab.panel("options").tab.remove();
tab.panel("destroy");
_4e.onClose.call(_4c,_4d);
_7(_4c);
for(var i=0;i<_50.length;i++){
if(_50[i]==_4d){
_50.splice(i,1);
i--;
}
}
var _53=_50.pop();
if(_53){
_45(_4c,_53);
}else{
if(_4f.length){
_45(_4c,_4f[0].panel("options").title);
}
}
};
function _52(_54,_55,_56){
var _57=$.data(_54,"tabs").tabs;
for(var i=0;i<_57.length;i++){
var tab=_57[i];
if(tab.panel("options").title==_55){
if(_56){
_57.splice(i,1);
}
return tab;
}
}
return null;
};
function _23(_58){
var _59=$.data(_58,"tabs").tabs;
for(var i=0;i<_59.length;i++){
var tab=_59[i];
if(tab.panel("options").closed==false){
return tab;
}
}
return null;
};
function _5a(_5b){
var _5c=$.data(_5b,"tabs").tabs;
for(var i=0;i<_5c.length;i++){
var tab=_5c[i];
if(!tab.panel("options").closed){
_45(_5b,tab.panel("options").title);
return;
}
}
if(_5c.length){
_45(_5b,_5c[0].panel("options").title);
}
};
function _45(_5d,_5e){
var _5f=$.data(_5d,"tabs").options;
var _60=$.data(_5d,"tabs").tabs;
var _61=$.data(_5d,"tabs").selectHis;
if(_60.length==0){
return;
}
var _62=_52(_5d,_5e);
if(!_62){
return;
}
var _63=_23(_5d);
if(_63){
_63.panel("close");
_63.panel("options").tab.removeClass("tabs-selected");
}
_62.panel("open");
var tab=_62.panel("options").tab;
tab.addClass("tabs-selected");
var _64=$(_5d).find(">div.tabs-header div.tabs-wrap");
var _65=tab.position().left+_64.scrollLeft();
var _66=_65-_64.scrollLeft();
var _67=_66+tab.outerWidth();
if(_66<0||_67>_64.innerWidth()){
var pos=Math.min(_65-(_64.width()-tab.width())/2,_1(_5d));
_64.animate({scrollLeft:pos},_5f.scrollDuration);
}else{
var pos=Math.min(_64.scrollLeft(),_1(_5d));
_64.animate({scrollLeft:pos},_5f.scrollDuration);
}
_20(_5d);
_61.push(_5e);
_5f.onSelect.call(_5d,_5e);
};
function _51(_68,_69){
return _52(_68,_69)!=null;
};
$.fn.tabs=function(_6a,_6b){
if(typeof _6a=="string"){
return $.fn.tabs.methods[_6a](this,_6b);
}
_6a=_6a||{};
return this.each(function(){
var _6c=$.data(this,"tabs");
var _6d;
if(_6c){
_6d=$.extend(_6c.options,_6a);
_6c.options=_6d;
}else{
$.data(this,"tabs",{options:$.extend({},$.fn.tabs.defaults,$.fn.tabs.parseOptions(this),_6a),tabs:_27(this),selectHis:[]});
}
_12(this);
_2c(this);
_18(this);
_5a(this);
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
},add:function(jq,_6e){
return jq.each(function(){
_40(this,_6e);
});
},close:function(jq,_6f){
return jq.each(function(){
_36(this,_6f);
});
},getTab:function(jq,_70){
return _52(jq[0],_70);
},getSelected:function(jq){
return _23(jq[0]);
},select:function(jq,_71){
return jq.each(function(){
_45(this,_71);
});
},exists:function(jq,_72){
return _51(jq[0],_72);
},update:function(jq,_73){
return jq.each(function(){
_46(this,_73);
});
}};
$.fn.tabs.parseOptions=function(_74){
var t=$(_74);
return {width:(parseInt(_74.style.width)||undefined),height:(parseInt(_74.style.height)||undefined),fit:(t.attr("fit")?t.attr("fit")=="true":undefined),border:(t.attr("border")?t.attr("border")=="true":undefined),plain:(t.attr("plain")?t.attr("plain")=="true":undefined)};
};
$.fn.tabs.defaults={width:"auto",height:"auto",plain:false,fit:false,border:true,tools:null,scrollIncrement:100,scrollDuration:400,onLoad:function(_75){
},onSelect:function(_76){
},onBeforeClose:function(_77){
},onClose:function(_78){
},onAdd:function(_79){
},onUpdate:function(_7a){
},onContextMenu:function(e,_7b){
}};
})(jQuery);

