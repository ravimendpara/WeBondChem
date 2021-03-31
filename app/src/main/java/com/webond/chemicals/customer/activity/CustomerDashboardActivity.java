package com.webond.chemicals.customer.activity;

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
import com.webond.chemicals.pojo.GetBannerListPojo;
import com.webond.chemicals.pojo.GetDashboardDetailsPojo;
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

public class CustomerDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvMyOrders;
    private MaterialCardView cvAddOrder;
    private Animation animation;
    RecyclerViewPager recyclerViewPagerStudentSideBanner;

    private CircleImageView imgProfile;
    private TextViewMediumFont tvName;
    private TextViewRegularFont tvEmail;

    TextViewMediumFont tvNameCard;
    TextViewMediumFont tvCode;
    TextViewMediumFont tvTotalPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        initView();
        getBannerList();
        getUserDetails();
    }

    private void initView() {
        recyclerViewPagerStudentSideBanner = findViewById(R.id.recyclerViewPagerStudentSideBanner);
        mySharedPreferences = new MySharedPreferences(CustomerDashboardActivity.this);
        cvProfile = findViewById(R.id.cvProfile);
        cvProfile.setOnClickListener(this);
        cvMyOrders = findViewById(R.id.cvMyOrders);
        cvMyOrders.setOnClickListener(this);
        cvAddOrder = findViewById(R.id.cvAddOrder);
        cvAddOrder.setOnClickListener(this);
        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(this);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

        tvNameCard = findViewById(R.id.tvNameCard);
        tvCode = findViewById(R.id.tvCode);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);


        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getCustomerPhotoPath())) {
            Glide.with(CustomerDashboardActivity.this)
                    .load(mySharedPreferences.getCustomerPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(imgProfile);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getCustomerName())) {
            tvName.setText(mySharedPreferences.getCustomerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getCustomerEmail())) {
            tvEmail.setText(mySharedPreferences.getCustomerEmail() + "");
        }

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
        } else if (v.getId() == R.id.cvMyOrders) {
            animation = AnimationUtils.loadAnimation(CustomerDashboardActivity.this, R.anim.bounce);
            cvMyOrders.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(CustomerDashboardActivity.this, CustomerMyOrdersActivity.class);
                    startActivity(intent);
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
        } else if (v.getId() == R.id.imgProfile) {
            Intent intent = new Intent(CustomerDashboardActivity.this, CustomerProfileActivity.class);
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
            Intent intent = new Intent(CustomerDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void getUserDetails() {
        ApiImplementer.getDashboardDetailsApiImplementer(CommonUtil.LOGIN_TYPE_CUSTOMER, mySharedPreferences.getCustomerId(), new Callback<ArrayList<GetDashboardDetailsPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDashboardDetailsPojo>> call, Response<ArrayList<GetDashboardDetailsPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null &&
                            response.body().size() > 0) {
                        if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getName())) {
                            tvNameCard.setText(response.body().get(0).getName() + "");
                        }

                        if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getCode())) {
                            tvCode.setText(response.body().get(0).getCode() + "");
                        }

                        if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getTotalPoint())) {
                            tvTotalPoints.setText(response.body().get(0).getTotalPoint() + "");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDashboardDetailsPojo>> call, Throwable t) {

            }
        });
    }

}