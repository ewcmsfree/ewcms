<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>专栏模板管理</title>
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript"> 
			var tpltreeURL = '<s:url value="/site/template/tree.do" />';
			var datagridId="#tt";
			function browseTPL(){
				openWindow("#template-window");
			}

			function selectTPL(){
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined' || node.iconCls != "")
    	    	{
    	    		$.messager.alert('提示','请选择模板文件');
    	    		return false;
    	    	}
	            $.post('<s:url action="importtpl"/>',{'channelVo.id':<s:property value="channelVo.id"/>,'channelVo.name':node.id},function(data){
		            if(data == 'false'){
	    	    		$.messager.alert('提示','模板导入失败');
	    	    		return;
		            }
		            defQueryCallBack();
	    	    }); 	    	    					
			}	
			
						
			$(function(){
				//公共模板目录树初始	
				$('#tt2').tree({
					checkbox: false,
					url: tpltreeURL
				});
								
				//创建和设置页面的基本对象 EwcmsBase`
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url action="query" namespace="/site/template"/>?parameters["channelId"]=<s:property value="channelVo.id"/>');
				ewcmsBOBJ.addToolItem('导入','icon-zip-import','browseTPL');
				//ewcmsBOBJ.addToolItem('导出','','exportTPL');
				ewcmsBOBJ.addToolItem('应用子栏目','icon-applied-child','appChild')
				ewcmsBOBJ.addToolItem('强制发布','icon-force-operate','forceOperate');
				ewcmsBOBJ.openDataGrid('#tt',{
					columns:[[
								 {field:'id',title:'编号',width:50,sortable:true,align:'center'},
				                 {field:'path',title:'模板路径',width:300,align:'left'},
				                 {field:'typeDescription',title:'模板类型',width:120},
				                 {field:'describe',title:'说明',width:120,align:'left'},
				                 {field:'parentId',title:'预览',width:70,align:'center',formatter:function(val,rec){
										return '<input type="button" name="Submit" value="预 览" class="inputbutton" onClick="previewTPL('+rec.id+');">';
								 }},
				                 {field:'size',title:'编辑',width:70,align:'center',formatter:function(val,rec){
									return '<input type="button" name="Submit" value="编 辑" class="inputbutton" onClick="editTPL('+rec.id+',\'' + rec.path + '\',\''+ rec.typeDescription + '\');">';
								 }}
								 //,
								 //{field:'down',title:'下载',width:70,align:'center',formatter:function(val,rec){
								 //	return '<input type="button" name="Submit" value="下 载" class="inputbutton" onClick="downloadTPL('+rec.id+');">';
								 //}}
				    ]]
				});
				
				//创建和设置页面的操作对象 EwcmsOperate
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url action="input" namespace="/site/template"/>?templateVo.channelId=<s:property value="channelVo.id"/>');
				ewcmsOOBJ.setDeleteURL('<s:url action="delete" namespace="/site/template"/>');
			});			
			function editTPL(idValue, path, typeDescription){
				//$("#editifr_pop").attr("src",'<s:url action="editContent" namespace="/site/template"/>?templateVo.id='+idValue);\
				var position="";
				var currentNode = parent.parent.selectedNode;
				if (currentNode){
					var rootNode = parent.parent.$("#tt2").tree('getRoot');
					if (rootNode && (currentNode.id == rootNode.id)){
						position += rootNode.text + " >> ";
					}else{
						var text = [];
						if (rootNode){
							position += rootNode.text + " >> ";
							$.each(currentNode , function(){
								if (currentNode && currentNode.id != rootNode.id){
									text.push(currentNode.text);
									currentNode = parent.parent.$("tt2").tree('getParent',currentNode.target);
								}
							});
						}
						for (var i = text.length - 1; i > 0; i--){
							if (typeof(text[i])!="undefined"){
								position += text[i] + " >> ";
							}
						}
						position += text[i] + " >> ";
					}
					position += "(" + idValue + ")" + path + "(" + typeDescription + ")" ;
					
					position = "<span style='color:red;'>当前位置：" + position + "</span>";
		        }
				ewcmsBOBJ.openWindow1({width:800,height:390,title:"模板编辑",url:'<s:url action="editContent" namespace="/site/template"/>?templateVo.id='+idValue,position:position});
				//top._home.addTab('模板编辑','<s:url action="editContent" namespace="/site/template"/>?templateVo.id='+idValue);
			}	

			function previewTPL(idValue){
				window.open('<s:url value="/template/preview"/>?templateId='+idValue, "previewwin", "height=600, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=1,location=no, status=no");			
			}					
			
			//function exportTPL(){
			//	var rows = $('#tt').datagrid('getSelections');
			//	if (rows.length == 0) {
			//		$.messager.alert('提示', '请选择删除记录', 'info');
			//		return;
			//	}
			//	var parameter = '?zipName=' + parent.parent.selectedNode.text;
			//	for ( var i = 0; i < rows.length; i++) {
			//		parameter = parameter + '&selections=' + rows[i].id;
			//	}
			//	window.open('<s:url namespace="/site/template" action="exporttpl"/>' + parameter);
			//}
			
			function downloadTPL(idValue){
				window.open('<s:url namespace="/site/template" action="downloadtpl"/>?selections=' + idValue);
			}
			
			function appChild(){
				var rows = $("#tt").datagrid("getSelections");
				if(rows.length == 0){
		         	$.messager.alert('提示','请选择模板记录','info');
		            return;
		        }
				var parameters = "?channelId=<s:property value='channelVo.id'/>";
				for ( var i = 0; i < rows.length; i++) {
					parameters += '&selections=' + rows[i].id;
				}
				var url = "<s:url namespace='/site/template' action='appChild'/>" + parameters;
				$.ajax({
			        type:'post',
			        async:false,
			        datatype:'json',
			        cache:false,
			        url:url,
			        data: '',
			        success:function(message, textStatus){
			        	loadingDisable();
			        	$.messager.alert('提示', message, 'info');
			        },
			        beforeSend:function(XMLHttpRequest){
			        	loadingEnable();
			        },
			        complete:function(XMLHttpRequest, textStatus){
			        	loadingDisable();
			        },
			        error:function(XMLHttpRequest, textStatus, errorThrown){
			        }
			    });
			}
			function forceOperate(){
				ewcmsBOBJ.openWindow('#force-window',{width : 550,height : 200,title : '强制发布'});
			}
			function forceRelease(){
				$.post("<s:url namespace='/site/template' action='forceRelease'/>?channelId=<s:property value='channelVo.id'/>", {children : $('input[name=\'channelRadio\']:checked').val()}, function(data) {
					$.messager.alert('提示', data, 'info');
				});
				$('#force-window').window('close');
				return false;
			}
			function loadingEnable(){
				   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
				   $("<div class=\"datagrid-mask-msg\"></div>").html("<font size='9'>正在处理，请稍候。。。</font>").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
			}
			function loadingDisable(){
				   $('.datagrid-mask-msg').remove();
				   $('.datagrid-mask').remove();
			}
		</script>		
	</head>
	<body class="easyui-layout">
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
	 	
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;模板编辑" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#edit-window').window('close');">取消</a>
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
                                <td class="tdtitle">名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
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
        <div id="template-window" class="easyui-window" closed="true"   style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false">
                	<ul  id="tt2"></ul>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:selectTPL();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#template-window').window('close');">取消</a>
                </div>
            </div>
        </div>               
		<div id="pop-window" class="easyui-window" title="弹出窗口" icon="icon-save" closed="true" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                	<iframe id="editifr_pop"  name="editifr_pop" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
                </div>
            </div>
        </div>
        <div id="force-window" class="easyui-window" closed="true" style="display:none;overflow:hidden;">
            <div class="easyui-layout" fit="true" >
                <div region="center" border="false" style="padding: 5px;">
                    <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#99BBE8" style="border: #99BBE8 1px solid;">
                        <tr align="center">
                            <td height="30" width="30%">操作</td>
                            <td height="30" width="70%">说明</td>
                        </tr>
                        <tr>
                            <td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="channelRadio" name="channelRadio" list='#{0:"本栏目"}' cssStyle="vertical-align: middle;" value="0"></s:radio></td>
                            <td height="40">&nbsp;只对本栏目进行强制发布</td>
                        </tr>
                        <tr>
                            <td height="40">&nbsp;&nbsp;&nbsp;&nbsp;<s:radio id="channelRadio" name="channelRadio"  list='#{1:"本栏目及子栏目"}' cssStyle="vertical-align: middle;"></s:radio></td>
                            <td height="40">&nbsp;对本栏目及本栏目所属子栏目进行强制发布</td>
                        </tr>
                    </table>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"  onclick="javascript:forceRelease();">确定</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:$('#force-window').window('close');return false;">取消</a>
                </div>
            </div>
        </div>
	</body>
</html>