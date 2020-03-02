/*
 *
 *
 *
 *
 */
package net.mall.plugin;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import net.mall.entity.Promotion;
import net.mall.entity.PromotionDefaultAttribute;

/**
 * Plugin - 免运费
 *
 * @author huanghy
 * @version 6.1
 */
@Component("freeShippingPromotionPlugin")
public class FreeShippingPromotionPlugin extends PromotionPlugin {

    @Override
    public String getName() {
        return "免运费";
    }

    @Override
    public String getInstallUrl() {
        return "/admin/plugin/free_shipping_promotion/install";
    }

    @Override
    public String getUninstallUrl() {
        return "/admin/plugin/free_shipping_promotion/uninstall";
    }

    @Override
    public String getSettingUrl() {
        return "/admin/plugin/free_shipping_promotion/setting";
    }

    @Override
    public String getAddUrl() {
        return "/business/free_shipping_promotion/add";
    }

    @Override
    public String getEditUrl() {
        return "/business/free_shipping_promotion/edit";
    }

    @Override
    public boolean isFreeShipping(Promotion promotion) {
        FreeShippingPromotionPlugin.FreeShippingAttribute freeShippingAttribute = (FreeShippingPromotionPlugin.FreeShippingAttribute) promotion.getPromotionDefaultAttribute();
        return freeShippingAttribute.getIsFreeShipping();
    }

    /**
     * 免运费属性
     *
     * @author huanghy
     * @version 6.1
     */
    @Entity(name = "FreeShippingAttribute")
    public static class FreeShippingAttribute extends PromotionDefaultAttribute {

        private static final long serialVersionUID = -1343559914106941832L;

        /**
         * 是否免运费
         */
        @NotNull
        private Boolean isFreeShipping;

        /**
         * 获取是否免运费
         *
         * @return 是否免运费
         */
        public Boolean getIsFreeShipping() {
            return isFreeShipping;
        }

        /**
         * 设置是否免运费
         *
         * @param isFreeShipping 是否免运费
         */
        public void setIsFreeShipping(Boolean isFreeShipping) {
            this.isFreeShipping = isFreeShipping;
        }

    }

}