<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>相关文章</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script>
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:'<s:url namespace="/document/relation" action="input"/>',
					queryURL:'<s:url namespace="/document/relation" action="query"><s:param name="articleId" value="articleId"></s:param></s:url>',
					deleteURL:'<s:url namespace="/document/relation" action="delete"/>',
					editwidth:1000,
					editheight:700
				});
				//数据表格定义 						
                openDataGrid({
                    singleSelect:true,
                    columns:[[
                                {field:'id',title:'文章编号',width:60,sortable:true},
                                {field:'flags',title:'属性',width:60,
                                    formatter:function(val,rec){
                                        var pro = [];
                                        if (rec.topFlag) pro.push("<img src='../../source/image/article/top.gif' width='13px' height='13px' title='有效期限:永久置顶'/>"); 
                                        if (rec.commentFlag) pro.push("<img src='../../source/image/article/comment.gif' width='13px' height='13px' title='允许评论'/>");
                                        //if (rec.copyoutFlag) pro.push("<img src='../../source/image/article/copyout.gif' width='13px' height='13px' title='复制源'");
                                        if (rec.copyFlag) pro.push("<img src='../../source/image/article/copy.gif' width='13px' height='13px' title='复制'/>");
                                        return pro.join("");
                                    }
                                },
                                {field:'title',title:'标题<span style=\"color:blue;\">[字体大小]</span><span style=\"color:red;\">[分类]</span>',width:400,
                                    formatter:function(val,rec){
                                        var fontSize = "12";
                                        var spanStyle = "";
                                        var titleStyle = rec.titleStyle;
                                        if (titleStyle != ""){
                                            try{
                                                fontSize = $.trim(titleStyle.match(/font-size:(.*)px\s*;/)[1]);
                                                spanStyle = titleStyle.replace(/font-size:(.*)px\s*;/g,"");
                                            }catch(e){
                                                spanStyle = titleStyle;
                                            }
                                        }
                                        var classPro = [];
                                        if (rec.imageFlag) classPro.push("图片");
                                        if (rec.videoFlag) classPro.push("视频");
                                        if (rec.annexFlag) classPro.push("附件");
                                        if (rec.hotFlag) classPro.push("热点");
                                        if (rec.recommendFlag) classPro.push("推荐");
                                        var classValue = "";
                                        if (classPro.length > 0){
                                            classValue = "<span style='color:red;'>[" + classPro.join(",") + "]</span>";
                                        }
                                        return "<span style='" + spanStyle + "'>" + rec.title + "</span><span style='color:blue;'>[" + fontSize + "px]</span>" + classValue;
                                    }
                                },
                                {field:'typeDescription',title:'类型',width:60},
                                {field:'author',title:'作者',width:60},
                                {field:'statusDescription',title:'状态',width:80},
                                {field:'published',title:'发布时间',width:125},
                                {field:'modified',title:'修改时间',width:125}
                            ]],
					toolbar:[
							{text:'添加',iconCls:'icon-add',handler:addOperate},'-',
							{text:'移除',iconCls:'icon-remove', handler:delOperate},'-',
							{text:'上移',iconCls:'icon-up',handler:upOperate},'-',
							{text:'下移',iconCls:'icon-down',handler:downOperate},'-',
							//{text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
							{text:'缺省查询',iconCls:'icon-back', handler:initOperateQueryBack}
							]
				});
			});
			function addOperate(){
				$("#editifr_article").attr("src","<s:url namespace='/document/relation' action='article'/>");
				openWindow("#edit-window",{width:600,height:400,title:'文章选择'});
			}
			
			function delOperate(){
				var rows = $("#tt").datagrid("getSelections");
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择修改记录','info');
	                return;
	            }
	            var parameter = {};
			    var url_param = '?articleId=' + $("#articleId").attr("value") + '&';
	           	for(var i=0;i<rows.length;++i){
	           		url_param += 'selectIds=' + rows[i].id +'&';
	           	}
	           	url_param = '<s:url namespace="/document/relation" action="delete"/>' + url_param + '';
	            $.post(url_param,parameter,function(data){
		            $.messager.alert('成功','删除成功');
		            $("#tt").datagrid('clearSelections');
		            $("#tt").datagrid('reload');
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
				});
                return false;           
			}
			
			function saveRelation(){
				var rows = editifr_article.getRelationRows();
			    var url_param = '?articleId=' + $("#articleId").attr("value");
	           	for(var i=0;i<rows.length;++i){
	           		url_param += '&selectIds=' + rows[i].article.id;
	           	}
	           	url_param = '<s:url namespace="/document/relation" action="save"/>' + url_param + '';
				$.post(url_param, {} ,function(data) {
					$("#tt").datagrid("reload");
					$("#edit-window").window("close");
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
                </div>
            </div>
        </div>
        <s:hidden id="articleId" name="articleId"/>      	
	</body>
</html>