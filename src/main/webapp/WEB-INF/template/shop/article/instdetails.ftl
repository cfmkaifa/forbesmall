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
    <div class="detail">
        <div class="details">
            <div class="detailsNav">
                    <ul class="detailsul">
                        <p style="margin: 16px 0px 10px 0px;">采购商服务</p>
                        <li class="actives">买家服务协议</li>
                        <li style="margin-bottom: 10px;">买家交易规则</li>
                        <p style=" border-top: 1px solid #EEEEEE; padding-top: 16px;  height: 50px;">合同模板</p>
                        <li style="margin-bottom: 10px;">产品采购合同</li>
                        <p style="border-top: 1px solid #EEEEEE; padding-top: 16px; height: 50px;">支付方式</p>
                        <li>在线支付</li>
                        <li style="margin-bottom: 10px;">支付常见问题</li>
                        <p style=" border-top: 1px solid #EEEEEE; padding-top: 16px;  height: 50px;">供应商服务</p>
                        <li>会员入驻</li>
                        <li>商家服务协议</li>
                        <li style="margin-bottom: 10px;">商家交易规则</li>
                    </ul>
            </div>
            <div class="detailsContent">
                <div class="content_title">
                    <h2 label="label" class="contenth2">
                        <p>${article.title}</p>
                    </h2>
                    <span class="content_time">
					        <p>${message("shop.article.time")}</p>
					        <p>${article.createdDate}</p>
					</span>
                    <span class="content_time">
					        <p>${message("shop.article.author")}</p>
					        <p>${article.author}</p>
					</span>
                    <div class="content_img">
                        [#noautoesc]
                            ${article.getPageContent(pageNumber)}
                        [/#noautoesc]
                    </div>
                </div>
            </div>
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
