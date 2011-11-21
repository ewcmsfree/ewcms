<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>报表系统</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>"></link>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
		<script type="text/javascript">
			ewcmsBOBJ = new EwcmsBase();
			function setReportParameter(reportId,eventStr){
				var url = '<s:url namespace="/report/show" action="paraset"/>?reportType=' + eventStr + '&reportId='+ reportId;
				$('#parameterifr').attr('src',url);
				ewcmsBOBJ.openWindow("#parameter-window",{width:400,height:300,title:"参数选择"});
			}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
		<table class="formtable" width="100%" height="100%">
			<tr>
				<td>
					<s:iterator value="%{categoryReports}" >
						<table class="formtable" width="100%" height="100%">
							<tr>
								<td colspan="4" bgcolor="#a9c9e2" height="20"><FONT color="#1E4176"><b><s:property value="name"/>报表集</b></FONT></td>
							</tr>
							<tr>
								<td><b>文字报表</b></td>
							</tr>
							<tr height="25">
								<td>
									<s:iterator value="texts">
										<label style="border:float gray;height:20px;vertical-align: middle;" onclick='setReportParameter(<s:property value="id"/>,"text");'>&nbsp;<s:property value="textName"/>&nbsp;</label>&nbsp;
									</s:iterator>
								</td>
							</tr>
							<tr>
								<td><b>图型报表</b></td>
							</tr>
							<tr height="25">
								<td>
									<s:iterator value="charts">
										<label style="border:float gray;height:20px;vertical-align: middle;" onclick='setReportParameter(<s:property value="id"/>,"chart");'>&nbsp;<s:property value="name"/>&nbsp;</label>&nbsp;
									</s:iterator>
								</td>							
							</tr>
						</table>
						<br>
					</s:iterator>
				</td>
			</tr>
		</table>
		</div>
        <div id="parameter-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;参数选择" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="parameterifr"  name="parameterifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" style="overflow-x:hidden;overflow-y:scroll"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="window.frames['parameterifr'].document.forms[0].submit();">生成</a>
                    <a class="easyui-linkbutton" icon="icon-close" href="javascript:void(0)" onclick="$('#parameter-window').window('close');">关闭</a>
                </div>
            </div>
        </div>	
	</body>
</html>