package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.NewsRlHolder;


/**
 * Created by yang on 2018/9/9.
 */

public class FakeNewsRlAdapter extends RecyclerView.Adapter<NewsRlHolder>{
    private NewsRlHolder.MyItemClickListener mItemClickListener;
    private Activity context;
    private int[] icons={R.drawable.news1,R.drawable.news2,R.drawable.news3};
    private String[] maintitles={"全国痴呆等级测试","我们都是普通家庭，没有什么特殊的",
            "没想到旧袜子这么时尚，新一轮技能来了"};
    private String[] times={"看看你是几级？哈哈哈","顶多是房子大了点","今天就教大家如何将它们大变身！"};
    private String[] urls={"http://static.xiaobu.hk/jhx/article/20161117/niduima/niduima.html","http://static.xiaobu.hk/jhx/article/20161117/tuhao/tuhao.html","http://static.xiaobu.hk/jhx/recommend/wazi/wazi.html"};


    public FakeNewsRlAdapter(Activity contetx) {
        this.context=contetx;
    }

    @Override
    public NewsRlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frg_home_news,parent,false);
        NewsRlHolder newsRlHolder =new NewsRlHolder(view,mItemClickListener);
        return newsRlHolder;
    }

    @Override
    public void onBindViewHolder(NewsRlHolder holder, final int position) {
        holder.tv_maintitle.setText(maintitles[position]);
        holder.tv_time.setText(times[position]);
        holder.iv_icon.setBackgroundDrawable(context.getResources().getDrawable(icons[position]));
    }

    @Override
    public int getItemCount() {
        return maintitles.length;
    }

    public void setItemClickListener(NewsRlHolder.MyItemClickListener itemClickListener){
        mItemClickListener=itemClickListener;
    }
}
