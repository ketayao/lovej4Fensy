<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com/>
<#include "WEB-INF/www/admin/lib/site-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.siteConfigManage.manage")}</#assign>

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

<script charset="utf-8" src="${rc.contextPath}/styles/kindeditor-4.1.7/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/styles/kindeditor-4.1.7/shcodeandquote.css" />
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[id="about"]', {
			langType : '<#if rc.locale?? && rc.locale != 'zh_CN'>en<#else>${(rc.locale)!'zh_CN'}</#if>',
			uploadJson : '${rc.contextPath}/admin/file/upload',
			cssPath : ['${rc.contextPath}/styles/kindeditor-4.1.7/plugins/shcode/shcode.css'],
			allowFileManager : false,
			newlineTag : 'br',
			items : [
					'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'shcode', 'quote', 'cut', 'copy', 'paste',
					'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
					'superscript', 'clearhtml', 'quickformat', 'selectall',
					'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
					'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
					'flash', 'media', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
					'anchor', 'link', 'unlink', '/', 'fullscreen', 'about'
				],
			afterChange : function() {
				jQuery('#about').validationEngine('hide');
			}
		});
	});
</script>
<div id="main">
		<h3 style="text-align:center;color:red;">
			<#if success??>${bundle("action.siteConfig.update")}</#if>
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
		</h3>
		<form action="${rc.contextPath}/admin/siteConfig/u" class="jNice" method="POST" id="formID">
		<fieldset>
			<p>
				<label>${bundle("siteConfig.name")}<font color="red">*</font>:</label><input type="text" name="name" class="validate[required,maxSize[255]] text-long" value="${siteConfig.name}"  id="name"/>
			</p>
			<p>
				<label>${bundle("siteConfig.url")}<font color="red">*</font>(http://):</label><input id="website" name="url" class="text-long validate[required,maxSize[255],custom[url]]" value="${siteConfig.url}" />
			</p>
			<p>    
				<label>${bundle("siteConfig.about")}:</label><input id="about" name="about" class="text-long validate[optional,maxSize[255]]" value="${siteConfig.about}" />
			</p>
			<p>    
				<label>${bundle("siteConfig.icp")}:</label><input id="icp" name="icp" class="text-long validate[optional,maxSize[50]]" value="${siteConfig.icp}" />
			</p>
			<p>
				<label>${bundle("siteConfig.contactDescription")}<font color="red">*</font>:</label><textarea name="contactDescription" id="description" style="width:655px;height:200px;">${siteConfig.contactDescription}</textarea>
			</p>
			<input type="submit" value='${bundle("form.save")}' />
		</fieldset>
        </form>
        <br/>	
        <br/><br/>
</div>
<!-- // #main -->

</@com.page>

</#escape>
</#compress>