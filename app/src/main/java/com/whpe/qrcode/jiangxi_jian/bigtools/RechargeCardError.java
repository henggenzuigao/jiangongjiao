package com.whpe.qrcode.jiangxi_jian.bigtools;

import android.util.Log;

/**
 * Created by yang on 2018/12/13.
 */

public enum RechargeCardError {
    ERROR1("00A0", "通讯报文 MAC 验证失败"), ERROR2("0001", "卡不存在"),ERROR3("0002", "卡状态非法"), ERROR4("0003", "卡种不存在"),
    ERROR5("0004", "卡种状态非法"), ERROR6("0005", "卡应用不存在或者非法"),ERROR7("0006", "不支持的业务"), ERROR8("0007", "卡文件解析错误"),
    ERROR9("0008", "圈存信息非法"), ERROR10("0009", "计算卡片 MAC 失败"),ERROR11("0010", "商户号非法"), ERROR12("0011", "终端号不存在非法"),
    ERROR13("0101", "订单金额非法"), ERROR14("0102", "写卡金额非法"),ERROR15("0103", "写卡金额非法"), ERROR16("0104", "超过卡片限额"),
    ERROR17("0201", "平台订单号非法"), ERROR18("0202", "平台订单号不存在"),ERROR19("0203", "支付订单号非法"), ERROR20("0204", "单卡空圈订单数超限"),
    ERROR21("0901", "连库写记录失败"), ERROR22("0902", "连库更新记录失败"),ERROR23("0999", "未知异常"), ERROR24("8051", "错误返回"),ERROR37("8053","程序异常"),
    ERROR25("0x01", "用户卡不存在"), ERROR26("0x02", "卡种非法或者状态非法"),ERROR27("0x03", "卡片黑名单"), ERROR28("0x04", "保存充值记录失败"),
    ERROR29("0x05", "用户卡状态不正确"), ERROR30("0x06", "金额非法--充后余额大于 1000"),ERROR31("0x07", "不可操作的卡种"), ERROR32("0x09", "卡种对应配置非法或者状态不正确"),
    ERROR33("0x10", "卡应用文件解析失败"), ERROR34("0x11", "卡应用配置非金额类,不可充值"),ERROR35("0x15", "应用启用标志未开,不能充值"), ERROR36("0x21", "连接加密机获取 Mac2 失败");


    private String value;
    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private RechargeCardError(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getErrorByCode(String value) {
        RechargeCardError defaultType = RechargeCardError.ERROR1;
        for (RechargeCardError ftype : RechargeCardError.values()) {
            if (ftype.value.equals(value)) {
                return ftype.desc;
            }
        }
        return defaultType.desc;
    }
}
