<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>项目基本数据</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/particular/pb/index.js"/>'></script>
		<script type="text/javascript">
			var pbIndex = new PbIndex({
				queryUrl:'<s:url namespace="/particular/pb" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>',
				inputUrl:'<s:url namespace="/particular/pb" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>',
				deleteUrl:'<s:url namespace="/particular/pb" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>',
				importUrl:'<s:url namespace="/particular/pb" action="import"><s:param name="channelId" value="channelId"></s:param></s:url>',
				generatorUrl:'<s:url namespace="/particular/pb" action="export"/>'
			});
			$(function(){
				<s:include value="../../alertMessage.jsp"/>
				pbIndex.init({
		        	datagridId:'#tt'
				});
			});
		</script>		
	</head>
	<body class="easyui-layout">
		<s:hidden id="channelId" name="channelId"/>
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;项目基本数据" style="display:none;">
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
                                <td class="tdtitle">项目编号：</td>
                                <td class="tdinput">
                                    <input type="text" id="code" name="code" class="inputtext" maxLength="23" size="23"/>
                                </td>
                                <td class="tdtitle">项目名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
 	                            <td class="tdtitle">建设性质：</td>
	                            <td class="tdinput">
	                                <s:select list="@com.ewcms.content.particular.model.ProjectBasic$Nature@values()" listValue="description" name="buildNature" id="buildNature" headerKey="" headerValue="------请选择------"/>
	                            </td>
                        	</tr>
							<tr>
	                            <td class="tdtitle">行业编码：</td>
	                            <td class="tdinput">
	                            	<input id="cc_industryCode" name="industryCode" style="width:150px;"></input>
	                            </td>
	                            <td class="tdtitle">行政区划代码：</td>
	                            <td class="tdinput">
	                            	<input id="cc_zoningCode" name="zoningCode" style="width: 150px;"></input>
	                            </td>
	                            <td class="tdtitle">审批备案机关编号：</td>
	                            <td class="tdinput">
	                            	<input id="cc_approvalRecordCode" name="approvalRecord" style="width: 150px;"></input>
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
        <div id="import-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;项目基本数据导入" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="importifr"  name="importifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
            </div>
        </div>	
	</body>
</html>