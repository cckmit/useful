package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.WxPublicNumberResponse;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.LnkService;


/**
 * Created by cen on 16/11/3.
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_SRV)
public interface WeixinPubicNumberService {


    /**
     * 获取微信公众号
     * @param
     * @return
     */
    WxPublicNumberResponse obtainWeiPublicNumber(Long wxPublicNumberId) throws AppBizException;

    /**
     * 获取微信公众号
     * @param appAlias
     * @return
     * @throws AppBizException
     */
    WxPublicNumberResponse obtainWeiPublicNumberWithAlias(String appAlias) throws AppBizException;

}
