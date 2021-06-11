package com.webond.chemicals.common_fragments.user_redeemlist_fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.webond.chemicals.R;
import com.webond.chemicals.adapter.common_adapter.UserRedeemListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetRedemListForUserPojo;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class ApproveUserRedeemListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private static ApproveUserRedeemListFragment approveUserRedeemListFragment = null;
    private static String loginId;
    private static String loginType;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvRedeemList;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private SwipeRefreshLayout swipeContainer;


    public ApproveUserRedeemListFragment() {
        // Required empty public constructor
    }

    public static ApproveUserRedeemListFragment newInstance(String loggedInUserId,String loginTypeNew) {
        loginId = loggedInUserId;
        loginType = loginTypeNew;
        if (approveUserRedeemListFragment == null) {
            approveUserRedeemListFragment = new ApproveUserRedeemListFragment();
        }
        return approveUserRedeemListFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approve_user_redeem_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvRedeemList = view.findViewById(R.id.rvRedeemList);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setEnabled(true);
        swipeContainer.setOnRefreshListener(this);
        getApproveCustomerListApiCall(false);
    }

    private void getApproveCustomerListApiCall(boolean isPullToRefresh) {
        if (isPullToRefresh) {
            swipeContainer.setRefreshing(true);
        }
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvRedeemList.setVisibility(View.GONE);
        ApiImplementer.GetRedemListForUser(loginType, loginId, "1", new Callback<ArrayList<GetRedemListForUserPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetRedemListForUserPojo>> call, Response<ArrayList<GetRedemListForUserPojo>> response) {
                try {
                    if (isPullToRefresh) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvRedeemList.setVisibility(View.VISIBLE);
//                            isNeedToRefresh = true;
                            rvRedeemList.setAdapter(new UserRedeemListAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvRedeemList.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvRedeemList.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetRedemListForUserPojo>> call, Throwable t) {
                if (isPullToRefresh) {
                    swipeContainer.setRefreshing(false);
                }
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvRedeemList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {
        getApproveCustomerListApiCall(true);
    }

}