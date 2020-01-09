<script id="mainHeaderMemberInfoTemplate" type="text/template">
	[#--<%if (currentUser != null && currentUser.type == "member") {%>
		<ul class="list-inline">
			<li>
				<a href="${base}/member/index"><%-currentUser.username%></a>
			</li>
			<li>
				<a class="logout" href="${base}/member/logout">${message("shop.mainHeader.memberLogout")}</a>
			</li>
		</ul>
	<%} else {%>--]
		<ul class="list-inline">
			<li>
				<a href="${base}/member/login">${message("shop.mainHeader.memberLogin")}</a>
				[#--<a href="http://localhost:8080/member/login">${message("shop.mainHeader.memberLogin")}</a>--]
			</li>
			<li>
				<a href="${base}/member/register">${message("shop.mainHeader.memberRegister")}</a>
			</li>
		</ul>
	<%}%>
</script>
<script id="mainHeaderCartDetailTemplate" type="text/template">
	<%if (!_.isEmpty(cart.cartItems)) {%>
		<div class="cart-detail-body">
			<ul>
				<%_.each(cart.cartItems, function(cartItem, i) {%>
					<li>
						<a href="${base}<%-cartItem.skuPath%>">
							<img src="<%-cartItem.skuThumbnail%>" alt="<%-cartItem.skuName%>">
							<span class="text-overflow" title="<%-cartItem.skuName%>"><%-cartItem.skuName%></span>
						</a>
						<span>
							<strong><%-$.currency(cartItem.price, true, false)%></strong>
							&times; <%-cartItem.quantity%>
						</span>
					</li>
				<%});%>
			</ul>
		</div>
		<div class="cart-detail-footer">
			${message("shop.mainHeader.cartPrice")}:
			<strong><%-$.currency(cart.effectivePrice, true, true)%></strong>
			<a class="pull-right" href="${base}/cart/list">${message("shop.mainHeader.cartCheckout")}</a>
		</div>
	<%} else {%>
		<p>${message("shop.mainHeader.cartEmpty")}</p>
	<%}%>
</script>
[#noautoesc]
	[#escape x as x?js_string]
		<script>
		$().ready(function() {
			
			var $document = $(document);
			var $mainHeaderTopAd = $("#mainHeaderTopAd");
			var $mainHeaderTopAdClose = $("#mainHeaderTopAd button.close");
			var $mainHeaderMemberInfo = $("#mainHeaderMemberInfo");
			var $mainHeaderProductSearchForm = $("#mainHeaderProductSearch form");
			var $searchType = $("#mainHeaderProductSearch [data-search-type]");
			var $mainHeaderProductSearchKeyword = $("#mainHeaderProductSearch input[name='keyword']");
			var $mainHeaderCart = $("#mainHeaderCart");
			var $mainHeaderCartQuantity = $("#mainHeaderCart em");
			var $mainHeaderCartDetail = $("#mainHeaderCart div.cart-detail");
			var $mainHeaderMainNavInkBar = $("#mainHeaderMainNav div.ink-bar");
			var $mainHeaderMainNavItem = $("#mainHeaderMainNav li");
			var $mainHeaderMainNavActiveItem = $("#mainHeaderMainNav li.active");
			var mainHeaderMemberInfoTemplate = _.template($("#mainHeaderMemberInfoTemplate").html());
			var mainHeaderCartDetailTemplate = _.template($("#mainHeaderCartDetailTemplate").html());
			
			// 顶部广告
			if (sessionStorage.getItem("mainHeaderTopAdHidden") == null) {
				$mainHeaderTopAd.show();
			}
			
			// 顶部广告
			$mainHeaderTopAdClose.click(function() {
				sessionStorage.setItem("mainHeaderTopAdHidden", "true");
				$mainHeaderTopAd.velocity("slideUp");
			});
			
			// 会员信息
			$mainHeaderMemberInfo.html(mainHeaderMemberInfoTemplate({
				currentUser: $.getCurrentUser()
			}));
			
			// 用户注销
			$mainHeaderMemberInfo.on("click", "a.logout", function() {
				$document.trigger("loggedOut.mall.user", $.getCurrentUser());
			});
			
			// 搜索类型
			$searchType.click(function() {
				var $element = $(this);
				var searchType = $element.data("search-type");
				
				$element.closest("div.input-group").find("[data-toggle='dropdown'] span:not(.caret)").text($element.text());
				
				switch (searchType) {
					case "product":
						$mainHeaderProductSearchForm.attr("action", "${base}/product/search");
						break;
					case "store":
						$mainHeaderProductSearchForm.attr("action", "${base}/store/search");
						break;
				}
			});
			
			// 商品搜索
			$mainHeaderProductSearchForm.submit(function() {
				if ($.trim($mainHeaderProductSearchKeyword.val()) == "") {
					return false;
				}
			});
			
			// 购物车
			$mainHeaderCart.hover(function() {
				var loading = true;
				
				setTimeout(function() {
					if (loading) {
						$mainHeaderCartDetail.html('<div class="cart-loader"><span></span><span></span><span></span><span></span><span></span></div>');
					}
				}, 500);
				$.getCurrentCart().done(function(data) {
					loading = false;
					$mainHeaderCartDetail.html(mainHeaderCartDetailTemplate({
						cart: data
					}));
				});
			});
			
			// 购物车数量
			var currentCartQuantity = $.getCurrentCartQuantity();
			if (currentCartQuantity != null) {
				$mainHeaderCartQuantity.text(currentCartQuantity < 100 ? currentCartQuantity : "99+");
			}
			
			// 购物车数量
			$document.on("complete.mall.setCurrentCartQuantity", function(event, quantity) {
				$mainHeaderCartQuantity.text(quantity < 100 ? quantity : "99+");
			});
			
			// 主导航
			if ($mainHeaderMainNavItem.length > 0) {
				if ($mainHeaderMainNavActiveItem.length < 1) {
					$mainHeaderMainNavActiveItem = $mainHeaderMainNavItem.first();
				}
				
				$mainHeaderMainNavInkBar.css({
					width: $mainHeaderMainNavActiveItem.outerWidth(),
					display: "block",
					left: $mainHeaderMainNavActiveItem.position().left
				});
				
				$mainHeaderMainNavItem.hover(function() {
					var $element = $(this);
					
					$mainHeaderMainNavInkBar.css({
						width: $element.outerWidth(),
						left: $element.position().left
					});
				}, function() {
					$mainHeaderMainNavInkBar.css({
						width: $mainHeaderMainNavActiveItem.outerWidth(),
						left: $mainHeaderMainNavActiveItem.position().left
					});
				});
			}
			
		});
		</script>
	[/#escape]
[/#noautoesc]
<header class="main-header" style="border-bottom:none">
	[@ad_position id = 1]
		[#if adPosition??]
			[#noautoesc]${adPosition.resolveTemplate()}[/#noautoesc]
		[/#if]
	[/@ad_position]
	<div class="top-nav">
		<div class="container">
			<div class="row">
				<div class="col-xs-12">
					<div id="mainHeaderMemberInfo" class="pull-left"></div>
					<ul class="list-inline pull-right">
						[@navigation_list navigationGroupId = 1]
							[#if navigations?has_content]
								[#list navigations as navigation]
									<li>
										<a href="${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a>
									</li>
								[/#list]
							[/#if]
						[/@navigation_list]
						<li class="top-nav-dropdown">
							<a href="javascript:;">
								${message("shop.mainHeader.business")}
								<span class="caret"></span>
							</a>
							<ul class="business">
								<li>
									<a href="${base}/business/index" target="_blank">${message("shop.mainHeader.businessIndex")}</a>
								</li>
								<li>
									<a href="${base}/business/register">${message("shop.mainHeader.businessRegister")}</a>
								</li>
							</ul>
						</li>
						<li class="top-nav-dropdown">
							<a href="javascript:;">
								${message("shop.mainHeader.qrcode")}
								<span class="caret"></span>
							</a>
							<ul class="qrcode">
								<li>
								</li>
								<li>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="logo-3">
		<div class="login-main3">
			<img src="/resources/shop/images/logo.png">
		</div>
	</div>
</header>