package com.whpe.qrcode.jiangxi_jian.net.getbean.custombus;

/**
 * Created by yang on 2019/1/16.
 */

public class GetQueryTicketByTicketIdBean {

    /**
     * endStation : 渼陂古村
     * layerNo : 2
     * payMoeny : 0
     * busPrice : 6800
     * chooseDate : 20190116
     * beginStation : 井大
     * vipPrice : 0
     * chooseTime : 0830
     * showSeq : ASC
     * seatNo : 1
     */

    private String endStation;
    private int layerNo;
    private int payMoeny;
    private int busPrice;
    private String chooseDate;
    private String beginStation;
    private int vipPrice;
    private String chooseTime;
    private String showSeq;
    private String seatNo;
    private String checkTicketCode;

    public String getCheckTicketCode() {
        return checkTicketCode;
    }

    public void setCheckTicketCode(String checkTicketCode) {
        this.checkTicketCode = checkTicketCode;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public int getLayerNo() {
        return layerNo;
    }

    public void setLayerNo(int layerNo) {
        this.layerNo = layerNo;
    }

    public int getPayMoeny() {
        return payMoeny;
    }

    public void setPayMoeny(int payMoeny) {
        this.payMoeny = payMoeny;
    }

    public int getBusPrice() {
        return busPrice;
    }

    public void setBusPrice(int busPrice) {
        this.busPrice = busPrice;
    }

    public String getChooseDate() {
        return chooseDate;
    }

    public void setChooseDate(String chooseDate) {
        this.chooseDate = chooseDate;
    }

    public String getBeginStation() {
        return beginStation;
    }

    public void setBeginStation(String beginStation) {
        this.beginStation = beginStation;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getChooseTime() {
        return chooseTime;
    }

    public void setChooseTime(String chooseTime) {
        this.chooseTime = chooseTime;
    }

    public String getShowSeq() {
        return showSeq;
    }

    public void setShowSeq(String showSeq) {
        this.showSeq = showSeq;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }
}
