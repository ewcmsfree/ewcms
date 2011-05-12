<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>用户信息</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <script type="text/javascript">
	        $(document).ready(function() {
	            $('#systemtab').tabs({
	                onSelect:function(title){
	                    var multi = $('#image_multi_id').val();
	                    if(title == '本地图片'){
	                        var src = '<s:url action="upload" namespace="/resource/image"/>?multi='+multi;
	                        $("#uploadifr_id").attr('src',src);
	                    }else{
	                        var src = '<s:url action="browse" namespace="/resource/image"/>?multi='+multi;
	                        $("#queryifr_id").attr('src',src);
	                    }
	                }
	            });
	        });
	        
			function tipMessage(){
			    <s:if test="hasActionMessages()">  
			        <s:iterator value="actionMessages">  
						$.messager.alert('提示','<s:property escape="false"/>','info');
			        </s:iterator>  
		     	</s:if>  
			}
            <s:property value="javaScript"/>
            
			//选择引用图片
			function selectImage(){
				openImageWindow();
			}
            function openImageWindow(){
            	$('#systemtab').tabs('select','本地图片');
                $('#uploadifr_id').attr('src','<s:url action="upload" namespace="/resource/image"/>?multi=false');
                openWindow("#image-window",{width:600,height:500,top:8,left:182,title:"图片选择"});
            }
            function insertImageOperator(){
                var tab = $('#systemtab').tabs('getSelected');
                var title = tab.panel('options').title;
                if(title == '本地图片'){
                    uploadifr.insert(function(data){
                        $.each(data,function(index,value){
	            			$("#referenceImage").attr("src", "../../.." + value.releasePath);
	            			$("#leader_image").attr("value", value.releasePath);
                        });
                    });
                }else{
                    queryifr.insert(function(data){
                        $.each(data,function(index,value){
	            			$("#referenceImage").attr("src", "../../.." + value.releasePath);
	            			$("#leader_image").attr("value", value.releasePath);
                        });
                    });
                }
            	$("#image-window").window("close");
            }
            
            function clearImage(){
            	$("#referenceImage").attr("src","<s:url value='/source/image/article/nopicture.jpg'/>");
            	$("#leader_image").attr("value","");
            }
        </script>		
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/plugin/leadingwindow/leader">
			<table class="formtable">
				<tr>
					<td>照片：</td>
					<td>
						<a href="javascript:void(0);" onclick="selectImage();return false;">
			        	<s:if test="leaderVo.image!=null&&leaderVo.image!=''">
			        		<img id="referenceImage" name="referenceImage" width="126px" height="154px" src="${leaderVo.image}"/>
			        	</s:if>
			        	<s:else>
			        		<img id="referenceImage" name="referenceImage" width="126px" height="154px" src="<s:url value='/source/image/article/nopicture.jpg'/>"/>
			        	</s:else>
			        	</a>
			        	<s:textfield id="leader_image" name="leaderVo.image" cssStyle="display:none;"/>
			        	<input type="button" value="清除图片" style="vertical-align:bottom;" onclick="clearImage();return false;"/>
					</td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td class="formFieldError">
						<s:textfield id="leaderName" name="leaderVo.name" cssClass="inputtext" size="80"/>
						<s:fielderror ><s:param value="%{'leaderVo.name'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>职务描述：</td>
					<td>
						<s:textfield id="leaderName" name="leaderVo.duties" size="80"/>
					</td>
				</tr>
				<tr>
					<td>简历：</td>
					<td>
						<s:textarea name="leaderVo.resume" cssStyle="height:80px;width:100%;"/>
					</td>
				</tr>
				<tr>
					<td>分管工作：</td>
					<td class="formFieldError">
						<s:textarea name="leaderVo.chargeWork" cssClass="inputtext" cssStyle="height:80px;width:100%;"/>
						<s:fielderror ><s:param value="%{'leaderVo.chargeWork'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>E-Mail：</td>
					<td>
						<s:textfield name="leaderVo.email" size="120"/><s:fielderror><s:param value="%{'leaderVo.email'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>联系电话：</td>
					<td>
						<s:textfield name="leaderVo.contact" size="120"/><s:fielderror><s:param value="%{'leaderVo.contact'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>手机：</td>
					<td>
						<s:textfield name="leaderVo.mobile" size="120"/><s:fielderror><s:param value="%{'leaderVo.mobile'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>办公地址：</td>
					<td>
						<s:textfield name="leaderVo.officeAddress" size="120"/><s:fielderror><s:param value="%{'leaderVo.officeAddress'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			<s:hidden name="leaderVo.id"/>
			<s:hidden name="channelId" id="channelId"/>
			<s:hidden name="leaderVo.channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
		<div id="image-window" class="easyui-window" closed="true" icon="icon-save" title="插入图片" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding:10px 5px 10px 0;background:#fff;border:1px solid #ccc;overflow: hidden;">
                    <div class="easyui-tabs"  id="systemtab" border="false" fit="true"  plain="true">
                        <div title="本地图片"  style="padding: 5px;" cache="true">
                            <iframe src="" id="uploadifr_id"  name="uploadifr" class="editifr" scrolling="no"></iframe>
                        </div>
                        <div title="服务器图片" cache="true">
                            <iframe src="" id="queryifr_id"  name="queryifr" class="editifr" scrolling="no"></iframe>
                        </div>
                    </div>
                </div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;padding:3px 6px;">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="insertImageOperator()">插入</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="cancelImageOperator();return false;">取消</a>
                </div>
            </div>
        </div>	
	</body>
</html>