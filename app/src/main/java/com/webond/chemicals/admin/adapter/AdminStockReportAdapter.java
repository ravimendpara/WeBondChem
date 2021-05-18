package com.webond.chemicals.admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminStockReportDetailActivity;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.AdminStockReportPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;

import java.io.Serializable;
import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class AdminStockReportAdapter extends RecyclerView.Adapter<AdminStockReportAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AdminStockReportPojo> adminStockReportPojo;
    private String loginType;

    public AdminStockReportAdapter(Context context  , ArrayList<AdminStockReportPojo> adminStockReportPojo,String loginType) {
        this.context = context;
        this.adminStockReportPojo = adminStockReportPojo;
        this.loginType = loginType;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_stock_report,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getCDName())){
            holder.tvName.setText(adminStockReportPojo.get(position).getCDName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getProductName())){
            holder.tvProductName.setText(adminStockReportPojo.get(position).getProductName());
        }


        if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getTotalQty())){
            holder.tvTotalQty.setText(adminStockReportPojo.get(position).getTotalQty()+"");
        }


        if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getSaleQty())){
            holder.tvSaleQty.setText(adminStockReportPojo.get(position).getSaleQty()+"");
        }


        if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getAvailableQty())){
            holder.tvAvlQty.setText(adminStockReportPojo.get(position).getAvailableQty()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getReportDate())){
            holder.tvDate.setText(adminStockReportPojo.get(position).getReportDate());
        }


        if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getCDCode())){
            holder.tvCode.setText(adminStockReportPojo.get(position).getCDCode());
        }

       /* if (!CommonUtil.checkIsEmptyOrNullCommon(adminStockReportPojo.get(position).getReportPDFLink())){
            holder.cvDownload.setVisibility(VISIBLE);
        }else{
            holder.cvDownload.setVisibility(View.GONE);
        }*/
        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent stockReportDateIntent = new Intent(context, AdminStockReportDetailActivity.class);
                stockReportDateIntent.putExtra("detail", (Serializable) adminStockReportPojo.get(position));
                stockReportDateIntent.putExtra("loginType", loginType);
                context.startActivity(stockReportDateIntent);

            }
        });
       /* holder.cvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileUrl = adminStockReportPojo.get(position).getReportPDFLink();
                String fileExtension = fileUrl.substring(fileUrl.lastIndexOf("."));
                new DownloadPdfFromUrl(context,fileUrl,fileExtension,"Stock Report");
            }
        });*/




    }

    @Override
    public int getItemCount() {
        return adminStockReportPojo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  MaterialCardView cvMain,cvDownload;
        private LinearLayout llDownload;
        private TextViewMediumFont tvName;
        private TextViewMediumFont tvProductName;
        private TextViewMediumFont tvTotalQty;
        private TextViewMediumFont tvSaleQty;
        private TextViewMediumFont tvAvlQty;
        private TextViewMediumFont tvDate;
        private TextViewMediumFont tvCode;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            llDownload = itemView.findViewById(R.id.llDownload);
            cvMain = itemView.findViewById(R.id.cvMain);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvTotalQty = itemView.findViewById(R.id.tvTotalQty);
            tvSaleQty = itemView.findViewById(R.id.tvSaleQty);
            tvAvlQty = itemView.findViewById(R.id.tvAvlQty);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCode = itemView.findViewById(R.id.tvCode);
            cvDownload = itemView.findViewById(R.id.cvDownload);
        }
    }
}
