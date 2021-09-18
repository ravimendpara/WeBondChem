package com.webond.chemicals.dealer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminStockReportActivity;
import com.webond.chemicals.admin.activity.AdminStockReportDetailActivity;
import com.webond.chemicals.admin.adapter.AdminStockReportAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.distributor.activity.DistributorStockActivity;
import com.webond.chemicals.pojo.AdminStockReportPojo;
import com.webond.chemicals.pojo.StockReportByIdPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;
import com.webond.chemicals.utils.MySharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerStockActivity extends AppCompatActivity implements View.OnClickListener  {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private ArrayList<String> selectTypeArrayList = new ArrayList<>();
    private MySharedPreferences mySharedPreferences;
    //    private RecyclerView rvAdminPointReport;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private HashMap<String, String> userTypeHashMap = new HashMap<>();
    private String loginType = "";
    private ExtendedFloatingActionButton extFabStockRepoert;
    private String reportUrl = "";
    private ScrollView svAdminReport;
    private LinearLayout llAdminDynamicReportMainRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_stock);

        initView();

        getStockReportList("3", mySharedPreferences.getDealerId());

    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        svAdminReport = findViewById(R.id.svAdminReport);
        mySharedPreferences = new MySharedPreferences(DealerStockActivity.this);
        llAdminDynamicReportMainRow = findViewById(R.id.llAdminDynamicReportMainRow);
        imgBack.setOnClickListener(this);
        svAdminReport = findViewById(R.id.svAdminReport);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Stock Report");
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        }
    }

    private void getStockReportList(String loginType, String userid) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        svAdminReport.setVisibility(View.GONE);
        ApiImplementer.GetStockReportDataById(loginType, userid, new Callback<ArrayList<StockReportByIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<StockReportByIdPojo>> call, Response<ArrayList<StockReportByIdPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            svAdminReport.setVisibility(View.VISIBLE);
                            generateDynamicStockReport(response.body());
                        } else {
                            extFabStockRepoert.setVisibility(View.GONE);
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            svAdminReport.setVisibility(View.GONE);
                        }
                    } else {
                        extFabStockRepoert.setVisibility(View.GONE);
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        svAdminReport.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StockReportByIdPojo>> call, Throwable t) {
                String url = call.request().url().toString();
                llLoading.setVisibility(View.GONE);
                extFabStockRepoert.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                svAdminReport.setVisibility(View.GONE);
            }
        });
    }

    private void generateDynamicStockReport(ArrayList<StockReportByIdPojo> adminStockReportPojoArrayList) {

        for (int i = 0; i <= adminStockReportPojoArrayList.size(); i++) {
            LinearLayout.LayoutParams layoutParamsForll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout llDynamicAdminStockReport = new LinearLayout(DealerStockActivity.this);
            llDynamicAdminStockReport.setOnClickListener(this);
            llDynamicAdminStockReport.setTag(adminStockReportPojoArrayList.get(i));

            llDynamicAdminStockReport.setOrientation(LinearLayout.HORIZONTAL);
            llDynamicAdminStockReport.setLayoutParams(layoutParamsForll);
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getProductCode()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(160,adminStockReportPojoArrayList.get(i).getProductName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getTotalQty()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getSaleQty()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getAvailableQty()+""));

            llAdminDynamicReportMainRow.addView(llDynamicAdminStockReport);
        }

    }

    private TextViewMediumFont createDynamicTextView(int txtBoxWidth, String txtBoxValue) {
        LinearLayout.LayoutParams txtBoxLayoutParam = new LinearLayout.LayoutParams(
                CommonUtil.convertDpToPxe(DealerStockActivity.this,txtBoxWidth), LinearLayout.LayoutParams.MATCH_PARENT);
        TextViewMediumFont textViewMediumFont = new TextViewMediumFont(DealerStockActivity.this);
        textViewMediumFont.setLayoutParams(txtBoxLayoutParam);
        textViewMediumFont.setTextColor(getResources().getColor(R.color.black));
//        textViewMediumFont.setTextSize(15);
        textViewMediumFont.setPadding(4, 4, 4, 4);
        textViewMediumFont.setGravity(Gravity.CENTER_VERTICAL);
        textViewMediumFont.setBackground(ContextCompat.getDrawable(DealerStockActivity.this,
                R.drawable.admin_report_corner));
        textViewMediumFont.setText(txtBoxValue);
        return textViewMediumFont;
    }
}