<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户组编辑</title>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/page/security/group/edit.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/cupertino/easyui.css"/>' rel="stylesheet" title="cupertino"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'/>
    <link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"/>
    <script type="text/javascript">
       $(function(){
           <s:include value="../../alertMessage.jsp"/>
           var groupEdit = new GroupEdit({
              detailUrl:'<s:url action="detail"/>',
              hasNameUrl:'<s:url action="hasGroupname"/>'
           });
         
           groupEdit.init({
               addSaveState:<s:property value="addSaveState"/>,
               name:'<s:property value="fullname"/>'
           });
       });
            
    </script>
</head>
<body class="easyui-layout" >
        <div region="center" border="false">
            <div class="easyui-tabs" border="false" fit="true">
                 <div title="基本信息" style="padding: 5px;">
                     <s:form action="save" method="post">
                        <table class="formtable">
                            <tr>
                                <td>名称：</td>
                                <td>
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td style="border: none;">GROP_</td>
                                            <td id="name-td" class="formFieldError" style="border: none;">
                                                <s:if test="eventOP=='add'">
                                                <s:textfield cssClass="inputtext" name="name" cssStyle="width:350px;" />
                                                </s:if>
                                                <s:else>
                                                <s:textfield cssClass="inputtext" name="name" readonly="true" cssStyle="width:350px;" />
                                                </s:else>
                                                <s:fielderror><s:param value="%{'name'}"/></s:fielderror>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>备注：</td>
                                <td class="formFieldError">
                                    <s:textarea name="remark" cssStyle="width:400px;height:120px;" />
                                    <s:fielderror><s:param value="%{'remark'}" /> </s:fielderror>
                                </td>
                            </tr>
                        </table>
                        <s:hidden name="eventOP" />
                        <s:iterator value="newGroupNames" var="newName">
                            <s:hidden name="newGroupNames" value="%{newName}"/>
                        </s:iterator>
                     </s:form>
                 </div>
                 <s:if test="showAuthUserTab">
                 <div title="权限/用户" style="padding: 5px;">
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