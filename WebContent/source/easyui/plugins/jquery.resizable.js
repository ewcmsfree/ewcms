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
$.fn.resizable=function(_1,_2){
if(typeof _1=="string"){
return $.fn.resizable.methods[_1](this,_2);
}
function _3(e){
var _4=e.data;
var _5=$.data(_4.target,"resizable").options;
if(_4.dir.indexOf("e")!=-1){
var _6=_4.startWidth+e.pageX-_4.startX;
_6=Math.min(Math.max(_6,_5.minWidth),_5.maxWidth);
_4.width=_6;
}
if(_4.dir.indexOf("s")!=-1){
var _7=_4.startHeight+e.pageY-_4.startY;
_7=Math.min(Math.max(_7,_5.minHeight),_5.maxHeight);
_4.height=_7;
}
if(_4.dir.indexOf("w")!=-1){
_4.width=_4.startWidth-e.pageX+_4.startX;
if(_4.width>=_5.minWidth&&_4.width<=_5.maxWidth){
_4.left=_4.startLeft+e.pageX-_4.startX;
}
}
if(_4.dir.indexOf("n")!=-1){
_4.height=_4.startHeight-e.pageY+_4.startY;
if(_4.height>=_5.minHeight&&_4.height<=_5.maxHeight){
_4.top=_4.startTop+e.pageY-_4.startY;
}
}
};
function _8(e){
var _9=e.data;
var _a=_9.target;
if($.boxModel==true){
$(_a).css({width:_9.width-_9.deltaWidth,height:_9.height-_9.deltaHeight,left:_9.left,top:_9.top});
}else{
$(_a).css({width:_9.width,height:_9.height,left:_9.left,top:_9.top});
}
};
function _b(e){
$.data(e.data.target,"resizable").options.onStartResize.call(e.data.target,e);
return false;
};
function _c(e){
_3(e);
if($.data(e.data.target,"resizable").options.onResize.call(e.data.target,e)!=false){
_8(e);
}
return false;
};
function _d(e){
_3(e,true);
_8(e);
$.data(e.data.target,"resizable").options.onStopResize.call(e.data.target,e);
$(document).unbind(".resizable");
$("body").css("cursor","default");
return false;
};
return this.each(function(){
var _e=null;
var _f=$.data(this,"resizable");
if(_f){
$(this).unbind(".resizable");
_e=$.extend(_f.options,_1||{});
}else{
_e=$.extend({},$.fn.resizable.defaults,_1||{});
$.data(this,"resizable",{options:_e});
}
if(_e.disabled==true){
return;
}
var _10=this;
$(this).bind("mousemove.resizable",function(e){
var dir=_11(e);
if(dir==""){
$(_10).css("cursor","default");
}else{
$(_10).css("cursor",dir+"-resize");
}
}).bind("mousedown.resizable",function(e){
var dir=_11(e);
if(dir==""){
return;
}
var _12={target:this,dir:dir,startLeft:_13("left"),startTop:_13("top"),left:_13("left"),top:_13("top"),startX:e.pageX,startY:e.pageY,startWidth:$(_10).outerWidth(),startHeight:$(_10).outerHeight(),width:$(_10).outerWidth(),height:$(_10).outerHeight(),deltaWidth:$(_10).outerWidth()-$(_10).width(),deltaHeight:$(_10).outerHeight()-$(_10).height()};
$(document).bind("mousedown.resizable",_12,_b);
$(document).bind("mousemove.resizable",_12,_c);
$(document).bind("mouseup.resizable",_12,_d);
$("body").css("cursor",dir+"-resize");
}).bind("mouseleave.resizable",function(){
$(_10).css("cursor","default");
});
function _11(e){
var dir="";
var _14=$(_10).offset();
var _15=$(_10).outerWidth();
var _16=$(_10).outerHeight();
var _17=_e.edge;
if(e.pageY>_14.top&&e.pageY<_14.top+_17){
dir+="n";
}else{
if(e.pageY<_14.top+_16&&e.pageY>_14.top+_16-_17){
dir+="s";
}
}
if(e.pageX>_14.left&&e.pageX<_14.left+_17){
dir+="w";
}else{
if(e.pageX<_14.left+_15&&e.pageX>_14.left+_15-_17){
dir+="e";
}
}
var _18=_e.handles.split(",");
for(var i=0;i<_18.length;i++){
var _19=_18[i].replace(/(^\s*)|(\s*$)/g,"");
if(_19=="all"||_19==dir){
return dir;
}
}
return "";
};
function _13(css){
var val=parseInt($(_10).css(css));
if(isNaN(val)){
return 0;
}else{
return val;
}
};
});
};
$.fn.resizable.methods={};
$.fn.resizable.defaults={disabled:false,handles:"n, e, s, w, ne, se, sw, nw, all",minWidth:10,minHeight:10,maxWidth:10000,maxHeight:10000,edge:5,onStartResize:function(e){
},onResize:function(e){
},onStopResize:function(e){
}};
})(jQuery);

