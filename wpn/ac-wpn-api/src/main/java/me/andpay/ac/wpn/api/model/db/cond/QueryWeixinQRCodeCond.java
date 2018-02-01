package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.AbstractOrderableCondition;
import me.andpay.ti.daf.dao.simplequery.annotation.Condition;

import javax.validation.constraints.NotNull;

/**
 * 查询二维码参数条件
 * Created by cen on 2017/2/15.
 */
@Condition
public class QueryWeixinQRCodeCond extends AbstractOrderableCondition {

    /**
     * 场景编号
     */
    @NotNull
    private String sceneId;
    /**
     * 二维码类型
     */
    @NotNull
    private String qrCodeType;

    /**
     * 微信公众号
     */
    private String appId;

    /**
     * 跟踪编号
     */
    private String traceNo;

    /**
     * 二维码业务类型
     */
    private String qrCodeBizType;

    public String getQrCodeBizType() {
        return qrCodeBizType;
    }

    public void setQrCodeBizType(String qrCodeBizType) {
        this.qrCodeBizType = qrCodeBizType;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getQrCodeType() {
        return qrCodeType;
    }

    public void setQrCodeType(String qrCodeType) {
        this.qrCodeType = qrCodeType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }
}
