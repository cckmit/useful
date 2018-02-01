package me.andpay.ac.wpn.srv.qrcode;

import me.andpay.ac.consts.wpn.QRCodeTypes;
import me.andpay.ac.wpn.api.callback.CreateSceneQRCodeCallback;
import me.andpay.ac.wpn.api.consts.AppExCodes;
import me.andpay.ac.wpn.api.model.CreateSceneQRCodeRequest;
import me.andpay.ac.wpn.api.model.QRCodeResult;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.api.model.db.WxPublicNumber;
import me.andpay.ac.wpn.api.model.db.cond.QueryWeixinQRCodeCond;
import me.andpay.ac.wpn.api.model.db.cond.QueryWxPublicNumberCond;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.api.service.WeixinBaseService;
import me.andpay.ac.wpn.api.service.WeixinQRCodeService;
import me.andpay.ac.wpn.inter.api.dto.CreateWexinQRCode;
import me.andpay.ac.wpn.srv.base.util.WeiXinUtil;
import me.andpay.ac.wpn.srv.dao.WeixinQRCodeDao;
import me.andpay.ac.wpn.srv.dao.WxPublicNumberDao;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信二维码服务
 * Created by cen on 2017/2/14.
 */
public class WeixinQRCodeServiceImpl implements WeixinQRCodeService {


    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpleHttpClient simpleHttpClient;

    @Autowired
    private WeixinAuthService weixinAuthService;
    /**
     * 微信主域
     */
    private String weixinDomain;

    @Autowired
    private WeixinQRCodeRepository weixinQrCodeRepository;

    @Autowired
    private WeixinBaseService weixinBaseService;

    @Autowired
    private WxPublicNumberDao wxPublicNumberDao;

    @Autowired
    private WeixinQRCodeDao weixinQRCodeDao;

    private static final String QRCODE_URL_PREFIX = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    private static final long DEFAULT_TIME_OUT = 600;

    private boolean isCacheQrCode(WeixinQRCode weixinQRCode) {
        if(weixinQRCode.getQrCodeType().equals(QRCodeTypes.QRT_SCAN)) {
            Long varTime = 60000l;//增加60秒的冗余时间
            Long time = weixinQRCode.getCreateTime().getTime()+Long.valueOf(weixinQRCode.getExpireSeconds())*1000-varTime;
            //未过期可以使用这个码，否则重新生成
            if(time > System.currentTimeMillis())  {
                return true;
            }
        }else {
            return true;
        }

        return  false;
    }


    @Override
    public QRCodeResult createSceneQRCode(CreateSceneQRCodeRequest createSceneQRCodeRequest)  throws AppBizException {

        logger.info("start create qrCode traceNo=[{}] appId=[{}],qrTyp=[{}]",new String[]{createSceneQRCodeRequest.getTraceNo(),
            createSceneQRCodeRequest.getAppAlais(), createSceneQRCodeRequest.getQrCodeType()
        });

        ValidateUtil.validate(createSceneQRCodeRequest);

        QueryWxPublicNumberCond queryWxPublicNumberCond = new QueryWxPublicNumberCond();
        queryWxPublicNumberCond.setAppAlais(createSceneQRCodeRequest.getAppAlais());
        WxPublicNumber wxPublicNumber =wxPublicNumberDao.getUnique(queryWxPublicNumberCond);
        if(wxPublicNumber == null) {
            throw new AppBizException(AppExCodes.QR_CODE_CREATE_ERROR, "wxPublicNumber not found appAlais="+createSceneQRCodeRequest.getAppAlais());
        }
        if(StringUtil.isNotBlank(createSceneQRCodeRequest.getTraceNo())) {
            QueryWeixinQRCodeCond weixinQRCodeCond = new QueryWeixinQRCodeCond();
            weixinQRCodeCond.setAppId(wxPublicNumber.getAppId());
            weixinQRCodeCond.setQrCodeType(createSceneQRCodeRequest.getQrCodeType());
            weixinQRCodeCond.setTraceNo(createSceneQRCodeRequest.getTraceNo());
            weixinQRCodeCond.setQrCodeBizType(createSceneQRCodeRequest.getQrCodeBizType());
            weixinQRCodeCond.setOrders("createTime-");
            List<WeixinQRCode> weixinQRCodes = weixinQRCodeDao.query(weixinQRCodeCond,0,1);
            if(weixinQRCodes.size() > 0 ) {
                WeixinQRCode weixinQRCode = weixinQRCodes.get(0);
                if(isCacheQrCode(weixinQRCode)) {
                    QRCodeResult qrCodeResult = new QRCodeResult();
                    qrCodeResult.setQrCode(weixinQRCode.getQrCodeContent());
                    String qrCodeUrl = QRCODE_URL_PREFIX+weixinQRCode.getTicket();
                    qrCodeResult.setUrl(qrCodeUrl);
                    qrCodeResult.setSuccess(true);
                    return  qrCodeResult;
                }
            }
        }


        String accessToken = weixinAuthService.obtainAccessToken(wxPublicNumber.getAppId());
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);

        SimpleHttpClient.HttpReq httpReq = new SimpleHttpClient.HttpReq();
        httpReq.setUrl(URLUtil.assembleUrl(weixinDomain,
                "https://", "/cgi-bin/qrcode/create", params));

        CreateWexinQRCode createWexinQRCode = new CreateWexinQRCode();
        createWexinQRCode.setAction_name(createSceneQRCodeRequest.getQrCodeType());
        createWexinQRCode.setExpire_seconds(createSceneQRCodeRequest.getExpireSeconds());


        WeixinQRCode weixinQRCode = weixinQrCodeRepository.createDbWeixinQRCode(createSceneQRCodeRequest.getQrParams(),wxPublicNumber.getAppId(),createSceneQRCodeRequest.getQrCodeType());
        weixinQRCode.setQrCodeType(createSceneQRCodeRequest.getQrCodeType());
        weixinQRCode.setQrCodeBizType(createSceneQRCodeRequest.getQrCodeBizType());
        weixinQRCode.setTraceNo(createSceneQRCodeRequest.getTraceNo());

        Map<String,String> stringStringMap = new HashMap<String, String>();
        if(QRCodeTypes.QRT_SCAN.equals(createSceneQRCodeRequest.getQrCodeType())){
            stringStringMap.put("scene_id",weixinQRCode.getSceneId());
            createWexinQRCode.setAction_name("QR_SCENE");
            //设置默认超时时间
            if(createSceneQRCodeRequest.getExpireSeconds()==0) {
                createWexinQRCode.setExpire_seconds(DEFAULT_TIME_OUT);
            }
            createWexinQRCode.setExpire_seconds(createSceneQRCodeRequest.getExpireSeconds());
            weixinQRCode.setExpireSeconds(createWexinQRCode.getExpire_seconds());
        }else {
            stringStringMap.put("scene_str",weixinQRCode.getSceneId());
            createWexinQRCode.setAction_name("QR_LIMIT_STR_SCENE");
        }
        createWexinQRCode.getAction_info().put("scene",stringStringMap);

        httpReq.setContent(JSON.getDefault().toJSONString(createWexinQRCode));
        SimpleHttpClient.HttpResp httpResp  = simpleHttpClient.doPost(httpReq);
        JsonpathContext ctx = WeiXinUtil.responseDeal(AppExCodes.QR_CODE_CREATE_ERROR,httpResp.getContent());

        String ticket = ctx.getValue("$.ticket",String.class);
        String qrCodeContent = ctx.getValue("$.url",String.class);

        String qrCodeUrl = QRCODE_URL_PREFIX+ticket;

        weixinQRCode.setTicket(ticket);
//        weixinQRCode.setQrCodeShortUrl(qrCodeUrl);
        weixinQRCode.setQrCodeContent(qrCodeContent);
        weixinQRCodeDao.add(weixinQRCode);

        QRCodeResult qrCodeResult = new QRCodeResult();
        qrCodeResult.setQrCode(qrCodeContent);
        qrCodeResult.setUrl(qrCodeUrl);
        qrCodeResult.setSuccess(true);


        logger.info("end create qrCode traceNo=[{}] appId=[{}],qrTyp=[{}],qrCodeUrl=[{}]",new String[]{createSceneQRCodeRequest.getTraceNo(),
                createSceneQRCodeRequest.getAppAlais(), createSceneQRCodeRequest.getQrCodeType(),qrCodeResult.getUrl()
        });

        return qrCodeResult;
    }


    public void asyncCreateSceneQRCode(CreateSceneQRCodeRequest createSceneQRCodeRequest,CreateSceneQRCodeCallback createSceneQRCodeCallback) {

        try {
            QRCodeResult qrCodeResult =  this.createSceneQRCode(createSceneQRCodeRequest);
            if(createSceneQRCodeCallback != null) {
                createSceneQRCodeCallback.onResult(qrCodeResult);
            }
        }catch (Exception ex) {
            QRCodeResult qrCodeResult = new QRCodeResult();
            qrCodeResult.setSuccess(false);
            createSceneQRCodeCallback.onResult(qrCodeResult);
        }
    }



    public void setWeixinDomain(String weixinDomain) {
        this.weixinDomain = weixinDomain;
    }

}
