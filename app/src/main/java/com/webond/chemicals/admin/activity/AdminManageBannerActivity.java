package com.webond.chemicals.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.adapter.AdminBannerListAdapter;
import com.webond.chemicals.admin.adapter.AdminProductListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.common_activity.LoginActivity;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.distributor.activity.DistributorDashboardActivity;
import com.webond.chemicals.pojo.GetBannerListPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import net.seifhadjhassen.recyclerviewpager.PagerModel;
import net.seifhadjhassen.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageBannerActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvBannerList;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private ExtendedFloatingActionButton btnAddBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_banner);
        initView();
        getApproveCustomerListApiCall();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(AdminManageBannerActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Banner List");
        rvBannerList = findViewById(R.id.rvBannerList);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
        btnAddBanner = findViewById(R.id.btnAddBanner);
        btnAddBanner.setOnClickListener(this);
    }

    private void getApproveCustomerListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvBannerList.setVisibility(View.GONE);
        ApiImplementer.getBannerListApiImplementer(new Callback<ArrayList<GetBannerListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetBannerListPojo>> call, Response<ArrayList<GetBannerListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvBannerList.setVisibility(View.VISIBLE);
                            rvBannerList.setAdapter(new AdminBannerListAdapter(AdminManageBannerActivity.this, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvBannerList.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvBannerList.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetBannerListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvBannerList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.btnAddBanner) {
            Intent intent = new Intent(AdminManageBannerActivity.this, AdminAddBannerActivity.class);
            startActivityForResult(intent, IntentConstants.REQUEST_CODE_ADD_PRODUCT);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_ADD_PRODUCT) {
            getApproveCustomerListApiCall();
        } else if (resultCode == RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_UPDATE_PRODUCT) {
            getApproveCustomerListApiCall();
        }
    }
}