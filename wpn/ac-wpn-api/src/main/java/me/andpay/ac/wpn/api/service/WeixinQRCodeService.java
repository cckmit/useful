package me.andpay.ac.wpn.api.service;

import me.andpay.ac.wpn.api.callback.CreateSceneQRCodeCallback;
import me.andpay.ac.wpn.api.consts.ServiceGroups;
import me.andpay.ac.wpn.api.model.CreateSceneQRCodeRequest;
import me.andpay.ac.wpn.api.model.QRCodeResult;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.Callback;
import me.andpay.ti.lnk.annotaion.LnkMethod;
import me.andpay.ti.lnk.annotaion.LnkService;
import me.andpay.ti.lnk.annotaion.Sla;

/**
 * 微信二维码服务
 * Created by cen on 2017/2/14.
 */
@LnkService(serviceGroup = ServiceGroups.AC_WPN_QRCODE_SRV)
public interface WeixinQRCodeService {

    /**
     * 生成带场景的微信二维码
     * @param createSceneQRCodeRequest
     * @return
     * @throws AppBizException
     */
    @Sla(timeout = 10000)
    public QRCodeResult createSceneQRCode(CreateSceneQRCodeRequest createSceneQRCodeRequest) throws AppBizException;


    @LnkMethod(async = true)
    public void asyncCreateSceneQRCode(CreateSceneQRCodeRequest createSceneQRCodeRequest,@Callback CreateSceneQRCodeCallback createSceneQRCodeCallback);

}
