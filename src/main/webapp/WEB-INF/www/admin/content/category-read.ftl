<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/content-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.categoryManage.manage")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<#-- form验证 -->
<script type="text/javascript" src="${rc.contextPath}/styles/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/styles/fancybox/jquery.fancybox-1.3.4.css" media="screen" />

<script>
$(document).ready(function(){
	$(".view").fancybox({
		'width'				: 320,
		'height'			: 380,
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});
	
	$(".delete").click(function(){
		return window.confirm("${bundle("form.isConfirm")}");
	}); 
});  
</script>


<div id="main">
		<h3 class="lovej-action">
			
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
		</h3>
		<h3>
			${bundle("form.notice.priority")}
		</h3>
    	<table cellpadding="0" cellspacing="0">
			<tr style="font-weight:bold;">
				<td style="width:200px;">${bundle("category.name")}</td>
				<td>${bundle("category.priority")}</td>
            	<td>${bundle("category.type")}</td>
            	<#--
            	<td>${bundle("category.secret")}</td>
            	-->
            	<td>${bundle("category.createTime")}</td>
            	<td align="center">${bundle("form.action")}</td>
        	</tr>
        	<#list parents as p>
        	<tr>
				<td>${p.name}</td>
            	<td>${p.priority}</td>
            	<td>${p.type}</td>
            	<#--
            	<td><#if p.secret == true>${bundle("category.secret.public")}<#else>${bundle("category.secret.private")}</#if></td>
            	-->
            	<td>${p.createTime?string('yyyy-MM-dd')}</td>
            	<td class="action">
            		<a href="${rc.contextPath}/admin/category/view/${p.id}" class="view">${bundle("form.view")}</a>
            		<a href="${rc.contextPath}/admin/category/preUpdate/${p.id}" class="edit">${bundle("form.edit")}</a>
            		<a href="${rc.contextPath}/admin/category/delete/${p.id}"" class="delete">${bundle("form.delete")}</a>
            	</td>
        	</tr>   
        		<#list p.children as c>
        		<tr style="color:#c66653;">
					<td>----${c.name}</td>
	            	<td>${c.priority}</td>
	            	<td>${c.type}</td>
	            	<#--
	            	<td><#if c.secret == true><@s.message code="category.secret.public"/><#else><@s.message code="category.secret.private"/></#if></td>
	            	-->
	            	<td>${c.createTime?string('yyyy-MM-dd')}</td>
	            	<td class="action">
	            		<a href="${rc.contextPath}/admin/category/view/${c.id}" class="view">${bundle("form.view")}</a>
	            		<a href="${rc.contextPath}/admin/category/preUpdate/${c.id}" class="edit">${bundle("form.edit")}</a>
	            		<a href="${rc.contextPath}/admin/category/delete/${c.id}" class="delete">${bundle("form.delete")}</a>
            		</td>
	        	</tr>   
        		</#list>
			</#list>
                       
        </table>
        <br/><br/><br/>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>