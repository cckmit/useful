package me.andpay.ac.wpn.web.model;

/**
 * 和付公众号绑定请求
 * Created by cen on 2017/2/21.
 */
public class AndpayBindRequest {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 微信用户编号
     */
    private String openId;

    /**
     * 微信公众号
     */
    private String appId;

    /**
     * 用户密码
     */
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
