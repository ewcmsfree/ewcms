<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>调查投票列表</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function(){
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/vote/questionnaire" action="query"/>');
	
				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				
				ewcmsBOBJ.openDataGrid('#tt',{
	                columns:[[
                              {field:'id',title:'编号',width:60},
                              {field:'title',title:'调查主题',width:500},
                              {field:'questionnaireStatusDescription',title:'查看方式',width:100},
                              {field:'number',title:'投票人数',width:60},
                              {field:'verifiCode',title:'验证码',width:43,
                              	formatter:function(val,rec){
                              		return val ? '&nbsp;&nbsp;是' : '&nbsp;&nbsp;否';
                              	}
                              },
                              {field:'startTime',title:'开始时间',width:125},
                              {field:'endTime',title:'结束时间',width:125},
                              {field:'voteEnd',title:'结束投票',width:55,
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
	                  ]]
				});
	
				//创建和设置页面的操作对象 EwcmsOperate
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				//ewcmsOOBJ.setInputURL('<s:url namespace="/vote/questionnaire" action="input"/>');
				//ewcmsOOBJ.setDeleteURL('<s:url namespace="/vote/questionnaire" action="delete"/>');
				
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/site/channel" action="tree"/>',
					onClick:function(node){
						channelId = node.id;
						if (node.attributes.maxpermission < 1){
							return;
						}else {
							if (node.attributes.maxpermission <=1 && node.attributes.maxpermission > 2){
							}else if (node.attributes.maxpermission >=2){
							}
							$("#tt").datagrid('clearSelections');
							if (channelId == $('#tt2').tree('getRoot')) return;
							var url='<s:url namespace="/vote/questionnaire" action="query"/>';
							url = url + "?channelId=" + channelId;
							$("#tt").datagrid({
				            	pageNumber:1,
				                url:url
				            });
							var rootnode = $('#tt2').tree('getRoot');
							if (rootnode.id == node.id && node.attributes.maxpermission >=2){
								return;
							}				            
						}
					}
				});
			});
			function privOperateBack(){
				var rows = $('#tt').datagrid('getSelections');
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
				var rows = $('#tt').datagrid('getSelections');
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
			function getVoteRows(){
				var rows = $('#tt').datagrid('getSelections');
				return rows;
			}
		</script>
	</head>
	<body class="easyui-layout">
		<div region="west"  title='<img src="<s:url value="/ewcmssource/theme/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
			<ul  id="tt2"></ul>
		</div>
		<div region="center" style="padding:2px;" border="false">
			<table id="tt" fit="true"></table>
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
                            <td class="tdtitle">标题：</td>
                            <td class="tdinput">
                                <input type="text" id="title" name="title" class="inputtext"/>
                            </td>
                        </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch_Article();">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden id="urlAndContextName" name="urlAndContextName"/>
	</body>
</html>