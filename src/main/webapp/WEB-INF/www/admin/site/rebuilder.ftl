<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com/>
<#include "WEB-INF/www/admin/lib/site-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.siteConfigManage.index.create")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<div id="main">
		<h3 style="text-align:center;color:red;">
			<#if success??>${bundle("action.index.create", useTime)}</#if>
		</h3>
		<h3>
			${bundle("form.notice.index.create")}
		</h3>
		<form action="${rc.contextPath}/admin/lucene/rebuilder" cssClass="jNice" method="POST" id="formID">
        <fieldset style="border:none;">
        <input type="submit" value='${bundle("site.siteConfigManage.index.create")}' />
        </fieldset>
        </form>
        <br/>	
        <br/><br/>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>