package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;

public class AdminReportActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private MaterialCardView cvPointReport;
    private MaterialCardView cvStockReport;
    private MaterialCardView cvOrderRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);
        initView();

    }


    private void initView(){
        imgBack = findViewById(R.id.imgBack);
        cvPointReport = findViewById(R.id.cvPointReport);
        cvOrderRegister = findViewById(R.id.cvOrderRegister);
        cvStockReport = findViewById(R.id.cvStockReport);
        imgBack.setOnClickListener(this);
        cvStockReport.setOnClickListener(this);
        cvPointReport.setOnClickListener(this);
        cvOrderRegister.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Reports");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();

        }else if (v.getId() == R.id.cvStockReport){

            Intent stockReportIntent =new Intent(this,AdminStockReportActivity.class);
            startActivity(stockReportIntent);

        }else if (v.getId() == R.id.cvPointReport){
            Intent pointReportIntent =new Intent(this,AdminPointReportActivity.class);
            startActivity(pointReportIntent);

        }else if (v.getId() == R.id.cvOrderRegister){
            Intent orderRegister =new Intent(this,OrderRegisterActivity.class);
            startActivity(orderRegister);

        }

    }
}