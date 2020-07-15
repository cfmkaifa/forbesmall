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
	<link href="${base}/resources/shop/css/about.css" rel="stylesheet">
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
<body data-spy="scroll" data-target="#myScrollspy" data-offset="20">
[#include "/shop/include/main_header.ftl" /]
<div class="container">
	<div class="row">
		<nav class="col-sm-2" id="myScrollspy">
			<div class="container-fluid"> 
			<div class="container-fluid"> 
			<ul class="nav nav-pills nav-stacked">
				<li class="active"><a href="#section1">关于我们</a></li>
				<li><a href="#section2">平台介绍</a></li>
				<li><a href="#section3">项目背景</a></li>
				<li><a href="#section4">平台优势</a></li>
				<li><a href="#section5">联系我们</a></li>
			</ul>
			</div>	
			</div>		
		</nav>
		<div class="col-sm-9">
			<div id="section1"> 
			   <!-- 分割线 -->
				<div class="division">
					<div class="division_x"></div>
					<div>
						<p class="division_eng">ABOUT US</p>
						<p class="division_title">关于我们</p>
					</div>
					<div class="division_x"></div>
				</div>
				<!-- 分割线 -->
				<div class="introduce">
					<p>CFM诞生于2015年2月8号，由上海智能科技有限公司创建。</p>
					<p>上海让雷智能科技有限公司根据市场调查，目前中国市场还没有一款完善的与纺织品企业对接原材料采购平台，尤其是无纺布生产企业对接的原材料采购平台。同时应对与各个化纤工厂。</p>
					<p>由于化纤是传统纺织行业，属于大宗物资交易交流，化纤工厂与纺织品企业交流交易方式仍然采用传统的方式进行，鉴于此市场信息交流更新缓慢，信息不集中等特点。</p>
					<p>上海让雷智能科技有限公司联合上海缔荣纺织品有限公司投资开发CFM平台，真诚服务于纺织品企业原材料采购，尤其是无纺布生产企业对于原材料采购的需求服务。</p>
				</div>
			</div>
			<div id="section2"> 
				<!-- 分割线 -->
				<div class="division">
					<div class="division_x"></div>
					<div>
						<p class="division_eng">PLATFORM PROFILE</p>
						<p class="division_title">平台介绍</p>
					</div>
					<div class="division_x"></div>
				</div>
				<!-- 分割线 -->
				<div class="introduce">
					<p>“福布云商化纤（CFM—China Fiber Marketing)”专注整合无纺布领域专用化纤资源、服务上下游产业原材料B2B平台建设。CFM平台致力于简化采购流程、节约采购成本、加速新品开发、促进采购合规化管理。CFM平台将着力提供和优化上下游供应链管理服务、采购链管理服务、技术与进出口服务、国内外行业资讯服务。</p>
					<p>福布云商化纤平台由上海让雷智能科技有限公司打造开发，CFM项目的建设得到化纤及无纺布领域诸多企业的关注和鼎力支持。CFM平台未来将建设成为化纤及无纺产业的特色供应链服贸平台，以及行业大数据平台、新材料新技术推广平台。欢迎产业上下游企业加入我们，共享数字经济时代的盛宴。</p>
				</div>
			</div>        
			<div id="section3">         
				<!-- 分割线 -->
				<div class="division">
					<div class="division_x"></div>
					<div>
						<p class="division_eng">PROJECT BACKGROUND</p>
						<p class="division_title">项目背景</p>
					</div>
					<div class="division_x"></div>
				</div>
				<!-- 分割线 -->
				<div class="background">
					<p>传统贸易模式下从化纤到无纺布商品的行业产业链比较长，供应链体系为线状链接、相对封闭，处于传统化纤无纺布产业链中的原材料物流、信息流、资金流的运转速度和效率受到限制。CFM平台将线性链接构成网状链接，增加化纤及无纺布制品的物流、信息流、资金流的流通渠道，提升化纤无纺产业链运作效率，大幅降低摩擦成本。这正是福布云商（CFM）互联网工业平台的初衷。2020年面对新型肺炎疫情的影响，全球的经济和贸易受到极大的影响，传统销售交易模式受到进一步挑战。寻求不见面不去现场就可以把生意完成的线上方式成为一种趋势。福布云商CFM助力化纤及无纺布领域企业实现提升贸易效率、减少交易成本、加速企业发展，为化纤及无纺布行业健康发展提供助力。</p>
					<img src="/resources/shop/images/technological2.png" />
				</div>
			</div>
			<div id="section4">
				<!-- 分割线 -->
				<div class="division">
					<div class="division_x"></div>
					<div>
						<p class="division_eng">WHY YOU CHOOSE FOB</p>
						<p class="division_title">为什么选择福布云商</p>
					</div>
					<div class="division_x"></div>
				</div>
				<!-- 分割线 -->
				<div class="choice">
					<div>
						<img src="/resources/shop/images/635.png">
						<p class="choice-title">节省采购成本</p>
						<p class="choice-conter">直接对接工厂，节约时间节省成本</p>
					</div>
					<div>
						<img src="/resources/shop/images/636.png">
						<p class="choice-title">产品种类多样</p>
						<p class="choice-conter">产品种类繁多，满足采购需求</p>
					</div>
					<div>
						<img src="/resources/shop/images/637.png">
						<p class="choice-title">简化采购流程</p>
						<p class="choice-conter">平台保障线上交易，简化采购流程</p>
					</div>
					<div>
						<img src="/resources/shop/images/638.png">
						<p class="choice-title">加速新品开发</p>
						<p class="choice-conter">资源合作，加速新品开发</p>
					</div>
				</div>
			</div>      
			<div id="section5">
				<!-- 分割线 -->
				<div class="division">
					<div class="division_x"></div>
					<div>
						<p class="division_eng">CONTACT US</p>
						<p class="division_title">联系我们</p>
					</div>
					<div class="division_x"></div>
				</div>
				<!-- 分割线 -->
				<div class="iphone">
					<img src="/resources/shop/images/technological.png"  class="iphoneimg">
					<ul class="address">
						<li>
							<span>联系人：</span>
							<span>张丽霞</span>
						</li>
						<li>
							<span>联系电话：</span>
							<span>13817937530</span>
						</li>
						<li>
							<span>电子邮箱：</span>
							<span>lisa.zhang.@shdbr.com</span>
						</li>
						<li>
							<span>公司电话：</span>
							<span>021-61833518</span>
						</li>
						<li>
							<span>公司地址：</span>
							<span>上海市青浦区诸光路1588号虹桥世界中心L2B-503室</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
[#include "/shop/include/main_footer.ftl" /]

<script type="text/javascript">
	$(window).scroll(function(){
		if($(window).scrollTop() >= 270){
			$(".nav-pills").css("top","20px");
		} else{
			$(".nav-pills").css("top","270px");
		}
	});
</script>
</body>
</html>