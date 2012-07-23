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
						currentnode = node;
						var url = '';
						if (rootnode.id == currentnode.id){
							url = '<s:url namespace="/document/article" action="index"/>' + '?channelId=' + currentnode.id;;
						}else{
							if (node.attributes.type == 'ARTICLE' || node.attributes.type == 'RETRIEVAL'){
								url = '<s:url namespace="/document/article" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'LEADER'){
								url = '<s:url namespace="/document/article" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'LEADERARTICLE'){
								url = '<s:url namespace="/document/refer" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'ONLINE'){
								url = '<s:url namespace="/plugin/online/workingbody" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'INTERACTION'){
								url = '<s:url namespace="/plguin/interaction/" action="index"/>';
							}else if (node.attributes.type == 'SPEAK'){
								url = '<s:url namespace="/plguin/interaction/" action="speak"/>';
							}else if (node.attributes.type == 'ADVISOR'){
								url = '<s:url namespace="/plugin/online/advisor/" action="index"/>';
							}else if (node.attributes.type == 'PROJECT'){
								url = '<s:url namespace="/particular/pb" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'PROJECTARTICLE'){
								url = '<s:url namespace="/particular/pa" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'ENTERPRISE'){
								url = '<s:url namespace="/particular/eb" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'ENTERPRISEARTICLE'){	
								url = '<s:url namespace="/particular/ea" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'EMPLOYE'){
								url = '<s:url namespace="/particular/mb" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'EMPLOYEARTICLE'){	
								url = '<s:url namespace="/particular/ma" action="index"/>' + '?channelId=' + currentnode.id;
							}else if (node.attributes.type == 'NODE'){
								url = '';
							}
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