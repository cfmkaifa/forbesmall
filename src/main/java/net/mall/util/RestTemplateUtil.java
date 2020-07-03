package net.mall.util;

import com.alibaba.fastjson.JSONObject;
import net.mall.model.ResultModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
       List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
       MappingJackson2HttpMessageConverter map = new MappingJackson2HttpMessageConverter();
       messageConverters.add(map);
       messageConverters.add(new FormHttpMessageConverter());
       restTemplate.setMessageConverters(messageConverters);
       return restTemplate;
    }


    /***
     * 查询结果
     * @param busType
     * @return
     * @throws URISyntaxException
     */
    public static ResultModel reqTemplate(String idNo, String busType)
            throws URISyntaxException, UnsupportedEncodingException {
        String sign = signStr(busType,idNo);
        java.net.URI uri = new java.net.URI(serviceUrl);
        JSONObject reqJson = new JSONObject();
        reqJson.put("bizType",busType);
        reqJson.put("idNo",idNo);
        reqJson.put("sign",sign);
        RestTemplate restTemplate =  initRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(reqJson, requestHeaders);
       return restTemplate.postForObject(uri,requestEntity, ResultModel.class);
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


//    public static void main(String[] args)
//            throws UnsupportedEncodingException, URISyntaxException {
//        ResultModel responseEntity = RestTemplateUtil.reqTemplate(String.format(ONE_ONE_ID_F,"b",13101),BusTypeEnum.SUPPLIER.getCode());
//        System.out.println("========responseEntity========"+responseEntity);
//    }
}
