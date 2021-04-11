package com.webond.chemicals.distributor.fragments.customer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.distributor.adapter.distributor_customer.DistributorManagePendingCustomerForDistOnlyAdapter;
import com.webond.chemicals.pojo.GetCustomerListForDistributorPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorPendingCustomerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDistributorPendingCustomer;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    //    private boolean isNeedToRefresh = false;
    SwipeRefreshLayout swipeContainer;

    public DistributorPendingCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_distributor_pending_cutomer, container, false);
        initView(view);
        getApproveCustomerListApiCall(false);
        return view;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isNeedToRefresh){
//            getApproveCustomerListApiCall();
//        }
//    }

    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvDistributorPendingCustomer = view.findViewById(R.id.rvDistributorPendingCustomer);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setEnabled(true);
        swipeContainer.setOnRefreshListener(this);
    }

    private void getApproveCustomerListApiCall(boolean isPullToRefresh) {
        if (isPullToRefresh) {
            swipeContainer.setRefreshing(true);
        }
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDistributorPendingCustomer.setVisibility(View.GONE);
        ApiImplementer.getCustomerListForDistributorApiImplementer(mySharedPreferences.getDistributorId(), CommonUtil.FILTER_VALUE_PENDING, new Callback<ArrayList<GetCustomerListForDistributorPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCustomerListForDistributorPojo>> call, Response<ArrayList<GetCustomerListForDistributorPojo>> response) {
                try {
                    if (isPullToRefresh) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDistributorPendingCustomer.setVisibility(View.VISIBLE);
//                            isNeedToRefresh = true;
                            rvDistributorPendingCustomer.setAdapter(new DistributorManagePendingCustomerForDistOnlyAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDistributorPendingCustomer.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDistributorPendingCustomer.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCustomerListForDistributorPojo>> call, Throwable t) {
                if (isPullToRefresh) {
                    swipeContainer.setRefreshing(false);
                }
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDistributorPendingCustomer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {
        getApproveCustomerListApiCall(true);
    }
}