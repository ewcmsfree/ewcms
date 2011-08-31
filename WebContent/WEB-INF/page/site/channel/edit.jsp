<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
	<head>
		<title>专栏编辑</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript">
			var tplload=false,srcloda=false,authload=false,quartzload=false,infoload=false,processload=false;
			$(function(){
				$('#systemtab').tabs({
					onSelect:function(title){
							var tplurl='<s:url action="channelTemplate"/>?channelVo.id=<s:property value="channelVo.id"/>';
							var srcurl='<s:url action="channelSource"/>?channelVo.id=<s:property value="channelVo.id"/>';
							var authurl='<s:url action="channelAcl"/>?id=<s:property value="channelVo.id"/>';
							var quartzurl="<s:url namespace='/scheduling/jobchannel' action='index'/>?channelId=<s:property value='channelVo.id'/>";
							var infourl='<s:url action="editInfo"/>?channelVo.id=<s:property value="channelVo.id"/>';
							var processurl="<s:url namespace='/document/reviewprocess' action='index'/>?channelId=<s:property value='channelVo.id'/>";							
							if(title=="专栏模板"&&!tplload){
								$("#edittplifr").attr('src',tplurl);
								tplload=true;
							}
							if(title=="专栏资源"&&!srcloda){
								$("#editsrcifr").attr('src',srcurl);
								srcloda=true;
							}
							if(title=="访问控制"&&!authload){
								$("#editauthifr").attr('src',authurl);
								authload=true;
							}
							if(title=="发布设置"&&!quartzload){
								$("#editquartzifr").attr('src',quartzurl);
								quartzload=true;
							}	
							if(title=="基本设置"&&!infoload){
								$("#editinfoifr").attr('src',infourl);
								infoload=true;
							}		
							if (title=="审核流程"&&!processload){
								$("#editprocessifr").attr('src',processurl);
								processload=true;
							}												
					}
				});
			});	
		</script>					
	</head>
	<body>
		<s:if test="channelVo==null">
			专栏是对建设网站的栏目进行管理的，在这可以定制网站所需的栏目
			<br>
			双击专栏或左边弹出菜单编辑可以对专栏进行设置							
		</s:if>
		<s:else>
			<div class="easyui-tabs" id="systemtab" border="false" fit="true">
				<div title="基本设置">
					<iframe  id="editinfoifr"  name="editinfoifr" class="editifr" scrolling="no"></iframe>
				</div>			
				<div title="专栏模板" >
					<iframe  id="edittplifr"  name="edittplifr" class="editifr" scrolling="no"></iframe>	
				</div>
				<div title="专栏资源" >
					<iframe  id="editsrcifr"  name="editsrcifr" class="editifr" scrolling="no"></iframe>	
				</div>					
				<div title="访问控制" >
				    <iframe id="editauthifr"  name="editauthifr" class="editifr" scrolling="no"></iframe> 
				</div>
				<div title="发布设置" style="padding: 5px;">
					<iframe id="editquartzifr"  name="editquartzifr" class="editifr" scrolling="no"></iframe>		
				</div>	
 				<div title="审核流程">
 					<iframe id="editprocessifr" name="editprocessifr" class="editifr" scrolling="no"></iframe>
 				</div>																		
			</div>	
		</s:else>
	</body>
</html>