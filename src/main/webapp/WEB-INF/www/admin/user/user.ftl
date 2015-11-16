<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/user-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.userManage.updateBase")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<#-- form验证 -->
<link rel="stylesheet" href="${rc.contextPath}/styles/formValidator.2.2.1/css/validationEngine.jquery.css" type="text/css"/>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/languages/jquery.validationEngine-${(rc.locale)!'zh_CN'}.js" type="text/javascript" charset="utf-8"></script>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    jQuery(document).ready(function(){
        // binds form submission and fields to the validation engine
        jQuery("#formID").validationEngine();
    });
</script>

<div id="main">
	<form action="${rc.contextPath}/admin/user/info" class="jNice" method="POST" id="formID">
		<input type="hidden" id="id" value="${user.id}"/>
		<h3 class="lovej-action">
			<#if RequestParameters.success??>${bundle("action.user.updateBase")}</#if>
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
		</h3>
		<fieldset>
			<p>
				<label>${bundle("user.username")}<font color="red">*</font>:</label><input type="text" class="text-long" value="${user.username}" readonly="readonly"/>
			</p>		
			<p>
				<label>${bundle("user.nickname")}<font color="red">*</font>:</label><input type="text" name="nickname" class="validate[required,maxSize[32]] text-long" value="${user.nickname}" id="nickname"/>
			</p>
			<p>
				<label>${bundle("user.email")}:</label><input type="text" name="email" class="validate[optional,custom[email],maxSize[128]] text-long" value="${user.email}" id="email"/>
			</p>
			<p>
				<label>${bundle("user.frozen")}:
				<#if user.frozen == 0>
					${bundle("user.frozen.false")}
				<#else>
					${bundle("user.frozen.true")}
				</#if> 
				</label>
			</p>
			<p>
				<label>${bundle("user.role")}:
				<#if user.role == 1>
					${bundle("user.role.normal")}
				<#elseif user.role == 127>
					${bundle("user.role.admin")}
				</#if> 
				</label>
			</p>
			<input type="submit" value='${bundle("form.update")}' />
		</fieldset>
	</form>
</div>
<#-- // #main -->

</@com.page>

</#escape>
</#compress>