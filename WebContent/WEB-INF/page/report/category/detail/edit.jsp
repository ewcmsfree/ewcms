<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>报表选择</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
        <script type="text/javascript">
            var textCategoryURL = "<s:url namespace='/report/text' action='findTextReport'/>?categoryId=<s:property value='categoryId'/>";
            var chartCategoryURL = "<s:url namespace='/report/chart' action='findChartReport'/>?categoryId=<s:property value='categoryId'/>";
	        $(function(){
	        	$('#text_categories').combobox({
	        		url: textCategoryURL,
	        		valueField:'id',
	                textField:'text',
	        		editable:false,
	        		multiple:true,
	        		cascadeCheck:false,
	        		panelWidth:200,
	        		panelHeight:70
	        	});
	        	$('#chart_categories').combobox({
	        		url: chartCategoryURL,
	        		valueField:'id',
	                textField:'text',
	        		editable:false,
	        		multiple:true,
	        		cascadeCheck:false,
	        		panelWidth:200,
	        		panelHeight:70
	        	});
	        })

			function tipMessage(){
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
						$.messager.alert('提示','<s:property escape="false"/>','info');
			        </s:iterator>  
		     	</s:if>  
			}
            <s:property value="javaScript"/>
        </script>		
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/report/category/detail">
			<table class="formtable" >
				<tr>
					<td>分类编号：</td>
					<td>
						<s:textfield name="categoryReportVo.id" cssClass="inputtext" readonly="true" />
					</td>
					<td>分类名称：</td>
					<td>
						<s:textfield name="categoryReportVo.name" cssClass="inputtext" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>文字报表：</td>
					<td>
						<input id="text_categories" name="textReportIds" style="width:200px;" ></input>
					</td>
					<td>图型报表：</td>
					<td>
						<input id="chart_categories" name="chartReportIds" style="width:200px;"></input>
					</td>
				</tr>
			</table>
			<s:hidden name="categoryId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>