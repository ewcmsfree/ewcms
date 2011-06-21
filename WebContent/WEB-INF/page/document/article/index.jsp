<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="ewcms" uri="/ewcms-tags"%>
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
					inputURL:'<s:url namespace="/document/article" action="input"/>',
					queryURL:'<s:url namespace="/document/article" action="query"/>',
					deleteURL:'<s:url namespace="/document/article" action="delete"/>',
					editwidth:1000,
					editheight:700,
					querywidth:600,
					queryheight:200
				});
				//数据表格定义 						
                openDataGrid({
                    columns:[[
                                {field:'id',title:'编号',width:60},
                                {field:'topFlag',title:'置顶',width:60,hidden:true,formatter:function(val,rec){return rec.article.topFlag;}},
                                {field:'reference',title:'引用',width:60,hidden:true},
                                {field:'flags',title:'属性',width:60,
                                    formatter:function(val,rec){
                                        var pro = [];
                                        if (rec.article.topFlag) pro.push("<img src='../../source/image/article/top.gif' width='13px' height='13px' title='有效期限:永久置顶'/>"); 
                                        if (rec.article.commentFlag) pro.push("<img src='../../source/image/article/comment.gif' width='13px' height='13px' title='允许评论'/>");
                                        if (rec.article.type=="TITLE") pro.push("<img src='../../source/image/article/title.gif' width='13px' height='13px' title='标题新闻'/>");
                                        if (rec.reference) pro.push("<img src='../../source/image/article/reference.gif' width='13px' height='13px' title='引用新闻'/>");
                                        if (rec.article.inside) pro.push("<img src='../../source/image/article/inside.gif' width='13px' height='13px' title='内部标题'/>");
                                        
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
                                {field:'author',title:'作者',width:80,formatter:function(val,rec){return rec.article.author;}},
                                {field:'statusDescription',title:'状态',width:60,formatter:function(val,rec){return rec.article.statusDescription;}},
                                {field:'auditReal',title:'审核人',width:80,formatter:function(val,rec){return rec.article.auditReal;}},
                                {field:'published',title:'发布时间',width:125,formatter:function(val,rec){return rec.article.published;}},
                                {field:'modified',title:'修改时间',width:125,formatter:function(val,rec){return rec.article.modified;}},
                                {field:'sort',title:'排序号',width:40}
                        ]],
			         toolbar:[
								{id:'btnAdd',text:'新增',iconCls:'icon-add',handler:addOperate},'-',
								{id:'btnUpd',text:'修改',iconCls:'icon-edit',handler:updOperate},'-',
								{id:'btnCopy',text:'复制',iconCls:'icon-copy',handler:copyOperate},'-',
								{id:'btnMove',text:'移动',iconCls:'icon-move',handler:moveOperate},'-',
								{id:'btnSort',text:'排序',iconCls:'icon-sort',handler:sortOperate},
								{id:'btnClearSort',text:'清除排序',iconCls:'icon-sort',handler:clearSortOperate},'-',
								{id:'btnRemove',text:'删除',iconCls:'icon-remove', handler:delOperate},'-',
								{id:'btnSearch',text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{id:'btnBack',text:'缺省查询',iconCls:'icon-back', handler:initOperateQuery},'-',
								{id:'btnSubmitReview',text:'提交审核',iconCls:'icon-submitreview',handler:submitReviewOperate},'-',
								{id:'btnReview',text:'审核',iconCls:'icon-review',handler:reviewOperate},'-',
								{id:'btnPub',text:'发布',iconCls:'icon-publish',handler:pubOperate}							
						     ]
				});
				//站点专栏目录树初始
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/site/channel" action="tree"/>',
					onClick:function(node){
						channelId = node.id;
						$('#channelId').attr('value',channelId);
						if (node.attributes.maxpermission < 1){
							disableButtons();
							$('#btnRelease').linkbutton('disable');
							$('#btnPub').linkbutton('disable');
							return;
						}else {
							if (node.attributes.maxpermission <=1 && node.attributes.maxpermission > 2){
								$('#btnRelease').linkbutton('disable');
								$('#btnPub').linkbutton('disable');
							}else if (node.attributes.maxpermission >=2){
								$('#btnRelease').linkbutton('enable');
								$('#btnPub').linkbutton('enable');
							}
							
							enableButtons();
							$("#tt").datagrid('clearSelections');
							if (channelId == $('#tt').tree('getRoot')) return;
							var url='<s:url namespace="/document/article" action="query"/>';
							url = url + "?channelId=" + channelId;
							$("#tt").datagrid({
				            	pageNumber:1,
				                url:url
				            });
							var rootnode = $('#tt2').tree('getRoot');
							if (rootnode.id == node.id && node.attributes.maxpermission >=2){
								$('#btnRelease').linkbutton('disable');
								$('#btnPub').linkbutton('enable');
								disableButtons();
								return;
							}				            
						}
					}
				});
				$('#tt3').click(function(){
					var selected = $('#tt3').tree('getSelected');
					if(selected == null || typeof(selected) == 'undefined') {
	    	    		$.messager.alert('提示','请选择要操作的专栏','info');
	    	    		return;
	    	    	}
					if(selected.attributes.maxpermission <= 1){
	    	    		$('#moveArticle').linkbutton('disable');
	    	    		$('#copyArticle').linkbutton('disable');
					}else{
						$('#moveArticle').linkbutton('enable');
						$('#copyArticle').linkbutton('enable');
					}    			

				});
			});
			
			//重载站点专栏目录树
			function channelTreeLoad(){
				$('#tt2').tree('reload');
			}

			function articleReload(){
				var url='<s:url namespace="/document/article" action="query"/>';
				url = url + "?channelId=" + channelId;
				$("#tt").datagrid({
	            	pageNumber:1,
	                url:url
	            });
			}
			
			function addOperate(){
				if (channelId != 0 && channelId != $('#tt2').tree('getRoot').id){
					var url_param = '?channelId=' + channelId + '';
					window.open('<s:url namespace="/document/article" action="input"/>' + url_param,'popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=' + (window.screen.width - 1280)/2 + ',top=' + (window.screen.height - 700)/2 + ''); 
				}else{
					$.messager.alert('提示','请选择栏目','info');
				}
				return false;
			}
			function updOperate(){
				if (channelId != $('#tt2').tree('getRoot').id){
		            var rows = $("#tt").datagrid('getSelections');
		            if(rows.length == 0){
		            	$.messager.alert('提示','请选择修改记录','info');
		                return;
		            }
		            if (rows.length > 1){
						$.messager.alert('提示','只能选择一个修改','info');
						return;
			        }
			        if (rows[0].reference == true){
				        $.messager.alert('提示','引用文章不能修改','info');
				        return;
			        }
			        if (rows[0].article.statusDescription == '初稿' || rows[0].article.statusDescription == '重新编辑'){
			            var url_param = '?channelId=' + channelId + '&';
			           	url_param += 'selections=' + rows[0].id;
						window.open('<s:url namespace="/document/article" action="input"/>' + url_param,'popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=' + (window.screen.width - 1280)/2 + ',top=' + (window.screen.height - 700)/2 + ''); 
			        }else{
			        	$.messager.alert('提示','文章只能在初稿或重新编辑状态在才能修改','info');
			        	return;
			        }
				}else{
					$.messager.alert('提示','请选择栏目','info');
					return;
				}
				return false;
			}

            function delOperate(){
                var rows = $("#tt").datagrid('getSelections');
                if(rows.length == 0){
                    $.messager.alert('提示','请选择删除记录','info');
                    return;
                }

                var url = '<s:url namespace="/document/article" action="delete"/>';
                var parameter = 'channelId=' + channelId + '&';
                for(var i=0;i<rows.length;++i){
                	parameter = parameter + 'selections=' + rows[i].id +'&';
                }
                $.messager.confirm("提示","确定要删除所选记录到回收站吗?",function(r){
                    if (r){
                        $.post(url, parameter, function(data){
                            $.messager.alert('成功','删除文档到回收站成功!');
                            $("#tt").datagrid('clearSelections');
                            $("#tt").datagrid('reload');
                        });
                    }
                });
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

			function moveOperate(){
				$('#tt3').tree({
					checkbox: false,
					url: '<s:url namespace="/site/channel" action="tree"/>'
				});
	            var rows = $("#tt").datagrid('getSelections');
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择移动记录','info');
	            	return ;
	            }
	            $("#span_move").attr("style","");
	            $("#span_copy").attr("style","display:none");
				openWindow("#moveorcopy-window",{width:300,height:400,title:'移动文章选择'});
			}

			function copyOperate(){
				$('#tt3').tree({
					checkbox: true,
					url: '<s:url namespace="/site/channel" action="tree"/>'
				});
	            var rows = $("#tt").datagrid('getSelections');
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择复制记录','info');
	            	return ;
	            }
	            $("#span_move").attr("style","display:none");
	            $("#span_copy").attr("style","");
				openWindow("#moveorcopy-window",{width:300,height:400,title:'复制文章选择'});
			}

			function moveArticle(){
				var selected = $('#tt3').tree('getSelected');
				var rootnode = $('#tt3').tree('getRoot');
				
				var url = "<s:url action='move' namespace='/document/article'/>";

				var parameter = 'channelId=' + channelId + '&';
				var rows = $("#tt").datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					parameter = parameter + 'selections=' + rows[i].id + '&';
				}
				
				if (selected.id != rootnode.id){
					parameter = parameter + 'selectChannelIds=' + selected.id +'&';
				}else{
					 $.messager.alert('提示','文章不能移动到根栏目','info');
					 return;
				}
					
				$.post(url,parameter,function(data){
					if (data == "true"){
			            $.messager.alert('成功','移动文章成功','info');
			            $("#tt3").tree('reload');
			            $("#tt").datagrid('clearSelections');
			            $("#tt").datagrid('reload');
					}
					$("#moveorcopy-window").window("close");
	            });	
			}

			function copyArticle(){
				var checkeds = $('#tt3').tree('getChecked')
				var rootnode = $('#tt3').tree('getRoot');
				//var url = "../../document/article/copy.do";
				var url = "<s:url action='copy' namespace='/document/article'/>";

				var parameter = 'channelId=' + channelId + '&';
				var rows = $("#tt").datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					parameter = parameter + 'selections=' + rows[i].id + "&";
				}
				
				for(var i=0;i<checkeds.length;i++){
					if (checkeds[i].id != rootnode.id){
						parameter = parameter + 'selectChannelIds=' + checkeds[i].id +'&';
					}
		        }
				$.post(url,parameter,function(data){
					if (data == "true"){
			            $.messager.alert('成功','复制文章成功','info');
			            $("#tt3").tree('reload');
			            $("#tt").datagrid('clearSelections');
			            $("#tt").datagrid('reload');
					}
					$("#moveorcopy-window").window("close");
	            });	
			}
			
			function closeCannel(){
				$("#moveorcopy-window").window("close");
			}
			
			function submitReviewOperate(){
		    	var rows = $("#tt").datagrid('getSelections');
		        if(rows.length == 0){
		        	$.messager.alert('提示','请选择提交审核记录','info');
		            return;
		        }
		        var parameter = 'channelId=' + channelId + '&';
		        var rows = $("#tt").datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					parameter = parameter + 'selections=' + rows[i].id + "&";
				}
		        var url = '<s:url namespace="/document/article" action="submitReviews"/>';
		        $.post(url, parameter, function(data){
			        if (data == 'system-false'){
			        	$.messager.alert('提示','文章提交审核失败','info');
			        	return;
			        }else if (data == 'true'){
				        $("#tt").datagrid('clearSelections');
				        articleReload();
				        $.messager.alert('提示','文章提交审核成功','info');
				        return;
			        }
		        });
				return false;
			}
			function pubOperate(){
				var url = '<s:url namespace="/document/article" action="pubArticle"/>';
				var parameter = "channelId=" + channelId + "";
				$.post(url, parameter, function(data){
					if (data == 'system-false'){
						$.messager.alert('提示','系统错误','info');
						return;
					}else if (data == 'accessdenied'){
						$.messager.alert('提示','没有发布权限','info');
						return;
					}else{
						$.messager.alert('提示','发布成功','info');
						return;
					}
				});
				return false;
			}
			function disableButtons(){
				$('#btnAdd').linkbutton('disable');
				$('#btnUpd').linkbutton('disable');
				$('#btnCopy').linkbutton('disable');
				$('#btnMove').linkbutton('disable');
				$('#btnRemove').linkbutton('disable');
				$('#btnSearch').linkbutton('disable');
				$('#btnBack').linkbutton('disable');
				$('#btnCitizen').linkbutton('disable');
				$('#btnSelf').linkbutton('disable');
				$('#btnShare').linkbutton('disable');
				$('#btnReview').linkbutton('disable');
				$('#btnSubmitReview').linkbutton('disable');
				$('#btnSort').linkbutton('disable');
				$('#btnClearSort').linkbutton('disable');				
			}
			function enableButtons(){
				$('#btnAdd').linkbutton('enable');
				$('#btnUpd').linkbutton('enable');
				$('#btnCopy').linkbutton('enable');
				$('#btnMove').linkbutton('enable');
				$('#btnRemove').linkbutton('enable');
				$('#btnSearch').linkbutton('enable');
				$('#btnBack').linkbutton('enable');
				$('#btnCitizen').linkbutton('enable');
				$('#btnSelf').linkbutton('enable');
				$('#btnShare').linkbutton('enable');
				$('#btnReview').linkbutton('enable');
				$('#btnSubmitReview').linkbutton('enable');
				$('#btnSort').linkbutton('enable');
				$('#btnClearSort').linkbutton('enable');
			}
			function reviewOperate(){
				var rows = $("#tt").datagrid('getSelections');
		        if(rows.length == 0){
		        	$.messager.alert('提示','请选择审核的文章','info');
		           	return ;
		        }
				openWindow("#review-window",{width:550,height:200,title:"审核"});
			}
			function reviewArticle(){
		    	var rows = $("#tt").datagrid('getSelections');
		        if(rows.length == 0){
		        	$.messager.alert('提示','请选择发布记录','info');
		            return;
		        }
		        
		        var parameter = 'review=' + $("input[name='reviewRadio']:checked").val() + '&channelId=' + channelId + '&';
		        var rows = $("#tt").datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					parameter = parameter + 'selections=' + rows[i].id + "&";
				}
		        var url = '<s:url namespace="/document/article" action="reviewArticle"/>';
		        $.post(url, parameter, function(data){
		        	$("#review-window").window("close");
			        if (data == 'system-false'){
			        	$.messager.alert('提示','文章审核失败','info');
			        }else if (data == 'true'){
				        $("#tt").datagrid('clearSelections');
				        articleReload();
				        $.messager.alert('提示','文章审核成功','info');
			        }
			        return;
		        });				
		        return false;
			}
			var sort='';
			function sortOperate(){
                if (channelId != $('#tt2').tree('getRoot').id){
                    var rows = $("#tt").datagrid('getSelections');
                    if(rows.length == 0){
                        $.messager.alert('提示','请选择排序记录','info');
                        return;
                    }
                    if (rows.length > 1){
                        $.messager.alert('提示','只能选择一个排序','info');
                        return;
                    }
                    $.messager.prompt('是否要对所选中的文章进行排序', '请输入排序号', function(r){
                        if (r){
                        	var reg=/^\d+$/;
	                        if (reg.test(r)){
	                            $.post('<s:url namespace="document/article" action="isSortArticle"/>',{'selections':$("#tt").datagrid("getSelections")[0].id,'channelId':channelId,'isTop':$("#tt").datagrid("getSelections")[0].article.topFlag,'sort':r},function(data){
	                                if (data=="true"){
	                                    sort = r;
	                                	openWindow("#sort-window",{width:550,height:200,title:"排序"});
	                                	return;
	                                }else if (data=="false"){
	                                	$.post('<s:url namespace="document/article" action="sortArticle"/>',{'selections':$("#tt").datagrid("getSelections")[0].id,'channelId':channelId,'isTop':$("#tt").datagrid("getSelections")[0].article.topFlag,'sort':r},function(data){
	                                    	if (data=="true"){
	                                    		$.messager.alert('提示','设置排序号成功','info');
	                                    		$("#tt").datagrid('clearSelections');
	                                            articleReload();
	                                    	}else if (data=="false"){
	                                    		$.messager.alert('提示','设置排序号失败','info');
	                                    	}else if (data=="system-false"){
	                                    		$.messager.alert('提示','系统错误','info');
	                                    	}
	                                    	return;
	                                	});
	                                }else if (data=="system-false"){
	                                	$.messager.alert('提示','系统错误','info');
	                                	return;
	                                }
	                            });
	                        }else{
	                            sortOperate();
	                            return;
	                        }
                        }
                    });
               }
               return false;
			}
			function sortArticle(){
				$.post('<s:url namespace="document/article" action="sortArticle"/>',{'selections':$("#tt").datagrid("getSelections")[0].id,'channelId':channelId,'isTop':$("#tt").datagrid("getSelections")[0].article.topFlag,'isInsert':$("input[name='sortRadio']:checked").val(),'sort':sort},function(data){
					$("#sort-window").window("close");
                    if (data=="true"){
                        $.messager.alert('提示','设置排序号成功','info');
                        $("#tt").datagrid('clearSelections');
                        articleReload();
                    }else if (data=="false"){
                        $.messager.alert('提示','设置排序号失败','info');
                    }else if (data=="system-false"){
                        $.messager.alert('提示','系统错误','info');
                    }
                    return;
                });
                return false;
			}
			function clearSortOperate(){
                if (channelId != $('#tt2').tree('getRoot').id){
                    var rows = $("#tt").datagrid('getSelections');
                    if(rows.length == 0){
                        $.messager.alert('提示','请选择清除排序记录','info');
                        return;
                    }
                    var parameter = "channelId=" + channelId + "&";
    				for(var i=0;i<rows.length;i++){
    					parameter = parameter + 'selections=' + rows[i].id + "&";
    				}
                    $.post('<s:url namespace="document/article" action="clearSortArticle"/>' ,parameter ,function(data){
                        if (data == "true"){
                        	$.messager.alert('提示','设置消除排序号成功','info');
                        	$("#tt").datagrid('clearSelections');
                            articleReload();
                        }else if (data=="system-false"){
                            $.messager.alert('提示','系统错误','info');
                        }
                        return;
                    });
                }
                return false;				
			}
		</script>
		<ewcms:datepickerhead></ewcms:datepickerhead>
	</head>
	<body class="easyui-layout">
		<div region="west"  title='<img src="<s:url value="/source/theme/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 站点专栏' split="true" style="width:180px;">
			<ul  id="tt2"></ul>
		</div>
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
                                <s:select list="@com.ewcms.content.document.model.ArticleStatus@values()" listValue="description" name="articleStatus" id="articleStatus" headerKey="-1" headerValue="------请选择------"></s:select>
                            </td>
                            <td class="tdtitle">类型：</td>
                            <td class="tdinput">
                                <s:select list="@com.ewcms.content.document.model.ArticleType@values()" listValue="description" name="articleType" id="articleType" headerKey="-1" headerValue="------请选择------"></s:select>
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
        <div id="moveorcopy-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul id="tt3"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="span_move" style="display:none"><a id="moveArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:moveArticle();">确定</a></span>
                    <span id="span_copy" style="display:none"><a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:copyArticle();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:closeCannel();">取消</a>
                </div>
            </div>
        </div>
        <div id="site-window" class="easyui-window" closed="true"   style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul  id="sitett"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:shareSite();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick='javascript:$("#site-window").window("close");'>取消</a>
                </div>
            </div>
        </div>
        <div id="review-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                	<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#99BBE8" style="border: #99BBE8 1px solid;">
                		<tr align="center">
                			<td height="30" width="20%">操作</td>
                			<td height="30" width="80%">说明</td>
                		</tr>
                		<tr>
                			<td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="reviewRadio" name="reviewRadio" list='#{0:"通过"}' cssStyle="vertical-align: middle;" value="0"></s:radio></td>
                			<td height="40">&nbsp;所选文章将进入"发布版"状态</td>
                		</tr>
                		<tr>
                			<td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="reviewRadio" name="reviewRadio"  list='#{1:"不通过"}' cssStyle="vertical-align: middle;"></s:radio></td>
                			<td height="40">&nbsp;所选文章将进入"重新编辑"状态</td>
                		</tr>
                	</table>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:reviewArticle();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#review-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
        <div id="sort-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                    <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#99BBE8" style="border: #99BBE8 1px solid;">
                        <tr align="center">
                            <td height="30" width="20%">操作</td>
                            <td height="30" width="80%">说明</td>
                        </tr>
                        <tr>
                            <td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="sortRadio" name="sortRadio" list='#{0:"插入"}' cssStyle="vertical-align: middle;" value="0"></s:radio></td>
                            <td height="40">&nbsp;所选文章将插入到当前排序号</td>
                        </tr>
                        <tr>
                            <td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="sortRadio" name="sortRadio"  list='#{1:"替换"}' cssStyle="vertical-align: middle;"></s:radio></td>
                            <td height="40">&nbsp;所选文章将替换已有的文章的排序号</td>
                        </tr>
                    </table>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a id="copyArticle" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:sortArticle();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#sort-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
	</body>
</html>