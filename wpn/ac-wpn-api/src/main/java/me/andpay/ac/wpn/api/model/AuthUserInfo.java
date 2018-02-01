package me.andpay.ac.wpn.api.model;

import java.util.List;

/**
 * Created by cen on 16/11/4.
 */
public class AuthUserInfo {

    /**
     * 用户唯一标示
     */
    private String openid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像图片
     */
    private String headimgurl;

    /**
     * 权限
     */
    private List<String> privilege;

    /**
     * 开放平台唯一编号
     */
    private String unionid;

    /**
     * 原数据
     */
    private String sourceJson;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getSourceJson() {
        return sourceJson;
    }

    public void setSourceJson(String sourceJson) {
        this.sourceJson = sourceJson;
    }
}
