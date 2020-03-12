<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("member.mainSidebar.proPurchApplyList")}  </title>
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
				<form action="${base}/member/pro_purch_apply/list" method="get">
					<input name="pageNumber" type="hidden" value="${page.pageNumber}">
					<div class="panel panel-default">
						<div class="panel-heading">${message("member.mainSidebar.proPurchApplyList")}</div>
						<div class="panel-body">
							[#if page.content?has_content]
								<table class="table">
									<thead>
									<tr>
										<th>
											${message("Product.member")}
										</th>
										<th>
											${message("Product.name")}
										</th>
										<th>
											${message("GroupPurchApply.spec")}
										</th>
										<th>
											${message("ProPurchApply.startPrice")}
										</th>
										<th>
											${message("ProPurchApply.endPrice")}
										</th>
										<th>
											${message("GroupPurchApply.startTime")}
										</th>
										<th>
											${message("GroupPurchApply.endTime")}
										</th>
										<th>
											${message("CategoryApplication.status")}
										</th>
									</tr>
									</thead>
									<tbody>
									[#list page.content as groupPurchApply]
										<tr>
											<td>
												${groupPurchApply.membeName}
											</td>
											<td>
												${groupPurchApply.proName}
											</td>
											<td>
												[#if groupPurchApply.sku.specifications?has_content]
													<span class="text-gray">[${ groupPurchApply.sku.specifications?join(", ")}]</span>
												[/#if]
											</td>
											<td>${groupPurchApply.startPrice}</td>
											<td>${groupPurchApply.endPrice}</td>
											<td>${groupPurchApply.startTime}</td>
											<td>${groupPurchApply.endTime}</td>
											<td>
												<span class="[#if groupPurchApply.status == "PENDING"]text-orange[#elseif groupPurchApply.status == "FAILED"]text-red[#else]text-green[/#if]">${message("CategoryApplication.Status." + groupPurchApply.status)}</span>
											</td>
										</tr>
									[/#list]
									</tbody>
								</table>
							[#else]
								<p class="text-gray">${message("common.noResult")}</p>
							[/#if]
						</div>
						[@pagination pageNumber = page.pageNumber totalPages = page.totalPages]
							[#if totalPages > 1]
								<div class="panel-footer text-right clearfix">
									[#include "/member/include/pagination.ftl" /]
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