<#if (parameters.params?exists && parameters.params?size > 0)>
	<#list parameters.params as param>
		<tr>
			<td class="texttd">
				<#if param.cnName == "">
					${param.enName}
				<#else>
					${param.cnName}
				</#if>：
			</td>
			<td class="inputtd" colspan="3">${parameters.parameterMethod(param)}
			</td>
		</tr>
	</#list>
</#if>
