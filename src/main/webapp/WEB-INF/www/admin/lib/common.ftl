<#compress>
<#include "WEB-INF/www/admin/lib/param.ftl"/>

<#macro page title sideNav sideNavUrl parentNav parentNavUrl currentNav>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${title} | ${systemConfig['lovej.site.name']}</title>
<#-- CSS -->
<link href="${rc.contextPath}/styles/admin/css/transdmin.css" rel="stylesheet" type="text/css" media="screen" />
<!--[if IE 6]><link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/styles/admin/css/ie6.css" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/styles/admin/css/ie7.css" /><![endif]-->
<link rel="shortcut icon" type="image/x-icon" href="${rc.contextPath}/styles/admin/images/favicon.ico" />
<script src="${rc.contextPath}/styles/admin/js/jquery1.7-min.js" type="text/javascript"></script>

<script>
$(document).ready(function(){
	$("#request_locale").change(function(){
		var url = window.location.href;
		if (url.indexOf('?') > -1) {
			if (url.indexOf('?request_locale') > -1) {
				url = url.substring(0, url.indexOf('?request_locale'));
				url += '?request_locale=' + $(this).val();
			} else if (url.indexOf('&request_locale') > -1) {
				url = url.substring(0, url.indexOf('&request_locale'));
				url += '&request_locale=' + $(this).val();
			} else {
				url += '&request_locale=' + $(this).val();
			}
		} else {
			url += '?request_locale=' + $(this).val()
		}
		window.location.href=url;
	});
});
</script>
</head>

<body>
	<div id="wrapper">
		<div class="logo">
			<a href="${systemConfig['lovej.site.url']}"></a>
			<div class="banner">
				${bundle("login.welcome", user.nickname!'')}
			</div>
			<div id="language">
				${bundle("form.change.language")}
				<select name="request_locale" id="request_locale">
				  <option value ="zh_CN">默认</option>
				  <option value ="zh_CN">简体中文</option>
				  <option value ="en_US">English</option>
				</select>
			</div>
		</div>
		
		<#-- start mainNav -->
		<ul id="mainNav">
			<li><a href="${rc.contextPath}/admin/index"" <#if parentNav==index0 >class="active"</#if> >${index0}</a></li>
			<li><a href="${rc.contextPath}/admin/article/preCreate" <#if parentNav==index1 >class="active"</#if> >${index1}</a></li>
			<li><a href="${rc.contextPath}/admin/user" <#if parentNav==index2 >class="active"</#if> >${index2}</a></li>
			<#if user.role == 127>
			<li><a href="${rc.contextPath}/admin/contact/r" <#if parentNav==index4 >class="active"</#if> >${index4}</a></li>
			<li><a href="${rc.contextPath}/admin/track/r" <#if parentNav==index3 >class="active"</#if> >${index3}</a></li>
			</#if>
			<li class="logout"><a href='${rc.contextPath}/admin/login/logout'>${bundle("site.logout")}</a></li>
		</ul>
		<#-- // #end mainNav -->

		<div id="containerHolder">
			<div id="container">
				<div id="sidebar">
					<ul class="sideNav">
						<#list sideNav as s>
							<#if s==currentNav>
								<li><a href="${sideNavUrl[s_index]}" class="active">${s}</a></li>
							<#else>
								<li><a href="${sideNavUrl[s_index]}">${s}</a></li>
							</#if>
						</#list>
					</ul>
					<#-- // .sideNav -->
				</div>
				<#-- // #sidebar -->

				<#-- h2 stays for breadcrumbs -->
				<h2>
					<#if currentNav!=''>
						<a href="${parentNavUrl}">${parentNav}</a> &raquo; <a href="#" class="active" style="TEXT-DECORATION:none;">${currentNav}</a>
					<#else>
						<a href="${parentNavUrl}"  style="TEXT-DECORATION:none;">${parentNav}</a> 
					</#if>
				</h2>

				<#nested/>

				<div class="clear"></div>
			</div>
			<#-- // #container -->
		</div>
		<#-- // #containerHolder -->

		<p id="footer">
			${systemConfig['lovej.site.version']}. Copyright&copy; 2011 &nbsp;${systemConfig['lovej.site.icp']}&nbsp;<a
				href="${systemConfig['lovej.site.url']}">${systemConfig['lovej.site.name']}</a>
		</p>
	</div>
	<#-- // #wrapper -->
</body>
</html>
</#macro>
</#compress>