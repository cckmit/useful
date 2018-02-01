package me.andpay.ac.wpn.srv.notify;

import java.util.Map;

/**
 * Created by cen on 2017/2/23.
 */
public class NotifyContext {

    /**
     * 消息数据
     */
    private Map<String,String> xmlData;

    /**
     * 消息存储编号
     */
    private Long weixinNotifyInfoId;

    /**
     * 微信公众号
     */
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Map<String, String> getXmlData() {
        return xmlData;
    }

    public void setXmlData(Map<String, String> xmlData) {
        this.xmlData = xmlData;
    }

    public Long getWeixinNotifyInfoId() {
        return weixinNotifyInfoId;
    }

    public void setWeixinNotifyInfoId(Long weixinNotifyInfoId) {
        this.weixinNotifyInfoId = weixinNotifyInfoId;
    }
}
