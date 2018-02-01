package me.andpay.ac.wpn.srv.service;


import me.andpay.ac.consts.wpn.*;
import me.andpay.ac.wpn.api.consts.DefaultGroupId;
import me.andpay.ac.wpn.api.consts.MessageAppIds;
import me.andpay.ac.wpn.api.consts.SwitchConfigTargetTypes;
import me.andpay.ac.wpn.api.consts.WxTpMessageConfigNames;
import me.andpay.ac.wpn.api.model.db.*;
import me.andpay.ac.wpn.srv.dao.*;
import me.andpay.ti.util.JSON;
import me.andpay.ti.vault.client.SimpleTokenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * Created by cen on 2016/11/16.
 * 数据初始化服务
 */
public class DataInitServiceImpl implements DataInitService{

    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;

    @Autowired
    private WxBindUserDao wxBindUserDao;

    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;
    @Autowired
    private WxMessageTemplateDao wxMessageTemplateDao;

    @Autowired
    private WeixinQRCodeDao weixinQRCodeDao;

    @Autowired
    private WxBindCardHolderDao wxBindCardHolderDao;

    @Autowired
    @Qualifier("ac-wpn.CardNoTokenClient")
    private SimpleTokenClient simpleTokenClient;

    @Autowired
    private MessageSwitchConfigDao messageSwitchConfigDao;

    @Autowired
    private MessageTemplateGroupDao messageTemplateGroupDao;

    public WxBindCardHolder createWxBindCardHolder(Long userId) {

        //创建绑定用户
        WxBindCardHolder wxBindCardHolder = new WxBindCardHolder();
        wxBindCardHolder.setUserId(userId);
        wxBindCardHolder.setBindStatus(BindUserStatuses.UBDS_BIND);
        wxBindCardHolder.setUpdateTime(new Date());
        wxBindCardHolder.setCreateTime(new Date());
        wxBindCardHolder.setCardNOToken(simpleTokenClient.token(CARDNO).getInternalToken());
        Long id = wxBindCardHolderDao.add(wxBindCardHolder);

        wxBindCardHolder.setWxBindCardHolderId(id);
        return  wxBindCardHolder;
    }


    public WxPublicNumber createPublicNumber(){
        WxPublicNumber wxPublicNumber = new WxPublicNumber();
        wxPublicNumber.setAppId(APPID);
        wxPublicNumber.setCreateTime(new Date());
        wxPublicNumber.setUpdateTime(new Date());
        wxPublicNumber.setName("我的测试号");
        wxPublicNumber.setSecret(SECRET);
        wxPublicNumber.setToken("12121313121212121");
        wxPublicNumber.setAppAlais(AppAlais.ANDPAY_PARTNER);
        wxPublicNumberDao.add(wxPublicNumber);
        return wxPublicNumber;
    }

    public WxPublicNumberUser createPublicNumberUser(){
        WxPublicNumberUser wxPublicNumberUser = new WxPublicNumberUser();
        wxPublicNumberUser.setCreateTime(new Date());
        wxPublicNumberUser.setUpdateTime(wxPublicNumberUser.getCreateTime());
        wxPublicNumberUser.setAppid(APPID);
        wxPublicNumberUser.setOpenid("oLjPPs-djjn7LvZiwv1ynUUGEbSs");
        wxPublicNumberUserDao.add(wxPublicNumberUser);
        return wxPublicNumberUser;
    }

    public WxBindUser createBindUser(Long userId) {
        WxBindUser wxBindUser = new WxBindUser();
        wxBindUser.setCreateTime(new Date());
        wxBindUser.setUpdateTime(wxBindUser.getCreateTime());
        wxBindUser.setBindType(BindTypes.BDT_PTN);
        wxBindUser.setUserName("15618691708");
        wxBindUser.setPartyId("1014816000005522");
        wxBindUser.setStatus(BindUserStatuses.UBDS_BIND);
        wxBindUser.setUserId(userId);
        wxBindUserDao.add(wxBindUser);
        return wxBindUser;
    }




    @Override
    public WxDbInitData createInitData() {

        WxDbInitData wxDbInitData = new WxDbInitData();

        WxPublicNumber wxPublicNumber = createPublicNumber();
        wxDbInitData.setWxPublicNumber(wxPublicNumber);

        WxPublicNumberUser wxPublicNumberUser = createPublicNumberUser();
        wxDbInitData.setWxPublicNumberUser(wxPublicNumberUser);

        WxBindUser wxBindUser = createBindUser(wxPublicNumberUser.getUserId());
        wxDbInitData.setWxBindUser(wxBindUser);

        WxBindCardHolder wxBindCardHolder = createWxBindCardHolder(wxPublicNumberUser.getUserId());
        wxDbInitData.setWxBindCardHolder(wxBindCardHolder);
        return wxDbInitData;
    }

    public WxMessageTemplate createWxTemplateMessage(){
        WxMessageTemplate wxMessageTemplate = new WxMessageTemplate();
        wxMessageTemplate.setCreateTime(new Date());
        wxMessageTemplate.setUpdateTime(new Date());
        wxMessageTemplate.setUrl("http://www.andpay.me");
        wxMessageTemplate.setTemplateDesc("desc");
        wxMessageTemplate.setTemplateName(TemplateNames.AC_OSS_DELIVER_GOODS_NOTIFY_TEMPLATE);
        wxMessageTemplate.setWeixinTemplateId("5HgHkwvIP8JjcTNgb3z7CShBw-0KTSbuByG1ylOFH0g");
        wxMessageTemplate.setTemplateName("交易成功");
        wxMessageTemplate.setAppAlais(AppAlais.ANDPAY_PARTNER);
        wxMessageTemplate.setMessageTemplateGroupId(DefaultGroupId.ID);
        Map<String,Object> parmas = new HashMap<String, Object>();
        Map<String,String> config = new HashMap<String, String>();
        config.put(WxTpMessageConfigNames.WCN_FORMAT,"{#0}元,在加{#1}元");
        config.put(WxTpMessageConfigNames.WCN_COLOR,"#173177");
        parmas.put("amt",config);
        parmas.put("content","{}");
        wxMessageTemplate.setParamConfigs(JSON.getDefault().toJSONString(parmas));
        wxMessageTemplateDao.add(wxMessageTemplate);
        return wxMessageTemplate;
    }

    public WeixinQRCode createWeixinQrCode() {

        WeixinQRCode weixinQRCode = new WeixinQRCode();
        weixinQRCode.setAppId(DataInitServiceImpl.APPID);
        weixinQRCode.setQrCodeShortUrl("http://weixinqr/121323");
        weixinQRCode.setCreateTime(new Date());
        weixinQRCode.setUpdateTime(weixinQRCode.getCreateTime());
        weixinQRCode.setSceneId("123456");
        weixinQRCode.setTicket("1231312323");
        weixinQRCode.setQrCodeBizType(QRCodeBizTypes.QRBT_TXN_VOUCHER_CODE);
        weixinQRCode.setQrCodeType(QRCodeTypes.QRT_SCAN);

        Map<String,String> jsonMap = new HashMap<String, String>();
        jsonMap.put(QRCodeParamNames.CARD_NO_TOKEN,"1213131413123123123");
        jsonMap.put(QRCodeParamNames.TXN_ID,"12313123123");
        weixinQRCode.setParamJson(JSON.getDefault().toJSONString(jsonMap));

        Long  qrCodeParmaId = weixinQRCodeDao.add(weixinQRCode);
        weixinQRCode.setWeixinQRCodeId(qrCodeParmaId);
        return weixinQRCode;
    }


    public List<MessageSwitchConfig> createMessageSwitchConfigList() {
        List<MessageSwitchConfig> switchConfigList = new ArrayList<MessageSwitchConfig>();

        MessageTemplateGroup messageTemplateGroup = new MessageTemplateGroup();
        messageTemplateGroup.setAppId(MessageAppIds.MSA_PTN);
        messageTemplateGroup.setCreateTime(new Date());
        messageTemplateGroup.setGroupName("活动通知");
        messageTemplateGroup.setPriority(1);
        Long groupId = messageTemplateGroupDao.add(messageTemplateGroup);

        MessageSwitchConfig messageSwitchConfig = new MessageSwitchConfig();
        messageSwitchConfig.setMessageTemplateGroupId(groupId);
        messageSwitchConfig.setTargetType(SwitchConfigTargetTypes.SCT_PARTY);
        messageSwitchConfig.setTarget(DataInitService.PATYID);
        messageSwitchConfig.setOpen(false);
        switchConfigList.add(messageSwitchConfig);
        messageSwitchConfigDao.add(messageSwitchConfig);


        messageTemplateGroup = new MessageTemplateGroup();
        messageTemplateGroup.setAppId(MessageAppIds.MSA_PTN);
        messageTemplateGroup.setCreateTime(new Date());
        messageTemplateGroup.setGroupName("到账通知");
        messageTemplateGroup.setPriority(2);
        groupId = messageTemplateGroupDao.add(messageTemplateGroup);

        messageSwitchConfig = new MessageSwitchConfig();
        messageSwitchConfig.setMessageTemplateGroupId(groupId);
        messageSwitchConfig.setTargetType(SwitchConfigTargetTypes.SCT_PARTY);
        messageSwitchConfig.setTarget(DataInitService.PATYID);
        messageSwitchConfig.setOpen(true);
        switchConfigList.add(messageSwitchConfig);
        messageSwitchConfigDao.add(messageSwitchConfig);


        messageTemplateGroup = new MessageTemplateGroup();
        messageTemplateGroup.setAppId(MessageAppIds.MSA_PTN);
        messageTemplateGroup.setCreateTime(new Date());
        messageTemplateGroup.setGroupName("平台通知");
        messageTemplateGroup.setPriority(3);
        groupId = messageTemplateGroupDao.add(messageTemplateGroup);



        return switchConfigList;
     }

}
