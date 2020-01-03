$(function(){
    let li = $(".introduce li");
    li.click(function() {
        $(this).siblings().removeClass('active');
        $(this).addClass('active');
        var content = $(".smartFactory_content");
        console.log(content)
        for(var i = 0;i<content.length;i++){
            content[i].style.display = "none"
            if($(this).index()==0){
                content[0].style.display = "block"
                console.log($('.smartFactory_content'));
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
            if($(this).index()==4){
                content[4].style.display = "block"
                // console.log($('.introduce_content'));
            }
        }

    })
})