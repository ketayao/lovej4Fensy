<#compress>
<#macro page>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemConfig['lovej.site.name']}</title>
<!-- CSS -->
<link href="${rc.contextPath}/styles/admin/css/transdmin.css" rel="stylesheet" type="text/css" media="screen" />
<!--[if IE 6]><link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/styles/admin/css/ie6.css" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/styles/admin/css/ie7.css" /><![endif]-->
</head>

<body>
<div id="wrapper">
	<#nested>
</div>	
</body>
</html>
</#macro>
</#compress>