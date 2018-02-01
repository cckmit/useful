package me.andpay.ac.wpn.api.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.andpay.ti.util.JSON;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信消息模板
 * Created by cen on 2016/11/16.
 */
@Entity
public class WxMessageTemplate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wxMessageTemplateId;
    /**
     *  自定义模板名称
     */
    @Size(max = 128)
    private String templateName;

    /**
     * app别名
     */
    private String appAlais;

    /**
     * 模板描述
     */
    @NotNull
    private String templateDesc;

    /**
     * 模板消息分组
     */
    @NotNull
    private Long messageTemplateGroupId;

    /**
     * 微信模板编号
     */
    @NotNull
    @Size(max = 128)
    private String weixinTemplateId;

    /**
     * 消息打开网址
     */
    @Size(max = 256)
    private String url;

    /**
     * 参数 key值和颜色json字符串
     */
    @Size(max = 512)
    private String paramConfigs;

    @Transient
    private Map<String,Map<String,String>> paramConfigsMap = new HashMap<String, Map<String, String>>();
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

    public Long getWxMessageTemplateId() {
        return wxMessageTemplateId;
    }

    public void setWxMessageTemplateId(Long wxMessageTemplateId) {
        this.wxMessageTemplateId = wxMessageTemplateId;
    }

    @JsonIgnore
    public Map<String, Map<String,String>> getParamConfigsMap() {
        String propertyData = getParamConfigs();
        if (paramConfigsMap.size()==0 && propertyData != null) {
            paramConfigsMap =JSON.getDefault().parseToObject(propertyData,paramConfigsMap.getClass());
        }
        return paramConfigsMap;
    }

    public void setParamConfigsMap(Map<String, Map<String,String>> paramConfigsMap) {
        this.paramConfigsMap = paramConfigsMap;
        setParamConfigs(JSON.getDefault().toJSONString(paramConfigsMap));
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public Long getMessageTemplateGroupId() {
        return messageTemplateGroupId;
    }

    public void setMessageTemplateGroupId(Long messageTemplateGroupId) {
        this.messageTemplateGroupId = messageTemplateGroupId;
    }

    public String getWeixinTemplateId() {
        return weixinTemplateId;
    }

    public void setWeixinTemplateId(String weixinTemplateId) {
        this.weixinTemplateId = weixinTemplateId;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParamConfigs() {
        return paramConfigs;
    }

    public void setParamConfigs(String paramConfigs) {
        this.paramConfigs = paramConfigs;
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

    public String getAppAlais() {
        return appAlais;
    }

    public void setAppAlais(String appAlais) {
        this.appAlais = appAlais;
    }
}
