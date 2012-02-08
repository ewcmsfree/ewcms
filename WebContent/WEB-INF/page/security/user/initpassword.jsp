<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
 <title>初始用户密码</title>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/page/security/user/initpassword.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/cupertino/easyui.css"/>' rel="stylesheet" title="cupertino"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
    <link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"/>
    
    <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
            var initPassword = new InitPassword();
            initPassword.init();
        });
    </script>
    
</head>
<body class="easyui-layout">
    <div region="center" border="false">
        <s:form action="saveInitPassword" method="post">
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
        </s:form>
    </div>
    <div region="south" border="false" style="padding:3px;text-align:right;height:38px;line-height:30px;background-color:#f6f6f6">
        <a class="easyui-linkbutton" id="button-save" icon="icon-save" href="javascript:void(0)">保存</a>
        <a class="easyui-linkbutton" id="button-cancel" icon="icon-cancel" href="javascript:void(0)">关闭</a>
    </div>
</body>
</html>