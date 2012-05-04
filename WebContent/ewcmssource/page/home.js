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
    $('#progress-menu').bind('click',function(){
        openWindow(windowId,{width:550,height:300,title:'发布进度',url:opts.progress}); 
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

home.prototype.getPopMessage=function(jsonPop, reread){
	if (!reread) return;
	var dataObj=eval("("+jsonPop+")");
	$.each(dataObj.pops, function(idx, item){
	    $.messager.show({title:item.title,msg:item.content,width:350,height:200,timeout:5000,showType:'fade'});
	});
}

var detailUrl = "/message/detail/index.do";
var noticesDetailUrl = detailUrl + "?type=notice";
home.prototype.getNoticeMessage = function(jsonNotice, reread){
	if (!reread) return;
	var dataObj=eval("("+jsonNotice+")");
	if (dataObj.notices.length > 0){
		$('#notice .t-list').empty();
		var noticesHtml = '<div class="t-list"><table width="100%">';
	    var pro = [];
	    $.each(dataObj.notices, function(idx, item){
	    	pro.push('<tr><td width="80%"><a href="javascript:void(0);" onclick="showRecord(\'' + noticesDetailUrl + '\',' + item.id + ');" style="text-decoration:none;" alt="' + item.title + '"><span class="ellipsis">' + item.title + '</span></a></td><td width="10%">[' + item.userName + ']' + '</td><td width="10%" align="right">' + item.sendTime + '</td></tr>');
	    });
	    var html = pro.join("");
	    noticesHtml += html + '</table></div>'
	    $(noticesHtml).appendTo('#notice');
    }
}

var subDetailUrl = detailUrl + "?type=subscription";
home.prototype.getSubscription = function(jsonSub, reread){
	if (!reread) return;
	var dataObj=eval("("+jsonSub+")");
	if (dataObj.subs.length > 0){
    	$('#subscription .t-list').empty();
    	var subscriptionHtml = '<div class="t-list"><table width="100%">';
    	var pro = [];
    	$.each(dataObj.subs, function(idx, item){
        	pro.push('<tr><td width="80%"><a href="javascript:void(0);" onclick="showRecord(\'' + subDetailUrl + '\',' + item.id + ');" style="text-decoration:none;"><span class="ellipsis">' + item.title + '</span></a></td><td width="10%">[' + item.userName + ']' + '</td><td width="10%" align="right">' + item.sendTime + '</td></tr>');
        });
        var html = pro.join("");
        subscriptionHtml += html + '</table></div>'
        $(subscriptionHtml).appendTo('#subscription');
    }
}

home.prototype.getUnReadMessage=function(count, reread){
	if (!reread) return;
	$('#tipMessage').empty();
	var html = '<span id="messageFlash">';
	if (count > 0){
	  	html += '<a href="javascript:void(0);" onclick="javascript:_home.addTab(\'个人消息\',\'message/index.do\');return false;" onfocus="this.blur();" style="color:red;font-size:13px;text-decoration:none;" class="message-unread">【&nbsp;&nbsp;&nbsp;&nbsp;新消息(' + count + ')】</a>';
	}
	html += '</span>';
	$(html).appendTo('#tipMessage');
}

home.prototype.getBeApproval=function(jsonBeApproval, reread){
	if (!reread) return;
	var dataObj=eval("("+jsonBeApproval+")");
	if (dataObj.beapprovals.length > 0){
		$('#other .t-list').empty();
    	var beApprovalHtml = '<div class="t-list"><table width="100%">';
    	var pro = [];
    	$.each(dataObj.beapprovals, function(idx, item){
   			pro.push('<tr><td width="50%"><a href="javascript:void(0);" style="text-decoration:none;">栏目：『' + item.channelName + '』 共有 ' + item.articleCount + ' 条需要审批</a></td></tr>');
   		});
   		var html = pro.join("");
   		beApprovalHtml += html + '</table></div>'
   		$(beApprovalHtml).appendTo('#other');
	}
}

function showRecord(url, id){
	url = url + '&id=' + id;
	$('#editifr_detail').attr('src',url);
	ewcmsBOBJ.openWindow('#detail-window',{width:700,height:400,title:'内容'});
}

function Clock() {
	var date = new Date();
	this.year = date.getFullYear();
	this.month = date.getMonth() + 1;
	this.date = date.getDate();
	this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
	this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	this.toString = function() {
		return "现在是:" + this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day; 
	};
	
	this.toSimpleDate = function() {
		return this.year + "-" + this.month + "-" + this.date;
	};
	
	this.toDetailDate = function() {
		return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
	};
	
	this.display = function(ele) {
		var clock = new Clock();
		ele.innerHTML = clock.toString();
		window.setTimeout(function() {clock.display(ele);}, 1000);
	};
}