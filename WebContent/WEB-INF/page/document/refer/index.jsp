<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>

<html>
	<head>
		<title>引用文章</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/easyui/ext/datagrid-detailview.js"/>'></script>
		<script type="text/javascript">
			var channelId = 0;
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL("<s:url namespace='/document/article' action='query'><s:param name='channelId' value='channelId'></s:param></s:url>");

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.addToolItem('新增引用','icon-add',referArticleOperate);
				ewcmsBOBJ.addToolItem('移除引用','icon-remove',moveArticleOperate);

				ewcmsBOBJ.openDataGrid('#tt',{
	                columns:[[
                              {field:'id',title:'编号',width:60},
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
                              {field:'modified',title:'修改时间',width:145,formatter:function(val,rec){return rec.article.modified;}},
                              {field:'sort',title:'排序号',width:60}
	                  ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/document/refer" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>');
			});

			//重载站点专栏目录树
			function channelTreeLoad(){
				$('#tt2').tree('reload');
			}
			
			function referArticleOperate(){
				var url = '<s:url namespace="/document/refer" action="article"/>';
				$("#editifr_pop").attr('src',url);
				openWindow("#pop-window",{width:1000,height:570,top:17,left:40,title:'新增引用文章'});
			}

			function saveArticleOperate(){
				var rows = editifr_pop.$("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择需引用的文章","info");
					return;
				}
				var parameter = 'channelId=' + $('#channelId').val();
	           	for(var i=0;i<rows.length;i++){
	           		parameter = parameter + '&articleMainIds=' + rows[i].id;
	           	}
				$.post('<s:url namespace="/document/refer" action="save"/>', parameter ,function(data) {
					if (data == 'true'){
						$('#tt').datagrid('clearSelections');
						$("#tt").datagrid('reload');
						$("#pop-window").window("close");
					}else if (data == 'false'){
						$.messager.alert('失败','新增引用失败','info');
						return;
					}
				});			
			}
			
			function moveArticleOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择删除的文章","info");
					return;
				}
				var parameter = '';
	           	for(var i=0;i<rows.length;i++){
	           		parameter = parameter + '&articleMainIds=' + rows[i].id;
	           	}
				$.post('<s:url namespace="/document/refer" action="delete"/>', parameter ,function(data) {
					if (data == 'true'){
						$('#tt').datagrid('clearSelections');
						$("#tt").datagrid('reload');
						$("#edit-window").window("close");
					}else if (data == 'false'){
						$.messager.alert('失败','删除文章失败','info');
						return;
					}
				});			
			}
			//有条件查询
			function querySearch_Article(url) {
				$('#tt').datagrid('clearSelections');
				var value = $('#queryform').serialize();
				value = 'parameters[\'' + value;
				value = value.replace(/\=/g, '\']=');
				value = value.replace(/\&/g, '&parameters[\'');

				url += queryURL + '&' + value;
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
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;文档回收站" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
 					<iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                </div>
            </div>
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
		<div id="pop-window" class="easyui-window" title="弹出窗口" icon="icon-save" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="save_span"><a id="saveorok" class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveArticleOperate();return false;">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#pop-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden name="channelId" id="channelId"/>
	</body>
</html>