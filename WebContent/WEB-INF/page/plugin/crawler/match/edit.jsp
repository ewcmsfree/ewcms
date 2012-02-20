<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>匹配块属性</title>
		<s:include value="../../../taglibs.jsp"/>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../../alertMessage.jsp"/>
	        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/crawler/match">
			<table class="formtable" >
				<tr>
					<td>表达式：</td>
					<td class="formFieldError">
						<s:textarea id="regex" name="matchBlockVo.regex" cols="50"/><a href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-help" onclick="parent.parent.helpOperate();"></a><font color="red">*</font>
						<s:fielderror><s:param value="%{'matchBlockVo.regex'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="matchBlockVo.id"/>
			<s:hidden name="parentId" id="parentId"/>
			<s:hidden name="gatherId" id="gatherId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>