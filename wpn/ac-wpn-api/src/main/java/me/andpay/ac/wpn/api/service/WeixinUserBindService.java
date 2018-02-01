package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.WxBindUserInfo;
import me.andpay.ac.wpn.api.model.WxUserBindRequest;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.LnkService;

/**
 * Created by cen on 16/11/2.
 * 绑定服务
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_SRV)
public interface WeixinUserBindService {

    /**
     * 微信用户绑定
     * 一个party只能绑定一个微信用户（openId）,一个微信账号可以绑定多个party
     * @param wxBindRequest
     * @return
     * @throws AppBizException
     */
    WxBindUserInfo weixinUserBind(WxUserBindRequest wxBindRequest) throws AppBizException;

    /**
     * 获取微信绑定用户
     * 当前openId最新登录过的用户
     * @param appid
     * @param openId
     */
    WxBindUserInfo obtainOneBindUser(String appid, String openId,String bindType) throws AppBizException;

    /**
     * 查询当前微信用户下所有绑定的party
     * @param appid
     * @param openId
     * @param bindType
     * @return
     * @throws AppBizException
     */
//    public List<WxBindUser> queryBindUser(String appid, String openId,String bindType) throws AppBizException;

    /**
     * 解绑用户
     * @param bindUserId
     * @throws AppBizException
     */
    void unBindUser(Long bindUserId) throws AppBizException;

    /**
     * 根据party获取绑定用户
     * @param partyId
     * @return
     * @throws AppBizException
     */
    WxBindUserInfo obtainBindUserWithParty(String partyId) throws AppBizException;
}
