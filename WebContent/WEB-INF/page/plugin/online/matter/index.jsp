<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>事项基本信息</title>	
		<s:include value="../../../taglibs.jsp"/>
		<script>
			var datagridId="#tt";
			$(function(){
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/plugin/online/matter" action="query"/>');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.delToolItem('查询');
				ewcmsBOBJ.delToolItem('缺省查询');
			    
				ewcmsBOBJ.addToolItem('新增','icon-add', addOperate, 'addToolbar');
				ewcmsBOBJ.addToolItem('修改','icon-edit', editOperate, 'editToolbar');
				ewcmsBOBJ.addToolItem('删除', 'icon-remove', removeOperate, 'removeToolbar');
				ewcmsBOBJ.addToolItem('上移','icon-up',upOperate,'upOperate');
              	ewcmsBOBJ.addToolItem('下移','icon-down',downOperate,'downOperate');
				ewcmsBOBJ.addToolItem('查询', 'icon-search', queryOperate,'queryToolbar');
				ewcmsBOBJ.addToolItem('缺省查询','icon-back', defQueryOperate, 'defQueryToolbar');
				
				ewcmsBOBJ.openDataGrid('#tt',{
					singleSelect:true,
					columns:[[
								{field:'id',title:'序号',width:40,sortable:true},
					            {field:'name',title:'事项名称',width:200},
					            {field:'handleBasis',title:'办理依据',width:300},
					            {field:'serviceObject',title:'服务对象',width:200},
					            {field:'acceptedCondition',title:'办理条件',width:200},
					            {field:'petitionMaterial',title:'申办材料',width:200},
					            {field:'handleCourse',title:'办理程序',width:200},
					            {field:'timeLimit',title:'办理时限',width:200},
					            {field:'fees',title:'收费标准',width:200},
					            {field:'department',title:'办理机构',width:200},
					            {field:'consultingTel',title:'办理时间',width:200},
					            {field:'handleSite',title:'办理地址',width:300},
					            {field:'contactTel',title:'联系方式',width:200},
					            {field:'email',title:'监督投诉',width:200}
					            //{field:'acceptedWay',title:'受理方式',width:80},
					            //{field:'handleWay',title:'审批、服务数量及方式',width:130},
					            //{field:'deadline',title:'承诺期限',width:200},
					            //{field:'feesBasis',title:'收费依据',width:200},
					            //{field:'contactName',title:'联系人',width:200},
					            //{field:'infoUrl',title:'信息告知',width:200},
					            //{field:'onlinePayUrl',title:'网上缴费',width:200}
							]]
				});
				//创建和设置页面的操作对象 EwcmsOperate
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/plugin/online/matter" action="input"/>');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/plugin/online/matter" action="delete"/>');				
			});

			function addOperate(){
				openWindow1({title:'新增事项基本信息', url:'<s:url namespace="/plugin/online/matter" action="input"/>', width:900, height:500});
			}

			function editOperate(){
				var rows = $(datagridId).datagrid('getSelections');
			    if(rows.length == 0){
			        $.messager.alert('提示','请选择修改记录','info');
			        return;
			    }
			    var url = '<s:url namespace="/plugin/online/matter" action="input"/>';            
			    var index = url.indexOf("?");
			    if (index == -1){
			        url = url + '?';
			    }else{
			        url = url + "&";
			    }
			    callBackId = function(row){
			        return row.id;
			    }
			    if(typeof(options) == 'undefined')options = {};	    
			    if(options.callBackId){
			        callBackId = options.callBackId;
			    }
			    for(var i=0;i<rows.length;++i){
			        url += 'selections=' + callBackId(rows[i]) +'&';
			    }
			    ewcmsBOBJ.openWindow1({title:'修改事项基本信息', url:url, width:900, height:500})
			}
			
			function removeOperate(){
				ewcmsOOBJ.delOperateBack();
			}

			function queryOperate(){
				ewcmsBOBJ.openWindow("#query-window");
			}

			function defQueryOperate(){
				ewcmsOOBJ.initOperateQueryBack();
			}
			
			function upOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择移动记录","info");
					return;
				}
				if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录进行移动","info");
					return;
				}
				$.post('<s:url namespace="/plugin/online/matter" action="upMatter"/>', {'matterId':rows[0].id}, function(data){
					if (data == 'false'){
						$.messager.alert("提示","上移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
				});
			}

			function downOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择移动记录","info");
					return;
				}
				if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录进行移动","info");
					return;
				}
				$.post('<s:url namespace="/plugin/online/matter" action="downMatter"/>', {'matterId':rows[0].id}, function(data){
					if (data == "false"){
						$.messager.alert("提示","下移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
				});
			}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
			<table id="tt" fit="true"></table>
		</div>
		<div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;事项信息" style="display:none;">
			<div class="easyui-layout" fit="true">
		    	<div region="center" border="false">
		        	<iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" width="100%" height="100%" scrolling="auto"></iframe>
		    	</div>
				<div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
		    		<a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
		  		</div>
			</div>
		</div>	
		<div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
			<div class="easyui-layout" fit="true">
		    	<div region="center" border="false">
			    	<form id="queryform">
			        	<table class="formtable">
			            	<tr>
			                	<td class="tdtitle">编号：</td>
			               		<td class="tdinput">
			                		<input type="text" id="id" name="id" class="inputtext"/>
			                	</td>
			           		</tr>
			            	<tr>
			                    <td class="tdtitle">名称：</td>
			                    <td class="tdinput">
			                    	<input type="text" id="title" name="name" class="inputtext"/>
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