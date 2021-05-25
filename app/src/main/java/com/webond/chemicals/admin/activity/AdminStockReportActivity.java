package com.webond.chemicals.admin.activity;

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
import com.webond.chemicals.admin.adapter.AdminStockReportAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminStockReportPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminStockReportActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private ArrayList<String> selectTypeArrayList = new ArrayList<>();
    private Spinner spLoginType;
    private static final String SELECT_USER_TYPE = "Select User Type";
    private static final String DISTRIBUTOR = "Distributor";
    private static final String DEALER = "Dealer";
    private SpinnerSimpleAdapter spinnerAdapterUserType;
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
        setContentView(R.layout.activity_admin_stock_report);
        initView();
        spLoginType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedType = selectTypeArrayList.get(spLoginType.getSelectedItemPosition());
                    if (selectedType.equals("Distributor")) {
                        loginType = "2";
                        llAdminDynamicReportMainRow.removeAllViewsInLayout();
                        getStockReportList(loginType);
                    } else if (selectedType.equals("Dealer")) {
                        loginType = "3";
                        llAdminDynamicReportMainRow.removeAllViewsInLayout();
                        getStockReportList(loginType);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        imgBack = findViewById(R.id.imgBack);
        svAdminReport = findViewById(R.id.svAdminReport);
//        rvAdminPointReport = findViewById(R.id.rvAdminPointReport);
        llAdminDynamicReportMainRow = findViewById(R.id.llAdminDynamicReportMainRow);
        imgBack.setOnClickListener(this);
        svAdminReport = findViewById(R.id.svAdminReport);
        spLoginType = findViewById(R.id.spLoginType);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Stock Report");
        selectTypeArrayList.add(SELECT_USER_TYPE);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
        selectTypeArrayList.add(DISTRIBUTOR);
        userTypeHashMap.put(DISTRIBUTOR, "2");
        selectTypeArrayList.add(DEALER);
        userTypeHashMap.put(DEALER, "3");
        extFabStockRepoert = findViewById(R.id.extFabStockRepoert);
        extFabStockRepoert.setVisibility(View.GONE);
        extFabStockRepoert.setOnClickListener(this::onClick);
        spinnerAdapterUserType = new SpinnerSimpleAdapter(AdminStockReportActivity.this, selectTypeArrayList);
        spLoginType.setAdapter(spinnerAdapterUserType);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.extFabStockRepoert) {
            String fileExtension = reportUrl.substring(reportUrl.lastIndexOf("."));
            new DownloadPdfFromUrl(AdminStockReportActivity.this, reportUrl, fileExtension, "Stock Reports");
        }else if(v instanceof LinearLayout){
            AdminStockReportPojo adminStockReportPojo = (AdminStockReportPojo) v.getTag();
            Intent stockReportDateIntent = new Intent(AdminStockReportActivity.this, AdminStockReportDetailActivity.class);
            stockReportDateIntent.putExtra("detail", (Serializable) adminStockReportPojo);
            stockReportDateIntent.putExtra("loginType", loginType);
            startActivity(stockReportDateIntent);
        }
    }

    private void getStockReportList(String loginType) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        svAdminReport.setVisibility(View.GONE);
        ApiImplementer.getStockReport(loginType, new Callback<ArrayList<AdminStockReportPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<AdminStockReportPojo>> call, Response<ArrayList<AdminStockReportPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getReportPDFLink())) {
                                reportUrl = response.body().get(0).getReportPDFLink().trim();
                                extFabStockRepoert.setVisibility(View.VISIBLE);
                            } else {
                                extFabStockRepoert.setVisibility(View.GONE);
                            }
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            svAdminReport.setVisibility(View.VISIBLE);
                            generateDynamicStockReport(response.body());
//                            rvAdminPointReport.setAdapter(new AdminStockReportAdapter(AdminStockReportActivity.this, response.body(), loginType));
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
            public void onFailure(Call<ArrayList<AdminStockReportPojo>> call, Throwable t) {
                String url = call.request().url().toString();
                llLoading.setVisibility(View.GONE);
                extFabStockRepoert.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                svAdminReport.setVisibility(View.GONE);
            }
        });
    }

    private void generateDynamicStockReport(ArrayList<AdminStockReportPojo> adminStockReportPojoArrayList) {

        for (int i = 0; i <= adminStockReportPojoArrayList.size(); i++) {
            LinearLayout.LayoutParams layoutParamsForll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout llDynamicAdminStockReport = new LinearLayout(AdminStockReportActivity.this);
            llDynamicAdminStockReport.setOnClickListener(this);
            llDynamicAdminStockReport.setTag(adminStockReportPojoArrayList.get(i));

            llDynamicAdminStockReport.setOrientation(LinearLayout.HORIZONTAL);
            llDynamicAdminStockReport.setLayoutParams(layoutParamsForll);
            llDynamicAdminStockReport.addView(createDynamicTextView(160,adminStockReportPojoArrayList.get(i).getCDName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(160,adminStockReportPojoArrayList.get(i).getProductName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getTotalQty()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getSaleQty()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminStockReportPojoArrayList.get(i).getAvailableQty()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(140,adminStockReportPojoArrayList.get(i).getReportDate()+""));

            llAdminDynamicReportMainRow.addView(llDynamicAdminStockReport);
        }

    }

    private TextViewMediumFont createDynamicTextView(int txtBoxWidth, String txtBoxValue) {
        LinearLayout.LayoutParams txtBoxLayoutParam = new LinearLayout.LayoutParams(
                CommonUtil.convertDpToPxe(AdminStockReportActivity.this,txtBoxWidth), LinearLayout.LayoutParams.MATCH_PARENT);
        TextViewMediumFont textViewMediumFont = new TextViewMediumFont(AdminStockReportActivity.this);
        textViewMediumFont.setLayoutParams(txtBoxLayoutParam);
        textViewMediumFont.setTextColor(getResources().getColor(R.color.black));
//        textViewMediumFont.setTextSize(15);
        textViewMediumFont.setPadding(4, 4, 4, 4);
        textViewMediumFont.setGravity(Gravity.CENTER_VERTICAL);
        textViewMediumFont.setBackground(ContextCompat.getDrawable(AdminStockReportActivity.this,
                R.drawable.admin_report_corner));
        textViewMediumFont.setText(txtBoxValue);
        return textViewMediumFont;
    }
}