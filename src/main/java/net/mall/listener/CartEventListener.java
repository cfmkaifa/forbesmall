/*
 *
 * 
 *
 * 
 */
package net.mall.listener;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import net.mall.entity.Cart;
import net.mall.entity.Member;
import net.mall.event.CartEvent;
import net.mall.service.UserService;
import net.mall.util.WebUtils;

/**
 * Listener - 购物车事件
 * 
 * @author huanghy
 * @version 6.1
 */
@Component
public class CartEventListener {

	@Inject
	private UserService userService;

	/**
	 * 事件处理
	 * 
	 * @param cartEvent
	 *            购物车事件
	 */
	@EventListener
	public void handle(CartEvent cartEvent) {
		HttpServletRequest request = WebUtils.getRequest();
		HttpServletResponse response = WebUtils.getResponse();

		Cart cart = cartEvent.getCart();
		Member currentUser = userService.getCurrent(Member.class);
		if (currentUser != null) {
			WebUtils.removeCookie(request, response, Cart.KEY_COOKIE_NAME);
		} else {
			WebUtils.addCookie(request, response, Cart.KEY_COOKIE_NAME, cart.getKey(), Cart.TIMEOUT);
		}
		WebUtils.addCookie(request, response, Cart.TAG_COOKIE_NAME, cart.getTag());
	}

}