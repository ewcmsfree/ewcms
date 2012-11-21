/**
 * 前台页面调用方法
 * <script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
 * <script src="/js/visit.js" type="text/javascript"></script>
 * <script>
 *   if(window._ewcms_stat)_ewcms_stat("siteId=1&articleId=0&channelId=1&country=" + remote_ip_info["country"] + "&province=" + remote_ip_info["province"] + "&city=" + remote_ip_info["city"] + "&Dest=/visit");
 *  </script>
 * 
 */
var _ewcms_d,_ewcms_s,_ewcms_c,_ewcms_l,_ewcms_t,_ewcms_s,_ewcms_country,_ewcms_province,_ewcms_city;
var _ewcms_st=new Date().getTime();
var _ewcms_stat = function(param){
	var p = {};
	if(param){
		var arr = param.split("&");
		for(var i=0;i<arr.length;i++){
			if(arr[i]){
				var arr2 = arr[i].split("=");
				if(arr2[0]){
					p[arr2[0]] = arr2[1];
				}
			}
		}
	}
	_ewcms_d = p["Dest"];
	_ewcms_s = p["siteId"];
	_ewcms_c = p["articleId"];
	_ewcms_l = p["channelId"];
	_ewcms_t = p["type"];
	_ewcms_country = p["country"];
	_ewcms_province = p["province"];
	_ewcms_city = p["city"];
	p["sr"] = screen.width+"x"+screen.height;
	p["cd"] = screen.colorDepth;
	p["fv"] = _ewcms_stat.fv();
	p["ce"] = _ewcms_stat.ce();	
	p["je"] = _ewcms_stat.je();
	p["la"] = navigator.language?navigator.language:navigator.browserLanguage;
	p["la"] = p["la"]?p["la"]:navigator.systemLanguage;
	p["cs"] = document.charset;
	p["event"] = "Load";
	
	p["vq"] = _ewcms_stat.vq();	
	p["Referer"] = _ewcms_stat.eu(document.referrer);
	p["Title"] = _ewcms_stat.eu(document.title);
	p["URL"] = _ewcms_stat.eu(location.href);
	p["Host"] = location.host;
	var dest = _ewcms_d;
	p["Dest"] = false;
	dest = dest+"?"+_ewcms_stat.mq(p);
	var s = document.createElement("script");
	s.src = dest;
	(document.getElementsByTagName("head")[0]||document.getElementsByTagName("body")[0]).appendChild(s);
};

_ewcms_stat.eu =  function(str){
	return encodeURI(str).replace(/=/g,"%3D").replace(/\+/g,"%2B").replace(/\?/g,"%3F").replace(/\&/g,"%26");
}

_ewcms_stat.mq = function(map){
	var sb = [];
	for(var prop in map){
		if(map[prop]){
			sb.push(prop+"="+map[prop]);
		}
	}	
	return sb.join("&");
}

_ewcms_stat.trim = function(str){
	return str.replace(/(^\s*)|(\s*$)/g,"");
}


_ewcms_stat.je = function(){
	var je="";
	var n=navigator;
	je = n.javaEnabled()?1:0;
	return je;
} 

_ewcms_stat.fv = function(){
	var f="",n=navigator;	
	if(n.plugins && n.plugins.length){
		for(var ii=0;ii<n.plugins.length;ii++){
			if(n.plugins[ii].name.indexOf('Shockwave Flash')!=-1){
				f=n.plugins[ii].description.split('Shockwave Flash ')[1];
				break;
			}
		}
	}else if(window.ActiveXObject){
		for(var ii=10;ii>=2;ii--){
			try{
				var fl=eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash."+ii+"');");
				if(fl){
					f=ii + '.0'; break;
				}
			}catch(e){} 
		} 
	}
	return f;
}

_ewcms_stat.ce = function(){
	var c_en = (navigator.cookieEnabled)? 1 : 0;
	return c_en;
}

_ewcms_stat.vq = function(){
  var cs = document.cookie.split("; ");
  var name = _ewcms_s+"_vq";
  var vq = 1;
  for(i=0; i<cs.length; i++){
	  var arr = cs[i].split("=");
	  var n = _ewcms_stat.trim(arr[0]);
	  var v = arr[1]?_ewcms_stat.trim(arr[1]):"";
	  if(n==name){
	  	vq = parseInt(v)+1;
	  	break;
	  }
	}
	var expires = new Date(new Date().getTime()+365*10*24*60*60*1000).toGMTString();
	var cv = name+"="+vq+";expires="+expires+";path=/;";
	document.cookie = cv;
	return vq;
}

function _ewcms_bu(){
	if(_ewcms_d){ 
		var p = {};
		p["event"] = "Unload";
		p["channelId"] = _ewcms_l;
		p["siteId"] = _ewcms_s;
		p["articleId"] = _ewcms_c;
		p["URL"] = _ewcms_stat.eu(location.href);
		if(_ewcms_c&&!_ewcms_l){
	  	//p["Trace"] = pos.join(";");//will implement in 2.0
			p["type"] = _ewcms_t;
		}
		var t = new Date().getTime();
		if(t-_ewcms_lt>30000){
			_ewcms_nt += (t-_ewcms_lt+1000);
		}
		p["stickTime"] = (t-_ewcms_st-_ewcms_nt)/1000;
		var dest = _ewcms_d+"?"+_ewcms_stat.mq(p);
		var s = document.createElement("script");
		s.src = dest;
		(document.getElementsByTagName("head")[0]||document.getElementsByTagName("body")[0]).appendChild(s);
	}
}

var _ewcms_lt = new Date().getTime();
var _ewcms_lt_ka = new Date().getTime();
var _ewcms_nt = 0;
function _ewcms_ka(){
	var t = new Date().getTime();
	if(t-_ewcms_lt_ka>60000){
		_ewcms_lt_ka = t;
		var p = {};
		p["event"] = "KeepAlive";
		p["channelId"] = _ewcms_l;
		p["siteId"] = _ewcms_s;
		p["articleId"] = _ewcms_c;
		p["URL"] = _ewcms_stat.eu(location.href);
		var t1 = new Date().getTime();
		if(t1-_ewcms_lt>30000){
			_ewcms_nt += (t1-_ewcms_lt+1000);
		}
		p["stickTime"] = (t1-_ewcms_st-_ewcms_nt)/1000;
		var dest = _ewcms_d+"?"+_ewcms_stat.mq(p);
		var s = document.createElement("script");
		s.src = dest;
		(document.getElementsByTagName("head")[0]||document.getElementsByTagName("body")[0]).appendChild(s);
	}
	if(t-_ewcms_lt>60000){
		_ewcms_nt += (t-_ewcms_lt+1000);
	}
	_ewcms_lt = t;
}

var pos = [];
function _ewcms_cr(evt){
	var x = evt.clientX, y=evt.clientY;
	var src = evt.srcElement;
	if(!src){
		var node = evt.target;
    while(node&&node.nodeType!=1)node=node.parentNode;
    src =  node;
	}
	var win;
	if(src.ownerDocument.defaultView){
		win = src.ownerDocument.defaultView;
	}else{
		win = src.ownerDocument.parentWindow;
	}
	x += Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
	y += Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
	pos.push([x,y]);
}

if(window.attachEvent){
	window.attachEvent("onbeforeunload",_ewcms_bu);
	window.attachEvent("onclick",_ewcms_ka);
	window.attachEvent("onkeydown",_ewcms_ka);
	window.attachEvent("onmousemove",_ewcms_ka);
	window.attachEvent("onscroll",_ewcms_ka);
}else if(window.addEventListener){
	window.addEventListener('beforeunload',_ewcms_bu,false);
	window.addEventListener("click",_ewcms_ka,false);
	window.addEventListener("click",_ewcms_cr,false);
	window.addEventListener("keydown",_ewcms_ka,false);
	window.addEventListener("mousemove",_ewcms_ka,false);
	window.addEventListener("scroll",_ewcms_ka,false);
}