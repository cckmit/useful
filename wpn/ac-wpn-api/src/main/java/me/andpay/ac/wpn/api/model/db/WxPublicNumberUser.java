package me.andpay.ac.wpn.api.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by cen on 16/11/4.
 * 微信公众号用户
 */
@Entity
public class WxPublicNumberUser {

    /**
     * 数据库自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
     * 创建时间
     */
    @NotNull
    private Date createTime;

    /**
     * 更新时间
     */
    @NotNull
    private Date updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
