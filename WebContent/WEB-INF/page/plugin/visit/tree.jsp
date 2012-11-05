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
		        url:'<s:url namespace="/plugin/visit" action="tree"/>'  
		    });  
		});
		function visitTreeLoad() {
			$('#tt2').tree('reload');
		}
	</script>
  </head>
  <body class="easyui-layout">
	<div region="west" title='<img src="<s:url value="/ewcmssource/easyui/themes/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="visitTreeLoad();"/> 统计分析' split="true" style="width: 180px;">
		<ul id="tt2"></ul>  
	</div>
	<div region="center" style="overflow: auto;">
		<iframe id="editifr" name="editifr" class="editifr"></iframe>
	</div>
  </body>
</html>