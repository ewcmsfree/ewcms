<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户组编辑</title>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript">
       
       $(function(){
           <s:if test="eventOP == 'update' && closeWindow">
           parent.closeWindow();
           </s:if>
           <s:else>
           init();
           <s:if test="hasActionErrors()">
           <s:iterator value="actionErrors">  
           $.messager.alert('错误','<s:property escape="false"/>\n');
           </s:iterator>  
           </s:if>
           </s:else>
       });
       
       function init(){
           $("#auth_all_tt").datagrid({
               title:'',
               fitColumns:true,
               nowrap: false,
               rownumbers:true,
               idField:'name',
               pagination:true,
               url:'<s:url action="query" namespace="/security/authority"/>',
               frozenColumns:[[
                    {field:'ck',checkbox:true},
                    {field:"name",title:'权限名称',width:100}
               ]],
               columns:[[
                    {field:"remark",title:'描述',width:300}
               ]]
           });
          $("#auth_tt").datagrid({
              title:'',
              fitColumns:true,
              nowrap: false,
              singleSelect:false,
              rownumbers:true,
              idField:'name',
              frozenColumns:[[
                   {field:'ck',checkbox:true},
                   {field:"name",title:'权限名称',width:200}
              ]],
              columns:[[
                   {field:"remark",title:'说明',width:300}
              ]],
              toolbar:[{
                  id:'btn_auth_add',
                  text:'添加',
                  iconCls:'icon-add',
                  handler:function(){
                      $('#auth_dd').dialog('open');
                }
              },'-',{
                  id:'btn_auth_remove',
                  text:'删除',
                  iconCls:'icon-remove',
                  handler:function(){
                      removeSelectRows('#auth_tt');
                  }
              }]
          });
          $("#user_all_tt").datagrid({
              title:'',
              fitColumns:true,
              nowrap: false,
              rownumbers:true,
              idField:'username',
              pagination:true,
              url:'<s:url action="query" namespace="/security/user"/>',
              frozenColumns:[[
                   {field:'ck',checkbox:true},
                   {field:"username",title:'用户名称',width:100}
              ]],
              columns:[[
                   {field:"userInfo",title:'姓名',width:300,formatter:function(value,row){
                       if(value){
                           return value.name;     
                       }else{
                           return '';
                       }
                   }}
              ]]
          });
          $("#user_tt").datagrid({
              title:'',
              fitColumns:true,
              nowrap: false,
              singleSelect:false,
              rownumbers:true,
              frozenColumns:[[
                   {field:'ck',checkbox:true},
                   {field:"name",title:'用户名称',width:200}
              ]],
              columns:[[
                   {field:"remark",title:'姓名',width:300}
              ]],
              toolbar:[{
                  id:'btn_user_add',
                  text:'添加',
                  iconCls:'icon-add',
                  handler:function(){
                      $('#user_dd').dialog('open');
                  }
                },'-',{
                  id:'btn_user_remove',
                  text:'删除',
                  iconCls:'icon-remove',
                  handler:function(){
                      removeSelectRows('#user_tt');
                  }
              }]
          });
        
          $('#auth_dd').dialog({
              buttons:[{
                  text:'确定',
                  iconCls:'icon-ok',
                  handler:function(){
                      var source = "#auth_all_tt";
                      var target = "#auth_tt";
                      addSelectRows(source,target,"name",function(value){
                          var row = {"name":value.name,"remark":value.remark};
                          return row;
                      });
                      $(source).datagrid('unselectAll');
                      $('#auth_dd').dialog('close');
                  }
               }],
               onOpen:function(){
                   $(this).window('resize');
                   $("#auth_all_tt").datagrid("resize");
               }
           });
          $('#user_dd').dialog({
              buttons:[{
                  text:'确定',
                  iconCls:'icon-ok',
                  handler:function(){
                      var source = "#user_all_tt";
                      var target = "#user_tt";
                      addSelectRows(source,target,"username",function(value){
                          var row = {"name":value.username,"remark":value.userInfo.name};
                          return row;
                      });
                      $(source).datagrid('unselectAll');
                      $('#user_dd').dialog('close');
                  }
               }],
               onOpen:function(){
                   $(this).window('resize');
                   $("#user_all_tt").datagrid("resize");
               }
           });
          <s:if test = "!authorities.isEmpty()">
          <s:iterator value="authorities" id="auth">
          var row = {"name":"<s:property value="name"/>","remark":"<s:property value="remark"/>"};
          $("#auth_tt").datagrid('appendRow',row);
          </s:iterator>               
          </s:if>
          
          <s:if test = "!users.isEmpty()">
          <s:iterator value="users" id="user">
          var row = {"name":"<s:property value="username"/>","remark":"<s:property value="userInfo.name"/>"};
          $("#user_tt").datagrid('appendRow',row);
          </s:iterator>               
          </s:if>
          
          <s:if test="eventOP != 'update'">
          $('input[name=name]').bind('focusout', function(){
              groupnameExist();
          })
          </s:if>
       }
       
       function groupnameExist(callback){
           var url = '<s:url action = "groupnameExist"/>?name='+$('input[name=name]').val();
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
       
       function removeSelectRows(selected){
           var rows = $(selected).datagrid('getSelections');
           if(rows.length == 0){
               
           }else{
               for(var i = 0 ; i < rows.length ; i++ ){
                   var row = rows[i];
                   var index = $(selected).datagrid('getRowIndex',row);
                   $(selected).datagrid('deleteRow',index);
               }
           }
       }
       
       function addSelectRows(source,target,property,callbackRow){
           var selects = $(source).datagrid('getSelections');
           if(selects.length == 0){
               return ;
           }
           var existRows = $(target).datagrid('getRows');
           for(var i = 0 ; i < selects.length ; i++){
               var  notExist = true;
               for(var j = 0 ; j < existRows.length; j++){
                   if(selects[i][property] == existRows[j].name){
                       notExist = false;
                       break;
                   }
               }
               if(notExist){
                   var row = callbackRow(selects[i]);
                   $(target).datagrid('appendRow',row);
               }
           }
       }
       
       function pageSubmit(){
           var rows = $("#auth_tt").datagrid('getRows');
           for(var i = 0 ; i < rows.length ; i++ ){
               appendForm('authorities.makeNew[' + i + '].name',rows[i].name);
               appendForm('authorities.makeNew[' + i + '].remark',rows[i].remark);
           }
           rows = $("#user_tt").datagrid('getRows');
           for(var i = 0 ; i < rows.length ; i++ ){
               appendForm('users.makeNew[' + i + '].username',rows[i].name);
               appendForm('users.makeNew[' + i + '].userInfo.name',rows[i].remark);
           }
           groupnameExist(function(exist){
               if(exist){
                   $.messager.alert('错误','用户组名已经存在');
               }else{
                   $('form').submit();
               }
           })
       }
       
       function appendForm(name,value){
           $('<input>').attr({
               type: 'hidden',
               name: name,
               value: value
           }).appendTo('form');
       }
    </script>
</head>
<body>
    <div class="easyui-tabs"  id="systemtab" border="false" fit="true">
        <div title="基本信息"  style="padding: 5px;">
            <s:form action="save" method="post">
                <table class="formtable" >
                    <tr>
                        <td>名称：</td>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td style="border: none;">GROP_</td>
                                    <td id="name-td" class="formFieldError" style="border: none;">
                                        <s:if test="eventOP == 'add'">
                                        <s:textfield cssClass="inputtext" name="name" cssStyle="width:350px;"/>
                                        </s:if>
                                        <s:else>
                                        <s:textfield cssClass="inputtext" name="name" readonly="true" cssStyle="width:350px;"/>
                                        </s:else>
                                        <s:fielderror ><s:param value="%{'name'}" /></s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                   </tr>
                   <tr>
                       <td>备注：</td>
                       <td class="formFieldError">
                           <s:textarea name="remark" cssStyle="width:400px;height:120px;"/>
                           <s:fielderror ><s:param value="%{'remark'}"/></s:fielderror>
                       </td>
                   </tr>
                </table>
                <s:hidden name="eventOP"/>
            </s:form>
        </div>
        <div title="所属权限" >
            <table id="auth_tt" fit="true"></table>
        </div>
        <div title="所属用户">
            <table id = "user_tt" fit="true"></table>
        </div>
   </div>
   <div id="user_dd" title="用户列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:300px;">
       <table id="user_all_tt" style="width:565px;height:220px;"></table>
   </div>
   <div id="auth_dd" title="权限列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:300px;">
     <table id="auth_all_tt" style="width:565px;height:220px;"></table>
   </div>
</body>
</html>