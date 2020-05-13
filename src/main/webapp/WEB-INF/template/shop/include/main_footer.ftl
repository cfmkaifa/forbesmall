<footer class="main-footer">
    <div class="container">
        <div class="promise">
            <div class="row">
                <div class="col-xs-3">
                    <div class="pull-left">
                        <i class="iconfont icon-roundcheck"></i>
                    </div>
                    <div class="pull-left">
                        <strong>简化采购流程</strong>
                        <p>标准规范 采购无忧</p>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="pull-left">
                        <i class="iconfont icon-time"></i>
                    </div>
                    <div class="pull-left">
                        <strong>节约采购成本</strong>
                        <p>团购集采 帮您省钱</p>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="pull-left">
                        <i class="iconfont icon-like"></i>
                    </div>
                    <div class="pull-left">
                        <strong>加速新品开发</strong>
                        <p>产学配合 高效服务</p>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="pull-left">
                        <i class="iconfont icon-goodsfavor"></i>
                    </div>
                    <div class="pull-left">
                        <strong>促进合规化管理</strong>
                        <p>规避采购风险 促进企业升级</p>
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
                <ul style="margin: 0 auto">
                    <li style="text-align: center;font-size: 14px;height: 30px;line-height: 30px;font-weight: 600;">
                        客服热线
                    </li>
                    <li><img src="${base}/resources/shop/images/kfdh.gif" class="imggif1"  style="width: 75px;  height: 75px;  margin: 4px 0px 0px 63px;">
                             </li>
                     <li style="text-align: center;font-size: 14px;height: 30px;line-height: 30px;font-weight: 600;">
                         021-61833518
                     </li>
                </ul>
            </div>
            <div class="col-xs-2">
                <dl class="help">
                    <dt>平台规则</dt>
                    <dd>
                        <a href="${base}/article/detail/17_1">平台服务协议</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/16_1">平台交易规则</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/15_1">购物流程</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/14_1">会员等级</a>
                    </dd>
                </dl>
            </div>
            <div class="col-xs-2">
                <dl class="help">
                    <dt>合同模板</dt>
                    <dd>
                        <a href="${base}/article/detail/22_1">产品采购合同</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/21_1">货到付款</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/20_1">网银支付</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/19_1">银行转帐</a>
                    </dd>
                </dl>
            </div>
            <div class="col-xs-2">
                <dl class="help">
                    <dt>支付方式</dt>
                    <dd>
                        <a href="${base}/article/detail/30_1">线下打款</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/27_1">银行承兑汇票</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/28_1">退换货政策</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/29_1">退换货申请</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/10466_1">退款说明</a>
                    </dd>

                </dl>
            </div>
            <div class="col-xs-2">
                <dl class="help">
                    <dt>供应商服务</dt>
                    <dd>
                        [#--<a href="${base}/article/member">会员入驻</a>--]
                        <a href="${base}/article/member">会员入驻</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/10470_1">服务热线</a>
                    </dd>
                    <dd>
                        <a href="${base}/article/detail/10469_1">商家帮助</a>
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
        <p>Copyright © 2020 CFM All Rights Reserved. 上海让雷智能科技有限公司 版权所有</p>
        <p>上海市青浦区虹桥世界中心L2-B栋503室  客服电话021-61833519</p>
        <div class="record">
            <div style="text-align:center; margin-top:10px;">
                <a href="https://rz.ce9000.org/QX8687805698.html">
                    <img src="https://rz.ce9000.org/cec/qxrz.png" alt="企信验证实名网站" width="128" height="47" border="0" />
                </a>
            </div>
            <div class="foots">
                <p><a href="https://tsm.miit.gov.cn" target="_blank">沪B2-20200070</a></p>
                <p><a href="http://www.beian.miit.gov.cn/" target="_blank">沪ICP备17024305号-2</a></p>
            </div>
        </div>
    </div>
</footer>