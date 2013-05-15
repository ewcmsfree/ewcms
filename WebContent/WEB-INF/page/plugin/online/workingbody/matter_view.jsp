<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>事项信息</title>
		<s:include value="../../../taglibs.jsp"	/>
        <script type="text/javascript">
            $(function(){
    			$('#w').window({
    				title: '事项信息',
    				collapsible:false,
				    minimizable:false,
				    maximizable:false,
				    closable:false,
    				closed: false,
				    draggable:false,
				    resizable:false,
				    shadow:false,
				    modal:true,
				    fit:true,
				    left:0,
				    top:0
    			});
    		});
        </script>		
	</head>
	<body>
		<div id="w" class="easyui-window" title="基本信息"  iconCls="icon-matter" style="width:500px;height:200px;padding:3px;background: #fafafa;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="OVERFLOW-Y:auto;OVERFLOW-X:hidden;border-right: 1px solid; border-top: 1px solid; border-left: 1px solid; border-bottom: 1px solid;">
					<div class="easyui-tabs" id="systemtab" border="true">
						<div title="事项基本信息" style="padding: 5px;">	
							<table class="formtable" >
								<tr>
									<td width="10%">事项名称：</td>
									<td width="40%">
										<s:textarea name="matterVo.name" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
									<!-- 
									<td width="10%">受理方式：</td>
									<td width="40%">
										<s:textarea name="matterVo.acceptedWay" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
								 -->
									<td width="10%">办理地点：</td>
									<td width="40%">
										<s:textarea name="matterVo.handleSite" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>办理依据：</td>
									<td>
										<s:textarea name="matterVo.handleBasis" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								<!-- 
								</tr>
								<tr>
									<td>审批、服务数量及方式：</td>
									<td>
										<s:textarea name="matterVo.handleWay" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								 -->
									<td>受理条件：</td>
									<td>
										<s:textarea name="matterVo.acceptedCondition" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>申请材料：</td>
									<td>
										<s:textarea name="matterVo.petitionMaterial" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
									<td>办理程序：</td>
									<td>
										<s:textarea name="matterVo.handleCourse" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>法定时限：</td>
									<td>
										<s:textarea name="matterVo.timeLimit" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
									<td>承诺期限：</td>
									<td>
										<s:textarea name="matterVo.deadline" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>收费标准：</td>
									<td>
										<s:textarea name="matterVo.fees" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
									<td>收费依据：</td>
									<td>
										<s:textarea name="matterVo.feesBasis" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>办理机构：</td>
									<td>
										<s:textarea name="matterVo.consultingTel" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
									<td>联系人姓名：</td>
									<td>
										<s:textarea name="matterVo.contactName" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>所在部门：</td>
									<td>
										<s:textarea name="matterVo.department" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
									<td>联系电话：</td>
									<td>
										<s:textarea name="matterVo.contactTel" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>监督投诉：</td>
									<td>
										<s:textarea name="matterVo.email" readonly="true" cssStyle="height:50px;width:100%;"/>
									</td>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</table>
						</div>
						<div title="附件" style="padding: 5px;">
							<table class="formtable" cellSpacing="1" cellPadding="3" border="0" width="100%">
								<tr>
									<td>
										<table align="center" border="0" width="100%" height="100%" id="second">
											<tr>
												<td width="100%" height="450" align="center">
													<div style="OVERFLOW-Y:auto;OVERFLOW-X:hidden;border-right: 1px solid; border-top: 1px solid; border-left: 1px solid; border-bottom: 1px solid; width: 100%; height:450;">
														<table width="100%" id="third_1">
														<s:iterator value="matterVo.matterAnnexs" status="matterAnnexStatus" var="matterAnnex">
											    			<tr id="image_upd_tr_<s:property value='#matterAnnexStatus.index+1'/>">
												    			<td>
													   				<table border="1" style="border-collapse: collapse" width="100%">
									  									<tr>
									  										<td align="center" colspan="2"><font color="#0000FF"></font>第 <s:property value='#matterAnnexStatus.index+1'/> 条信息&nbsp;&nbsp;
									  											<input type="hidden" id="matterAnnexId" name="matterAnnexId" value="<s:property value='id'/>"/>
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
									  										<td><textarea name="legend" cols="70" readonly="true" rows="1"><s:property value="%{legend}" default=""/></textarea></td>
									  									</tr>
									  								</table>
								  								</td>
							  								</tr>
						  								</s:iterator>
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
					<s:hidden name="channelId" id="channelId"/>			    
				</div>
			</div>
		</div>
	</body>
</html>