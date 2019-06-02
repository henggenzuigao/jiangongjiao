package com.whpe.qrcode.jiangxi_jian.net.getbean.custombus;

import java.util.List;

/**
 * Created by yang on 2019/1/14.
 */

public class GetQueryTicketsByBuslineidBean {

    /**
     * ticketList : [{"busPrice":68,"dayNum":"20190114","limitTicketNo":"2","lineRunId":"A6B3B54F71C24B4D90BA4744C83CF1C5","saleStatus":"ONSALE","supportVip":"yes","surplusTicket":"40","vipPrice":0},{"busPrice":68,"dayNum":"20190115","limitTicketNo":"2","lineRunId":"13D6D78947954BC58452B2A9C59648EB","saleStatus":"ONSALE","supportVip":"yes","surplusTicket":"40","vipPrice":0},{"busPrice":68,"dayNum":"20190116","limitTicketNo":"2","lineRunId":"D9CA3C8D003C428A9E5BA2AE8DD61BA6","saleStatus":"ONSALE","supportVip":"yes","surplusTicket":"40","vipPrice":0},{"busPrice":68,"dayNum":"20190117","limitTicketNo":"2","lineRunId":"94A95449B7B14BBEB61A7AAF2A64003F","saleStatus":"ONSALE","supportVip":"yes","surplusTicket":"40","vipPrice":0},{"busPrice":68,"dayNum":"20190118","limitTicketNo":"2","lineRunId":"7222406BEC5E42089C61E991F80C577E","saleStatus":"ONSALE","supportVip":"yes","surplusTicket":"40","vipPrice":0},{"busPrice":68,"dayNum":"20190104","limitTicketNo":"2","lineRunId":"934651ED28604656933370DCD359F9AD","saleStatus":"ONSALE","supportVip":"yes","surplusTicket":"40","vipPrice":0}]
     * showSeq : ASC
     * chooseTime : 0830
     */

    private String showSeq;
    private String chooseTime;
    private List<TicketListBean> ticketList;

    public String getShowSeq() {
        return showSeq;
    }

    public void setShowSeq(String showSeq) {
        this.showSeq = showSeq;
    }

    public String getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(String chooseTime) {
        this.chooseTime = chooseTime;
    }

    public List<TicketListBean> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketListBean> ticketList) {
        this.ticketList = ticketList;
    }

    public static class TicketListBean {
        /**
         * busPrice : 68
         * dayNum : 20190114
         * limitTicketNo : 2
         * lineRunId : A6B3B54F71C24B4D90BA4744C83CF1C5
         * saleStatus : ONSALE
         * supportVip : yes
         * surplusTicket : 40
         * vipPrice : 0
         */

        private String busPrice;
        private String dayNum;
        private String limitTicketNo;
        private String lineRunId;
        private String saleStatus;
        private String supportVip;
        private String surplusTicket;
        private String vipPrice;
        private String layerNum;


        public String getDayNum() {
            return dayNum;
        }

        public void setDayNum(String dayNum) {
            this.dayNum = dayNum;
        }

        public String getLimitTicketNo() {
            return limitTicketNo;
        }

        public void setLimitTicketNo(String limitTicketNo) {
            this.limitTicketNo = limitTicketNo;
        }

        public String getLineRunId() {
            return lineRunId;
        }

        public void setLineRunId(String lineRunId) {
            this.lineRunId = lineRunId;
        }

        public String getSaleStatus() {
            return saleStatus;
        }

        public void setSaleStatus(String saleStatus) {
            this.saleStatus = saleStatus;
        }

        public String getSupportVip() {
            return supportVip;
        }

        public void setSupportVip(String supportVip) {
            this.supportVip = supportVip;
        }

        public String getSurplusTicket() {
            return surplusTicket;
        }

        public void setSurplusTicket(String surplusTicket) {
            this.surplusTicket = surplusTicket;
        }

        public String getBusPrice() {
            return busPrice;
        }

        public void setBusPrice(String busPrice) {
            this.busPrice = busPrice;
        }

        public String getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(String vipPrice) {
            this.vipPrice = vipPrice;
        }

        public String getLayerNum() {
            return layerNum;
        }

        public void setLayerNum(String layerNum) {
            this.layerNum = layerNum;
        }

    }
}
