<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>用户组明细</title>
<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
<script type="text/javascript" src='<s:url value="/source/easyui/jquery.easyui.min.js"/>'></script>
<script type="text/javascript" src='<s:url value="/source/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
<script type="text/javascript" src='<s:url value="/source/page/security/group/detail.js"/>'></script>
<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/default/easyui.css"/>' />
<link rel="stylesheet" type="text/css" href='<s:url value="/source/easyui/themes/icon.css"/>'>

<script type="text/javascript">
    $(function() {
        var detail = new GroupDetail({
                    queryUrl : '<s:url action="detailQuery"/>',
                    authQueryUrl : '<s:url action="query" namespace="/security/authority"/>',
                    userQueryUrl : '<s:url action="query" namespace="/security/user"/>',
                    addUrl : '<s:url action="addAuthsAndUsers"/>',
                    removeUrl : '<s:url action="removeAuthsAndUsers"/>'
                });
        
        detail.init({
            groupName : '<s:property value="name"/>'
        });
    });
</script>
</head>
<body>
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
            <div title="用户" style="padding: 5px;">
                <table id="user-tt" toolbar="#user-tb"></table>
                <div id="user-tb" style="padding: 5px; height: auto; display: none;">
                    <div style="padding-left: 5px;">
                        <form id="user-queryform" style="padding: 0; margin: 0;">
                                名称: <input type="text" name="username" style="width: 100px" />&nbsp; 
                                姓名: <input type="text" name=name style="width: 150px" />&nbsp;
                            <a href="#" id="user-toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>