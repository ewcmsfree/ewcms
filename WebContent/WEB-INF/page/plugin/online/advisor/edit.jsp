<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>网上咨询回复</title>
		<s:include value="../../../taglibs.jsp"/>
        <script type="text/javascript">
        	$(function(){
        		if ($('#id').val()==""){
        			parent.closeWindow();
        		}
        	});
            <s:if test="success">
                parent.queryAdvisorSearch('');
            </s:if>
        </script>
    </head>
    <body>
        <s:form action="edit" method="post">
            <table class="formtable" align="center">
                <tr>
                    <td width="100">编号：</td>
                    <td>
                        <s:property value="advisor.id"/>
                    </td>
                </tr>
                <tr>
                    <td>标题：</td>
                    <td>
                        <s:property value="advisor.title"/>
                    </td>
                </tr>
                <tr>
                    <td>用户：</td>
                    <td>
                        <s:property value="advisor.name"/>
                    </td>
                </tr>
                <tr>
                    <td>咨询：</td>
                    <td>
                        <s:property value="advisor.matter.name"/>
                    </td>
                </tr>
                <tr>
                    <td>咨询单位：</td>
                    <td>
                        <s:property value="advisor.organ.name"/>
                    </td>
                </tr>
                <tr>
                    <td height="100">内容：</td>
                    <td>
                        <div style="height:40;overflow: auto;"><s:property value="advisor.content"/></div>
                    </td>
                </tr>
                <tr>
                    <td>回复：</td>
                    <td>
                        <s:textarea name="replay" style="height:100px;width:500px"/>
                    </td>
                </tr>
                <tr>
                    <td>发布：</td>
                    <td>
                        <s:radio name="checked" list="#{'true':'通过','false':'未通过'}"/>
                    </td>
                </tr>
            </table>
            <s:hidden id="id" name="id"/>
        </s:form>
        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
             <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="document.forms[0].submit();">保存</a>
        </div>
    </body>
</html>