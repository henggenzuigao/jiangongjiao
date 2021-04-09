package com.whpe.qrcode.jiangxi_jian.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListAckBody;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListItem;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityCloudRechargeCard;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityCustomBusTotal;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityLogin;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityMain;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityMypurse;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityNewsAndAdvertiseWeb;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityStudentCardSearch;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityTitleWeb;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.ShowNewsContentListAction;
import com.whpe.qrcode.jiangxi_jian.net.action.ShowTopCardContentListAction;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.TrueNewsBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.FakeNewsRlAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.HomeTopPagerAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.TrueNewsRlAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.TrueNewsRlHolder;

import java.util.ArrayList;


/**
 * Created by yang on 2018/9/30.
 */

public class FrgHome extends Fragment implements View.OnClickListener, ShowNewsContentListAction.Inter_ShowNewsContentList, ShowTopCardContentListAction.Inter_ShowCardContentList {
    private View content;
    private Context context;
    private ParentActivity activity;
    private ImageView iv_tabrecharge;
    private ImageView iv_tabbusmap;
    private ImageView iv_tabcallsus;
    private ImageView iv_tabusehelp;
    private ImageView iv_tabcustombus;
    private ImageView iv_tabcloudrechargecard;
    private ImageView iv_tabsearchstudentcard;
    private LinearLayout ll_content;
    private RecyclerView rl_news;
    private FakeNewsRlAdapter fakeNewsRlAdapter;
    private TrueNewsRlAdapter trueNewsRlAdapter;
    private ArrayList<TrueNewsBean> trueNewsBeans=new ArrayList<TrueNewsBean>();
    private ImageView iv_topcard;
    private ViewPager vp_top;
    private HomeTopPagerAdapter homeTopPagerAdapter;
    private SwipeRefreshLayout srl_refresh;
    private ViewPagerIndicator indicator_line;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_home,container,false);
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
        manageNewsAndTopCard();
    }

    private void manageNewsAndTopCard() {
        if(activity.isNetworkAvailable(activity)){
            requestForTopCardList();
            requestForNewsList();
        }else {
            ToastUtils.showToast(activity,getString(R.string.app_notnet));
        }
    }

    /*private void manageTopCard() {
        if(activity.isNetworkAvailable(activity)){
            requestForTopCardList();
        }
    }

    private void manageNews() {
        if(activity.isNetworkAvailable(activity)){
            requestForNewsList();
        }
    }*/

    public void requestForNewsList() {
        ShowNewsContentListAction showNewsContentListAction=new ShowNewsContentListAction(activity,this);
        String phone="";
        if(((ActivityMain)activity).sharePreferenceLogin.getLoginStatus()){
            phone=((ActivityMain)activity).sharePreferenceLogin.getLoginPhone();
        }
        showNewsContentListAction.sendAction(GlobalConfig.NEWSANDADVERLIST_PAGECHOOSE_HOMEPAGE,phone,GlobalConfig.NEWSANDADVERLIST_SPACEID_2);
    }

    public void requestForTopCardList() {
        ShowTopCardContentListAction showTopCardContentListAction=new ShowTopCardContentListAction(activity,this);
        String phone="";
        if(((ActivityMain)activity).sharePreferenceLogin.getLoginStatus()){
            phone=((ActivityMain)activity).sharePreferenceLogin.getLoginPhone();
        }
        showTopCardContentListAction.sendAction(GlobalConfig.NEWSANDADVERLIST_PAGECHOOSE_HOMEPAGE,phone,GlobalConfig.NEWSANDADVERLIST_SPACEID_1);
    }

    private void initView() {
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(context,getString(R.string.app_function_notopen));
            }
        };
        iv_tabrecharge.setOnClickListener(this);
        iv_tabbusmap.setOnClickListener(this);
        iv_tabcallsus.setOnClickListener(this);
        iv_tabcloudrechargecard.setOnClickListener(this);
        iv_tabsearchstudentcard.setOnClickListener(this);
        iv_tabcustombus.setOnClickListener(this);
        initTitle();
        initTop();
        initNews();
        initRefresh();
    }

    private void initRefresh() {
        srl_refresh.setFocusable(true);
        srl_refresh.setFocusableInTouchMode(true);

        srl_refresh.requestFocus();
        srl_refresh.setColorSchemeResources(R.color.app_theme);
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_refresh.setRefreshing(false);
                manageNewsAndTopCard();
            }
        });
    }

    private void initTop() {
        homeTopPagerAdapter = new HomeTopPagerAdapter(activity);
        vp_top.setAdapter(homeTopPagerAdapter);
    }

    private String[] urls={"http://static.xiaobu.hk/jhx/article/20161117/niduima/niduima.html","http://static.xiaobu.hk/jhx/article/20161117/tuhao/tuhao.html","http://static.xiaobu.hk/jhx/recommend/wazi/wazi.html"};

    /*private void initNews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        //设置布局管理器
        rl_news.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        fakeNewsRlAdapter = new FakeNewsRlAdapter(activity);
        rl_news.setAdapter(fakeNewsRlAdapter);
        fakeNewsRlAdapter.setItemClickListener(new NewsRlHolder.MyItemClickListener() {

            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle=new Bundle();
                bundle.putString("weburl",urls[postion]);
                ((ParentActivity)getActivity()).transAty(ActivityNewsWeb.class,bundle);
            }
        });

    }*/

    private void initNews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        //设置布局管理器
        rl_news.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rl_news.setNestedScrollingEnabled(false);
        //设置Adapter
        trueNewsRlAdapter = new TrueNewsRlAdapter(activity);
        trueNewsRlAdapter.setHasStableIds(true);
        trueNewsRlAdapter.setNewsList(trueNewsBeans);
        rl_news.setAdapter(trueNewsRlAdapter);
        trueNewsRlAdapter.setItemClickListener(new TrueNewsRlHolder.MyItemClickListener() {

            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle=new Bundle();
                bundle.putString(GlobalConfig.NEWSANDADVER_INTENT_CONTENTID,trueNewsBeans.get(postion).getContentid());
                bundle.putString(GlobalConfig.NEWSANDADVER_INTENT_TITLE,GlobalConfig.NEWSANDADVER_NEWS);
                activity.transAty(ActivityNewsAndAdvertiseWeb.class,bundle);
            }
        });

    }

    private void initTitle() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        ll_content.setPadding(0,result,0,30);
    }

    private void bindView() {
        iv_tabrecharge = (ImageView)content.findViewById(R.id.iv_tabrecharge);
        iv_tabbusmap = (ImageView)content.findViewById(R.id.iv_tabbusmap);
        iv_tabcallsus = (ImageView)content.findViewById(R.id.iv_tabcallsus);
        iv_tabusehelp = (ImageView)content.findViewById(R.id.iv_tabusehelp);
        iv_tabcloudrechargecard=(ImageView)content.findViewById(R.id.iv_tabcloudrechargecard);
        iv_tabsearchstudentcard=(ImageView)content.findViewById(R.id.iv_tabsearchstudentcard);
        iv_tabcustombus=(ImageView)content.findViewById(R.id.iv_tabcustombus);
        ll_content=(LinearLayout)content.findViewById(R.id.frg_ll_content);
        rl_news = (RecyclerView) content.findViewById(R.id.rl_news);
        iv_topcard=(ImageView)content.findViewById(R.id.iv_topcard);
        vp_top = (ViewPager)content.findViewById(R.id.vp_top);
        srl_refresh = (SwipeRefreshLayout)content.findViewById(R.id.srl_refresh);
        indicator_line = (ViewPagerIndicator)content.findViewById(R.id.indicator_line);
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.iv_tabbusmap){
            Bundle bundle=new Bundle();
            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBURL,GlobalConfig.SHISHIGONGJIAO_URL);
            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBTITLE,getString(R.string.aty_busmap_title));
            ((ParentActivity)getActivity()).transAty(ActivityTitleWeb.class,bundle);
        }else if(id==R.id.iv_tabrecharge){
            if(activity.sharePreferenceLogin.getLoginStatus()){
                activity.transAty(ActivityMypurse.class);
            }else {
                activity.transAty(ActivityLogin.class);
            }
        }else if(id==R.id.iv_tabusehelp){
            Bundle bundle=new Bundle();
            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBURL,GlobalConfig.USEHELP_URL);
            bundle.putString(GlobalConfig.TITLEWEBVIEW_WEBTITLE,getString(R.string.usehelp_title));
            ((ParentActivity)getActivity()).transAty(ActivityTitleWeb.class,bundle);
        }else if(id==R.id.iv_tabcallsus){
            Intent intent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.calls_phone)));
            startActivity(intent);
        }else if(id==R.id.iv_tabsearchstudentcard){
            if(activity.sharePreferenceLogin.getLoginStatus()){
                ((ParentActivity)getActivity()).transAty(ActivityStudentCardSearch.class);
            }else {
                activity.transAty(ActivityLogin.class);
            }
        }else if(id==R.id.iv_tabcloudrechargecard){
            if(activity.sharePreferenceLogin.getLoginStatus()){
                ((ParentActivity)getActivity()).transAty(ActivityCloudRechargeCard.class);
            }else {
                activity.transAty(ActivityLogin.class);
            }
        }else if(id==R.id.iv_tabcustombus){
            if(activity.sharePreferenceLogin.getLoginStatus()){
                activity.transAty(ActivityCustomBusTotal.class);
            }else {
                activity.transAty(ActivityLogin.class);
            }
        }
    }

    @Override
    public void onShowNewsContentListSucces(QueryNewsListAckBody getinfo) {
        trueNewsBeans.clear();
        ArrayList<QueryNewsListItem> queryNewsListItems=getinfo.getContentList();
        for(int i=0;i<queryNewsListItems.size();i++){
            TrueNewsBean trueNewsBean=new TrueNewsBean();
            trueNewsBean.setContentid(queryNewsListItems.get(i).getContentId());
            trueNewsBean.setTitle(queryNewsListItems.get(i).getContentName());
            trueNewsBean.setInfo(queryNewsListItems.get(i).getContentDesc());
            trueNewsBean.setImg(queryNewsListItems.get(i).getContentImage());
            trueNewsBeans.add(trueNewsBean);
            //Log.e("YC","TITLE="+queryNewsListItems.get(i).getContentName()+"----img="+queryNewsListItems.get(i).getContentImage());
        }
        trueNewsRlAdapter.setNewsList(trueNewsBeans);
        trueNewsRlAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowNewsContentListFaild(String resmsg) {

    }

    @Override
    public void onShowCardContentListSucces(QueryNewsListAckBody getinfo) {
        ArrayList<QueryNewsListItem> queryNewsListItems=getinfo.getContentList();
        homeTopPagerAdapter.setImageList(queryNewsListItems);

        if(queryNewsListItems!=null&&queryNewsListItems.size()>1){
            //viewpager是固定页数, 传入viewpager即可
            indicator_line.setViewPager(vp_top);

            //viewpager是轮播图 ,如:总数为100000个 实际展示页为6个
            //需要传入实际展示个数num
            indicator_line.setViewPager(vp_top,queryNewsListItems.size());
        }

        homeTopPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowCardContentListFaild(String resmsg) {

    }
}
