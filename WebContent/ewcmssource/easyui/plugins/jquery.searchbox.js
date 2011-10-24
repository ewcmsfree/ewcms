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
$(_2).hide();
var _3=$("<span class=\"searchbox\"></span>").insertAfter(_2);
var _4=$("<input type=\"text\" class=\"searchbox-text\">").appendTo(_3);
$("<span><span class=\"searchbox-button\"></span></span>").appendTo(_3);
var _5=$(_2).attr("name");
if(_5){
_4.attr("name",_5);
$(_2).removeAttr("name").attr("searchboxName",_5);
}
return _3;
};
function _6(_7){
var _8=$.data(_7,"searchbox").options;
var sb=$.data(_7,"searchbox").searchbox;
if(_9){
_8.width=_9;
}
sb.appendTo("body");
if(isNaN(_8.width)){
_8.width=sb.find("input.searchbox.text").outerWidth();
}
var _9=_8.width-sb.find("a.searchbox-menu").outerWidth()-sb.find("span.searchbox-button").outerWidth();
if($.boxModel==true){
_9-=sb.outerWidth()-sb.width();
}
sb.find("input.searchbox-text").width(_9);
sb.insertAfter(_7);
};
function _a(_b){
var _c=$.data(_b,"searchbox");
var _d=_c.options;
if(_d.menu){
_c.menu=$(_d.menu).menu({onClick:function(_e){
_f(_e);
}});
var _10=_c.menu.menu("getItem",_c.menu.children("div.menu-item")[0]);
_c.menu.children("div.menu-item").triggerHandler("click");
}else{
_c.searchbox.find("a.searchbox-menu").remove();
_c.menu=null;
}
function _f(_11){
_c.searchbox.find("a.searchbox-menu").remove();
var mb=$("<a class=\"searchbox-menu\" href=\"javascript:void(0)\"></a>").html(_11.text);
mb.prependTo(_c.searchbox).menubutton({menu:_c.menu,iconCls:_11.iconCls});
_c.searchbox.find("input.searchbox-text").attr("name",$(_11.target).attr("name")||_11.text);
_6(_b);
};
};
function _12(_13){
var _14=$.data(_13,"searchbox");
var _15=_14.options;
var _16=_14.searchbox.find("input.searchbox-text");
var _17=_14.searchbox.find(".searchbox-button");
_16.unbind(".searchbox").bind("blur.searchbox",function(e){
_15.value=$(this).val();
if(_15.value==""){
$(this).val(_15.prompt);
$(this).addClass("searchbox-prompt");
}else{
$(this).removeClass("searchbox-prompt");
}
}).bind("focus.searchbox",function(e){
if($(this).val()!=_15.value){
$(this).val(_15.value);
}
$(this).removeClass("searchbox-prompt");
}).bind("keydown.searchbox",function(e){
if(e.keyCode==13){
e.preventDefault();
_15.value=$(this).val();
_15.searcher.call(_13,_15.value,_16.attr("name"));
return false;
}
});
_17.unbind(".searchbox").bind("click.searchbox",function(){
_15.searcher.call(_13,_15.value,_16.attr("name"));
}).bind("mouseenter.searchbox",function(){
$(this).addClass("searchbox-button-hover");
}).bind("mouseleave.searchbox",function(){
$(this).removeClass("searchbox-button-hover");
});
};
function _18(_19){
var _1a=$.data(_19,"searchbox");
var _1b=_1a.options;
var _1c=_1a.searchbox.find("input.searchbox-text");
if(_1b.value==""){
_1c.val(_1b.prompt);
_1c.addClass("searchbox-prompt");
}else{
_1c.val(_1b.value);
_1c.removeClass("searchbox-prompt");
}
};
$.fn.searchbox=function(_1d,_1e){
if(typeof _1d=="string"){
return $.fn.searchbox.methods[_1d](this,_1e);
}
_1d=_1d||{};
return this.each(function(){
var _1f=$.data(this,"searchbox");
if(_1f){
$.extend(_1f.options,_1d);
}else{
_1f=$.data(this,"searchbox",{options:$.extend({},$.fn.searchbox.defaults,$.fn.searchbox.parseOptions(this),_1d),searchbox:_1(this)});
}
_a(this);
_18(this);
_12(this);
_6(this);
});
};
$.fn.searchbox.methods={options:function(jq){
return $.data(jq[0],"searchbox").options;
},menu:function(jq){
return $.data(jq[0],"searchbox").menu;
},textbox:function(jq){
return $.data(jq[0],"searchbox").searchbox.find("input.searchbox-text");
},getValue:function(jq){
return $.data(jq[0],"searchbox").options.value;
},setValue:function(jq,_20){
return jq.each(function(){
$(this).searchbox("options").value=_20;
$(this).searchbox("textbox").val(_20);
$(this).searchbox("textbox").blur();
});
},getName:function(jq){
return $.data(jq[0],"searchbox").searchbox.find("input.searchbox-text").attr("name");
},destroy:function(jq){
return jq.each(function(){
var _21=$(this).searchbox("menu");
if(_21){
_21.menu("destroy");
}
$.data(this,"searchbox").searchbox.remove();
$(this).remove();
});
},resize:function(jq,_22){
return jq.each(function(){
_6(this,_22);
});
}};
$.fn.searchbox.parseOptions=function(_23){
var t=$(_23);
return {width:(parseInt(_23.style.width)||undefined),prompt:t.attr("prompt"),value:t.val(),menu:t.attr("menu"),searcher:(t.attr("searcher")?eval(t.attr("searcher")):undefined)};
};
$.fn.searchbox.defaults={width:"auto",prompt:"",value:"",menu:null,searcher:function(_24,_25){
}};
})(jQuery);

