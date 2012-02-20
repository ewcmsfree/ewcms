<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>文字报表信息</title>
		<s:include value="../../../taglibs.jsp"/>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../../alertMessage.jsp"/>
	        });
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/report/text" enctype="multipart/form-data" method="post">
			<table class="formtable" >
				<tr>
					<td>报表名：</td>
					<td class="formFieldError">
						<s:textfield name="textReportVo.name" cssClass="inputtext" maxlength="20" />
						<s:fielderror><s:param value="%{'textReportVoVo.name'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>报表文件：</td>
					<td class="formFieldError">
						<s:file	name="textFile" accept="jrxml" theme="simple" onchange="javascript:if(this.value.toLowerCase().lastIndexOf('jrxml')==-1){alert('请选择jrxml文件！');this.value='';}"/>
						<s:fielderror><s:param value="%{'textFile'}" /></s:fielderror>&nbsp;&nbsp;<label style="color: red;">*</label>
					</td>
				</tr>
				<tr>
					<td>数据源类型：</td>
					<td class="formFieldError">
						<s:select list="baseDSList" name="textReportVo.baseDS.id" listKey="id" listValue="name" headerKey="0" headerValue="默认数据源" cssClass="inputtext"/>
					</td>
				</tr>
				<tr>
					<td>是否隐藏：</td>
					<td>
						<s:checkbox	name="textReportVo.hidden" />
					</td>
				</tr>
                <tr>
					<td>备注：</td>
					<td>
						<s:textarea name="textReportVo.remarks" cols="60"/>
					</td>
				</tr>			
			</table>
			<s:hidden name="textReportVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>