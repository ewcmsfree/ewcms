<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
  <head>
    <title>个人备忘</title>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/default/easyui.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="/source/theme/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>" />
	<link rel="stylesheet" type="text/css" href="<s:url value='/source/css/notes.css'/>" />
    <script type="text/javascript" src='<s:url value="/source/js/jquery.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.base.js"/>'></script>
    <script type="text/javascript" src='<s:url value="/source/js/ewcms.func.js"/>'></script>
    <script>
       var dropURL = '<s:url namespace="/document/notes" action="drop"/>';
       $(function(){
		  ewcmsBOBJ = new EwcmsBase();
		  ewcmsOOBJ = new EwcmsOperate();  
       });
       function ChangeDate(year,month,weight){
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
            $.post('<s:url namespace="/document/notes" action="changeDate"/>',{'year':$('#year').val(),'month':$('#month').val()},function(data) {
            	$('tr').remove('.notes_tr');
            	$('#result').append(data);
            });
        }
        function toDay(){
            $('#year').attr('value',$('#currentYear').val());
            $('#month').attr('value',$('#currentMonth').val());
        	$.post('<s:url namespace="/document/notes" action="toDay"/>',{},function(data) {
            	$('tr').remove('.notes_tr');
            	$('#result').append(data);
            });
        }
    	function add(i){
        	var url = '<s:url namespace="/document/notes" action="input"/>?year=' + $('#year').val() + '&month=' + $('#month').val() + '&day=' + i;
        	$('#editifr').attr('src',url);
        	var title = '新增备忘(' + $('#year').val() + '年' + $('#month').val() + '月' + i + '日)';
        	$('#bntRemove').linkbutton('disable');
        	ewcmsBOBJ.openWindow('#edit-window',{width:420,height:330,title:title});
    	}
    	function edit(i){
    		var url = '<s:url namespace="/document/notes" action="input"/>?selections=' + i;
    		$('#editifr').attr('src',url);
    		$('#bntRemove').linkbutton('enable');
        	ewcmsBOBJ.openWindow('#edit-window',{width:420,height:330,title:'修改备忘'});
    	}
    	function remove(){
    		var id = $(window.frames['editifr'].document).find('#memorandaId').val();
    		if (id != ''){
        		$.messager.confirm("提示","确定要删除记录吗?",function(r){
            		if (r){
		        		$.post('<s:url namespace="/document/notes" action="delete"/>',{'selections':id},function(data){
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
    		$.post('<s:url action="save" namespace="/document/notes"/>',params,function(data){
        		if (data == 'true'){
                    var id = $(window.frames['editifr'].document).find('#memorandaId').val()
                    if (id != ''){
                        var value = $(window.frames['editifr'].document).find('#title').val();
                        $('#a_title_' + id).attr('title', value);
                        if (value.length > 10) value = value.substring(0,11) + '...';
                        $('#title_' + id).text(value);
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
 		  	var url =  '<s:url namespace="/document/notes" action="list"/>';
			$('#editifr_notes').attr('src',url);
			ewcmsBOBJ.openWindow('#notes-window',{width:1300,height:600,title:'备访录列表'});
 	   }
    </script>
  </head>
  <body class="easyui-layout">
    <div region="center" style="padding: 2px;" border="false">
      <table width="100%" cellspacing="0" cellpadding="6" border="0" >
        <tr>
		  <td valign="middle" style="padding-bottom:0;">
		    &nbsp;<!-- <img width="20" height="20" src="<s:url value='/source/image/notes/notes.gif'/>">个人备忘列表-->
		  </td>
        </tr>
 		<tr>
		  <td style="padding:0;">
		    <table align="left" width="400" cellspacing="0" cellpadding="0" border="0">
		      <tr>
		        <td>&nbsp;</td>
		        <td valign="middle" style="padding-bottom:0;"><a onclick="toDay();;return false;" id="" onselectstart="return false" tabindex="-1" hidefocus="true" class="ewcmsBtn" href="javascript:void(0);"><b>&nbsp;今&nbsp;天&nbsp;</b></a></td>
		        <td><s:text name="toDayLunar"/></td>
		      </tr>
		    </table>
		    <table align="left" width="100" cellspacing="0" cellpadding="0" border="0">
		    	<tr>
		    		<td width="100%"><a onclick="notesDetail();return false;" id="" onselectstart="return false" tabindex="-1" hidefocus="true" class="ewcmsBtn" href="javascript:void(0);"><img src="<s:url value='/source/image/notes/notes.gif'/>"><b>备忘录列表</b></a></td>
		    	</tr>
		    </table>
		    <table align="right" width="260" cellspacing="0" cellpadding="0" border="0">
		      <tr>
		        <td width="90"><a onclick="ChangeDate($('#year').val(),$('#month').val(),-1);return false;" id="" onselectstart="return false" tabindex="-1" hidefocus="true" class="ewcmsBtn" href="javascript:void(0);"><img src="<s:url value='/source/image/notes/notes_prev.gif'/>"><b>上一个月&nbsp;</b></a></td>
				<td width="75" align="center"><s:textfield id="year" name="year" size="3" cssStyle="background-color:transparent;border:0;" readonly="true"/>年 <s:textfield id="month" name="month" size="1" cssStyle="background-color:transparent;border:0;" readonly="true"/>月&nbsp;</td>
				<td width="90"><a onclick="ChangeDate($('#year').val(),$('#month').val(),1);return false;" id="" onselectstart="return false" tabindex="-1" hidefocus="true" class="ewcmsBtn" 	href="javascript:void(0);"><img src="<s:url value='/source/image/notes/notes_next.gif'/>"><b>下一个月&nbsp;</b></a></td>
			  </tr>
			</table>
		  </td>
		</tr>
        <tr>
          <td>
            <table id="result" width="100%" cellspacing="0" cellpadding="0" bordercolor="#aaccee" border="1">
			  <s:text name="result"/>
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
	<div id="notes-window" class="easyui-window" icon="icon-votedetail" closed="true" style="display:none;">
      <div class="easyui-layout" fit="true">
        <div region="center" border="false">
          <iframe id="editifr_notes"  name="editifr_notes" class="editifr" frameborder="0" width="100%" height="100%" scrolling="no"></iframe>
        </div>
      </div>
    </div>
    <s:hidden id="currentYear" name="currentYear"/>
    <s:hidden id="currentMonth" name="currentMonth"/>
  </body>
</html>