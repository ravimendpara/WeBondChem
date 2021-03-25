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

public class AllCustomerListAdapter extends RecyclerView.Adapter<AllCustomerListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetCustomerListPojo> getCustomerListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AllCustomerListAdapter(Context context, ArrayList<GetCustomerListPojo> getCustomerListPojoArrayList) {
        this.context = context;
        this.getCustomerListPojoArrayList = getCustomerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_all_customer_list_item, parent, false);
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
                    .into(holder.imgAllCustomer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCustomerName())) {
            holder.tvNameAllCustomer.setText(getCustomerListPojo.getCustomerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getMobileNo())) {
            holder.tvMobileNoAllCustomer.setText(getCustomerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getEmailId())) {
            holder.tvEmailAllCustomer.setText(getCustomerListPojo.getEmailId() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStateName())) {
            holder.tvStateAllCustomer.setText(getCustomerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getDistrictName())) {
            holder.tvDistrictAllCustomer.setText(getCustomerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getTalukaName())) {
            holder.tvTalukaAllCustomer.setText(getCustomerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getCityName())) {
            holder.tvCityAllCustomer.setText(getCustomerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getCustomerListPojo.getStatus())) {
            if (getCustomerListPojo.getStatus().equalsIgnoreCase("Alld")) {
                holder.tvStatusAllCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getCustomerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusAllCustomer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusAllCustomer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusAllCustomer.setText(getCustomerListPojo.getStatus() + "");
        }
    }

    @Override
    public int getItemCount() {
        return getCustomerListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgAllCustomer;
        TextViewMediumFont tvNameAllCustomer;
        TextViewRegularFont tvMobileNoAllCustomer;
        TextViewRegularFont tvEmailAllCustomer;
        TextViewMediumFont tvStateAllCustomer;
        TextViewMediumFont tvDistrictAllCustomer;
        TextViewMediumFont tvTalukaAllCustomer;
        TextViewMediumFont tvCityAllCustomer;
        TextViewMediumFont tvStatusAllCustomer;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAllCustomer = itemView.findViewById(R.id.imgAllCustomer);
            tvNameAllCustomer = itemView.findViewById(R.id.tvNameAllCustomer);
            tvMobileNoAllCustomer = itemView.findViewById(R.id.tvMobileNoAllCustomer);
            tvEmailAllCustomer = itemView.findViewById(R.id.tvEmailAllCustomer);
            tvStateAllCustomer = itemView.findViewById(R.id.tvStateAllCustomer);
            tvDistrictAllCustomer = itemView.findViewById(R.id.tvDistrictAllCustomer);
            tvTalukaAllCustomer = itemView.findViewById(R.id.tvTalukaAllCustomer);
            tvCityAllCustomer = itemView.findViewById(R.id.tvCityAllCustomer);
            tvStatusAllCustomer = itemView.findViewById(R.id.tvStatusAllCustomer);
        }
    }

}
