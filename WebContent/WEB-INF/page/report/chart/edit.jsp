<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>Bean数据源设置</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
        <script type="text/javascript">
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
		<s:form action="save" namespace="/report/chart">
			<table class="formtable" >
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
						<s:textfield name="reportChartVo.name" cssClass="inputtext" maxlength="10" />
						<s:fielderror><s:param value="%{'reportChartVo.name'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
					<td>数据源类型：</td>
					<td>
						<s:select list="baseDSList" name="reportChartVo.baseDS.id" listKey="id" listValue="name" headerKey="0" headerValue="默认数据源" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>SQL表达式：</td>
					<td class="formFieldError" colspan="3">
						<s:textarea	name="reportChartVo.chartSql" cols="80" rows="6"/><a id="regexHelp" href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-help" onclick="parent.helpOperate();"></a>
						<s:fielderror><s:param value="%{'reportChartVo.chartSql'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>图表类型：</td>
					<td>
						<s:select list="chartTypeList" name="reportChartVo.chartType" listKey="name()" listValue="description" cssClass="inputtext"/>
					</td>
					<td>工具提示：</td>
					<td>
						<s:checkbox name="reportChartVo.showTooltips" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>图表标题：</td>
					<td>
						<s:textfield name="reportChartVo.chartTitle" cssClass="inputtext"/>
					</td>
					<td>图表标题字体：</td>
					<td>
						<s:select name="reportChartVo.fontName" list="fontNameMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.fontStyle" list="fontStyleMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.fontSize" list="fontSizeMap" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>横坐标轴标题：</td>
					<td>
						<s:textfield name="reportChartVo.horizAxisLabel" cssClass="inputtext"/>
					</td>
					<td>纵坐标轴标题：</td>
					<td>
						<s:textfield name="reportChartVo.vertAxisLabel" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>数据字体：</td>
					<td>
						<s:select name="reportChartVo.dataFontName" list="fontNameMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.dataFontStyle" list="fontStyleMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.dataFontSize" list="fontSizeMap" cssClass="inputtext"/>
					</td>
					<td>坐标轴字体：</td>
					<td>
						<s:select name="reportChartVo.axisFontName" list="fontNameMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.axisFontStyle" list="fontStyleMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.axisFontSize" list="fontSizeMap" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>坐标轴尺值字体：</td>
					<td>
						<s:select name="reportChartVo.axisTickFontName" list="fontNameMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.axisTickFontStyle" list="fontStyleMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.axisTickFontSize" list="fontSizeMap" cssClass="inputtext"/>
					</td>
					<td>数据轴标签角度：</td>
					<td>
						<s:select name="reportChartVo.tickLabelRotate" list="rotateMap" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>图示说明：</td>
					<td>
						<s:checkbox name="reportChartVo.showLegend" cssClass="inputtext"/>
					</td>
					<td>图示位置：</td>
					<td>
						<s:select name="reportChartVo.legendPosition" list="positionMap" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>图示字体：</td>
					<td>
						<s:select name="reportChartVo.legendFontName" list="fontNameMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.legendFontStyle" list="fontStyleMap" cssClass="inputtext"/>&nbsp;
						<s:select name="reportChartVo.legendFontSize" list="fontSizeMap" cssClass="inputtext"/>
					</td>
					<td>图表高度：</td>
					<td>
						<s:textfield name="reportChartVo.chartHeight" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>图表宽度：</td>
					<td>
						<s:textfield name="reportChartVo.chartWidth" cssClass="inputtext"/>
					</td>
					<td>RGB背景色：</td>
					<td>
						<s:textfield name="reportChartVo.bgColorR" maxLength="3" size="3" cssClass="inputtext"/>&nbsp;&nbsp;
						<s:textfield name="reportChartVo.bgColorG" maxLength="3" size="3" cssClass="inputtext"/>&nbsp;&nbsp;
						<s:textfield name="reportChartVo.bgColorB" maxLength="3" size="3" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3">
						<s:textarea name="reportChartVo.remarks" cols="60"/>
					</td>
				</tr>
			</table>
			<s:hidden name="reportChartVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>