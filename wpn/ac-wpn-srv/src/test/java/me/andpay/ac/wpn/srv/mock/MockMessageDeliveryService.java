package me.andpay.ac.wpn.srv.mock;

import me.andpay.ac.consts.SystemIds;
import me.andpay.ti.lnk.annotaion.Callback;
import me.andpay.ti.mns.api.*;
import me.andpay.ti.mns.api.wxtpmsg.WxTempMessagePropertyNames;
import org.junit.Assert;

/**
 * Created by cen on 2017/2/8.
 */
public class MockMessageDeliveryService implements MessageDeliveryService {


    @Override
    public void deliver(Message message, DeliveryStrategy deliveryStrategy, @Callback DeliveryReportSubscriber deliveryReportSubscriber) {

        String msg = "{\"touser\":\"oLjPPs-djjn7LvZiwv1ynUUGEbSs\",\"template_id\":\"5HgHkwvIP8JjcTNgb3z7CShBw-0KTSbuByG1ylOFH0g\",\"url\":\"http://www.andpay.me?test=001\",\"data\":{\"amt\":{\"value\":\"1000000.00元\",\"color\":\"#173177\"}}}";

        Assert.assertEquals(message.getMessageType(), MessageTypes.WXTP_MSG);
        Assert.assertEquals(message.getSourceSystemId(), SystemIds.AC_WPN);
        Assert.assertNotNull(message.getProperties().get(WxTempMessagePropertyNames.ACCESS_TOKEN));
        Assert.assertNotNull(message.getTraceNo());
        Assert.assertEquals(message.getProperties().get(WxTempMessagePropertyNames.MSG_CONTENT), msg);

        DeliveryReport deliveryReport = new DeliveryReport();
        deliveryReport.setMessageId(1l);
        deliveryReport.setDelivery(true);
        deliveryReport.setDescription("12121");
        deliveryReport.setTraceNo(message.getTraceNo());

        deliveryReportSubscriber.onComplete(deliveryReport);

    }

    @Override
    public void deliver(long l, boolean b) {

    }

    @Override
    public long redeliver(long l, String s, @Callback DeliveryReportSubscriber deliveryReportSubscriber) {
        return 0;
    }

    @Override
    public void stopDelivery(long l) {

    }

    @Override
    public void onMessageRead(long l) {

    }
}
