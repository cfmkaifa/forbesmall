
<style>
    body{
        height: 1862px;
    }
    .rights{
        position: fixed;
        right: 7%;
        top: 25%;
        z-index: 9999;

    }
    .rights > img {
        max-width: 100%;
    }
    .off{
        position: absolute;
        top: -8px; right: -25px;
        width: 15px;
        font-size: 12px;
        text-align: center;
        border: 1px solid #999999;
        background: #dddddd;
        color: #666666;
        cursor: pointer;
    }
</style>
    <div class="rights">
        <img src="${base}/resources/common/images/right.png" alt="">
        <div class="off">关闭</div>
    </div>
[#noautoesc]
[#escape x as x?js_string]
    <script>
        $().ready(function () {
            let img = document.getElementsByTagName('img')[0]
            let off = document.getElementsByClassName('off')[0]
            off.onclick = function () {
                img.style.display = 'none'
                off.style.display = 'none'
            }
        })
    </script>
[/#escape]
[/#noautoesc]