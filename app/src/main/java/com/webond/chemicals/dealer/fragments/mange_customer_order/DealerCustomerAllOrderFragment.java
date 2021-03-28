package com.webond.chemicals.dealer.fragments.mange_customer_order;

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
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.dealer.adapter.DealerCustomerAllOrderAdapter;
import com.webond.chemicals.dealer.adapter.DealerCustomerApproveOrderAdapter;
import com.webond.chemicals.pojo.GetCustomerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.MySharedPreferences;

import java.util.ArrayList;

public class DealerCustomerAllOrderFragment extends Fragment {

    private Context context;
    private MySharedPreferences mySharedPreferences;
    private RecyclerView rvDealerAllCustomerOrder;
    private LinearLayout llLoading;
    private LinearLayout llNoDateFound;
    private boolean isNeedToRefresh = false;

    public DealerCustomerAllOrderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_dealer_customer_all_order, container, false);
        initView(view);
        getAllCustomerOrderListApiCall();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isNeedToRefresh) {
            getAllCustomerOrderListApiCall();
        }
    }


    private void initView(View view) {
        mySharedPreferences = new MySharedPreferences(context);
        rvDealerAllCustomerOrder = view.findViewById(R.id.rvDealerAllCustomerOrder);
        llLoading = view.findViewById(R.id.llLoading);
        llNoDateFound = view.findViewById(R.id.llNoDateFound);
    }

    private void getAllCustomerOrderListApiCall() {
        llLoading.setVisibility(View.VISIBLE);
        llNoDateFound.setVisibility(View.GONE);
        rvDealerAllCustomerOrder.setVisibility(View.GONE);
        ApiImplementer.getCustomerOrderApiImplementer("3",mySharedPreferences.getDealerId(), CommonUtil.FILTER_VALUE_ALL, new Callback<ArrayList<GetCustomerOrderListPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCustomerOrderListPojo>> call, Response<ArrayList<GetCustomerOrderListPojo>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().size() > 0) {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.GONE);
                            rvDealerAllCustomerOrder.setVisibility(View.VISIBLE);
                            isNeedToRefresh = true;
                            rvDealerAllCustomerOrder.setAdapter(new DealerCustomerAllOrderAdapter(context, response.body()));
                        } else {
                            llLoading.setVisibility(View.GONE);
                            llNoDateFound.setVisibility(View.VISIBLE);
                            rvDealerAllCustomerOrder.setVisibility(View.GONE);
                        }
                    } else {
                        llLoading.setVisibility(View.GONE);
                        llNoDateFound.setVisibility(View.VISIBLE);
                        rvDealerAllCustomerOrder.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(context, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCustomerOrderListPojo>> call, Throwable t) {
                llLoading.setVisibility(View.GONE);
                llNoDateFound.setVisibility(View.VISIBLE);
                rvDealerAllCustomerOrder.setVisibility(View.GONE);
            }
        });
    }
    
}