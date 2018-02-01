package me.andpay.ac.wpn.srv.notify;

import me.andpay.ac.wpn.api.consts.WeixinNotifyDealStatuses;
import me.andpay.ac.wpn.api.model.WeixinNotifyRequest;
import me.andpay.ac.wpn.api.model.db.WeixinNotifyInfo;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.service.WeixinNotifyReceiverService;
import me.andpay.ac.wpn.inter.api.util.SimpleDigestUtil;
import me.andpay.ac.wpn.srv.dao.WeixinNotifyInfoDao;
import me.andpay.ac.wpn.srv.dao.WxBindCardHolderDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.JSON;
import me.andpay.ti.util.MD5;
import me.andpay.ti.util.StringUtil;
import me.andpay.ti.util.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 微信通知服务
 * Created by cen on 2017/2/16.
 */
public class WeixinNotifyReceiverServiceImpl implements WeixinNotifyReceiverService {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private WxBindCardHolderDao wxBindCardHolderDao;

    @Autowired
    private WeixinNotifyInfoDao weixinNotifyInfoDao;

    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;


    @Autowired
    private WeiXinMsgHandler weiXinMsgHandler;


    /**
     * 消息签名验证
     * @param request
     * @return
     * @throws AppBizException
     */
    private boolean checkCallbackUrlSign(WeixinNotifyRequest request,WxPublicNumber wxPublicNumber) throws AppBizException {

        List<String> list=new ArrayList<String>();
        list.add(wxPublicNumber.getToken());
        list.add( request.getTimestamp());
        list.add(request.getNonce());
        Collections.sort(list);
        String signData = list.get(0)+list.get(1)+list.get(2);

        String sign = SimpleDigestUtil.simpleDigest("SHA1", signData);

        if(request.getSignature().toUpperCase().equals(sign)) {
            return true;
        }
        return  false;
    }

    /**
     * 保存消息
     * @param msg
     */
    private Long saveMsg(String msg,String appId,String sign) {

        String hashCode = MD5.md5Hex32(msg,"utf-8");
        WeixinNotifyInfo weixinNotifyInfo = new WeixinNotifyInfo();
        weixinNotifyInfo.setReceiveTime(new Date());
        weixinNotifyInfo.setUpdateTime(weixinNotifyInfo.getReceiveTime());
        weixinNotifyInfo.setNotifyContent(msg);
        weixinNotifyInfo.setDealStatus(WeixinNotifyDealStatuses.WNDS_CREATE);
        weixinNotifyInfo.setMessageHashCode(hashCode);
        weixinNotifyInfo.setAppId(appId);
        weixinNotifyInfo.setSign(sign);
        return weixinNotifyInfoDao.add(weixinNotifyInfo);

    }

    /**
     * 错误日志
     * @param weixinNotifyRequest
     * @param ex
     */
    private void logError(WeixinNotifyRequest weixinNotifyRequest,Exception ex) {
        logger.error(JSON.getDefault().toExplictJSONString(weixinNotifyRequest),ex);
    }

    /**
     * 解析消息
     * @param msg
     * @return
     */
    private Map<String,String> parseMsg(String msg) {
        XmlUtils xmlUtils = new XmlUtils("xml");
        Map<String,String> xmlData = xmlUtils.parse(msg);
        return xmlData;
    }



    @Override
    public String receive(WeixinNotifyRequest weixinNotifyRequest) throws AppBizException{

        logger.info("receive weixin notify publicNumberId=[{}]",weixinNotifyRequest.getWxPublicNumberId());
        try {
            WxPublicNumber wxPublicNumber = wxPublicNumberDao.get(weixinNotifyRequest.getWxPublicNumberId());
            if(wxPublicNumber == null) {
                logger.error("wxpublic number is null,wxPublicNumberId=[{}]",weixinNotifyRequest.getWxPublicNumberId());
                return NotifySupport.NOTIFY_COMPLITE;
            }

            //验证签名
            if(!checkCallbackUrlSign(weixinNotifyRequest,wxPublicNumber)){
                logError(weixinNotifyRequest,null);
                return NotifySupport.NOTIFY_COMPLITE;
            }

            String notifyContent = weixinNotifyRequest.getNotifyContent();

            //没有消息内容，只要验证签名
            if(StringUtil.isBlank(notifyContent)) {
                return weixinNotifyRequest.getEchostr();
            }
           //存储消息
            Long weixinNotifyInfoId = saveMsg(notifyContent,wxPublicNumber.getAppId(),weixinNotifyRequest.getSignature());

            //解析消息
            Map<String,String> xmlData = parseMsg(notifyContent);

            NotifyContext notifyContext = new NotifyContext();
            notifyContext.setWeixinNotifyInfoId(weixinNotifyInfoId);
            notifyContext.setXmlData(xmlData);
            notifyContext.setAppId(wxPublicNumber.getAppId());
            return weiXinMsgHandler.handle(notifyContext);

        }catch (Exception ex) {
            logError(weixinNotifyRequest,ex);
        }

        return NotifySupport.NOTIFY_COMPLITE;
    }
}
