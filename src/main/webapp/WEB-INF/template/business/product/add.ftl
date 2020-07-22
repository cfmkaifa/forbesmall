<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("business.product.add")}  </title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
	<link href="${base}/resources/common/css/bootstrap-select.css" rel="stylesheet">
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
	<script src="${base}/resources/common/js/bootbox.js"></script>
	<script src="${base}/resources/common/js/bootstrap-select.js"></script>
	<script src="${base}/resources/common/js/sortable.js"></script>
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
	<style>
		.table tbody tr td {
			vertical-align: top;
		}
	</style>
	<script id="productImageInputTemplate" type="text/template">
		<input name="productImages[<%-productImageIndex%>].source" type="hidden" value="<%-productImage.source%>">
		<input name="productImages[<%-productImageIndex%>].large" type="hidden" value="<%-productImage.large%>">
		<input name="productImages[<%-productImageIndex%>].medium" type="hidden" value="<%-productImage.medium%>">
		<input name="productImages[<%-productImageIndex%>].thumbnail" type="hidden" value="<%-productImage.thumbnail%>">
		<input name="productImages[<%-productImageIndex%>].order" type="hidden" value="<%-productImage.order%>">
	</script>
	<script id="addParameterGroupTemplate" type="text/template">
		<div class="item" data-parameter-index="<%-parameterIndex%>">
			<div class="form-group">
				<div class="col-xs-3 col-sm-1 col-sm-offset-1 text-right">
					<p class="form-control-static">${message("Parameter.group")}:</p>
				</div>
				<div class="col-xs-6 col-sm-4">
					<input name="parameterValues[<%-parameterIndex%>].group" class="parameter-group form-control" type="text" maxlength="200">
				</div>
				<div class="col-xs-3 col-sm-6">
					<p class="form-control-static">
						<a class="remove group" href="javascript:;">[${message("common.delete")}]</a>
						<a class="add" href="javascript:;">[${message("common.add")}]</a>
					</p>
				</div>
			</div>
			<div class="form-group">
				<div class="col-xs-3 col-sm-1 col-sm-offset-1">
					<input name="parameterValues[<%-parameterIndex%>].entries[<%-parameterEntryIndex%>].name" class="parameter-entry-name form-control text-right" type="text" maxlength="200">
				</div>
				<div class="col-xs-6 col-sm-4">
					<input name="parameterValues[<%-parameterIndex%>].entries[<%-parameterEntryIndex%>].value" class="parameter-entry-value form-control" type="text" maxlength="200">
				</div>
				<div class="col-xs-3 col-sm-6">
					<p class="form-control-static">
						<a class="remove" href="javascript:;">[${message("common.delete")}]</a>
					</p>
				</div>
			</div>
		</div>
	</script>
	<script id="addParameterTemplate" type="text/template">
		<div class="form-group">
			<div class="col-xs-3 col-sm-1 col-sm-offset-1">
				<input name="parameterValues[<%-parameterIndex%>].entries[<%-parameterEntryIndex%>].name" class="parameter-entry-name form-control text-right" type="text" maxlength="200">
			</div>
			<div class="col-xs-6 col-sm-4">
				<input name="parameterValues[<%-parameterIndex%>].entries[<%-parameterEntryIndex%>].value" class="parameter-entry-value form-control" type="text" maxlength="200">
			</div>
			<div class="col-xs-3 col-sm-6">
				<p class="form-control-static">
					<a class="remove" href="javascript:;">[${message("common.delete")}]</a>
				</p>
			</div>
		</div>
	</script>
	<script id="parameterGroupTemplate" type="text/template">
		<%_.each(parameters, function(parameter, i) {%>
			<div class="item" data-parameter-index="<%-parameterIndex%>">
				<div class="form-group">
					<div class="col-xs-3 col-sm-1 col-sm-offset-1 text-right">
						<p class="form-control-static">${message("Parameter.group")}:</p>
					</div>
					<div class="col-xs-6 col-sm-4">
						<input name="parameterValues[<%-parameterIndex%>].group" class="parameter-group form-control" type="text" value="<%-parameter.group%>" maxlength="200">
					</div>
					<div class="col-xs-3 col-sm-6">
						<p class="form-control-static">
							<a class="remove group" href="javascript:;">[${message("common.delete")}]</a>
							<a class="add" href="javascript:;">[${message("common.add")}]</a>
						</p>
					</div>
				</div>
				<% var parameterEntryIndex = 0 %>
				<%_.each(parameter.names, function(name, i) {%>
					<div class="form-group">
						<div class="col-xs-3 col-sm-1 col-sm-offset-1">
							<input name="parameterValues[<%-parameterIndex%>].entries[<%-parameterEntryIndex%>].name" class="parameter-entry-name form-control text-right" type="text" value="<%-name%>" maxlength="200">
						</div>
						<div class="col-xs-6 col-sm-4">
							<input name="parameterValues[<%-parameterIndex%>].entries[<%-parameterEntryIndex%>].value" class="parameter-entry-value form-control" type="text" maxlength="200">
						</div>
						<div class="col-xs-3 col-sm-6">
							<p class="form-control-static">
								<a class="remove" href="javascript:;">[${message("common.delete")}]</a>
							</p>
						</div>
					</div>
					<% parameterEntryIndex ++ %>
				<%});%>
				<% parameterIndex ++ %>
			</div>
		<%});%>
	</script>
	<script id="attributeTemplate" type="text/template">
		<%_.each(attributes, function(attribute, i) {%>
			<div class="form-group">
				<label class="col-xs-3 col-sm-2 control-label"><%-attribute.name%>:</label>
				<div class="col-xs-9 col-sm-4">
					<select name="attribute_<%-attribute.id%>" class="form-control">
						<option value="">${message("common.choose")}</option>
						<%_.each(attribute.options, function(option, i) {%>
							<option value="<%-option%>"><%-option%></option>
						<%});%>
					</select>
				</div>
			</div>
		<%});%>
	</script>
	<script id="specificationTemplate" type="text/template">
		<%_.each(specifications, function(specification, i) {%>
			<div class="specification-item form-group">
				<div class="col-xs-4 col-sm-2" style="margin: 5px 0px;">
					<input name="specificationItems[<%-i%>].name" class="specification-item-name form-control text-right" type="text" value="<%-specification.name%>" data-value=<%-specification.name%>>
				</div>
				<div class="col-xs-8 col-sm-10">
					<div class="row">
						<%_.each(specification.options, function(option, j) {%>
							<div class="col-xs-10 col-sm-2" style="margin: 5px 0px;">
								<div class="input-group">
									<span class="input-group-addon">
										<div class="checkbox">
											<input name="specificationItems[<%-i%>].entries[<%-j%>].isSelected" class="specification-item-check" type="checkbox" value="true">
											<label></label>
										</div>
									</span>
									<input name="specificationItems[<%-i%>].entries[<%-j%>].value" class="specification-item-entry-value form-control" type="text" value="<%-option%>" data-value=<%-specification.name%>>
									<input name="_specificationItems[<%-i%>].entries[<%-j%>].isSelected" type="hidden" value="false">
									<input name="specificationItems[<%-i%>].entries[<%-j%>].id" class="specification-item-entry-id" type="hidden" value="<%-_.uniqueId()%>">
								</div>
							</div>
						<%});%>
					</div>
				</div>
			</div>
		<%});%>
	</script>
	<script id="skuTemplate" type="text/template">
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<%_.each(specificationItems, function(specificationItem, i) {%>
							<th><%-specificationItem.name%></th>
						<%});%>
						<%if (type == "GENERAL") {%>
							<th>${message("Sku.price")}</th>
						<%}%>
						<th>${message("Sku.cost")}</th>
						<th>${message("Sku.marketPrice")}</th>
						<%if (type == "GENERAL") {%>
							<th>${message("Sku.maxCommission")}</th>
						<%}%>
						<%if (type == "GENERAL") {%>
							<th>${message("Sku.rewardPoint")}</th>
						<%} else if (type == "EXCHANGE") {%>
							<th>${message("Sku.exchangePoint")}</th>
						<%}%>
						<th>${message("Sku.stock")}</th>
						<th>${message("Product.unit")}</th>
						<th id="parentUnit">${message("Product.unit.data")}</th>
						<th>${message("Sku.isDefault")}</th>
						<th>${message("business.product.isEnabled")}</th>
					</tr>
				</thead>
				<tbody>
					<%_.each(skus, function(entries, i) {%>
						<%
							var ids = [];
							_.each(entries, function(entry, j) {
								ids.push(entry.id);
							});
						%>
						<tr data-ids="<%-ids.join(",")%>">
							<%
								var skuValue = skuValues[ids.join(",")];
								var price = skuValue != null && skuValue.price != null ? skuValue.price : "";
								var cost = skuValue != null && skuValue.cost != null ? skuValue.cost : "";
								var marketPrice = skuValue != null && skuValue.marketPrice != null ? skuValue.marketPrice : "";
								var maxCommission = skuValue != null && skuValue.maxCommission != null ? skuValue.maxCommission : "";
								var rewardPoint = skuValue != null && skuValue.rewardPoint != null ? skuValue.rewardPoint : "";
								var exchangePoint = skuValue != null && skuValue.exchangePoint != null ? skuValue.exchangePoint : "";
								var stock = skuValue != null && skuValue.stock != null ? skuValue.stock : "";
								var unit = skuValue != null && skuValue.unit != null ? skuValue.unit : "";
								var totalUnit = skuValue != null && skuValue.totalUnit != null ? skuValue.totalUnit : 0;
								var isDefault = skuValue != null && skuValue.isDefault != null ? skuValue.isDefault : false;
								var isEnabled = skuValue != null && skuValue.isEnabled != null ? skuValue.isEnabled : false;
							%>
							<%_.each(entries, function(entry, j) {%>
								<td>
									<p class="form-control-static"><%-entry.value%></p>
									<input name="skuList[<%-i%>].specificationValues[<%-j%>].id" type="hidden" value="<%-entry.id%>">
									<input name="skuList[<%-i%>].specificationValues[<%-j%>].value" type="hidden" value="<%-entry.value%>">
								</td>
							<%});%>
							<%if (type == "GENERAL") {%>
								<td>
									<input name="skuList[<%-i%>].price" class="price form-control" type="text" value="<%-price%>" maxlength="16"<%-!isEnabled ? " disabled" : ""%>>
								</td>
							<%}%>
							<td>
								<input name="skuList[<%-i%>].cost" class="cost form-control" type="text" value="<%-cost%>" maxlength="16"<%-!isEnabled ? " disabled" : ""%>>
							</td>
							<td>
								<input name="skuList[<%-i%>].marketPrice" class="market-price form-control" type="text" value="<%-marketPrice%>" maxlength="16"<%-!isEnabled ? " disabled" : ""%>>
							</td>
							<%if (type == "GENERAL") {%>
								<td>
									<input name="skuList[<%-i%>].maxCommission" class="max-commission form-control" type="text" value="<%-maxCommission%>" maxlength="16"<%-!isEnabled ? " disabled" : ""%>>
								</td>
							<%}%>
							<%if (type == "GENERAL") {%>
								<td>
									<input name="skuList[<%-i%>].rewardPoint" class="reward-point form-control" type="text" value="<%-rewardPoint%>" maxlength="9"<%-!isEnabled ? " disabled" : ""%>>
								</td>
							<%} else if (type == "EXCHANGE") {%>
								<td>
									<input name="skuList[<%-i%>].exchangePoint" class="exchange-point form-control" type="text" value="<%-exchangePoint%>" maxlength="9"<%-!isEnabled ? " disabled" : ""%>>
								</td>
							<%}%>
							<td>
								<input name="skuList[<%-i%>].stock" class="stock form-control" type="text" value="<%-stock%>" maxlength="9"<%-!isEnabled ? " disabled" : ""%>>
							</td>
							<td>
								<input id="prounit" name="skuList[<%-i%>].unit" class="unit form-control" type="text"  value="<%-unit%>" maxlength="9"<%-!isEnabled ? " disabled" : ""%>>
							</td>
							<td>
								<input name="skuList[<%-i%>].totalUnit" class="totalUnit form-control" type="text" value="<%-totalUnit%>" maxlength="9"<%-!isEnabled ? " disabled" : ""%>>
							</td>
							<td>
								<div class="checkbox">
									<input name="_skuList[<%-i%>].isDefault" type="hidden" value="false">
									<input name="skuList[<%-i%>].isDefault" class="is-default" type="checkbox" value="true"<%-isDefault ? " checked" : ""%><%-!isEnabled ? " disabled" : ""%>>
									<label></label>
								</div>
							</td>
							<td>
								<div class="checkbox">
									<input name="isEnabled" class="is-enabled" type="checkbox" value="true"<%-isEnabled ? " checked" : ""%>>
									<label></label>
								</div>
							</td>
						</tr>
					<%});%>
				</tbody>
			</table>
		</div>
	</script>
	[#noautoesc]
		[#escape x as x?js_string]
			<script>
			$().ready(function() {
				
				var $productForm = $("#productForm");
				var $isDefault = $("#isDefault");
				var $productCategoryId = $("#productCategoryId");
				var $type = $("#type");
				var $price = $("#price");
				var $cost = $("#cost");
				var $marketPrice = $("#marketPrice");
				var $maxCommission = $("#maxCommission");
				var $rewardPoint = $("#rewardPoint");
				var $exchangePoint = $("#exchangePoint");
				var $stock = $("#stock");
				var $promotionIds = $("input[name='promotionIds']");
				var $productImage = $("#productImage");
				var $productImageFile = $("#productImage input:file");
				var $addParameterButton = $("#addParameterButton");
				var $resetParameter = $("#resetParameter");
				var $parameterContent = $("#parameterContent");
				var $attribute = $("#attribute");
				var $sku = $("#sku");
				var $resetSpecification = $("#resetSpecification");
				var $specificationContent = $("#specificationContent");
				var productImageInputTemplate = _.template($("#productImageInputTemplate").html());
				var addParameterGroupTemplate = _.template($("#addParameterGroupTemplate").html());
				var addParameterTemplate = _.template($("#addParameterTemplate").html());
				var parameterGroupTemplate = _.template($("#parameterGroupTemplate").html());
				var attributeTemplate = _.template($("#attributeTemplate").html());
				var specificationTemplate = _.template($("#specificationTemplate").html());
				var skuTemplate = _.template($("#skuTemplate").html());
				var productImageIndex = 0;
				var parameterIndex = 0;
				var parameterEntryIndex = 0;
				var hasSpecification = false;
				
				var previousProductCategoryId = localStorage.getItem("previousProductCategoryId");
				previousProductCategoryId != null ? $productCategoryId.val(previousProductCategoryId) : previousProductCategoryId = $productCategoryId.val();
				
				loadParameter();
				loadAttribute();
				loadSpecification();

				// 商品分类
				$productCategoryId.change(function() {
					if (!isEmpty($attribute.find("select"))) {
						bootbox.confirm("${message("business.product.productCategoryChangeConfirm")}", function(result) {
							if (result) {
								if (isEmpty($parameterContent.find("input.parameter-entry-value"))) {
									loadParameter();
								}
								loadAttribute();
								if (isEmpty($sku.find("input:text"))) {
									hasSpecification = false;
									changeView();
									loadSpecification();
								}
								previousProductCategoryId = $productCategoryId.val();
							} else {
								$productCategoryId.selectpicker("val", previousProductCategoryId);
							}
						});
					} else {
						if (isEmpty($parameterContent.find("input.parameter-entry-value"))) {
							loadParameter();
						}
						loadAttribute();
						if (isEmpty($sku.find("input:text"))) {
							hasSpecification = false;
							changeView();
							loadSpecification();
						}
						previousProductCategoryId = $productCategoryId.val();
					}
				});
				
				// 判断是否为空
				function isEmpty($elements) {
					var isEmpty = true;
					
					$elements.each(function() {
						var $element = $(this);
						if ($.trim($element.val()) != "") {
							isEmpty = false;
							return false;
						}
					});
					return isEmpty;
				}
				
				// 类型
				$type.change(function() {
					changeView();
					buildSkuTable();
				});
				
				// 修改视图
				function changeView() {
					if (hasSpecification) {
						$isDefault.prop("disabled", true);
						$price.add($cost).add($maxCommission).add($marketPrice).add($rewardPoint).add($exchangePoint).add($stock).prop("disabled", true).closest("div.form-group").velocity("slideUp");
						switch ($type.val()) {
							case "GENERAL":
								$promotionIds.prop("disabled", false).closest("div.form-group").velocity("slideDown");
								break;
							case "EXCHANGE":
								$promotionIds.prop("disabled", true).closest("div.form-group").velocity("slideUp");
								break;
							case "GIFT":
								$promotionIds.prop("disabled", true).closest("div.form-group").velocity("slideUp");
								break;
						}
					} else {
						$isDefault.prop("disabled", false);
						if ($cost.add($marketPrice).add($stock).is(":hidden")) {
							$cost.add($marketPrice).add($stock).prop("disabled", false).closest("div.form-group").velocity("slideDown");
						}
						switch ($type.val()) {
							case "GENERAL":
								if ($price.add($maxCommission).add($rewardPoint).add($promotionIds).is(":hidden")) {
									$price.add($maxCommission).add($rewardPoint).add($promotionIds).prop("disabled", false).closest("div.form-group").velocity("slideDown");
								}
								$exchangePoint.prop("disabled", true).closest("div.form-group").velocity("slideUp");
								break;
							case "EXCHANGE":
								if ($price.add($maxCommission).add($rewardPoint).add($maxCommission).add($promotionIds).is(":visible")) {
									$price.add($maxCommission).add($rewardPoint).add($maxCommission).add($promotionIds).prop("disabled", true).closest("div.form-group").velocity("slideUp");
								}
								$exchangePoint.prop("disabled", false).closest("div.form-group").velocity("slideDown");
								break;
							case "GIFT":
								if ($price.add($maxCommission).add($rewardPoint).add($exchangePoint).add($promotionIds).is(":visible")) {
									$price.add($maxCommission).add($rewardPoint).add($exchangePoint).add($promotionIds).prop("disabled", true).closest("div.form-group").velocity("slideUp");
									break;
								}
						}
					}
				}
				
				// 增加参数
				$addParameterButton.click(function() {
					var parameterIndex = $parameterContent.children(".item").size();
					
					$parameterContent.append(addParameterGroupTemplate({
						parameterIndex: parameterIndex,
						parameterEntryIndex: parameterEntryIndex
					}));
				});
				
				// 重置参数
				$resetParameter.click(function() {
					bootbox.confirm("${message("business.product.resetParameterConfirm")}", function(result) {
						if (result) {
							loadParameter();
						}
					});
				});
				
				// 删除参数
				$parameterContent.on("click", "a.remove", function() {
					var $element = $(this);
					
					if ($element.hasClass("group")) {
						$element.closest("div.item").velocity("fadeOut", {
							complete: function() {
								$(this).remove();
							}
						});
					} else {
						if ($element.closest("div.item").find("div.form-group").size() <= 2) {
							$.bootstrapGrowl("${message("business.product.deleteAllNotAllowed")}", {
								type: "warning"
							});
							return false;
						}
						$element.closest("div.form-group").velocity("fadeOut", {
							complete: function() {
								$(this).remove();
							}
						});
					}
				});
				
				// 添加参数
				$parameterContent.on("click", "a.add", function() {
					var $element = $(this);
					var $item = $element.closest(".item");
					var parameterIndex = $item.data("parameter-index");
					var parameterEntryIndex = $item.find(".form-group").size() - 1;
					
					$item.append(addParameterTemplate({
						parameterIndex: parameterIndex,
						parameterEntryIndex: parameterEntryIndex
					}));
				});
				
				// 加载参数
				function loadParameter() {
					$.ajax({
						url: "${base}/business/product/parameters",
						type: "GET",
						data: {
							productCategoryId: $productCategoryId.val()
						},
						dataType: "json",
						success: function(parameters) {
							$parameterContent.html(parameterGroupTemplate({
								parameters: parameters,
								parameterIndex: parameterIndex
							}));
						}
					});
				}
				
				// 加载属性
				function loadAttribute() {
					$.ajax({
						url: "${base}/business/product/attributes",
						type: "GET",
						data: {
							productCategoryId: $productCategoryId.val()
						},
						dataType: "json",
						success: function(attributes) {
							$attribute.html(attributeTemplate({
								attributes: attributes
							})).find("select").selectpicker();
						}
					});
				}
				
				// 重置规格
				$resetSpecification.click(function() {
					bootbox.confirm("${message("business.product.resetSpecificationConfirm")}", function(result) {
						if (result) {
							hasSpecification = false;
							changeView();
							loadSpecification();
						}
					});
				});
				
				// 选择规格
				$specificationContent.on("change", "input.specification-item-check", function() {
					hasSpecification = $specificationContent.find("input:checkbox:checked").size() > 0;
					changeView();
					buildSkuTable();
					if ($sku.find("input.is-default:not(:disabled):checked").size() == 0) {
						$sku.find("input.is-default:not(:disabled):first").prop("checked", true);
					}
				});
				
				// 规格
				$specificationContent.on("change", "input:text", function() {
					var $element = $(this);
					var value = $.trim($element.val());
					
					if (value == "") {
						$element.val($element.data("value"));
						return false;
					}
					if ($element.hasClass("specification-item-entry-value")) {
						var values = $element.closest("div.specification-item").find("input.specification-item-entry-value").not($element).map(function() {
							return $.trim($(this).val());
						}).get();
						if ($.inArray(value, values) >= 0) {
							$.bootstrapGrowl("${message("business.product.specificationItemEntryValueRepeated")}", {
								type: "warning"
							});
							$element.val($element.data("value"));
							return false;
						}
					}
					$element.data("value", value);
					buildSkuTable();
				});
				
				// 是否默认
				$sku.on("change", "input.is-default", function() {
					var $element = $(this);
					
					if ($element.prop("checked")) {
						$sku.find("input.is-default").not($element).prop("checked", false);
					} else {
						$element.prop("checked", true);
					}
				});
				
				// 是否启用
				$sku.on("change", "input.is-enabled", function() {
					var $element = $(this);
					
					if ($element.prop("checked")) {
						$element.closest("tr").find("input:not(input.is-enabled)").prop("disabled", false);
					} else {
						$element.closest("tr").find("input:not(input.is-enabled)").prop("disabled", true).end().find("input.is-default").prop("checked", false);
					}
					if ($sku.find("input.is-default:not(:disabled):checked").size() == 0) {
						$sku.find("input.is-default:not(:disabled):first").prop("checked", true);
					}
				});
				
				// 生成SKU表
				function buildSkuTable() {
					var type = $type.val();
					var skuValues = {};
					var specificationItems = [];
					
					if (!hasSpecification) {
						$sku.empty();
						return false;
					}
					$specificationContent.find("div.specification-item").each(function() {
						var $element = $(this);
						var $checked = $element.find("input:checkbox:checked");
						
						if ($checked.size() > 0) {
							var specificationItem = {};
							
							specificationItem.name = $element.find("input.specification-item-name").val();
							specificationItem.entries = $checked.map(function() {
								var $element = $(this);
								return {
									id: $element.closest("div.input-group").find("input.specification-item-entry-id").val(),
									value: $element.closest("div.input-group").find("input.specification-item-entry-value").val()
								};
							}).get();
							specificationItems.push(specificationItem);
						}
					});
					var skus = cartesianSkuOf($.map(specificationItems, function(specificationItem) {
						return [specificationItem.entries];
					}));
					$sku.find("tr").each(function() {
						var $element = $(this);
						
						skuValues[$element.data("ids")] = {
							price: $element.find("input.price").val(),
							cost: $element.find("input.cost").val(),
							marketPrice: $element.find("input.market-price").val(),
							maxCommission: $element.find("input.max-commission").val(),
							rewardPoint: $element.find("input.reward-point").val(),
							exchangePoint: $element.find("input.exchange-point").val(),
							stock: $element.find("input.stock").val(),
							unit:$element.find("input.unit").val(),
							totalUnit:$element.find("input.totalUnit").val(),
							isDefault: $element.find("input.is-default").prop("checked"),
							isEnabled: $element.find("input.is-enabled").prop("checked")
						};
					});
					
					$sku.html(skuTemplate({
						type: type,
						specificationItems: specificationItems,
						skus: skus,
						skuValues: skuValues
					}));
				}
				
				// 笛卡尔积
				function cartesianSkuOf(array) {
					function addTo(current, args) {
						var i, copy;
						var rest = args.slice(1);
						var isLast = !rest.length;
						var result = [];
						
						for (i = 0; i < args[0].length; i++) {
							copy = current.slice();
							copy.push(args[0][i]);
							if (isLast) {
								result.push(copy);
							} else {
								result = result.concat(addTo(copy, rest));
							}
						}
						return result;
					}
					return addTo([], array);
				}
				
				// 加载规格
				function loadSpecification() {
					$.ajax({
						url: "${base}/business/product/specifications",
						type: "GET",
						data: {
							productCategoryId: $productCategoryId.val()
						},
						dataType: "json",
						success: function(specifications) {
							$specificationContent.html(specificationTemplate({
								specifications: specifications
							}));
							$sku.empty();
						}
					});
				}
				
				$.validator.addClassRules({
					"parameter-group": {
						required: true
					},
					price: {
						required: true,
						number: true,
						min: 0,
						decimal: {
							integer: 12,
							fraction: ${setting.priceScale}
						}
					},
					cost: {
						number: true,
						min: 0,
						decimal: {
							integer: 12,
							fraction: ${setting.priceScale}
						}
					},
					"market-price": {
						number: true,
						min: 0,
						decimal: {
							integer: 12,
							fraction: ${setting.priceScale}
						}
					},
					"max-commission": {
						required: true,
						number: true,
						min: 0,
						max: function(element) {
							var price = $(element).closest("tr").find("input.price").val();
							
							return $.isNumeric(price) ? parseFloat(price) : 999999999;
						},
						decimal: {
							integer: 12,
							fraction: ${setting.priceScale}
						}
					},
					"reward-point": {
						digits: true,
						max: function(element) {
							var price = $(element).closest("tr").find("input.price").val();
							
							return $.isNumeric(price) ? parseInt(price * ${setting.maxPointScale}) : 999999999;
						}
					},
					"exchange-point": {
						required: true,
						digits: true
					},
					stock: {
						required: true,
						digits: true
					},
					totalUnit: {
						required: true,
						digits: true
					}
				});
				
				// 表单验证
				$productForm.validate({
					rules: {
						productCategoryId: "required",
						sn: {
							pattern: /^[0-9a-zA-Z_-]+$/,
							remote: {
								url: "${base}/business/product/check_sn",
								cache: false
							}
						},
						name: "required",
						"sku.price": {
							required: true,
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						"sku.cost": {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						"sku.marketPrice": {
							number: true,
							min: 0,
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						"sku.maxCommission": {
							required: true,
							number: true,
							min: 0,
							max: function() {
								var price = $price.val();
								
								return $.isNumeric(price) ? parseFloat(price) : 999999999;
							},
							decimal: {
								integer: 12,
								fraction: ${setting.priceScale}
							}
						},
						weight: "digits",
						"sku.rewardPoint": {
							digits: true,
							max: function() {
								return parseInt($price.val() * ${setting.maxPointScale});
							}
						},
						"sku.exchangePoint": {
							required: true,
							digits: true
						},
						"sku.stock": {
							required: true,
							digits: true
						}
					},
					messages: {
						sn: {
							pattern: "${message("common.validator.illegal")}",
							remote: "${message("common.validator.exist")}"
						}
					},
					submitHandler: function(form) {
						if (hasSpecification && $sku.find("input.is-enabled:checked").size() < 1) {
							$.bootstrapGrowl("${message("business.product.specificationSkuRequired")}", {
								type: "warning"
							});
							return false;
						}
						if ($productImageFile.fileinput("getFileStack").length > 0) {
							$.bootstrapGrowl("${message("business.product.productImageUnupload")}", {
								type: "warning"
							});
							return false;
						}
						$productImage.find(".kv-preview-thumb input[name$='.order']").each(function(i) {
							$(this).val(i);
						});
						$productImage.find(".kv-zoom-thumb input").each(function() {
							$(this).prop("disabled", true);
						});
						localStorage.setItem("previousProductCategoryId", $productCategoryId.val());
						$(form).ajaxSubmit({
							successRedirectUrl: "${base}/business/product/list"
						});
					}
				});
			});
			</script>
			<script>
				//单位显示
				$("#prounit").keyup(function(){
					var oText=$("#prounit").val()
					console.log("======"+oText)
					$("#parentUnit").text(oText).value
				})
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
				<li class="active">${message("business.product.add")}</li>
			</ol>
			<form id="productForm" class="form-horizontal" action="${base}/business/product/save" method="post">
				<input id="isDefault" name="sku.isDefault" type="hidden" value="true">
				<div class="panel panel-default">
					<div class="panel-body">
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#base" data-toggle="tab">${message("business.product.base")}</a>
							</li>
							<li>
								<a href="#introduction" data-toggle="tab">${message("business.product.introduction")}</a>
							</li>
							<li>
								<a href="#productImage" data-toggle="tab">${message("business.product.productImage")}</a>
							</li>
							<li>
								<a href="#parameter" data-toggle="tab">${message("business.product.parameter")}</a>
							</li>
							<li>
								<a href="#attribute" data-toggle="tab">${message("business.product.attribute")}</a>
							</li>
							<li>
								<a href="#specification" data-toggle="tab">${message("business.product.specification")}</a>
							</li>
						</ul>
						<div class="tab-content">
							<div id="base" class="tab-pane active">
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required">${message("Product.productCategory")}:</label>
									<div class="col-xs-9 col-sm-4">
										<select id="productCategoryId" name="productCategoryId" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list productCategoryTree as productCategory]
												[#if allowedProductCategories?seq_contains(productCategory) || allowedProductCategoryParents?seq_contains(productCategory)]
													<option value="${productCategory.id}" title="${productCategory.name}"[#if !allowedProductCategories?seq_contains(productCategory)] disabled[/#if]>
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
									<label class="col-xs-3 col-sm-2 control-label">${message("Product.storeProductCategory")}:</label>
									<div class="col-xs-9 col-sm-4">
										<select name="storeProductCategoryId" class="selectpicker form-control" data-live-search="true" data-size="10">
											<option value="">${message("common.choose")}</option>
											[#list storeProductCategoryTree as storeProductCategory]
												<option value="${storeProductCategory.id}" title="${storeProductCategory.name}">
													[#if storeProductCategory.grade != 0]
														[#list 1..storeProductCategory.grade as i]
															&nbsp;&nbsp;
														[/#list]
													[/#if]
													${storeProductCategory.name}
												</option>
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Product.type")}:</label>
									<div class="col-xs-9 col-sm-4">
										<select id="type" name="type" class="selectpicker form-control" data-size="5">
											[#list types as type]
												<option value="${type}">${message("Product.Type." + type)}</option>
											[/#list]
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="sn">${message("Product.sn")}:</label>
									<div class="col-xs-9 col-sm-4" title="${message("business.product.snTitle")}" data-toggle="tooltip">
										<input id="sn" name="sn" class="form-control" type="text" maxlength="100">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="name">${message("Product.name")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="name" name="name" class="form-control" type="text" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="caption">${message("Product.caption")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="caption" name="caption" class="form-control" type="text" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="price">${message("Sku.price")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="price" name="sku.price" class="form-control" type="text" maxlength="16">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="cost">${message("Sku.cost")}:</label>
									<div class="col-xs-9 col-sm-4" title="${message("business.product.costTitle")}" data-toggle="tooltip">
										<input id="cost" name="sku.cost" class="form-control" type="text" maxlength="16">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="marketPrice">${message("Sku.marketPrice")}:</label>
									<div class="col-xs-9 col-sm-4" title="${message("business.product.marketPriceTitle")}" data-toggle="tooltip">
										<input id="marketPrice" name="sku.marketPrice" class="form-control" type="text" maxlength="16">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="maxCommission">${message("Sku.maxCommission")}:</label>
									<div class="col-xs-9 col-sm-4" title="${message("business.product.maxCommission")}" data-toggle="tooltip">
										<input id="maxCommission" name="sku.maxCommission" class="form-control" type="text" maxlength="16">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="unit">${message("Product.addunit")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="unit" name="unit" class="form-control" type="text" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="weight">${message("Product.weight")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="input-group">
											<input id="weight" name="weight" class="form-control" type="text" maxlength="9">
											<span class="input-group-addon">${message("common.unit.gram")}</span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="rewardPoint">${message("Sku.rewardPoint")}:</label>
									<div class="col-xs-9 col-sm-4" title="${message("business.product.rewardPointTitle")}" data-toggle="tooltip">
										<input id="rewardPoint" name="sku.rewardPoint" class="form-control" type="text" maxlength="9">
									</div>
								</div>
								<div class="hidden-element form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="exchangePoint">${message("Sku.exchangePoint")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="exchangePoint" name="sku.exchangePoint" class="form-control" type="text" maxlength="9" disabled>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required" for="stock">${message("Sku.stock")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="stock" name="sku.stock" class="form-control" type="text" value="1" maxlength="9">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("Product.brand")}:</label>
									<div class="col-xs-9 col-sm-4">
										<select name="brandId" class="selectpicker form-control" data-live-search="true" data-size="10">
										<option value="">${message("common.choose")}</option>
											[#list brands as brand]
												<option value="${brand.id}">${brand.name}</option>
											[/#list]
										</select>
									</div>
								</div>
								<!--质量保障书start-->
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label item-required">${message("Product.quality")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input name="quality" type="hidden" value="" data-provide="fileinput" data-file-type="PDF">
									</div>
								</div>
								<!--质量保障书end-->
								[#if promotions?has_content]
									<div class="form-group">
										<label class="col-xs-3 col-sm-2 control-label">${message("Product.promotions")}:</label>
										<div class="col-xs-9 col-sm-10">
											[#list promotions as promotion]
												<div class="checkbox checkbox-inline">
													<input id="promotion_${promotion.id}" name="promotionIds" type="checkbox" value="${promotion.id}">
													<label for="promotion_${promotion.id}">${promotion.name}</label>
												</div>
											[/#list]
										</div>
									</div>
								[/#if]
								[#if productTags?has_content]
									<div class="form-group">
										<label class="col-xs-3 col-sm-2 control-label">${message("Product.productTags")}:</label>
										<div class="col-xs-9 col-sm-10">
											[#list productTags as productTag]
												<div class="checkbox checkbox-inline">
													<input id="productTag_${productTag.id}" name="productTagIds" type="checkbox" value="true" checked>
													<label for="productTag_${productTag.id}">${productTag.name}</label>
												</div>
											[/#list]
										</div>
									</div>
								[/#if]
								[#if storeProductTags?has_content]
									<div class="form-group">
										<label class="col-xs-3 col-sm-2 control-label item-required">${message("Product.storeProductTags")}:</label>
										<div class="col-xs-9 col-sm-10">
											[#list storeProductTags as storeProductTag]
												<div class="checkbox checkbox-inline">
													<input id="storeProductTag_${storeProductTag.id}" name="storeProductTagIds" type="checkbox" value="${storeProductTag.id}" checked>
													<label for="storeProductTag_${storeProductTag.id}">${storeProductTag.name}</label>
												</div>
											[/#list]
										</div>
									</div>
								[/#if]
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label">${message("common.setting")}:</label>
									<div class="col-xs-9 col-sm-4">
										<div class="checkbox checkbox-inline">
											<input name="_isMarketable" type="hidden" value="false">
											<input id="isMarketable" name="isMarketable" type="checkbox" value="true" checked>
											<label for="isMarketable">${message("Product.isMarketable")}</label>
										</div>
										<div class="checkbox checkbox-inline">
											<input name="_isList" type="hidden" value="false">
											<input id="isList" name="isList" type="checkbox" value="true" checked>
											<label for="isList">${message("Product.isList")}</label>
										</div>
										<div class="checkbox checkbox-inline">
											<input name="_isTop" type="hidden" value="false">
											<input id="isTop" name="isTop" type="checkbox" value="true">
											<label for="isTop">${message("Product.isTop")}</label>
										</div>
										<div class="checkbox checkbox-inline">
											<input name="_isDelivery" type="hidden" value="false">
											<input id="isDelivery" name="isDelivery" type="checkbox" value="true" checked>
											<label for="isDelivery">${message("Product.isDelivery")}</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="memo">${message("Product.memo")}:</label>
									<div class="col-xs-9 col-sm-4">
										<input id="memo" name="memo" class="form-control" type="text" maxlength="200">
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 col-sm-2 control-label" for="keyword">${message("Product.keyword")}:</label>
									<div class="col-xs-9 col-sm-4" title="${message("business.product.keywordTitle")}" data-toggle="tooltip">
										<input id="keyword" name="keyword" class="form-control" type="text" maxlength="200">
									</div>
								</div>
							</div>
							<div id="introduction" class="tab-pane">
								<textarea name="introduction" data-provide="editor"></textarea>
							</div>
							<div id="productImage" class="tab-pane">
								<div class="form-group">
									<div class="col-xs-12 col-sm-6">
										<input name="file" type="file" multiple>
									</div>
								</div>
							</div>
							<div id="parameter" class="tab-pane">
								<div class="form-group">
									<div class="col-xs-10 col-xs-offset-2">
										<button id="addParameterButton" class="btn btn-default" type="button">
											<i class="iconfont icon-add"></i>
											${message("business.product.addParameter")}
										</button>
										<button id="resetParameter" class="btn btn-default" type="button">
											<i class="iconfont icon-repeal"></i>
											${message("business.product.resetParameter")}
										</button>
									</div>
								</div>
								<div id="parameterContent"></div>
							</div>
							<div id="attribute" class="tab-pane"></div>
							<div id="specification" class="tab-pane">
								<div class="form-group">
									<div class="col-xs-10 col-xs-offset-2">
										<button id="resetSpecification" class="btn btn-default" type="button">
											<i class="iconfont icon-repeal"></i>
											${message("business.product.resetSpecification")}
										</button>
									</div>
								</div>
								<div id="specificationContent"></div>
								<div id="sku"></div>
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