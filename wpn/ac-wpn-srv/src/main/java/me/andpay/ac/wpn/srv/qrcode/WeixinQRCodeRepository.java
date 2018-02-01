package me.andpay.ac.wpn.srv.qrcode;

import me.andpay.ac.consts.wpn.QRCodeTypes;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.api.model.db.cond.QueryWeixinQRCodeCond;
import me.andpay.ac.wpn.srv.dao.WeixinQRCodeDao;
import me.andpay.ti.seq.SequenceService;
import me.andpay.ti.util.JSON;
import me.andpay.ti.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cen on 2017/2/16.
 */
public class WeixinQRCodeRepository {

    /**
     * 二维码最大编号
     */
    private static final long MAX_INT = 4294967296l;

    private static final String QR_CODE_SEQ_PREIFX = "qr_code_seq-";

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private WeixinQRCodeDao weixinQrCodeDao;

    /**
     * 保存参数
     * @param params
     * @return 场景编号
     */
    public WeixinQRCode createDbWeixinQRCode(Map<String,String> params, String appId,String qrType) {

        long seqValue = sequenceService.nextValueWithCreate(QR_CODE_SEQ_PREIFX+qrType+"_"+appId,1l);

        String seqStr = null;
        if(qrType.equals(QRCodeTypes.QRT_SCAN)){
            seqValue = seqValue%MAX_INT;
            seqStr = String.valueOf(seqValue);
        }else {
            seqStr = "m"+seqValue;
        }

        WeixinQRCode weixinQrCode = new WeixinQRCode();
        weixinQrCode.setParamJson(JSON.getDefault().toJSONString(params));
        weixinQrCode.setSceneId(seqStr);
        weixinQrCode.setAppId(appId);
        weixinQrCode.setCreateTime(new Date());
        weixinQrCode.setUpdateTime(weixinQrCode.getCreateTime());

        return weixinQrCode;
    }

    /**
     * 保存二维码链接
     * @param weixinQRCode
     * @param ticket
     * @param shortUrl
     */
    public void saveQrCodeResult(WeixinQRCode weixinQRCode,String ticket,String shortUrl,String qrCodecontent) {
        weixinQRCode.setTicket(ticket);
        weixinQRCode.setQrCodeShortUrl(shortUrl);
        weixinQRCode.setQrCodeContent(qrCodecontent);
        weixinQrCodeDao.add(weixinQRCode);
    }

    /**
     * 获取参数
     * @param sceneId
     * @param qrType
     * @return 场景参数
     */
    public Map<String,String> getParam(String sceneId,String qrType) {

        QueryWeixinQRCodeCond queryWeixinQRCodeCond = new QueryWeixinQRCodeCond();
        queryWeixinQRCodeCond.setSceneId(sceneId);
        queryWeixinQRCodeCond.setQrCodeType(qrType);
        WeixinQRCode weixinQrCode = weixinQrCodeDao.getUnique(queryWeixinQRCodeCond);

        Map<String,String> params = new HashMap<String, String>();
        if(weixinQrCode == null|| StringUtil.isBlank(weixinQrCode.getParamJson())) {
            return params;
        }
        return JSON.getDefault().parseToObject(weixinQrCode.getParamJson(),Map.class);
    }
}
