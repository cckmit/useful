package me.andpay.ac.wpn.api.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 模板消息分组
 * Created by cen on 2017/7/6.
 */
@Entity
public class MessageTemplateGroup {

    /**
     * 组编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageTemplateGroupId;

    /**
     * 组名称
     */
    @NotNull
    private String groupName;

    /**
     * 所属应用
     */
    @NotNull
    private String appId;
    /**
     * 排序编号
     */
    @NotNull
    private Integer priority;

    /**
     * 创建时间
     */
    @NotNull
    private Date createTime;

    public Long getMessageTemplateGroupId() {
        return messageTemplateGroupId;
    }

    public void setMessageTemplateGroupId(Long messageTemplateGroupId) {
        this.messageTemplateGroupId = messageTemplateGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getAppId() {

        return appId;
    }

    public void setAppId(String appId) {

        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
