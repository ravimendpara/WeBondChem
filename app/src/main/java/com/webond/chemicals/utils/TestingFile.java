package com.webond.chemicals.utils;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.webond.chemicals.R;
import com.webond.chemicals.common_activity.SplashActivity;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetVersionInfoPojo;

public class TestingFile {
    private Dialog updateDialog;


    private void updateDialog(GetVersionInfoPojo getVersionInfoPojo) {
        updateDialog = new Dialog(SplashActivity.this);
        updateDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_shape_for_custom_dialog);//if need to change dialog radius in custom_layout_for_progress_dialog

        updateDialog.setCancelable(false);
        View customProgressDialog = LayoutInflater.from(SplashActivity.this).inflate(R.layout.custom_layout_for_update_app_dialog, null);
        TextViewMediumFont tvNoThanks = customProgressDialog.findViewById(R.id.tvNoThanks);
        Button btnUpdate = customProgressDialog.findViewById(R.id.btnUpdate);
        if (getVersionInfoPojo.getRECORDS().get(0).getUpdateSeverity().equals("2")) {
            tvNoThanks.setVisibility(View.GONE);
        } else {
            tvNoThanks.setVisibility(View.VISIBLE);
        }
        tvNoThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateDialog.dismiss();
                fetchLocation();

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
                UpdateApp atualizaApp = new UpdateApp();
                atualizaApp.setContext(SplashActivity.this);
                atualizaApp.execute(getVersionInfoPojo.getRECORDS().get(0).getApkUrl());
            }
        });


        updateDialog.setContentView(customProgressDialog);
        updateDialog.show();

    }


}
