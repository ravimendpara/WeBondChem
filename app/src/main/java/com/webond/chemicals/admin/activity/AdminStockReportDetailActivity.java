package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.adapter.AdminStockItemDetailAdapter;
import com.webond.chemicals.admin.adapter.AdminStockReportAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminStockReportDetailPojo;
import com.webond.chemicals.pojo.AdminStockReportPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminStockReportDetailActivity extends AppCompatActivity  implements View.OnClickListener{
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private LinearLayout llLoading;
    private MaterialCardView cvMain;
    private LinearLayout llNoDateFound;
    private TextViewMediumFont tvCode;
    private TextViewMediumFont tvProductName;
    private TextViewMediumFont tvOrderDate;
    private TextViewMediumFont tvOrderNo;
    private TextViewMediumFont tvQty;
    private TextViewMediumFont tvPoints;
    private TextViewMediumFont tvName;
    private RecyclerView rvStockDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_stock_report_detail);

        initView();
        Intent i = getIntent();
        AdminStockReportPojo adminStockReportPojo = (AdminStockReportPojo)i.getSerializableExtra("detail");
        String loginType = i.getStringExtra("loginType");
        getStockReportDetail(loginType,adminStockReportPojo.getId()+"",adminStockReportPojo.getProductId()+"");

    }

    private void initView(){
        imgBack = findViewById(R.id.imgBack);
        cvMain = findViewById(R.id.cvMain);
        tvCode = findViewById(R.id.tvCode);
        tvName = findViewById(R.id.tvName);
        tvProductName = findViewById(R.id.tvProductName);
        tvOrderNo = findViewById(R.id.tvOrderNo);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvQty = findViewById(R.id.tvQty);
        tvPoints = findViewById(R.id.tvPoints);
        rvStockDetails = findViewById(R.id.rvStockDetails);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
        imgBack.setOnClickListener(this::onClick);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Stock Report Detail");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();

        }

    }

    private void getStockReportDetail(String loginType,String loginId,String productId) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvStockDetails.setVisibility(View.GONE);
        ApiImplementer.getStockReportDataDetail(loginType, loginId,productId, new Callback<ArrayList<AdminStockReportDetailPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<AdminStockReportDetailPojo>> call, Response<ArrayList<AdminStockReportDetailPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                      //  Toast.makeText(AdminStockReportDetailActivity.this,"done",Toast.LENGTH_SHORT).show();
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                           rvStockDetails.setVisibility(View.VISIBLE);
                            rvStockDetails.setAdapter(new AdminStockItemDetailAdapter(AdminStockReportDetailActivity.this,response.body()));
                          /*  cvMain.setVisibility(View.VISIBLE);
                            tvCode.setText(response.body().get(0).getCdCode());
                            tvOrderNo.setText(response.body().get(0).getOrderNo());
                            tvName.setText(response.body().get(0).getCdName());
                            tvProductName.setText(response.body().get(0).getProductName());
                            tvOrderDate.setText(response.body().get(0).getOrderDate());
                            tvOrderNo.setText(response.body().get(0).getOrderNo());
                            tvQty.setText(response.body().get(0).getQty()+"");
                            tvPoints.setText(response.body().get(0).getPoints()+"");*/



                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvStockDetails.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvStockDetails.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AdminStockReportDetailPojo>> call, Throwable t) {
                String url =  call.request().url().toString();
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvStockDetails.setVisibility(View.GONE);
            }
        });
    }
}