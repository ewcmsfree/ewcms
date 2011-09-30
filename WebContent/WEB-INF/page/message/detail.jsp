<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>内容</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript">
			function subscribe(){
				var url = '<s:url namespace="/message/detail" action="subscribe"/>';
				$.post(url, {'id':$('#id').val()}, function(data) {
					if (data == 'own'){
						$.messager.alert('提示','您订阅的信息是自已发布的','info');
						return;
					}
					if (data == 'exist'){
						$.messager.alert('提示','您已订阅了此信息，不需要再订阅了','info');
						return;
					}
					if (data == 'false'){
						$.messager.alert('提示','订阅信息失败','info');
						return;
					}
					if (data == 'true'){
						$.messager.alert('提示','订阅成功','info');
						return;
					}
				});
			}
		</script>	
	</head>
	<body>
		<table class="formtable" width="100%">
		  <tr>
		    <td width="15%">标题：</td>
		    <td width="85%"><s:label name="title"/><s:if test="results!=null">&nbsp;&nbsp;<a href="javascript:void(0);" onclick="subscribe();return false;" onfocus="this.blur();">订阅</a></s:if></td>
		  </tr>
		  <tr>
		    <td width="15%">内容：</td>
		    <td width="85%">
		    <s:if test="results!=null">
		    <table class="formatable" width="100%">
			<s:iterator value="results" status="rowstatus">
			  <tr>
			    <s:if test="#rowstatus.odd == false">
			      <td width="100%" style="background: #EEEEFF"><s:property/></td>
			    </s:if>
			    <s:else>
			      <td width="100%"><s:property/></td>
			    </s:else>
			  </tr>
			</s:iterator>
		    </table>
		    </s:if>
		    <s:else>
		    	<s:label name="detail"/>
		    </s:else>
		    </td>
		  </tr>
		</table>
		<s:hidden id="msgType" name="msgType"/>
		<s:hidden id="id" name="id"/>
	</body>
</html>