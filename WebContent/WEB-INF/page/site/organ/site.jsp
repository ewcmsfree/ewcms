<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>站点管理</title>	
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/css/ewcms.css"/>'>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>          
        <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>	
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
		</head>
		<s:if test="organVo.parent!=null && organVo.parent.homeSiteId == null">
			<body>
				上级机构还未创建主站，本机构目前还不能创建站点。
			</body>
		</s:if>
		<s:else>
		<script>
			//站点目录树初始
			$(function(){
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url value="/site/setup/tree.do"/>?siteVo.id=<s:property value="organVo.parent.homeSiteId"/>&siteVo.organ.id=<s:property value="organVo.id"/>',
					onDblClick:function(node){
						var rootnode = $('#tt2').tree('getRoot');
						if(rootnode.id == node.id){
		    				$('#tt2').tree('reload');     			
		    			}
					}					
				});

				$('#tt2').bind('contextmenu',function(e){
					$('#treeopmenu').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
					return false;
				});		

				ewcmsBOBJ = new EwcmsBase();
				ewcmsOOBJ = new EwcmsOperate();	
			});
			function getNodeId(node){
				return node.id==null?'':node.id;
			}
			//添加站点
			function addSite(){
				//判断是否选择了操作
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined')
    	    	{
    	    		$.messager.alert('提示','请选择要操作的站点');
    	    		return false;
    	    	}
    			
    	    	//添加站点			
				$.messager.prompt(node.text, '请输入站点名', function(r){
					if (r){
			            $.post('<s:url value="/site/setup/add.do"/>',{'siteVo.id':getNodeId(node),'siteVo.siteName':r,'siteVo.organ.id':<s:property value="organVo.id"/>},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','站点创建失败');
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
			
			//设置机构主站
			function setSite(){
				//判断是否选择了站点
    			var node = getSelectNode();
    			if(!node) return; 
    			if(node.id == document.all.homeSiteId.value){
    	    		$.messager.alert('提示','该机构已是主站');
    	    		return;        			
    			}    						
    	    	//设置站点			
			            $.post('<s:url action="setsite"/>',{'organVo.homeSiteId':getNodeId(node),'organVo.id':<s:property value="organVo.id"/>},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','主站设置失败！');
			    	    		return;
				            }else{
				            	loadMainSite(node.id);
				            }
			    	    });
			}
			
			function getSelectNode(){
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined')
    	    	{
    	    		$.messager.alert('提示','请选择要操作的站点');
    	    		return false;
    	    	}	
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','非机构站点不能操作');
					 return false;
				}     	    				
				return node;
			}
			
			function loadMainSite(id){
				try{
					var node = $('#tt2').tree('find',id);
					document.all.homeSiteId.value = id;
					document.getElementById('mainsitelabel').innerHTML = node.text;
				}catch(err){}
			}
			
			//重命名站点
			function renameSite(){
				//判断是否选择了操作站点
    			var node = getSelectNode();
    			if(!node) return;
    	    			
    	    	//重命名专栏 			
				$.messager.prompt(node.text, '请修改站点名', function(r){
					if (r){
			            $.post('<s:url value="/site/setup/rename.do"/>',{'siteVo.id':getNodeId(node),'siteVo.siteName':r},function(data){
				            if(data == 'false'){
			    	    		$.messager.alert('提示','站点重命名失败');
			    	    		return;
				            }
				            
				            node.text = r;
							$('#tt2').tree('update',node);	
							if(node.id == document.all.homeSiteId.value){
								loadMainSite(node.id);
							}
			    	    });						
					}
				});
			}
						
			//删除站点专栏
			function delSite(){
				//判断是否选择了操作站点
    			var node = getSelectNode();
    			if(!node) return;   
    			if(node.id == document.all.homeSiteId.value){
    	    		$.messager.alert('提示','机构主站不能删除');
    	    		return;        			
    			}
    			$.messager.confirm('', '确认要删除 ' + node.text + ' 站点吗?', function(r){
    				if (r){
    	    	    	//删除专栏 			
    		            $.post('<s:url value="/site/setup/del.do"/>',{'siteVo.id':getNodeId(node)},function(data){
    			            if(data == 'false'){
    		    	    		$.messager.alert('提示','站点不能删除');
    		    	    		return;
    			            }
    						$('#tt2').tree('remove',node.target);	
    		    	    });	
    				}
    			}); 
			}
			
			var cutNode,parentNode,opobj,popNode;
			function cutSite(obj){
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    				//判断是否选择了操作站点
        			var node = getSelectNode();
        			if(!node) return;         			
        			$.messager.confirm('', '确认要剪切 ' + node.text + '站点吗?', function(r){
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
			
			function parseSite(){
				//判断是否选择了操作模板
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined')
    	    	{
    	    		$.messager.alert('提示','请选择要操作的站点');
    	    		return false;
    	    	}
    			if(typeof(cutNode) == 'undefined' || cutNode == ''){
    	    		$.messager.alert('提示','请先剪切站点');		    	    			    	    		
    	    		return;
    			}
    	    	try{
	    	    	if(cutNode.id == node.id || parentNode.id == node.id){
	    	    		$.messager.alert('提示','不能粘到同一 录和父目录下');	    	    		
	    	    		return;
	    	    	}
	    	    	//移动专栏 			
		            $.post('<s:url value="/site/setup/moveto.do"/>',{'siteVo.id':getNodeId(cutNode),'siteVo.parent.id':getNodeId(node)},function(data){
			            if(data == 'false'){
		    	    		$.messager.alert('提示','站点移动失败');
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

			function configSite(){
				//判断是否选择了操作机构
    			var node = getSelectNode();
    			if(!node) return;	  			
				var url='<s:url action="configSite"/>';
				url = url + "? siteVo.id=" + getNodeId(node);	
				$("#editifr").attr('src',url); 
			    openWindow('#edit-window',{width:600,height:400,top:10,left:10,title:'属性设置'});				   						
			}

			function serverSite(){
				//判断是否选择了操作机构
    			var node = getSelectNode();
    			if(!node) return;	  			
				var url='<s:url action="serverSite"/>';
				url = url + "? siteVo.id=" + getNodeId(node);
				$("#serverifr").attr('src',url);
			    openWindow('#server-window',{width:600,height:400,top:10,left:10,title:'发布设置'});	
			}
		</script>		

	<body class="easyui-layout">
		<div region="center"  style="overflow:hidden;">
			<table class="formtable" align="center">
				<tr>
					<td><u><s:property value="organVo.name"/>&nbsp;&nbsp;</u>机构主站：<strong><label id="mainsitelabel"><s:property value="siteVo.siteName"/></label></strong></td>
				</tr>
				<tr><td><ul  id="tt2"></ul></td></tr>
			</table>		
		</div>
		<div id="treeopmenu" class="easyui-menu" style="width:100px; padding:4px 0 8px 0;">
			<div icon="icon-add" onclick="addSite();">创建站点</div>	
			<div icon="icon-redo" onclick="setSite();">设为主站</div>
			<div icon="icon-reload" onclick="configSite();">属性设置</div>
			<div icon="icon-reload" onclick="serverSite();">发布设置</div>
			<div icon="icon-undo" onclick="renameSite();">重命名</div>	   	
			<div icon="icon-remove" onclick="delSite();">删除</div>								
			<div icon="icon-ok" onclick="parseSite();">粘贴</div>	
			<div icon="icon-cut" onclick="cutSite(this);">剪切</div>															   
		</div>
		<input type="hidden" value="<s:property value="organVo.homeSiteId"/>" name="homeSiteId">	
		
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;属性设置" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" frameborder="0" width=100% height=335 style="overflow:hidden;"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick=" $('#edit-window').window('close');">关闭窗口</a>
                </div>
            </div>
        </div>
        <div id="server-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;发布设置" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="serverifr"  name="serverifr" frameborder="0" width=100% height=335 style="overflow:hidden;"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator('serverifr')">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick=" $('#server-window').window('close');">关闭窗口</a>
                     <a class="easyui-linkbutton" icon="icon-reload" href="javascript:void(0)" onclick=" window.frames['serverifr'].testServer();">连接测试</a>
                </div>
            </div>
        </div>	        		
	</body>
	</s:else>
</html>