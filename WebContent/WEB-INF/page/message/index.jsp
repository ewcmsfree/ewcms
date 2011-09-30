<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>个人消息</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<script>
		  var sendload=false,receiveload=false,allload=false;
		  $(function(){
			$('#msgboxtab').tabs({
				onSelect:function(title){
					var sendUrl = '<s:url namespace="/message/send" action="index"/>';
					var receiveUrl = '<s:url namespace="/message/receive" action="index"/>';
					if(title=='收件箱'&&!receiveload){
						$('#receiveifr').attr('src',receiveUrl);
						receiveload = true;
						return;
					}
					if(title=='发件箱'&&!sendload){
					    $('#sendifr').attr('src',sendUrl);
					    sendload = true;
					    return;
					}
				}
			});
		  });
		</script>		
	</head>
	<body>
	  <div class="easyui-tabs" id="msgboxtab" border="false" fit="true">
	    <div title="收件箱">
		  <iframe id="receiveifr"  name="receiveifr" class="editifr" scrolling="no"></iframe>	
		</div>			
		<div title="发件箱">
		  <iframe id="sendifr" name="sendifr" class="editifr" scrolling="no"></iframe>
		</div>
	  </div>	
	</body>
</html>