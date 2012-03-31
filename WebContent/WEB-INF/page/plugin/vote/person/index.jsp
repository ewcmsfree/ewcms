<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>调查投票明细</title>	
		<s:include value="../../../taglibs.jsp"/>
		<ewcms:datepickerhead></ewcms:datepickerhead>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/vote/person" action="query"/>?questionnaireId=' + $('#questionnaireId').val() + '');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				
				ewcmsBOBJ.setWinWidth(440);
				ewcmsBOBJ.setWinHeight(120);
				
				ewcmsBOBJ.openDataGrid('#tt',{
                    columns:[[
                              {field:'id',title:'编号',width:60},
                              {field:'ip',title:'IP地址',width:120},
                              {field:'recordTime',title:'投票时间',width:145},
                              {field:'item',title:'填写内容',width:60,
                              	formatter:function(val,rec){
                              		return '<a href="#" onclick="showRecord(' + rec.id + ');">内容</a>';
                              	}
                              }
                      ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			});
			function showRecord(id){
				var url =  '<s:url namespace="/vote/record" action="index"/>?personId=' + id + '&questionnaireId=' + $('#questionnaireId').val();
				$('#editifr_record').attr('src',url);
				ewcmsBOBJ.openWindow('#record-window',{width:400,height:180,title:'内容'});
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
                            <td class="tdtitle">IP：</td>
                            <td class="tdinput">
                                <input type="text" id="ip" name="ip" class="inputtext"/>
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
        <s:hidden id="questionnaireId" name="questionnaireId"/>
	</body>
</html>