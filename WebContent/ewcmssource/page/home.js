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

home.prototype.init = function(opts){
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
        openWindow(windowId,{width:550,height:300,title:'修改用户信息',url:opts.user}); 
    });
    $('#password-menu').bind('click',function(){
        openWindow(windowId,{width:550,height:230,title:'修改密码',url:opts.password}); 
    });
    $('#switch-menu').bind('click',function(){
        var item = $('#mm').menu('getItem','#switch-menu');
        if(!item.disabled){
            openWindow(windowId,{width:450,height:280,title:'站点切换',url:opts.siteswitch});            
        }
    });      
    $('#exit-menu').bind('click',function(){
        window.location = opts.exit;
    });
    
    if(!opts.hasSite){
        $('#mainmenu').accordion('remove','站点建设');
        $('#mainmenu').accordion('remove','站点内容');
        $('#mainmenu').accordion('remove','站点资源');
        $('#mm').menu('disableItem','#switch-menu');
    }
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

home.prototype.getNotice = function(url,detailUrl){
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
        			pro.push('<tr><td width="80%"><a href="javascript:void(0);" onclick="showRecord(\'' + detailUrl + '\',' + message[i].id + ');" style="text-decoration:none;" alt="' + message[i].title + '"><span class="ellipsis">' + message[i].title + '</span></a></td><td width="10%">[' + message[i].userName + ']' + '</td><td width="10%" align="right">' + message[i].sendTime + '</td></tr>');
        		}
        		var html = pro.join("");
        		noticesHtml += html + '</table></div>'
        		$(noticesHtml).appendTo('#notice');
        		$('#notice_tr').show();
    		}else{
    			$('#notice_tr').hide();
    		}
		},
		beforeSend:function(XMLHttpRequest){
			$('#notice_tr').hide();
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

home.prototype.getSubscription = function(url, detailUrl){
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
        			pro.push('<tr><td width="80%"><a href="javascript:void(0);" onclick="showRecord(\'' + detailUrl + '\',' + message[i].id + ');" style="text-decoration:none;"><span class="ellipsis">' + message[i].title + '</span></a></td><td width="10%">[' + message[i].userName + ']' + '</td><td width="10%" align="right">' + message[i].sendTime + '</td></tr>');
        		}
        		var html = pro.join("");
        		subscriptionHtml += html + '</table></div>'
        		$(subscriptionHtml).appendTo('#subscription');
        		$('#subscription_tr').show();
    		}else{
    			$('#subscription_tr').hide();
    		}
		},
		beforeSend:function(XMLHttpRequest){
			$('#subscription_tr').hide();
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
        	var html = '<span id="messageFlash">';
            if (message != 'false'){
            	var tiplength = message.length;
            	html += '<a href="javascript:void(0);" onclick="javascript:_home.addTab(\'个人消息\',\'message/index.do\');return false;" onfocus="this.blur();" style="color:red;font-size:13px;text-decoration:none;">【<img src="./ewcmssource/image/msg/msg_new.gif"/>新消息(' + tiplength + ')】</a>';
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

home.prototype.getBeApproval=function(url){
    var beApproval = this._beApproval;
	var currentAjax = $.ajax({
		type:'post',
		datatype:'json',
		cache:false,
		url: url,
		data: '',
		success:function(message, textStatus){
    		if (message != 'false'){
    			$('#other .t-list').empty();
    			var subscriptionHtml = '<div class="t-list"><table width="100%">';
    			var pro = [];
        		for (var i=0;i<message.length;i++){
        			pro.push('<tr><td width="50%"><a href="javascript:void(0);" style="text-decoration:none;">栏目：『' + message[i].channelName + '』 共有 ' + message[i].articleCount + ' 条需要审批</a></td></tr>');
        		}
        		var html = pro.join("");
        		subscriptionHtml += html + '</table></div>'
        		$(subscriptionHtml).appendTo('#other');
        		$('#other_tr').show();
    		}else{
    			$('#other_tr').hide();
    		}
		},
		beforeSend:function(XMLHttpRequest){
			$('#other_tr').hide();
		},
		complete:function(XMLHttpRequest, textStatus){
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			clearInterval(beApproval);
			if(currentAjax) {currentAjax.abort();}
		}
	});
}

home.prototype.setBeApprovalInterval=function(beApproval){
    this._beApproval = beApproval;
}

function showRecord(url, id){
	url = url + '&id=' + id;
	$('#editifr_detail').attr('src',url);
	ewcmsBOBJ.openWindow('#detail-window',{width:700,height:400,title:'内容'});
}