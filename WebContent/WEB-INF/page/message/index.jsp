<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>个人消息</title>	
		<s:include value="../taglibs.jsp"/>
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
			url = '<s:url namespace="/message/detail" action="index"/>?type=message&id=' + id;
			$('#editifr_detail').attr('src',url);
			ewcmsBOBJ.openWindow('#detail-window',{width:700,height:400,title:'内容'});
			receiveifr.initOperateQuery();
			refreshTipMessage();
		  }
		  function refreshTipMessage(){
			  $.ajax({
				  type:'post',
				  datatype:'json',
				  cache:false,
				  url:'<s:url namespace="/message/receive" action="unRead"/>',
				  data: '',
				  success:function(message, textStatus){
					  parent.$('#tipMessage').empty();
				      var html = '<span id="messageFlash">';
				      if (message != 'false'){
				      	var tiplength = message.length;
				        html += '<a href="javascript:void(0);" onclick="javascript:_home.addTab(\'个人消息\',\'message/index.do\');return false;" onfocus="this.blur();" style="color:red;font-size:13px;text-decoration:none;">【<img src="./ewcmssource/image/msg/msg_new.gif"/>新消息(' + tiplength + ')】</a>';
				      }
				      html += '</span>';
				      $(html).appendTo('#tipMessage');
				  }
			  });
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