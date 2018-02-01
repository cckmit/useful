package me.andpay.ac.wpn.api.model;

/**
 * Created by cen on 2017/2/22.
 */
public class QRCodeResult {

    /**
     * 二维码内容
     */
    private String qrCode;

    /**
     * 二维码图片地址
     */
    private String url;

    /**
     * 是否成功(接口败笔)
     */
    private boolean success;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
