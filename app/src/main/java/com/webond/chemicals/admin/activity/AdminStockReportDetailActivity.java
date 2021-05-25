package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.adapter.AdminStockItemDetailAdapter;
import com.webond.chemicals.admin.adapter.AdminStockReportAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminStockReportDetailPojo;
import com.webond.chemicals.pojo.AdminStockReportPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminStockReportDetailActivity extends AppCompatActivity implements View.OnClickListener {
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
//    private RecyclerView rvStockDetails;
    private ScrollView svAdminDetailReport;
    private LinearLayout llAdminDetailDynamicReportMainRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_stock_report_detail);
        initView();
        Intent i = getIntent();
        AdminStockReportPojo adminStockReportPojo = (AdminStockReportPojo) i.getSerializableExtra("detail");
        String loginType = i.getStringExtra("loginType");
        getStockReportDetail(loginType, adminStockReportPojo.getId() + "", adminStockReportPojo.getProductId() + "");

    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        cvMain = findViewById(R.id.cvMain);
        tvCode = findViewById(R.id.tvCode);
        tvName = findViewById(R.id.tvName);
        tvProductName = findViewById(R.id.tvProductName);
        tvOrderNo = findViewById(R.id.tvOrderNo);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvQty = findViewById(R.id.tvQty);
        tvPoints = findViewById(R.id.tvPoints);
//        rvStockDetails = findViewById(R.id.rvStockDetails);
        llAdminDetailDynamicReportMainRow = findViewById(R.id.llAdminDetailDynamicReportMainRow);
        svAdminDetailReport = findViewById(R.id.svAdminDetailReport);
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

    private void getStockReportDetail(String loginType, String loginId, String productId) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        svAdminDetailReport.setVisibility(View.GONE);
        ApiImplementer.getStockReportDataDetail(loginType, loginId, productId, new Callback<ArrayList<AdminStockReportDetailPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<AdminStockReportDetailPojo>> call, Response<ArrayList<AdminStockReportDetailPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        //  Toast.makeText(AdminStockReportDetailActivity.this,"done",Toast.LENGTH_SHORT).show();
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            svAdminDetailReport.setVisibility(View.VISIBLE);
                            generateDynamicStockReport(response.body());
//                            rvStockDetails.setAdapter(new AdminStockItemDetailAdapter(AdminStockReportDetailActivity.this, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            svAdminDetailReport.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        svAdminDetailReport.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AdminStockReportDetailPojo>> call, Throwable t) {
                String url = call.request().url().toString();
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                svAdminDetailReport.setVisibility(View.GONE);
            }
        });
    }

    private void generateDynamicStockReport(ArrayList<AdminStockReportDetailPojo> adminStockReportPojoArrayList) {

        for (int i = 0; i <= adminStockReportPojoArrayList.size(); i++) {
            LinearLayout.LayoutParams layoutParamsForll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout llDynamicAdminStockReport = new LinearLayout(AdminStockReportDetailActivity.this);
            llDynamicAdminStockReport.setTag(adminStockReportPojoArrayList.get(i));

            llDynamicAdminStockReport.setOrientation(LinearLayout.HORIZONTAL);
            llDynamicAdminStockReport.setLayoutParams(layoutParamsForll);
            llDynamicAdminStockReport.addView(createDynamicTextView(160,adminStockReportPojoArrayList.get(i).getCdName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(160,adminStockReportPojoArrayList.get(i).getProductName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getProductCode()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getQty()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getPoints()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getOrderNo()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(150,adminStockReportPojoArrayList.get(i).getOrderDate()+""));

            llAdminDetailDynamicReportMainRow.addView(llDynamicAdminStockReport);
        }

    }

    private TextViewMediumFont createDynamicTextView(int txtBoxWidth, String txtBoxValue) {
        LinearLayout.LayoutParams txtBoxLayoutParam = new LinearLayout.LayoutParams(
                CommonUtil.convertDpToPxe(AdminStockReportDetailActivity.this,txtBoxWidth), LinearLayout.LayoutParams.MATCH_PARENT);
        TextViewMediumFont textViewMediumFont = new TextViewMediumFont(AdminStockReportDetailActivity.this);
        textViewMediumFont.setLayoutParams(txtBoxLayoutParam);
        textViewMediumFont.setTextColor(getResources().getColor(R.color.black));
//        textViewMediumFont.setTextSize(15);
        textViewMediumFont.setPadding(4, 4, 4, 4);
        textViewMediumFont.setGravity(Gravity.CENTER_VERTICAL);
        textViewMediumFont.setBackground(ContextCompat.getDrawable(AdminStockReportDetailActivity.this,
                R.drawable.admin_report_corner));
        textViewMediumFont.setText(txtBoxValue);
        return textViewMediumFont;
    }


}