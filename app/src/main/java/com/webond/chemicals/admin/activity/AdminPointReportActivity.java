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
import com.webond.chemicals.admin.adapter.AdminPointReportAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminPointReportPojo;
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
    private RecyclerView rvAdminPointReport;
    private LinearLayout llLoading;
    private ExtendedFloatingActionButton extFabPointRepoert;
    private LinearLayout llNoDateFound;


    private HashMap<String, String> userTypeHashMap = new HashMap<>();

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
                        getPointReportList(loginType);
                    }else if (selectedType.equals("Dealer")){
                        String loginType = "3";
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
        extFabPointRepoert = findViewById(R.id.extFabPointRepoert);
        extFabPointRepoert.setVisibility(View.GONE);
        extFabPointRepoert.setOnClickListener(this::onClick);
        rvAdminPointReport =findViewById(R.id.rvAdminPointReport);
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
            if (arrayLists != null && arrayLists.size() > 0){
                String fileUrl = arrayLists.get(0).getReportPDFLink();
                String fileExtension = fileUrl.substring(fileUrl.lastIndexOf("."));
                new DownloadPdfFromUrl(AdminPointReportActivity.this,fileUrl,fileExtension,"Point Reports");
            }

        }

    }
    ArrayList<AdminPointReportPojo> arrayLists;
    private void getPointReportList(String loginType) {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvAdminPointReport.setVisibility(View.GONE);
        ApiImplementer.getPointReport(loginType,  new Callback<ArrayList<AdminPointReportPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AdminPointReportPojo>> call, Response<ArrayList<AdminPointReportPojo>> response) {
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                arrayLists = response.body();
                                if (response.body().size() > 0) {
                                    if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getReportPDFLink())){
                                        extFabPointRepoert.setVisibility(View.VISIBLE);
                                    }else{
                                        extFabPointRepoert.setVisibility(View.GONE);
                                    }
                                    llLoading.setVisibility(View.GONE);
                                    llNoDateFound.setVisibility(View.GONE);
                                    rvAdminPointReport.setVisibility(View.VISIBLE);
                                    rvAdminPointReport.setAdapter(new AdminPointReportAdapter(AdminPointReportActivity.this, response.body()));
                                } else {
                                    extFabPointRepoert.setVisibility(View.GONE);
                                    llLoading.setVisibility(View.GONE);
                                    llNoDateFound.setVisibility(View.VISIBLE);
                                    rvAdminPointReport.setVisibility(View.GONE);
                                }
                            } else {
                                extFabPointRepoert.setVisibility(View.GONE);
                                llLoading.setVisibility(View.GONE);
                                llNoDateFound.setVisibility(View.VISIBLE);
                                rvAdminPointReport.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AdminPointReportPojo>> call, Throwable t) {
                       String url =  call.request().url().toString();
                        extFabPointRepoert.setVisibility(View.GONE);
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvAdminPointReport.setVisibility(View.GONE);
                    }
                });
    }
}