var ewcmsBOBJ,ewcmsOOBJ;

/**
  * iframe自适应大小调节
  */  
function iframeFitHeight(oIframe){
     newHeight = oIframe.contentWindow.document.body.scrollHeight;
     newWidth = oIframe.contentWindow.document.body.scrollWidth;
     if(newHeight < 80)newHeight = 80;
     if(newWidth < 150)newWidth = 150;
     oIframe.width = newWidth;
     oIframe.height = newHeight;
}

function openWindow(winID,options){
	ewcmsBOBJ.openWindow(winID,options);
}
function openWindow1(options){
	ewcmsBOBJ.openWindow1(options);
}
function querySearch(formID){
	if(typeof(formID) == 'undefined'){
		ewcmsOOBJ.querySearch();
	}else{
		ewcmsOOBJ.querySearch(formID);
	}
}

function addCallBack(){
	ewcmsOOBJ.addOperateBack();
}
function updCallBack(){
	ewcmsOOBJ.updOperateBack();
}
function delCallBack(){
	ewcmsOOBJ.delOperateBack();
}
function queryCallBack(){
	ewcmsOOBJ.queryOperateBack();
}
function defQueryCallBack(){
	ewcmsOOBJ.initOperateQueryBack();
}
function saveOperator(iframeID){
	if(typeof(iframeID) == 'undefined'){
		ewcmsOOBJ.saveOperator();
	}else{
		ewcmsOOBJ.saveOperator(iframeID);
	}	
}
function queryNews(ids){
	ewcmsOOBJ.queryNews(ids);
}
function queryReload(hide){
	ewcmsOOBJ.queryReload(hide);
}
