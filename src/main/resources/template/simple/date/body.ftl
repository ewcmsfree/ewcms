<#assign imgUrl="/struts/ewcms/date/skin/datePicker.gif"><#t/>
<#if parameters.option == "inputsimple">
	<@s.textfield name="${parameters.name}" size="20"  onclick="WdatePicker({dateFmt:'${parameters.format}'})" cssClass="Wdate" theme="simple"/>
</#if>
<#if parameters.option == "inputimg">
	<@s.textfield name="${parameters.name}" size="20" />
	<img onclick="WdatePicker({dateFmt:'${parameters.format}',lang:'${parameters.lang}',el:document.getElementsByName('${parameters.name}')[0]})" src="<@s.url value='${imgUrl}' encode='false' includeParams='none' />" width="16" height="22" align="absmiddle">
</#if>