package com.webond.chemicals.distributor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetDealerOrderListPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DistributorAllDealerOrderAdapter extends RecyclerView.Adapter<DistributorAllDealerOrderAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GetDealerOrderListPojo> getDealerOrderListPojoArrayList;
    private LayoutInflater layoutInflater;

    public DistributorAllDealerOrderAdapter(Context context, ArrayList<GetDealerOrderListPojo> getDealerOrderListPojoArrayList) {
        this.context = context;
        this.getDealerOrderListPojoArrayList = getDealerOrderListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_manage_dealer_all_order_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDealerOrderListPojo getDealerOrderListPojo = getDealerOrderListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getProductName())) {
            holder.tvProductName.setText(getDealerOrderListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getOrderNo())) {
            holder.tvOrderNo.setText(getDealerOrderListPojo.getOrderNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getOrderDate())) {
            holder.tvOrderDate.setText(getDealerOrderListPojo.getOrderDate() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getQty())) {
            holder.tvQuantity.setText(getDealerOrderListPojo.getQty() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getPoint())) {
            holder.tvProductPoint.setText(getDealerOrderListPojo.getPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerOrderListPojo.getStatus())) {
            holder.tvStatus.setText(getDealerOrderListPojo.getStatus() + "");
        }
    }

    public int getItemCount() {
        return getDealerOrderListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvProductPoint;
        TextViewMediumFont tvStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvProductPoint = itemView.findViewById(R.id.tvProductPoint);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

}
