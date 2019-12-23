<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("business.review.reply")} - Powered By </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/business/css/base.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/jquery.nicescroll.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
	<script src="${base}/resources/common/js/jquery.form.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/business/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
			
				var $reviewForm = $("#reviewForm");
				
				// 表单验证
				$reviewForm.validate({
					rules: {
						content: {
							required: true,
							maxlength: 200
						}
					},
					submitHandler: function(form) {
						$(form).ajaxSubmit({
							successRedirectUrl: "${base}/business/review/reply?reviewId=${review.id}"
						});
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
				<li class="active">${message("business.review.reply")}</li>
			</ol>
			<form id="reviewForm" class="ajax-form form-horizontal" action="${base}/business/review/reply" method="post">
				<input name="reviewId" type="hidden" value="${review.id}">
				<div class="panel panel-default">
					<div class="panel-heading">${message("business.review.reply")}</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-6">
								<dl class="items dl-horizontal clearfix">
									<dt>${message("Review.product")}:</dt>
									<dd>
										<a href="${base}${review.product.path}" target="_blank">${review.product.name}</a>
										[#if review.specifications?has_content]
											<span class="text-gray">[${review.specifications?join(", ")}]</span>
										[/#if]
									</dd>
									<dt>${message("Review.member")}:</dt>
									<dd>
										[#if review.member??]
											${review.member.username}
										[#else]
											${message("business.review.anonymous")}
										[/#if]
									</dd>
									<dt>${message("Review.content")}:</dt>
									<dd>${review.content}</dd>
								</dl>
							</div>
						</div>
						[#if review.replyReviews?has_content]
							[#list review.replyReviews as replyReview]
								<div class="form-group">
									<div class="col-xs-3 col-xs-offset-2">
										<p class="form-control-static">${replyReview.content}</p>
									</div>
									<div class="col-xs-2">
										<p class="form-control-static text-gray">${replyReview.createdDate?string("yyyy-MM-dd HH:mm:ss")}</p>
									</div>
								</div>
							[/#list]
						[/#if]
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label item-required" for="content">${message("Review.replyReviews")}:</label>
							<div class="col-xs-9 col-sm-4">
								<textarea id="content" name="content" class="form-control" rows="5"></textarea>
							</div>
						</div>
					</div>
					<div class="panel-footer">
						<div class="row">
							<div class="col-xs-9 col-sm-10 col-xs-offset-3 col-sm-offset-2">
								<button class="btn btn-primary" type="submit">${message("common.submit")}</button>
								<a class="btn btn-default" href="${base}/business/review/list">${message("common.back")}</a>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</main>
</body>
</html>