<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>文档编辑</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src="<s:url value='/ewcmssource/easyui/ext/datagrid-detailview.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/page/document/index.js'/>"></script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
		<script type="text/javascript">
			queryURL = "<s:url namespace='/document/article' action='query'/>";
			inputURL = "<s:url namespace='/document/article' action='input'/>";
			deleteURL = "<s:url namespace='/document/article' action='delete'/>";
			treeURL = "<s:url namespace='/site/channel' action='tree'/>";
			reasonURL = "<s:url namespace='/document/article' action='reason'/>";
			trackURL = "<s:url namespace='/document/track' action='index'/>";
			effectiveURL = "<s:url namespace='/document/article' action='reviewEffective'/>";
		</script>
	</head>
	<body class="easyui-layout">
		<div region="west"  title='<img src="<s:url value="/ewcmssource/easyui/themes/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
			<ul id="tt2"></ul>
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
                        <tr>
                            <td class="tdtitle">发布时间：</td>
                            <td class="tdinput" colspan="3">
                                <ewcms:datepicker id="publishedStart" name="publishedStart" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                                &nbsp;至&nbsp;
                                <ewcms:datepicker id="publishedEnd" name="publishedEnd" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtitle">修改时间：</td>
                            <td class="tdinput" colspan="3">
                                <ewcms:datepicker id="modifiedStart" name="modifiedStart" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                                &nbsp;至&nbsp;
                                <ewcms:datepicker id="modifiedEnd" name="modifiedEnd" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtitle">状态：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.content.document.model.Article$Status@values()" listValue="description" name="articleStatus" id="articleStatus" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                            <td class="tdtitle">类型：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.content.document.model.Article$Type@values()" listValue="description" name="articleType" id="articleType" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                        </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch_Article('<s:url namespace='/document/article' action='query'/>');">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="moveorcopy-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul id="tt3"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="span_move" style="display:none"><a id="moveArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="moveArticle('<s:url namespace='/document/article' action='move'/>');">确定</a></span>
                    <span id="span_copy" style="display:none"><a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="copyArticle('<s:url namespace='/document/article' action='copy'/>');">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#moveorcopy-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="review-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;width:550px;height:230px;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                	<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#99BBE8" style="border: #99BBE8 1px solid;">
                		<tr align="center">
                			<td height="30" width="20%">操作</td>
                			<td height="30" width="80%">说明</td>
                		</tr>
                		<tr>
                			<td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="reviewRadio" name="reviewRadio" list='#{0:"通过"}' cssStyle="vertical-align: middle;" value="0"></s:radio></td>
                			<td height="40">&nbsp;</td>
                		</tr>
                		<tr>
                			<td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="reviewRadio" name="reviewRadio"  list='#{1:"不通过"}' cssStyle="vertical-align: middle;"></s:radio></td>
                			<td height="40">&nbsp;<s:textarea id="reason" name="reason" cols="42"/></td>
                		</tr>
                	</table>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:reviewArticle('<s:url namespace='/document/article' action='reviewArticle'/>');">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#review-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
        <div id="sort-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                    <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#99BBE8" style="border: #99BBE8 1px solid;">
                        <tr align="center">
                            <td height="30" width="20%">操作</td>
                            <td height="30" width="80%">说明</td>
                        </tr>
                        <tr>
                            <td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="sortRadio" name="sortRadio" list='#{0:"插入"}' cssStyle="vertical-align: middle;" value="0"></s:radio></td>
                            <td height="40">&nbsp;所选文章将插入到当前排序号</td>
                        </tr>
                        <tr>
                            <td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="sortRadio" name="sortRadio"  list='#{1:"替换"}' cssStyle="vertical-align: middle;"></s:radio></td>
                            <td height="40">&nbsp;所选文章将替换已有的文章的排序号</td>
                        </tr>
                    </table>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:sortArticle('<s:url namespace='/document/article' action='sortArticle'/>');">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#sort-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
        <div id="btnSortSub" style="width:80px;display:none;">
        	<div id="btnSortSet" iconCls="icon-sortset" onclick="sortOperate('<s:url namespace='/document/article' action='isSortArticle'/>','<s:url namespace='/document/article' action='sortArticle'/>');">设置</div>
	        <div id="btnSortClear" iconCls="icon-sortclear" onclick="clearSortOperate('<s:url namespace='/document/article' action='clearSortArticle'/>');">清除</div>
	    </div>
	    <div id="btnReviewSub" style="width:80px;display:none;">
	    	<div id="btnReviewSubmit" iconCls="icon-reviewsubmit" onclick="submitReviewOperate('<s:url namespace='/document/article' action='submitReview'/>');">提交</div>
	        <div id="btnReviewProcess" iconCls="icon-reviewprocess" onclick="reviewOperate();">确认</div>
	    </div>
	    <div id="btnPubSub" style="width:80px;display:none;">
	    	<div id="btnPublishOk" iconCls="icon-publishok" onclick="pubOperate('<s:url namespace='/document/article' action='pubArticle'/>?recursion=false');" >独立</div>
	    	<div id="btnPublishRec" iconCls="icon-publishrec" onclick="pubOperate('<s:url namespace='/document/article' action='pubArticle'/>?recursion=true');" >关联</div>
	    	<div id="btnPublishSep" class="menu-sep"></div>
	    	<div id="btnBreakArticle" iconCls="icon-breakarticle" onclick="breakOperate('<s:url namespace='/document/article' action='breakArticle'/>');">退回</div>
	    </div>
	    <div id="btnTopSub" style="width:80px;display:none;">
	    	<div id="btnTopSet" iconCls="icon-top-set" onclick="topOperate('<s:url namespace='/document/article' action='topArticle'/>',true);">确定</div>
	        <div id="btnTopCancel" iconCls="icon-top-cancel" onclick="topOperate('<s:url namespace='/document/article' action='topArticle'/>',false);">取消</div>
	    </div>
		<div id="reason-window" class="easyui-window" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_reason"  name="editifr_reason" frameborder="0" width="100%" height="100%" scrolling="auto" style="width:100%;height:100%;"></iframe>
                </div>
            </div>
        </div>
	</body>
</html>