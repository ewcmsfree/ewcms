<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>企业文章信息</title>
		<s:include value="../../taglibs.jsp"/>
		<link rel="stylesheet" type="text/css" href="<s:url value='/ewcmssource/page/document/article.css'/>"></link>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/particular/ea/edit.js"/>'></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_particular.js'/>"></script>
        <script type="text/javascript">
	        enterpriseName = '<s:property value="enterpriseArticleVo.enterpriseBasic.name"  escape="false"/>';
	    	enterpriseUrl = '<s:url namespace="/particular/ea" action="findEbAll"/>';
	    	treeUrl = '<s:url namespace="/particular" action="tree"/>';
	    	organId = <s:if test="((enterpriseArticleVo.organ==null) || (enterpriseArticleVo.organ.id==null))">''</s:if><s:else><s:property value="enterpriseArticleVo.organ.id"/></s:else>;
	        $(function(){
	            <s:include value="../../alertMessage.jsp"/>
		    });
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/ea">
			<div id="wrapper" >
			<table id="inputBarTable" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;" class="formtable">
				<tr>
					<td width="10%">企业名称：<span style="color:#FF0000">*</span></td>
					<td width="40%" class="formFieldError">
						<input id="cc_enterpriseBasic" name="enterpriseArticleVo.enterpriseBasic.yyzzzch" style="width:150px;" maxlength="100"></input>
						(<span id="enterpriseBasic_yyzzch"><s:property value="enterpriseArticleVo.enterpriseBasic.yyzzzch"/></span>)
						<s:fielderror ><s:param value="%{'enterpriseArticleVo.enterpriseBasic.yyzzzch'}" /></s:fielderror>
					</td>
					<td width="10%">发布部门：<span style="color:#FF0000">*</span></td>
					<td width="40" class="formFieldError">
						<select id="tt_organ" name="enterpriseArticleVo.organ.id" style="width: 120px;"></select>
						<s:fielderror ><s:param value="%{'enterpriseArticleVo.organ.id'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>发布日期：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<ewcms:datepicker id="published" name="enterpriseArticleVo.published" option="inputsimple" format="yyyy-MM-dd"/>
						<s:fielderror ><s:param value="%{'enterpriseArticleVo.published'}" /></s:fielderror>
					</td>
					<td>所属密级：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:select list="@com.ewcms.content.particular.model.Dense@values()" listValue="description" name="enterpriseArticleVo.dense" id="enterpriseArticleVo_dense"></s:select>
						<s:fielderror ><s:param value="%{'enterpriseArticleVo.dense'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			
			<table id="table_content" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;border-collapse:collapse">
				<tr>
					<td valign='top' class="formFieldError">
						<s:fielderror ><s:param value="%{'enterpriseArticleVo.content.detail'}" /></s:fielderror>
						<div id="_DivContainer" style="text-align: center; overflow: auto; height: 476px; width: 100%; background-color: #666666; position: relative">
			  				<table id="_Table1" width="800" border="0" cellpadding="10" bgcolor="#FFFFFF" style="margin: 5px auto;">
			  					<tr>
			  						<td valign="top">
			  							<div id="DivContent">
			  								<table id="tableContent" width="100%" height="100%" cellpadding="0" cellspacing="0">
												<tr id="trContent_1">
													<td>
														<textarea id="_Content_1" class="mceEditor" name="enterpriseArticleVo.content.detail">
														    <s:property value="enterpriseArticleVo.content.detail" escape="false"/>
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
			<s:hidden id="enterpriseArticleId" name="enterpriseArticleVo.id"/>
			<s:hidden id="particularContentId" name="enterpriseArticleVo.content.id"/>
			<s:hidden id="organShow" name="organShow"/>
			<s:hidden id="channelId" name="channelId"/>
           	<s:iterator value="selections" var="id">
               <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
		<div style="width:100%;height:16px;position:absolute;text-align:center;height:28px;line-height:28px;background-color:#f6f6f6;bottom:0px;left:0px;">
	    	<a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="document.forms[0].submit();">保存</a>
	    	<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:window.close();">关闭</a>
	    </div>
	</body>
</html>