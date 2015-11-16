<#macro menu_nav title index>
  <li <#if title==index[0]>class="current_page_item"<#else>class="page_item"</#if>><a href="${rc.contextPath}/">${index[0]}</a></li>
  	<#list categories as c>
		<li <#if title==c.name>class="current_page_item"<#else>class="page_item"</#if>><a href="${rc.contextPath}/archive/expages/${c.id}">${c.name}</a></li>
	</#list>
  <li <#if title==index[1]>class="current_page_item"<#else>class="page_item"</#if>><a href="${rc.contextPath}/contact">${index[1]}</a></li>
</#macro>