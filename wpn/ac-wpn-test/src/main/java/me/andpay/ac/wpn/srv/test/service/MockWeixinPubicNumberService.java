package me.andpay.ac.wpn.srv.test.service;

import me.andpay.ac.wpn.api.model.WxPublicNumberResponse;
import me.andpay.ac.wpn.api.service.WeixinPubicNumberService;
import me.andpay.ti.base.AppBizException;

/**
 * Created by cen on 2017/2/9.
 */
public class MockWeixinPubicNumberService implements WeixinPubicNumberService {
    @Override
    public WxPublicNumberResponse obtainWeiPublicNumber(Long wxPublicNumberId) throws AppBizException {
        return null;
    }

    @Override
    public WxPublicNumberResponse obtainWeiPublicNumberWithAlias(String appAlias) throws AppBizException {
        return null;
    }
}
