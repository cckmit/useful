package me.andpay.ac.wpn.api.model;

/**
 * Created by cen on 16/11/2.
 */
public class AuthTwoAccessTokenResult {

    /**
     * 网页授权accessToken
     */
    private String accessToken;

    /**
     * 页面刷新accessToken
     */
    private String refreshToken;

    /**
     * 公众号下用户的唯一id
     */
    private String openId;

    /**
     * 用户授权作用域
     */
    private String scope;

    /**
     * 凭证的超时时间
     */
    private  int expiresIn;

    private String unionid;

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
