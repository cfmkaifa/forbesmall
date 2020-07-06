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
                           $("#accountAddress").html(data.accountAddress);
                           $("#linkValue").html(data.value);
                           $("#remark").html(data.memo);
                           $("#linkCreateDate").html(data.chainTransTime);
                           $("#linkHeight").html(data.height);
                           $("#linkImgAddress").html(data.ossFileUrl);
                           $("#hashAddress").html(data.hash);
                           $("#blockChainModal").modal('show');
                       }
                   });
               }catch(e){
                   console.log(e);
               }
           }
        </script>
    [/#escape]
[/#noautoesc]
<style>
    h5.blockTitle{
        text-align: center !important;
        font-size: 14px !important;
    }
    #blockChainModal .blockChains{
        height: 30px !important;
        line-height: 30px;
        font-size: 14px !important;
    }
</style>
<!--订单合同start-->
<div id="blockChainModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">&times;</button>
                <h5 class="modal-title blockTitle">${message("blockChain.title")}</h5>
            </div>
            <div class="modal-body">
                <div class="row blockChains">
                    <div class="col-xs-4">
                        <span class="blockChainTitle">${message("blockChain.accountAddress")}：</span>
                    </div>
                    <div class="col-xs-8">
                        <span id="accountAddress"></span>
                    </div>
                </div>
                <div class="row blockChains">
                    <div class="col-xs-4">
                        <span class="blockChainTitle">${message("blockChain.linkValue")}：</span>
                    </div>
                    <div class="col-xs-8">
                        <span id="linkValue"></span>
                    </div>
                </div>
                <div class="row blockChains">
                    <div class="col-xs-4">
                        <span class="blockChainTitle">${message("blockChain.remark")}：</span>
                    </div>
                    <div class="col-xs-8">
                        <span id="remark"></span>
                    </div>
                </div>
                <div class="row blockChains">
                    <div class="col-xs-4">
                        <span class="blockChainTitle">${message("blockChain.linkCreateDate")}：</span>
                    </div>
                    <div class="col-xs-8">
                        <span id="linkCreateDate"></span>
                    </div>
                </div>
                <div class="row blockChains">
                    <div class="col-xs-4">
                        <span class="blockChainTitle">${message("blockChain.linkHeight")}：</span>
                    </div>
                    <div class="col-xs-8">
                        <span id="linkHeight"></span>
                    </div>
                </div>
                <div class="row blockChains">
                    <div class="col-xs-4">
                        <span class="blockChainTitle">${message("blockChain.linkImgAddress")}：</span>
                    </div>
                    <div class="col-xs-8">
                        <span id="linkImgAddress"></span>
                    </div>
                </div>
                <div class="row blockChains">
                    <div class="col-xs-4">
                        <span class="blockChainTitle">${message("blockChain.hashAddress")}：</span>
                    </div>
                    <div class="col-xs-8">
                        <span id="hashAddress"></span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" type="button" data-dismiss="modal">${message("common.cancel")}</button>
            </div>
        </div>
    </div>
</div>
<!--订单合同end-->