package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 查询开关列表请求
 * Created by cen on 2017/7/6.
 */
public class QuerySwitchListReq {


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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
