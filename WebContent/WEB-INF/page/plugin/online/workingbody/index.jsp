<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>领导基本信息</title>	
		<s:include value="../../../taglibs.jsp"/>
		<script>
			var workingBodyId = 0;
			$(function(){
				//基本变量初始
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/plugin/online/workingbody" action="tree"/>?channelId=' + $('#channelId').val() + '&isMatter=true',
					onClick:function(node){
						workingBodyId = node.id
						var url='<s:url namespace="/plugin/online/workingbody" action="queryChannelStatus"/>';
						url = url + '?channelId=' + $('#channelId').val() + '&status=' + node.attributes.type + '&workingBodyId=' + node.id;
						$("#editifr_info").attr('src',url);
					}
				});
				$('#tt2').bind('contextmenu',function(e){
					$('#treeopmenu').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
					return false;
				});
				$('#cc_matter').combobox({
				    url:'<s:url namespace="/plugin/online/workingbody" action="findAllMatter"/>',
				    valueField:'id',
				    textField:'name'
				});
				$('#cc_citizen').combobox({
				    url:'<s:url namespace="/plugin/online/workingbody" action="findAllCitizen"/>',
				    valueField:'id',
				    textField:'name'
				});
				$('#systemtab').tabs({
					onSelect:function(title){
						if (title == '信息编辑'){
							//$('#tt2').tree('reload');
						}else if (title == '事项基本信息'){
							$('#edit_matter').attr('src','<s:url namespace="/plugin/online/matter" action="index"/>');
						}
					}
				});
			});

			function addWorkingBody(){
    			var node = getSelectNode();
    			if(!node) return;
    			var type = node.attributes.type;
    			if (type == 'component' || type == 'workingbody'){
					$.messager.prompt(node.text, '请输入专栏名', function(r){
						if (r){
				            $.post('<s:url namespace="/plugin/online/workingbody" action="addWorkingBody"><s:param name="channelId" value="channelId"></s:param></s:url>',{'parentId':node.id,'workingBodyName':r},function(data){
					            if(data == 'matter'){
									$.messager.alert('提示','人员信息下不能添加事项主体','info');
									return;
						        }
						        if (data == 'article'){
									$.messager.alert('提示','下不能添加事项主体','info');
									return;
							    }
					            if(data == 'false'){
				    	    		$.messager.alert('提示','职务添加失败','info');
				    	    		return;
					            }
					            $('#tt2').tree('reload');
					            $.messager.alert('提示','添加事项主体成功','info');
				    	    });						
						}
					});
    			}else{
        			$.messager.alert('提示','只能在组件下添加事项主体','info');
        			return;
    			}
			}
			
			function renameWorkingBody(){
				//判断是否选择了专栏
    			var node = getSelectNode();
    			if(!node) return;

    			var type = node.attributes.type;
    			if (type == 'workingbody'){ 		
	    	    	//重命名专栏 			
					$.messager.prompt(node.text, '请修改专栏名', function(r){
						if (r){
				            $.post('<s:url namespace="/plugin/online/workingbody" action="renameWorkingBody"><s:param name="channelId" value="channelId"></s:param></s:url>',{'workingBodyId':node.id,'workingBodyName':r},function(data){
					            if(data == 'false'){
				    	    		$.messager.alert('提示','专栏重命名失败','info');
				    	    		return;
					            }
					            node.text = r + node.attributes.organ;
								$('#tt2').tree('update',node);
				    	    });						
						}
					});
    			}else{
        			$.messager.alert('提示','只能重命名职务','info');
        			return;
    			}
			}
						
			//删除站点专栏
			function delWorkingBody(){
				//判断是否选择了专栏
    			var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许删除该专栏','info');
					 return;
				}
    			var type = node.attributes.type;
    			if (type == 'workingbody'){ 		
	    			$.messager.confirm('提示', '确认要删除 ' + node.text + ' 专栏?', function(r){
	    				if (r){
	    	    	    	//删除专栏 			
	    		            $.post('<s:url namespace="/plugin/online/workingbody" action="delWorkingBody"><s:param name="channelId" value="channelId"></s:param></s:url>',{'workingBodyId':node.id},function(data){
	    			            if(data == 'false'){
	    		    	    		$.messager.alert('提示','职务不能删除','info');
	    		    	    		return;
	    			            }
	    						$('#tt2').tree('remove',node.target);
	    		    	    });	
	    				}
	    			}); 
    			}else{
        			$.messager.alert('提示','只能删除职务','info');
        			return;
    			}
			}
							
			//重载站点专栏目录树
			function channelTreeLoad(){
				$('#tt2').tree('reload');
			}
			
			function getSelectNode(){
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined')
    	    	{
    	    		$.messager.alert('提示','请选择要操作的专栏','info');
    	    		return false;
    	    	}
    	    	workingBodyId = node.id;
				return node;
			}
			
			function selMatter(){
    			var node = getSelectNode();
    			if(!node) return;
    			var type = node.attributes.type;
    			if (type == 'workingbody'){
	    			$('#cc_matter').combobox('reload');
	    			openWindow("#matter-window",{width:500,height:90,top:80,left:260,title:'事项选择'}); 			
    			}else{
    				$.messager.alert('提示','只能在事项主体下添加事项信息','info');
    				return;
    			}
			}

			function addMatter(){
				var val = $('#cc_matter').combobox('getValues');
				if (val == ""){
					$.messager.alert('提示','请选择事项信息','info');
    	    		return;
				}
				$.post('<s:url namespace="/plugin/online/workingbody" action="addMatterToWorkingBody"><s:param name="channelId" value="channelId"></s:param></s:url>',{'selectIds':val.toString(),'parentId':workingBodyId},function(data){
					 if(data == 'false'){
		    	    	$.messager.alert('提示','不能在事项信息下再选择事项信息','info');
		    	    	return;
			         }
			         if (data == 'system-false'){
				        $.messager.alert('提示','添加事项信息不成功','info');
				        return; 
			         }
			         $('#tt2').tree('reload');
			         $.messager.alert('提示','选择事项信息成功','info');
				});
			}

			function removeMatter(){
				var node = getSelectNode();
				var type = node.attributes.type;
    			if (type == 'matter'){
        			var node_parent = $('#tt2').tree('getParent',node.target);
        			type = node_parent.attributes.type;
        			if (type == 'workingbody'){
        				$.post('<s:url namespace="/plugin/online/workingbody" action="removeMatterFromWorkingBody"><s:param name="channelId" value="channelId"></s:param></s:url>',{'matterId':node.id,'parentId':node_parent.id},function(data){
            				if (data == 'true'){
            					$.messager.alert('提示','移除事项信息成功','info');
            					$('#tt2').tree('reload');
                				return;
            				}else{
            					$.messager.alert('提示','移除事项信息失败','info');
                				return;
            				}
        				});
        			}else{
        				$.messager.alert('提示','移除事项信息失败','info');
        				return;
        			}
    			}else{
    				$.messager.alert('提示','只能移除事项信息','info');
    				return;
    			}
			}
			
			function selCitizen(){
    			var node = getSelectNode();
    			if(!node) return;
    			var type = node.attributes.type;
    			if (type == 'matter'){
	    			$('#cc_citizen').combobox('reload');
	    			openWindow("#citizen-window",{width:500,height:90,top:80,left:260,title:'公民选择'}); 			
    			}else{
    				$.messager.alert('提示','只能在事项信息下添加公民信息','info');
    				return;
    			}
			}

			function addCitizen(){
				var val = $('#cc_citizen').combobox('getValues');
				if (val == ""){
					$.messager.alert('提示','请选择公民信息','info');
    	    		return;
				}
				$.post('<s:url namespace="/plugin/online/workingbody" action="addCitizenToMatter"/>',{'selectIds':val.toString(),'matterId':workingBodyId},function(data){
					 if(data == 'false'){
		    	    	$.messager.alert('提示','不能在事项主体下再选择公民信息','info');
		    	    	return;
			         }
			         if (data == 'system-false'){
				        $.messager.alert('提示','添加公民信息不成功','info');
				        return; 
			         }
			         $('#tt2').tree('reload');
			         $.messager.alert('提示','选择公民信息成功','info');
				});
			}

			function removeCitizen(){
				var node = getSelectNode();
				var type = node.attributes.type;
    			if (type == 'matter'){
        			$.post('<s:url namespace="/plugin/online/workingbody" action="removeCitizenFromMatter"/>',{'matterId':node.id,'matterId':node.id},function(data){
            			if (data == 'false'){
            				$.messager.alert('提示','移除公民信息失败','info');
                			return;
            			}
        				$.messager.alert('提示','移除公民信息成功','info');
        				$('#tt2').tree('reload');
        			});
    			}else{
    				$.messager.alert('提示','只能公民事项信息','info');
    				return;
    			}
			}
			
			function pubLeadingWindow(){
				$.post('<s:url namespace="/plugin/online/workingbody" action="pubLeadingWindow"/>', {'channelId':$('#channelId').val()}, function(data){
					if (data == 'true'){
						$.messager.alert("提示","发布成功","info");
						return;
					}else if (data == 'false'){
						$.messager.alert("提示","发布失败","info");
						return;
					}
				});
			}

			function upWorkingBody(){
				var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许移动该专栏','info');
					 return;
				}
    			var type = node.attributes.type;
    			if (type == 'workingbody'){ 
	    			$.messager.confirm('提示', '确认要上移 ' + node.text + ' 专栏?', function(r){
	    				if (r){
	    					var node_parent = $('#tt2').tree('getParent',node.target);
	    		            $.post('<s:url namespace="/plugin/online/workingbody" action="upWorkingBody"><s:param name="channelId" value="channelId"></s:param></s:url>',{'parentId':node_parent.id,'workingBodyId':node.id},function(data){
	    			            if(data == 'false'){
	    		    	    		$.messager.alert('提示','上移失败','info');
	    		    	    		return;
	    			            }
	    			            $('#tt2').tree('reload');
	    		    	    });	
	    				}
	    			}); 
    			}else{
        			$.messager.alert('提示','只能移动办事','info');
        			return;
    			}
			}

			function downWorkingBody(){
				var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许移动该专栏','info');
					 return;
				}
    			var type = node.attributes.type;
    			if (type == 'workingbody'){ 		
	    			$.messager.confirm('提示', '确认要下移 ' + node.text + ' 专栏?', function(r){
	    				if (r){
	    					var node_parent = $('#tt2').tree('getParent',node.target);
	    		            $.post('<s:url namespace="/plugin/online/workingbody" action="downWorkingBody"><s:param name="channelId" value="channelId"></s:param></s:url>',{'parentId':node_parent.id,'workingBodyId':node.id},function(data){
	    			            if(data == 'false'){
	    		    	    		$.messager.alert('提示','下移失败','info');
	    		    	    		return;
	    			            }
	    			            $('#tt2').tree('reload');
	    		    	    });	
	    				}
	    			}); 
    			}else{
        			$.messager.alert('提示','只能移动办事','info');
        			return;
    			}
			}

			var selMode = "";
			var tt2_node_id = -1;
			function addOrgan(){
				var node = getSelectNode();
    			if(!node) return;
    			var type = node.attributes.type;
    			tt2_node_id = node.id;
    			if (type == 'workingbody'){
        			selMode = 'workingbody';
    				$('#tt3').tree({
    					checkbox: true,
    					cascadeCheck:false,
    					url: '<s:url namespace="/site/organ" action="tree"></s:url>'
    				});
					openWindow("#organ-window",{width:800,height:400,top:80,left:260,title: node.text + ' - 选择组织'});
    			}else if (type == 'matter'){
        			selMode = 'matter';
    				$('#tt3').tree({
    					checkbox: false,
    					url: '<s:url namespace="/site/organ" action="tree"></s:url>'
    				});
					openWindow("#organ-window",{width:800,height:400,top:80,left:260,title:node.text + ' - 选择组织'});
    			}else{
    				tt2_node_id = -1;
        			selMode = '';
        			$.messager.alert('提示','只能对办事主体进行引用组织操作','info');
        			return;
    			}
			}

			function saveOrgan(){
				if (selMode == 'workingbody'){
					var nodes = $('#tt3').tree('getChecked');
						
					var parameter = 'workingBodyId=' + tt2_node_id;
		           	for(var i=0;i<nodes.length;i++){
		           		parameter = parameter + '&organIds=' + nodes[i].id;
		           	}
					
					$.post('<s:url namespace="/plugin/online/workingbody" action="addOrganToWorkingBody"/>',parameter,function(data){
						if(data == 'false'){
 		    	    		$.messager.alert('提示','引用织织失败','info');
 		    	    		return;
 			            }
						$.messager.alert('提示','引用组织成功','info');
						$('#tt2').tree('reload');
					});
				}else if (selMode == 'matter'){
					var node = $('#tt3').tree('getSelected');
					$.post('<s:url namespace="/plugin/online/workingbody" action="addOrganToMatter"/>',{'matterId':tt2_node_id,'organId':node.id},function(data){
						if(data == 'false'){
 		    	    		$.messager.alert('提示','引用组织失败','info');
 		    	    		return;
 			            }
						$.messager.alert('提示','引用组织成功','info');
						$('#tt2').tree('reload');
					 });
				}else{
					tt2_node_id = -1;
					selMode = '';
				}
			}

			function removeOrgan(){
				var node = getSelectNode();
    			if(!node) return;
    			var type = node.attributes.type;
    			if (type == 'workingbody'){
					$.post('<s:url namespace="/plugin/online/workingbody" action="removeOrganFromWorkingBody"/>',{'workingBodyId':node.id},function(data){
						if (data == 'false'){
							$.messager.alert('提示','移除组织失败','info');
							return;
						}
						$('#tt2').tree('reload');
					});
    			}else if (type == 'matter'){
					$.post('<s:url namespace="/plugin/online/workingbody" action="removeOrganFromMatter"/>',{'matterId':node.id},function(data){
						if (data == 'false'){
							$.messager.alert('提示','移除组织失败','info');
							return;
						}
						$('#tt2').tree('reload');
					});
    			}
			}
		</script>		
	</head>
	<body>
		<div class="easyui-tabs" id="systemtab" border="false" fit="true">
			<div title="信息编辑" style="padding: 0px;">
				<div class="easyui-layout" fit="true">
					<div region="west"  title='<img src="<s:url value="/ewcmssource/easyui/themes/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 网上办事<font color="red">[组织机构]</font><font color="blue">[公民信息]</font>' split="true" style="width:247px;">
						<ul id="tt2"></ul>
					</div>
					<div region="center" style="overflow:hidden;">
						<iframe id="editifr_info"  name="editifr_info" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
					</div>
				</div>
				<div id="treeopmenu" class="easyui-menu" style="width:100px; padding:4px 0 8px 0;">
					<div icon="icon-workingbody" onclick="addWorkingBody();">添加办事</div>
					<div icon="icon-undo" onclick="renameWorkingBody();">重命名办事</div>
					<div icon="icon-remove" onclick="delWorkingBody();">删除办事</div>
					<div icon="icon-up" onclick="upWorkingBody();">上移办事</div>
					<div icon="icon-down" onclick="downWorkingBody();">下移办事</div>
					<div class="menu-sep"></div>
					<div icon="icon-organisation-add" onclick="addOrgan();">选择组织</div>
					<div icon="icon-organisation-del" onclick="removeOrgan();">移除组织</div>
					<div class="menu-sep"></div>
					<div icon="icon-matter-add" onclick="selMatter();">选择事项</div>
					<div icon="icon-matter-del" onclick="removeMatter();">移除事项</div>
					<div class="menu-sep"></div>
					<div icon="icon-citizen-add" onclick="selCitizen();">选择公民</div>
					<div icon="icon-citizen-del" onclick="removeCitizen();">移除公民</div>
					<div class="menu-sep"></div>
					<div icon="icon-reload" onclick="pubLeadingWindow();">发布</div>
				</div>	    	
			</div>
			<div title="事项基本信息" style="padding: 0px;">
				<iframe src="<s:url namespace='/plugin/online/matter' action='index'/>" id="edit_matter"  name="edit_matter" class="editifr" scrolling="no"></iframe>
			</div>
		</div>	
        <div id="matter-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;" icon="icon-matter">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<span>事项选择：</span>
                	<select id="cc_matter" class="easyui-combobox" name="state" style="width:360px;" panelWidth="380px" multiple="true"></select>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:addMatter();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#matter-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="citizen-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;" icon="icon-citizen">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<span>公民选择：</span>
                	<select id="cc_citizen" class="easyui-combobox" name="state" style="width:360px;" panelWidth="380px" multiple="true"></select>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:addCitizen();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#citizen-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <div id="organ-window" class="easyui-window"  closed="true" style="display:none;overflow:hidden;" icon="icon-organisation">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul id="tt3"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="span_ok"><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:saveOrgan();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:tt2_node_id=-1;selMode='';$('#organ-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
        <s:hidden id="channelId" name="channelId"/>
	</body>
</html>