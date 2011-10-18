<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户组编辑</title>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/page/security/group/detail.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/default/easyui.css"/>' />
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    <script type="text/javascript">
       $(function(){
           $("#button-auth-user").bind('click',function(){
               parent.openDetailWindow('<s:property value="name"/>');
           });
       });
       
       
       function groupnameExist(callback){
           var url = '<s:url action = "hasName"/>?name='+$('input[name=name]').val();
           $.getJSON(url,function(data){
               if(data.exist){
                   $('#name-td ul').remove();
                   $('<ul>').appendTo('#name-td');
                   $('<li>用户组名已经存在</li>').appendTo('#name-td ul');
               }else{
                   $('#name-td ul').remove();
               }
               if(callback){
                   callback(data.exist);
               }
           });
       }
       
       function pageSubmit(){
           $('form').submit();
       }
       
    </script>
</head>
<body class="easyui-layout" >
        <div region="center" border="false" style="padding:10px;">
            <s:form action="save" method="post">
                <table class="formtable">
                    <tr>
                        <td>名称：</td>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td style="border: none;">GROP_</td>
                                    <td id="name-td" class="formFieldError" style="border: none;">
                                        <s:if test = "eventOP == 'update'">
                                        <s:textfield cssClass="inputtext" name="name" readonly="true" cssStyle="width:250px;" />
                                        </s:if>
                                        <s:else>
                                        <s:textfield cssClass="inputtext" name="name" cssStyle="width:250px;" />
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
                            <s:textarea name="remark" cssStyle="width:300px;height:120px;" />
                            <s:fielderror><s:param value="%{'remark'}" /> </s:fielderror>
                        </td>
                    </tr>
                </table>
                <s:hidden name="eventOP" />
            </s:form>
        </div>
        <div region="south" border="false" style="padding:3px;text-align:right;height:38px;line-height:30px;background-color:#f6f6f6">
            <a class="easyui-linkbutton" id="button-auth-user" style="margin-right: 150px;" href="javascript:void(0)">修改 权限/用户</a>
            <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="editifr.pageSubmit()">保存</a>
            <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">取消</a>
       </div>
</body>
</html>