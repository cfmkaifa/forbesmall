<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("business.product.list")}  </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-select.css" rel="stylesheet">
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
	<script src="${base}/resources/common/js/bootbox.js"></script>
	<script src="${base}/resources/common/js/jquery.nicescroll.js"></script>
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
				
				var $delete = $("[data-action='delete']");
				var $shelves = $("#shelves");
				var $shelf = $("#shelf");
				var $ids = $("input[name='ids']");
				
				// 删除
				$delete.on("success.mall.delete", function() {
					$shelves.add($shelf).attr("disabled", true);
				});
				
				// 上架
				$shelves.click(function() {
					bootbox.confirm("${message("business.product.shelvesConfirm")}", function(result) {
						if (result == null || !result) {
							return;
						}
						
						$.post("${base}/business/product/shelves", $("input[name='ids']").serialize(), function() {
							location.reload(true);
						});
					});
				});
				
				// 下架
				$shelf.click(function() {
					bootbox.confirm("${message("business.product.shelfConfirm")}", function(result) {
						if (result == null || !result) {
							return;
						}
						
						$.post("${base}/business/product/shelf", $("input[name='ids']").serialize(), function() {
							location.reload(true);
						});
					});
				});
				
				// ID多选框
				$ids.change(function() {
					$shelves.add($shelf).attr("disabled", $("input[name='ids']:checked").length < 1);
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
				<li class="active">${message("business.product.list")}</li>
			</ol>
			<form action="${base}/business/product/list" method="get">
				<input name="pageSize" type="hidden" value="${page.pageSize}">
				<input name="searchProperty" type="hidden" value="${page.searchProperty}">
				<input name="orderProperty" type="hidden" value="${page.orderProperty}">
				<input name="orderDirection" type="hidden" value="${page.orderDirection}">
				<input name="isActive" type="hidden" value="[#if isActive??]${isActive?string("true", "false")}[/#if]">
				<input name="isMarketable" type="hidden" value="[#if isMarketable??]${isMarketable?string("true", "false")}[/#if]">
				<input name="isList" type="hidden" value="[#if isList??]${isList?string("true", "false")}[/#if]">
				<input name="isTop" type="hidden" value="[#if isTop??]${isTop?string("true", "false")}[/#if]">
				<input name="isOutOfStock" type="hidden" value="[#if isOutOfStock??]${isOutOfStock?string("true", "false")}[/#if]">
				<input name="isStockAlert" type="hidden" value="[#if isStockAlert??]${isStockAlert?string("true", "false")}[/#if]">
				<div id="filterModal" class="modal fade" tabindex="-1">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button class="close" type="button" data-dismiss="modal">&times;</button>
								<h5 class="modal-title">${message("common.moreOption")}</h5>
							</div>
							<div class="modal-body form-horizontal">
								<div class="form-group">
									<label class="col-xs-3 control-label">${message("Product.productCategory")}:</label>
									<div class="col-xs-9 col-sm-7">
										<select name="productCategoryId" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list productCategoryTree as productCategory]
												[#if allowedProductCategories?seq_contains(productCategory) || allowedProductCategoryParents?seq_contains(productCategory)]
													<option value="${productCategory.id}" title="${productCategory.name}"[#if productCategory.id == productCategoryId] selected[/#if][#if !allowedProductCategories?seq_contains(productCategory)] disabled[/#if]>
														[#if productCategory.grade != 0]
															[#list 1..productCategory.grade as i]
																&nbsp;&nbsp;
															[/#list]
														[/#if]
														${productCategory.name}
													</option>
												[/#if]
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">${message("Product.type")}:</label>
									<div class="col-xs-9 col-sm-7">
										<select name="type" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list types as item]
												<option value="${item}"[#if item == type] selected[/#if]>${message("Product.Type." + item)}</option>
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">${message("Product.brand")}:</label>
									<div class="col-xs-9 col-sm-7">
										<select name="brandId" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list brands as brand]
												<option value="${brand.id}"[#if brand.id == brandId] selected[/#if]>${brand.name}</option>
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">${message("Product.productTags")}:</label>
									<div class="col-xs-9 col-sm-7">
										<select name="productTagId" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list productTags as productTag]
												<option value="${productTag.id}"[#if productTag.id == productTagId] selected[/#if]>${productTag.name}</option>
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">${message("Product.storeProductTags")}:</label>
									<div class="col-xs-9 col-sm-7">
										<select name="storeProductTagId" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list storeProductTags as storeProductTag]
												<option value="${storeProductTag.id}"[#if storeProductTag.id == storeProductTagId] selected[/#if]>${storeProductTag.name}</option>
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">${message("Product.promotions")}:</label>
									<div class="col-xs-9 col-sm-7">
										<select name="promotionId" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list promotions as promotion]
												<option value="${promotion.id}"[#if promotion.id == promotionId] selected[/#if]>${promotion.name}</option>
											[/#list]
										</select>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-primary" type="submit">${message("common.ok")}</button>
								<button class="btn btn-default" type="button" data-dismiss="modal">${message("common.cancel")}</button>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-12 col-sm-9">
								<div class="btn-group">
									<a class="btn btn-default" href="${base}/business/product/add" data-redirect-url="${base}/business/product/list">
										<i class="iconfont icon-add"></i>
										${message("common.add")}
									</a>
									<button class="btn btn-default" type="button" data-action="delete" disabled>
										<i class="iconfont icon-close"></i>
										${message("common.delete")}
									</button>
									<button class="btn btn-default" type="button" data-action="refresh">
										<i class="iconfont icon-refresh"></i>
										${message("common.refresh")}
									</button>
									<button id="shelves" class="btn btn-default" type="button" disabled>
										<i class="iconfont icon-fold"></i>
										${message("business.product.shelves")}
									</button>
									<button id="shelf" class="btn btn-default" type="button" disabled>
										<i class="iconfont icon-unfold"></i>
										${message("business.product.shelf")}
									</button>
									<div class="btn-group">
										<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
											${message("business.product.filter")}
											<span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li[#if isActive?? && isActive] class="active"[/#if] data-filter-property="isActive" data-filter-value="true">
												<a href="javascript:;">${message("business.product.isActive")}</a>
											</li>
											<li[#if isActive?? && !isActive] class="active"[/#if] data-filter-property="isActive" data-filter-value="false">
												<a href="javascript:;">${message("business.product.notActive")}</a>
											</li>
											<li class="divider"></li>
											<li[#if isMarketable?? && isMarketable] class="active"[/#if] data-filter-property="isMarketable" data-filter-value="true">
												<a href="javascript:;">${message("business.product.isMarketable")}</a>
											</li>
											<li[#if isMarketable?? && !isMarketable] class="active"[/#if] data-filter-property="isMarketable" data-filter-value="false">
												<a href="javascript:;">${message("business.product.notMarketable")}</a>
											</li>
											<li class="divider"></li>
											<li[#if isList?? && isList] class="active"[/#if] data-filter-property="isList" data-filter-value="true">
												<a href="javascript:;">${message("business.product.isList")}</a>
											</li>
											<li[#if isList?? && !isList] class="active"[/#if] data-filter-property="isList" data-filter-value="false">
												<a href="javascript:;">${message("business.product.notList")}</a>
											</li>
											<li class="divider"></li>
											<li[#if isTop?? && isTop] class="active"[/#if] data-filter-property="isTop" data-filter-value="true">
												<a href="javascript:;">${message("business.product.isTop")}</a>
											</li>
											<li[#if isTop?? && !isTop] class="active"[/#if] data-filter-property="isTop" data-filter-value="false">
												<a href="javascript:;">${message("business.product.notTop")}</a>
											</li>
											<li class="divider"></li>
											<li[#if isOutOfStock?? && !isOutOfStock] class="active"[/#if] data-filter-property="isOutOfStock" data-filter-value="false">
												<a href="javascript:;">${message("business.product.isStack")}</a>
											</li>
											<li[#if isOutOfStock?? && isOutOfStock] class="active"[/#if] data-filter-property="isOutOfStock" data-filter-value="true">
												<a href="javascript:;">${message("business.product.isOutOfStack")}</a>
											</li>
											<li class="divider"></li>
											<li[#if isStockAlert?? && !isStockAlert] class="active"[/#if] data-filter-property="isStockAlert" data-filter-value="false">
												<a href="javascript:;">${message("business.product.normalStore")}</a>
											</li>
											<li[#if isStockAlert?? && isStockAlert] class="active"[/#if] data-filter-property="isStockAlert" data-filter-value="true">
												<a href="javascript:;">${message("business.product.isStockAlert")}</a>
											</li>
										</ul>
									</div>
									<button class="btn btn-default" type="button" data-toggle="modal" data-target="#filterModal">${message("common.moreOption")}</button>
									<div class="btn-group">
										<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
											${message("common.pageSize")}
											<span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li[#if page.pageSize == 10] class="active"[/#if] data-page-size="10">
												<a href="javascript:;">10</a>
											</li>
											<li[#if page.pageSize == 20] class="active"[/#if] data-page-size="20">
												<a href="javascript:;">20</a>
											</li>
											<li[#if page.pageSize == 50] class="active"[/#if] data-page-size="50">
												<a href="javascript:;">50</a>
											</li>
											<li[#if page.pageSize == 100] class="active"[/#if] data-page-size="100">
												<a href="javascript:;">100</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-3">
								<div id="search" class="input-group">
									<div class="input-group-btn">
										<button class="btn btn-default" type="button" data-toggle="dropdown">
											[#switch page.searchProperty]
												[#case "name"]
													<span>${message("Product.name")}</span>
													[#break /]
												[#case "productCategory.name"]
													<span>${message("Product.productCategory")}</span>
													[#break /]
												[#default]
													<span>${message("Product.sn")}</span>
											[/#switch]
											<span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li[#if !page.searchProperty?? || page.searchProperty == "sn"] class="active"[/#if] data-search-property="sn">
												<a href="javascript:;">${message("Product.sn")}</a>
											</li>
											<li[#if page.searchProperty == "name"] class="active"[/#if] data-search-property="name">
												<a href="javascript:;">${message("Product.name")}</a>
											</li>
											<li[#if page.searchProperty == "productCategory.name"] class="active"[/#if] data-search-property="productCategory.name">
												<a href="javascript:;">${message("Product.productCategory")}</a>
											</li>
										</ul>
									</div>
									<input name="searchValue" class="form-control" type="text" value="${page.searchValue}" placeholder="${message("common.search")}" x-webkit-speech="x-webkit-speech" x-webkit-grammar="builtin:search">
									<div class="input-group-btn">
										<button class="btn btn-default" type="submit">
											<i class="iconfont icon-search"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>
											<div class="checkbox">
												<input type="checkbox" data-toggle="checkAll">
												<label></label>
											</div>
										</th>
										<th>
											<a href="javascript:;" data-order-property="sn">
												${message("Product.sn")}
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="name">
												${message("Product.name")}
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="productCategory">
												${message("Product.productCategory")}
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="price">
												${message("Product.price")}
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="isMarketable">
												${message("Product.isMarketable")}
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="isActive">
												${message("Product.isActive")}
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>
											<a href="javascript:;" data-order-property="createdDate">
												${message("common.createdDate")}
												<i class="iconfont icon-biaotou-kepaixu"></i>
											</a>
										</th>
										<th>${message("common.action")}</th>
									</tr>
								</thead>
								[#if page.content?has_content]
									<tbody>
										[#list page.content as product]
											<tr>
												<td>
													<div class="checkbox">
														<input name="ids" type="checkbox" value="${product.id}">
														<label></label>
													</div>
												</td>
												<td>
													<span class="[#if product.isOutOfStock] text-red[#elseif product.isStockAlert] text-blue[/#if]">${product.sn}</span>
												</td>
												<td>
													${product.name}
													[#if product.type != "GENERAL"]
														<span class="text-red">*</span>
													[/#if]
													[#list product.validPromotions as promotion]
														<span class="promotion text-orange" title="${promotion.title}" data-toggle="tooltip">${promotion.name}</span>
													[/#list]
												</td>
												<td>${product.productCategory.name}</td>
												<td>${currency(product.price, true)}</td>
												<td>
													[#if product.isMarketable]
														<i class="text-green iconfont icon-check"></i>
													[#else]
														<i class="text-red iconfont icon-close"></i>
													[/#if]
												</td>
												<td>
													[#if product.isActive]
														<i class="text-green iconfont icon-check"></i>
													[#else]
														<i class="text-red iconfont icon-close"></i>
													[/#if]
												</td>
												<td>
													<span title="${product.createdDate?string("yyyy-MM-dd HH:mm:ss")}" data-toggle="tooltip">${product.createdDate}</span>
												</td>
												<td>
													<a class="btn btn-default btn-xs btn-icon" href="${base}/business/product/edit?productId=${product.id}" title="${message("common.edit")}" data-toggle="tooltip" data-redirect-url>
														<i class="iconfont icon-write"></i>
													</a>
													<a class="btn btn-default btn-xs btn-icon" href="${base}/business/group_purch_apply/index/${product.id}" title="${message("business.groupPurchApply.add")}" data-toggle="tooltip" data-redirect-url>
														<i class="iconfont icon-write"></i>
													</a>
													[#if product.isMarketable && product.isActive]
														<a class="btn btn-default btn-xs btn-icon" href="${base}${product.path}" title="${message("common.view")}" data-toggle="tooltip" target="_blank">
															<i class="iconfont icon-search"></i>
														</a>
													[#else]
														<button class="disabled btn btn-default btn-xs btn-icon" type="button" title="${message("business.product.notMarketable")}" data-toggle="tooltip">
															<i class="iconfont icon-search"></i>
														</button>
													[/#if]
												</td>
											</tr>
										[/#list]
									</tbody>
								[/#if]
							</table>
							[#if !page.content?has_content]
								<p class="no-result">${message("common.noResult")}</p>
							[/#if]
						</div>
					</div>
					[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
						[#if totalPages > 1]
							<div class="panel-footer text-right">
								[#include "/business/include/pagination.ftl" /]
							</div>
						[/#if]
					[/@pagination]
				</div>
			</form>
		</div>
	</main>
</body>
</html>