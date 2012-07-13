<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>公民信息</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../alertMessage.jsp"/>
	        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/citizen">
			<table class="formtable">
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
						<s:textfield id="citizenName" cssClass="inputtext" name="citizenVo.name"/>
						<s:fielderror ><s:param value="%{'citizenVo.name'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="citizenVo.id"/>
             <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>