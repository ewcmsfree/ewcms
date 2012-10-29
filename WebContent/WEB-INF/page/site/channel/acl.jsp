<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
   	<title>频道权限</title>
	<s:include value="../../taglibs.jsp"/>
    <script type="text/javascript" src='<s:url value="/ewcmssource/page/site/channel/acl.js"/>'></script>    
        
    <script type="text/javascript">
        var _acl = new ChannelAcl({
            queryUrl:'<s:url action="aclQuery"/>',
            removeUrl:'<s:url action="removeAcl"/>',
            inheritUrl:'<s:url action="inheritAcl"/>',
            saveUrl:'<s:url action="saveAcl"/>',
            userQueryUrl:'<s:url action="query" namespace="/security/user"/>',
            groupQueryUrl:'<s:url action="query" namespace="/security/group"/>',
            authQueryUrl:'<s:url action="query" namespace="/security/authority"/>'
        });
        
        $(function(){
            _acl.init({id:<s:property value="id"/>});
        });
  	 </script>
</head>
<body class="easyui-layout">
   <div region="center" style="padding:2px;" border="false">
	 	<table id="tt" fit="true"></table>	
	</div>
    <div id="edit-window" class="easyui-window" closed="true" icon='icon-winedit'>
          <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 10px 5px;">
                <form id="permissionform">
                      <table class="formtable" >
                            <tr>
                                <td width="60px">名称：</td>
                                <td class="formFieldError" style="border: none;">
                                   <select id="cc" class="easyui-combobox" name="type" style="width:80px;" required="true">
                                        <option value="user">用户</option>
                                        <option value="group">用户组</option>
                                        <option value="auth">通用权限</option>
                                    </select>
                                    <input type="text" id="input-name" name="name" style="width:150px;" style="margin-right: 5px;"/>
                                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                    <a id="button-query" class="easyui-linkbutton" icon="icon-levels" href="javascript:void(0)" plain="true"></a>
                                    </sec:authorize>
                                </td>
                           </tr>
                           <tr>
                               <td width="60px">权限：</td>
                               <td>
                                   <input type="radio" value="1" name="mask" checked style="vertical-align:middle;"/><span style="vertical-align:middle;">读文章</span>&nbsp;&nbsp;
                                   <input type="radio" value="2" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">写文章</span>&nbsp;&nbsp;
                                   <input type="radio" value="4" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">删除文章</span>&nbsp;&nbsp;
                                   <input type="radio" value="8" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">审核文章</span>&nbsp;&nbsp;
                                   <input type="radio" value="16" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">发布文章</span>&nbsp;&nbsp;
                                   <input type="radio" value="32" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">新建栏目</span>&nbsp;&nbsp;
                                   <input type="radio" value="64" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">修改栏目</span>&nbsp;&nbsp;
                                   <input type="radio" value="128" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">删除栏目</span>&nbsp;&nbsp;
                                   <input type="radio" value="256" name="mask"style="vertical-align:middle;"/><span style="vertical-align:middle;">管理员</span>&nbsp;&nbsp;
                               </td>
                           </tr>
                        </table>
                        <s:hidden name="id"/>
                </form>
                </div>
                <div region="south" border="false" style="text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a id="button-save" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#edit-window').window('close');return false;">取消</a>
                </div>
            </div>
      </div>
      <div id="query-window" icon="icon-winedit" closed="true">
        <div class="easyui-layout" fit="true" >
            <div region="center" border="false" style="padding: 10px 5px;">
                <table id="query-tt" toolbar="#query-tb"></table>
                <div id="query-tb" style="padding: 5px; height: auto; display: none;">
                   <div style="padding-left: 5px;">
                          <form id="auth-queryform" style="padding: 0; margin: 0;">
                                <span id="query-label-name">名称:</span><input id='query-input-name' type="text" name="name" style="width: 100px" />&nbsp; 
                                <span id="query-label-desc">描述:</span><input id='query-input-desc' type="text" name="remark" style="width: 150px" />&nbsp;
                              <a href="#" id="auth-toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                          </form>
                   </div>
                </div>
            </div>
            <div region="south" border="false" style="text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                <a id="button-selected" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a>
                <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#query-window').window('close');return false;">取消</a>
            </div>
         </div>
    </div>
</body>
</html>