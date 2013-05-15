<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
    <head>
        <title>投稿审核</title>
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_simple.js'/>"></script>
        <script type="text/javascript">
            //机构目录树初始
            $(function(){
            	<s:include value="../../alertMessage.jsp"/>
            	
            	if ($('#id').val()==""){
        			parent.closeWindow();
        		}
                $('form textarea').bind("focusin",function(){
                    $("#organ_top_id").css("display","none");
                });
            });
            <s:if test="success">
    			parent.queryInteractionSearch('');
			</s:if>
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>
    </head>
    <body>
            <table class="formtable" align="center">
                <tr>
                    <td width="10%">姓名：</td>
                    <td width="40%">
                        <s:property value="contribute.username"/>
                    </td>
                    <td width="10%">电子邮件：</td>
                    <td width="40%"><s:property value="contribute.email"/></td>
                </tr>
                <tr>
                	<td>联系方式：</td>
                    <td><s:property value="contribute.phone"/></td>
                    <td>投稿时间：</td>
                    <td><s:property value="contribute.date"/></td>
                </tr>
                <tr>
					<td>标题：</td>                	
					<td colspan="3"><s:property value="contribute.title"/></td>
                <tr>
                    <td height="100">内容：</td>
                    <td colspan="3">
                    	<s:property value="contribute.content"/>
                    </td>
                </tr>
            </table>
            <s:hidden id="id" name="id"/>
            <s:hidden name="update" value="true"/>
    </body>
</html>