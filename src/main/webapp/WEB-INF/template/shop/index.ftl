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
    <link href="${base}/resources/common/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/index.css?version=0.3" rel="stylesheet">
    <!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
    <script src="${base}/resources/common/js/jquery.js"></script>
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.js"></script>
    <script src="${base}/resources/common/js/bootstrap.js"></script>
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
    [#noautoesc]
        [#escape x as x?js_string]
            <script>
                $().ready(function () {

                    var $window = $(window);
                    var $topbar = $("#topbar");
                    var $topbarProductSearchForm = $("#topbar form");
                    var $topbarProductSearchKeyword = $("#topbar input[name='keyword']");
                    var $mainSlider = $("#mainSlider");
                    var $sideSlider = $("#sideSlider");
                    var $featuredProductSlider = $(".featured-product .slider");
                    var topbarHidden = true;

                    // 顶部栏
                    $window.scroll(_.throttle(function () {
                        if ($window.scrollTop() > 500) {
                            if (topbarHidden) {
                                topbarHidden = false;
                                $topbar.velocity("transition.slideDownIn", {
                                    duration: 500
                                });
                            }
                        } else {
                            if (!topbarHidden) {
                                topbarHidden = true;
                                $topbar.velocity("transition.slideUpOut", {
                                    duration: 500
                                });
                            }
                        }
                    }, 500));

                    // 商品搜索
                    $topbarProductSearchForm.submit(function () {
                        if ($.trim($topbarProductSearchKeyword.val()) == "") {
                            return false;
                        }
                    });

                    // 主轮播广告
                    $mainSlider.bxSlider({
                        mode: "fade",
                        auto: true,
                        controls: false
                    });

                    // 侧边轮播广告
                    $sideSlider.bxSlider({
                        pager: false,
                        auto: true,
                        nextText: "&#xe6a3;",
                        prevText: "&#xe679;"
                    });

                    // 推荐商品轮播广告
                    $featuredProductSlider.bxSlider({
                        pager: false,
                        auto: true,
                        nextText: "&#xe6a3;",
                        prevText: "&#xe679;"
                    });

                });

            </script>
        [/#escape]
    [/#noautoesc]
</head>
<body class="shop index">
<<<<<<< HEAD
[#include "/shop/include/main_header.ftl" /]
[#include "/shop/include/main_sidebar.ftl" /]
<main style="background:#f4f4f4;">
    <div id="topbar" class="topbar">
        <div class="container">
            <div class="row">
                <div class="col-xs-4">
                    <a href="${base}/">
                      [#--  <img class="logo" src="${setting.logo}" alt="${setting.siteName}">--]
                        <img class="logo" src="${base}/resources/shop/images/logo.png" >
                    </a>
                </div>
                <div class="col-xs-5">
                    <div class="product-search">
                        <form action="${base}/product/search" method="get">
                            <input name="keyword" type="text"
                                   placeholder="${message("shop.index.productSearchKeywordPlaceholder")}"
                                   autocomplete="off" x-webkit-speech="x-webkit-speech"
                                   x-webkit-grammar="builtin:search">
                            <button type="submit">
                                <i class="iconfont icon-search"></i>
                                ${message("shop.index.productSearchSubmit")}
                            </button>
                        </form>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="cart">
                        <i class="iconfont icon-cart"></i>
                        <a href="${base}/cart/list">${message("shop.index.cart")}</a>
                        <em>0</em>
                    </div>
                </div>
            </div>
        </div>
    </div>
    [@ad_position id = 2]
        [#if adPosition??]
            [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
        [/#if]
    [/@ad_position]
    <div class="container">
        <div class="row">
            <div class="col-xs-2">
                [@product_category_root_list count = 6]
                    <div class="product-category-menu">
                        <ul>
                            [#list productCategories as productCategory]
                                <li>
                                    [#switch productCategory_index]
                                        [#case 0]
                                            <span class="iconfont">&#xe651;</span>
                                            [#break /]
                                        [#case 1]
=======
	[#include "/shop/include/main_header.ftl" /]
	[#include "/shop/include/main_sidebar.ftl" /]
	<main style="background:#f4f4f4;">
		<div id="topbar" class="topbar">
			<div class="container">
				<div class="row">
					<div class="col-xs-4">
						<a href="${base}/">
							[#--<img class="logo" src="${setting.logo}" alt="${setting.siteName}">--]
							<img class="logo" src="${base}/resources/shop/images/logo.png">
						</a>
					</div>
					<div class="col-xs-5">
						<div class="product-search">
							<form action="${base}/product/search" method="get">
								<input name="keyword" type="text" placeholder="${message("shop.index.productSearchKeywordPlaceholder")}" autocomplete="off" x-webkit-speech="x-webkit-speech" x-webkit-grammar="builtin:search">
								<button type="submit">
									<i class="iconfont icon-search"></i>
									${message("shop.index.productSearchSubmit")}
								</button>
							</form>
						</div>
					</div>
					<div class="col-xs-3">
						<div class="cart">
							<i class="iconfont icon-cart"></i>
							<a href="${base}/cart/list">${message("shop.index.cart")}</a>
							<em>0</em>
						</div>
					</div>
				</div>
			</div>
		</div>
		[@ad_position id = 2]
			[#if adPosition??]
				[#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
			[/#if]
		[/@ad_position]
		<div class="container">
			<div class="row">
				<div class="col-xs-2">
					[@product_category_root_list count = 6]
						<div class="product-category-menu">
							<ul>
								[#list productCategories as productCategory]
									<li>
										[#switch productCategory_index]
											[#case 0]
                                                <span class="iconfont">&#xe651;</span>
												[#break /]
											[#case 1]
>>>>>>> c99afd385bf9f2d0f0046944b4bfdf77dbd698e0
                                            <span class="iconfont">&#xe642;</span>
                                            [#break /]
                                        [#case 2]
                                            <span class="iconfont">&#xe681;</span>
                                            [#break /]
                                        [#case 3]
                                            <span class="iconfont">&#xe650;</span>
                                            [#break /]
                                        [#case 4]
                                            <span class="iconfont">&#xe697;</span>
                                            [#break /]
                                        [#case 5]
                                            <span class="iconfont">&#xe61a;</span>
                                            [#break /]
                                        [#case 6]
                                            <i class="iconfont icon-favor"></i>
                                            [#break /]
                                        [#case 7]
                                            <i class="iconfont icon-like"></i>
                                            [#break /]
                                        [#default]
                                            <i class="iconfont icon-goodsfavor"></i>
                                    [/#switch]
                                    <a href="${base}${productCategory.path}">${productCategory.name}</a>
                                    [@product_category_children_list productCategoryId = productCategory.id recursive = false count = 4]
                                        <p>
                                            [#list productCategories as productCategory]
                                                <a href="${base}${productCategory.path}">${productCategory.name}</a>
                                            [/#list]
                                        </p>
                                    [/@product_category_children_list]
                                    [@product_category_children_list productCategoryId = productCategory.id recursive = false count = 8]
                                        <div class="product-category-menu-content">
                                            <div class="row">
                                                <div class="col-xs-9" style=" display: flex;">
                                                    [@promotion_list productCategoryId = productCategory.id hasEnded = false count = 6]
                                                        [#if promotions?has_content]
                                                            <ul class="promotion clearfix">
                                                                [#list promotions as promotion]
                                                                    <li>
                                                                        <a href="${base}${promotion.path}"
                                                                           title="${promotion.title}">${promotion.name}</a>
                                                                        <i class="iconfont icon-right"></i>
                                                                    </li>
                                                                [/#list]
                                                            </ul>
                                                        [/#if]
                                                    [/@promotion_list]
                                                    [#list productCategories as productCategory]
                                                        <dl class="product-category clearfix">
                                                            <dt class="text-overflow">
                                                                <a href="${base}${productCategory.path}"
                                                                   title="${productCategory.name}">${productCategory.name}</a>
                                                            </dt>
                                                            [@product_category_children_list productCategoryId = productCategory.id recursive = false]
                                                                [#list productCategories as productCategory]
                                                                    <dd>
                                                                        <a href="${base}${productCategory.path}">${productCategory.name}</a>
                                                                    </dd>
                                                                [/#list]
                                                            [/@product_category_children_list]
                                                        </dl>
                                                    [/#list]
                                                </div>
                                                <div class="col-xs-3">
                                                    [@brand_list productCategoryId = productCategory.id type = "IMAGE" count = 10]
                                                        [#if brands?has_content]
                                                            <ul class="brand clearfix">
                                                                [#list brands as brand]
                                                                    <li>
                                                                        <a href="${base}${brand.path}"
                                                                           title="${brand.name}">
                                                                            <img class="img-responsive center-block"
                                                                                 src="${brand.logo}"
                                                                                 alt="${brand.name}">
                                                                        </a>
                                                                    </li>
                                                                [/#list]
                                                            </ul>
                                                        [/#if]
                                                    [/@brand_list]
                                                    [@promotion_list productCategoryId = productCategory.id hasEnded = false count = 2]
                                                        [#if promotions?has_content]
                                                            <ul class="promotion-image">
                                                                [#list promotions as promotion]
                                                                    <li>
                                                                        [#if promotion.image?has_content]
                                                                            <a href="${base}${promotion.path}"
                                                                               title="${promotion.title}">
                                                                                <img class="img-responsive center-block"
                                                                                     src="${promotion.image}"
                                                                                     alt="${promotion.title}">
                                                                            </a>
                                                                        [/#if]
                                                                    </li>
                                                                [/#list]
                                                            </ul>
                                                        [/#if]
                                                    [/@promotion_list]
                                                </div>
                                            </div>
                                        </div>
                                    [/@product_category_children_list]
                                </li>
                            [/#list]
                        </ul>
                    </div>
                [/@product_category_root_list]
            </div>
            <div class="col-xs-2 col-xs-offset-8">
                [@article_category_root_list]
                    [#if articleCategories?has_content]
                        <div class="article">
                            <ul class="nav nav-pills nav-justified">
                                [#list articleCategories as articleCategory]
                                    <li[#if articleCategory_index == 0] class="active"[/#if]>
                                        <a href="#articleCategory_${articleCategory.id}"
                                           data-toggle="tab">${articleCategory.name}</a>
                                    </li>
                                [/#list]
                            </ul>
                            <div class="tab-content">
                                [#list articleCategories as articleCategory]
                                    <div id="articleCategory_${articleCategory.id}"
                                         class="tab-pane fade[#if articleCategory_index == 0] active in[/#if]">
                                        [@article_list articleCategoryId = articleCategory.id count = 5]
                                            <ul>
                                                [#list articles as article]
                                                    [#if articleCategory.name =="行业报告"]
                                                        <li class="text-overflow">
                                                            <a href="${base}/article/articlelist/10051"
                                                               title="${article.title}"
                                                               target="_blank">${abbreviate(article.title, 40)}</a>
                                                        </li>
                                                    [#else]
                                                        <li class="text-overflow">
                                                            <a href="${base}${article.path}" title="${article.title}"
                                                               target="_blank">${abbreviate(article.title, 40)}</a>
                                                        </li>
                                                    [/#if]
                                                [/#list]
                                            </ul>
                                        [/@article_list]
                                    </div>
                                [/#list]
                            </div>
                        </div>
                    [/#if]
                [/@article_category_root_list]
                [@ad_position id = 3]
                    [#if adPosition??]
                        [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
                    [/#if]
                [/@ad_position]
            </div>
        </div>
        [@ad_position id = 4]
            [#if adPosition??]
                [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
            [/#if]
        [/@ad_position]
        [@ad_position id = 5]
            [#if adPosition??]
                [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
            [/#if]
        [/@ad_position]
        [@product_category_root_list count = 5]

<<<<<<< HEAD
            <div class="featured-product">
                <div class="featured-product-heading">
                    <img src="${base}/resources/shop/images/gangchangzhixiao.png">
                </div>
                <div class="featured-product-body">
                    <div class="row">
                        <div class="box-2">
                            <div id="myCarousel" class="carousel slide">
                                <!-- 轮播（Carousel）项目 -->
                                <div class="carousel-inner">
                                    <div class="item active">
                                        <div class="factory-2">
                                            [@ad_factory]
                                                [#list adFactory.content as stores]
                                                    <div class="swiper-2">
                                                        <a href="${base}/store/${stores.id}">
=======
						<div class="featured-product">
							<div class="featured-product-heading">
								<img src="${base}/resources/shop/images/gangchangzhixiao.png">
							</div>
							<div class="featured-product-body">
								<div class="row">
                                    <div class="box-2">
                                        <div id="myCarousel" class="carousel slide">
                                            <!-- 轮播（Carousel）项目 -->
                                            <div class="carousel-inner">
                                                <div class="item active">
                                                    <div class="factory-2">
														[@ad_factory]
															[#list adFactory.content as stores]
                                                                <div class="swiper-2">
                                                                 <a href="${base}/store/${stores.id}">
>>>>>>> c99afd385bf9f2d0f0046944b4bfdf77dbd698e0
																<span class="swiper_title-2">

																	  <img src="${stores.logo}" class="logo-2">
																	<p>${stores.name}</p>
																</span>
<<<<<<< HEAD
                                                                    <p class="tonsof-2">${stores.capacity}${message("shop.index.mass")}</p>
                                                                    <p class="themain-2">${message("shop.index.varieties")}</p>
                                                                    <p class="varieties-2">${stores.keyword}</p>
                                                                 </a>
                                                                </div>
															[/#list]
														[/@ad_factory]
=======
                                                            <p class="tonsof-2">${stores.capacity}吨/年</p>
                                                            <p class="themain-2">主营品种</p>
                                                            <p class="varieties-2">${stores.keyword}</p>
                                                        </a>
>>>>>>> fabric_mall
                                                    </div>
                                                [/#list]
                                            [/@ad_factory]
                                        </div>
                                    </div>
                                </div>
                                <!-- 轮播（Carousel）导航 -->
                                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        [/@product_category_root_list]
        <div class="row">
            [@product_category_root_list count = 3]
                [#list productCategories as productCategory]
                    [@product_list productCategoryId = productCategory.id productTagId = 1 count = 5]
                        [#if products?has_content]
                            <div class="col-xs-4">
                                <div class="hot-product ${productCategory?item_parity}">
                                    <div class="hot-product-heading">${productCategory.name}</div>
                                    <div class="hot-product-body">
                                        <ul class="clearfix">
                                            [#list products as product]
                                                <li>
                                                    <a href="${base}${product.path}" target="_blank">
                                                        <h5 title="${product.name}">${abbreviate(product.name, 24)}</h5>
                                                        [#if product.caption?has_content]
                                                            <span title="${product.caption}">${abbreviate(product.caption, 24)}</span>
                                                        [/#if]
                                                        <img class="lazy-load img-responsive center-block"
                                                             src="${base}/resources/common/images/transparent.png"
                                                             alt="${product.name}"
                                                             data-original="${product.thumbnail!setting.defaultThumbnailProductImage}">
                                                    </a>
                                                </li>
                                            [/#list]
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        [/#if]
                    [/@product_list]
                [/#list]
            [/@product_category_root_list]
        </div>
        [@ad_position id = 16]
            [#if adPosition??]
                [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
            [/#if]
        [/@ad_position]
        [@friend_link_list type = "IMAGE" count = 10]
            [#if friendLinks?has_content]
                <div class="row">
                    <div class="col-xs-12">
                        <div class="friend-link">
                            <div class="friend-link-heading">${message("shop.index.friendLink")}</div>
                            <div class="friend-link-body">
                                <ul class="clearfix">
                                    [#list friendLinks as friendLink]
                                        <li>
                                            <a href="${friendLink.url}" title="${friendLink.name}" target="_blank">
                                                <img class="img-responsive center-block" src="${friendLink.logo}"
                                                     alt="${friendLink.name}">
                                            </a>
                                        </li>
                                    [/#list]
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            [/#if]
        [/@friend_link_list]
    </div>
</main>
[#include "/shop/include/main_footer.ftl" /]
</body>
<script type="text/javascript">
    $(function () {
        $(".factory-2").hover(function () {
            $('.carousel-control').stop().fadeIn("slow");
        }, function () {
            $('.carousel-control').stop().fadeOut("slow");
        })
    })
</script>
</html>