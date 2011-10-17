<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>个人消息</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<script>
		  var sendload=false,receiveload=false,allload=false;
		  $(function(){
			ewcmsBOBJ = new EwcmsBase();
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
		  function showRecord(id){
			url = '<s:url namespace="/message/detail" action="index"/>?msgType=message&id=' + id;
			$('#editifr_detail').attr('src',url);
			ewcmsBOBJ.openWindow('#detail-window',{width:700,height:400,title:'内容'});
			receiveifr.initOperateQuery();
		  }
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
	  <div id="detail-window" class="easyui-window" icon="" closed="true" style="display:none;">
        <div class="easyui-layout" fit="true">
          <div region="center" border="false">
            <iframe id="editifr_detail"  name="editifr_detail" frameborder="0" width="100%" height="100%" scrolling="auto" style="width:100%;height:100%;"></iframe>
          </div>
        </div>
      </div>
	</body>
</html>