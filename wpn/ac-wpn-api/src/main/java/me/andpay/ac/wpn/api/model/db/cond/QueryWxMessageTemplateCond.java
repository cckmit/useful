package me.andpay.ac.wpn.api.model.db.cond;

import me.andpay.ti.daf.dao.simplequery.annotation.Condition;

/**
 * Created by cen on 2016/11/16.
 */

@Condition
public class QueryWxMessageTemplateCond {

    /**
     * 模板名称
     */
    private String templateName;


    /**
     * app别名
     */
    private String appAlais;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAppAlais() {
        return appAlais;
    }

    public void setAppAlais(String appAlais) {
        this.appAlais = appAlais;
    }
}
