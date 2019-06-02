package com.whpe.qrcode.jiangxi_jian.net.getbean.payunity;

/**
 * Created by yang on 2018/10/22.
 */

public class CcbBean {

    /**
     * payParam : https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain?MERCHANTID=105000041310929&POSID=027907079&BRANCHID=130000000&ORDERID=254&PAYMENT=0.0&CURCODE=01&TXCODE=520100&REMARK1=&REMARK2=&TYPE=1&GATEWAY=UnionPay&CLIENTIP=&REGINFO=&PROINFO=&REFERER=&TIMEOUT=20181022194715&MAC=9f6b4ec21ab632bc8b89ffde68b73559
     * cardNo : 03115140010110001026
     * merchantOderNo : 254
     */

    private String payParam;
    private String cardNo;
    private String merchantOderNo;

    public String getPayParam() {
        return payParam;
    }

    public void setPayParam(String payParam) {
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
}
