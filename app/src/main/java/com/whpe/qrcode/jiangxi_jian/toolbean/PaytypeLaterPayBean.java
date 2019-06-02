package com.whpe.qrcode.jiangxi_jian.toolbean;

/**
 * Created by yang on 2018/10/11.
 */

public class PaytypeLaterPayBean {

    /**
     * payWayCode : 00
     * payWayName : 余额
     * payWayStatus : 01
     */

    private String payWayCode;
    private String payWayName;
    private String payWayStatus;
    private String lastQrcodeTime;
    private String isBank;
    private String bankInfo;

    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayWayStatus() {
        return payWayStatus;
    }

    public void setPayWayStatus(String payWayStatus) {
        this.payWayStatus = payWayStatus;
    }

    public String getLastQrcodeTime() {
        return lastQrcodeTime;
    }

    public void setLastQrcodeTime(String lastQrcodeTime) {
        this.lastQrcodeTime = lastQrcodeTime;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }
}
