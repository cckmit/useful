package me.andpay.ac.wpn.api.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 微信持卡人绑定
 * Created by cen on 2017/2/17.
 */
@Entity
public class WxBindCardHolder {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wxBindCardHolderId;
    /**
     * 卡号令牌信息
     */
    @NotNull
    private String cardNOToken;

    /**
     * 微信用户编号
     */
    @NotNull
    private Long userId;

    @NotNull
    @Size(max = 1,min = 1)
    private String bindStatus;


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



    public String getCardNOToken() {
        return cardNOToken;
    }

    public void setCardNOToken(String cardNOToken) {
        this.cardNOToken = cardNOToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWxBindCardHolderId() {
        return wxBindCardHolderId;
    }

    public void setWxBindCardHolderId(Long wxBindCardHolderId) {
        this.wxBindCardHolderId = wxBindCardHolderId;
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

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }
}
