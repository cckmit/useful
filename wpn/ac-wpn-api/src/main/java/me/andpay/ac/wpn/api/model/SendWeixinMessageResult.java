package me.andpay.ac.wpn.api.model;

/**
 * 微信模板消息发送返回结果
 * Created by cen on 2017/2/8.
 */
public class SendWeixinMessageResult {
    /**
     * 是否发送成功
     */
    private boolean success;
    /**
     * 返回消息
     */
    private String responseMsg;

    /**
     * 跟踪编号
     */
    private String traceNo;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }
}
