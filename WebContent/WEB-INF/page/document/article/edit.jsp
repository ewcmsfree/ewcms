<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
	<head>
		<title>文档编辑：</title>
		<s:include value="../../taglibs.jsp"/>
		<link rel="stylesheet" type="text/css" href="<s:url value='/ewcmssource/page/document/article.css'/>"></link>
		<script type="text/javascript" src="<s:url value='/ewcmssource/js/jquery.cookies.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/tiny_mce_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config_gzip.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/tiny_mce/config.js'/>"></script>
		<script type="text/javascript" src="<s:url value='/ewcmssource/page/document/edit.js'/>"></script>
	    <script type="text/javascript">
	    	pages = <s:if test="articleVo.contents.size>0"><s:property value="articleVo.contents.size"/></s:if><s:else>0</s:else>;
	    	categoryURL = '<s:url namespace="/document/category" action="findCategoryAll"><s:param name="articleId" value="articleVo.id"></s:param></s:url>';
	    	insertURL = '<s:url action="insert" namespace="/resource"/>';
	    	userName = '<sec:authentication property="name"/>';
	    	voteURL = '<s:url action="article" namespace="/vote/questionnaire"/>';
	    	treeURL = "<s:url namespace='/site/channel' action='tree'/>";
	    	saveURL = "<s:url action='save' namespace='/document/article'/>";
	        $(function(){
	            <s:include value="../../alertMessage.jsp"/>
	        });
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body>
		<s:form id="articleSave" action="save" namespace="/document/article">
			<div id="wrapper" >
				<table id="buttonBarTable" width="100%" border="0" cellpadding="0" cellspacing="0" style="border: #B7D8ED 1px solid;">
					<tr>
						<td>
							<div>
								<table width="100%">
									<tr>
										<td width="100%" style="border: 0px solid;">
											<a class="easyui-linkbutton" iconCls="icon-article-create" href="javascript:void(0)" onclick="createArticle('<s:url action='input' namespace='/document/article'><s:param name='channelId' value='channelId'></s:param></s:url>');return false;">新建</a>
											<a class="easyui-linkbutton" iconCls="icon-article-save" href="javascript:void(0)" onclick="saveArticle();return false;">保存</a>
											<a class="easyui-linkbutton" iconCls="icon-article-review" href="javascript:void(0)" onclick="submitReview('<s:url action='submitReview' namespace='/document/article'/>',<s:property value="channelId"/>,$('#articleMainId').val());return false;">审核</a>
											<a class="easyui-linkbutton" iconCls="icon-article-keyword" href="javascript:void(0)" onclick="getKeywordOrSummary('keyWord','<s:url action="keyword" escapeAmp="false"/>');return false;">提取关键字</a>
											<a class="easyui-linkbutton" iconCls="icon-article-summary" href="javascript:void(0)" onclick="getKeywordOrSummary('summary','<s:url action="summary" escapeAmp="false"/>');return false;">提取摘要</a>
											<a class="easyui-linkbutton" iconCls="icon-article-history" href="javascript:void(0)" onclick="selectHistory('<s:url action='selectId' namespace='/document/history'><s:param name='articleId' value='articleVo.id'></s:param></s:url>');return false;">历史</a>
											<a class="easyui-linkbutton" iconCls="icon-article-relation" href="javascript:void(0)" onclick="selectRelation('<s:url action='relation' namespace='/document/relation'><s:param name='articleId' value='articleVo.id'></s:param></s:url>');return false;">相关文章</a>
											<a class="easyui-linkbutton" iconCls="icon-article-cookies" href="javascript:void(0)" onclick="ewcmsCookies();return false;">常用项</a>
											<a class="easyui-linkbutton" iconCls="icon-article-show" href="javascript:void(0)" onclick="showHide();return false;"><s:label id="showHideLabel" value="展开"></s:label>&nbsp;</a>
											<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeArticle();return false;">关闭</a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table id="inputBarTable" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;" class="formtable" >
			    	<tr >
			        	<td width="6%">标题：</td>
			        	<td width="44%" id="tdTitle" class="formFieldError">
			        		<s:textfield id="articleTitle" name="articleVo.title" cssClass="inputtext" cssStyle="width:320px;background:url(../../ewcmssource/image/article/rule.gif) repeat-x left bottom;" maxlength="50"/>
			        		<s:fielderror><s:param value="%{'articleVo.title'}" /></s:fielderror>
						</td>
						<td width="6%"></td>
						<td width="44%" style="vertical-align: middle;">
							<table width="100%" style="border: 0px solid;">
			        			<tr>
				        			<td width="100%" style="border: 0px solid;">
				        				<input type="checkbox" value="checkbox" id="ShowShortTitle" onclick="$('#trShortTitle').toggle()" style="vertical-align: middle;"/><label for="ShowShortTitle">&nbsp;短标题</label>&nbsp;&nbsp;
					           	 		<input type="checkbox" value="checkbox" id="ShowSubTitle" onclick="$('#trSubTitle').toggle()" style="vertical-align: middle;"/><label for="ShowSubTitle">&nbsp;副标题</label>&nbsp;&nbsp;&nbsp;&nbsp;
					           	 		<!--<s:checkbox id="inside" name="articleVo.inside" cssStyle="vertical-align: middle;"/><label for="inside">&nbsp;使用内部标题</label>-->
					           	 	</td>
				           	 	</tr>
				           	 </table>
			        	</td>
			        </tr>
			        <tr id="trShortTitle" style="display:none;">
			        	<td>短标题：</td>
			        	<td id="tdShortTitle" colspan="3" class="formFieldError">
			        		<s:textfield id="articleShortTitle" name="articleVo.shortTitle" cssClass="inputtext" cssStyle="width:300px;background:url(../../ewcmssource/image/article/rule.gif) repeat-x left bottom;" maxlength="25"></s:textfield>
			        		<s:fielderror><s:param value="%{'articleVo.shortTitle'}" /></s:fielderror>
			        	</td>
			        </tr>
			        <tr id="trSubTitle" style="display:none;">
			        	<td>副标题：</td>
			        	<td id="tdSubTitle" colspan="3" class="formFieldError">
			        		<s:textfield id="articleSubTitle" name="articleVo.subTitle" cssClass="inputtext" cssStyle="width:320px;background:url(../../ewcmssource/image/article/rule.gif) repeat-x left bottom;" maxlength="50"></s:textfield>
			        		<s:fielderror><s:param value="%{'articleVo.subTitle'}" /></s:fielderror>
			        	</td>
			        </tr>
			        <tr>
			        	<td width="6%">内部标题：</td>
			        	<td width="44%">
			        		<s:checkbox id="inside" name="articleVo.inside" cssStyle="vertical-align: middle;"/><label for="inside">&nbsp;&nbsp;</label>
			        	</td>
			        	<td width="6%">频道选择：</td>
			        	<td width="44%">
			        		<select id="cc_channel" style="width:200px;" required="true"></select>
			        	</td>
			        </tr>
			        <tr id="trShowHide_1" style="display:none">
			        	<td width="6%">发布日期：</td>
			        	<td width="44%">
			        		<ewcms:datepicker id="published" name="published" option="inputsimple" format="yyyy-MM-dd HH:mm"/>
			        	</td>
			        	<td width="6%">责任编辑：</td>
			        	<td width="44%">
			        		<s:textfield name="articleVo.author" size="30"></s:textfield>
			        	</td>
			        </tr>
			        <tr id="trShowHide_2" style="display:none">
			        	<td>文章类型：</td>
			        	<td>
			        		<s:select list="@com.ewcms.content.document.model.Article$Type@values()" listValue="description" name="articleVo.type" id="articleVo_type" onchange="changeType()"></s:select>
			        	</td>
			        	<td>来源：</td>
			        	<td>
			        		<s:textfield name="articleVo.origin" size="60"></s:textfield>
			        	</td>
			        </tr>
			        <tr id="trShowHide_3" style="display:none">
			        	<td>关键字：</td>
			        	<td>
			        		<s:textfield id="keyword" name="articleVo.keyword" size="60"></s:textfield>
			        	</td>
			        	<td>Tag：</td>
			        	<td>
			        		<s:textfield name="articleVo.tag" size="60"></s:textfield>
			        	</td>
			        </tr>
			        <tr id="trShowHide_4" style="display:none">
			        	<td style="height:30px;vertical-align: middle;">文章选项：</td>
			        	<td>
			        		<s:checkbox id="comment" name="articleVo.comment" cssStyle="vertical-align: middle;"/><label for="comment">允许评论</label>
			        	</td>
			        	<td style="height:30px;vertical-align: middle;">分类属性：</td>
			        	<td>
			        		<input id="cc_categories" name="categories" style="width:200px;"></input>
			        	</td>
			        </tr>
			        <tr id="trShowHide_5" style="display:none">
			        	<td>引用图片：</td>
			        	<td>
			        		<a href="javascript:void(0);" onclick="openRefenceImageWindow();return false;" style="text-decoration:none;">
			        		<s:if test="articleVo.image!=null&&articleVo.image!=''">
			        			<img id="referenceImage" name="referenceImage" width="120px" height="90px" src="../..<s:property value="articleVo.image"/>"/>
			        		</s:if>
			        		<s:else>
			        			<img id="referenceImage" name="referenceImage" width="120px" height="90px" src="<s:url value='/ewcmssource/image/article/nopicture.jpg'/>"/>
			        		</s:else>
			        		</a>
			        		<s:textfield id="article_image" name="articleVo.image" cssStyle="display:none;"/>
			        		<a class="easyui-linkbutton" href="javascript:void(0)" onclick="clearImage();return false;" style="vertical-align:bottom;">清除图片</a>
			        		<!-- <input type="button" value="清除图片" style="vertical-align:bottom;" onclick="clearImage();return false;"/> -->
			        	</td>
			        	<td>摘要：</td>
			        	<td>
			        		<s:textarea id="summary" name="articleVo.summary" cols="42"></s:textarea>
			        	</td>
			        </tr>
			        <tr id="tr_url" style="display:none">
						<td>链接地址：</td>
						<td colspan="3"><s:textfield id="url" name="articleVo.url" size="120"></s:textfield></td>
					</tr>
			    </table>
			    
			    <s:if test="articleVo.contents.size>0">
				<table id="table_content" width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F6F9FD" style="border: #B7D8ED 1px solid;border-collapse:collapse">
					<tr>
						<td valign='top'>
							<div id="_DivContainer" style="text-align: center; overflow: auto; height: 466px; width: 100%; background-color: #666666; position: relative">
			  					<table id="_Table1" width="1000" border="0" cellpadding="10" bgcolor="#FFFFFF" style="margin: 5px auto;">
			  						<tr>
			  							<td valign="top">
			  								<div id="DivContent" align="center">
			  									<table id="tableContent" width="100%" height="100%" cellpadding="0" cellspacing="0">
													<s:iterator value="articleVo.contents" status="contentsStatus" >
														<s:if test="#contentsStatus.index==0">
															<tr id="trContent_<s:property value='#contentsStatus.index+1'/>">
														</s:if>
														<s:else>
															<tr id="trContent_<s:property value='#contentsStatus.index+1'/>" style="display:none">
														</s:else>
														<td>
															<textarea id="_Content_<s:property value='#contentsStatus.index+1'/>" class="mceEditor">
															    <s:property value="detail" escape="false"/>
															</textarea>
															<input type="hidden" id="textAreaContent_<s:property value='#contentsStatus.index+1'/>" name="textAreaContent"/>
														</td>
													</tr>
													</s:iterator>			              						
												</table>
			  								</div>
			  							</td>
			  						</tr>
			  					</table>
			  				</div>
			  			</td>
					</tr>
				</table>
				</s:if>
			</div>
			<div id="pageBarDiv" style="padding-right: 230px;">
				<table width="100%" id="pageBarTable_general">
					<tr>
						<td id="td_pageBar" valign="middle" bgcolor="#F7F8FD" class="pagetab" height="20" width="80%">
							<ul id="pageList">
								<s:if test="articleVo.contents.size>0">
									<li onclick="changePage('p1')" onmouseover="onOverPage('p1')" onmouseout="onOutPage('p1')" class="current" id="p1" name="tabs"><b>页 1</b></li>
								</s:if>
							</ul>
							<span class="add">
								<s:a onclick="addPage();" href="#;" alt="在当前页后插入">
									<img src="<s:url value='/ewcmssource/image/article/icon_plus.gif'/>"	border="0" />
								</s:a>
							</span>
							<span class="add">
								<s:a onclick="delPage();" href="#;" alt="删除当前页">
									<img src="<s:url value='/ewcmssource/image/article/icon_minus.gif'/>" border="0" />
								</s:a>
							</span>
						</td>
						<td width="20%" height="20" valign="middle" align="right" bgcolor="#F7F8FD" class="pagetab" style="vertical-align: middle;">最后保存时间:
							<span id="saveTime_general"><s:if test="modified!=null"><s:date name="articleVo.modified" format="yyyy-MM-dd HH:mm:ss" /></s:if></span>
						</td>
					</tr>
				</table>
				<table width="100%" id="pageBarTable_title" style="display:none">
					<tr>
						<td width="100%" height="20" valign="middle" bgcolor="#F7F8FD" class="pagetab" align="left" style="">最后保存时间:
							<span id="saveTime_title"><s:if test="modified!=null"><s:date name="articleVo.modified" format="yyyy-MM-dd HH:mm:ss" /></s:if></span>
						</td>
					</tr>
				</table>
			</div>
			<s:hidden id="state" name="state"/>
			<s:hidden id="channelId" name="channelId"/>
			<s:hidden id="articleId" name="articleVo.id"/>
			<s:hidden id="articleMainId" name="articleMainId"/>
            <s:iterator value="selections" var="id">
                <s:hidden name="selections" value="%{id}"/>
            </s:iterator>			
		</s:form>
		<div id="pop-window" class="easyui-window" title="弹出窗口" icon="icon-save" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="selectHistory_span"><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="selectHistoryOperator('<s:url action='history' namespace='/document/article'/>');return false;">选择</a></span>
                    <span id="save_span"><a id="saveorok" class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="$('#pop-window').window('close');return false;">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#pop-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
		<div id="insert-window" class="easyui-window" closed="true" icon="icon-save" title="插入" style="display:none;">
		    <s:hidden id="refence_img"/>
            <div class="easyui-layout" fit="true">
            	<div region="center" border="false">
             		<iframe src="" id="uploadifr_insert_id"  name="uploadifr_insert" class="editifr" scrolling="no"></iframe>
             	</div>
                <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;padding:3px 6px;">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="insertFileToTinyMCEOperator();">插入</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#insert-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>	
		<div id="vote-window" class="easyui-window" title="问卷调查" icon="icon-save" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_vote"  name="editifr_vote" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="insertVote();return false;">选择</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#vote-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
        <div id="ewcms-cookies" class="easyui-window" closed="true" style="display:none;overflow:hidden;" icon="icon-cookies">
        	<div class="easyui-layout" fit="true" >
	        	<div region="center" border="false">
		        	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border: #B7D8ED 1px solid;">
		        		<tr align="center">
		        			<td>可以在这里设置常用项，选择后可保存2个星期</td>
		        		</tr>
		        		<tr>
		        			<td><input type="checkbox" value="checkbox" id="ewcms_1" onclick="ewcmsCookiesSet(this,'trShowHide_1');" style="vertical-align: middle;"/><label for="ewcms_1">&nbsp;第二行显示——<font color='red'>【发布日期、作者、审核人】</font></label></td>
		        		</tr>
		        		<tr>
		        			<td><input type="checkbox" value="checkbox" id="ewcms_2" onclick="ewcmsCookiesSet(this,'trShowHide_2');" style="vertical-align: middle;"/><label for="ewcms_2">&nbsp;第三行显示——<font color='red'>【文章类型、来源】</font></label></td>
		        		</tr>
		        		<tr>
		        			<td><input type="checkbox" value="checkbox" id="ewcms_3" onclick="ewcmsCookiesSet(this,'trShowHide_3');" style="vertical-align: middle;"/><label for="ewcms_3">&nbsp;第四行显示——<font color='red'>【关键了、Tag】</font></label></td>
		        		</tr>
		        		<tr>
		        			<td><input type="checkbox" value="checkbox" id="ewcms_4" onclick="ewcmsCookiesSet(this,'trShowHide_4');" style="vertical-align: middle;"/><label for="ewcms_4">&nbsp;第四行显示——<font color='red'>【文章选项、分类属性】</font></label></td>
		        		</tr>
		        		<tr>
		        			<td><input type="checkbox" value="checkbox" id="ewcms_5" onclick="ewcmsCookiesSet(this,'trShowHide_5');" style="vertical-align: middle;"/><label for="ewcms_5">&nbsp;第五行显示——<font color='red'>【引用图片、摘要】</font></label></td>
		        		</tr>
		        		<tr>
		        			<td>&nbsp;</td>
		        		</tr>
		        	</table>
	        	</div>
                 <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="$('#ewcms-cookies').window('close');return false;">关闭</a>
                </div>
        	</div>
        </div>
	</body>
</html>