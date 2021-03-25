package com.webond.chemicals.distributor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminManageCustomerActivity;
import com.webond.chemicals.admin.activity.AdminManageDealerActivity;
import com.webond.chemicals.utils.MySharedPreferences;

public class DistributorDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvManageDealer;
    private MaterialCardView cvManageCustomer;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_dashboard);
        initView();
    }


    private void initView() {
        mySharedPreferences = new MySharedPreferences(DistributorDashboardActivity.this);
        cvProfile = findViewById(R.id.cvProfile);
        cvProfile.setOnClickListener(this);
        cvManageDealer = findViewById(R.id.cvManageDealer);
        cvManageDealer.setOnClickListener(this);
        cvManageCustomer = findViewById(R.id.cvManageCustomer);
        cvManageCustomer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvProfile) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvProfile.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 400);
        } else if (v.getId() == R.id.cvManageDealer) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvManageDealer.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorManageDealerActivity.class);
                    startActivity(intent);
                }
            }, 400);
        } else if (v.getId() == R.id.cvManageCustomer) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvManageCustomer.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorManageCustomerActivity.class);
                    startActivity(intent);
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