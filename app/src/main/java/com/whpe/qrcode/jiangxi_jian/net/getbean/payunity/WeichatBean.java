package com.whpe.qrcode.jiangxi_jian.net.getbean.payunity;

/**
 * Created by yang on 2018/10/12.
 */

public class WeichatBean {

    /**
     * payParam : {"appid":"wx2c76cd0e0d63adfe","mch_id":"1516430751","nonce_str":"LEqqn2Wy2qvLOYJ2","prepay_id":"wx121443108395248e863085532290856898","returnCode":"SUCCESS","returnMsg":"OK","trade_type":"APP"}
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
         * appid : wx2c76cd0e0d63adfe
         * mch_id : 1516430751
         * nonce_str : LEqqn2Wy2qvLOYJ2
         * prepay_id : wx121443108395248e863085532290856898
         * returnCode : SUCCESS
         * returnMsg : OK
         * trade_type : APP
         */

        private String appid;
        private String mch_id;
        private String nonce_str;
        private String prepay_id;
        private String returnCode;
        private String returnMsg;
        private String trade_type;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMsg() {
            return returnMsg;
        }

        public void setReturnMsg(String returnMsg) {
            this.returnMsg = returnMsg;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }
}
