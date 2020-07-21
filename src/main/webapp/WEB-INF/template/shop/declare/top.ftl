<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>top</title>
		<link rel="stylesheet" type="text/css" href="/resources/shop/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="/resources/shop/css/top.css"/>
		
	</head>
	<body>
		<div class="header">
			<div class="header-left">
				<div class="logo">
					<a href="http://www.chinafibermarketing.com"><img src="/resources/shop/images/logo.png"></a>
				</div>
				<div class="data">
					<p>
						<span class="hms"></span>
						<span class="week"></span>
					</p>
					<p class="year"></p>
				</div>
			</div>
			<div class="header-right">
				<img src="/resources/shop/images/ban.png" >
				<ul class="nav clearfix">
					<li class="selectedNav"><a href="${base}/article/declarecenter">分析中心</a></li>
					<li><a href="">期货行情</a></li>
					<li><a href="">宏观财经</a></li>
					<li><a href="${base}/article/oil">原油石化</a></li>
					<li><a href="${base}/article/forex">外汇</a></li>
				</ul>
			</div>
		</div>
	</body>
	<script src="js/nav.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var myDate = new Date;
		var year = myDate.getFullYear();
		var mon = myDate.getMonth() + 1; 
		var date = myDate.getDate(); 
		var h = myDate.getHours();
		var m = myDate.getMinutes();
		var s = myDate.getSeconds();
		var week = myDate.getDay();
		var weeks = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
		$(".hms").html(h + "-" + m);
		$(".week").html(weeks[week]);
		$(".year").html(year+'-'+mon+'-'+date);
	</script>
</html>
