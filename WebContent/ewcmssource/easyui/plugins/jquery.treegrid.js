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
function _1(a,o){
for(var i=0,_2=a.length;i<_2;i++){
if(a[i]==o){
return i;
}
}
return -1;
};
function _3(a,o){
var _4=_1(a,o);
if(_4!=-1){
a.splice(_4,1);
}
};
function _5(_6){
var _7=$.data(_6,"treegrid").options;
$(_6).datagrid($.extend({},_7,{url:null,onLoadSuccess:function(){
},onResizeColumn:function(_8,_9){
_16(_6);
_7.onResizeColumn.call(_6,_8,_9);
},onSortColumn:function(_a,_b){
_7.sortName=_a;
_7.sortOrder=_b;
if(_7.remoteSort){
_15(_6);
}else{
var _c=$(_6).treegrid("getData");
_3d(_6,0,_c);
}
_7.onSortColumn.call(_6,_a,_b);
},onBeforeEdit:function(_d,_e){
if(_7.onBeforeEdit.call(_6,_e)==false){
return false;
}
},onAfterEdit:function(_f,row,_10){
_2a(_6);
_7.onAfterEdit.call(_6,row,_10);
},onCancelEdit:function(_11,row){
_2a(_6);
_7.onCancelEdit.call(_6,row);
}}));
if(_7.pagination){
var _12=$(_6).datagrid("getPager");
_12.pagination({pageNumber:_7.pageNumber,pageSize:_7.pageSize,pageList:_7.pageList,onSelectPage:function(_13,_14){
_7.pageNumber=_13;
_7.pageSize=_14;
_15(_6);
}});
_7.pageSize=_12.pagination("options").pageSize;
}
};
function _16(_17,_18){
var _19=$.data(_17,"datagrid").options;
var _1a=$.data(_17,"datagrid").panel;
var _1b=_1a.children("div.datagrid-view");
var _1c=_1b.children("div.datagrid-view1");
var _1d=_1b.children("div.datagrid-view2");
if(_19.rownumbers||(_19.frozenColumns&&_19.frozenColumns.length>0)){
if(_18){
_1e(_18);
_1d.find("tr[node-id="+_18+"]").next("tr.treegrid-tr-tree").find("tr[node-id]").each(function(){
_1e($(this).attr("node-id"));
});
}else{
_1d.find("tr[node-id]").each(function(){
_1e($(this).attr("node-id"));
});
if(_19.showFooter){
var _1f=$.data(_17,"datagrid").footer||[];
for(var i=0;i<_1f.length;i++){
_1e(_1f[i][_19.idField]);
}
$(_17).datagrid("resize");
}
}
}
if(_19.height=="auto"){
var _20=_1c.children("div.datagrid-body");
var _21=_1d.children("div.datagrid-body");
var _22=0;
var _23=0;
_21.children().each(function(){
var c=$(this);
if(c.is(":visible")){
_22+=c.outerHeight();
if(_23<c.outerWidth()){
_23=c.outerWidth();
}
}
});
if(_23>_21.width()){
_22+=18;
}
_20.height(_22);
_21.height(_22);
_1b.height(_1d.height());
}
_1d.children("div.datagrid-body").triggerHandler("scroll");
function _1e(_24){
var tr1=_1c.find("tr[node-id="+_24+"]");
var tr2=_1d.find("tr[node-id="+_24+"]");
tr1.css("height","");
tr2.css("height","");
var _25=Math.max(tr1.height(),tr2.height());
tr1.css("height",_25);
tr2.css("height",_25);
};
};
function _26(_27){
var _28=$.data(_27,"treegrid").options;
if(!_28.rownumbers){
return;
}
$(_27).datagrid("getPanel").find("div.datagrid-view1 div.datagrid-body div.datagrid-cell-rownumber").each(function(i){
var _29=i+1;
$(this).html(_29);
});
};
function _2a(_2b){
var _2c=$.data(_2b,"treegrid").options;
var _2d=$(_2b).datagrid("getPanel");
var _2e=_2d.find("div.datagrid-body");
_2e.find("span.tree-hit").unbind(".treegrid").bind("click.treegrid",function(){
var tr=$(this).parent().parent().parent();
var id=tr.attr("node-id");
_96(_2b,id);
return false;
}).bind("mouseenter.treegrid",function(){
if($(this).hasClass("tree-expanded")){
$(this).addClass("tree-expanded-hover");
}else{
$(this).addClass("tree-collapsed-hover");
}
}).bind("mouseleave.treegrid",function(){
if($(this).hasClass("tree-expanded")){
$(this).removeClass("tree-expanded-hover");
}else{
$(this).removeClass("tree-collapsed-hover");
}
});
_2e.find("tr[node-id]").unbind(".treegrid").bind("mouseenter.treegrid",function(){
var id=$(this).attr("node-id");
_2e.find("tr[node-id="+id+"]").addClass("datagrid-row-over");
}).bind("mouseleave.treegrid",function(){
var id=$(this).attr("node-id");
_2e.find("tr[node-id="+id+"]").removeClass("datagrid-row-over");
}).bind("click.treegrid",function(){
var id=$(this).attr("node-id");
if(_2c.singleSelect){
_31(_2b);
_80(_2b,id);
}else{
if($(this).hasClass("datagrid-row-selected")){
_84(_2b,id);
}else{
_80(_2b,id);
}
}
_2c.onClickRow.call(_2b,_48(_2b,id));
}).bind("dblclick.treegrid",function(){
var id=$(this).attr("node-id");
_2c.onDblClickRow.call(_2b,_48(_2b,id));
}).bind("contextmenu.treegrid",function(e){
var id=$(this).attr("node-id");
_2c.onContextMenu.call(_2b,e,_48(_2b,id));
});
_2e.find("div.datagrid-cell-check input[type=checkbox]").unbind(".treegrid").bind("click.treegrid",function(e){
var id=$(this).parent().parent().parent().attr("node-id");
if(_2c.singleSelect){
_31(_2b);
_80(_2b,id);
}else{
if($(this).attr("checked")){
_80(_2b,id);
}else{
_84(_2b,id);
}
}
e.stopPropagation();
});
var _2f=_2d.find("div.datagrid-header");
_2f.find("input[type=checkbox]").unbind().bind("click.treegrid",function(){
if(_2c.singleSelect){
return false;
}
if($(this).attr("checked")){
_30(_2b);
}else{
_31(_2b);
}
});
};
function _32(_33,_34){
var _35=$.data(_33,"treegrid").options;
var _36=$(_33).datagrid("getPanel").children("div.datagrid-view");
var _37=_36.children("div.datagrid-view1");
var _38=_36.children("div.datagrid-view2");
var tr1=_37.children("div.datagrid-body").find("tr[node-id="+_34+"]");
var tr2=_38.children("div.datagrid-body").find("tr[node-id="+_34+"]");
var _39=$(_33).datagrid("getColumnFields",true).length+(_35.rownumbers?1:0);
var _3a=$(_33).datagrid("getColumnFields",false).length;
_3b(tr1,_39);
_3b(tr2,_3a);
function _3b(tr,_3c){
$("<tr class=\"treegrid-tr-tree\">"+"<td style=\"border:0px\" colspan=\""+_3c+"\">"+"<div></div>"+"</td>"+"</tr>").insertAfter(tr);
};
};
function _3d(_3e,_3f,_40,_41){
var _42=$.data(_3e,"treegrid").options;
_40=_42.loadFilter.call(_3e,_40,_3f);
var _43=$.data(_3e,"datagrid").panel;
var _44=_43.children("div.datagrid-view");
var _45=_44.children("div.datagrid-view1");
var _46=_44.children("div.datagrid-view2");
var _47=_48(_3e,_3f);
if(_47){
var _49=_45.children("div.datagrid-body").find("tr[node-id="+_3f+"]");
var _4a=_46.children("div.datagrid-body").find("tr[node-id="+_3f+"]");
var cc1=_49.next("tr.treegrid-tr-tree").children("td").children("div");
var cc2=_4a.next("tr.treegrid-tr-tree").children("td").children("div");
}else{
var cc1=_45.children("div.datagrid-body").children("div.datagrid-body-inner");
var cc2=_46.children("div.datagrid-body");
}
if(!_41){
$.data(_3e,"treegrid").data=[];
cc1.empty();
cc2.empty();
}
if(_42.view.onBeforeRender){
_42.view.onBeforeRender.call(_42.view,_3e,_3f,_40);
}
_42.view.render.call(_42.view,_3e,cc1,true);
_42.view.render.call(_42.view,_3e,cc2,false);
if(_42.showFooter){
_42.view.renderFooter.call(_42.view,_3e,_45.find("div.datagrid-footer-inner"),true);
_42.view.renderFooter.call(_42.view,_3e,_46.find("div.datagrid-footer-inner"),false);
}
if(_42.view.onAfterRender){
_42.view.onAfterRender.call(_42.view,_3e);
}
_42.onLoadSuccess.call(_3e,_47,_40);
if(!_3f&&_42.pagination){
var _4b=$.data(_3e,"treegrid").total;
var _4c=$(_3e).datagrid("getPager");
if(_4c.pagination("options").total!=_4b){
_4c.pagination({total:_4b});
}
}
_16(_3e);
_26(_3e);
_4d();
_2a(_3e);
function _4d(){
var _4e=_44.find("div.datagrid-header");
var _4f=_44.find("div.datagrid-body");
var _50=_4e.find("div.datagrid-header-check");
if(_50.length){
var ck=_4f.find("div.datagrid-cell-check");
if($.boxModel){
ck.width(_50.width());
ck.height(_50.height());
}else{
ck.width(_50.outerWidth());
ck.height(_50.outerHeight());
}
}
};
};
function _15(_51,_52,_53,_54,_55){
var _56=$.data(_51,"treegrid").options;
var _57=$(_51).datagrid("getPanel").find("div.datagrid-body");
if(_53){
_56.queryParams=_53;
}
var _58=$.extend({},_56.queryParams);
if(_56.pagination){
$.extend(_58,{page:_56.pageNumber,rows:_56.pageSize});
}
if(_56.sortName){
$.extend(_58,{sort:_56.sortName,order:_56.sortOrder});
}
var row=_48(_51,_52);
if(_56.onBeforeLoad.call(_51,row,_58)==false){
return;
}
if(!_56.url){
return;
}
var _59=_57.find("tr[node-id="+_52+"] span.tree-folder");
_59.addClass("tree-loading");
$(_51).treegrid("loading");
$.ajax({type:_56.method,url:_56.url,data:_58,dataType:"json",success:function(_5a){
_59.removeClass("tree-loading");
$(_51).treegrid("loaded");
_3d(_51,_52,_5a,_54);
if(_55){
_55();
}
},error:function(){
_59.removeClass("tree-loading");
$(_51).treegrid("loaded");
_56.onLoadError.apply(_51,arguments);
if(_55){
_55();
}
}});
};
function _5b(_5c){
var _5d=_5e(_5c);
if(_5d.length){
return _5d[0];
}else{
return null;
}
};
function _5e(_5f){
return $.data(_5f,"treegrid").data;
};
function _60(_61,_62){
var row=_48(_61,_62);
if(row._parentId){
return _48(_61,row._parentId);
}else{
return null;
}
};
function _63(_64,_65){
var _66=$.data(_64,"treegrid").options;
var _67=$(_64).datagrid("getPanel").find("div.datagrid-view2 div.datagrid-body");
var _68=[];
if(_65){
_69(_65);
}else{
var _6a=_5e(_64);
for(var i=0;i<_6a.length;i++){
_68.push(_6a[i]);
_69(_6a[i][_66.idField]);
}
}
function _69(_6b){
var _6c=_48(_64,_6b);
if(_6c&&_6c.children){
for(var i=0,len=_6c.children.length;i<len;i++){
var _6d=_6c.children[i];
_68.push(_6d);
_69(_6d[_66.idField]);
}
}
};
return _68;
};
function _6e(_6f){
var _70=_71(_6f);
if(_70.length){
return _70[0];
}else{
return null;
}
};
function _71(_72){
var _73=[];
var _74=$(_72).datagrid("getPanel");
_74.find("div.datagrid-view2 div.datagrid-body tr.datagrid-row-selected").each(function(){
var id=$(this).attr("node-id");
_73.push(_48(_72,id));
});
return _73;
};
function _75(_76,_77){
if(!_77){
return 0;
}
var _78=$.data(_76,"treegrid").options;
var _79=$(_76).datagrid("getPanel").children("div.datagrid-view");
var _7a=_79.find("div.datagrid-body tr[node-id="+_77+"]").children("td[field="+_78.treeField+"]");
return _7a.find("span.tree-indent,span.tree-hit").length;
};
function _48(_7b,_7c){
var _7d=$.data(_7b,"treegrid").options;
var _7e=$.data(_7b,"treegrid").data;
var cc=[_7e];
while(cc.length){
var c=cc.shift();
for(var i=0;i<c.length;i++){
var _7f=c[i];
if(_7f[_7d.idField]==_7c){
return _7f;
}else{
if(_7f["children"]){
cc.push(_7f["children"]);
}
}
}
}
return null;
};
function _80(_81,_82){
var _83=$(_81).datagrid("getPanel").find("div.datagrid-body");
var tr=_83.find("tr[node-id="+_82+"]");
tr.addClass("datagrid-row-selected");
tr.find("div.datagrid-cell-check input[type=checkbox]").attr("checked",true);
};
function _84(_85,_86){
var _87=$(_85).datagrid("getPanel").find("div.datagrid-body");
var tr=_87.find("tr[node-id="+_86+"]");
tr.removeClass("datagrid-row-selected");
tr.find("div.datagrid-cell-check input[type=checkbox]").attr("checked",false);
};
function _30(_88){
var tr=$(_88).datagrid("getPanel").find("div.datagrid-body tr[node-id]");
tr.addClass("datagrid-row-selected");
tr.find("div.datagrid-cell-check input[type=checkbox]").attr("checked",true);
};
function _31(_89){
var tr=$(_89).datagrid("getPanel").find("div.datagrid-body tr[node-id]");
tr.removeClass("datagrid-row-selected");
tr.find("div.datagrid-cell-check input[type=checkbox]").attr("checked",false);
};
function _8a(_8b,_8c){
var _8d=$.data(_8b,"treegrid").options;
var _8e=$(_8b).datagrid("getPanel").find("div.datagrid-body");
var row=_48(_8b,_8c);
var tr=_8e.find("tr[node-id="+_8c+"]");
var hit=tr.find("span.tree-hit");
if(hit.length==0){
return;
}
if(hit.hasClass("tree-collapsed")){
return;
}
if(_8d.onBeforeCollapse.call(_8b,row)==false){
return;
}
hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
hit.next().removeClass("tree-folder-open");
row.state="closed";
tr=tr.next("tr.treegrid-tr-tree");
var cc=tr.children("td").children("div");
if(_8d.animate){
cc.slideUp("normal",function(){
_16(_8b,_8c);
_8d.onCollapse.call(_8b,row);
});
}else{
cc.hide();
_16(_8b,_8c);
_8d.onCollapse.call(_8b,row);
}
};
function _8f(_90,_91){
var _92=$.data(_90,"treegrid").options;
var _93=$(_90).datagrid("getPanel").find("div.datagrid-body");
var tr=_93.find("tr[node-id="+_91+"]");
var hit=tr.find("span.tree-hit");
var row=_48(_90,_91);
if(hit.length==0){
return;
}
if(hit.hasClass("tree-expanded")){
return;
}
if(_92.onBeforeExpand.call(_90,row)==false){
return;
}
hit.removeClass("tree-collapsed tree-collapsed-hover").addClass("tree-expanded");
hit.next().addClass("tree-folder-open");
var _94=tr.next("tr.treegrid-tr-tree");
if(_94.length){
var cc=_94.children("td").children("div");
_95(cc);
}else{
_32(_90,row[_92.idField]);
var _94=tr.next("tr.treegrid-tr-tree");
var cc=_94.children("td").children("div");
cc.hide();
_15(_90,row[_92.idField],{id:row[_92.idField]},true,function(){
_95(cc);
});
}
function _95(cc){
row.state="open";
if(_92.animate){
cc.slideDown("normal",function(){
_16(_90,_91);
_92.onExpand.call(_90,row);
});
}else{
cc.show();
_16(_90,_91);
_92.onExpand.call(_90,row);
}
};
};
function _96(_97,_98){
var _99=$(_97).datagrid("getPanel").find("div.datagrid-body");
var tr=_99.find("tr[node-id="+_98+"]");
var hit=tr.find("span.tree-hit");
if(hit.hasClass("tree-expanded")){
_8a(_97,_98);
}else{
_8f(_97,_98);
}
};
function _9a(_9b,_9c){
var _9d=$.data(_9b,"treegrid").options;
var _9e=_63(_9b,_9c);
if(_9c){
_9e.unshift(_48(_9b,_9c));
}
for(var i=0;i<_9e.length;i++){
_8a(_9b,_9e[i][_9d.idField]);
}
};
function _9f(_a0,_a1){
var _a2=$.data(_a0,"treegrid").options;
var _a3=_63(_a0,_a1);
if(_a1){
_a3.unshift(_48(_a0,_a1));
}
for(var i=0;i<_a3.length;i++){
_8f(_a0,_a3[i][_a2.idField]);
}
};
function _a4(_a5,_a6){
var _a7=$.data(_a5,"treegrid").options;
var ids=[];
var p=_60(_a5,_a6);
while(p){
var id=p[_a7.idField];
ids.unshift(id);
p=_60(_a5,id);
}
for(var i=0;i<ids.length;i++){
_8f(_a5,ids[i]);
}
};
function _a8(_a9,_aa){
var _ab=$.data(_a9,"treegrid").options;
if(_aa.parent){
var _ac=$(_a9).datagrid("getPanel").find("div.datagrid-body");
var tr=_ac.find("tr[node-id="+_aa.parent+"]");
if(tr.next("tr.treegrid-tr-tree").length==0){
_32(_a9,_aa.parent);
}
var _ad=tr.children("td[field="+_ab.treeField+"]").children("div.datagrid-cell");
var _ae=_ad.children("span.tree-icon");
if(_ae.hasClass("tree-file")){
_ae.removeClass("tree-file").addClass("tree-folder");
var hit=$("<span class=\"tree-hit tree-expanded\"></span>").insertBefore(_ae);
if(hit.prev().length){
hit.prev().remove();
}
}
}
_3d(_a9,_aa.parent,_aa.data,true);
};
function _af(_b0,_b1){
var _b2=$.data(_b0,"treegrid").options;
var _b3=$(_b0).datagrid("getPanel").find("div.datagrid-body");
var tr=_b3.find("tr[node-id="+_b1+"]");
tr.next("tr.treegrid-tr-tree").remove();
tr.remove();
var _b4=del(_b1);
if(_b4){
if(_b4.children.length==0){
tr=_b3.find("tr[node-id="+_b4[_b2.treeField]+"]");
var _b5=tr.children("td[field="+_b2.treeField+"]").children("div.datagrid-cell");
_b5.find(".tree-icon").removeClass("tree-folder").addClass("tree-file");
_b5.find(".tree-hit").remove();
$("<span class=\"tree-indent\"></span>").prependTo(_b5);
}
}
_26(_b0);
function del(id){
var cc;
var _b6=_60(_b0,_b1);
if(_b6){
cc=_b6.children;
}else{
cc=$(_b0).treegrid("getData");
}
for(var i=0;i<cc.length;i++){
if(cc[i][_b2.treeField]==id){
cc.splice(i,1);
break;
}
}
return _b6;
};
};
$.fn.treegrid=function(_b7,_b8){
if(typeof _b7=="string"){
var _b9=$.fn.treegrid.methods[_b7];
if(_b9){
return _b9(this,_b8);
}else{
return this.datagrid(_b7,_b8);
}
}
_b7=_b7||{};
return this.each(function(){
var _ba=$.data(this,"treegrid");
if(_ba){
$.extend(_ba.options,_b7);
}else{
$.data(this,"treegrid",{options:$.extend({},$.fn.treegrid.defaults,$.fn.treegrid.parseOptions(this),_b7),data:[]});
}
_5(this);
_15(this);
});
};
$.fn.treegrid.methods={options:function(jq){
return $.data(jq[0],"treegrid").options;
},resize:function(jq,_bb){
return jq.each(function(){
$(this).datagrid("resize",_bb);
});
},fixRowHeight:function(jq,_bc){
return jq.each(function(){
_16(this,_bc);
});
},loadData:function(jq,_bd){
return jq.each(function(){
_3d(this,null,_bd);
});
},reload:function(jq,id){
return jq.each(function(){
if(id){
var _be=$(this).treegrid("find",id);
if(_be.children){
_be.children.splice(0,_be.children.length);
}
var _bf=$(this).datagrid("getPanel").find("div.datagrid-body");
var tr=_bf.find("tr[node-id="+id+"]");
tr.next("tr.treegrid-tr-tree").remove();
var hit=tr.find("span.tree-hit");
hit.removeClass("tree-expanded tree-expanded-hover").addClass("tree-collapsed");
_8f(this,id);
}else{
_15(this,null,{});
}
});
},reloadFooter:function(jq,_c0){
return jq.each(function(){
var _c1=$.data(this,"treegrid").options;
var _c2=$(this).datagrid("getPanel").children("div.datagrid-view");
var _c3=_c2.children("div.datagrid-view1");
var _c4=_c2.children("div.datagrid-view2");
if(_c0){
$.data(this,"treegrid").footer=_c0;
}
if(_c1.showFooter){
_c1.view.renderFooter.call(_c1.view,this,_c3.find("div.datagrid-footer-inner"),true);
_c1.view.renderFooter.call(_c1.view,this,_c4.find("div.datagrid-footer-inner"),false);
if(_c1.view.onAfterRender){
_c1.view.onAfterRender.call(_c1.view,this);
}
$(this).treegrid("fixRowHeight");
}
});
},loading:function(jq){
return jq.each(function(){
$(this).datagrid("loading");
});
},loaded:function(jq){
return jq.each(function(){
$(this).datagrid("loaded");
});
},getData:function(jq){
return $.data(jq[0],"treegrid").data;
},getFooterRows:function(jq){
return $.data(jq[0],"treegrid").footer;
},getRoot:function(jq){
return _5b(jq[0]);
},getRoots:function(jq){
return _5e(jq[0]);
},getParent:function(jq,id){
return _60(jq[0],id);
},getChildren:function(jq,id){
return _63(jq[0],id);
},getSelected:function(jq){
return _6e(jq[0]);
},getSelections:function(jq){
return _71(jq[0]);
},getLevel:function(jq,id){
return _75(jq[0],id);
},find:function(jq,id){
return _48(jq[0],id);
},isLeaf:function(jq,id){
var _c5=$.data(jq[0],"treegrid").options;
var tr=_c5.finder.getTr(jq[0],id);
var hit=tr.find("span.tree-hit");
return hit.length==0;
},select:function(jq,id){
return jq.each(function(){
_80(this,id);
});
},unselect:function(jq,id){
return jq.each(function(){
_84(this,id);
});
},selectAll:function(jq){
return jq.each(function(){
_30(this);
});
},unselectAll:function(jq){
return jq.each(function(){
_31(this);
});
},collapse:function(jq,id){
return jq.each(function(){
_8a(this,id);
});
},expand:function(jq,id){
return jq.each(function(){
_8f(this,id);
});
},toggle:function(jq,id){
return jq.each(function(){
_96(this,id);
});
},collapseAll:function(jq,id){
return jq.each(function(){
_9a(this,id);
});
},expandAll:function(jq,id){
return jq.each(function(){
_9f(this,id);
});
},expandTo:function(jq,id){
return jq.each(function(){
_a4(this,id);
});
},append:function(jq,_c6){
return jq.each(function(){
_a8(this,_c6);
});
},remove:function(jq,id){
return jq.each(function(){
_af(this,id);
});
},refresh:function(jq,id){
return jq.each(function(){
var _c7=$.data(this,"treegrid").options;
_c7.view.refreshRow.call(_c7.view,this,id);
});
},beginEdit:function(jq,id){
return jq.each(function(){
$(this).datagrid("beginEdit",id);
$(this).treegrid("fixRowHeight",id);
});
},endEdit:function(jq,id){
return jq.each(function(){
$(this).datagrid("endEdit",id);
});
},cancelEdit:function(jq,id){
return jq.each(function(){
$(this).datagrid("cancelEdit",id);
});
}};
$.fn.treegrid.parseOptions=function(_c8){
var t=$(_c8);
return $.extend({},$.fn.datagrid.parseOptions(_c8),{treeField:t.attr("treeField"),animate:(t.attr("animate")?t.attr("animate")=="true":undefined)});
};
var _c9=$.extend({},$.fn.datagrid.defaults.view,{render:function(_ca,_cb,_cc){
var _cd=$.data(_ca,"treegrid").options;
var _ce=$(_ca).datagrid("getColumnFields",_cc);
var _cf=this;
var _d0=_d1(_cc,this.treeLevel,this.treeNodes);
$(_cb).append(_d0.join(""));
function _d1(_d2,_d3,_d4){
var _d5=["<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<_d4.length;i++){
var row=_d4[i];
if(row.state!="open"&&row.state!="closed"){
row.state="open";
}
var _d6=_cd.rowStyler?_cd.rowStyler.call(_ca,row):"";
var _d7=_d6?"style=\""+_d6+"\"":"";
_d5.push("<tr node-id="+row[_cd.idField]+" "+_d7+">");
_d5=_d5.concat(_cf.renderRow.call(_cf,_ca,_ce,_d2,_d3,row));
_d5.push("</tr>");
if(row.children&&row.children.length){
var tt=_d1(_d2,_d3+1,row.children);
var v=row.state=="closed"?"none":"block";
_d5.push("<tr class=\"treegrid-tr-tree\"><td style=\"border:0px\" colspan="+(_ce.length+(_cd.rownumbers?1:0))+"><div style=\"display:"+v+"\">");
_d5=_d5.concat(tt);
_d5.push("</div></td></tr>");
}
}
_d5.push("</tbody></table>");
return _d5;
};
},renderFooter:function(_d8,_d9,_da){
var _db=$.data(_d8,"treegrid").options;
var _dc=$.data(_d8,"treegrid").footer||[];
var _dd=$(_d8).datagrid("getColumnFields",_da);
var _de=["<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
for(var i=0;i<_dc.length;i++){
var row=_dc[i];
row[_db.idField]=row[_db.idField]||("foot-row-id"+i);
_de.push("<tr node-id="+row[_db.idField]+">");
_de.push(this.renderRow.call(this,_d8,_dd,_da,0,row));
_de.push("</tr>");
}
_de.push("</tbody></table>");
$(_d9).html(_de.join(""));
},renderRow:function(_df,_e0,_e1,_e2,row){
var _e3=$.data(_df,"treegrid").options;
var cc=[];
if(_e1&&_e3.rownumbers){
cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">0</div></td>");
}
for(var i=0;i<_e0.length;i++){
var _e4=_e0[i];
var col=$(_df).datagrid("getColumnOption",_e4);
if(col){
var _e5=col.styler?(col.styler(row[_e4],row)||""):"";
var _e6=col.hidden?"style=\"display:none;"+_e5+"\"":(_e5?"style=\""+_e5+"\"":"");
cc.push("<td field=\""+_e4+"\" "+_e6+">");
var _e6="width:"+(col.boxWidth)+"px;";
_e6+="text-align:"+(col.align||"left")+";";
_e6+=_e3.nowrap==false?"white-space:normal;":"";
cc.push("<div style=\""+_e6+"\" ");
if(col.checkbox){
cc.push("class=\"datagrid-cell-check ");
}else{
cc.push("class=\"datagrid-cell ");
}
cc.push("\">");
if(col.checkbox){
if(row.checked){
cc.push("<input type=\"checkbox\" checked=\"checked\"/>");
}else{
cc.push("<input type=\"checkbox\"/>");
}
}else{
var val=null;
if(col.formatter){
val=col.formatter(row[_e4],row);
}else{
val=row[_e4]||"&nbsp;";
}
if(_e4==_e3.treeField){
for(var j=0;j<_e2;j++){
cc.push("<span class=\"tree-indent\"></span>");
}
if(row.state=="closed"){
cc.push("<span class=\"tree-hit tree-collapsed\"></span>");
cc.push("<span class=\"tree-icon tree-folder "+(row.iconCls?row.iconCls:"")+"\"></span>");
}else{
if(row.children&&row.children.length){
cc.push("<span class=\"tree-hit tree-expanded\"></span>");
cc.push("<span class=\"tree-icon tree-folder tree-folder-open "+(row.iconCls?row.iconCls:"")+"\"></span>");
}else{
cc.push("<span class=\"tree-indent\"></span>");
cc.push("<span class=\"tree-icon tree-file "+(row.iconCls?row.iconCls:"")+"\"></span>");
}
}
cc.push("<span class=\"tree-title\">"+val+"</span>");
}else{
cc.push(val);
}
}
cc.push("</div>");
cc.push("</td>");
}
}
return cc.join("");
},refreshRow:function(_e7,id){
var row=$(_e7).treegrid("find",id);
var _e8=$.data(_e7,"treegrid").options;
var _e9=$(_e7).datagrid("getPanel").find("div.datagrid-body");
var _ea=_e8.rowStyler?_e8.rowStyler.call(_e7,row):"";
var _eb=_ea?_ea:"";
var tr=_e9.find("tr[node-id="+id+"]");
tr.attr("style",_eb);
tr.children("td").each(function(){
var _ec=$(this).find("div.datagrid-cell");
var _ed=$(this).attr("field");
var col=$(_e7).datagrid("getColumnOption",_ed);
if(col){
var _ee=col.styler?(col.styler(row[_ed],row)||""):"";
var _ef=col.hidden?"display:none;"+_ee:(_ee?_ee:"");
$(this).attr("style",_ef);
var val=null;
if(col.formatter){
val=col.formatter(row[_ed],row);
}else{
val=row[_ed]||"&nbsp;";
}
if(_ed==_e8.treeField){
_ec.children("span.tree-title").html(val);
var cls="tree-icon";
var _f0=_ec.children("span.tree-icon");
if(_f0.hasClass("tree-folder")){
cls+=" tree-folder";
}
if(_f0.hasClass("tree-folder-open")){
cls+=" tree-folder-open";
}
if(_f0.hasClass("tree-file")){
cls+=" tree-file";
}
if(row.iconCls){
cls+=" "+row.iconCls;
}
_f0.attr("class",cls);
}else{
_ec.html(val);
}
}
});
$(_e7).treegrid("fixRowHeight",id);
},onBeforeRender:function(_f1,_f2,_f3){
if(!_f3){
return false;
}
var _f4=$.data(_f1,"treegrid").options;
if(_f3.length==undefined){
if(_f3.footer){
$.data(_f1,"treegrid").footer=_f3.footer;
}
if(_f3.total){
$.data(_f1,"treegrid").total=_f3.total;
}
_f3=this.transfer(_f1,_f2,_f3.rows);
}else{
function _f5(_f6,_f7){
for(var i=0;i<_f6.length;i++){
var row=_f6[i];
row._parentId=_f7;
if(row.children&&row.children.length){
_f5(row.children,row[_f4.idField]);
}
}
};
_f5(_f3,_f2);
}
var _f8=_48(_f1,_f2);
if(_f8){
if(_f8.children){
_f8.children=_f8.children.concat(_f3);
}else{
_f8.children=_f3;
}
}else{
$.data(_f1,"treegrid").data=$.data(_f1,"treegrid").data.concat(_f3);
}
if(!_f4.remoteSort){
this.sort(_f1,_f3);
}
this.treeNodes=_f3;
this.treeLevel=$(_f1).treegrid("getLevel",_f2);
},sort:function(_f9,_fa){
var _fb=$.data(_f9,"treegrid").options;
var opt=$(_f9).treegrid("getColumnOption",_fb.sortName);
if(opt){
var _fc=opt.sorter||function(a,b){
return (a>b?1:-1);
};
_fd(_fa);
}
function _fd(_fe){
_fe.sort(function(r1,r2){
return _fc(r1[_fb.sortName],r2[_fb.sortName])*(_fb.sortOrder=="asc"?1:-1);
});
for(var i=0;i<_fe.length;i++){
var _ff=_fe[i].children;
if(_ff&&_ff.length){
_fd(_ff);
}
}
};
},transfer:function(_100,_101,data){
var opts=$.data(_100,"treegrid").options;
var rows=[];
for(var i=0;i<data.length;i++){
rows.push(data[i]);
}
var _102=[];
for(var i=0;i<rows.length;i++){
var row=rows[i];
if(!_101){
if(!row._parentId){
_102.push(row);
_3(rows,row);
i--;
}
}else{
if(row._parentId==_101){
_102.push(row);
_3(rows,row);
i--;
}
}
}
var toDo=[];
for(var i=0;i<_102.length;i++){
toDo.push(_102[i]);
}
while(toDo.length){
var node=toDo.shift();
for(var i=0;i<rows.length;i++){
var row=rows[i];
if(row._parentId==node[opts.idField]){
if(node.children){
node.children.push(row);
}else{
node.children=[row];
}
toDo.push(row);
_3(rows,row);
i--;
}
}
}
return _102;
}});
$.fn.treegrid.defaults=$.extend({},$.fn.datagrid.defaults,{treeField:null,animate:false,singleSelect:true,view:_c9,loadFilter:function(data,_103){
return data;
},finder:{getTr:function(_104,id,type,_105){
type=type||"body";
_105=_105||0;
var dc=$.data(_104,"datagrid").dc;
if(_105==0){
var opts=$.data(_104,"treegrid").options;
var tr1=opts.finder.getTr(_104,id,type,1);
var tr2=opts.finder.getTr(_104,id,type,2);
return tr1.add(tr2);
}else{
if(type=="body"){
return (_105==1?dc.body1:dc.body2).find("tr[node-id="+id+"]");
}else{
if(type=="footer"){
return (_105==1?dc.footer1:dc.footer2).find("tr[node-id="+id+"]");
}else{
if(type=="selected"){
return (_105==1?dc.body1:dc.body2).find("tr.datagrid-row-selected");
}else{
if(type=="last"){
return (_105==1?dc.body1:dc.body2).find("tr:last[node-id]");
}else{
if(type=="allbody"){
return (_105==1?dc.body1:dc.body2).find("tr[node-id]");
}else{
if(type=="allfooter"){
return (_105==1?dc.footer1:dc.footer2).find("tr[node-id]");
}
}
}
}
}
}
}
},getRow:function(_106,id){
return $(_106).treegrid("find",id);
}},onBeforeLoad:function(row,_107){
},onLoadSuccess:function(row,data){
},onLoadError:function(){
},onBeforeCollapse:function(row){
},onCollapse:function(row){
},onBeforeExpand:function(row){
},onExpand:function(row){
},onClickRow:function(row){
},onDblClickRow:function(row){
},onContextMenu:function(e,row){
},onBeforeEdit:function(row){
},onAfterEdit:function(row,_108){
},onCancelEdit:function(row){
}});
})(jQuery);

