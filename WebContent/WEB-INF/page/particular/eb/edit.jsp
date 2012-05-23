<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>项目基本数据</title>
		<s:include value="../../taglibs.jsp"/>
		<link rel="stylesheet" type="text/css" href="<s:url value='/ewcmssource/page/document/article.css'/>"></link>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_particular.js'/>"></script>
        <script type="text/javascript">
        $(function(){
            <s:include value="../../alertMessage.jsp"/>
            
            $('#cc_publishingSector').combobox({
        		url: '<s:url namespace="/particular/ps" action="findPsToEb"><s:param name="enterpriseBasicId" value="enterpriseBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
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
		<s:form action="save" namespace="/particular/eb">
			<table class="formtable" >
				<tr>
					<td>企业名称：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:textfield id="name" cssClass="inputtext" name="enterpriseBasicVo.name"/>
						<s:fielderror ><s:param value="%{'enterpriseBasicVo.name'}" /></s:fielderror>
					</td>
					<td>发布部门：<span style="color:#FF0000">*</span></td>
					<td>
						<input id="cc_publishingSector" name="enterpriseBasicVo.publishingSector.code" style="width: 120px;"></input>
					</td>
					<td>发布时间：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<ewcms:datepicker id="published" name="enterpriseBasicVo.published" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
						<s:fielderror ><s:param value="%{'projectBasicVo.published'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>营业执照注册号：</td>
					<td>
						<s:textfield id="yyzzzch" cssClass="inputtext" name="enterpriseBasicVo.yyzzzch"/>
					</td>
					<td>营业执照登记机关：</td>
					<td>
						<s:textfield id="yyzzdjjg" cssClass="inputtext" name="enterpriseBasicVo.yyzzdjjg"/>
					</td>
					<td>法人代表：</td>
					<td>
						<s:textfield id="frdb" cssClass="inputtext" name="enterpriseBasicVo.frdb"/>
					</td>
				</tr>
				<tr>
					<td>成立日期：</td>
					<td>
						<ewcms:datepicker id="clrq" name="enterpriseBasicVo.clrq" option="inputsimple" format="yyyy-MM-dd"/>
					</td>
					<td>经营范围：</td>
					<td>
						<s:textfield id="jyfw" cssClass="inputtext" name="enterpriseBasicVo.jyfw"/>
					</td>
					<td>组织机构登记机关：</td>
					<td>
						<s:textfield id="zzjgdjjg" cssClass="inputtext" name="enterpriseBasicVo.zzjgdjjg"/>
					</td>
				</tr>
				<tr>
					<td>组织机构代码：</td>
					<td>
						<s:textfield id="zzjgdm" cssClass="inputtext" name="enterpriseBasicVo.zzjgdm"/>
					</td>
					<td>企业类型：</td>
					<td>
						<s:textfield id="qyrx" cssClass="inputtext" name="enterpriseBasicVo.qyrx"/>
					</td>
					<td>注册资本：</td>
					<td>
						<s:textfield id="zzzb" cssClass="inputtext" name="enterpriseBasicVo.zzzb"/>
					</td>
				</tr>
				<tr>
					<td>实缴注册资本：</td>
					<td>
						<s:textfield id="sjzzzb" cssClass="inputtext" name="enterpriseBasicVo.sjzzzb"/>
					</td>
					<td>经营期限：</td>
					<td>
						<s:textfield id="jyqx" cssClass="inputtext" name="enterpriseBasicVo.jyqx"/>
					</td>
					<td>住所：</td>
					<td>
						<s:textfield id="zs" cssClass="inputtext" name="enterpriseBasicVo.zs"/>
					</td>
				</tr>
				<tr>
					<td>所属密级：</td>
					<td>
						<s:select list="@com.ewcms.content.particular.model.Dense@values()" listValue="description" name="enterpriseBasicVo.dense" id="enterpriseBasicVo_dense" headerKey="" headerValue="----请选择----"></s:select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>工商年检结果：</td>
					<td colspan="5">
						<table id="table_content" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;border-collapse:collapse">
							<tr>
								<td valign='top'>
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
					</td>
				</tr>
			</table>
			<s:hidden id="enterpriseBasicId" name="enterpriseBasicVo.id"/>
			<s:hidden id="particularContentId" name="enterpriseBasicVo.content.id"/>
			<s:hidden id="channelId" name="channelId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>