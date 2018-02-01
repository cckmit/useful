package me.andpay.ac.wpn.api.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by cen on 16/11/2.
 * 微信公众号实体
 */
@Entity
public class WxPublicNumber {

    /**
     * 主键编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wxPublicNumberId;

    /**
     * 公众号编号
     */
    @NotNull
    private String appId;

    /**
     * app别名
     */
    private String appAlais;
    /**
     * 公众号密钥
     */
    @NotNull
    private String secret;
    /**
     * 公众号名称
     */
    @NotNull
    private String name;

    /**
     * 微信验证回调地址token
     */
    private String token;

    /**
     * 公众号模板消息所属行业编号 主营
     */
    private String primaryndustry;

    /**
     * 公众号模板消息所属行业编号 副营
     */
    private String secondaryndustry;

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

    public Long getWxPublicNumberId() {
        return wxPublicNumberId;
    }

    public void setWxPublicNumberId(Long wxPublicNumberId) {
        this.wxPublicNumberId = wxPublicNumberId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrimaryndustry() {
        return primaryndustry;
    }

    public void setPrimaryndustry(String primaryndustry) {
        this.primaryndustry = primaryndustry;
    }

    public String getSecondaryndustry() {
        return secondaryndustry;
    }

    public void setSecondaryndustry(String secondaryndustry) {
        this.secondaryndustry = secondaryndustry;
    }

    public String getAppAlais() {
        return appAlais;
    }

    public void setAppAlais(String appAlais) {
        this.appAlais = appAlais;
    }
}
