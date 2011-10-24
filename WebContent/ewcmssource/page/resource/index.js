/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

var setIfrSrc = function(url,type){
    var ifrId = "#" + type + "ifr";
    var src = $(ifrId).attr('src');
    if(!src){
        $(ifrId).attr("src",url+"?type="+type);
    }
};

var index = function(url){
    this._url = url;
};

index.prototype.init=function(){
    var url = this._url;
    $(".easyui-tabs").tabs({
        onSelect:function(title){  
            if(title == "图片"){
                setIfrSrc(url,"image");
            }else if(title == "FLASH"){
                setIfrSrc(url,"flash");
            }else if(title == "视频和音频"){
                setIfrSrc(url,"video");
            }else{
                setIfrSrc(url,"annex");
            }
        }  
    });
};
