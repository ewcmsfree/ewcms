<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>内容</title>
		<s:include value="../taglibs.jsp"/>
		<script type="text/javascript">
			function subscribe(){
				var url = '<s:url namespace="/message/detail" action="subscribe"/>';
				$.post(url, {'id':$('#id').val()}, function(data) {
					if (data == 'own'){
						$.messager.alert('提示','您不能订阅自已发布的信息！','info');
						return;
					}
					if (data == 'exist'){
						$.messager.alert('提示','您已订阅了此信息，不需要再订阅！','info');
						return;
					}
					if (data == 'false'){
						$.messager.alert('提示','订阅信息失败！','info');
						return;
					}
					if (data == 'true'){
						$.messager.alert('提示','订阅成功！','info');
						return;
					}
				});
			}
		</script>	
	</head>
	<body>
		<table class="formtable" width="100%">
		  <tr>
		    <td width="10%">标题：</td>
		    <td width="90%"><s:label name="title"/><s:if test="results!=null">&nbsp;&nbsp;<a href="javascript:void(0);" onclick="subscribe();return false;" onfocus="this.blur();">订阅</a></s:if></td>
		  </tr>
		  <tr>
		    <td width="10%">内容：</td>
		    <td width="90%">
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
		<s:hidden id="type" name="type"/>
		<s:hidden id="id" name="id"/>
	</body>
</html>