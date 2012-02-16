<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>用户明细</title>
<s:include value="../../taglibs.jsp"/>
<script type="text/javascript" src='<s:url value="/ewcmssource/page/security/user/detail.js"/>'></script>

<script type="text/javascript">
    $(function() {
        var detail = new UserDetail({
                    queryUrl : '<s:url action="detailQuery"/>',
                    authQueryUrl : '<s:url action="query" namespace="/security/authority"/>',
                    groupQueryUrl : '<s:url action="query" namespace="/security/group"/>',
                    addUrl : '<s:url action="addAuthsAndGroups"/>',
                    removeUrl : '<s:url action="removeAuthsAndGroups"/>'
                });
        
        detail.init({
            username : '<s:property value="username"/>',
            showTitle : <s:property value="showTitle"/>
        });
    });
</script>
</head>
<body class="easyui-layout">
    <div region="center" border="false" collapsible="false">
        <table id="tt"></table>
        <s:hidden value="name"></s:hidden>
        <div id="edit-window" icon="icon-winedit" closed="true">
            <div class="easyui-tabs" border="false" fit="true">
                <div title="权限" style="padding: 5px;">
                    <table id="auth-tt" toolbar="#auth-tb"></table>
                    <div id="auth-tb" style="padding: 5px; height: auto; display: none;">
                        <div style="padding-left: 5px;">
                            <form id="auth-queryform" style="padding: 0; margin: 0;">
                                    名称: <input type="text" name="name" style="width: 100px" />&nbsp; 
                                    描述: <input type="text" name="remark" style="width: 150px" />&nbsp;
                                <a href="#" id="auth-toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </form>
                        </div>
                    </div>
                </div>
                <div title="用户组" style="padding: 5px;">
                    <table id="group-tt" toolbar="#group-tb"></table>
                    <div id="group-tb" style="padding: 5px; height: auto; display: none;">
                        <div style="padding-left: 5px;">
                            <form id="group-queryform" style="padding: 0; margin: 0;">
                                    名称: <input type="text" name="name" style="width: 100px" />&nbsp; 
                                    描述: <input type="text" name="remark" style="width: 150px" />&nbsp;
                                <a href="#" id="group-toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>