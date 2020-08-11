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
    [#noautoesc]
        [#escape x as x?js_string]
            <script>
                $().ready(function () {
                    var $articlePayForm = $("#articlePayForm");
                    var articleId = ${articleId};
                    var is_login = ${is_login};
                    $("#get_ct_more").click(function(){
                        if(is_login==""){
                            $("#modoes").show(); //未登录情况弹框
                            $("#cover").show();	//遮罩层弹框
                        }else{
                            document.getElementById("articlePayForm").submit();
                        }
                    });
                    $articlePayForm.validate({
                        rules: {
                            email: {
                                required: true,
                                email: true
                            }
                        },
                        submitHandler: function (form) {
                            $.ajax({
                                url: $articlePayForm.attr("action"),
                                type: $articlePayForm.attr("method"),
                                data: {
                                    articleId: articleId
                                },
                                dataType: "json",
                                cache: false,
                                success: function (data) {
                                    $.bootstrapGrowl(data.message);
                                    // $productNotifyModal.modal("hide");
                                }
                            });
                        }
                    });
                });
            </script>
        [/#escape]
    [/#noautoesc]
</head>
<body>
[#include "/shop/include/main_header.ftl" /]
<main>
    <div class="arti-main">
        <div class="conter">
            <form id="articlePayForm" class="form-horizontal" action="${base}/article/articlePay/${articleId}" method="get" style=" margin: 0 auto; ">
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
                        <div class="details-a" id="details-a">
                            [#noautoesc]
                                ${article.getPageContent(pageNumber)}
                            [/#noautoesc]
                            <div class="get_ct_more">
                                <button id="get_ct_more" type="button">点击购买全文阅读</button>
                            </div>
                        </div>
                    [/#if]
                </div>
            </form>
        </div>
    </div>
    <div id="cover"></div>
    <div class="modoe" id="modoes">
        <div class="modoe-title">
            <p>提示</p>
            <p class="xx">×</p>
        </div>
        <div class="modoe-conter">
            <p class="modoe-P">您当前尚未登录，如需购买当前文章，请立即登录</p>
            <div class="modoe-button">
                <object>
                    <a href="${base}/member/login"><button type="button" class=" abuttn btn btn-default">采购商登录入口</button></a>
                </object>
                <object>
                    <a href="${base}/business/login"><button type="button" class="btn btn-default">供应商登录入口</button></a>
                </object>
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
