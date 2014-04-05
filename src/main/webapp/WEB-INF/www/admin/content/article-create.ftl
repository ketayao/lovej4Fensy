<#compress>
<#import "WEB-INF/www/admin/lib/common.ftl" as com>
<#include "WEB-INF/www/admin/lib/content-nav.ftl"/>

<#--currentNav定义-->
<#assign currentNav>${bundle("site.articleManage.add")}</#assign>
</#compress>
<#escape x as x?html>
<@com.page title=title sideNav=sideNav sideNavUrl=sideNavUrl parentNav=parentNav parentNavUrl=parentNavUrl currentNav=currentNav>
<#-- autocomplete -->
<script type='text/javascript' src='${rc.contextPath}/styles/jquery-autocomplete/jquery.autocomplete.pack.js'></script>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/styles/jquery-autocomplete/jquery.autocomplete.css" />

<#-- form验证 -->
<link rel="stylesheet" href="${rc.contextPath}/styles/formValidator.2.2.1/css/validationEngine.jquery.css" type="text/css"/>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/languages/jquery.validationEngine-${(rc.locale)!'zh_CN'}.js" type="text/javascript" charset="utf-8"></script>
<script src="${rc.contextPath}/styles/formValidator.2.2.1/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function(){
        jQuery("#formID").validationEngine();
        
        $("#tags").autocomplete(${tags}, {
			width: 200,
			multiple: true,
			matchContains: true
		});
    });
    
</script>

<script charset="utf-8" src="${rc.contextPath}/styles/kindeditor-4.1.7/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/styles/kindeditor-4.1.7/shcodeandquote.css" />
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[id="content"]', {
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
				jQuery('#content').validationEngine('hide');
			}
		});
	});
	
	var editorSummary;
	
	KindEditor.ready(function(K) {
		editorSummary = K.create('textarea[id="summary"]', {
			langType : '<#if rc.locale?? && rc.locale != 'zh_CN'>en<#else>${(rc.locale)!'zh_CN'}</#if>',
			uploadJson : '${rc.contextPath}/admin/file/upload',
			cssPath : ['${rc.contextPath}/styles/kindeditor-4.1.7/plugins/shcode/shcode.css'],
			allowFileManager : false,
			newlineTag : 'br',
		    items : ['bold', 'italic', 'underline', 'strikethrough', 'removeformat','|','insertorderedlist', 'insertunorderedlist', 
				 'forecolor', 'hilitecolor', 'fontname', 'fontsize',  '|', 'link', 'unlink', 'emoticons', 
				 'shcode', 'image', 'flash', 'quote', '|', 'fullscreen', 'source','about'],
			afterChange : function() {
				jQuery('#summary').validationEngine('hide');
			}
		});
	});
	
</script>

<script type="text/javascript" src="${rc.contextPath}/styles/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript">
function ajaxFileUpload(){
	$("#loading").ajaxStart(function(){
		$(this).show();
	}).ajaxComplete(function(){
		$(this).hide();
	});

	var arr = [];
	arr[0] = 'imgFile';
	$.ajaxFileUpload({
		url:'${rc.contextPath}/admin/file/upload',
		secureuri:false,
		fileElementId:arr,
		dataType: 'json',
		data:{},
		success: function (data, status) {
			if(typeof(data.error) != 'undefined'){
				if(data.error == 1){
					alert(data.message);
				} else {
					$("#showUrl").attr("src", data.url);
					$("#showUrl").show();
					$("#delImgBtn").show();
					$("#imgUrl").val(data.url);
				}
			}
		},
		error: function (data, status, e){
			alert(e);
		}
	});
	
	return false;
}

function deleteImg(){
	$("#imgUrl").val("");
	$("#showUrl").hide();
	$("#delImgBtn").hide();
}
</script>

<div id="main">
	<form action="${rc.contextPath}/admin/article/create" class="jNice" method="POST" id="formID">
		<h3 class="lovej-action">
			<#if success??>${bundle("action.article.add")}</#if>
		</h3>
		<h3>
			<#noescape>${bundle("form.notice.required")}</#noescape>
			<div class="choise" style="float:right;color:red;"><input id="top" type="checkbox" name="top"/>${bundle("article.top")}</div>
		</h3>
		<fieldset>
			<p>
				<label>${bundle("article.title")}<font color="red">*</font>:</label><input type="text" name="title" class="validate[required,maxSize[255]] text-long"  id="title"/>
			</p>
			<p>
				<label>${bundle("article.imgUrl")}:<img id="loading" src="${rc.contextPath}/styles/AjaxFileUploaderV2.1/loading.gif" style="display:none;"></label>
				<input id="imgFile" type="file" size="45" name="imgFile" class="text-long">
				<input type="button" value="上传" onclick="return ajaxFileUpload();">
			</p>
			<p>
				<img id="showUrl" src="" style="display:none;">
				<input id="delImgBtn" type="button" value="删除" onclick="deleteImg();" style="display:none;">
				<input id="imgUrl" name="imgUrl" type="hidden">
			</p>
			<p>
				<label>${bundle("article.keywords")}:</label><input type="text" name="keywords" id="keywords" class="validate[optional,maxSize[255]] text-long" style="width:640px;"/>
			</p>
			<p>
				<label>${bundle("article.category")}<font color="red">*</font>:</label>
			</p>
			<div class="lovej-choise">
				<#if !parents?? || parents?size==0>
					<font color="red">--------${bundle("form.notice.article.category")}</font>
				<#else>
					<#list parents as p>
						<div class="choise-group">
						<div class="choise"><input id="p${p.id}" type="checkbox" name="categoryId" value='${p.id}' class='validate[required,maxCheckbox[1]]'/>${p.name}(${p.type})</div>
						<#list p.children as c>
							<#if (c_index+1)%4 == 0>
								<div class="choise"></div>
							</#if>
							<div class="choise"><input id="c${c.id}" type="checkbox" name="categoryId" value='${c.id}' class='validate[required,maxCheckbox[1]]'/>${c.name}(${c.type})</div>
						</#list>
						</div>
					</#list>	
				</#if>
			</div>
			<p>
				<label>${bundle("article.content")}<font color="red">*</font>:</label>
				<textarea id="content" name="content" style="width:655px;height:500px;" ></textarea>
			</p>
			<p>
				<label>${bundle("article.tag")}:</label><input type="text" name="tags" id="tags" class="validate[optional,maxSize[255]] text-long" style="width:640px;"/>
			</p>			
			<p>
				<label>${bundle("article.summary")}:</label><textarea style="width:655px;height:300px;" name="summary" id="summary" class="validate[optional,maxSize[1000]]"></textarea>
			</p>
			<p>
				<label>${bundle("article.status")}<font color="red">*</font>:</label>
			</p>
			<div class="lovej-choise" >
				<div class="choise"><input id="status1" type="checkbox" checked="checked" name="status" value='publish' class='validate[required,maxCheckbox[1]]'/>${bundle("article.status.publish")}</div>
				<div class="choise"><input id="status2" type="checkbox" name="status" value='private' class='validate[required,maxCheckbox[1]]'/>${bundle("article.status.private")}</div>
				<div class="choise"><input id="status3" type="checkbox" name="status" value='draft' class='validate[required,maxCheckbox[1]]'/>${bundle("article.status.draft")}</div>
			</div>
			<input id="actionArticle" type="submit" value='${bundle("form.save")}' />
		</fieldset>
	</form>
</div>
<!-- // #main -->
</@com.page>
</#escape>