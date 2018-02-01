package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;

/**
 * 微信通知请求
 * Created by cen on 2017/2/23.
 */
public class WeixinNotifyRequest {

    /**
     * 通知内容
     */
    @NotNull
    private String notifyContent;
    /**
     * 签名
     */
    @NotNull
    private String signature;

    /**
     * 随机字符串
     */
    private String echostr;

    /**
     * 时间戳
     */
    @NotNull
    private String timestamp;

    /**
     * 随机数
     */
    @NotNull
    private String nonce;

    /**
     * 微信公众号编号
     */
    @NotNull
    private Long wxPublicNumberId;

    public Long getWxPublicNumberId() {
        return wxPublicNumberId;
    }

    public void setWxPublicNumberId(Long wxPublicNumberId) {
        this.wxPublicNumberId = wxPublicNumberId;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

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
}
