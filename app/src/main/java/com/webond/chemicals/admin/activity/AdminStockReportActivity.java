package com.webond.chemicals.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
    private RecyclerView rvAdminPointReport;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private HashMap<String, String> userTypeHashMap = new HashMap<>();
    private String loginType ="";
    private ExtendedFloatingActionButton extFabStockRepoert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_stock_report);
        initView();
        spLoginType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    String selectedType = selectTypeArrayList.get(spLoginType.getSelectedItemPosition());
                    if (selectedType.equals("Distributor")){
                        loginType = "2";
                        getStockReportList(loginType);
                    }else if (selectedType.equals("Dealer")){
                         loginType = "3";
                        getStockReportList(loginType);
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
        rvAdminPointReport =findViewById(R.id.rvAdminPointReport);
        imgBack.setOnClickListener(this);
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

        }else if (v.getId() == R.id.extFabStockRepoert){
            if (adminStockReportActivities != null && adminStockReportActivities.size() > 0){
                String fileUrl = adminStockReportActivities.get(0).getReportPDFLink();
                String fileExtension = fileUrl.substring(fileUrl.lastIndexOf("."));
                new DownloadPdfFromUrl(AdminStockReportActivity.this,fileUrl,fileExtension,"Stock Reports");
            }
        }

    }

    private ArrayList<AdminStockReportPojo> adminStockReportActivities;
    private void getStockReportList(String loginType) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvAdminPointReport.setVisibility(View.GONE);
        ApiImplementer.getStockReport(loginType,  new Callback<ArrayList<AdminStockReportPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<AdminStockReportPojo>> call, Response<ArrayList<AdminStockReportPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                       adminStockReportActivities = response.body();


                       // Toast.makeText(AdminStockReportActivity.this,"done",Toast.LENGTH_SHORT).show();
                        if (response.body().size() > 0) {

                            if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getReportPDFLink())){
                                extFabStockRepoert.setVisibility(View.VISIBLE);
                            }else{
                                extFabStockRepoert.setVisibility(View.GONE);
                            }
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvAdminPointReport.setVisibility(View.VISIBLE);
                            rvAdminPointReport.setAdapter(new AdminStockReportAdapter(AdminStockReportActivity.this, response.body(),loginType));
                        } else {
                            extFabStockRepoert.setVisibility(View.GONE);
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvAdminPointReport.setVisibility(View.GONE);
                        }
                    } else {
                        extFabStockRepoert.setVisibility(View.GONE);
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvAdminPointReport.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AdminStockReportPojo>> call, Throwable t) {
                String url =  call.request().url().toString();
                llLoading.setVisibility(View.GONE);
                extFabStockRepoert.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvAdminPointReport.setVisibility(View.GONE);
            }
        });
    }
}