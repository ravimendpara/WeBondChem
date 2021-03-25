package com.webond.chemicals.adapter.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.GetCustomerListPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApproveCustomerListAdapter extends RecyclerView.Adapter<ApproveCustomerListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetCustomerListPojo> getCustomerListPojoArrayList;
    private LayoutInflater layoutInflater;

    public ApproveCustomerListAdapter(Context context, ArrayList<GetCustomerListPojo> getCustomerListPojoArrayList) {
        this.context = context;
        this.getCustomerListPojoArrayList = getCustomerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_approve_customer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetCustomerListPojo getCustomerListPojo = getCustomerListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getPhotoPath())) {
            Glide.with(context)
                    .load(getCustomerListPojo.getPhotoPath())
                    .centerCrop()
                    .placeholder(R.drawable.person_img)
                    .into(holder.imgApproveCustomer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCustomerName())) {
            holder.tvNameApproveCustomer.setText(getCustomerListPojo.getCustomerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getMobileNo())) {
            holder.tvMobileNoApproveCustomer.setText(getCustomerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getEmailId())) {
            holder.tvEmailApproveCustomer.setText(getCustomerListPojo.getEmailId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStateName())) {
            holder.tvStateApproveCustomer.setText(getCustomerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getDistrictName())) {
            holder.tvDistrictApproveCustomer.setText(getCustomerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getTalukaName())) {
            holder.tvTalukaApproveCustomer.setText(getCustomerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCityName())) {
            holder.tvCityApproveCustomer.setText(getCustomerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStatus())) {
            if (getCustomerListPojo.getStatus().equalsIgnoreCase("Approved")) {
                holder.tvStatusApproveCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getCustomerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusApproveCustomer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusApproveCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusApproveCustomer.setText(getCustomerListPojo.getStatus() + "");
        }
    }

    @Override
    public int getItemCount() {
        return getCustomerListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgApproveCustomer;
        TextViewMediumFont tvNameApproveCustomer;
        TextViewRegularFont tvMobileNoApproveCustomer;
        TextViewRegularFont tvEmailApproveCustomer;
        TextViewMediumFont tvStateApproveCustomer;
        TextViewMediumFont tvDistrictApproveCustomer;
        TextViewMediumFont tvTalukaApproveCustomer;
        TextViewMediumFont tvCityApproveCustomer;
        TextViewMediumFont tvStatusApproveCustomer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgApproveCustomer = itemView.findViewById(R.id.imgApproveCustomer);
            tvNameApproveCustomer = itemView.findViewById(R.id.tvNameApproveCustomer);
            tvMobileNoApproveCustomer = itemView.findViewById(R.id.tvMobileNoApproveCustomer);
            tvEmailApproveCustomer = itemView.findViewById(R.id.tvEmailApproveCustomer);
            tvStateApproveCustomer = itemView.findViewById(R.id.tvStateApproveCustomer);
            tvDistrictApproveCustomer = itemView.findViewById(R.id.tvDistrictApproveCustomer);
            tvTalukaApproveCustomer = itemView.findViewById(R.id.tvTalukaApproveCustomer);
            tvCityApproveCustomer = itemView.findViewById(R.id.tvCityApproveCustomer);
            tvStatusApproveCustomer = itemView.findViewById(R.id.tvStatusApproveCustomer);
        }
    }

}
