package net.mall.util;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

/****请求工具
 */
public class RestTemplateUtil {

    /***服务器地址
     */
    private static final String serviceUrl = "https://fubu-chain-svc-test.zhongan.io/chainQuery";



    /***初始化配置
     */
   public  static RestTemplate initRestTemplate() {
       RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                HttpHeaders headers = httpRequest.getHeaders();
                headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }
        });
        return restTemplate;
    }


    /***
     * 查询结果
     * @param busType
     * @return
     * @throws URISyntaxException
     */
    public static ResponseEntity<BusResult> reqTemplate(String idNo, String busType)
            throws URISyntaxException, UnsupportedEncodingException {
        String sign = signStr(busType,idNo);
        java.net.URI uri = new java.net.URI(serviceUrl);
        Map<String,String> reqMap = new LinkedHashMap<String,String>();
        reqMap.put("bizType",busType);
        reqMap.put("idNo",idNo);
        reqMap.put("sign",sign);
        RestTemplate restTemplate =  initRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Accept","application/json;charset=utf-8");
        HttpEntity<Map<String,String>> requestEntity = new HttpEntity<Map<String,String>>(reqMap, requestHeaders);
       return restTemplate.postForEntity(uri,requestEntity,BusResult.class);
    }


    /***
     * 加密
     * @param bizType
     * @param idNo
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String signStr (String bizType,String idNo)
            throws UnsupportedEncodingException {
        String salt = "fubu-chain";
        String orgStr ="bizType="+bizType+"&idNo="+idNo+salt;
        String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(orgStr.getBytes("UTF-8"));
        return  sign;
    }


    /***返回数据
     */
   public static class BusResult{

        /**链账户地址*/;
        private String accountAddress;
        /***上链值*/
        private String value;
        /**备注**/
        private String memo;
        /**上链时间**/
        private String chainTransTime;
        /***高度**/
        private String height;
        /***图片的上传地址**/
        private String ossFileUrl;
        /**hash地址**/
        private String hash;


        public String getAccountAddress() {
            return accountAddress;
        }

        public void setAccountAddress(String accountAddress) {
            this.accountAddress = accountAddress;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getChainTransTime() {
            return chainTransTime;
        }

        public void setChainTransTime(String chainTransTime) {
            this.chainTransTime = chainTransTime;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getOssFileUrl() {
            return ossFileUrl;
        }

        public void setOssFileUrl(String ossFileUrl) {
            this.ossFileUrl = ossFileUrl;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }

}
