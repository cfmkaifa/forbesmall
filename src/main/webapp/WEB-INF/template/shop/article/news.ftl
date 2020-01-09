<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
        <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="${base}/resources/common/css/base.css" rel="stylesheet">
        <link href="${base}/resources/shop/css/base.css" rel="stylesheet">
		<link rel="stylesheet" href="${base}/resources/shop/css/news.css">
        <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
        <link href="${base}/resources/common/css/demo.css" rel="stylesheet">
        <!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
        <![endif]-->
        <script src="${base}/resources/common/js/jquery.js"></script>
        <script src="${base}/resources/common/js/bootstrap.js?version=0.1"></script>
		<title>新闻首页</title>
		<style>
			
		</style>
	</head>
	<body>
	      [#include "/shop/include/main_newheader.ftl" /]
	<main>
		<div>
			<div class="newsNav">
				<ul class="navigation">
				[#list articleCategories as articleCategories]
                         <li [#if articleCategories_index ==0 ] class="item active" [/#if] class="item">
							 <a href="${base}/article/articlelist/${articleCategories.id}">${articleCategories.name}</a>
						 </li>
				[/#list]
				</ul>
			</div>
			<div class="information_content">
				<div class="hotAttention content">
				    <div class="hotAttention_img">
				        <img src="/resources/shop/images/bannerx.jpg" alt="">
				    </div>
				    <div>
				        <div class="hotAttention_news">
				            <span class="hotAttention_title">
				                <div></div>
				                <h3>${articleCategories[1].name}</h3>
				                <p>${message("shop.article.instantnews")}</p>
				            </span>
				            <span class="more"><a href="http://localhost:8080/article/articlelist/${articleCategories[1].id}">更多>></a></span>
				        </div>
				         <div class="hotAttention_content1 hotAttention_content" >
				            [#list instantnews.content as instantnews]
                                <span><p><a href="${base}${instantnews.path}">${instantnews.title}</a></p></span>
							[/#list]
				        </div>
				    </div>
				</div>
				<div class="content view" >
				    <div class="information_title">
				            <span class="hotAttention_title">
				                <div></div>
				                  <h3>${articleCategories[2].name}</h3>
				                  <p>${message("shop.article.fubuinsights")}</p>
				            </span>
                        <span class="more"><a href="${base}/article/articlelist/${articleCategories[2].id}">更多>></a></span>
				    </div>
				    <div class="hotAttention">
				         <div class="hotAttention_content hotAttention_content2">
						 [#list fubuinsights.content as fubuinsights]
                             <span><p><a href="${base}${fubuinsights.path}">${fubuinsights.title}</a></p></span>
						 [/#list]
				        </div>
				        <div class="information_img">
				            <img src="/resources/shop/images/11.png" alt="">
				        </div>
				    </div>
				</div>
				<div class="content view" >
				    <div class="information_title">
				            <span class="hotAttention_title">
				                <div></div>
				                <h3>${articleCategories[3].name}</h3>
				                  <p>${message("shop.article.factorystatus")}</p>
				            </span>
                        <span class="more"><a href="${base}/article/articlelist/${articleCategories[3].id}">更多>></a></span>
				    </div>
				    <div class="hotAttention">
				         <div class="hotAttention_content hotAttention_content2">
						 [#list factorystatus.content as factorystatus]
                             <span><p><a href="${base}${factorystatus.path}">${factorystatus.title}</a></p></span>
						 [/#list]
				        </div>
				        <div class="information_img">
				            <img src="/resources/shop/images/11.png" alt="">
				        </div>
				    </div>
				</div>
				<div class="content view" >
				    <div class="information_title">
				            <span class="hotAttention_title">
				                <div></div>
				                <h3>${articleCategories[5].name}</h3>
								 <p>${message("shop.article.commonality")}</p>
				            </span>
                        <span class="more"><a href="${base}/article/articlelist/${articleCategories[5].id}">更多>></a></span>
				    </div>
				    <div class="hotAttention">
				         <div class="hotAttention_content hotAttention_content2">
						 [#list commonality.content as commonality]
                             <span><p><a href="${base}${commonality.path}">${commonality.title}</a></p></span>
						 [/#list]
				        </div>
				        <div class="information_img">
				            <img src="/resources/shop/images/11.png" alt="">
				        </div>
				    </div>
				</div>
			</div>
		</div>
    </main>
	[#include "/shop/include/main_footer.ftl" /]
	</body>
	<script>
		$(function(){
			let li = $(".item");
			li.click(function() {
			    $(this).siblings().removeClass('active');
			    $(this).addClass('active');
			})
			
		})
	</script>
</html>
