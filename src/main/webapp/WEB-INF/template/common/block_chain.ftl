[#noautoesc]
    [#escape x as x?js_string]
        <script>
           function blockChain(obj){
               var   $idObj = $(obj);
               var   dataId = $idObj.attr("dataId");
               var   serviceUrl = "${base}" + $idObj.attr("dataUrl");
               try {
                   $.ajax({
                       type: "POST",
                       url: serviceUrl,
                       data: {
                           "dataId": dataId
                       },
                       contentType: "application/x-www-form-urlencoded",
                       dataType: "json",
                       success: function (data) {//返回数据根据结果进行相应的处理
                           $("#accountAddress").html("");
                           $("#accountAddress").html(data.accountAddress);
                           $("#linkValue").html("");
                           $("#linkValue").html(data.value);
                           $("#linkCreateDate").html("");
                           $("#linkCreateDate").html(data.chainTransTime);
                           $("#linkHeight").html("");
                           $("#linkHeight").html(data.height);
                           $("#linkImgAddress").html("");
                           $("#linkImgAddress").html(data.ossFileUrl);
                           $("#hashAddress").html("");
                           $("#hashAddress").html(data.hash);
                           $("#blockChainModal").modal('show');
                       }
                   });
               }catch(e){
                   console.log("============"+e);
               }
           }
        </script>
    [/#escape]
[/#noautoesc]
<style>
    .blockChainContent{
        width: 600px !important;
    }
    .block-chain-title{
        background: url(/resources/common/images/blockBackground.png) no-repeat;
        background-size: 100%;
        height: 190px;
        border-bottom: none;
    }
    .block-chain-title>h4{
        color: #FFFFFF;
        font-size: 60px;
        text-align: center;
        line-height: 150px;
    }
    .block-chain-title>button,.block-chain-title>button:hover{
        color: #FFFFFF;
        border: 1px solid #FFFFFF;
        border-radius: 50%;
        opacity: 1;
        font-size: 18px;
        width: 18px;
    }
    .block-chain-body{
        width: 90%;
        margin: 0 auto;
        border-radius:10px;
        margin-top: -80px;
        background: #FFFFFF;
        z-index: 10;
        text-align: center;
    }
    .block-chain-body ul{
        padding-top: 20px;
    }
    .block-chain-body ul li{
        display: flex;
        height: 40px;
        font-size: 14px;
        padding: 0 10px;
    }
    .block-chain-body ul li .blockChainAccountAddress{
        width: 26% !important;
        text-align: left
    }
</style>
<!--链路信息start-->
<div id="blockChainModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-lg blockChainContent">
        <div class="modal-content">
            <div class="modal-header block-chain-title">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">${message("blockChain.title")}</h4>
            </div>
            <div class="modal-body">
                <div class="block-chain-body">
                    <ul>
                        <li>
                            <p class="blockChainAccountAddress">${message("blockChain.accountAddress")}：</p>
                            <p id="accountAddress" style="word-wrap: break-word; word-break: normal;width: 74%;text-align: left"></p>
                        </li>
                        <li>
                            <p class="blockChainAccountAddress">${message("blockChain.linkValue")}：</p>
                            <p  id="linkValue" style="word-wrap: break-word; word-break: normal;width: 74%;text-align: left"></p>
                        </li>
                        <li>
                            <p class="blockChainAccountAddress">${message("blockChain.linkCreateDate")}：</p>
                            <p  id="linkCreateDate" style="word-wrap: break-word; word-break: normal;width: 74%;text-align: left"></p>
                        </li>
                        <li>
                            <p class="blockChainAccountAddress">${message("blockChain.linkHeight")}：</p>
                            <p  id="linkHeight" style="word-wrap: break-word; word-break: normal;width: 74%;text-align: left"></p>
                        </li>
                        <li>
                            <p class="blockChainAccountAddress">${message("blockChain.linkImgAddress")}：</p>
                            <p  id="linkImgAddress" style="word-wrap: break-word; word-break: normal;width: 74%;text-align: left"></p>
                        </li>
                        <li>
                            <p class="blockChainAccountAddress">${message("blockChain.hashAddress")}：</p>
                            <p  id="hashAddress" style="word-wrap: break-word; word-break: normal;width: 74%;text-align: left"></p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal">${message("common.cancel")}</button>
            </div>
        </div>
    </div>
</div>
<!--链路信息end-->