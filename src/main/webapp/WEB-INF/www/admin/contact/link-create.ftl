<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/contact-link-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.linkManage.add")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<#-- form验证 -->
<link rel="stylesheet" href="${rc.contextPath}/styles/formValidator.2.2.1/css/validationEngine.jquery.css" type="text/css"/>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/languages/jquery.validationEngine-${(rc.locale)!'zh_CN'}.js" type="text/javascript" charset="utf-8"></script>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function(){
        jQuery("#formID").validationEngine();
    });
    
</script>

<div id="main">
	<form action="${rc.contextPath}/admin/link/c" class="jNice" method="POST" id="formID">
		<h3 class="lovej-action">
			<#if success??>${bundle("action.link.add")}</#if>
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
		</h3>
		<fieldset>
			<p>
				<label>${bundle("link.name")}<font color="red">*</font>:</label><input type="text" name="name" class="validate[required,maxSize[50]] text-long"  id="name"/>
			</p>
			<p>    
				<label>${bundle("link.site")}<font color="red">*</font>(http://)</label><input id="website" name="site" class="text-long validate[required,maxSize[255],custom[url]]" />
			</p>
			<p>
				<label>${bundle("link.description")}:</label><textarea name="description" id="description" class="validate[optional,maxSize[255]]" style="width:640px;height:100px;"></textarea>
			</p>
			<p>
				<label>${bundle("link.status")}<font color="red">*</font>:</label>
			</p>
			<div class="lovej-choise" >
				<div class="choise"><input id="status1" type="checkbox" checked="checked" name="status" value='show' class='validate[required,maxCheckbox[1]]'/>${bundle("link.status.show")}</div>
				<div class="choise"><input id="status2" type="checkbox" name="status" value='hidden' class='validate[required,maxCheckbox[1]]'/>${bundle("link.status.hidden")}</div>
			</div>
			<input id="actionLink" type="submit" value='${bundle("form.save")}' />
		</fieldset>
	</form>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>