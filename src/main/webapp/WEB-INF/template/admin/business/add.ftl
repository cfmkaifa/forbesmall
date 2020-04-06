<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("admin.business.add")}  </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-select.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-datetimepicker.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-fileinput.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/admin/css/base.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/bootstrap-select.js"></script>
	<script src="${base}/resources/common/js/moment.js"></script>
	<script src="${base}/resources/common/js/bootstrap-datetimepicker.js"></script>
	<script src="${base}/resources/common/js/bootstrap-fileinput.js"></script>
	<script src="${base}/resources/common/js/jquery.nicescroll.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.js"></script>
	<script src="${base}/resources/common/js/jquery.validate.additional.js"></script>
	<script src="${base}/resources/common/js/jquery.form.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/base.js?version=0.1"></script>
	<script src="${base}/resources/admin/js/base.js"></script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
			
				var $businessForm = $("#businessForm");
				
				// 表单验证
				$businessForm.validate({
					rules: {
						username: {
							required: true,
							minlength: 4,
							username: true,
							notAllNumber: true,
							remote: {
								url: "${base}/admin/business/check_username",
								cache: false
							}
						},
						password: {
							required: true,
							minlength: 4,
							normalizer: function(value) {
								return value;
							}
						},
						rePassword: {
							required: true,
							equalTo: "#password",
							normalizer: function(value) {
								return value;
							}
						},
						email: {
							required: true,
							email: true,
							remote: {
								url: "${base}/admin/business/check_email",
								cache: false
							}
						},
						mobile: {
							required: true,
							mobile: true,
							remote: {
								url: "${base}/admin/business/check_mobile",
								cache: false
							}
						}
						[#list businessAttributes as businessAttribute]
							[#if businessAttribute.isRequired || businessAttribute.pattern?has_content]
								,"businessAttribute_${businessAttribute.id}": {
									[#if businessAttribute.isRequired]
										required: true
										[#if businessAttribute.pattern?has_content],[/#if]
									[/#if]
									[#if businessAttribute.pattern?has_content]
										pattern: new RegExp("${businessAttribute.pattern}")
									[/#if]
								}
							[/#if]
						[/#list]
					},
					messages: {
						username: {
							remote: "${message("common.validator.exist")}"
						},
						email: {
							remote: "${message("common.validator.exist")}"
						},
						mobile: {
							remote: "${message("common.validator.exist")}"
						}
					}
				});
			
			});
			</script>
		[/#escape]
	[/#noautoesc]
</head>
<body class="admin">
	[#include "/admin/include/main_header.ftl" /]
	[#include "/admin/include/main_sidebar.ftl" /]
	<main>
		<div class="container-fluid">
			<ol class="breadcrumb">
				<li>
					<a href="${base}/admin/index">
						<i class="iconfont icon-homefill"></i>
						${message("common.breadcrumb.index")}
					</a>
				</li>
				<li class="active">${message("admin.business.add")}</li>
			</ol>
			<form id="businessForm" class="ajax-form form-horizontal" action="${base}/admin/business/save" method="post">
				<div class="panel panel-default">
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#base" data-toggle="tab">${message("admin.business.base")}</a>
							</li>
							[#if businessAttributes?has_content]
								<li>
									<a href="#profile" data-toggle="tab">${message("admin.business.profile")}</a>
								</li>
							[/#if]
						</ul>
						<div class="tab-content">
							<div id="base" class="tab-pane active">
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="username">${message("Business.username")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="username" name="username" class="form-control" type="text" maxlength="20">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="password">${message("Business.password")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="password" name="password" class="form-control" type="password" maxlength="20" autocomplete="off">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="rePassword">${message("admin.business.rePassword")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="rePassword" name="rePassword" class="form-control" type="password" maxlength="20" autocomplete="off">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="email">${message("Business.email")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="email" name="email" class="form-control" type="text" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="mobile">${message("Business.mobile")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="mobile" name="mobile" class="form-control" type="text" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("common.setting")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="checkbox">
											<input name="_isEnabled" type="hidden" value="false">
											<input id="isEnabled" name="isEnabled" type="checkbox" value="true" checked>
											<label for="isEnabled">${message("User.isEnabled")}</label>
										</div>
									</div>
								</div>
							</div>
							[#if businessAttributes?has_content]
								<div id="profile" class="tab-pane">
									[#list businessAttributes as businessAttribute]
										<div class="form-group">
											<label class="col-xs-3 col-sm-2 control-label[#if businessAttribute.isRequired] item-required[/#if]" for="businessAttribute_${businessAttribute.id}">${businessAttribute.name}:</label>
											[#if businessAttribute.type == "TEXT" || businessAttribute.type == "NAME" || businessAttribute.type == "LICENSE_NUMBER" || businessAttribute.type == "LEGAL_PERSON" || businessAttribute.type == "ID_CARD" || businessAttribute.type == "PHONE" || businessAttribute.type == "ORGANIZATION_CODE" || businessAttribute.type == "IDENTIFICATION_NUMBER" || businessAttribute.type == "BANK_NAME" || businessAttribute.type == "BANK_ACCOUNT" ||  businessAttribute.type == "BANK_ADDRESS"  || businessAttribute.type == "ADDRESS"|| businessAttribute.type == "ZIP_CODE"]
												<div class="col-xs-9 col-sm-4">
													<input id="businessAttribute_${businessAttribute.id}" name="businessAttribute_${businessAttribute.id}" class="form-control" type="text" maxlength="200">
												</div>
											[#elseif businessAttribute.type == "IMAGE" || businessAttribute.type == "LICENSE_IMAGE" || businessAttribute.type == "ID_CARD_IMAGE" || businessAttribute.type == "ORGANIZATION_IMAGE" || businessAttribute.type == "TAX_IMAGE"]
												<div class="col-xs-9 col-sm-4">
													<input name="businessAttribute_${businessAttribute.id}" type="hidden" data-provide="fileinput" data-file-type="IMAGE">
												</div>
											[#elseif businessAttribute.type == "SELECT"]
												<div class="col-xs-9 col-sm-4">
													<select id="businessAttribute_${businessAttribute.id}" name="businessAttribute_${businessAttribute.id}" class="selectpicker form-control" data-size="5">
														<option value="">${message("common.choose")}</option>
														[#list businessAttribute.options as option]
															<option value="${option}">${option}</option>
														[/#list]
													</select>
												</div>
											[#elseif businessAttribute.type == "CHECKBOX"]
												<div class="col-xs-9 col-sm-10">
													[#list businessAttribute.options as option]
														<div class="checkbox checkbox-inline">
															<input id="${option}_${businessAttribute.id}" name="businessAttribute_${businessAttribute.id}" type="checkbox" value="${option}">
															<label for="${option}_${businessAttribute.id}">${option}</label>
														</div>
													[/#list]
												</div>
											[#elseif businessAttribute.type == "DATE"]
												<div class="col-xs-9 col-sm-4">
													<div class="input-group">
														<input name="businessAttribute_${businessAttribute.id}" class="form-control" type="text" data-provide="datetimepicker">
														<span class="input-group-addon">
															<i class="iconfont icon-calendar"></i>
														</span>
													</div>
												</div>
											[/#if]
										</div>
									[/#list]
								</div>
							[/#if]
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