<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="sec" uri = "http://www.springframework.org/security/tags"%>

<html>
	<head>
		<title>查询参数设置</title>
		<s:include value="../../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/ds/index.js"/>'></script>
		<ewcms:datepickerhead/>
		<script type="text/javascript">
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
			  if(patrn.test(str))   {  
			  	return true;    
			  }else{  
			  	return false;  
			  }   
			}
		</script>
	</head>
	<body>
		<s:form action="build" namespace="/report/show" target="_blank">
			<table class="basetable">
				<tr>
					<td>
						<table width="100%">
							<s:iterator id="param" value="parameters" >
								<tr>
									<td class="texttd">
									<s:if test="(cnName!=null)&&(cnName.length()>0)">
									<s:property	value="cnName" />：
									</s:if>
									<s:else>
									<s:property	value="enName" />：
									</s:else>
									</td>
									<td class="inputtd">
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
												<input type="text" name="paraMap['<s:property value="enName"/>']"  value='<sec:authentication property="principal.username"/>' readonly="readonly">
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
							<s:if test="textable">
								<tr>
									<td class="texttd">报表文件类型：</td>
									<td class="inputtd">
										<s:select list="fileFormatList" name="fileFormat" listKey="name()" listValue="description"/>
									</td>
								</tr>	
							</s:if>						
						</table>
					</td>
				</tr>
			</table>
			<s:hidden name="reportType"/>
			<s:hidden name="reportId"/>
		</s:form>
	</body>
</html>