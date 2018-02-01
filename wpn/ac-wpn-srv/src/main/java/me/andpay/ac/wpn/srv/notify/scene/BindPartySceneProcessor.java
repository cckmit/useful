package me.andpay.ac.wpn.srv.notify.scene;

import me.andpay.ac.consts.wpn.AppAlais;
import me.andpay.ac.consts.wpn.BindTypes;
import me.andpay.ac.consts.wpn.QRCodeParamNames;
import me.andpay.ac.wpn.api.consts.WeiXinXmlKeys;
import me.andpay.ac.wpn.api.model.WxUserBindRequest;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberUserCond;
import me.andpay.ac.wpn.api.service.WeixinUserBindService;
import me.andpay.ac.wpn.srv.dao.WxBindUserDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberUserDao;
import me.andpay.ac.wpn.srv.notify.NotifyContext;
import me.andpay.ac.wpn.srv.notify.NotifySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易凭证场景处理器
 * Created by cen on 2017/2/24.
 */
public class BindPartySceneProcessor implements SceneQrCodeProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;

    @Autowired
    private WxBindUserDao wxBindUserDao;

    @Autowired
    private WeixinUserBindService weixinUserBindService;

    @Override
    public String process(WeixinQRCode weixinQRCode, NotifyContext notifyContext) {

        logger.info("start bind party weixinNotifyInfoId=[{}]",notifyContext.getWeixinNotifyInfoId());

        Map<String,String> paramMap = weixinQRCode.getParamMap();
        String partyId = paramMap.get(QRCodeParamNames.PARTY_ID);
        String userName = paramMap.get(QRCodeParamNames.USERNAME);
        String bindPartyTarget = paramMap.get(QRCodeParamNames.BIND_PARTY_TARGET);


        Map<String,String> xmlData = notifyContext.getXmlData();
        String openId = xmlData.get(WeiXinXmlKeys.FROM_USER_NAME);
        String appId = weixinQRCode.getAppId();

        QueryWxPublicNumberUserCond queryWxPublicNumberUserCond = new QueryWxPublicNumberUserCond();
        queryWxPublicNumberUserCond.setAppid(appId);
        queryWxPublicNumberUserCond.setOpenid(openId);

        Map<String,String> appMapBindType = new HashMap<String, String>(){{
            put(AppAlais.ANDPAY_PARTNER,BindTypes.BDT_PTN);
            put(AppAlais.ANDPAY_SERVICE_PLATFORM,BindTypes.BDT_APU);
        }};

        WxUserBindRequest wxUserBindRequest = new WxUserBindRequest();
        wxUserBindRequest.setBindType(appMapBindType.get(bindPartyTarget));
        wxUserBindRequest.setPartyId(partyId);
        wxUserBindRequest.setUserName(userName);
        wxUserBindRequest.setOpenid(openId);
        wxUserBindRequest.setAppid(appId);
        try {
            weixinUserBindService.weixinUserBind(wxUserBindRequest);
        }catch (Exception ex) {
            logger.error("bind error",ex);
        }

        return NotifySupport.NOTIFY_COMPLITE;
    }



}
