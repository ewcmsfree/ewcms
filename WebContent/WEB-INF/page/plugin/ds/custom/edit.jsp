<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>custom数据源设置</title>
		<s:include value="../../../taglibs.jsp"/>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../../alertMessage.jsp"/>
	        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/ds/custom">
			<table class="formtable" >
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
						<s:textfield name="customDSVo.name" cssClass="inputtext"maxlength="10" />
						<s:fielderror><s:param value="%{'customDSVo.name'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>自定义名称：</td>
					<td class="formFieldError">
						<s:textfield name="customDSVo.customName" cssClass="inputtext" size="70" maxlength="127" />
						<s:fielderror><s:param value="%{'customDSVo.customName'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>自定义方法：</td>
					<td class="formFieldError">
						<s:textfield name="customDSVo.customMethod" cssClass="inputtext" size="70" maxlength="127" />
						<s:fielderror><s:param value="%{'customDSVo.customMethod'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>
						<s:textarea name="customDSVo.remarks" cols="60"/>
					</td>
				</tr>
			</table>
			<s:hidden name="customDSVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>