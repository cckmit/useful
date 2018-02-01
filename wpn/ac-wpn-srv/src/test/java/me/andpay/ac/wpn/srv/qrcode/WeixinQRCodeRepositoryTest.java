package me.andpay.ac.wpn.srv.qrcode;

import me.andpay.ac.consts.wpn.QRCodeBizTypes;
import me.andpay.ac.consts.wpn.QRCodeTypes;
import me.andpay.ac.wpn.api.model.db.WeixinQRCode;
import me.andpay.ac.wpn.srv.dao.WeixinQRCodeDao;
import me.andpay.ac.wpn.srv.service.DataInitServiceImpl;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import me.andpay.ti.util.Assert;
import me.andpay.ti.util.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cen on 2017/2/16.
 */

@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/qrcode/ac-wpn-srv-test-qrcode-config.xml" })
public class WeixinQRCodeRepositoryTest {

    @Autowired
    private WeixinQRCodeRepository weixinQrCodeRepository;

    @Autowired
    private WeixinQRCodeDao weixinQrCodeDao;

    @Test
    public void test() throws Exception {

        Map<String,String> params = new HashMap<String, String>();
        params.put("test","111");
        params.put("test1","222");

        WeixinQRCode weixinQRCodeTemp = weixinQrCodeRepository.createDbWeixinQRCode(params, DataInitServiceImpl.APPID,QRCodeTypes.QRT_SCAN);
        weixinQRCodeTemp.setQrCodeType(QRCodeTypes.QRT_SCAN);
        weixinQRCodeTemp.setQrCodeBizType(QRCodeBizTypes.QRBT_TXN_VOUCHER_CODE);
        weixinQrCodeRepository.saveQrCodeResult(weixinQRCodeTemp,"12345","http://www.baidu.com",QRCodeTypes.QRT_SCAN);

        WeixinQRCode weixinQrCode = weixinQrCodeDao.get(1l);
        Assert.assertEquals(weixinQrCode.getTicket(),"12345");
        Assert.assertEquals(weixinQrCode.getQrCodeShortUrl(),"http://www.baidu.com");
        Assert.assertEquals(weixinQrCode.getParamJson(),"{\"test1\":\"222\",\"test\":\"111\"}");
        Assert.assertEquals(QRCodeTypes.QRT_SCAN, weixinQrCode.getQrCodeType());
        Assert.assertEquals(weixinQrCode.getSceneId(),weixinQRCodeTemp.getSceneId());
        Assert.assertEquals(weixinQrCode.getQrCodeBizType(),QRCodeBizTypes.QRBT_TXN_VOUCHER_CODE);


        Map<String,String> paramDb = weixinQrCodeRepository.getParam(weixinQRCodeTemp.getSceneId(), weixinQrCode.getQrCodeType());
        Assert.assertEquals(JSON.getDefault().toJSONString(paramDb),"{\"test1\":\"222\",\"test\":\"111\"}");
    }
}
