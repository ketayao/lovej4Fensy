<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/content-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.categoryManage.add")}</#assign>

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
    	jQuery("#type1[value='post']").click(function(){
    		jQuery(".lovej-autoindent").show();
    	});
    	
    	jQuery("#type2[value='page']").click(function(){
    		jQuery(".lovej-autoindent").hide();
    	});
    	
    	jQuery("#actionCategory").click(function(){
    		var $page = jQuery("#type1[value='post']");
    		if ($page.is(":checked")) {
    			var $parent = jQuery(".parentId");
    			if ($parent.is(":checked")) {
    				return true;
    			} else {
    				return false;
    			}
    		}
    		return true;
    	});

    });
</script>


<div id="main">
	<form action="${rc.contextPath}/admin/category/create" class="jNice" method="POST" id="formID">
		<h3 class="lovej-action">
			<#if success??>${bundle("action.category.add")}</#if>
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
		</h3>
		<h3>
			${bundle("form.notice.priority")}
		</h3>
		<fieldset>
			<p>
				<label>${bundle("category.name")}<font color="red">*</font>:</label><input type="text" name="name" class="validate[required,maxSize[50]] text-long"  id="name"/>
			</p>
			<p>
				<label>${bundle("category.priority")}:</label><input type="text" name="priority" class="validate[optional,custom[integer],maxSize[2]] text-long" value="99" id="priority"/>
			</p>
			<p>
				<label>${bundle("category.type")}:</label>
				<input type="radio" id="type1" name="type"  value="post" checked="checked" />${bundle("category.type.post")}&nbsp;&nbsp;
				<input type="radio" id="type2" name="type"  value="page" />${bundle("category.type.page")}
			</p>
			<div class="lovej-autoindent">
				<label>----${bundle("category.parent")}(<font color="red">${bundle("form.notice.category.post")}</font>):</label>
				<#if !parents?? || parents?size==0>
					<font color="red">--------${bundle("form.notice.category.notParent")}</font>
				<#else>
					<#list parents as p>
						<div><input type="radio" name="parentId" value='${p.id}' class='parentId'/>${p.name}</div>
					</#list>	
				</#if>
			</div>
			<#--
			<p>
				<label><@s.message code="category.secret"/>:</label>
				<input type="radio" id="secret1" name="secret" checked="checked" value="true"/><@s.message code="category.secret.public"/>&nbsp;&nbsp;
				<input type="radio" id="secret2" name="secret" value="false"/><@s.message code="category.secret.private"/>
			</p>
			-->
			<p>
				<label>${bundle("category.description")}:</label><textarea rows="1" cols="1" name="description" id="description" class="validate[optional,maxSize[255]]"></textarea>
			</p>
			<input id="actionCategory" type="submit" value='${bundle("form.save")}' />
		</fieldset>
	</form>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>