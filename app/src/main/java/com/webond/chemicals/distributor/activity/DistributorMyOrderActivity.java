package com.webond.chemicals.distributor.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.webond.chemicals.R;
import com.webond.chemicals.adapter.distributor.AllDistributorListAdapter;
import com.webond.chemicals.admin.adapter.AdminAllDistributorOrderAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.dealer.adapter.DealerMyOrderListAdapter;
import com.webond.chemicals.distributor.adapter.DistributorMyOrderListAdapter;
import com.webond.chemicals.pojo.GetDealerOrderListPojo;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class DistributorMyOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDistributorMyOrder;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_my_order);
        initView();
        getDistributorOrderList();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(DistributorMyOrderActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("My Orders");
        rvDistributorMyOrder = findViewById(R.id.rvDistributorMyOrder);
        llLoading = findViewById(R.id.llLoading);
        llNoDateFound = findViewById(R.id.llNoDateFound);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBack) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getDistributorOrderList() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDistributorMyOrder.setVisibility(View.GONE);
        ApiImplementer.getDistributorsOrderApiImplementer("1", mySharedPreferences.getDistributorId(), CommonUtil.FILTER_VALUE_ALL, new Callback<ArrayList<GetDistributorOrderListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistributorOrderListPojo>> call, Response<ArrayList<GetDistributorOrderListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDistributorMyOrder.setVisibility(View.VISIBLE);
                            rvDistributorMyOrder.setAdapter(new DistributorMyOrderListAdapter(DistributorMyOrderActivity.this, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDistributorMyOrder.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDistributorMyOrder.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistributorOrderListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDistributorMyOrder.setVisibility(View.GONE);
            }
        });
    }


}