package me.andpay.ac.wpn.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息请求基础类
 * Created by cen on 2017/2/8.
 */
public class SendTempMsgBaseRequest {


    /**
     * 模板消息名称
     */
    @NotNull
    @Size(max = 128)
    private String templateName;

    /**
     * 点击打开的URL,如果不传会使用默认地址
     */
    private String url;
    /**
     * 发送参数
     */
    private Map<String,String> params = new HashMap<String, String>();

    /**
     * 链接参数
     */
    private Map<String,String> urlParams = new HashMap<String, String>();
    /**
     * 来源系统编号
     */
    @NotNull
    private String sourceSystemId;

    /**
     * 跟踪编号
     */
    @Size(max = 128)
    private String traceNo;

    /**
     * 发送目标,这里用微信公众号别名
     */
    @NotNull
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
    }
}
