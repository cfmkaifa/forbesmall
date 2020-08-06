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
		<li class="palette_color__main">换肤</li>
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
	<div class="palette">
		<div class="palette_item_wrapper">
			<div data-color="#e169c3" class="palette_color"></div>
			<div data-color="#b569e1" class="palette_color"></div>
			<div data-color="#7169e1" class="palette_color"></div>
			<div data-color="#69b8e1" class="palette_color"></div>
			<div data-color="#69e1d4" class="palette_color"></div>
			<div data-color="#6ce169" class="palette_color"></div>
			<div data-color="#bee169" class="palette_color"></div>
			<div data-color="#e1a169" class="palette_color"></div>
			<div data-color="#108EE9" class="palette_color"></div>
		</div>
		[#--		<div class="palette_color__main">换肤</div>--]
	</div>
</header>
[#noautoesc]
	[#escape x as x?js_string]
		<script>
		$().ready(function() {
			var coco= localStorage.getItem("color");
			// $(".main-header .title").css()
			$('.main-header').css('background',coco);
			var $document = $(document);
			var $mainHeaderLogout = $("#mainHeaderLogout");
			
			// 用户注销
			$mainHeaderLogout.click(function() {
				$document.trigger("loggedOut.mall.user", $.getCurrentUser());
			});
			
		});

		// 换肤
		var _$paletteElement = $('.palette_color');

		function _colorizePaletteItems(){
			$.each(_$paletteElement, function(i){
				$(this).css('background-color', $(this).data('color'));
			})
		}

		function _togglePalette(){
			$('.palette_color__main').on('click', function(){
				$('.palette').toggleClass('palette__opened');
				if ($('.palette_item_wrapper').is(':hidden')){
					setTimeout(()=>{
						$('.palette_item_wrapper').show();
					},200)
				}else  {
					setTimeout(()=>{
						$('.palette_item_wrapper').hide();
					},500)

				}
			})
		}

		function _initSetColor(){
			$('.palette_color').on('click', function(){
				var color = $(this).data('color');
				_setColor(color);
			})
		}

		function _setColor(color){
			localStorage.setItem("color",color);
			$('.main-header').css('background', color);
			window.location.reload()
		}

		_colorizePaletteItems();
		_togglePalette();
		_initSetColor();
		</script>
	[/#escape]
[/#noautoesc]