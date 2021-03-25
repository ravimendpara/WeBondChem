package com.webond.chemicals.distributor.fragments.customer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.webond.chemicals.R;
import com.webond.chemicals.adapter.customer.AllCustomerListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetCustomerListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class DistributorAllCustomerFragment extends Fragment {


    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDistributorAllCustomer;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;


    public DistributorAllCustomerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_distributor_all_cutomer, container, false);
        initView(view);
        getApproveCustomerListApiCall();
        return view;
    }


    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvDistributorAllCustomer = view.findViewById(R.id.rvDistributorAllCustomer);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
    }

    private void getApproveCustomerListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDistributorAllCustomer.setVisibility(View.GONE);
        ApiImplementer.getCustomerListImplementer("2", mySharedPreferences.getDistributorId(), CommonUtil.FILTER_VALUE_ALL, new Callback<ArrayList<GetCustomerListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCustomerListPojo>> call, Response<ArrayList<GetCustomerListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDistributorAllCustomer.setVisibility(View.VISIBLE);
                            rvDistributorAllCustomer.setAdapter(new AllCustomerListAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDistributorAllCustomer.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDistributorAllCustomer.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCustomerListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDistributorAllCustomer.setVisibility(View.GONE);
            }
        });
    }


}