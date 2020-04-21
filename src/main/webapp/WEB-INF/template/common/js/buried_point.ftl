<script>
    (function(para) {
        var p = para.sdk_url, n = para.name, w = window, d = document, s = 'script',x = null,y = null;
        if(typeof(w['sensorsDataAnalytic201505']) !== 'undefined') {
            return false;
        }
        w['sensorsDataAnalytic201505'] = n;
        w[n] = w[n] || function(a) {return function() {(w[n]._q = w[n]._q || []).push([a, arguments]);}};
        var ifs = ['track','quick','register','registerPage','registerOnce','trackSignup', 'trackAbtest', 'setProfile','setOnceProfile','appendProfile', 'incrementProfile', 'deleteProfile', 'unsetProfile', 'identify','login','logout','trackLink','clearAllRegister','getAppStatus'];
        for (var i = 0; i < ifs.length; i++) {
            w[n][ifs[i]] = w[n].call(null, ifs[i]);
        }
        if (!w[n]._t) {
            x = d.createElement(s), y = d.getElementsByTagName(s)[0];
            x.async = 1;
            x.src = p;
            x.setAttribute('charset','UTF-8');
            w[n].para = para;
            y.parentNode.insertBefore(x, y);
        }
    })({
        sdk_url: 'https://cdn.jsdelivr.net/npm/sa-sdk-javascript@1.15.1/sensorsdata.min.js',
        heatmap_url: 'https://cdn.jsdelivr.net/npm/sa-sdk-javascript@1.15.1/heatmap.min.js',
        name: 'sensors',
        server_url: 'http://fiber.datasink.sensorsdata.cn/sa?token=59f587d85e2799d7'
    });
    sensors.quick('autoTrack');
    // 如果需要调用 login 来重新设置用户标识，必须在此之前调用
    sensors.quick('autoTrack', {
        platform:'h5'
    })
</script>