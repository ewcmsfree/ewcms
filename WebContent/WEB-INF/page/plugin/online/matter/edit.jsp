<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>事项基本信息</title>
		<s:include value="../../../taglibs.jsp"	/>
        <script type="text/javascript">
	        $(function(){
	        	<s:include value="../../../alertMessage.jsp"/>
	        });
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
    				  '       <td><textarea name=\"legend\" cols=\"110\" rows=\"1\"></textarea></td>' +
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
    			ewcmsBOBJ = new EwcmsBase();
        		selItem = selectId;
        		ewcmsBOBJ.openWindow("#insert-window",{width:600,height:450,top:2,title:"附件选择", url:'<s:url action="insert" namespace="/resource"/>?type=annex&multi=false'});
			}
            function insertAnnex(){
            	annex_insert.insert(function(data,success){
            		if (success){
            			$.each(data, function(index,value){
            				$("#filePath_" + selItem).attr("value", value.uri);
            			});
            		}else{
            			$.messager.alert('错误', '插入失败', 'error');
            		}
            	});
                selItem = 0;
            	$("#insert-window").window("close");
            }            
        </script>		
	</head>
	<body>
		<s:form action="save" namespace="/plugin/online/matter">
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
								<s:textarea name="matterVo.acceptedWay" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>办理地点：</td>
							<td>
								<s:textarea name="matterVo.handleSite" cssStyle="height:40px;width:100%;"/>
							</td>
							<td>办理依据：</td>
							<td>
								<s:textarea name="matterVo.handleBasis" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>审批、服务数量及方式：</td>
							<td>
								<s:textarea name="matterVo.handleWay" cssStyle="height:40px;width:100%;"/>
							</td>
							<td>受理条件：</td>
							<td>
								<s:textarea name="matterVo.acceptedCondition" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>申请材料：</td>
							<td>
								<s:textarea name="matterVo.petitionMaterial" cssStyle="height:40px;width:100%;"/>
							</td>
							<td>办理程序：</td>
							<td>
								<s:textarea name="matterVo.handleCourse" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>法定时限：</td>
							<td>
								<s:textarea name="matterVo.timeLimit" cssStyle="height:40px;width:100%;"/>
							</td>
							<td>承诺期限：</td>
							<td>
								<s:textarea name="matterVo.deadline" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>收费标准：</td>
							<td>
								<s:textarea name="matterVo.fees" cssStyle="height:40px;width:100%;"/>
							</td>
							<td>收费依据：</td>
							<td>
								<s:textarea name="matterVo.feesBasis" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>咨询电话：</td>
							<td>
								<s:textarea name="matterVo.consultingTel" cssStyle="height:40px;width:100%;"/>
							</td>
							<td>联系人姓名：</td>
							<td>
								<s:textarea name="matterVo.contactName" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>所在部门：</td>
							<td>
								<s:textarea name="matterVo.department" cssStyle="height:40px;width:100%;"/>
							</td>
							<td>联系电话：</td>
							<td>
								<s:textarea name="matterVo.contactTel" cssStyle="height:40px;width:100%;"/>
							</td>
						</tr>
						<tr>
							<td>E—MAIL：</td>
							<td>
								<s:textarea name="matterVo.email" cssStyle="height:40px;width:100%;"/>
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
										<td width="100%" height="350" align="center">
											<div style="OVERFLOW-Y:auto;OVERFLOW-X:hidden;border-right: 1px solid; border-top: 1px solid; border-left: 1px solid; border-bottom: 1px solid; width: 100%; height:350;">
												<table width="100%" id="third_1">
												<s:iterator value="matterVo.matterAnnexs" status="matterAnnexStatus" var="matterAnnex">
									    			<tr id="image_upd_tr_<s:property value='#matterAnnexStatus.index+1'/>">
										    			<td>
											   				<table border="1" style="border-collapse: collapse" width="100%">
							  									<tr>
							  										<td align="center" colspan="2"><font color="#0000FF">【修改】</font>第 <s:property value='#matterAnnexStatus.index+1'/> 条信息&nbsp;&nbsp;<input type="button" value="删除" onclick='delComponent_upd("<s:property value='#matterAnnexStatus.index+1'/>");'>
							  											<input type="hidden" id="matterAnnexId" name="matterAnnexId" value="<s:property value='id'/>"/>
							  										</td>
							  									</tr> 
							  									<tr> 
							  										<td align="right">上传文件：</td> 
							  										<td>
							  											<input type="text" id="filePath_<s:property value='#matterAnnexStatus.index+1'/>" name="filePath" size="80" readonly="true"/><input type="button" id="fileButton_<s:property value='#matterAnnexStatus.index+1'/>" onclick="selectAnnex(<s:property value='#matterAnnexStatus.index+1'/>);" value="浏览"/>
							  										</td> 
							  									</tr>
							  									<tr>
							  										<td align="right">原文件：</td>
							  										<td>
							  											<s:if test="url!=''">
																		<a href="<s:property value='url'/>" target="_blank">下载</a>
																		</s:if>
																		<s:else>
																		无文件下载
																		</s:else>							  											
							  										</td>
							  									</tr>
							 									<tr>
							 										<td align="right">说明：</td> 
							  										<td><textarea name="legend" cols="140" rows="1"><s:property value="legend" default=""/></textarea></td>
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
		<div style="width:100%;height:16px;position:absolute;text-align:center;height:28px;line-height:28px;background-color:#f6f6f6;bottom:0px;left:0px;">
	    	<a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="document.forms[0].submit();">保存</a>
	    </div>
		
		<div id="insert-window" class="easyui-window" closed="true" icon="icon-save" title="插入" style="display:none;">
            <div class="easyui-layout" fit="true">
            	<div region="center" border="false">
             		<iframe src="" id="annex_insert_id"  name="annex_insert" class="editifr" scrolling="no"></iframe>
             	</div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;padding:3px 6px;">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="insertAnnex();">插入</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#insert-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>	
	</body>
</html>