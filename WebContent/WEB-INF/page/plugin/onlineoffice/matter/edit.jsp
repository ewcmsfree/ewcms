<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>事项基本信息</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>	
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
        <script type="text/javascript">
	        $(function(){
	        	$('#systemtab').tabs('select','事项基本信息');
	        	$('#systemtab_annex').tabs('select','本地附件');
                $('#systemtab_annex').tabs({
                    onSelect:function(title){
                        if(title == '本地附件'){
                            var src = '<s:url action="upload" namespace="/resource/annex"/>?multi=false';
                            $("#uploadifr_annex_id").attr('src',src);
                        }else{
                            var src = '<s:url action="browse" namespace="/resource/annex"/>?multi=false';
                            $("#queryifr_annex_id").attr('src',src);
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
			var i = <s:if test="matterVo.matterAnnexs.size>0"><s:property value="matterVo.matterAnnexs.size"/></s:if><s:else>0</s:else>;
            function addAnnexTable(){
            	var temp = document.getElementById("third");
    			temp.insertRow(temp.rows.length);
    			temp.rows.item(temp.rows.length -1).insertCell(0);
    			var xx = temp.rows.length -1 ;//-1
    			var sHTML;
    			
    			sHTML='<table class=\"formtable\" border=\"1\" style=\"border-collapse: collapse\" width=\"100%\">' + 
    				  '    <tr>' + 
    				  '        <td align=\"center\" colspan=\"2\"><font color=\"#FF0000\">【新增】</font>第 ' + (++i) + ' 条信息&nbsp;&nbsp;<input type=\"button\" value=\"删除\" onclick=\"delComponent(this);\"/></td>' + 
    				  '    </tr>' +
    				  '    <tr>' +
    				  '       <td align=\"right\">上传文件：</td>' +
    				  '       <td><input type=\"text\" id=\"filePath_' + i + '\" name=\"filePath\" size=\"80\" readonly=\"true\"/><input type=\"button\" id=\"fileButton_' + i + '\" onclick=\"selectAnnex(' + i + ');\" value=\"浏览\"/></td>' + 
    				  '    </tr>' +
    				  '    <tr>' +
    				  '       <td align=\"right\">说明：</td>' +
    				  '       <td><textarea name=\"legend\" cols=\"140\" rows=\"1\"></textarea></td>' +
    				  '    </tr>' + 
    				  '</table>';
    			temp.rows.item(temp.rows.length - 1).cells.item(0).innerHTML = sHTML;
    			temp.rows.item(temp.rows.length - 1).cells.item(0).childNodes.item(0).rows.item(0).cells.item(0).childNodes.item(2).text = xx;
    		}
    		function delComponent(x){
    			var temp = document.getElementById("third");
    			temp.deleteRow(x.text.valueOf());
    			for (var j=0;j<temp.rows.length;j++) {
    				temp.rows.item(j).cells.item(0).childNodes.item(0).rows.item(0).cells.item(0).childNodes.item(2).text = j;
    			}
    		}

    		function delComponent_upd(x){
    			var image_upd_tr = document.getElementById("image_upd_tr_" + x);
    			if (image_upd_tr != undefined){
    				image_upd_tr.parentNode.removeChild(image_upd_tr);
    			}
    		}
    		var selItem = 0;
    		function selectAnnex(selectId){
        		selItem = selectId;
				openAnnexWindow();
			}
            function openAnnexWindow(){
            	$('#systemtab_annex').tabs('select','本地附件');
                $('#uploadifr_annex_id').attr('src','<s:url action="upload" namespace="/resource/annex"/>?multi=false');
                openWindow("#annex-window",{width:600,height:500,top:5,title:"附件选择"});
            }
            function insertAnnexOperator(){
                var tab = $('#systemtab_annex').tabs('getSelected');
                var title = tab.panel('options').title;
                if (selItem > 0){
	                if(title == '本地附件'){
	                    uploadifr.insert(function(data){
	                        $.each(data,function(index,value){
		            			$("#filePath_" + selItem).attr("value","../.." + value.releasePath);
	                        });
	                    });
	                }else{
	                    queryifr.insert(function(data){
	                        $.each(data,function(index,value){
	                        	$("#filePath_" + selItem).attr("value","../.." + value.releasePath);
	                        });
	                    });
	                }
                }
                selItem = 0;
            	$("#annex-window").window("close");
            }            
            <s:property value="javaScript"/>
        </script>		
	</head>
	<body onload="tipMessage();">
		<s:form action="save" namespace="/plugin/onlineoffice/matter">
			<div class="easyui-tabs" id="systemtab" border="false">
				<div title="事项基本信息" style="padding: 5px;">	
					<table class="formtable" cellspacing="4">
						<tr>
							<td width="10%">事项名称：</td>
							<td width="40%" class="formFieldError">
								<s:textfield name="matterVo.name" cssClass="inputtext" size="40"/>
								<s:fielderror ><s:param value="%{'matterVo.name'}" /></s:fielderror>
							</td>
							<td width="10%">受理方式：</td>
							<td width="40%">
								<s:textarea name="matterVo.acceptedWay" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>办理地点：</td>
							<td>
								<s:textarea name="matterVo.handleSite" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>办理依据：</td>
							<td>
								<s:textarea name="matterVo.handleBasis" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>审批、服务数量及方式：</td>
							<td>
								<s:textarea name="matterVo.handleWay" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>受理条件：</td>
							<td>
								<s:textarea name="matterVo.acceptedCondition" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>申请材料：</td>
							<td>
								<s:textarea name="matterVo.petitionMaterial" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>办理程序：</td>
							<td>
								<s:textarea name="matterVo.handleCourse" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>法定时限：</td>
							<td>
								<s:textarea name="matterVo.timeLimit" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>承诺期限：</td>
							<td>
								<s:textarea name="matterVo.deadline" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>收费标准：</td>
							<td>
								<s:textarea name="matterVo.fees" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>收费依据：</td>
							<td>
								<s:textarea name="matterVo.feesBasis" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>咨询电话：</td>
							<td>
								<s:textarea name="matterVo.consultingTel" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>联系人姓名：</td>
							<td>
								<s:textarea name="matterVo.contactName" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>所在部门：</td>
							<td>
								<s:textarea name="matterVo.department" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>联系电话：</td>
							<td>
								<s:textarea name="matterVo.contactTel" cssStyle="height:50px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>E—MAIL：</td>
							<td>
								<s:textarea name="matterVo.email" cssStyle="height:50px;width:100%;"/>
							</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</div>
				<div title="附件管理" style="padding: 5px;">
					<table class="formtable" cellSpacing="1" cellPadding="3" border="0" width="100%">
						<tr>
							<td>
								<input type="button" name="addAnnex" value="添加附件" onclick="addAnnexTable();"/>
								<table align="center" border="0" width="100%" height="100%" id="second">
									<tr>
										<td width="100%" height="450" align="center">
											<div style="overflow: auto;border-right: 1px solid; border-top: 1px solid; border-left: 1px solid; border-bottom: 1px solid; width: 100%; height: 100%">
												<table width="100%" id="third_1">
												<s:iterator value="matterVo.matterAnnexs" status="matterAnnexStatus" var="matterAnnex">
									    			<tr id="image_upd_tr_${matterAnnexStatus.index + 1}">
										    			<td>
											   				<table border="1" style="border-collapse: collapse" width="100%">
							  									<tr>
							  										<td align="center" colspan="2"><font color="#0000FF">【修改】</font>第 ${matterAnnexStatus.index + 1} 条信息&nbsp;&nbsp;<input type="button" value="删除" onclick='delComponent_upd("${matterAnnexStatus.index + 1}");'>
							  											<input type="hidden" id="matterAnnexId" name="matterAnnexId" value="${id}"/>
							  										</td>
							  									</tr> 
							  									<tr> 
							  										<td align="right">上传文件：</td> 
							  										<td>
							  											<input type="text" id="filePath_${matterAnnexStatus.index + 1}" name="filePath" size="80" readonly="true"/><input type="button" id="fileButton_${matterAnnexStatus.index + 1}" onclick="selectAnnex(${matterAnnexStatus.index + 1});" value="浏览"/>
							  										</td> 
							  									</tr>
							  									<tr>
							  										<td align="right">原文件：</td>
							  										<td>
																		<a href="../${url}" target="_blank">下载</a>							  											
							  										</td>
							  									</tr>
							 									<tr>
							 										<td align="right">说明：</td> 
							  										<td><textarea name="legend" cols="140" rows="1"><s:property value="%{legend}" default=""/></textarea></td>
							  									</tr>
							  								</table>
						  								</td>
					  								</tr>
				  								</s:iterator>
												</table>
												<table width="100%" id="third">
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:hidden name="matterVo.id"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
		<div id="annex-window" class="easyui-window" closed="true" icon="icon-save" title="插入附件" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false" style="padding:10px 5px 10px 0;background:#fff;border:1px solid #ccc;overflow: hidden;">
                    <div class="easyui-tabs" id="systemtab_annex" border="false" fit="true"  plain="true">
                        <div title="本地附件"  style="padding: 5px;" cache="true">
                            <iframe src="" id="uploadifr_annex_id"  name="uploadifr" class="editifr" scrolling="no"></iframe>
                        </div>
                        <div title="服务器附件" cache="true">
                            <iframe src="" id="queryifr_annex_id"  name="queryifr" class="editifr" scrolling="no"></iframe>
                        </div>
                    </div>
                </div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;padding:3px 6px;">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="insertAnnexOperator()">插入</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#annex-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>	
	</body>
</html>