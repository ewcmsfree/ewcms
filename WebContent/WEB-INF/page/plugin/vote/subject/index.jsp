<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>问卷调查主题</title>	
		<s:include value="../../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/vote/subject" action="query"/>?questionnaireId=' + $('#questionnaireId').val() + '');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.delToolItem('查询');
				ewcmsBOBJ.delToolItem('缺省查询');
				
				ewcmsBOBJ.addToolItem('新增','icon-add', addOperate);
				ewcmsBOBJ.addToolItem('修改','icon-edit',updOperate);
				ewcmsBOBJ.addToolItem('删除','icon-remove',delCallBack);
				ewcmsBOBJ.addToolItem('上移','icon-up',upOperate);
				ewcmsBOBJ.addToolItem('下移','icon-down',downOperate);
				ewcmsBOBJ.addToolItem('查询','icon-search',queryCallBack);
				ewcmsBOBJ.addToolItem('缺省查询','icon-back',defQueryCallBack);
				
				ewcmsBOBJ.setWinWidth(500);
				ewcmsBOBJ.setWinHeight(160);
				
				ewcmsBOBJ.openDataGrid('#tt_subject',{
					singleSelect:true,
                    columns:[[
                              {field:'id',title:'编号',width:60},
                              {field:'title',title:'主题名称',width:500,
                              	formatter:function(val,rec){
                              		return '<a href="javascript:void(0);" onclick="showSubjectItem(' + rec.id + ',\'' + val +  '\',\'' + rec.statusDescription + '\');">' + val + '</a>';
                              	}
                              },
                              {field:'subjectStatusDescription',title:'主题选择方式',width:100}
                      ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setDatagridID('#tt_subject');
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/vote/subject" action="input"/>?questionnaireId=' + $('#questionnaireId').val() + '');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/vote/subject" action="delete"/>?questionnaireId=' + $('#questionnaireId').val() + '');
			});
			function showSubjectItem(subjectId, subjectTitle, optionDescription){
				var subjectTitle = '[<span style="color:red;">主题编号：</span>' + subjectId + '] [<span style="color:red;">主题名称：</span>' + subjectTitle + '] - 问卷调查主题列表';
				var url = "";
				if (optionDescription == '录入'){
					$('#save_span').attr("style","");
					url = '<s:url namespace="/vote/subjectitem" action="inputopt"/>?subjectId=' + subjectId + '&questionnaireId=' + $('#questionnaireId').val() + '';
				}else{
					$('#save_span').attr("style","display:none");
					url = '<s:url namespace="/vote/subjectitem" action="index"/>?subjectId=' + subjectId + '&questionnaireId=' + $('#questionnaireId').val() + '';
				}
				$('#editifr').attr('src',url);
				ewcmsBOBJ.openWindow('#edit-window',{width:858,height:320,title:subjectTitle});
			}
			function upOperate(){
				var rows = $('#tt_subject').datagrid('getSelections');
				if(rows.length == 0){
	            	$.messager.alert('提示','请选择问卷调查主题记录','info');
	                return;
	            }
	            if (rows.length > 1){
					$.messager.alert('提示','只能选择一个问卷调查主题记录','info');
					return;
		        }
		        var url = '<s:url namespace="/vote/subject" action="up"/>';
		        var parameter = 'questionnaireId=' + $('#questionnaireId').val() + '&selections=' + rows[0].id + '';
	            $.post(url,parameter,function(data){
					if (data == "false"){
						$.messager.alert("提示","上移失败","info");
						return;
					}else if (data == "false-system"){
						$.messager.alert("提示","系统错误","info");
						return;
					}
					$("#tt_subject").datagrid('reload');
	            });
	            return false;
			}
			function downOperate(){
				var rows = $('#tt_subject').datagrid('getSelections');
				if(rows.length == 0){
	            	$.messager.alert('提示','请选择问卷调查主题记录','info');
	                return;
	            }
	            if (rows.length > 1){
					$.messager.alert('提示','只能选择一个问卷调查主题记录','info');
					return;
		        }
		        var url = '<s:url namespace="/vote/subject" action="down"/>';
		        var parameter = 'questionnaireId=' + $('#questionnaireId').val() + '&selections=' + rows[0].id + '';
	            $.post(url,parameter,function(data){
					if (data == "false"){
						$.messager.alert("提示","下移失败","info");
						return;
					}else if (data == "false-system"){
						$.messager.alert("提示","系统错误","info");
						return;
					}
					$("#tt_subject").datagrid('reload');
	            });
	            return false;
			}
			function updOperate(){
				$('#save_span').attr("style","");
				updCallBack();
			}
			function addOperate(){
				$('#save_span').attr("style","");
				addCallBack();
			}
			function queryOperate(){
				$('#tt_subject').datagrid('clearSelections');
				queryCallBack();
			}
		</script>
	</head>
	<body class="easyui-layout">
		<div id="subjectdiv" region="center" style="padding:2px;" title="[<span style='color:red;'>问卷编号</span>: <s:property value='questionnaireId'/>] [<span style='color:red;'>问卷名称</span>: <s:property value='questionnaireTitle'/>] - 问卷调查主题 " split="true">
			<table id="tt_subject" fit="true" split="true"></table>
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;问卷调查主题" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                	<span id="save_span"><a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#editifr').attr('src','');$('#edit-window').window('close');">关闭</a>
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
                            <td class="tdtitle">主题名称：</td>
                            <td class="tdinput">
                                <input type="text" id="title" name="title" class="inputtext"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtitle">选择方式：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.plugin.vote.model.Subject$Status@values()" listValue="description" name="status" id="status" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                            <td class="tdtitle"></td>
                            <td class="tdinput"></td>
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
        <s:hidden id="questionnaireId" name="questionnaireId"/>
        <s:hidden id="questionnaireTitle"/>
	</body>
</html>