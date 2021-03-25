package com.webond.chemicals.adapter.dealer;

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
import com.webond.chemicals.pojo.GetDealerListPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllDealerListAdapter extends RecyclerView.Adapter<AllDealerListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetDealerListPojo> getDealerListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AllDealerListAdapter(Context context, ArrayList<GetDealerListPojo> getDealerListPojoArrayList) {
        this.context = context;
        this.getDealerListPojoArrayList = getDealerListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.inflater_all_dealer_list_item, parent, false);
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
                    .into(holder.imgAllDealer);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDealerName())) {
            holder.tvNameAllDealer.setText(getDealerListPojo.getDealerName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getMobileNo())) {
            holder.tvMobileNoAllDealer.setText(getDealerListPojo.getMobileNo() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getEmail())) {
            holder.tvEmailAllDealer.setText(getDealerListPojo.getEmail() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStateName())) {
            holder.tvStateAllDealer.setText(getDealerListPojo.getStateName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getDistrictName())) {
            holder.tvDistrictAllDealer.setText(getDealerListPojo.getDistrictName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getTalukaName())) {
            holder.tvTalukaAllDealer.setText(getDealerListPojo.getTalukaName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getCityName())) {
            holder.tvCityAllDealer.setText(getDealerListPojo.getCityName() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getDealerListPojo.getStatus())) {
            if (getDealerListPojo.getStatus().equalsIgnoreCase("Alld")) {
                holder.tvStatusAllDealer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (getDealerListPojo.getStatus().equalsIgnoreCase("Rejected")) {
                holder.tvStatusAllDealer.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.tvStatusAllDealer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
            holder.tvStatusAllDealer.setText(getDealerListPojo.getStatus() + "");
        }
    }

    @Override
    public int getItemCount() {
        return getDealerListPojoArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAllDealer;
        TextViewMediumFont tvNameAllDealer;
        TextViewRegularFont tvMobileNoAllDealer;
        TextViewRegularFont tvEmailAllDealer;
        TextViewMediumFont tvStateAllDealer;
        TextViewMediumFont tvDistrictAllDealer;
        TextViewMediumFont tvTalukaAllDealer;
        TextViewMediumFont tvCityAllDealer;
        TextViewMediumFont tvStatusAllDealer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAllDealer = itemView.findViewById(R.id.imgAllDealer);
            tvNameAllDealer = itemView.findViewById(R.id.tvNameAllDealer);
            tvMobileNoAllDealer = itemView.findViewById(R.id.tvMobileNoAllDealer);
            tvEmailAllDealer = itemView.findViewById(R.id.tvEmailAllDealer);
            tvStateAllDealer = itemView.findViewById(R.id.tvStateAllDealer);
            tvDistrictAllDealer = itemView.findViewById(R.id.tvDistrictAllDealer);
            tvTalukaAllDealer = itemView.findViewById(R.id.tvTalukaAllDealer);
            tvCityAllDealer = itemView.findViewById(R.id.tvCityAllDealer);
            tvStatusAllDealer = itemView.findViewById(R.id.tvStatusAllDealer);
        }
    }

}
