<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<meta name="author" content="huanghy">
	<meta name="copyright" content="">
	<title>${message("member.storeFavorite.list")}[#if showPowered] - Powered By [/#if]</title>
	<link href="${base}/favicon.ico" rel="icon">
	<link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
	<link href="${base}/resources/common/css/font-awesome.css" rel="stylesheet">
	<link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
	<link href="${base}/resources/common/css/base.css" rel="stylesheet">
	<link href="${base}/resources/mobile/member/css/base.css" rel="stylesheet">
	<!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
	<![endif]-->
	<script src="${base}/resources/common/js/jquery.js"></script>
	<script src="${base}/resources/common/js/bootstrap.js"></script>
	<script src="${base}/resources/common/js/bootstrap-growl.js"></script>
	<script src="${base}/resources/common/js/jquery.scrolltofixed.js"></script>
	<script src="${base}/resources/common/js/bootbox.js"></script>
	<script src="${base}/resources/common/js/jquery.cookie.js"></script>
	<script src="${base}/resources/common/js/scrollload.js"></script>
	<script src="${base}/resources/common/js/underscore.js"></script>
	<script src="${base}/resources/common/js/url.js"></script>
	<script src="${base}/resources/common/js/velocity.js"></script>
	<script src="${base}/resources/common/js/velocity.ui.js"></script>
	<script src="${base}/resources/common/js/base.js"></script>
	<script src="${base}/resources/mobile/member/js/base.js"></script>
	<script id="scrollLoadTemplate" type="text/template">
		<%_.each(data, function(storeFavorite, i) {%>
			<div class="panel panel-default">
				<div class="panel-heading">
					${message("Store.name")}: <%-storeFavorite.store.name%>
					<%if (!storeFavorite.store.isEnabled) {%>
						<span class="pull-right text-orange">${message("member.storeFavorite.storeNotActive")}</span>
					<%} else if (storeFavorite.store.hasExpired) {%>
						<span class="pull-right text-orange">${message("member.storeFavorite.storeHasExpired")}</span>
					<%}%>
				</div>
				<div class="panel-body">
					<div class="media">
						<div class="media-left media-middle">
							<a href="${base}<%-storeFavorite.store.path%>" title="<%-storeFavorite.store.name%>">
								<img class="media-object img-thumbnail" src="<%-storeFavorite.store.logo != null ? storeFavorite.store.logo : "${setting.defaultStoreLogo}"%>" alt="<%-storeFavorite.store.name%>">
							</a>
						</div>
						<div class="media-body media-middle">
							<a href="${base}<%-storeFavorite.store.path%>"><%-storeFavorite.store.name%></a>
						</div>
					</div>
				</div>
				<div class="panel-footer text-right">
					<%if (storeFavorite.store.isEnabled && !storeFavorite.store.hasExpired) {%>
						<a class="btn btn-default btn-sm" href="${base}<%-storeFavorite.store.path%>">${message("member.storeFavorite.inShop")}</a>
					<%}%>
					<a class="btn btn-default btn-sm" href="javascript:;" data-action="delete" data-url="${base}/member/store_favorite/delete?storeFavoriteId=<%-storeFavorite.id%>">${message("common.delete")}</a>
				</div>
			</div>
		<%});%>
	</script>
</head>
<body class="member">
	<header class="header-default" data-spy="scrollToFixed">
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-1">
					<a href="${base}/member/index">
						<i class="iconfont icon-back"></i>
					</a>
				</div>
				<div class="col-xs-10">
					<h5>${message("member.storeFavorite.list")}</h5>
				</div>
			</div>
		</div>
	</header>
	<main>
		<div class="container-fluid">
			<div data-spy="scrollLoad" data-url="${base}/member/store_favorite/list?pageNumber=<%-pageNumber%>">
				<div id="scrollLoadContent"></div>
			</div>
		</div>
	</main>
</body>
</html>