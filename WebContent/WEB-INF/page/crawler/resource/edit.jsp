<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>采集器基本信息</title>
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
        		if ($('#isAnnex').attr('checked') == 'checked'){
        			$("#annexType_span").show();
        		}else{
        			$("#annexType_span").hide();
        		}
        		$("#proxy").click(function() {
        			if ($("#proxy").attr("checked") == 'checked') {
        				$("#trProxy").show();
        			} else {
        				$("#trProxy").hide();
        			}
        		});
        		$('#isAnnex').click(function(){
            		if ($('#isAnnex').attr('checked') == 'checked'){
            			$("#annexType_span").show();
            		}else{
            			$("#annexType_span").hide();
            		}
        		});
       	})
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/crawler/resource">
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
					<td>资源类型：</td>
					<td>
						<s:checkbox id="isImage" name="gatherVo.isImage" cssStyle="vertical-align: middle;"/><label for="isImage">图片</label>&nbsp;
						<s:checkbox id="isFlash" name="gatherVo.isFlash" cssStyle="vertical-align: middle;"/><label for="isFlash">Flash</label>&nbsp;
						<s:checkbox id="isVideo" name="gatherVo.isVideo" cssStyle="vertical-align: middle;"/><label for="isVideo">视频</label>&nbsp;
						<s:checkbox id="isAnnex" name="gatherVo.isAnnex" cssStyle="vertical-align: middle;"/><label for="isAnnex">附件</label>&nbsp;[<span id="annexType_span">类型：<s:textfield id="annexType" name="gatherVo.annexType" size="10"></s:textfield>(以文件扩展名书写，多项文件中间以"|"隔开，例如：rar|zip)</span>]
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
			<s:hidden name="gatherVo.type" value="RESOURCE"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>