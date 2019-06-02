package com.whpe.qrcode.jiangxi_jian.net.getbean;

/**
 * Created by yang on 2018/10/10.
 */

public class ComomBean {

    /**
     * respCode : ‘xxxx’
     * respMsg : ’xxx’
     * data : JSONObject
     * sign : ’xxxx’
     */

    private String respCode;
    private String respMsg;
    private String data;
    private String sign;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
