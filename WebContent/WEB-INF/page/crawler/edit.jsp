<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>采集器基本信息</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
        <script type="text/javascript">
			function tipMessage(){
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
						$.messager.alert('提示','<s:property escape="false"/>','info');
			        </s:iterator>  
		     	</s:if>  
			}
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
            <s:property value="javaScript"/>
        </script>		
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/crawler">
			<table class="formtable" >
				<tr>
					<td>名称：</td>
					<td class="formFieldError">
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
					<td>状态：</td>
					<td>
						<s:radio id="status" name="gatherVo.status" list='#{true:"&nbsp;启用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"}' cssStyle="vertical-align: middle;" value="true"></s:radio>
						<s:radio id="status" name="gatherVo.status" list='#{false:"&nbsp;停止&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"}' cssStyle="vertical-align: middle;"></s:radio> 
					</td>
				</tr>
				<tr>
					<td>最大采集数：</td>
					<td class="formFieldError">
						<s:textfield id="maxContent" name="gatherVo.maxContent" maxlength="8"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.maxContent'}" /></s:fielderror>(-1表示不限制)
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
						<s:fielderror><s:param value="%{'gatherVo.threadCount'}"/></s:fielderror>(1-10)
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
					<td>错误时重试次数：</td>
					<td>
						<s:textfield id="errorCount" name="gatherVo.errorCount" maxlength="2"/><font color="red">*</font>
						<s:fielderror><s:param value="%{'gatherVo.errorCount}"/></s:fielderror>(1-10)
					</td>
				</tr>
				<tr>
					<td>发布日期格式：</td>
					<td>
						<s:textfield id="dateFormat" name="gatherVo.dateFormat"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>采集选项：</td>
					<td>
						<s:checkboxlist list="@com.ewcms.crawler.model.CaptureOptions@values()" listValue="description" name="gatherVo.option" id="option" cssStyle="vertical-align: middle;"></s:checkboxlist>
					</td>
				</tr>
				<tr>
					<td>采集到此频道：</td>
					<td>
						<s:textfield id="channelName" name="channelName" readonly="true"/><input type="button" name="btnChannel" value="选择..." onclick="selectOperate();return false;"/>
						<s:hidden id="channelId" name="gatherVo.channelId"/>
					</td>
				</tr>
			</table>
			<s:hidden name="gatherVo.id"/>
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