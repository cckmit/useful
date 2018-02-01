package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;

/**
 * Created by cen on 2017/4/14.
 */
public class WxQueryBindUserRequest {

    /**
     * 绑定用户编号
     */
    private Long bindUserId;

    /**
     * 机构编号
     */
    private String partyId;

    /**
     * 绑定用户名
     */
    private String userName;

    /**
     * 绑定类型
     */
    private String bindType;


    private Long userId;
    /**
     * 微信公众号openId
     */
    @NotNull
    private String openid;
    /**
     * 公众号编号
     */
    @NotNull
    private String appid;
    /**
     * 用户全局统一编号,暂时预留
     */
    private String unionid;
    /**
     * 绑定状态
     */
    private String status;


    public Long getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(Long bindUserId) {
        this.bindUserId = bindUserId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
