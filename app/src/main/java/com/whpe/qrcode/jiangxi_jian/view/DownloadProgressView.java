package com.whpe.qrcode.jiangxi_jian.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;


/**
 * Created by yang on 2018/10/2.
 */

public class DownloadProgressView {
    private Context context;
    private Dialog dialog;
    private RelativeLayout lLayout_bg;
    private Display display;
    private TextView tv_download;
    private ProgressBar progressBar;

    public DownloadProgressView(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public DownloadProgressView builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_download_progress, null);

        lLayout_bg = (RelativeLayout) view.findViewById(R.id.ll_download);
        tv_download = (TextView)view.findViewById(R.id.tv_download);
        progressBar = (ProgressBar)view.findViewById(R.id.download_progress);

        dialog = new Dialog(context, R.style.Widget_AppCompat_ProgressBar_Horizontal);
        dialog.setContentView(view);

        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 1), ViewGroup.LayoutParams.MATCH_PARENT));

        return this;
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public void setMsg(int progress) {
        tv_download.setText(String.format(context.getString(R.string.update_tv_dowanload),progress));
    }

    public void setProgress(int progress){
        progressBar.setProgress(progress);
    }



    public DownloadProgressView setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dissmiss(){
        dialog.dismiss();
    }
}
