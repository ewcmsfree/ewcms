<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>专栏模板管理</title>
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript"> 
			var tpltreeURL = '<s:url value="/site/template/tree.do" />';
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
				ewcmsBOBJ.addToolItem("导入","icon-print","browseTPL");
				ewcmsBOBJ.addToolItem("应用子栏目","","appChild")
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
									return '<input type="button" name="Submit" value="编 辑" class="inputbutton" onClick="editTPL('+rec.id+');">';
								 }}
				    ]]
				});
				
				//创建和设置页面的操作对象 EwcmsOperate
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url action="input" namespace="/site/template"/>?templateVo.channelId=<s:property value="channelVo.id"/>');
				ewcmsOOBJ.setDeleteURL('<s:url action="delete" namespace="/site/template"/>');
			});			
			function editTPL(idValue){
				$("#editifr_pop").attr("src",'<s:url action="editContent" namespace="/site/template"/>?templateVo.id='+idValue);
				ewcmsBOBJ.openWindow("#pop-window",{width:800,height:370,title:"模板编辑"});
				//top._home.addTab('模板编辑','<s:url action="editContent" namespace="/site/template"/>?templateVo.id='+idValue);
			}	

			function previewTPL(idValue){
				window.open('/template/preview?templateId='+idValue, "previewwin", "height=600, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=1,location=no, status=no");			
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
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"  onclick="javascript:closeWindow('#template-window');">取消</a>
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
	</body>
</html>