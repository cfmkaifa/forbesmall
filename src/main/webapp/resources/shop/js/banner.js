$(function(){
    $('#carousel1').carousel({
        el : {
            imgsContainer	: '.carousel', // 图片容器
            prevBtn 		: '.carousel-prev', // 上翻按钮
            nextBtn 		: '.carousel-next', // 下翻按钮
            indexContainer	: '.carousel-index', // 下标容器
        },conf : {
            auto			: true, //是否自动播放 true/false 默认:true
            needIndexNum	: true, //是否需要下标数字 true/false 默认:true
            animateTiming	: 0, //动画时长(毫秒) 默认:1000
            autoTiming		: 3000, //自动播放间隔时间(毫秒) 默认:3000
            direction 		: 'right', //自动播放方向 left/right 默认:right
        }
    });

/*以下代码按照需要添加/修改*/
    $(".carousel-prev").hover(function(){
        $(this).find("img").attr("src","/resources/shop/images/left_btn1.png");
    },function(){
        $(this).find("img").attr("src","/resources/shop/images/left_btn1.png");
    });
    $(".carousel-next").hover(function(){
        $(this).find("img").attr("src","/resources/shop/images/right_btn1.png");
    },function(){
        $(this).find("img").attr("src","/resources/shop/images/right_btn1.png");
    });

    $("#carousel3").find('.carousel-prev,.carousel-next,.carousel-index').hide();
    $("#carousel3").hover(function(){
        $(this).find(".carousel-prev,.carousel-next,.carousel-index").stop().fadeIn(300);
    },function(){
        $(this).find(".carousel-prev,.carousel-next,.carousel-index").stop().fadeOut(300);
    });

   
    let li = $(".introduce li");
    li.click(function() {
        $(this).siblings().removeClass('active');
        $(this).addClass('active');
        var content = $(".introduce_content");
        console.log(content)
        for(var i = 0;i<content.length;i++){
            content[i].style.display = "none"
            if($(this).index()==0){
                content[0].style.display = "block"
                console.log($('.introduce_content'));
            }
            if($(this).index()==1){
                content[1].style.display = "block"
                // console.log($('.introduce_content'));
            }
            if($(this).index()==2){
                content[2].style.display = "block"
                // console.log($('.introduce_content'));
            }
            if($(this).index()==3){
                content[3].style.display = "block"
                // console.log($('.introduce_content'));
            }
        }
    })
});

