<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>专栏信息设置</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript"> 
	    	$(function() {
		        <s:include value="../../alertMessage.jsp"/>
		        <s:if test="channelVo.appChannel==null">$('#span-viewconnect').hide();</s:if><s:else>$('#span-viewconnect').show();</s:else>;
	    	});
	    	function openImageWindow(){
	    		var ewcmsBOBJ = new EwcmsBase();
	    	    var url = '<s:url action="insert" namespace="/resource"/>?type=image&multi=false';
	    	    ewcmsBOBJ.openWindow("#insert-window",{width:600,height:500,title:"图片选择",url:url});
	    	}
	    	function indertIconUrl(){
	    		uploadifr_insert.insert(function(data,success){
	    			if (success){
	    				$.each(data, function(index,value){
	    	    			$("#viewImage").attr("src", value.uri);
	    	    			$("#iconUrl").attr("value", value.uri);
	    			   });
	    			   $("#insert-window").window("close");
	    			}else{
	    				$.messager.alert('错误', '插入失败', 'error');
	    			}
	    	    });
	    	}		
	    	function clearImage(){
	    		$("#viewImage").attr("src","../../ewcmssource/image/article/nopicture.jpg");
	    		$("#iconUrl").attr("value","");
	    	}
	    	function pinYin(){
	    		var channelName = $('#channelVo_name').val();
	    		$.post('<s:url action="pinYin" namespace="/site/template"/>', {'channelName':channelName} ,function(data) {
	    			$('#channelVo_dir').val(data);
	    		});
	    	}
	    	function updateChannelTypeDesc(){
	    		//var node = parent.parent.selectedNode;
	    		//var parentNode = $('#tt2').tree('getParent',node.target);
				//parent.parent.$('#tt2').tree('reload',parentNode.target);
				//parent.parent.$('#tt2').tree('check', parentNode.target);
	    	}
	    	function connectOperate(){
				$.post("<s:url namespace='/site/template' action='connect'/>?channelId=<s:property value='channelVo.id'/>", {}, function(data) {
					$.messager.alert('提示', data, 'info');
					if (data.indexOf('完成') > 0){
						$('#span-connect').html('已建立');
						$('#span-viewconnect').show();
					}
				});
				return false;
			}
			function disConnectOperate(){
				$.post("<s:url namespace='/site/template' action='disConnect'/>?channelId=<s:property value='channelVo.id'/>", {}, function(data) {
					$.messager.alert('提示', data, 'info');
					if (data.indexOf('完成') > 0){
						$('#span-connect').html('已断开');
						$('#span-viewconnect').hide();
					}
				});
				return false;
			}
			function viewConnect(){
				var ewcmsBOBJ = new EwcmsBase();
				var url = '<s:url action="appIndex" namespace="/site/channel"/>?channelId=<s:property value="channelVo.id"/>';
				ewcmsBOBJ.openWindow("#connect-window",{width:650,height:370,url:url,title:"查看被引用栏目"});
			}
	    </script>
	</head>
	<body>
		<s:form action="saveInfo" namespace="/site/channel"  method="post" enctype="multipart/form-data">
			<table  class="formtable" align="center">
				<tr>
					<td>专栏(<s:property value="channelVo.id"/>)：</td>
					<td  width="80%" class="formFieldError">
						 <s:checkbox id="publicenable" name="channelVo.publicenable"/><label for="publicenable">是否发布</label>
					</td>
				</tr>
				<s:if test="channelVo.parent == null && !hasFieldErrors()">
					<tr>
						<td>站点名称：</td>
						<td  width="80%">
							<s:property value="channelVo.site.siteName"/> &nbsp;<a href="<s:property value="channelVo.site.siteURL" />" target="_blank">预览</a>
						</td>
					</tr>
					<tr>
						<td >站点目录名：</td>
						<td >
							<s:property value="channelVo.site.siteRoot"/>
							<input type="hidden" name="channelVo.dir" value='<s:property value="channelVo.site.siteRoot"/>'>
						</td>				
					</tr>	
					<tr>
						<td >站点URL：</td>
						<td >
							<s:property value="channelVo.site.siteURL"/>
						</td>				
					</tr>														
				</s:if>	
				<s:else>
					<tr>
						<td>专栏类型：</td>
						<td>
							<s:select list="@com.ewcms.core.site.model.Channel$Type@values()" listValue="description" name="channelVo.type" id="channelVo_type"></s:select>
						</td>
					</tr>													
					<tr>
						<td>专栏访问相对地址：</td>
						<td  width="80%" class="formFieldError">
							<s:property value="channelVo.absUrl"/>
						</td>
					</tr>																
					<tr>
						<td>专栏名称：</td>
						<td width="80%">
						    <s:property value="channelVo.name"/>
						    <s:hidden id="channelVo_name" name="channelVo.name"/>
							<a class="easyui-linkbutton" icon="" href="javascript:void(0);" onclick="pinYin();">名称转拼音</a>
						</td>
					</tr>
					<tr>
						<td>专栏目录：</td>
						<td class="formFieldError">
							<s:textfield id="channelVo_dir" name="channelVo.dir" cssClass="inputtext" size="20"/>
							<s:fielderror><s:param value="%{'channelVo.dir'}" /></s:fielderror>
						</td>				
					</tr>	
					<tr>
					  <td>被其他栏目引用：</td>
					  <td>
					    <span id="span-connect"><s:if test="channelVo.appChannel==null">已断开</s:if><s:else>已建立</s:else></span>&nbsp;
					    <a class="easyui-linkbutton" icon="icon-connect" href="javascript:void(0);" onclick="connectOperate();">重建</a>
					    <a class="easyui-linkbutton" icon="icon-disconnect"  href="javascript:void(0);" onclick="disConnectOperate();">断开</a>
					    <span id="span-viewconnect"><a class="easyui-linkbutton" icon=""  href="javascript:void(0);" onclick="viewConnect();">查看</a></span>
					  </td>
					</tr>
					<tr>
						<td>专栏URL：</td>
						<td class="formFieldError">
							<s:textfield name="channelVo.url" cssClass="inputtext" size="30"/>
							<s:fielderror ><s:param value="%{'channelVo.url'}" /></s:fielderror>
						</td>				
					</tr>		
					<tr>
						<td>列表页最大文档数：</td>
						<td class="formFieldError">
							<s:textfield name="channelVo.listSize" cssClass="inputtext" size="10"/>
							<s:fielderror ><s:param value="%{'channelVo.listSize'}" /></s:fielderror>
						</td>					
					</tr>
					<tr>
						<td>最大显示文档数：</td>
						<td class="formFieldError">
							<s:textfield name="channelVo.maxSize" cssClass="inputtext" size="10"/>
							<s:fielderror ><s:param value="%{'channelVo.maxSize'}" /></s:fielderror>
						</td>					
					</tr>
					<tr>
						<td>专栏介绍：</td>
						<td>
							<s:textarea name="channelVo.describe" style="width:300px;height:100px" cssClass="inputtext"></s:textarea>			
						</td>				
					</tr>																																					
				</s:else>	
					<tr>
						<td>引导图：</td>
						<td>
							<a href="javascript:void(0);" onclick="openImageWindow();return false;" style="text-decoration:none;">
							<s:textfield id="iconUrl" name="channelVo.iconUrl" cssStyle="display:none;"/>
			        		<s:if test="channelVo.iconUrl!=null&&channelVo.iconUrl!=''">
			        			<img id="viewImage" name="viewImage" width="120px" height="90px" src="../..<s:property value='channelVo.iconUrl'/>"/>
			        		</s:if>
			        		<s:else>
			        			<img id="viewImage" name="viewImage" width="120px" height="90px" src="<s:url value='/ewcmssource/image/article/nopicture.jpg'/>"/>
			        		</s:else>
			        		</a>
			        		<a class="easyui-linkbutton" href="javascript:void(0)" onclick="clearImage();return false;" style="vertical-align:bottom;">清除图片</a>				
						</td>				
					</tr>
				<tr>
					<td colspan="2" style="padding:0;">
						<div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
						    <a class="easyui-linkbutton" icon="icon-save" href="javascript:updateChannelTypeDesc();document.forms[0].submit();">保存</a>
						    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:document.forms[0].reset();">重置</a>
					    </div>								
					</td>
				</tr>																																																														
			</table>
			<s:hidden name="channelVo.id"/>					
		</s:form>
		<div id="insert-window" class="easyui-window" closed="true" icon="icon-save" title="插入" style="display:none;">
            <div class="easyui-layout" fit="true">
            	<div region="center" border="false">
             		<iframe src="" id="uploadifr_insert_id"  name="uploadifr_insert" class="editifr" scrolling="no"></iframe>
             	</div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;padding:3px 6px;">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="indertIconUrl();">插入</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#insert-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
        <div id="connect-window" class="easyui-window" title="查看被引用栏目" icon="icon-save" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
            </div>
        </div>	
	</body>
</html>