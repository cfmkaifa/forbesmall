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
      [#if article.articleCategory.name == '平台规则']
          [#include "/shop/include/main_header.ftl" /]
      [#elseif article.articleCategory.name == '合同模板']
          [#include "/shop/include/main_header.ftl" /]
      [#elseif article.articleCategory.name == '支付方式']
          [#include "/shop/include/main_header.ftl" /]
      [#elseif article.articleCategory.name == '供应商服务']
          [#include "/shop/include/main_header.ftl" /]
      [#else]
           [#include "/shop/include/main_newheader.ftl" /]
      [/#if]
<main>
    <div class="detail">
        <div class="details">
            <div class="detailsNav">
[#--                <ul>--]
[#--                    [#list page.content as temparticle]--]
[#--                        <li>--]
[#--                            <div style="padding-top: 20px">--]
[#--                                <p><a href="${base}${temparticle.path}">${temparticle.title}</a></p>--]
[#--                            </div>--]
[#--                            <div class="detailsTime">--]
[#--                                <span>${temparticle.createdDate}</span>--]
[#--                            </div>--]
[#--                        </li>--]
[#--                    [/#list]--]
[#--                </ul>--]
                <ul class="detailsul">
                    <p style="margin: 16px 0px 10px 0px;">采购商服务</p>
                    <li class=""><a href="${base}/article/detail/17_1">平台服务协议</a></li>
                    <li style="margin-bottom: 10px;"><a href="${base}/article/detail/16_1">买家交易规则</a></li>
                    <p style=" border-top: 1px solid #EEEEEE; padding-top: 16px;  height: 50px;">合同模板</p>
                    <li style="margin-bottom: 10px;"><a href="${base}/article/detail/22_1">产品采购合同</a></li>
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
					        <p>${message("shop.article.author")}</p>
					        <p>${article.createdDate}</p>
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
