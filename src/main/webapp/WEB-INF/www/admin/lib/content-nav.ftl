<#--sideNav组合-->
<#assign sideNav0>${bundle("site.articleManage.add")}</#assign>
<#assign sideNav1>${bundle("site.articleManage.manage")}</#assign>
<#assign sideNav2>${bundle("site.categoryManage.add")}</#assign>
<#assign sideNav3>${bundle("site.categoryManage.manage")}</#assign>

<#--sideNavUrl-->
<#assign sideNavUrl0>${rc.contextPath}/admin/article/preCreate</#assign>
<#assign sideNavUrl1>${rc.contextPath}/admin/article/read</#assign>
<#assign sideNavUrl2>${rc.contextPath}/admin/category/preCreate</#assign>
<#assign sideNavUrl3>${rc.contextPath}/admin/category/read</#assign>

<#--title定义-->
<#assign title>${bundle("site.contentManage")}</#assign>

<#--parentNav定义-->
<#assign parentNav>${bundle("site.contentManage")}</#assign>

<#--parentNavUrl定义-->
<#assign parentNavUrl>${rc.contextPath}/admin/article/preCreate</#assign>

<#if user.role == 127>
<#assign sideNav=["${sideNav0}","${sideNav1}","${sideNav2}","${sideNav3}"]> 
<#assign sideNavUrl=["${sideNavUrl0}","${sideNavUrl1}","${sideNavUrl2}","${sideNavUrl3}"]>
<#else>
<#assign sideNav=["${sideNav0}","${sideNav1}"]> 
<#assign sideNavUrl=["${sideNavUrl0}","${sideNavUrl1}"]>
</#if>