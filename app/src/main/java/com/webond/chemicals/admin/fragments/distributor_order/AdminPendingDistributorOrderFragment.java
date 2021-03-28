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
import android.widget.Toast;

import com.webond.chemicals.R;
import com.webond.chemicals.adapter.distributor.PendingDistributorListAdapter;
import com.webond.chemicals.admin.adapter.AdminPendingDistributorOrderAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class AdminPendingDistributorOrderFragment extends Fragment {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvAdminPendingDistributorOrder;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private boolean isNeedToRefresh = false;

    public AdminPendingDistributorOrderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_admin_pending_distributor_order, container, false);
        initView(view);
        getPendingDistributorListApiCall();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isNeedToRefresh) {
            getPendingDistributorListApiCall();
        }
    }


    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvAdminPendingDistributorOrder = view.findViewById(R.id.rvAdminPendingDistributorOrder);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
    }

    private void getPendingDistributorListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvAdminPendingDistributorOrder.setVisibility(View.GONE);
        ApiImplementer.getDistributorsOrderApiImplementer("1","0",CommonUtil.FILTER_VALUE_PENDING, new Callback<ArrayList<GetDistributorOrderListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistributorOrderListPojo>> call, Response<ArrayList<GetDistributorOrderListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvAdminPendingDistributorOrder.setVisibility(View.VISIBLE);
                            isNeedToRefresh = true;
                            rvAdminPendingDistributorOrder.setAdapter(new AdminPendingDistributorOrderAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvAdminPendingDistributorOrder.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvAdminPendingDistributorOrder.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(context, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistributorOrderListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvAdminPendingDistributorOrder.setVisibility(View.GONE);
            }
        });
    }

}