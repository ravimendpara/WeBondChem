package com.webond.chemicals.distributor.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewBoldFont;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.customer.activity.CustomerDashboardActivity;
import com.webond.chemicals.dealer.activity.DealerRedeemProductActivity;
import com.webond.chemicals.dealer.adapter.DealerRedeemProductAdapter;
import com.webond.chemicals.distributor.adapter.DistributorRedeemProductAdapter;
import com.webond.chemicals.pojo.GetRedemProductListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class DistributorRedeemProductActivity extends AppCompatActivity implements View.OnClickListener, DistributorRedeemProductAdapter.IDistributorOnRedeemPoint {

    public static String DISTRIBUTOR_TOTAL_POINTS="distributor_total_points";
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDistributorRedeemProductList;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private String totalPoint = "0";
    private TextViewBoldFont tvTotalPoints;
    private FrameLayout fmRedeem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_redeem_product);
        initView();
        getRedeemProductListApiCall();
        if(getIntent().hasExtra(IntentConstants.TOTAL_POINT)){
            totalPoint = getIntent().getStringExtra(IntentConstants.TOTAL_POINT);
            tvTotalPoints.setText(totalPoint);
        }
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(DistributorRedeemProductActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Redeem Product");
        rvDistributorRedeemProductList = findViewById(R.id.rvDistributorRedeemProductList);
        rvDistributorRedeemProductList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);
        fmRedeem = findViewById(R.id.fmRedeem);
    }

    private void getRedeemProductListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        fmRedeem.setVisibility(View.GONE);
//        rvCustomerRedeemProductList.setVisibility(View.GONE);
        ApiImplementer.GetRedemProductList("2", mySharedPreferences.getDistributorId(), new Callback<ArrayList<GetRedemProductListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetRedemProductListPojo>> call, Response<ArrayList<GetRedemProductListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getTotalPoint())) {
                                totalPoint = response.body().get(0).getTotalPoint()+"";
                                tvTotalPoints.setText(totalPoint);
                            }
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            fmRedeem.setVisibility(View.VISIBLE);
//                            rvCustomerRedeemProductList.setVisibility(View.VISIBLE);
                            rvDistributorRedeemProductList.setAdapter(new DistributorRedeemProductAdapter(DistributorRedeemProductActivity.this, response.body()));
                        } else {
//                            totalPoint = "0";
                            llLoading.setVisibility(View.GONE);
                            fmRedeem.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
//                            rvCustomerRedeemProductList.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        fmRedeem.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
//                        rvCustomerRedeemProductList.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetRedemProductListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                fmRedeem.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
//                rvCustomerRedeemProductList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == IntentConstants.REQUEST_CODE_REDEEM_SUCCESSFULLY){
            getRedeemProductListApiCall();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DistributorRedeemProductActivity.this, DistributorDashboardActivity.class);
        intent.putExtra(DISTRIBUTOR_TOTAL_POINTS, totalPoint);
        setResult(IntentConstants.REQUEST_CODE_DISTRIBUTOR_REDEEM_REQ, intent);
        super.onBackPressed();
    }

    @Override
    public void onRedeemPoints(String redeemPoints) {
        try {
            int remainingPoints =  Integer.parseInt(totalPoint) - Integer.parseInt(redeemPoints);
            totalPoint = String.valueOf(remainingPoints);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}