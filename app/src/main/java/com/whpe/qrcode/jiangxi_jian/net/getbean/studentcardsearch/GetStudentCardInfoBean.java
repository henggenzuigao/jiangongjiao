package com.whpe.qrcode.jiangxi_jian.net.getbean.studentcardsearch;

import java.util.List;

/**
 * Created by yang on 2018/12/14.
 */

public class GetStudentCardInfoBean {

    /**
     * data : [{"jdzz":"江西省吉安市中山东路11号2单元302","sfzhm":"360802200601190511","jg":"江西永丰","lxdh":"13979630046","xb":"男","mz":"汉族","xszt":"在校","xsxm":"李人熠","xxsbm":"3436010143","xjh":"G360802200601190511","xxmc":"江西省吉安市白鹭洲中学"}]
     * code : 1
     */

    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * jdzz : 江西省吉安市中山东路11号2单元302
         * sfzhm : 360802200601190511
         * jg : 江西永丰
         * lxdh : 13979630046
         * xb : 男
         * mz : 汉族
         * xszt : 在校
         * xsxm : 李人熠
         * xxsbm : 3436010143
         * xjh : G360802200601190511
         * xxmc : 江西省吉安市白鹭洲中学
         */

        private String jdzz;
        private String sfzhm;
        private String jg;
        private String lxdh;
        private String xb;
        private String mz;
        private String xszt;
        private String xsxm;
        private String xxsbm;
        private String xjh;
        private String xxmc;

        public String getJdzz() {
            return jdzz;
        }

        public void setJdzz(String jdzz) {
            this.jdzz = jdzz;
        }

        public String getSfzhm() {
            return sfzhm;
        }

        public void setSfzhm(String sfzhm) {
            this.sfzhm = sfzhm;
        }

        public String getJg() {
            return jg;
        }

        public void setJg(String jg) {
            this.jg = jg;
        }

        public String getLxdh() {
            return lxdh;
        }

        public void setLxdh(String lxdh) {
            this.lxdh = lxdh;
        }

        public String getXb() {
            return xb;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }

        public String getMz() {
            return mz;
        }

        public void setMz(String mz) {
            this.mz = mz;
        }

        public String getXszt() {
            return xszt;
        }

        public void setXszt(String xszt) {
            this.xszt = xszt;
        }

        public String getXsxm() {
            return xsxm;
        }

        public void setXsxm(String xsxm) {
            this.xsxm = xsxm;
        }

        public String getXxsbm() {
            return xxsbm;
        }

        public void setXxsbm(String xxsbm) {
            this.xxsbm = xxsbm;
        }

        public String getXjh() {
            return xjh;
        }

        public void setXjh(String xjh) {
            this.xjh = xjh;
        }

        public String getXxmc() {
            return xxmc;
        }

        public void setXxmc(String xxmc) {
            this.xxmc = xxmc;
        }
    }
}
