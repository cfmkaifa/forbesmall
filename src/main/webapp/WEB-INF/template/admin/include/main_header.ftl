<header class="main-header">
	<div class="title hidden-xs">
		<a href="${base}/admin/index">
			<img src="${base}/resources/admin/images/main_header_logo.png" alt="${setting.siteName}">
		</a>
	</div>
	<button class="main-sidebar-toggle" type="button" data-toggle="mainSidebarCollapse">
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	</button>
	<ul class="nav pull-right">
		<li class="palette">
			<div class="palette_item_wrapper">
				<div data-color="#e169c3" class="palette_color"></div>
				<div data-color="#b569e1" class="palette_color"></div>
				<div data-color="#7169e1" class="palette_color"></div>
				<div data-color="#69b8e1" class="palette_color"></div>
				<div data-color="#69e1d4" class="palette_color"></div>
				<div data-color="#6ce169" class="palette_color"></div>
				<div data-color="#bee169" class="palette_color"></div>
				<div data-color="#e1a169" class="palette_color"></div>
				<div data-color="#83091d" class="palette_color"></div>
			</div>
			<p class="palette_color__main">换肤</p>
		</li>
		<li>
			<a href="${base}/admin/profile/edit">${message("admin.mainHeader.profile")}</a>
		</li>
		<li class="dropdown">
			<a class="dropdown-toggle" href="javascript:;" data-toggle="dropdown">
				<i class="iconfont icon-sort"></i>
			</a>
			<ul class="dropdown-menu dropdown-menu-right">
				<li>
					<a href="" target="_blank">${message("admin.mainHeader.official")}</a>
				</li>
				<li>
					<a href="/about.html" target="_blank">${message("admin.mainHeader.about")}</a>
				</li>
			</ul>
		</li>
		<li>
			<a id="mainHeaderLogout" class="logout" href="${base}/admin/logout">
				<i class="iconfont icon-exit"></i>
				${message("admin.mainHeader.logout")}
			</a>
		</li>
	</ul>
</header>
[#noautoesc]
	[#escape x as x?js_string]
		<script>
		$().ready(function() {
			
			var $document = $(document);
			var $mainHeaderLogout = $("#mainHeaderLogout");
			
			// 用户注销
			$mainHeaderLogout.click(function() {
				$document.trigger("loggedOut.mall.user", $.getCurrentUser());
			});
			
		});
		</script>
	[/#escape]
[/#noautoesc]