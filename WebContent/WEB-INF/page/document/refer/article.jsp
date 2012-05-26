<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>文档编辑</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
			var channelId = 0;
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/document/article" action="query"/>');

				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');

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
	                  ]],
				});

				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				
				//站点专栏目录树初始
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/site/channel" action="tree"/>',
					onClick:function(node){
						channelId = node.id;
						var rootnode = $('#tt2').tree('getRoot');
						if(rootnode.id == node.id) return;

						var url='<s:url namespace="/document/article" action="query"/>';
						url = url + "?channelId=" + node.id;
						$("#tt").datagrid({
			            	pageNumber:1,
			                url:url
			            });
					}
				});
			});
			
			//重载站点专栏目录树
			function channelTreeLoad(){
				$('#tt2').tree('reload');
			}

			function getRelationRows(){
				var rows = $('#tt').datagrid('getSelections');
				return rows;
			}
			
			function initOperateQuery(){
				var url='<s:url namespace="/document/article" action="query"/>';
				url = url + "?channelId=" + channelId;
				$("#tt").datagrid({
	            	pageNumber:1,
	                url:url
	            });
			}

			function querySearch_Article(){
                var value = $("#queryform").serialize();
                value = "parameters['" + value;
                value = value.replace(/\=/g,"']=");
                value = value.replace(/\&/g,"&parameters['");  

                var url = '<s:url namespace="/document/article" action="query"/>';
                url += "?channelId=" + channelId + "&" + value;
                $("#tt").datagrid({
                    pageNumber:1,
                    url:url
                });
                $("#query-window").window('close');
			}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="west"  title='<img src="<s:url value="/ewcmssource/theme/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
			<ul  id="tt2"></ul>
		</div>
		<div id="tt_related" region="center" style="padding:2px;" border="false">
			<table id="tt" fit="true"></table>
	 	</div>
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;文档编辑" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
 					<iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
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
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch_Article();">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>
	</body>
</html>