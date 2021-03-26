package com.webond.chemicals.adapter.distributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.Animations;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.GetDistributorListPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApproveDistributorListAdapter extends RecyclerView.Adapter<ApproveDistributorListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDistributorListPojo> getDistributorListPojos;
    private LayoutInflater layoutInflater;

    public ApproveDistributorListAdapter(Context context, ArrayList<GetDistributorListPojo> getDistributorListPojos) {
        this.context = context;
        this.getDistributorListPojos = getDistributorListPojos;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_approve_distributor_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDistributorListPojo getDistributorListPojo = getDistributorListPojos.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getDistributorListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgApproveDistributor);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getPethiName())) {
            holder.tvPedthiName.setText(getDistributorListPojo.getPethiName() + "");
        }


        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getDistributorName())) {
            holder.tvNameApproveDistributor.setText(getDistributorListPojo.getDistributorName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getMobileNo())) {
            holder.tvMobileNoApproveDistributor.setText(getDistributorListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getEmail())) {
            holder.tvEmailApproveDistributor.setText(getDistributorListPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getStateName())) {
            holder.tvStateApproveDistributor.setText(getDistributorListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getDistrictName())) {
            holder.tvDistrictApproveDistributor.setText(getDistributorListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getTalukaName())) {
            holder.tvTalukaApproveDistributor.setText(getDistributorListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getCityName())) {
            holder.tvCityApproveDistributor.setText(getDistributorListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDistributorListPojo.getStatus())) {
            if (getDistributorListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusApproveDistributor.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getDistributorListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusApproveDistributor.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusApproveDistributor.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusApproveDistributor.setText(getDistributorListPojo.getStatus() + "");
        }


        holder.llExpandedHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(!getDistributorListPojos.get(position).isExpanded(), holder.ivViewMoreBtn, holder.llExpandableLayout);
                getDistributorListPojos.get(position).setExpanded(show);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getDistributorListPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgApproveDistributor;
        TextViewMediumFont tvNameApproveDistributor;
        TextViewRegularFont tvMobileNoApproveDistributor;
        TextViewRegularFont tvEmailApproveDistributor;
        TextViewMediumFont tvStateApproveDistributor;
        TextViewMediumFont tvDistrictApproveDistributor;
        TextViewMediumFont tvTalukaApproveDistributor;
        TextViewMediumFont tvCityApproveDistributor;
        TextViewMediumFont tvStatusApproveDistributor;
        TextViewMediumFont tvPedthiName;

        AppCompatImageView ivViewMoreBtn;
        LinearLayout llExpandedHeader;
        LinearLayout llExpandableLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            ivViewMoreBtn = itemView.findViewById(R.id.ivViewMoreBtn);
            llExpandedHeader = itemView.findViewById(R.id.llExpandedHeader);
            llExpandableLayout = itemView.findViewById(R.id.llExpandableLayout);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            imgApproveDistributor = itemView.findViewById(R.id.imgApproveDistributor);
            tvNameApproveDistributor = itemView.findViewById(R.id.tvNameApproveDistributor);
            tvMobileNoApproveDistributor = itemView.findViewById(R.id.tvMobileNoApproveDistributor);
            tvEmailApproveDistributor = itemView.findViewById(R.id.tvEmailApproveDistributor);
            tvStateApproveDistributor = itemView.findViewById(R.id.tvStateApproveDistributor);
            tvDistrictApproveDistributor = itemView.findViewById(R.id.tvDistrictApproveDistributor);
            tvTalukaApproveDistributor = itemView.findViewById(R.id.tvTalukaApproveDistributor);
            tvCityApproveDistributor = itemView.findViewById(R.id.tvCityApproveDistributor);
            tvStatusApproveDistributor = itemView.findViewById(R.id.tvStatusApproveDistributor);
        }
    }


    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;

    }

}
