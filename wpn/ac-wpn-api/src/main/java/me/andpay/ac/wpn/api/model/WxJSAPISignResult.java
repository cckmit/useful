package me.andpay.ac.wpn.api.model;

/**
 * Created by cen on 16/11/2.
 *
 */
public class WxJSAPISignResult {

    /**
     * 随机字符创
     */
    private String noncestr;
    /**
     * 签名时间戳
     */
    private String timestamp;

    /**
     * 签名结果
     */
    private String sign;

    /**
     * appId
     */
    private String appId;


    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
