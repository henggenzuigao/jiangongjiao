package com.whpe.qrcode.jiangxi_jian.net.action.studentcardsearch;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.ApplyQrcodeRequestBody;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.TransparentRequestBody;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yang on 2018/12/14.
 */

public class SearchStudentCardInfoAction {
    public Inter_SearchStudentCardInfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();


    public SearchStudentCardInfoAction(Activity context, Inter_SearchStudentCardInfo inter_inter) {
        this.inter = inter_inter;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String name,String idcard){
        /*final String url = "http://182.101.206.184:9201/GJCard/InterfServlet?xm="+name+"&sfzh="+idcard;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = new FormBody.Builder().build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    final String getinfo=response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("YC","查询学生卡信息="+getinfo);
                            inter.onSearchStudentCardInfoSucces(getinfo);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inter.onSearchStudentCardInfoFaild("请求出错");
                        }
                    });
                }
            }
        }).start();*/
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final TransparentRequestBody transparentRequestBody=new TransparentRequestBody();
        transparentRequestBody.setBusinessType(GlobalConfig.BUSSINSS_QUERYSTUDENTINFO);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name",name);
        jsonObject.addProperty("idCardNo",idcard);
        transparentRequestBody.setParam(jsonObject);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).transparent(head, transparentRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String info) {
                        final String getinfo=info;
                        Log.e("YC","学籍查询="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onSearchStudentCardInfoSucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onSearchStudentCardInfoFaild(e.getMessage());
                            }
                        });

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }).start();

    }

    public interface Inter_SearchStudentCardInfo{
        public void onSearchStudentCardInfoSucces(ArrayList<String> getinfo);
        public void onSearchStudentCardInfoFaild(String resmag);
    }
}
