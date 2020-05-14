<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>会员中心</title>
    <link href="${base}/resources/shop/css/members.css?version=0.1" rel="stylesheet">
    <link href="${base}/favicon.ico" rel="icon">
    <link href="${base}/resources/common/css/bootstrap.css" rel="stylesheet">
    <link href="${base}/resources/common/css/iconfont.css" rel="stylesheet">
    <link href="${base}/resources/common/css/jquery.bxslider.css" rel="stylesheet">
    <link href="${base}/resources/common/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/base.css" rel="stylesheet">
    <link href="${base}/resources/shop/css/index.css?version=0.1" rel="stylesheet">
    <!--[if lt IE 9]>
		<script src="${base}/resources/common/js/html5shiv.js"></script>
		<script src="${base}/resources/common/js/respond.js"></script>
        <![endif]-->
    <script src="${base}/resources/common/js/jquery.js"></script>
    <script src="${base}/resources/common/js/bootstrap.js"></script>
    <script src="${base}/resources/common/js/jquery.lazyload.js"></script>
    <script src="${base}/resources/common/js/jquery.bxslider.js"></script>
    <script src="${base}/resources/common/js/jquery.qrcode.js"></script>
    <script src="${base}/resources/common/js/underscore.js"></script>
    <script src="${base}/resources/common/js/url.js"></script>
    <script src="${base}/resources/common/js/velocity.js"></script>
    <script src="${base}/resources/common/js/velocity.ui.js"></script>
    <script src="${base}/resources/common/js/base.js?version=0.1"></script>
    <script src="${base}/resources/shop/js/base.js"></script>

    <style type="text/css">
        .platform{
            font-size: 16px;
            color: #000000;

        }
        .platformTitle{
            margin-top: 40px;
        }
        .members{
            width: 1010px;
            margin: 0 auto;
            /* text-align: center; */
        }
        .table>tbody>tr img{
            width: 22px;
            height: 22px;
        }
        .table>thead>tr>th{
            vertical-align: top;
        }
        .table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td {
            padding: 0px !important;
            color: #333333 !important;
        }
        .rowspan {
            width: 80px;
        }
        .one {
            width: 20px;
            margin: 0 auto;
            line-height: 24px;
            font-size: 14px;
            color: #333333;
        }
        .table tr th,.table tr td {
            text-align: center;
            vertical-align: middle !important;
        }
    </style>

</head>
<body>
[#include "/shop/include/main_header.ftl" /]
<div class="members">
    <!-- <div class="membersBanner">
        <img src="/resources/shop/images/membersbanner.png" alt="">
    </div> -->
    <div>
        <div class="membershipTitle platformTitle">
            <h3>平台介绍</h3>
            <span></span>
            <p>PLATFORM IS INTRODUCED</p>
        </div>
        <div class="platform">
            <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上海让雷智能科技有限公司根据市场调查，目前中国市场还没有一款完善的与纺织品企业对接原材料采购平台，尤其是无纺布生产企业对接的原材料采购平台。同时应对与各个化纤工厂，由于化纤是传统纺织行业，属于大宗物资交易交流，化纤工厂与纺织品企业交流交易方式仍然采用传统的方式进行，鉴于此市场信息交流更新缓慢，信息不集中等特点，上海让雷智能科技有限公司联合上海缔荣纺织品有限公司投资开发CFM平台，真诚服务于纺织品企业原材料采购，尤其是无纺布生产企业对于原材料采购的需求服务，致力于打造跨行业跨领域“互联网+”一站式</p>
        </div>
    </div>
    <div class="membership">
        <div>
            <div class="membershipTitle">
                <h3>会员优享</h3>
                <span></span>
                <p>MEMBER SERVICES</p>
            </div>
            <div class="membershipContent">
                <table class="table">
                    <thead>
                    <tr>
                        <th style="width: 50px;">序号</th>
                        <th style="width: 50px;" class="rowspan">类型</th>
                        <th class="th1">
                            <div class="out">
                                <b>会员等级</b>
                                <em>会员权益</em>
                            </div>
                        </th>
                        <th class="services">普通会员</th>
                        <th class="services">黄金会员</th>
                        <th class="services">臻享会员</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr align="center">
                        <td></td>
                        <td rowspan="10"><p class="one">信息服务</p></td>
                        <td>价格</td>
                        <td class="services">3688元/年</td>
                        <td class="services">9688元/年</td>
                        <td class="services">16688元/年</td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>标样销售</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>产品展示</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>交易数据</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>产品位展示数量</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>求购信息接收</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>

                    <tr>
                        <td>6</td>
                        <td>星级优先展示</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>专业客服服务</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>8</td>
                        <td>移动APP产品展示</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>9</td>
                        <td>站内信息接收</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>10</td>
                        <td rowspan="7"><p class="one">行业销售服务</p></td>
                        <td>运输服务</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>

                    <tr>
                        <td>11</td>
                        <td>站内广告服务</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>12</td>
                        <td>发起团售</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>13</td>
                        <td>设定保证金</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>14</td>
                        <td>行业资讯接收</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>15</td>
                        <td>行业周报推送</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>16</td>
                        <td>行业数据分析推送</td>
                        <td><img src="/resources/shop/images/members4.png" ></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>17</td>
                        <td rowspan="9"><p class="one">智能数据服务</p></td>
                        <td>行业月报推送</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>18</td>
                        <td>行业季报推送</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>19</td>
                        <td>行业年报推送</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>20</td>
                        <td>海关数据月报</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>21</td>
                        <td>数据中心服务</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>22</td>
                        <td>智能工厂升级服务</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>23</td>
                        <td>供应链金融服务</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>24</td>
                        <td>区块链通证服务</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>25</td>
                        <td>产学研联合服务</td>
                        <td></td>
                        <td><img src="/resources/shop/images/members5.png" ></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>26</td>
                        <td rowspan="10"><p class="one">国际商业合作</p></td>
                        <td>新品专利服务</td>
                        <td></td>
                        <td></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>27</td>
                        <td>行业展会服务</td>
                        <td></td>
                        <td></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>28</td>
                        <td>国际贸易服务</td>
                        <td></td>
                        <td></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>29</td>
                        <td>国际专业机构服务</td>
                        <td></td>
                        <td></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td>30</td>
                        <td>专业律师服务</td>
                        <td></td>
                        <td></td>
                        <td><img src="/resources/shop/images/members6.png" ></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td><a href="${base}/business/store/payment/1" class="memberColor">立即订购</a></td>
                        <td><a href="${base}/business/store/payment/5" class="memberColor">立即订购</a></td>
                        <td><a href="${base}/business/store/payment/6" class="memberColor">立即订购</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div>
            <div class="membershipTitle platformTitle">
                <h3>产品优势</h3>
                <span></span>
                <p>PLATFORM ADVANTAGE</p>
            </div>
            <div class="advavtane">
                <div>
                    <img src="/resources/shop/images/members1.png" alt="">
                    <h3>信息全面化</h3>
                    <p>近三千家化纤工厂与无纺布工厂的加入，供采更方便更快捷</p>
                </div>
                <div>
                    <img src="/resources/shop/images/members2.png" alt="">
                    <h3>性价比高</h3>
                    <p>性价比高，划算的价格打造，给予客户更完善的体验</p>
                </div>
                <div>
                    <img src="/resources/shop/images/members3.png" alt="">
                    <h3>智能化</h3>
                    <p>避免过多的人工成本，只需点一点便可完成订单</p>
                </div>
            </div>
        </div>
        <div class="question">
            <div class="membershipTitle platformTitle">
                <h3>常见问题</h3>
                <span></span>
                <p>QUESTION ＆ ANWSER</p>
            </div>
            <div>
                <p>Q：注册时候一般需要哪些证明材料？</p>
                <p>A：您在注册的时候需要展示商家注册号与营业执照等相关文件。</p>
                <p>Q：在买家已经付款，我却没有收到款项，买家急着找我发货的时候我应该怎么办？</p>
                <p>A：您这边可以联系我们客服人员，我们会处理相关问题的。</p>
                <p>Q：我忘记密码了如何登陆？</p>
                <p>A：您可以在登陆页面点击忘记密码，根据提示进行相关操作，如果还是有问题的话，您可以联系工作人员，我们后台帮助
                    您重设密码。</p>
                <p>Q：客户买了产品需要开发票，我在哪给客户开具发票？</p>
                <p>A：在客户申请开票的情况下，您在发票管理>申请列表，找到客户提交的申请</p>

            </div>
        </div>
    </div>

</div>
[#include "/shop/include/main_footer.ftl" /]
</body>
</html>
