/*
 *
 * 
 *
 * 
 */
package net.mall.plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import net.mall.entity.Promotion;
import net.mall.entity.PromotionDefaultAttribute;
import net.mall.entity.Sku;
import net.mall.entity.Store;

/**
 * Plugin - 赠品
 * 
 * @author huanghy
 * @version 6.1
 */
@Component("giftPromotionPlugin")
public class GiftPromotionPlugin extends PromotionPlugin {

	@Override
	public String getName() {
		return "赠品";
	}

	@Override
	public String getInstallUrl() {
		return "/admin/plugin/gift_promotion/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/plugin/gift_promotion/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/plugin/gift_promotion/setting";
	}

	@Override
	public String getAddUrl() {
		return "/business/gift_promotion/add";
	}

	@Override
	public String getEditUrl() {
		return "/business/gift_promotion/edit";
	}

	@Override
	public List<Sku> getGifts(Promotion promotion, Store store) {
		GiftPromotionPlugin.GiftAttribute giftAttribute = (GiftPromotionPlugin.GiftAttribute) promotion.getPromotionDefaultAttribute();
		return new ArrayList<>(giftAttribute.getGifts());
	}

	/**
	 * 赠品属性
	 * 
	 * @author huanghy
	 * @version 6.1
	 */
	@Entity(name = "GiftAttribute")
	public static class GiftAttribute extends PromotionDefaultAttribute {

		private static final long serialVersionUID = -1996945993194404912L;

		/**
		 * 赠品
		 */
		@NotNull
		@ManyToMany(fetch = FetchType.LAZY)
		private Set<Sku> gifts = new HashSet<>();

		/**
		 * 获取赠品
		 * 
		 * @return 赠品
		 */
		public Set<Sku> getGifts() {
			return gifts;
		}

		/**
		 * 设置赠品
		 * 
		 * @param gifts
		 *            赠品
		 */
		public void setGifts(Set<Sku> gifts) {
			this.gifts = gifts;
		}
	}

}