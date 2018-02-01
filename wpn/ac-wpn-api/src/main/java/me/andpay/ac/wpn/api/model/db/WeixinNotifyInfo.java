package me.andpay.ac.wpn.api.model.db;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by cen on 2017/2/16.
 */
@Entity
public class WeixinNotifyInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weixinNotifyInfoId;

    /**
     * 微信账号
     */
    @NotNull
    private String appId;

    /**
     * 消息散列值
     */
    @NotNull
    private String messageHashCode;

    /**
     * 消息内容
     */
    @Column(columnDefinition = "clob")
    private String notifyContent;

    /**
     * 签名信息
     */
    @NotNull
    private String sign;

    /**
     * 处理状态
     */
    @NotNull
    private String dealStatus;

    /**
     * 处理结果描述
     */
    private String dealResultDesc;

    /**
     * 接受时间
     */
    @NotNull
    private Date receiveTime;

    /**
     * 更新时间
     */
    @NotNull
    private Date updateTime;

    public Long getWeixinNotifyInfoId() {
        return weixinNotifyInfoId;
    }

    public void setWeixinNotifyInfoId(Long weixinNotifyInfoId) {
        this.weixinNotifyInfoId = weixinNotifyInfoId;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getDealResultDesc() {
        return dealResultDesc;
    }

    public void setDealResultDesc(String dealResultDesc) {
        this.dealResultDesc = dealResultDesc;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getMessageHashCode() {
        return messageHashCode;
    }

    public void setMessageHashCode(String messageHashCode) {
        this.messageHashCode = messageHashCode;
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
}


