package com.webond.chemicals.dealer.fragments;

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
import com.webond.chemicals.adapter.customer.PendingCustomerListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetCustomerListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class DealerPendingCustomerFragment extends Fragment {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDealerPendingCustomer;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;

    public DealerPendingCustomerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_dealer_pending_customer, container, false);
        initView(view);
        getApproveCustomerListApiCall();
        return view;
    }



    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvDealerPendingCustomer = view.findViewById(R.id.rvDealerPendingCustomer);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
    }

    private void getApproveCustomerListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDealerPendingCustomer.setVisibility(View.GONE);
        ApiImplementer.getCustomerListImplementer("3", mySharedPreferences.getDealerId(), CommonUtil.FILTER_VALUE_PENDING, new Callback<ArrayList<GetCustomerListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCustomerListPojo>> call, Response<ArrayList<GetCustomerListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDealerPendingCustomer.setVisibility(View.VISIBLE);
                            rvDealerPendingCustomer.setAdapter(new PendingCustomerListAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDealerPendingCustomer.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDealerPendingCustomer.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCustomerListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDealerPendingCustomer.setVisibility(View.GONE);
            }
        });
    }



}