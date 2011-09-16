<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
	<head>
		<title>备忘录</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
		<ewcms:datepickerhead></ewcms:datepickerhead>
        <script type="text/javascript">
            $(function(){
                initWarn();
                $('#warn').click(function() {
                    initWarn();
                });
            });
            function initWarn(){
                if ($('#warn').attr('checked') == 'checked') {
                    $('#tr_warn').show();
                } else {
                    $('#tr_warn').hide();
                }
            }
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
		<s:form id="notesForm" action="save" namespace="/notes">
			<table class="formtable">
				<tr>
					<td width="60">标题：</td>
					<td class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="memorandaVo.title" size="25" maxlength="25"/>
						<s:fielderror ><s:param value="%{'memorandaVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>内容：</td>
					<td>
						<s:textarea id="content" name="memorandaVo.content" cols="50"></s:textarea>
					</td>
				</tr>
				<tr>
				    <td>提醒：</td>
				    <td>
				        <s:checkbox id="warn" name="memorandaVo.warn" cssStyle="vertical-align: top;"/><label for="warn"></label>
				    </td>
				</tr>
				<tr id="tr_warn" >
				    <td>&nbsp;</td>
				    <td>
				        <table class="formtable">
				            <tr>
				                <td>时间：</td>
				                <td>
				                    <ewcms:datepicker id="warnTime" name="warnTime" option="inputsimple" format="HH:mm:00"/>
				                </td>
				            </tr>
                            <tr>
                                <td>重复频率：</td>
                                <td>
                                    <s:select list="@com.ewcms.content.notes.model.FrequencyStatus@values()" listValue="description" name="memorandaVo.frequency" id="frequencyStatus"></s:select>  
                                </td>
                            </tr>
                            <tr>
                                <td>提前：</td>
                                <td>
                                    <s:select list="@com.ewcms.content.notes.model.BeforeStatus@values()" listValue="description" name="memorandaVo.before" id="beforeStatus"></s:select>  
                                </td>
                            </tr>
                            <tr>
                            	<td>错过提醒：</td>
                            	<td>
                            		<s:checkbox id="missRemind" name="memorandaVo.missRemind" cssStyle="vertical-align: top;"/><label for="missRemind"></label>
                            	</td>
                            </tr>
				        </table>
				    </td>
				</tr>
			</table>
			<s:hidden id="memorandaId" name="memorandaVo.id"/>
			<s:hidden id="memorandaVo.noteDate" name="memorandaVo.noteDate"/>
			<s:hidden id="memorandaVo.userName" name="memorandaVo.userName"/>
			<s:hidden id="year" name="year"/>
			<s:hidden id="month" name="month"/>
			<s:hidden id="day" name="day"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>