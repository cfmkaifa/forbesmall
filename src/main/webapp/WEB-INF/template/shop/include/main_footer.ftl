<footer class="main-footer">
	<div class="container">
		<div class="promise">
			<div class="row">
				<div class="col-xs-3">
					<div class="pull-left">
						<i class="iconfont icon-roundcheck"></i>
					</div>
					<div class="pull-left">
						<strong>品质保障</strong>
						<p>品质护航 购物无忧</p>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="pull-left">
						<i class="iconfont icon-time"></i>
					</div>
					<div class="pull-left">
						<strong>极速物流</strong>
						<p>多仓直发 极速配送</p>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="pull-left">
						<i class="iconfont icon-like"></i>
					</div>
					<div class="pull-left">
						<strong>售后无忧</strong>
						<p>退换无忧 维修无忧</p>
					</div>
				</div>
				<div class="col-xs-3">
					<div class="pull-left">
						<i class="iconfont icon-goodsfavor"></i>
					</div>
					<div class="pull-left">
						<strong>天天低价</strong>
						<p>天天低价 帮您省钱</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-2">
				<ul class="contact">
					<li>
						<i class="iconfont icon-phone"></i>
						<strong>${setting.phone}</strong>
					</li>
					<li>
						<i class="iconfont icon-mark"></i>
						${setting.email}
					</li>
					<li>
						<i class="iconfont icon-location"></i>
						${setting.address}
					</li>
				</ul>
			</div>
			<div class="col-xs-2">
				<ul  style="margin: 0 auto">
					<li style="text-align: center;font-size: 14px;height: 30px;line-height: 30px;font-weight: 600;">客服热线</li>
					<li><img src="/resources/shop/images/kfdh.gif" class="imggif1" style="width: 75px;  height: 75px;  margin: 13px 0px 0px 66px;" ></li>
				</ul>
			</div>
			<div class="col-xs-2">
				<dl class="help">
					<dt>购物指南</dt>
					<dd>
						<a href="${base}/article/detail/16_1">免费注册</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/15_1">购物流程</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/17_1">会员等级</a>
					</dd>
				</dl>
			</div>
			<div class="col-xs-2">
				<dl class="help">
					<dt>支付方式</dt>
					<dd>
						<a href="${base}/article/detail/19_1">货到付款</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/20_1">网银支付</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/21_1">银行转帐</a>
					</dd>
				</dl>
			</div>
			<div class="col-xs-2">
				<dl class="help">
					<dt>售后服务</dt>
					<dd>
						<a href="${base}/article/detail/31_1">退换货政策</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/33_1">退换货申请</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/34_1">退款说明</a>
					</dd>
				</dl>
			</div>
			<div class="col-xs-2">
				<dl class="help">
					<dt>会员服务</dt>
					<dd>
						[#--<a href="${base}/article/member">会员入驻</a>--]
                            <a href="http://localhost:8080/article/member">会员入驻</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/37_1">商家帮助</a>
					</dd>
					<dd>
						<a href="${base}/article/detail/38_1">运营服务</a>
					</dd>
				</dl>
			</div>
		</div>
	</div>
	<div class="bottom-nav">
		[@navigation_list navigationGroupId = 3]
			[#if navigations?has_content]
				<ul class="clearfix1" style="justify-content: space-between;">
					[#list navigations as navigation]
						<li>
							<a href="${navigation.url}"[#if navigation.isBlankTarget] target="_blank"[/#if]>${navigation.name}</a>
							[#if navigation_has_next]|[/#if]
						</li>
					[/#list]
				</ul>
			[/#if]
		[/@navigation_list]
		<p>Copyright &copy; 2008-2018  版权所有</p>
	</div>
</footer>