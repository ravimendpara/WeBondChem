package com.webond.chemicals.admin.fragments.dealer;

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
import com.webond.chemicals.adapter.dealer.PendingDealerListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminPendingDealerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvAdminPendingDealer;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    //    private boolean isNeedToRefresh = false;
    SwipeRefreshLayout swipeContainer;

    public AdminPendingDealerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_admin_pending_dealer, container, false);
        initView(view);
        getApproveDealerListApiCall(false);
        return view;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isNeedToRefresh) {
//            getApproveDealerListApiCall();
//        }
//    }

    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvAdminPendingDealer = view.findViewById(R.id.rvAdminPendingDealer);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setEnabled(true);
        swipeContainer.setOnRefreshListener(this);
    }

    private void getApproveDealerListApiCall(boolean isPullToRefresh) {
        if (isPullToRefresh) {
            swipeContainer.setRefreshing(true);
        }
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvAdminPendingDealer.setVisibility(View.GONE);
        ApiImplementer.getDealerListImplementer("1", "0", CommonUtil.FILTER_VALUE_PENDING, new Callback<ArrayList<GetDealerListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDealerListPojo>> call, Response<ArrayList<GetDealerListPojo>> response) {
                try {
                    if (isPullToRefresh) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvAdminPendingDealer.setVisibility(View.VISIBLE);
//                            isNeedToRefresh = true;
                            rvAdminPendingDealer.setAdapter(new PendingDealerListAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvAdminPendingDealer.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvAdminPendingDealer.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDealerListPojo>> call, Throwable t) {
                if (isPullToRefresh) {
                    swipeContainer.setRefreshing(false);
                }
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvAdminPendingDealer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {
        getApproveDealerListApiCall(true);
    }
}