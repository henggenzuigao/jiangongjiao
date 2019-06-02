package com.whpe.qrcode.jiangxi_jian.net.getbean;

import java.util.List;

/**
 * Created by yang on 2018/10/10.
 */

public class LoadQrcodeParamBean {


    /**
     * cityQrParamConfig : {"allowLowestAmt":0,"allowOweAmt":0,"appId":"00000001YMDX","needDeposit":0,"paramVersion":"0001","payWay":[{"payLevel":"01","payWayCode":"20","payWayName":"微信充值","payWayType":"prepay"},{"payLevel":"01","payWayCode":"00","payWayName":"余额","payWayType":"laterPay"},{"payLevel":"01","payWayCode":"80","payWayName":"建行充值","payWayType":"prepay"}],"payWayUnbindType":"normalUnBind","qrPayType":"prepay","rebackDepositType":"auditReturnDeposit"}
     */

    private CityQrParamConfigBean cityQrParamConfig;

    public CityQrParamConfigBean getCityQrParamConfig() {
        return cityQrParamConfig;
    }

    public void setCityQrParamConfig(CityQrParamConfigBean cityQrParamConfig) {
        this.cityQrParamConfig = cityQrParamConfig;
    }

    public static class CityQrParamConfigBean {
        /**
         * allowLowestAmt : 0
         * allowOweAmt : 0
         * appId : 00000001YMDX
         * needDeposit : 0
         * paramVersion : 0001
         * payWay : [{"payLevel":"01","payWayCode":"20","payWayName":"微信充值","payWayType":"prepay"},{"payLevel":"01","payWayCode":"00","payWayName":"余额","payWayType":"laterPay"},{"payLevel":"01","payWayCode":"80","payWayName":"建行充值","payWayType":"prepay"}]
         * payWayUnbindType : normalUnBind
         * qrPayType : prepay
         * rebackDepositType : auditReturnDeposit
         */

        private int allowLowestAmt;
        private int allowOweAmt;
        private String appId;
        private int needDeposit;
        private String paramVersion;
        private String payWayUnbindType;
        private String qrPayType;
        private String rebackDepositType;
        private String rechargeAmount;
        private List<PayWayBean> payWay;

        public String getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(String rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public int getAllowLowestAmt() {
            return allowLowestAmt;
        }

        public void setAllowLowestAmt(int allowLowestAmt) {
            this.allowLowestAmt = allowLowestAmt;
        }

        public int getAllowOweAmt() {
            return allowOweAmt;
        }

        public void setAllowOweAmt(int allowOweAmt) {
            this.allowOweAmt = allowOweAmt;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public int getNeedDeposit() {
            return needDeposit;
        }

        public void setNeedDeposit(int needDeposit) {
            this.needDeposit = needDeposit;
        }

        public String getParamVersion() {
            return paramVersion;
        }

        public void setParamVersion(String paramVersion) {
            this.paramVersion = paramVersion;
        }

        public String getPayWayUnbindType() {
            return payWayUnbindType;
        }

        public void setPayWayUnbindType(String payWayUnbindType) {
            this.payWayUnbindType = payWayUnbindType;
        }

        public String getQrPayType() {
            return qrPayType;
        }

        public void setQrPayType(String qrPayType) {
            this.qrPayType = qrPayType;
        }

        public String getRebackDepositType() {
            return rebackDepositType;
        }

        public void setRebackDepositType(String rebackDepositType) {
            this.rebackDepositType = rebackDepositType;
        }

        public List<PayWayBean> getPayWay() {
            return payWay;
        }

        public void setPayWay(List<PayWayBean> payWay) {
            this.payWay = payWay;
        }

        public static class PayWayBean {
            /**
             * payLevel : 01
             * payWayCode : 20
             * payWayName : 微信充值
             * payWayType : prepay
             */

            private String payLevel;
            private String payWayCode;
            private String payWayName;
            private String payWayType;

            public String getPayLevel() {
                return payLevel;
            }

            public void setPayLevel(String payLevel) {
                this.payLevel = payLevel;
            }

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

            public String getPayWayType() {
                return payWayType;
            }

            public void setPayWayType(String payWayType) {
                this.payWayType = payWayType;
            }
        }
    }
}
