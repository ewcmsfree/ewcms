<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板资源编辑</title>
		<s:include value="../../../taglibs.jsp"/>
		<script type="text/javascript"> 
			$(function(){
				$('#systemtab').tabs({
					onSelect:function(title){
						var info='<s:url action="editInfo"/>?sourceVo.id=<s:property value="sourceVo.id"/>';
						var content='<s:url action="editContent"/>?sourceVo.id=<s:property value="sourceVo.id"/>';
						if(title=="基本信息"){
							$("#editinfoifr").attr('src',info);
						}
						if(title=="内容编辑"){
							var fileName = parent.$('#tt2').tree('getSelected').text;
							if(fileName.lastIndexOf(".") > 0){
								 var modeName = fileName.substring(fileName.lastIndexOf(".")+1) ;
								 if (modeName == "htm" || modeName == "html" || modeName == "css" || modeName == "js"){
									 $("#editcontentifr").attr('src',content);
								 }else{
									 $.messager.alert('提示', '不能对' + fileName +'内容进行编辑', 'warn');
								 }
							}
						}
					}
				});
			});	
			function alertInfo(mesg){
				$.messager.alert("",mesg);
			}
		</script> 					
	</head>
	<body>
		<s:if test="sourceVo==null">
			模板资源管理可以对一个网站模板资源进行统一管理,可以按照文件目录对模板资源管理
			<br>
			双击模板资源文件或左边弹出菜单编辑可以对模板资源内容进行编辑。								
		</s:if>
		<s:else>	
			<div class="easyui-tabs"  id="systemtab" border="false" fit="true">
				<div title="基本信息" style="padding: 5px;">
					<iframe id="editinfoifr"  name="editinfoifr" class="editifr" scrolling="no"></iframe>				
				</div>
				<div title="内容编辑" style="padding: 5px;">
					<iframe id="editcontentifr"  name="editcontentifr" class="editifr" scrolling="no"></iframe>										
				</div>								
			</div>			
		</s:else>
	</body>
</html>