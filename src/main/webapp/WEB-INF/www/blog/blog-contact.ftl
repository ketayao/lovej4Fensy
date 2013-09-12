<#import "WEB-INF/www/blog/lib/common.ftl" as com>
<#import "WEB-INF/www/blog/lib/commentTree.ftl" as cmt>
<#--title定义-->
<#assign navTitle>联系我</#assign>

<#escape x as x?html>
<@com.page title=navTitle navTitle=navTitle keywords=navTitle>
<article id="post-${siteConfig.id}" class="post type-post status-publish format-standard hentry">
	<header class="entry-header">
	<h1 class="entry-title" style="text-align:center;">关于${siteConfig.name}</h1>
  	</header>
  	<!-- .entry-header -->
  
  	<div class="entry-content">
	<#noescape>${siteConfig.contactDescription}</#noescape>
	</div>
  	<!-- .entry-content -->
  	
</article>
<!-- #post -->

<div class="comments-area" id="comments">
<#-- form验证 -->
<link rel="stylesheet" href="${rc.contextPath}/styles/validator-0.2.1/jquery.validator.css" type="text/css"/>
<script src="${rc.contextPath}/styles/validator-0.2.1/jquery.validator.js" type="text/javascript" charset="utf-8"></script>
<script src="${rc.contextPath}/styles/validator-0.2.1/local/zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script charset="utf-8" src="${rc.contextPath}/styles/kindeditor-4.1.7/kindeditor-min.js"></script>
<script>
	var editor;
	KindEditor.ready(function(K) {
	    editor = K.create('#comment', {
	    	minWidth: '625px',
	    	width : '625px',
	    	height: '250px',
			resizeType : 1,
			urlType: 'domain',
			shadowMode : false,
			allowPreviewEmoticons : false,
			newlineTag : 'br',
			afterCreate : function(){
				K.ctrl(this.edit.iframe.get(0).contentWindow.document, 13, function() {
					$("#commentform").submit();
				});
			},
			afterChange : function() {
				this.sync();
			},
			items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'link','unlink','source','about']
	    });
	});
	
    jQuery(document).ready(function(){
		$('#commentform').validator({
		    fields: {
		        'name': 'required;length[0~16]',
		        'email': 'required;email;length[0~64]',
		        'site': 'url;length[0~128]',
		        'content':'required;length[~500]',
		        'verifyCode':'required;length[5]'
		    }
		});  
    });	
    
    jQuery(document).ready(function(){
    	$("#captcha").click(function(){
    		$(this).attr("src", "${rc.contextPath}/ImageCaptcha?time=" + new Date());
    		return false;
    	});
    });
</script>	
	<div class="comment-respond" id="respond">
		<h3 class="comment-reply-title" id="reply-title">发表评论 </h3>
		<form class="comment-form" id="commentform" method="post" action="${rc.contextPath}/contact/c">
			<p class="comment-notes">电子邮件地址不会被公开。</p>							
			<p class="comment-form-author"><label for="author">姓名</label> <input type="text" size="30" name="name" id="author" ></p>
			<p class="comment-form-email"><label for="email">电子邮件</label> <input type="text" size="30" name="email" id="email" ></p>
			<p class="comment-form-url"><label for="url">站点</label> <input type="text" size="30" value="" name="site" id="url" ></p>
			<p class="comment-form-comment">
				<label for="comment">留言
					<span class="msg-box" data-for="content" style="margin-top:4px;"></span>
				</label> 
				<textarea aria-required="true" name="content" id="comment"></textarea>
			</p>						
			<p class="form-submit">
			<input type="text" id="verifyCode" name="verifyCode" style="float:left;">
			<div style="float:left;">
				<img id="captcha" width="120" height="40" src="${rc.contextPath}/ImageCaptcha" alt="点击刷新验证码">
				<span class="msg-box" data-for="verifyCode" style="margin-top:10px;"></span>
			</div>
			<input type="submit" value="发表评论" id="submit" name="submit" style="float:right;">
			</p>
		</form>
	</div><!-- #respond -->
</div><!-- #comments .comments-area -->
</@com.page>
</#escape>