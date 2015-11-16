<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/content-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.categoryManage.update")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>

<#-- form验证 -->
<link rel="stylesheet" href="${rc.contextPath}/styles/formValidator.2.2.1/css/validationEngine.jquery.css" type="text/css"/>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/languages/jquery.validationEngine-${(rc.locale)!'zh_CN'}.js" type="text/javascript" charset="utf-8"></script>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function(){
        // binds form submission and fields to the validation engine
        jQuery("#formID").validationEngine();
    });
    
    <#-- 显示父目录 -->
    jQuery(document).ready(function(){    	
    	jQuery("#back").click(function(){
    		window.location.href="${rc.contextPath}/admin/category/read";
    	});
    });
</script>


<div id="main">
	<form action="${rc.contextPath}/admin/category/update" class="jNice" method="POST" id="formID">
		<h3 class="lovej-action">
			<#if success??>${bundle("action.category.update")}</#if>
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
			
		</h3>
		<h3>
			${bundle("form.notice.priority")}
			
		</h3>
		<fieldset>
			<p>
				<label>${bundle("category.name")}<font color="red">*</font>:</label><input type="text" name="name" class="validate[required,maxSize[50]] text-long" value="${category.name}" id="name"/>
			</p>
			<p>
				<label>${bundle("category.priority")}:</label><input type="text" name="priority" class="validate[optional,custom[integer],maxSize[2]] text-long" value="${category.priority}" id="priority"/>
			</p>
			<#--
			<p>
				<label>${bundle("category.secret")}:</label>
				<input type="radio" id="secret1" name="secret" <#if category.secret == true>checked="checked"</#if> value="true"/>${bundle("category.secret.public")}&nbsp;&nbsp;
				<input type="radio" id="secret2" name="secret" <#if category.secret == false>checked="checked"</#if> value="false"/>${bundle("category.secret.private")}
			</p>
			-->
			<p>
				<label>${bundle("category.description")}:</label><textarea rows="1" cols="1" name="description" id="description" class="validate[optional,maxSize[255]]">${category.description}</textarea>
			</p>
			<input name="id" value="${category.id}" type="hidden"/>

			<input id="actionCategory" type="submit" value='${bundle("form.save")}' />&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="back" type="button" value='${bundle("form.back")}' />
		</fieldset>
	</form>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>