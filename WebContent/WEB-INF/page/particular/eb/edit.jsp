<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>企业基本数据</title>
		<s:include value="../../taglibs.jsp"/>
		<link rel="stylesheet" type="text/css" href="<s:url value='/ewcmssource/page/document/article.css'/>"></link>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_particular.js'/>"></script>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../alertMessage.jsp"/>
	            
	            $('#tt_organ').combotree({
	            	 url : "<s:url namespace='/particular' action='tree'/>",
	            	onBeforeSelect: function(node){
	                    if (node.id == null) {
	                   		$.messager.alert('提示','根节点不能选择','info');
	                   		return;
	                   	}
	            	}
	            });
	            $('#tt_organ').combotree($('#organShow').val());
	            $("#tt_organ").combotree("setValue", <s:if test="((enterpriseBasicVo.organ==null) || (enterpriseBasicVo.organ.id==null))">''</s:if><s:else><s:property value="enterpriseBasicVo.organ.id"/></s:else>);
	
	        	var height = $(window).height() - $("#inputBarTable").height() - 38;
	        	var width = $(window).width() - 80*2;
	        	$("div #_DivContainer").css("height",height + "px");
	        	try{
	        		if (tinyMCE.getInstanceById('_Content_1') != null){
	        			tinyMCE.getInstanceById('_Content_1').theme.resizeTo(width,(height - 114));
	        		}else{
	        			$("#_Content_1").css("width", (width + 2) + "px");
	        			$("#_Content_1").css("height", (height - 44) + "px");
	        		}
	        	}catch(errRes){
	        	}
	       });
		   parent.$(window).resize(function(){
		   		var height = $(window).height() - $("#inputBarTable").height() - 38;
		    	var width = $(window).width() - 105*2;
		        $("div #_DivContainer").css("height",height + "px");
		        try{
		        	if (tinyMCE.getInstanceById('_Content_1') != null){
		        		tinyMCE.getInstanceById('_Content_1').theme.resizeTo(width,(height - 135));
		        	}else{
		        		$("#_Content_1").css("width", (width + 2) + "px");
		        		$("#_Content_1").css("height", (height - 142) + "px");
		        	}
		        }catch(errRes){
		        }
		   });
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/eb">
			<div id="wrapper" >
			<table id="inputBarTable" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;" class="formtable">
				<tr>
					<td width="12%">企业名称：<span style="color:#FF0000">*</span></td>
					<td width="22%" class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="enterpriseBasicVo.name" maxlength="100"/>
						<s:fielderror ><s:param value="%{'enterpriseBasicVo.name'}" /></s:fielderror>
					</td>
					<td width="12%">发布部门：<span style="color:#FF0000">*</span></td>
					<td width="21%" class="formFieldError">
						<select id="tt_organ" name="enterpriseBasicVo.organ.id" style="width: 120px;"></select>
						<s:fielderror ><s:param value="%{'enterpriseBasicVo.organ.id'}" /></s:fielderror>
					</td>
					<td width="12%">发布日期：<span style="color:#FF0000">*</span></td>
					<td width="21%" class="formFieldError">
						<ewcms:datepicker id="published" name="enterpriseBasicVo.published" option="inputsimple" format="yyyy-MM-dd"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.published'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>营业执照注册号：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="yyzzzch" cssClass="inputtext" name="enterpriseBasicVo.yyzzzch" maxlength="100"/>
						<s:fielderror ><s:param value="%{'enterpriseBasicVo.yyzzzch'}" /></s:fielderror>
					</td>
					<td>营业执照登记机关：</td>
					<td>
						<s:textfield id="yyzzdjjg" cssClass="inputtext" name="enterpriseBasicVo.yyzzdjjg" maxlength="100"/>
					</td>
					<td>法人代表：</td>
					<td>
						<s:textfield id="frdb" cssClass="inputtext" name="enterpriseBasicVo.frdb" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td>成立日期：</td>
					<td>
						<ewcms:datepicker id="clrq" name="enterpriseBasicVo.clrq" option="inputsimple" format="yyyy-MM-dd"/>
					</td>
					<td>经营范围：</td>
					<td>
						<s:textfield id="jyfw" cssClass="inputtext" name="enterpriseBasicVo.jyfw" maxlength="100"/>
					</td>
					<td>组织机构登记机关：</td>
					<td>
						<s:textfield id="zzjgdjjg" cssClass="inputtext" name="enterpriseBasicVo.zzjgdjjg" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td>组织机构代码：</td>
					<td>
						<s:textfield id="zzjgdm" cssClass="inputtext" name="enterpriseBasicVo.zzjgdm" maxlength="100"/>
					</td>
					<td>企业类型：</td>
					<td>
						<s:textfield id="qyrx" cssClass="inputtext" name="enterpriseBasicVo.qyrx" maxlength="100"/>
					</td>
					<td>注册资本：</td>
					<td>
						<s:textfield id="zzzb" cssClass="inputtext" name="enterpriseBasicVo.zzzb" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td>实缴注册资本：</td>
					<td>
						<s:textfield id="sjzzzb" cssClass="inputtext" name="enterpriseBasicVo.sjzzzb" maxlength="100"/>
					</td>
					<td>经营期限：</td>
					<td>
						<s:textfield id="jyqx" cssClass="inputtext" name="enterpriseBasicVo.jyqx" maxlength="100"/>
					</td>
					<td>住所：</td>
					<td>
						<s:textfield id="zs" cssClass="inputtext" name="enterpriseBasicVo.zs" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td>所属密级：</td>
					<td>
						<s:select list="@com.ewcms.content.particular.model.Dense@values()" listValue="description" name="enterpriseBasicVo.dense" id="enterpriseBasicVo_dense"></s:select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			
			<table id="table_content" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;border-collapse:collapse">
				<tr>
					<td width="12%">工商年检结果：</td>
					<td width="88%" valign='top'>
						<div id="_DivContainer" style="text-align: center; overflow: auto; height: 356px; width: 100%; background-color: #666666; position: relative">
						  	<table id="_Table1" width="800" border="0" cellpadding="10" bgcolor="#FFFFFF" style="margin: 5px auto;">
						  		<tr>
						  			<td valign="top">
						  				<div id="DivContent">
						  					<table id="tableContent" width="100%" height="100%" cellpadding="0" cellspacing="0">
												<tr id="trContent_1">
													<td>
														<textarea id="_Content_1" class="mceEditor" name="enterpriseBasicVo.content.detail">
														    <s:property value="enterpriseBasicVo.content.detail" escape="false"/>
														</textarea>
													</td>
												</tr>
											</table>
						  				</div>
						  			</td>
						  		</tr>
						  	</table>
						</div>
					</td>
				</tr>
			</table>
			</div>
			<s:hidden id="enterpriseBasicId" name="enterpriseBasicVo.id"/>
			<s:hidden id="particularContentId" name="enterpriseBasicVo.content.id"/>
			<s:hidden id="organShow" name="organShow"/>
			<s:hidden id="channelId" name="channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
		<div style="width:100%;height:16px;position:absolute;text-align:center;height:28px;line-height:28px;background-color:#f6f6f6;bottom:0px;left:0px;">
	    	<a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="document.forms[0].submit();">保存</a>
	    </div>
	</body>
</html>