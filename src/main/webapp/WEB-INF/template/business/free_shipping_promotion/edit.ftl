<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, max-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("business.freeShippingPromotionPlugin.edit")}  </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-select.css" rel="stylesheet">
	<link href="${base}/resources/common/css/ajax-bootstrap-select.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-datetimepicker.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-fileinput.css" rel="stylesheet">
	<link href="${base}/resources/common/css/summernote.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/business/css/base.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/bootstrap-select.js"></script>
	<script src="${base}/resources/common/js/ajax-bootstrap-select.js"></script>
	<script src="${base}/resources/common/js/moment.js"></script>
	<script src="${base}/resources/common/js/bootstrap-datetimepicker.js"></script>
	<script src="${base}/resources/common/js/bootstrap-fileinput.js"></script>
	<script src="${base}/resources/common/js/summernote.js"></script>
	<script src="${base}/resources/common/js/jquery.nicescroll.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
	<script src="${base}/resources/common/js/jquery.form.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js?version=0.1"></script>
	<script src="${base}/resources/business/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
				
				var $freeShippingPromotionForm = $("#freeShippingPromotionForm");
				
				// 表单验证
				$freeShippingPromotionForm.validate({
					rules: {
						name: "required",
						minPrice: {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						maxPrice: {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							},
							greaterThanEqual: "#minPrice"
						},
						minQuantity: "digits",
						maxQuantity: {
							digits: true,
							greaterThanEqual: "#minQuantity"
						},
						value: {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						order: "digits"
					}
				});
			
			});
			</script>
		[/#escape]
	[/#noautoesc]
</head>
<body class="business">
	[#include "/business/include/main_header.ftl" /]
	[#include "/business/include/main_sidebar.ftl" /]
	<main>
		<div class="container-fluid">
			<ol class="breadcrumb">
				<li>
					<a href="${base}/business/index">
						<i class="iconfont icon-homefill"></i>
						${message("common.breadcrumb.index")}
					</a>
				</li>
				<li class="active">${message("business.freeShippingPromotionPlugin.edit")}</li>
			</ol>
			<form id="freeShippingPromotionForm" class="ajax-form form-horizontal" action="${base}/business/free_shipping_promotion/update" method="post">
				<input name="promotionId" type="hidden" value="${promotion.id}">
				<input name="freeShippingAttributeId" type="hidden" value="${freeShippingAttribute.id}">
				<div class="panel panel-default">
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#base" data-toggle="tab">${message("business.freeShippingPromotionPlugin.base")}</a>
							</li>
							<li>
								<a href="#introduction" data-toggle="tab">${message("Promotion.introduction")}</a>
							</li>
						</ul>
						<div class="tab-content">
							<div id="base" class="tab-pane active">
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="name">${message("Promotion.name")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="name" name="name" class="form-control" type="text" value="${promotion.name}" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Promotion.image")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input name="image" type="hidden" value="${promotion.image}" data-provide="fileinput" data-file-type="IMAGE">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="beginDate">${message("common.dateRange")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="input-group" data-provide="datetimerangepicker" data-date-format="YYYY-MM-DD HH:mm:ss">
											<input id="beginDate" name="beginDate" class="form-control" type="text" value="[#if promotion.beginDate??]${promotion.beginDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]">
											<span class="input-group-addon">-</span>
											<input name="endDate" class="form-control" type="text" value="[#if promotion.endDate??]${promotion.endDate?string("yyyy-MM-dd HH:mm:ss")}[/#if]">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="minPrice">${message("common.priceRange")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="input-group">
											<input id="minPrice" name="minPrice" class="form-control" type="text" value="${freeShippingAttribute.minPrice}" maxlength="16">
											<span class="input-group-addon">-</span>
											<input name="maxPrice" class="form-control" type="text" value="${freeShippingAttribute.maxPrice}" maxlength="16">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="minQuantity">${message("common.quantityRange")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="input-group">
											<input id="minQuantity" name="minQuantity" class="form-control" type="text" value="${freeShippingAttribute.minQuantity}" maxlength="9">
											<span class="input-group-addon">-</span>
											<input name="maxQuantity" class="form-control" type="text" value="${freeShippingAttribute.maxQuantity}" maxlength="9">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("common.setting")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="checkbox checkbox-inline">
											<input name="_isFreeShipping" type="hidden" value="false">
											<input id="isFreeShipping" name="isFreeShipping" type="checkbox" value="true"[#if freeShippingAttribute.isFreeShipping] checked[/#if]>
											<label for="isFreeShipping">${message("FreeShippingPromotionPlugin.isFreeShipping")}</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Promotion.memberRanks")}:</label>
									<div class="col-xs-9 col-sm-10">
										[#list memberRanks as memberRank]
											<div class="checkbox checkbox-inline">
												<input id="memberRank_${memberRank.id}" name="memberRankIds" type="checkbox" value="${memberRank.id}"[#if promotion.memberRanks?seq_contains(memberRank)] checked[/#if]>
												<label for="memberRank_${memberRank.id}">${memberRank.name}</label>
											</div>
										[/#list]
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("common.setting")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="checkbox checkbox-inline">
											<input name="_isCouponAllowed" type="hidden" value="false">
											<input id="isCouponAllowed" name="isCouponAllowed" type="checkbox" value="true"[#if promotion.isCouponAllowed] checked[/#if]>
											<label for="isCouponAllowed">${message("Promotion.isCouponAllowed")}</label>
										</div>
										<div class="checkbox checkbox-inline">
											<input name="_isEnabled" type="hidden" value="false">
											<input id="isEnabled" name="isEnabled" type="checkbox" value="true"[#if promotion.isEnabled] checked[/#if]>
											<label for="isEnabled">${message("Promotion.isEnabled")}</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="order">${message("common.order")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="order" name="order" class="form-control" type="text" value="${promotion.order}" maxlength="9">
									</div>
								</div>
							</div>
							<div id="introduction" class="tab-pane">
								<textarea name="introduction" data-provide="editor">${promotion.introduction}</textarea>
							</div>
						</div>
					</div>
					<div class="panel-footer">
						<div class="row">
							<div class="col-xs-9 col-sm-10 col-xs-offset-3 col-sm-offset-2">
								<button class="btn btn-primary" type="submit">${message("common.submit")}</button>
								<button class="btn btn-default" type="button" data-action="back">${message("common.back")}</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</main>
</body>
</html>