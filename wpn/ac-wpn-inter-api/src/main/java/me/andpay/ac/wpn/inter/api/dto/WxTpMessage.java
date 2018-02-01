package me.andpay.ac.wpn.inter.api.dto;

import me.andpay.ac.wpn.api.consts.WxTpMessageConfigNames;
import me.andpay.ti.util.JSON;
import me.andpay.ti.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cen on 2016/11/16.
 */
public class WxTpMessage {

    /**
     * 发送给谁
     */
    private String touser;
    /**
     * 模板编号
     */
    private String template_id;
    /**
     * 点击url地址
     */
    private String url;
    /**
     * 参数表
     */
    private Map<String,DataPackage> data;

    /**
     * 参数配置
     */
    private Map<String,Map<String,String>> paramsConfig;
    /**
     * 参数数据
     */
    private Map<String,String> params;


    private String appId;

    private void genReplaceDate(String key,Map<String,String> params, Map<String,String> replaceDate ,int index) {
        String value = params.get(key);
        if(StringUtil.isNotBlank(value)) {
            replaceDate.put("{}",value);
            return;
        }

        value = params.get(key+"#"+index);
        if(StringUtil.isBlank(value)) {
            return;
        }
        replaceDate.put("{#"+index+"}",value);
        genReplaceDate(key,params,replaceDate,index+1);
        return;
    }

    public String postJson(){

        JSON jsonutil = JSON.getDefault();

        WxTpMessage wxTpMessage = new WxTpMessage();
        wxTpMessage.setUrl(url);
        wxTpMessage.setTemplate_id(template_id);
        wxTpMessage.setTouser(touser);

        Map<String,DataPackage> stringDataPackageMap = new HashMap<String, DataPackage>();
        if(paramsConfig != null && params != null) {
            for (String key : paramsConfig.keySet()){


                Object config = paramsConfig.get(key);
                String color = null;
                String format = null;
                if(config instanceof  Map) {
                    Map<String,String> mapConfig =  (Map<String,String>) config;
                    color = mapConfig.get(WxTpMessageConfigNames.WCN_COLOR);
                    format = mapConfig.get(WxTpMessageConfigNames.WCN_FORMAT);
                }


                DataPackage dataPackage = new DataPackage();

                if(StringUtil.isNotBlank(color)) {
                    dataPackage.setColor(color);
                }

                Map<String,String> replaceDate = new HashMap<String, String>();
                genReplaceDate(key,params,replaceDate,0);

                if(StringUtil.isNotBlank(format)) {
                    for(String repKey:replaceDate.keySet()) {
                        format = format.replace(repKey,replaceDate.get(repKey));
                    }
                    dataPackage.setValue(format);
                }else {
                    dataPackage.setValue(replaceDate.get("{}"));
                }

                stringDataPackageMap.put(key,dataPackage);
            }
        }
        wxTpMessage.setData(stringDataPackageMap);

        String postContent = jsonutil.toJSONString(wxTpMessage);
        return postContent;

    }


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, DataPackage> getData() {
        return data;
    }

    public void setData(Map<String, DataPackage> data) {
        this.data = data;
    }

    public Map<String, Map<String, String>> getParamsConfig() {
        return paramsConfig;
    }

    public void setParamsConfig(Map<String, Map<String, String>> paramsConfig) {
        this.paramsConfig = paramsConfig;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
