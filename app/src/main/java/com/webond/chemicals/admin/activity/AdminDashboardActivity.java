package com.webond.chemicals.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.utils.MySharedPreferences;

public class AdminDashboardActivity extends AppCompatActivity implements View.OnClickListener {


    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvManageDistributor;
    private MaterialCardView cvManageDealer;
    private MaterialCardView cvManageCustomer;
    private MaterialCardView cvAddProduct;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        initView();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(AdminDashboardActivity.this);
        cvProfile = findViewById(R.id.cvProfile);
        cvProfile.setOnClickListener(this);
        cvManageDistributor = findViewById(R.id.cvManageDistributor);
        cvManageDistributor.setOnClickListener(this);
        cvManageDealer = findViewById(R.id.cvManageDealer);
        cvManageDealer.setOnClickListener(this);
        cvManageCustomer = findViewById(R.id.cvManageCustomer);
        cvManageCustomer.setOnClickListener(this);
        cvAddProduct = findViewById(R.id.cvAddProduct);
        cvAddProduct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvProfile) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvProfile.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 400);
        } else if (v.getId() == R.id.cvManageDistributor) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvManageDistributor.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminManageDistributorActivity.class);
                    startActivity(intent);
                }
            }, 400);
        } else if (v.getId() == R.id.cvManageDealer) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvManageDealer.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminManageDealerActivity.class);
                    startActivity(intent);
                }
            }, 400);
        } else if (v.getId() == R.id.cvManageCustomer) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvManageCustomer.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminManageCustomerActivity.class);
                    startActivity(intent);
                }
            }, 400);
        } else if (v.getId() == R.id.cvAddProduct) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvAddProduct.startAnimation(animation);
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




