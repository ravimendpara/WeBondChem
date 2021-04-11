package com.webond.chemicals.customer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.customer.adapter.CustomerAddOrderAdapter;
import com.webond.chemicals.pojo.GetDealerListByTalukaIdPojo;
import com.webond.chemicals.pojo.GetDistributorListByTalukaIdPojo;
import com.webond.chemicals.pojo.GetDistrictListPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.pojo.GetTalukaListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAddOrderActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String SELECT_DISTRICT = "Select District*";
    public static final String SELECT_TALUKA = "Select Taluka*";
    public static final String SELECT_DEALER = "Select Dealer*";
    public static final String SELECT_DISTRIBUTOR = "Select Distributor*";

    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvProductList;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    private ArrayList<String> districtArrayList;
    private HashMap<String, String> districtHashMap;
    private ArrayList<String> talukaArrayList;
    private HashMap<String, String> talukaHashMap;
    private ArrayList<String> dealerArrayList;
    private HashMap<String, String> dealerHashMap;
    private ArrayList<String> distributorArrayList;
    private HashMap<String, String> distributorHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_order);
        initView();
        getDistrictApiCall();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(CustomerAddOrderActivity.this);
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
                            rvProductList.setAdapter(new CustomerAddOrderAdapter(CustomerAddOrderActivity.this,
                                    response.body(), districtArrayList, districtHashMap, talukaArrayList, talukaHashMap,
                                    dealerArrayList, dealerHashMap,distributorArrayList,distributorHashMap));
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
                            Toast.makeText(CustomerAddOrderActivity.this, "District Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getProductListApiCall();
                        Toast.makeText(CustomerAddOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    getProductListApiCall();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistrictListPojo>> call, Throwable t) {
                getProductListApiCall();
                Toast.makeText(CustomerAddOrderActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                            if (mySharedPreferences.getCustomerUnderRegStatus().equalsIgnoreCase("1")){
                                getDealerListByTalukaIdApiCall();
                            }else {
                                getDistributorApiCall();
                            }
                        } else {
                            getProductListApiCall();
                            talukaArrayList = new ArrayList<>();
                            talukaArrayList.add(SELECT_TALUKA);
                            talukaHashMap = new HashMap<>();
                            Toast.makeText(CustomerAddOrderActivity.this, "Taluka Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getProductListApiCall();
                        Toast.makeText(CustomerAddOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    getProductListApiCall();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetTalukaListPojo>> call, Throwable t) {
                getProductListApiCall();
                Toast.makeText(CustomerAddOrderActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDealerListByTalukaIdApiCall() {
        ApiImplementer.getDealerListByTalukaIdApiImplementer("0", new Callback<ArrayList<GetDealerListByTalukaIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDealerListByTalukaIdPojo>> call, Response<ArrayList<GetDealerListByTalukaIdPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetDealerListByTalukaIdPojo> getDealerListByCityIdPojoArrayList = response.body();
                            dealerArrayList = new ArrayList<>();
                            dealerArrayList.add(SELECT_DEALER);
                            dealerHashMap = new HashMap<>();
                            for (int i = 0; i < getDealerListByCityIdPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListByCityIdPojoArrayList.get(i).getDealerName())) {
                                    String dealerName = getDealerListByCityIdPojoArrayList.get(i).getDealerName().trim();
                                    dealerArrayList.add(dealerName);
                                    dealerHashMap.put(dealerName, getDealerListByCityIdPojoArrayList.get(i).getDealerId().toString());
                                }
                            }
                            getProductListApiCall();
                        } else {
                            dealerArrayList = new ArrayList<>();
                            dealerArrayList.add(SELECT_DEALER);
                            dealerHashMap = new HashMap<>();
                            getProductListApiCall();
                            Toast.makeText(CustomerAddOrderActivity.this, "Dealer Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getProductListApiCall();
                        Toast.makeText(CustomerAddOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDealerListByTalukaIdPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(CustomerAddOrderActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDistributorApiCall() {
        ApiImplementer.getDistributorListByTalukaIdApiImplementer("0", new Callback<ArrayList<GetDistributorListByTalukaIdPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistributorListByTalukaIdPojo>> call, Response<ArrayList<GetDistributorListByTalukaIdPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            ArrayList<GetDistributorListByTalukaIdPojo> getDistributorListByCityIdPojoArrayList = response.body();
                            distributorArrayList = new ArrayList<>();
                            distributorArrayList.add(SELECT_DISTRIBUTOR);
                            distributorHashMap = new HashMap<>();
                            for (int i = 0; i < getDistributorListByCityIdPojoArrayList.size(); i++) {
                                if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListByCityIdPojoArrayList.get(i).getDistributorName())) {
                                    String distributorName = getDistributorListByCityIdPojoArrayList.get(i).getDistributorName().trim();
                                    distributorArrayList.add(distributorName);
                                    distributorHashMap.put(distributorName, getDistributorListByCityIdPojoArrayList.get(i).getDistributorId().toString());
                                }
                            }
                            getProductListApiCall();
                        } else {
                            distributorArrayList = new ArrayList<>();
                            distributorArrayList.add(SELECT_DISTRIBUTOR);
                            distributorHashMap = new HashMap<>();
                            getProductListApiCall();
                            Toast.makeText(CustomerAddOrderActivity.this, "Distributors Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getProductListApiCall();
                        Toast.makeText(CustomerAddOrderActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistributorListByTalukaIdPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(CustomerAddOrderActivity.this, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}