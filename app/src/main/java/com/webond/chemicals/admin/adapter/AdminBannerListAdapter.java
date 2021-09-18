package com.webond.chemicals.admin.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.webond.chemicals.admin.activity.AdminUpdateBannerActivity;
import com.webond.chemicals.admin.activity.AdminUpdateProductActivity;
import com.webond.chemicals.api.ApiImplementer;
import com.webond.chemicals.custom_class.TextViewMediumFont;
import com.webond.chemicals.custom_class.TextViewRegularFont;
import com.webond.chemicals.pojo.ApproveOrderPojo;
import com.webond.chemicals.pojo.DeleteBannerPojo;
import com.webond.chemicals.pojo.GetBannerListPojo;
import com.webond.chemicals.pojo.GetDistributorOrderListPojo;
import com.webond.chemicals.pojo.GetProductListPojo;
import com.webond.chemicals.utils.CommonUtil;
import com.webond.chemicals.utils.DialogUtil;
import com.webond.chemicals.utils.IntentConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBannerListAdapter extends RecyclerView.Adapter<AdminBannerListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<GetBannerListPojo> getProductListPojoArrayList;
    private LayoutInflater layoutInflater;

    public AdminBannerListAdapter(Context context, ArrayList<GetBannerListPojo> getProductListPojoArrayList) {
        this.context = context;
        this.getProductListPojoArrayList = getProductListPojoArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdminBannerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_admin_banner_list_item, parent, false);
        return new AdminBannerListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminBannerListAdapter.MyViewHolder holder, int position) {

        GetBannerListPojo getProductListPojo = getProductListPojoArrayList.get(position);

        if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getBannerPath())) {
            Glide.with(context)
                    .load(getProductListPojo.getBannerPath())
                    .centerCrop()
                    .placeholder(R.drawable.default_product)
                    .into(holder.imgProduct);
        }

        //if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getBannerName())) {
        //    holder.tvProductName.setText(getProductListPojo.getBannerName());
       //}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getBannerId())) {
                    Intent intent = new Intent(context, AdminUpdateBannerActivity.class);
                    intent.putExtra(IntentConstants.BANNERID, getProductListPojo.getBannerId() + "");
                    ((Activity) context).startActivityForResult(intent, IntentConstants.REQUEST_CODE_UPDATE_PRODUCT);
                } else {
                    Toast.makeText(context, "Banner Id Not Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnDeleteBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.checkIsEmptyOrNullCommon(getProductListPojo.getBannerId())) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Do you want to delete ?. Once you delete this order can't retrive.");
                    builder.setCancelable(false);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            DeleteBanner(getProductListPojo.getBannerId() + "",
                                    position,getProductListPojoArrayList);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                } else {
                    Toast.makeText(context, "Banner Not Found!", Toast.LENGTH_SHORT).show();
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
        TextViewRegularFont btnDeleteBanner;
        //TextViewMediumFont tvProductName;
        //TextViewMediumFont tvProductCode;
        //TextViewMediumFont tvDescription;
        //TextViewMediumFont tvTotalPoints;
        //TextViewMediumFont tvPrice;
        //TextViewMediumFont tvDistributorPer;
        //TextViewMediumFont tvDealerPer;
        //TextViewMediumFont tvCustomerPer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnDeleteBanner = itemView.findViewById(R.id.tvDeleteBanner);
            //tvProductName = itemView.findViewById(R.id.tvProductName);
            //tvProductCode = itemView.findViewById(R.id.tvProductCode);
            //tvTotalPoints = itemView.findViewById(R.id.tvTotalPoints);
            //tvDescription = itemView.findViewById(R.id.tvDescription);
            //tvPrice = itemView.findViewById(R.id.tvPrice);
            //tvDistributorPer = itemView.findViewById(R.id.tvDistributorPer);
            //tvDealerPer = itemView.findViewById(R.id.tvDealerPer);
            //tvCustomerPer = itemView.findViewById(R.id.tvCustomerPer);
        }
    }

    private void DeleteBanner(String BannerId, int pos,ArrayList<GetBannerListPojo> getBannerListPojoArrayList) {
        DialogUtil.showProgressDialogNotCancelable(context, "");
        ApiImplementer.deleteBannerImplementer(BannerId, new Callback<ArrayList<DeleteBannerPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<DeleteBannerPojo>> call, Response<ArrayList<DeleteBannerPojo>> response) {
                DialogUtil.hideProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null && response.body().size() > 0 &&
                            response.body().get(0).getStatus() == 1) {
                        Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        getBannerListPojoArrayList.remove(pos);
                        notifyDataSetChanged();
                    } else {
                        if (!CommonUtil.checkIsEmptyOrNullCommon(response.body().get(0).getMsg())) {
                            Toast.makeText(context, "" + response.body().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DeleteBannerPojo>> call, Throwable t) {
                DialogUtil.hideProgressDialog();
                Toast.makeText(context, "Request Failed:- " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
