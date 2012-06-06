<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>从业人员基本数据</title>
		<s:include value="../../taglibs.jsp"/>
		<link rel="stylesheet" type="text/css" href="<s:url value='/ewcmssource/page/document/article.css'/>"></link>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_particular.js'/>"></script>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../alertMessage.jsp"/>
		        $('#tt_organ').combotree('setValue', $('#organId').val());
	            $('#tt_organ').combotree($('#organShow').val());
	            $('#tt_organ').combotree({
	            	onBeforeSelect: function(node){
	                    if (node.id == null) {
	                   		$.messager.alert('提示','根节点不能选择','info');
	                   		return;
	                   	}
	            	}
	            });
		        var height = $(window).height() - $("#inputBarTable").height() - 10;
	        	var width = $(window).width() - 80*2;
	        	$("div #_DivContainer").css("height",height + "px");
	        	try{
	        		if (tinyMCE.getInstanceById('_Content_1') != null){
	        			tinyMCE.getInstanceById('_Content_1').theme.resizeTo(width,(height - 110));
	        		}else{
	        			$("#_Content_1").css("width", (width + 2) + "px");
	        			$("#_Content_1").css("height", (height - 210) + "px");
	        		}
	        	}catch(errRes){
	        	}
	        });
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/mb">
			<table class="formtable" >
				<tr>
					<td width="20%">姓名：<span style="color:#FF0000">*</span></td>
					<td width="80%" class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="employeBasicVo.name" size="20" maxlength="100"/>
						<s:fielderror ><s:param value="%{'employeBasicVo.name'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>性别：</td>
					<td>
						<s:select list="@com.ewcms.content.particular.model.EmployeBasic$Sex@values()" listValue="description" name="employeBasicVo.sex" id="employeBasicVo_sex"></s:select>
					</td>
				</tr>
				<tr>
					<td>发布部门：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<input id="tt_organ" url="<s:url namespace='/particular' action='tree'/>" name="employeBasicVo.organ.id" class="easyui-combotree" style="width: 120px;"></input>
						<s:fielderror ><s:param value="%{'employeBasicVo.organ.id'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>发布时间：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<ewcms:datepicker id="published" name="employeBasicVo.published" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
						<s:fielderror ><s:param value="%{'employeBasicVo.published'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>证件类型：</td>
					<td>
						<s:select list="@com.ewcms.content.particular.model.EmployeBasic$CardType@values()" listValue="description" name="employeBasicVo.cardType" id="employeBasicVo_cardType"></s:select>
					</td>
				</tr>
				<tr>
					<td>证件号码：</td>
					<td class="formFieldError">
						<s:textfield id="cardCode" cssClass="inputtext" name="employeBasicVo.cardCode" size="30" maxlength="100"/>
						<s:fielderror ><s:param value="%{'employeBasicVo.cardCode'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>所属密级：</td>
					<td>
						<s:select list="@com.ewcms.content.particular.model.Dense@values()" listValue="description" name="employeBasicVo.dense" id="employeBasicVo_dense"></s:select>
					</td>
				</tr>
			</table>
			<s:hidden id="employeBasicId" name="employeBasicVo.id"/>
			<s:hidden id="organShow" name="organShow"/>
			<s:hidden id="organId" name="organId"/>
			<s:hidden id="channelId" name="channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>