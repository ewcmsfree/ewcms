<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板资源管理</title>	
		<s:include value="../../taglibs.jsp"/>
		<script>
			var folderparten = /^[0-9A-Za-z_]*$/;
			var fileparten = /^[a-zA-z0-9._]*$/;
			//资源目录树初始
			$(function(){
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url action="tree"/>',
					onDblClick:function(node){
		    			if(node.iconCls == ""){
							var url='<s:url action="edit"/>';
							url = url + "? sourceVo.id=" + getNodeId(node);
							$("#editifr").attr('src',url);
		    			}
					}
				});
			});

			function getNodeId(node){
				return node.id==null?'':node.id;
			}
			//添加资源
			function addSource(){
				//判断是否选择了资源
    			var node = getSelectNode();
    			if(!node) return;
    	    	//添加资源操作			
				$.messager.prompt(node.text, '请输入文件名称', function(r){
					if (r){
						if(!fileparten.exec(r)){
		    	    		$.messager.alert('提示','文件名由字母、数字、组成的文件');
		    	    		return;
						}
			            $.post('<s:url action="add"/>',{'sourceVo.parent.id':getNodeId(node),'sourceVo.name':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','新建资源失败');
			    	    		return;
				            }
				            $('#tt2').tree('append',{
								parent: node.target,
								data:[{
									id:data,
									iconCls:"",
									text:r
								}]
							});	
							$('#tt2').tree('expand',node.target);					            
			    	    });						
					}
				});
			}
			
			//添加文件夹
			function addFolder(){
				//判断是否选择了资源
    			var node = getSelectNode();
    			if(!node) return;
    	    	//添加资源文件夹操作			
				$.messager.prompt(node.text, '请输入文件夹名', function(r){
					if (r){
						if(!folderparten.exec(r)){
		    	    		$.messager.alert('提示','目录只能由字母、数字、下划线组成');
		    	    		return;
						}						
			            $.post('<s:url action="addFolder"/>',{'sourceVo.parent.id':getNodeId(node),'sourceVo.name':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','新建文件夹失败');
			    	    		return;
				            }
				            $('#tt2').tree('append',{
								parent: node.target,
								data:[{
									id:data,
									iconCls:"icon-folder",
									text:r
								}]
							});	
							$('#tt2').tree('expand',node.target);				            
			    	    });						
					}
				});
			}
						
			function renameSource(){
				//判断是否选择了操作资源
    			var node = getSelectNode();
    			if(!node) return;
    	    	 		
    	    	//重命名资源 		
				$.messager.prompt(node.text, '请修改名称', function(r){
					if (r){
						if(node.iconCls != ""){
							if(!folderparten.exec(r)){
			    	    		$.messager.alert('提示','目录只能由字母、数字、下划线组成');
			    	    		return;
							}
						}else{
							if(!fileparten.exec(r)){
			    	    		$.messager.alert('提示','文件名由字母、数字、组成的文件');
			    	    		return;
							}	
						}												
			            $.post('<s:url action="rename"/>',{'sourceVo.id':getNodeId(node),'sourceVo.name':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','重命名失败');
			    	    		return;
				            }
				            node.text = r;
							$('#tt2').tree('update',node);					            
			    	    });						
					}
				});
			}
						
			//删除资源
			function delSource(){
				//判断是否选择了操作资源
    			var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许删除该资源');
					 return;
				}     			
    			$.messager.confirm('', '确认要删除 ' + node.text + '资源吗?', function(r){
    				if (r){
    	    	    	//删除资源			
    		            $.post('<s:url action="del"/>',{'sourceVo.id':getNodeId(node)},function(data){
    			            if(data == 'false'){
    		    	    		$.messager.alert('提示','资源不能删除');
    		    	    		return;
    			            }
    			            $('#tt2').tree('remove',node.target);    			            
    		    	    });	
    				}
    			}); 
			}
							
			//重载资源目录树
			function sourceTreeLoad(){
				try{
					opobj.innerHTML = opobj.innerHTML.replace('撤消','剪切');
					opobj = "";
				}catch(e){}	
				$('#tt2').tree('reload');
			}
			
			function getSelectNode(){
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined')
    	    	{
    	    		$.messager.alert('提示','请选择操作项目');
    	    		return false;
    	    	}				
				return node;
			}
			function loadHomePage(){
				$("#editifr").attr('src','<s:url action="edit"/>');
			}

			var cutNode,parentNode,opobj,popNode;
			function cutSource(obj){
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    				//判断是否选择了操作模板
        			var node = getSelectNode();
        			if(!node) return;
    				var rootnode = $('#tt2').tree('getRoot');
    				if(rootnode.id == node.id){
    					$.messager.alert('提示','不允许剪切该模板');
    					 return;
    				}         			
        			$.messager.confirm('', '确认要剪切 ' + node.text + '模板吗?', function(r){
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
			function parseSource(){
				//判断是否选择了操作资源
    			var node = getSelectNode();
    			if(!node) return;
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    	    		$.messager.alert('提示','请先剪切资源');		    	    			    	    		
    	    		return;
    			}
    	    	try{
	    	    	if(cutNode.id == node.id || parentNode.id == node.id){
	    	    		$.messager.alert('提示','不能粘到同一 录和父目录下');	    	    		
	    	    		return;
	    	    	}
	    	    	//移动专栏 			
		            $.post('<s:url action="moveto"/>',{'sourceVo.id':getNodeId(cutNode),'sourceVo.parent.id':getNodeId(node)},function(data){
			            if(data == 'false'){
		    	    		$.messager.alert('提示','粘贴资源失败');
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

			function importSource(){
				//判断是否选择了操作资源
    			var node = getSelectNode();
    			if(!node) return;
    			//判断是否选择的是文件夹
    			if(node.iconCls == ""){
    				$.messager.alert('提示','请选择文件夹');	
    				return ;
    			}
				var url='<s:url action="import"/>?sourceVo.parent.id='+ getNodeId(node);
				$("#editifr").attr('src',url);    			
			}
			
			function editSource(){
				//判断是否选择了操作模板
    			var node = getSelectNode();
    			if(!node) return;
    			//判断是否选择的是文件夹
    			if(node.iconCls != ""){
    				$.messager.alert('提示','请选择文件');	
    				return ;
    			}			
				var url='<s:url action="edit"/>?sourceVo.id='+ getNodeId(node);
				$("#editifr").attr('src',url);    			
			}
			function importLoad(){
				$('#tt2').tree('reload');
			}
			function toTemplate(){
				window.location='<s:url value="/site/template/index.do"/>';
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

			function pubSource(){
				//判断是否选择了操作模板
    			var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不能发布根目录');
					 return;
				}   
	            $.post('<s:url action="pubsource"/>',{'sourceVo.id':getNodeId(node)},function(data){
		            if(data == 'false'){
	    	    		$.messager.alert('提示','资源发布失败');
	    	    		return;
		            }
		            $.messager.alert('提示','资源发布成功');
	    	    });
			}
		</script>		
	</head>
	<body class="easyui-layout" onload="loadHomePage();">
		<div region="west"  title='<label style="vertical-align: middle;cursor:pointer;" onclick="sourceTreeLoad();">资源库</label>&nbsp;&nbsp;<label id="opmenu" style="cursor:pointer;"><font style="color:black;">操作<img src="<s:url value="/ewcmssource/easyui/themes/default/images/menu_downarrow.png"/>" style="vertical-align: middle;"/></font></label><label style="cursor:pointer;" onclick="toTemplate();"><font style="color:black;">模板库<img src="<s:url value="/ewcmssource/easyui/themes/default/images/menu_rightarrow.png"/>" style="vertical-align: middle;"/></font></label>' split="true" style="width:190px;">		
			<ul  id="tt2"></ul>
		</div>
		<div region="center"  style="overflow:auto;">
			<iframe id="editifr"  name="editifr" class="editifr"></iframe>
		</div>
		<div id="treeopmenu" class="easyui-menu" style="width:100px; padding:4px 0 8px 0;">
			<div icon="icon-add" onclick="addSource();">新建资源</div>
			<div icon="icon-folder" onclick="addFolder();">文件夹</div>
			<div icon="icon-undo" onclick="renameSource();">重命名</div>
			<div icon="icon-edit" onclick="editSource();">编辑</div>	
			<div icon="icon-remove" onclick="delSource();">删除</div>
			<div icon="icon-redo" onclick="importSource();">导入</div>	
			<div icon="icon-ok" onclick="parseSource();">粘贴</div>
			<div icon="icon-cut" onclick="cutSource(this);">剪切</div>
			<div icon="icon-table-pub" onclick="pubSource();">发布资源</div>
		</div>
	</body>
</html>