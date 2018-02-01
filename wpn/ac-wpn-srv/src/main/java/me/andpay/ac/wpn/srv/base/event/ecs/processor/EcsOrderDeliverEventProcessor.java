package me.andpay.ac.wpn.srv.base.event.ecs.processor;

import me.andpay.ac.cas.api.txn.CashAcctTxnService;
import me.andpay.ac.cif.api.party.FullOrgParty;
import me.andpay.ac.cif.api.party.PartyService;
import me.andpay.ac.common.api.dict.CachedDictViewClient;
import me.andpay.ac.consts.SystemIds;
import me.andpay.ac.consts.wpn.TemplateNames;
import me.andpay.ac.event.ecs.order.EcsOrderDeliverEvent;
import me.andpay.ac.event.ecs.order.EcsOrderDetail;
import me.andpay.ac.vacct.client.ops.sec.VAcctSecDataClient;
import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ac.wpn.api.model.SendWeixinMessageResult;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.srv.base.constant.DictClassCodes;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tangzhiqiang on 17/9/11.
 */
public class EcsOrderDeliverEventProcessor {


    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());


    /**
     * 微信消息发送服务
     */
    @Lnkwired
    private WeixinMessageService messageService;

    /**
     * 商户服务
     */
    @Lnkwired
    private PartyService partyService;

    private CachedDictViewClient dictViewClient;
    
    public void dealEvent(final EcsOrderDeliverEvent eode) {

        try{
            Map<String, String> dictMap = dictViewClient.getCodeMap(DictClassCodes.BIZ_TYPE_2_NOTIFY_TARGETS);
            String target = dictMap.get(eode.getChannel());
            if (target == null) {
                LOG.info( "The party type cann't null,EcsOrderDeliverEventProcessor=" + eode.getOrderNo() + "bizType=" + eode.getChannel());
                return;
            }

            SendTempMsgToBindUserRequest request = new SendTempMsgToBindUserRequest();
            request.setTemplateName(TemplateNames.AC_ECS_ORDER_DELIVER_NOTIFY_TEMPLATE);
            request.setPartyId(eode.getBuyerPartyId());
            request.setTarget(target);
            request.setSourceSystemId(SystemIds.AC_ECS);
            request.setTraceNo(eode.getOrderNo());

            try {
                FullOrgParty fullOrgParty = partyService.getOrgPartyWithoutCheck(eode.getBuyerPartyId());
                request.setUserName(fullOrgParty.getOrgParty().getCorporateRepresentUserName());
            } catch (Exception e) {
                LOG.info("Get settle party mobile no error!", e);
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("first", eode.getConsigneeName());
            //订单内容
            if(eode.getEcsOrderDetails().size()>0){
                EcsOrderDetail detail = eode.getEcsOrderDetails().get(0);
                params.put("keyword1", detail.getGoodsName());
            }else{
                params.put("keyword1", eode.getOrderNo());
            }
            params.put("keyword2", eode.getLogisticsComName());
            params.put("keyword3", eode.getAirWaybillNo());
            params.put("keyword4", eode.getConsigneeName() + " " + eode.getConsigneeAddress());

            request.setParams(params);
            try {
                messageService.sendTemplateMessageToBindUser(request, new SendWeixinMessageCallback() {

                    public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                        LOG.info("Wechat msg sed success, success={}, responseMsg={}", sendWeixinMessageResult.isSuccess(),
                                sendWeixinMessageResult.getResponseMsg());
                    }
                });
            } catch (Exception e) {
                LOG.error("Wechat msg sed error, traceNo={}, buyerPartyId={}",
                        new Object[] { request.getTraceNo(), eode.getBuyerPartyId(), e });
            }
        }catch (Exception e){
            LOG.info( "EcsOrderDeliverEventProcessor sendMsg error orderNo=" + eode.getOrderNo());
        }





    }

    @Autowired
    @Qualifier("ac-wpn.DictViewClient")
    public void setDictViewClient(CachedDictViewClient dictViewClient) {
        this.dictViewClient = dictViewClient;
    }
}
