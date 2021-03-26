package com.webond.chemicals.admin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.webond.chemicals.R;
import com.webond.chemicals.admin.adapter.AdminProductListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProductListActivity extends AppCompatActivity implements View.OnClickListener {

    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvProductList;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_list);
        initView();
        getApproveCustomerListApiCall();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(AdminProductListActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("Product List");
        rvProductList = findViewById(R.id.rvProductList);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
    }

    private void getApproveCustomerListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvProductList.setVisibility(View.GONE);
        ApiImplementer.getProductListApiImplementer(new Callback<ArrayList<GetProductListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetProductListPojo>> call, Response<ArrayList<GetProductListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvProductList.setVisibility(View.VISIBLE);
                            rvProductList.setAdapter(new AdminProductListAdapter(AdminProductListActivity.this, response.body()));
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
}