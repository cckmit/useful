package me.andpay.ac.wpn.api.callback;

import me.andpay.ac.wpn.api.model.QRCodeResult;

/**
 * 二维码生成回调接口
 * Created by cen on 2017/3/8.
 */
public interface CreateSceneQRCodeCallback {

    public void onResult(QRCodeResult qrCodeResult);
}
