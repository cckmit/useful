package me.andpay.ac.wpn.api.model;


import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by cen on 16/11/5.
 */
public class WxBindUserInfo {

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
     * 授权用户详细信息
     */
    private String authUserInfo;

    /**
     * 用户全局统一编号,暂时预留
     */
    private String unionid;
    /**
     * 绑定状态
     */
    private String status;

    /**
     * 绑定数据
     */
    private Map<String,String> bindAttr;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getBindAttr() {
        return bindAttr;
    }

    public void setBindAttr(Map<String, String> bindAttr) {
        this.bindAttr = bindAttr;
    }
}
