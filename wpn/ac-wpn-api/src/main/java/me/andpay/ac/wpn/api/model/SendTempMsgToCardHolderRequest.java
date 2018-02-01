package me.andpay.ac.wpn.api.model;

/**
 * Created by cen on 2017/2/26.
 */
public class SendTempMsgToCardHolderRequest extends SendTempMsgBaseRequest{

    /**
     * 卡号
     */
    private String cardNo;


    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
