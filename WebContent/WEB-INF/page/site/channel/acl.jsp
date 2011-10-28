<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
   	<title>频道权限</title>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>          
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>    
        
    <script type="text/javascript">
    ewcmsBOBJ = new EwcmsBase();
    ewcmsOOBJ = new EwcmsOperate();
    $(function(){
        $('#tt').propertygrid({
            width:450,
            height:'auto',
            url:'<s:url action="aclQuery"/>',
            showGroup:true,
            scrollbarSize:0,
            singleSelect:true,
            columns:[[
                {field:'ck',checkbox:true},
                {field:'name',title:'名称',width:300},
                {field:"value",title:'权限',width:150}
            ]],
            onBeforeLoad:function(param){
                 param['id'] = <s:property value="id"/>;    
            },
            onSelect:function(rowIndex,data){
                if(data.group == '其他'){
                    $('#btnremove').linkbutton('disable');
                }else{
                    $('#btnremove').linkbutton('enable');
                }
            },
            onAfterEdit:function(rowIndex,data,changes){
              if(changes){
                  var params = {};
                  params['id'] = <s:property value='id'/>
                  if(data.name == '继承权限'){
                      params['inherit'] = changes.value;
                      $.post('<s:url action="inheritAcl"/>',params,function(data){
                          if(!data.success){
                              $.messager.alert('错误',data.message,'error');
                          }
                       });
                  }else{
                      params['name'] = data.name;
                      params['mask'] = changes.value;
                      $.post('<s:url action="saveAcl"/>',params,function(data){
                          if(!data.success){
                              $.messager.alert('错误',data.message,'error');
                          }
                       });    
                  }
              }
            },
            toolbar:[{
                id:'btnadd',
                text:'添加',
                iconCls:'icon-add',
                handler:function(){
                    openWindow('#edit-window',{title:'增加 - 权限/用户',width:400,height:200})
              }
            },'-',{
                id:'btnremove',
                text:'删除',
                iconCls:'icon-remove',
                handler:function(){
                    var rows = $('#tt').datagrid('getSelections');
                    if(rows.length == 0){
                        $.messager.alert('提示','请选择删除的记录','info');
                        return;
                    }
                    var parameter ='id=' + <s:property value="id"/> + '&name=' + rows[0].name;
                    $.messager.confirm('提示', '确定删除所选记录?', function(r){
                        if (r){
                            $.post('<s:url action="removeAcl"/>',parameter,function(data){
                                if(data.success){
                                    $("#tt").datagrid('reload');
                                }else{
                                    $.messager.alert('错误',data.message);
                                }
                          });
                        }
                    },'info');
                }
            }]
        });    
        
        $('#button-save').bind('click',function(){
            var value = $("#permissionform").serialize();
            $.post('<s:url action="saveAcl"/>',value,function(data){
               if(data.success){
                   $('#tt').propertygrid('reload');
               } else{
                   $.messager.alert('错误',data.message,'error');
               }
            });
        });
    });
    
  	 </script>
</head>
<body>
    <div style="padding: 10px;">
        <table id="tt" style="margin: 5px;"></table>
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
                                        <option value="role">通用权限</option>
                                    </select>
                                    <input type="text" name="name" style="width:150px;" style="margin-right: 5px;"/>
                                    <sec:authorize ifAnyGranted="ROLE_ADMIN">
                                    <a id="button-save" class="easyui-linkbutton" icon="icon-levels" href="javascript:void(0)" plain="true"></a>
                                    </sec:authorize>
                                </td>
                           </tr>
                           <tr>
                               <td width="60px">权限：</td>
                               <td>
                                   <input type="radio" value="1" name="mask" checked style="vertical-align:middle;"/><span style="vertical-align:middle;">读</span>&nbsp;&nbsp;
                                   <input type="radio" value="2" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">写</span>&nbsp;&nbsp;
                                   <input type="radio" value="4" name="mask" style="vertical-align:middle;"/><span style="vertical-align:middle;">发布</span>&nbsp;&nbsp;
                                   <input type="radio" value="64" name="mask"style="vertical-align:middle;"/><span style="vertical-align:middle;">管理</span>&nbsp;&nbsp;
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
          <table id="query-tt" toolbar="#query-tb"></table>
          <div id="#query-tb" style="padding: 5px; height: auto; display: none;">
              <div style="padding-left: 5px;">
                  <form id="auth-queryform" style="padding: 0; margin: 0;">
                         名称: <input type="text" name="name" style="width: 100px" />&nbsp; 
                         描述: <input type="text" name="remark" style="width: 150px" />&nbsp;
                      <a href="#" id="auth-toolbar-query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                  </form>
             </div>
        </div>
    </div>
</body>
</html>