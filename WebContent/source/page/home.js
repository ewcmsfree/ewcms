/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var updateUsername = function(name){
    $('#user-name').html(name);
}

var home = function(refurbish,windowId){
    
    this._refurbish = refurbish || true;
    this._windowId = windowId || '#edit-window';
}

home.prototype.addTab=function(title,src){
    var tabob = $('#systemtab');
    var content = '<iframe src='  + src+ ' width=100% frameborder=0 height=100%/>';
    
    if (tabob.tabs('exists', title) && this._refurbish) {
        tabob.tabs('select', title);
        var tab = tabob.tabs("getTab", title);
        tabob.tabs('update', {
            tab : tab,
            options : {
                content :content
            }
        });
    } else {
        tabob.tabs('add', {
            title : title,
            content : content,
            closable : true
        });
    }    
}

home.prototype.init = function(urls){
    var windowId = this._windowId;
 
    $('#button-main').bind('click',function(){
        $('#mm').menu('show',{
            left:$(this).offset().left - 80,
            top:$(this).offset().top + 35
        });
       
    }).hover(function(){
        $(this).addClass('l-btn l-btn-plain m-btn-plain-active');
    },function(){
        $(this).removeClass('l-btn l-btn-plain m-btn-plain-active');
    });
    
    $('#user-menu').bind('click',function(){
        openWindow(windowId,{width:550,height:300,title:'修改用户信息',url:urls.user}); 
    });
    $('#password-menu').bind('click',function(){
        openWindow(windowId,{width:550,height:230,title:'修改密码',url:urls.password}); 
    });
    $('#exit-menu').bind('click',function(){
        window.location = urls.exit;
    });
}

home.prototype.getPopMessage=function(url){
    var popInterval = this._popInterval;
    var currentAjax = $.ajax({
        type:'post',
        datatype:'json',
        cache:false,
        url:url + '?clientTime=' + new Date(),
        data: '',
        success:function(message, textStatus){
            if (message != 'false'){
                for (var i=0;i<message.length;i++){
                    $.messager.show({title:message[i].title,msg:message[i].content,width:350,height:200,timeout:0,showType:'fade'});
                }
            }
        },
        beforeSend:function(XMLHttpRequest){
        },
        complete:function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            clearInterval(popInterval);
            if(currentAjax) {currentAjax.abort();}
        }
    });
}

home.prototype.setPopInterval=function(popInterval){
    this._popInterval = popInterval;
}

home.prototype.getNotice = function(url){
	var noticeInterval = this._noticeInterval;
	var currentAjax = $.ajax({
		type:'post',
		datatype:'json',
		cache:false,
		url: url,
		data: '',
		success:function(message, textStatus){
    		if (message != 'false'){
    			$('#notice .t-list').empty();
    			var noticesHtml = '<div class="t-list"><table width="100%">';
    			var pro = [];
        		for (var i=0;i<message.length;i++){
        			pro.push('<tr><td width="77%"><a href="#"><span class="ellipsis">' + message[i].title + '</span></a></td><td width="7%">[' + message[i].userName + ']' + '</td><td width="16%" align="right">' + message[i].sendTime + '</td></tr>');
        		}
        		var html = pro.join("");
        		noticesHtml += html + '</table></div>'
        		$(noticesHtml).appendTo('#notice');
    		}
		},
		beforeSend:function(XMLHttpRequest){
		},
		complete:function(XMLHttpRequest, textStatus){
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			clearInterval(noticeInterval);
			if(currentAjax) {currentAjax.abort();}
		}
	});
}

home.prototype.setNoticeInterval = function(noticeInterval){
	this._noticeInterval = noticeInterval;
}

home.prototype.getSubscription = function(url){
	var subscriptionInterval = this._subscriptionInterval;
	var currentAjax = $.ajax({
		type:'post',
		datatype:'json',
		cache:false,
		url: url,
		data: '',
		success:function(message, textStatus){
    		if (message != 'false'){
    			$('#subscription .t-list').empty();
    			var subscriptionHtml = '<div class="t-list"><table width="100%">';
    			var pro = [];
        		for (var i=0;i<message.length;i++){
        			pro.push('<tr><td width="77%"><a href="#"><span class="ellipsis">' + message[i].title + '</span></a></td><td width="7%">[' + message[i].userName + ']' + '</td><td width="16%" align="right">' + message[i].sendTime + '</td></tr>');
        		}
        		var html = pro.join("");
        		subscriptionHtml += html + '</table></div>'
        		$(subscriptionHtml).appendTo('#subscription');
    		}
		},
		beforeSend:function(XMLHttpRequest){
		},
		complete:function(XMLHttpRequest, textStatus){
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			clearInterval(subscriptionInterval);
			if(currentAjax) {currentAjax.abort();}
		}
	});
}

home.prototype.setSubscriptionInterval = function(subscriptionInterval){
	this._subscriptionInterval = subscriptionInterval;
}

home.prototype.getTipMessage=function(url){
    var tipInterval = this._tipInterval;
    var currentAjax = $.ajax({
        type:'post',
        datatype:'json',
        cache:false,
        url:url,
        data: '',
        success:function(message, textStatus){
        	$('#tipMessage').empty();
        	var html = '<span>';
            if (message != 'false'){
            	var tiplength = message.length;
            	html += '【新消息(' + tiplength + ')】';
            }
            html += '</span>';
            $(html).appendTo('#tipMessage');
        },
        beforeSend:function(XMLHttpRequest){
        },
        complete:function(XMLHttpRequest, textStatus){
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            clearInterval(tipInterval);
            if(currentAjax) {currentAjax.abort();}
        }
    });
}

home.prototype.setTipInterval=function(tipInterval){
    this._tipInterval = tipInterval;
}

home.prototype.siteLoad = function(id,url){
    window.location = url + '?siteId=' + siteId;
}
