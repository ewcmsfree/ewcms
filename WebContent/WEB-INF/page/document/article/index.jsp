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
					inputURL:'<s:url namespace="/document/article" action="input"/>',
					queryURL:'<s:url namespace="/document/article" action="query"/>',
					deleteURL:'<s:url namespace="/document/article" action="delete"/>',
					editwidth:1000,
					editheight:700
				});
				//数据表格定义 						
				openDataGrid({
					columns:[[
								{field:'id',title:'序号',width:60,sortable:true},
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
								{field:'title',title:'标题[字体大小][分类属性]',width:500,
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
					            {field:'author',title:'作者',width:80,
							    	formatter:function(val,rec){
							    		return rec.article.author;
							    	}
						        },
					            {field:'statusDescription',title:'状态',width:80},
					            {field:'published',title:'发布时间',width:125},
					            {field:'modified',title:'修改时间',width:125}
			            ]],
			         toolbar:[
								{id:'btnAdd',text:'新增',iconCls:'icon-add',handler:addOperate},'-',
								{id:'btnUpd',text:'修改',iconCls:'icon-edit',handler:updOperate},'-',
								{id:'btnCopy',text:'复制',iconCls:'icon-copy',handler:copyOperate},'-',
								{id:'btnMove',text:'移动',iconCls:'icon-move',handler:moveOperate},'-',
								{id:'btnRemove',text:'删除',iconCls:'icon-remove', handler:delOperateBack},'-',
								{id:'btnSearch',text:'查询',iconCls:'icon-search', handler:queryOperateBack},'-',
								{id:'btnBack',text:'缺省查询',iconCls:'icon-back', handler:initOperateQuery},'-',
								{id:'btnRelease',text:'预发布',iconCls:'icon-release',handler:releaseOperate},'-',
								{id:'btnPub',text:'发布',iconCls:'icon-publish',handler:pubOperate},'-',
								{id:'btnCitizen',text:'关联人群',iconCls:'icon-citizen',handler:selCitizen},'-',
								{id:'btnSelf',text:'本站共享', iconCls:'icon-shareself',handler:shareSelf},'-',
								{id:'btnShare',text:'群站共享', iconCls:'icon-share', handler:share}								
						     ]
				});
				//站点专栏目录树初始
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/site/channel" action="tree"/>',
					onClick:function(node){
						channelId = node.id;
						//if(rootnode.id == node.id) return;
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
				$('#tt4').datagrid({
					pageNumber:1,
					url:'<s:url namespace="/document/citizen" action="getCitizen"/>',
					idField:'id',
					columns:[[
							{field:'ck',checkbox:true},
							{field:'id',title:'序号',width:50,sortable:true},
							{field:'name',title:'名称',width:200	}
					]],
					pagination:true
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
	
		            var url_param = '?channelId=' + channelId + '&';
		           	url_param += 'selections=' + rows[0].id;
					window.open('<s:url namespace="/document/article" action="input"/>' + url_param,'popup','width=1280,height=700,resizable=yes,toolbar=no,directories=no,location=no,menubar=no,status=no,left=' + (window.screen.width - 1280)/2 + ',top=' + (window.screen.height - 700)/2 + ''); 
				}else{
					$.messager.alert('提示','请选择栏目','info');
				}
				return false;
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

				var parameter = '';
				var rows = $("#tt").datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					parameter = parameter + 'selections=' + rows[i].id + "&";
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

				var parameter = '';
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
			
			function shareSelf(){
				var ids = getSelectRow();
				if(ids == '')return;
	    		$.messager.confirm("提示","确定共享所选文章至本站吗?",function(r){
	    			if (r){
			            $.post('<s:url value="/document/share/shareself.do"/>',ids,function(data){
				            if(data=='false'){
				            	$.messager.alert('提示','数据共享失败');
				            }else{
				            	$.messager.alert('提示','数据共享成功');
				            }
			            });	
	    			}
	    		});
			}

			function shareSite(){
				var checkeds = $('#sitett').tree('getChecked');
				if(checkeds.length == 0){
					$.messager.alert('提示','请选择站点');
					return;
				}				
	    		$.messager.confirm("提示","确定共享所选文章至所选站点吗?",function(r){
	    			if (r){
	    				var ids = getSelectRow();//文章id集
	    	            for(var i=0;i<checkeds.length;++i){//站点集
	    	            	ids =ids + 'siteIdList=' + checkeds[i].id +'&';
	    	            }							
	    	            
			            $.post('<s:url value="/document/share/share.do"/>',ids,function(data){
				            if(data=='false'){
				            	$.messager.alert('提示','数据共享失败');
				            }else{
				            	$("#site-window").window("close");
				            	$.messager.alert('提示','数据共享成功');
				            }
			            });	
	    			}
	    		});
			}
			
			function share(){
				var ids = getSelectRow();
				if(ids == '')return;				
				//站点目录树初始
				$(function(){
					$('#sitett').tree({
						checkbox: true,
						url: '<s:url value="/site/setup/tree.do"/>',
						cascadeCheck:false
					});
				});	
				openWindow("#site-window",{width:280,height:400,title:"选择共享站点"});
			}
			
			function getSelectRow(){
	            var rows = $("#tt").datagrid('getSelections');
	            if(rows.length == 0){
	            	$.messager.alert('提示','请选择要共享记录');
	            	return '' ;
	            }
	            var ids = '';
	            for(var i=0;i<rows.length;++i){
	            	ids =ids + 'selections=' + rows[i].id +'&';
	            }	
	            return ids;			
			}

			function selCitizen(){
				$("#tt4").datagrid('clearSelections');
				var rows = $("#tt").datagrid('getSelections');
		        if(rows.length == 0){
		        	$.messager.alert('提示','请选择要关联人群的文章记录');
		            return;
		        }
		        if(rows.length > 1){
		        	$.messager.alert('提示','只能选择一条文章记录与人群进行关联');
		            return;
		        }
				openWindow("#citizen-window",{width:520,height:420,top:70,left:400,title:'选择人群'});
				$.post('<s:url namespace="/document/article" action="selCitizen"/>' ,{articleRmcId:rows[0].id} ,function(data){
					if (data !=""){
						var citizenIds = data.toString().split(',');
						for (var i = 0 ; i < citizenIds.length; i++){
							$('#tt4').datagrid('selectRecord',parseInt(citizenIds[i]));
						}
					}
				});
			}

			function addCitizen(){
				var rows = $("#tt").datagrid('getSelections');
				if(rows.length == 0){
		        	$.messager.alert('提示','请选择要人群信息');
		            return;
		        }
				var articleRmcId = rows[0].id;
				rows = $('#tt4').datagrid('getSelections');
		        var ids = '?articleRmcId=' + articleRmcId + '&';
				for(var i=0;i<rows.length;++i){
					ids =ids + 'citizenIds=' + rows[i].id +'&';
				}
				var url = '<s:url namespace="/document/article" action="addCitizen"/>' + ids;
				$.post(url ,{} ,function(data){
					if (data=='false'){
						$.messager.alert('提示','文章关联人群失败','info');
						return;
					}
					$.messager.alert('提示','文章关联人群成功','info');
				});
			}
			function releaseOperate(){
		    	var rows = $("#tt").datagrid('getSelections');
		        if(rows.length == 0){
		        	$.messager.alert('提示','请选择发布记录','info');
		            return;
		        }
		        var parameter = '';
		        var rows = $("#tt").datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					parameter = parameter + 'selections=' + rows[i].id + "&";
				}
		        var url = '<s:url namespace="/document/article" action="prereleases"/>';
		        $.post(url, parameter, function(data){
			        if (data == 'system-false'){
			        	$.messager.alert('提示','文章发布失败','info');
			        }else if (data == 'true'){
				        $("#tt").datagrid('clearSelections');
				        articleReload();
				        $.messager.alert('提示','文章发布成功','info');
			        }
			        return;
		        });
				return false;
			}
			function pubOperate(){
				var url = '<s:url namespace="/document/article" action="pubArticle"/>';
				url = url + "?channelId=" + channelId;
				$.post(url, '', function(data){
					if (data == 'system-false'){
						$.messager.alert('提示','系统错误','info');
					}else if (data == 'accessdenied'){
						$.messager.alert('提示','没有发布权限','info');
					}else{
						$.messager.alert('提示','发布成功','info');
					}
				});
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
			}
		</script>
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
        <div id="citizen-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
               		<table id="tt4" fit="true"></table>
                	<!-- <iframe id="editifr_citizen"  name="editifr_citizen" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe> -->
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:addCitizen();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#citizen-window').window('close');">取消</a>
                </div>
            </div>
        </div>
	</body>
</html>