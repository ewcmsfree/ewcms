<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>URL层级属性</title>
		<s:include value="../../../taglibs.jsp"/>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../../alertMessage.jsp"/>
	        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/crawler/domain">
			<table class="formtable" >
				<tr>
					<td width="10%">URL：</td>
					<td width="90%" class="formFieldError">
						<s:textfield id="regex" cssClass="inputtext" name="domainVo.url" size="40" maxlength="50"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'domainVo.url'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="domainVo.id"/>
			<s:hidden name="gatherId" name="gatherId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>