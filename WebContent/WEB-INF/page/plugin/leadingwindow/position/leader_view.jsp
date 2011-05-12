<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<title>用户信息</title>
		<link rel="stylesheet" type="text/css" href="<s:url value="/source/css/ewcms.css"/>"/>							
        <script type="text/javascript">
            <s:property value="javaScript"/>
        </script>		
	</head>
	<body>
		<table class="formtable" >
			<tr>
				<td width="10%">照片：</td>
				<td width="90%">
					<s:if test="leaderVo.image!=null&&leaderVo.image!=''">
						<img id="referenceImage" name="referenceImage" width="126px" height="154px" src="${leaderVo.image}"/>
					</s:if>
					<s:else>
						<img id="referenceImage" name="referenceImage" width="126px" height="154px" src="<s:url value='/source/image/article/nopicture.jpg'/>"/>
				    </s:else>
				</td>
			</tr>
			<tr>
				<td>姓名：</td>
				<td>
					<s:textfield id="leaderName" name="leaderVo.name" readonly="true" size="80"/>
				</td>
			</tr>
			<tr>
				<td>职务描述：</td>
				<td>
					<s:textfield id="leaderName" name="leaderVo.duties" readonly="true" size="80"/>
				</td>
			</tr>
			<tr>
				<td>简历：</td>
				<td>
					<s:textarea name="leaderVo.resume" readonly="true" cssStyle="height:120px;width:100%;"/>
				</td>
			</tr>
			<tr>
				<td>分管工作：</td>
				<td>
					<s:textarea name="leaderVo.chargeWork" readonly="true" cssStyle="height:120px;width:100%;"/>
				</td>
			</tr>
			<tr>
				<td>E-Mail：</td>
				<td>
					<s:textfield name="leaderVo.email" size="120" readonly="true" />
				</td>
			</tr>
			<tr>
				<td>联系电话：</td>
				<td>
					<s:textfield name="leaderVo.contact" size="120" readonly="true" />
				</td>
			</tr>
			<tr>
				<td>手机：</td>
				<td>
					<s:textfield name="leaderVo.mobile" size="120" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td>办公地址：</td>
				<td>
					<s:textfield name="leaderVo.officeAddress" size="120" readonly="true"/>
				</td>
			</tr>
		</table>
		<s:hidden name="leaderVo.id"/>
		<s:hidden name="channelId" id="channelId"/>
	</body>
</html>