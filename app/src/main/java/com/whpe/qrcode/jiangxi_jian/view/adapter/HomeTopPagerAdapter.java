package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListItem;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.view.CustomRoundAngleImageView;


import java.util.ArrayList;

/**
 * Created by yang on 2019/1/18.
 */

public class HomeTopPagerAdapter extends PagerAdapter {
    private ArrayList<QueryNewsListItem> queryNewsListItemArrayList;
    private Context context;

    public HomeTopPagerAdapter(Context context) {
        this.context = context;
    }

    public void setImageList(ArrayList<QueryNewsListItem> queryNewsListItems){
        queryNewsListItemArrayList=queryNewsListItems;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.e("YC","??="+queryNewsListItemArrayList.get(position).getContentImage());
        View view=LayoutInflater.from(context).inflate(R.layout.item_home_vptop,container,false);
        CustomRoundAngleImageView iv_top=(CustomRoundAngleImageView)view.findViewById(R.id.iv_top);
        /*if(queryNewsListItemArrayList==null||queryNewsListItemArrayList.size()==0){
            iv_top.setImageDrawable(MyDrawableUtils.getDrawble(context,R.drawable.frg_home_topcard));
            container.addView(view);
            return view;
        }*/
        Picasso.with(this.context)
                .load(queryNewsListItemArrayList.get(position).getContentImage())
                .placeholder(R.drawable.frg_home_topcard)
                .error(R.drawable.frg_home_topcard)
                .config(Bitmap.Config.RGB_565)
                .into(iv_top);
        container.addView(view);
        return (view);
    }

    @Override
    public int getCount() {
        if (queryNewsListItemArrayList == null)
            return 0;
        return queryNewsListItemArrayList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
