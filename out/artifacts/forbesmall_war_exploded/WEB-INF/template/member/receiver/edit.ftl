<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("member.receiver.edit")}  </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
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
	<script src="${base}/resources/common/js/jquery.lSelect.js"></script>
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
				
				var $receiverForm = $("#receiverForm");
				var $areaId = $("#areaId");
				
				// 地区选择
				$areaId.lSelect({
					url: "${base}/common/area"
				});
				
				$.validator.addMethod("requiredOne",
					function(value, element, param) {
						return $.trim(value) != "" || $.trim($(param).val()) != "";
					},
					"${message("member.receiver.requiredOne")}"
				);
				
				// 表单验证
				$receiverForm.validate({
					rules: {
						consignee: "required",
						areaId: "required",
						address: "required",
						zipCode: {
							required: true,
							zipCode: true
						},
						phone: {
							required: true,
							phone: true
						}
					},
					submitHandler: function(form) {
						$(form).ajaxSubmit({
							successRedirectUrl: "${base}/member/receiver/list"
						});
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
			<div class="row">
				<div class="col-xs-2">
					[#include "/member/include/main_menu.ftl" /]
				</div>
				<div class="col-xs-10">
					<form id="receiverForm" class="form-horizontal" action="${base}/member/receiver/update" method="post">
						<div class="panel panel-default">
							<div class="panel-heading">${message("member.receiver.edit")}</div>
							<div class="panel-body">
								<input name="receiverId" type="hidden" value="${receiver.id}">
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required">${message("Receiver.consignee")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input name="consignee" class="form-control" type="text" value="${receiver.consignee}" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required">${message("Receiver.area")}:</label>
									<div class="col-xs-9 col-sm-10">
										<input id="areaId" name="areaId" type="hidden" value="${(receiver.area.id)!}" treePath="${(receiver.area.treePath)!}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required">${message("Receiver.address")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input name="address" class="form-control" type="text" value="${receiver.address}" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required">${message("Receiver.zipCode")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input name="zipCode" class="form-control" type="text" value="${receiver.zipCode}" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required">${message("Receiver.phone")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input name="phone" class="form-control" type="text" value="${receiver.phone}" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("common.setting")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="checkbox">
											<input name="_isDefault" type="hidden" value="false">
											<input id="isDefault" name="isDefault" type="checkbox" value="true"[#if receiver.isDefault] checked[/#if]>
											<label for="isDefault">${message("Receiver.isDefault")}</label>
										</div>
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
			</div>
		</div>
	</main>
	[#include "/shop/include/main_footer.ftl" /]
</body>
</html>