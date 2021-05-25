package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import com.webond.chemicals.admin.adapter.AdminPointReportAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminPointReportPojo;
import com.webond.chemicals.pojo.AdminStockReportPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPointReportActivity extends AppCompatActivity  implements View.OnClickListener{
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
    private ExtendedFloatingActionButton extFabPointRepoert;
    private LinearLayout llNoDateFound;
    private HashMap<String, String> userTypeHashMap = new HashMap<>();
    private ScrollView svAdminPointReport;
    private LinearLayout llAdminPointDynamicReportMainRow;
    private String pointReportUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_point_report);
        initView();

        spLoginType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    String selectedType = selectTypeArrayList.get(spLoginType.getSelectedItemPosition());
                    if (selectedType.equals("Distributor")){
                        String loginType = "2";
                        llAdminPointDynamicReportMainRow.removeAllViewsInLayout();
                        getPointReportList(loginType);
                    }else if (selectedType.equals("Dealer")){
                        String loginType = "3";
                        llAdminPointDynamicReportMainRow.removeAllViewsInLayout();
                        getPointReportList(loginType);
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView(){
        imgBack = findViewById(R.id.imgBack);
        svAdminPointReport = findViewById(R.id.svAdminPointReport);
        llAdminPointDynamicReportMainRow = findViewById(R.id.llAdminPointDynamicReportMainRow);
        extFabPointRepoert = findViewById(R.id.extFabPointRepoert);
        extFabPointRepoert.setVisibility(View.GONE);
        extFabPointRepoert.setOnClickListener(this::onClick);
//        rvAdminPointReport =findViewById(R.id.rvAdminPointReport);
        imgBack.setOnClickListener(this);
        spLoginType = findViewById(R.id.spLoginType);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Point Report");
        selectTypeArrayList.add(SELECT_USER_TYPE);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
        selectTypeArrayList.add(DISTRIBUTOR);
        userTypeHashMap.put(DISTRIBUTOR, "2");
        selectTypeArrayList.add(DEALER);
        userTypeHashMap.put(DEALER, "3");

        spinnerAdapterUserType = new SpinnerSimpleAdapter(AdminPointReportActivity.this, selectTypeArrayList);
        spLoginType.setAdapter(spinnerAdapterUserType);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBack) {
            onBackPressed();

        }else if (v.getId() == R.id.extFabPointRepoert){
//            if (arrayLists != null && arrayLists.size() > 0){
//                String fileUrl = pointReportUrl.get(0).getReportPDFLink();
                String fileExtension = pointReportUrl.substring(pointReportUrl.lastIndexOf("."));
                new DownloadPdfFromUrl(AdminPointReportActivity.this,pointReportUrl,fileExtension,"Point Reports");
//            }
        }

    }
    private void getPointReportList(String loginType) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        svAdminPointReport.setVisibility(View.GONE);
        ApiImplementer.getPointReport(loginType,  new Callback<ArrayList<AdminPointReportPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AdminPointReportPojo>> call, Response<ArrayList<AdminPointReportPojo>> response) {
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                if (response.body().size() > 0) {
                                    if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getReportPDFLink())){
                                        pointReportUrl = response.body().get(0).getReportPDFLink().trim();
                                        extFabPointRepoert.setVisibility(View.VISIBLE);
                                    }else{
                                        extFabPointRepoert.setVisibility(View.GONE);
                                    }
                                    llLoading.setVisibility(View.GONE);
                                    llNoDateFound.setVisibility(View.GONE);
                                    svAdminPointReport.setVisibility(View.VISIBLE);
                                    generateDynamicStockReport(response.body());
//                                    rvAdminPointReport.setAdapter(new AdminPointReportAdapter(AdminPointReportActivity.this, response.body()));
                                } else {
                                    extFabPointRepoert.setVisibility(View.GONE);
                                    llLoading.setVisibility(View.GONE);
                                    llNoDateFound.setVisibility(View.VISIBLE);
                                    svAdminPointReport.setVisibility(View.GONE);
                                }
                            } else {
                                extFabPointRepoert.setVisibility(View.GONE);
                                llLoading.setVisibility(View.GONE);
                                llNoDateFound.setVisibility(View.VISIBLE);
                                svAdminPointReport.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AdminPointReportPojo>> call, Throwable t) {
//                       String url =  call.request().url().toString();
                        extFabPointRepoert.setVisibility(View.GONE);
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        svAdminPointReport.setVisibility(View.GONE);
                    }
                });
    }

    private void generateDynamicStockReport(ArrayList<AdminPointReportPojo> adminPointReportPojoList) {

        for (int i = 0; i <= adminPointReportPojoList.size(); i++) {
            LinearLayout.LayoutParams layoutParamsForll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout llDynamicAdminStockReport = new LinearLayout(AdminPointReportActivity.this);

            llDynamicAdminStockReport.setOrientation(LinearLayout.HORIZONTAL);
            llDynamicAdminStockReport.setLayoutParams(layoutParamsForll);
            llDynamicAdminStockReport.addView(createDynamicTextView(160,adminPointReportPojoList.get(i).getCdName()));
            llDynamicAdminStockReport.addView(createDynamicTextView(90,adminPointReportPojoList.get(i).getCdCode()));
            llDynamicAdminStockReport.addView(createDynamicTextView(130,adminPointReportPojoList.get(i).getTotalPoint()+""));

            llAdminPointDynamicReportMainRow.addView(llDynamicAdminStockReport);
        }

    }

    private TextViewMediumFont createDynamicTextView(int txtBoxWidth, String txtBoxValue) {
        LinearLayout.LayoutParams txtBoxLayoutParam = new LinearLayout.LayoutParams(
                CommonUtil.convertDpToPxe(AdminPointReportActivity.this,txtBoxWidth), LinearLayout.LayoutParams.MATCH_PARENT);
        TextViewMediumFont textViewMediumFont = new TextViewMediumFont(AdminPointReportActivity.this);
        textViewMediumFont.setLayoutParams(txtBoxLayoutParam);
        textViewMediumFont.setTextColor(getResources().getColor(R.color.black));
//        textViewMediumFont.setTextSize(15);
        textViewMediumFont.setPadding(4, 4, 4, 4);
        textViewMediumFont.setGravity(Gravity.CENTER_VERTICAL);
        textViewMediumFont.setBackground(ContextCompat.getDrawable(AdminPointReportActivity.this,
                R.drawable.admin_report_corner));
        textViewMediumFont.setText(txtBoxValue);
        return textViewMediumFont;
    }

}