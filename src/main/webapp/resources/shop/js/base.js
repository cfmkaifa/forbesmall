/*
 *
 * 
 *
 * 
 * JavaScript - Base
 * Version: 6.1
 */

(function($) {

	// 解析推广Url
	var spreadUrlMatch = /SPREAD_([^&#]+)/.exec(location.href);
	if (spreadUrlMatch != null && spreadUrlMatch.length >= 2) {
		if ($.base64 == null) {
			throw new Error("spreadUrlMatch requires jquery.base64.js");
		}
		$.setSpreadUser({
			username: $.base64.decode(spreadUrlMatch[1])
		});
	}

})(jQuery);

$().ready(function() {

	$("#get_ct_more").click(function(){
		$(".get_ct_more").hide();
		$(".details-a").css("height","auto");
	})
	$(".xx").click(function(){
		$(".modoe").hide();
		$("#cover").hide();
	})

	$(".detailsul li").click(function () {
		$(this).addClass("actives").siblings().removeClass('actives');
	})
	$(".detailsul a").each(function(){
		if($(this)[0].href==String(window.location)){

		}
	});
	$(window).scroll(function(){
		if($(window).scrollTop() >= 270){
			$(".nav-pills").css("top","20px");
		} else{
			$(".nav-pills").css("top","270px");
		}
	});
	var $pageNumberItem = $("[data-page-number]");
	
	// 页码
	$pageNumberItem.click(function() {
		var $element = $(this);
		var $form = $element.closest("form");
		var $pageNumber = $form.find("input[name='pageNumber']");
		var pageNumber = $element.data("page-number");
		
		if ($form.length > 0) {
			if ($pageNumber.length > 0) {
				$pageNumber.val(pageNumber);
			} else {
				$form.append('<input name="pageNumber" type="hidden" value="' + pageNumber + '">');
			}
			$form.submit();
			return false;
		}
	});

});