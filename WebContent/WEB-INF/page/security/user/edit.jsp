<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
<head>
    <title>用户编辑</title>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/page/security/user/edit.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
    <link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"/>
    <ewcms:datepickerhead/>
    <script type="text/javascript">
       $(function(){
           <s:include value="../../alertMessage.jsp"/>
           var userEdit = new UserEdit({
              detailUrl:'<s:url action="detail"/>',
              hasNameUrl:'<s:url action="hasUsername"/>'
           });
         
           userEdit.init({
               addSaveState:<s:property value="addSaveState"/>,
               username:'<s:property value="username"/>'
           });
       });
            
    </script>
</head>
<body class="easyui-layout" >
        <div region="center" border="false">
            <div class="easyui-tabs" border="false" fit="true">
                 <div title="基本信息" style="padding: 5px;">
                    <s:form action="save" method="post">
                        <table class="formtable" >
                            <tr>
                                <td width="120px">用户名称：</td>
                                <td id="name-td" class="formFieldError" style="border: none;">
                                    <s:if test="eventOP == 'add'">
                                    <s:textfield id="usernameid" cssClass="inputtext" name="user.username"/>
                                    </s:if>
                                    <s:else>
                                    <s:textfield id="usernameid" cssClass="inputtext" name="user.username" readonly="true"/>
                                    </s:else>
                                    <s:fielderror ><s:param value="%{'user.username'}"/></s:fielderror>
                                </td>
                           </tr>
                           <s:if test="eventOP == 'add'">
                           <tr>
                               <td width="120px">初始密码：</td>
                               <td class="formFieldError">
                                   <s:password id="passwordId" name="user.password"/>&nbsp;&nbsp;
                                   <a class="easyui-linkbutton" id="button-default-password" href="javascript:void(0)" plain="true">缺省密码:</a>
                                   <span id="default-password" style="display:none;"><s:property value="defaultPassword"/></span>
                                   <s:fielderror ><s:param value="%{'user.password'}"/></s:fielderror>
                               </td>
                           </tr>
                           </s:if>
                           <tr>
                               <td width="120px">授权开始时间：</td>
                               <td class="formFieldError">
                                   <ewcms:datepicker id="accountStartid" name="user.accountStart" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                                   <s:fielderror ><s:param value="%{'user.accountStart'}"/></s:fielderror>
                               </td>
                           </tr>
                           <tr>
                               <td width="120px">授权结束时间：</td>
                               <td class="formFieldError">
                                   <ewcms:datepicker id="accountEndid" name="user.accountEnd" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                                   <s:fielderror ><s:param value="%{'user.accountEnd'}"/></s:fielderror>
                               </td>
                           </tr>
                            <tr>
                               <td width="120px">启用：</td>
                               <td>
                                   <s:checkbox cssClass="inputtext" name="user.enabled"/>
                               </td>
                           </tr>
                        </table>
                        <h2>详细信息：</h2>
                        <table class="formtable" >
                           <tr>
                               <td width="120px">姓名：</td>
                               <td class="formFieldError">
                                   <s:textfield cssClass="inputtext" name="user.userInfo.name"/>
                                   <s:fielderror ><s:param value="%{'user.userInfo.name'}"/></s:fielderror>
                               </td>
                           </tr>
                            <tr>
                               <td width="120px">证件编号：</td>
                               <td class="formFieldError">
                                   <s:textfield cssClass="inputtext" name="user.userInfo.identification"/>
                                   <s:fielderror ><s:param value="%{'user.userInfo.identification'}"/></s:fielderror>
                               </td>
                           </tr>
                           <tr>
                               <td width="120px">生日：</td>
                               <td class="formFieldError">
                                   <ewcms:datepicker id="accountStartid" name="user.userInfo.birthday" option="inputsimple" format="yyyy-MM-dd"/>
                                   <s:fielderror ><s:param value="%{'user.userInfo.birthday'}"/></s:fielderror>
                               </td>
                           </tr>
                           <tr>
                               <td width="120px">邮件地址：</td>
                               <td class="formFieldError">
                                   <s:textfield cssClass="inputtext" name="user.userInfo.email"/>
                                   <s:fielderror ><s:param value="%{'user.userInfo.email'}"/></s:fielderror>
                               </td>
                           </tr>
                           <tr>
                               <td width="120px">电话：</td>
                               <td class="formFieldError">
                                   <s:textfield cssClass="inputtext" name="user.userInfo.phone"/>
                                   <s:fielderror ><s:param value="%{'user.userInfo.phone'}"/></s:fielderror>
                               </td>
                           </tr>
                           <tr>
                               <td width="120px">手机：</td>
                               <td class="formFieldError">
                                   <s:textfield cssClass="inputtext" name="user.userInfo.mphone"/>
                                   <s:fielderror ><s:param value="%{'user.userInfo.mphone'}"/></s:fielderror>
                               </td>
                           </tr>
                        </table>
                        <s:hidden name="eventOP"/>
                        <s:iterator value="newUsernames" var="newUsername">
                        <s:hidden name="newUsernames" value="%{newUsername}"/>
                        </s:iterator>
                    </s:form>
                 </div>
                 <s:if test="showAuthGroupTab">
                 <div title="权限/用户组" style="padding: 5px;">
                     <iframe id="editifr-id" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                 </div>
                 </s:if>
            </div>
        </div>
        <div region="south" border="false" style="padding:3px;text-align:right;height:38px;line-height:30px;background-color:#f6f6f6">
            <a class="easyui-linkbutton" id="button-new" style="display:none;" icon="icon-add" href="javascript:void(0)">新增</a>
            <a class="easyui-linkbutton" id="button-save" icon="icon-save" href="javascript:void(0)">保存</a>
            <a class="easyui-linkbutton" id="button-cancel" icon="icon-cancel" href="javascript:void(0)">关闭</a>
       </div>
</body>
</html>