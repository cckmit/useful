package me.andpay.ac.wpn.srv.base.event.cmmks.processor;

import me.andpay.ac.bams.api.AppExcCodes;
import me.andpay.ac.cas.api.data.CashAcctDataService;
import me.andpay.ac.cif.api.party.FullOrgParty;
import me.andpay.ac.cif.api.party.PartyService;
import me.andpay.ac.common.api.dict.CachedDictViewClient;
import me.andpay.ac.common.api.syspara.SysParaService;
import me.andpay.ac.consts.SysSwitch;
import me.andpay.ac.consts.SystemIds;
import me.andpay.ac.consts.wpn.AppAlais;
import me.andpay.ac.consts.wpn.TemplateNames;
import me.andpay.ac.event.acq.cmmks.DailyProfitSettleEvent;
import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ac.wpn.api.model.SendWeixinMessageResult;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.srv.base.constant.DictClassCodes;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.quartz.QuartzJobExeContext;
import me.andpay.ti.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Tangzhiqiang on 17/9/7.
 */
public class DailyProfitSettleEventProcessor {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * 微信消息服务
     */
    @Autowired
    private WeixinMessageService messageService;

    /**
     * 现金账户数据服务
     */
    @Lnkwired
    private CashAcctDataService cashAcctDataService;

    /**
     * 商户服务
     */
    @Lnkwired
    private PartyService partyService;

    private CachedDictViewClient dictViewClient;

    public void dealEvent(final DailyProfitSettleEvent dpse) {

        try {

            Map<String, String> dictMap = dictViewClient.getCodeMap(DictClassCodes.BIZ_TYPE_2_NOTIFY_TARGETS);
            String target = dictMap.get(dpse.getSettlePartyType());
            if (target == null) {
                LOG.info( "The party type cann't null,DailyProfitSettleEventProcessor settlePartyId=" + dpse.getSettlePartyId() + "settlePartyType=" + dpse.getSettlePartyType());
                return;
            }


            SendTempMsgToBindUserRequest request = new SendTempMsgToBindUserRequest();

            request.setTemplateName(TemplateNames.AC_CMMKS_ACCOUNT_CHANGE_EVERYDAY_NOTIFY_TEMPLDATE);
            request.setPartyId(dpse.getSettlePartyId());
            request.setTarget(target);
            request.setSourceSystemId(SystemIds.AC_WPN);
            request.setTraceNo(getTraceNo());

            try {
                FullOrgParty fullOrgParty = partyService.getOrgPartyWithoutCheck(dpse.getSettlePartyId());
                request.setUserName(fullOrgParty.getOrgParty().getCorporateRepresentUserName());
            } catch (Exception e) {
                LOG.info("Get settle party mobile no error!", e);
            }

            BigDecimal balance = cashAcctDataService.getAcct(dpse.getSettleAccountNo()).getBalance();

            Map<String, String> params = new HashMap<String, String>();
            params.put("first", dpse.getSettlePartyName());
            params.put("keyword1", DateUtil.format("yyyy/MM/dd HH:mm", new Date()));
            params.put("keyword2", dpse.getSettleAmt().toString());
            params.put("keyword3", balance.toString());

            request.setParams(params);
            try {
                messageService.sendTemplateMessageToBindUser(request, new SendWeixinMessageCallback() {

                    public void onResult(SendWeixinMessageResult result) {
                        LOG.info("Wechat msg sed success={}, responseMsg={}", result.isSuccess(), result.getResponseMsg());
                    }

                });
            } catch (Exception e) {
                LOG.error("Wechat msg sed error, traceNo={}, settlePartyId={}",
                        new Object[] { request.getTraceNo(), dpse.getSettlePartyId(), e });
            }
        } catch (Exception e) {
            LOG.error("dealEvent DailyProfitSettleEventProcessor error",e);

        }


    }

    public static String getTraceNo(){
        return "CALCBATCH" + DateUtil.format("yyyyMMddHHmmssS", new Date()) + new Random().nextInt(10000);
    }


    @Autowired
    @Qualifier("ac-wpn.DictViewClient")
    public void setDictViewClient(CachedDictViewClient dictViewClient) {
        this.dictViewClient = dictViewClient;
    }
}
