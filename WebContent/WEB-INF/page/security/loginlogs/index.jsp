<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>登录日志</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/security/loginlogs/index.js"/>'></script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
		<script type="text/javascript">
			var logsIndex = new LogsIndex({
				queryUrl:'<s:url namespace="/security/loginlogs" action="query"/>'
			});
			$(function(){
				logsIndex.init({
		        	datagridId:'#tt'
				});
			});
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;登录日志" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
            </div>
        </div>	
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">登录名：</td>
                                <td class="tdinput" colspan="3">
                                    <input type="text" id="userName" name="userName" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                            	<td class="tdtitle">登录时间：</td>
                            	<td class="tdinput" colspan="3">
                            		<ewcms:datepicker id="startDate" name="startDate" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;至&nbsp;
                            		<ewcms:datepicker id="endDate" name="endDate" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
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
	</body>
</html>