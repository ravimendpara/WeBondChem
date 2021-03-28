package com.webond.chemicals.admin.fragments.distributor_order;

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
import com.webond.chemicals.admin.adapter.AdminApproveDistributorOrderAdapter;
import com.webond.chemicals.admin.adapter.AdminPendingDistributorOrderAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class AdminApproveDistributorOrderFragment extends Fragment {


    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvAdminApproveDistributorOrder;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private boolean isNeedToRefresh = false;

    public AdminApproveDistributorOrderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_admin_approve_distributor_order, container, false);
        initView(view);
        getApproveDistributorListApiCall();
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isNeedToRefresh) {
            getApproveDistributorListApiCall();
        }
    }


    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvAdminApproveDistributorOrder = view.findViewById(R.id.rvAdminApproveDistributorOrder);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
    }

    private void getApproveDistributorListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvAdminApproveDistributorOrder.setVisibility(View.GONE);
        ApiImplementer.getDistributorsOrderApiImplementer("1", "0", CommonUtil.FILTER_VALUE_APPROVE, new Callback<ArrayList<GetDistributorOrderListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistributorOrderListPojo>> call, Response<ArrayList<GetDistributorOrderListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvAdminApproveDistributorOrder.setVisibility(View.VISIBLE);
                            isNeedToRefresh = true;
                            rvAdminApproveDistributorOrder.setAdapter(new AdminApproveDistributorOrderAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvAdminApproveDistributorOrder.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvAdminApproveDistributorOrder.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistributorOrderListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvAdminApproveDistributorOrder.setVisibility(View.GONE);
            }
        });
    }

}