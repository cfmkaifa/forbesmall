<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("admin.product.list")}  </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/shop/css/base.css" rel="stylesheet">
	<link href="${base}/resources/member/css/base.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js?version=0.1"></script>
	<script src="${base}/resources/member/js/base.js"></script>
	<script src="${base}/resources/admin/js/base.js"></script>
</head>
<body class="member">
[#include "/shop/include/main_header.ftl" /]
<main>
	<div class="container">
		<div class="row">
			<div class="col-xs-2">
				[#include "/member/include/main_menu.ftl" /]
			</div>
			<div class="col-xs-10">
				<form action="${base}/member/product/list" method="get">
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
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-12 col-sm-3">
										<div id="search" class="input-group">
											<div class="input-group-btn">
												<button class="btn btn-default" type="button" data-toggle="dropdown">
													[#switch page.searchProperty]
														[#case "name"]
															<span>${message("Product.name")}</span>
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
												<a href="javascript:;" data-order-property="store.name">
													${message("Product.store")}
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
														<span[#if product.isOutOfStock] class="text-red"[#elseif product.isStockAlert] class="text-blue"[/#if]>${product.sn}</span>
													</td>
													<td>
														${product.name}
														[#if product.type != "GENERAL"]
															<span class="text-red">*</span>
														[/#if]
														[#list product.validPromotions as promotion]
															<span class="promotion" title="${promotion.title}" data-toggle="tooltip">${promotion.name}</span>
														[/#list]
													</td>
													<td>
														[#if !product.productCategory?has_content ]
															${product.productCategory.name}
														[/#if]

													</td>
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
													<td>${product.store.name}</td>
													<td>
														<span title="${product.createdDate?string("yyyy-MM-dd HH:mm:ss")}" data-toggle="tooltip">${product.createdDate}</span>
													</td>
													<td>
														[#if product.isMarketable && product.isActive]
															<a class="btn btn-default btn-xs btn-icon" href="${base}/member/pro_purch_apply/index/${product.id}" title="${message("member.mainMenu.proApply")}" data-toggle="tooltip" data-redirect-url>
																<i class="iconfont icon-write"></i>
															</a>
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
							<div class="panel-footer text-right clearfix">
								[#include "/admin/include/pagination.ftl" /]
							</div>
						[/#if]
					[/@pagination]
			</div>
		</form>
		</div>
		</div>
		</div>
	</main>
	[#include "/shop/include/main_footer.ftl" /]
</body>
</html>