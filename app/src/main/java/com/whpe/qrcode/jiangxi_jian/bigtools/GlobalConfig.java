package com.whpe.qrcode.jiangxi_jian.bigtools;

/**
 * Created by yang on 2018/9/30.
 */

public class GlobalConfig {
    public static final String testurl="http://192.168.31.205:8080/download/00000001CSZX";

    /**
     * 老年卡退款相关url
     */
    public static final String SENIORCARD_REFUND_URL="http://222.89.193.235:9001/GJCard/cardManage/";

    /**
     * 二维码等app相关功能url（耿旺的功能）
     */
    //public static final String APP_GW_FUNCTION_URL="http://192.168.3.33:8080/GJCard/cardManage/";
    //public static final String APP_GW_FUNCTION_URL="http://43.241.232.24:8080/AppServerWhpe/";
    //public static final String APP_GW_FUNCTION_URL="http://43.241.237.88:80/AppServerWhpe/";
    //域名端口
    //public static final String APP_GW_FUNCTION_URL="http://test-mobileqrcode.ymdx.cn/AppServerWhpe/";
    public static final String APP_GW_FUNCTION_URL="http://mobileqrcode.ymdx.cn/AppServerWhpe/";

    /*
     *app名字
     */
    public static final String APP_APKNAME="gy_jagj.apk";


    //请求数据
    public static final String APPID="03634350JAGJ";
    public static final String CITYCODE="03634350";

    /**
     * titlewebview参数传递标识
     */
    public static final String TITLEWEBVIEW_WEBURL="weburl";
    public static final String TITLEWEBVIEW_WEBTITLE="webtitle";

    /**
     * 新闻以及广告接口相关参数
     */
    public static final String NEWSANDADVERLIST_PAGECHOOSE_HOMEPAGE="homePage";
    public static final String NEWSANDADVERLIST_PAGECHOOSE_RECHARGEPAGE="rechargePage";
    public static final String NEWSANDADVERLIST_PAGECHOOSE_FIRSTPAGE="firstPage";
    public static final String NEWSANDADVERLIST_SPACEID_1="1";
    public static final String NEWSANDADVERLIST_SPACEID_2="2";
    public static final String NEWSANDADVERLIST_SPACEID_22="22";
    public static final String NEWSANDADVERLIST_SPACEID_TEST="111";
    public static final String NEWSANDADVER_NEWS="新闻";
    public static final String NEWSANDADVER_ADVER="广告";
    public static final String NEWSANDADVER_INTENT_TITLE="title";
    public static final String NEWSANDADVER_INTENT_CONTENTID="contentid";
    public static final String NEWSANDADVER_CONTENTTYPE="contentType";
    public static final String NEWSANDADVER_CONTENTTYPE_IMAGE="IMAGE";
    public static final String NEWSANDADVER_CONTENTTYPE_URL="URL";
    public static final String NEWSANDADVER_CONTENTTYPE_HTML="HTML";


    /**
     * 获取刷码类型的各种情况
     */
    public static final String QRCODE_TYPE_KEY="QRCODE_TYPE_KEY";
    public static final int QRCODE_TYPE_QRCODESHOW=0;
    public static final int QRCODE_TYPE_NOTOPEN=1;
    public static final int QRCODE_TYPE_BALANCECANTUSE=2;
    public static final int QRCODE_TYPE_ARREAR=3;
    public static final int QRCODE_TYPE_DEPOSIT=4;
    public static final int QRCODE_TYPE_NOTBANDPAYTYPE=5;



    /**
     * 二维码用户电子卡是否开通
     */
    public static final String QRCODE_CARD_ISOPEN="01";
    public static final String QRCODE_CARD_NO_KEY="CARDNO";


    /**
     * 我的钱包-钱包充值
     */
    public static final String INTENT_MONEY_KEY="INTENT_MONEY_KEY";



    /**
     * 预加载参数标识
     */
    public static final String LOADPARAM_QROPAYTYPE_PREPAY="prepay";
    public static final String LOADPARAM_QROPAYTYPE_LATERPAY="laterPay";
    public static final String LOADPARAM_QROPAYTYPE_ALL="all";
    public static final String LOADPARAM_QROPAYTYPE_PREPAYWEICHAT="20";
    public static final String LOADPARAM_QROPAYTYPE_PREPAYUNION="21";
    public static final String LOADPARAM_QROPAYTYPE_PREPAYALIPAY="22";
    public static final String LOADPARAM_QROPAYTYPE_PREPAYCCB="80";
    public static final String LOADPARAM_QROPAYTYPE_LATERPAYBALANCE="00";



    /**
     * 各种rescode
     */
    //公共返回码
    public static final String RESCODE_SUCCESS="01";
    public static final String RESCODE_SYSTEMERROR="99";
    //二维码用户状态查询未开通电子卡
    public static final String RESCODE_NOTOPENQRCARD="02";
    //开通二维码电子卡失败
    public static final String RESCODE_APPLYQRCARD_FAILD="09";
    //统一下单失败
    public static final String RESCODE_PAYUNITY_QRCARDNOTFIND="31";
    //请重新登录
    public static final String RESCODE_PLEASE_RELOGIN="12";
    //请重新加载预加载参数
    public static final String RESCODE_PLEASE_RELOADPARAM="15";
    //请下载最新app（版本完全不兼容）
    public static final String RESCODE_PLEASE_UPDATE="14";
    //空圈实体卡相关异常（需要特殊处理）
    public static final String RESCODE_RECHARGECARD_SPEACIAL="98";


    /**
     * 业务类型（耿旺中转）
     */
    public static final String BUSSINSS_QUERYSTUDENTINFO="queryStudentInfo";


    /**
     * 二维码卡状态码
     */
    public static final String QRCODESTATUS_SUCCESS="01";



    /**
     * 下单参数
     */
    //下单类型
    public static final String PAYUNITY_TYPE_BALANCE_TOPAY="00";
    public static final String PAYUNITY_TYPE_DEPOSIT_TOPAY="01";
    public static final String PAYUNITY_TYPE_BUSCARDAMOUNT_TOPAY="02";
    public static final String PAYUNITY_TYPE_BUSCARDTIMES_TOPAY="03";
    public static final String PAYUNITY_TYPE_CUSTOMBUS_TOPAY="10";
    //充值应用区域（当且仅当实体卡 充值存在此域 ）
    public static final String PAYUNITY_TYPE_BUSCARD_CARDAREA_M1="1001";//M1卡
    public static final String PAYUNITY_TYPE_BUSCARD_CARDAREA_CPUNORMAL="0105";//cpu一卡通
    public static final String PAYUNITY_TYPE_BUSCARD_CARDAREA_CPUOTHER="1002";//cpu其他行业卡
    //读卡方式（当且仅当实体卡 充值存在此域 ）
    public static final String PAYUNITY_TYPE_BUSCARD_READWAY_CLOUD="01";//手输云充模式
    public static final String PAYUNITY_TYPE_BUSCARD_READWAY_CIRCLE="00";//nfc读取
    //余额（当且仅当实体卡 充值存在此域 ）
    public static final String PAYUNITY_TYPE_BUSCARD_BALANCE_NORMAL="0";//手输云充无余额默认0


    /**
     * 订单查询网关
     */
    //public static final String



    /**
     *查询二维码消费记录(以二维码为主)
     */
    public static final String QUERYQRAMTDATA_SOURCETYPE_BUSDATA ="busData";
    public static final String QUERYQRAMTDATA_SOURCETYPE_TRADEDATA="tradeData";
    public static final String QUERYQRAMTDATA_SOURCETYPE_OWEDATA="oweData";
    public static final String QUERYQRAMTDATA_BUSDATA_SUCCESS ="01";
    public static final String QUERYQRAMTDATA_BUSDATA_INIT="00";
    public static final String QUERYQRAMTDATA_BUSDATA_FAILED="02";


    /**
     * 下单支付接口传递页面参数key
     */
    public static final String INTENT_TOPAYWEB_TYPE="INTENT_TOPAYWEB_TYPE";
    public static final String INTENT_TOPAYWEB_TYPE_URL="INTENT_TOPAYWEB_URL";
    public static final String INTENT_TOPAYWEB_TYPE_HTML="INTENT_TOPAYWEB_HTML";
    public static final String INTENT_TOPAYWEB_PARAM="INTENT_TOPAYWEB_PARAM";


    /**
     *查询订单网关支付等记录(以用户为主)
     */
    //查询参数
    public static final String QUERYORDER_SELECTTYPE_GETWAY="gateway";
    public static final String QUERYORDER_SELECTPARAMTYPE_CARDNO="cardNo";
    public static final String QUERYORDER_SELECTPARAMTYPE_goodsOrderNum="goodsOrderNum";
    public static final String QUERYORDER_SELECTPARAMTYPE_merchantOderNo="merchantOderNo";
    public static final String QUERYORDER_SELECTPARAMTYPE_UID="uid";
    public static final String QUERYORDER_GETSTATUS_SUCCESS="success";
    public static final String QUERYORDER_GETSTATUS_UNKNOWN="unknown";
    public static final String QUERYORDER_GETSTATUS_FAILD="failed";


    /**
     * 微信支付的appid以及key
     */
    public static final String WEICHAT_KEY="1HQPVKapC16LMjLL3AB6tzN8TVlqj6ME";

    /**
     * 银联支付的参数
     */
    // “00” – 银联正式环境 // “01” – 银联测试环境，该环境中不发生真实交易
    public static final String UNION_SERVERMODE="00";

    /**
     * 支付宝的参数
     */
    public static final int SDK_PAY_FLAG = 1;

    /**
     * 吉安特别的参数
     */
    public static final String SHISHIGONGJIAO_URL="http://0796.shenghuoquan.cn:8081/jah5/index.html";
    public static final String USEHELP_URL="http://mobileqrcode-manager.ymdx.cn:8080/AppServerManage/getHelpHtml.do?appId=03634350JAGJ";


    /**
     * 吉安定制公交相关参数
     */
    /*
     */
    public static final String CUSTOMBUS_INTERFACEVERSION="1";
    public static final String CUSTOMBUS_QUERYBUSLINEPAGE_LINENO="lineNo";
    public static final String CUSTOMBUS_QUERYBUSLINEPAGE_STATIONNAME="stationName";
    public static final String INTENT_CUSTOMBUS_SEARCHBUSLINETOSELECTFREQUENCY_KEY="searchbuslinetoselectfrequency";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_LINEID_KEY="lineId";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_LINENAME_KEY="linename";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_CHOOSETIME_KEY="choosetime";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_STARTSITE_KEY="startsite";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_POINTSITE_KEY="pointsite";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_TOTALLEN_KEY="totalLen";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_KEY="showSeq";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_VALUE_ASC="ASC";
    public static final String INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_VALUE_DESC="DESC";
    public static final String CUSTOMBUS_SELECTDATE_SALESTATUS_FORSALE="FORSALE";
    public static final String CUSTOMBUS_SELECTDATE_SALESTATUS_ONSALE="ONSALE";
    public static final String CUSTOMBUS_SELECTDATE_SALESTATUS_STOPSALE="STOPSALE";
    public static final String INTENT_CUSTOMBUS_SELECTDATETOORDERINFO_KEY="INTENT_CUSTOMBUS_SELECTDATETOORDERINFO_KEY";
    public static final String INTENT_CUSTOMBUS_ORDERINFO_SHOWSEQ_VALUE_ASC="ASC";
    public static final String INTENT_CUSTOMBUS_ORDERINFO_SHOWSEQ_VALUE_DESC="DESC";
    public static final String INTENT_CUSTOMBUS_ORDERINFOTOPAY_KEY="INTENT_CUSTOMBUS_ORDERINFOTOPAY_KEY";
    public static final String INTENT_CUSTOMBUS_USERTICKETSTOTICKETINFO_TICKETID_KEY="INTENT_CUSTOMBUS_USERTICKETSTOTICKETINFO_TICKETID_KEY";
    public static final String INTENT_CUSTOMBUS_ASC="ASC";
    public static final String INTENT_CUSTOMBUS_DESC="DESC";

    public static final String TICKETNOTICE_URL="http://mobileqrcode-manager.ymdx.cn/AppServerManage/linkUrl/ticketAgreement/03634350JAGJ.do";
}
