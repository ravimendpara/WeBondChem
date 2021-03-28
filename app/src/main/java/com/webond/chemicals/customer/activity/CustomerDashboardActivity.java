package com.webond.chemicals.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.common_activity.LoginActivity;
import com.webond.chemicals.pojo.GetBannerListPojo;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import net.seifhadjhassen.recyclerviewpager.PagerModel;
import net.seifhadjhassen.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvProductList;
    private MaterialCardView cvManageOrder;
    private MaterialCardView cvAddOrder;
    private Animation animation;
    RecyclerViewPager recyclerViewPagerStudentSideBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        initView();
        getBannerList();
    }

    private void initView() {
        recyclerViewPagerStudentSideBanner = findViewById(R.id.recyclerViewPagerStudentSideBanner);
        mySharedPreferences = new MySharedPreferences(CustomerDashboardActivity.this);
        cvProfile = findViewById(R.id.cvProfile);
        cvProfile.setOnClickListener(this);
        cvProductList = findViewById(R.id.cvProductList);
        cvProductList.setOnClickListener(this);
        cvManageOrder = findViewById(R.id.cvManageOrder);
        cvManageOrder.setOnClickListener(this);
        cvAddOrder = findViewById(R.id.cvAddOrder);
        cvAddOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvProfile) {
            animation = AnimationUtils.loadAnimation(CustomerDashboardActivity.this, R.anim.bounce);
            cvProfile.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(CustomerDashboardActivity.this, CustomerProfileActivity.class);
                    startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_LOGOUT);
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
        } else if (v.getId() == R.id.cvAddOrder) {
            animation = AnimationUtils.loadAnimation(CustomerDashboardActivity.this, R.anim.bounce);
            cvAddOrder.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(CustomerDashboardActivity.this, CustomerAddOrderActivity.class);
                    startActivity(intent);
                }
            }, 400);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animation != null) {
            animation.cancel();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_FOR_LOGOUT) {
            Intent intent = new Intent(CustomerDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}