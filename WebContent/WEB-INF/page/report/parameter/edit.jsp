<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>参数设置</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
        <script type="text/javascript">
	    	$(function() {
	            <s:include value="../../alertMessage.jsp"/>
	    		$('#userName').combobox({
	    			url: '<s:url namespace="/report/parameter" action="sessionInfo"><s:param name="parameterId" value="parameterVo.id"></s:param></s:url>',
	    			valueField:'id',
	    	        textField:'text',
	    			editable:false,
	    			multiple:true,
	    			cascadeCheck:false,
	    			panelWidth:130,
	    			panelHeight:100
	    		});
	    		if ($('#parametersType').val() == 'SESSION'){
	    			$('#userName_span').show();
    				$('#defaultvalue_span').hide();
		    	}else{
					$('#userName_span').hide();
					$('#defaultvalue_span').show();
				}
	    		$('#parametersType').click(function() {
	    			if ($('#parametersType').val() == 'SESSION'){
	    				$('#userName_span').show();
	    				$('#defaultvalue_span').hide();
	    			}else{
	    				$('#userName_span').hide();
	    				$('#defaultvalue_span').show();
	    			}
	    		});
	    	})
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/report/parameter">
			<table class="formtable" >
				<tr>
					<td>参数编号：</td>
					<td>
						<s:textfield name="parameterVo.id" cssClass="inputtext" readonly="true" />
					</td>
					<td>参数名：</td>
					<td>
						<s:textfield name="parameterVo.enName" cssClass="inputtext" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>中文名：</td>
					<td>
						<s:textfield name="parameterVo.cnName" cssClass="inputtext" />
					</td>
					<td>默认值：</td>
					<td >
						<span id="defaultvalue_span"><s:textfield id="defaultValue" name="parameterVo.defaultValue" cssClass="inputtext"/></span>
						<span id="userName_span"><input id="userName" name="sessionValue"></input></span>
					</td>
				</tr>
				<tr>
					<td>数据输入方式：</td>
					<td>
						<s:select list="@com.ewcms.plugin.report.model.Parameters$Type@values()" listValue="description" name="parameterVo.type" id="type"></s:select>
					</td>
					<td>辅助数据设置：</td>
					<td>
						<s:textfield name="parameterVo.value" cssClass="inputtext"/>
					</td>
				</tr>
			</table>
			<s:hidden name="reportId"/>
			<s:hidden name="reportType"/>
			<s:hidden name="parameterVo.className"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>