<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/content-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.articleManage.manage")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<script>
$(document).ready(function(){
	
	$(".delete").click(function(){
		return window.confirm("${bundle("form.isConfirm")}");
	});
	
	$("#selectCategory").change(function(){
		var url = "${rc.contextPath}/admin/article/read";
		url = url + "?categoryId=" + $(this).val();
		window.location.href=url;
	});

	<#-- 设置select值 -->
	var sc = "${categoryId!""}";
	if (sc != "") {
		$("#selectCategory").attr("value", sc);
	}
	
});
</script>
<div id="main">
		<h3>${bundle("category.name")}
			<select id="selectCategory" name="category.id">
				<option value="">${bundle("form.select.all")}</option>
				<#list parents as p>
						<option value="${p.id}">${p.name}</option>
					<#list p.children as c>
						<option value="${c.id}">${c.name}</option>
					</#list>
				</#list>
			</select>
		</h3>
    	<table cellpadding="0" cellspacing="0">
			<tr style="font-weight:bold;">
				<td style="width:180px;">${bundle("article.title")}</td>
				<td>${bundle("article.status")}</td>
            	<td>${bundle("article.view")}</td>
            	<td>${bundle("article.postTime")}</td>
            	<td align="center">${bundle("form.action")}</td>
        	</tr>
        	<#list articles as p>
        	<tr>
				<td><#if p.topTime??><font style="color:red;">[${bundle("form.top")}]</font></#if>${p.title}</td>
            	<td>
            		<#if p.status == 'publish'>
            			${bundle("article.status.publish")}
            		<#elseif p.status == 'private'>
            			${bundle("article.status.private")}
            		<#elseif p.status == 'draft'>
            			${bundle("article.status.draft")}
            		</#if>
            	</td>
            	<td>${p.view}</td>
            	<td>${p.postTime?string('yyyy-MM-dd HH:mm:ss')}</td>
            	<td class="action">
            		<a target="_blank" href="${rc.contextPath}/view/${p.id}" class="view">${bundle("form.view")}</a>
            		<a href="${rc.contextPath}/admin/article/preUpdate?id=${p.id}&categoryId=${categoryId!''}&pageIndex=${pageInfo.pageIndex}" class="edit">${bundle("form.edit")}</a>
            		<a href="${rc.contextPath}/admin/article/delete?id=${p.id}&pageIndex=${pageInfo.pageIndex}&categoryId=${categoryId!''}" class="delete">${bundle("form.delete")}</a>
            	</td>
        	</tr>
			</#list>
        </table>
        <br/>
        <#if articles?size gt 0>
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