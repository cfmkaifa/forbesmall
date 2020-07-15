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
			<div class="conter">
				<div class="conter-left">
					<div class="details">
						[#noautoesc]
							${article.getPageContent(pageNumber)}
						[/#noautoesc]
					</div>
					<div class="get_ct_more"> 
						<button id="get_ct_more" type="button" >点击购买全文阅读</button>
					</div> 
				</div>
				<div class="conter-right">
					<div class="newsrilist">
						<div class="newss">
							<div class="newss-top">
								<span></span>
								<p>风从柯桥来｜新迷彩花型赋予更多创意与可能</p>
							</div>
							<div class="newssdata">
								2020-07-07   10:30
							</div>
						</div>
					</div>
					<div class="Hot">
						<p class="widths">热门产品</p>
						<ul class="Hotlist">
							<li>ES(PP/PE复合短纤维)</li>
							<li>竹炭纤维</li>
							<li>水刺专用粘胶</li>
							<li>竹炭纤维</li>
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
	[#include "/shop/include/main_footer.ftl" /]
	</body>
	<script type="text/javascript">
		$("#get_ct_more").click(function(){
			$(".get_ct_more").hide();
			$(".details").css("height","auto");
		})
		$(".xx").click(function(){
			$(".modoe").hide();
			$("#cover").hide();
		})
	</script>
</html>
