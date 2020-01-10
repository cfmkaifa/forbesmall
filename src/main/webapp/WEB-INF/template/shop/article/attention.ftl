<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" href="/resources/shop/css/attention.css">
        <link href="${base}/favicon.ico" rel="icon">
        <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
        <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
        <link href="${base}/resources/common/css/demo.css" rel="stylesheet">
        <link href="${base}/resources/common/css/jquery.bxslider.css" rel="stylesheet">
        <link href="${base}/resources/common/css/base.css" rel="stylesheet">
        <link href="${base}/resources/shop/css/base.css" rel="stylesheet">
        <link href="${base}/resources/shop/css/charge.css"  rel="stylesheet">
        <link href="${base}/resources/shop/css/iconfont.css" rel="stylesheet">
        <link href="${base}/resources/shop/css/index.css?version=0.1" rel="stylesheet">
        <!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
        <![endif]-->

        <script src="${base}/resources/common/js/jquery.js"></script>
        <script src="${base}/resources/common/js/bootstrap.js?version=0.1"></script>
        <script src="${base}/resources/common/js/jquery.lazyload.js"></script>
        <script src="${base}/resources/common/js/jquery.bxslider.js"></script>
        <script src="${base}/resources/common/js/jquery.qrcode.js"></script>
        <script src="${base}/resources/common/js/underscore.js"></script>
        <script src="${base}/resources/common/js/url.js"></script>
        <script src="${base}/resources/common/js/velocity.js"></script>
        <script src="${base}/resources/common/js/velocity.ui.js"></script>
        <script src="${base}/resources/common/js/base.js?version=0.1"></script>
        <script src="${base}/resources/shop/js/base.js"></script>
        <script src="${base}/resources/shop/js/iconfont.js"></script>
		<title>福布云商</title>
	</head>
	<body>
	  [#-- [#include "/shop/include/main_newheader.ftl" /]--]
	   <main>
		<div>
			<div class="newsNav">
				<ul class="navigation">
                   [#list articleCategories as articleCategories]
						<li class="item">
							<a href="${base}/article/list/${articleCategories.id}">${articleCategories.name}</a>
						</li>
				   [/#list]
				</ul>
			</div>
			<div style="width:1200px;padding:40px;background: #ffffff;margin: 0 auto;">
			[#if isPerm == false]
				[#include "/shop/include/noperm.ftl" /]
			[#else]
				[#if page.content?has_content]
					[#list  page.content as article]
						<div class="insights_content">
							<div class="insights_title">
								<span class="time">
									<p style="font-size:24px;">${article.createdDate}</p>
								</span>
								<span class="news_border"></span>

							</div>
							<div class="newsTitle">
								<span class="newsTitle_text">
									<h3>${article.title}</h3>
								</span>
								[#--<a href="${base}${article.path}">--]
								<a href="${base}${article.path}">
									<img src="/resources/shop/images/khcfdc.png" alt="" style="width:18px;">
								</a>
							</div>
						</div>
					[/#list]
			[#else]
                <div class="no-result">
					[#noautoesc]
					   ${message("shop.article.noResult")}
					[/#noautoesc]
                </div>
			[/#if]
				[@pagination pageNumber = page.pageNumber totalPages = page.totalPages pattern = "${base}${articleCategory.path}[#if {pageNumber} > 1]?pageNumber={pageNumber}[/#if]"]
					[#if totalPages > 1]
                        <div class="panel-footer text-right">
							[#include "/shop/include/pagination.ftl" /]
                        </div>
					[/#if]
				[/@pagination]
			[/#if]
			</div>
		</div>
       </main>
	     [#include "/shop/include/main_footer.ftl" /]
	</body>
	<script type="text/javascript">
		/*$(function(){
			let li = $(".item");
			li.click(function() {
			    $(this).siblings().removeClass('active');
			    $(this).addClass('active');
			})
			
		})*/
	</script>
</html>
