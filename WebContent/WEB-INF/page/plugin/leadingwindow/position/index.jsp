<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>领导基本信息</title>	
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
		<link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
		<script type="text/javascript" src='<s:url value="/source/js/jquery-1.4.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>
		<script type="text/javascript" src='<s:url value="/source/js/ewcms.js"/>'></script>
		<script>
			var leaderChannelId = 0;
			$(function(){
				//基本变量初始
				setGlobaVariable({
					inputURL:' ',
					queryURL:' ',
					deleteURL:' '
				});
				//站点专栏目录树初始
				$('#tt2').tree({
					checkbox: false,
					url: '<s:url namespace="/plugin/leadingwindow/position" action="tree"><s:param name="channelId" value="channelId"></s:param></s:url>',
					onClick:function(node){
						leaderChannelId = node.id
						var url='<s:url namespace="/plugin/leadingwindow/position" action="queryChannelStatus"/>';
						url = url + '?channelId=' + $('#channelId').val() + '&status=' + node.attributes.type + '&leaderChannelId=' + node.id;
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
				$('#cc_leader').combobox({
				    url:'<s:url namespace="/plugin/leadingwindow/position" action="findAllLeader"><s:param name="channelId" value="channelId"></s:param></s:url>',
				    valueField:'id',
				    textField:'name'
				});
				$('#systemtab').tabs({
					onSelect:function(title){
						if (title == '信息编辑'){
							//$('#tt2').tree('reload');
						}else if (title == '人员基本信息'){
							$('#edit_leader').attr('src','<s:url namespace="/plugin/leadingwindow/leader" action="index"><s:param name="channelId" value="channelId"></s:param></s:url>');
						}
					}
				});
			});
			function addPosition(){
    			var node = getSelectNode();
    			if(!node) return;
    			var type = node.attributes.type;
    			if (type == 'position' || type == 'component'){
					$.messager.prompt(node.text, '请输入专栏名', function(r){
						if (r){
				            $.post('<s:url namespace="/plugin/leadingwindow/position" action="addPosition"><s:param name="channelId" value="channelId"></s:param></s:url>',{'parentId':node.id,'positionName':r},function(data){
					            if(data == 'leader'){
									$.messager.alert('提示','人员信息下不能添加职务','info');
									return;
						        }
						        if (data == 'article'){
									$.messager.alert('提示','下不能添加职务','info');
									return;
							    }
					            if(data == 'false'){
				    	    		$.messager.alert('提示','职务添加失败','info');
				    	    		return;
					            }
					            $('#tt2').tree('reload');
					            $.messager.alert('提示','添加职务成功','info');
				    	    });						
						}
					});
    			}else{
        			$.messager.alert('提示','只能在职务下添加职务','info');
        			return;
    			}
			}
			
			function renamePosition(){
				//判断是否选择了专栏
    			var node = getSelectNode();
    			if(!node) return;

    			var type = node.attributes.type;
    			if (type == 'position'){ 		
	    	    	//重命名专栏 			
					$.messager.prompt(node.text, '请修改专栏名', function(r){
						if (r){
				            $.post('<s:url namespace="/plugin/leadingwindow/position" action="renamePosition"><s:param name="channelId" value="channelId"></s:param></s:url>',{'positionId':node.id,'positionName':r},function(data){
					            if(data == 'false'){
				    	    		$.messager.alert('提示','专栏重命名失败','info');
				    	    		return;
					            }
					            node.text = r;
								$('#tt2').tree('update',node);
								if (editifr_info.$('#leaderName')){
									editifr_info.$('#leaderName').val(r);
								}		
				    	    });						
						}
					});
    			}else{
        			$.messager.alert('提示','只能重命名职务','info');
        			return;
    			}
			}
						
			//删除站点专栏
			function delPosition(){
				//判断是否选择了专栏
    			var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许删除该专栏','info');
					 return;
				}
    			var type = node.attributes.type;
    			if (type == 'position'){ 		
	    			$.messager.confirm('提示', '确认要删除 ' + node.text + ' 专栏?', function(r){
	    				if (r){
	    	    	    	//删除专栏 			
	    		            $.post('<s:url namespace="/plugin/leadingwindow/position" action="delPosition"><s:param name="channelId" value="channelId"></s:param></s:url>',{'positionId':node.id},function(data){
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
    	    	leaderChannelId = node.id;
				return node;
			}
			
			function selLeader(){
    			var node = getSelectNode();
    			if(!node) return;
    			var type = node.attributes.type;
    			if (type == 'position'){
	    			$('#cc_leader').combobox('reload');
	    			openWindow("#leader-window",{width:500,height:90,title:'职务选择'}); 			
    			}else{
    				$.messager.alert('提示','只能在职务下添加人员信息','info');
    				return;
    			}
			}

			function addLeaderToPosition(){
				var val = $('#cc_leader').combobox('getValues');
				if (val == ""){
					$.messager.alert('提示','请选择人员','info');
    	    		return;
				}
				$.post('<s:url namespace="/plugin/leadingwindow/position" action="addLeaderToPosition"><s:param name="channelId" value="channelId"></s:param></s:url>',{'selectIds':val.toString(),'parentId':leaderChannelId},function(data){
					 if(data == 'false'){
		    	    	$.messager.alert('提示','不能在人员信息下再选择人员','info');
		    	    	return;
			         }
			         if (data == 'system-false'){
				        $.messager.alert('提示','添加人员信息不成功','info');
				        return; 
			         }
			         $('#tt2').tree('reload');
			         $.messager.alert('提示','选择人员信息成功','info');
				});
			}

			function removeLeaderToPosition(){
				var node = getSelectNode();
				var type = node.attributes.type;
    			if (type == 'leader'){
        			var node_parent = $('#tt2').tree('getParent',node.target);
        			type = node_parent.attributes.type;
        			if (type == 'position'){
        				$.post('<s:url namespace="/plugin/leadingwindow/position" action="removeLeaderToPosition"><s:param name="channelId" value="channelId"></s:param></s:url>',{'leaderId':node.id,'parentId':node_parent.id},function(data){
            				if (data == 'true'){
            					$.messager.alert('提示','移除人员成功','info');
            					$('#tt2').tree('reload');
                				return;
            				}else{
            					$.messager.alert('提示','移除人员失败','info');
                				return;
            				}
        				});
        			}else{
        				$.messager.alert('提示','移除人员失败','info');
        				return;
        			}
    			}else{
    				$.messager.alert('提示','只能移除人员','info');
    				return;
    			}
				
			}

			function pubLeadingWindow(){
				$.post('<s:url namespace="/plugin/leadingwindow/position" action="pubLeadingWindow"/>', {'channelId':$('#channelId').val()}, function(data){
					if (data == 'true'){
						$.messager.alert("提示","发布成功","info");
						return;
					}else if (data == 'false'){
						$.messager.alert("提示","发布失败","info");
						return;
					}
				});
			}

			function upPosition(){
				var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许移动该专栏','info');
					 return;
				}
    			var type = node.attributes.type;
    			if (type == 'position'){ 
	    			$.messager.confirm('提示', '确认要上移 ' + node.text + ' 专栏?', function(r){
	    				if (r){
	    					var node_parent = $('#tt2').tree('getParent',node.target);
	    		            $.post('<s:url namespace="/plugin/leadingwindow/position" action="upPosition"><s:param name="channelId" value="channelId"></s:param></s:url>',{'parentId':node_parent.id,'positionId':node.id},function(data){
	    			            if(data == 'false'){
	    		    	    		$.messager.alert('提示','上移失败','info');
	    		    	    		return;
	    			            }
	    			            $('#tt2').tree('reload');
	    		    	    });	
	    				}
	    			}); 
    			}else{
        			$.messager.alert('提示','只能移动职务','info');
        			return;
    			}
			}

			function downPosition(){
				var node = getSelectNode();
    			if(!node) return;
				var rootnode = $('#tt2').tree('getRoot');
				if(rootnode.id == node.id){
					$.messager.alert('提示','不允许移动该专栏','info');
					 return;
				}
    			var type = node.attributes.type;
    			if (type == 'position'){ 
	    			$.messager.confirm('提示', '确认要上移 ' + node.text + ' 专栏?', function(r){
	    				if (r){
	    					var node_parent = $('#tt2').tree('getParent',node.target);
	    		            $.post('<s:url namespace="/plugin/leadingwindow/position" action="downPosition"><s:param name="channelId" value="channelId"></s:param></s:url>',{'parentId':node_parent.id,'positionId':node.id},function(data){
	    			            if(data == 'false'){
	    		    	    		$.messager.alert('提示','上移失败','info');
	    		    	    		return;
	    			            }
	    			            $('#tt2').tree('reload');
	    		    	    });	
	    				}
	    			}); 
    			}else{
        			$.messager.alert('提示','只能移动职务','info');
        			return;
    			}
			}
		</script>		
	</head>
	<body>
		<div class="easyui-tabs" id="systemtab" border="false" fit="true">
			<div title="信息编辑" style="padding: 0px;">
				<div class="easyui-layout" fit="true">
					<div region="west"  title='<img src="<s:url value="/source/theme/icons/reload.png"/>" style="vertical-align: middle;cursor:pointer;" onclick="channelTreeLoad();"/> 领导之窗' split="true" style="width:180px;">
						<ul id="tt2"></ul>
					</div>
					<div region="center" style="overflow:hidden;">
						<iframe id="editifr_info"  name="editifr_info" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
					</div>
				</div>
				<div id="treeopmenu" class="easyui-menu" style="width:100px; padding:4px 0 8px 0;">
					<div icon="icon-position" onclick="addPosition();">添加职务</div>
					<div icon="icon-undo" onclick="renamePosition();">重命名</div>
					<div icon="icon-remove" onclick="delPosition();">删除</div>   
					<div icon="icon-up" onclick="upPosition();">上移职务</div>
					<div icon="icon-down" onclick="downPosition();">下移职务</div>
					<div class="menu-sep"></div>
					<div icon="icon-leader" onclick="selLeader();">选择人员</div>
					<div icon="icon-remove" onclick="removeLeaderToPosition();">移除人员</div>
					<div class="menu-sep"></div>
					<div icon="icon-reload" onclick="pubLeadingWindow();">发布</div>
				</div>	    	
			</div>
			
			<div title="人员基本信息" style="padding: 0px;">
				<iframe src="<s:url namespace='/plugin/leadingwindow/leader' action='index'><s:param name='channelId' value='channelId'></s:param></s:url>" id="edit_leader"  name="edit_leader" class="editifr" scrolling="no"></iframe>			
			</div>
		</div>	
        <div id="leader-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<span>人员选择：</span>
                	<select id="cc_leader" class="easyui-combobox" name="state" style="width:360px;" panelWidth="380px" multiple="true"></select>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <span id="span_move"><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:addLeaderToPosition();">确定</a></span>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#leader-window').window('close');">取消</a>
                </div>
            </div>
        </div>
        <s:hidden id="channelId" name="channelId"/>
	</body>
</html>