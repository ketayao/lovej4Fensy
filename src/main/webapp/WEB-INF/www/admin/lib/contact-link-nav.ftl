<#--title定义-->
<#assign title>${bundle("site.contactAndLinkManage")}</#assign>

<#--sideNav组合-->
<#assign sideNav0>${bundle("site.contactManage.manage")}</#assign>
<#assign sideNav1>${bundle("site.linkManage.add")}</#assign>
<#assign sideNav2>${bundle("site.linkManage.manage")}</#assign>

<#--sideNavUrl-->
<#assign sideNavUrl0>${rc.contextPath}/admin/contact/r</#assign>
<#assign sideNavUrl1>${rc.contextPath}/admin/link/c</#assign>
<#assign sideNavUrl2>${rc.contextPath}/admin/link/r</#assign>

<#--parentNav定义-->
<#assign parentNav>${bundle("site.contactAndLinkManage")}</#assign>

<#--parentNavUrl定义-->
<#assign parentNavUrl>${rc.contextPath}/admin/contact/r</#assign>

<#assign sideNav=["${sideNav0}","${sideNav1}","${sideNav2}"]> 
<#assign sideNavUrl=["${sideNavUrl0}","${sideNavUrl1}","${sideNavUrl2}"]> 