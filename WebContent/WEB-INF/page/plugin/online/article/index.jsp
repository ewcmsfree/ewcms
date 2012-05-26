<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>文档编辑</title>	
		<s:include value="../../../taglibs.jsp"/>
		<script>
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:'<s:url namespace="/plugin/online/article" action="input?workingBodyId=' + $('#workingBodyId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					queryURL:'<s:url namespace="/plugin/online/article" action="query?workingBodyId=' + $('#workingBodyId').val() + '&channelId=' + $('#channelId').val() + '"/>',
					deleteURL:'<s:url namespace="/plugin/online/article" action="delete?workingBodyId=' + $('#workingBodyId').val() + '&channelId=' + $('#channelId').val() + '"/>',
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
										if (rec.article.topFlag) pro.push("<img src='../../source/image/article/top.gif' width='13px' height='13px' title='有效期限:永久置顶'/>"); 
										if (rec.article.commentFlag) pro.push("<img src='../../source/image/article/comment.gif' width='13px' height='13px' title='允许评论'/>");
										if (rec.article.copyoutFlag) pro.push("<img src='../../source/image/article/copyout.gif' width='13px' height='13px' title='复制源'");
										if (rec.article.copyFlag) pro.push("<img src='../../source/image/article/copy.gif' width='13px' height='13px' title='复制'/>");
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
								{text:'新增',iconCls:'icon-add',handler:addOperate},'-',
								{text:'修改',iconCls:'icon-edit',handler:updOperate},'-',
								{text:'移动',iconCls:'icon-move',handler:moveOperate},'-',
								{text:'删除',iconCls:'icon-remove', handler:delOperateBack},'-',
								{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{text:'缺省查询',iconCls:'icon-back', handler:initOperateQuery},'-'
						     ]
				});
			});
			function articleReload(){
				var url='<s:url namespace="/plugin/online/article" action="query"/>';
				url = url + "?workingBodyId=" + $('#workingBodyId').val() + "&channelId=" + $('#channelId').val();
				$("#tt").datagrid({
	            	pageNumber:1,
	                url:url
	            });
			}
			
			function initOperateQuery(){
				var url='<s:url namespace="/plugin/online/article" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>';
				url = url + "?workingBodyId=" +  $('#workingBodyId').val();
				$("#tt").datagrid({
	            	pageNumber:1,
	                url:url
	            });
			}

			function closeCannel(){
				$("#pop-window").window("close");
			}
			
			function addOperate(){
				var url_param = 'channelId=' + $('#channelId').val() + '&workingBodyId=' + $('#workingBodyId').val();
				window.open('<s:url namespace="/plugin/online/article" action="input?' + url_param + '"/>','popup','width=1200,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=0,top=0'); 
				return false;
			}
			function updOperate(){
		    	var rows = $("#tt").datagrid('getSelections');
		        if(rows.length == 0){
		          	$.messager.alert('提示','请选择修改记录','info');
		            return;
		        }
		        if (rows.length > 1){
					$.messager.alert('提示','只能选择一个修改','info');
					return;
			    }
	            var url_param = 'channelId=' + $('#channelId').val() + '&workingBodyId=' + $('#workingBodyId').val() + '&selections=' + rows[0].id;;
				window.open('<s:url namespace="/plugin/online/article" action="input?' + url_param + '"/>','popup','width=1200,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=0,top=0'); 
				return false;
			}
			function moveOperate(){
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/plugin/online/workingbody" action="tree"/>?channelId=' + $('#channelId').val() + '&isMatter=false'
				});
	            var rows = $("#tt").datagrid("getSelections");
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择移动记录','info');
	            	return ;
	            }
	            $("#span_move").attr("style","");
	            $("#span_copy").attr("style","display:none");
				openWindow("#moveorcopy-window",{width:300,height:400,title:'移动文章选择'});
			}

			function moveArticle(){
				var selected = $('#tt2').tree('getSelected');
				if (selected.id == $('#workingBodyId').val()){
					$.messager.alert('提示','文章不能移动相同的办事主体上','info');
					return;
				}
				var rootnode = $('#tt2').tree('getRoot');
				var url = "<s:url action='move' namespace='/plugin/online/article'/>";

				var parameter = '';
				var rows = $("#tt").datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					parameter = parameter + 'selections=' + rows[i].id + "&";
				}
				
				if (selected.id != rootnode.id){
					parameter = parameter + 'moveWorkingBodyId=' + selected.id +'&workingBodyId=' + $('#workingBodyId').val();
				}else{
					 $.messager.alert('提示','文章不能移动到组件上','info');
					 return;
				}
					
				$.post(url,parameter,function(data){
					if (data == "true"){
			            $.messager.alert('成功','移动文章成功','info');
			            $("#tt2").tree('reload');
			            $("#tt").datagrid('clearSelections');
			            $("#tt").datagrid('reload');
					}
					$("#moveorcopy-window").window("close");
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
        <div id="moveorcopy-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul id="tt2"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="span_move" style="display:none"><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:moveArticle();">确定</a></span>
                    <span id="span_copy" style="display:none"><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:copyArticle();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:closeCannel();">取消</a>
                </div>
            </div>
        </div>
        <s:hidden name="channelId" id="channelId"/>
        <s:hidden name="workingBodyId" id="workingBodyId"/>
	</body>
</html>