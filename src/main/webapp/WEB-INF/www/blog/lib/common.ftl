<#compress>
<#import "WEB-INF/www/blog/lib/lovej-blog.ftl" as lj>
<#include "WEB-INF/www/blog/lib/param.ftl"/>
<#macro page title navTitle keywords>
<!DOCTYPE html>
<!--[if IE 7]>
<html class="ie ie7" lang="zh-CN">
<![endif]-->
<!--[if IE 8]>
<html class="ie ie8" lang="zh-CN">
<![endif]-->
<!--[if !(IE 7) | !(IE 8)  ]><!-->
<html lang="zh-CN">
<!--<![endif]-->
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width">
<meta name="robots" content="index, follow" />
<meta name="Keywords" content="${siteConfig.name} ${keywords}"/>
<meta name="Description" content="${siteConfig.name}"/>
<title>${title} | ${siteConfig.name}</title>
<!--[if lt IE 9]>
<script src="${rc.contextPath}/styles/blog/js/html5.js" type="text/javascript"></script>
<![endif]-->
<link rel="canonical" href="${siteConfig.url}/">
<link rel="shortcut icon" href="${rc.contextPath}/styles/admin/images/favicon.ico" />
<link rel="stylesheet" id="twentytwelve-style-css" href="${rc.contextPath}/styles/blog/css/style.css" type="text/css" media="all">
<!--[if lt IE 9]>
<link rel='stylesheet' id='twentytwelve-ie-css'  href='${rc.contextPath}/styles/blog/css/ie.css' type='text/css' media='all' />
<![endif]-->

<link rel="stylesheet" id="wp-pagenavi-style-css" href="${rc.contextPath}/styles/blog/css/white_blue.css" type="text/css" media="all">

<link rel="stylesheet" id="dynamic-top-css" href="${rc.contextPath}/styles/blog/css/dynamic.css" type="text/css" media="all">
<script type="text/javascript" src="${rc.contextPath}/styles/blog/js/jquery1.7-min.js"></script>

<script type="text/javascript" src="${rc.contextPath}/styles/syntaxhighlighter_2.1.382/scripts/brush.js"></script>
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/styles/syntaxhighlighter_2.1.382/styles/shCore.css"/>
<link type="text/css" rel="stylesheet" href="${rc.contextPath}/styles/syntaxhighlighter_2.1.382/styles/shThemeDefault.css"/>
<script type='text/javascript'><!--
$(document).ready(function(){
	SyntaxHighlighter.config.clipboardSwf = '${rc.contextPath}/styles/syntaxhighlighter_2.1.382/scripts/clipboard.swf';
	SyntaxHighlighter.all();
});
//-->
</script>

<style type="text/css">
.wp-pagenavi {
	font-size: 12px !important;
	float: left !important;
}
/**syntaxhighlighter强行换行**/
.syntaxhighlighter td {
	word-break:break-all !important;
}
div.toolbar {
	height:16px !important;
}
</style>
<style type="text/css">
.recentcomments a {
	display: inline !important;
	padding: 0 !important;
	margin: 0 !important;
}
</style>
<!-- /all in one seo pack -->
<style type="text/css" media="screen">
body {
	position: relative
}
</style>
<style type="text/css" id="twentytwelve-header-css">
.site-header h1 a, .site-header h2 {
	color: #444;
}
</style>
<style type="text/css" id="custom-background-css">
body.custom-background { background-color: #e6e6e6; }
span.highlight {
	color:#dd4b39;
}
</style>
</head>

<body class="home blog custom-background custom-font-enabled single-author">
<div id="page" class="hfeed site">
  <header id="masthead" class="site-header" role="banner">
    <hgroup>
      <h1 class="site-title"><a href="${siteConfig.url}" title="${siteConfig.name}" rel="home">${siteConfig.name}</a></h1>
      <h2 class="site-description">${siteConfig.about}</h2>
    </hgroup>
    <nav id="site-navigation" class="main-navigation" role="navigation">
      <h3 class="menu-toggle">菜单</h3>
      <a class="assistive-text" href="#content" title="跳至内容">跳至内容</a>
      <div class="nav-menu">
        <ul>
          <@lj.menu_nav title=navTitle index=index/>
        </ul>
      </div>
    </nav>
    <!-- #site-navigation --> 
    
  </header>
  <!-- #masthead -->
  
  <div id="main" class="wrapper">
    <div id="primary" class="site-content">
      <div id="content" role="main">
      	<#if who?exists>
		<header class="archive-header">
			<h1 class="archive-title">${who}</h1>
		</header> 
		</#if>     
       	<#nested/>
      </div>
      <!-- #content --> 
    </div>
    <!-- #primary -->
	<script type="text/javascript">
		$().ready(function(){
			$("#searchsubmit").click(function(event){
				event.preventDefault();
			  	var v = $("#s").val().trim();
				if (v == '') {
					return false;
				}
				$("#searchform").submit();
			});
		});	
	</script>     
    <div id="secondary" class="widget-area" role="complementary">
      <aside id="search-2" class="widget widget_search">
        <form role="search" method="get" id="searchform" class="searchform" action="${rc.contextPath}/archive/searchText">
          <div>
            <label class="screen-reader-text" for="s">搜索：</label>
            <input name="s" id="s" type="text">
            <input id="searchsubmit" value="搜索" type="submit">
          </div>
        </form>
      </aside>
      <aside id="views-2" class="widget widget_views">
        <h3 class="widget-title">热门文章</h3>
        <ul>
          <#list hotestArticles as a>
          	<li> <a href="${rc.contextPath}/view/${a.id}" title="${a.title}">${a.title}</a> - ${a.view} views</li>
          </#list>        
        </ul>
      </aside>
      <aside id="recent-posts-2" class="widget widget_recent_entries">
        <h3 class="widget-title">近期文章</h3>
        <ul>
          <#list newestArticles as a>
          	<li> <a href="${rc.contextPath}/view/${a.id}" title="${a.title}">${a.title}</a> </li>
          </#list>
        </ul>
      </aside>
      <aside id="recent-comments-2" class="widget widget_recent_comments">
        <h3 class="widget-title">近期评论</h3>
        <ul id="recentcomments">
          <#list newestComments as c>
          	<li class="recentcomments">
          		<#if c.site?exists && c.site?trim != ''>
          			<a href="${c.site}" rel="external nofollow" class="url">${c.name}</a>
          			<#else>
          			${c.name}
          		</#if>
          		发表在《<a href="${rc.contextPath}/view/${c.article.id}#comments">${c.article.title}</a>》</li>
          </#list>        
        </ul>
      </aside>
      <aside id="archives-2" class="widget widget_archive">
        <h3 class="widget-title">文章归档</h3>
        <ul>
          <#list mothes as m>
          	<li> <a href="${rc.contextPath}/archive/month/${m?string('yyyy-MM')}" title="${m?string('yyyy年MM月')}">${m?string('yyyy年MM月')}</a> </li>
          </#list>        
        </ul>
      </aside>
      <aside id="categories-2" class="widget widget_categories">
        <h3 class="widget-title">分类目录</h3>
        <ul>
          <#list categories as c>
          	<li class="cat-item cat-item-${c.id}"><a href="${rc.contextPath}/archive/category/${c.id}" title="查看${c.name}下的所有文章">${c.name}</a></li>
          </#list>
        </ul>
      </aside>
      <aside id="newestLinks-2" class="widget widget_newestLinks">
        <h3 class="widget-title">友情链接</h3>
        <ul>
          <#list newestLinks as link>
          	<li class="cat-item cat-item-${link.id}"><a href="${link.site}" title="查看${link.name}的网站">${link.name}</a></li>
          </#list>
        </ul>
      </aside> 
      <aside id="newestLinks-2" class="widget widget_newestLinks">
        <h3 class="widget-title">且听风吟</h3>
		<object type="application/x-shockwave-flash" data="${rc.contextPath}/styles/dewplayer/dewplayer-playlist.swf" width="240" height="200" id="dewplayer" name="dewplayer">
			<param name="wmode" value="transparent" />
			<param name="movie" value="dewplayer-playlist.swf" />
			<param name="flashvars" value="showtime=true&autoreplay=true&autostart=false&xml=${rc.contextPath}/styles/dewplayer/playlist.xml" />
		</object>		         
      </aside>      
      <aside id="meta-2" class="widget widget_meta">
        <h3 class="widget-title">功能</h3>
        <ul>
          <li><a href="${rc.contextPath}/admin/login">登录</a></li>
        </ul>
      </aside>
    </div>
    <!-- #secondary --> 
  </div>
  <!-- #main .wrapper -->
  <footer id="colophon" role="contentinfo">
    <div class="site-info"> <a href="${siteConfig.url}" title="${siteConfig.name}">© Copyright 2011-2013 &nbsp;${siteConfig.icp}&nbsp;<a href="${siteConfig.url}">${siteConfig.name}</a></a> </div>
    <!-- .site-info --> 
  </footer>
  <!-- #colophon --> 
</div>
<!-- #page --> 
<#-- 暂时只有一级目录，不适用导航 
<script type="text/javascript" src="${rc.contextPath}/styles/blog/js/navigation.js"></script>
--> 
<script type="text/javascript" src="${rc.contextPath}/styles/blog/js/jquery.easing.1.3.js"></script> 
<script type="text/javascript">
/* <![CDATA[ */
var mv_dynamic_to_top = {"text":"0","version":"0","min":"200","speed":"1000","easing":"easeInOutExpo","margin":"20"};
/* ]]> */
</script> 
<script type="text/javascript" src="${rc.contextPath}/styles/blog/js/dynamic.js"></script><a style="display: none;" href="#" id="dynamic-to-top"><span>&nbsp;</span></a>
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Ff8137cdb1aaa1d376fb2422389fcca49' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>
</#macro>
</#compress>
