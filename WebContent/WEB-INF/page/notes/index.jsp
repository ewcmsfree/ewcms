<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="../loading.jsp" flush="true"/>
<html>
  <head>
    <title>个人备忘</title>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/default/easyui.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/easyui/themes/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/css/ewcms.css"/>" />
    <link rel="stylesheet" type="text/css" href="<s:url value="/ewcmssource/page/notes/notes.css"/>" />
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/easyui/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.base.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/ewcmssource/js/ewcms.func.js"/>'></script>
    <script type="text/javascript">
       var dropURL = '<s:url namespace="/notes" action="drop"/>';
       $(function(){
           ewcmsBOBJ = new EwcmsBase();
           ewcmsOOBJ = new EwcmsOperate(); 
           $('.a_notes_value').not('span.span_title').draggable({
			   revert:true,
			   proxy:'clone'
		   });
		   $('.div_notes').not('span.span_title').droppable({
			   onDragEnter:function(e, source){
		       },
		       onDragLeave:function(e, source){
		       },
		       onDrop:function(e, source){
			       $(this).append(source);
			       var divMemoId = $(source).attr('id');    
			       var targetDivId = $(source).parents('div:first').attr('id');
			       var memoId = divMemoId.split('_')[3];
			       var dropDay = targetDivId.split('_')[2];
			       $.post(dropURL,{'memoId':memoId,'year':$('#year').val(),'month':$('#month').val(),'dropDay':dropDay},function(data){
			         if (data != 'true'){
			         }
			       });
		       }
		   });
       });
       function ChangeDate(year,month,weight){
    	   loadingEnable();
           var monthValue = parseInt(month) + weight;
           if (monthValue >= 13){
               year = parseInt(year) + 1;
               monthValue = monthValue - 12;
           }else if (monthValue <= 0){
               year = parseInt(year) - 1;
               monthValue = monthValue + 12;
           }
           $('#year').attr('value',year);
           $('#month').attr('value',monthValue);
           $.post('<s:url namespace="/notes" action="changeDate"/>',{'year':$('#year').val(),'month':$('#month').val()},function(data) {
               $('tr').remove('.notes_tr');
               $('#result').append(data);
               loadingDisable();
           });
        }
        function toDay(){
            $('#year').attr('value',$('#currentYear').val());
            $('#month').attr('value',$('#currentMonth').val());
        	$.post('<s:url namespace="/notes" action="toDay"/>',{},function(data) {
            	$('tr').remove('.notes_tr');
            	$('#result').append(data);
            });
        }
    	function add(i){
        	var url = '<s:url namespace="/notes" action="input"/>?year=' + $('#year').val() + '&month=' + $('#month').val() + '&day=' + i;
        	$('#editifr').attr('src',url);
        	var title = '新增备忘(' + $('#year').val() + '年' + $('#month').val() + '月' + i + '日)';
        	$('#bntRemove').linkbutton('disable');
        	ewcmsBOBJ.openWindow('#edit-window',{width:420,height:330,title:title});
    	}
    	function edit(i){
    		var url = '<s:url namespace="/notes" action="input"/>?selections=' + i;
    		$('#editifr').attr('src',url);
    		$('#bntRemove').linkbutton('enable');
        	ewcmsBOBJ.openWindow('#edit-window',{width:420,height:330,title:'修改备忘'});
    	}
    	function remove(){
    		var id = $(window.frames['editifr'].document).find('#memorandaId').val();
    		if (id != ''){
        		$.messager.confirm("提示","确定要删除记录吗?",function(r){
            		if (r){
		        		$.post('<s:url namespace="/notes" action="delete"/>',{'selections':id},function(data){
		            		if (data == 'success'){
		                		$('#div_notes_memo_' + id).remove();
		            			$('#edit-window').window('close');
		            		}else{
		                		$.messager.alert('错误','删除失败','error');
		            		}
		        		});
            		}
        		});
    		}
    	}
    	function saveBack(){
    		var params = $(window.frames['editifr'].document).find('#notesForm').serialize();
    		$.post('<s:url action="save" namespace="/notes"/>',params,function(data){
        		if (data == 'true'){
                    var id = $(window.frames['editifr'].document).find('#memorandaId').val()
                    if (id != ''){
                        var value = $(window.frames['editifr'].document).find('#title').val();
                        $('#a_title_' + id).attr('title', value);
                        if (value.length > 10) value = value.substring(0, 9) + '...';
                        if ($(window.frames['editifr'].document).find('#warn').attr('checked') == 'checked'){
                            value = value + "<img id='img_clock_" + id + "'  src='../../ewcmssource/image/notes/clock.png' width='13px' height='13px' align='bottom'/>";
                        }else{
                            $('#img_clock_' + id).remove();
                        }
                        $('#title_' + id).html(value);
                    }else{
                    	ChangeDate($('#year').val(), $('#month').val(), 0);
                    }
                    $('#edit-window').window('close');
        		}else{
            		$.messager.alert('错误','保存失败','error');
        		}
    		});
    		return false;
	   }
 	   function notesDetail(){
 		  	var url =  '<s:url namespace="/notes" action="list"/>';
			$('#editifr_notes').attr('src',url);
			ewcmsBOBJ.openWindow('#notes-window',{width:900,height:500,title:'备访录列表'});
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
    <div region="center" style="padding: 2px;" border="false">
      <table width="100%" cellspacing="0" cellpadding="6" border="0" >
        <tr>
		  <td valign="middle" style="padding-bottom:0;">&nbsp;</td>
        </tr>
 		<tr>
		  <td style="padding:0;">
		    <table align="left" width="40%" cellspacing="0" cellpadding="0" border="0">
		      <tr align="left">
		        <td>&nbsp;</td>
		        <td valign="middle">
		        	<a href="javascript:void(0);" iconCls="icon-notes-today" class="easyui-linkbutton" onclick="toDay();return false;">今天</a>
		        </td>
		        <td  align="left">
		        	<s:property value="toDayLunar" escape="false"/>
		        </td>
		      </tr>
		    </table>
		    <table align="left" width="30%" cellspacing="0" cellpadding="0" border="0">
		    	<tr>
		    		<td align="left" width="100%">
		    		  <a href="javascript:void(0);" iconCls="icon-notes-list" class="easyui-linkbutton" onclick="notesDetail();return false;">备忘录列表</a>
		    		</td>
		    	</tr>
		    </table>
		    <table aligh="right" width="30%" cellspacing="0" cellpadding="0" border="0">
		      <tr>
		        <td align="right" width="100%">
		        	<a id="prevMonth" href="javascript:void(0);" iconCls="icon-notes-prev" class="easyui-linkbutton" onclick="ChangeDate($('#year').val(),$('#month').val(),-1);return false;">上一个月</a>&nbsp;
					<s:textfield id="year" name="year" size="3" cssStyle="background-color:transparent;border:0;" readonly="true"/>年 <s:textfield id="month" name="month" size="1" cssStyle="background-color:transparent;border:0;" readonly="true"/>月&nbsp;
					<a id="nextMonth" href="javascript:void(0);" iconCls="icon-notes-next" class="easyui-linkbutton" onclick="ChangeDate($('#year').val(),$('#month').val(),1);return false;">下一个月</a>
		        </td>
		        <td>&nbsp;&nbsp;</td>
			  </tr>
			</table>
		  </td>
		</tr>
        <tr>
          <td>
            <table id="result" width="100%" cellspacing="0" cellpadding="0" bordercolor="#aaccee" border="1">
			  <s:property value="result" escape="false"/>
	        </table>
	      </td>
        </tr>
      </table>
    </div>
    <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;备忘录" style="display:none;">
      <div class="easyui-layout" fit="true">
        <div region="center" border="false">
          <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
        </div>
        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
          <a id='bntRemove' class="easyui-linkbutton" icon="icon-remove" href="javascript:void(0);" onclick="javascript:remove();">删除</a>
          <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0);" onclick="javascript:saveBack();">保存</a>
          <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0);" onclick="javascript:$('#edit-window').window('close');">取消</a>
        </div>
      </div>
    </div>
	<div id="notes-window" class="easyui-window" closed="true" title="&nbsp;备忘录列表" style="display:none;">
      <div class="easyui-layout" fit="true">
        <div region="center" border="false">
          <iframe id="editifr_notes"  name="editifr_notes" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
        </div>
        <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
          <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0);" onclick="javascript:$('#editifr_notes').attr('src','');$('#notes-window').window('close');">关闭</a>
        </div>
      </div>
    </div>
    <s:hidden id="currentYear" name="currentYear"/>
    <s:hidden id="currentMonth" name="currentMonth"/>
  </body>
</html>