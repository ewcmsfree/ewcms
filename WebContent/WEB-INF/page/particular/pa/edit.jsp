<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>项目文章信息</title>
		<s:include value="../../taglibs.jsp"/>
		<link rel="stylesheet" type="text/css" href="<s:url value='/ewcmssource/page/document/article.css'/>"></link>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_particular.js'/>"></script>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../alertMessage.jsp"/>
	            
	            $('#cc_projectBasic').combogrid({
					panelWidth:850,
					value: '<s:property value="projectArticleVo.projectBasic.code"/>',
					idField:'code',
					textField:'name',
					url:'<s:url namespace="/particular/pa" action="findPbAll"/>',
					columns:[[
		                        {field:'id',title:'编号',hidden:true},
				                {field:'organ_name',title:'发布部门',width:200,
				                	formatter : function(val, rec){
				                		return (rec.organ == null) ? "" : rec.organ.name;
				                	}	
				                },
								{field:'code',title:'项目编号',width:150,sortable:true},
				                {field:'name',title:'项目名称',width:200},
				                {field:'buildTime',title:'建设时间',width:85},
				                {field:'investmentScale',title:'投资规模',width:200},
				                {field:'overview',title:'项目概况',width:300},
				                {field:'buildUnit',title:'建设单位',width:200},
				                {field:'unitId',title:'项目编号',width:60},
				                {field:'unitPhone',title:'单位联系电话',width:100},
				                {field:'unitAddress',title:'单位地址',width:200},
				                {field:'zoningName',title:'行政区划名称',width:80,
				                	formatter : function(val, rec) {
				                		return (rec.zoningCode == null) ? "" : rec.zoningCode.name;
				                	}
				                },
				                {field:'organizationCode',title:'组织机构代码',width:80},
				                {field:'industryName',title:'行业名称',width:80,
				                	formatter : function(val, rec){
				                		return (rec.industryCode == null) ? "" : rec.industryCode.name;
				                	}	
				                },
				                {field:'approvalRecordName',title:'审批备案机关名称',width:120,
				                	formatter : function(val, rec){
				                		return (rec.approvalRecord == null) ? "" : rec.approvalRecord.name;
				                	}	
				                },
				                {field:'contact',title:'联系人',width:120},
				                {field:'phone',title:'联系人电话',width:100},
				                {field:'email',title:'联系人电子邮箱',width:120},
				                {field:'address',title:'项目地址',width:200},
				                {field:'natureDescription',title:'建设性质',width:100},
				                {field:'shape',title:'形式',width:100},
				                {field:'documentId',title:'文号',width:100},
				                {field:'participation',title:'参建单位',width:200}
					]],
					onClickRow : function(rowIndex, rowData){
						$('#projectBasic_code').html(rowData.code);
					},
					onSelect : function(rowIndex, rowData){
						$("#projectBasic_code").html(rowData.code);
					}
				});
	            
	            $('#tt_organ').combotree({
	            	url:"<s:url namespace='/particular' action='tree'/>",
	            	onBeforeSelect: function(node){
	                    if (node.id == null) {
	                   		$.messager.alert('提示','根节点不能选择','info');
	                   		return;
	                   	}
	            	}
	            });
	            $('#tt_organ').combotree($('#organShow').val());
	            $("#tt_organ").combotree("setValue", <s:if test="((projectArticleVo.organ==null) || (projectArticleVo.organ.id==null))">''</s:if><s:else><s:property value="projectArticleVo.organ.id"/></s:else>);

	        	var height = $(window).height() - $("#inputBarTable").height() - 10;
	        	var width = $(window).width() - 30*2;
	        	$("div #_DivContainer").css("height",height + "px");
	        	try{
	        		if (tinyMCE.getInstanceById('_Content_1') != null){
	        			tinyMCE.getInstanceById('_Content_1').theme.resizeTo(width,(height - 110));
	        		}else{
	        			$("#_Content_1").css("width", (width + 2) + "px");
	        			$("#_Content_1").css("height", (height - 42) + "px");
	        		}
	        	}catch(errRes){
	        	}
	       });
        </script>
        <ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body>
		<s:form action="save" namespace="/particular/pa">
			<div id="wrapper" >
			<table id="inputBarTable" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;" class="formtable">
				<tr>
					<td width="10%">项目名称：<span style="color:#FF0000">*</span></td>
					<td width="40%" class="formFieldError">
						<input id="cc_projectBasic" name="projectArticleVo.projectBasic.code" style="width:150px;" size="20" maxlength="100"></input>
						(<span id="projectBasic_code"><s:property value="projectArticleVo.projectBasic.code"/></span>)
						<s:fielderror ><s:param value="%{'projectArticleVo.projectBasic.code'}" /></s:fielderror>
					</td>
					<td width="10%">发布部门：<span style="color:#FF0000">*</span></td>
					<td width="40%" class="formFieldError">
						<select id="tt_organ" name="projectArticleVo.organ.id" style="width: 120px;"></select>
						<s:fielderror ><s:param value="%{'projectArticleVo.organ.id'}" /></s:fielderror>
					</td>
				</tr>
				<tr>
					<td>发布日期：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<ewcms:datepicker id="published" name="projectArticleVo.published" option="inputsimple" format="yyyy-MM-dd"/>
						<s:fielderror ><s:param value="%{'projectArticleVo.published'}" /></s:fielderror>
					</td>
					<td>所属密级：<span style="color:#FF0000">*</span></td>
					<td class="formFieldError">
						<s:select list="@com.ewcms.content.particular.model.Dense@values()" listValue="description" name="projectArticleVo.dense" id="projectArticleVo_dense"></s:select>
						<s:fielderror ><s:param value="%{'projectArticleVo.dense'}" /></s:fielderror>
					</td>
				</tr>
			</table>
			
			<table id="table_content" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;border-collapse:collapse">
				<tr>
					<td valign='top' class="formFieldError">
						<s:fielderror ><s:param value="%{'projectArticleVo.content.detail'}" /></s:fielderror>
						<div id="_DivContainer" style="text-align: center; overflow: auto; height: 476px; width: 100%; background-color: #666666; position: relative">
			  				<table id="_Table1" width="800" border="0" cellpadding="10" bgcolor="#FFFFFF" style="margin: 5px auto;">
			  					<tr>
			  						<td valign="top">
			  							<div id="DivContent">
			  								<table id="tableContent" width="100%" height="100%" cellpadding="0" cellspacing="0">
												<tr id="trContent_1">
													<td>
														<textarea id="_Content_1" class="mceEditor" name="projectArticleVo.content.detail">
														    <s:property value="projectArticleVo.content.detail" escape="false"/>
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
			<s:hidden id="projectArticleId" name="projectArticleVo.id"/>
			<s:hidden id="particularContentId" name="projectArticleVo.content.id"/>
			<s:hidden id="organShow" name="organShow"/>
			<s:hidden id="channelId" name="channelId"/>
           	<s:iterator value="selections" var="id">
               <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
	</body>
</html>