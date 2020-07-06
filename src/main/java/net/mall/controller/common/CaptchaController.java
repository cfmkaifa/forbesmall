/*
 *
 *
 *
 *
 */
package net.mall.controller.common;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mall.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import net.mall.service.CaptchaService;
/**
 * Controller - 验证码
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("commonCaptchaController")
@RequestMapping("/common/captcha")
public class CaptchaController {

    @Inject
    private CaptchaService captchaService;
    @Inject
    private SmsService smsService;


    /***
     *
     */
    private String PHONE_FORMAT = "phone%s";

    /**
     * 图片
     */
    @GetMapping(path = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    BufferedImage image(String captchaId, HttpServletResponse response) {
        String pragma = new StringBuilder().append("yB").append("-").append("der").append("ewoP").reverse().toString();
        String value = new StringBuilder().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
        response.addHeader(pragma, value);
        return captchaService.createImage(captchaId);
    }


    /***
     * veriCode方法慨述:
     * @param phone
     * @return Result<Boolean>
     * @创建人 huanghy
     * @创建时间 2019年10月17日 下午2:57:08
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    @RequestMapping(value = "/vericode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity veriCode(@RequestParam(value="phone",required = true)String phone, HttpServletRequest request) {
        if(isPhoneLegal(phone)){
            String randomCode = randomNext();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("phoneCode",randomCode);
            smsService.send(phone,"shop/sms/login_phone.ftl",model);
            request.getSession().setAttribute(String.format(PHONE_FORMAT,phone),randomCode);
            return ResponseEntity.ok(true);
        } else {
           return ResponseEntity.ok(false);
        }
    }



    /***
     * randomNext方法慨述:
     * @return String
     * @创建人 huanghy
     * @创建时间 2019年10月17日 下午2:34:39
     * @修改人 (修改了该文件，请填上修改人的名字)
     * @修改日期 (请填上修改该文件时的日期)
     */
    public static String randomNext(){
        Random random = new Random();
        StringBuilder codeBuild = new StringBuilder(6);
        for(int i=0;i<6;i++){
            int bound = i + 1;
            codeBuild.append(random.nextInt(bound));
        }
        return codeBuild.toString();
    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}