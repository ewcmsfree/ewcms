<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>采集器基本信息</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>							
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>	
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
        <script type="text/javascript">
        	$(function() {
    	        <s:include value="../../alertMessage.jsp"/>
    	        
        		if ($("#proxy").attr("checked") == 'checked') {
    				$("#trProxy").show();
    			} else {
    				$("#trProxy").hide();
    			}
        		if ($('#titleExternal').attr('checked') == 'checked'){
        			$('#titleRegex').attr('readonly',false);
        		}else{
        			$('#titleRegex').attr('readonly',true);
        		}
        		$("#proxy").click(function() {
        			if ($("#proxy").attr("checked") == 'checked') {
        				$("#trProxy").show();
        			} else {
        				$("#trProxy").hide();
        			}
        		});
        		$('#titleExternal').click(function(){
            		if ($('#titleExternal').attr('checked') == 'checked'){
            			$('#titleRegex').attr('readonly',false);
            		}else{
            			$('#titleRegex').attr('readonly',true);
            		}
        		});
        	})
            function selectOperate(){
            	ewcmsBOBJ = new EwcmsBase();
            	$('#tt').tree( {
            		checkbox : false,
            		url : '<s:url namespace="/site/channel" action="tree"/>'
            	});
            	ewcmsBOBJ.openWindow('#channel-window', {title : '采集到', width : 300, height : 400});
            }
            function selectChannel(){
            	var selected = $('#tt').tree('getSelected');
            	if (selected == null || typeof (selected) == 'undefined') {
            		$.messager.alert('提示', '请选择采集到目标的栏目', 'info');
            		return;
            	}
            	var rootnode = $('#tt').tree('getRoot');
            	if (selected.id == rootnode.id) {
            		$.messager.alert('提示', '采集的文章不能在根栏目', 'info');
            		return;
            	}
        		$('#channelId').val(selected.id);
        		$('#channelName').val(selected.text);
        		$('#channel-window').window('close');
            }
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/crawler/content">
			<table class="formtable" >
				<tr>
					<td width="20%">名称：</td>
					<td width="80%" class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="gatherVo.name" maxlength="50"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.name'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>
						<s:textarea id="description" name="gatherVo.description" cols="50"></s:textarea>
					</td>
				</tr>
				<tr>
					<td>网页类型：</td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="gatherVo.htmlType" maxlength="50"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.htmlType'}" /></s:fielderror>(包括:html,shtml,htm等等,如有多个类型之间用逗号分隔)
					</td>
				</tr>
				<tr>
					<td>页面编码格式：</td>
					<td class="formFieldError">
						<s:textfield id="encoding" name="gatherVo.encoding" maxlength="50"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.encoding'}" /></s:fielderror>(包括:UTF-8,GBK,GB2312,ISO8859-1等等)
					</td>
				</tr>
				<tr>
					<td>网站地址：</td>
					<td class="formFieldError">
						<s:textfield id="baseURI" name="gatherVo.baseURI" size="50"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.baseURI'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>使用其他标题：</td>
					<td>
						<s:checkbox id="titleExternal" name="gatherVo.titleExternal" cssStyle="vertical-align:middle;"/>&nbsp;&nbsp;表达式：<s:textfield id="titleRegex" name="gatherVo.titleRegex" readonly="true" size="30"></s:textfield>&nbsp;<a id="regexHelp" href="javascript:void(0);" class="easyui-linkbutton" plain="true" iconCls="icon-help" onclick="parent.helpOperate();"></a>
					</td>
				</tr>
				<tr>
					<td>状态：</td>
					<td>
						<s:radio id="status" name="gatherVo.status" list='#{true:"&nbsp;启用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}' cssStyle="vertical-align: middle;" value="true"></s:radio>
						<s:radio id="status" name="gatherVo.status" list='#{false:"&nbsp;停止&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"}' cssStyle="vertical-align: middle;"></s:radio> 
					</td>
				</tr>
				<tr>
					<td>最大采集数：</td>
					<td class="formFieldError">
						<s:textfield id="maxContent" name="gatherVo.maxPage" maxlength="8"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.maxPage'}" /></s:fielderror>(-1表示不限制)
					</td>
				</tr>
				<tr>
					<td>采集深度：</td>
					<td class="formFieldError">
						<s:textfield id="depth" name="gatherVo.depth" maxlength="2"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.depth'}"/></s:fielderror>(-1表示不限制)
					</td>
				</tr>
				<tr>
					<td>采集线程数：</td>
					<td class="formFieldError">
						<s:textfield id="threadCount" name="gatherVo.threadCount" maxlength="2"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.threadCount'}"/></s:fielderror>(1-30)
					</td>
				</tr>
				<tr>
					<td>超时等待时间：</td>
					<td>
						<s:textfield id="timeOutWait" name="gatherVo.timeOutWait" maxlength="3"/><font color="red">*</font>秒
						<s:fielderror><s:param value="%{'gatherVo.timeOutWait'}"/></s:fielderror>(1-600)
					</td>
				</tr>
				<tr>
					<td>采集选项：</td>
					<td>
						<s:checkbox id="downloadFile" name="gatherVo.downloadFile" cssStyle="vertical-align: middle;"/><label for="downloadFile">下载内容中的文件&nbsp;&nbsp;</label>
						<s:checkbox id="removeHref" name="gatherVo.removeHref" cssStyle="vertical-align: middle;"/><label for="removeHref">移除内容中的链接&nbsp;&nbsp;</label>
						<s:checkbox id="removeHtmlTag" name="gatherVo.removeHtmlTag" cssStyle="vertical-align: middle;"/><label for="removeHtmlTag">移除内容中的HTML标签&nbsp;&nbsp;</label>
					</td>
				</tr>
				<tr>
					<td>采集到此频道：</td>
					<td>
						<s:textfield id="channelName" name="channelName" readonly="true"/><input type="button" name="btnChannel" value="选择..." onclick="selectOperate();return false;"/>
						<s:hidden id="channelId" name="gatherVo.channelId"/>
					</td>
				</tr>
				<tr>
					<td>使用代理服务器：</td>
					<td>
						<s:checkbox id="proxy" name="gatherVo.proxy" cssStyle="vertical-align: middle;"/><label for="proxy">&nbsp;&nbsp;</label>
					</td>
				</tr>
				<tr id="trProxy" style="display: none;">
					<td>&nbsp;</td>
					<td colspan="2" style="padding: 1px 1px;">
						<table class="formtable">
							<tr>
								<td width="20%">服务器地址：</td>
								<td width="80%">
									<s:textfield id="proxyHost" name="gatherVo.proxyHost" size="40"></s:textfield>
								</td>
							</tr>
							<tr>
								<td>端口：</td>
								<td>
									<s:textfield id="proxyPort" name="gatherVo.proxyPort"></s:textfield>
								</td>
							</tr>
							<tr>
								<td>用户名：</td>
								<td>
									<s:textfield id="proxyUserName" name="gatherVo.proxyUserName"></s:textfield>
								</td>
							</tr>
							<tr>
								<td>密码：</td>
								<td>
									<s:textfield id="proxyPassWord" name="gatherVo.proxyPassWord"></s:textfield>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<s:hidden name="gatherVo.id"/>
			<s:hidden name="gatherVo.type" value="CONTENT"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
        <div id="channel-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul id="tt"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="selectChannel();return false;">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#channel-window').window('close');">取消</a>
                </div>
            </div>
        </div>
	</body>
</html>