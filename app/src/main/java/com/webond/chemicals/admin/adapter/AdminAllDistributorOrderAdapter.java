package com.webond.chemicals.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminAllDistributorOrderAdapter extends RecyclerView.Adapter<AdminAllDistributorOrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AdminAllDistributorOrderAdapter(Context context, ArrayList<GetDistributorOrderListPojo> getDistributorOrderListPojoArrayList) {
        this.context = context;
        this.getDistributorOrderListPojoArrayList = getDistributorOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_distributor_all_order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDistributorOrderListPojo getDistributorOrderListPojo = getDistributorOrderListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getDistributorName())) {
            holder.tvDistributorName.setText(getDistributorOrderListPojo.getDistributorName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getProductName())) {
            holder.tvProductName.setText(getDistributorOrderListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getOrderNo())) {
            holder.tvOrderNo.setText(getDistributorOrderListPojo.getOrderNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getOrderDate())) {
            holder.tvOrderDate.setText(getDistributorOrderListPojo.getOrderDate() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getQty())) {
            holder.tvQuantity.setText(getDistributorOrderListPojo.getQty() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getPoint())) {
            holder.tvProductPoint.setText(getDistributorOrderListPojo.getPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorOrderListPojo.getStatus())) {
            holder.tvStatus.setText(getDistributorOrderListPojo.getStatus() + "");
        }

    }

    @Override
    public int getItemCount() {
        return getDistributorOrderListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvProductPoint;
        TextViewMediumFont tvStatus;
        TextViewMediumFont tvDistributorName;
        MaterialButton btnApprove;
        MaterialButton btnReject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvProductPoint = itemView.findViewById(R.id.tvProductPoint);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
            tvDistributorName = itemView.findViewById(R.id.tvDistributorName);
        }
    }

}