package com.webond.chemicals.dealer.fragments.mange_customer_order;

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
import com.webond.chemicals.dealer.adapter.DealerCustomerPendingOrderAdapter;
import com.webond.chemicals.pojo.GetCustomerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerCustomerPendingOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDealerPendingCustomerOrder;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    //    private boolean isNeedToRefresh = false;
    SwipeRefreshLayout swipeContainer;


    public DealerCustomerPendingOrderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_dealer_customer_pending_order, container, false);
        initView(view);
        getPendingCustomerOrderListApiCall(false);
        return view;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isNeedToRefresh) {
//            getPendingCustomerOrderListApiCall();
//        }
//    }


    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvDealerPendingCustomerOrder = view.findViewById(R.id.rvDealerPendingCustomerOrder);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setEnabled(true);
        swipeContainer.setOnRefreshListener(this);
    }

    private void getPendingCustomerOrderListApiCall(boolean isPullToRefresh) {
        if (isPullToRefresh) {
            swipeContainer.setRefreshing(true);
        }
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDealerPendingCustomerOrder.setVisibility(View.GONE);
        ApiImplementer.getCustomerOrderApiImplementer("3", mySharedPreferences.getDealerId(), CommonUtil.FILTER_VALUE_PENDING, new Callback<ArrayList<GetCustomerOrderListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCustomerOrderListPojo>> call, Response<ArrayList<GetCustomerOrderListPojo>> response) {
                try {
                    if (isPullToRefresh) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDealerPendingCustomerOrder.setVisibility(View.VISIBLE);
//                            isNeedToRefresh = true;
                            rvDealerPendingCustomerOrder.setAdapter(new DealerCustomerPendingOrderAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDealerPendingCustomerOrder.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDealerPendingCustomerOrder.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCustomerOrderListPojo>> call, Throwable t) {
                if (isPullToRefresh) {
                    swipeContainer.setRefreshing(false);
                }
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDealerPendingCustomerOrder.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {
        getPendingCustomerOrderListApiCall(true);
    }
}