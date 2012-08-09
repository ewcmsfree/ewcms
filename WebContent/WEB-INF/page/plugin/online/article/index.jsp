<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>文档编辑</title>	
		<s:include value="../../../taglibs.jsp"/>
		<script>
			$(function(){
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/plugin/online/article" action="query?workingBodyId=' + $('#workingBodyId').val() + '&channelId=' + $('#channelId').val() + '"/>');
				
				ewcmsBOBJ.delToolItem('新增');
				ewcmsBOBJ.delToolItem('修改');
				ewcmsBOBJ.delToolItem('删除');
				ewcmsBOBJ.delToolItem('查询');
				ewcmsBOBJ.delToolItem('缺省查询');
				
				ewcmsBOBJ.addToolItem('新增','icon-add',addOperate);
				ewcmsBOBJ.addToolItem('修改','icon-edit',updOperate);
				ewcmsBOBJ.addToolItem('移动','icon-move',moveOperate);
				//ewcmsBOBJ.addToolItem('删除','icon-remove', delOperateBack);
				//ewcmsBOBJ.addToolItem('查询','icon-search', queryOperateBack);
				//ewcmsBOBJ.addToolItem('缺省查询','icon-back', initOperateQuery);
				
				ewcmsBOBJ.openDataGrid('#tt',{
					columns:[[
					    {field : 'id',title : '编号',width : 50,sortable:true},
						{field : 'flags',title : '属性',width : 60,
						    formatter : function(val, rec) {
							    var pro = [];
								if (rec.top) pro.push("<img src='../../ewcmssource/image/article/top.gif' width='13px' height='13px' title='有效期限:永久置顶' style='border:0'/>");
								if (rec.article.comment) pro.push("<img src='../../ewcmssource/image/article/comment.gif' width='13px' height='13px' title='允许评论' style='border:0'/>");
								if (rec.article.type == "TITLE") pro.push("<img src='../../ewcmssource/image/article/title.gif' width='13px' height='13px' title='标题新闻' style='border:0'/>");
								if (rec.reference) pro.push("<img src='../../ewcmssource/image/article/reference.gif' width='13px' height='13px' title='引用新闻' style='border:0'/>");
								if (rec.article.inside) pro.push("<img src='../../ewcmssource/image/article/inside.gif' width='13px' height='13px' title='内部标题' style='border:0'/>");
								return pro.join("");
							}
						},
						{field : 'title',title : '标题<span style=\"color:red;\">[分类]</span>',width : 500,
							formatter : function(val, rec) {
								var classPro = [];
								var categories = rec.article.categories;
								for ( var i = 0; i < categories.length; i++) {
									classPro.push(categories[i].categoryName);
								}
								var classValue = "";
								if (classPro.length > 0) {
									classValue = "<span style='color:red;'>[" + classPro.join(",") + "]</span>";
								}
								return rec.article.title + classValue;
							}
						},
						{field : 'statusDescription',title : '状态',width : 120,sortable:true,
							formatter : function(val, rec) {
								var processName = "";
								if (rec.article.status == 'REVIEW' && rec.article.reviewProcess != null){
									processName = "(" + rec.article.reviewProcess.name + ")";
								}
								return rec.article.statusDescription + processName;
							}
						}, 
						{field : 'published',title : '发布时间',width : 145,sortable:true,formatter : function(val, rec) {return rec.article.published;}}, 
						{field : 'modified',title : '修改时间',width : 145,sortable:true,formatter : function(val, rec) {return rec.article.modified;}}, 
						{field : 'sort',title : '排序号',width : 50}
					]]
				});
				
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url namespace="/plugin/online/article" action="input?workingBodyId=' + $('#workingBodyId').val() + '&channelId=' + $('#channelId').val() + '"/>');
				ewcmsOOBJ.setDeleteURL('<s:url namespace="/plugin/online/article" action="delete?workingBodyId=' + $('#workingBodyId').val() + '&channelId=' + $('#channelId').val() + '"/>');
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