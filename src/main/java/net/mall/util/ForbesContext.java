package net.mall.util;

/***上下文
 */
public class ForbesContext {

     private static final ThreadLocal<String>  REQ_URI = new ThreadLocal<String>();
     private static final ThreadLocal<String>  SESSION_PHONE_CODE = new ThreadLocal<String>();


    public static String getReqUri() {
        return REQ_URI.get();
    }

    public static void setReqUri(String reqUri) {
        REQ_URI.set(reqUri);
    }

    public static String getSessionPhoneCode() {
        return SESSION_PHONE_CODE.get();
    }

    public static void setSessionPhoneCode(String sessionPhoneCode) {
        SESSION_PHONE_CODE.set(sessionPhoneCode);
    }


    public static void clearThread(){
        REQ_URI.remove();
        SESSION_PHONE_CODE.remove();
    }
}
