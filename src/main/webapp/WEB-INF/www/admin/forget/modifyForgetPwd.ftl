<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/user-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.userManage.updatePassword")}</#assign>

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
	<form action="${rc.contextPath}/admin/forget/modifyForgetPwd" class="jNice" method="POST" id="formID">
		<input type="hidden" name="username" value="${username}">
		<input type="hidden" name="forgetCode" value="${forgetCode}">
		
		<h3 class="lovej-action">
			<#if RequestParameters.success??>
				<#if RequestParameters.success == "true">
					${bundle("action.user.updatePassword")}
				<#else>
					${bundle("action.user.updatePassword.false")}
				</#if>
			</#if>
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
		</h3>
		<fieldset>
			<p>
				<label>${bundle("user.password.new")}<font color="red">*</font>:
				</label><input type="password" name="newPassword" class="validate[required,minSize[5],maxSize[12]] text-long" id="newPassword"/>
			</p>
			<p>
				<label>${bundle("user.password.confirm")}<font color="red">*</font>:
				</label><input type="password" name="confirmPassword" class="validate[required,minSize[5],maxSize[12],equals[newPassword]] text-long" id="confirmPassword"/>
			</p>
			<input type="submit" value='${bundle("form.update")}' />
		</fieldset>
	</form>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>