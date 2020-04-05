<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
    <link href="${base}/resources/common/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/articlenews.css" rel="stylesheet">
    <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/common/css/jquery.bxslider.css" rel="stylesheet">
    <link href="${base}/resources/common/css/demo.css" rel="stylesheet">
    <!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
        <![endif]-->
    <script src="${base}/resources/common/js/jquery.js"></script>
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.js"></script>
    <script src="${base}/resources/common/js/bootstrap.js?version=0.1"></script>
    <script src="${base}/resources/common/js/underscore.js"></script>
    <script src="${base}/resources/common/js/base.js?version=0.1"></script>
    <script src="${base}/resources/shop/js/base.js"></script>
    <script src="${base}/resources/common/js/jquery.bxslider.js"></script>
    <title>${message("shop.product.news")}</title>
</head>
<body>
[#include "/shop/include/main_newheader.ftl" /]
<main>
    <div>
        <div class="information_content">
            <div class="hotAttention content">
                <div class="hotAttention_img">
                    [#--<img src="${base}/resources/shop/images/bannerx.jpg" alt="">--]
                    [@ad_position id = 9952]
                        [#if adPosition??]
                            [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
                        [/#if]
                    [/@ad_position]
                </div>
                <div style="width: 43%;">
                    <div class="hotAttention_news">
				            <span class="hotAttention_title">
				                <div></div>
				                <h3>${articleCategories[1].name}</h3>
				                <p>${message("shop.article.instantnews")}</p>
				            </span>
                        <span class="more">
                                    <a target="_blank" href="${base}/article/articlelist/${articleCategories[1].id}">更多>></a></span>
                    </div>
                    <div class="hotAttention_content1 hotAttention_content">
                        [#list instantnews.content as instantnews]
                            <span><p><a target="_blank" title="${instantnews.title}" href="${base}${instantnews.path}">${instantnews.title}</a></p></span>
                        [/#list]
                    </div>
                </div>
            </div>
            <div class="content view">
                <div class="information_title">
				            <span class="hotAttention_title">
				                <div></div>
				                  <h3>${articleCategories[2].name}</h3>
				                  <p>${message("shop.article.fubuinsights")}</p>
				            </span>
                    <span class="more"><a target="_blank" href="${base}/article/articlelist/${articleCategories[2].id}">更多>></a></span>
                </div>
                <div class="hotAttention">
                    <div class="hotAttention_content hotAttention_content2">
                        [#list fubuinsights.content as fubuinsights]
                            <span><p><a target="_blank" title="${instantnews.title}" href="${base}${fubuinsights.path}">${fubuinsights.title}</a></p></span>
                        [/#list]
                    </div>
                    <div class="information_img">
                        [@ad_position id = 10001]
                            [#if adPosition??]
                                [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
                            [/#if]
                        [/@ad_position]
                    </div>
                </div>
            </div>
            <div class="content view">
                <div class="information_title">
				            <span class="hotAttention_title">
				                <div></div>
				                <h3>${articleCategories[3].name}</h3>
                                <p>${message("shop.article.factorystatus")}</p>
				            </span>
                    <span class="more"><a target="_blank" href="${base}/article/articlelist/${articleCategories[3].id}">更多>></a></span>
                </div>
                <div class="hotAttention">
                    <div class="hotAttention_content hotAttention_content2">
                        [#list factorystatus.content as factorystatus]
                            <span><p><a target="_blank" title="${instantnews.title}" href="${base}${factorystatus.path}">${factorystatus.title}</a></p></span>
                        [/#list]
                    </div>
                    <div class="information_img">
                        [#-- <img src="${base}/resources/shop/images/11.png" alt="">--]
                        [@ad_position id = 10002]
                            [#if adPosition??]
                                [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
                            [/#if]
                        [/@ad_position]
                    </div>
                </div>
            </div>
            <div class="content view">
                <div class="information_title">
				            <span class="hotAttention_title">
				                <div></div>
				                <h3>${articleCategories[5].name}</h3>
								 <p>${message("shop.article.commonality")}</p>
				            </span>
                    <span class="more"><a target="_blank" href="${base}/article/articlelist/${articleCategories[5].id}">更多>></a></span>
                </div>
                <div class="hotAttention">
                    <div class="hotAttention_content hotAttention_content2">
                        [#list commonality.content as commonality]
                            <span><p><a target="_blank" title="${instantnews.title}" href="${base}${commonality.path}">${commonality.title}</a></p></span>
                        [/#list]
                    </div>
                    <div class="information_img">
                        [@ad_position id = 10003]
                            [#if adPosition??]
                                [#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
                            [/#if]
                        [/@ad_position]
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
[#include "/shop/include/main_footer.ftl" /]
</body>
<script>
    $(function () {
        let li = $(".item");
        li.click(function () {
            $(this).siblings().removeClass('active');
            $(this).addClass('active');
        })

    })
</script>
</html>
