package me.andpay.ac.wpn.api.consts;

/**
 * Created by cen on 16/11/2.
 * 错误码常量
 */
public class AppExCodes {



    /**
     * 微信access token请求失败
     */
    public final static String WEIXIN_ACCESS_TOKEN_ERROR = "WPN.001";

    /**
     * 微信jsapi ticket 请求失败
     */
    public final static String WEIXIN_API_TICKET_ERROR = "WPN.002";

    /**
     * 微信jsapi 签名异常
     */
    public final static String WEIXIN_JSAPI_SIGN_ERROR = "WPN.003";

    /**
     * 网页授权获取access异常
     */
    public final static String AUTH_TOW_ACCESS_TOKEN_ERROR =  "WPN.004";


    /**
     * 刷新网页授权获取access异常
     */
    public final static String REFRESH_AUTH_TOW_ACCESS_TOKEN_ERROR =  "WPN.005";

    /**
     * 获取授权用户信息异常
     */
    public final static String GET_AUTH_USER_ERROR =  "WPN.006";


    /**
     * 发送模板消息异常
     */
    public final static String SEND_TEMPLATE_MSG_ERROR =  "WPN.007";


    /**
     * 找不到绑定用户
     */
    public final static String CAN_NOT_FOUND_BIND_USER = "WPN.008";


    /**
     * 找不到消息模板
     */
    public final static String CAN_NOT_FOUND_TEMPMSG = "WPN.009";

    /**
     * 短链转换错误
     */
    public final static String LONG_SHORT_URL_CONVERT_ERROR =  "WPN.010";

    /**
     * 二维码生成失败
     */
    public final static String QR_CODE_CREATE_ERROR =  "WPN.011";

    /**
     * 系统内部错误
     */
    public final static String SYS_ERROR = "WPN.999";
}
