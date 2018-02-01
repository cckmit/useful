package me.andpay.ac.wpn.srv.wxmsg;

import me.andpay.ac.common.api.bin.CardNoUtil;
import me.andpay.ac.consts.wpn.BindCardHolderStatuses;
import me.andpay.ac.consts.wpn.BindUserStatuses;
import me.andpay.ac.wpn.api.model.*;
import me.andpay.ac.wpn.api.model.db.*;
import me.andpay.ac.wpn.api.model.db.cond.*;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.inter.api.dto.WxTpMessage;
import me.andpay.ac.wpn.srv.dao.*;
import me.andpay.ac.wpn.srv.helper.WxBindUserMigrateHelper;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.Callback;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.mns.api.*;
import me.andpay.ti.mns.api.wxtpmsg.WxTempMessagePropertyNames;
import me.andpay.ti.util.*;
import me.andpay.ti.vault.api.TokenResponse;
import me.andpay.ti.vault.client.SimpleTokenClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by cen on 16/11/9.
 */
public class WeixinMessageServiceImpl implements WeixinMessageService {


    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 微信绑定用dao
     */
    @Autowired
    private WxBindUserDao wxBindUserDao;

    /**
     * 微信公众号用户dao
     */
    @Autowired
    private WxPublicNumberUserDao wxPublicNumberUserDao;

    /**
     * 微信模板消息dao
     */
    @Autowired
    private WxMessageTemplateDao wxMessageTemplateDao;

    /**
     * 消息分发器
     */
    @Lnkwired
    private MessageDeliveryService messageDeliveryService;


    @Autowired
    private WeixinAuthService weixinAuthService;


    @Autowired
    private DeliveryStrategy deliveryStrategy;

    @Autowired
    @Qualifier("ac-wpn.CardNoTokenClient")
    private SimpleTokenClient simpleTokenClient;

    @Autowired
    private WxBindCardHolderDao wxBindCardHolderDao;

    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;

    @Autowired
    private MessageSwitchConfigDao switchConfigDao;

    /**
     * 检测消息是否可以发送
     * @param wxMessageTemplate
     * @return
     */
    private boolean checkMessageCanSend(WxMessageTemplate wxMessageTemplate) {

        QueryMessageSwitchConfigCond configCond = new QueryMessageSwitchConfigCond();
        configCond.setMessageTemplateGroupId(wxMessageTemplate.getMessageTemplateGroupId());
        List<MessageSwitchConfig> switchConfigList =  switchConfigDao.query(configCond,0,1);
        if(switchConfigList== null||switchConfigList.size() == 0) {
            return true;
        }else {
            return switchConfigList.get(0).isOpen();
        }
    }

    private void messageForbidSend(SendWeixinMessageResult sendWeixinMessageResult,SendWeixinMessageCallback sendWeixinMessageCallback,String templdateName) {
        logger.error("message forbidSend templateName=[{}]",templdateName);
        sendWeixinMessageResult.setResponseMsg("message forbidSend");
        sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
    }

    /**
     * 查询模板消息
     * @param templateName
     * @return
     */
    private WxMessageTemplate queryWxTemplateMessage(String templateName,String appAlias){

        QueryWxMessageTemplateCond wxTemplateMessageCond = new QueryWxMessageTemplateCond();
        wxTemplateMessageCond.setTemplateName(templateName);
        wxTemplateMessageCond.setAppAlais(appAlias);
        WxMessageTemplate wxMessageTemplate = wxMessageTemplateDao.getUnique(wxTemplateMessageCond);
        return wxMessageTemplate;
    }

    private SendWeixinMessageCallback checkCallback(SendWeixinMessageCallback sendWeixinMessageCallback) {
        if(sendWeixinMessageCallback == null) {
            sendWeixinMessageCallback = new SendWeixinMessageCallback() {
                @Override
                public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                }
            };
        }

        return  sendWeixinMessageCallback;
    }

    private void tempMessageNotFound(SendWeixinMessageResult sendWeixinMessageResult,SendWeixinMessageCallback sendWeixinMessageCallback,String templdateName){
        logger.error("can not found templateMessage config templateName=[{}]",templdateName);
        sendWeixinMessageResult.setResponseMsg("can not found templateMessage");
        sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
    }

    private void sendMessage(SendTempMsgBaseRequest sendMsgReq,
                             WxMessageTemplate wxMessageTemplate,
                             WxPublicNumberUser wxPublicNumberUser,
                             SendWeixinMessageCallback sendWeixinMessageCallback,
                             final SendWeixinMessageResult sendWeixinMessageResult
                             ) throws AppBizException{
        //发送消息
        Message message = new Message();
        message.setMessageType(MessageTypes.WXTP_MSG);
        message.setSourceSystemId(sendMsgReq.getSourceSystemId());
        message.setTraceNo(sendMsgReq.getTraceNo());

        WxTpMessage wxTpMessage = new WxTpMessage();
        wxTpMessage.setAppId(wxPublicNumberUser.getAppid());
        wxTpMessage.setTemplate_id(wxMessageTemplate.getWeixinTemplateId());

        String url;
        if(StringUtil.isNotBlank(sendMsgReq.getUrl())) {
            url = sendMsgReq.getUrl();
        }else {
            url = wxMessageTemplate.getUrl();
            if(StringUtil.isNotBlank(url)){
                url = URLUtil.assembleUrl(url,sendMsgReq.getUrlParams());
            }
        }

        wxTpMessage.setUrl(url);
        wxTpMessage.setTouser(wxPublicNumberUser.getOpenid());
        wxTpMessage.setParams(sendMsgReq.getParams());
        wxTpMessage.setParamsConfig(wxMessageTemplate.getParamConfigsMap());

        message.setProperty(WxTempMessagePropertyNames.ACCESS_TOKEN, weixinAuthService.obtainAccessToken(wxTpMessage.getAppId()));
        message.setProperty(WxTempMessagePropertyNames.MSG_CONTENT,wxTpMessage.postJson());
        message.setProperty(WxTempMessagePropertyNames.TEMPLATE_NAME,wxMessageTemplate.getTemplateName());
        message.setPriority(MessagePriorities.HIGH);


        final SendWeixinMessageCallback finalCallback =  sendWeixinMessageCallback;
        messageDeliveryService.deliver(message, deliveryStrategy, new DeliveryReportSubscriber() {
            @Override
            public void onComplete(DeliveryReport deliveryReport) {
                sendWeixinMessageResult.setResponseMsg(deliveryReport.getDescription());
                if(deliveryReport.isDelivery()) {
                    sendWeixinMessageResult.setSuccess(true);
                }
                finalCallback.onResult(sendWeixinMessageResult);
            }
        });

    }


    @Override
    public void sendTemplateMessageToBindUser(SendTempMsgToBindUserRequest sendMsgReq,@Callback SendWeixinMessageCallback sendWeixinMessageCallback) throws AppBizException {

        logger.info("send weixin templateMessage target=[{}] userName=[{}],partyId=[{}],templateName=[{}],traceNo=[{}]",new String[]{sendMsgReq.getTarget(),
                sendMsgReq.getUserName(),sendMsgReq.getPartyId(),sendMsgReq.getTemplateName(),sendMsgReq.getTraceNo()});

        ValidateUtil.validate(sendMsgReq);

        sendWeixinMessageCallback = checkCallback(sendWeixinMessageCallback);

        SendWeixinMessageResult sendWeixinMessageResult = new SendWeixinMessageResult();
        sendWeixinMessageResult.setTraceNo(sendMsgReq.getTraceNo());

        try {

            WxMessageTemplate wxMessageTemplate = queryWxTemplateMessage(sendMsgReq.getTemplateName(),sendMsgReq.getTarget());
            if(wxMessageTemplate == null) {
                tempMessageNotFound(sendWeixinMessageResult,sendWeixinMessageCallback,sendMsgReq.getTemplateName());
                return;
            }

            if(!checkMessageCanSend(wxMessageTemplate)) {
                messageForbidSend(sendWeixinMessageResult,sendWeixinMessageCallback,sendMsgReq.getTemplateName());
                return;
            }

            QueryWxPublicNumberCond queryWxPublicNumberCond = new QueryWxPublicNumberCond();
            queryWxPublicNumberCond.setAppAlais(sendMsgReq.getTarget());
            WxPublicNumber wxPublicNumber =wxPublicNumberDao.getUnique(queryWxPublicNumberCond);
            if(wxPublicNumber == null) {
                logger.error("sendTemplateMessageToBindUser can not found publicnumber target=[{}]",sendMsgReq.getTarget());
                sendWeixinMessageResult.setResponseMsg("can not found publicnumber");
                sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
                return;
            }

            QueryWxBindUserCond queryWxBindUserCond = new QueryWxBindUserCond();
            queryWxBindUserCond.setUserName(sendMsgReq.getUserName());
            queryWxBindUserCond.setPartyId(sendMsgReq.getPartyId());
            queryWxBindUserCond.setStatus(BindUserStatuses.UBDS_BIND);
            queryWxBindUserCond.setOrders("updateTime-");

            List<WxBindUser> wxBindUsers = wxBindUserDao.query(queryWxBindUserCond,0,-1);
            if(wxBindUsers.size()==0) {
                logger.error("can not found bindUser partyId=[{}],userName=[{}]，bindType=[{}]",
                        new String[]{sendMsgReq.getPartyId(),sendMsgReq.getUserName()});
                sendWeixinMessageResult.setResponseMsg("can not found bindUser");
                sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
                return;
            }

            WxBindUser wxBindUser = wxBindUsers.get(0);
            WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.get(wxBindUser.getUserId());

            if(wxPublicNumberUser == null) {
                logger.error("can not found wxPublicNumberUser partyId=[{}],userName=[{}]",
                        new String[]{sendMsgReq.getPartyId(),sendMsgReq.getUserName()});
                sendWeixinMessageResult.setResponseMsg("can not found wxPublicNumberUser");
                sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
                return;
            }
            //TODO 决定消息是否可以发送,开关
            //-------
            sendMessage(sendMsgReq, wxMessageTemplate,wxPublicNumberUser,sendWeixinMessageCallback,sendWeixinMessageResult);

        }catch (Exception ex) {
            logger.error("send message error",ex);
            sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
        }


    }

    @Override
    public void sendTemplateMessageToCardHolder(SendTempMsgToCardHolderRequest sendMsgReq,
                                                @Callback SendWeixinMessageCallback sendWeixinMessageCallback){

        logger.info("send weixin templateMessage target=[{}], templateName=[{}],traceNo=[{}],cardNo=[{}]",new String[]{sendMsgReq.getTarget(),
                sendMsgReq.getTemplateName(),sendMsgReq.getTraceNo(), CardNoUtil.getShortCardNo(sendMsgReq.getCardNo())});

        SendWeixinMessageResult sendWeixinMessageResult = new SendWeixinMessageResult();
        sendWeixinMessageResult.setTraceNo(sendMsgReq.getTraceNo());

        try {

            sendWeixinMessageCallback = checkCallback(sendWeixinMessageCallback);
            ValidateUtil.validate(sendMsgReq);

            WxMessageTemplate wxMessageTemplate = queryWxTemplateMessage(sendMsgReq.getTemplateName(),sendMsgReq.getTarget());
            if(wxMessageTemplate == null) {
                tempMessageNotFound(sendWeixinMessageResult,sendWeixinMessageCallback,sendMsgReq.getTemplateName());
                return;
            }
            if(!checkMessageCanSend(wxMessageTemplate)) {
                messageForbidSend(sendWeixinMessageResult,sendWeixinMessageCallback,sendMsgReq.getTemplateName());
                return;
            }

            QueryWxPublicNumberCond queryWxPublicNumberCond = new QueryWxPublicNumberCond();
            queryWxPublicNumberCond.setAppAlais(sendMsgReq.getTarget());
            WxPublicNumber wxPublicNumber =wxPublicNumberDao.getUnique(queryWxPublicNumberCond);
            if(wxPublicNumber == null) {
                logger.error("sendTemplateMessageToCardHolder can not found publicnumber target=[{}]",sendMsgReq.getTarget());
                sendWeixinMessageResult.setResponseMsg("can not found publicnumber");
                sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
                return;
            }

            QueryWxBindCardHolderCond queryWxBindCardHolderCond = new QueryWxBindCardHolderCond();
            TokenResponse cardNoToken = simpleTokenClient.token(sendMsgReq.getCardNo());
            queryWxBindCardHolderCond.setCardNOToken(cardNoToken.getInternalToken());
            queryWxBindCardHolderCond.setBindStatus(BindCardHolderStatuses.UBDS_BIND);
            queryWxBindCardHolderCond.setOrders("updateTime-");
            List<WxBindCardHolder> cardHolders = wxBindCardHolderDao.query(queryWxBindCardHolderCond,0,1);

            if(cardHolders.size()==0) {
                logger.error("can not found bindCardHolder cardNOToken=[{}]",
                        new String[]{cardNoToken.getInternalToken()});
                sendWeixinMessageResult.setResponseMsg("can not found cardNOHolder");
                sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
                return;
            }
            WxBindCardHolder wxBindCardHolder = cardHolders.get(0);
            WxPublicNumberUser wxPublicNumberUser = wxPublicNumberUserDao.get(wxBindCardHolder.getUserId());
            if(wxPublicNumberUser == null) {
                logger.error("can not found wxPublicNumberUser userId=[{}]",
                        new String[]{wxBindCardHolder.getUserId().toString()});
                sendWeixinMessageResult.setResponseMsg("can not found wxPublicNumberUser");
                sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
                return;
            }

            //发送消息
            sendMessage(sendMsgReq, wxMessageTemplate,wxPublicNumberUser,sendWeixinMessageCallback,sendWeixinMessageResult);

        }catch (Exception ex) {
            logger.error("send message error",ex);
            sendWeixinMessageCallback.onResult(sendWeixinMessageResult);
        }


    }


}
