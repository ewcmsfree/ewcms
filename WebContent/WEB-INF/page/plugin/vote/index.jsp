<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>调查投票列表</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function(){
				//站点专栏目录树初始
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/site/channel" action="tree"/>',
					onClick:function(node){
						if (node.id == $('#tt2').tree('getRoot')){
							url = '';
						}else{
							if (node.attributes.type == 'ARTICLE'){
								url = '<s:url namespace="/vote/questionnaire" action="index"/>' + '?channelId=' + node.id;
							}else {
								url = '';
							}
						}
						$('#mainifr').attr('src',url);
						$('#subjectifr').attr('src','');
					}
				});
			});
		</script>
	</head>
	<body class="easyui-layout">
	    <div region="west" title='<img src="<s:url value="/ewcmssource/easyui/themes/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
			<ul id="tt2"></ul>
		</div>  
	    <div region="center" style="padding:2px;" border="false">
	    	<iframe id="mainifr" name="mainifr" class="mainifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no" style="width:100%;height:47%;" style="padding:0px;"></iframe>
	    	<iframe id="subjectifr" name="subjectifr" class="subjectifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no" style="width:100%;height:53%;" style="padding:0px;"></iframe>
	    </div>
	</body>
</html>