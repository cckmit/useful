package me.andpay.ac.wpn.api.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by cen on 2016/11/16.
 * 微信消息发送记录
 */
@Entity
public class MessageRecord {

    /**
     * 消息记录编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long messageRecordId;
    /**
     * 微信用户编号
     */
    public String openId;

    /**
     * 公众号编号
     */
    public String appId;
    /**
     * 用户名
     */
    public String userName;
    /**
     * 机构名称
     */
    private String partyId;
    /**
     * 消息模板号
     */
    private String templateId;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 消息参数
     */
    private String content;

    /**
     * 消息发送状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getMessageRecordId() {
        return messageRecordId;
    }

    public void setMessageRecordId(Long messageRecordId) {
        this.messageRecordId = messageRecordId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
