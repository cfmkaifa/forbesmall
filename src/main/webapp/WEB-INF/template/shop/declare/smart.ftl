<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>智能工厂</title>
	<link rel="stylesheet" type="text/css" href="/resources/shop/css/common.css"/>
	<link rel="stylesheet" type="text/css" href="/resources/shop/css/smart.css"/>
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
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/jquery.lazyload.js"></script>
	<script src="${base}/resources/common/js/jquery.bxslider.js"></script>
	<script src="${base}/resources/common/js/jquery.qrcode.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
</head>
<body>
[#include "/shop/include/main_header.ftl" /]
<div style="width: 1200px; margin: 10px auto;">
	<ol class="breadcrumb">
		<li>
			<a href="${base}">
				<i class="iconfont icon-homefill"></i>
				${message("common.breadcrumb.index")}
			</a>
		</li>
		<li class="active">智能工厂</li>
	</ol>
</div>
<div class="smart-top">
	<ul>
		<li><h2>智能工厂</h2></li>
		<li class="contir">智慧工程与智能制造解决方案</li>
	</ul>
</div>
<div class="smartnav">
	<ul class="smartnav-ul">
		<li class="activeaa smartnavli1">智能工厂应用场景</li>
		<li class="smartnavli2">智能工厂解决方案</li>
		<li class="smartnavli3">工业互联网业务规划及实施建议</li>
	</ul>
</div>
<div class="scenario">
	<p class="paln-title">智能工厂应用场景</p>
	<p class="paln-title2">INTELLIGENT FACTORY APPLICATION SCENARIO</p>
	<div class="paln-main">
		<div class="palnmain-left">
			<dl>
				<dt>面向企业运营的管理决策优化</dt>
				<dd>供应链管理优化</dd>
				<dd>生产管控一体化</dd>
				<dd>企业智能决策</dd>
			</dl>
			<dl class="dl">
				<dt>面向工业现场的生产过程优化</dt>
				<dd>制造工艺优化</dd>
				<dd>生产流程优化</dd>
				<dd>企业智能决策</dd>
				<dd>质量优化</dd>
				<dd>设备运行优化</dd>
				<dd>能耗优化</dd>
			</dl>
		</div>
		<div class="palnmain-center">
			<img src="/resources/shop/images/dispark1.png" />
		</div>
		<div class="palnmain-right">
			<dl>
				<dt>面向社会化生产资源优化配置与协同</dt>
				<dd>协同制造</dd>
				<dd>制造能力交易</dd>
				<dd>个性化定制</dd>
				<dd>产融结合</dd>
			</dl>
			<dl class="dl">
				<dt>面向产品全生命周期的管理与服务优化</dt>
				<dd>产品溯源</dd>
				<dd>产品设计反馈优化</dd>
				<dd>产品远程预测性维护</dd>
			</dl>
		</div>
	</div>
	<div class="application">
		<p class="paln-title">典型应用</p>
		<p class="paln-title2">TYPICAL APPLICATIONS</p>
		<div class="applicationimg">
			<img src="/resources/shop/images/dispark2.png" >
			<dl>
				<dt>质量优化管理</dt>
				<dd>1. 智能质量检测判定或分类一提高质量检测的准确性和效率、降低检测成本</dd>
				<dd>2. 缺陷产品原因的挖一降低缺陷或废品率</dd>
				<dd>3. 在线质量检测或预测-动态寻优调整牛产过程，中断废品生产流程。</dd>
				<dd>4.实现工艺、物料、过程和质量的闭环关联一综合性地优化工艺参数，并对物料品质的控制提供反馈。</dd>
				<dd>5. 提供在线质量数据，支持数字孪生体一实现对产品质量进行全过程追溯</dd>
				<dd>6. 提供在线质量数据，支持在线成本核算和绩效预和评估一提高生产管理水平</dd>
			</dl>
		</div>
		<div class="applicationimg">
			<img src="/resources/shop/images/smart1.png" >
			<dl>
				<dt>能耗优化管理</dt>
				<dd>1. 能量使用和效率的监控一提高能耗的可见度，为实现节减排提供确切依据。</dd>
				<dd>2. 对能量的供给和使用根实时工况实现动态寻优，提高稳定性一保障牛产，避免浪费。</dd>
				<dd>3. 对生产过程操作参数进行优化控制，提高牛产过程的稳定性和实现最佳控制值一提高热能使用效率，降低能耗和减少排放。</dd>
				<dd>4. 实现工艺、物料、过程、质量和能耗的闭环关联一综合性地优化工艺参数</dd>
				<dd>5. 提供能耗和排放在线数据，支持在线成本核算和绩效预测和评估一提高生产管理水平</dd>
			</dl>
		</div>
		<div class="applicationimg">
			<img src="/resources/shop/images/smart1.png" >
			<dl>
				<dt>工艺与过程优化</dt>
				<dd>1. 建立质量、物耗、能耗、产率（产量和用时）对工艺规程和参数的闭环反馈，在保障质量的前提下，寻找成本最低和工期最短（效率最高）的最佳工艺</dd>
				<dd>2. 根据工序上游物料或制品的质量波动，工况、下游产品质量的反馈，对工艺或过程参数进行动态寻优一保障产品质量的稳定性和提高合格率，降低能耗</dd>
				<dd>3. 建立技术框架，对工艺技术和经验以及最佳实践，逐斯实现模型化和软件化，加快工知识的积累、继承和创新</dd>
			</dl>
		</div>
		<div class="applicationimg">
			<dl>
				<dt>计划与排产优化</dt>
				<dd>1. 建立自动化的和具有动态调整能力的最佳生产排程一实现产能利用的最大化和交期的最小化</dd>
				<dd>2. 建立以订单和业务价值拉动的生产排程，最终实现零库存的按需生产</dd>
			</dl>
			<img src="/resources/shop/images/smart2.png" >
		</div>
	</div>
</div>
<div class="plan">
	<div>
		<p class="paln-title">智能工厂 — 功能架构图</p>
		<p class="paln-title2">FUNCTIONAL ARCHITECTURE DIAGRAM</p>
		<div class="architecture1 architecture">

		</div>
	</div>
	<div class="plan-div">
		<p class="paln-title">智能工厂 — 系统架构图</p>
		<p class="paln-title2">SYSTEM ARCHITECTURE DIAGRAM</p>
		<div class="architecture2 architecture">

		</div>
	</div>
	<div class="plan-div">
		<p class="paln-title">智能工厂开发流程</p>
		<p class="paln-title2">DEVELOPMENT PROCESS</p>
		<div class="architecture3 architecture">
		</div>
	</div>
	<div class="plan-div plan-div1">
		<p class="paln-title">智能工厂数据可视化</p>
		<p class="paln-title2">DATA VISUALIZATION</p>
		<div class="architecture architecture4">
			<img src="/resources/shop/images/live2.png" class="adviceimg3" />
			<img src="/resources/shop/images/live1.png" class="adviceimg2">
		</div>
	</div>
</div>
<div class="advice">
	<p class="paln-title">全面的解决方案应用平台</p>
	<p class="paln-title2">COMPREHENSIVE SOLUTION APPLICATION PLATFORM</p>
	<div class="open">
	</div>
	<div>
		<p class="open-tltle">工业企业级工业大数据服务</p>
		<div class="openimg1">
		</div>
	</div>
	<div>
		<p class="open-tltle">工业企业设备远程运维服务</p>
		<div class="openimg2">
		</div>
	</div>
	<div>
		<p class="open-tltle">工业互联网 — 设备预测性维护的价值</p>
		<div class="openimg3">
		</div>
	</div>
	<div>
		<p class="open-tltle">工业云平台服务内容 — 基础服务（全景图）</p>
		<div class="openimg4">
		</div>
	</div>
	<div>
		<p class="open-tltle">工业云平台服务内容 — 基础服务（全景图）</p>
		<div class="openimg5">
		</div>
	</div>
	<div>
		<p class="open-tltle">应用服务-大屏展示＆APP展示＆智能健康分析</p>
		<div class="openimg6">
		</div>
	</div>
	<div>
		<p class="open-tltle">典型应用 — 设备点巡检管理智能服务</p>
		<div class="openimg7">
		</div>
	</div>
	<div>
		<p class="open-tltle">典型应用 — 智能设备状态诊断分析</p>
		<div class="openimg8">
		</div>
	</div>
	<div>
		<p class="open-tltle">典型应用 — 智能设备状态诊断分析</p>
		<div class="openimg9">
		</div>
	</div>
</div>
[#include "/shop/include/main_footer.ftl" /]
</body>
<script type="text/javascript">
	$(function(){
		$(".smartnav-ul li").click(function(){
			$(this).addClass("activeaa").siblings("li").removeClass("activeaa")
		})
		$(".smartnavli1").click(function(){
			$(".scenario").show();
			$(".plan").hide();
			$(".advice").hide();
		})
		$(".smartnavli2").click(function(){
			$(".scenario").hide();
			$(".plan").show();
			$(".advice").hide();
		})
		$(".smartnavli3").click(function(){
			$(".scenario").hide();
			$(".plan").hide();
			$(".advice").show();
		})
	})
</script>
</html>
