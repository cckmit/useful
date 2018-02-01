package me.andpay.ac.wpn.srv.service;

import me.andpay.ac.wpn.api.model.db.WxBindCardHolder;
import me.andpay.ac.wpn.api.model.db.WxBindUser;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.model.db.WxPublicNumberUser;

/**
 * Created by cen on 2016/11/16.
 */
public class WxDbInitData {

    private WxPublicNumber wxPublicNumber;

    private WxBindUser wxBindUser;

    private WxPublicNumberUser wxPublicNumberUser;

    private WxBindCardHolder wxBindCardHolder;

    public WxPublicNumber getWxPublicNumber() {
        return wxPublicNumber;
    }

    public void setWxPublicNumber(WxPublicNumber wxPublicNumber) {
        this.wxPublicNumber = wxPublicNumber;
    }

    public WxBindUser getWxBindUser() {
        return wxBindUser;
    }

    public void setWxBindUser(WxBindUser wxBindUser) {
        this.wxBindUser = wxBindUser;
    }

    public WxPublicNumberUser getWxPublicNumberUser() {
        return wxPublicNumberUser;
    }

    public void setWxPublicNumberUser(WxPublicNumberUser wxPublicNumberUser) {
        this.wxPublicNumberUser = wxPublicNumberUser;
    }

    public WxBindCardHolder getWxBindCardHolder() {
        return wxBindCardHolder;
    }

    public void setWxBindCardHolder(WxBindCardHolder wxBindCardHolder) {
        this.wxBindCardHolder = wxBindCardHolder;
    }
}
