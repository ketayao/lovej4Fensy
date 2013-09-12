<!DOCTYPE html>
<!-- Ticket #11289, IE bug fix: always pad the error page with enough characters such that it is greater than 512 bytes, even after gzip compression abcdefghijklmnopqrstuvwxyz1234567890aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz11223344556677889900abacbcbdcdcededfefegfgfhghgihihjijikjkjlklkmlmlnmnmononpopoqpqprqrqsrsrtstsubcbcdcdedefefgfabcadefbghicjkldmnoepqrfstugvwxhyz1i234j567k890laabmbccnddeoeffpgghqhiirjjksklltmmnunoovppqwqrrxsstytuuzvvw0wxx1yyz2z113223434455666777889890091abc2def3ghi4jkl5mno6pqr7stu8vwx9yz11aab2bcc3dd4ee5ff6gg7hh8ii9j0jk1kl2lmm3nnoo4p5pq6qrr7ss8tt9uuvv0wwx1x2yyzz13aba4cbcb5dcdc6dedfef8egf9gfh0ghg1ihi2hji3jik4jkj5lkl6kml7mln8mnm9ono
-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="refresh" content="20; url=${siteConfig.url}/">
	<title>NOT FOUND | ${siteConfig.name}</title>
	<style type="text/css">
		html {
			background: #f9f9f9;
		}
		body {
			background: #fff;
			color: #333;
			font-family: sans-serif;
			margin: 2em auto;
			padding: 1em 2em;
			-webkit-border-radius: 3px;
			border-radius: 3px;
			border: 1px solid #dfdfdf;
			max-width: 700px;
		}
		h1 {
			border-bottom: 1px solid #dadada;
			clear: both;
			color: #666;
			font: 24px Georgia, "Times New Roman", Times, serif;
			margin: 30px 0 0 0;
			padding: 0;
			padding-bottom: 7px;
		}
		#error-page {
			margin-top: 50px;
		}
		#error-page p {
			font-size: 14px;
			line-height: 1.5;
			margin: 25px 0 20px;
		}
		#error-page code {
			font-family: Consolas, Monaco, monospace;
		}
		ul li {
			margin-bottom: 10px;
			font-size: 14px ;
		}
		a {
			color: #21759B;
			text-decoration: none;
		}
		a:hover {
			color: #D54E21;
		}
		.button {
			display: inline-block;
			text-decoration: none;
			font-size: 14px;
			line-height: 23px;
			height: 24px;
			margin: 0;
			padding: 0 10px 1px;
			cursor: pointer;
			border-width: 1px;
			border-style: solid;
			-webkit-border-radius: 3px;
			border-radius: 3px;
			white-space: nowrap;
			-webkit-box-sizing: border-box;
			-moz-box-sizing:    border-box;
			box-sizing:         border-box;
			background: #f3f3f3;
			background-image: -webkit-gradient(linear, left top, left bottom, from(#fefefe), to(#f4f4f4));
			background-image: -webkit-linear-gradient(top, #fefefe, #f4f4f4);
			background-image:    -moz-linear-gradient(top, #fefefe, #f4f4f4);
			background-image:      -o-linear-gradient(top, #fefefe, #f4f4f4);
			background-image:   linear-gradient(to bottom, #fefefe, #f4f4f4);
			border-color: #bbb;
		 	color: #333;
			text-shadow: 0 1px 0 #fff;
		}

		.button.button-large {
			height: 29px;
			line-height: 28px;
			padding: 0 12px;
		}

		.button:hover,
		.button:focus {
			background: #f3f3f3;
			background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#f3f3f3));
			background-image: -webkit-linear-gradient(top, #fff, #f3f3f3);
			background-image:    -moz-linear-gradient(top, #fff, #f3f3f3);
			background-image:     -ms-linear-gradient(top, #fff, #f3f3f3);
			background-image:      -o-linear-gradient(top, #fff, #f3f3f3);
			background-image:   linear-gradient(to bottom, #fff, #f3f3f3);
			border-color: #999;
			color: #222;
		}

		.button:focus  {
			-webkit-box-shadow: 1px 1px 1px rgba(0,0,0,.2);
			box-shadow: 1px 1px 1px rgba(0,0,0,.2);
		}

		.button:active {
			outline: none;
			background: #eee;
			background-image: -webkit-gradient(linear, left top, left bottom, from(#f4f4f4), to(#fefefe));
			background-image: -webkit-linear-gradient(top, #f4f4f4, #fefefe);
			background-image:    -moz-linear-gradient(top, #f4f4f4, #fefefe);
			background-image:     -ms-linear-gradient(top, #f4f4f4, #fefefe);
			background-image:      -o-linear-gradient(top, #f4f4f4, #fefefe);
			background-image:   linear-gradient(to bottom, #f4f4f4, #fefefe);
			border-color: #999;
			color: #333;
			text-shadow: 0 -1px 0 #fff;
			-webkit-box-shadow: inset 0 2px 5px -3px rgba( 0, 0, 0, 0.5 );
		 	box-shadow: inset 0 2px 5px -3px rgba( 0, 0, 0, 0.5 );
		}

</style>
</head>
<body id="error-page">
	<header class="entry-header">
		<h1 class="entry-title">抱歉，您访问的页面没找到。</h1>
		<br/>
		<a class="button" href="javascript:history.go(-1)">返回上页</a> <a class="button" href="${systemConfig['lovej.site.url']}/">返回首页</a>
	</header>
</body>
</html>