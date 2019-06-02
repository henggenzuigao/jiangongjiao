package com.whpe.qrcode.jiangxi_jian.net.getbean.custombus;

import java.util.List;

/**
 * Created by yang on 2019/1/15.
 */

public class GetQueryUserTicketInfoBean {


    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * choose_date : 20190116
         * line_num : 游一路
         * seat_no : 1
         * choose_time : 0830
         * ticket_id : A29793305B6140F8B4A1ABBD8A63288D
         */

        private String chooseDate;
        private String lineNum;
        private int seatNo;
        private String chooseTime;
        private String ticketId;

        public String getChooseDate() {
            return chooseDate;
        }

        public void setChooseDate(String chooseDate) {
            this.chooseDate = chooseDate;
        }

        public String getLineNum() {
            return lineNum;
        }

        public void setLineNum(String lineNum) {
            this.lineNum = lineNum;
        }

        public int getSeatNo() {
            return seatNo;
        }

        public void setSeatNo(int seatNo) {
            this.seatNo = seatNo;
        }

        public String getChooseTime() {
            return chooseTime;
        }

        public void setChooseTime(String chooseTime) {
            this.chooseTime = chooseTime;
        }

        public String getTicketId() {
            return ticketId;
        }

        public void setTicketId(String ticketId) {
            this.ticketId = ticketId;
        }
    }
}
