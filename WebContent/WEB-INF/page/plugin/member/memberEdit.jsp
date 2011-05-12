<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>政协/人大委员新增</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript">
            <s:property value="javaScript" escape="false" escapeJavaScript="false"/>
        </script>		
    </head>
    <body>
        <s:form action="edit" method="post">
            <table class="formtable" align="center">
                <tr>
                    <td width="100">*用户名：</td>
                    <td>
                        <s:if test="eventOP.equals('add')">
                            <s:textfield name="member.username"/>
                        </s:if>
                        <s:else>
                            <s:textfield name="member.username" readonly="true"/>
                        </s:else>
                    </td>
                </tr>
                <tr>
                    <td>真实名称：</td>
                    <td>
                        <s:textfield name="member.name"/>
                    </td>
                </tr>
                <tr>
                    <td>密码：</td>
                    <td>
                        <s:if test="eventOP.equals('add')">
                            <s:password name= "member.password"/>&nbsp;&nbsp;缺省密码为：xy123456
                        </s:if>
                        <s:else>
                            <s:password name= "member.password"/>&nbsp;&nbsp;为空则密码不修改
                        </s:else>
                    </td>
                </tr>
                <tr>
                    <td width="90px">是否有效：</td>
                    <td>
                        <s:checkbox name="member.enabled"/>有效
                    </td>
                </tr>
            </table>
            <s:hidden name="eventOP"/>
            <s:hidden name="member.cppcc"/>
            <s:hidden name="member.nccpc"/>
            <s:hidden name="cppcc"/>
            <s:hidden name="nccpc"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>
        </s:form>
    </body>
</html>