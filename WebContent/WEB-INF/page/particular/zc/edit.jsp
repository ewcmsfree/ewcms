<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>行政区划代码</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/zc">
			<table class="formtable" >
				<tr>
					<td>行政区划编码：</td>
					<td class="formFieldError">
						<s:textfield id="id" cssClass="inputtext" name="zoningCodeVo.code" maxLength="6" size="6"/>
						<s:fielderror ><s:param value="%{'zoningCodeVo.code'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>行政区划名称：</td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="zoningCodeVo.name" size="60"/>
						<s:fielderror ><s:param value="%{'zoningCodeVo.name'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="zoningCodeVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>