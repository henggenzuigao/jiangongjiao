package com.whpe.qrcode.jiangxi_jian.net.getbean.custombus;

import java.util.List;

/**
 * Created by yang on 2019/1/15.
 */

public class GetGenerateTicketOrderInfoBean {

    /**
     * totalTicketNum : 1
     * ticketList : [{"layerNo":"1","reallyPrice":"49","seatNo":"1"}]
     * pre_order_id : FDB52D01FC51406F8302FCC0441A1612
     * totalTicketPrice : 49
     */

    private int totalTicketNum;
    private String preOrderId;
    private int totalTicketPrice;
    private List<TicketListBean> ticketList;

    public int getTotalTicketNum() {
        return totalTicketNum;
    }

    public void setTotalTicketNum(int totalTicketNum) {
        this.totalTicketNum = totalTicketNum;
    }

    public String getPreOrderId() {
        return preOrderId;
    }

    public void setPreOrderId(String preOrderId) {
        this.preOrderId = preOrderId;
    }

    public int getTotalTicketPrice() {
        return totalTicketPrice;
    }

    public void setTotalTicketPrice(int totalTicketPrice) {
        this.totalTicketPrice = totalTicketPrice;
    }

    public List<TicketListBean> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketListBean> ticketList) {
        this.ticketList = ticketList;
    }

    public static class TicketListBean {
        /**
         * layerNo : 1
         * reallyPrice : 49
         * seatNo : 1
         */

        private String layerNo;
        private String reallyPrice;
        private String seatNo;

        public String getLayerNo() {
            return layerNo;
        }

        public void setLayerNo(String layerNo) {
            this.layerNo = layerNo;
        }

        public String getReallyPrice() {
            return reallyPrice;
        }

        public void setReallyPrice(String reallyPrice) {
            this.reallyPrice = reallyPrice;
        }

        public String getSeatNo() {
            return seatNo;
        }

        public void setSeatNo(String seatNo) {
            this.seatNo = seatNo;
        }
    }
}
