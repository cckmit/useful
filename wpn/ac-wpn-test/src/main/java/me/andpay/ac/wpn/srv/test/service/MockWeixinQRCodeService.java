package me.andpay.ac.wpn.srv.test.service;

import me.andpay.ac.wpn.api.callback.CreateSceneQRCodeCallback;
import me.andpay.ac.wpn.api.model.CreateSceneQRCodeRequest;
import me.andpay.ac.wpn.api.model.QRCodeResult;
import me.andpay.ac.wpn.api.service.WeixinQRCodeService;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.Callback;
import me.andpay.ti.util.ValidateUtil;

/**
 * Created by cen on 2017/3/24.
 */
public class MockWeixinQRCodeService implements WeixinQRCodeService {
    @Override
    public QRCodeResult createSceneQRCode(CreateSceneQRCodeRequest createSceneQRCodeRequest) throws AppBizException {

        ValidateUtil.validate(createSceneQRCodeRequest);

        QRCodeResult qrCodeResult = new QRCodeResult();
        qrCodeResult.setSuccess(true);
        qrCodeResult.setUrl("http://api.andpay.me/qrcode/url");
        qrCodeResult.setQrCode("http://api.andpay.me/qrcode");
        return qrCodeResult;
    }

    @Override
    public void asyncCreateSceneQRCode(CreateSceneQRCodeRequest createSceneQRCodeRequest, @Callback CreateSceneQRCodeCallback createSceneQRCodeCallback) {
        ValidateUtil.validate(createSceneQRCodeRequest);

        QRCodeResult qrCodeResult = new QRCodeResult();
        qrCodeResult.setSuccess(true);
        qrCodeResult.setUrl("http://api.andpay.me/qrcode/url");
        qrCodeResult.setQrCode("http://api.andpay.me/qrcode");

        if(createSceneQRCodeCallback != null) {
            createSceneQRCodeCallback.onResult(qrCodeResult);
        }
    }
}
