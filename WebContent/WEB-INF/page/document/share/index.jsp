<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>文章共享库</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/document/category/index.js"/>'></script>
		
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL("<s:url namespace='/document/share' action='query'/>");
	
				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.addToolItem('引用','icon-refence',refenceOperate);
				ewcmsBOBJ.addToolItem('复制','icon-copy',copyOperate);
				ewcmsBOBJ.addToolItem('预览', 'icon-article-preview', previewOperate, 'btnPreview')
	
				ewcmsBOBJ.openDataGrid('#tt',{
	                columns:[[
	                          {field:'id',hidden:true},
	                          {field:'articleId',title:'编号',width:60,
                            	  formatter:function(val,rec){
                            		  return rec.article.id;
                            	  }  
							  },
	                          {field:'flags',title:'属性',width:60,
	                              formatter:function(val,rec){
	                                  var pro = [];
	                                  if (rec.top) pro.push("<img src='../../ewcmssource/image/article/top.gif' width='13px' height='13px' title='有效期限:永久置顶'/>"); 
	                                  if (rec.article.comment) pro.push("<img src='../../ewcmssource/image/article/comment.gif' width='13px' height='13px' title='允许评论'/>");
	                                  if (rec.article.type=="TITLE") pro.push("<img src='../../ewcmssource/image/article/title.gif' width='13px' height='13px' title='标题新闻'/>");
	                                  if (rec.reference) pro.push("<img src='../../ewcmssource/image/article/reference.gif' width='13px' height='13px' title='引用新闻'/>");
	                                  if (rec.article.inside) pro.push("<img src='../../ewcmssource/image/article/inside.gif' width='13px' height='13px' title='内部标题'/>");
	                                  if (rec.share) pro.push("<img src='../../ewcmssource/image/article/share.gif' width='13px' height='13px' title='共享' style='border:0'/>");
	                                  return pro.join("");
	                              }
	                          },
	                          {field:'title',title:'标题<span style=\"color:red;\">[分类]</span>',width:500,
	                              formatter:function(val,rec){
	                                  var classPro = [];
	                                  var categories = rec.article.categories;
	                                  for (var i=0;i<categories.length;i++){
	                                     classPro.push(categories[i].categoryName);
	                                  }
	                                  var classValue = "";
	                                  if (classPro.length > 0){
	                                      classValue = "<span style='color:red;'>[" + classPro.join(",") + "]</span>";
	                                  }
	                                  return rec.article.title + classValue;
	                              }
	                          },
	                          {field:'owner',title:'创建者',width:80,formatter:function(val,rec){return rec.article.owner;}},
	              			  {field : 'statusDescription',title : '状态',width : 120,
	              				  formatter : function(val, rec) {
	              					  var processName = "";
	              					  if (rec.article.status == 'REVIEW' && rec.article.reviewProcess != null){
	              						  processName = "(" + rec.article.reviewProcess.name + ")";
	              					  }
	              					  return rec.article.statusDescription + processName;
	              				  }
	              			  }, 
	                          {field:'published',title:'发布时间',width:145,formatter:function(val,rec){return rec.article.published;}},
	                          {field:'modified',title:'修改时间',width:145,formatter:function(val,rec){return rec.article.modified;}}
	                  ]]
				});
	
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			});
			
			function copyOperate() {
				$('#tt3').tree( {
					checkbox : true,
					url : "<s:url namespace='/site/channel' action='tree'/>"
				});
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择复制记录', 'info');
					return;
				}
				ewcmsBOBJ.openWindow('#copy-window', {
					width : 300,
					height : 400,
					title : '复制文章选择'
				});
			}
			function refenceOperate(){
				$('#tt2').tree( {
					checkbox : true,
					url : "<s:url namespace='/site/channel' action='tree'/>"
				});
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择引用记录', 'info');
					return;
				}
				ewcmsBOBJ.openWindow('#refence-window', {
					width : 300,
					height : 400,
					title : '引用文章选择'
				});
			}
			function copyArticle(url) {
				var checkeds = $('#tt3').tree('getChecked')
				if (checkeds.length == 0) {
					$.messager.alert('提示', '请选择复制到目标的栏目', 'info');
					return;
				}
				var rootnode_tt3 = $('#tt3').tree('getRoot');

				var parameter = '';
				var rows = $('#tt').datagrid('getSelections');
				for ( var i = 0; i < rows.length; i++) {
					parameter = parameter + '&selections=' + rows[i].id;
				}

				for ( var i = 0; i < checkeds.length; i++) {
					if (checkeds[i].id != rootnode_tt3.id) {
						parameter = parameter + '&selectChannelIds=' + checkeds[i].id;
					}
				}
				$.post(url, parameter, function(data) {
					if (data == 'true') {
						$.messager.alert('成功', '复制文章成功', 'info');
						$('#copy-window').window('close');
					}else{
						$.messager.alert('失败','复制文章失败','error');
						return;
					}
				});
			}
			function refenceArticle(url) {
				var checkeds = $('#tt2').tree('getChecked')
				if (checkeds.length == 0) {
					$.messager.alert('提示', '请选择引用到目标的栏目', 'info');
					return;
				}
				var rootnode_tt2 = $('#tt2').tree('getRoot');

				var parameter = '';
				var rows = $('#tt').datagrid('getSelections');
				for ( var i = 0; i < rows.length; i++) {
					parameter = parameter + '&selections=' + rows[i].id;
				}

				for ( var i = 0; i < checkeds.length; i++) {
					if (checkeds[i].id != rootnode_tt2.id) {
						parameter = parameter + '&selectChannelIds=' + checkeds[i].id;
					}
				}
				$.post(url, parameter, function(data) {
					if (data == 'true') {
						$.messager.alert('成功', '引用文章成功', 'info');
						$('#copy-window').window('close');
					}else{
						$.messager.alert('失败','引用文章失败','error');
						return;
					}
				});
			}
			function previewOperate(){
				var rows = $('#tt').datagrid('getSelections');
				if (rows.length == 0) {
					$.messager.alert('提示', '请选择预览记录', 'info');
					return;
				}
				if (rows.length > 1) {
					$.messager.alert('提示', '只能选择一个预览', 'info');
					return;
				}
				var previewURL = "/template/preview?channelId=" + rows[0].channelId;
				window.open(previewURL + '&articleId=' + rows[0].article.id,'popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,scrollbars=yes,left=' + (window.screen.width - 1280)/ 2 + ',top=' + (window.screen.height - 700) / 2);
			}
			function querySearch_Article() {
				$('#tt').datagrid('clearSelections');
				var value = $('#queryform').serialize();
				value = 'parameters[\'' + value;
				value = value.replace(/\=/g, '\']=');
				value = value.replace(/\&/g, '&parameters[\'');

				var url = "<s:url namespace='/document/share' action='query'/>" + '?' + value;
				$('#tt').datagrid( {
					pageNumber : 1,
					url : url
				});
				$('#query-window').window('close');
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
                <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                        <tr>
                            <td class="tdtitle">编号：</td>
                            <td class="tdinput">
                                <input type="text" id="id" name="id" class="inputtext"/>
                            </td>
                            <td class="tdtitle">标题：</td>
                            <td class="tdinput">
                                <input type="text" id="title" name="title" class="inputtext"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtitle">发布时间：</td>
                            <td class="tdinput" colspan="3">
                                <ewcms:datepicker id="publishedStart" name="publishedStart" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                                &nbsp;至&nbsp;
                                <ewcms:datepicker id="publishedEnd" name="publishedEnd" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtitle">修改时间：</td>
                            <td class="tdinput" colspan="3">
                                <ewcms:datepicker id="modifiedStart" name="modifiedStart" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                                &nbsp;至&nbsp;
                                <ewcms:datepicker id="modifiedEnd" name="modifiedEnd" option="inputsimple" format="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtitle">状态：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.content.document.model.Article$Status@values()" listValue="description" name="articleStatus" id="articleStatus" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                            <td class="tdtitle">类型：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.content.document.model.Article$Type@values()" listValue="description" name="articleType" id="articleType" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                        </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch_Article();">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="copy-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul id="tt3"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="span_copy"><a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="copyArticle('<s:url namespace='/document/share' action='copy'/>');">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#copy-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="refence-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul id="tt2"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="span_copy"><a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="refenceArticle('<s:url namespace='/document/share' action='refence'/>');">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#refence-window').window('close');">取消</a>
                </div>
            </div>
        </div>
	</body>
</html>