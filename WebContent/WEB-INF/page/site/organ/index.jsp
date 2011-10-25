<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>机构管理</title>	
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>          
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>		
		<script>
			//机构目录树初始
			$(function(){
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url action="tree"/>',
					onDblClick:function(node){
						var rootnode = $('#tt2').tree('getRoot');
						if(rootnode.id == node.id)    	    	{
		    	    		$.messager.alert('提示','不能操作根机构');
		    	    		return false;
		    	    	}						
							var url='<s:url action="edit"/>';
							url = url + "? organVo.id=" + getNodeId(node);
							$("#editifr").attr('src',url);
					}
				});
			});
	
			function getNodeId(node){
				return node.id==null?'':node.id;
			}
			//添加机构
			function addOrgan(){
				//判断是否选择了机构
    			var node = getSelectNode();
    			if(!node) return;
    	    	//添加机构			
				$.messager.prompt(node.text, '请输入机构名', function(r){
					if (r){
			            $.post('<s:url action="add"/>',{'organVo.id':getNodeId(node),'organVo.name':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','机构添加失败');
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
			
			//重命名机构
			function renameOrgan(){
				//判断是否选择了操作机构
    			var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id)    	    	{
    	    		$.messager.alert('提示','不能操作根机构');
    	    		return false;
    	    	}   	    	 		
    	    	//重命名专栏 			
				$.messager.prompt(node.text, '请修改机构名', function(r){
					if (r){
			            $.post('<s:url action="rename"/>',{'organVo.id':getNodeId(node),'organVo.name':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','机构重命名失败');
			    	    		return;
				            }
				            node.text = r;
							$('#tt2').tree('update',node);	
			    	    });						
					}
				});
			}
						
			//删除机构
			function delOrgan(){
				//判断是否选择了操作机构
    			var node = getSelectNode();
    			if(!node) return;  	
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id)    	    	{
    	    		$.messager.alert('提示','不能操作根机构');
    	    		return false;
    	    	}    					
    			$.messager.confirm('', '确认要删除 ' + node.text + ' 机构吗?', function(r){
    				if (r){
    	    	    	//删除机构			
    		            $.post('<s:url action="del"/>',{'organVo.id':getNodeId(node)},function(data){
    			            if(data == 'false'){
    		    	    		$.messager.alert('提示','机构不能删除');
    		    	    		return;
    			            }
    						$('#tt2').tree('remove',node.target);	
    		    	    });	
    				}
    			}); 
			}
							
			//重载机构目录树
			function organTreeLoad(){
				try{
					opobj.innerHTML = opobj.innerHTML.replace('撤消','剪切');
					opobj = "";
				}catch(e){}				
				$('#tt2').tree('reload');
			}
			
			var cutNode,parentNode,opobj,popNode;
			function cutOrgan(obj){
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    				//判断是否选择了操作机构
        			var node = getSelectNode();
        			if(!node) return;   
    				var rootnode = $('#tt2').tree('getRoot');
    				if(rootnode.id == node.id)    	    	{
        	    		$.messager.alert('提示','不能操作根机构');
        	    		return false;
        	    	}        			      			
        			$.messager.confirm('', '确认要剪切 ' + node.text + '机构吗?', function(r){
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
			
			function parseOrgan(){
				//判断是否选择了操作模板
    			var node = getSelectNode();
    			if(!node) return;
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    	    		$.messager.alert('提示','请先剪切机构');		    	    			    	    		
    	    		return;
    			}
    	    	try{
	    	    	if(cutNode.id == node.id || parentNode.id == node.id){
	    	    		$.messager.alert('提示','不能粘到同一 录和父目录下');	    	    		
	    	    		return;
	    	    	}
	    	    	//移动专栏 			
		            $.post('<s:url action="moveto"/>',{'organVo.id':getNodeId(cutNode),'organVo.parent.id':getNodeId(node)},function(data){
			            if(data == 'false'){
		    	    		$.messager.alert('提示','机构移动失败');
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
    	    		$.messager.alert('提示','请选择要操作的机构');
    	    		return false;
    	    	}
				return node;
			}
			
			function loadHomePage(){
				$("#editifr").attr('src','<s:url action="edit"/>');
			}
			
			function editOrgan(){
				//判断是否选择了操作机构
    			var node = getSelectNode();
    			if(!node) return;	
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id)    	    	{
    	    		$.messager.alert('提示','不能操作根机构');
    	    		return false;
    	    	}    			
				var url='<s:url action="edit"/>';
				url = url + "? organVo.id=" + getNodeId(node);
				$("#editifr").attr('src',url);    						
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
		<div region="west"  title='<label style="cursor:pointer;" onclick="organTreeLoad();" ><img src="<s:url value="/source/image/refresh.png" />" style="vertical-align: middle;">机构库</label>&nbsp;&nbsp;&nbsp;&nbsp;<label id="opmenu" style="cursor:pointer;"><font style="color:black;">操作<img src="<s:url value="/source/theme/default/images/menu_downarrow.png"/>" style="vertical-align: middle;"/></font></label>' split="true" style="width:190px;">		
			<ul  id="tt2"></ul>
		</div>
		
		<div region="center"  style="overflow:hidden;">
			<iframe id="editifr"  name="editifr" class="editifr"></iframe>
		</div>
	    
		<div id="treeopmenu" class="easyui-menu" style="width:100px; padding:4px 0 8px 0;">
			<div icon="icon-add" onclick="addOrgan();">添加</div>
			<div icon="icon-undo" onclick="renameOrgan();">重命名</div>	   	
			<div icon="icon-edit" onclick="editOrgan();">编辑</div>	
			<div icon="icon-remove" onclick="delOrgan();">删除</div>	
			<!--  	
			<div icon="icon-ok" onclick="parseOrgan();">粘贴</div>	
			<div icon="icon-cut" onclick="cutOrgan(this);">剪切</div>	
			-->							   
		</div>	  
		  	
	</body>
</html>