<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	[@seo type = "INDEX"]
		[#if seo.resolveKeywords()?has_content]
			<meta name="keywords" content="${seo.resolveKeywords()}">
		[/#if]
		[#if seo.resolveDescription()?has_content]
			<meta name="description" content="${seo.resolveDescription()}">
		[/#if]
		<title>${seo.resolveTitle()}[#if showPowered]  [/#if]</title>
	[/@seo]
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/demo.css" rel="stylesheet">
	<link href="${base}/resources/common/css/jquery.bxslider.css" rel="stylesheet">
	<link href="${base}/resources/common/css/page.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/datacenter.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/base.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/index.css?version=0.3" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/jquery.lazyload.js"></script>
	<script src="${base}/resources/common/js/jquery.bxslider.js"></script>
	<script src="${base}/resources/common/js/jquery.qrcode.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js?version=0.1"></script>
	<script src="${base}/resources/shop/js/base.js"></script>
	<script src="${base}/resources/shop/js/iconfont.js"></script>
</head>
<body>

[#include "/shop/include/main_header.ftl" /]
    <div class="conter">
		<div class="left">
			<ul class="navul">
				[@video_category]
					[#list videoCategories as videoCategories]
						[#if videoCategories.name == '化纤']
							<li class="active">
								<a href="${base}/datacenter/videos/${videoCategories.id}">
									${videoCategories.name}
								</a>
							</li>
						[#else ]
							<li>
								<a href="${base}/datacenter/videos/${videoCategories.id}">
									${videoCategories.name}
								</a>
							</li>
						[/#if]
					[/#list]
				[/@video_category]
			</ul>
		</div>
		<div class="right">
			[#--<div class="videotop">
				<input type="text" class="videoinput"  placeholder="请输入您要查找的工厂"/>
			</div>--]
				<ul class="videotopdet">
					<li class="ttt">${video.title}</li>
					<li class="sss">${video.createdDate}</li>
				</ul>
			<div class="videolostdet">
				<video width="100%" height="530" controls loop>
				  <source src="${video.content}" type="video/mp4">
				  <source src="${video.content}" type="video/ogg">
				  您的浏览器不支持 视频播放
				</video>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(".navul li").click(function () {
			$(this).addClass("active").siblings().removeClass('active');
		})
	</script>
   [#include "/shop/include/main_footer.ftl" /]
</body>
</html>
