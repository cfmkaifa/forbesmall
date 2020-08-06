<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="author" content="huanghy">
    <meta name="copyright" content="">
    [@seo type = "ARTICLE_LIST"]
        [#if articleCategory.seoKeywords?has_content]
            <meta name="keywords" content="${articleCategory.seoKeywords}">
        [#elseif seo.resolveKeywords()?has_content]
            <meta name="keywords" content="${seo.resolveKeywords()}">
        [/#if]
        [#if articleCategory.seoDescription?has_content]
            <meta name="description" content="${articleCategory.seoDescription}">
        [#elseif seo.resolveDescription()?has_content]
            <meta name="description" content="${seo.resolveDescription()}">
        [/#if]
        <title>[#if articleCategory.seoTitle?has_content]${articleCategory.seoTitle}[#else]${seo.resolveTitle()}[/#if][#if showPowered]  [/#if]</title>
    [/@seo]
    <link href="${base}/favicon.ico" rel="icon">
    <link href="${base}/resources/common/css/bootstrap.css?version=0.1" rel="stylesheet">
    <link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
    <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/common/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/article.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/charge.css" rel="stylesheet">
    <!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
    <script src="${base}/resources/common/js/jquery.js"></script>
    <script src="${base}/resources/common/js/bootstrap.js?version=0.1"></script>
    <script src="${base}/resources/common/js/bootstrap-growl.js?version=0.1"></script>
    <script src="${base}/resources/common/js/jquery.validate.js"></script>
    <script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
    <script src="${base}/resources/common/js/jquery.form.js"></script>
    <script src="${base}/resources/common/js/jquery.cookie.js"></script>
    <script src="${base}/resources/common/js/jquery.qrcode.js"></script>
    <script src="${base}/resources/common/js/underscore.js"></script>
    <script src="${base}/resources/common/js/url.js"></script>
    <script src="${base}/resources/common/js/velocity.js"></script>
    <script src="${base}/resources/common/js/velocity.ui.js"></script>
    <script src="${base}/resources/common/js/base.js?version=0.1"></script>
    <script src="${base}/resources/shop/js/base.js"></script>
    <style>
        .list-group .icon-roundcheck {
            font-size: 18px;
            -webkit-transition: color 0.5s;
            transition: color 0.5s;
        }

        .list-group-item {
            padding: 10px;
        }

        .list-group-item .media-object {
            width: 100px;
            overflow: hidden;
        }

        .list-group-item .media-object img {
            max-width: 100%;
            border: solid 1px #e8e8e8;
        }

        .list-group-item.active .icon-roundcheck {
            color: #dd0000;
        }
    </style>
    [#noautoesc]
        [#escape x as x?js_string]
            <script>
                $().ready(function () {
                    var $subscribeForm = $("#subscribeForm");
                    var $paymentPluginId = $("#paymentPluginId");
                    var $paymentPluginItem = $("#paymentPlugin .media");
                    // 支付插件
                    $paymentPluginItem.click(function () {
                        var $element = $(this);
                        var paymentPluginId = $element.data("payment-plugin-id");
                        $element.addClass("active").siblings().removeClass("active");
                        $paymentPluginId.val(paymentPluginId);
                    });
                    // 表单验证
                    $subscribeForm.validate({
                        rules: {
                            "paymentItemList[0].amount": {
                                required: true,
                                positive: true,
                                decimal: {
                                    integer: 12,
                                    fraction: ${setting.priceScale}
                                }
                            }
                        }
                    });

                });
            </script>
        [/#escape]
    [/#noautoesc]
</head>
<body class="shop article-list">
[#include "/shop/include/main_header.ftl" /]
[#include "/shop/include/main_sidebar.ftl" /]
<main>
    <div class="container">
        <div class="row">
            <div class="col-xs-2">
            </div>
            <div class="col-xs-8">
                <ol class="breadcrumb">
                    <li>
                        <a href="${base}/">
                            <i class="iconfont icon-homefill"></i>
                            ${message("common.breadcrumb.index")}
                        </a>
                    </li>
                    [@article_category_parent_list articleCategoryId = articleCategory.id]
                        [#list articleCategories as articleCategory]
                            <li>
                                <a href="${base}${articleCategory.path}">${articleCategory.name}</a>
                            </li>
                        [/#list]
                    [/@article_category_parent_list]
                    <li class="active">${articleCategory.name}</li>
                </ol>
                <div class="list panel panel-default">
                    <div class="panel-body">
                        <form id="subscribeForm" action="${base}/payment" method="post">
                            <input name="paymentItemList[0].type" type="hidden" value="NEWS_SUBSCRIBE_PAYMENT">
                            <input id="paymentPluginId" name="paymentPluginId" type="hidden"
                                   value="${defaultPaymentPlugin.id}">
                            <input name="rePayUrl" type="hidden" value="${base}/article/list/${articleCategory.id}">
                            <input id="subscribeAmount" name="paymentItemList[0].amount" class="form-control"
                                   type="hidden" maxlength="16" onpaste="return false;" value="${subFee}">
                            <input name="paymentItemList[0].orderSn" class="form-control" type="hidden" maxlength="16"
                                   onpaste="return false;" value="${orderSn}">
                            <div class="subscribe">
                                <div class="subscribeTitle">
                                    <p>${message("shop.article.subscribeInfo")}</p>
                                </div>
                                <div class="subscribeContent">
                                    <p>${message("shop.article.subscribeOrder")}${articleCategory.name}
                                        ，${message("shop.article.needToPay")}${currency(subFee, true, true)}</p>
                                </div>
                            </div>
                            <div class="paying">
                                <div class="subscribeTitle payingTitle">
                                    <p>${message("common.paymentPlugin")}</p>
                                    <span class="pricea">
													<p>${message("shop.article.stayPay")}：</p>
													<h4>${currency(subFee, true, true)}</h4>
												</span>
                                </div>
                                [#if paymentPlugins?has_content]
                                    <div id="paymentPlugin" class="payment-plugin">
                                        <div class="payment-plugin-heading">${message("common.paymentPlugin")}</div>
                                        <div class="payment-plugin-body clearfix">
                                            [#list paymentPlugins as paymentPlugin]
                                                <div class="media[#if paymentPlugin == defaultPaymentPlugin] active[/#if]"
                                                     data-payment-plugin-id="${paymentPlugin.id}">
                                                    <div class="media-left media-middle">
                                                        <i class="iconfont icon-roundcheck"></i>
                                                    </div>
                                                    <div class="media-body media-middle">
                                                        <div class="media-object">
                                                            [#if paymentPlugin.logo?has_content]
                                                                <img src="${paymentPlugin.logo}"
                                                                     alt="${paymentPlugin.displayName}">
                                                            [#else]
                                                                ${paymentPlugin.displayName}
                                                            [/#if]
                                                        </div>
                                                    </div>
                                                </div>
                                            [/#list]
                                        </div>
                                    </div>
                                [/#if]
                                <button class="btn btn-primary" type="submit">${message("common.submit")}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-xs-2">
                [#include "/shop/include/article_search.ftl" /]
            </div>
        </div>
    </div>
</main>
[#include "/shop/include/main_footer.ftl" /]
</body>

<script type="text/javascript">
    //点击购买行业资讯埋点事件
    $(function () {
        try {
            sensors.track('newsBuy',{
                news_buy_type:"${news_buy_type}",
                news_buy_price:${news_buy_price},
                news_buy_user:"${news_buy_user}",
                is_vip:${is_vip},
                vip_type:"${vip_type}"
            })
        }catch (e) {
            console.log(e)
        }
    })
</script>

</html>	