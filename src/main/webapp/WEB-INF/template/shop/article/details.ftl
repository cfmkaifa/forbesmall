<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
    <link href="${base}/resources/common/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/base.css" rel="stylesheet">
    <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/common/css/demo.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/articledetails.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/articleindex.css" rel="stylesheet">
    <!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
        <![endif]-->
    <script src="${base}/resources/common/js/jquery.js"></script>
    <script src="${base}/resources/common/js/bootstrap.js?version=0.1"></script>
    <script src="${base}/resources/common/js/underscore.js"></script>
    <script src="${base}/resources/common/js/base.js?version=0.1"></script>
    <script src="${base}/resources/shop/js/base.js"></script>
    <title>${message("shop.product.logo")}</title>
</head>
<body>
[#include "/shop/include/main_header.ftl" /]
<main>
    <div class="arti-main">
        <div class="conter">
            <div class="conter-left">
                <div class="arti-title">${article.title}</div>
                <div class="source">
                    <p class="">
                        <span>${message("shop.article.author")}:</span>
                        <span>${article.author}</span>
                    </p>
                    <p class="source-data">${article.createdDate}</p>
                </div>
                [#if isPerm]
                    <div class="details-b">
                        [#noautoesc]
                            ${article.getPageContent(pageNumber)}
                        [/#noautoesc]
                    </div>
                [#else ]
                    <div class="details-a">
                        [#noautoesc]
                            ${article.getPageContent(pageNumber)}
                        [/#noautoesc]
                        <div class="get_ct_more">
                            <button id="get_ct_more" type="button" >点击购买全文阅读</button>
                        </div>
                    </div>
                [/#if]
            </div>
            <div class="conter-right">
                <p class="widths">${article.articleCategory.name}</p>
                <div class="newsrilists">
                    [#list page.content as articles]
                        <div class="newss">
                            <div class="newss-top">
                                <span></span>
                                <p title="${articles.title}"> <a href="${base}${articles.path}">${articles.title}</a></p>
                            </div>
                            <div class="newssdata">
                                <p>${articles.createdDate}</p >
                                [#if articles.articleCategory.weekSubFee?has_content]
                                    <p class="week">周报</p >
                                [#elseif articles.articleCategory.monthSubFee?has_content]
                                    <p class="month">月报</p >
                                [#elseif articles.articleCategory.quarterSubFee?has_content]
                                    <p class="quarter">季报</p >
                                [#elseif articles.articleCategory.yearSubFee?has_content]
                                    <p class="year">年报</p >
                                [/#if]
                            </div>
                        </div>
                    [/#list]
                </div>
                <div class="Hot">
                    <p class="widths">热门产品</p>
                    <ul class="Hotlist">
                        [#list product.content as product]
                            <li>
                                <a href="${base}${product.path}">
                                    ${product.name}
                                </a>
                            </li>
                        [/#list]
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="cover"></div>
    <div class="modoe">
        <div class="modoe-title">
            <p>提示</p>
            <p class="xx">×</p>
        </div>
        <div class="modoe-conter">
            <p class="modoe-P">您当前尚未登录，如需购买当前文章，请立即登录</p>
            <div class="modoe-button">
                <button type="button" class=" abuttn btn btn-default">采购商登录入口</button>
                <button type="button" class="btn btn-default">供应商登录入口</button>
            </div>
        </div>
    </div>
    <div class="modoemoney">
        <div class="modoe-title">
            <p>提示</p>
            <p class="xx">×</p>
        </div>
        <div class="modoemoney-conter">
            <p>您当前正在进行支付操作，请确认购买商品信息：</p>
            <p>[周报]风从柯桥来｜新迷彩花型赋予更多创意与可能，来源：万方数据库</p>
            <p class="money-color">应付金额：¥25</p>
        </div>
    </div>
</main>
[#include "/shop/include/main_footer.ftl" /]
</body>

<script type="text/javascript">
    //新闻资讯详情浏览埋点事件
    $(function () {
        var refer=document.referrer;
        try {
            sensors.track('newsClick',{
                news_from_page:refer,
                news_id:${article.id},
                news_title:"${article.title}",
                news_category:"${article.articleCategory.name}",
                is_pay:"${article.articleCategory.subscribe}",
                is_vip:${is_vip},
                vip_type:"${vip_type}"
            })
        }catch (e) {
            console.log(e)
        }
    })
</script>

</html>
