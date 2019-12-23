/*
 *
 * 
 *
 * 
 */
package net.mall.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 友情链接
 * 
 * @author huanghy
 * @version 6.1
 */
@Controller("shopFriendLinkController")
@RequestMapping("/friend_link")
public class FriendLinkController extends BaseController {

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		return "shop/friend_link/index";
	}

}