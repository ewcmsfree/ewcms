<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>文档编辑</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
			var channelId = 0;
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:' ',
					queryURL:' ',
					deleteURL:' ',
					editwidth:1000,
					editheight:700
				});
				//数据表格定义 						
                openDataGrid({
                    columns:[[
                                {field:'id',title:'序号',width:60,sortable:true},
                                {field:'articleId',title:'文章序号',width:60,formatter:function(val,rec){return rec.article.id;}},
                                {field:'topFlag',title:'置顶',width:60,hidden:true,formatter:function(val,rec){return rec.article.topFlag;}},
                                {field:'isReference',title:'引用',width:60,hidden:true},
                                {field:'flags',title:'属性',width:60,
                                    formatter:function(val,rec){
                                        var pro = [];
                                        if (rec.article.topFlag) pro.push("<img src='../../source/image/article/top.gif' width='13px' height='13px' title='有效期限:永久置顶'/>"); 
                                        if (rec.article.commentFlag) pro.push("<img src='../../source/image/article/comment.gif' width='13px' height='13px' title='允许评论'/>");
                                        //if (rec.article.copyoutFlag) pro.push("<img src='../../source/image/article/copyout.gif' width='13px' height='13px' title='复制源'");
                                        if (rec.article.copyFlag) pro.push("<img src='../../source/image/article/copy.gif' width='13px' height='13px' title='复制'/>");
                                        if (rec.isReference) pro.push("<img src='../../source/image/article/reference.gif' width='13px' height='13px' title='引用'/>");
                                        return pro.join("");
                                    }
                                },
                                {field:'title',title:'标题<span style=\"color:blue;\">[字体大小]</span><span style=\"color:red;\">[分类]</span>',width:500,
                                    formatter:function(val,rec){
                                        var fontSize = "12";
                                        var spanStyle = "";
                                        var titleStyle = rec.article.titleStyle;
                                        if (titleStyle != ""){
                                            try{
                                                fontSize = $.trim(titleStyle.match(/font-size:(.*)px\s*;/)[1]);
                                                spanStyle = titleStyle.replace(/font-size:(.*)px\s*;/g,"");
                                            }catch(e){
                                                spanStyle = titleStyle;
                                            }
                                        }
                                        var classPro = [];
                                        var categories = rec.article.categories;
                                        for (var i=0;i<categories.length;i++){
                                           classPro.push(categories[i].categoryName);
                                        }
                                        var classValue = "";
                                        if (classPro.length > 0){
                                            classValue = "<span style='color:red;'>[" + classPro.join(",") + "]</span>";
                                        }
                                        return "<span style='" + spanStyle + "'>" + rec.article.title + "</span><span style='color:blue;'>[" + fontSize + "px]</span>" + classValue;
                                    }
                                },
                                {field:'typeDescription',title:'类型',width:60,formatter:function(val,rec){return rec.article.typeDescription;}},
                                {field:'author',title:'作者',width:80,formatter:function(val,rec){return rec.article.author;}},
                                {field:'statusDescription',title:'状态',width:60,formatter:function(val,rec){return rec.article.statusDescription;}},
                                {field:'eauthor',title:'审核人',width:80,formatter:function(val,rec){return rec.article.eauthorReal;}},
                                {field:'published',title:'发布时间',width:125,formatter:function(val,rec){return rec.article.published;}},
                                {field:'modified',title:'修改时间',width:125,formatter:function(val,rec){return rec.article.modified;}}
                        ]],
			         toolbar:[
								{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{text:'缺省查询',iconCls:'icon-back', handler:initOperateQuery}
						     ]
				});
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

			function getRelatedRows(){
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
		<div region="west"  title='<img src="<s:url value="/source/theme/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
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
                </div>
            </div>
        </div>
        <s:hidden name="articleId" id="articleId"></s:hidden>
	</body>
</html>