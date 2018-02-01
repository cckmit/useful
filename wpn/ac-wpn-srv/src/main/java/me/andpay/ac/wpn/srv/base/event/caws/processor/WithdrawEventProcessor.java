package me.andpay.ac.wpn.srv.base.event.caws.processor;

import me.andpay.ac.bams.api.AppExcCodes;
import me.andpay.ac.cif.api.party.FullOrgParty;
import me.andpay.ac.cif.api.party.PartyService;
import me.andpay.ac.cif.api.product.ProductService;
import me.andpay.ac.common.api.dict.CachedDictViewClient;
import me.andpay.ac.wpn.srv.base.constant.DictClassCodes;
import me.andpay.ac.consts.SystemIds;
import me.andpay.ac.consts.wpn.TemplateNames;
import me.andpay.ac.event.acq.caws.AccountWithdrawEvent;
import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ac.wpn.api.model.SendWeixinMessageResult;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ac.wpn.srv.base.util.WpnUtils;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.util.JSON;
import me.andpay.ti.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tangzhiqiang on 17/9/7.
 */
public class WithdrawEventProcessor {

    private Logger LOG = LoggerFactory.getLogger(getClass());


    public static final String FIRST = "first";

    public static final String KEYWORD1 = "keyword1";

    public static final String KEYWORD2 = "keyword2";

    public static final String KEYWORD3 = "keyword3";

    public static final String KEYWORD4 = "keyword4";

    public static final String KEYWORD5 = "keyword5";

    public static final String REMARK = "remark";


    /**
     * 微信消息服务
     */
    @Autowired
    private WeixinMessageService weixinMessageService;

    /**
     * 产品服务接口
     */
    @Lnkwired
    private ProductService productService;

    /**
     * 商户服务
     */
    @Lnkwired
    private PartyService partyService;

    /**
     * 字典
     */
    private CachedDictViewClient dictViewClient;


    public void dealEvent(final AccountWithdrawEvent awe) {

        try {



            Map<String, String> dictMap = dictViewClient.getCodeMap(DictClassCodes.BIZ_TYPE_2_NOTIFY_TARGETS);
            String target = dictMap.get(awe.getBizType());
            if (target == null) {

                LOG.info( "The party type cann't null,WithdrawEventProcessor idFundOrder=" + awe.getIdFundOrder() + "bizType=" + awe.getBizType());
                return;
            }

            SendTempMsgToBindUserRequest sendTempMSgReq = new SendTempMsgToBindUserRequest();
            sendTempMSgReq.setTemplateName(TemplateNames.AC_CAWS_WITHDERAW_SUCC_NOTIRY_TEMPLATE);
            sendTempMSgReq.setTarget(target);
            sendTempMSgReq.setPartyId(awe.getOwner());
            sendTempMSgReq.setSourceSystemId(SystemIds.AC_CAWS);
            sendTempMSgReq.setTraceNo(SystemIds.AC_CAWS + awe.getId() + "#"
                    + StringUtil.format(WpnUtils.DATE_FORMAT_YYYYMMDDHHMMSS, new Date()));

            try {
                FullOrgParty fullOrgParty = partyService.getOrgPartyWithoutCheck(awe.getOwner());
                sendTempMSgReq.setUserName(fullOrgParty.getOrgParty().getCorporateRepresentUserName());
            } catch (Exception e) {
                LOG.info("Get party mobile no error!,partyId={}", awe.getOwner(), e);
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put(FIRST, awe.getOwnerName());
            params.put(KEYWORD1, StringUtil.format(WpnUtils.YYYY_MM_DD_HH_MM, awe.getCreateTime()));
            params.put(KEYWORD2, genBankInfo(awe));
            params.put(KEYWORD3, WpnUtils.formatAMTForStr(awe.getAmt()));
            params.put(KEYWORD4, WpnUtils.formatAMTForStr(awe.getWithdrawFee().negate()));
            params.put(KEYWORD5, WpnUtils.formatAMTForStr(awe.getFundAmt()));

            sendTempMSgReq.setParams(params);

            LOG.info("Send template message to bind user,reqData[{}]", JSON.getDefault().toJSONString(sendTempMSgReq));
            weixinMessageService.sendTemplateMessageToBindUser(sendTempMSgReq, new SendWeixinMessageCallback() {

                @Override
                public void onResult(SendWeixinMessageResult result) {
                    LOG.error("Semd template message end,withdrawCtrlId={},result={}", awe.getId(),
                            result.getResponseMsg());
                }
            });



        }catch (Exception ex) {
            LOG.error("dealEvent WithdrawEventProcessor error",ex);
        }

    }

    /**
     * 屏蔽结算账户信息
     *
     * @param settleAccountNo
     * @param startNums
     * @param endNums
     * @return
     */
    private String maskSettleAccountNo(String settleAccountNo, int startNums, int endNums) {
        if (StringUtil.isBlank(settleAccountNo) || (startNums + endNums) > StringUtil.length(settleAccountNo)) {
            throw new AppRtException(String.format("Input params error,settleAccountNo=%s,startNums=%s,endNums=%s",
                    new Object[] { settleAccountNo, startNums, endNums }));
        }

        return StringUtil.mark(settleAccountNo, "*".charAt(0), startNums, StringUtil.length(settleAccountNo) - endNums);
    }

    /**
     * 处理银行名称和结算账户
     *
     * @param fundOrder
     * @return
     */
    public String genBankInfo(AccountWithdrawEvent awe) {
        StringBuffer sb = new StringBuffer();
        sb.append(awe.getBankName());
        sb.append(" ");
        sb.append(maskSettleAccountNo(awe.getAccountNo(), 4, 4));

        return sb.toString();
    }

    @Autowired
    @Qualifier("ac-wpn.DictViewClient")
    public void setDictViewClient(CachedDictViewClient dictViewClient) {
        this.dictViewClient = dictViewClient;
    }

}
