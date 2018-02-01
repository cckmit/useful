package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;

/**
 * 开关条目
 * Created by cen on 2017/7/6.
 */
public class SwitchItem {


    /**
     * 是否打开
     */
    private boolean open;

    /**
     * 组名称
     */
    @NotNull
    private String groupName;

    /**
     * 组编号
     */
    @NotNull
    private Long messageTemplateGroupId;


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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
