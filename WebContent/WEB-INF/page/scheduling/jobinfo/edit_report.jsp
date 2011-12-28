<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
	<head>
		<title>调度器任务</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/scheduling/jobinfo/edit.js"/>'></script>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../alertMessage.jsp"/>
	        });
	        function checkBoxValue(name){
				var strValue = '';
				var list = document.getElementsByName(name);
				for (var i = 0; i < list.length; i++){
					if (list[i].type == 'checkbox'){
						if (list[i].checked == true) {
							listValue = list[i].value;
							if(strValue != '')strValue += ',';
							if (isNumber(listValue)){
								strValue += listValue;
							}
							else{
								strValue += "'" + listValue + "'";
							}
						}
					}
				}
				obName = "paraMap['" + name + "']";
				document.all[obName].value = strValue;
			}
				
			function isNumber(str){
			  var patrn=/^\d*$/;    
			  if(patrn.test(str)) {  
			  	return true;    
			  }else{  
			  	return false;  
			  }   
			}
        </script>	
        <ewcms:datepickerhead></ewcms:datepickerhead>	
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/scheduling/jobreport">
			<table class="formtable" align="center">
				<tr>
					<td colspan="4" align="left"><font color="#0066FF"><b>任务信息</b></font></td>
				</tr>
				<tr>
					<td width="15%">名称：</td>
					<td>
						<s:textfield name="pageDisplayVo.label"	maxlength="50"></s:textfield>
					</td>
					<td width="15%">报表名称：</td>
					<td>
						<s:label name="pageDisplayVo.reportName"></s:label>
						<s:if test="reportType=='text'">
							<s:label value="(文字报表)"></s:label>
						</s:if>
						<s:if test="reportType=='chart'">
							<s:label value="(图型报表)"></s:label>
						</s:if>
						<s:hidden name="reportType" />
					</td>
				</tr>
				<tr>
					<td>说明：</td>
					<td colspan="3">
						<s:textarea name="pageDisplayVo.description" rows="3" cols="80"></s:textarea>
					</td>
				</tr>
				<s:if test="(reportType!='')&&(null!=pageShowParams)&&!(pageShowParams.isEmpty())">
				<tr>
					<td colspan="4" align="left"><font color="#0066FF"><b>参数信息</b></font></td>
				</tr>
				<s:iterator id="param" value="pageShowParams" >
				<tr>
					<td>
					  <s:if test="(cnName!=null)&&(cnName.length()>0)">
					    <s:property value="cnName" />：
					  </s:if>
					  <s:else>
					    <s:property value="enName" />：
					  </s:else>
					</td>
					<td colspan="3">
					  <s:if test='type.name().equals("TEXT")'>
					    <s:textfield name="paraMap['%{enName}']" value="%{defaultValue}"/>
					  </s:if>
					  <s:if test='type.name().equals("BOOLEAN")'>
						<s:checkbox name="paraMap['%{enName}']" value="%{defaultValue}"/>
					  </s:if>	
					  <s:if test='type.name().equals("LIST")'>
					    <s:select list="value" name="paraMap['%{enName}']"/>
					  </s:if>		
					  <s:if test='type.name().equals("CHECK")'>
					    <s:checkboxlist list="value" name="%{enName}" onclick="checkBoxValue('%{enName}');"/>
						<s:hidden name="paraMap['%{enName}']"/>
					  </s:if>		
					  <s:if test='type.name().equals("DATE")'>
						<ewcms:datepicker name="paraMap['%{enName}']"/>
					  </s:if>		
					  <s:if test='type.name().equals("SESSION")'>
					    <s:if test='value.get("0").equals("SPRING_SECURITY_CONTEXT")'>
					      <s:textfield name="paraMap['<s:property value="enName"/>']"  value='<sec:authentication property="principal.username"/>' readonly="true"/>
					    </s:if>
					    <s:else>
					      <s:textfield name="paraMap['%{enName}']" value='%{#session[value.get("0")]}'/>
					    </s:else>
					  </s:if>		
					  <s:if test='type.name().equals("SQL")'>
					    <s:textfield name="paraMap['%{enName}']" value="%{defaultValue}"/>
					  </s:if>																																																													
					</td>
				  </tr>
				</s:iterator>
				</s:if>
				<s:if test="reportType=='text'">
				<tr>
					<td colspan="4" align="left"><font color="#0066FF"><b>输出格式信息</b></font></td>
				</tr>
				<tr>
					<td class="texttd">输出格式：</td>
					<td class="inputtd" colspan="4"><s:checkboxlist
						id="pageDisplayVo.outputFormats"
						name="pageDisplayVo.outputFormats"
						list='#{1:"Pdf",2:"Xls",3:"Rtf",4:"Xml"}'></s:checkboxlist></td>
				</tr>
				</s:if>
				<tr>
					<td colspan="4" align="left"><font color="#0066FF"><b>计划信息</b></font></td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td colspan="3">
						<!-- 
						<s:radio id="start_" name="pageDisplayVo.start" list='#{1:"&nbsp;立刻执行"}' cssStyle="vertical-align: middle;"></s:radio> 
						<s:radio id="start_" name="pageDisplayVo.start" list='#{2:"&nbsp;在"}' value="2" cssStyle="vertical-align: middle;"></s:radio>
						 -->
						<ewcms:datepicker id="startDate" name="pageDisplayVo.startDate" option="inputsimple" format="yyyy-MM-dd HH:mm" disabled="false"/>&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>
					</td>
				</tr>
				<tr>
					<td>调度方式：</td>
					<td colspan="3">
						<s:radio id="mode" name="pageDisplayVo.mode" list='#{0:"&nbsp;无&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}' cssStyle="vertical-align: middle;"></s:radio>
						<s:radio id="mode" name="pageDisplayVo.mode" list='#{1:"&nbsp;简单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"}' cssStyle="vertical-align: middle;"></s:radio> 
						<s:radio id="mode" name="pageDisplayVo.mode" list='#{2:"&nbsp;复杂&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}' cssStyle="vertical-align: middle;"></s:radio>
					</td>
				</tr>
				<tr id="trSimplicity" style="display: none;">
					<td>&nbsp;</td>
					<td colspan="3" style="padding: 1px 1px;">
						<table class="formtable" align="center">
							<tr>
								<td>发生：</td>
								<td colspan="3">
									<s:radio id="occur"	name="pageDisplayVo.occur" list='#{1:"&nbsp;无限期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}' cssStyle="vertical-align: middle;"></s:radio>
									<s:radio id="occur" name="pageDisplayVo.occur" list='#{2:"&nbsp;结束时间&nbsp;"}' cssStyle="vertical-align: middle;"></s:radio>
									<label for="occur2"><ewcms:datepicker name="pageDisplayVo.endDateSimple" option="inputsimple" format="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
									<s:radio id="occur" name="pageDisplayVo.occur" list='#{3:"&nbsp;"}' cssStyle="vertical-align: middle;"></s:radio>
									<s:textfield id="occurrenceCount" name="pageDisplayVo.occurrenceCount" size="4" onkeyup="this.value=this.value.replace(/\D/g,'');"></s:textfield>&nbsp;次数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td>每隔：</td>
								<td colspan="3">
									<s:textfield name="pageDisplayVo.recurrenceInterval" onkeyup="this.value=this.value.replace(/\D/g,'');"></s:textfield>
									<s:select id="pageDisplayVo.recurrenceIntervalUnit"	name="pageDisplayVo.recurrenceIntervalUnit"	list='#{1:"分钟",2:"小时",3:"天",4:"星期"}' cssStyle="vertical-align:middle;"></s:select>执行一次&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="trComplexity">
					<td>&nbsp;</td>
					<td colspan="3" style="padding: 1px 1px;">
						<table class="formtable" align="center">
							<tr>
								<td>结束时间：</td>
								<td colspan="3">
									<ewcms:datepicker id="endDateCalendar" name="pageDisplayVo.endDateCalendar" option="inputsimple" format="yyyy-MM-dd HH:mm" />
								</td>
							</tr>
							<tr>
								<td width="10%">分钟：</td>
								<td width="40%">
									<s:textfield name="pageDisplayVo.minutes" size="2" maxlength="2" onkeyup="this.value=this.value.replace(/\D/g,'');"></s:textfield>&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>&nbsp;&nbsp;（在0-59之间）
								</td>
								<td width="10%">小时：</td>
								<td width="40%">
									<s:textfield name="pageDisplayVo.hours" size="2" maxlength="2" onkeyup="this.value=this.value.replace(/\D/g,'');"></s:textfield>&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>&nbsp;&nbsp;（在0-23之间）
								</td>
							</tr>
							<tr>
								<td>天数：</td>
								<td colspan="3">
									<s:radio id="pageDisplayVo.days" name="pageDisplayVo.days" list='#{1:"&nbsp;每一天"}' cssStyle="vertical-align: middle;"></s:radio>
								</td>
							</tr>
							<tr>
								<td></td>
								<td colspan="3">
									<s:radio id="pageDisplayVo.days" name="pageDisplayVo.days" list='#{2:"&nbsp;一周内"}' cssStyle="vertical-align: middle;"></s:radio>
								</td>
							</tr>
							<tr>
								<td></td>
								<td colspan="3"><s:checkboxlist cssStyle="vertical-align:middle;"
									id="pageDisplayVo.weekDays" name="pageDisplayVo.weekDays"
									list='#{1:" 星期一  ",2:" 星期二 ",3:" 星期三 ",4:" 星期四 ",5:" 星期五 ",6:" 星期六 ",7:" 星期天 "}'></s:checkboxlist>
									（<input type="checkbox" name="weekDaysAll" id="weekDaysAll" style="vertical-align: middle;"/><label for="monthAll" style="vertical-align: middle;">&nbsp;全选）</label>
								</td>
							</tr>
							<tr>
								<td></td>
								<td colspan="3">
									<s:radio id="pageDisplayVo.days" name="pageDisplayVo.days" list='#{3:"一个月内"}' cssStyle="vertical-align: middle;"></s:radio>
									<s:textfield id="monthDays" name="pageDisplayVo.monthDays" onkeyup="this.value=this.value.replace(/\D/g,'');"></s:textfield>
								</td>
							</tr>
							<tr>
								<td>月份：</td>
								<td colspan="3">
									<s:checkboxlist	id="pageDisplayVo.months" name="pageDisplayVo.months" list='#{1:"一月",2:"二月",3:"三月",4:"四月",5:"五月",6:"六月",7:"七月",8:"八月",9:"九月",10:"十月",11:"十一月",12:"十二月"}' cssStyle="vertical-align: middle;"></s:checkboxlist>
									（<input type="checkbox" name="monthsAll" id="monthsAll"/><label for="monthAll">&nbsp;全选）</label>&nbsp;&nbsp;<font color="red" style="vertical-align: middle;">*</font>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>
			<s:hidden id="jobId" name="pageDisplayVo.jobId"/>
			<s:hidden name="pageDisplayVo.jobVersion"/>
			<s:hidden name="pageDisplayVo.triggerId"/>
			<s:hidden name="pageDisplayVo.triggerVersion"/>
			<s:hidden name="pageDisplayVo.start" value="2"/>
            <s:hidden id="reportId" name="reportId"/>			
		</s:form>
	</body>
</html>