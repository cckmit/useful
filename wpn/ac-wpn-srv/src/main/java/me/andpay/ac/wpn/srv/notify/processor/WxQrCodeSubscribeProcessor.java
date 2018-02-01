package me.andpay.ac.wpn.srv.notify.processor;

import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.api.model.db.cond.QueryWeixinQRCodeCond;
import me.andpay.ac.wpn.srv.dao.WeixinQRCodeDao;
import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ac.wpn.srv.notify.NotifySupport;
import me.andpay.ac.wpn.srv.notify.WeixinNotifyProcessor;
import me.andpay.ac.wpn.srv.notify.scene.SceneQrCodeProcessor;
import me.andpay.ti.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信关注取消事件
 * Created by cen on 2017/2/24.
 */
public class WxQrCodeSubscribeProcessor implements WeixinNotifyProcessor{

    private Map<String,SceneQrCodeProcessor> sceneQrCodeProcessors = new HashMap<String, SceneQrCodeProcessor>();

    @Autowired
    private WeixinQRCodeDao weixinQRCodeDao;

    /**
     * 处理上下文
     * @param notifyContext
     * @return
     */
    @Override
    public String process(NotifyContext notifyContext) {

        Map<String,String> xmlData  = notifyContext.getXmlData();

        //带场景的二维码处理
        String eventKey = xmlData.get(WeiXinXmlKeys.EVENT_KEY);
        int index = eventKey.indexOf("qrscene_");
        if(index>=0) {
            eventKey = eventKey.substring(index+8,eventKey.length());
        }
        if(StringUtil.isNotBlank(eventKey)) {
            QueryWeixinQRCodeCond queryWeixinQRCodeCond = new QueryWeixinQRCodeCond();
            queryWeixinQRCodeCond.setAppId(notifyContext.getAppId());
            queryWeixinQRCodeCond.setSceneId(eventKey);
            queryWeixinQRCodeCond.setOrders("createTime-");
            List<WeixinQRCode> weixinQRCodeList = weixinQRCodeDao.query(queryWeixinQRCodeCond,0,1);
            if(weixinQRCodeList.size() != 0) {
                WeixinQRCode weixinQRCode = weixinQRCodeList.get(0);
                SceneQrCodeProcessor sceneQrCodeProcessor = sceneQrCodeProcessors.get(weixinQRCode.getQrCodeBizType());
                if(sceneQrCodeProcessor != null) {
                    return sceneQrCodeProcessor.process(weixinQRCode,notifyContext);
                }
            }
        }

        return NotifySupport.NOTIFY_COMPLITE;
    }


    public void setSceneQrCodeProcessors(Map<String, SceneQrCodeProcessor> sceneQrCodeProcessors) {
        this.sceneQrCodeProcessors = sceneQrCodeProcessors;
    }
}
