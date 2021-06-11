package com.webond.chemicals.common_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;

public class RedeemSuccessfullyActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton btnGoToRedemption;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_successfully);
        initView();
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("My Redemption");
        btnGoToRedemption = findViewById(R.id.btnGoToRedemption);
        btnGoToRedemption.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGoToRedemption) {
            onBackPressed();
        } else if (view.getId() == R.id.imgBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        super.onBackPressed();
    }
}