<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>从业人员基本信息</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/particular/mb/index.js"/>'></script>
		<script type="text/javascript">
			var queryUrl='<s:url namespace="/particular/mb" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>';
			var inputUrl='<s:url namespace="/particular/mb" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>';
			var deleteUrl='<s:url namespace="/particular/mb" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>';
			var pubUrl='<s:url namespace="/particular/mb" action="pub"><s:param name="channelId" value="channelId"></s:param></s:url>';
			var unPubUrl='<s:url namespace="/particular/mb" action="unPub"><s:param name="channelId" value="channelId"></s:param></s:url>';
			var datagridId='#tt';
			var queryWinID="#query-window";
			$(function(){
				<s:include value="../../alertMessage.jsp"/>
			});
		</script>		
	</head>
	<body class="easyui-layout">
		<s:hidden id="channelId" name="channelId"/>
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;从业人员基本信息" style="display:none;">
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
                                <td class="tdtitle">姓名：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">证件号码：</td>
                                <td class="tdinput">
                                    <input type="text" id="cardCode" name="cardCode" class="inputtext"/>
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