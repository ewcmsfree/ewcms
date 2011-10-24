			
			//站点目录树初始
			$(function(){
				$('#tt2').tree({
					checkbox: false,
					url: tpltreeURL
				});
			});
			
			var tpl_urlname,tpl_idname;
			function browseTPL(urlName,idName){
				tpl_urlname = urlName;
				tpl_idname = idName;
				openWindow("#template-window",{width:280,height:400,title:"模板选择"});
			}
			
			function closeTPL(){
				$('#template-window').window('close');
			}
			
			function setvalueTPL(urlValue,idValue){
				document.all(tpl_urlname).value = urlValue;
				document.all(tpl_idname).value = idValue;
				closeTPL();
			}
			
			function selectTPL(){
				var node = $('#tt2').tree('getSelected');
    	    	if(node == null || typeof(node) == 'undefined' || node.iconCls != "")
    	    	{
    	    		$.messager.alert('提示','请选择模板文件');
    	    		return false;
    	    	}
    	    	setvalueTPL(node.attributes.path,node.id); 	    	    					
			}
