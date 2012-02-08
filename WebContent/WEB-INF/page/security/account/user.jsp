<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
<head>
    <title>修改用户信息</title>
    <script type='text/javascript' src='<s:url value="/ewcmssource/js/jquery.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
    <link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"/>
    <ewcms:datepickerhead/>
    <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
            parent.updateUsername('<s:property value="userInfo.name"/>');
        });
       
       function pageSubmit(){
           $('form').submit();
       }
    </script>
</head>
<body>
    <s:form action="saveUser" method="post">
        <table class="formtable">
            <tr>
                <td width="120px">姓名：</td>
                <td class="formFieldError">
                    <s:textfield cssClass="inputtext"  name="userInfo.name" />
                    <s:fielderror><s:param value="%{'userInfo.name'}" /></s:fielderror>
                </td>
            </tr>
            <tr>
                <td width="120px">证件编号：</td>
                <td class="formFieldError">
                    <s:textfield cssClass="inputtext" name="userInfo.identification" />
                    <s:fielderror><s:param value="%{'userInfo.identification'}" /></s:fielderror>
                </td>
            </tr>
            <tr>
                <td width="120px">生日：</td>
                <td class="formFieldError">
                    <ewcms:datepicker id="accountStartid" name="userInfo.birthday" option="inputsimple" format="yyyy-MM-dd" /> 
                    <s:fielderror><s:param value="%{'userInfo.birthday'}" /></s:fielderror>
                </td>
            </tr>
            <tr>
                <td width="120px">邮件地址：</td>
                <td class="formFieldError">
                    <s:textfield cssClass="inputtext" name="userInfo.email"/>
                    <s:fielderror><s:param value="%{'userInfo.email'}" /></s:fielderror>
                </td>
            </tr>
            <tr>
                <td width="120px">电话：</td>
                <td class="formFieldError">
                    <s:textfield cssClass="inputtext" name="userInfo.phone" />
                    <s:fielderror><s:param value="%{'userInfo.phone'}" /></s:fielderror>
                </td>
            </tr>
            <tr>
                <td width="120px">手机：</td>
                <td class="formFieldError">
                    <s:textfield cssClass="inputtext" name="userInfo.mphone" />
                    <s:fielderror><s:param value="%{'userInfo.mphone'}" /></s:fielderror>
                </td>
            </tr>
        </table>
    </s:form>
</body>
</html>