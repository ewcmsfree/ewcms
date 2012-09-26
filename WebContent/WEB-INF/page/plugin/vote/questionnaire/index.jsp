<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>问卷调查</title>	
		<s:include value="../../../taglibs.jsp"/>
		<ewcms:datepickerhead></ewcms:datepickerhead>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/vote/questionnaire" action="query"/>?channelId=' + $('#channelId').val() + '');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.delToolItem('查询');
				ewcmsBOBJ.delToolItem('缺省查询');

				ewcmsBOBJ.addToolItem('新增','icon-add', addOperate);
				ewcmsBOBJ.addToolItem('修改','icon-edit',updOperate);
				ewcmsBOBJ.addToolItem('删除','icon-remove',delOperate);
				ewcmsBOBJ.addToolItem('预览','icon-voteprivew',privOperateBack);
				ewcmsBOBJ.addToolItem('结果','icon-voteresult',resultOperateBack);
				ewcmsBOBJ.addToolItem('投票人员','icon-votedetail',detailOperate);
				ewcmsBOBJ.addToolItem('查询','icon-search',queryOperate);
				ewcmsBOBJ.addToolItem('缺省查询','icon-back',defQueryCallBack);
	
				ewcmsBOBJ.openDataGrid('#tt_main',{
					singleSelect:true,
                    columns:[[
                              {field:'id',title:'编号',width:60},
                              {field:'title',title:'问卷名称',width:500},
                              {field:'statusDescription',title:'查看方式',width:100},
                              {field:'number',title:'投票人数',width:60},
                              {field:'verifiCode',title:'验证码',width:43,
                              	formatter:function(val,rec){
                              		return val ? '&nbsp;&nbsp;是' : '&nbsp;&nbsp;否';
                              	}
                              },
                              {field:'startTime',title:'开始时间',width:145},
                              {field:'endTime',title:'结束时间',width:145},
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
				ewcmsOOBJ.setDatagridID('#tt_main');
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/vote/questionnaire" action="input"/>?channelId=' + $('#channelId').val() + '');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/vote/questionnaire" action="delete"/>?channelId=' + $('#channelId').val() + '');

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
				delCallBack();
			}
			function updOperate(){
				parent.$('#subjectifr').attr('src','');
				updCallBack();
			}
			function addOperate(){
				parent.$('#subjectifr').attr('src','');
				addCallBack();
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
				ewcmsBOBJ.openWindow('#person-window',{width:500,height:265,title:'人员'});
			}
			function queryOperate(){
				$('#tt_main').datagrid('clearSelections');
				parent.$('#subjectifr').attr('src','');
				queryCallBack();
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
                        <tr>
                            <td class="tdtitle">开始时间：</td>
                            <td class="tdinput">
                                <ewcms:datepicker id="startTime" name="startTime" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td class="tdtitle">结束时间：</td>
                            <td class="tdinput">
                                <ewcms:datepicker id="endTime" name="endTime" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                        <tr>
                        	<td class="tdtitle">查看方式：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.plugin.vote.model.Questionnaire$Status@values()" listValue="description" name="status" id="status" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                            <td class="tdtitle">结束投票：</td>
                            <td class="tdinput">
                        		<s:select list="#{true:'是',false:'否'}" name="voteEnd" id="voteEnd" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                        </tr>
                        <tr>
                        	<td class="tdtitle">投票人数：</td>
                            <td class="tdinput">
                            	<table cellpadding="0" border="0">
                            		<tr>
                            			<td style="border:0px;">
                            				<input type="text" id="numberBegin" name="numberBegin" class="inputtext" size="7"/>
                            			</td>
			                            <td style="border:0px;">到</td>
			                            <td style="border:0px;">
			                            	<input type="text" id="numberEnd" name="numberEnd" class="inputtext" size="7"/>
			                            </td>
			                           </tr>
                            	</table>
                            </td>
                         	<td class="tdtitle">验证码：</td>
                        	<td class="tdinput">
                        		<s:select list="#{true:'是',false:'否'}" name="verifiCode" id="verifiCode" headerKey="-1" headerValue="------请选择------"></s:select>
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