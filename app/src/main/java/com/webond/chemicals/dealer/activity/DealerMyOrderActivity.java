package com.webond.chemicals.dealer.activity;

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
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.customer.adapter.CustomerMyOrderListAdapter;
import com.webond.chemicals.dealer.adapter.DealerMyOrderListAdapter;
import com.webond.chemicals.pojo.GetCustomerOrderListPojo;
import com.webond.chemicals.pojo.GetDealerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class DealerMyOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView imgBack;
    private TextViewMediumFont tvHeaderTitle;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDealerMyOrder;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_my_order);
        initView();
        getDealerOrderList();
    }

    private void initView() {
        mySharedPreferences = new MySharedPreferences(DealerMyOrderActivity.this);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText("My Orders");
        rvDealerMyOrder = findViewById(R.id.rvDealerMyOrder);
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

    private void getDealerOrderList() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDealerMyOrder.setVisibility(View.GONE);
        ApiImplementer.getDealerOrderApiImplementer("2", mySharedPreferences.getDealerId(), CommonUtil.FILTER_VALUE_ALL, new Callback<ArrayList<GetDealerOrderListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDealerOrderListPojo>> call, Response<ArrayList<GetDealerOrderListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDealerMyOrder.setVisibility(View.VISIBLE);
                            rvDealerMyOrder.setAdapter(new DealerMyOrderListAdapter(DealerMyOrderActivity.this, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDealerMyOrder.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDealerMyOrder.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(DealerMyOrderActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDealerOrderListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDealerMyOrder.setVisibility(View.GONE);
            }
        });
    }



}