package me.andpay.ac.wpn.api.model;

import java.util.Map;

/**
 * Created by cen on 2017/2/4.
 */
public class SendTempMsgRequest extends SendTempMsgBaseRequest{

    /**
     * 微信用户编号
     */
    private String openId;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


}
