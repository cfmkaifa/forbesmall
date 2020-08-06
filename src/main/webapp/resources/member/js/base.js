/*
 *
 * 
 *
 * 
 * JavaScript - Base
 * Version: 6.1
 */

$().ready(function() {

	var $pageNumber = $("input[name='pageNumber']");
	var $button = $(".btn");
	var $deleteAction = $("[data-action='delete']");
	var $pageNumberItem = $("[data-page-number]");
	
	// 按钮
	$button.click(function() {
		var $element = $(this);
		
		if ($.support.transition) {
			$element.addClass("btn-clicked").one("bsTransitionEnd", function() {
				$(this).removeClass("btn-clicked");
			}).emulateTransitionEnd(300);
		}
	});
	
	// 删除
	$deleteAction.on("success.mall.delete", function(event) {
		var $element = $(event.target);
		
		if ($.fn.velocity == null) {
			throw new Error("Delete requires velocity.js");
		}
		
		$element.closest("li, tr, .media, [class^='col-']").velocity("slideUp", {
			complete: function() {
				$(this).remove();
				if ($("[data-action='delete']").length < 1) {
					location.reload(true);
				}
			}
		});
	});
	
	// 页码
	$pageNumberItem.click(function() {
		var $element = $(this);
		
		$pageNumber.val($element.data("page-number")).closest("form").submit();
		return false;
	});

	$(".login-switcli").click(function () {
		$(".passwords-login").show();
		$(".iphone-login").hide();

	})
	$(".login-switcli2").click(function () {
		$(".passwords-login").hide();
		$(".iphone-login").show();
	})
	$(".login-switch li").click(function () {
		$(this).addClass("login-weight").siblings().removeClass('login-weight')
	})
});