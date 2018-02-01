package me.andpay.ac.wpn.api.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 消息开关配置
 * Created by cen on 2017/7/6.
 */
@Entity
public class MessageSwitchConfig {

    /**
     * 消息开关配置编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageSwitchConfigId;
    /**
     * 是否打开
     */
    @NotNull
    private boolean open;
    /**
     * 关联目标
     */
    @NotNull
    private String target;

    /**
     * 目标类型 参考SwitchConfigTargetTypes
     */
    @NotNull
    private String targetType;

    /**
     * 消息组
     */
    @NotNull
    private Long messageTemplateGroupId;

    /**
     * 创建时间
     */
    @NotNull
    private Date createTime;

    public Long getMessageSwitchConfigId() {
        return messageSwitchConfigId;
    }

    public void setMessageSwitchConfigId(Long messageSwitchConfigId) {
        this.messageSwitchConfigId = messageSwitchConfigId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Long getMessageTemplateGroupId() {
        return messageTemplateGroupId;
    }

    public void setMessageTemplateGroupId(Long messageTemplateGroupId) {
        this.messageTemplateGroupId = messageTemplateGroupId;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
