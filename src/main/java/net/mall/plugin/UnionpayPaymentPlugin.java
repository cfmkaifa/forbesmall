/*
 *
 *
 *
 *
 */
package net.mall.plugin;

import net.mall.Filter;
import net.mall.entity.Order;
import net.mall.entity.PaymentTransaction;
import net.mall.entity.SupplierPluginConfig;
import net.mall.service.SupplierPluginConfigService;
import net.mall.util.ConvertUtils;
import net.mall.util.SecurityUtils;
import net.mall.util.WebUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.*;

/**
 * Plugin - 银联在线支付
 *
 * @author huanghy
 * @version 6.1
 */
@Component("unionpayPaymentPlugin")
public class UnionpayPaymentPlugin extends PaymentPlugin {


    @Inject
    private SupplierPluginConfigService supplierPluginConfigService;


    /**
     * https://gateway.95516.com/gateway/api/frontTransReq.do
     * 交易请求URL
     */
    private static final String TRANS_REQUEST_URL = "https://gateway.test.95516.com/gateway/api/frontTransReq.do";

    /**
     * https://gateway.95516.com/gateway/api/backTransReq.do
     * 查询请求URL
     */
    private static final String QUERY_REQUEST_URL = "https://gateway.test.95516.com/gateway/api/backTransReq.do";

    @Override
    public String getName() {
        return "银联在线支付";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "";
    }

    @Override
    public String getSiteUrl() {
        return "";
    }

    @Override
    public String getInstallUrl() {
        return "/admin/plugin/unionpay_payment/install";
    }

    @Override
    public String getUninstallUrl() {
        return "/admin/plugin/unionpay_payment/uninstall";
    }

    @Override
    public String getSettingUrl() {
        return "/admin/plugin/unionpay_payment/setting";
    }

    @Override
    public void payHandle(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        Order order = paymentTransaction.getOrder();
        String certId = getCertId();
        String merchantId = getMerchantId();
        PrivateKey privateKey = null;
        if (ConvertUtils.isNotEmpty(order)
                && ConvertUtils.isNotEmpty(order.getGroup())
                && !order.getGroup()) {
            List<Filter> filters = new ArrayList<Filter>();
            filters.add(new Filter("supplierId", Filter.Operator.EQ, order.getStore().getBusiness().getId()));
            filters.add(new Filter("pluginId", Filter.Operator.EQ, paymentPlugin.getPluginConfig().getPluginId()));
            List<SupplierPluginConfig> supplierPluginConfigs = supplierPluginConfigService.findList(0, 1, filters, null);
            if (ConvertUtils.isNotEmpty(supplierPluginConfigs)) {
                SupplierPluginConfig supplierPluginConfig = supplierPluginConfigs.get(0);
                certId = supplierPluginConfig.getAttribute("certId");
                merchantId = supplierPluginConfig.getAttribute("merchantId");
                String key = supplierPluginConfig.getAttribute("key");
                privateKey = (PrivateKey) SecurityUtils.generatePrivateKey(key, SecurityUtils.RSA_KEY_ALGORITHM);
            }
        }
        parameterMap.put("version", "5.1.0");
        parameterMap.put("encoding", "UTF-8");
        parameterMap.put("certId", certId);
        parameterMap.put("signMethod", "01");
        parameterMap.put("txnType", "01");
        parameterMap.put("txnSubType", "01");
        parameterMap.put("bizType", "000201");
        parameterMap.put("channelType", "07");
        parameterMap.put("accessType", "0");
        parameterMap.put("merId", merchantId);
        parameterMap.put("frontUrl", getPostPayUrl(paymentPlugin, paymentTransaction));
        parameterMap.put("backUrl", getPostPayUrl(paymentPlugin, paymentTransaction));
        parameterMap.put("orderId", paymentTransaction.getSn());
        parameterMap.put("currencyCode", "156");
        parameterMap.put("txnAmt", String.valueOf(paymentTransaction.getAmount().multiply(new BigDecimal(100)).setScale(0)));
        parameterMap.put("txnTime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        parameterMap.put("signature", generateSign(parameterMap, privateKey));
        modelAndView.addObject("requestUrl", TRANS_REQUEST_URL);
        modelAndView.addObject("requestMethod", "post");
        modelAndView.addObject("parameterMap", parameterMap);
        modelAndView.setViewName(PaymentPlugin.DEFAULT_PAY_VIEW_NAME);
    }

    @Override
    public boolean isPaySuccess(PaymentPlugin paymentPlugin, PaymentTransaction paymentTransaction, String paymentDescription, String extra, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        Order order = paymentTransaction.getOrder();
        String certId = getCertId();
        String merchantId = getMerchantId();
        PrivateKey privateKey = null;
        if (ConvertUtils.isNotEmpty(order)) {
            List<Filter> filters = new ArrayList<Filter>();
            filters.add(new Filter("supplierId", Filter.Operator.EQ, order.getStore().getBusiness().getId()));
            filters.add(new Filter("pluginId", Filter.Operator.EQ, paymentPlugin.getPluginConfig().getPluginId()));
            List<SupplierPluginConfig> supplierPluginConfigs = supplierPluginConfigService.findList(0, 1, filters, null);
            if (ConvertUtils.isNotEmpty(supplierPluginConfigs)) {
                SupplierPluginConfig supplierPluginConfig = supplierPluginConfigs.get(0);
                certId = supplierPluginConfig.getAttribute("certId");
                merchantId = supplierPluginConfig.getAttribute("merchantId");
                String key = supplierPluginConfig.getAttribute("key");
                privateKey = (PrivateKey) SecurityUtils.generatePrivateKey(key, SecurityUtils.RSA_KEY_ALGORITHM);
            }
        }
        parameterMap.put("version", "5.1.0");
        parameterMap.put("encoding", "UTF-8");
        parameterMap.put("certId", certId);
        parameterMap.put("signMethod", "01");
        parameterMap.put("txnType", "00");
        parameterMap.put("txnSubType", "00");
        parameterMap.put("bizType", "000201");
        parameterMap.put("accessType", "0");
        parameterMap.put("merId", merchantId);
        parameterMap.put("orderId", paymentTransaction.getSn());
        parameterMap.put("txnTime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        parameterMap.put("signature", generateSign(parameterMap, privateKey));
        String result = WebUtils.post(QUERY_REQUEST_URL, parameterMap);
        Map<String, String> resultMap = WebUtils.parse(result);
        return StringUtils.equals(resultMap.get("respCode"), "00") && StringUtils.equals(resultMap.get("origRespCode"), "00") && paymentTransaction.getAmount().multiply(new BigDecimal(100)).compareTo(new BigDecimal(resultMap.get("txnAmt"))) == 0;
    }

    /**
     * 商户代码
     *
     * @return 商户代码
     */
    private String getMerchantId() {
        return getAttribute("merchantId");
    }

    /**
     * 证书ID
     *
     * @return 证书ID
     */
    private String getCertId() {
        return getAttribute("certId");
    }

    /**
     * 获取私钥
     *
     * @return 私钥
     */
    private PrivateKey getPrivateKey() {
        return (PrivateKey) SecurityUtils.generatePrivateKey(getAttribute("key"), SecurityUtils.RSA_KEY_ALGORITHM);
    }

    /**
     * 生成签名
     *
     * @param parameterMap 参数
     * @return 签名
     */
    private String generateSign(Map<String, ?> parameterMap, PrivateKey privateKey) {
        byte[] data = sha256Hex(joinKeyValue(new TreeMap<>(parameterMap), null, null, "&", true, "signature"));
        if (ConvertUtils.isEmpty(privateKey)) {
            privateKey = getPrivateKey();
        }
        byte[] sign = SecurityUtils.sign("SHA256withRSA", privateKey, data);
        return new String(Base64.encodeBase64(sign));
    }

    /**
     * SHA256摘要加密字节并转换成16进制
     *
     * @param data 数据
     * @return SHA256摘要加密字节
     */
    private byte[] sha256Hex(String data) {
        try {
            return DigestUtils.sha256Hex(data.getBytes("UTF-8")).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}