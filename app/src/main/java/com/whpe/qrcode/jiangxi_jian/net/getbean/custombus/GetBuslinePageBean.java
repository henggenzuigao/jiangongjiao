package com.whpe.qrcode.jiangxi_jian.net.getbean.custombus;

import java.util.List;

/**
 * Created by yang on 2019/1/14.
 */

public class GetBuslinePageBean {

    private List<LineListBean> lineList;

    public List<LineListBean> getLineList() {
        return lineList;
    }

    public void setLineList(List<LineListBean> lineList) {
        this.lineList = lineList;
    }

    public static class LineListBean {
        /**
         * appId : 03634350JAGJ
         * ascTime : [{"runTime":"0830"}]
         * beginStation : 井大
         * busPrice : 68
         * consumeTime : 1555
         * currentPage : 0
         * descTime : [{"runTime":"0830"}]
         * endStation : 渼陂古村
         * isTemp : no
         * lineId : 2
         * lineNum : 游一路
         * pageNo : 0
         * showSeq : ASC
         * supportDesc : no
         * totalLen : 26666
         */

        private String appId;
        private String beginStation;
        private int busPrice;
        private String consumeTime;
        private int currentPage;
        private String endStation;
        private String isTemp;
        private String lineId;
        private String lineNum;
        private int pageNo;
        private String showSeq;
        private String supportDesc;
        private int totalLen;
        private List<AscTimeBean> ascTime;
        private List<DescTimeBean> descTime;
        private String busDescription;

        public String getBusDescription() {
            return busDescription;
        }

        public void setBusDescription(String busDescription) {
            this.busDescription = busDescription;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getBeginStation() {
            return beginStation;
        }

        public void setBeginStation(String beginStation) {
            this.beginStation = beginStation;
        }

        public int getBusPrice() {
            return busPrice;
        }

        public void setBusPrice(int busPrice) {
            this.busPrice = busPrice;
        }

        public String getConsumeTime() {
            return consumeTime;
        }

        public void setConsumeTime(String consumeTime) {
            this.consumeTime = consumeTime;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public String getEndStation() {
            return endStation;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }

        public String getIsTemp() {
            return isTemp;
        }

        public void setIsTemp(String isTemp) {
            this.isTemp = isTemp;
        }

        public String getLineId() {
            return lineId;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        public String getLineNum() {
            return lineNum;
        }

        public void setLineNum(String lineNum) {
            this.lineNum = lineNum;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public String getShowSeq() {
            return showSeq;
        }

        public void setShowSeq(String showSeq) {
            this.showSeq = showSeq;
        }

        public String getSupportDesc() {
            return supportDesc;
        }

        public void setSupportDesc(String supportDesc) {
            this.supportDesc = supportDesc;
        }

        public int getTotalLen() {
            return totalLen;
        }

        public void setTotalLen(int totalLen) {
            this.totalLen = totalLen;
        }

        public List<AscTimeBean> getAscTime() {
            return ascTime;
        }

        public void setAscTime(List<AscTimeBean> ascTime) {
            this.ascTime = ascTime;
        }

        public List<DescTimeBean> getDescTime() {
            return descTime;
        }

        public void setDescTime(List<DescTimeBean> descTime) {
            this.descTime = descTime;
        }

        public static class AscTimeBean {
            /**
             * runTime : 0830
             */

            private String runTime;

            public String getRunTime() {
                return runTime;
            }

            public void setRunTime(String runTime) {
                this.runTime = runTime;
            }
        }

        public static class DescTimeBean {
            /**
             * runTime : 0830
             */

            private String runTime;

            public String getRunTime() {
                return runTime;
            }

            public void setRunTime(String runTime) {
                this.runTime = runTime;
            }
        }
    }
}
