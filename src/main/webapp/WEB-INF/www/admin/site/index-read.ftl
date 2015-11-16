<#compress>
<#import "/WEB-INF/content/admin/lib/common.ftl" as com/>
<#include "/WEB-INF/content/admin/lib/site-nav.ftl"/>

<#--currentNav定义-->
<@s.set var="currentNav">
	<@s.text name="site.siteConfigManage.index.create"/>
</@s.set>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<div id="main">
		<h3 style="text-align:center;color:red;">
			<@s.actionmessage/>
		</h3>
		<h3>
			<@s.text name="form.notice.index.create"/>
		</h3>
		<@s.form action="createIndex" namespace="/admin/site"  cssClass="jNice" method="POST" id="formID">
        <fieldset style="border:none;">
        <input type="submit" value='<@s.text name="site.siteConfigManage.index.create"/>' />
        </fieldset>
        </@s.form>
        <br/>	
        <br/><br/>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>