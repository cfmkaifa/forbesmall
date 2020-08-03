<!DOCTYPE html>
<html>
	<head>
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
		<link href="${base}/resources/shop/css/base.css" rel="stylesheet">
		<link href="${base}/resources/shop/css/iconfont.css" rel="stylesheet">
		<link href="${base}/resources/shop/css/index.css?version=0.3" rel="stylesheet">
		<link href="${base}/resources/shop/css/articleindex.css"rel="stylesheet"/>

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
		<div class="arti-main">
			<div class="arti-top">
					<div>
						[@ad_position id = 10101]
							[#if adPosition??]
								[#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
							[/#if]
						[/@ad_position]
					</div>
				<div>
					[@ad_position id = 10102]
						[#if adPosition??]
							[#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
						[/#if]
					[/@ad_position]
				</div>
				<div>
					[@ad_position id = 10103]
						[#if adPosition??]
							[#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
						[/#if]
					[/@ad_position]
				</div>
			</div>
			<div class="advertisement">
				
			</div>
			<div class="nav">
					<ul class="navul">
						[#list articleCategories as articleCategories]
							<li [#if articleCategoryId == articleCategories.id] class="active"[/#if]>
									<a href="${base}/article/articlelist/${articleCategories.id}">${articleCategories.name}</a>
							</li>
						[/#list]
					</ul>
				<p class="navp">
					<span>最新播报</span>
				</p>
			</div>
			<div class="conter">
				<div class="conter-left">
					[#list  page.content as article]

							<div class="newslist">
								<div class="img">
									[#if article.thumbnail?? && article.thumbnail != ""]
										<img src="${article.thumbnail}">
									[#else]
										<img src="/resources/shop/images/arti1.png">
									[/#if]
								</div>
								<a href="${base}${article.path}">
									<ul>
										<li class="lefttop">${article.title}</li>
										<li class="data">
											<span>${article.createdDate}</span>
											<span>${article.author}</span>
										</li>
										<li class="newsconter">
												${article.seoDescription}
										</li>
										<li class="see">
											查看全文
										</li>
									</ul>
								</a>
							</div>
					[/#list]
				</div>
				<div class="conter-right">
					<div class="imgimg">
						[@ad_position id = 10104]
							[#if adPosition??]
								[#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
							[/#if]
						[/@ad_position]
					</div>
					<div class="newsrilist">
						[#list articles.content as articles]
							<div class="newss">
								<div class="newss-top">
									<span></span>
									<p>
										<a href="${base}${articles.path}">
											${articles.title}
										</a>
									</p>
								</div>
								<div class="newssdata">
									${articles.createdDate}
								</div>
							</div>
						[/#list]
					</div>
				</div>
			</div>
			[@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "${base}${articleCategory.path}[#if {pageNumber} > 1]?pageNumber={pageNumber}[/#if]"]
				[#if totalPages > 1]
					<div class="panel-footer text-right">
						[#include "/shop/include/pagination.ftl" /]
					</div>
				[/#if]
			[/@pagination]
		</div>
	[#include "/shop/include/main_footer.ftl" /]
	</body>
	<script type="text/javascript">
		$(".navul li").click(function () {
			$(this).addClass("active").siblings().removeClass('active');
})
</script>
</html>
