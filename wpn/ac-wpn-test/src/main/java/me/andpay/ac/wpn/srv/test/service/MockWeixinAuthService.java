package me.andpay.ac.wpn.srv.test.service;

import me.andpay.ac.wpn.api.model.AuthTwoAccessTokenResult;
import me.andpay.ac.wpn.api.model.AuthUserInfo;
import me.andpay.ac.wpn.api.model.CheckCallbackUrlSignRequest;
import me.andpay.ac.wpn.api.model.WxJSAPISignResult;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ti.base.AppBizException;

/**
 * Created by cen on 2017/2/9.
 */
public class MockWeixinAuthService implements WeixinAuthService {
    @Override
    public String obtainAccessToken(String appId) throws AppBizException {
        return null;
    }

    @Override
    public String obtainJsapiTicket(String appid) throws AppBizException {
        return null;
    }

    @Override
    public WxJSAPISignResult jsApiSign(String requestUrl, String appId) throws AppBizException {
        return null;
    }

    @Override
    public WxJSAPISignResult jsApiSignWithAlias(String requestUrl, String alias) throws AppBizException {
        return null;
    }

    @Override
    public AuthTwoAccessTokenResult obtainAuthTwoAccessToken(String appId, String code) throws AppBizException {
        return null;
    }

    @Override
    public AuthTwoAccessTokenResult refreshAuthTwoAccessToken(String refreshToken, String appId) throws AppBizException {
        return null;
    }

    @Override
    public AuthUserInfo getAuthUserInfo(String accessToken, String openid) throws AppBizException {
        return null;
    }

    @Override
    public boolean checkAccessToken(String accessToken, String opneid) throws AppBizException {
        return false;
    }

}
