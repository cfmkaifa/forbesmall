<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="author" content="huanghy">
    <meta name="copyright" content="">
    <title>${message("business.groupPurchApply.add")}  </title>
    <link href="${base}/favicon.ico" rel="icon">
    <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
    <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
    <link href="${base}/resources/common/css/bootstrap-select.css" rel="stylesheet">
    <link href="${base}/resources/common/css/bootstrap-datetimepicker.css" rel="stylesheet">
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
    <script src="${base}/resources/common/js/moment.js"></script>
    <script src="${base}/resources/common/js/bootstrap-datetimepicker.js"></script>
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
    [#noautoesc]
        [#escape x as x?js_string]
            <script>
                $().ready(function() {
                    var $groupPurchApplyForm = $("#groupPurchApplyForm");
                    // 表单验证
                    $groupPurchApplyForm.validate({
                        rules: {
                            skuSn: "required",
                            mqqWeight: "digits",
                            limitWeight:"digits",
                            mqqPeople:"digits",
                            groupPrice:"digits",
                            limitPeople:"digits"
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
            <li class="active">${message("business.groupPurchApply.add")}</li>
        </ol>
        <form id="groupPurchApplyForm" class="ajax-form form-horizontal" action="${base}/business/group_purch_apply/save" method="post">
            <input name="proSn" type="hidden" value="${product.sn}">
            <div class="panel panel-default">
                <div class="panel-heading">${message("business.groupPurchApply.add")}</div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-xs-3 col-sm-2 control-label item-required">${message("GroupPurchApply.spec")}:</label>
                        <div class="col-xs-9 col-sm-4">
                            <select id="skuSn" name="skuSn" class="selectpicker form-control" data-live-search="true" data-size="10">
                                [#list product.skus as sku]
                                    <option value="${sku.sn}" >
                                        [#if sku.specifications?has_content]
                                            <span class="text-gray">[${sku.specifications?join(", ")}]</span>
                                        [/#if]
                                    </option>
                                [/#list]
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 col-sm-2 control-label" for="weight">${message("GroupPurchApply.groupPrice")}:</label>
                        <div class="col-xs-9 col-sm-4">
                            <div class="input-group">
                                <input id="groupPrice" name="groupPrice" class="form-control" type="text" maxlength="9">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 col-sm-2 control-label" for="weight">${message("GroupPurchApply.mqqWeight")}:</label>
                        <div class="col-xs-9 col-sm-4">
                            <div class="input-group">
                                <input id="mqqWeight" name="mqqWeight" class="form-control" type="text" maxlength="9">
                                <span class="input-group-addon">${product.unit}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 col-sm-2 control-label" for="weight">${message("GroupPurchApply.limitWeight")}:</label>
                        <div class="col-xs-9 col-sm-4">
                            <div class="input-group">
                                <input id="limitWeight" name="limitWeight" class="form-control" type="text" maxlength="9">
                                <span class="input-group-addon">${product.unit}</span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 col-sm-2 control-label" for="weight">${message("GroupPurchApply.mqqPeople")}:</label>
                        <div class="col-xs-9 col-sm-4">
                            <div class="input-group">
                                <input id="mqqPeople" name="mqqPeople" class="form-control" type="text" maxlength="9" value="1">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 col-sm-2 control-label" for="weight">${message("GroupPurchApply.limitPeople")}:</label>
                        <div class="col-xs-9 col-sm-4">
                            <div class="input-group">
                                <input id="limitPeople" name="limitPeople" class="form-control" type="text" maxlength="9">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 col-sm-2 control-label" for="beginDate">${message("common.dateRange")}:</label>
                        <div class="col-xs-9 col-sm-4">
                            <div class="input-group" data-provide="datetimerangepicker" data-date-format="YYYY-MM-DD HH:mm:ss">
                                <input id="startTime" name="startTime" class="form-control" type="text">
                                <span class="input-group-addon">-</span>
                                <input name="endTime" class="form-control" type="text">
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
</main>
</body>
</html>