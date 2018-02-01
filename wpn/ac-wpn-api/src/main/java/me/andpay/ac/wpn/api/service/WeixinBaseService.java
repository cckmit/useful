package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.LnkService;

/**
 * 微信的一些基础服务
 * Created by cen on 2017/2/16.
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_SRV)
public interface WeixinBaseService {

    /**
     * 长链接转短链接
     * @param longUrl
     * @throws AppBizException
     */
    public String shortUrlToLongUrl(String longUrl,String appId) throws AppBizException;


}
