<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>行业编码</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
            if ($('#industryCodeVo_id').val() != ""){
            	$('#code').attr('readonly', true);
            }else{
            	$('#code').attr('readonly', false);
            }
        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/ic">
			<table class="formtable" >
				<tr>
					<td>行业编码：</td>
					<td class="formFieldError">
						<s:textfield id="id" cssClass="inputtext" name="industryCodeVo.code" maxLength="4" size="4"/>
						<s:fielderror ><s:param value="%{'industryCodeVo.code'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>行业名称：</td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="industryCodeVo.name" size="60"/>
						<s:fielderror ><s:param value="%{'industryCodeVo.name'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden id="industryCodeVo_id" name="industryCodeVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>