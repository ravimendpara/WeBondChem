package com.webond.chemicals.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminStockReportDetailPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

public class AdminStockItemDetailAdapter extends RecyclerView.Adapter<AdminStockItemDetailAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AdminStockReportDetailPojo>stockReportDetailPojos;

    public AdminStockItemDetailAdapter(Context context, ArrayList<AdminStockReportDetailPojo> stockReportDetailPojos) {
        this.context = context;
        this.stockReportDetailPojos = stockReportDetailPojos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_stock_detail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getCdCode())){

            holder.tvCode.setText(stockReportDetailPojos.get(position).getCdCode());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getCdName())){
            holder.tvName.setText(stockReportDetailPojos.get(position).getCdName());

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getOrderNo())){
            holder.tvOrderNo.setText(stockReportDetailPojos.get(position).getOrderNo());

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getOrderDate())){
            holder.tvOrderDate.setText(stockReportDetailPojos.get(position).getOrderDate());

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getProductName())){
            holder.tvProductName.setText(stockReportDetailPojos.get(position).getProductName());

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getProductCode())){
            holder.tvProductCode.setText(stockReportDetailPojos.get(position).getProductCode());

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getQty())){
            holder.tvQty.setText(stockReportDetailPojos.get(position).getQty()+"");

        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getPoints())){
            holder.tvPoints.setText(stockReportDetailPojos.get(position).getPoints()+"");

        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(stockReportDetailPojos.get(position).getOrderType())){
            holder.tvOrderTYpe.setText(stockReportDetailPojos.get(position).getOrderType()+"");

        }

    }

    @Override
    public int getItemCount() {
        return stockReportDetailPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextViewMediumFont tvCode;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvName;
        TextViewMediumFont tvProductName;
        TextViewMediumFont tvOrderDate;
        TextViewMediumFont tvQty;
        TextViewMediumFont tvPoints;
        TextViewMediumFont tvProductCode;
        TextViewMediumFont tvOrderTYpe;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvName = itemView.findViewById(R.id.tvName);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvProductCode = itemView.findViewById(R.id.tvProductCode);
            tvOrderTYpe = itemView.findViewById(R.id.tvOrderTYpe);


        }
    }
}
