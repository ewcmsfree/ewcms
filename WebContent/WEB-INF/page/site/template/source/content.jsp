<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>模板资源编辑</title>
		<s:include value="../../../taglibs.jsp"/>
        <link rel="stylesheet" type="text/css" href='<s:url value="/ewcmssource/codemirror/lib/codemirror.css"/>'/>
        <script type="text/javascript" src='<s:url value="/ewcmssource/codemirror/lib/codemirror.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/codemirror/mode/xml/xml.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/codemirror/mode/javascript/javascript.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/ewcmssource/codemirror/mode/css/css.js"/>'></script>
        <script type="text/javascript">
	        $(function(){
	            <s:include value="../../../alertMessage.jsp"/>
	        });
        </script>	
	</head>
	<body>	
		<script type="text/javascript">
			var currentNode = parent.parent.currentNode;
			if (currentNode){
				var position = "";
				var rootNode = parent.parent.$('#tt2').tree('getRoot');
				var text = [];
				if (rootNode){
					position += rootNode.text + " >> ";
					$.each(currentNode , function(){
						if (currentNode && currentNode.id != rootNode.id){
							text.push(currentNode.text);
							currentNode = parent.parent.$('#tt2').tree('getParent',currentNode.target);
						}
					});
				}
				for (var i = text.length - 1; i > 0; i--){
					position += text[i] + " >> ";
				}
				position += text[i];
				document.write("<span style='color:red;'>当前位置：" + position + "</span>");
			}
		</script>
		<s:form action="saveContent" namespace="/site/template/source">
			<table class="formtable" align="center">
				<tr>
					<td ><s:textarea id="sourceContent" name="sourceContent" style="height:380px; width:100%;" ></s:textarea></td>
				</tr>	
				<tr>
					<td style="padding:0;">
						<div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
							<a class="easyui-linkbutton" icon="icon-save" href="javascript:document.forms[0].submit();">保存</a>
						 	<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:document.forms[0].reset();">重置</a>
						</div>								
					</td>
				</tr>																							
			</table>		
			<s:hidden name="sourceVo.id"/>	
		</s:form>
		<script type="text/javascript">
			var fileName = parent.parent.$('#tt2').tree('getSelected').text;
			var modeName = "xml";
		    if(fileName.lastIndexOf(".") > 0){
			    var modeName = fileName.substring(fileName.lastIndexOf(".")+1) ;
			    if (modeName == "htm" || modeName == "html"){
			    	modeName = "xml";
			    }else if (modeName == "js"){
			    	modeName = "javascript";
			    }
		    }
			var editor = CodeMirror.fromTextArea(document.getElementById("sourceContent"), {
 				mode: modeName,
  				lineNumbers: true,
  				lineWrapping: true,
                matchBrackets: true,
                indentUnit: 4
			});
   		</script>
	</body>
</html>