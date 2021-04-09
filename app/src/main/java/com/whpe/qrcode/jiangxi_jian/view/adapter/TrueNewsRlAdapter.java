package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.toolbean.TrueNewsBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.TrueNewsRlHolder;

import java.util.ArrayList;

/**
 * Created by yang on 2018/9/9.
 */

public class TrueNewsRlAdapter extends RecyclerView.Adapter<TrueNewsRlHolder>{
    private TrueNewsRlHolder.MyItemClickListener mItemClickListener;
    private Activity context;
    private ArrayList<TrueNewsBean> trueNewsBeans;
    public TrueNewsRlAdapter(Activity contetx) {
        this.context=contetx;
    }

    public void setNewsList(ArrayList<TrueNewsBean> trueNewsBeanArrayList){
        trueNewsBeans=trueNewsBeanArrayList;
    }

    @Override
    public TrueNewsRlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frg_home_news,parent,false);
        TrueNewsRlHolder newsRlHolder =new TrueNewsRlHolder(view,mItemClickListener);
        return newsRlHolder;
    }

    @Override
    public void onBindViewHolder(TrueNewsRlHolder holder, final int position) {
        TrueNewsBean trueNewsBean=trueNewsBeans.get(position);
        holder.tv_maintitle.setText(trueNewsBean.getTitle());
        holder.tv_info.setText(trueNewsBean.getInfo());
        if (!TextUtils.isEmpty(trueNewsBean.getImg())){
            Picasso.with(this.context)
                    .load(trueNewsBean.getImg())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE) //新版Picasso必须都设置缓存
                    .placeholder(R.drawable.frg_home_news_defultlogo)
                    .error(R.drawable.frg_home_news_defultlogo)
                    .config(Bitmap.Config.RGB_565)
                    .fit()
                    .centerCrop()
                    .into(holder.iv_icon);
        }else {
            holder.iv_icon.setBackground(MyDrawableUtils.getDrawble(context,R.drawable.frg_home_news_defultlogo));
        }
    }

    @Override
    public int getItemCount() {
        if(trueNewsBeans.size()>20){
            return 20;
        }
        return trueNewsBeans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItemClickListener(TrueNewsRlHolder.MyItemClickListener itemClickListener){
        mItemClickListener=itemClickListener;
    }
}
