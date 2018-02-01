package me.andpay.ac.wpn.srv.base.event.cmmks.processor;

import me.andpay.ac.bams.api.*;
import me.andpay.ac.cas.api.txn.CashAcctTxnService;
import me.andpay.ac.cif.api.party.FullOrgParty;
import me.andpay.ac.cif.api.party.PartyService;
import me.andpay.ac.common.api.dict.CachedDictViewClient;
import me.andpay.ac.common.api.syspara.SysParaService;
import me.andpay.ac.consts.SysSwitch;
import me.andpay.ac.consts.SystemIds;
import me.andpay.ac.consts.wpn.AppAlais;
import me.andpay.ac.consts.wpn.TemplateNames;
import me.andpay.ac.event.acq.cmmks.RealtimeProfitSettleEvent;
import me.andpay.ac.vacct.api.ops.txn.VAcctTxnArgs;
import me.andpay.ac.vacct.api.ops.txn.VAcctTxnVoucher;
import me.andpay.ac.vacct.client.ops.sec.VAcctSecDataClient;
import me.andpay.ac.vacct.consts.*;
import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ac.wpn.api.model.SendWeixinMessageResult;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.srv.base.constant.DictClassCodes;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tangzhiqiang on 17/9/7.
 */
public class RealtimeProfitSettleEventProcessor {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());


    /**
     * 微信消息发送服务
     */
    @Autowired
    private WeixinMessageService messageService;

    /**
     * 商户服务
     */
    @Lnkwired
    private PartyService partyService;


    private CachedDictViewClient dictViewClient;

    public void dealEvent(final RealtimeProfitSettleEvent rpse) {

        try {

            Map<String, String> dictMap = dictViewClient.getCodeMap(DictClassCodes.BIZ_TYPE_2_NOTIFY_TARGETS);
            String target = dictMap.get(rpse.getSettlePartyType());
            if (target == null) {
                LOG.info( "The party type cann't null,DailyProfitSettleEventProcessor settlePartyId=" + rpse.getSettlePartyId());
                return;
            }

            SendTempMsgToBindUserRequest request = new SendTempMsgToBindUserRequest();

            request.setTemplateName(TemplateNames.AC_CMMKS_ACCOUNT_CHANGE_REALTIME_NOTIFY_TEMPLDATE);
            request.setPartyId(rpse.getSettlePartyId());
            request.setTarget(target);
            request.setSourceSystemId(SystemIds.AC_CMMKS);
            request.setTraceNo(rpse.getTraceNo());

            try {
                FullOrgParty fullOrgParty = partyService.getOrgPartyWithoutCheck(rpse.getSettlePartyId());
                request.setUserName(fullOrgParty.getOrgParty().getCorporateRepresentUserName());
            } catch (Exception e) {
                LOG.info("Get settle party mobile no error!", e);
            }

            try {
                Map<String, String> params = new HashMap<String, String>();
                params.put("first", rpse.getSettlePartyNameCn());
                params.put("keyword1", DateUtil.format("yyyy/MM/dd HH:mm", new Date()));
                params.put("keyword2", rpse.getSettleAmt().toString());
                params.put("keyword3", rpse.getAmtAfter().toString());

                request.setParams(params);

                messageService.sendTemplateMessageToBindUser(request, new SendWeixinMessageCallback() {

                    public void onResult(SendWeixinMessageResult sendWeixinMessageResult) {
                        LOG.info("Wechat msg sed success, success={}, responseMsg={}", sendWeixinMessageResult.isSuccess(),
                                sendWeixinMessageResult.getResponseMsg());
                    }
                });
            } catch (Exception e) {
                LOG.error("Wechat msg sed error, traceNo={}, settlePartyId={}",
                        new Object[] { request.getTraceNo(), rpse.getSettlePartyId(), e });
            }
        } catch (Exception e) {

            LOG.error("dealEvent RealtimeProfitSettleEventProcessor error",e);

        }


    }

    @Autowired
    @Qualifier("ac-wpn.DictViewClient")
    public void setDictViewClient(CachedDictViewClient dictViewClient) {
        this.dictViewClient = dictViewClient;
    }




}
