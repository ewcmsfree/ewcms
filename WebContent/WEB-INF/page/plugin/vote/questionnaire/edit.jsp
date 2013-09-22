<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>调查投票</title>
		<s:include value="../../../taglibs.jsp"/>
        <script type="text/javascript">
	    	$(function() {
		        <s:include value="../../../alertMessage.jsp"/>
		    });
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body>
		<s:form action="save" namespace="/vote/questionnaire">
			<table class="formtable" align="center">
				<tr>
					<td width="17%" height="21px">问卷名称：</td>
					<td colspan="3" width="83%" height="21px" class="formFieldError">
						<s:textfield id="title" cssClass="inputtext" name="questionnaireVo.title"/>
						<s:fielderror ><s:param value="%{'questionnaireVo.title'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>查看方式：</td>
					<td>
						<s:radio list="@com.ewcms.plugin.vote.model.Questionnaire$Status@values()" listValue="description" name="questionnaireVo.status" id="questionnaireVo_status"></s:radio>
					</td>
					<td>验证码：</td>
					<td>
						<s:checkbox id="verifiCode" name="questionnaireVo.verifiCode" cssStyle="vertical-align: middle;"/><label for="verifiCode">验证码</label>&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<ewcms:datepicker id="startTime" name="startTime" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>结束时间：</td>
					<td>
						<ewcms:datepicker id="endTime" name="endTime" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td>人数：</td>
					<td class="formFieldError">
						<s:textfield id="number" cssClass="inputtext" name="questionnaireVo.number" maxlength="10" />
						<s:fielderror><s:param value="%{'questionnaireVo.number'}" /></s:fielderror>
					</td>
					<td>结束投票：</td>
					<td>
						<s:checkbox id="voteEnd" name="questionnaireVo.voteEnd"/><label for="voteEnd">&nbsp;</label>
					</td>
				</tr>
				<tr>
					<td>限制IP</td>
					<td>
						<s:checkbox id="isIpLimit" name="questionnaireVo.isIpLimit"/><label for="isIpLimit">&nbsp;</label>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<!-- 
					<td>限制IP一天内</td>
					<td>
						<s:checkbox id="isIpDay" name="questionnaireVo.isIpDay"/><label for="isIpDay">&nbsp;</label>
					</td>
					 -->
				</tr>
			</table>
			<s:hidden name="questionnaireVo.id"/>
			<s:hidden name="questionnaireVo.sort"/>
			<s:hidden name="channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>