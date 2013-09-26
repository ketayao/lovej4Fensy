<#macro tree children>
	<#if children?? && children?size gt 0>
	<#list children as c>
	<ol class="children">
		<li id="li-comment-${c.id}" class="comment comment-author-dong <#if c.userId?? && c.userId gt 0>bypostauthor</#if>">
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
					<p>${c.content}</p>
				</section><!-- .comment-content -->
				<div class="reply">
					<a onclick='return addParentId(${c.id})' href="#" class="comment-reply-link">回复</a> 
					<span>↓</span>			
				</div><!-- .reply -->
			</article><!-- #comment-## -->
            <@tree children=c.children/>
		</li><!-- #comment-## -->
	</ol>		
	</#list>
	</#if>
</#macro>