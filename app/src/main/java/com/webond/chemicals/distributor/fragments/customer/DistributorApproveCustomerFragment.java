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
import com.webond.chemicals.adapter.dealer.ApproveDealerListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class DistributorApproveCustomerFragment extends Fragment {


    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDistributorApproveCustomer;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;


    public DistributorApproveCustomerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_distributor_approve_cutomer, container, false);
        initView(view);
        getApproveDealerListApiCall();
        return view;
    }

    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvDistributorApproveCustomer = view.findViewById(R.id.rvDistributorApproveCustomer);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
    }

    private void getApproveDealerListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDistributorApproveCustomer.setVisibility(View.GONE);
        ApiImplementer.getDealerListImplementer("2", mySharedPreferences.getDistributorId(), CommonUtil.FILTER_VALUE_APPROVE, new Callback<ArrayList<GetDealerListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDealerListPojo>> call, Response<ArrayList<GetDealerListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDistributorApproveCustomer.setVisibility(View.VISIBLE);
                            rvDistributorApproveCustomer.setAdapter(new ApproveDealerListAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDistributorApproveCustomer.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDistributorApproveCustomer.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDealerListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDistributorApproveCustomer.setVisibility(View.GONE);
            }
        });
    }
}