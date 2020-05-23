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
        server_url: 'http://fiber.datasink.sensorsdata.cn/sa?project=production&token=59f587d85e2799d7'
    });
    sensors.quick('autoTrack');
    // 如果需要调用 login 来重新设置用户标识，必须在此之前调用
    sensors.quick('autoTrack', {
        platform:'h5'
    })
    // 公共属性
    sensors.registerPage({
        platform: "${base}",
        user_type:"${Session.userType}",
        is_login:${Session.isLogin}
    });

    //设置用户表内容
    sensors.setProfile({
        email:"${Session.email}",
        user_name:"${Session.username}",
        last_login_date:"${Session.lastLoginDate}",
        phone_number:"${Session.phone_number}",
        register_time:"${Session.register_time}",
        first_order_time:"${Session.first_order_time}",
        last_order_time:"${Session.last_order_time}",
        vip_level:"${Session.vip_level}"
    });
    // SDK 初始化参数配置
    heatmap: {
        //是否开启点击图，默认 default 表示开启，自动采集 $WebClick 事件，可以设置 'not_collect' 表示关闭
        //需要 Web JS SDK 版本号大于 1.7
        clickmap:'default'
    }
</script>