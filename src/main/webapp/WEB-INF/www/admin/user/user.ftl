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
<#-- 日期选择  -->
<link href="${rc.contextPath}/styles/wdDatePicker/css/dp.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/styles/wdDatePicker/src/Plugins/datepicker_lang_zh.js" type="text/javascript"></script>
<script src="${rc.contextPath}/styles/wdDatePicker/src/Plugins/jquery.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    jQuery(document).ready(function(){
		$("#birthday").datepicker({ picker: "<img class='picker' style='padding-top:7px;' vertical-align='middle' src='${rc.contextPath}/styles/wdDatePicker/css/cal.gif' alt=''/>" });
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
				<label>${bundle("user.frozen")}:</label>
				<input type="radio" name="frozen" value="0"  <#if user.frozen == 0>checked="checked"</#if> />${bundle("user.frozen.false")}&nbsp;&nbsp;&nbsp;&nbsp;
			    <input type="radio" name="frozen" value="1" <#if user.frozen == 1>checked="checked"</#if> />${bundle("user.frozen.true")}
			</p>
			<p>
				<label>${bundle("user.role")}:</label><input type="text" name="role" id="role" class="validate[required,custom[integer],min[0],max[127]] text-medium" value="${user.role}"/>
			</p>
			<input type="submit" value='${bundle("form.update")}' />
		</fieldset>
	</form>
</div>
<#-- // #main -->

</@com.page>

</#escape>
</#compress>