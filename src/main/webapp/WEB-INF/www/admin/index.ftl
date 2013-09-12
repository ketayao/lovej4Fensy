<#import "WEB-INF/www/admin/lib/common.ftl" as com>

<#--title定义-->
<#assign title>${bundle("site.home")}</#assign>

<#--sideNav组合-->
<#assign sideNav0>${bundle("site.home")}</#assign>

<#--url-->

<#--parentNav,currentNav定义-->
<#assign parentNav>${bundle("site.home")}</#assign>

<#escape x as x?html>
<@com.page title=title sideNav=sideNav0 sideNavUrl=["#"] parentNav=parentNav parentNavUrl="#" currentNav=''>

<div id="main">
	<div  class="jNice">
	<h3>简介</h3>
    <table cellpadding="0" cellspacing="0">
			<tr>
                <td colspan="2">                    	
	<div class="love-article-content">
		<p>
			&nbsp; &nbsp; <span style="font-size: 12px;">&nbsp;
				&nbsp;&nbsp;</span><span style="font-size: 12px;">loveJ是一个完全开源的、简单的个人博客，可以用来发布内容，图片，链接，附件，留言等功能。</span>
		</p>
		<p>
			<span style="font-size: 12px;">fensy 版本0.1。</span>
		</p>
		<p>
			<span style="white-space: nowrap; font-size: 12px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果你有好的建议和意见请给我留言，或者</span><a
				href="http://ketayao.com/blog/contact" style="white-space: nowrap;"><span
				style="font-size: 12px;">联系我</span></a><span
				style="white-space: nowrap; font-size: 12px;">。</span>
		</p>
		<p></p>
	</div>
    		    </td>
            </tr>                                               
        </table>

		<br/>
        <br/>
        <br/>
    </div>
</div>
<#-- // #main -->

</@com.page>

</#escape>