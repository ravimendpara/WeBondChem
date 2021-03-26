package com.webond.chemicals.adapter.dealer;

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
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApproveDealerListAdapter extends RecyclerView.Adapter<ApproveDealerListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDealerListPojo> getDealerListPojoArrayList;
    private LayoutInflater layoutInflater;

    public ApproveDealerListAdapter(Context context, ArrayList<GetDealerListPojo> getDealerListPojoArrayList) {
        this.context = context;
        this.getDealerListPojoArrayList = getDealerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_approve_dealer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetDealerListPojo getDealerListPojo = getDealerListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getDealerListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgApproveDealer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getPedthiName())) {
            holder.tvPedthiName.setText(getDealerListPojo.getPedthiName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDealerName())) {
            holder.tvNameApproveDealer.setText(getDealerListPojo.getDealerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getMobileNo())) {
            holder.tvMobileNoApproveDealer.setText(getDealerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getEmail())) {
            holder.tvEmailApproveDealer.setText(getDealerListPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStateName())) {
            holder.tvStateApproveDealer.setText(getDealerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDistrictName())) {
            holder.tvDistrictApproveDealer.setText(getDealerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getTalukaName())) {
            holder.tvTalukaApproveDealer.setText(getDealerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getCityName())) {
            holder.tvCityApproveDealer.setText(getDealerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStatus())) {
            if (getDealerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusApproveDealer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getDealerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusApproveDealer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusApproveDealer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusApproveDealer.setText(getDealerListPojo.getStatus() + "");
        }
        holder.llExpandedHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean show = toggleLayout(!getDealerListPojoArrayList.get(position).isExpanded(), holder.ivViewMoreBtn, holder.llExpandableLayout);
                getDealerListPojoArrayList.get(position).setExpanded(show);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getDealerListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgApproveDealer;
        TextViewMediumFont tvNameApproveDealer;
        TextViewRegularFont tvMobileNoApproveDealer;
        TextViewRegularFont tvEmailApproveDealer;
        TextViewMediumFont tvStateApproveDealer;
        TextViewMediumFont tvDistrictApproveDealer;
        TextViewMediumFont tvTalukaApproveDealer;
        TextViewMediumFont tvCityApproveDealer;
        TextViewMediumFont tvStatusApproveDealer;
        TextViewMediumFont tvPedthiName;

        AppCompatImageView ivViewMoreBtn;
        LinearLayout llExpandedHeader;
        LinearLayout llExpandableLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgApproveDealer = itemView.findViewById(R.id.imgApproveDealer);
            tvNameApproveDealer = itemView.findViewById(R.id.tvNameApproveDealer);
            tvMobileNoApproveDealer = itemView.findViewById(R.id.tvMobileNoApproveDealer);
            tvEmailApproveDealer = itemView.findViewById(R.id.tvEmailApproveDealer);
            tvStateApproveDealer = itemView.findViewById(R.id.tvStateApproveDealer);
            tvDistrictApproveDealer = itemView.findViewById(R.id.tvDistrictApproveDealer);
            tvTalukaApproveDealer = itemView.findViewById(R.id.tvTalukaApproveDealer);
            tvCityApproveDealer = itemView.findViewById(R.id.tvCityApproveDealer);
            tvStatusApproveDealer = itemView.findViewById(R.id.tvStatusApproveDealer);
            tvPedthiName = itemView.findViewById(R.id.tvPedthiName);
            ivViewMoreBtn = itemView.findViewById(R.id.ivViewMoreBtn);
            llExpandedHeader = itemView.findViewById(R.id.llExpandedHeader);
            llExpandableLayout = itemView.findViewById(R.id.llExpandableLayout);
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
