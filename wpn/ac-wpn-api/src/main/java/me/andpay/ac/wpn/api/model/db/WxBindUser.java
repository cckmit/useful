package me.andpay.ac.wpn.api.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by cen on 16/11/2.
 * 微信用户绑定信息
 */
@Entity
public class WxBindUser {
    /**
     * 微信绑定用户Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bindUserId;
    /**
     * 用户名
     */
    @NotNull
    private Long userId;
    /**
     * 机构编号
     */
    @NotNull
    private String partyId;

    /**
     * 绑定用户名
     */
    @NotNull
    private String userName;

    /**
     * 绑定类型
     */
    @NotNull
    private String bindType;
    /**
     * 绑定状态
     */
    @NotNull
    private String status;

    /**
     * 绑定参数
     */
    private String bindAttrs;
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

    public String getBindAttrs() {
        return bindAttrs;
    }

    public void setBindAttrs(String bindAttrs) {
        this.bindAttrs = bindAttrs;
    }

    public Long getBindUserId() {
        return bindUserId;
    }

    public void setBindUserId(Long bindUserId) {
        this.bindUserId = bindUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
