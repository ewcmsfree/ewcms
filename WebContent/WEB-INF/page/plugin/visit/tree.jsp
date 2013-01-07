<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
  <head>
    <title>统计分析</title>
    <s:include value="../../taglibs.jsp" />
    <script type="text/javascript">
		$(function() {
		    $('#tt2').tree({
		    	checkbox : false,
		        url:'<s:url namespace="/plugin/visit" action="tree"/>',
		        onClick : function(node) {
					rootnode = $('#tt2').tree('getRoot');
					currentnode = node;
					var url = node.attributes.url;
					if (typeof(url) == 'undefined' || url == ''){
						return false;
					}else{
						url = '<s:url namespace="/plugin/visit" action="' + url + '"/>';
						$("#editifr").attr('src', url);
					}
		        },
		    	onLoadSuccess : function(node, data){
		    		var nodeSel = $('#tt2').tree('find', 2);
		    		$('#tt2').tree('select', nodeSel.target);
		    		url = '<s:url namespace="/plugin/visit" action="' + nodeSel.attributes.url + '"/>';
					$("#editifr").attr('src', url);
		    	}
		    });  
		});
		function visitTreeLoad() {
			$('#tt2').tree('reload');
		}
	</script>
  </head>
  <body class="easyui-layout">
	<div region="west" title='统计分析' split="true" style="width: 180px;">
		<ul id="tt2"></ul>  
	</div>
	<div region="center" style="overflow: auto;">
		<iframe id="editifr" name="editifr" class="editifr"></iframe>
	</div>
  </body>
</html>