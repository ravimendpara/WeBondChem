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
import com.webond.chemicals.pojo.AdminPointReportPojo;
import com.webond.chemicals.pojo.GetLoginOrderListForDistributorPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DownloadPdfFromUrl;

import java.util.ArrayList;

import okhttp3.internal.Util;

import static android.view.View.VISIBLE;

public class AdminPointReportAdapter extends RecyclerView.Adapter<AdminPointReportAdapter.MyViewHolder> {
    Context context;
    private ArrayList<AdminPointReportPojo> adminPointReportPojos;

    public AdminPointReportAdapter(Context context, ArrayList<AdminPointReportPojo> adminPointReportPojos) {
        this.context = context;
        this.adminPointReportPojos = adminPointReportPojos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_admin_point_report,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if (!CommonUtil.checkIsEmptyOrNullCommon(adminPointReportPojos.get(position).getCdName())){
            holder.tvName.setText(adminPointReportPojos.get(position).getCdName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(adminPointReportPojos.get(position).getCdCode())){
            holder.tvOrderNo.setText(adminPointReportPojos.get(position).getCdCode());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(adminPointReportPojos.get(position).getTotalPoint())){
            holder.tvTotalPoint.setText(adminPointReportPojos.get(position).getTotalPoint()+"");
        }









    }

    @Override
    public int getItemCount() {
        return adminPointReportPojos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextViewMediumFont tvName;
        TextViewMediumFont tvOrderNo;
        TextViewMediumFont tvTotalPoint;
        MaterialCardView cvDownload;
        LinearLayout llDownload;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvTotalPoint = itemView.findViewById(R.id.tvTotalPoint);
            llDownload = itemView.findViewById(R.id.llDownload);
            cvDownload = itemView.findViewById(R.id.cvDownload);
        }
    }
}
