<#import "WEB-INF/www/blog/lib/common.ftl" as com>
<#import "WEB-INF/www/blog/lib/commentTree.ftl" as cmt>
<#--title定义-->
<#if article.category.parent!=null>
	<#assign navTitle>${article.category.parent.name}</#assign>
<#else>
	<#assign navTitle>${article.category.name}</#assign>
</#if>

<#escape x as x?html>
<@com.page title='${article.title}' navTitle=navTitle keywords='${article.title}'>
<article id="post-${article.id}" class="post type-post status-publish format-standard hentry">
	<header class="entry-header">
	<h1 class="entry-title">${article.title}</h1>
	<div class="comments-link"> 
		<#if article.comments?size < 1>
			<a href="#respond" title="${article.title}"><span class="leave-reply">发表回复</span></a>
		<#else>
			<a href="#comments" title="${article.title}"><span class="leave-reply">${article.comments?size}条回复</span></a>
		</#if>
		<div class="leave-reply" style="float:right;">${article.view} views</div>
	</div>
	<!-- .comments-link --> 
  	</header>
  	<!-- .entry-header -->
  
  	<div class="entry-content">
	<#noescape>${article.content}</#noescape>
	<#if article.attaches?size gt 0>
		<h4>附件：</h4>
		<ul>
		<#list article.attaches as a>
  			<li><a href="${rc.contextPath}/download?file=${a.url}" target="_blank">${a.description}</a>&nbsp;&nbsp;(下载次数:${a.download})</li>
		</#list>
		</ul>
	</#if>
	</div>
  	<!-- .entry-content -->
  
	<footer class="entry-meta"> 本条目发布于
	<a href="${rc.contextPath}/view/${article.id}" title="${article.postTime?string.short}" rel="bookmark"><time class="entry-date" datetime="${article.postTime}">${article.postTime?string("yyyy年MM月dd日")}</time></a>。
	属于<a href="${rc.contextPath}/archive/category/${article.category.id}" title="查看${article.category.name}中的全部文章" rel="category tag">${article.category.name}</a>分类
	<#if article.articleTags?size gt 0>
	，被贴了
	<#list article.articleTags as at>
		<a href="${rc.contextPath}/archive/tag/${at.tag.id}" rel="tag">${at.tag.title}</a><#if (item_has_next)>、</#if>
	</#list>标签
	</#if>。
	<span class="">作者是<span class="author vcard"><a class="url fn n" href="${rc.contextPath}/archive/user/${article.user.id}" title="查看所有由${article.user.nickname}发布的文章" rel="author">${article.user.nickname}</a></span>。</span>
	<#if user.id == article.userId>
		<a class="url" href="${rc.contextPath}/admin/article/preUpdate?id=${article.id}">修改此条目</a>
	</#if> 
	</footer>
  <!-- .entry-meta --> 
</article>
<!-- #post -->
<div style="border:1px #EDEDED solid; background-color: #F8F8F8; padding: 10px; margin-top: -50px; margin-bottom: 30px; line-height: 16px; font-size: 13px;">
<table>
	<tbody>
	<tr>
		<td><a href="http://creativecommons.org/licenses/by-nc-sa/3.0/deed.zh" rel="license"><img src="${rc.contextPath}/styles/blog/images/88x31.png" style="border-width:0" alt="知识共享许可协议"></a></td>
		<td style="vertical-align: top; padding-left: 5px; ">发表于${siteConfig.name}的原创文章，转载时请注明：转载自<a href="${siteConfig.url}">${siteConfig.name}</a><br>本文采用<a href="http://creativecommons.org/licenses/by-nc-sa/3.0/deed.zh" rel="license">知识共享署名-非商业性使用-相同方式共享 3.0 未本地化版本许可协议</a>进行许可</td>
	</tr>
	</tbody>
</table>
</div>
<nav class="nav-single">
	<h3 class="assistive-text">文章导航</h3>
	<#if pa??>
	<span class="nav-previous"><a rel="prev" href="${rc.contextPath}/view/${pa.id}"><span class="meta-nav">←</span>${pa.title}</a></span>
	</#if>
	<#if na??>
	<span class="nav-next"><a rel="next" href="${rc.contextPath}/view/${na.id}">${na.title}<span class="meta-nav">→</span></a></span>
	</#if>
</nav>

<div class="comments-area" id="comments">
	<#if comments?size gt 0>
	<script>
		function addParentId(parentId) {
			$("#comment_parent_ID").val(parentId);
			$("#cancel-comment-reply-link").show();
			return false;
		}
		function removeParentId() {
			$("#comment_parent_ID").val(0);
			$("#cancel-comment-reply-link").hide();
			return false;
		}
	</script>
	<h2 class="comments-title">《<span>${article.title}</span>》上有${article.comments?size}条评论</h2>
	<ol class="commentlist">
	<#list comments as c>
	<li id="li-comment-${c.id}" class="comment <#if c.userId?? && c.userId gt 0>bypostauthor</#if>">
		<article class="comment" id="comment-${c.id}">
			<header class="comment-meta comment-author vcard">
				<#if c.userId?? && c.userId gt 0>
					<img width="44" height="44" class="photo" src="${rc.contextPath}/styles/blog/images/author.jpeg" alt="">
					<cite><b class="fn">${article.user.nickname}</b> <span>文章作者</span></cite>	
				<#else>
					<img width="44" height="44" class="photo" src="${rc.contextPath}/styles/blog/images/somebody.jpeg" alt="">
					<cite>
						<b class="fn">
					<#if c.site?? && c.site?trim!=''>
						<a class="url" rel="external nofollow" href="${c.site}"><#if !c.name?? || c.name?trim==''>匿名<#else>${c.name}</#if></a>
					<#else>
						<#if !c.name?? || c.name?trim==''>匿名<#else>${c.name}</#if>					
					</#if>
						</b>
					</cite>					
				</#if>
					<a href="${rc.contextPath}/view/${article.id}/${cp.pageIndex}#comment-${c.id}"><time datetime="${c.postTime}">${c.postTime?string('yyyy 年 MM 月 dd 日')} ${c.postTime?time?string.short}</time></a>
				<#if (!c.children?? || c.children?size lt 1) && (user.id == article.userId)>		
					<a class="url" href="${rc.contextPath}/admin/comment/d/${c.id}" style="text-decoration:underline;"><time>删除此评论</time></a>
				</#if>
			</header><!-- .comment-meta -->
			<section class="comment-content comment">
				<p><#noescape>${c.content}</#noescape></p>
			</section><!-- .comment-content -->
			<div class="reply">
				<a onclick='return addParentId(${c.id})' href="#" class="comment-reply-link">回复</a> 
				<span>↓</span>			
			</div><!-- .reply -->
		</article><!-- #comment-## -->
		<@cmt.tree children=c.children/>		
	</li>	
	</#list>
	</ol>
	
	<#if cp?? && cp.totalPage gt 1>
		<nav role="navigation" class="navigation" id="comment-nav-below">
			<h1 class="assistive-text section-heading">评论导航</h1>
			<#if cp.pageIndex != 1><div class="nav-previous"><a href="${rc.contextPath}/view/${article.id}/${cp.prePage}#comments">← 早期评论</a></div></#if>
			<#if cp.pageIndex != cp.totalPage><div class="nav-next"><a href="${rc.contextPath}/view/${article.id}/${cp.nextPage}#comments">较新评论 →</a></div></#if>
		</nav>		
	</#if>
	</#if>
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
		        'name': 'length[0~16]',
		        'email': 'email;length[0~64]',
		        'site': 'url;length[0~128]',
		        'content':'required;length[~250]',
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
		<h3 class="comment-reply-title" id="reply-title">发表评论 <small><a style="display:none;" onclick='return removeParentId()' href="#" id="cancel-comment-reply-link" rel="nofollow">取消回复</a></small></h3>
		<form class="comment-form" id="commentform" method="post" action="${rc.contextPath}/comment/${cp.pageIndex}">
			<p class="comment-notes">电子邮件地址不会被公开。</p>							
			<p class="comment-form-author"><label for="author">姓名</label> <input type="text" size="30" name="name" id="author" value="${cookieUser.name!''}"></p>
			<p class="comment-form-email"><label for="email">电子邮件</label> <input type="text" size="30" name="email" id="email" value="${cookieUser.email!''}"></p>
			<p class="comment-form-url"><label for="url">站点</label> <input type="text" size="30" value="" name="site" id="url" value="${cookieUser.site!''}"></p>
			<p class="comment-form-comment">
				<label for="comment">评论
					<span class="msg-box" data-for="content" style="margin-top:4px;"></span>
				</label> 
				<textarea aria-required="true" name="content" id="comment"></textarea>
			</p>						
			<p class="form-submit">
			<input type="hidden" id="comment_parent_ID" value="0" name="parentId">
			<input type="hidden" id="comment_post_ID" value="${article.id}" name="articleId">
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