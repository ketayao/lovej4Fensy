<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com/>
<#include "WEB-INF/www/admin/lib/contact-link-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.contactManage.manage")}</#assign>

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
				<td style="width:200px;">${bundle("contact.name")}</td>
				<td>${bundle("contact.status")}</td>
            	<td>${bundle("contact.postTime")}</td>
            	<td align="center">${bundle("form.action")}</td>
        	</tr>
        	<#list contacts as p>
        	<tr>
        		<td><#if p.site?? && p.site != ''><a href="${p.site}" target="_blank">${p.name}</a><#else>${p.name}</#if></td>
        		<td>
            		<#if p.status == 'new'>
            			${bundle("contact.status.new")}
            		<#elseif p.status == 'readed'>
            			${bundle("contact.status.read")}
            		<#elseif p.status == 'replied'>
            			${bundle("contact.status.reply")}
            		</#if>
            	</td>
            	<td>${p.postTime?string('yyyy-MM-dd HH:mm:ss')}</td>
            	<td class="action">
            		<a href="${rc.contextPath}/admin/contact/u?id=${p.id}&status=readed&pageIndex=${pageInfo.pageIndex}" class="view">${bundle("contact.status.read")}</a>
            		<a href="${rc.contextPath}/admin/contact/u?id=${p.id}&status=replied&pageIndex=${pageInfo.pageIndex}" class="view">${bundle("contact.status.reply")}</a>
            		<a href="${rc.contextPath}/admin/contact/d?id=${p.id}&pageIndex=${pageInfo.pageIndex}" class="delete">${bundle("form.delete")}</a>
            	</td>
        	</tr>
        	<tr>
				<td colspan="4" style="background: #fbfbfb;">${p.content}<br/>${bundle("contact.email")}:${p.email}</td>
        	</tr>
			</#list>
        </table>
        <br/>
        <#if contacts?size gt 0>
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