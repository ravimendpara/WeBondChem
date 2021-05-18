package com.webond.chemicals.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminOrderRegisterPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;

import java.util.ArrayList;

public class AdminOrderRegisterAdapter extends RecyclerView.Adapter<AdminOrderRegisterAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<AdminOrderRegisterPojo>adminOrderRegisterPojos;

    public AdminOrderRegisterAdapter(Context context, ArrayList<AdminOrderRegisterPojo> adminOrderRegisterPojos) {
        this.context = context;
        this.adminOrderRegisterPojos = adminOrderRegisterPojos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_order_rgister_report,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getCdCode())){
            holder.tvOrderNo.setText(adminOrderRegisterPojos.get(position).getOrderNo());
        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getCdCode())){
            holder.tvCode.setText(adminOrderRegisterPojos.get(position).getCdCode());
        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getOrderDate())){
            holder.tvDate.setText(adminOrderRegisterPojos.get(position).getOrderDate());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getProductCode())){
            holder.tvProductCode.setText(adminOrderRegisterPojos.get(position).getProductCode());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getQty())){
            holder.tvQty.setText(adminOrderRegisterPojos.get(position).getQty()+"");
        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getPoints())){
            holder.tvPoint.setText(adminOrderRegisterPojos.get(position).getPoints()+"");
        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getCdName())){
            holder.tvName.setText(adminOrderRegisterPojos.get(position).getCdName());
        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getOrderType())){
            holder.tvOrderType.setText(adminOrderRegisterPojos.get(position).getOrderType());
        }
        if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getOrderStatus())){
            holder.tvStatus.setText(adminOrderRegisterPojos.get(position).getOrderStatus());
        }

       /* if (!CommonUtil.checkIsEmptyOrNullCommon(adminOrderRegisterPojos.get(position).getReportPDFLink())){
            holder.cvDownload.setVisibility(View.VISIBLE);
        }else{
            holder.cvDownload.setVisibility(View.GONE);
        }*/

       /* holder.cvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String fileUrl = adminOrderRegisterPojos.get(position).getReportPDFLink();
                    String fileExtension = fileUrl.substring(fileUrl.lastIndexOf("."));
                    new DownloadPdfFromUrl(context,fileUrl,fileExtension,"Order Register Report");
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return adminOrderRegisterPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvCode;
        TextViewMediumFont tvName;
        TextViewMediumFont tvDate;
        TextViewMediumFont tvProductCode;
        TextViewMediumFont tvQty;
        TextViewMediumFont tvPoint;
        TextViewMediumFont tvOrderType;
        TextViewMediumFont tvStatus;
        LinearLayout llDownload;
        MaterialCardView cvDownload;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            llDownload = itemView.findViewById(R.id.llDownload);
            tvName = itemView.findViewById(R.id.tvName);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvProductCode = itemView.findViewById(R.id.tvProductCode);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvPoint = itemView.findViewById(R.id.tvPoint);
            tvOrderType = itemView.findViewById(R.id.tvOrderType);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cvDownload = itemView.findViewById(R.id.cvDownload);
        }

    }
}
