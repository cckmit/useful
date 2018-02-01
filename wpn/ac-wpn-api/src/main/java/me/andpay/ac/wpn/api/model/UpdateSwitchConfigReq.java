package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 更新开关请求
 * Created by cen on 2017/7/6.
 */
public class UpdateSwitchConfigReq {

    /**
     * 开关列表
     */
    @NotNull
    private List<SwitchItem> switchItems;

    /**
     * 关联目标
     */
    @NotNull
    @Size(max = 32)
    private String target;

    /**
     * 目标类型 参考SwitchConfigTargetTypes
     */
    @NotNull
    private String targetType;

    /**
     * 应用编号
     */
    @NotNull
    @Size(max = 12)
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<SwitchItem> getSwitchItems() {
        return switchItems;
    }

    public void setSwitchItems(List<SwitchItem> switchItems) {
        this.switchItems = switchItems;
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
}
