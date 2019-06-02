package com.whpe.qrcode.jiangxi_jian.net.getbean.payunity;

/**
 * Created by yang on 2018/11/8.
 */

public class AlipayBean {
    /**
     * payParam : {tn:"111"}
     * cardNo : 03115140010110001026
     * merchantOderNo : 2015
     */

    private PayParamBean payParam;
    private String cardNo;
    private String merchantOderNo;

    public PayParamBean getPayParam() {
        return payParam;
    }

    public void setPayParam(PayParamBean payParam) {
        this.payParam = payParam;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMerchantOderNo() {
        return merchantOderNo;
    }

    public void setMerchantOderNo(String merchantOderNo) {
        this.merchantOderNo = merchantOderNo;
    }

    public static class PayParamBean {
        /**
         * tn : 111
         */

        private String orderStr;

        public String getOrderStr() {
            return orderStr;
        }

        public void setOrderStr(String orderStr) {
            this.orderStr = orderStr;
        }
    }
}
