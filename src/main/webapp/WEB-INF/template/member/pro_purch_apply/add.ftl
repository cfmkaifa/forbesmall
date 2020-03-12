<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="author" content="huanghy">
    <meta name="copyright" content="">
    <title>${message("admin.proPurchApply.add")}  </title>
    <link href="${base}/favicon.ico" rel="icon">
    <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
    <link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
    <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/common/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="${base}/resources/common/css/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="${base}/resources/common/css/bootstrap-fileinput.css" rel="stylesheet">
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
    <script src="${base}/resources/common/js/moment.js"></script>
    <script src="${base}/resources/common/js/bootstrap-datetimepicker.js"></script>
    <script src="${base}/resources/common/js/bootstrap-fileinput.js"></script>
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
                    var $proPurchApplyForm = $("#proPurchApplyForm");
                    // 表单验证
                    $proPurchApplyForm.validate({
                        rules: {
                            skuSn: "required",
                            mqqWeight: {
                                required:true,
                                digits:true
                            },
                            startPrice:{
                                required:true,
                                number: true,
                                min: 0,
                                decimal: {
                                    integer: 12,
                                    fraction: ${setting.priceScale}
                                }
                            },
                            endPrice:{
                                required:true,
                                number: true,
                                min: 0,
                                decimal: {
                                    integer: 12,
                                    fraction: ${setting.priceScale}
                                }
                            },
                            startTime: "required",
                            endTime: "required"
                        },
                        submitHandler: function(form) {
                            var startPrice = $("#startPrice").val();
                            var endPrice = $("#endPrice").val();
                            var startTime = $("#startTime").val();
                            var endTime = $("#endTime").val();
                            if(parseFloat(startPrice) == 0){
                                $.bootstrapGrowl("${message("member.proPurchApply.startPriceRequired")}", {
                                    type: "warning"
                                });
                                return false;
                            }
                            if(parseFloat(endPrice) == 0){
                                $.bootstrapGrowl("${message("member.proPurchApply.endPriceRequired")}", {
                                    type: "warning"
                                });
                                return false;
                            }
                            if(parseFloat(endPrice) <= parseFloat(startPrice)){
                                $.bootstrapGrowl("${message("member.proPurchApply.startOrendPriceRequired")}", {
                                    type: "warning"
                                });
                                return false;
                            }
                            var endDate = new Date(endTime);
                            var startDate = new Date(startTime);
                            if(endDate <= startDate){
                                $.bootstrapGrowl("${message("business.groupPurch.timeRequired")}", {
                                    type: "warning"
                                });
                                return false;
                            }
                            $(form).ajaxSubmit({
                                successRedirectUrl: "${base}/member/pro_purch_apply/list"
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
                <form id="proPurchApplyForm" class="ajax-form form-horizontal" action="${base}/member/pro_purch_apply/save" method="post">
                    <input name="proSn" type="hidden" value="${product.sn}">
                    <div class="panel panel-default">
                        <div class="panel-heading">${message("business.groupPurchApply.add")}</div>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-xs-3 col-sm-2 control-label item-required">${message("GroupPurchApply.spec")}:</label>
                                <div class="col-xs-9 col-sm-4">
                                    <select id="skuSn" name="skuSn" class="selectpicker form-control" data-live-search="true" data-size="10">
                                        [#list product.skus as sku]
                                            [#if  !sku.group]
                                                <option value="${sku.sn}" >
                                                    [#if sku.specifications?has_content]
                                                        <span class="text-gray">[${sku.specifications?join(", ")}]</span>
                                                    [/#if]
                                                </option>
                                            [/#if]
                                        [/#list]
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 col-sm-2 control-label" for="startPrice">${message("ProPurchApply.startPrice")}:</label>
                                <div class="col-xs-9 col-sm-4">
                                    <div class="input-group">
                                        <input id="startPrice" name="startPrice" class="form-control" type="text" maxlength="9">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 col-sm-2 control-label" for="endPrice">${message("ProPurchApply.endPrice")}:</label>
                                <div class="col-xs-9 col-sm-4">
                                    <div class="input-group">
                                        <input id="endPrice" name="endPrice" class="form-control" type="text" maxlength="9">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 col-sm-2 control-label" for="beginDate">${message("common.dateRange")}:</label>
                                <div class="col-xs-9 col-sm-4">
                                    <div class="input-group" data-provide="datetimerangepicker" data-date-format="YYYY-MM-DD HH:mm:ss">
                                        <input id="startTime" name="startTime" class="form-control" type="text">
                                        <span class="input-group-addon">-</span>
                                        <input id="endTime" name="endTime" class="form-control" type="text">
                                    </div>
                                </div>
                            </div>
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