<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
<head>
    <title>用户编辑</title>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    <script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <ewcms:datepickerhead/>
    <script type="text/javascript">
       $(function(){
           init();
           <s:if test="hasActionErrors()">
           <s:iterator value="actionErrors">  
           $.messager.alert('错误','<s:property escape="false"/>\n');
           </s:iterator>  
           </s:if>
           <s:property value="javaScript" escape="false"/>
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
          $("#group_all_tt").datagrid({
              title:'',
              fitColumns:true,
              nowrap: false,
              rownumbers:true,
              idField:'name',
              pagination:true,
              url:'<s:url action="query" namespace="/security/group"/>',
              frozenColumns:[[
                   {field:'ck',checkbox:true},
                   {field:"name",title:'用户组名称',width:100}
              ]],
              columns:[[
                   {field:"remark",title:'说明',width:300}
              ]]
          });
          $("#group_tt").datagrid({
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
                   {field:"remark",title:'说明',width:300}
              ]],
              toolbar:[{
                  id:'btn_group_add',
                  text:'添加',
                  iconCls:'icon-add',
                  handler:function(){
                      $('#group_dd').dialog('open');
                  }
                },'-',{
                  id:'btn_group_remove',
                  text:'删除',
                  iconCls:'icon-remove',
                  handler:function(){
                      removeSelectRows('#group_tt');
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
          $('#group_dd').dialog({
              buttons:[{
                  text:'确定',
                  iconCls:'icon-ok',
                  handler:function(){
                      var source = "#group_all_tt";
                      var target = "#group_tt";
                      addSelectRows(source,target,"name",function(value){
                          var row = {"name":value.name,"remark":value.remark};
                          return row;
                      });
                      $(source).datagrid('unselectAll');
                      $('#group_dd').dialog('close');
                  }
               }],
               onOpen:function(){
                   $(this).window('resize');
                   $("#group_all_tt").datagrid("resize");
               }
           });
          <s:if test = "!authorities.isEmpty()">
          <s:iterator value="authorities" id="auth">
          var row = {"name":"<s:property value="name"/>","remark":"<s:property value="remark"/>"};
          $("#auth_tt").datagrid('appendRow',row);
          </s:iterator>               
          </s:if>
          
          <s:if test = "!groups.isEmpty()">
          <s:iterator value="groups" id="group">
          var row = {"name":"<s:property value="name"/>","remark":"<s:property value="remark"/>"};
          $("#group_tt").datagrid('appendRow',row);
          </s:iterator>               
          </s:if>
          
          $('#inputusername').bind('focusout', function(){
              usernameExist();
          });
          
          $('#defaultPasswordId').toggle(function(){
              $(this).html($(this).attr('title'));
          },function(){
              $(this).html('显示缺省密码');
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
       
       function usernameExist(callback){
           var url = '<s:url action = "usernameExist"/>?selections='+$('#inputusername').val();
           $.getJSON(url,function(data){
               if(data.exist){
                   $('#name-td ul').remove();
                   $('<ul>').appendTo('#name-td');
                   $('<li>用户名已经存在</li>').appendTo('#name-td ul');
               }else{
                   $('#name-td ul').remove();
               }
               if(callback){
                   callback(data.exist);
               }
           });
       }
       
       function pageSubmit(){
           var rows = $("#auth_tt").datagrid('getRows');
           for(var i = 0 ; i < rows.length ; i++ ){
               appendForm('authorities.makeNew[' + i + '].name',rows[i].name);
               appendForm('authorities.makeNew[' + i + '].remark',rows[i].remark);
           }
           rows = $("#group_tt").datagrid('getRows');
           for(var i = 0 ; i < rows.length ; i++ ){
               appendForm('groups.makeNew[' + i + '].name',rows[i].name);
               appendForm('groups.makeNew[' + i + '].remark',rows[i].remark);
           }
           usernameExist(function(exist){
               if(exist){
                   $.messager.alert('错误','用户名已经存在');
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
    <style type="text/css">
        h2 {font-size: 110%;color:#cc66ff;font-weight:normal;margin: 10px 0 3px 5px;}
    </style>
</head>
<body>
    <div class="easyui-tabs"  id="systemtab" border="false" fit="true">
        <div title="基本信息"  style="padding: 5px;">
            <s:form action="save" method="post">
                <table class="formtable" >
                    <tr>
                        <td width="120px">用户名称：</td>
                        <td id="name-td" class="formFieldError" style="border: none;">
                            <s:if test="eventOP == 'add'">
                            <s:textfield id="inputusername" cssClass="inputtext" name="user.username"/>
                            <s:fielderror ><s:param value="%{'user.username'}"/></s:fielderror>
                            </s:if>
                            <s:else>
                            <s:textfield cssClass="inputtext" name="user.username" readonly="true"/>
                            </s:else>
                        </td>
                   </tr>
                   <s:if test="eventOP == 'add'">
                   <tr>
                       <td width="120px">初始密码：</td>
                       <td class="formFieldError">
                           <s:password id="passwordId" name="user.password"/>&nbsp;&nbsp;
                           <a href="javascript:void(0);" style="text-decoration: none;;color: red;">
                               <span id="defaultPasswordId" title="<s:property value="defaultPassword"/>">显示缺省密码</span>
                           </a>
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
                <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
                </s:iterator>
            </s:form>
        </div>
        <div title="所属权限" >
            <table id="auth_tt" fit="true"></table>
        </div>
        <div title="所属用户组">
            <table id = "group_tt" fit="true"></table>
        </div>
   </div>
   <div id="group_dd" title="用户组列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:380px;">
       <table id="group_all_tt" style="width:565px;height:300px;"></table>
   </div>
   <div id="auth_dd" title="权限列表" icon="icon-save" closed="true" style="padding:5px;width:600px;height:380px;">
     <table id="auth_all_tt" style="width:565px;height:300px;"></table>
   </div>
</body>
</html>