package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.AuthTwoAccessTokenResult;
import me.andpay.ac.wpn.api.model.AuthUserInfo;
import me.andpay.ac.wpn.api.model.CheckCallbackUrlSignRequest;
import me.andpay.ac.wpn.api.model.WxJSAPISignResult;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.LnkService;

/**
 * Created by cen on 16/11/2.
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_WXAUTH_SRV)
public interface WeixinAuthService {

    /**
     * 获取微信公众号全局accessToken
     * @param appId
     * @return
     * @throws AppBizException
     */
    String obtainAccessToken(String appId) throws AppBizException;

    /**
     * 获取微信公众号全局票据号
     * @param appid
     * @return
     * @throws AppBizException
     */
    String obtainJsapiTicket(String appid) throws AppBizException;


    /**
     * 微信JSAPI签名服务
     * @param requestUrl
     * @return
     * @throws AppBizException
     */
    WxJSAPISignResult jsApiSign(String requestUrl, String appId) throws AppBizException;

    /**
     * 微信JSAPI签名服务(通过App描述获取)
     * @param requestUrl
     * @return
     * @throws AppBizException
     */
    WxJSAPISignResult jsApiSignWithAlias(String requestUrl, String alias) throws AppBizException;

    /**
     * 网页授权auth2.0 access_token获取，不同于全局api access_token，主要用于用户信息的获取
     * @param appId
     * @param code
     * @return AuthTwoAccessTokenResult
     * @throws AppBizException
     */
    AuthTwoAccessTokenResult obtainAuthTwoAccessToken(String appId, String code) throws AppBizException;


    /**
     * 刷新access_token
     * @param refreshToken
     * @param appId
     * @return
     * @throws AppBizException
     */
    public AuthTwoAccessTokenResult refreshAuthTwoAccessToken(String refreshToken,String appId) throws AppBizException;


    /**
     * 获取网页授权用户信息
     * @param accessToken
     * @param openid
     * @return
     * @throws AppBizException
     */
    public AuthUserInfo getAuthUserInfo(String accessToken, String openid) throws AppBizException;

    /**
     * 校验网页授权accessToken是否有效
     * @param accessToken
     * @param opneid
     * @return
     * @throws AppBizException
     */
    public boolean checkAccessToken(String accessToken,String opneid) throws AppBizException;



}
