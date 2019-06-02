package com.whpe.qrcode.jiangxi_jian.net.getbean;

import java.util.List;

/**
 * Created by yang on 2018/10/11.
 */

public class QrcodeStatusBean {

    /**
     * qrCardStatus : 01
     * statusDesc :
     * balance : 0
     * platformUserId : 0000000000002035
     * oweCount : 0
     * deposit : 0
     * qrCardNo : 03115140010110001026
     * oweAmt : 0
     * bindWay : [{"payWayCode":"00","payWayName":"余额","payWayStatus":"01"}]
     */

    private String qrCardStatus;
    private String statusDesc;
    private int balance;
    private String platformUserId;
    private int oweCount;
    private int deposit;
    private String qrCardNo;
    private int oweAmt;
    private List<BindWayBean> bindWay;

    public String getQrCardStatus() {
        return qrCardStatus;
    }

    public void setQrCardStatus(String qrCardStatus) {
        this.qrCardStatus = qrCardStatus;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(String platformUserId) {
        this.platformUserId = platformUserId;
    }

    public int getOweCount() {
        return oweCount;
    }

    public void setOweCount(int oweCount) {
        this.oweCount = oweCount;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getQrCardNo() {
        return qrCardNo;
    }

    public void setQrCardNo(String qrCardNo) {
        this.qrCardNo = qrCardNo;
    }

    public int getOweAmt() {
        return oweAmt;
    }

    public void setOweAmt(int oweAmt) {
        this.oweAmt = oweAmt;
    }

    public List<BindWayBean> getBindWay() {
        return bindWay;
    }

    public void setBindWay(List<BindWayBean> bindWay) {
        this.bindWay = bindWay;
    }

    public static class BindWayBean {
        /**
         * payWayCode : 00
         * payWayName : 余额
         * payWayStatus : 01
         */

        private String payWayCode;
        private String payWayName;
        private String payWayStatus;

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
    }
}
