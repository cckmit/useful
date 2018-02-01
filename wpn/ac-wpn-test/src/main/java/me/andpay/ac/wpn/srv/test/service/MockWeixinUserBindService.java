package me.andpay.ac.wpn.srv.test.service;

import me.andpay.ac.wpn.api.model.WxBindUserInfo;
import me.andpay.ac.wpn.api.model.WxUserBindRequest;
import me.andpay.ac.wpn.api.service.WeixinUserBindService;
import me.andpay.ti.base.AppBizException;

/**
 * Created by cen on 2017/2/9.
 */
public class MockWeixinUserBindService implements WeixinUserBindService {
    @Override
    public WxBindUserInfo weixinUserBind(WxUserBindRequest wxBindRequest) throws AppBizException {
        return null;
    }

    @Override
    public WxBindUserInfo obtainOneBindUser(String appid, String openId, String bindType) throws AppBizException {
        return null;
    }

    @Override
    public void unBindUser(Long bindUserId) throws AppBizException {

    }

    @Override
    public WxBindUserInfo obtainBindUserWithParty(String partyId) throws AppBizException {
        return null;
    }
}
