package com.webond.chemicals.distributor.activity;

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
import com.webond.chemicals.customer.activity.CustomerDashboardActivity;
import com.webond.chemicals.customer.activity.CustomerRedeemListActivity;
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

public class DistributorDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private MaterialCardView cvProfile;
    private MaterialCardView cvManageDealer;
    private MaterialCardView cvManageCustomer;
    private MaterialCardView cvManageDealerOrder;
    private MaterialCardView cvAddOrder;
    private MaterialCardView cvMyOrders;
    private MaterialCardView cvMyRedemption;
    private MaterialCardView cvRedeemProduct;
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
        setContentView(R.layout.activity_distributor_dashboard);
        initView();
        getBannerList();
        getUserDetails();
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
        cvManageDealerOrder = findViewById(R.id.cvManageDealerOrder);
        cvManageDealerOrder.setOnClickListener(this);
        cvAddOrder = findViewById(R.id.cvAddOrder);
        cvAddOrder.setOnClickListener(this);
        cvMyOrders = findViewById(R.id.cvMyOrders);
        cvMyOrders.setOnClickListener(this);
        cvMyRedemption = findViewById(R.id.cvMyRedemption);
        cvMyRedemption.setOnClickListener(this);
        cvRedeemProduct = findViewById(R.id.cvRedeemProduct);
        cvRedeemProduct.setOnClickListener(this);

        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(this);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

        tvNameCard = findViewById(R.id.tvNameCard);
        tvCode = findViewById(R.id.tvCode);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);

       try {
           if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorPhotoPath())) {
               Glide.with(DistributorDashboardActivity.this)
                       .load(mySharedPreferences.getDealerPhotoPath())
                       .centerCrop()
                       .placeholder(R.drawable.person_img)
                       .into(imgProfile);
           }
       }catch (Exception ex){
           ex.printStackTrace();
       }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorName())) {
            tvName.setText(mySharedPreferences.getDistributorName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(mySharedPreferences.getDistributorEmail())) {
            tvEmail.setText(mySharedPreferences.getDistributorEmail() + "");
        }

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
                    startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_LOGOUT);
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
        } else if (v.getId() == R.id.cvManageDealerOrder) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvManageDealerOrder.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorManageDealerOrderActivity.class);
                    startActivity(intent);
                }
            }, 400);
        } else if (v.getId() == R.id.cvAddOrder) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvAddOrder.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorAddOrderActivity.class);
                    startActivity(intent);
                }
            }, 400);
        }else if (v.getId() == R.id.cvMyOrders) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvMyOrders.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorMyOrderActivity.class);
                    startActivity(intent);
                }
            }, 400);
        }else if (v.getId() == R.id.imgProfile){
            Intent intent = new Intent(DistributorDashboardActivity.this, DistributorProfileActivity.class);
            startActivityForResult(intent, IntentConstants.REQUEST_CODE_FOR_LOGOUT);
        }else if (v.getId() == R.id.cvMyRedemption) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvMyRedemption.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorRedeemListActivity.class);
                    intent.putExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ, tvTotalPoints.getText().toString());
                    startActivityForResult(intent,IntentConstants.REQUEST_CODE_FOR_CANCEL_REDEEM_REQ);
                }
            }, 400);
        }else if (v.getId() == R.id.cvRedeemProduct) {
            animation = AnimationUtils.loadAnimation(DistributorDashboardActivity.this, R.anim.bounce);
            cvRedeemProduct.startAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(DistributorDashboardActivity.this, DistributorRedeemProductActivity.class);
                    intent.putExtra(IntentConstants.TOTAL_POINT, tvTotalPoints.getText().toString());
                    startActivityForResult(intent,IntentConstants.REQUEST_CODE_DISTRIBUTOR_REDEEM_REQ);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_FOR_LOGOUT) {
            Intent intent = new Intent(DistributorDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else if (requestCode == IntentConstants.REQUEST_CODE_DISTRIBUTOR_REDEEM_REQ){
            if (data != null && data.hasExtra(DistributorRedeemProductActivity.DISTRIBUTOR_TOTAL_POINTS)){
                String totalPoints = data.getStringExtra(DistributorRedeemProductActivity.DISTRIBUTOR_TOTAL_POINTS);
                tvTotalPoints.setText(totalPoints);
            }
        }else if (requestCode == IntentConstants.REQUEST_CODE_FOR_CANCEL_REDEEM_REQ){
            if (data != null && data.hasExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ)){
                String totalPoints = data.getStringExtra(IntentConstants.TOTAL_POINT_AFTER_CANCEL_REQ);
                tvTotalPoints.setText(totalPoints);
            }
        }
    }

    private void getUserDetails() {
        ApiImplementer.getDashboardDetailsApiImplementer(CommonUtil.LOGIN_TYPE_DISTRIBUTOR, mySharedPreferences.getDistributorId(), new Callback<ArrayList<GetDashboardDetailsPojo>>() {
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