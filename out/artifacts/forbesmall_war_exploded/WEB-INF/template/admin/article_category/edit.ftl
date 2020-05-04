<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("admin.articleCategory.edit")}  </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-fileinput.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-select.css" rel="stylesheet">
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
	<script src="${base}/resources/common/js/bootstrap-fileinput.js?version=0.5"></script>
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
				var $articleCategoryForm = $("#articleCategoryForm");
				var $thumbnailFile = $("#thumbnailFile");
				// 缩略图
				$thumbnailFile.fileinput({
						uploadUrl: "${base}/common/file/upload",
						uploadExtraData: {
							fileType: "FILE"
						},
						allowedFileExtensions: "${setting.uploadImageExtension}".split(","),
						[#if setting.uploadMaxSize != 0]
							maxFileSize: ${setting.uploadMaxSize} * 1024,
						[/#if]
						maxFileCount: 1,
						autoReplace: true,
						showPreview:true,
						dropZoneEnabled: false,
						overwriteInitial: false,
						initialPreviewAsData: true,
						previewClass: "multiple-file-preview",
						initialPreviewFileType:"image",
						[#if articleCategory.thumbnail?has_content]
							initialPreview:"${articleCategory.thumbnail}",
						[/#if]
						layoutTemplates: {
							footer: '<div class="file-thumbnail-footer">{actions}</div>',
							actions: '<div class="file-actions"><div class="file-footer-buttons">{upload} {download} {delete} {zoom} {other}</div>{drag}<div class="clearfix"></div></div>'
						},
						removeFromPreviewOnError: true,
						showAjaxErrorDetails: false
					}).on("fileloaded", function(event, file, previewId, index, reader) {
					}).on("fileuploaded", function(event, data, previewId, index) {
					     $("#thumbnailPath").val(data.response.url);
					}).on("filecleared fileerror fileuploaderror", function() {
				});
				// 表单验证
				$articleCategoryForm.validate({
					rules: {
						name: "required",
						order: "digits",
						"timeout": {
							digits: true
						},
						"weekSubFee": {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},"monthSubFee": {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},"quarterSubFee": {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},"yearSubFee": {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
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
				<li class="active">${message("admin.articleCategory.edit")}</li>
			</ol>
			<form id="articleCategoryForm" class="ajax-form form-horizontal" action="${base}/admin/article_category/update" method="post">
				<input name="id" type="hidden" value="${articleCategory.id}">
				<div class="panel panel-default">
					<div class="panel-heading">${message("admin.articleCategory.edit")}</div>
					<div class="panel-body">
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label item-required" for="name">${message("ArticleCategory.name")}:</label>
							<div class="col-xs-9 col-sm-4">
								<input id="name" name="name" class="form-control" type="text" value="${articleCategory.name}" maxlength="200">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label">${message("ArticleCategory.parent")}:</label>
							<div class="col-xs-9 col-sm-4">
								<select name="parentId" class="selectpicker form-control" data-size="10">
									<option value="">${message("admin.articleCategory.root")}</option>
									[#list articleCategoryTree as category]
										[#if category != articleCategory && !children?seq_contains(category)]
											<option value="${category.id}" title="${category.name}" [#if category == articleCategory.parent] selected[/#if]>
												[#if category.grade != 0]
													[#list 1..category.grade as i] &nbsp;&nbsp; [/#list]
												[/#if]
												${category.name}
											</option>
										[/#if]
									[/#list]
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label">${message("ArticleCategory.subscribe")}:</label>
							<div class="col-xs-9 col-sm-4">
								<select name="subscribe" class="selectpicker form-control" data-live-search="true" data-size="10">
									<option value="NO" title="NO" [#if articleCategory.subscribe == "NO" ] selected[/#if] >NO</option>
									<option value="YES" title="YES" [#if articleCategory.subscribe == "YES" ] selected[/#if]>YES</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label">${message("ArticleCategory.type")}:</label>
							<div class="col-xs-9 col-sm-4">
								<select name="type" class="selectpicker form-control" data-live-search="true" data-size="10">
									<option value="NEWS" title="${message("ArticleCategory.news")}" [#if articleCategory.type == "NEWS" ] selected[/#if] >${message("ArticleCategory.news")}</option>
									<option value="INST" title="${message("ArticleCategory.inst")}" [#if articleCategory.type == "INST" ] selected[/#if] >${message("ArticleCategory.inst")}</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="timeout">${message("ArticleCategory.timeout")}:</label>
							<div class="col-xs-9 col-sm-4"  data-toggle="tooltip">
								<div class="input-group">
									<input id="timeout" name="timeout" class="form-control" type="text" maxlength="9" value="${articleCategory.timeout}" >
									<span class="input-group-addon">${message("common.unit.day")}</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="timeout">${message("ArticleCategory.thumbnail")}:</label>
							<div class="col-xs-9 col-sm-4" >
								<input id="thumbnailFile" name="file" type="file"  class="form-control" maxlength="200">
								<input id = "thumbnailPath" name="thumbnail" class="form-control" type="hidden" maxlength="200" value="${articleCategory.thumbnail}" >
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="timeout">${message("ArticleCategory.weekSubFee")}:</label>
							<div class="col-xs-9 col-sm-4"  data-toggle="tooltip">
								<div class="input-group">
									<input id="weekSubFee" name="weekSubFee" class="form-control" type="text" maxlength="9" value="${articleCategory.weekSubFee}" >
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="timeout">${message("ArticleCategory.monthSubFee")}:</label>
							<div class="col-xs-9 col-sm-4"  data-toggle="tooltip">
								<div class="input-group">
									<input id="monthSubFee" name="monthSubFee" class="form-control" type="text" maxlength="9" value="${articleCategory.monthSubFee}">
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="timeout">${message("ArticleCategory.quarterSubFee")}:</label>
							<div class="col-xs-9 col-sm-4"  data-toggle="tooltip">
								<div class="input-group">
									<input id="quarterSubFee" name="quarterSubFee" class="form-control" type="text" maxlength="9" value="${articleCategory.quarterSubFee}" >
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="timeout">${message("ArticleCategory.yearSubFee")}:</label>
							<div class="col-xs-9 col-sm-4"  data-toggle="tooltip">
								<div class="input-group">
									<input id="yearSubFee" name="yearSubFee" class="form-control" type="text" maxlength="9" value="${articleCategory.yearSubFee}" >
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="seoTitle">${message("ArticleCategory.seoTitle")}:</label>
							<div class="col-xs-9 col-sm-4">
								<input id="seoTitle" name="seoTitle" class="form-control" type="text" value="${articleCategory.seoTitle}" maxlength="200">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="seoKeywords">${message("ArticleCategory.seoKeywords")}:</label>
							<div class="col-xs-9 col-sm-4">
								<input id="seoKeywords" name="seoKeywords" class="form-control" type="text" value="${articleCategory.seoKeywords}" maxlength="200">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="seoDescription">${message("ArticleCategory.seoDescription")}:</label>
							<div class="col-xs-9 col-sm-4">
								<input id="seoDescription" name="seoDescription" class="form-control" type="text" value="${articleCategory.seoDescription}" maxlength="200">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 col-sm-2 control-label" for="order">${message("common.order")}:</label>
							<div class="col-xs-9 col-sm-4">
								<input id="order" name="order" class="form-control" type="text" value="${articleCategory.order}" maxlength="9">
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