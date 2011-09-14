<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<html>
	<head>
		<title>备忘录列表</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/document/notes" action="query"/>');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.delToolItem('查询');
				ewcmsBOBJ.delToolItem('缺省查询');

				ewcmsBOBJ.addToolItem('删除','icon-remove',deleteOperate);
				ewcmsBOBJ.addToolItem('查询','icon-search',queryCallBack);
				ewcmsBOBJ.addToolItem('缺省查询','icon-back',defQueryCallBack);

				ewcmsBOBJ.setWinWidth(500);
				ewcmsBOBJ.setWinHeight(180);

				ewcmsBOBJ.openDataGrid('#tt',{
                    columns:[[
                              {field:'id',title:'编号',width:60},
                              {field:'title',title:'标题',width:200},
                              {field:'noteDate',title:'日期',width:125},
                              {field:'warn',title:'提醒',width:33,
                                formatter:function(val,rec){
                            	  	var flag = '&nbsp;否';
                            		if (val){
                                		flag = '&nbsp;是';
                            		}
                            		return flag;
                            	}
                              },
                              {field:'warnTime',title:'提醒时间',width:60},
                              {field:'frequencyDescription',title:'提醒频率',width:80},
                              {field:'beforeDescription',title:'提前时间',width:80},
                              {field:'fireTime',title:'触发时间',width:125},
                              {field:'missRemind',title:'错过提醒',width:55,
                                  formatter:function(val,rec){
                                    var flag = '&nbsp;&nbsp;&nbsp;否';
                          			if (val){
                              			flag = '&nbsp;&nbsp;&nbsp;是';
    	                      		}
        	                  		return flag;
            	              	}
                              }
                      ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			});
			function deleteOperate(){
			    var rows = $('#tt').datagrid('getSelections');
			    if(rows.length == 0){
			        $.messager.alert('提示','请选择删除记录','info');
			        return ;
			    }
			    var ids = '';
			    for(var i=0;i<rows.length;++i){
			        ids =ids + 'selections=' + rows[i].id +'&';
			    }
			    $.messager.confirm("提示","确定要删除所选记录吗?",function(r){
			        if (r){
			            $.post('<s:url namespace="/document/notes" action="delete"/>',ids,function(data){          	
			            	$.messager.alert('成功','删除成功','info');
			                for (var i=0;i<rows.length;++i){ 
			                	$(window.parent.document).find('#div_notes_memo_' + rows[i].id).remove();             	
			                }
			            	$(datagridID).datagrid('clearSelections');
			                $(datagridID).datagrid('reload');
			            });
			        }
			    });
				
			}
			</script>
	</head>
	<body class="easyui-layout">
	    <div region="center" style="padding:2px;" split="true">  
			<table id="tt" fit="true" split="true"></table>
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
                        <tr>
                            <td class="tdtitle">日期：</td>
                            <td class="tdinput" colspan="3">
                                <ewcms:datepicker id="noteDateStart" name="noteDateStart" option="inputsimple" format="yyyy-MM-dd HH:mm:00"/>
                                &nbsp;至&nbsp;
                                <ewcms:datepicker id="noteDateEnd" name="noteDateEnd" option="inputsimple" format="yyyy-MM-dd HH:mm:00"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtitle">提醒频率：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.content.notes.model.FrequencyStatus@values()" listValue="description" name="frequencyStatus" id="frequencyStatus" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                            <td class="tdtitle">提前时间：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.content.notes.model.BeforeStatus@values()" listValue="description" name="beforeStatus" id="beforeStatus" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                        </tr>
                        <tr>
                        	<td class="tdtitle">是否提醒：</td>
                        	<td class="tdinput">
                        		<s:select list="#{true:'是',false:'否'}" name="warn" id="warn" headerKey="-1" headerValue="------请选择------"></s:select>
                        	</td>
                        	<td class="tdtitle">错过提醒：</td>
                        	<td class="tdinput">
                        		<s:select list="#{true:'是',false:'否'}" name="missRemind" id="missRemind" headerKey="-1" headerValue="------请选择------"></s:select>
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
		<div id="record-window" class="easyui-window" icon="icon-votedetail" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_record"  name="editifr_record" frameborder="0" width="100%" height="100%" scrolling="auto" style="width:100%;height:100%;"></iframe>
                </div>
            </div>
        </div>
	</body>
</html>