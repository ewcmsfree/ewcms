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
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:'<s:url namespace="/plugin/leadingwindow/article" action="input?leaderChannelId=' + $('#leaderChannelId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					queryURL:'<s:url namespace="/plugin/leadingwindow/article" action="query?leaderChannelId=' + $('#leaderChannelId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					deleteURL:'<s:url namespace="/plugin/leadingwindow/article" action="delete?leaderChannelId=' + $('#leaderChannelId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					editwidth:1000,
					editheight:700
				});
				//数据表格定义 						
				openDataGrid({
					columns:[[
								{field:'id',title:'序号',width:40,sortable:true},
								{field:'topFlag',title:'属性',width:60,
									formatter:function(val,rec){
										var pro = [];
										if (rec.article.topFlag) pro.push("<img src='../../../source/image/article/top.gif' width='13px' height='13px' title='有效期限:永久置顶'/>"); 
										if (rec.article.commentFlag) pro.push("<img src='../../../source/image/article/comment.gif' width='13px' height='13px' title='允许评论'/>");
										if (rec.article.copyoutFlag) pro.push("<img src='../../../source/image/article/copyout.gif' width='13px' height='13px' title='复制源'");
										if (rec.article.copyFlag) pro.push("<img src='../../../source/image/article/copy.gif' width='13px' height='13px' title='复制'/>");
										return pro.join("");
									}
								},
								{field:'title',title:'标题[字体大小][分类属性]',width:400,
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
			                 			if (rec.article.imageFlag) classPro.push("图片");
				                 		if (rec.article.videoFlag) classPro.push("视频");
					                 	if (rec.article.annexFlag) classPro.push("附件");
						                if (rec.article.hotFlag) classPro.push("热点");
							            if (rec.article.recommendFlag) classPro.push("推荐");
								        var classValue = "";
								        if (classPro.length > 0){
											classValue = "<span style='color:#FF0000;'>[" + classPro.join(",") + "]</span>";
									    }
			                 			return "<span style='" + spanStyle + "'>" + rec.article.title + "</span>[" + fontSize + "px]" + classValue;
				            		}
							    },
							    {field:'typeDescription',title:'文章类型',width:60,
								    formatter:function(val,rec){
								    	return rec.article.typeDescription;
							    	}
								},
					            {field:'author',title:'作者',width:60,
							    	formatter:function(val,rec){
							    		return rec.article.author;
							    	}
						        },
					            {field:'statusDescription',title:'状态',width:80},
					            {field:'published',title:'发布时间',width:125},
					            {field:'modified',title:'修改时间',width:125}
					        ]],
			         toolbar:[
								{text:'新增引用',iconCls:'icon-add',handler:addQuoteOperate},'-',
								{text:'移除引用',iconCls:'icon-remove',handler:moveQuoteOperate},'-',
								{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{text:'缺省查询',iconCls:'icon-back', handler:initOperateQuery}
						     ]
				});
			});
			function articleReload(){
				var url='<s:url namespace="/plugin/leadingwindow/article" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>';
				url = url + "?leaderChannelId=" + parent.leaderChannelId;
				$("#tt").datagrid({
	            	pageNumber:1,
	                url:url
	            });
			}
			
			function initOperateQuery(){
				var url='<s:url namespace="/plugin/leadingwindow/article" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>';
				url = url + "?leaderChannelId=" + parent.leaderChannelId;
				$("#tt").datagrid({
	            	pageNumber:1,
	                url:url
	            });
			}

			function closeCannel(){
				$("#pop-window").window("close");
			}
			
			function addQuoteOperate(){
				var url = '<s:url namespace="/plugin/leadingwindow/refarticle" action="index"/>';
				$("#editifr_pop").attr('src',url);
				openWindow("#pop-window",{width:1000,height:570,top:17,left:40,title:'新增引用文章'});
			}

			function saveQuoteOperate(){
				var rows = editifr_pop.$("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择需引用的文章","info");
					return;
				}
				var rows = editifr_pop.getRelatedRows();
				var parameter = 'leaderChannelId=' + $('#leaderChannelId').val() + '&channelId=' + $('#channelId').val();
	           	for(var i=0;i<rows.length;++i){
	           		parameter = parameter + '&selArticleRmcIds=' + rows[i].id;
	           	}
				$.post('<s:url namespace="/plugin/leadingwindow/article" action="saveQuote"/>', parameter ,function(data) {
					if (data == 'true'){
						$("#tt").datagrid('reload');
						$("#edit-window").window("close");
					}else if (data == 'false'){
						$.messager.alert('失败','新增引用失败','info');
						return;
					}
				});			
			}
			
			function moveQuoteOperate(){
				var rows = $("#tt").datagrid("getSelections");
				if (rows.length == 0){
					$.messager.alert("提示","请选择删除的文章","info");
					return;
				}
				var parameter = 'leaderChannelId=' + $('#leaderChannelId').val() + '&channelId=' + $('#channelId').val();
	           	for(var i=0;i<rows.length;++i){
	           		parameter = parameter + '&selArticleRmcIds=' + rows[i].id;
	           	}
				$.post('<s:url namespace="/plugin/leadingwindow/article" action="delQuote"/>', parameter ,function(data) {
					if (data == 'true'){
						$("#tt").datagrid('reload');
						$("#edit-window").window("close");
					}else if (data == 'false'){
						$.messager.alert('失败','删除文章失败','info');
						return;
					}
				});			
				
			}
		</script>		
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
                </div>
            </div>
        </div>
		<div id="pop-window" class="easyui-window" title="弹出窗口" icon="icon-save" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="save_span"><a id="saveorok" class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveQuoteOperate();return false;">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="closeCannel();return false;">取消</a>
                </div>
            </div>
        </div>
        <s:hidden name="channelId" id="channelId"/>
        <s:hidden name="leaderChannelId" id="leaderChannelId"/>
	</body>
</html>