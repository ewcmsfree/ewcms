<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>修改密码</title>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/source/page/security/account/password.css"/>'/>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/source/js/digitialspaghetti.password.min.js"/>'></script>
        <script type="text/javascript">
            $(function(){
            <s:if test="hasActionErrors()">
                <s:iterator value="actionErrors">  
                $.messager.alert('错误','<s:property escape="false"/>');
                </s:iterator>  
            </s:if>
            <s:if test="hasActionMessages()">
                <s:iterator value="actionMessages">  
                $.messager.alert('提示','<s:property escape="false"/>');
                </s:iterator>  
           </s:if>
                $('input[name=password]').pstrength({
                    'displayMinChar': false,
                    'minChar': 6,
                    'colors': ["#f00", "#c06", "#f60", "#3c0", "#3f0"],
                    'scores': [20, 30, 43, 50],
                    'verdicts': ['弱', '一般', '较强', '强', '非常强']
                });
            });
            
            function pageSubmit(){
                $('form').submit();
            }
        </script>
    </head>
    <body>
        <s:form action="savePassword" method="post">
             <table class="formtable">
                 <tr>
                     <td class='title'>现有密码:</td>
                     <td>
                         <table class="table_none" border="0" cellpadding="2" cellspacing="0" >
                             <tr>
                                 <td class="input_t"><s:password name="oldPassword" cssClass="input"/></td>
                                 <td><s:fielderror ><s:param value="%{'oldPassword'}"/></s:fielderror></td>
                             </tr>
                         </table>
                     </td>
                </tr>
                <tr>
                    <td class='title'>新密码:</td>
                    <td>
                         <table class="table_none" border="0" cellpadding="2" cellspacing="0">
                             <tr>
                                 <td class="input_t"><s:password name="password" cssClass="input"/></td>
                                 <td><s:fielderror ><s:param value="%{'password'}"/></s:fielderror></td>
                             </tr>
                             <tr>
                                 <td class='password-help' colspan="2">6-16位字符，可以是半角字母、数字、“.”、“-”、“?”和下划线</td>
                             </tr>
                         </table>
                    </td>
                </tr>
                <tr>
                    <td class='title'>确认密码:</td>
                    <td>
                        <table class="table_none" border="0" cellpadding="2" cellspacing="0">
                             <tr>
                                 <td class="input_t"><s:password name="againPassword" cssClass="input"/></td>
                                 <td><s:fielderror ><s:param value="%{'againPassword'}"/></s:fielderror></td>
                             </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </s:form>
    </body>
</html>