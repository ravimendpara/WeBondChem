package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminRedeemListReportPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminRedeemListReportActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private ArrayList<String> selectTypeArrayList = new ArrayList<>();
    private ArrayList<String> selectFilterTypeArrayList = new ArrayList<>();
    private HashMap<String, String> filterTypeHashMap = new HashMap<>();
    private Spinner spLoginType,spFilterType;
    private static final String SELECT_USER_TYPE = "Select User Type";
    private static final String SELECT_FILTER_TYPE = "Select Filter Type";
    private static final String FILTER_TYPE_PENDING = "Pending";
    private static final String FILTER_TYPE_APPROVE = "Approve";
    private static final String FILTER_TYPE_REJECT = "Reject";
    private static final String FILTER_TYPE_ALL = "All";
    private static final String DISTRIBUTOR = "Distributor";
    private static final String DEALER = "Dealer";
    private static final String CUSTOMER = "Applicant";
    private SpinnerSimpleAdapter spinnerAdapterUserType;
    private SpinnerSimpleAdapter spinnerAdapterFilterType;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private HashMap<String, String> userTypeHashMap = new HashMap<>();
    private ExtendedFloatingActionButton extFabRedeemListReport;
    private String reportUrl = "";
    private ScrollView svAdminReport;
    private LinearLayout llAdminDynamicReportMainRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_redeem_list_report);
        initView();
        spLoginType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && spFilterType.getSelectedItemPosition() > 0) {
                    llAdminDynamicReportMainRow.removeAllViewsInLayout();
                    String loginType = getSelectedLoginType(position);
                    String filterType = getSelectedFilterType(spFilterType.getSelectedItemPosition());
                    getRedeemListReportApiCall(loginType,filterType);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spFilterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0 && spLoginType.getSelectedItemPosition() > 0) {
                    llAdminDynamicReportMainRow.removeAllViewsInLayout();
                    String loginType = getSelectedLoginType(spLoginType.getSelectedItemPosition());
                    String filterType = getSelectedFilterType(position);
                    getRedeemListReportApiCall(loginType,filterType);
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
        llAdminDynamicReportMainRow = findViewById(R.id.llAdminDynamicReportMainRow);
        imgBack.setOnClickListener(this);
        svAdminReport = findViewById(R.id.svAdminReport);
        spLoginType = findViewById(R.id.spLoginType);
        spFilterType = findViewById(R.id.spFilterType);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Redeem List Report");
        selectTypeArrayList.add(SELECT_USER_TYPE);
        selectFilterTypeArrayList.add(SELECT_FILTER_TYPE);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
        selectTypeArrayList.add(DISTRIBUTOR);
        userTypeHashMap.put(DISTRIBUTOR, "2");
        selectTypeArrayList.add(DEALER);
        userTypeHashMap.put(DEALER, "3");
        selectTypeArrayList.add(CUSTOMER);
        userTypeHashMap.put(CUSTOMER, "4");

        selectFilterTypeArrayList.add(FILTER_TYPE_PENDING);
        filterTypeHashMap.put(FILTER_TYPE_PENDING, "0");
        selectFilterTypeArrayList.add(FILTER_TYPE_APPROVE);
        filterTypeHashMap.put(FILTER_TYPE_APPROVE, "1");
        selectFilterTypeArrayList.add(FILTER_TYPE_REJECT);
        filterTypeHashMap.put(FILTER_TYPE_REJECT, "2");
        selectFilterTypeArrayList.add(FILTER_TYPE_ALL);
        filterTypeHashMap.put(FILTER_TYPE_ALL, "3");

        extFabRedeemListReport = findViewById(R.id.extFabRedeemListReport);
        extFabRedeemListReport.setVisibility(View.GONE);
        extFabRedeemListReport.setOnClickListener(this);
        spinnerAdapterUserType = new SpinnerSimpleAdapter(AdminRedeemListReportActivity.this, selectTypeArrayList);
        spLoginType.setAdapter(spinnerAdapterUserType);
        spinnerAdapterFilterType = new SpinnerSimpleAdapter(AdminRedeemListReportActivity.this,selectFilterTypeArrayList);
        spFilterType.setAdapter(spinnerAdapterFilterType);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (v.getId() == R.id.extFabRedeemListReport) {
            String fileExtension = reportUrl.substring(reportUrl.lastIndexOf("."));
            new DownloadPdfFromUrl(AdminRedeemListReportActivity.this, reportUrl, fileExtension, "Stock Reports");
        }
    }

    private void getRedeemListReportApiCall(String loginType,String filterType) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        svAdminReport.setVisibility(View.GONE);
        ApiImplementer.getRedeemListReportApiImplementer(loginType,filterType, new Callback<ArrayList<AdminRedeemListReportPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<AdminRedeemListReportPojo>> call, Response<ArrayList<AdminRedeemListReportPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getReportPdfLink())) {
                                reportUrl = response.body().get(0).getReportPdfLink().trim();
                                extFabRedeemListReport.setVisibility(View.VISIBLE);
                            } else {
                                extFabRedeemListReport.setVisibility(View.GONE);
                            }
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            svAdminReport.setVisibility(View.VISIBLE);
                            generateDynamicRedeemListReport(response.body());
                        } else {
                            extFabRedeemListReport.setVisibility(View.GONE);
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            svAdminReport.setVisibility(View.GONE);
                        }
                    } else {
                        extFabRedeemListReport.setVisibility(View.GONE);
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        svAdminReport.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AdminRedeemListReportPojo>> call, Throwable t) {
                String url = call.request().url().toString();
                llLoading.setVisibility(View.GONE);
                extFabRedeemListReport.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                svAdminReport.setVisibility(View.GONE);
            }
        });
    }

    private void generateDynamicRedeemListReport(ArrayList<AdminRedeemListReportPojo> AdminRedeemListReportPojoArrayList) {

        for (int i = 0; i <= AdminRedeemListReportPojoArrayList.size(); i++) {
            LinearLayout.LayoutParams layoutParamsForll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout llDynamicAdminStockReport = new LinearLayout(AdminRedeemListReportActivity.this);
            llDynamicAdminStockReport.setOnClickListener(this);
            llDynamicAdminStockReport.setTag(AdminRedeemListReportPojoArrayList.get(i));

            llDynamicAdminStockReport.setOrientation(LinearLayout.HORIZONTAL);
            llDynamicAdminStockReport.setLayoutParams(layoutParamsForll);
            llDynamicAdminStockReport.addView(createDynamicTextView(180,AdminRedeemListReportPojoArrayList.get(i).getPethiName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(160,AdminRedeemListReportPojoArrayList.get(i).getCdName()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(160,AdminRedeemListReportPojoArrayList.get(i).getSchemeProductName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,AdminRedeemListReportPojoArrayList.get(i).getCdCode()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,AdminRedeemListReportPojoArrayList.get(i).getQty()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,AdminRedeemListReportPojoArrayList.get(i).getRedemPoint()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,AdminRedeemListReportPojoArrayList.get(i).getRedemDate()+""));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,AdminRedeemListReportPojoArrayList.get(i).getRedemStatus()+""));

            llAdminDynamicReportMainRow.addView(llDynamicAdminStockReport);
        }

    }

    private TextViewMediumFont createDynamicTextView(int txtBoxWidth, String txtBoxValue) {
        LinearLayout.LayoutParams txtBoxLayoutParam = new LinearLayout.LayoutParams(
                CommonUtil.convertDpToPxe(AdminRedeemListReportActivity.this,txtBoxWidth), LinearLayout.LayoutParams.MATCH_PARENT);
        TextViewMediumFont textViewMediumFont = new TextViewMediumFont(AdminRedeemListReportActivity.this);
        textViewMediumFont.setLayoutParams(txtBoxLayoutParam);
        textViewMediumFont.setTextColor(getResources().getColor(R.color.black));
//        textViewMediumFont.setTextSize(15);
        textViewMediumFont.setPadding(4, 4, 4, 4);
        textViewMediumFont.setGravity(Gravity.CENTER_VERTICAL);
        textViewMediumFont.setBackground(ContextCompat.getDrawable(AdminRedeemListReportActivity.this,
                R.drawable.admin_report_corner));
        textViewMediumFont.setText(txtBoxValue);
        return textViewMediumFont;
    }

    private String getSelectedLoginType(int selectedLoginTypePosition){
        String selectedLoginType = "";
        if(selectTypeArrayList.get(selectedLoginTypePosition).equalsIgnoreCase(DISTRIBUTOR)){
            selectedLoginType = "2";
        }else if(selectTypeArrayList.get(selectedLoginTypePosition).equalsIgnoreCase(DEALER)){
            selectedLoginType = "3";
        }else if(selectTypeArrayList.get(selectedLoginTypePosition).equalsIgnoreCase(CUSTOMER)){
            selectedLoginType = "4";
        }
        return selectedLoginType;
    }

    private String getSelectedFilterType(int selectedFilterTypePosition){
        String selectedFilterType = "";
        if(selectFilterTypeArrayList.get(selectedFilterTypePosition).equalsIgnoreCase(FILTER_TYPE_PENDING)){
            selectedFilterType = "0";
        }else if(selectFilterTypeArrayList.get(selectedFilterTypePosition).equalsIgnoreCase(FILTER_TYPE_APPROVE)){
            selectedFilterType = "1";
        }else if(selectFilterTypeArrayList.get(selectedFilterTypePosition).equalsIgnoreCase(FILTER_TYPE_REJECT)){
            selectedFilterType = "2";
        }else if(selectFilterTypeArrayList.get(selectedFilterTypePosition).equalsIgnoreCase(FILTER_TYPE_ALL)){
            selectedFilterType = "3";
        }
        return selectedFilterType;
    }


}