package com.webond.chemicals.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminRedeemRequestActivity;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetRedemListAdminPojo;
import com.webond.chemicals.utils.CommonUtil;

import net.seifhadjhassen.recyclerviewpager.PagerAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AdminRedeemReqListAdapter extends RecyclerView.Adapter<AdminRedeemReqListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetRedemListAdminPojo> getRedemListAdminPojoArrayList;
    private LayoutInflater layoutInflater;

    public AdminRedeemReqListAdapter(Context context, ArrayList<GetRedemListAdminPojo> getRedemListAdminPojoArrayList) {
        this.context = context;
        this.getRedemListAdminPojoArrayList = getRedemListAdminPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.admin_redeem_req_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRedeemReqListAdapter.MyViewHolder holder, int position) {
        GetRedemListAdminPojo getRedemListAdminPojo = getRedemListAdminPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getCdName())){
            holder.tvPersonName.setText(getRedemListAdminPojo.getCdName() +" ("+getRedemListAdminPojo.getCdCode()+")");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getSchemeProductName())){
            holder.tvProductName.setText(getRedemListAdminPojo.getSchemeProductName()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getRedemPoint())){
            holder.tvRedeemPoint.setText(getRedemListAdminPojo.getRedemPoint()+"");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getQty())){
            holder.tvQuantity.setText(getRedemListAdminPojo.getQty()+"");
        }

//        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getCdCode())){
//            holder.tvCode.setText(getRedemListAdminPojo.getCdCode()+"");
//        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getRedemStatus())){
            holder.tvStatus.setText(getRedemListAdminPojo.getRedemStatus()+"");
            if (getRedemListAdminPojo.getRedemStatus().equalsIgnoreCase("Reject")){
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getRedemListAdminPojo.getSchemeProductImagePathLink())){
            Picasso.get().load(getRedemListAdminPojo.getSchemeProductImagePathLink().trim())
                    .placeholder(R.drawable.default_product)
                    .error(R.drawable.default_product)
                    .into(holder.imgRedeemedProduct);
        }

    }

    @Override
    public int getItemCount() {
        return getRedemListAdminPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextViewMediumFont tvPersonName;
        TextViewMediumFont tvProductName;
        TextViewMediumFont tvRedeemPoint;
        TextViewMediumFont tvQuantity;
//        TextViewMediumFont tvCode;
        TextViewMediumFont tvStatus;
        AppCompatImageView imgRedeemedProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPersonName = itemView.findViewById(R.id.tvPersonName);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvRedeemPoint = itemView.findViewById(R.id.tvRedeemPoint);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
//            tvCode = itemView.findViewById(R.id.tvCode);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgRedeemedProduct = itemView.findViewById(R.id.imgRedeemedProduct);
        }
    }

}
