package com.webond.chemicals.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.utils.MySharedPreferences;

public class CustomerDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvProductList;
    private MaterialCardView cvManageOrder;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        initView();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(CustomerDashboardActivity.this);
        cvProfile = findViewById(R.id.cvProfile);
        cvProfile.setOnClickListener(this);
        cvProductList = findViewById(R.id.cvProductList);
        cvProductList.setOnClickListener(this);
        cvManageOrder = findViewById(R.id.cvManageOrder);
        cvManageOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvProfile) {
            animation = AnimationUtils.loadAnimation(CustomerDashboardActivity.this, R.anim.bounce);
            cvProfile.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 400);
        } else if (v.getId() == R.id.cvProductList) {
            animation = AnimationUtils.loadAnimation(CustomerDashboardActivity.this, R.anim.bounce);
            cvProductList.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 400);
        } else if (v.getId() == R.id.cvManageOrder) {
            animation = AnimationUtils.loadAnimation(CustomerDashboardActivity.this, R.anim.bounce);
            cvManageOrder.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 400);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animation.cancel();
    }
}