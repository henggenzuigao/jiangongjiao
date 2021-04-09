package com.whpe.qrcode.jiangxi_jian.activity;


import androidx.fragment.app.FragmentTransaction;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.fragment.studentcardsearch.FrgStudentCardSearchResult;
import com.whpe.qrcode.jiangxi_jian.fragment.studentcardsearch.FrgStudentCardSearchToSearch;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.studentcardsearch.GetStudentCardInfoBean;
import com.whpe.qrcode.jiangxi_jian.parent.BackgroundTitleActivity;

/**
 * Created by yang on 2018/12/13.
 */

public class ActivityStudentCardSearch extends BackgroundTitleActivity  {
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    private String frg_mark="";
    private FrgStudentCardSearchToSearch frgStudentCardSearchToSearch;
    private FrgStudentCardSearchResult frgStudentCardSearchResult;
    public GetStudentCardInfoBean getStudentCardInfoBean=new GetStudentCardInfoBean();

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_studentcardsearch);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setMyTitleColor(R.color.transparency);
        setTitle(getString(R.string.studentcard_search_title));
        showSearch();

    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
    }

    @Override
    protected void setMyTitleColor(int color) {
        super.setMyTitleColor(color);
    }

    public void showSearch(){
        frg_mark=getString(R.string.studentcard_search_frg_search);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgStudentCardSearchToSearch = new FrgStudentCardSearchToSearch();
        ft.replace(R.id.fl_content, frgStudentCardSearchToSearch);
        ft.commitAllowingStateLoss();
    }

    public void showResult(){
        frg_mark=getString(R.string.studentcard_search_frg_result);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgStudentCardSearchResult = new FrgStudentCardSearchResult();
        ft.replace(R.id.fl_content, frgStudentCardSearchResult);
        ft.commitAllowingStateLoss();
    }


}
