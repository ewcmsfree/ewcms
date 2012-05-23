<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>项目基本数据</title>
		<s:include value="../../taglibs.jsp"/>
        <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
            
            $('#cc_industryCode').combobox({
        		url: '<s:url namespace="/particular/ic" action="findIndustryCodeAll"><s:param name="projectBasicId" value="projectBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
        	});
            
            $('#cc_zoningCode').combobox({
        		url: '<s:url namespace="/particular/zc" action="findZoningCodeAll"><s:param name="projectBasicId" value="projectBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
            });
            
            $('#cc_approvalRecordCode').combobox({
        		url: '<s:url namespace="/particular/ar" action="findApprovalRecordAll"><s:param name="projectBasicId" value="projectBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
            });
            
            $('#cc_publishingSector').combobox({
        		url: '<s:url namespace="/particular/ps" action="findPsToPb"><s:param name="projectBasicId" value="projectBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
            });
        });
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/pb">
			<table class="formtable" >
				<tr>
					<td colspan="6"><b>项目信息</b></td>
				</tr>
				<tr>
					<td>项目名称：<span style="color:#FF0000">*</span></td>
					<td colspan="5" class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="projectBasicVo.name" size="60"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.name'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					
					<td>投资规模：</td>
					<td>
						<s:textfield id="investmentScale" cssClass="inputtext" name="projectBasicVo.investmentScale"/>
					</td>
					<td>项目概况：</td>
					<td>
						<s:textfield id="overview" cssClass="inputtext" name="projectBasicVo.overview"/>
					</td>
					<td>项目类别：</td>
					<td><s:textfield id="category" cssClass="inputtext" name="projectBasicVo.category"/></td>
				</tr>
				<tr>
					<td width="14%">项目地址：<span style="color:#FF0000">*</span></td>
					<td width="20%" class="formFieldError">
						<s:textfield id="address" cssClass="inputtext" name="projectBasicVo.address"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.address'}" /></s:fielderror>
					</td>
					<td width="10%">建设性质：<span style="color:#FF0000">*</span></td>
					<td width="23%" class="formFieldError">
						<s:select list="@com.ewcms.content.particular.model.ProjectBasic$Nature@values()" listValue="description" name="projectBasicVo.bildNature" id="projectBasicVo_bildNature"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.bildNature'}" /></s:fielderror>
					</td>
					<td width="10%">行业编码：<span style="color:#FF0000">*</span></td>
					<td width="23%" class="formFieldError">
						<input id="cc_industryCode" name="projectBasicVo.industryCode.code" style="width:120px;"></input>
						<s:fielderror><s:param value="%{'projectBasicVo.industryCode.code'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>建设时间：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<ewcms:datepicker id="buildTime" name="projectBasicVo.buildTime" option="inputsimple" format="yyyy-MM-dd"></ewcms:datepicker>
						<s:fielderror ><s:param value="%{'projectBasicVo.buildTime'}" /></s:fielderror>
					</td>
					<td>行政区划代码：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<input id="cc_zoningCode" name="projectBasicVo.zoningCode.code" style="width: 120px;"></input>
						<s:fielderror ><s:param value="%{'projectBasicVo.zoningCode.code'}" /></s:fielderror>
					</td>
					<td>单位项目编号：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="unitId" cssClass="inputtext" name="projectBasicVo.unitId"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.unitId'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td colspan="6"><b>建设单位信息</b></td>
				</tr>
				<tr>
					<td>单位名称：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="buildUnit" cssClass="inputtext" name="projectBasicVo.buildUnit"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.buildUnit'}" /></s:fielderror>
					</td>
					<td>组织机构代码：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="organizationCode" cssClass="inputtext" name="projectBasicVo.organizationCode"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.organizationCode'}" /></s:fielderror>
					</td>
					<td>单位联系电话：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="unitPhone" cssClass="inputtext" name="projectBasicVo.unitPhone"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.unitPhone'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>联系人：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="contact" cssClass="inputtext" name="projectBasicVo.contact"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.contact'}" /></s:fielderror>
					</td>
					<td>联系人电话：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="phone" cssClass="inputtext" name="projectBasicVo.phone"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.phone'}" /></s:fielderror>
					</td>
					<td>联系人邮箱：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="email" cssClass="inputtext" name="projectBasicVo.email"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.email'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>审批备案机关编号：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<input id="cc_approvalRecordCode" name="projectBasicVo.approvalRecord.code" style="width: 120px;"></input>
						<s:fielderror ><s:param value="%{'projectBasicVo.approvalRecord.code'}" /></s:fielderror>
					</td>
					<td>单位地址：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="unitAddress" cssClass="inputtext" name="projectBasicVo.unitAddress"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.unitAddress'}" /></s:fielderror>
					</td>
					<td>形式：</td>
					<td>
						<s:textfield id="shape" cssClass="inputtext" name="projectBasicVo.shape"/>
					</td>
				</tr>
				<tr>
					<td>文号：</td>
					<td>
						<s:textfield id="documentId" cssClass="inputtext" name="projectBasicVo.documentId"/>
					</td>
					<td>参建单位：</td>
					<td>
						<s:textfield id="participation" cssClass="inputtext" name="projectBasicVo.participation"/>
					</td>
					<td>发布部门：</td>
					<td>
						<input id="cc_publishingSector" name="projectBasicVo.publishingSector.code" style="width: 120px;"></input>
					</td>
				</tr>
			</table>
			<p></p>
			<table class="formtable"  cellpadding="0" cellspacing="1">
				<tr>
					<td>填表说明：</td>
					<td>
						<table class="formtable">
							<tr>
								<td>1.&nbsp;单位名称和项目名称：要求必须与文件中名称一致，要填写单位或项目的全称，不要简称。</td>
							</tr>
							<tr>
								<td>2.&nbsp;项目地址：要求填写项目建设所在地址 ×× 市 ×× 县（区）。</td>
							</tr>
							<tr>
								<td>3.&nbsp;行业编码：填写建设项目所属的国民经济行业种类，按《国民经济行业分类及代码》(GB/T 4754-2002)中规定的分类标准最后一级分类即小类填写。</td>
							</tr>
							<tr>
								<td>4.&nbsp;建设性质:
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1) 新建；
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2) 扩建；
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(3) 改建和技术改造；
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(4) 单纯建造生活设施：指在不扩建、改建生产性工程和业务用房的情况下，单纯建造职工住宅、托儿所、子弟学校、医务室、浴室、食堂等生活福利设施的项目；
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(5) 迁建：指为改变生产力布局或由于城市环保和生产的需要等原因而搬迁到另地建设的项目。在搬迁另地建设过程中，不论是维持原来规模还是扩大规模都按迁建统计；
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(6) 恢复：指因自然灾害、战争等原因使原有固定资产全部或部分报废以后又投资恢复建设的项目。不论是按原规模恢复还是在恢复的同时进行扩建的都按恢复项目统计；
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(7) 单纯购置：指现有企业、事业、行政单位单纯购置不需要安装的设备、工具、器具而不进行工程建设的项目；
									<br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(8) 其它。
								</td>
							</tr>
							<tr>
								<td>5.&nbsp;报送要求：此电子表作为审批条件之一，务必报送审批部门，审批部门对此表进行审核。</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<s:hidden id="projectBasicId" name="projectBasicVo.id"/>
			<s:hidden id="code" name="projectBasicVo.code"/>
			<s:hidden id="channelId" name="channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>