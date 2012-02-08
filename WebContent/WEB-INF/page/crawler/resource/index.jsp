<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>网络采集器</title>	
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/cupertino/easyui.css"/>' rel="stylesheet" title="cupertino"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/crawler/index.js"/>'></script>
		<script type="text/javascript">
			queryURL = '<s:url namespace="/crawler" action="query"/>?type=RESOURCE';
			inputURL = '<s:url namespace="/crawler/resource" action="input"/>';
			deleteURL = '<s:url namespace="/crawler" action="delete"/>';
			delGatherDataURL = '<s:url namespace="/crawler" action="delGatherData"/>';
			matchIndexURL = '<s:url namespace="/crawler/match" action="index"/>';
			filterIndexURL = '<s:url namespace="/crawler/filter" action="index"/>';
			domainIndexURL = '<s:url namespace="/crawler/domain" action="index"/>';
			crawlRunURL = '<s:url namespace="/crawler" action="crawlRun"/>';
			schedulingURL = '<s:url namespace="/scheduling/jobcrawler" action="index"/>';
			helpURL = '<s:url namespace="/crawler/help" action="index"/>';
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;资源采集属性" style="display:none;">
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
                            </tr>
                            <tr>
                                <td class="tdtitle">名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
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
		<div id="block-window" class="easyui-window" closed="true" title="&nbsp;区块" style="display:none;">
	      <div class="easyui-layout" fit="true">
	        <div region="center" border="false">
	          <iframe id="editifr_block"  name="editifr_block" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
	        </div>
	        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
	          <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0);" onclick="javascript:$('#editifr_block').attr('src','');$('#block-window').window('close');">关闭</a>
	        </div>
	      </div>
	    </div>
        <div id="btnCrawlSub" style="width:80px;display:none;">
        	<div id="btnExe" iconCls="icon-sortset" onclick="sortOperate('<s:url namespace='/document/article' action='isSortArticle'/>','<s:url namespace='/document/article' action='sortArticle'/>');">立刻</div>
	        <div id="btnSchedul" iconCls="icon-sortclear" onclick="clearSortOperate('<s:url namespace='/document/article' action='clearSortArticle'/>');">定时</div>
	    </div>
		<div id="scheduling-window" class="easyui-window" closed="true" title="&nbsp;区块" style="display:none;">
	      <div class="easyui-layout" fit="true">
	        <div region="center" border="false">
	          <iframe id="editifr_scheduling"  name="editifr_scheduling" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
	        </div>
	        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
	          <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0);" onclick="javascript:saveScheduling();">保存</a>
	          <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0);" onclick="javascript:$('#editifr_scheduling').attr('src','');$('#scheduling-window').window('close');">关闭</a>
	        </div>
	      </div>
	    </div>
		<div id="help-window" class="easyui-window" closed="true" title="&nbsp;表达式帮助" style="display:none;">
	      <div class="easyui-layout" fit="true">
	        <div region="center" border="false">
	          <iframe id="editifr_help"  name="editifr_help" class="editifr" frameborder="0" width="100%" height="100%" scrolling="auto" style="overflow: inherit;"></iframe>
	        </div>
	        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
	          <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0);" onclick="javascript:$('#help-window').window('close');">关闭</a>
	        </div>
	      </div>
	    </div>
	</body>
</html>