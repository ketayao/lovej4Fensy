<#--title定义-->
<#assign title>${bundle("site.siteConfig")}</#assign>

<#--sideNav组合-->
<#assign sideNav0>${bundle("site.trackManage.manage")}</#assign>
<#assign sideNav1>${bundle("site.siteConfigManage.manage")}</#assign>
<#assign sideNav2>${bundle("site.siteConfigManage.index.create")}</#assign>

<#--sideNavUrl-->
<#assign sideNavUrl0>${rc.contextPath}/admin/track/r</#assign>
<#assign sideNavUrl1>${rc.contextPath}/admin/siteConfig/pu"</#assign>
<#assign sideNavUrl2>${rc.contextPath}/admin/lucene/index</#assign>

<#--parentNav定义-->
<#assign parentNav>${bundle("site.siteConfig")}</#assign>

<#--parentNavUrl定义-->
<#assign parentNavUrl>${rc.contextPath}/admin/track/r</#assign>

<#assign sideNav=["${sideNav0}","${sideNav1}","${sideNav2}"]> 
<#assign sideNavUrl=["${sideNavUrl0}","${sideNavUrl1}","${sideNavUrl2}"]> 
