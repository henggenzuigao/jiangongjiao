package com.whpe.qrcode.jiangxi_jian.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityAboutUs;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityConsumrecords;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityLogin;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityMypurse;
import com.whpe.qrcode.jiangxi_jian.activity.ActivitySettings;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityTitleWeb;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;


/**
 * Created by yang on 2018/9/30.
 */

public class FrgMyself extends Fragment {
    private View content;
    private Context context;
    private ParentActivity activity;
    private TextView tv_phone;
    private ImageView iv_usericon;
    private RelativeLayout rl_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_myself,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ParentActivity) getActivity();
        bindView();
        initView();
    }

    private void initView() {
        ((ImageView)(content.findViewById(R.id.tab_mypurse).findViewById(R.id.iv_tab_img))).setImageDrawable(getResources().getDrawable(R.drawable.frg_myself_mypurse));
        ((ImageView)(content.findViewById(R.id.tab_deposit).findViewById(R.id.iv_tab_img))).setImageDrawable(getResources().getDrawable(R.drawable.frg_myself_deposit));
        ((ImageView)(content.findViewById(R.id.tab_usehelp).findViewById(R.id.iv_tab_img))).setImageDrawable(getResources().getDrawable(R.drawable.frg_myself_usehelp));
        ((ImageView)(content.findViewById(R.id.tab_call).findViewById(R.id.iv_tab_img))).setImageDrawable(getResources().getDrawable(R.drawable.frg_myself_call));
        ((ImageView)(content.findViewById(R.id.tab_aboutus).findViewById(R.id.iv_tab_img))).setImageDrawable(getResources().getDrawable(R.drawable.frg_myself_aboutus));
        ((ImageView)(content.findViewById(R.id.tab_settings).findViewById(R.id.iv_tab_img))).setImageDrawable(getResources().getDrawable(R.drawable.frg_myself_settings));

        ((TextView)(content.findViewById(R.id.tab_mypurse).findViewById(R.id.tv_tab_title))).setText(context.getString(R.string.myself_tab_mypurse));
        ((TextView)(content.findViewById(R.id.tab_deposit).findViewById(R.id.tv_tab_title))).setText(context.getString(R.string.myself_tab_deposit));
        ((TextView)(content.findViewById(R.id.tab_usehelp).findViewById(R.id.tv_tab_title))).setText(context.getString(R.string.myself_tab_usehelp));
        ((TextView)(content.findViewById(R.id.tab_call).findViewById(R.id.tv_tab_title))).setText(context.getString(R.string.myself_tab_calls));
        ((TextView)(content.findViewById(R.id.tab_aboutus).findViewById(R.id.tv_tab_title))).setText(context.getString(R.string.myself_tab_aboutus));
        ((TextView)(content.findViewById(R.id.tab_settings).findViewById(R.id.tv_tab_title))).setText(context.getString(R.string.myself_tab_settings));

        ((RelativeLayout)(content.findViewById(R.id.tab_mypurse).findViewById(R.id.rl_tab))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity.sharePreferenceLogin.getLoginStatus()){
                    activity.transAty(ActivityMypurse.class);
                }else {
                    activity.transAty(ActivityLogin.class);
                }
            }
        });
        ((RelativeLayout)(content.findViewById(R.id.tab_deposit).findViewById(R.id.rl_tab))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity.sharePreferenceLogin.getLoginStatus()){
                    activity.transAty(ActivityConsumrecords.class);
                }else {
                    activity.transAty(ActivityLogin.class);
                }
            }
        });
        ((RelativeLayout)(content.findViewById(R.id.tab_usehelp).findViewById(R.id.rl_tab))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToastUtils.showToast(context,getString(R.string.app_function_notopen));
                Bundle bundle=new Bundle();
                bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBURL,GlobalConfig.USEHELP_URL);
                bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBTITLE,getString(R.string.usehelp_title));
                ((ParentActivity)getActivity()).transAty(ActivityTitleWeb.class,bundle);
            }
        });
        ((RelativeLayout)(content.findViewById(R.id.tab_call).findViewById(R.id.rl_tab))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(context,getString(R.string.app_function_notopen));
            }
        });
        ((RelativeLayout)(content.findViewById(R.id.tab_aboutus).findViewById(R.id.rl_tab))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToastUtils.showToast(context,getString(R.string.app_function_notopen));
                activity.transAty(ActivityAboutUs.class);
            }
        });
        ((RelativeLayout)(content.findViewById(R.id.tab_settings).findViewById(R.id.rl_tab))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.transAty(ActivitySettings.class);
            }
        });

        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!activity.sharePreferenceLogin.getLoginStatus()){
                    activity.transAty(ActivityLogin.class);
                }
            }
        });
        iv_usericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!activity.sharePreferenceLogin.getLoginStatus()){
                    activity.transAty(ActivityLogin.class);
                }
            }
        });
        initTitle();
    }

    private void initTitle() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        rl_content.setPadding(0,result,0,0);
    }

    private void bindView() {
        tv_phone = (TextView)content.findViewById(R.id.tv_phone);
        iv_usericon = (ImageView)content.findViewById(R.id.iv_usericon);
        rl_content=(RelativeLayout)content.findViewById(R.id.rl_content);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(activity.sharePreferenceLogin.getLoginStatus()){
            String st_phone=activity.sharePreferenceLogin.getLoginPhone();
            String st_show_phone=st_phone.substring(0,3)+"****"+st_phone.substring(7,st_phone.length());
            tv_phone.setText(st_show_phone);
            iv_usericon.setImageDrawable(MyDrawableUtils.getDrawble(context,R.drawable.userhead));
            tv_phone.setClickable(false);
        }else {
            tv_phone.setText(getString(R.string.myself_pleaselogin));
            iv_usericon.setImageDrawable(MyDrawableUtils.getDrawble(context,R.drawable.nologin_userhead));
            tv_phone.setClickable(true);
        }
    }
}
