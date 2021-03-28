package com.webond.chemicals.dealer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetCustomerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DealerCustomerAllOrderAdapter extends RecyclerView.Adapter<DealerCustomerAllOrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public DealerCustomerAllOrderAdapter(Context context, ArrayList<GetCustomerOrderListPojo> getCustomerOrderListPojoArrayList) {
        this.context = context;
        this.getCustomerOrderListPojoArrayList = getCustomerOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_manage_customer_all_order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetCustomerOrderListPojo getCustomerOrderListPojo = getCustomerOrderListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getCustomerName())) {
            holder.tvCustomerName.setText(getCustomerOrderListPojo.getCustomerName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getProductName())) {
            holder.tvProductName.setText(getCustomerOrderListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getOrderNo())) {
            holder.tvOrderNo.setText(getCustomerOrderListPojo.getOrderNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getOrderDate())) {
            holder.tvOrderDate.setText(getCustomerOrderListPojo.getOrderDate() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getQty())) {
            holder.tvQuantity.setText(getCustomerOrderListPojo.getQty() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getPoint())) {
            holder.tvProductPoint.setText(getCustomerOrderListPojo.getPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerOrderListPojo.getStatus())) {
            holder.tvStatus.setText(getCustomerOrderListPojo.getStatus() + "");
        }
    }

    @Override
    public int getItemCount() {
        return getCustomerOrderListPojoArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvProductPoint;
        TextViewMediumFont tvStatus;
        TextViewMediumFont tvCustomerName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvProductPoint = itemView.findViewById(R.id.tvProductPoint);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
        }
    }

}
