<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="author" content="huanghy">
    <meta name="copyright" content="">
    <title>${message("member.mainMenu.account")}</title>
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
    <script src="${base}/resources/common/js/bootbox.js"></script>
    <script src="${base}/resources/common/js/jquery.cookie.js"></script>
    <script src="${base}/resources/common/js/jquery.base64.js"></script>
    <script src="${base}/resources/common/js/underscore.js"></script>
    <script src="${base}/resources/common/js/url.js"></script>
    <script src="${base}/resources/common/js/velocity.js"></script>
    <script src="${base}/resources/common/js/velocity.ui.js"></script>
    <script src="${base}/resources/common/js/base.js?version=0.1"></script>
    <script src="${base}/resources/member/js/base.js"></script>
    <style type="text/css">
        #acctountContents{
            background: #ffffff;
            padding: 10px;
        }
        .accountTitle{
            height: 48px;
            line-height: 48px;
            font-size: 14px;
            border-bottom: 1px solid #EEEEEE;
        }
        .account-management{
            width: 992px;
            margin: 0 auto;
            font-size: 12px;
        }
        .account-management .addAccount{
            cursor: pointer;
            color: #3B79CE;
        }
        .formInput{
            width: 416px !important;
        }
        .dispalyFlex{
            display: flex;
        }
        .dispalyFlex label{
            width: 8%;
            color: #333333;
            font-size: 12px;
            font-weight:400;
        }
        .accountContent{
            border-top: 1px solid #EEEEEE;
            padding: 20px 0px;
        }
        .accountContent>p{
            line-height: 30px;
        }
        .addAccount{
            margin-bottom: 14px;
        }
        .removeAccount,.removeAccount.active.focus{
            background-color: #ffffff;
            outline: none;
            border-color: transparent;
            box-shadow:none;
            float: right;
            margin-right: 20px;
        }
        .accountContentBtn{
            display: flex;
            justify-content: center;
            margin-top: 30px;
        }
        .accountBtn{
            width:80px;
            height:32px;
            background:rgba(238,56,52,1);
            border-radius:4px;
            margin-right: 20px;
            color: #FFFFFF;
            outline: none;
            border-color: transparent;
            box-shadow:none;
        }
        .returnBtn,.returnBtn.active.focus{
            width:80px;
            height:32px;
            background: #FFFFFF;
            border:1px solid #DDDDDD;
            border-radius:4px;
            box-shadow:none;
            outline: none;
        }
    </style>
    [#noautoesc]
        [#escape x as x?js_string]
            <script>
                $().ready(function() {

                    var $document = $(document);
                    var $accountForm = $("#accountForm");
                    // 表单验证
                    $accountForm.validate({
                        rules: {
                            username: {
                                required: true,
                                minlength: 4,
                                username: true,
                                notAllNumber: true,
                                remote: {
                                    url: "${base}/member/register/check_username",
                                    cache: false
                                }
                            },
                            password: {
                                required: true,
                                minlength: 4,
                                normalizer: function (value) {
                                    return value;
                                }
                            },
                            mobile: {
                                required: true,
                                mobile: true,
                                remote: {
                                    url: "${base}/member/register/check_mobile",
                                    cache: false
                                }
                            }
                        },
                        messages: {
                            username: {
                                remote: "${message("member.register.usernameExist")}"
                            },
                            mobile: {
                                remote: "${message("member.register.mobileExist")}"
                            }
                        },
                        submitHandler: function(form) {
                            $(form).ajaxSubmit({
                                successRedirectUrl: "${base}/member/account/list"
                            });
                        }
                    });
                });
            </script>
        [/#escape]
    [/#noautoesc]
</head>
<body class="member order">
[#include "/shop/include/main_header.ftl" /]
<main>
    <div class="container">
        <div class="row">
            <div class="col-xs-2">
                [#include "/member/include/main_menu.ftl" /]
            </div>
            <div class="col-xs-10">
                <div id="acctountContents">
                    [#list members as member]
                        <div class="accountContent">
                                <div class="form-group dispalyFlex">
                                    <label for="name">${message("Member.name")}：</label>
                                    <label for="name">${member.name}</label>
                                </div>
                                <div class="form-group dispalyFlex">
                                    <label for="name">${message("member.account.userName")}：</label>
                                    <label for="name">${member.username}</label>
                                </div>
                                <div class="form-group dispalyFlex">
                                    <label for="name">${message("member.account.phone")}：</label>
                                    <label for="name">${member.mobile}</label>
                                </div>
                            <a class="btn btn-default btn-xs btn-icon" href="javascript:;" title="${message("common.delete")}" data-action="delete" data-url="${base}/member/account/delete?accountId=${member.id}" style="float: right;margin-right: 12%">
                                <i class="iconfont icon-close"></i>
                            </a>
                        </div>
                    [/#list]
                    <div class="accountContent">
                        <form role="form" id="accountForm"  action="${base}/member/account/add_account" method="post"  >
                            <div class="form-group dispalyFlex">
                                <label for="name">${message("Member.name")}：</label>
                                <input type="text" class="form-control formInput" name="name" id="name" placeholder="${message("Member.name")}">
                            </div>
                            <div class="form-group dispalyFlex">
                                <label for="name">${message("member.account.userName")}：</label>
                                <input type="text" class="form-control formInput" name="username" id="userName" placeholder="${message("member.account.userName")}">
                            </div>
                            <div class="form-group dispalyFlex">
                                <label for="name">${message("member.account.phone")}：</label>
                                <input type="text" class="form-control formInput" name="mobile" id="mobile" placeholder="${message("member.account.phone")}">
                            </div>
                            <div class="form-group dispalyFlex">
                                <label for="name">${message("member.account.password")}：</label>
                                <input type="password" class="form-control formInput" name="password" id="password" placeholder="${message("member.account.password")}">
                            </div>
                            <div class="accountContentBtn">
                                <button type="submit" class="accountBtn"  id="saveAccount" >${message("common.ok")}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
[#include "/shop/include/main_footer.ftl" /]
</body>
</html>
