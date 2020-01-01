<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("member.memberDeposit.recharge")} - Powered By </title>
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
	<script src="${base}/resources/common/js/jquery.validate.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
	<script src="${base}/resources/common/js/jquery.form.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js?version=0.1"></script>
	<script src="${base}/resources/member/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
				
				var $payConfirmModal = $("#payConfirmModal");
				var $depositForm = $("#depositForm");
				var $paymentPluginId = $("#paymentPluginId");
				var $rechargeAmount = $("#rechargeAmount");
				var $feeItem = $("#feeItem");
				var $fee = $("#fee");
				var $paymentPluginItem = $("#paymentPlugin div.media");
				
				// 充值金额
				$rechargeAmount.on("input propertychange change", function(event) {
					if (event.type != "propertychange" || event.originalEvent.propertyName == "value") {
						calculate();
					}
				});
				
				// 支付插件
				$paymentPluginItem.click(function() {
					var $element = $(this);
					var paymentPluginId = $element.data("payment-plugin-id");
					
					$element.addClass("active").siblings().removeClass("active");
					$paymentPluginId.val(paymentPluginId);
					calculate();
				});
				
				// 计算支付手续费
				var calculate = _.debounce(function() {
					if (!$.isNumeric($rechargeAmount.val()) || !$depositForm.validate().element($rechargeAmount)) {
						if ($feeItem.is(":visible")) {
							$feeItem.velocity("slideUp");
						}
						return;
					}
					$.ajax({
						url: "${base}/member/member_deposit/calculate_fee",
						type: "POST",
						data: {
							paymentPluginId: $paymentPluginId.val(),
							rechargeAmount: $rechargeAmount.val()
						},
						dataType: "json",
						cache: false,
						success: function(data) {
							if (data.fee > 0) {
								$fee.text($.currency(data.fee, true, true));
								if ($feeItem.is(":hidden")) {
									$feeItem.velocity("slideDown");
								}
							} else {
								if ($feeItem.is(":visible")) {
									$feeItem.velocity("slideUp");
								}
							}
						}
					});
				}, 200);
				
				// 检查余额
				setInterval(function() {
					$.ajax({
						url: "${base}/member/member_deposit/check_balance",
						type: "POST",
						dataType: "json",
						cache: false,
						success: function(data) {
							if (data.balance > ${currentUser.balance}) {
								location.href = "${base}/member/member_deposit/log";
							}
						}
					});
				}, 10000);
				
				// 表单验证
				$depositForm.validate({
					rules: {
						"paymentItemList[0].amount": {
							required: true,
							positive: true,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						}
					},
					submitHandler: function(form) {
						$payConfirmModal.modal({
							backdrop: "static",
							keyboard: false
						}).modal("show");
						form.submit();
					}
				});
			
			});
			</script>
		[/#escape]
	[/#noautoesc]
</head>
<body class="member">
	[#include "/shop/include/main_header.ftl" /]
	<main>
		<div class="container">
			<div id="payConfirmModal" class="pay-confirm-modal modal fade" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">${message("PayConfirmModal.payConfirm")}</h5>
						</div>
						<div class="modal-body">
							<p>
								<i class="iconfont icon-warn"></i>
								[#noautoesc]${message("PayConfirmModal.payPrimary")}[/#noautoesc]
							</p>
						</div>
						<div class="modal-footer">
							<a class="btn btn-primary" href="${base}/member/member_deposit/log">${message("PayConfirmModal.complete")}</a>
							<a class="btn btn-default" href="${base}/">${message("PayConfirmModal.problem")}</a>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-2">
					[#include "/member/include/main_menu.ftl" /]
				</div>
				<div class="col-xs-10">
					<form id="depositForm" class="form-horizontal" action="${base}/payment" method="post" target="_blank">
						<input id="paymentPluginId" name="paymentPluginId" type="hidden" value="${defaultPaymentPlugin.id}">
						<input name="paymentItemList[0].type" type="hidden" value="DEPOSIT_RECHARGE">
						<input name="rePayUrl" type="hidden" value="${base}/member/member_deposit/recharge">
						<div class="panel panel-default">
							<div class="panel-heading">${message("member.memberDeposit.recharge")}</div>
							<div class="panel-body">
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Member.availableBalance")}:</label>
									<div class="col-xs-9 col-sm-4">
										<p class="form-control-static text-red">${currency(currentUser.availableBalance, true, true)}</p>
									</div>
								</div>
								[#if currentUser.frozenAmount > 0]
									<div class="form-group">
										<label class="col-xs-3 col-sm-2 control-label">${message("Member.frozenAmount")}:</label>
										<div class="col-xs-9 col-sm-4">
											<p class="form-control-static text-gray">${currency(currentUser.frozenAmount, true, true)}</p>
										</div>
									</div>
								[/#if]
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="rechargeAmount">${message("member.memberDeposit.recharge")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="rechargeAmount" name="paymentItemList[0].amount" class="form-control" type="text" maxlength="16">
									</div>
								</div>
								<div id="feeItem" class="hidden-element form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("member.memberDeposit.fee")}:</label>
									<div class="col-xs-9 col-sm-4">
										<p id="fee" class="form-control-static text-red"></p>
									</div>
								</div>
								[#if paymentPlugins?has_content]
									<div id="paymentPlugin" class="payment-plugin">
										<div class="payment-plugin-heading">${message("common.paymentPlugin")}</div>
										<div class="payment-plugin-body clearfix">
											[#list paymentPlugins as paymentPlugin]
												<div class="media[#if paymentPlugin == defaultPaymentPlugin] active[/#if]" data-payment-plugin-id="${paymentPlugin.id}">
													<div class="media-left media-middle">
														<i class="iconfont icon-roundcheck"></i>
													</div>
													<div class="media-body media-middle">
														<div class="media-object">
															[#if paymentPlugin.logo?has_content]
																<img src="${paymentPlugin.logo}" alt="${paymentPlugin.displayName}">
															[#else]
																${paymentPlugin.displayName}
															[/#if]
														</div>
													</div>
												</div>
											[/#list]
										</div>
									</div>
								[/#if]
							</div>
							<div class="panel-footer">
								<div class="row">
									<div class="col-xs-9 col-sm-10 col-xs-offset-3 col-sm-offset-2">
										<button class="btn btn-primary" type="submit">${message("common.submit")}</button>
										<a class="btn btn-default" href="${base}/member/index">${message("common.back")}</a>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>
	[#include "/shop/include/main_footer.ftl" /]
</body>
</html>