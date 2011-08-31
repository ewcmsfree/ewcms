<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>调查投票列表</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
			$(function(){
				//站点专栏目录树初始
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/site/channel" action="tree"/>',
					onClick:function(node){
						if (node.id == $('#tt2').tree('getRoot')) return;
						var url='<s:url namespace="/vote/questionnaire" action="index"/>';
						url = url + "?channelId=" + node.id;
						$('#mainifr').attr('src',url);
						$('#subjectifr').attr('src','');
					}
				});
			});
		</script>
	</head>
	<body class="easyui-layout">
	    <div region="west" title='<img src="<s:url value="/source/theme/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
			<ul id="tt2"></ul>
		</div>  
	    <div region="center" style="padding:2px;" border="false">
	    	<iframe id="mainifr" name="mainifr" class="mainifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no" style="width:100%;height:47%;" style="padding:0px;"></iframe>
	    	<iframe id="subjectifr" name="subjectifr" class="subjectifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no" style="width:100%;height:53%;" style="padding:0px;"></iframe>
	    </div>
	</body>
</html>