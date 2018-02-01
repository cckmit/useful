package me.andpay.ac.wpn.inter.api.service;

/**
 * Created by cen on 2016/11/17.
 */
public interface SendWxTpMessageCallback {

    /**
     * 发送成功
     * @param content
     */
    public void sendReponse(String content);

    /**
     * 发送失败
     * @param ex
     */
    public void sendError(Exception ex);

}
