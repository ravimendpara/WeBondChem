package com.webond.chemicals.distributor.fragments.manage_dealer_order;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.webond.chemicals.R;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.distributor.adapter.DistributorPendingDealerOrderAdapter;
import com.webond.chemicals.pojo.GetDealerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorPendingDealerOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDistributorPendingDealerOrder;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
//    private boolean isNeedToRefresh = false;

    SwipeRefreshLayout swipeContainer;


    public DistributorPendingDealerOrderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_distributor_pending_dealer_order, container, false);
        initView(view);
        getPendingDealerListApiCall(false);
        return view;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isNeedToRefresh) {
//            getPendingDealerListApiCall();
//        }
//    }


    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvDistributorPendingDealerOrder = view.findViewById(R.id.rvDistributorPendingDealerOrder);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setEnabled(true);
        swipeContainer.setOnRefreshListener(this);
    }

    private void getPendingDealerListApiCall(boolean isPullToRefresh) {
        if (isPullToRefresh) {
            swipeContainer.setRefreshing(true);
        }
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDistributorPendingDealerOrder.setVisibility(View.GONE);
        ApiImplementer.getDealerOrderApiImplementer("2", mySharedPreferences.getDistributorId(), CommonUtil.FILTER_VALUE_PENDING, new Callback<ArrayList<GetDealerOrderListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDealerOrderListPojo>> call, Response<ArrayList<GetDealerOrderListPojo>> response) {
                try {
                    if (isPullToRefresh) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDistributorPendingDealerOrder.setVisibility(View.VISIBLE);
//                            isNeedToRefresh = true;
                            rvDistributorPendingDealerOrder.setAdapter(new DistributorPendingDealerOrderAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDistributorPendingDealerOrder.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDistributorPendingDealerOrder.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDealerOrderListPojo>> call, Throwable t) {
                if (isPullToRefresh) {
                    swipeContainer.setRefreshing(false);
                }
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDistributorPendingDealerOrder.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {
        getPendingDealerListApiCall(true);
    }
}