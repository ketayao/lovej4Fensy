<#--title定义-->
<#assign title>${bundle("site.userManage")}</#assign>

<#--sideNav组合-->
<#assign sideNav0>${bundle("site.userManage.updateBase")}</#assign>
<#assign sideNav1>${bundle("site.userManage.updatePassword")}</#assign>
<#assign sideNav1>${bundle("site.userManage.list")}</#assign>

<#--sideNavUrl-->
<#assign sideNavUrl0>${rc.contextPath}/admin/user</#assign>
<#assign sideNavUrl1>${rc.contextPath}/admin/user/pwd</#assign>
<#assign sideNavUrl1>${rc.contextPath}/admin/user/list</#assign>

<#--parentNav定义-->
<#assign parentNav>${bundle("site.userManage")}</#assign>

<#--parentNavUrl定义-->
<#assign parentNavUrl>${rc.contextPath}/admin/user/user</#assign>

<#assign sideNav=["${sideNav0}","${sideNav1}"]> 
<#assign sideNavUrl=["${sideNavUrl0}","${sideNavUrl1}"]> 