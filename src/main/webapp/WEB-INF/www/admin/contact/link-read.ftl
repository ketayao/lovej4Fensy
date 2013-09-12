<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com/>
<#include "WEB-INF/www/admin/lib/contact-link-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.linkManage.manage")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<script>
$(document).ready(function(){
	
	$(".delete").click(function(){
		return window.confirm("${bundle("form.isConfirm")}");
	});	
});

   
</script>


<div id="main">
		<h3></h3>
    	<table cellpadding="0" cellspacing="0">
			<tr style="font-weight:bold;">
				<td style="width:100px;">${bundle("link.name")}</td>
				<td style="width:150px;">${bundle("link.site")}</td>
				<td>${bundle("link.status")}</td>
            	<td>${bundle("link.createTime")}</td>
            	<td align="center">${bundle("form.action")}</td>
        	</tr>
        	<#list links as p>
        	<tr>
        		<td>${p.name}</td>
        		<td><a href="${p.site}" target="_blank">${p.site}</a>
        		<td>
            		<#if p.status == 'show'>
            			${bundle("link.status.show")}
            		<#elseif p.status == 'hidden'>
            			${bundle("link.status.hidden")}
            		</#if>
            	</td>
            	<td>${p.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
            	<td class="action">
            		<a href="${rc.contextPath}/admin/link/pu?id=${p.id}&pageIndex=${pageInfo.pageIndex}" class="edit">${bundle("form.edit")}</a>
            		<a href="${rc.contextPath}/admin/link/d?id=${p.id}&pageIndex=${pageInfo.pageIndex}" class="delete">${bundle("form.delete")}</a>
            	</td>
        	</tr>
        	<tr>
				<td colspan="5" style="background: #fbfbfb;">${bundle("link.description")}:${p.description}</td>
        	</tr>
			</#list>
        </table>
        <br/>
        <#if links?size gt 0>
        	<div class="megas512">
        	<#noescape>
				${pageInfo.pageHtml}
        	</#noescape>
        	</div>
        </#if>	
        <br/><br/>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>