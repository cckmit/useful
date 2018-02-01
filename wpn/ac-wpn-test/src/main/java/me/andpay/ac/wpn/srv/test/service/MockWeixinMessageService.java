package me.andpay.ac.wpn.srv.test.service;

import me.andpay.ac.wpn.api.model.SendTempMsgToBindUserRequest;
import me.andpay.ac.wpn.api.model.SendTempMsgToCardHolderRequest;
import me.andpay.ac.wpn.api.model.SendWeixinMessageCallback;
import me.andpay.ac.wpn.api.model.SendWeixinMessageResult;
import me.andpay.ac.wpn.api.service.WeixinMessageService;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.lnk.annotaion.Callback;
import me.andpay.ti.util.ValidateUtil;

/**
 * Created by cen on 2017/2/9.
 */
public class MockWeixinMessageService implements WeixinMessageService {
    @Override
    public void sendTemplateMessageToBindUser(SendTempMsgToBindUserRequest sendTempMsgToBindUserRequest, @Callback SendWeixinMessageCallback sendWeixinMessageCallback) throws AppBizException {

        ValidateUtil.validate(sendTempMsgToBindUserRequest);

        SendWeixinMessageResult sendWeixinMessageResult = new SendWeixinMessageResult();
        sendWeixinMessageResult.setResponseMsg("suucess");
        sendWeixinMessageResult.setTraceNo(sendTempMsgToBindUserRequest.getTraceNo());
        sendWeixinMessageResult.setSuccess(true);

        sendWeixinMessageCallback.onResult(sendWeixinMessageResult);


    }

    @Override
    public void sendTemplateMessageToCardHolder(SendTempMsgToCardHolderRequest sendTempMsgToCardHolderRequest, @Callback SendWeixinMessageCallback sendWeixinMessageCallback) {

    }
}
