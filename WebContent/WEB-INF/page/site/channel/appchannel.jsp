<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>查看被其他栏目引用</title>
		<s:include value="../../taglibs.jsp"/>
		<script>
			$(function(){
				
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				
				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');

				ewcmsBOBJ.addToolItem('移除','icon-remove','deleteOperate');
				
				ewcmsBOBJ.setQueryURL('<s:url action="appQuery" namespace="/site/channel"/>?channelId=<s:property value="channelId"/>');
				ewcmsBOBJ.openDataGrid('#tt',{
					columns:[[
						{field:'id',title:'栏目编号',width:60},
				        {field:'name',title:'栏目名称',width:200},
				        {field:'absUrl',title:'相对地址',width:300}
				    ]]
				});

				//创建和设置页面的操作对象 EwcmsOperate
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			});
			
			function deleteOperate(){
				var parentRefer = false;
				var rows = $('#tt').datagrid('getSelections');
			    if(rows.length == 0){
			        $.messager.alert('提示','请选择移除记录','info');
			        return ;
			    }
			    if (rows.length == $('#tt').datagrid('getData').total){
			    	parentRefer = true;
			    }
			    var ids = '';
			    for(var i=0;i<rows.length;++i){
			        ids =ids + 'selections=' + rows[i].id +'&';
			    }
			    $.messager.confirm("提示","确定要移除所选记录吗?",function(r){
			        if (r){
			            $.post('<s:url action="appDel" namespace="/site/channel"/>?channelId=<s:property value="channelId"/>',ids,function(data){          	
			            	$.messager.alert('成功','删除成功','info');
			            	$('#tt').datagrid('clearSelections');
			                $('#tt').datagrid('reload');
			                if (parentRefer){
			                	parent.$('#span-connect').html('已断开');
			                	parent.$('#span-viewconnect').hide();
			                }
			            });
			        }
			    });	
			}
		</script>
	</head>
	<body class="easyui-layout">
		<s:hidden id="channelId" name="channelId"/>
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;被其他栏目引用" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#edit-window').window('close');">取消</a>
                </div>
            </div>
        </div>	
        
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">栏目编号：</td>
                                <td class="tdinput">
                                    <input type="text" id="id" name="id" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">栏目名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
                            </tr>    
                            <tr>
                            	<td class="tdtitle">栏目名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="absUrl" name="absUrl" class="inputtext"/>
                                </td>
                            </tr>                        
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                </div>
            </div>
        </div>
	</body>
</html>