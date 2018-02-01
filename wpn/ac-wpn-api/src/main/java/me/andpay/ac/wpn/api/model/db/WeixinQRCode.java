package me.andpay.ac.wpn.api.model.db;

import me.andpay.ti.util.ArrayUtil;
import me.andpay.ti.util.JSON;
import me.andpay.ti.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码参数
 * Created by cen on 2017/2/15.
 */
@Entity
public class WeixinQRCode {

    /**
     * 参数编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weixinQRCodeId;
    /**
     * 场景编号
     */
    @NotNull
    private String sceneId;
    /**
     * 二维码类型
     */
    @NotNull
    private String qrCodeType;
    /**
     * 参数内容
     */
    private String paramJson;

    @Transient
    private Map<String,String> paramMap = new HashMap<String, String>();

    /**
     * 二维码长链接
     */
    @NotNull
    private String ticket;
    /**
     * 二维码短链接
     */
//    @NotNull
    private String qrCodeShortUrl;

    /**
     * 二维码业务类型
     */
    @NotNull
    @Size(max = 2,min = 2)
    private String qrCodeBizType;

    /**
     * 跟踪编号
     */
    @Size(max = 64)
    private String traceNo;

    /**
     * 二维码内容
     */
    @NotNull
    @Size(max = 256)
    private String qrCodeContent;

    /**
     * 微信公众号
     */
    @NotNull
    private String appId;

    /**
     * 过期时间（秒）
     */
    private Long expireSeconds;
    /**
     * 创建时间
     */
    @NotNull
    private Date createTime;

    /**
     * 更新时间
     */
    @NotNull
    private Date updateTime;


    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getQrCodeContent() {
        return qrCodeContent;
    }

    public void setQrCodeContent(String qrCodeContent) {
        this.qrCodeContent = qrCodeContent;
    }

    public Long getWeixinQRCodeId() {
        return weixinQRCodeId;
    }

    public void setWeixinQRCodeId(Long weixinQRCodeId) {
        this.weixinQRCodeId = weixinQRCodeId;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getQrCodeType() {
        return qrCodeType;
    }

    public void setQrCodeType(String qrCodeType) {
        this.qrCodeType = qrCodeType;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getQrCodeShortUrl() {
        return qrCodeShortUrl;
    }

    public void setQrCodeShortUrl(String qrCodeShortUrl) {
        this.qrCodeShortUrl = qrCodeShortUrl;
    }

    public String getQrCodeBizType() {
        return qrCodeBizType;
    }

    public void setQrCodeBizType(String qrCodeBizType) {
        this.qrCodeBizType = qrCodeBizType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, String> getParamMap() {
        if(StringUtil.isNotBlank(paramJson)) {
            paramMap = JSON.getDefault().parseToObject(paramJson,Map.class);
        }else  {
            paramMap = new HashMap<String, String>();
        }
        return paramMap;
    }


    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}
