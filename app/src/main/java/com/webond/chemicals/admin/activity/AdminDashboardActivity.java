package com.webond.chemicals.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.common_activity.LoginActivity;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.distributor.activity.DistributorDashboardActivity;
import com.webond.chemicals.pojo.GetBannerListPojo;
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

public class AdminDashboardActivity extends AppCompatActivity implements View.OnClickListener {


    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvManageDistributor;
    private MaterialCardView cvManageDealer;
    private MaterialCardView cvManageCustomer;
    private MaterialCardView cvManageProduct;
    private MaterialCardView cvManageDistributorOrder;
    private Animation animation;
    RecyclerViewPager recyclerViewPagerStudentSideBanner;

    private CircleImageView imgProfile;
    private TextViewMediumFont tvName;
    private TextViewRegularFont tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        initView();
        getBannerList();
    }

    private void initView() {
        recyclerViewPagerStudentSideBanner = findViewById(R.id.recyclerViewPagerStudentSideBanner);
        mySharedPreferences = new MySharedPreferences(AdminDashboardActivity.this);
        cvProfile = findViewById(R.id.cvProfile);
        cvProfile.setOnClickListener(this);
        cvManageDistributor = findViewById(R.id.cvManageDistributor);
        cvManageDistributor.setOnClickListener(this);
        cvManageDealer = findViewById(R.id.cvManageDealer);
        cvManageDealer.setOnClickListener(this);
        cvManageCustomer = findViewById(R.id.cvManageCustomer);
        cvManageCustomer.setOnClickListener(this);
        cvManageProduct = findViewById(R.id.cvManageProduct);
        cvManageProduct.setOnClickListener(this);
        cvManageDistributorOrder = findViewById(R.id.cvManageDistributorOrder);
        cvManageDistributorOrder.setOnClickListener(this);

        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(this);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

//        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getAdmin())) {
//            Glide.with(AdminDashboardActivity.this)
//                    .load(mySharedPreferences.getDealerPhotoPath())
//                    .centerCrop()
//                    .placeholder(R.drawable.person_img)
//                    .into(imgProfile);
//        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getAdminCompanyName())) {
            tvName.setText(mySharedPreferences.getAdminCompanyName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getAdminCompanyEmail())) {
            tvEmail.setText(mySharedPreferences.getAdminCompanyEmail() + "");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvProfile) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvProfile.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminProfileActivity.class);
                    startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_LOGOUT);
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
        } else if (v.getId() == R.id.cvManageProduct) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvManageProduct.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminProductListActivity.class);
                    startActivity(intent);
                }
            }, 400);
        }else if (v.getId() == R.id.cvManageDistributorOrder) {
            animation = AnimationUtils.loadAnimation(AdminDashboardActivity.this, R.anim.bounce);
            cvManageDistributorOrder.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AdminDashboardActivity.this, AdminManageDistributorOrderActivity.class);
                    startActivity(intent);
                }
            }, 400);
        }else if (v.getId() == R.id.imgProfile){
            Intent intent = new Intent(AdminDashboardActivity.this, AdminProfileActivity.class);
            startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_LOGOUT);
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
            Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}




