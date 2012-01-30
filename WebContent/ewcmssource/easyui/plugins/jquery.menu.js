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
$(_2).appendTo("body");
$(_2).addClass("menu-top");
var _3=[];
_4($(_2));
var _5=null;
for(var i=0;i<_3.length;i++){
var _6=_3[i];
_7(_6);
_6.children("div.menu-item").each(function(){
_10(_2,$(this));
});
_6.bind("mouseenter",function(){
if(_5){
clearTimeout(_5);
_5=null;
}
}).bind("mouseleave",function(){
_5=setTimeout(function(){
_19(_2);
},100);
});
}
function _4(_8){
_3.push(_8);
_8.find(">div").each(function(){
var _9=$(this);
var _a=_9.find(">div");
if(_a.length){
_a.insertAfter(_2);
_9[0].submenu=_a;
_4(_a);
}
});
};
function _7(_b){
_b.addClass("menu").find(">div").each(function(){
var _c=$(this);
if(_c.hasClass("menu-sep")){
_c.html("&nbsp;");
}else{
var _d=_c.addClass("menu-item").html();
_c.empty().append($("<div class=\"menu-text\"></div>").html(_d));
var _e=_c.attr("iconCls")||_c.attr("icon");
if(_e){
$("<div class=\"menu-icon\"></div>").addClass(_e).appendTo(_c);
}
if(_c[0].submenu){
$("<div class=\"menu-rightarrow\"></div>").appendTo(_c);
}
if($.boxModel==true){
var _f=_c.height();
_c.height(_f-(_c.outerHeight()-_c.height()));
}
}
});
_b.hide();
};
};
function _10(_11,_12){
_12.unbind(".menu");
_12.bind("mousedown.menu",function(){
return false;
}).bind("click.menu",function(){
if($(this).hasClass("menu-item-disabled")){
return;
}
if(!this.submenu){
_19(_11);
var _13=$(this).attr("href");
if(_13){
location.href=_13;
}
}
var _14=$(_11).menu("getItem",this);
$.data(_11,"menu").options.onClick.call(_11,_14);
}).bind("mouseenter.menu",function(e){
_12.siblings().each(function(){
if(this.submenu){
_18(this.submenu);
}
$(this).removeClass("menu-active");
});
_12.addClass("menu-active");
if($(this).hasClass("menu-item-disabled")){
_12.addClass("menu-active-disabled");
return;
}
var _15=_12[0].submenu;
if(_15){
var _16=_12.offset().left+_12.outerWidth()-2;
if(_16+_15.outerWidth()+5>$(window).width()+$(document).scrollLeft()){
_16=_12.offset().left-_15.outerWidth()+2;
}
var top=_12.offset().top-3;
if(top+_15.outerHeight()>$(window).height()+$(document).scrollTop()){
top=$(window).height()+$(document).scrollTop()-_15.outerHeight()-5;
}
_1f(_15,{left:_16,top:top});
}
}).bind("mouseleave.menu",function(e){
_12.removeClass("menu-active menu-active-disabled");
var _17=_12[0].submenu;
if(_17){
if(e.pageX>=parseInt(_17.css("left"))){
_12.addClass("menu-active");
}else{
_18(_17);
}
}else{
_12.removeClass("menu-active");
}
});
};
function _19(_1a){
var _1b=$.data(_1a,"menu").options;
_18($(_1a));
$(document).unbind(".menu");
_1b.onHide.call(_1a);
return false;
};
function _1c(_1d,pos){
var _1e=$.data(_1d,"menu").options;
if(pos){
_1e.left=pos.left;
_1e.top=pos.top;
if(_1e.left+$(_1d).outerWidth()>$(window).width()+$(document).scrollLeft()){
_1e.left=$(window).width()+$(document).scrollLeft()-$(_1d).outerWidth()-5;
}
if(_1e.top+$(_1d).outerHeight()>$(window).height()+$(document).scrollTop()){
_1e.top-=$(_1d).outerHeight();
}
}
_1f($(_1d),{left:_1e.left,top:_1e.top},function(){
$(document).unbind(".menu").bind("mousedown.menu",function(){
_19(_1d);
$(document).unbind(".menu");
return false;
});
_1e.onShow.call(_1d);
});
};
function _1f(_20,pos,_21){
if(!_20){
return;
}
if(pos){
_20.css(pos);
}
_20.show(0,function(){
if(!_20[0].shadow){
_20[0].shadow=$("<div class=\"menu-shadow\"></div>").insertAfter(_20);
}
_20[0].shadow.css({display:"block",zIndex:$.fn.menu.defaults.zIndex++,left:_20.css("left"),top:_20.css("top"),width:_20.outerWidth(),height:_20.outerHeight()});
_20.css("z-index",$.fn.menu.defaults.zIndex++);
if(_21){
_21();
}
});
};
function _18(_22){
if(!_22){
return;
}
_23(_22);
_22.find("div.menu-item").each(function(){
if(this.submenu){
_18(this.submenu);
}
$(this).removeClass("menu-active");
});
function _23(m){
m.stop(true,true);
if(m[0].shadow){
m[0].shadow.hide();
}
m.hide();
};
};
function _24(_25,_26){
var _27=null;
var tmp=$("<div></div>");
function _28(_29){
_29.children("div.menu-item").each(function(){
var _2a=$(_25).menu("getItem",this);
var s=tmp.empty().html(_2a.text).text();
if(_26==$.trim(s)){
_27=_2a;
}else{
if(this.submenu&&!_27){
_28(this.submenu);
}
}
});
};
_28($(_25));
tmp.remove();
return _27;
};
function _2b(_2c,_2d,_2e){
var t=$(_2d);
if(_2e){
t.addClass("menu-item-disabled");
if(_2d.onclick){
_2d.onclick1=_2d.onclick;
_2d.onclick=null;
}
}else{
t.removeClass("menu-item-disabled");
if(_2d.onclick1){
_2d.onclick=_2d.onclick1;
_2d.onclick1=null;
}
}
};
function _2f(_30,_31){
var _32=$(_30);
if(_31.parent){
_32=_31.parent.submenu;
}
var _33=$("<div class=\"menu-item\"></div>").appendTo(_32);
$("<div class=\"menu-text\"></div>").html(_31.text).appendTo(_33);
if(_31.iconCls){
$("<div class=\"menu-icon\"></div>").addClass(_31.iconCls).appendTo(_33);
}
if(_31.id){
_33.attr("id",_31.id);
}
if(_31.href){
_33.attr("href",_31.href);
}
if(_31.onclick){
if(typeof _31.onclick=="string"){
_33.attr("onclick",_31.onclick);
}else{
_33[0].onclick=eval(_31.onclick);
}
}
if(_31.handler){
_33[0].onclick=eval(_31.handler);
}
_10(_30,_33);
};
function _34(_35,_36){
function _37(el){
if(el.submenu){
el.submenu.children("div.menu-item").each(function(){
_37(this);
});
var _38=el.submenu[0].shadow;
if(_38){
_38.remove();
}
el.submenu.remove();
}
$(el).remove();
};
_37(_36);
};
function _39(_3a){
$(_3a).children("div.menu-item").each(function(){
_34(_3a,this);
});
if(_3a.shadow){
_3a.shadow.remove();
}
$(_3a).remove();
};
$.fn.menu=function(_3b,_3c){
if(typeof _3b=="string"){
return $.fn.menu.methods[_3b](this,_3c);
}
_3b=_3b||{};
return this.each(function(){
var _3d=$.data(this,"menu");
if(_3d){
$.extend(_3d.options,_3b);
}else{
_3d=$.data(this,"menu",{options:$.extend({},$.fn.menu.defaults,_3b)});
_1(this);
}
$(this).css({left:_3d.options.left,top:_3d.options.top});
});
};
$.fn.menu.methods={show:function(jq,pos){
return jq.each(function(){
_1c(this,pos);
});
},hide:function(jq){
return jq.each(function(){
_19(this);
});
},destroy:function(jq){
return jq.each(function(){
_39(this);
});
},setText:function(jq,_3e){
return jq.each(function(){
$(_3e.target).children("div.menu-text").html(_3e.text);
});
},setIcon:function(jq,_3f){
return jq.each(function(){
var _40=$(this).menu("getItem",_3f.target);
if(_40.iconCls){
$(_40.target).children("div.menu-icon").removeClass(_40.iconCls).addClass(_3f.iconCls);
}else{
$("<div class=\"menu-icon\"></div>").addClass(_3f.iconCls).appendTo(_3f.target);
}
});
},getItem:function(jq,_41){
var _42={target:_41,id:$(_41).attr("id"),text:$.trim($(_41).children("div.menu-text").html()),disabled:$(_41).hasClass("menu-item-disabled"),href:$(_41).attr("href"),onclick:_41.onclick};
var _43=$(_41).children("div.menu-icon");
if(_43.length){
var cc=[];
var aa=_43.attr("class").split(" ");
for(var i=0;i<aa.length;i++){
if(aa[i]!="menu-icon"){
cc.push(aa[i]);
}
}
_42.iconCls=cc.join(" ");
}
return _42;
},findItem:function(jq,_44){
return _24(jq[0],_44);
},appendItem:function(jq,_45){
return jq.each(function(){
_2f(this,_45);
});
},removeItem:function(jq,_46){
return jq.each(function(){
_34(this,_46);
});
},enableItem:function(jq,_47){
return jq.each(function(){
_2b(this,_47,false);
});
},disableItem:function(jq,_48){
return jq.each(function(){
_2b(this,_48,true);
});
}};
$.fn.menu.defaults={zIndex:110000,left:0,top:0,onShow:function(){
},onHide:function(){
},onClick:function(_49){
}};
})(jQuery);

