package com.webond.chemicals.distributor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminAddProductActivity;
import com.webond.chemicals.admin.activity.AdminProductListActivity;
import com.webond.chemicals.admin.adapter.AdminProductListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.distributor.adapter.DistributorAddOrderAdapter;
import com.webond.chemicals.pojo.GetDistrictListPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.pojo.GetTalukaListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.IntentConstants;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributorAddOrderActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SELECT_DISTRICT = "Select District";
    public static final String SELECT_TALUKA = "Select Taluka";

    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvProductList;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private ArrayList<String> districtArrayList;
    private HashMap<String,String> districtHashMap;
    private ArrayList<String> talukaArrayList;
    private HashMap<String,String> talukaHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_add_order);
        initView();
        getDistrictApiCall();
//        getApproveCustomerListApiCall();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(DistributorAddOrderActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Add Order");
        rvProductList = findViewById(R.id.rvProductList);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
    }

    private void getProductListApiCall() {
//        llLoading.setVisibility(View.VISIBLE);
//        llNoDateFound.setVisibility(View.GONE);
//        rvProductList.setVisibility(View.GONE);
        ApiImplementer.getProductListApiImplementer(new Callback<ArrayList<GetProductListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetProductListPojo>> call, Response<ArrayList<GetProductListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvProductList.setVisibility(View.VISIBLE);
                            rvProductList.setAdapter(new DistributorAddOrderAdapter(DistributorAddOrderActivity.this,
                                    response.body(),districtArrayList,districtHashMap,talukaArrayList,talukaHashMap));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvProductList.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvProductList.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetProductListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvProductList.setVisibility(View.GONE);
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getDistrictApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvProductList.setVisibility(View.GONE);
        ApiImplementer.getDistrictListApiImplementer("0", new Callback<ArrayList<GetDistrictListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistrictListPojo>> call, Response<ArrayList<GetDistrictListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetDistrictListPojo> getDistrictListPojoArrayList = response.body();
                            districtArrayList = new ArrayList<>();
                            districtArrayList.add(SELECT_DISTRICT);
                            districtHashMap = new HashMap<>();
                            for (int i = 0; i < getDistrictListPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getDistrictListPojoArrayList.get(i).getDistrictName())) {
                                    String districtName = getDistrictListPojoArrayList.get(i).getDistrictName().trim();
                                    districtArrayList.add(districtName);
                                    districtHashMap.put(districtName, getDistrictListPojoArrayList.get(i).getDistrictId().toString());
                                }
                            }
                            getTalukaApiCall();
                        } else {
                            getProductListApiCall();
                            districtArrayList = new ArrayList<>();
                            districtArrayList.add(SELECT_DISTRICT);
                            districtHashMap = new HashMap<>();
                            Toast.makeText(DistributorAddOrderActivity.this, "District Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getProductListApiCall();
                        Toast.makeText(DistributorAddOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    getProductListApiCall();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistrictListPojo>> call, Throwable t) {
                getProductListApiCall();
                Toast.makeText(DistributorAddOrderActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTalukaApiCall() {
        ApiImplementer.getTalukaListApiImplementer("0", new Callback<ArrayList<GetTalukaListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetTalukaListPojo>> call, Response<ArrayList<GetTalukaListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetTalukaListPojo> getTalukaListPojoArrayList = response.body();
                            talukaArrayList = new ArrayList<>();
                            talukaArrayList.add(SELECT_TALUKA);
                            talukaHashMap = new HashMap<>();
                            for (int i = 0; i < getTalukaListPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getTalukaListPojoArrayList.get(i).getTalukaName())) {
                                    String districtName = getTalukaListPojoArrayList.get(i).getTalukaName().trim();
                                    talukaArrayList.add(districtName);
                                    talukaHashMap.put(districtName, getTalukaListPojoArrayList.get(i).getTalukaId().toString());
                                }
                            }
                            getProductListApiCall();
                        } else {
                            getProductListApiCall();
                            talukaArrayList = new ArrayList<>();
                            talukaArrayList.add(SELECT_TALUKA);
                            talukaHashMap = new HashMap<>();
                            Toast.makeText(DistributorAddOrderActivity.this, "Taluka Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getProductListApiCall();
                        Toast.makeText(DistributorAddOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    getProductListApiCall();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetTalukaListPojo>> call, Throwable t) {
                getProductListApiCall();
                Toast.makeText(DistributorAddOrderActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}