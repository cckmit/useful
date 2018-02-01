package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * 二维码生成请求
 * Created by cen on 2017/2/14.
 */
public class CreateSceneQRCodeRequest {

    /**
     * 过期时间，单位（秒）
     */
    private long expireSeconds;

    /**
     * 二维码的类型
     */
    @NotNull
    private String qrCodeType;

    /**
     * 二维码业务类型
     */
    @NotNull
    @Size(max = 2,min = 2)
    private String qrCodeBizType;

    /**
     * 二维码参数
     */
    private Map<String,String> qrParams;

    /**
     * 跟踪编号
     * 要保证你的跟踪编号不重复
     */
    @Size(max = 64)
    private String traceNo;
    /**
     * 公众号编号
     */
    @NotNull
    public String appAlais;

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getQrCodeType() {
        return qrCodeType;
    }

    public void setQrCodeType(String qrCodeType) {
        this.qrCodeType = qrCodeType;
    }

    public String getAppAlais() {
        return appAlais;
    }

    public void setAppAlais(String appAlais) {
        this.appAlais = appAlais;
    }

    public Map<String, String> getQrParams() {
        return qrParams;
    }

    public void setQrParams(Map<String, String> qrParams) {
        this.qrParams = qrParams;
    }

    public String getQrCodeBizType() {
        return qrCodeBizType;
    }

    public void setQrCodeBizType(String qrCodeBizType) {
        this.qrCodeBizType = qrCodeBizType;
    }
}
