<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
 <title>初始用户密码</title>
    <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    
    <script type="text/javascript">
    $(function(){
        <s:if test="hasActionErrors()">
        var errorInfos = '';
        <s:iterator value="actionErrors">  
        errorInfos += '<s:property escape="false"/>\n';
        </s:iterator>
        $.messager.alert('错误',errorInfos);
        </s:if>
        <s:else>
        <s:if test="selections.isEmpty()">
        parent.queryReload(true);
        </s:if>
        </s:else>
    });
    
    function pageSubmit(){
       $('form').submit();
    }
    </script>
</head>
<body>
    <s:form action="savePassword" method="post">
        <table class="formtable" >
            <tr>
                <td width="120px">用户名：</td>
                 <td class="formFieldError">
                     <s:textfield name="username" readonly="true"/>
                     <s:fielderror ><s:param value="%{'username'}"/></s:fielderror>
                 </td>
            </tr>
            <tr>
                <td width="120px">密码：</td>
                 <td class="formFieldError">
                     <s:password name="password"/>
                     <s:fielderror ><s:param value="%{'password'}"/></s:fielderror>
                 </td>
            </tr>
            <tr>
                <td width="120px">确认密码：</td>
                 <td class="formFieldError">
                     <s:password name="passwordAgain"/>
                     <s:fielderror ><s:param value="%{'passwordAgain'}"/></s:fielderror>
                 </td>
            </tr>
        </table>
        <s:iterator value="selections" var="id">
        <s:hidden name="selections" value="%{id}"/>
        </s:iterator>
    </s:form>
</body>
</html>