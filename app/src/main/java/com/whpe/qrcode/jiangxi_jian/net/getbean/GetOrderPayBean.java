package com.whpe.qrcode.jiangxi_jian.net.getbean;

import java.util.List;

/**
 * Created by yang on 2018/10/15.
 */

public class GetOrderPayBean {

    private List<OrderListBean> orderList;

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * cardNo : 03115140010110001026
         * cityCode : 00000001
         * merchantOrderNo : 1048
         * orderAmt : 1
         * orderGenerateTime : 20181012212458
         * orderResultTime : 20181012212731
         * orderStatus : success
         * payMerchantOrderId : 4200000162201810126278599530
         * selectType : gateway
         */

        private String cardNo;
        private String cityCode;
        private String merchantOrderNo;
        private int orderAmt;
        private String orderGenerateTime;
        private String orderResultTime;
        private String orderStatus;
        private String payMerchantOrderId;
        private String selectType;
        private String payPurpose;

        public String getPayPurpose() {
            return payPurpose;
        }

        public void setPayPurpose(String payPurpose) {
            this.payPurpose = payPurpose;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getMerchantOrderNo() {
            return merchantOrderNo;
        }

        public void setMerchantOrderNo(String merchantOrderNo) {
            this.merchantOrderNo = merchantOrderNo;
        }

        public int getOrderAmt() {
            return orderAmt;
        }

        public void setOrderAmt(int orderAmt) {
            this.orderAmt = orderAmt;
        }

        public String getOrderGenerateTime() {
            return orderGenerateTime;
        }

        public void setOrderGenerateTime(String orderGenerateTime) {
            this.orderGenerateTime = orderGenerateTime;
        }

        public String getOrderResultTime() {
            return orderResultTime;
        }

        public void setOrderResultTime(String orderResultTime) {
            this.orderResultTime = orderResultTime;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPayMerchantOrderId() {
            return payMerchantOrderId;
        }

        public void setPayMerchantOrderId(String payMerchantOrderId) {
            this.payMerchantOrderId = payMerchantOrderId;
        }

        public String getSelectType() {
            return selectType;
        }

        public void setSelectType(String selectType) {
            this.selectType = selectType;
        }
    }
}
