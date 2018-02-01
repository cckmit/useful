package me.andpay.ac.wpn.api.model;

import java.util.Map;
import java.util.Set;

/**
 * Created by cen on 16/11/2.
 * 微信绑定请求
 */
public class WxUserBindRequest {

    /**
     * 公众号用户唯一编号
     */
    private String openid;

    /**
     * 公众号编号
     */
    private String appid;

    /**
     * party编号
     */
    private String partyId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 绑定类型
     */
    private String bindType;

    /**
     * 微信公众平台唯一编号
     */
    private String unionid;
    /**
     * 授权用户信息
     */
    private String authUserInfo;

    /**
     * 绑定参数
     */
    private Map<String,String> bindAttr;

    public Map<String, String> getBindAttr() {
        return bindAttr;
    }

    public void setBindAttr(Map<String, String> bindAttr) {
        this.bindAttr = bindAttr;
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

    public String getAuthUserInfo() {
        return authUserInfo;
    }

    public void setAuthUserInfo(String authUserInfo) {
        this.authUserInfo = authUserInfo;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
