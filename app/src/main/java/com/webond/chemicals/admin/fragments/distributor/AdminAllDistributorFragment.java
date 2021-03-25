package com.webond.chemicals.admin.fragments.distributor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.webond.chemicals.R;
import com.webond.chemicals.adapter.distributor.AllDistributorListAdapter;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminAllDistributorFragment extends Fragment {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvAdminAllDistributor;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;

    public AdminAllDistributorFragment() {
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
        View view = inflater.inflate(R.layout.fragment_admin_all_distributor, container, false);
        initView(view);
        getApproveDistributorListApiCall();
        return view;
    }


    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvAdminAllDistributor = view.findViewById(R.id.rvAdminAllDistributor);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
    }

    private void getApproveDistributorListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvAdminAllDistributor.setVisibility(View.GONE);
        ApiImplementer.getDistributorListApiImplementer("0", CommonUtil.FILTER_VALUE_ALL, new Callback<ArrayList<GetDistributorListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetDistributorListPojo>> call, Response<ArrayList<GetDistributorListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvAdminAllDistributor.setVisibility(View.VISIBLE);
                            rvAdminAllDistributor.setAdapter(new AllDistributorListAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvAdminAllDistributor.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvAdminAllDistributor.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetDistributorListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvAdminAllDistributor.setVisibility(View.GONE);
            }
        });
    }

}