<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>报表集</title>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/loading.js'/>"></script>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/dark-hive/easyui.css"/>' rel="stylesheet" title="dark-hive"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>' rel="stylesheet" title="default"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/pepper-grinder/easyui.css"/>' rel="stylesheet" title="pepper-grinder"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/sunny/easyui.css"/>' rel="stylesheet" title="sunny"/>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'></link>
		<link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/page/report/show.css"/>'></link>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="/ewcmssource/js/skin.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
			});
			function setReportParameter(reportId,eventStr){
				var url = '<s:url namespace="/report/show" action="paraset"/>?reportType=' + eventStr + '&reportId='+ reportId;
				$('#parameterifr').attr('src',url);
				ewcmsBOBJ.openWindow("#parameter-window",{width:400,height:213,title:"参数选择"});
			}
		</script>		
	</head>
	<body>
		<div style="padding: 2px;" border="false">
			<table class="formtable" width="100%" height="100%">
				<tr>
					<td>
						<table class="formtable" width="100%" height="100%">
						<s:iterator value="categoryReports" >
							<tr>
								<td colspan="4" bgcolor="#a9c9e2" height="20"><font color="#1E4176"><b><s:property value="name"/>报表集</b></font></td>
							</tr>
							<tr>
								<td colspan="4"><b>文字报表：</b></td>
							</tr>
							<tr>
							<s:iterator value="texts" status="stat">
								<td width="20%">
									<a href="javascript:void(0);" onclick='setReportParameter(<s:property value="id"/>,"text");' style="text-decoration:none;" title="<s:property value='remarks'/>"><span class="ellipsis"><s:property value="name"/></span></a>
								</td>
								<s:if test="#stat.index%3==0 && !#stat.first && !#stat.last">
           						</tr>
           						<tr>
       							</s:if>
							</s:iterator>
							</tr>
							<tr>
								<td colspan="4"><b>图型报表：</b></td>
							</tr>
							<tr>
							<s:iterator value="charts" status="stat">
								<td width="20%">
									<a href="javascript:void(0);" onclick='setReportParameter(<s:property value="id"/>,"chart");' style="text-decoration:none;" title="<s:property value='remarks'/>"><span class="ellipsis"><s:property value="name"/></span></a>
								</td>	
								<s:if test="#stat.index%3==0 && !#stat.first && !#stat.last">
           						</tr>
           						<tr>
       							</s:if>
							</s:iterator>
							</tr>
						</s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</div>
        <div id="parameter-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;参数选择" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="parameterifr"  name="parameterifr" class="editifr" frameborder="0" style="overflow-x:hidden;overflow-y:scroll"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="window.frames['parameterifr'].document.forms[0].submit();">生成</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#parameter-window').window('close');">关闭</a>
                </div>
            </div>
        </div>	
	</body>
</html>