package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.annotation.Condition;
import me.andpay.ti.daf.dao.simplequery.annotation.Expression;

import java.util.Set;

/**
 * 消息开关条件
 * Created by cen on 2017/7/6.
 */
@Condition
public class QueryMessageSwitchConfigCond {

    /**
     * 是否打开
     */
    private Boolean open;
    /**
     * 关联目标
     */
    private String target;

    /**
     * 目标类型 参考SwitchConfigTargetTypes
     */
    private String targetType;

    /**
     * 消息组
     */
    private Long messageTemplateGroupId;

    @Expression(property = "messageTemplateGroupId",operator = "in")
    private Set<Long> messageTemplateGroupIds;

    public Set<Long> getMessageTemplateGroupIds() {
        return messageTemplateGroupIds;
    }

    public void setMessageTemplateGroupIds(Set<Long> messageTemplateGroupIds) {
        this.messageTemplateGroupIds = messageTemplateGroupIds;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
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
}
