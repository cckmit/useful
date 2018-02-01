package me.andpay.ac.wpn.srv.qrcode;

import me.andpay.ac.consts.wpn.AppAlais;
import me.andpay.ac.consts.wpn.QRCodeBizTypes;
import me.andpay.ac.consts.wpn.QRCodeTypes;
import me.andpay.ac.wpn.api.callback.CreateSceneQRCodeCallback;
import me.andpay.ac.wpn.api.model.CreateSceneQRCodeRequest;
import me.andpay.ac.wpn.api.model.QRCodeResult;
import me.andpay.ac.wpn.api.model.db.cond.QueryWeixinQRCodeCond;
import me.andpay.ac.wpn.api.service.WeixinQRCodeService;
import me.andpay.ac.wpn.srv.dao.WeixinQRCodeDao;
import me.andpay.ac.wpn.srv.service.DataInitService;
import me.andpay.ac.wpn.srv.service.DataInitServiceImpl;
import me.andpay.ti.test.dbunit.SpringDbUnitClassRunner;
import me.andpay.ti.util.Assert;
import me.andpay.ti.util.SleepUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by cen on 2017/2/16.
 */

@RunWith(SpringDbUnitClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = { "classpath:spring-config/qrcode/ac-wpn-srv-test-qrcode-config.xml" })
public class WeixinQRCodeServiceTest {

    @Autowired
    private WeixinQRCodeService weixinQRCodeService;

    @Autowired
    private DataInitService dataInitService;

    @Autowired
    private WeixinQRCodeDao weixinQRCodeDao;

    @Test
    public void test() throws Exception {

        dataInitService.createPublicNumber();

        CreateSceneQRCodeRequest createSceneQRCodeRequest = new CreateSceneQRCodeRequest();
        createSceneQRCodeRequest.setExpireSeconds(300);
        createSceneQRCodeRequest.setQrCodeType(QRCodeTypes.QRT_SCAN);
        createSceneQRCodeRequest.setAppAlais(AppAlais.ANDPAY_PARTNER);
        createSceneQRCodeRequest.setQrCodeBizType(QRCodeBizTypes.QRBT_TXN_VOUCHER_CODE);
        createSceneQRCodeRequest.setTraceNo("123456");
        QRCodeResult qrCodeResult = weixinQRCodeService.createSceneQRCode(createSceneQRCodeRequest);
        Assert.assertNotNull(qrCodeResult.getQrCode());
        Assert.assertNotNull(qrCodeResult.getUrl());
        Assert.assertTrue(qrCodeResult.isSuccess());

        long count = weixinQRCodeDao.count(new QueryWeixinQRCodeCond());
        Assert.assertTrue(count == 1);

        qrCodeResult = weixinQRCodeService.createSceneQRCode(createSceneQRCodeRequest);
        Assert.assertNotNull(qrCodeResult.getQrCode());
        Assert.assertNotNull(qrCodeResult.getUrl());
        Assert.assertTrue(qrCodeResult.isSuccess());

        count = weixinQRCodeDao.count(new QueryWeixinQRCodeCond());
        Assert.assertTrue(count == 1);

        createSceneQRCodeRequest.setExpireSeconds(10);
        createSceneQRCodeRequest.setTraceNo("4564");
        qrCodeResult = weixinQRCodeService.createSceneQRCode(createSceneQRCodeRequest);
        qrCodeResult = weixinQRCodeService.createSceneQRCode(createSceneQRCodeRequest);
        count = weixinQRCodeDao.count(new QueryWeixinQRCodeCond());
        Assert.assertTrue(count == 3);


        createSceneQRCodeRequest.setQrCodeType(QRCodeTypes.QRT_LIMIT);
        qrCodeResult = weixinQRCodeService.createSceneQRCode(createSceneQRCodeRequest);
        Assert.assertNotNull(qrCodeResult.getQrCode());
        Assert.assertNotNull(qrCodeResult.getUrl());
        Assert.assertTrue(qrCodeResult.isSuccess());


        count = weixinQRCodeDao.count(new QueryWeixinQRCodeCond());
        Assert.assertTrue(count == 4);


        weixinQRCodeService.asyncCreateSceneQRCode(createSceneQRCodeRequest, new CreateSceneQRCodeCallback() {
            @Override
            public void onResult(QRCodeResult qrCodeResultTe) {
                Assert.assertNotNull(qrCodeResultTe.getQrCode());
                Assert.assertNotNull(qrCodeResultTe.getUrl());
                Assert.assertTrue(qrCodeResultTe.isSuccess());
            }
        });


    }
}
