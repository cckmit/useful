package me.andpay.ac.wpn.web.controllers;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;

import me.andpay.ac.wpn.api.consts.AppExCodes;
import me.andpay.ac.wpn.api.model.WeixinNotifyRequest;
import me.andpay.ac.wpn.api.service.WeixinAuthService;
import me.andpay.ac.wpn.api.service.WeixinNotifyReceiverService;
import me.andpay.ac.wpn.inter.api.model.message.WeixinReceiveMessage;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.lnk.annotaion.Lnkwired;
import me.andpay.ti.util.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;


@Controller
public class ReceiveWeixinMessageController {

	@Lnkwired
	private WeixinNotifyReceiverService weixinNotifyReceiverService;


	private String readPostData(HttpServletRequest httpServletRequest) {
		try {
			BufferedReader br = httpServletRequest.getReader();
			String str, wholeStr="";
			while((str = br.readLine()) != null){
				wholeStr += str;
			}
			return wholeStr;
		}catch (Exception ex) {
			throw new AppRtException(AppExCodes.SYS_ERROR,ex);
		}
	}

	@ResponseBody
	@RequestMapping("receive/{refId}")
	public String receiveMessage(HttpServletRequest httpRequest, WeixinReceiveMessage message, @PathVariable Long refId) throws AppBizException{

		String msg = readPostData(httpRequest);

		WeixinNotifyRequest weixinNotifyRequest = new WeixinNotifyRequest();
		BeanUtils.copyProperties(weixinNotifyRequest,message);
		weixinNotifyRequest.setNotifyContent(msg);
		weixinNotifyRequest.setWxPublicNumberId(refId);

		String returnMsg = weixinNotifyReceiverService.receive(weixinNotifyRequest);

		return returnMsg;

	}
}
