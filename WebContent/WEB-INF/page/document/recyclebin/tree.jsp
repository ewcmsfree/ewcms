<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>文档编辑</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
		    var rootnode,currentnode;
			$(function() {
				$('#tt2').tree( {
					checkbox : false,
					url : '<s:url namespace="/site/channel" action="tree"/>',
					onClick : function(node) {
						rootnode = $('#tt2').tree('getRoot');
						if (rootnode.id == node.id){return;}
						var url = '';
						if (node.attributes.type == 'ARTICLE'){
							url = '<s:url namespace="/document/recyclebin" action="index"/>' + '?channelId=' + node.id;
						}else {
							url = '';
						}
						$("#editifr").attr('src', url);
					}
				});
			});
			function channelTreeLoad() {
				$('#tt2').tree('reload');
			}
		</script>
	</head>
	<body class="easyui-layout">
		<div region="west"  title='<img src="<s:url value="/ewcmssource/easyui/themes/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
			<ul id="tt2"></ul>
		</div>
		<div region="center"  style="overflow:auto;">
			<iframe id="editifr"  name="editifr" class="editifr"></iframe>
		</div>
	</body>
</html>