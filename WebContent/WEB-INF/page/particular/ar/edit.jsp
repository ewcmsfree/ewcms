<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>审批备案机关</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
            if ($('#approvalRecordVo_id').val() != ""){
            	$('#code').attr('readonly', true);
            }else{
            	$('#code').attr('readonly', false);
            }
        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/ar">
			<table class="formtable" >
				<tr>
					<td>组织机构代码：</td>
					<td class="formFieldError">
						<s:textfield id="code" cssClass="inputtext" name="approvalRecordVo.code" maxLength="9" size="9"/>
						<s:fielderror ><s:param value="%{'approvalRecordVo.code'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>机关单位名称：</td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="approvalRecordVo.name" size="60"/>
						<s:fielderror ><s:param value="%{'approvalRecordVo.name'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden id="approvalRecordVo_id" name="approvalRecordVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>