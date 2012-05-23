<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>发布部门</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/ps">
			<table class="formtable" >
				<tr>
					<td>组织机构代码：</td>
					<td class="formFieldError">
						<s:textfield id="id" cssClass="inputtext" name="publishingSectorVo.code" maxLength="9" size="9"/>
						<s:fielderror ><s:param value="%{'publishingSectorVo.code'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>机关单位名称：</td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="publishingSectorVo.name" size="60"/>
						<s:fielderror ><s:param value="%{'publishingSectorVo.name'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="publishingSectorVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>