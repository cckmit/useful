package me.andpay.ac.wpn.api.model;

/**
 * Created by cen on 16/11/9.
 */
public class CheckCallbackUrlSignRequest {


    /**
     * 签名
     */
    private String signature;

    /**
     * 随机字符串
     */
    private String echostr;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 微信公众号关联编号
     */
    private Long refAppId;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Long getRefAppId() {
        return refAppId;
    }

    public void setRefAppId(Long refAppId) {
        this.refAppId = refAppId;
    }
}
