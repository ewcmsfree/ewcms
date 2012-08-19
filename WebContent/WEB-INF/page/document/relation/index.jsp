<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>相关文章</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/document/relation" action="query"><s:param name="articleId" value="articleId"></s:param></s:url>');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.delToolItem('查询');
				ewcmsBOBJ.delToolItem('缺省查询');

				ewcmsBOBJ.addToolItem('添加','icon-add', addOperate);
				ewcmsBOBJ.addToolItem('移除','icon-remove',delOperate);
				ewcmsBOBJ.addToolItem('上移','icon-up',upOperate);
				ewcmsBOBJ.addToolItem('下移','icon-down',downOperate);
				ewcmsBOBJ.addToolItem('缺省查询','icon-back',defQueryCallBack);
				
				ewcmsBOBJ.openDataGrid('#tt',{
	                columns:[[
                              {field:'id',title:'编号',width:60},
                              {field:'flags',title:'属性',width:60,
                                  formatter:function(val,rec){
                                      var pro = [];
                                      if (rec.comment) pro.push("<img src='../../ewcmssource/image/article/comment.gif' width='13px' height='13px' title='允许评论'/>");
                                      if (rec.type=="TITLE") pro.push("<img src='../../ewcmssource/image/article/title.gif' width='13px' height='13px' title='标题新闻'/>");
                                      if (rec.reference) pro.push("<img src='../../ewcmssource/image/article/reference.gif' width='13px' height='13px' title='引用新闻'/>");
                                      if (rec.inside) pro.push("<img src='../../ewcmssource/image/article/inside.gif' width='13px' height='13px' title='内部标题'/>");
                                      if (rec.share) pro.push("<img src='../../ewcmssource/image/article/share.gif' width='13px' height='13px' title='共享' style='border:0'/>");
                                      return pro.join("");
                                  }
                              },
                              {field:'title',title:'标题<span style=\"color:red;\">[分类]</span>',width:500,
                                  formatter:function(val,rec){
                                      var classPro = [];
                                      var categories = rec.categories;
                                      for (var i=0;i<categories.length;i++){
                                         classPro.push(categories[i].categoryName);
                                      }
                                      var classValue = "";
                                      if (classPro.length > 0){
                                          classValue = "<span style='color:red;'>[" + classPro.join(",") + "]</span>";
                                      }
                                      return rec.title + classValue;
                                  }
                              },
                              {field:'statusDescription',title:'状态',width:60},
                              {field:'published',title:'发布时间',width:145},
                              {field:'modified',title:'修改时间',width:145},
	                  ]]
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				//ewcmsOOBJ.setInputURL('<s:url namespace="/document/relation" action="input"/>');
				//ewcmsOOBJ.setDeleteURL('<s:url namespace="/document/relation" action="delete"/>');
			});
			function addOperate(){
				$("#editifr_article").attr("src","<s:url namespace='/document/relation' action='article'/>");
				ewcmsBOBJ.openWindow("#edit-window",{width:600,height:400,title:'文章选择'});
			}
			
			function delOperate(){
				var rows = $("#tt").datagrid("getSelections");
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择修改记录','info');
	                return;
	            }
	            var parameter = {};
			    var url_param = '?articleId=' + $("#articleId").attr("value");
	           	for(var i=0;i<rows.length;i++){
	           		url_param += '&selectIds=' + rows[i].id;
	           	}
	           	url_param = '<s:url namespace="/document/relation" action="delete"/>' + url_param + '';
	            $.post(url_param,parameter,function(data){
		            $.messager.alert('成功','删除成功');
		            $("#tt").datagrid('clearSelections');
		            $("#tt").datagrid('reload');
		            return;
	            });
	            return false;	    	
			}

			function upOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择移动记录","info");
					return;
				}
				if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录进行移动","info");
					return;
				}
				$.post('<s:url namespace="/document/relation" action="up"/>', {'articleId':$("#articleId").val(),'selectIds':rows[0].id}, function(data){
					if (data == "false"){
						$.messager.alert("提示","上移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
					return;
				});
                return false;           
			}

			function downOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择移动记录","info");
					return;
				}
				if (rows.length > 1){
					$.messager.alert("提示","只能选择一个记录进行移动","info");
					return;
				}
				$.post('<s:url namespace="/document/relation" action="down"/>', {'articleId':$("#articleId").val(),'selectIds':rows[0].id}, function(data){
					if (data == "false"){
						$.messager.alert("提示","下移失败","info");
						return;
					}
					$("#tt").datagrid('reload');
					return;
				});
                return false;           
			}
			
			function saveRelation(){
				var rows = editifr_article.getRelationRows();
			    var url_param = '?articleId=' + $("#articleId").attr("value");
	           	for(var i=0;i<rows.length;i++){
	           		url_param += '&selectIds=' + rows[i].article.id;
	           	}
	           	url_param = '<s:url namespace="/document/relation" action="save"/>' + url_param + '';
				$.post(url_param, {} ,function(data) {
					$("#tt").datagrid("reload");
					$("#edit-window").window("close");
					return;
				});
                return false;           
			}
		</script>
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
			<table id="tt" fit="true"></table>
	 	</div>
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;相关文章" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
 					<iframe id="editifr_article"  name="editifr_article" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="saveRelation()">选择</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="$('#edit-window').window('close');">取消</a>
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
                            </tr>
                            <tr>
                                <td class="tdtitle">标题：</td>
                                <td class="tdinput">
                                    <input type="text" id="title" name="title" class="inputtext"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden id="articleId" name="articleId"/>      	
	</body>
</html>