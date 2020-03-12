/*
 *
 *
 *
 *
 */
package net.mall.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;

import net.mall.plugin.LoginPlugin;
import net.mall.plugin.PaymentPlugin;
import net.mall.plugin.PromotionPlugin;
import net.mall.plugin.StoragePlugin;
import net.mall.service.PluginService;

/**
 * Service - 插件
 *
 * @author huanghy
 * @version 6.1
 */
@Service
public class PluginServiceImpl implements PluginService {

    @Inject
    private List<PaymentPlugin> paymentPlugins = new ArrayList<>();
    @Inject
    private List<StoragePlugin> storagePlugins = new ArrayList<>();
    @Inject
    private List<LoginPlugin> loginPlugins = new ArrayList<>();
    @Inject
    private List<PromotionPlugin> promotionPlugins = new ArrayList<>();
    @Inject
    private Map<String, PaymentPlugin> paymentPluginMap = new HashMap<>();
    @Inject
    private Map<String, StoragePlugin> storagePluginMap = new HashMap<>();
    @Inject
    private Map<String, LoginPlugin> loginPluginMap = new HashMap<>();
    @Inject
    private Map<String, PromotionPlugin> promotionPluginMap = new HashMap<>();

    @Override
    public List<PaymentPlugin> getPaymentPlugins() {
        Collections.sort(paymentPlugins);
        return paymentPlugins;
    }

    @Override
    public List<StoragePlugin> getStoragePlugins() {
        Collections.sort(storagePlugins);
        return storagePlugins;
    }

    @Override
    public List<LoginPlugin> getLoginPlugins() {
        Collections.sort(loginPlugins);
        return loginPlugins;
    }

    @Override
    public List<PromotionPlugin> getPromotionPlugins() {
        Collections.sort(promotionPlugins);
        return promotionPlugins;
    }

    @Override
    public List<PaymentPlugin> getPaymentPlugins(final boolean isEnabled) {
        List<PaymentPlugin> result = new ArrayList<>();
        CollectionUtils.select(paymentPlugins, new Predicate() {
            public boolean evaluate(Object object) {
                PaymentPlugin paymentPlugin = (PaymentPlugin) object;
                return paymentPlugin.getIsEnabled() == isEnabled;
            }
        }, result);
        Collections.sort(result);
        return result;
    }

    @Override
    public List<PaymentPlugin> getActivePaymentPlugins(final HttpServletRequest request) {
        List<PaymentPlugin> result = new ArrayList<>();
        CollectionUtils.select(getPaymentPlugins(true), new Predicate() {
            public boolean evaluate(Object object) {
                PaymentPlugin paymentPlugin = (PaymentPlugin) object;
                return paymentPlugin.supports(request);
            }
        }, result);
        return result;
    }

    @Override
    public List<StoragePlugin> getStoragePlugins(final boolean isEnabled) {
        List<StoragePlugin> result = new ArrayList<>();
        CollectionUtils.select(storagePlugins, new Predicate() {
            public boolean evaluate(Object object) {
                StoragePlugin storagePlugin = (StoragePlugin) object;
                return storagePlugin.getIsEnabled() == isEnabled;
            }
        }, result);
        Collections.sort(result);
        return result;
    }

    @Override
    public List<LoginPlugin> getLoginPlugins(final boolean isEnabled) {
        List<LoginPlugin> result = new ArrayList<>();
        CollectionUtils.select(loginPlugins, new Predicate() {
            public boolean evaluate(Object object) {
                LoginPlugin loginPlugin = (LoginPlugin) object;
                return loginPlugin.getIsEnabled() == isEnabled;
            }
        }, result);
        Collections.sort(result);
        return result;
    }

    @Override
    public List<LoginPlugin> getActiveLoginPlugins(final HttpServletRequest request) {
        List<LoginPlugin> result = new ArrayList<>();
        List<LoginPlugin> loginPlugins = getLoginPlugins(true);
        CollectionUtils.select(loginPlugins, new Predicate() {
            public boolean evaluate(Object object) {
                LoginPlugin loginPlugin = (LoginPlugin) object;
                return loginPlugin.supports(request);
            }
        }, result);
        return result;
    }

    @Override
    public List<PromotionPlugin> getPromotionPlugins(final boolean isEnabled) {
        List<PromotionPlugin> result = new ArrayList<>();
        CollectionUtils.select(promotionPlugins, new Predicate() {
            public boolean evaluate(Object object) {
                PromotionPlugin promotionPlugin = (PromotionPlugin) object;
                return promotionPlugin.getIsEnabled() == isEnabled;
            }
        }, result);
        Collections.sort(result);
        return result;
    }

    @Override
    public PaymentPlugin getPaymentPlugin(String id) {
        return paymentPluginMap.get(id);
    }

    @Override
    public StoragePlugin getStoragePlugin(String id) {
        return storagePluginMap.get(id);
    }

    @Override
    public LoginPlugin getLoginPlugin(String id) {
        return loginPluginMap.get(id);
    }

    @Override
    public PromotionPlugin getPromotionPlugin(String id) {
        return promotionPluginMap.get(id);
    }

}