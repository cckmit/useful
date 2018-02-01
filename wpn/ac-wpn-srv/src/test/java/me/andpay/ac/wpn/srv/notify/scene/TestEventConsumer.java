package me.andpay.ac.wpn.srv.notify.scene;

import me.andpay.ac.consts.wpn.QRCodeParamNames;
import me.andpay.ac.event.wpn.consts.EventTypes;
import me.andpay.ac.event.wpn.qrcode.WeixinSceneQrCodeSubscibeEvent;
import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ti.event.api.EventConsumer;
import me.andpay.ti.util.JSON;
import me.andpay.ti.vault.api.TokenResponse;

import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by cen on 2017/2/27.
 */
public class TestEventConsumer implements EventConsumer {

    private WeixinQRCode weixinQRCode;
    private NotifyContext notifyContext;
    @Override
    public void onEvent(String event, Object o) {


        Map<String,String> paramMap = weixinQRCode.getParamMap();
        Map<String,String> xmlData = notifyContext.getXmlData();

        assertEquals(event, EventTypes.WEIXIN_SCENE_QRCODE_SUBSLIBE);
        WeixinSceneQrCodeSubscibeEvent eventData = (WeixinSceneQrCodeSubscibeEvent)o;

        assertEquals(eventData.getQrCodeBizType(),weixinQRCode.getQrCodeBizType());
        assertEquals(eventData.getQrCodeType(),weixinQRCode.getQrCodeType());
        assertEquals(eventData.getSceneId(),weixinQRCode.getSceneId());
        assertEquals(eventData.getSubscibeOpenId(),xmlData.get(WeiXinXmlKeys.FROM_USER_NAME));
        assertEquals(JSON.getDefault().toJSONString(eventData.getParamData()),JSON.getDefault().toJSONString(paramMap));
        assertEquals(eventData.getAppId(),weixinQRCode.getAppId());
        assertTrue(eventData.isNewSubscibe());

    }

    public void setData(WeixinQRCode weixinQRCode, NotifyContext notifyContext) {
        this.weixinQRCode = weixinQRCode;
        this.notifyContext = notifyContext;
    }
}