package com.webond.chemicals.admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webond.chemicals.R;
import com.webond.chemicals.admin.activity.AdminUpdateProductActivity;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.IntentConstants;

import java.util.ArrayList;

public class AdminProductListAdapter extends RecyclerView.Adapter<AdminProductListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetProductListPojo> getProductListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AdminProductListAdapter(Context context, ArrayList<GetProductListPojo> getProductListPojoArrayList) {
        this.context = context;
        this.getProductListPojoArrayList = getProductListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_admin_product_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GetProductListPojo getProductListPojo = getProductListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductPhoto1())) {
            Glide.with(context)
                    .load(getProductListPojo.getProductPhoto1())
                    .centerCrop()
                    .placeholder(R.drawable.default_product)
                    .into(holder.imgProduct);
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductName())) {
            holder.tvProductName.setText(getProductListPojo.getProductName());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductCode())) {
            holder.tvProductCode.setText(getProductListPojo.getProductCode());
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductDescription())) {
            holder.tvDescription.setText(getProductListPojo.getProductDescription() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductTotalPoint())) {
            holder.tvTotalPoints.setText(getProductListPojo.getProductTotalPoint() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductPrice())) {
            holder.tvPrice.setText(" " +context.getString(R.string.rupee_symbol) + getProductListPojo.getProductPrice() + "");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getDistPer())) {
            holder.tvDistributorPer.setText(getProductListPojo.getDistPer() + "%");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getDealerPer())) {
            holder.tvDealerPer.setText(getProductListPojo.getDealerPer() + "%");
        }

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getCustomerPer())) {
            holder.tvCustomerPer.setText(getProductListPojo.getCustomerPer() + "%");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getProductId())) {
                    Intent intent = new Intent(context, AdminUpdateProductActivity.class);
                    intent.putExtra(IntentConstants.PRODUCT_ID, getProductListPojo.getProductId() + "");
                    ((Activity) context).startActivityForResult(intent, IntentConstants.REQUEST_CODE_UPDATE_PRODUCT);
                } else {
                    Toast.makeText(context, "Product Id Not Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return getProductListPojoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imgProduct;
        TextViewMediumFont tvProductName;
        TextViewMediumFont tvProductCode;
        TextViewMediumFont tvDescription;
        TextViewMediumFont tvTotalPoints;
        TextViewMediumFont tvPrice;
        TextViewMediumFont tvDistributorPer;
        TextViewMediumFont tvDealerPer;
        TextViewMediumFont tvCustomerPer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductCode = itemView.findViewById(R.id.tvProductCode);
            tvTotalPoints = itemView.findViewById(R.id.tvTotalPoints);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDistributorPer = itemView.findViewById(R.id.tvDistributorPer);
            tvDealerPer = itemView.findViewById(R.id.tvDealerPer);
            tvCustomerPer = itemView.findViewById(R.id.tvCustomerPer);
        }
    }

}
