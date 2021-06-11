package com.webond.chemicals.adapter.common_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.webond.chemicals.R;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.GetRedemListForUserPojo;
import com.webond.chemicals.utils.CommonUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class UserRedeemListAdapter extends RecyclerView.Adapter<UserRedeemListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetRedemListForUserPojo> getRedemListForUserPojoArrayList;
    private LayoutInflater layoutInflater;

    public UserRedeemListAdapter(Context context, ArrayList<GetRedemListForUserPojo> getRedemListForUserPojoArrayList) {
        this.context = context;
        this.getRedemListForUserPojoArrayList = getRedemListForUserPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.user_redeem_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRedeemListAdapter.MyViewHolder holder, int position) {

        GetRedemListForUserPojo getRedemListForUserPojo = getRedemListForUserPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getSchemeProductName())) {
            holder.tvProductName.setText(getRedemListForUserPojo.getSchemeProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getRedemPoint())) {
            holder.tvRedeemPoint.setText(getRedemListForUserPojo.getRedemPoint()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getQty())) {
            holder.tvQuantity.setText(getRedemListForUserPojo.getQty()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getCdCode())) {
            holder.tvCode.setText(getRedemListForUserPojo.getCdCode()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getRedemStatus())) {
            holder.tvRedeemStatus.setText(getRedemListForUserPojo.getRedemStatus()+"");

            if (getRedemListForUserPojo.getRedemStatus().contains("Reject")){
                holder.tvRedeemStatus.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.redeem_product_reject_border));
            }else {
                holder.tvRedeemStatus.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.border_odd));
            }

        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListForUserPojo.getSchemeProductImagePathLink())) {
            Picasso.get().load(getRedemListForUserPojo.getSchemeProductImagePathLink().trim())
                    .placeholder(R.drawable.default_product)
                    .error(R.drawable.default_product)
                    .into(holder.imgRedeemedProduct);
        }

    }

    @Override
    public int getItemCount() {
        return getRedemListForUserPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewMediumFont tvProductName;
        TextViewMediumFont tvRedeemPoint;
        TextViewMediumFont tvQuantity;
        TextViewMediumFont tvCode;
        TextViewRegularFont tvRedeemStatus;
        AppCompatImageView imgRedeemedProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvRedeemPoint = itemView.findViewById(R.id.tvRedeemPoint);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvCode = itemView.findViewById(R.id.tvCode);
            tvRedeemStatus = itemView.findViewById(R.id.tvRedeemStatus);
            imgRedeemedProduct = itemView.findViewById(R.id.imgRedeemedProduct);

        }
    }

}
