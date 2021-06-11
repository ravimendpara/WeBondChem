package com.webond.chemicals.admin.fragments.redeem_req_list;

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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.webond.chemicals.R;
import com.webond.chemicals.admin.adapter.AdminRedeemReqListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.SpinnerSimpleAdapter;
import com.webond.chemicals.pojo.GetRedemListAdminPojo;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class AllRedeemRequestListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private static AllRedeemRequestListFragment allRedeemRequestListFragment = null;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvAdminRedeemList;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private SwipeRefreshLayout swipeContainer;
    private Spinner spAdminRedeemFilter;
    private String loginType = "2";
    private ArrayList<String> filterTypeArrayList;
    private SpinnerSimpleAdapter spinnerAdapterUserType;

    public AllRedeemRequestListFragment() {
    }

    public static AllRedeemRequestListFragment newInstance() {
        if (allRedeemRequestListFragment == null) {
            allRedeemRequestListFragment = new AllRedeemRequestListFragment();
        }
        return allRedeemRequestListFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_redeem_request_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        filterTypeArrayList = new ArrayList<>();
        mySharedPreferences = new MySharedPreferences(context);
        rvAdminRedeemList = view.findViewById(R.id.rvAdminRedeemList);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        spAdminRedeemFilter = view.findViewById(R.id.spAdminRedeemFilter);
        swipeContainer.setEnabled(true);
        swipeContainer.setOnRefreshListener(this);
        filterTypeArrayList.add("Distributor");
        filterTypeArrayList.add("Dealer");
        filterTypeArrayList.add("Applicant");

        spinnerAdapterUserType = new SpinnerSimpleAdapter(context, filterTypeArrayList);
        spAdminRedeemFilter.setAdapter(spinnerAdapterUserType);

        spAdminRedeemFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    loginType = "2";
                }else if(position == 1){
                    loginType = "3";
                }else {
                    loginType = "4";
                }
                getRedeemListAdminApiCall(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getRedeemListAdminApiCall(boolean isPullToRefresh) {
        if (isPullToRefresh) {
            swipeContainer.setRefreshing(true);
        }
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvAdminRedeemList.setVisibility(View.GONE);

        ApiImplementer.GetRedemListAdmin(loginType, "3", new Callback<ArrayList<GetRedemListAdminPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetRedemListAdminPojo>> call, Response<ArrayList<GetRedemListAdminPojo>> response) {
                try {
                    if (isPullToRefresh) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvAdminRedeemList.setVisibility(View.VISIBLE);
//                            isNeedToRefresh = true;
                            rvAdminRedeemList.setAdapter(new AdminRedeemReqListAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvAdminRedeemList.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvAdminRedeemList.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetRedemListAdminPojo>> call, Throwable t) {
                if (isPullToRefresh) {
                    swipeContainer.setRefreshing(false);
                }
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvAdminRedeemList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {
        getRedeemListAdminApiCall(true);
    }

}