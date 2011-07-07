<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>问卷调查</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
			$(function(){
				//基本变量初始
				setGlobaVariable({
					tableid:'#tt_main',
					inputURL:'<s:url namespace="/vote/questionnaire" action="input"/>?channelId=' + $('#channelId').val() + '',
					queryURL:'<s:url namespace="/vote/questionnaire" action="query"/>?channelId=' + $('#channelId').val() + '',
					deleteURL:'<s:url namespace="/vote/questionnaire" action="delete"/>?channelId=' + $('#channelId').val() + '',
					editwidth:650,
					editheight:260,
					querywidth:500,
					queryheight:100
				});
				//数据表格定义 						
                openDataGrid({
                	singleSelect:true,
                    columns:[[
                                {field:'id',title:'编号',width:60},
                                {field:'title',title:'问卷名称',width:500},
                                {field:'questionnaireStatusDescription',title:'查看方式',width:100},
                                {field:'number',title:'投票人数',width:60},
                                {field:'verifiCode',title:'验证码',width:43,
                                	formatter:function(val,rec){
                                		return val ? '&nbsp;&nbsp;是' : '&nbsp;&nbsp;否';
                                	}
                                },
                                {field:'startTime',title:'开始时间',width:125},
                                {field:'endTime',title:'结束时间',width:125},
                                {field:'voteFlag',title:'结束投票',width:55,
                                	formatter:function(val,rec){
                                		var flag = '&nbsp;&nbsp;&nbsp;否';
                                		var nowDate = new Date();
                                		if (val){
                                    		flag = '&nbsp;&nbsp;&nbsp;是';
                                		}else if (rec.endTime < nowDate.toLocaleString()){
                                			flag = '&nbsp;&nbsp;&nbsp;是';
                                		}
                                		return flag;
                                	}
                                }
                        ]],
        				toolbar:[
     							{text:'新增',iconCls:'icon-add',handler:addOperate},'-',
     							{text:'修改',iconCls:'icon-edit',handler:updOperate},'-',
     							{text:'删除',iconCls:'icon-remove', handler:delOperate},'-',
     							{text:'预览',iconCls:'icon-voteprivew', handler:privOperateBack},'-',
     							{text:'结果',iconCls:'icon-voteresult', handler:resultOperateBack},'-',
     							{text:'投票人员',iconCls:'icon-votedetail', handler:detailOperate},'-',
     							{text:'查询',iconCls:'icon-search', handler:queryOperate},'-',
     							{text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
     						]                    
				});
				$('#tt_main').datagrid({
					onSelect:function(rowIndex,rowData){
						var url='<s:url namespace="/vote/subject" action="index"/>';
						url = url + '?questionnaireId=' + rowData.id + '';
						parent.$('#subjectifr').attr('src',url);
					},
					onUnselect:function(rowIndex,rowData){
						$('#tt_main').datagrid('unselectRow', rowIndex)
						parent.$('#subjectifr').attr('src','');
					}
				});
			});
			function privOperateBack(){
				var rows = $('#tt_main').datagrid('getSelections');
				if(rows.length == 0){
	            	$.messager.alert('提示','请选择预览记录','info');
	                return;
	            }
	            if (rows.length > 1){
					$.messager.alert('提示','只能选择一个预览','info');
					return;
		        }
				window.open('<s:url value="/view.vote"/>?id=' + rows[0].id + '','popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=' + (window.screen.width - 1280)/2 + ',top=' + (window.screen.height - 700)/2 + '');
			}
			function resultOperateBack(){
				var rows = $('#tt_main').datagrid('getSelections');
				if(rows.length == 0){
	            	$.messager.alert('提示','请选择结果查看记录','info');
	                return;
	            }
	            if (rows.length > 1){
					$.messager.alert('提示','只能选择一个结果查看','info');
					return;
		        }
				window.open('<s:url namespace="/vote/questionnaire" action="resultVote"/>?id=' + rows[0].id + '','popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=' + (window.screen.width - 1280)/2 + ',top=' + (window.screen.height - 700)/2 + '');
			}
			function delOperate(){
				parent.$('#subjectifr').attr('src','');
				delOperateBack();
				return false;
			}
			function updOperate(){
				parent.$('#subjectifr').attr('src','');
				updOperateBack();
				return false;
			}
			function addOperate(){
				parent.$('#subjectifr').attr('src','');
				addOperateBack();
				return false;
			}
			function detailOperate(){
				var rows = $('#tt_main').datagrid('getSelections');
				if(rows.length == 0){
	            	$.messager.alert('提示','请选择结果查看记录','info');
	                return;
	            }
	            if (rows.length > 1){
					$.messager.alert('提示','只能选择一个结果查看','info');
					return;
		        }
				var url =  '<s:url namespace="/vote/person" action="index"/>?questionnaireId=' + rows[0].id + '';
				$('#editifr_person').attr('src',url);
				openWindow('#person-window',{width:500,height:265,title:'人员'});
			}
			function queryOperate(){
				$('#tt_main').datagrid('clearSelections');
				parent.$('#subjectifr').attr('src','');
				queryOperateBack();
				return false;
			}
		</script>
	</head>
	<body class="easyui-layout">
	    <div region="center" title="问卷调查" style="padding:2px;" split="true">  
			<table id="tt_main" fit="true" split="true"></table>
	    </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;问卷调查" style="display:none;">
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
                            <td class="tdtitle">编号：</td>
                            <td class="tdinput">
                                <input type="text" id="id" name="id" class="inputtext"/>
                            </td>
                            <td class="tdtitle">问卷名称：</td>
                            <td class="tdinput">
                                <input type="text" id="title" name="title" class="inputtext"/>
                            </td>
                        </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
		<div id="person-window" class="easyui-window" icon="icon-votedetail" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_person"  name="editifr_person" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
            </div>
        </div>
        <s:hidden id="channelId" name="channelId"/>
	</body>
</html>