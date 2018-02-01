package me.andpay.ac.wpn.srv.notify.scene;

import me.andpay.ac.consts.wpn.BindCardHolderStatuses;
import me.andpay.ac.consts.wpn.QRCodeParamNames;
import me.andpay.ac.event.wpn.consts.EventTypes;
import me.andpay.ac.event.wpn.qrcode.WeixinSceneQrCodeSubscibeEvent;
import me.andpay.ac.wpn.api.consts.WeiXinEventNames;
import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.api.consts.WeixinNotifyDealStatuses;
import me.andpay.ac.wpn.api.model.db.WeixinNotifyInfo;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.api.model.db.WxBindCardHolder;
import me.andpay.ac.wpn.api.model.db.WxPublicNumberUser;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxBindCardHolderCond;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberUserCond;
import me.andpay.ac.wpn.srv.dao.WeixinNotifyInfoDao;
import me.andpay.ac.wpn.srv.dao.WxBindCardHolderDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberUserDao;
import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ac.wpn.srv.notify.NotifySupport;
import me.andpay.ti.event.api.EventPublisher;
import me.andpay.ti.vault.api.TokenResponse;
import me.andpay.ti.vault.client.SimpleTokenClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交易凭证场景处理器
 * Created by cen on 2017/2/24.
 */
public class TxnVoucherSceneProcessor implements SceneQrCodeProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WxBindCardHolderDao wxBindCardHolderDao;

    @Autowired
    @Qualifier("ac-wpn.CardNoTokenClient")
    private SimpleTokenClient simpleTokenClient;

    private EventPublisher eventPublisher;

    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;

    @Autowired
    private WeixinNotifyInfoDao weixinNotifyInfoDao;

    @Override
    @Transactional
    public String process(WeixinQRCode weixinQRCode, NotifyContext notifyContext) {

        logger.info("start bind weixinCardHolder weixinNotifyInfoId=[{}]",notifyContext.getWeixinNotifyInfoId());

        Map<String,String> paramMap = weixinQRCode.getParamMap();
        Map<String,String> xmlData = notifyContext.getXmlData();
        String eventName = xmlData.get(WeiXinXmlKeys.EVENT);


        String cardNoToken = paramMap.get(QRCodeParamNames.CARD_NO_TOKEN);
        String openId = xmlData.get(WeiXinXmlKeys.FROM_USER_NAME);
        String appId = weixinQRCode.getAppId();


        QueryWxPublicNumberUserCond queryWxPublicNumberUserCond = new QueryWxPublicNumberUserCond();
        queryWxPublicNumberUserCond.setAppid(appId);
        queryWxPublicNumberUserCond.setOpenid(openId);
        WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.getUniqueForUpdate(queryWxPublicNumberUserCond);
        if(wxPublicNumberUser == null) {
            //创建公众号微信用户
            wxPublicNumberUser = new WxPublicNumberUser();
            wxPublicNumberUser.setAppid(appId);
            wxPublicNumberUser.setCreateTime(new Date());
            wxPublicNumberUser.setUpdateTime(wxPublicNumberUser.getCreateTime());
            wxPublicNumberUser.setOpenid(openId);
            wxPublicNumberUserDao.add(wxPublicNumberUser);
        }

        QueryWxBindCardHolderCond queryWxBindCardHolderCond = new QueryWxBindCardHolderCond();
        queryWxBindCardHolderCond.setCardNOToken(cardNoToken);
        queryWxBindCardHolderCond.setOrders("updateTime-");
        List<WxBindCardHolder> cardHolderList = wxBindCardHolderDao.query(queryWxBindCardHolderCond,0,1);
        boolean isNewBind = false;
        if(cardHolderList.size() > 0 ){
            WxBindCardHolder wxBindCardHolder = cardHolderList.get(0);
            wxBindCardHolder.setUpdateTime(new Date());
            wxBindCardHolder.setUserId(wxPublicNumberUser.getUserId());
            wxBindCardHolder.setBindStatus(BindCardHolderStatuses.UBDS_BIND);
            wxBindCardHolderDao.update(wxBindCardHolder);
        }else  {
            //绑定卡号
            WxBindCardHolder wxBindCardHolder = new WxBindCardHolder();
            wxBindCardHolder.setCardNOToken(cardNoToken);
            wxBindCardHolder.setUserId(wxPublicNumberUser.getUserId());
            wxBindCardHolder.setBindStatus(BindCardHolderStatuses.UBDS_BIND);
            wxBindCardHolder.setCreateTime(new Date());
            wxBindCardHolder.setUpdateTime(wxBindCardHolder.getCreateTime());
            wxBindCardHolderDao.add(wxBindCardHolder);
            isNewBind = true;
        }


        //发送事件
        WeixinSceneQrCodeSubscibeEvent event = new WeixinSceneQrCodeSubscibeEvent();
        event.setNewBind(isNewBind);
        event.setSceneId(weixinQRCode.getSceneId());
        if(eventName.equals(WeiXinEventNames.SUBSCIBE)) {
            event.setNewSubscibe(true);
        }else {
            event.setNewSubscibe(false);
        }
        event.setAppId(appId);
        event.setQrCodeBizType(weixinQRCode.getQrCodeBizType());
        event.setParamData(paramMap);
        event.setQrCodeType(weixinQRCode.getQrCodeType());
        event.setSubscibeOpenId(openId);

        eventPublisher.publish(EventTypes.WEIXIN_SCENE_QRCODE_SUBSLIBE,event);

        WeixinNotifyInfo weixinNotifyInfo = weixinNotifyInfoDao.getForUpdate(notifyContext.getWeixinNotifyInfoId());
        weixinNotifyInfo.setDealStatus(WeixinNotifyDealStatuses.WNDS_HAS_DEAL);
        weixinNotifyInfo.setUpdateTime(new Date());
        weixinNotifyInfoDao.update(weixinNotifyInfo);

        return NotifySupport.NOTIFY_COMPLITE;
    }

    @Autowired
    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
