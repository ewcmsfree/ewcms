<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>站点专栏</title>
		<s:include value="../../taglibs.jsp"/>
		<script>
			//站点专栏目录树初始
			var selectedNode;
			$(function(){
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url action="treePub"/>',
					onDblClick:function(node){
						if(node.attributes.maxpermission<16){
		    	    		$.messager.alert('提示','您不具有该操作权限');
		    	    		return false;
						}
						selectedNode = node;
						var url='<s:url action="edit"/>';
						url = url + "? channelVo.id=" + node.id;
						$("#editifr").attr('src',url);
					}
				});
			});

			//添加站点专栏
			function addChannel(){
				//判断是否选择了专栏
    			var node = getSelectNode();
    			if(!node) return;
				if(node.attributes.maxpermission<8){
    	    		$.messager.alert('提示','您不具有该操作权限');
    	    		return false;
				}    			
    	    	//添加专栏 			
				$.messager.prompt(node.text, '请输入专栏名', function(r){
					if (r){
			            $.post('<s:url action="add"/>',{'channelVo.id':node.id,'channelVo.name':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','专栏添加失败');
			    	    		return;
				            }
							$('#tt2').tree('append',{
								parent: node.target,
								data:[{
									id:data,
									iconCls:"icon-folder",
									text:r,
									attributes:{
										maxpermission:64
									}
								}]
							});	
							$('#tt2').tree('expand',node.target);	
			    	    });						
					}
				});
			}
			
			//重命名站点专栏
			function renameChannel(){
				//判断是否选择了专栏
    			var node = getSelectNode();
    			if(!node) return;
				if(node.attributes.maxpermission<16){
    	    		$.messager.alert('提示','您不具有该操作权限');
    	    		return false;
				}    	    	 		
    	    	//重命名专栏 			
				$.messager.prompt(node.text, '请修改专栏名', function(r){
					if (r){
			            $.post('<s:url action="rename"/>',{'channelVo.id':node.id,'channelVo.name':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','专栏重命名失败');
			    	    		return;
				            }
				            node.text = r;
							$('#tt2').tree('update',node);	
			    	    });						
					}
				});
			}
						
			//删除站点专栏
			function delChannel(){
				//判断是否选择了专栏
    			var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许删除该专栏');
					 return;
				}   
				if(node.attributes.maxpermission<32){
    	    		$.messager.alert('提示','您不具有该操作权限');
    	    		return false;
				}				  			
    			$.messager.confirm('', '确认要删除 ' + node.text + ' 专栏?', function(r){
    				if (r){
    	    	    	//删除专栏 			
    		            $.post('<s:url action="del"/>',{'channelVo.id':node.id},function(data){
    			            if(data == 'false'){
    		    	    		$.messager.alert('提示','专栏不能删除');
    		    	    		return;
    			            }
    						$('#tt2').tree('remove',node.target);	
    		    	    });	
    				}
    			}); 
			}
							
			//重载站点专栏目录树
			function channelTreeLoad(){
				try{
					opobj.innerHTML = opobj.innerHTML.replace('撤消','剪切');
					opobj = "";
				}catch(e){}				
				$('#tt2').tree('reload');
			}
			
			var cutNode,parentNode,opobj,popNode;
			function cutChannel(obj){
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    				//判断是否选择了操作站点
        			var node = getSelectNode();
        			if(!node) return;
    				var rootnode = $('#tt2').tree('getRoot');
    				if(rootnode.id == node.id){
    					$.messager.alert('提示','不允许剪切该专栏');
    					 return;
    				}         			
        			$.messager.confirm('', '确认要剪切 ' + node.text + '专栏吗?', function(r){
        				if (r){
        	    			parentNode = $('#tt2').tree('getParent',node.target);
        	    			cutNode = node;
        	    			popNode = $('#tt2').tree('pop',node.target);
        					obj.innerHTML = obj.innerHTML.replace('剪切','撤消');
        				}
        			}); 
    			}
    			else{
					$('#tt2').tree('append',{
						parent: parentNode.target,
						data:[popNode]
					});	
    				clearCut(obj);
    			}	
    			opobj = obj;	
			}
			
			function clearCut(obj){
				obj.innerHTML = obj.innerHTML.replace('撤消','剪切');
				cutNode = "";
				parentNode = "";
				opobj = ""; 
				popNode = ""; 
			}
			
			function parseChannel(){
				//判断是否选择了操作模板
    			var node = getSelectNode();
    			if(!node) return;
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    	    		$.messager.alert('提示','请先剪切专栏');		    	    			    	    		
    	    		return;
    			}
    	    	try{
	    	    	if(cutNode.id == node.id || parentNode.id == node.id){
	    	    		$.messager.alert('提示','不能粘到同一 录和父目录下');	    	    		
	    	    		return;
	    	    	}
	    	    	//移动专栏 			
		            $.post('<s:url action="moveto"/>',{'channelVo.id':cutNode.id,'channelVo.parent.id':node.id},function(data){
			            if(data == 'false'){
		    	    		$.messager.alert('提示','专栏移动失败');
		    	    		return;
			            }
						$('#tt2').tree('append',{
							parent: node.target,
							data:[popNode]
						});	
						$('#tt2').tree('expand',node.target);				            
			            clearCut(opobj);
		    	    });	
    	    	}catch(err){}				
			}
			
			function getSelectNode(){
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined')
    	    	{
    	    		$.messager.alert('提示','请选择要操作的专栏');
    	    		return false;
    	    	}				
				return node;
			}
			function loadHomePage(){
				$("#editifr").attr('src','<s:url action="edit"/>');
			}
			
			function editChannel(){
				//判断是否选择了操作专栏
    			var node = getSelectNode();
    			if(!node) return;	
				if(node.attributes.maxpermission<16){
    	    		$.messager.alert('提示','您不具有该操作权限');
    	    		return false;
				}
				var url='<s:url action="edit"/>';
				url = url + "? channelVo.id=" + node.id;
				$("#editifr").attr('src',url);  						
			}
			
			function upChannel(){
				//判断是否选择了操作模板
    			var node = getSelectNode();
    			if(!node) return;
    			if(node.attributes.maxpermission<16){
    	    		$.messager.alert('提示','您不具有该操作权限');
    	    		return false;
				}
    			var rootnode = $('#tt2').tree('getRoot');
    			if(rootnode.id == node.id){
					$.messager.alert('提示','不允许移动站点');
					 return;
				}
    			var parentNode = $('#tt2').tree('getParent',node.target);
    			  
    			$.post('<s:url action="up"/>',{'channelId':node.id,'parentId':parentNode.id},function(data){
			    	if(data == 'false'){
		    	    	$.messager.alert('提示','专栏上移动失败');
		    	    	return;
			        }
		    	});
			}
			
			function downChannel(){
				//判断是否选择了操作模板
    			var node = getSelectNode();
    			if(!node) return;
    			if(node.attributes.maxpermission<16){
    	    		$.messager.alert('提示','您不具有该操作权限');
    	    		return false;
				}
    			var rootnode = $('#tt2').tree('getRoot');
    			if(rootnode.id == node.id){
					$.messager.alert('提示','不允许移动站点');
					 return;
				}
    			var parentNode = $('#tt2').tree('getParent',node.target);
    			  
    			$.post('<s:url action="down"/>',{'channelId':node.id,'parentId':parentNode.id},function(data){
			    	if(data == 'false'){
		    	    	$.messager.alert('提示','专栏下移动失败');
		    	    	return;
			        }
		    	});
			}
			
			//操作菜单初始
			$(function(){
				$('#opmenu').click(function(e){
					$('#treeopmenu').menu('show', {
						left: e.pageX-20,
						top: e.pageY+12
					});
					return false;
				});
			});
		</script>		
	</head>
	<body class="easyui-layout" onload="loadHomePage();">
		<div region="west"  title='<label style="cursor:pointer;" onclick="channelTreeLoad();"><img src="<s:url value="/ewcmssource/image/refresh.png" />" style="vertical-align: middle;">专栏库</label>&nbsp;&nbsp;&nbsp;&nbsp;<label id="opmenu" style="cursor:pointer;"><font style="color:black;">操作<img src="<s:url value="/ewcmssource/easyui/themes/default/images/menu_downarrow.png"/>" style="vertical-align: middle;"/></font></label>' split="true" style="width:180px;">		
			<ul id="tt2"></ul>
		</div>
		<div region="center"  style="overflow:auto;">
			<iframe id="editifr"  name="editifr" class="editifr"></iframe>
		</div>
	    
		<div id="treeopmenu" class="easyui-menu" style="width:110px; padding:4px 0 8px 0;">
			<div icon="icon-add" onclick="addChannel();">创建</div>
			<div icon="icon-undo" onclick="renameChannel();">重命名</div>
			<div icon="icon-edit" onclick="editChannel();">编辑</div>	   
			<div icon="icon-remove" onclick="delChannel();">删除</div>   
			<div icon="icon-ok" onclick="parseChannel();">粘贴</div>	
			<div icon="icon-cut" onclick="cutChannel(this);">剪切</div>	
			<div icon="icon-up" onclick="upChannel();">上移</div>
			<div icon="icon-down" onclick="downChannel();">下移</div>
		</div>	    	
	</body>
</html>