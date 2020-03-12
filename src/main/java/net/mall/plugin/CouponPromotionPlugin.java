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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import net.mall.entity.Coupon;
import net.mall.entity.Promotion;
import net.mall.entity.PromotionDefaultAttribute;
import net.mall.entity.Store;

/**
 * Plugin - 优惠券
 *
 * @author huanghy
 * @version 6.1
 */
@Component("couponPromotionPlugin")
public class CouponPromotionPlugin extends PromotionPlugin {

    @Override
    public String getName() {
        return "优惠券";
    }

    @Override
    public String getInstallUrl() {
        return "/admin/plugin/coupon_promotion/install";
    }

    @Override
    public String getUninstallUrl() {
        return "/admin/plugin/coupon_promotion/uninstall";
    }

    @Override
    public String getSettingUrl() {
        return "/admin/plugin/coupon_promotion/setting";
    }

    @Override
    public String getAddUrl() {
        return "/business/coupon_promotion/add";
    }

    @Override
    public String getEditUrl() {
        return "/business/coupon_promotion/edit";
    }

    @Override
    public List<Coupon> getCoupons(Promotion promotion, Store store) {
        CouponPromotionPlugin.CouponAttribute couponAttribute = (CouponPromotionPlugin.CouponAttribute) promotion.getPromotionDefaultAttribute();
        return new ArrayList<>(couponAttribute.getCoupons());
    }

    /**
     * 优惠券属性
     *
     * @author huanghy
     * @version 6.1
     */
    @Entity(name = "CouponAttribute")
    public static class CouponAttribute extends PromotionDefaultAttribute {

        private static final long serialVersionUID = -616862055443133271L;

        /**
         * 优惠券
         */
        @NotNull
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "CouponAttribute_Coupon")
        private Set<Coupon> coupons = new HashSet<>();

        /**
         * 获取优惠券
         *
         * @return 优惠券
         */
        public Set<Coupon> getCoupons() {
            return coupons;
        }

        /**
         * 设置优惠券
         *
         * @param coupons 优惠券
         */
        public void setCoupons(Set<Coupon> coupons) {
            this.coupons = coupons;
        }
    }

}