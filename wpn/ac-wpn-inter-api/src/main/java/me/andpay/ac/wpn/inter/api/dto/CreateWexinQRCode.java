package me.andpay.ac.wpn.inter.api.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cen on 2017/2/14.
 */
public class CreateWexinQRCode {

    /**
     * 过期时间
     */
    private long expire_seconds;
    /**
     * 二维码类型
     */
    private String action_name;

    /**
     * 场景参数
     */
    private Map<String,Map<String,String>> action_info = new HashMap<String, Map<String, String>>();


    public long getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(long expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public Map<String, Map<String, String>> getAction_info() {
        return action_info;
    }

    public void setAction_info(Map<String, Map<String, String>> action_info) {
        this.action_info = action_info;
    }
}
