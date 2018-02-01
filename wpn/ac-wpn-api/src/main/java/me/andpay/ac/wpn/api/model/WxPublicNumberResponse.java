package me.andpay.ac.wpn.api.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by cen on 16/11/4.
 */
public class WxPublicNumberResponse {


    /**
     * 公众号编号
     */
    private String appId;
    /**
     * 公众号名称
     */
    private String name;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
