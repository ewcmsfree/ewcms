<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>用户</title>
    <script type='text/javascript' src='<s:url value="/source/js/jquery-1.4.2.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'/>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
    <script type="text/javascript">
        $(function(){
          //基本变量初始
            setGlobaVariable({
                title:'用户',
                inputURL:'<s:url action="input"/>',
                queryURL:'<s:url action="query"/>',
                deleteURL:'<s:url action="delete"/>',
                editwidth:800,
                editheight:500
            });
            //数据表格定义                                                
            openDataGrid({
                title:'用户列表',
                idField:"username",
                sortName:"username",
                sortOrder:"asc",
                columns:[[
                    {field:'username',title:'用户名称',width:120,sortable:true},
                    {field:'userInfo.name',title:'姓名',width:120,formatter:function(val,row){
                        return row.userInfo.name;
                    }},
                    {field:'enabled',title:'停用',width:100,align:'center',formatter:function(val,row){
                        if(!val){
                            return "<span style='color:red;'>是</span>";
                         }else{
                            return '';
                         }
                    }},
                    {field:'accountStart',title:'授权开始时间',width:150},
                    {field:'accountEnd',title:'授权结束时间',width:150},
                    {field:'userInfo.mphone',title:'手机',width:150,formatter:function(val,row){
                        return row.userInfo.mphone;
                    }},
                    {field:'userInfo.phone',title:'电话',width:150,formatter:function(val,row){
                        return row.userInfo.phone;
                    }},
                    {field:'userInfo.email',title:'邮件',width:250,formatter:function(val,row){
                        return row.userInfo.email;
                    }},
                    {field:'createTime',title:'创建时间',width:150,sortable:true}
               ]],
               toolbar:[{
                    text:'新增',
                    iconCls:'icon-add',
                    handler:function(){
                        addOperateBack();
                    }
                },{
                    text:'修改',
                    iconCls:'icon-edit',
                    handler:function(){
                        updOperateBack({
                            callBackId:function(data){
                                return data.username;
                            }
                        })                        
                    }
                },{
                    text:'删除',
                    iconCls:'icon-remove',
                    handler:function(){
                        delOperateBack({
                            callBackId:function(data){
                                return data.username;
                            }
                        })              
                    }
                },'-',{
                    text:'修改密码',
                    //iconCls:'icon-search',
                    handler:function(){
                        var rows = $('#tt').datagrid('getSelections');
                        if(rows.length == 0){
                            $.messager.alert('提示','请选择修改密码的用户','info');
                            return;
                        }
                        var url = '<s:url action="password"/>?'
                        for(var i=0;i<rows.length;++i){
                            url += 'selections=' + rows[i].username +'&';
                        }       
                        $('#editifr').attr('src',url);
                        openWindow('#edit-window',{width:500,height:300,title:'修改密码'});
                    }
                },'-',{
                    text:'启用',
                    //iconCls:'icon-search',
                    handler:function(){
                        active(true);
                    }
                },{
                    text:'停用',
                    //iconCls:'icon-search',
                    handler:function(){
                        active(false);
                    }
                },'-',{
                    text:'查询',
                    iconCls:'icon-search',
                    handler:function(){
                        queryOperateBack()
                    }
                },{
                    text:'缺省查询',
                    iconCls:'icon-back',
                    handler:function(){
                        initOperateQueryBack()
                    }
                }]
            });
            $('#edit-window').window({
                onClose:function(){
                    $(this).window('options').width = 800;
                    $(this).window('options').height = 500;
                    $('#editifr').attr('src',"about:blank");
                }
            });
       });
        
       function closeWindow(){
           $('#edit-window').window('close');
       }
       
       function active(enabled){
           var rows = $('#tt').datagrid('getSelections');
           var operateName = '';
           var url = '';
           if(enabled){
               operateName = '启用';
               url = '<s:url action="active"/>';
           }else{
               operateName = '停用';
               url = '<s:url action="inactive"/>';
           }
           if(rows.length == 0){
               $.messager.alert('提示','请选择'+operateName+'的用户','info');
               return;
           }
           $.messager.confirm("提示",'确定要'+operateName+'所选用户吗?',function(r){
               if (r){
                   var selections = '';
                   for(var i=0;i<rows.length;++i){
                       selections += 'selections=' + rows[i].username +'&';
                   }
                   $.getJSON(url,selections,function(data){
                      if(data.success){
                          queryReload(true);
                      } else{
                           $.messager.alert('错误',data.message,'error');
                      }
                   });
               }
           });
         
       }
       
       function editSubmit(iframeid){
           if(typeof(iframeid) == 'undefined')iframeid = 'editifr';
           window.frames[iframeid].pageSubmit();
       }  
    </script>
</head>
 <body class="easyui-layout">
     <div region="center" border="false" style="background:#fff;">
         <table id="tt" align="center" fit="true"></table>
     </div>
     <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="用户" style="display:none;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false">
               <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
            </div>
            <div region="south" border="false" style="padding-right:20px;text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="editSubmit()">保存</a>
                <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">取消</a>
            </div>
        </div>
    </div>
    <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
        <div class="easyui-layout" fit="true"  >
            <div region="center" border="false"  style="padding: 10px;">
                <form id="queryform">
                    <table class="formtable">
                        <tr>
                             <td class="tdtitle">用户名：</td>
                             <td class="tdinput">
                                <input type="text" name="username" class="inputtext"/>
                             </td>
                        </tr>
                        <!-- TODO Query not prover 级联 
                        <tr>
                            <td class="tdtitle">名称：</td>
                            <td class="tdinput">
                                <input type="text" name="name" class="inputtext"/>
                            </td>
                        </tr>
                         -->
                    </table>
                </form>
            </div>
            <div region="south" border="false" style="padding-right:20px;text-align:right;height:28px;line-height:28px;background-color:#f6f6f6">
                <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
            </div>
        </div>
    </div>          
</body>
</html>