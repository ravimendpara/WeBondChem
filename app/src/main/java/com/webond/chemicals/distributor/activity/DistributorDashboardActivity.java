package com.webond.chemicals.distributor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetBannerListPojo;
import com.webond.chemicals.utils.MySharedPreferences;

import net.seifhadjhassen.recyclerviewpager.PagerModel;
import net.seifhadjhassen.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvManageDealer;
    private MaterialCardView cvManageCustomer;
    private Animation animation;
    RecyclerViewPager recyclerViewPagerStudentSideBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_dashboard);
        initView();
        getBannerList();
    }


    private void initView() {
        recyclerViewPagerStudentSideBanner = findViewById(R.id.recyclerViewPagerStudentSideBanner);
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
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorProfileActivity.class);
                    startActivity(intent);
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

    private void getBannerList() {
        ApiImplementer.getBannerListApiImplementer(new Callback<ArrayList<GetBannerListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetBannerListPojo>> call, Response<ArrayList<GetBannerListPojo>> response) {
                if (response.code() == 200 && response.body() != null && response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getBannerPath() != null && !response.body().get(i).getBannerPath().isEmpty() && response.body().get(i).getBannerPath().length() > 7) {
                            recyclerViewPagerStudentSideBanner.addItem(new PagerModel(response.body().get(i).getBannerPath()));
                        }
                    }
                    recyclerViewPagerStudentSideBanner.start();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetBannerListPojo>> call, Throwable t) {

            }
        });
    }
}