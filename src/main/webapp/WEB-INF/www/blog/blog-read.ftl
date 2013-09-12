<#import "WEB-INF/www/blog/lib/common.ftl" as com>
<#--title定义-->
<#if category.parent!=null>
	<#assign navTitle>${category.parent.name}</#assign>
<#elseif category??>
	<#assign navTitle>${category.name}</#assign>
<#elseif who??>
	<#assign navTitle>${who}</#assign>
<#else>	
	<#assign navTitle>首页</#assign>
</#if>

<#escape x as x?html>
<@com.page title=navTitle navTitle=navTitle keywords=navTitle>
<#list articles as a>
<article id="post-${a.id}" class="post type-post status-publish format-standard hentry">
	<header class="entry-header">
	<h1 class="entry-title"> <a href="${rc.contextPath}/view/${a.id}" rel="bookmark"><#noescape>${a.title}</#noescape></a><#if a.topTime??><font style="color:red;font-size:15px;">[Top]</font></#if> </h1>
	<div class="comments-link"> <a href="http://www.elanblog.com/2013/08/19/parsley/#respond" title="${a.title}"><span class="leave-reply">发表回复</span></a>
	<div class="leave-reply" style="float:right;">${a.view} views</div>
	</div>
	<!-- .comments-link --> 
  	</header>
  	<!-- .entry-header -->
  
  	<div class="entry-content">
	<#noescape>${a.preview}</#noescape>	
	<a class="more-link" href="${rc.contextPath}/view/${a.id}">继续阅读 <span class="meta-nav">→</span></a></p>
	</div>
  	<!-- .entry-content -->
  
	<footer class="entry-meta"> 本条目发布于
	<a href="${rc.contextPath}/view/${a.id}" title="${a.postTime?string.short}" rel="bookmark"><time class="entry-date" datetime="${a.postTime}">${a.postTime?string("yyyy年MM月dd日")}</time></a>。
	属于<a href="${rc.contextPath}/archive/category/${a.category.id}" title="查看${a.category.name}中的全部文章" rel="category tag">${a.category.name}</a>分类
	<#if a.articleTags?size gt 0>
	，被贴了
	<#list a.articleTags as at>
		<a href="${rc.contextPath}/archive/tag/${at.tag.id}" rel="tag">${at.tag.title}</a><#if (item_has_next)>、</#if>
	</#list>标签
	</#if>。
	<span class="">作者是<span class="author vcard"><a class="url fn n" href="${rc.contextPath}/archive/user/${a.user.id}" title="查看所有由${a.user.nickname}发布的文章" rel="author">${a.user.nickname}</a></span>。</span> 
	</footer>
  <!-- .entry-meta --> 
</article>
<!-- #post -->
</#list> 

<#if articles?size gt 0>
	<div class="wp-pagenavi">
	<#noescape>${pageInfo.blogStyle}</#noescape>
	</div>
 </#if>
</@com.page>
</#escape>